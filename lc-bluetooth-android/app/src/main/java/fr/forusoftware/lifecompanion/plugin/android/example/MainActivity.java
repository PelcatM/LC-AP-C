package fr.forusoftware.lifecompanion.plugin.android.example;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * Example class to connect to provide a custom bluetooth service
 *
 * @author Mathieu THEBAUD <math.thebaud@gmail.com>
 */
public class MainActivity extends Activity {
    private static final String LC_SERVICE_NAME = "LifeCompanion Android Bluetooth Service";
    private static final String LC_SERVICE_UUID = "3f25f1ab-b55e-4d96-97a0-1a021c471d17";

    private static final String LOG_TAG = "LCBluetoothService";

    /**
     * ID for start bluetooth intent
     */
    private static final int ENABLE_BLUETOOTH_INTENT_ID = 1;

    /**
     * Current bluetooth server thread
     */
    private LCAndroidServiceThread serviceThread;

    // View items
    private TextView textLogConsole;
    private Button buttonLaunch, buttonStop;
    private ScrollView scrollConsole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textLogConsole = (TextView) this.findViewById(R.id.text_log_console);
        scrollConsole = (ScrollView) this.findViewById(R.id.text_log_scroll);

        //Launch button : enable bluetooth and launch the bluetooth server
        buttonLaunch = (Button) this.findViewById(R.id.button_launch_service);
        buttonLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Bluetooth is not enabled, or device not visible, request the bluetooth to be enabled, and phone to be discoverable
                final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                if (!bluetoothAdapter.isEnabled() || bluetoothAdapter.
                        getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
                    appendMessageToConsole("Demande d'activation du bluetooth par l'utilisateur");
                    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                    startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH_INTENT_ID);
                } else {
                    launchBluetoothServer();
                }
            }
        });

        //Stop button : stop the running bluetooth server
        buttonStop = (Button) this.findViewById(R.id.button_stop_service);
        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    appendMessageToConsole("Arrêt du service");
                    serviceThread.stopService();
                    buttonStop.setEnabled(false);
                    buttonLaunch.setEnabled(true);
                } catch (Exception e) {
                    Log.e(LOG_TAG, "Couldn't stop bluetooth serveur", e);
                }
            }
        });
    }

    /**
     * Launch a new bluetooth server in a new {@link LCAndroidServiceThread}
     */
    private void launchBluetoothServer() {
        try {
            appendMessageToConsole("Lancement du service");
            serviceThread = new LCAndroidServiceThread();
            serviceThread.start();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Couldn't launch bluetooth serveur", e);
        }
    }

    // When intent to activate bluetooth returns
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ENABLE_BLUETOOTH_INTENT_ID) {
            if (resultCode != RESULT_CANCELED) {
                appendMessageToConsole("Bluetooth activé et visible pour " + resultCode + "s");
                this.launchBluetoothServer();
            } else {
                appendMessageToConsole("Echec de l'activation du bluetooth");
            }
        }
    }

    /**
     * Method to append a new message in the {@link TextView}, and to scroll to this message
     *
     * @param msg the message to add
     */
    private void appendMessageToConsole(final String msg) {
        textLogConsole.post(new Runnable() {
            public void run() {
                textLogConsole.append("\n" + msg);
                //Post because the scroll view should be ready to scroll
                scrollConsole.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollConsole.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        });
    }

    /**
     * Thread implementation that launch a bluetooth server and accept the first connection.
     */
    private class LCAndroidServiceThread extends Thread {
        private BluetoothServerSocket serverSocket;
        private BluetoothSocket currentSocket;
        private boolean running = true;

        public LCAndroidServiceThread() throws IOException {
            serverSocket = BluetoothAdapter.getDefaultAdapter().
                    listenUsingInsecureRfcommWithServiceRecord(LC_SERVICE_NAME,
                            UUID.fromString(LC_SERVICE_UUID));
            textLogConsole.append("\nService créé");
            buttonLaunch.setEnabled(false);
            buttonStop.setEnabled(true);
        }

        @Override
        public void run() {
            try {
                byte[] reponse = new byte[65];
                byte[] jsonMessage = new byte[65];

                // Wait for a connexion to the service
                appendMessageToConsole("En attente d'une connection au service...");
                currentSocket = serverSocket.accept();
                appendMessageToConsole("Connexion acceptée au service " + currentSocket.getRemoteDevice().getAddress() + " - " + currentSocket.getRemoteDevice().getName());
                try (DataInputStream dataIS = new DataInputStream(currentSocket.getInputStream())) {
                    try (DataOutputStream dataOS = new DataOutputStream(currentSocket.getOutputStream())) {
                        appendMessageToConsole("Stream créés");
                        while (running) {
                            dataIS.read(reponse);
                            String recep = "";
                            for(int i = 0; i < reponse.length; i++){
                                recep = recep + reponse[i]+" ,";

                            }

                            for(int z = 5; z < reponse.length; z++){
                                jsonMessage[z - 5] = reponse[z];
                            }

                            String json = new String(jsonMessage);

                            appendMessageToConsole(recep);
                            appendMessageToConsole(json);
                            dataOS.write(reponse);
                        }
                    }
                }
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error in service thread", e);
                appendMessageToConsole("Problème dans le Thread du service " + e.getMessage() + "\"");
            }
        }

        /**
         * Try to stop the current bluetooth service
         *
         * @throws IOException if stop fails
         */
        public void stopService() throws IOException {
            if (currentSocket != null) {
                currentSocket.close();
            }
            if (this.serverSocket != null) {
                this.running = false;
                this.serverSocket.close();
            }
        }
    }
}
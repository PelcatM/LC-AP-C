package fr.forusoftware.lifecompanion.plugin.android.example;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CyclicBarrier;
import java.util.function.Predicate;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DataElement;
import javax.bluetooth.DeviceClass;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.DiscoveryListener;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.RemoteDevice;
import javax.bluetooth.ServiceRecord;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example class to connect to a bluetooth device : this use Bluecove to select a device, and to connect to the provided service.
 * @author Mathieu THEBAUD <math.thebaud@gmail.com>
 */
public class ExampleBluetoothApp {
	private final static Logger LOGGER = LoggerFactory.getLogger(ExampleBluetoothApp.class);

	/**
	 * Standard bluetooth attribute ID for service name
	 */
	private static final int NAME_ATTRIBUTE_VALUE = 0x0100;

	// Class part : "Device/service settings"
	//========================================================================
	/**
	 * Name of our phone : should be the visible name set in Android setting (case insensitive)
	 */
	private static final String WANTED_DEVICE_NAME = "simon";

	/**
	 * ID of the custom service implemented by our Android App
	 */
	private static final String WANTED_SERVICE_UUID = "3f25f1ab-b55e-4d96-97a0-1a021c471d17";
	//========================================================================

	/**
	 * Filter for device wanted name
	 */
	private static final Predicate<? super RemoteDevice> DEVICE_NAME_FILTER = d -> {
		try {
			return StringUtils.containsIgnoreCase(d.getFriendlyName(false), WANTED_DEVICE_NAME);
		} catch (IOException e) {
			LOGGER.warn("Can't get name for device {}", d.getBluetoothAddress(), e);
			return false;
		}
	};

	/**
	 * List of found devices in discovery
	 */
	private List<RemoteDevice> foundDevices;

	/**
	 * List of found service URL in discovery
	 */
	private List<String> foundServiceUrls;

	private final LocalDevice localDevice;
	private final DiscoveryAgent discoveryAgent;
	private final DiscoveryListener discoveryListener;
	private final CyclicBarrier discoveryCyclicBarrier;

	public ExampleBluetoothApp() throws BluetoothStateException {
		discoveryListener = new DiscoveryListenerImpl();
		localDevice = LocalDevice.getLocalDevice();
		discoveryAgent = localDevice.getDiscoveryAgent();
		foundDevices = new ArrayList<>();
		foundServiceUrls = new ArrayList<>();
		discoveryCyclicBarrier = new CyclicBarrier(2);//2 thread should join at the same moment
	}

	/**
	 * Main method : to launch the bluetooth test
	 */
	public void launchExample() throws Exception {
		LOGGER.info("Launch bluetooth connection example");
		RemoteDevice selected = selectRemoteDevice();
		LOGGER.info("Found the wanted device, with address {}", selected.getBluetoothAddress());
		String serviceURL = searchService(selected);
		LOGGER.info("Found the wanted service with URL {}", serviceURL);
		executeRequestOnService(serviceURL);
		LOGGER.info("Bluetooth connection example finished");
	}

	/**
	 * Select the wanted device set in {@link #WANTED_DEVICE_NAME}.<br>
	 * Try to select from already connected devices (cache), if not found, launch a discovery to find it.
	 * @return the wanted remote device
	 * @throws Exception if we can't find the device
	 */
	private RemoteDevice selectRemoteDevice() throws Exception {
		//Wanted device might be cached
		RemoteDevice[] cachedDevices = discoveryAgent.retrieveDevices(DiscoveryAgent.CACHED);
		if (cachedDevices != null) {
			LOGGER.info("Found {} cached devices", cachedDevices.length);
			Optional<RemoteDevice> wantedCachedDevice = Arrays.stream(cachedDevices).filter(DEVICE_NAME_FILTER).findAny();
			if (wantedCachedDevice.isPresent()) {
				LOGGER.info("Wanted device found in cached devivce");
				return wantedCachedDevice.get();
			}
		}
		//Discover devices
		LOGGER.info("Device search will start, enable your bluetooth visibility on Android");
		boolean started = discoveryAgent.startInquiry(DiscoveryAgent.GIAC, this.discoveryListener);
		if (started) {
			this.discoveryCyclicBarrier.await();//wait for the end
			Optional<RemoteDevice> foundDevice = foundDevices.stream().filter(DEVICE_NAME_FILTER).findAny();
			if (foundDevice.isPresent()) {
				LOGGER.info("Wanted device found in discovery");
				return foundDevice.get();
			}
		} else {
			LOGGER.error("Couldn't start device discovery");
		}
		throw new IllegalArgumentException("Couldn't find wanted device with name " + WANTED_DEVICE_NAME);
	}

	/**
	 * Try to search for the wanted LC service on a given device
	 * @param selected the device that provide the service
	 * @return the service URL
	 * @throws Exception if we can't find the wanted service on the given device
	 */
	private String searchService(RemoteDevice selected) throws Exception {
		this.discoveryCyclicBarrier.reset();
		//Settings
		UUID[] searchUuidSet = new UUID[] { new UUID(WANTED_SERVICE_UUID.replace("-", ""), false) };//Standard Java UUID has -, Bluecove UUID has not
		int[] attrIDs = new int[] { NAME_ATTRIBUTE_VALUE };
		//Launch search and wait for complet
		LOGGER.info("Will launch service search for service with UUID {}, make sure that the Android App is running with the service enabled",
				WANTED_SERVICE_UUID);
		discoveryAgent.searchServices(attrIDs, searchUuidSet, selected, this.discoveryListener);
		this.discoveryCyclicBarrier.await();
		//Result
		LOGGER.info("Service search completed, found {} services", this.foundServiceUrls.size());
		if (this.foundServiceUrls.isEmpty()) {
			throw new IllegalArgumentException("Couldn't find the wanted service with UUID " + WANTED_SERVICE_UUID);
		} else {
			return this.foundServiceUrls.get(0);
		}
	}

	/**
	 * Execute a demo request on a service connected through bluetooth : send a UUID every 5 second and read the response.
	 * @param serviceURL the service URL where messages should be sent
	 * @throws Exception if the connection fails
	 */
	private void executeRequestOnService(String serviceURL) throws Exception {
		//Open a socket connection
		StreamConnection connection = (StreamConnection) Connector.open(serviceURL);
		LOGGER.info("Connection opened to service");
		// OS stream for response, IS for request
		try (DataOutputStream dataOS = connection.openDataOutputStream()) {
			try (DataInputStream dataIS = connection.openDataInputStream()) {
				LOGGER.info("Stream opened");
				// Write anything you want : can also wrap stream on Object-Input/Output-Stream, or use XML/JSON in UTF String.
				// Test message every 5 second 
				while (true) {
					dataOS.writeUTF("Test message " + java.util.UUID.randomUUID());
					String response = dataIS.readUTF();
					LOGGER.info("Got server response \"{}\"", response);
					Thread.sleep(5000);
				}
			}
		}
	}

	// Class part : "Bluetooth state listener"
	//========================================================================
	/**
	 * Private subclass to listen for discovery progress.<br>
	 * Use the {@link CyclicBarrier} to synchronize with the main Thread.
	 */
	private class DiscoveryListenerImpl implements DiscoveryListener {

		@Override
		public void deviceDiscovered(RemoteDevice btDevice, DeviceClass cod) {
			try {
				LOGGER.info("Found a bluetooth device, address {}, name {}", btDevice.getBluetoothAddress(), btDevice.getFriendlyName(false));
				foundDevices.add(btDevice);
			} catch (IOException e) {
				LOGGER.warn("Couldn't add the bluetooth device {}", btDevice.getBluetoothAddress(), e);
			}
		}

		@Override
		public void inquiryCompleted(int discType) {
			try {
				LOGGER.info("Inquiry completed with result {}", discType);
				discoveryCyclicBarrier.await();
			} catch (Exception e) {
				LOGGER.error("Couldn't notify that the bluetooth device search is completed", e);
			}
		}

		@Override
		public void servicesDiscovered(int transID, ServiceRecord[] servRecord) {
			for (ServiceRecord serviceRecord : servRecord) {
				String url = serviceRecord.getConnectionURL(ServiceRecord.AUTHENTICATE_NOENCRYPT, false);
				if (url != null) {
					DataElement serviceName = serviceRecord.getAttributeValue(NAME_ATTRIBUTE_VALUE);
					LOGGER.info("Found the wanted service with url {}, and name {}", url, serviceName != null ? serviceName.getValue() : "null");
					foundServiceUrls.add(url);
				}
			}
		}

		@Override
		public void serviceSearchCompleted(int transID, int respCode) {
			try {
				LOGGER.info("Service search completed with code {}", respCode);
				discoveryCyclicBarrier.await();
			} catch (Exception e) {
				LOGGER.error("Couldn't notify that the bluetooth service search is completed", e);
			}
		}

	}
	//========================================================================

	// Class part : "MAIN"
	//========================================================================
	/**
	 * Launch the test
	 */
	public static void main(String[] args) {
		System.out.println(java.util.UUID.randomUUID());
		try {
			new ExampleBluetoothApp().launchExample();
		} catch (Exception e) {
			LOGGER.info("Bluetooth example failed", e);
		}
	}
	//========================================================================

}

package fr.forusoftware.lifecompanion.plugin.android.example;

import fr.forusoftware.lifecompanion.plugin.android.example.ExampleBluetoothApp;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.RemoteDevice;

public class TestExampleBluetoothApp {
	
	public static void main ( String[] args){
		testExampleBluetoothApp();
	}
	
	public static void testExampleBluetoothApp(){
		try {
			ExampleBluetoothApp ex = new ExampleBluetoothApp();
			if(ex.getLocalDevice().getFriendlyName().equals("DESKTOP-R65IKS8")){
				System.out.println("Vous etes bien sur le bon serveur");
			}
			else System.out.println("Vous n'etes pas sur le bon serveur");
		} catch (BluetoothStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}

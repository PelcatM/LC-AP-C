package fr.forusoftware.lifecompanion.plugin.android.example;

import org.json.simple.JSONObject;


public class Commandes{
	
	  public static byte[] writeCommande(String cmd) throws Exception{
		  byte[] ret = null;
		  
		  if(cmd.equals("send")) {
			  
			  JSONObject obj = new JSONObject();
			  obj.put("num", "0785531462");
			  obj.put("message", "Hello World");
			  
			  byte[] message = Converting.JsonToByte(obj);
			  ret = new byte[20+message.length];
			  ret[0] = 10;
			  ret[1] = (byte) message.length;
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;
			  for(int i = 0; i < message.length; i++) {
				  ret[i+5] = message[i];
			  }
			  
		  }
		  return ret;
	  }
	  
	  public static byte[] writeCt_List() throws Exception{
		  byte[] ret = new byte[5];
		  
			  ret[0] = 1;
			  ret[1] = 0;
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;
			  
		  return ret;
	  }
	  
	  public static byte[] writeCt(int idCt) throws Exception{
		  byte[] ret = new byte[5];
		  
			  ret[0] = 1;
			  ret[1] = 1;
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;
			  ret[5] = (byte) idCt;
			  
		  return ret;
	  }
}

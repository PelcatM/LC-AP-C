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
		  
			  ret[0] = 2;
			  ret[1] = 1;
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;
			  ret[5] = (byte) idCt;
			  
		  return ret;
	  }
	  
	  public static byte[] writeCt_Add(byte vCard) throws Exception{
		  byte[] ret = new byte[6];
		  
			  ret[0] = 3;
			  ret[1] = 1;
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;
			  ret[5] = vCard;
			  
		  return ret;
	  }
	  
	  public static byte[] writeCt_Edit(int idCt, byte vCard) throws Exception{
		  byte[] ret = new byte[7];
		  
			  ret[0] = 4;
			  ret[1] = 2;
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;
			  ret[5] = (byte) idCt;
			  ret[6] = vCard;
			  
		  return ret;
	  }
	  
	  public static byte[] writeCt_Remove(int idCtDelete) throws Exception{
		  byte[] ret = new byte[6];
		  
			  ret[0] = 5;
			  ret[1] = 1;
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;
			  ret[5] = (byte) idCtDelete;
			  
		  return ret;
	  }
	  
	  public static byte[] writeSms_Send(JSONObject obj) throws Exception{			//Il faudra ajouter l'ID du contact
		  byte[] ret = null;

		  byte[] message = Converting.JsonToByte(obj);
		  
			  ret = new byte[20+message.length];
			  ret[0] = 6;
			  ret[1] = (byte) message.length;
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;
			  
			  for(int i = 0; i < message.length; i++) {
				  ret[i+5] = message[i];
			  }
			  
		  return ret;
	  }
	  
	  public static byte[] writeSms_Send_Grp(int grpId, JSONObject obj) throws Exception{
		  byte[] ret = null;
		  int taille = 0;

		  byte[] message = Converting.JsonToByte(obj);
		  
			  ret = new byte[20+message.length];
			  taille = message.length+1;
			  ret[0] = 7;
			  ret[1] = (byte) taille;
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;
			  ret[5] = (byte) grpId;
			  
			  for(int i = 0; i < message.length; i++) {
				  ret[i+6] = message[i];
			  }
			  
		  return ret;
	  }
	  
	  public static byte[] writeSms_Get(int idCt) throws Exception{
		  byte[] ret = new byte[6];
		  
			  ret[0] = 8;
			  ret[1] = 1;		//Variable dans la spécif mais différent de tout les autres coups.
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;
			  ret[5] = (byte) idCt;

		  return ret;
	  }
	  
	  public static byte[] writeSms_Get_Num(int idSms) throws Exception{
		  byte[] ret = new byte[6];
		  
			  ret[0] = 9;
			  ret[1] = 1;		
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;
			  ret[5] = (byte) idSms;

		  return ret;
	  }
	  
	  public static byte[] writeSms_All() throws Exception{
		  byte[] ret = new byte[6];
		  
			  ret[0] = 10;
			  ret[1] = 0;		
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;

		  return ret;
	  }
	  
	  public static byte[] writeSms_Get_NumDest(int idSms) throws Exception{	//Changement du nom car conflit
		  byte[] ret = new byte[6];
		  
			  ret[0] = 11;
			  ret[1] = 1;		
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;
			  ret[5] = (byte) idSms;

		  return ret;
	  }
	  
}

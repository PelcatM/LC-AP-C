package fr.forusoftware.lifecompanion.plugin.android.example;

import java.util.Scanner;

import org.json.simple.JSONObject;

/**
 * Class who regroup all the methode to write some messages in Bytes verifying our Bluetooth Protocol
 * @author Simon Naveau
 * */
public class Commandes{
	
	 public static byte[] writeCommande(String cmd) throws Exception{		//methode juste pour le test d'envoi de sms
		  byte[] ret = null;
		  
		  if(cmd.equals("send")) {
			  
			  JSONObject obj = new JSONObject();
			  obj.put("num", "0785531462");
			  obj.put("message", "Message de test");
			  
			  byte[] message = Converting.JsonToByte(obj);
			  ret = new byte[5+message.length];
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
	  
	/**
	 * Create a message to launch the ask of the contact list on the mobile phone connected in bluetooth.
	 * @return The message in a table of byte ready to send.
	 * @throws Exception if we can't create the message
	 */
	  public static byte[] writeCt_List() throws Exception{
		  byte[] ret = new byte[5];
		  
			  ret[0] = 1;
			  ret[1] = 0;
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;
			  
		  return ret;
	  }
	  
	  /**
		 * Create a message to launch the ask of a specific contact on the mobile phone connected in bluetooth.
		 * @param idCt The id of the contact in the android device.
		 * @return The message in a table of byte ready to send.
		 * @throws Exception if we can't create the message
		 */
	  public static byte[] writeCt(int idCt) throws Exception{
		 // if (idCt > 4000000) throw new RuntimeException("ERROR : idCt too long for the transmission"); 		//Modifier pour avoir plus en utilisant les négatifs.
		  
		  byte[] ret = new byte[6];
		  
			  ret[0] = 2;
			  ret[1] = 1;
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;
			  ret[5] = (byte) idCt;
			  
		  return ret;
	  }
	  
	  /**
		 * Create a message to launch the add of a contact in the contact list of the mobile phone connected in bluetooth.
		 * @param vCard The information on the new contact (vCard converted in a byte)
		 * @return The message in a table of byte ready to send.
		 * @throws Exception if we can't create the message
		 */
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
	  
	  /**
		 * Create a message to launch the edit a contact in the contact list of the mobile phone connected in bluetooth.
		 * @param idCt The id of the contact in the android device to edit.
		 * @param vCard The information on the contact (vCard converted in a byte)
		 * @return The message in a table of byte ready to send.
		 * @throws Exception if we can't create the message
		 */
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
	  
	  /**
		 * Create a message to launch the remove of a contact in the contact list of the mobile phone connected in bluetooth.
		 * @param idCtDelete The id of the contact to remove in the android device to edit.
		 * @return The message in a table of byte ready to send.
		 * @throws Exception if we can't create the message
		 */
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
	  
	  /**
		 * Create a message to launch the sending of an SMS with the android device to a mobile phone.
		 * @param obj The information for the send (phone number and message) in a JSONObject.
		 * @return The message in a table of byte ready to send.
		 * @throws Exception if we can't create the message
		 */
	  public static byte[] writeSms_Send(JSONObject obj) throws Exception{			//Il faudra ajouter l'ID du contact
		  byte[] ret = null;

		  byte[] message = Converting.JsonToByte(obj);
		  
			  ret = new byte[5+message.length];
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
	  
	  /**
		 * Create a message to launch the sending of an SMS to many phone numbers with the android device to a mobile phone.
		 * @param obj The information for the send (phone number and message) in a JSONObject.
		 * @param grpId The id of the group in the android device connected.
		 * @return The message in a table of byte ready to send.
		 * @throws Exception if we can't create the message
		 */
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
	  
	  /**
		 * Create a message to launch the ask of the SMS from a contact on the android device to a mobile phone.
		 * @param idCt The id of the contact in the android device.
		 * @return The message in a table of byte ready to send.
		 * @throws Exception if we can't create the message
		 */
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
	  
	  /**
		 * Create a message to launch the ask of the phone number of a SMS sender on the android device to a mobile phone.
		 * @param idSms The id of the SMS in the android device.
		 * @return The message in a table of byte ready to send.
		 * @throws Exception if we can't create the message
		 */
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
	  
	  /**
		 * Create a message to launch the ask a list of all the SMS in the android device to a mobile phone.
		 * @return The message in a table of byte ready to send.
		 * @throws Exception if we can't create the message
		 */
	  public static byte[] writeSms_All() throws Exception{
		  byte[] ret = new byte[5];
		  
			  ret[0] = 10;
			  ret[1] = 0;		
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;

		  return ret;
	  }
	  
	  /**
		 * Create a message to launch the ask of the phone number of a SMS receiver on the android device to a mobile phone.
		 * @param idSms The id of the SMS in the android device.
		 * @return The message in a table of byte ready to send.
		 * @throws Exception if we can't create the message
		 */
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
	  
	  /**
		 * Create a message to answer to a SMS_NOTIFY command launch by the android device. Answer if the information is arrived or not to the serveur.
		 * @param received True if received correctly or false if not.
		 * @return The message in a table of byte ready to send.
		 * @throws Exception if we can't create the message
		 */
	  public static byte[] writeCt_Sms_Notify_Ret(boolean received) throws Exception{	
		  byte[] ret = new byte[6];
		  
			  ret[0] = 12;
			  ret[1] = 1;		
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;											//Envoi de l'aquitement ou non sur un octet et non un bit unique pour faciliter la lecture.
			  if(received == true) {
				  ret[5] = (byte) 1;
			  }
			  else {
				  ret[5] = (byte) 0;
			  }

		  return ret;
	  }
	  
	  /**
		 * Create a message to launch a call with the android device connected.
		 * @param choice Defined if the call is made with a phone number(false) or a contact id(true) .
		 * @param idCt The id of the contact in the android device.
		 * @param num The phone number to call
		 * @return The message in a table of byte ready to send.
		 * @throws Exception if we can't create the message
		 */
	  public static byte[] writeCall(boolean choice,int idCt, int num) throws Exception{		//if choix = true  => utilisation de l'identifiant sinon le numero de télephone
		  byte[] ret = new byte[6];
		  
			  ret[0] = 13;
			  ret[1] = 1;		
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;
			  if(choice == true) {
				  ret[5] = (byte) idCt;
			  }
			  else {
				  ret[5] = (byte) num;					//WARNING !!! Va surement poser un problème
			  }

		  return ret;
	  }
	  
	  /**
		 * Create a message to launch a call with the video with the android device connected.
		 * @param choice Defined if the call is made with a phone number(false) or a contact id(true) .
		 * @param idCt The id of the contact in the android device.
		 * @param num The phone number to call
		 * @return The message in a table of byte ready to send.
		 * @throws Exception if we can't create the message
		 */	
	  public static byte[] writeCall_Vid(boolean choice, int idCt, int num) throws Exception{			//Amelioration
		  byte[] ret = new byte[6];
		  
			  ret[0] = 14;
			  ret[1] = 1;		
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;
			  if(choice == true) {				
				  ret[5] = (byte) idCt;
			  }
			  else {
				  ret[5] = (byte) num;
			  }

		  return ret;
	  }
	  
	  @SuppressWarnings("unchecked")
	public static byte[] chooseCmd(CmdList cmd) throws Exception{
			 byte[] ret = null;
			 Scanner sc = new Scanner(System.in);
			 
		        switch (cmd) {
		            case CT_LIST:
		            	ret = Commandes.writeCt_List();
		            	break;
		            	
		            case CT:
		            	System.out.println("Veuillez saisir l'identifiant du contact a ajouter :");		//la récup ce fera d'une autre façon surement automatique
		            	int id1 = sc.nextInt();
		            	ret = Commandes.writeCt(id1);
		            	break;
		            	
		            case CT_ADD:  
		            	//byte vCard = Converting.vCardToByte(); //A implementer
		            	byte vc1 = 5;
		            	ret = Commandes.writeCt_Add(vc1);
		            	break;
		            	
		            case CT_EDIT:
		            	System.out.println("Veuillez saisir l'identifiant du contact a editer:");		//a changer car pas connu par le user
		            	int id2 = sc.nextInt();
		            	
		            	//byte vCard = Converting.vCardToByte(); //A implementer
		            	byte vc2 = 5;
		            	ret = Commandes.writeCt_Edit(id2, vc2);
		            	break;
		            	
		            case CT_REMOVE:
		            	System.out.println("Veuillez saisir l'identifiant du contact a supprimer :");		//a changer car pas connu par le user
		            	int id3 = sc.nextInt();
		            	ret = Commandes.writeCt_Remove(id3);
		            	break;
		            	
		            case SMS_SEND: 
		            	JSONObject obj = new JSONObject();
		  			  
		            	System.out.println("Veuillez saisir le numero de telephone du destinataire:");		
		            	String num = sc.nextLine();
		            	obj.put("num", num);
		            	
		            	System.out.println("Veuillez saisir le message:");		
		            	String msg = sc.nextLine();
		            	obj.put("message", msg);
		            	
		            	ret = Commandes.writeSms_Send(obj);
		            	System.out.println("ecriture réussite");
		            	break;
		            	
		            case SMS_SEND_GRP:
		            	JSONObject obj2 = new JSONObject();
			  			  
		            	System.out.println("Veuillez saisir l'identifiant du groupe destinataire :");		//a changer car pas connu par le user
		            	int id5 = sc.nextInt();
		            	
		            	System.out.println("Veuillez saisir le message:");		
		            	String msg2 = sc.nextLine();
		            	obj2.put("message", msg2);
		            	
		            	ret = Commandes.writeSms_Send_Grp(id5 , obj2);
		            	break;
		            	
		            case SMS_GET:
		            	System.out.println("Veuillez saisir l'identifiant du contact :");				//a changer car pas connu par le user
		            	int id6 = sc.nextInt();
		            	ret = Commandes.writeSms_Get(id6);
		            	break;
		            	
		            case SMS_GET_NUMDEST:  
		            	System.out.println("Veuillez saisir l'identifiant du SMS a analyser :");		//a changer car pas connu par le user
		            	int id7 = sc.nextInt();
		            	ret = Commandes.writeSms_Get_NumDest(id7);
		            	break;
		            	
		            case SMS_ALL: 
		            	ret = Commandes.writeSms_All();
		            	break;
		            	
		            case SMS_GET_NUM: 
		            	System.out.println("Veuillez saisir l'identifiant du SMS a analyser :");		//a changer car pas connu par le user
		            	int id8 = sc.nextInt();
		            	ret = Commandes.writeSms_Get_Num(id8);
		            	break;
		            	
		            case SMS_NOTIFY_RET_YES: 
		            	ret = Commandes.writeCt_Sms_Notify_Ret(true);
		            	break;
		            	
		            case SMS_NOTIFY_RET_NO:
		            	ret = Commandes.writeCt_Sms_Notify_Ret(false);
		            	break;
		            	
		            case CALL_NUM:
		            	System.out.println("Veuillez saisir le numero a appeler :");		
		            	int num1 = sc.nextInt();
		            	ret = Commandes.writeCall(false, 0, num1);
		            	break;
		            	
		            case CALL_ID:
		            	System.out.println("Veuillez saisir l'identifiant du contact a appeler :");		//a changer car pas connu par le user
		            	int id9 = sc.nextInt();
		            	ret = Commandes.writeCall(true, id9, 0);
		            	break;
		            	
		            case CALL_VID_NUM:
		            	System.out.println("Veuillez saisir le numero a appeler :");		
		            	int num2 = sc.nextInt();
		            	ret = Commandes.writeCall_Vid(false, 0, num2);
		            	break;
		            	
		            case CALL_VID_ID:
		            	System.out.println("Veuillez saisir l'identifiant du contact a appeler :");		//a changer car pas connu par le user
		            	int id10 = sc.nextInt();
		            	ret = Commandes.writeCall_Vid(true, id10, 0);
		            	break;
		            	
		            default: 
		            	System.out.println("Error in the ask request, no conform request");
		            	break;
		        }
		        sc.close();
		        return ret;
	  }
}

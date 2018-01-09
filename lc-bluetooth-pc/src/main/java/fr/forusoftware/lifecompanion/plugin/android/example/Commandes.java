package fr.forusoftware.lifecompanion.plugin.android.example;

import org.json.simple.JSONObject;


public class Commandes{
	
	  public static byte[] writeCommande(String cmd) throws Exception{
		  byte[] ret = null;
		  
		  if(cmd.equals("send")) {
			  
			  JSONObject obj = new JSONObject();
			  obj.put("num", "0785531462");
			  obj.put("message", "pipi caca prout j'aime les chats");
			  
			  byte[] message = Converting.JsonToByte(obj);
			  ret = new byte[5+message.length];
			  ret[0] = 10;
			  ret[1] = (byte) message.length;
			  ret[2] = 0;
			  ret[3] = 0;
			  ret[4] = 0;
			  System.out.println(message.length);
			  for(int i = 5; i < message.length; i++) {
				  ret[i] = message[i-5];
			  }
			  
		  }
		  return ret;
	  }

	  public static void response(byte[] rep){
	    if( rep[0]==0){
	      ct_list();
	    }
	    else if(rep[0]==1){
	      ct_list_rest();
	    }
	    else if(rep[0]==2){
	      ct();
	    }
	    else if(rep[0]==3){
	      ct_ret();
	    }
	    else if(rep[0]==4){
	      ct_add();
	    }
	    else if(rep[0]==5){
	      ct_add_ret();
	    }
	    else if(rep[0]==6){
	      ct_edit();
	    }
	    else if(rep[0]==7){
	      ct_edit_ret();
	    }
	    else if(rep[0]==8){
	      ct_remove();
	    }
	    else if(rep[0]==9){
	      ct_remove_ret();
	    }
	    else if(rep[0]==10){
	      sms_send();
	    }
	    else if(rep[0]==11){
	      sms_send_ret();
	    }
	    else if(rep[0]==12){
	      sms_send_grp();
	    }
	    else if(rep[0]==13){
	      sms_send_grp_ret();
	    }
	    else if(rep[0]==14){
	      sms_get();
	    }
	    else if(rep[0]==15){
	      sms_get_ret();
	    }
	    else if(rep[0]==16){
	      sms_get_num();
	    }
	    else if(rep[0]==17){
	      sms_all();
	    }
	    else if(rep[0]==18){
	      ct_sms_all_ret();
	    }
	    else if(rep[0]==19){
	      sms_get_num();
	    }
	    else if(rep[0]==20){
	      sms_get_num_ret();
	    }
	    else if(rep[0]==21){
	      sms_notify();
	    }
	    else if(rep[0]==22){
	      ct_sms_notify_ret();
	    }
	    else if(rep[0]==23){
	      call();
	    }
	    else if(rep[0]==24){
	      call_ret();
	    }
	    else if(rep[0]==25){
	      call_vid();
	    }
	    else if(rep[0]==26){
	      call_vid_ret();
	    }
	  }
	
	  private static void ct_list(){
	    System.out.println("CT_LIST");
	  }
	
	  private static void ct_list_rest(){
	    System.out.println("CT_LIST_REST");
	  }
	
	  private static void ct(){
	    System.out.println("CT");
	  }
	
	  private static void ct_ret(){
	    System.out.println("CT_RET");
	  }
	
	  private static void ct_add(){
	    System.out.println("CT_ADD");
	  }
	
	  private static void ct_add_ret(){
	    System.out.println("CT_ADD_RET");
	  }
	
	  private static void ct_edit(){
	    System.out.println("CT_EDIT");
	  }
	
	  private static void ct_edit_ret(){
	    System.out.println("CT_EDIT_RET");
	  }
	
	  private static void ct_remove(){
	    System.out.println("CT_REMOVE");
	  }
	
	  private static void ct_remove_ret(){
	    System.out.println("CT_REMOVE_RET");
	  }
	
	  private static void sms_send(){
	    System.out.println("SMS_SEND");
	  }
	
	  private static void sms_send_ret(){
	    System.out.println("SMS_SEND_RET");
	  }
	
	  private static void sms_send_grp(){
	    System.out.println("SMS_SEND_GRP");
	  }
	
	  private static void sms_send_grp_ret(){
	    System.out.println("SMS_SEND_GRP_RET");
	  }
	
	  private static void sms_get(){
	    System.out.println("SMS_GET");
	  }
	
	  private static void sms_get_ret(){
	    System.out.println("SMS_GET_RET");
	  }
	
	  private static void sms_all(){
	    System.out.println("SMS_ALL");
	  }
	
	  private static void ct_sms_all_ret(){
	    System.out.println("CT_SMS_ALL_RET");
	  }
	
	  private static void sms_get_num(){
	    System.out.println("SMS_GET_NUM");
	  }
	
	  private static void sms_get_num_ret(){
	    System.out.println("SMS_GET_NUM_RET");
	  }
	
	  private static void sms_notify(){
	    System.out.println("SMS_NOTIFY");
	  }
	
	  private static void ct_sms_notify_ret(){
	    System.out.println("CT_SMS_NOTIFY_RET");
	  }
	
	  private static void call(){
	    System.out.println("CALL");
	  }
	
	  private static void call_ret(){
	    System.out.println("CALL_RET");
	  }
	
	  private static void call_vid(){
	    System.out.println("CALL_VID");
	  }
	
	  private static void call_vid_ret(){
	    System.out.println("CALL_VID_RET");
	  }
}

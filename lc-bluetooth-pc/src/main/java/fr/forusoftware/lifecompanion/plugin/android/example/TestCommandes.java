package fr.forusoftware.lifecompanion.plugin.android.example;

import org.json.JSONException;
import org.json.simple.JSONObject;

public class TestCommandes {

	public static void main(String[] args) throws Exception{
		testWriteCt_List();
		testWriteCt();
		testWriteCt_Add();
		testWriteCt_Edit();
		testWriteCt_Remove();
		testWriteSms_Send();
		testWriteSms_Send_Grp();
		testWriteSms_Get();
		testWriteSms_Get_Num();
		testWriteSms_All();
		testWriteSms_Get_NumDest();
		testWriteCt_Sms_Notify_Ret();
		testWriteCall();
		testWriteCall_Vid();
	}
	
	public static void testWriteCt_List() throws Exception{
		byte[] res = new byte[5];
		res = Commandes.writeCt_List();
		if(res[0]==1 && res[1]==0 && res[2]==0 && res[3]==0 && res[4]==0) System.out.println("Le tableau d octet a les bonnes valeurs avant l'envoi");
		else System.out.println("Mauvais valeur dans le tableau avant l'envoi");
	}
	
	public static void testWriteCt() throws Exception{
		//cas normal
		byte[] res = new byte[6];
		int mes = 2;
		res = Commandes.writeCt(mes);
		if(res[0]==2 && res[1]==1 && res[2]==0 && res[3]==0 && res[4]==0 && res[5]==(byte) mes) System.out.println("Le tableau d octet a les bonnes valeurs avant l'envoi");
		else System.out.println("Mauvaisee valeur dans le tableau avant l'envoi");
		/*//cas d'erreur
		byte[] res2 = new byte[6];
		int mes2 = 2147483648;
		res2 = Commandes.writeCt(mes2);
		if(res2[0]==2 && res2[1]==1 && res2[2]==0 && res2[3]==0 && res2[4]==0 && res2[5]==(byte) mes2) System.out.println((byte) mes2);
		else System.out.println((byte) mes2);*/
	}
	
	public static void testWriteCt_Add() throws Exception{
		//cas normal
		byte[] res = new byte[6];
		byte mes = 2;
		res = Commandes.writeCt_Add(mes);
		if(res[0]==3 && res[1]==1 && res[2]==0 && res[3]==0 && res[4]==0 && res[5]==(byte) mes) System.out.println("Le tableau d octet a les bonnes valeurs avant l'envoi");
		else System.out.println("Mauvaise valeur dans le tableau avant l'envoi");
		/*//cas d'erreur
		byte[] res2 = new byte[6];
		byte mes2 = 128;
		res2 = Commandes.writeCt_Add(mes2);
		if(res2[0]==3 && res2[1]==1 && res2[2]==0 && res2[3]==0 && res2[4]==0 && res2[5]==(byte) mes2) System.out.println((byte) mes2);
		else System.out.println((byte) mes2);*/
	}
	
	public static void testWriteCt_Edit() throws Exception{
		//cas normal
		byte[] res = new byte[7];
		int mes = 2;
		byte b = 2;
		res = Commandes.writeCt_Edit(mes, b);
		if(res[0]==4 && res[1]==2 && res[2]==0 && res[3]==0 && res[4]==0 && res[5]==(byte) mes && res[6]==b) System.out.println("Le tableau d octet a les bonnes valeurs avant l'envoi");
		else System.out.println("Mauvaise valeur dans le tableau avant l'envoi");
		/*//cas d'erreur
		byte[] res2 = new byte[7];
		int mes2 = 2147483648;
		byte b2 = 128;
		res2 = Commandes.writeCt_Edit(mes2, b);
		if(res2[0]==4 && res2[1]==2 && res2[2]==0 && res2[3]==0 && res2[4]==0 && res2[5]==(byte) mes2 && res[6]==b2) System.out.println((byte) mes2);
		else System.out.println((byte) mes2);*/
	}
	
	public static void testWriteCt_Remove() throws Exception{
		//cas normal
		byte[] res = new byte[6];
		int mes = 2;
		res = Commandes.writeCt_Remove(mes);
		if(res[0]==5 && res[1]==1 && res[2]==0 && res[3]==0 && res[4]==0 && res[5]==(byte) mes) System.out.println("Le tableau d octet a les bonnes valeurs avant l'envoi");
		else System.out.println("Mauvaise valeur dans le tableau avant l'envoi");
		/*//cas d'erreur
		byte[] res2 = new byte[6];
		int mes2 = 2147483648;
		res2 = Commandes.writeCt_Remove(mes2);
		if(res2[0]==5 && res2[1]==1 && res2[2]==0 && res2[3]==0 && res2[4]==0 && res2[5]==(byte) mes2) System.out.println((byte) mes2);
		else System.out.println((byte) mes2);*/
	}
	
	
	public static void testWriteSms_Send() throws Exception{
		JSONObject json = new JSONObject();
		json.put("tel","0695170827");
		json.put("message", "coucou toa");
		byte[] b = Commandes.writeSms_Send(json);
		byte[] d = new byte[b.length-5];
        for (int i = 0; i < b[1] + b[2] + b[3] + b[4]; i++) {
            d[i] = b[i + 5];
        }
        String dest2 = new String(d);
        String ret = json.toString();
		if(b[0]==6 && b[1]==(byte) b.length-5 && b[2]==0 && b[3]==0 && b[4]==0 && dest2.equals(ret)) System.out.println("Le tableau d octet a les bonnes valeurs avant l'envoi");
		else System.out.println("Mauvaise valeur dans le tableau avant l'envoi");
		
	}
	
	public static void testWriteSms_Send_Grp() throws Exception{
		JSONObject json = new JSONObject();
		int id = 1;
		json.put("tel","0695170827");
		json.put("message", "coucou toa");
		byte[] b = Commandes.writeSms_Send_Grp(id, json);
		byte[] d = new byte[b.length-6];
        for (int i = 0; i < b[1] + b[2] + b[3] + b[4]; i++) {
            d[i] = b[i + 6];
        }
        String dest2 = new String(d);
        String ret = json.toString();
		if(b[0]==7 && b[1]==(byte) b.length-6 && b[2]==0 && b[3]==0 && b[4]==0 && b[5]==id && dest2.equals(ret)) System.out.println("Le tableau d octet a les bonnes valeurs avant l'envoi");
		else System.out.println("Mauvaise valeur dans le tableau avant l'envoi");
		
	}
	
	public static void testWriteSms_Get() throws Exception{
		//cas normal
		byte[] res = new byte[6];
		int mes = 2;
		res = Commandes.writeSms_Get(mes);
		if(res[0]==8 && res[1]==1 && res[2]==0 && res[3]==0 && res[4]==0 && res[5]==(byte) mes) System.out.println("Le tableau d octet a les bonnes valeurs avant l'envoi");
		else System.out.println("Mauvaise valeur dans le tableau avant l'envoi");
		/*//cas d'erreur
		byte[] res2 = new byte[6];
		int mes2 = 2147483648;
		res2 = Commandes.writeCt(mes2);
		if(res2[0]==2 && res2[1]==1 && res2[2]==0 && res2[3]==0 && res2[4]==0 && res2[5]==(byte) mes2) System.out.println((byte) mes2);
		else System.out.println((byte) mes2);*/
	}
	
	public static void testWriteSms_Get_Num() throws Exception{
		//cas normal
		byte[] res = new byte[6];
		int mes = 2;
		res = Commandes.writeSms_Get_Num(mes);
		if(res[0]==9 && res[1]==1 && res[2]==0 && res[3]==0 && res[4]==0 && res[5]==(byte) mes) System.out.println("Le tableau d octet a les bonnes valeurs avant l'envoi");
		else System.out.println("Mauvaise valeur dans le tableau avant l'envoi");
		/*//cas d'erreur
		byte[] res2 = new byte[6];
		int mes2 = 2147483648;
		res2 = Commandes.writeCt(mes2);
		if(res2[0]==2 && res2[1]==1 && res2[2]==0 && res2[3]==0 && res2[4]==0 && res2[5]==(byte) mes2) System.out.println((byte) mes2);
		else System.out.println((byte) mes2);*/
	}
	
	public static void testWriteSms_All() throws Exception{
		//cas normal
		byte[] res = new byte[5];
		res = Commandes.writeSms_All();
		if(res[0]==10 && res[1]==0 && res[2]==0 && res[3]==0 && res[4]==0) System.out.println("Le tableau d octet a les bonnes valeurs avant l'envoi");
		else System.out.println("Mauvaise valeur dans le tableau avant l'envoi");
		/*//cas d'erreur
		byte[] res2 = new byte[6];
		int mes2 = 2147483648;
		res2 = Commandes.writeCt(mes2);
		if(res2[0]==2 && res2[1]==1 && res2[2]==0 && res2[3]==0 && res2[4]==0 && res2[5]==(byte) mes2) System.out.println((byte) mes2);
		else System.out.println((byte) mes2);*/
	}
	
	public static void testWriteSms_Get_NumDest() throws Exception{
		//cas normal
		byte[] res = new byte[6];
		int id = 2;
		res = Commandes.writeSms_Get_NumDest(id);
		if(res[0]==11 && res[1]==1 && res[2]==0 && res[3]==0 && res[4]==0 && res[5]== (byte) id) System.out.println("Le tableau d octet a les bonnes valeurs avant l'envoi");
		else System.out.println("Mauvaise valeur dans le tableau avant l'envoi");
		/*//cas d'erreur
		byte[] res2 = new byte[6];
		int mes2 = 2147483648;
		res2 = Commandes.writeCt(mes2);
		if(res2[0]==2 && res2[1]==1 && res2[2]==0 && res2[3]==0 && res2[4]==0 && res2[5]==(byte) mes2) System.out.println((byte) mes2);
		else System.out.println((byte) mes2);*/
	}
	
	public static void testWriteCt_Sms_Notify_Ret() throws Exception{
		//cas normal
		byte[] res = new byte[6];
		res = Commandes.writeCt_Sms_Notify_Ret(true);
		if(res[0]==12 && res[1]==1 && res[2]==0 && res[3]==0 && res[4]==0 && res[5]== (byte) 1) System.out.println("Le tableau d octet a les bonnes valeurs avant l'envoi");
		else System.out.println("Mauvaise valeur dans le tableau avant l'envoi");
		res = Commandes.writeCt_Sms_Notify_Ret(false);
		if(res[0]==12 && res[1]==1 && res[2]==0 && res[3]==0 && res[4]==0 && res[5]== (byte) 0) System.out.println("Le tableau d octet a les bonnes valeurs avant l'envoi");
		else System.out.println("Mauvaise valeur dans le tableau avant l'envoi");
		/*//cas d'erreur
		byte[] res2 = new byte[6];
		int mes2 = 2147483648;
		res2 = Commandes.writeCt(mes2);
		if(res2[0]==2 && res2[1]==1 && res2[2]==0 && res2[3]==0 && res2[4]==0 && res2[5]==(byte) mes2) System.out.println((byte) mes2);
		else System.out.println((byte) mes2);*/
	}
	
	public static void testWriteCall() throws Exception{
		//cas normal
		byte[] res = new byte[6];
		int id = 2;
		int num = 3;
		res = Commandes.writeCall(true, id, num);
		if(res[0]==13 && res[1]==1 && res[2]==0 && res[3]==0 && res[4]==0 && res[5]== (byte) id) System.out.println("Le tableau d octet a les bonnes valeurs avant l'envoi");
		else System.out.println("Mauvaise valeur dans le tableau avant l'envoi");
		res = Commandes.writeCall(false, id, num);
		if(res[0]==13 && res[1]==1 && res[2]==0 && res[3]==0 && res[4]==0 && res[5]== (byte) num) System.out.println("Le tableau d octet a les bonnes valeurs avant l'envoi");
		else System.out.println("Mauvaise valeur dans le tableau avant l'envoi");
		/*//cas d'erreur
		byte[] res2 = new byte[6];
		int mes2 = 2147483648;
		res2 = Commandes.writeCt(mes2);
		if(res2[0]==2 && res2[1]==1 && res2[2]==0 && res2[3]==0 && res2[4]==0 && res2[5]==(byte) mes2) System.out.println((byte) mes2);
		else System.out.println((byte) mes2);*/
	}
	
	
	public static void testWriteCall_Vid() throws Exception{
		//cas normal
		byte[] res = new byte[6];
		int id = 2;
		int num = 3;
		res = Commandes.writeCall_Vid(true, id, num);
		if(res[0]==14 && res[1]==1 && res[2]==0 && res[3]==0 && res[4]==0 && res[5]== (byte) id) System.out.println("Le tableau d octet a les bonnes valeurs avant l'envoi");
		else System.out.println("Mauvaise valeur dans le tableau avant l'envoi");
		res = Commandes.writeCall_Vid(false, id, num);
		if(res[0]==14 && res[1]==1 && res[2]==0 && res[3]==0 && res[4]==0 && res[5]== (byte) num) System.out.println("Le tableau d octet a les bonnes valeurs avant l'envoi");
		else System.out.println("Mauvaise valeur dans le tableau avant l'envoi");
		/*//cas d'erreur
		byte[] res2 = new byte[6];
		int mes2 = 2147483648;
		res2 = Commandes.writeCt(mes2);
		if(res2[0]==2 && res2[1]==1 && res2[2]==0 && res2[3]==0 && res2[4]==0 && res2[5]==(byte) mes2) System.out.println((byte) mes2);
		else System.out.println((byte) mes2);*/
	}
}

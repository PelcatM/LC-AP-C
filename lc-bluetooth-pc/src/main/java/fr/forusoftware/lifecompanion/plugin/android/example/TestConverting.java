package fr.forusoftware.lifecompanion.plugin.android.example;
import org.json.simple.JSONObject;

public class TestConverting {

	public static void main( String[] args) throws Exception{
		testJSonToByte();
	}
	
	public static void testJSonToByte() throws Exception{
		JSONObject json = new JSONObject();
		json.put("tel","0695170827");
		json.put("message", "coucou toa");
		byte[] res = Converting.JsonToByte(json);
        String dest = new String(res);
        String ret = json.toString();
        if(dest.equals(ret)) System.out.println("Bonne conversion de bit JSon a byte");
        else System.out.println("Mauvaise conversion de bit JSon a byte");
	}
}

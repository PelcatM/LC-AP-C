package fr.forusoftware.lifecompanion.plugin.android.example;
import org.json.simple.JSONObject;

public class Converting {
	
	public static byte[] JsonToByte(JSONObject obj) throws Exception {
		byte[] ret = new byte[5];
	    ret = obj.toString().getBytes("utf-8");
	    return ret;
	}

}

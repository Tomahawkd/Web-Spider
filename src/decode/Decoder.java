package decode;

import java.util.Base64;

public class Decoder {
	public static String getFromBASE64(String s) throws IllegalArgumentException { 
		if (s.equals("")) return "Nothing input"; 
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] b = decoder.decode(s.getBytes());		
		return new String(b);
	}
}

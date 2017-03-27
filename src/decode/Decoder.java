package decode;

import java.util.Base64;

public class Decoder {
	
	/**
	 * Decode the BASE64 encryption code
	 * 
	 * @param string 
	 * @return Code after decryption
	 * 
	 * @throws IllegalArgumentException
	 * 
	 * @author Ghost
	 */
	
	public static String getFromBASE64(String string) throws IllegalArgumentException { 
		if (string.equals("")) return "Nothing input"; 
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] decryption = decoder.decode(string.getBytes());		
		return new String(decryption);
	}
}

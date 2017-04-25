package decode;

import java.util.Base64;

/**
 * Decoder
 * 
 * @author Tomahawkd
 */

public class Decoder {
	
	/**
	 * Decode the BASE64 encryption code
	 * 
	 * @param string 
	 * @return Code after decryption
	 * 
	 * @throws IllegalArgumentException
	 * 
	 * @author Tomahawkd
	 */
	
	public static String getFromBASE64(String string) throws IllegalArgumentException {
		
		//Notify the user while input nothing
		if (string.equals("")) return "Nothing input";
		
		//BASE64 decode operation
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] decryption = decoder.decode(string.getBytes());		
		return new String(decryption);
	}
}

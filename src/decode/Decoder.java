package decode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

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

		// Notify the user while input nothing
		if (string.equals(""))
			return "Nothing input";

		// BASE64 decode operation
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] decryption = decoder.decode(string.getBytes());
		return new String(decryption);
	}

	/**
	 * Decode the gzip encryption code
	 * 
	 * @param string
	 * @return Code after decryption
	 * 
	 * @throws IllegalArgumentException
	 * 
	 * @author Tomahawkd
	 */

	public static String getFromGzip(String string) {

		// Notify the user while input nothing
		if (string.equals(""))
			return "Nothing input";

		try {
			
			//Decode gzip
			GZIPInputStream input = new GZIPInputStream(new ByteArrayInputStream(string.getBytes()));
			ByteArrayOutputStream output = new ByteArrayOutputStream();

			int count;
			byte data[] = new byte[1024];
			while ((count = input.read(data, 0, 1024)) != -1) {
				output.write(data, 0, count);
				output.flush();
			}

			// Return the result
			return new String(output.toByteArray());

		} catch (IOException e) {

			// Notify the user there is something wrong
			return new String("Decode Failed");
		}
	}

}

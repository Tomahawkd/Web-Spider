package spider;

/**
 * Exception: The exception throws when the host equals non-parameters.
 * 
 * @author Ghost
 */

public class nullHostException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public nullHostException() {
		
		super("Host parameter is not correctly set.");
	}
		
	
}

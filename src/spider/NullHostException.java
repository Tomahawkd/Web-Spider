package spider;

/**
 * Exception: The exception throws when the host equals non-parameters.
 * 
 * @author Tomahawkd
 */

public class NullHostException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NullHostException() {
		super("Host parameter is not correctly set.");
	}

}

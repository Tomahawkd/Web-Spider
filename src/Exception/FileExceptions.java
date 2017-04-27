package Exception;

/**
 * Exception: Universal exceptions about files
 * 
 * @author Tomahawkd
 */

public class FileExceptions extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileExceptions(String errorMessage) {
		super(errorMessage);

	}

}

/**
 * Enumeration of all exceptions
 * 
 * @author Tomahawkd
 */

enum ExceptionType {

	/**
	 * File exist exception (New file and save-as operation)
	 */

	EXIST("File is already exist."),

	/**
	 * File not exist exception (Load and save operation)
	 */

	NULL("File is not exist."),

	/**
	 * File name invalid exception (New file and save-as operation)
	 */

	INVALID("File name is invalid.");

	private String errorMessage;

	private ExceptionType(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * If needed, using <code>Exception.printStackTrace</code> method in catch
	 * block. Using to debug.
	 * 
	 * @return error message
	 */

	String getErrorMessage() {
		return errorMessage;
	}
}

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

enum ExceptionType {
	
	EXIST("File is already exist."), 
	NULL("File is not exist."),
	INVALID("File name is invalid.");
	
	private String errorMessage;
	
	private ExceptionType(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
}



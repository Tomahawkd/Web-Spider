package Exception;

/**
 * Exception: File is not found while tend to open a file.
 * 
 * @author Ghost
 */

public class FileNotFoundException extends FileExceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileNotFoundException() {
		super(ExceptionType.NULL.getErrorMessage());
	}
	
}

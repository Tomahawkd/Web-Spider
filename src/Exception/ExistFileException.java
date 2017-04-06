package Exception;

/**
 * Exception: file is already exist, handle it to notify the user if tend to overwrite it
 * 
 * @author Tomahawkd
 */

public class ExistFileException extends FileExceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExistFileException() {
		super(ExceptionType.EXIST.getErrorMessage());
	}
	
}


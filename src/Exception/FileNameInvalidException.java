package Exception;

/**
 * Exception: file name invalid, handle it to tip the user to rename the file.
 * 
 * @author Tomahawkd
 */

public class FileNameInvalidException extends FileExceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileNameInvalidException() {
		super(ExceptionType.INVALID.getErrorMessage());
	}

}

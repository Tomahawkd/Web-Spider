package Exception;

/**
 * Exception: file name invalid, handle it to tip the user to rename the file.
 * 
 * @author Ghost
 */

public class FileNameInvaildException extends FileExceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileNameInvaildException() {
		super(ExceptionType.INVALID.getErrorMessage());
	}
	
}


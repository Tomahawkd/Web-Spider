package Exception;

public class fileNameInvaildException extends FileExceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public fileNameInvaildException() {
		super(ExceptionType.INVALID.getErrorMessage());
	}
	
}


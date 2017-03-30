package Exception;

public class fileNotFoundException extends FileExceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public fileNotFoundException() {
		super(ExceptionType.NULL.getErrorMessage());
	}
	
}

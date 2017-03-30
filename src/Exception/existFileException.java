package Exception;

public class existFileException extends FileExceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public existFileException() {
		super(ExceptionType.EXIST.getErrorMessage());
	}
	
}


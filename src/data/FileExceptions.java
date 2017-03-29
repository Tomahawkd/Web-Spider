package data;

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

class existFileException extends FileExceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public existFileException() {
		super(ExceptionType.EXIST.getErrorMessage());
	}
	
}

class fileNotFoundException extends FileExceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public fileNotFoundException() {
		super(ExceptionType.NULL.getErrorMessage());
	}
	
}

class fileNameInvaildException extends FileExceptions {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public fileNameInvaildException() {
		super(ExceptionType.INVALID.getErrorMessage());
	}
	
}



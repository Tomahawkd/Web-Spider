package data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.regex.*;

import Exception.ExistFileException;
import Exception.FileNameInvaildException;

/**
 * FileIO: Execute file load & save operation
 * 
 * @author Ghost and Yezipoiny
 */

public class FileIO {
	
	private String targetFilePath;
	private boolean target;
	
	public FileIO() {
		this.targetFilePath = "";
		this.target = false;
	}

	/**
	 * Get the target file path
	 * 
	 * @return file path
	 * 
	 * @author Ghost
	 */
	
	public String getTargetFilePath() {
		return targetFilePath;
	}

	/**
	 * Set the target file path while creating loading or saving file.
	 * 
	 * @param targetFilePath 
	 * 
	 * @see JFileChooser
	 * 
	 * @author Ghost
	 */

	public void setTargetFilePath(String targetFilePath) {
		this.targetFilePath = targetFilePath;
		
	}
	
	/**
	 * Get a boolean indicate the date file of the project property.
	 * 
	 * @return return true if and only if the project has a file to save
	 * 
	 * @author Ghost
	 */
	
	public boolean hasTargetFile() {
		return target;
	}
	
	/**
	 * Create file operation
	 * 
	 * @param force is tend to force to cover the exist file
	 * 
	 * @throws existFileException throws if not force to cover and there exist the file.
	 * @throws fileNameInvaildException the file name is not valid cause of the system restriction.
	 * @throws IOException other exceptions
	 * 
	 * @author Ghost and Yezipoiny
	 */
	
	public void createFile(boolean force) throws ExistFileException, FileNameInvaildException, IOException {
		if(!targetFilePath.contains(".sdf")) {
			targetFilePath += ".sdf";
		}
		
		String targetFileName = targetFilePath.substring(targetFilePath.lastIndexOf(File.separator) + 1);
		
		Matcher invalid = Pattern.compile("[\\\\/:\"*?<>|]").matcher(targetFileName);
		Matcher rejectHide = Pattern.compile("^[.]").matcher(targetFileName);
		
		if (invalid.find() | rejectHide.find()) {
			throw new FileNameInvaildException();
		}
			
		File newFile = new File(targetFilePath);
		
		if (!force && newFile.exists()) {
			throw new ExistFileException();
		}
			
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(targetFilePath));
		
		target = true;
		output.close();
	}
}

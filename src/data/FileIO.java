package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.regex.*;

import Exception.ExistFileException;
import Exception.FileNameInvaildException;
import Exception.FileNotFoundException;

/**
 * FileIO: Execute file load & save operation
 * 
 * @author Tomahawkd and Yezipoiny
 */

public class FileIO {
	
	private String targetFilePath;
	private boolean target;
	private DataSet data;
	
	public FileIO() {
		this.targetFilePath = "";
		this.target = false;
		this.data = new DataSet();
	}
	
	public DataSet getDataSet() {
		return data;
	}

	/**
	 * Get the target file path
	 * 
	 * @return file path
	 * 
	 * @author Tomahawkd
	 */
	
	public String getTargetFilePath() {
		return targetFilePath;
	}

	/**
	 * Set the target file path while creating loading or saving file.
	 * 
	 * @param targetFilePath 
	 * 
	 * @see {@link JFileChooser}
	 * 
	 * @author Tomahawkd
	 */

	public void setTargetFilePath(String targetFilePath) {
		this.targetFilePath = targetFilePath;
		
	}
	
	/**
	 * Get a boolean indicate the date file of the project property.
	 * 
	 * @return return true if and only if the project has a file to save
	 * 
	 * @author Tomahawkd
	 */
	
	public boolean hasTargetFile() {
		return target;
	}
	
	/**
	 * Create file operation
	 * 
	 * @param force is tend to force to cover the exist file.
	 * 
	 * @throws ExistFileException throws if not force to cover and there exist the file.
	 * @throws FileNameInvaildException the file name is not valid cause of the system restriction.
	 * @throws IOException other exceptions
	 * 
	 * @author Tomahawkd and Yezipoiny
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
	
	/**
	 * Load user's file
	 * 
	 * @throws FileNotFoundException throws if not force to cover and there exist the file.
	 * @throws ClassCastException the data does not match application's data class.
	 * @throws IOException other exceptions.
	 * @throws ClassNotFoundException the data does not match application's data class.
	 * 
	 * @author Tomahawkd
	 */
	
	public void loadFile() throws FileNotFoundException, ClassCastException, IOException, ClassNotFoundException {
		
		File newFile = new File(targetFilePath);
		if (!newFile.exists()) {
			throw new FileNotFoundException();
		}
		
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(targetFilePath));
		
		this.data = (DataSet) input.readObject();
		
		input.close();
		target = true;
	}
	
	/**
	 * Save data to exist file.
	 * 
	 * @throws FileNotFoundException throws if not force to cover and there exist the file.
	 * @throws IOException other exceptions.
	 * 
	 * @author Tomahawkd
	 */
	
	public void saveFile() throws FileNotFoundException, IOException {
			
		if (!target) {
			throw new FileNotFoundException();
		}
			
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(targetFilePath));
		output.writeObject(data);
		output.close();
		
	}
	
	/**
	 * Save data to a new file.
	 * 
	 * @param force is tend to force to cover the exist file
	 * @throws ExistFileException throws if not force to cover and there exist the file.
	 * @throws FileNameInvaildException the file name is not valid cause of the system restriction.
	 * @throws IOException other exceptions.
	 * 
	 * @author Tomahawkd
	 */
	
	public void saveAsFile(boolean force) throws ExistFileException, FileNameInvaildException, IOException {
		
		if(!targetFilePath.contains(".sdf")) {
			targetFilePath += ".sdf";
		}
		
		String targetFileName = targetFilePath.substring(targetFilePath.lastIndexOf(File.separator) + 1);
		
		Matcher invalid = Pattern.compile("[\\\\/:\"*?<>|]").matcher(targetFileName);
		Matcher rejectHide = Pattern.compile("^[.]").matcher(targetFileName);
		
		if (invalid.find() | rejectHide.find()) {
			throw new FileNameInvaildException();
		}
			
		File saveFile = new File(targetFilePath);
		
		if (!force && saveFile.exists()) {
			throw new ExistFileException();
		}
			
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(targetFilePath));
		output.writeObject(data);
		output.close();
		
	}
	
}

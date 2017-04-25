package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.regex.*;

import Exception.ExistFileException;
import Exception.FileNameInvalidException;
import Exception.FileNotFoundException;

/**
 * FileIO: Execute file load & save operation
 * 
 * @author Tomahawkd and Yezipoiny
 */

public class FileIO {
	
	/**
	 * File path in system
	 */
	
	private String targetFilePath;
	
	/**
	 * Has a target file to save.
	 */
	
	private boolean target;
	
	/**
	 * DataSet class to load and save all of the data
	 */
	
	private DataSet data;
	
	
	
	
	
	
	
	
	
	public FileIO() {
		this.targetFilePath = "";
		this.target = false;
		this.data = new DataSet();
	}
	
	/**
	 * Get all of the data.
	 * 
	 * @return data
	 * 
	 * @author Tomahawkd
	 */
	
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
	
	public void createFile(boolean force) throws ExistFileException, FileNameInvalidException, IOException {
		
		//Add identifier if the user does not enter it.
		if(!targetFilePath.endsWith(".sdf")) {
			targetFilePath += ".sdf";
		}
		
		//Get the file name
		String targetFileName = targetFilePath.substring(targetFilePath.lastIndexOf(File.separator) + 1);
		
	//Check the validation of the file name
		
		//Contains illegal pattern in WINDOWS system
		Matcher invalid = Pattern.compile("[\\\\/:\"*?<>|]").matcher(targetFileName);
		
		//Start with dot which indicate a hidden file in UNIX/LINUX system.
		Matcher rejectHide = Pattern.compile("^[.]").matcher(targetFileName);
		
		//throws invalid file name exception
		if (invalid.find() | rejectHide.find()) {
			throw new FileNameInvalidException();
		}
		
		//Get the file in file system
		File newFile = new File(targetFilePath);
		
		//throws exist file exception
		if (!force && newFile.exists()) {
			throw new ExistFileException();
		}
		
		//Create a new file
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(targetFilePath));
		
		//Close stream
		output.close();
		
		//Already has the target file to save
		target = true;
		
		//New dataSet
		this.data = new DataSet();
	}
	
	/**
	 * Load user's file
	 * 
	 * @throws FileNotFoundException throws if not force to cover and there exist the file.
	 * @throws ClassCastException the data does not match application's data class.
	 * @throws IOException other exceptions.
	 * @throws ClassNotFoundException the data does not match application's data class.
	 * 
	 * @author Tomahawkd and Yezipoiny
	 */
	
	public void loadFile() throws FileNotFoundException, ClassCastException, IOException, ClassNotFoundException {
		
		//Get the file in file system
		File newFile = new File(targetFilePath);
		
		//Check file existence
		if (!newFile.exists()) {
			throw new FileNotFoundException();
		}
		
		//Load file
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(targetFilePath));
		
		//Cast the target object to DataSet class
		this.data = (DataSet) input.readObject();
		
		//Close stream
		input.close();
		
		//Already has the target file to save
		target = true;
	}
	
	/**
	 * Save data to exist file.
	 * 
	 * @throws FileNotFoundException throws if not force to cover and there exist the file.
	 * @throws IOException other exceptions.
	 * 
	 * @author Tomahawkd and Yezipoiny
	 */
	
	public void saveFile() throws FileNotFoundException, IOException {
		
		//Check the target file existence
		if (!target) {
			throw new FileNotFoundException();
		}
		
		//Save data to exist file
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(targetFilePath));
		
		//Write the data to the file
		output.writeObject(data);
		
		//Close stream
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
	 * @author Tomahawkd and Yezipoiny
	 */
	
	public void saveAsFile(boolean force) throws ExistFileException, FileNameInvalidException, IOException {
		
		//Add identifier if the user does not enter it.
		if(!targetFilePath.endsWith(".sdf")) {
			targetFilePath += ".sdf";
		}
		
		//Get the file name
		String targetFileName = targetFilePath.substring(targetFilePath.lastIndexOf(File.separator) + 1);
		
	//Check the validation of the file name
		
		//Contains illegal pattern in WINDOWS system
		Matcher invalid = Pattern.compile("[\\\\/:\"*?<>|]").matcher(targetFileName);
		
		//Start with dot which indicate a hidden file in UNIX/LINUX system.
		Matcher rejectHide = Pattern.compile("^[.]").matcher(targetFileName);
		
		//throws invalid file name exception
		if (invalid.find() | rejectHide.find()) {
			throw new FileNameInvalidException();
		}
		
		//Get the file in file system
		File saveFile = new File(targetFilePath);
		
		//throws exist file exception
		if (!force && saveFile.exists()) {
			throw new ExistFileException();
		}
			
		//Create a new file
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(targetFilePath));
		
		//Write the data to the file
		output.writeObject(data);
		
		//Close stream
		output.close();
		
	}
	
}

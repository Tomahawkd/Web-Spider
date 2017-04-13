package Interface;

import data.FileIO;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Exception.FileNotFoundException;

/**
 * Interface: Load file FileChooser panel.
 * 
 * @author Tomahawkd
 *
 */

class LoadFile extends JFileChooser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * Load file window
	 * 
	 * @param file file operation handler
	 * @param window Main Window class to refresh data after loading
	 * 
	 * @see {@link JFileChooser}
	 * 
	 * @author Tomahawkd
	 */
	
	LoadFile(FileIO file, MainWindow window) {
		
		super();
				
		/*
		 * JFileChooser configuration
		 */
		
		//Filter
		FileFilter filter = new FileNameExtensionFilter("Spider Data File", "sdf");
		setFileFilter(filter);
		
		//Other configuration
		setFileSelectionMode(FILES_AND_DIRECTORIES);
		setDialogTitle("Open File");
		setEnabled(true);
		
		//Button clicked listener
		int state = showOpenDialog(null);
		
		execute(state, file, window);
	}
	
	
	/**
	 * Execute the loading operation.
	 * 
	 * @param state Indicate which button clicked
	 * @param file file operation handler
	 * @param window Main Window class to refresh data after loading
	 * 
	 * @see {@link JFileChooser}
	 * 
	 * @author Tomahawkd
	 */
	
	private void execute(int state, FileIO file, MainWindow window) {
		
		//Button clicked listener
		switch (state) {
		
		//Confirm button clicked
		case JFileChooser.APPROVE_OPTION:
			
			//Get the path of the file
			File filePath=this.getSelectedFile();
			file.setTargetFilePath(filePath.getAbsolutePath());
			
			try {
				
				//execute load file operation
				file.loadFile();
				
				
				//Display the file loading process
				new Thread(new Runnable() {
					public void run() {
						FileProcess process = new FileProcess(OperationType.LOAD);
						process.setVisible(true);
					}
				}).start();
				
				//Refresh data in GUI
				window.updateUI();
				
			} catch (FileNotFoundException e) {
				//File is not exist, notify the user to create a new file
				
				//Display new file panel
				NewFile newFile = new NewFile(file);
				newFile.setVisible(true);
				
			} catch (ClassCastException | ClassNotFoundException e) {
				//File data does not match the application's data
				
				//Notify the user data is error
				FileDataError error = new FileDataError();
				error.setVisible(true);
				file.setTargetFilePath("");

			} catch (IOException e) {
				//Exception with file load failure
				
				FileFailure error = new FileFailure(ErrorType.LOAD);
				error.setVisible(true);
			}
			
			break;

			
		//Cancel button clicked or error happened
		default:
			break;
		}
	}

}



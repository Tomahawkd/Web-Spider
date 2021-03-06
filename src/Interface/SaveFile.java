package Interface;

import data.FileIO;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Exception.ExistFileException;
import Exception.FileNameInvalidException;

/**
 * Interface: Save to a new file FileChooser panel.
 * 
 * @author Tomahawkd
 *
 */

class SaveFile extends JFileChooser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Save-as file window
	 * 
	 * @param file
	 *            file operation handler
	 * 
	 * @see {@link JFileChooser}
	 * 
	 * @author Tomahawkd
	 */

	SaveFile(FileIO file) {

		super();

		/*
		 * JFileChooser configuration
		 */

		// Filter
		FileFilter filter = new FileNameExtensionFilter("Spider Data File", "sdf");
		setFileFilter(filter);

		// Other configuration
		setFileSelectionMode(FILES_AND_DIRECTORIES);
		setDialogTitle("Save File");
		setEnabled(true);

		// Button clicked listener
		int state = showSaveDialog(null);

		execute(state, file);
	}

	/**
	 * Execute the saving operation.
	 * 
	 * @param state
	 *            Indicate which button clicked
	 * @param file
	 *            file operation handler
	 * 
	 * @see {@link JFileChooser}
	 * 
	 * @author Tomahawkd
	 */

	private void execute(int state, FileIO file) {

		// Button clicked listener
		switch (state) {

		// Confirm button clicked
		case JFileChooser.APPROVE_OPTION:

			// Get the path of the file
			final File filePath = this.getSelectedFile();
			file.setTargetFilePath(filePath.getAbsolutePath());

			try {

				// execute load file operation
				file.saveAsFile(false);

				// Display the file creation process
				new Thread(new Runnable() {
					public void run() {
						FileProcess process = new FileProcess(OperationType.SAVE);
						process.setVisible(true);
					}
				}, "SavingFileThread").start();

			} catch (FileNameInvalidException e) {
				// Notify the user file name is not valid

				// Display name invalid tip
				FileNameInvalid invalid = new FileNameInvalid();
				invalid.setVisible(true);

			} catch (ExistFileException e) {
				// Notify the user file is already exist

				// Display exist file tip
				ExistFileTip tip = new ExistFileTip(file, Operation.SAVE);
				tip.setVisible(true);

			} catch (IOException e) {
				// Exception with file creation failure

				FileFailure error = new FileFailure(ErrorType.CREAT);
				error.setVisible(true);
			}

			break;

		// Cancel button clicked or error happened
		default:
			break;
		}
	}

}

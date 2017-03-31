package Interface;

import data.FileIO;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import Exception.ExistFileException;
import Exception.FileNameInvaildException;


public class NewFile extends JFileChooser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int state;
	private FileIO file;
	
	public NewFile(FileIO file) {
		
		super();
		
		this.file = file;
		
		FileFilter filter = new FileNameExtensionFilter("Spider Data File", "sdf");
		setFileFilter(filter);
		setFileSelectionMode(DIRECTORIES_ONLY);
		setDialogTitle("New File");
		setEnabled(true);
		
		state = showSaveDialog(null);
		
		execute();
	}
	
	private void execute() {
		switch (state) {
		case JFileChooser.APPROVE_OPTION:
			
			File filePath=this.getSelectedFile();
			file.setTargetFilePath(filePath.getAbsolutePath());
			
			try {
				
				file.createFile(false);
				new Thread(new Runnable() {
					public void run() {
						FileProcess process = new FileProcess(OperationType.NEW);
						process.setVisible(true);
					}
				});
			} catch (ExistFileException e) {
						
				ExistFileTip tip = new ExistFileTip(file, Operation.NEW);
				tip.setVisible(true);
						
			} catch (FileNameInvaildException e) {
				
				FileNameInvalid invalid = new FileNameInvalid();
				invalid.setVisible(true);
						
			} catch (IOException e) {
				Error error = new Error();
				error.setVisible(true);
			}

			break;

		default:
			break;
		}
	}

}

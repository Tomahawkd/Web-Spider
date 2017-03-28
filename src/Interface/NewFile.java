package Interface;

import data.FileIO;

import java.io.File;
import javax.swing.JFileChooser;


public class NewFile extends JFileChooser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FileIO file = new FileIO();
	private int state;
	
	public NewFile() {
		
		super();
		
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
			file.setNewFilePath(filePath.getAbsolutePath());
			
			
			break;

		default:
			break;
		}
	}

}

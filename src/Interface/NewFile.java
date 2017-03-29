package Interface;

import data.FileIO;

import java.io.File;
import javax.swing.JFileChooser;


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
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					file.setTargetFilePath(filePath.getAbsolutePath());
					//TODO
					
				}
			});
			
			NewFileProcess process = new NewFileProcess();
			process.setVisible(true);
			break;

		default:
			break;
		}
	}

}

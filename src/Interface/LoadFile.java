package Interface;

import data.FileIO;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class LoadFile extends JFileChooser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int state;
	private FileIO file;
	
	public LoadFile(FileIO file) {
		
		super();
		
		this.file = file;
		
		FileFilter filter = new FileNameExtensionFilter("Spider Data File", "sdf");
		setFileFilter(filter);
		setFileSelectionMode(FILES_AND_DIRECTORIES);
		setDialogTitle("Open File");
		setEnabled(true);
		
		state = showOpenDialog(null);
		
		execute();
	}
	
	private void execute() {
		switch (state) {
		case JFileChooser.APPROVE_OPTION:
			
			File filePath=this.getSelectedFile();
			
			new Thread(new Runnable() {
				public void run() {
					file.setTargetFilePath(filePath.getAbsolutePath());
					//TODO
					
				}
			}
			);
			
			FileProcess process = new FileProcess(OperationType.LOAD);
			process.setVisible(true);
			break;

		default:
			break;
		}
	}

}



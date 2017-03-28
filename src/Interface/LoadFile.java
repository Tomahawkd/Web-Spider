package Interface;

import data.FileIO;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;


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
		
		FileFilter filter = new FileFilter() {
			
			@Override
			public String getDescription() {
				return "Spider Data File";
			}
			
			@Override
			public boolean accept(File fileName) {
				
				boolean flag = false;
				
				if(fileName.getName().contains(".sdf")){
					flag = true;
				}
				return flag;
			}
		};
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
			file.setNewFilePath(filePath.getAbsolutePath());
			//TODO
			
			LoadFileProcess process = new LoadFileProcess();
			process.setVisible(true);
			break;

		default:
			break;
		}
	}

}



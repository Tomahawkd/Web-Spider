package Interface;

import data.FileIO;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;


public class SaveFile extends JFileChooser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int state;
	private FileIO file;
	
	public SaveFile(FileIO file) {
		
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
		setDialogTitle("Save File");
		setEnabled(true);
		
		state = showSaveDialog(null);
		
		execute();
	}
	
	private void execute() {
		switch (state) {
		case JFileChooser.APPROVE_OPTION:
			
			final File filePath=this.getSelectedFile();
			
			new Thread(new Runnable() {
				public void run() {
					file.setTargetFilePath(filePath.getAbsolutePath());
					//TODO
					
				}
			});
			
			FileProcess process = new FileProcess(OperationType.SAVE);
			process.setVisible(true);
			break;

		default:
			break;
		}
	}

}



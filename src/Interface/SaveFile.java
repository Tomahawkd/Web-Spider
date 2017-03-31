package Interface;

import data.FileIO;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import Exception.ExistFileException;
import Exception.FileNameInvaildException;


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
			file.setTargetFilePath(filePath.getAbsolutePath());
			
			try {
				file.saveAsFile(false);
				new Thread(new Runnable() {
					public void run() {
						FileProcess process = new FileProcess(OperationType.SAVE);
						process.setVisible(true);
					}
				});
			} catch (ExistFileException e) {
						
				ExistFileTip tip = new ExistFileTip(file, Operation.SAVE);
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



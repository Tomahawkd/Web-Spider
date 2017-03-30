package Interface;

import data.FileIO;

import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import Exception.existFileException;
import Exception.fileNameInvaildException;


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
				
				file.createFile();
				NewFileProcess process = new NewFileProcess();
				process.setVisible(true);
			} catch (existFileException e) {
						
				ExistFileTip tip = new ExistFileTip(file);
				tip.setVisible(true);
						
			} catch (fileNameInvaildException e) {
				
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

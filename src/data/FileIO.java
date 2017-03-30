package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import Exception.existFileException;
import Exception.fileNameInvaildException;

public class FileIO {
	
	private String targetFilePath;
	private boolean target;
	
	public FileIO() {
		this.targetFilePath = "";
		this.target = false;
	}


	public String getTargetFilePath() {
		return targetFilePath;
	}


	public void setTargetFilePath(String targetFilePath) {
		this.targetFilePath = targetFilePath;
		
	}
	
	public boolean hasTargetFile() {
		return target;
	}
	
	public void createFile(boolean force) throws existFileException, fileNameInvaildException, FileNotFoundException, IOException {
		if(!targetFilePath.contains(".spf")) {
			targetFilePath += ".spf";
		}
		
		String targetFileName = targetFilePath.split(File.separator)[targetFilePath.split(File.separator).length - 1];
		
		if (targetFileName.contains("\\") || targetFileName.contains("/") || targetFileName.contains(":") || 
			targetFileName.contains("*") || targetFileName.contains("?") || targetFileName.contains("\"") || 
			targetFileName.contains("<") || targetFileName.contains(">") || targetFileName.contains("|")) {
			throw new fileNameInvaildException();
		}
			
		File newFile = new File(targetFilePath);
		
		if (!force && newFile.exists()) {
			throw new existFileException();
		}
			
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(targetFilePath));
		
		target = true;
		output.close();
	}
}

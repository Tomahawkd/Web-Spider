package data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class FileIO {
	
	private String targetFilePath;
	private boolean target;
	private boolean exist;
	
	public FileIO() {
		this.targetFilePath = "";
		this.target = false;
		this.exist = false;
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
	
	
	public boolean isExist() {
		return exist;
	}
	
	private void setTarget(boolean target) {
		this.target = target;
	}
	
	public void createFile() throws existFileException, fileNameInvaildException, FileNotFoundException, IOException {
		String targetFileName = targetFilePath.split("\\")[targetFilePath.split("\\").length - 1];
			
		if (targetFileName.contains("\\") || targetFileName.contains("/") || targetFileName.contains(":") || 
			targetFileName.contains("*") || targetFileName.contains("?") || targetFileName.contains("\"") || 
			targetFileName.contains("<") || targetFileName.contains(">") || targetFileName.contains("|")) {
			throw new fileNameInvaildException();
		}
			
		File newFile = new File(targetFilePath);
		
		if (newFile.exists()) {
			throw new existFileException();
		}
			
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(targetFilePath));
	}
}

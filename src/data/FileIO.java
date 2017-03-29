package data;

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
	
	public void setTarget(boolean target) {
		this.target = target;
	}
	
	public boolean isExist() {
		return exist;
	}
	
}

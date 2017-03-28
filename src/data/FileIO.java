package data;

public class FileIO {
	
	private String existFilePath;
	private String newFilePath;
	private boolean saveFileFlag;
	private boolean newFileFlag;
	private boolean target;
	
	public FileIO() {
		this.existFilePath = "";
		this.newFilePath = "";
		this.saveFileFlag = false;
		this.newFileFlag = false;
	}


	public String getExistFilePath() {
		return existFilePath;
	}


	public void setExistFilePath(String existFilePath) {
		this.existFilePath = existFilePath;
	}


	public String getNewFilePath() {
		return newFilePath;
	}


	public void setNewFilePath(String newFilePath) {
		this.newFilePath = newFilePath;
		//TODO
		
	}


	public boolean isSaved() {
		return saveFileFlag;
	}


	public void setSaved(boolean saveFileFlag) {
		this.saveFileFlag = saveFileFlag;
	}
	
	public boolean hasTargetFile() {
		return target;
	}
	
	
}

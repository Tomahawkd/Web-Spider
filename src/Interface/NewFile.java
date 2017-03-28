package Interface;

import javax.swing.JFileChooser;

public class NewFile extends JFileChooser {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public NewFile() {
		
		super();
		
		setFileSelectionMode(DIRECTORIES_ONLY);
		showSaveDialog(null);
	}

}

package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Exception.FileNotFoundException;
import data.FileIO;

/**
 * Interface: Menu bar
 * 
 * @author Tomahawkd
 */

class MenuBar extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	MenuBar(FileIO file, MainWindow window) {
		
		/*
		 * Menu: Project
		 */
		
		JMenu mnProject = new JMenu("Project");
		add(mnProject);
		
		JMenuItem mntmNew = new JMenuItem("New...");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewFile newFile = new NewFile(file);
				newFile.setVisible(true);
			}
		});
		mnProject.add(mntmNew);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        LoadFile loadFile = new LoadFile(file, window);
		        loadFile.setVisible(true);
			}
		});
		mnProject.add(mntmLoad);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(file.hasTargetFile()) {
					try {
						file.saveFile();
						FileProcess frame = new FileProcess(OperationType.SAVE);
						frame.setVisible(true);	
					} catch (FileNotFoundException e1) {
						SaveFile saveFile = new SaveFile(file);
						saveFile.setVisible(true);
					} catch (IOException e1) {
						Error error = new Error();
						error.setVisible(true);
					}

				} else {
					SaveFile saveFile = new SaveFile(file);
					saveFile.setVisible(true);
				}
			}
		});
		mnProject.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save as...");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveFile saveFile = new SaveFile(file);
				saveFile.setVisible(true);
			}
		});
		mnProject.add(mntmSaveAs);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Exit frame = new Exit();
				frame.setVisible(true);
			}
		});
		mnProject.add(mntmExit);
		
		
		/*
		 *  Menu: About
		 */
		
		JMenu mnAbout = new JMenu("About");
		add(mnAbout);
		
		JMenuItem mntmAboutUs = new JMenuItem("About Us");
		mntmAboutUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aboutUs frame = new aboutUs();
				frame.setVisible(true);	
			}
		});
		mnAbout.add(mntmAboutUs);
	}

	
}

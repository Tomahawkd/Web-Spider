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

	/**
	 * Menu bar components
	 * 
	 * @param file
	 *            file operation handler
	 * @param window
	 *            Main Window class to refresh data after loading
	 */

	MenuBar(FileIO file, MainWindow window) {

		/*
		 * Menu: Project
		 */

		JMenu mnProject = new JMenu("Project");
		add(mnProject);

		/*
		 * Items
		 */

		JMenuItem mntmNew = new JMenuItem("New...");

		// New file operation
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Display new file panel
				NewFile newFile = new NewFile(file);
				newFile.setVisible(true);
			}
		});
		mnProject.add(mntmNew);

		JMenuItem mntmLoad = new JMenuItem("Load");

		// Load file operation
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Display load file panel
				LoadFile loadFile = new LoadFile(file, window);
				loadFile.setVisible(true);
			}
		});
		mnProject.add(mntmLoad);

		JMenuItem mntmSave = new JMenuItem("Save");

		// Save file operation
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					// Execute save file operation
					file.saveFile();

					// Display the file creation process
					FileProcess frame = new FileProcess(OperationType.SAVE);
					frame.setVisible(true);

				} catch (FileNotFoundException e1) {
					// File not exist, notify the user to operate save-as
					// operation

					// Display save-as file panel
					SaveFile saveFile = new SaveFile(file);
					saveFile.setVisible(true);

				} catch (IOException e1) {
					// Exception with file save failure

					FileFailure error = new FileFailure(ErrorType.CREAT);
					error.setVisible(true);
				}

			}
		});
		mnProject.add(mntmSave);

		JMenuItem mntmSaveAs = new JMenuItem("Save as...");

		// Save-as file operation
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Display save-as file panel
				SaveFile saveFile = new SaveFile(file);
				saveFile.setVisible(true);
			}
		});
		mnProject.add(mntmSaveAs);

		JMenuItem mntmExit = new JMenuItem("Exit");

		// Exit the application
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Display exit panel
				Exit frame = new Exit();
				frame.setVisible(true);
			}
		});
		mnProject.add(mntmExit);

		/*
		 * Menu: About
		 */

		JMenu mnAbout = new JMenu("About");
		add(mnAbout);

		/*
		 * Items
		 */

		JMenuItem mntmAboutUs = new JMenuItem("About Us");
		mntmAboutUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Display about-us panel
				aboutUs frame = new aboutUs();
				frame.setVisible(true);
			}
		});
		mnAbout.add(mntmAboutUs);
	}

}

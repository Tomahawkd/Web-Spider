package Interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Exception.ExistFileException;
import Exception.FileNameInvalidException;
import data.FileIO;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

/**
 * Interface: Notify the user if tend to overwrite the file. (In create-file and save-as operation)
 * 
 * @author Tomahawkd
 */

public class ExistFileTip extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/**
	 *  Notify the user if they want to overwrite when a file exist already.
	 * 
	 * @param file file operation handler
	 * @param type file operation type
	 * 
	 * @author Tomahawkd
	 */
	
	public ExistFileTip(FileIO file, Operation type) {
		
		
		/*
		 * Self configuration
		 */
		
		setBounds(100, 100, 450, 155);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		/*
		 * Label
		 */
		
		JLabel lblTheFileIs = new JLabel("The file is exist, do you want to cover it?");
		lblTheFileIs.setBounds(97, 33, 255, 16);
		contentPane.add(lblTheFileIs);
		
		
		/*
		 * Buttons
		 */
		
		JButton btnOk = new JButton("OK");
		
		//Confirm to overwrite to the selected file
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//Judge operation type
				switch(type) {
				
				//Overwrite without data (New file)
				case NEW:
					try {
						
						//File create operation
						file.createFile(true);
						
						//Display the file creation process
						FileProcess process = new FileProcess(type.getOperationType());
						process.setVisible(true);
						
					} catch (ExistFileException | FileNameInvalidException e1) {
						//Logically the exception will never happens, ignore them
						
					} catch (IOException e1) {
						//Exception with file creation failure
						
						FileCreationFailure error = new FileCreationFailure();
						error.setVisible(true);
						dispose();
					}
				break;
				
				//Overwrite with data (Save file)
				case SAVE:
					try {
						
						//File save-as operation
						file.saveAsFile(true);
						
						//Display the file creation process
						FileProcess process = new FileProcess(type.getOperationType());
						process.setVisible(true);
						
					} catch (ExistFileException | FileNameInvalidException e1) {
						//Logically the exception will never happens, ignore them
						
					} catch (IOException e1) {
						//Exception with file creation failure
						
						FileCreationFailure error = new FileCreationFailure();
						error.setVisible(true);
						dispose();
					}
				break;
				}
				
				dispose();
			}
		});
		btnOk.setBounds(68, 86, 117, 29);
		contentPane.add(btnOk);
		
		
		JButton btnCancel = new JButton("Cancel");
		
		//Cancel overwrite operation
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(265, 86, 117, 29);
		contentPane.add(btnCancel);
	}
}





/**
 * Enumeration of data written operation type
 * 
 * @author Tomahawkd
 */

enum Operation {
	
	/**
	 * File creation exception
	 * 
	 * @see OperationType
	 */
	
	NEW(OperationType.NEW), 
	
	/**
	 * File creation exception
	 * 
	 * @see OperationType
	 */
	
	SAVE(OperationType.SAVE);
	
	
	
	
	private OperationType type;
	
	private Operation(OperationType type) {
		this.type = type;
	}
	
	/**
	 * Get the type for process to judge type.
	 * 
	 * @return
	 */
	
	OperationType getOperationType() {
		return type;
	}
}

package Interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Exception.ExistFileException;
import Exception.FileNameInvaildException;
import data.FileIO;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class ExistFileTip extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/*
	 * Create the frame.
	 */
	public ExistFileTip(FileIO file) {
		setBounds(100, 100, 450, 155);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTheFileIs = new JLabel("The file is exist, do you want to cover it?");
		lblTheFileIs.setBounds(97, 33, 255, 16);
		contentPane.add(lblTheFileIs);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
				
					try {
						file.createFile(true);
					} catch (ExistFileException | FileNameInvaildException e1) {
						
					} catch (IOException e1) {
						Error error = new Error();
						error.setVisible(true);
						dispose();
					}
				
				FileProcess process = new FileProcess(OperationType.NEW);
				process.setVisible(true);
				dispose();
			}
		});
		btnOk.setBounds(68, 86, 117, 29);
		contentPane.add(btnOk);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(265, 86, 117, 29);
		contentPane.add(btnCancel);
	}
}

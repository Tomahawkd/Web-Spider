package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import data.FileIO;

/**
 * Interface: Option panel
 * 
 * @author Tomahawkd
 */

class OptionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * file operation handler
	 * 
	 * @see {@link FileIO}
	 */
	
	private FileIO file;
	
	/**
	 * Request header UI field.
	 * 
	 * @see {@link JList}
	 */
	
	private JList<String> list;
	
	
	/**
	 * Contains option component.
	 * 
	 * @param file file operation handler
	 * 
	 * @author Tomahawkd
	 */
	
	OptionPanel(FileIO file) {
		
		//Initialize FileIO class
		this.file = file;
		
		
		/*
		 * Self configuration
		 */
		
		setLayout(null);
		
		
		/*
		 * Labels
		 */
		
		JLabel lblSetRequest = new JLabel("Set Request Head");
		lblSetRequest.setBounds(6, 6, 123, 16);
		add(lblSetRequest);
		
		
		/*
		 * Scroll pane
		 */
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(135, 40, 513, 87);
		add(scrollPane);
		
		
		/*
		 * List
		 */
		
		list = new JList<String>();
		scrollPane.setViewportView(list);
		list.setModel(file.getDataSet().getSpiderOption().getRequestHeader());
		
		
		/*
		 * Buttons
		 */
		

		
		
		//Panel used to update data
		OptionPanel panel = this;
		
		
		JButton btnNew = new JButton("New");
		
		//Request header creator
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OptionNewHeader frame = new OptionNewHeader(file.getDataSet().getSpiderOption(), panel);
				frame.setVisible(true);
			}
		});
		btnNew.setBounds(6, 40, 117, 29);
		add(btnNew);
		
		
		JButton btnEdit = new JButton("Edit");
		
		//Request header editor
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
					OptionEditHeader frame = new OptionEditHeader(list.getSelectedIndex(), file.getDataSet().getSpiderOption(), panel);
					frame.setVisible(true);
					
				} catch (ArrayIndexOutOfBoundsException e1) {
					//Ignore exception
				}
			}
		});
		btnEdit.setBounds(6, 70, 117, 29);
		add(btnEdit);
		
		
		JButton btnDelete = new JButton("Delete");
		
		//Delete request header
		btnDelete.setBounds(6, 98, 117, 29);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					file.getDataSet().getSpiderOption().removeHeaderElement(list.getSelectedIndex());
					
				} catch (IndexOutOfBoundsException e1) {
					//Ignore exception
				}
			}
		});
		add(btnDelete);
		
	}
	
	
	/**
	 * Update data in the panel.
	 */
	
	void updateData() {
		this.list.setModel(file.getDataSet().getSpiderOption().getRequestHeader());
	}
}

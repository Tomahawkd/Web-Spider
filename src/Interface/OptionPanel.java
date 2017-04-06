package Interface;

import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import data.FileIO;

class OptionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FileIO file;
	private JList<String> list;
	private JTextField textFieldPort;
	
	OptionPanel(FileIO file) {
		this.file = file;
		
		setLayout(null);
		
		JLabel lblSetRequest = new JLabel("Set Request Head");
		lblSetRequest.setBounds(6, 6, 123, 16);
		add(lblSetRequest);
		
		
		JLabel lblProxy = new JLabel("Proxy");
		lblProxy.setBounds(6, 159, 61, 16);
		add(lblProxy);
		
		JLabel lblHost = new JLabel("Host:");
		lblHost.setBounds(16, 187, 39, 16);
		add(lblHost);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(239, 187, 31, 16);
		add(lblPort);
		
		textFieldPort = new JTextField();
		textFieldPort.setBounds(282, 182, 130, 26);
		add(textFieldPort);
		textFieldPort.setText("" + file.getDataSet().getIntercepterOption().getPort());
		
		JLabel lblPortInvalid = new JLabel("Port invalid");
		lblPortInvalid.setBounds(424, 187, 71, 16);
		add(lblPortInvalid);
		lblPortInvalid.setVisible(false);
		
		Label labelTip = new Label("Only Localhost Support");
		labelTip.setBounds(61, 187, 172, 16);
		add(labelTip);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(135, 40, 513, 87);
		add(scrollPane);
		
		list = new JList<String>();
		scrollPane.setViewportView(list);
		list.setModel(file.getDataSet().getSpiderOption().getRequestHeader());
		
		OptionPanel panel = this;
		
		JButton btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OptionNewHeader frame = new OptionNewHeader(file.getDataSet().getSpiderOption(), panel);
				frame.setVisible(true);
			}
		});
		btnNew.setBounds(6, 40, 117, 29);
		add(btnNew);
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OptionEditHeader frame;
				try {
					frame = new OptionEditHeader(list.getSelectedIndex(), file.getDataSet().getSpiderOption(), panel);
					frame.setVisible(true);
				} catch (IndexOutOfBoundsException e1) {
				}
			}
		});
		btnEdit.setBounds(6, 70, 117, 29);
		add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(6, 98, 117, 29);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					file.getDataSet().getSpiderOption().removeHeaderElement(list.getSelectedIndex());
				} catch (IndexOutOfBoundsException e1) {
				}
			}
		});
		add(btnDelete);
		
		JButton btnApply = new JButton("Apply");
		btnApply.setBounds(6, 248, 117, 29);
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String strPort = textFieldPort.getText();
				lblPortInvalid.setVisible(false);
				try {
					file.getDataSet().getIntercepterOption().setPort(Integer.parseInt(strPort));
				} catch (NumberFormatException e1) {
					lblPortInvalid.setVisible(true);
				}
			}
		});
		add(btnApply);
		
	}
	
	void updateData() {
		this.textFieldPort.setText("" + file.getDataSet().getIntercepterOption().getPort());
		this.list.setModel(file.getDataSet().getSpiderOption().getRequestHeader());
	}
}

package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

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
	 * Intercepter port.
	 */

	private JTextField textFieldPort;

	/**
	 * Contains option component.
	 * 
	 * @param file
	 *            file operation handler
	 * @param intercept
	 *            the intercepter panel
	 * 
	 * @author Tomahawkd
	 */

	OptionPanel(FileIO file) {

		// Initialize FileIO class
		this.file = file;

		/*
		 * Self configuration
		 */

		setLayout(null);

		JLabel lblSetRequest = new JLabel("Set Request Head");
		lblSetRequest.setBounds(6, 6, 123, 16);
		add(lblSetRequest);

		/*
		 * Labels
		 */

		JLabel lblProxy = new JLabel("Proxy");
		lblProxy.setBounds(6, 159, 61, 16);
		add(lblProxy);

		JLabel lblHost = new JLabel("Host:");
		lblHost.setBounds(16, 187, 39, 16);
		add(lblHost);

		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(239, 187, 31, 16);
		add(lblPort);

		JLabel lblPortInvalid = new JLabel("Port invalid");
		lblPortInvalid.setBounds(424, 187, 71, 16);
		add(lblPortInvalid);
		lblPortInvalid.setVisible(false);

		JLabel labelTip = new JLabel("Only Localhost Support");
		labelTip.setBounds(61, 187, 172, 16);
		add(labelTip);

		JLabel lblServerStartingFailed = new JLabel("Server Starting Failed");
		lblServerStartingFailed.setBounds(264, 253, 132, 16);
		add(lblServerStartingFailed);
		lblServerStartingFailed.setVisible(false);

		/*
		 * Text field
		 */

		textFieldPort = new JTextField();
		textFieldPort.setBounds(282, 182, 130, 26);
		add(textFieldPort);
		textFieldPort.setText("" + file.getDataSet().getIntercepterOption().getPort());

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

		// Panel used to update data
		OptionPanel panel = this;

		JButton btnNew = new JButton("New");

		// Request header creator
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OptionNewHeader frame = new OptionNewHeader(file.getDataSet().getSpiderOption(), panel);
				frame.setVisible(true);
			}
		});
		btnNew.setBounds(6, 40, 117, 29);
		add(btnNew);

		JButton btnEdit = new JButton("Edit");

		// Request header editor
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {

					OptionEditHeader frame = new OptionEditHeader(list.getSelectedIndex(),
							file.getDataSet().getSpiderOption(), panel);
					frame.setVisible(true);

				} catch (ArrayIndexOutOfBoundsException e1) {
					// Ignore exception
				}
			}
		});
		btnEdit.setBounds(6, 70, 117, 29);
		add(btnEdit);

		JButton btnDelete = new JButton("Delete");

		// Delete request header
		btnDelete.setBounds(6, 98, 117, 29);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {

					file.getDataSet().getSpiderOption().removeHeaderElement(list.getSelectedIndex());

				} catch (IndexOutOfBoundsException e1) {
					// Ignore exception
				}
			}
		});
		add(btnDelete);

		JButton btnApply = new JButton("Apply");

		// Apply save port option
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
		btnApply.setBounds(6, 248, 117, 29);
		add(btnApply);

	}

	/**
	 * Update data in the panel.
	 */

	void updateData() {
		this.textFieldPort.setText("" + file.getDataSet().getIntercepterOption().getPort());
		this.list.setModel(file.getDataSet().getSpiderOption().getRequestHeader());
	}
}

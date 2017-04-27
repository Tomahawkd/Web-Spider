package Interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import data.SpiderOption;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Interface: Http request header creator.
 * 
 * @author Tomahawkd
 *
 */

class OptionNewHeader extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Request header creator
	 * 
	 * @param optionData
	 *            Data content
	 * @param panel
	 *            Option panel to update data
	 * 
	 * @author Tomahawkd
	 */

	OptionNewHeader(SpiderOption optionData, OptionPanel panel) {

		/*
		 * Self configuration
		 */

		setBounds(100, 100, 450, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		/*
		 * Labels
		 */

		JLabel lblNewHeader = new JLabel("Write your header string here");
		lblNewHeader.setBounds(6, 18, 438, 16);
		contentPane.add(lblNewHeader);

		JLabel lblTip = new JLabel("Please input valid string");
		lblTip.setBounds(149, 75, 151, 16);
		lblTip.setVisible(false);
		contentPane.add(lblTip);

		/*
		 * Text field
		 */

		JTextField Content = new JTextField();
		Content.setBounds(6, 46, 438, 26);
		contentPane.add(Content);

		/*
		 * Button
		 */

		JButton btnConfirm = new JButton("Confirm");

		// Confirm creation
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Validation check
				if (!Content.getText().equals("") && Content.getText().contains(": ")) {

					// Content valid, tip need not to be shown
					lblTip.setVisible(false);

					// Get header
					String newHeader = Content.getText();

					// Save it to the data class
					optionData.newHeaderElement(newHeader);

					// Update panel's data
					panel.updateData();

					// Close the dialog
					dispose();
				} else {

					// Content invalid, show the tip
					lblTip.setVisible(true);
				}
			}
		});
		btnConfirm.setBounds(169, 93, 117, 29);
		contentPane.add(btnConfirm);
	}
}

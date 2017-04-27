package Interface;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Interface: Notify the user the file name is invalid.
 * 
 * @author Tomahawkd
 *
 */

class FileNameInvalid extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();

	/*
	 * Create the dialog.
	 */

	FileNameInvalid() {

		/*
		 * Self configuration
		 */

		setBounds(100, 100, 450, 139);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		/*
		 * Label
		 */

		JLabel lblInvalid = new JLabel("File name is invalid");
		lblInvalid.setBounds(164, 33, 121, 16);
		contentPanel.add(lblInvalid);

		/*
		 * Button panel
		 */

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{

				// Button
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
		}
	}
}

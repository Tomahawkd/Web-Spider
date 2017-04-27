package Interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Interface: Notify the user when exit the application.
 * 
 * @author Tomahawkd
 */

class Exit extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/*
	 * Create the frame.
	 */

	Exit() {

		/*
		 * Self configuration
		 */

		setResizable(false);
		setTitle("Confirm");
		setBounds(230, 250, 450, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		/*
		 * Labels
		 */

		JLabel lblSaveYourProjects = new JLabel("Save your projects before you quit the application.");
		lblSaveYourProjects.setBounds(66, 22, 317, 16);
		contentPane.add(lblSaveYourProjects);

		JLabel lblDoYouWant = new JLabel("Do you want to quit?");
		lblDoYouWant.setBounds(66, 50, 317, 16);
		contentPane.add(lblDoYouWant);

		/*
		 * Buttons
		 */

		JButton btnOk = new JButton("OK");

		// Exit the application
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnOk.setBounds(76, 78, 117, 29);
		contentPane.add(btnOk);

		JButton btnCancel = new JButton("Cancel");

		// Cancel exiting
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnCancel.setBounds(266, 78, 117, 29);
		contentPane.add(btnCancel);
	}

}

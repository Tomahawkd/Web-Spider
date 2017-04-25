package Interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Interface: Fatal error handled notify to the user.
 * 
 * @author Tomahawkd
 */

class Error extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/*
	 * Create the frame.
	 */
	
	Error() {
		
		
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
		
		JLabel lblException = new JLabel("Unconfirmed Exception");
		lblException.setBounds(151, 27, 147, 16);
		contentPane.add(lblException);
		
		JLabel lblContactUs = new JLabel("Please contact us.");
		lblContactUs.setBounds(166, 55, 117, 16);
		contentPane.add(lblContactUs);
		
		
		/*
		 * Buttons
		 */
		
		JButton btnConfirm = new JButton("Confirm");
		
		//Fatal exception handled and exit the application
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		btnConfirm.setBounds(71, 83, 117, 29);
		contentPane.add(btnConfirm);
		
		
		JButton btnContact = new JButton("Contact");
		
		//Display the developer's contact
		btnContact.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aboutUs frame = new aboutUs();
				frame.setVisible(true);	
			}
		});
		btnContact.setBounds(253, 83, 117, 29);
		contentPane.add(btnContact);
	}
}

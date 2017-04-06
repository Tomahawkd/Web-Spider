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

public class Error extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	/*
	 * Create the frame.
	 */
	public Error() {
		setBounds(100, 100, 450, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblUnconfirmedException = new JLabel("Unconfirmed Exception");
		lblUnconfirmedException.setBounds(151, 27, 147, 16);
		contentPane.add(lblUnconfirmedException);
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		btnConfirm.setBounds(166, 81, 117, 29);
		contentPane.add(btnConfirm);
		
		JLabel lblContactUs = new JLabel("Please contact us.");
		lblContactUs.setBounds(166, 55, 117, 16);
		contentPane.add(lblContactUs);
	}
}

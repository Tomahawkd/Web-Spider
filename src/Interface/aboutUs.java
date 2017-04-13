package Interface;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Interface: Detailed information and developer contact.
 * 
 * @author Tomahawkd
 *
 */

class aboutUs extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	/*
	 * Create the frame.
	 */
	
	aboutUs() {
		
		
		/*
		 * Self configuration
		 */
		
		setResizable(false);
		setBounds(250, 230, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		/*
		 * Labels
		 */
		
		JLabel lblAboutUs = new JLabel("About us");
		lblAboutUs.setFont(new Font("Lucida Grande", Font.PLAIN, 28));
		lblAboutUs.setHorizontalAlignment(SwingConstants.CENTER);
		lblAboutUs.setBounds(156, 32, 138, 34);
		contentPane.add(lblAboutUs);
		
		JLabel lblStatement = new JLabel("Made only for study and web test, not for commercial.");
		lblStatement.setBounds(25, 88, 400, 16);
		contentPane.add(lblStatement);
		
		JLabel lblContactUs = new JLabel("Contact us: 942190747a@gmail.com");
		lblContactUs.setBounds(25, 144, 400, 16);
		contentPane.add(lblContactUs);
		
		JLabel lblAuthor = new JLabel("Author: Duan Mingcheng(Tomahawkd), Ye Tianchun(Yezipoiny)");
		lblAuthor.setBounds(25, 116, 400, 16);
		contentPane.add(lblAuthor);
		
		
		/*
		 * Buttons
		 */
		
		JButton btnOk = new JButton("OK");
		
		//Just close current window
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnOk.setBounds(166, 222, 117, 29);
		contentPane.add(btnOk);
	}
}



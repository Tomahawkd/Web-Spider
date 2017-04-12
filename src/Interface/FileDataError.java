package Interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Interface: Notify the user the file isn't match the data class.
 * 
 * @author Tomahawkd
 */

class FileDataError extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	
	
	/*
	 * Create the frame.
	 */
	
	FileDataError() {
		
		
		/*
		 * Self configuration
		 */
		
		setBounds(100, 100, 450, 160);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		/*
		 * Labels
		 */
		
		JLabel lblFileDataWas = new JLabel("File data was destoryed.");
		lblFileDataWas.setBounds(149, 49, 151, 16);
		contentPane.add(lblFileDataWas);
		
		
		/*
		 * Buttons
		 */
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnOk.setBounds(171, 90, 117, 29);
		contentPane.add(btnOk);
	}
}

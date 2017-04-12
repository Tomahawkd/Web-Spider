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
 * Interface: File creation error handled notify to the user.
 * 
 * @author Tomahawkd
 */

class FileCreationFailure extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	

	/*
	 * Create the frame.
	 */
	
	FileCreationFailure() {
		
		
		/*
		 * Self configuration
		 */
		
		setBounds(100, 100, 450, 197);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		
		/*
		 * Labels
		 */
		
		JLabel lblWrong = new JLabel("There is something wrong while creating the file.");
		lblWrong.setBounds(71, 60, 308, 16);
		contentPanel.add(lblWrong);
		
		
		/*
		 * Button panel
		 */
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				
				//Button
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

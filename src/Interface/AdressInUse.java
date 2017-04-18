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
 * Interface: Tip to indicate the server port is already in use.
 * 
 * @author Tomahawkd
 */

class AdressInUse extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();


	
	
	/*
	 * Create the dialog.
	 */
	
	AdressInUse() {
		setBounds(100, 100, 450, 233);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		
		/*
		 * Labels
		 */
		
		JLabel lblTip1 = new JLabel("The port is already in use, please shut down the ");
		lblTip1.setBounds(72, 51, 305, 16);
		contentPanel.add(lblTip1);
		{
			JLabel lblTip2 = new JLabel("program using this port and restart the server.");
			lblTip2.setBounds(75, 70, 300, 16);
			contentPanel.add(lblTip2);
		}
		{
			JLabel lblTip3 = new JLabel("Otherwise you cannot use intercepter function.");
			lblTip3.setBounds(77, 90, 296, 16);
			contentPanel.add(lblTip3);
		}
		{
			JLabel lblTip4 = new JLabel("Continue to ignore the issue, exit to close the application.");
			lblTip4.setBounds(41, 108, 367, 16);
			contentPanel.add(lblTip4);
		}
		
		
		/*
		 * Button panel 
		 */
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				
				
				/*
				 * Buttons
				 */
				
				JButton okButton = new JButton("Continue");
				
				//Ignore the issue
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				buttonPane.add(okButton);
				
				
				JButton cancelButton = new JButton("Exit");
				
				//Exit the application
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				buttonPane.add(cancelButton);
			}
		}
	}
}

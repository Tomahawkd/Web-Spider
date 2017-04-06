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
 * Interface: Http request header editor.
 * 
 * @author Tomahawkd
 *
 */

public class OptionEditHeader extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField Content;
	private JLabel lblTip;

	/**
	 * Create the frame.
	 */
	public OptionEditHeader(int index, SpiderOption optionData, OptionPanel panel) throws ArrayIndexOutOfBoundsException {
		setBounds(100, 100, 450, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEditHeader = new JLabel("Write your header string here");
		lblEditHeader.setBounds(6, 18, 438, 16);
		contentPane.add(lblEditHeader);
		
		Content = new JTextField();
		Content.setBounds(6, 46, 438, 26);
		contentPane.add(Content);
		Content.setText(optionData.getHeaderElement(index));
		
		JButton btnConfirm = new JButton("Confirm");
		btnConfirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!Content.getText().equals("") && Content.getText().contains(":")){
					lblTip.setVisible(false);
					String newHeader = Content.getText();
					optionData.editHeaderElement(index, newHeader);
					panel.updateData();
					dispose();
				} else {
					lblTip.setVisible(true);
				}
			}
		});
		btnConfirm.setBounds(169, 93, 117, 29);
		contentPane.add(btnConfirm);
		
		lblTip = new JLabel("Please input valid string");
		lblTip.setBounds(149, 75, 151, 16);
		contentPane.add(lblTip);
		lblTip.setVisible(false);
	}
}


package Interface;

import java.awt.Button;
import java.awt.Font;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import decode.Decoder;

/**
 * Interface: Decoder panel
 * 
 * @author Tomahawkd
 */

class DecoderPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	DecoderPanel() {
		setLayout(null);
		
		TextArea textArea_sourse_Decode = new TextArea();
		textArea_sourse_Decode.setBounds(10, 39, 649, 150);
		add(textArea_sourse_Decode);
		
		JLabel lblBaseDecode = new JLabel("Base64 Decode");
		lblBaseDecode.setBounds(10, 6, 123, 16);
		add(lblBaseDecode);
		
		JLabel lblResult_Decode = new JLabel("Result");
		lblResult_Decode.setBounds(6, 210, 61, 16);
		add(lblResult_Decode);
		
		TextArea textArea_result_Decode = new TextArea();
		textArea_result_Decode.setEditable(false);
		textArea_result_Decode.setFont(new Font("Arial", Font.PLAIN, 12));
		textArea_result_Decode.setBounds(10, 236, 649, 152);
		add(textArea_result_Decode);
		
		JButton btnOk_Decode = new JButton("OK");
		btnOk_Decode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String base64 = textArea_sourse_Decode.getText();
					textArea_result_Decode.setText(Decoder.getFromBASE64(base64));
				} catch (Exception ex){
					textArea_result_Decode.setText("Decode failed");
				}
			}
		});
		btnOk_Decode.setBounds(542, 205, 117, 29);
		add(btnOk_Decode);
		
		Button btnClear_Decode = new Button("Clear");
		btnClear_Decode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_sourse_Decode.setText("");
				textArea_result_Decode.setText("");
			}
		});
		btnClear_Decode.setBounds(419, 205, 117, 29);
		add(btnClear_Decode);
	}

}

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
import java.awt.Choice;

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

	/**
	 * Contains decode component.
	 */

	DecoderPanel() {

		/*
		 * Self configuration
		 */

		setLayout(null);

		/*
		 * Labels
		 */

		JLabel lblDecode = new JLabel("Decoder");
		lblDecode.setBounds(10, 6, 95, 16);
		add(lblDecode);

		JLabel lblResult = new JLabel("Result");
		lblResult.setBounds(6, 210, 61, 16);
		add(lblResult);

		/*
		 * Text areas
		 */

		// Source area
		TextArea textAreaSourse = new TextArea();
		textAreaSourse.setBounds(10, 39, 649, 150);
		add(textAreaSourse);

		// Result area
		TextArea textAreaResult = new TextArea();
		textAreaResult.setEditable(false);
		textAreaResult.setFont(new Font("Arial", Font.PLAIN, 12));
		textAreaResult.setBounds(10, 236, 649, 152);
		add(textAreaResult);

		/*
		 * Choice
		 */

		Choice choice = new Choice();
		choice.setBounds(533, 6, 126, 27);
		choice.add("BASE64");
		choice.add("Gzip");
		add(choice);

		/*
		 * Buttons
		 */

		JButton btnOK = new JButton("OK");

		// Confirm the input and activate the decoder
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String code = textAreaSourse.getText();
				if (choice.getSelectedItem().equals("Gzip")) {
					textAreaResult.setText(Decoder.getFromGzip(code));
				} else {
					try {
						textAreaResult.setText(Decoder.getFromBASE64(code));
					} catch (IllegalArgumentException ex) {
						textAreaResult.setText("Decode failed");
					}
				}
			}
		});
		btnOK.setBounds(542, 205, 117, 29);
		add(btnOK);

		Button btnClear = new Button("Clear");

		// Clear the input and the result
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textAreaSourse.setText("");
				textAreaResult.setText("");
			}
		});
		btnClear.setBounds(419, 205, 117, 29);
		add(btnClear);
	}
}

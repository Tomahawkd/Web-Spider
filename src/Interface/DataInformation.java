package Interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Interface: The detailed data web spider got.
 * 
 * @author Tomahawkd
 *
 */

class DataInformation extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * A window displays response data
	 * 
	 * @param data
	 *            response data
	 * 
	 * @author Tomahawkd
	 */

	DataInformation(String data) {

		/*
		 * Self configuration
		 */

		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		setBounds(100, 100, 840, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		/*
		 * Scroll pane
		 */

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 840, 428);
		contentPane.add(scrollPane);

		/*
		 * Data display panel
		 */

		JTextPane dataTextPanel = new JTextPane();
		dataTextPanel.setEditable(false);

		// Data contains response headers and body
		dataTextPanel.setText(data);
		scrollPane.setViewportView(dataTextPanel);
	}
}

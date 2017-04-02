package Interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class DataInformation extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/*
	 * Create the frame.
	 */
	public DataInformation(String data) {
		setBounds(100, 100, 550, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 550, 428);
		contentPane.add(scrollPane);
		
		JTextPane dataTextPanel = new JTextPane();
		dataTextPanel.setEditable(false);
		dataTextPanel.setText(data);
		scrollPane.setViewportView(dataTextPanel);
	}
}

package Interface;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 * Interface: Display the request and response data
 * 
 * @author Tomahawkd
 */

class InterceptInformation extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/*
	 * Create the frame.
	 */
	
	InterceptInformation(String url, String request, String response) {
		
		/*
		 * Self configuration
		 */
		
		setTitle(url);
		setBounds(100, 100, 800, 500);
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		});
		
		/*
		 * TabbedPane
		 */
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane);
		
		
		/*
		 * Request Panel
		 */
		
		
		/*
		 * Scroll pane 		 
		 */
		
		JScrollPane requestPanel = new JScrollPane();
		tabbedPane.addTab("Request", null, requestPanel, null);
		tabbedPane.setEnabledAt(0, true);
		
		
		/*
		 * Text pane
		 */
		
		JTextPane requestTextPane = new JTextPane();
		requestTextPane.setEditable(false);
		requestTextPane.setText(request);
		requestPanel.setViewportView(requestTextPane);
		
		
		/*
		 * Response Panel
		 */
		
		
		/*
		 * Scroll pane 		 
		 */
		
		JScrollPane responsePanel = new JScrollPane();
		tabbedPane.addTab("Response", null, responsePanel, null);
		tabbedPane.setEnabledAt(1, true);
		
		
		/*
		 * Text pane
		 */
		
		JTextPane responseTextPane = new JTextPane();
		responseTextPane.setEditable(false);
		responseTextPane.setText(response);
		responsePanel.setViewportView(responseTextPane);
		
		
		
	}

}

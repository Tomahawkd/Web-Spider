package Interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

/**
 * Interface: File load & save process indicator.
 * 
 * @author Tomahawkd
 *
 */

class FileProcess extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	
	
	/**
	 * File process indicator initializer
	 * 
	 * @param type File operation type
	 * 
	 * @see OperationType
	 * 
	 * @author Tomahawkd
	 */
	
	FileProcess(OperationType type) {
		
		
		/*
		 * Self configuration
		 */
		
		setResizable(false);
		setBounds(100, 100, 450, 178);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		/*
		 * Labels 
		 */
		
		JLabel lblSaving = new JLabel(type.getMessage());
		lblSaving.setBounds(194, 48, 61, 16);
		contentPane.add(lblSaving);
		lblSaving.setVisible(true);
		
		JLabel lblComplete = new JLabel("Complete");
		lblComplete.setBounds(194, 48, 61, 16);
		contentPane.add(lblComplete);
		lblComplete.setVisible(false);
		
		
		/*
		 * Buttons
		 */
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnOk.setBounds(166, 108, 117, 29);
		contentPane.add(btnOk);
		btnOk.setVisible(false);
		
		
		/*
		 * Process bar
		 */
		
		JProgressBar progressBar = new JProgressBar();		
		progressBar.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				
				//Process Monitor
				if(progressBar.getValue() == progressBar.getMaximum()){
					btnOk.setVisible(true);
					lblComplete.setVisible(true);
					lblSaving.setVisible(false);
				}
			}
		});
		progressBar.setBounds(152, 76, 146, 20);
		contentPane.add(progressBar);
		
		
		/*
		 * Self window listener configuration
		 */
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				new Thread(new Runnable(){
					public void run(){
						
						//Execute process bar processing state
						for(int i = progressBar.getMinimum(); i <= progressBar.getMaximum(); i++){
							
							//Refresh value
							progressBar.setValue(i);
							try {
								
								//Equals completing time
								Thread.sleep(type.getSleepTime());
								
							} catch (InterruptedException ex) {}
						} 
					}
				}).start();
			}
		});
		
	}
}



/**
 * Enumeration of file operation type
 * 
 * @author Tomahawkd
 */

enum OperationType {
	
	/**
	 * File creation exception
	 */
	
	NEW("Creating...", 5), 
	
	/**
	 * File load exception
	 */
	
	LOAD("Loading...", 10),
	
	/**
	 * File save and save-as exception
	 */
	
	SAVE("Saving..." , 10);
	
	private String message;
	private int sleepTime;
	
	
	
	
	private OperationType(String message, int sleepTime) {
		this.message = message;
		this.sleepTime = sleepTime;
	}
	
	/**
	 * Get the message of the current operation
	 * 
	 * @return Operation indicator message
	 */
	
	String getMessage() {
		return message;
	}
	
	/**
	 * Get the thread sleeping time
	 * 
	 * @return sleeping time
	 */
	
	int getSleepTime() {
		return sleepTime;
	}
}



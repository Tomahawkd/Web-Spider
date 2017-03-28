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

public class SaveFileProcess extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public SaveFileProcess() {
		setResizable(false);
		
		setBounds(100, 100, 450, 178);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSaving = new JLabel("Saving...");
		lblSaving.setBounds(194, 48, 61, 16);
		contentPane.add(lblSaving);
		lblSaving.setVisible(true);
		
		JLabel lblComplete = new JLabel("Complete");
		lblComplete.setBounds(194, 48, 61, 16);
		contentPane.add(lblComplete);
		lblComplete.setVisible(false);
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnOk.setBounds(166, 108, 117, 29);
		contentPane.add(btnOk);
		btnOk.setVisible(false);
		
		JProgressBar progressBar = new JProgressBar();		
		progressBar.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(progressBar.getValue() == 100){
					btnOk.setVisible(true);
					lblComplete.setVisible(true);
					lblSaving.setVisible(false);
				}
			}
		});
		progressBar.setBounds(152, 76, 146, 20);
		contentPane.add(progressBar);
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				new Thread(new Runnable(){
					public void run(){
						for(int i = progressBar.getMinimum(); i <= progressBar.getMaximum(); i++){
							progressBar.setValue(i);
							try {
								Thread.sleep(50);
							} catch (InterruptedException ex) {}
						} 
					}
				}).start();
			}
		});
		
	}
}

package Interface;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;

public class LoadFileProcess extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private final int THREADSLEEP = 50;
	/**
	 * Create the frame.
	 */
	public LoadFileProcess() {
		setEnabled(false);

		setBounds(100, 100, 450, 178);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLoading = new JLabel("Loading...");
		lblLoading.setBounds(194, 48, 61, 16);
		contentPane.add(lblLoading);
		lblLoading.setVisible(true);
		
		JProgressBar progressBar = new JProgressBar();		
		progressBar.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(progressBar.getValue() == progressBar.getMaximum()){
					dispose();
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
								Thread.sleep(THREADSLEEP);
							} catch (InterruptedException ex) {}
						} 
					}
				}).start();
			}
		});
	}
	
}

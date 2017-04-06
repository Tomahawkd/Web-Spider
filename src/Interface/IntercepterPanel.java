package Interface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;

import data.FileIO;
import proxy.ServerSocketListener;

public class IntercepterPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	IntercepterPanel(FileIO file) {
				
		setLayout(null);
		
		JTextArea httpContent_Intercept = new JTextArea();
		httpContent_Intercept.setBounds(6, 87, 657, 305);
		add(httpContent_Intercept);
		
		JLabel lblHost_InterceptLab = new JLabel("Host:");
		lblHost_InterceptLab.setBounds(6, 28, 61, 16);
		add(lblHost_InterceptLab);
		
		JLabel lblHost_Intercept = new JLabel("");
		lblHost_Intercept.setBounds(79, 28, 421, 16);
		add(lblHost_Intercept);
		
		JLabel lblTip_Intercept = new JLabel("Port Invalid");
		lblTip_Intercept.setBounds(463, 20, 71, 16);
		add(lblTip_Intercept);
		lblTip_Intercept.setVisible(false);
		
		JLabel lblError_InterceptLab = new JLabel("Unconfirmed Error");
		lblError_InterceptLab.setBounds(417, 51, 117, 16);
		add(lblError_InterceptLab);
		lblError_InterceptLab.setVisible(false);
		
		JToggleButton tglbtnIntercept = new JToggleButton("Intercept Off");
//		tglbtnIntercept.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//					if(tglbtnIntercept.getText().equals("Intercept Off")){
//						tglbtnIntercept.setText("Intercept On");
//						//TODO
//						new Thread(new Runnable() {
//							public void run() {
//								try {
//									while(true){
//										ServerSocketListener ssl = new ServerSocketListener();
//										ssl.setOption(file.getDataSet().getIntercepterOption());
//										ssl.start();
//										if(ssl.getSuspend()) {
//											String result = "";
//											for (String text : ssl.getData().getRequest()) {
//												result += text;
//											}
//											httpContent_Intercept.setText(result);
//											break;
//										}
//									}
//								} catch (IOException e) {
//									lblError_InterceptLab.setVisible(true);
//								}
//							}
//						}).start();
//					} else {
//						tglbtnIntercept.setText("Intercept Off");
//					}
//			}
//		});
		tglbtnIntercept.setBounds(546, 15, 117, 29);
		add(tglbtnIntercept);
		
		JButton btnForward_Intercept = new JButton("Forward");
		btnForward_Intercept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		btnForward_Intercept.setBounds(546, 46, 117, 29);
		add(btnForward_Intercept);
	}

}

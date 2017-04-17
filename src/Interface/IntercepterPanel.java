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
import intercepter.Server;
import javax.swing.JScrollPane;

/**
 * Interface: Intercepter panel
 * 
 * @author Tomahawkd
 */

class IntercepterPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Contains intercepter component.
	 * 
	 * @param file
	 *            file operation handler
	 */

	IntercepterPanel(FileIO file) {

		/*
		 * Self configuration
		 */

		setLayout(null);

		/*
		 * Label
		 */

		JLabel lblHost_InterceptLab = new JLabel("Host:");
		lblHost_InterceptLab.setBounds(6, 28, 61, 16);
		add(lblHost_InterceptLab);

		JLabel lblHost = new JLabel("");
		lblHost.setBounds(79, 28, 421, 16);
		add(lblHost);

		JLabel lblTip = new JLabel("Port Invalid");
		lblTip.setBounds(463, 20, 71, 16);
		add(lblTip);
		lblTip.setVisible(false);

		JLabel lblError = new JLabel("Unconfirmed Error");
		lblError.setBounds(417, 51, 117, 16);
		add(lblError);
		lblError.setVisible(false);

		/*
		 * Intercepted request container
		 */

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 87, 657, 305);
		add(scrollPane);

		JTextArea textAreaRequest = new JTextArea();
		scrollPane.setViewportView(textAreaRequest);

		/*
		 * Intercepter
		 */

		Server intercepter = new Server(file);
		new Thread(new Runnable() {
			public void run() {
				try {
					intercepter.start();
					while (true) {
						intercepter.action();
					}
				} catch (IOException e) {
					lblError.setVisible(true);
					e.printStackTrace();
				}
			}
		}).start();

		/*
		 * Buttons
		 */

		JToggleButton tglbtnIntercept = new JToggleButton("Intercept Off");
		tglbtnIntercept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tglbtnIntercept.getText().equals("Intercept Off")) {
					tglbtnIntercept.setText("Intercept On");
					new Thread(new Runnable() {
						public void run() {
							intercepter.resume();
							try {
								lblHost.setText(intercepter.current().getData().getURLString());
								textAreaRequest.setText(intercepter.current().getData().getRequest());
							} catch (NullPointerException e) {
								lblHost.setText("");
								textAreaRequest.setText("");
							}
						}
					}, "updateThread").start();
				} else {
					tglbtnIntercept.setText("Intercept Off");
					intercepter.stop();
				}
			}
		});
		tglbtnIntercept.setBounds(546, 15, 117, 29);
		add(tglbtnIntercept);

		JButton btnForward = new JButton("Forward");
		btnForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tglbtnIntercept.getText().equals("Intercept On")) {
					try {
						intercepter.current().getData().setRequest(textAreaRequest.getText());
						intercepter.response(intercepter.current());
						if (intercepter.next() != null) {
							lblHost.setText(intercepter.current().getData().getURLString());
							textAreaRequest.setText(intercepter.current().getData().getRequest());
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (NullPointerException e1) {
						lblHost.setText("");
						textAreaRequest.setText("");
					}
				}
			}
		});
		btnForward.setBounds(546, 46, 117, 29);
		add(btnForward);
	}

}

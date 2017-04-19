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

public class IntercepterPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FileIO file;
	private Server intercepter;
	private JLabel lblError;
	private JToggleButton tglbtnIntercept;
	private JButton btnForward;
	private JLabel lblHost;
	private JTextArea textAreaRequest;

	/**
	 * Contains intercepter component.
	 * 
	 * @param file
	 *            file operation handler
	 */

	IntercepterPanel(FileIO file) {

		// Initialization
		this.file = file;

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

		lblHost = new JLabel("");
		lblHost.setBounds(79, 28, 421, 16);
		add(lblHost);

		JLabel lblTip = new JLabel("Port Invalid");
		lblTip.setBounds(463, 20, 71, 16);
		add(lblTip);
		lblTip.setVisible(false);

		lblError = new JLabel("Unconfirmed Error");
		lblError.setBounds(417, 51, 117, 16);
		add(lblError);
		lblError.setVisible(false);

		/*
		 * Intercepted request container
		 */

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 87, 657, 305);
		add(scrollPane);

		textAreaRequest = new JTextArea();
		scrollPane.setViewportView(textAreaRequest);

		/*
		 * Intercepter
		 */

		intercepter = new Server(file, this);
		new Thread(new Runnable() {
			public void run() {

				try {
					intercepter.start();
					while (true) {
						try {
							intercepter.action();
						} catch (IOException e) {
							lblError.setVisible(true);
							e.printStackTrace();
						}
					}
				} catch (IOException e) {
					AdressInUse dialog = new AdressInUse();
					dialog.setVisible(true);
				}
			}
		}, "IntercepterMainThread").start();

		/*
		 * Buttons
		 */

		tglbtnIntercept = new JToggleButton("Intercept Off");
		tglbtnIntercept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (tglbtnIntercept.getText().equals("Intercept Off")) {
					tglbtnIntercept.setText("Intercept On");
					intercepter.resume();
				} else {
					tglbtnIntercept.setText("Intercept Off");
					intercepter.stop();
					new Thread(new Runnable() {
						public void run() {
							try {
								intercepter.sendAll();
							} catch (IOException e1) {
								lblError.setVisible(true);
								e1.printStackTrace();
							}
						}
					}, "SendAllRequestThread").start();
					lblHost.setText("");
					textAreaRequest.setText("");
				}
			}
		});
		tglbtnIntercept.setBounds(546, 15, 117, 29);
		add(tglbtnIntercept);

		btnForward = new JButton("Forward");
		btnForward.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tglbtnIntercept.getText().equals("Intercept On")) {
					try {
						if (intercepter.current() != null) {
							intercepter.current().getData().setRequest(textAreaRequest.getText());
							new Thread(new Runnable() {
								public void run() {
									try {
										intercepter.response(intercepter.current());
									} catch (IOException e1) {
										lblError.setVisible(true);
										e1.printStackTrace();
									}
								}
							}, "ForwardThread").start();
							if (intercepter.next() != null) {
								lblHost.setText(intercepter.current().getData().getURLString());
								textAreaRequest.setText(intercepter.current().getData().getRequest());
							} else {
								lblHost.setText("");
								textAreaRequest.setText("");
							}
						}
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

	public void updateData() {
		lblHost.setText(intercepter.current().getData().getURLString());
		textAreaRequest.setText(intercepter.current().getData().getRequest());
	}

	/**
	 * Restart server to refresh to the new port.
	 * 
	 * @author Tomahawkd
	 */

	void restartServer() {

		intercepter = new Server(file, this);

		new Thread(new Runnable() {
			public void run() {

				try {
					intercepter.start();
					while (true) {
						try {
							intercepter.action();
						} catch (IOException e) {
							lblError.setVisible(true);
							e.printStackTrace();
						}
					}
				} catch (IOException e) {
					AdressInUse dialog = new AdressInUse();
					dialog.setVisible(true);
				}
			}
		}, "IntercepterMainThread").start();

	}

}

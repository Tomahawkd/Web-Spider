package Interface;

import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import data.FileIO;
import intercepter.Intercepter;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.swing.JToggleButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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

	/**
	 * File operation handler
	 * 
	 * @see {@link FileIO}
	 */

	private FileIO file;

	/**
	 * Intercepter system
	 * 
	 * @see {@link IntercepterPanel}
	 */

	private Intercepter intercepter;

	/**
	 * Intercepter result list
	 * 
	 * @see {@link JList}
	 */

	private JList<String> list;

	/**
	 * Button controls server
	 */

	private JToggleButton tglbtnServerStart;

	/**
	 * Contains intercepter component.
	 * 
	 * @param file
	 *            file operation handler
	 * 
	 * @throws IOException
	 */

	IntercepterPanel(FileIO file) {

		// Initialization
		this.file = file;

		/*
		 * Self configuration
		 */

		setLayout(null);

		/*
		 * Intercepted request container
		 */

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 55, 657, 337);
		add(scrollPane);

		/*
		 * List
		 */

		list = new JList<String>();
		list.setModel(file.getDataSet().getIntercepterData().getModel());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				// Listen for double click
				if (e.getClickCount() == 2) {
					if (list.getSelectedIndex() != -1) {
						try {

							// Construct data panel
							InterceptInformation info = new InterceptInformation(
									file.getDataSet().getIntercepterData().getURL(list.getSelectedIndex()),
									file.getDataSet().getIntercepterData().getRequest(list.getSelectedIndex()),
									file.getDataSet().getIntercepterData().getResponse(list.getSelectedIndex()));
							info.setVisible(true);

						} catch (IndexOutOfBoundsException e1) {
						}
					}
				}
			}
		});
		scrollPane.setViewportView(list);

		/*
		 * Toggle button
		 */

		tglbtnServerStart = new JToggleButton("Server Start");
		tglbtnServerStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// Start Server
				if (tglbtnServerStart.getText().equals("Server Start")) {

					tglbtnServerStart.setText("Server Stop");
					file.getDataSet().refreshIntercepterData();
					startServer();

					// Stop Server
				} else {

					tglbtnServerStart.setText("Server Start");
					if (intercepter != null) {
						intercepter.stop();
					}
				}

			}
		});
		tglbtnServerStart.setBounds(502, 14, 161, 29);
		add(tglbtnServerStart);

	}

	/**
	 * Update list data
	 * 
	 * @author Tomahawkd
	 */

	public void updateData() {
		list.setModel(file.getDataSet().getIntercepterData().getModel());
	}

	/**
	 * Start server.
	 * 
	 * @throws IOException
	 * 
	 * @author Tomahawkd
	 */

	private void startServer() {

		try {
			intercepter = new Intercepter(file, this);

			new Thread(new Runnable() {
				public void run() {
					try {
						intercepter.start();
					} catch (IOException e) {
						intercepter.stop();
					}
				}
			}, "IntercepterMainThread").start();

		} catch (IOException e) {
			// The server port is already in use.

			intercepter.stop();
			AdressInUse dialog = new AdressInUse();
			dialog.setVisible(true);
			tglbtnServerStart.doClick();
		}
	}
}

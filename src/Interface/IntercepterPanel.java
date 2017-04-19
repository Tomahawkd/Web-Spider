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
	private Intercepter intercepter;
	private JList<String> list;

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
		 * Intercepted request container
		 */

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 6, 657, 386);
		add(scrollPane);

		list = new JList<String>();
		list.setModel(file.getDataSet().getIntercepterData().getModel());
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					if (list.getSelectedIndex() != -1) {
						try {
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
		 * Intercepter
		 */

		try {
			startServer();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void updateData() {
		list.setModel(file.getDataSet().getIntercepterData().getModel());
	}

	/**
	 * Restart server to refresh to the new port.
	 * 
	 * @author Tomahawkd
	 * @throws IOException
	 */

	void startServer() throws IOException {

		intercepter = new Intercepter(file);

		new Thread(new Runnable() {
			public void run() {
				try {
					intercepter.start();
				} catch (IOException e) {
					AdressInUse dialog = new AdressInUse();
					dialog.setVisible(true);
				}
			}
		}, "IntercepterMainThread").start();

	}

}

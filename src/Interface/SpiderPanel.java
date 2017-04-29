package Interface;

import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import data.FileIO;
import spider.SpiderRun;
import spider.NullHostException;

/**
 * Interface: Spider panel
 * 
 * @author Tomahawkd
 */

public class SpiderPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * User data handler
	 * 
	 * @see {@link FileIO}
	 */

	private FileIO file;

	/**
	 * Spider system
	 * 
	 * @see {@link SpiderRun}
	 */

	private SpiderRun spr;

	/**
	 * Host text field
	 */

	private JTextField site;

	/**
	 * Protocol choice
	 */

	private Choice protocol;

	/**
	 * Filter preference
	 */

	private Choice filter;

	/**
	 * Queue indicator
	 */

	private JLabel lblQueue;

	/**
	 * Request counter indicator
	 */

	private JLabel lblRequest;

	/**
	 * Contains spider settings component.
	 * 
	 * @param file
	 * 
	 * @author Tomahawkd
	 */

	SpiderPanel(FileIO file) {

		// Initialize data handler
		this.file = file;

		// Initialize
		spr = new SpiderRun(file, this);

		/*
		 * Self configuration
		 */

		setLayout(null);

		/*
		 * Labels
		 */

		JLabel lblHost = new JLabel("Host:");
		lblHost.setBounds(110, 97, 40, 16);
		add(lblHost);

		JLabel lblTipInvalid = new JLabel("Input invalid");
		lblTipInvalid.setBounds(286, 308, 79, 16);
		add(lblTipInvalid);
		lblTipInvalid.setVisible(false);

		JLabel lblProtocol = new JLabel("Protocol:");
		lblProtocol.setBounds(110, 45, 61, 16);
		add(lblProtocol);

		JLabel lblInQueue = new JLabel("In queue:");
		lblInQueue.setBounds(110, 154, 61, 16);
		add(lblInQueue);

		JLabel lblOption = new JLabel("Filter:");
		lblOption.setBounds(334, 45, 47, 16);
		add(lblOption);

		JLabel lblRequestSent = new JLabel("Request sent:");
		lblRequestSent.setBounds(110, 200, 85, 16);
		add(lblRequestSent);

		/*
		 * Counters
		 */

		lblQueue = new JLabel("0");
		lblQueue.setBounds(210, 154, 61, 16);
		add(lblQueue);

		lblRequest = new JLabel("0");
		lblRequest.setBounds(210, 200, 61, 16);
		add(lblRequest);

		/*
		 * Choices
		 */

		protocol = new Choice();
		protocol.setBounds(210, 45, 118, 21);
		add(protocol);
		protocol.add("http");
		protocol.add("https");

		filter = new Choice();
		filter.setBounds(387, 45, 118, 21);
		add(filter);
		filter.add("Host Only");
		filter.add("All Site");

		/*
		 * Text field
		 */

		site = new JTextField();
		site.setBounds(210, 92, 298, 26);
		add(site);
		site.setColumns(10);

		/*
		 * Toggle button
		 */

		JToggleButton tglbtnSession = new JToggleButton("Session Start");
		tglbtnSession.setBounds(248, 335, 161, 29);
		tglbtnSession.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// UI Update
				if (tglbtnSession.getText().equals("Session Start")) {
					tglbtnSession.setText("Session Stop");

					// Spider Settings Update
					file.getDataSet().getSpiderOption().setHost(site.getText());
					file.getDataSet().getSpiderOption().setProtocol(protocol.getSelectedItem());
					file.getDataSet().getSpiderOption().setFilter(filter.getSelectedItem());
					spr.setOption(file.getDataSet().getSpiderOption());

					// Run Spider
					new Thread(new Runnable() {
						public void run() {
							try {

								// Check is the first time to run
								if (spr.isFirstRun()) {

									// start the operation
									spr.start();
								} else {

									// resume the operation
									spr.resume();
								}
							} catch (NullHostException e) {
								// Validate the host

								lblTipInvalid.setVisible(true);
							} finally {

							// All operation has done, stop the spider
							spr.stop();
							}
						}
					}, "SpiderThread").start();

				} else {

					// Stop Spider
					spr.stop();

					// UI Update
					tglbtnSession.setText("Session Start");
					lblTipInvalid.setVisible(false);
				}
			}
		});
		add(tglbtnSession);
	}

	/**
	 * Refresh the queue indicator.
	 * 
	 * @param queue
	 *            new queue count
	 * 
	 * @author Tomahawkd
	 */

	public void refreshQueue(int queue) {

		// Update data displayed on GUI
		lblQueue.setText("" + queue);

		// Update data in DataSet class
		file.getDataSet().setQueueCounter(queue);
	}

	/**
	 * Refresh the counter.
	 * 
	 * @param request
	 *            new request sent count
	 * 
	 * @author Tomahawkd
	 */

	public void refreshRequestCounter(int request) {

		// Update data displayed on GUI
		lblRequest.setText("" + request);

		// Update data in DataSet class
		file.getDataSet().setRequestCounter(request);
	}

	/**
	 * Update data after loading a file.
	 * 
	 * @author Tomahawkd
	 */

	void updateData() {

		// Host
		this.site.setText(file.getDataSet().getSpiderOption().getHost());

		// Protocol
		this.protocol.select(file.getDataSet().getSpiderOption().getProtocol());

		// Filter
		this.filter.select(file.getDataSet().getSpiderOption().getFilter());

		// Queue indicator
		lblQueue.setText("" + file.getDataSet().getQueueCounter());

		// Request indicator
		lblRequest.setText("" + file.getDataSet().getRequestCounter());

		// Spider system
		spr.updateData();
	}

	/**
	 * Hold the site map class to update site map data.
	 * 
	 * @param siteMap
	 * 
	 * @see {@link SiteMapPanel}
	 * 
	 * @author Tomahawkd
	 */

	void setSiteMap(SiteMapPanel siteMap) {
		spr.setSiteMap(siteMap);
	}
}

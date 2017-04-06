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
import spider.nullHostException;

/**
 * Interface: Spider panel
 * 
 * @author Tomahawkd
 */

class SpiderPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FileIO file;
	private JTextField site;
	private JTextField port;
	private Choice protocol;
	private SiteMapPanel siteMap;
	
	SpiderPanel(FileIO file) {
		
		this.file = file;
		
		setLayout(null);
		
		JLabel lblHost = new JLabel("Host:");
		lblHost.setBounds(30, 48, 40, 16);
		add(lblHost);
		
		site = new JTextField();
		site.setBounds(200, 43, 268, 26);
		add(site);
		site.setColumns(10);
		
		JLabel lblPort_SpiderLab = new JLabel("Port:");
		lblPort_SpiderLab.setBounds(480, 48, 40, 16);
		add(lblPort_SpiderLab);
		
		JLabel lblTipInvalid_Spider = new JLabel("Input invalid");
		lblTipInvalid_Spider.setBounds(286, 308, 79, 16);
		add(lblTipInvalid_Spider);
		lblTipInvalid_Spider.setVisible(false);
		
		port = new JTextField();
		port.setBounds(532, 43, 86, 26);
		add(port);
		port.setColumns(10);
		port.setText("" + file.getDataSet().getSpiderOption().getPort());
		
		protocol = new Choice();
		protocol.setBounds(76, 48, 118, 21);
		add(protocol);
		protocol.add("http");
		protocol.add("https");
		
		SpiderRun spr = new SpiderRun(file.getDataSet().getSpiderData());
		JToggleButton tglbtn_Start_Spider = new JToggleButton("Session Start");
		tglbtn_Start_Spider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Change Text
				if(tglbtn_Start_Spider.getText().equals("Session Start")) {
					tglbtn_Start_Spider.setText("Session Stop");
				//Input Check
					try {
						String portStr = port.getText();
						Integer portInt = Integer.parseInt(portStr);
						file.getDataSet().getSpiderOption().setPort(portInt.intValue());
						file.getDataSet().getSpiderOption().setHost(site.getText());
						file.getDataSet().getSpiderOption().setProtocol(protocol.getSelectedItem());
						spr.setOption(file.getDataSet().getSpiderOption());
				//Run Spider
						new Thread(new Runnable() {
							public void run() {
								try {
									spr.start();
								} catch (nullHostException e) {
									lblTipInvalid_Spider.setVisible(true);
								}
								spr.stop();
							}
						}).start();
						siteMap.updateData();
					} catch (NumberFormatException e1) {
						lblTipInvalid_Spider.setVisible(true);
					}
				} else {
					spr.stop();
					siteMap.updateData();
					tglbtn_Start_Spider.setText("Session Start");
					lblTipInvalid_Spider.setVisible(false);
				}
			}
		});
		tglbtn_Start_Spider.setBounds(248, 335, 161, 29);
		add(tglbtn_Start_Spider);
	}
	
	void updateData() {
		this.port.setText("" + file.getDataSet().getSpiderOption().getPort());
		this.site.setText(file.getDataSet().getSpiderOption().getHost());
		this.protocol.select(file.getDataSet().getSpiderOption().getProtocol());
	}
	
	void setSiteMap(SiteMapPanel siteMap) {
		this.siteMap = siteMap;
	}

}

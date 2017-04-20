package Interface;

import data.*;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * Interface: The main panel
 * 
 * @author Tomahawkd
 *
 */

public class MainWindow {


	//Main frame
	private JFrame frmWebSpider;
	
	//Tab panel components
	private SpiderPanel spider;
	private SiteMapPanel siteMap;
	private IntercepterPanel intercepter;
	private OptionPanel options;
	private DecoderPanel decoder;
	
	/**
	 * User data handler
	 */
	
	private FileIO file = new FileIO();

	
	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frmWebSpider.setVisible(true);
				} catch (Exception e) {
					Error frame = new Error();
					frame.setVisible(true);
				}
			}
		});
	}
	
	
	/**
	 * Create the application.
	 */
	
	public MainWindow() {
		initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	
	private void initialize() {
		
		
		/*
		 *  Frame
		 */
		
		frmWebSpider = new JFrame();
		frmWebSpider.setResizable(false);
		frmWebSpider.setTitle("Web Spider");
		frmWebSpider.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Exit frame = new Exit();
				frame.setVisible(true);
			}
		});
		frmWebSpider.setBounds(100, 100, 702, 500);
		frmWebSpider.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		
		/*
		 *  Menu
		 */
		
		MenuBar menuBar = new MenuBar(file, this);
		frmWebSpider.setJMenuBar(menuBar);
				
		
		/*
		 *  Main tab Panel
		 */
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 690, 444);
		frmWebSpider.getContentPane().add(tabbedPane);
		
		
		/*
		 *  Panel: Spider
		 */
		
		spider = new SpiderPanel(file);
		tabbedPane.addTab("Spider", null, spider, "Web Spider");
		
		
		/*
		 *  Panel2: Site Map
		 */

		siteMap = new SiteMapPanel(file);
		tabbedPane.addTab("Site Map", null, siteMap, "Site Map");
		spider.setSiteMap(siteMap);
		
		
		/*
		 *  Panel3: Intercepter
		 */
		
		intercepter = new IntercepterPanel(file);
		tabbedPane.addTab("Intercepter", null, intercepter, "Intercepter Server");
		
		
		/*
		 *  Panel4: Options
		 */
		
		options = new OptionPanel(file);
		tabbedPane.addTab("Options", null, options, "User Preference");
		
		
		/*
		 *  Panel5: Decoder
		 */
		
		decoder = new DecoderPanel();
		tabbedPane.addTab("Decoder", null, decoder, "Decodes BASE64");
	}
	
	
	
	
	/**
	 * Update all data in <code>DataSet</code> class after loading file.
	 * 
	 * @author Tomahawkd
	 */
	
	public void updateUI() {
		this.frmWebSpider.setTitle(file.getTargetFilePath().equals("") ? 
				"Web Spider - " + file.getTargetFilePath() : "Web Spider");
		this.spider.updateData();
		this.options.updateData();
		this.siteMap.updateData();
		this.intercepter.updateData();
	}
}

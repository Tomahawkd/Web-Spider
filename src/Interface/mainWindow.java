package Interface;

import decode.Decoder;
import proxy.*;
import spider.*;
import data.*;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.awt.TextArea;
import java.awt.Panel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Button;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JTextArea;
import java.awt.Label;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Choice;

public class mainWindow {

	/*
	 * Global element in order to transfer data throw different tab pane
	 */
	private JFrame frmWebSpider;
	private JTextField textField_Site_Spider;
	private JTextField textField_Port_Spider;
	private JTextField textField_Port_Option;
	
	//User data set
	private RequestData requestData = new RequestData();
	private Option optionData = new Option();

	/*
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow window = new mainWindow();
					window.frmWebSpider.setVisible(true);
				} catch (Exception e) {
					Error frame = new Error();
					frame.setVisible(true);
				}
			}
		});
	}

	/*
	 * Create the application.
	 */
	
	public mainWindow() {
		initialize();
	}

	
	/*
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
				exit frame = new exit();
				frame.setVisible(true);
			}
		});
		frmWebSpider.setBounds(100, 100, 702, 500);
		frmWebSpider.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		
		/*
		 *  Menu
		 */
		
		JMenuBar menuBar = new JMenuBar();
		frmWebSpider.setJMenuBar(menuBar);
		
		/*
		 *  Menu: Project
		 */
		
		JMenu mnProject = new JMenu("Project");
		menuBar.add(mnProject);
		
		JMenuItem mntmNew = new JMenuItem("New...");
		mntmNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewFile newFile = new NewFile();
				newFile.setVisible(true);
			}
		});
		mnProject.add(mntmNew);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        JFileChooser jfc=new JFileChooser();  
		        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);  
		        jfc.showOpenDialog(new JLabel());  
		        File file=jfc.getSelectedFile();  
		        if(file.isDirectory()){  
		            System.out.println("文件夹:"+file.getAbsolutePath());  
		        }else if(file.isFile()){  
		            System.out.println("文件:"+file.getAbsolutePath());  
		        }  
		        System.out.println(jfc.getSelectedFile().getName()); 
			}
		});
		mnProject.add(mntmLoad);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mntmSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saving frame = new saving();
				frame.setVisible(true);				
			}
		});
		mnProject.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save as...");
		mntmSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc=new JFileChooser();  
		        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);  
		        jfc.showSaveDialog(new JLabel());
		        File file=jfc.getSelectedFile();  
		        if(file.isDirectory()){  
		            System.out.println("文件夹:"+file.getAbsolutePath());  
		        }else if(file.isFile()){  
		            System.out.println("文件:"+file.getAbsolutePath());  
		        }  
		        System.out.println(jfc.getSelectedFile().getName()); 
			}
		});
		mnProject.add(mntmSaveAs);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				exit frame = new exit();
				frame.setVisible(true);
			}
		});
		mnProject.add(mntmExit);
		
		/*
		 *  Menu: About
		 */
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		JMenuItem mntmAboutUs = new JMenuItem("About Us");
		mntmAboutUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aboutUs frame = new aboutUs();
				frame.setVisible(true);	
			}
		});
		mnAbout.add(mntmAboutUs);
		
		
		/*
		 *  Main Panel
		 */
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(6, 6, 690, 444);
		frmWebSpider.getContentPane().add(tabbedPane);
		
		/*
		 *  Panel: Spider
		 */
		
		JPanel panel_spider = new JPanel();
		tabbedPane.addTab("Spider", null, panel_spider, null);
		panel_spider.setLayout(null);
		
		JLabel lblHost_SpiderLab = new JLabel("Host:");
		lblHost_SpiderLab.setBounds(30, 48, 40, 16);
		panel_spider.add(lblHost_SpiderLab);
		
		textField_Site_Spider = new JTextField();
		textField_Site_Spider.setBounds(200, 43, 268, 26);
		panel_spider.add(textField_Site_Spider);
		textField_Site_Spider.setColumns(10);
		
		JLabel lblPort_SpiderLab = new JLabel("Port:");
		lblPort_SpiderLab.setBounds(480, 48, 40, 16);
		panel_spider.add(lblPort_SpiderLab);
		
		JLabel lblTipInvalid_Spider = new JLabel("Input invalid");
		lblTipInvalid_Spider.setBounds(286, 308, 79, 16);
		panel_spider.add(lblTipInvalid_Spider);
		lblTipInvalid_Spider.setVisible(false);
		
		textField_Port_Spider = new JTextField();
		textField_Port_Spider.setBounds(532, 43, 86, 26);
		panel_spider.add(textField_Port_Spider);
		textField_Port_Spider.setColumns(10);
		
		Choice choiceProtocol = new Choice();
		choiceProtocol.setBounds(76, 48, 118, 21);
		panel_spider.add(choiceProtocol);
		choiceProtocol.add("http");
		choiceProtocol.add("https");
		
		SpiderRun spr = new SpiderRun();
		JToggleButton tglbtn_Start_Spider = new JToggleButton("Session Start");
		tglbtn_Start_Spider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//Change Text
				if(tglbtn_Start_Spider.getText().equals("Session Start")) {
					tglbtn_Start_Spider.setText("Session Stop");
				//TODO
				//Input Check
					try {
						String portStr = textField_Port_Spider.getText();
						Integer portInt = Integer.parseInt(portStr);
						optionData.setPortSpider(portInt.intValue());
						optionData.setHost(textField_Site_Spider.getText());
						optionData.setProtocol(choiceProtocol.getSelectedItem());
				//Run Spider
						new Thread(new Runnable() {
							public void run() {
								try {
									spr.start(optionData.getProtocol(), optionData.getHost(), optionData.getPortSpider());
								} catch (nullHostException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								spr.stop();
							}
						});
				
					} catch (NumberFormatException e1) {
						lblTipInvalid_Spider.setVisible(true);
//					} catch (IOException e1){
//						Error frame = new Error();
//						frame.setVisible(true);
					}
				} else {
					spr.stop();
					tglbtn_Start_Spider.setText("Session Start");
					lblTipInvalid_Spider.setVisible(false);
				}
			}
		});
		tglbtn_Start_Spider.setBounds(248, 335, 161, 29);
		panel_spider.add(tglbtn_Start_Spider);
		
		
		/*
		 *  Panel2: Site Map
		 */
		
		JScrollPane panel_SiteMap = new JScrollPane();
		tabbedPane.addTab("Site Map", null, panel_SiteMap, null);
		
		
		/*
		 *  Panel3: Intercepter
		 */
		
		JPanel panel_intercepter = new JPanel();
		tabbedPane.addTab("Intercepter", null, panel_intercepter, null);
		panel_intercepter.setLayout(null);
		
		JTextArea httpContent_Intercept = new JTextArea();
		httpContent_Intercept.setBounds(6, 87, 657, 305);
		panel_intercepter.add(httpContent_Intercept);
		
		JLabel lblHost_InterceptLab = new JLabel("Host:");
		lblHost_InterceptLab.setBounds(6, 28, 61, 16);
		panel_intercepter.add(lblHost_InterceptLab);
		
		JLabel lblHost_Intercept = new JLabel("");
		lblHost_Intercept.setBounds(79, 28, 421, 16);
		panel_intercepter.add(lblHost_Intercept);
		
		JLabel lblTip_Intercept = new JLabel("Port Invalid");
		lblTip_Intercept.setBounds(463, 20, 71, 16);
		panel_intercepter.add(lblTip_Intercept);
		lblTip_Intercept.setVisible(false);
		
		JLabel lblError_InterceptLab = new JLabel("Unconfirmed Error");
		lblError_InterceptLab.setBounds(417, 51, 117, 16);
		panel_intercepter.add(lblError_InterceptLab);
		lblError_InterceptLab.setVisible(false);
		
		JToggleButton tglbtnIntercept = new JToggleButton("Intercept Off");
		tglbtnIntercept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(tglbtnIntercept.getText().equals("Intercept Off")){
						tglbtnIntercept.setText("Intercept On");
							if(!textField_Port_Option.getText().equals("")){
								try {
									String strPort_Option = textField_Port_Option.getText();
									optionData.setPortOption(Integer.parseInt(strPort_Option));
									//TODO
//									new Thread(new Runnable() {
//										public void run() {
//											try {
//												while(true){
//													ServerSocketListener ssl = new ServerSocketListener(port_Option);
//													ssl.action();
//													if(ssl.getSuspend()) {
//														httpRequest = ssl.getHttpRequset();
//														break;
//													}
//												}
//											} catch (IOException e) {
//												lblError_InterceptLab.setVisible(true);
//											}
//										}
//									});
								} catch (NumberFormatException e1) {
									lblTip_Intercept.setVisible(true);
								}
							}
					} else {
						tglbtnIntercept.setText("Intercept Off");
						lblTip_Intercept.setVisible(false);
					}
			}
		});
		tglbtnIntercept.setBounds(546, 15, 117, 29);
		panel_intercepter.add(tglbtnIntercept);
		
		JButton btnForward_Intercept = new JButton("Forward");
		btnForward_Intercept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		btnForward_Intercept.setBounds(546, 46, 117, 29);
		panel_intercepter.add(btnForward_Intercept);
	
		
		/*
		 *  Panel4: Options
		 */
		
		JPanel panel_options = new JPanel();
		tabbedPane.addTab("Options", null, panel_options, null);
		panel_options.setLayout(null);
		
		JLabel lblSetRequest_Option = new JLabel("Set Request Head");
		lblSetRequest_Option.setBounds(6, 6, 123, 16);
		panel_options.add(lblSetRequest_Option);
		
		
		JLabel lblProxy_Option = new JLabel("Proxy");
		lblProxy_Option.setBounds(6, 159, 61, 16);
		panel_options.add(lblProxy_Option);
		
		JLabel lblHost_Option = new JLabel("Host:");
		lblHost_Option.setBounds(16, 187, 39, 16);
		panel_options.add(lblHost_Option);
		
		JLabel lblPort_Option = new JLabel("Port:");
		lblPort_Option.setBounds(239, 187, 31, 16);
		panel_options.add(lblPort_Option);
		
		textField_Port_Option = new JTextField();
		textField_Port_Option.setBounds(282, 182, 130, 26);
		panel_options.add(textField_Port_Option);
		textField_Port_Option.setText("" + optionData.getPortOption());
		
		Label labelTip_Option = new Label("Only Localhost Support");
		labelTip_Option.setBounds(61, 187, 172, 16);
		panel_options.add(labelTip_Option);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(135, 40, 513, 87);
		panel_options.add(scrollPane);
		
		JList<String> list = new JList<String>();
		scrollPane.setViewportView(list);
		list.setModel(optionData.getRequestHeader());
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				list.validate();
			}
		});
		
		
		JButton btnNew = new JButton("New");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OptionNewHeader frame = new OptionNewHeader(optionData);
				frame.setVisible(true);
			}
		});
		btnNew.setBounds(6, 40, 117, 29);
		panel_options.add(btnNew);
		
		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OptionEditHeader frame;
				try {
					frame = new OptionEditHeader(list.getSelectedIndex(), optionData);
					frame.setVisible(true);
				} catch (IndexOutOfBoundsException e1) {
				}
			}
		});
		btnEdit.setBounds(6, 70, 117, 29);
		panel_options.add(btnEdit);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(6, 98, 117, 29);
		panel_options.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					optionData.removeHeaderElement(list.getSelectedIndex());
				} catch (IndexOutOfBoundsException e1) {
				}
			}
		});
		
		/*
		 *  Panel5: Decoder
		 */
		
		Panel panel_decoder = new Panel();
		tabbedPane.addTab("Decoder", null, panel_decoder, null);
		panel_decoder.setLayout(null);
		
		TextArea textArea_sourse_Decode = new TextArea();
		textArea_sourse_Decode.setBounds(10, 39, 649, 150);
		panel_decoder.add(textArea_sourse_Decode);
		
		JLabel lblBaseDecode = new JLabel("Base64 Decode");
		lblBaseDecode.setBounds(10, 6, 123, 16);
		panel_decoder.add(lblBaseDecode);
		
		JLabel lblResult_Decode = new JLabel("Result");
		lblResult_Decode.setBounds(6, 210, 61, 16);
		panel_decoder.add(lblResult_Decode);
		
		TextArea textArea_result_Decode = new TextArea();
		textArea_result_Decode.setEditable(false);
		textArea_result_Decode.setFont(new Font("Arial", Font.PLAIN, 12));
		textArea_result_Decode.setBounds(10, 236, 649, 152);
		panel_decoder.add(textArea_result_Decode);
		
		JButton btnOk_Decode = new JButton("OK");
		btnOk_Decode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					String base64 = textArea_sourse_Decode.getText();
					textArea_result_Decode.setText(Decoder.getFromBASE64(base64));
				} catch (Exception ex){
					textArea_result_Decode.setText("Decode failed");
				}
			}
		});
		btnOk_Decode.setBounds(542, 205, 117, 29);
		panel_decoder.add(btnOk_Decode);
		
		Button btnClear_Decode = new Button("Clear");
		btnClear_Decode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textArea_sourse_Decode.setText("");
				textArea_result_Decode.setText("");
			}
		});
		btnClear_Decode.setBounds(419, 205, 117, 29);
		panel_decoder.add(btnClear_Decode);
	}
}

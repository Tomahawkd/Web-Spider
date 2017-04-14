package Interface;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import data.FileIO;

/**
 * Interface: Site Map panel
 * 
 * @author Tomahawkd
 */

public class SiteMapPanel extends JScrollPane {

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
	 * Site map JTree panel
	 * 
	 * @see {@link JTree}
	 */
	
	private JTree siteMap;

	
	
	
	SiteMapPanel(FileIO file) {
		
		//initialize the handler
		this.file = file;
		
		
		/*
		 * Site map
		 */
		
		siteMap = new JTree();
		
		//site map configuration
		siteMap.setRootVisible(true);
		siteMap.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		//Get data
		siteMap.setModel(new DefaultTreeModel(file.getDataSet().getSpiderData().getRoot()));
		
		//Double tap to get detailed information
		siteMap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				//Double tap listener
				if(e.getClickCount() == 2) {
					
					//Get the selection
					Object node = siteMap.getLastSelectedPathComponent();
					
					//Check the selection is currently selected
					if(node != null) {
						
						try {
							
							//Get detailed information data
							String data = file.getDataSet().getSpiderData().getData(node);
							
							//Give the data to the information panel
							DataInformation frame = new DataInformation(data);
							frame.setVisible(true);
							
						} catch (ClassCastException e1) {
							//Exception data is not valid
							
							//Notify the user the date is not valid
							DataInformation frame = new DataInformation("Data is not valid");
							frame.setVisible(true);
						}
					}
				}
			}
		});
		setViewportView(siteMap);
	}
	
	
	/**
	 * Update site map data when the spider get a new <code>SpiderNode</code>.
	 * 
	 * @see {@link data.SpiderData}
	 */
	
	public void updateData() {
		this.siteMap.setModel(new DefaultTreeModel(file.getDataSet().getSpiderData().getRoot()));
	}

}

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

class SiteMapPanel extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FileIO file;
	private JTree siteMap;

	SiteMapPanel(FileIO file) {
		
		this.file = file;
		
		siteMap = new JTree();
		siteMap.setModel(new DefaultTreeModel(file.getDataSet().getSpiderData().getRoot()));
		siteMap.setRootVisible(false);
		siteMap.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					Object node = siteMap.getLastSelectedPathComponent();
					if(node != null) {
						try {
							String data = file.getDataSet().getSpiderData().getData(node);
							DataInformation frame = new DataInformation(data);
							frame.setVisible(true);
						} catch (ClassCastException e1) {}
					}
				}
			}
		});
		siteMap.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		setViewportView(siteMap);
	}
	
	void updateData() {
		this.siteMap.setModel(new DefaultTreeModel(file.getDataSet().getSpiderData().getRoot()));
	}

}

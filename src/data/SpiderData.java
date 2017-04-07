package data;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Data: Store spider's site data
 * 
 * @author Tomahawkd
 */

public class SpiderData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<SpiderNode> mainNodes;
	private SpiderNode currentNode;
	private SpiderNode root;
	
	/**
	 * Constructor using to initialize data class in <code>DataSet</code>> class.
	 * 
	 * @see {@link DataSet}
	 * 
	 * @author Tomahawkd
	 */
	
	public SpiderData() {
		root = new SpiderNode("", "");
		mainNodes = new ArrayList<SpiderNode>();
	}
	
	/**
	 * Get the root node.
	 * 
	 * @return root node.
	 * 
	 * @see {@link JTree}
	 * 
	 * @author Tomahawkd
	 */
	
	public SpiderNode getRoot() {
		return root;
	}
	
	/**
	 * Get the selected node data
	 * 
	 * @param node selected node
	 * 
	 * @return Data in the node
	 * 
	 * @throws ClassCastException
	 * 
	 * @see {@link JTree}
	 * 
	 * @author Tomahawkd
	 */
	
	public String getData(Object node) throws ClassCastException {
		return ((SpiderNode) node).getData();
	}
	
	/**
	 * Add a new site node to the site map.
	 * 
	 * @param path A string array contains path sequence.
	 * @param name The site node's name.
	 * @param data THe site node's data.
	 * 
	 * @author Tomahawkd
	 */
	
	public void add(String[] path, String name, String data) {
		
		//Judge a root node
			for(int index = 0; index < mainNodes.size(); index++) {
				if(mainNodes.get(index).name.equals(path[0])) {
					currentNode = mainNodes.get(index);
				}
			}
			if(currentNode == null) {
				SpiderNode newMainNode = new SpiderNode(name, data);
				mainNodes.add(newMainNode);
				root.add(newMainNode);
				currentNode = newMainNode;
			}
			for(int index = 0; index < path.length; index++) {
				if(currentNode.isChildExist(path[index]) == -1) {
					SpiderNode newChild;
					if(index != path.length - 1) {
						newChild = new SpiderNode(name, "");
					} else {
						newChild = new SpiderNode(name, data);
					}
					currentNode.add(newChild);
				
				} else {
					currentNode = (SpiderNode) currentNode.getChildAt(currentNode.isChildExist(path[index]));
				}
			}
	}

	/**
	 * Data: Spider node
	 * 
	 * @see {@link DefaultMutableTreeNode}
	 * 
	 * @author Tomahawkd
	 */
	
	private class SpiderNode extends DefaultMutableTreeNode implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String name;
		private String data;
		
		/**
		 * Constructor
		 * 
		 * @param name node's name.
		 * @param data node's data.
		 * 
		 * @author Tomahawkd
		 */
		
		public SpiderNode(String name, String data) {
			super(name);
			this.name = name;
			this.data = data;
		}
//		
//		/**
//		 * Set specific node's data.
//		 * 
//		 * @param name node's data.
//		 * 
//		 * @author Tomahawkd
//		 */
//		
//		void setData(String data) {
//			this.data = data;
//		}

		/**
		 * Get data in the specific node.
		 * 
		 * @return node's data.
		 * 
		 * @author Tomahawkd
		 */
		
		String getData() {
			return data;
		}
		
		/**
		 * Check and return the child's index of parent node's children array.
		 * 
		 * @param name node's name
		 * @return index the node in parent node's children array. return -1 if not exist.
		 * 
		 * @author Tomahawkd
		 */
		
		int isChildExist(String name) {
			int exist = -1;
			if(this.children != null) {
				for(Object child : this.children) {
					try{
						SpiderNode childNode = (SpiderNode) child;
						if (childNode.name.equals(name)) {
							break;
						}
						exist++;
					} catch(ClassCastException e) {}
				}
			}
			return exist;
		}
		
		

	}
	
}

package data;

import java.io.Serializable;

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

	/**
	 * Root nodes
	 */

	private SpiderNode root;

	/**
	 * Constructor using to initialize data class in <code>DataSet</code>>
	 * class.
	 * 
	 * @see {@link DataSet}
	 * 
	 * @author Tomahawkd
	 */

	public SpiderData() {
		root = new SpiderNode("", "");
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
	 * @param node
	 *            selected node
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
	 * @param path
	 *            A string array contains path sequence.
	 * @param name
	 *            The site node's name.
	 * @param data
	 *            THe site node's data.
	 * 
	 * @author Tomahawkd
	 */

	public void add(String[] path, String data) {

		SpiderNode currentNode = root;

		// Loop path array to locate the file
		for (int index = 0; index < path.length; index++) {

			// Check if the node is already exist
			int childIndex = currentNode.getChildIndex(path[index]);
			if (childIndex == -1) {

				// Create new node
				SpiderNode newChild = new SpiderNode(path[index], "");
				currentNode.add(newChild);
				currentNode = newChild;

			} else {

				// Get child node
				currentNode = (SpiderNode) currentNode.getChildAt(childIndex);
			}
		}

		// Add data
		currentNode.data = data;
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
		 * @param name
		 *            node's name.
		 * @param data
		 *            node's data.
		 * 
		 * @author Tomahawkd
		 */

		public SpiderNode(String name, String data) {
			super(name);
			this.name = name;
			this.data = data;
		}

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
		 * @param name
		 *            node's name
		 * 
		 * @return index the node in parent node's children array. return -1 if
		 *         not exist.
		 * 
		 * @author Tomahawkd
		 */

		int getChildIndex(String name) {

			// Initialize the index pointer
			int index = -1;

			//
			if (this.children != null) {

				// Point to the first child
				index = 0;
				for (Object child : this.children) {
					try {

						SpiderNode childNode = (SpiderNode) child;

						// Check the child name
						if (childNode.name.equals(name)) {
							break;
						}

						// Point to next child
						index++;

					} catch (ClassCastException e) {
					}
				}

				// Out of index equal not exist
				if (index == this.children.size()) {
					index = -1;
				}
			}
			return index;
		}

	}

}

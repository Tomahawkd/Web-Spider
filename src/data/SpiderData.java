package data;

import java.io.Serializable;

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

	private SpiderNode mainNode;
	private SpiderNode currentNode;
	
	public SpiderData() {
		mainNode = new SpiderNode("", "");
	}
	
	public SpiderData(String host, String data) {
		mainNode = new SpiderNode(host, data);
	}
	
	public void setHost(String host) {
		mainNode.setName(host);
	}
		
	public void setMainNode(SpiderNode main) {
		this.mainNode = main;
	}
	
	public SpiderNode getMainNode() {
		return mainNode;
	}
	
	public String getData(Object node) throws ClassCastException {
		return ((SpiderNode) node).getData();
	}
	
	public void add(String[] path, String name, String data) {
		currentNode = mainNode;
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

	private class SpiderNode extends DefaultMutableTreeNode implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String name;
		private String data;
		
		public SpiderNode(String name, String data) {
			super(name);
			this.name = name;
			this.data = data;
		}

		public String getData() {
			return data;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public int isChildExist(String name) {
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

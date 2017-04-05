package data;

import java.io.Serializable;
import org.jsoup.nodes.Document;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Data: Store spider's site data
 * 
 * @author Ghost
 */

@SuppressWarnings("restriction")
public class SpiderData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SpiderNode mainNode;
	private SpiderNode currentNode;
	
	public SpiderData() {
		Document empty = new Document("");
		mainNode = new SpiderNode("", empty);
	}
	
	public SpiderData(String host, Document data) {
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
	
	public Document getData(Object node) throws ClassCastException {
		return ((SpiderNode) node).getData();
	}
	
	public void add(String[] path, String name, Document data) {
		currentNode = mainNode;
		for(int index = 0; index < path.length; index++) {
			if(currentNode.isChildExist(path[index]) == -1) {
				SpiderNode newChild;
				if(index != path.length - 1) {
					Document empty = new Document("");
					newChild = new SpiderNode(name, empty);
				} else {
					newChild = new SpiderNode(name, data);
				}
				currentNode.add(newChild);
				
			} else {
				currentNode = (SpiderNode) currentNode.getChildAt(currentNode.isChildExist(path[index]));
			}
		}
	}
	
	public String getName() {
		return currentNode.getName();
	}
	 
	
	private static class SpiderNode extends DefaultMutableTreeNode implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String name;
		private Document data;
		
		public SpiderNode(String name, Document data) {
			super(name);
			this.name = name;
			this.data = data;
		}

		public Document getData() {
			return data;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public String getName() {
			return name;
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

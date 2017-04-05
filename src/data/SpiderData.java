package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.NoSuchElementException;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 * Data: Store spider's site data
 * 
 * @author Ghost
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
				SpiderNode newChild = new SpiderNode(name, data);
				currentNode.add(newChild);
				
			} else {
				currentNode = (SpiderNode) currentNode.getChildAt(index);
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
		private String data;
		
		public SpiderNode(String name, String data) {
			this.name = name;
			this.data = data;
		}

		public String getData() {
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
			for(Object child : this.children) {
				try{
					SpiderNode childNode = (SpiderNode) child;
					if (childNode.name.equals(name)) {
						break;
					}
					exist++;
				} catch(ClassCastException e) {}
			}
			return exist;
		}
		
		

	}
	
}

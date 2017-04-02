package data;

import java.io.Serializable;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

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
	private SpiderNode parentNode;
	private SpiderNode currentNode;
	
	public SpiderData() {}
	
	public SpiderData(String host, String data) {
		mainNode = new SpiderNode(host, data);
	}
		
	public void setMainNode(SpiderNode main) {
		this.mainNode = main;
	}
	
	public SpiderNode getMainNode() {
		return mainNode;
	}
	
	public String getData() {
		return currentNode.getData();
	}
	
	public String getName() {
		return currentNode.getName();
	}
	
	public void addChild(String name, String data) {
		currentNode = new SpiderNode(name, data);
		parentNode.add(currentNode);
	}
	
	public String getData(TreePath path) {
		
		
		return new String();
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
			this.data = data;
		}
		
		String getData() {
			return data;
		}
		
		public String getName() {
			return name;
		}

	}
	
}

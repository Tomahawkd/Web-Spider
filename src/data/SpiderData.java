package data;

import java.io.Serializable;

import javax.swing.tree.DefaultMutableTreeNode;

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

	private String data;
	private SpiderNode mainNode;
	private SpiderNode parentNode;
	private SpiderNode currentNode;
	
	public SpiderData() {}
	
	public SpiderData(String host, String data) {
		mainNode = new SpiderNode(host, data);
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public void addChild(String name, String data) {
		currentNode = new SpiderNode(name, data);
		
	}
	
	private void search(String path) {
		
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
		
		public String getData() {
			return data;
		}
		
		public void setData(String data) {
			this.data = data;
		}
		
		public String getName() {
			return name;
		}

	}
	
}

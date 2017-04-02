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

	private String host;
	private String data;
	private SpiderNode mainNode;
	private SpiderNode parentNode;
	private SpiderNode childNode;
	
	public SpiderData() {
	}
	
	public SpiderData(String host, String data) {
		this.host = host;
		mainNode = new SpiderNode(host, data);
	}
	
	public String getHost() {
		return host;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public void addChild(String name, String data) {
		childNode = new SpiderNode(name, data);
		
	}
	
	public void search(String path) {
		
	}
	
}

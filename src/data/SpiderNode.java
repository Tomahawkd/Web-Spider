package data;

import java.io.Serializable;

import javax.swing.tree.DefaultMutableTreeNode;

public class SpiderNode extends DefaultMutableTreeNode implements Serializable {

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

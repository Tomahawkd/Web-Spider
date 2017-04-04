package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.NoSuchElementException;

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
		//TODO
		
		
		currentNode.add(new SpiderNode(name, data, currentNode));
	}
	
	public String getName() {
		return currentNode.getName();
	}
	 
	
	private static class SpiderNode implements Serializable, TreeNode {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String name;
		private String data;
		private TreeNode parent;
		private ArrayList<SpiderNode> children;
		
		public SpiderNode(String name, String data) {
			this.name = name;
			this.data = data;
			this.parent = null;
			this.children = new ArrayList<SpiderNode>();
		}
		
		public SpiderNode(SpiderNode self, TreeNode parent) {
			this.name = self.getName();
			this.data = self.getData();
			this.parent = parent;
			this.children = self.children;
		}
		
		public SpiderNode(String name, String data, TreeNode parent) {
			this.name = name;
			this.data = data;
			this.parent = parent;
			this.children = new ArrayList<SpiderNode>();
		}

		public String getData() {
			return data;
		}
		
		public String getName() {
			return name;
		}
		
		public void add(SpiderNode child) {
			children.add(child);
		}

		@Override
		public TreeNode getChildAt(int childIndex) {
			return new SpiderNode(this.children.get(childIndex), this);
		}

		@Override
		public int getChildCount() {
			return this.children.size();
		}

		@Override
		public TreeNode getParent() {
			return this.parent;
		}

		@Override
		public int getIndex(TreeNode node) {
			SpiderNode sn = (SpiderNode) node;
			for (int i = 0; i < this.children.size(); i++) {
				if (sn.equals(this.children.get(i)))
					return i;
			}
			return -1;
		}

		@Override
		public boolean getAllowsChildren() {
			return true;
		}

		@Override
		public boolean isLeaf() {
			return (this.getChildCount() == 0);
		}

		@Override
		public Enumeration<?> children() {
			final int elementCount = this.children.size();
			return new Enumeration<SpiderNode>() {
				int count = 0;

				public boolean hasMoreElements() {
					return this.count < elementCount;
				}

				public SpiderNode nextElement() {
					if (this.count < elementCount) {
						return SpiderNode.this.children.get(this.count++);
					}
					throw new NoSuchElementException("Vector Enumeration");
				}
			};

		}

	}
	
}

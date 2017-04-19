package data;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

public class IntercepterData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DefaultListModel<String> model;

	private ArrayList<Data> data;

	public IntercepterData() {
		model = new DefaultListModel<String>();
		data = new ArrayList<Data>();
	}

	public DefaultListModel<String> getModel() {
		return model;
	}

	public void setNewData(String url, String request, String response) {
		Data newData = new Data(url, request, response);
		data.add(newData);
		model.addElement(url);
	}

	public String getURL(int index) throws IndexOutOfBoundsException {
		return data.get(index).url;
	}

	public String getRequest(int index) throws IndexOutOfBoundsException {
		return data.get(index).request;
	}

	public String getResponse(int index) throws IndexOutOfBoundsException {
		return data.get(index).response;
	}

	private class Data implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String url;
		private String request;
		private String response;

		Data(String url, String request, String response) {
			this.url = url;
			this.request = request;
			this.response = response;
		}
	}
}

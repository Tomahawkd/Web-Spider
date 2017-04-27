package data;

import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultListModel;

/**
 * Data: Store intercepted data
 * 
 * @author Tomahawkd
 */

public class IntercepterData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * List model displayed in GUI
	 * 
	 * @see {@link DefaultListModel}
	 */

	private DefaultListModel<String> model;

	/**
	 * Intercept data array
	 */

	private ArrayList<Data> data;

	public IntercepterData() {
		model = new DefaultListModel<String>();
		data = new ArrayList<Data>();
	}

	/**
	 * Get model.
	 * 
	 * @return default list model
	 * 
	 * @author Tomahawkd
	 */

	public DefaultListModel<String> getModel() {
		return model;
	}

	/**
	 * Add a new data
	 * 
	 * @param url
	 *            Connection URL
	 * @param request
	 *            Connection Request
	 * @param response
	 *            Connection Response
	 * 
	 * @author Tomahawkd
	 */

	public synchronized void add(String url, String request, String response) {
		Data newData = new Data(url, request, response);
		data.add(newData);
		model.addElement(url);
	}

	/**
	 * Get connection URL
	 * 
	 * @param index
	 *            the index of the data
	 * 
	 * @return URL String
	 * 
	 * @throws IndexOutOfBoundsException
	 * 
	 * @author Tomahawkd
	 */

	public String getURL(int index) throws IndexOutOfBoundsException {
		return data.get(index).url;
	}

	/**
	 * Get connection request data
	 * 
	 * @param index
	 *            the index of the data
	 * 
	 * @return Request String
	 * 
	 * @throws IndexOutOfBoundsException
	 * 
	 * @author Tomahawkd
	 */

	public String getRequest(int index) throws IndexOutOfBoundsException {
		return data.get(index).request;
	}

	/**
	 * Get connection response data
	 * 
	 * @param index
	 *            the index of the data
	 * 
	 * @return Response String
	 * 
	 * @throws IndexOutOfBoundsException
	 * 
	 * @author Tomahawkd
	 */

	public String getResponse(int index) throws IndexOutOfBoundsException {
		return data.get(index).response;
	}

	/**
	 * Data preserve class
	 * 
	 * @author Ghost
	 */

	private class Data implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * Connection URL
		 */

		private String url;

		/**
		 * Connection Request
		 */

		private String request;

		/**
		 * Connection Response
		 */

		private String response;

		Data(String url, String request, String response) {
			this.url = url;
			this.request = request;
			this.response = response;
		}
	}
}

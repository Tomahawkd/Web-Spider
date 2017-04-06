package data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Data: Store intercepter's cache data
 * 
 * @author Tomahawkd
 */

public class RequestData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String> request;
	
	public RequestData() {
		request = new ArrayList<String>();
	}
	
	public ArrayList<String> getRequest() {
		return request;
	}
	
	public void setRequest(ArrayList<String> request) {
		this.request = request;
	}
	
	public void addRequestElement(String element) {
		request.add(element);
	}
	
}

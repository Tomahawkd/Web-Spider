package intercepter;

/**
 * Data: Store intercepter's cache data
 * 
 * @author Tomahawkd
 */

public class RequestData {

	private String request;
	
	public RequestData() {
		request = "";
	}
	
	public String getRequest() {
		return request;
	}
	
	public void setRequest(String request) {
		this.request = request;
	}
	
	void addRequestElement(String element) {
		request += element + "\n";
	}
	
}

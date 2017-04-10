package intercepter;


/**
 * Intercepter: Backend section to edit request data and send to the target host
 * 
 * @author Tomahawkd
 */

public class Backend {

	RequestData request;
	
	public Backend(RequestData request) {
		this.request = request;
	}
	
	public String getRequestData() {
		return request.getRequest();
	}
	
	public void setRequestData(String requestData) {
		request.setRequest(requestData);
	}
	
}

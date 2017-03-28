package data;

import java.util.ArrayList;

public class RequestData {

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

package intercepter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Data: Store intercepter's cache data
 * 
 * @author Tomahawkd
 */

public class InterceptData {

	private String host;
	private Method method;
	private LinkedHashMap<String, String> requestHeader;
	private String requestBody;
	private Map<String, List<String>> responseHeader;
	private String responseBody;

	InterceptData() {
		this.host = "";
		requestHeader = new LinkedHashMap<String, String>();
		requestBody = "";
		responseBody = "";
	}

	public String getHost() {
		return host;
	}

	void setHost(String host) {
		this.host = ("http://" + host);
	}

	Method getMethod() {
		return method;
	}

	void setMethod(String methodMessage) {
		if (methodMessage.equals("POST"))
			method = Method.POST;
		else
			method = Method.GET;
	}

	public void setRequest(String header) {
		
	}

	public String getRequest() {
		String request = "";
		
		for(Entry<String, String> mapping : requestHeader.entrySet()) {
			if (mapping.getKey().equals("")) {
				request += (mapping.getValue() + "\r\n");
			} else {
				request += (mapping.getKey() + ": " + mapping.getValue() + "\r\n");
			}
		}
		request += "\r\n";
		
		request += requestBody;
		
		return request;
	}

	Map<String, String> getRequestHeader() {
		return requestHeader;
	}

	void addRequestHeaderElement(String key, String value) {
		requestHeader.put(key, value);
	}

	String getRequestBody() {
		return requestBody;
	}

	void addRequestBodyElement(String line) {
		requestBody += line;
		requestBody += "\n";
	}

	void setResponseHeader(Map<String, List<String>> headers) {
		this.responseHeader = headers;
	}

	String getResponseBody() {
		return requestBody;
	}

	void addResponseBodyElement(String line) {
		requestBody += line;
		requestBody += "\n";
	}

	String getResponse() {

		String response = "";

		// Get response message which should comes the first
				for(String key : responseHeader.keySet()) {
					if(key == null) {	
						for(String value : responseHeader.get(key)){
							response += value;
						}
					}
					
					//Format the header
					if(!response.endsWith("\r\n")) {
						response += "\r\n";
					}
				}
				
				for(String key : responseHeader.keySet()) {
					
					//Skip response message and get the rest
					if(key != null) {
						response += key + ": ";
						for(String value : responseHeader.get(key)){
							response += value + "; ";
						}
					}
					
					//Format the header
					if(!response.endsWith("\r\n")) {
						response += "\r\n";
					}
				}
				
				//Format the header
				if(!response.endsWith("\r\n\r\n")) {
					response += "\r\n";
				}

		response += responseBody;

		return response;
	}

}

package intercepter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Data: Store intercepter's cache data
 * 
 * @author Tomahawkd
 */

class InterceptData {

	private String host;
	private String method;
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
	
	String getHost() {
		return host;
	}
	
	void setHost(String host) {
		this.host = host;
	}
	
	String getMethod() {
		return method;
	}
	
	void setMethod(String method) {
		this.method = method;
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
		
		for(Map.Entry<String, List<String>> mapping : responseHeader.entrySet()) {
			if(mapping.getKey().equals("")) {
				for (String status : mapping.getValue()) {
					response += status;
				}
				response += "\n";
				
			} else {
				response += mapping.getKey();
				response += ": ";
				for (String status : mapping.getValue()) {
					response += status;
				}
				response += "\n";
			}
		}
		
		response += "\n";
		response += responseBody;
		
		return response;
	}
	
}






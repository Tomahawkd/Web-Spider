package intercepter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Data: Store intercepter's cache data
 * 
 * @author Tomahawkd
 */

public class InterceptData {

	private String host;
	private String path;
	private LinkedHashMap<String, String> requestHeader;
	private String requestBody;
	private String response;

	InterceptData() {
		this.host = "";
		requestHeader = new LinkedHashMap<String, String>();
		requestBody = "";
		response = "";
	}

	URL getURL() throws MalformedURLException {
		String urlStr = host + path;
		return new URL(urlStr);
	}
	
	public String getURLString() {
		return (host + path);
	}

	private void setHost(String host) {
		this.host = ("http://" + host);
	}

	private void setPath(String path) {
		this.path = path;
	}

	public void setRequest(String request) {
		String[] requestSet = request.split("\r\n");
		boolean isFirst = true;

		for (String line : requestSet) {
			// Set host
			if (line.contains("Host: ")) {
				setHost(line.split(": ")[1]);
			}

			// Set request method and path
			if (isFirst) {
				setPath(line.split(" ")[1]);
				isFirst = false;

			// Headers
			} else if (line.contains(": ")) {
				String[] header = line.split(": ");
				addRequestHeaderElement(header[0], header[1]);

			// Body
			} else {
				addRequestBodyElement(line);
			}
		}
		
		requestHeader.put("Content-Length", ("" + requestBody.getBytes().length));
	}

	public String getRequest() {
		String request = "";

		for (Entry<String, String> mapping : requestHeader.entrySet()) {
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

	private void addRequestHeaderElement(String key, String value) {
		requestHeader.put(key, value);
	}
	
	private void addRequestBodyElement(String line) {
		requestBody += line;
	}

	String getRequestBody() {
		return requestBody;
	}

	String getResponse() {
		return response;
	}
	
	void setResponse(String response) {
		this.response = response;
	}

}

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

	private String url;
	private String method;
	private LinkedHashMap<String, String> requestHeader;
	private String requestBody;
	private String response;

	InterceptData() {
		this.url = "";
		requestHeader = new LinkedHashMap<String, String>();
		requestBody = "";
		response = "";
	}

	URL getURL() throws MalformedURLException {
		String urlStr = url;
		return new URL(urlStr);
	}

	public String getURLString() {
		return url;
	}

	private void setURL(String url) {
		this.url = url;
	}

	public void setRequest(String request) {

		String url = "";
		String[] requestSet = request.split("\r\n");
		boolean isFirst = true;

		for (String line : requestSet) {
			// Set host
			if (isFirst) {
				url = line.split(" ")[1];
				method = line.split(" ")[0];
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
		
		if(url.startsWith("/")) {
			url = "http://" + requestHeader.get("Host") + url;
		}
		
		if(!url.startsWith("http://")) {
			url = "http://" + requestHeader.get("Host");
		}
		
		setURL(url);
		
		if (method.equals("POST")) {
			requestHeader.put("Content-Length", ("" + requestBody.getBytes().length));
		}
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

		if (method.equals("POST")) {
			request += "\r\n";
			request += requestBody;
		}

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

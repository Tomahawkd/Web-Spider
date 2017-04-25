package intercepter;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map.Entry;

/**
 * Intercepter: Store intercepter's cache data
 * 
 * @author Tomahawkd
 */

public class InterceptData {

	/**
	 * The target URL
	 */

	private String url;

	/**
	 * HTTP request method
	 */

	private String method;

	/**
	 * HTTP request header
	 */

	private LinkedHashMap<String, String> requestHeader;

	/**
	 * HTTP request body
	 */

	private String requestBody;

	/**
	 * The response origin data from the server
	 */

	private byte[] response;

	
	
	
	
	
	
	InterceptData() {
		this.url = "";
		requestHeader = new LinkedHashMap<String, String>();
		requestBody = "";
	}

	/**
	 * Get the URL
	 * 
	 * @return current HTTP request URL
	 * 
	 * @throws MalformedURLException
	 * 
	 * @author Tomahawkd
	 */

	URL getURL() throws MalformedURLException {
		String urlStr = url;
		return new URL(urlStr);
	}

	/**
	 * Get the URL for GUI display
	 * 
	 * @return a URL string
	 * 
	 * @author Tomahawkd
	 */

	String getURLString() {
		return url;
	}

	/**
	 * HTTP request parser
	 * 
	 * @param request
	 * 
	 * @author Tomahawkd
	 */

	void setRequest(String request) {

		String url = "";

		// Split into line
		String[] requestSet = request.split("\r\n");

		// First line is HTTP status which not contains ": "
		boolean isFirst = true;

		for (String line : requestSet) {

			if (isFirst) {

				// Set request method
				requestHeader.put("http request header", line);

				// Get path
				url = line.split(" ")[1];

				// Get method
				method = line.split(" ")[0];
				isFirst = false;

				// Headers
			} else if (line.contains(": ")) {
				String[] header = line.split(": ");
				requestHeader.put(header[0], header[1]);

				// Body
			} else {
				requestBody = line;
			}
		}

		// Get URL in HTTP request
		if (url.startsWith("/")) {
			url = "http://" + requestHeader.get("Host") + url;
		}

		if (!url.startsWith("http://")) {
			url = "http://" + requestHeader.get("Host");
		}

		// Set URL
		this.url = url;

		// Set connection type
		requestHeader.put("Connection", "close");

		// Update content length
		if (method.equals("POST")) {
			requestHeader.put("Content-Length", ("" + requestBody.getBytes().length));
		}
	}

	/**
	 * Get request
	 * 
	 * @return HTTP request
	 * 
	 * @author Tomahawkd
	 */

	String getRequest() {
		String request = "";

		for (Entry<String, String> mapping : requestHeader.entrySet()) {

			// Get the first line
			if (mapping.getKey().equals("http request header")) {
				request += (mapping.getValue() + "\r\n");

				// HTTP request header
			} else {
				request += (mapping.getKey() + ": " + mapping.getValue() + "\r\n");
			}
		}
		request += "\r\n";

		if (method.equals("POST")) {

			// Add request body
			request += requestBody;
		}

		return request;
	}

	/**
	 * Get the response from the server using server.
	 * 
	 * @return response
	 * 
	 * @see {@link Server}
	 * 
	 * @author Tomahawkd
	 */

	byte[] getResponse() {
		return response;
	}

	/**
	 * Set response using backend.
	 * 
	 * @param response
	 * 
	 * @see {@link Backend}
	 * 
	 * @author Tomahawkd
	 */

	void setResponse(byte[] response) {
		this.response = response;
	}

	/**
	 * Get response in String
	 * 
	 * @return Response String
	 * 
	 * @author Tomahawkd
	 */
	
	String getResponseText() {
		String responseText = "";

		responseText = new String(response);

		return responseText;
	}

}

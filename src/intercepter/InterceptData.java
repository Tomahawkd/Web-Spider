package intercepter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;

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
		String charset = "utf-8";

		// Range of Content-Type
		int from = 0;
		int to = 0;
		for (int i = 0; i < response.length; i++) {

			// Search for content-type
			if ((response[i] == 99 || response[i] == 67) && (response[i + 1] == 111 || response[i + 1] == 79)
					&& (response[i + 2] == 110 || response[i + 2] == 78)
					&& (response[i + 3] == 116 || response[i + 3] == 84)
					&& (response[i + 4] == 101 || response[i + 4] == 69)
					&& (response[i + 5] == 110 || response[i + 5] == 78)
					&& (response[i + 6] == 116 || response[i + 6] == 84) && response[i + 7] == 45
					&& (response[i + 8] == 84 || response[i + 8] == 116)
					&& (response[i + 9] == 121 || response[i + 9] == 89)
					&& (response[i + 10] == 112 || response[i + 10] == 80)
					&& (response[i + 11] == 101 || response[i + 11] == 69)) {

				// Start of the
				from = i;
				continue;
			}
			if (from > 0 && to == 0 && response[i] == 13 && response[i + 1] == 10) {
				to = i;
				break;
			}
		}
		// Get Content-Type position
		byte[] headerByte = Arrays.copyOfRange(response, from, to);

		// decode header
		String contentType = new String(headerByte);

		// get charset
		if (contentType.toLowerCase().contains("charset")) {
			charset = contentType.substring(contentType.lastIndexOf("=") + 1);
		}

		/*
		 * Decode response body
		 */

		try {

			// Decode Gzip
			if (charset.contains("gzip")) {
				GZIPInputStream input = new GZIPInputStream(new ByteArrayInputStream(response));
				ByteArrayOutputStream output = new ByteArrayOutputStream();

				int count;
				byte data[] = new byte[1024];
				while ((count = input.read(data, 0, 1024)) != -1) {
					output.write(data, 0, count);
					output.flush();
				}

				// Get response
				responseText = new String(output.toByteArray());
				
				input.close();
				output.close();

				// Other decode
			} else {
				responseText = new String(response, charset);
			}

		} catch (IOException e) {

			// Charset isn't support
			responseText = "Unknown Encode Charset";
		}

		return responseText;
	}

}

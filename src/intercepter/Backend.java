package intercepter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Intercepter: Backend section to edit request data and send to the target host
 * 
 * @author Tomahawkd
 */

public class Backend {

	private InterceptData data;
	private Backend next;

	Backend(InterceptData data) {
		this.data = data;
		next = null;
	}

	void addNext(Backend next) {
		this.next = next;
	}
	
	Backend next() {
		if(next == null) {
			return null;
		} else {
			return next;
		}
	}
	
	public InterceptData getData() {
		return data;
	}

	public void getResponse() {
		switch (data.getMethod()) {
		case GET:
			getGetResponse();
			break;
		case POST:
			getPostResponse();
			break;
		}
	}

	private void getGetResponse() {
		BufferedReader in = null;
		try {

			// Set URL
			URL realUrl = new URL(data.getHost());

			// Open URL connection
			URLConnection connection = realUrl.openConnection();

			// Set request header
			for (Entry<String, String> mapping : data.getRequestHeader().entrySet()) {

				// Abandon method and origin content length
				if (!mapping.getKey().equals("") || !mapping.getKey().equals("Content-Length")) {
					connection.setRequestProperty(mapping.getKey(), mapping.getValue());
				}
			}

			// Set timeout
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);

			// Set connection
			connection.connect();

			// Get response headers
			Map<String, List<String>> map = connection.getHeaderFields();
			data.setResponseHeader(map);

			// Get response body
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				data.addResponseBodyElement(line);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Close stream
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void getPostResponse() {
		PrintWriter out = null;
		BufferedReader in = null;
		try {
			URL url = new URL(data.getHost());

			// Open URL connection
			URLConnection connection = url.openConnection();
			// Set request header
			for (Entry<String, String> mapping : data.getRequestHeader().entrySet()) {

				// Abandon method and origin content length
				if (!mapping.getKey().equals("") || !mapping.getKey().equals("Content-Length")) {
					connection.setRequestProperty(mapping.getKey(), mapping.getValue());
				}
			}

			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);

			// Set POST method
			connection.setDoOutput(true);
			connection.setDoInput(true);

			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(connection.getOutputStream());

			// Post data
			out.print(data.getRequestBody());

			// flush
			out.flush();

			// Get response headers
			Map<String, List<String>> map = connection.getHeaderFields();
			data.setResponseHeader(map);

			// Get response body
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				data.addResponseBodyElement(line);
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			data.addResponseBodyElement("<!DOCTYPE html>");
			data.addResponseBodyElement("<html lang=\"en\">");
			data.addResponseBodyElement("<head>");
			data.addResponseBodyElement("    <meta charset=\"UTF-8\">");
			data.addResponseBodyElement("    <title>Time out</title>");
			data.addResponseBodyElement("</head>");
			data.addResponseBodyElement("<body>");
			data.addResponseBodyElement("<p>TIME OUT</p>");
			data.addResponseBodyElement("</body>");
			data.addResponseBodyElement("</html>");
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Close stream
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

enum Method {
	GET("GET"), POST("POST");

	private String method;

	private Method(String method) {
		this.method = method;
	}

	String getMethod() {
		return method;
	}
}

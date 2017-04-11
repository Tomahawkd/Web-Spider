package spider;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * Spider: Web Spider connection system
 * 
 * @author Tomahawkd
 */

class SpiderConnection {
	
	/**
	 * Validate the current URL
	 */
	
	private boolean urlValidate;
		
	/**
	 * Connection to get response headers.
	 */
	
	// *Cause Jsoup only support a few mount of <code>MINE</code> type, but I want to get all contents' response headers
	private HttpURLConnection connection;
	
	/**
	 * Request headers
	 * 
	 * @see {@link data.SpiderOption}
	 */
	
	private Map<String, String> headers;

	
	
	
	
	
	
	SpiderConnection(Map<String, String> headers) {
		urlValidate = false;
		this.headers = headers;
	}
	
	/**
	 * Set connection URL and property(Default settings)
	 * 
	 * @param url connection URL
	 * 
	 * @author Tomahawkd
	 */
	
	void setUrl(String url) throws IOException {
		try {
			
			//Set URL
			URL link = new URL(url);
			
			//URL initialize success, that is, URL validation is passed
			urlValidate = true;
			connection = (HttpURLConnection) link.openConnection();
			
			//Request method is always GET, No exception will come up
			connection.setRequestMethod("GET");
			
			//Set request headers
			for(String key : headers.keySet()) {
				String value = headers.get(key);
				connection.setRequestProperty(key, value);
			}
			
			//Set connection timeout
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
		} catch (MalformedURLException e) {
			urlValidate = false;
		}
	}
	
	/**
	 * Get the result of the URL validation
	 * 
	 * @return validation result
	 * 
	 * @author Tomahawkd
	 */
	
	boolean Validate() {
		return urlValidate;
	}
	
	/**
	 * Get the response headers as <code>String</code>
	 * 
	 * @return response headers
	 * 
	 * @author Tomahawkd
	 */
	
	String getHeaders() {
		
		//Initialize header string
		String headerStr = "";
		
		//Get headers from connection
		Map<String, List<String>> headers = connection.getHeaderFields();
		
		// Get response message which should comes the first
		for(String key : headers.keySet()) {
			if(key == null) {	
				for(String value : headers.get(key)){
					headerStr += value;
				}
			}
			
			//Format the header
			if(!headerStr.endsWith("\n")) {
				headerStr += "\n";
			}
		}
		
		for(String key : headers.keySet()) {
			
			//Skip response message and get the rest
			if(key != null) {
				headerStr += key + ": ";
				for(String value : headers.get(key)){
					headerStr += value + "; ";
				}
			}
			
			//Format the header
			if(!headerStr.endsWith("\n")) {
				headerStr += "\n";
			}
		}
		
		//Format the header
		if(!headerStr.endsWith("\n\n")) {
			headerStr += "\n";
		}
		return headerStr;
	}
}

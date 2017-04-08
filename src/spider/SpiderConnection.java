package spider;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

class SpiderConnection {
	private boolean urlValidate;
	private URL url;
	private HttpURLConnection connection;
	private Map<String, String> headers;

	SpiderConnection(Map<String, String> headers) {
		urlValidate = false;
		this.headers = headers;
	}
	
	void setUrl(String urlStr) {
		try {
			url = new URL(urlStr);
			urlValidate = true;
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			for(String key : headers.keySet()) {
				String value = headers.get(key);
				connection.setRequestProperty(key, value);
			}
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
		} catch (MalformedURLException e) {
			urlValidate = false;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	boolean Validate() {
		return urlValidate;
	}
	
	String getHeaders() {
		String headerStr = "";
		Map<String, List<String>> headers = connection.getHeaderFields();
				
		for(String key : headers.keySet()) {
			if(key == null) {	
				for(String value : headers.get(key)){
					headerStr += value;
				}
			}
			if(!headerStr.endsWith("\n")) {
				headerStr += "\n";
			}
		}
		
		for(String key : headers.keySet()) {
			if(key != null) {	
				headerStr += key + ": ";
				for(String value : headers.get(key)){
					headerStr += value + "; ";
				}
			}
			if(!headerStr.endsWith("\n")) {
				headerStr += "\n";
			}
		}
		
		if(!headerStr.endsWith("\n\n")) {
			headerStr += "\n";
		}
		return headerStr;
	}
}

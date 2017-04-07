package spider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;


@SuppressWarnings("restriction")
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
	
	void connect() {
		try {
			connection.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	Response getResponse() throws IOException {
		return Jsoup.connect(url.toString()).headers(headers).execute();
	}
	
	String getHeaders() {
		String headerStr = "";
		Map<String, List<String>> headers = connection.getHeaderFields();
		for(String key : headers.keySet()) {
			headerStr += key + ": ";
			for(String value : headers.get(key)){
				headerStr += value + "; ";
			}
			headerStr += "\n";
		}
		
		return headerStr;
	}
	
	String getBody() {
		String body = "";
		try{
			InputStream inputStream = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
			String line = "";
			while ((line = reader.readLine()) != null) {
				body += line;
				body += "\n";
			}
		} catch (IOException e) {}
		return body;
	}
}

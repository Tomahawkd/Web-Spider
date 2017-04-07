package spider;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import data.SpiderData;
import data.SpiderOption;

/**
 * Spider: Web Spider core system
 * 
 * @author Tomahawkd
 */

@SuppressWarnings("restriction")
public class SpiderRun {
	private Response res = null;
	private String body;
	private boolean urlValidate;
	private String currentUrl;
	private SpiderIndex result;
	private boolean suspendFlag;
	private SpiderOption option;
	private SpiderData data;
	
	public SpiderRun(SpiderData data) {
		suspendFlag = false;
		this.data = data;
	}
	
	/**
	 * Get user's option data.
	 * 
	 * @param option user's preference
	 * 
	 * @see {@link SpiderOption}
	 * 
	 * @author Tomahawkd
	 */
	
	public void setOption(SpiderOption option) {
		this.option = option;
	}
	
	/**
	 * Start web spider operation.
	 * 
	 * @throws nullHostException throws while the host has no parameters
	 * 
	 * @author Tomahawkd
	 */
	
	public void start() throws nullHostException {
		if (option.getHost().equals("")) throw new nullHostException();
		suspendFlag = false;
		currentUrl = option.getProtocol() + "://" + option.getHost() + ":" + option.getPort();
		result = new SpiderIndex(option.getHost());
		result.addNewUrl(currentUrl);
		data.setHost(option.getProtocol() + "://" + option.getHost());
		getHerfHtml();
	}
	
	/**
	 * Resume a suspend spider operation.
	 * 
	 * @author Tomahawkd
	 */
	
	public void resume() {
		suspendFlag = false;
		getHerfHtml();
	}
	
	/**
	 * Stop a spider operation.
	 * 
	 * @author Tomahawkd
	 */
	
	public void stop() {
		suspendFlag = true;
	}
	
	/**
	 * Test the host connection.
	 * 
	 * @param baseURL
	 * 
	 * @author Tomahawkd
	 */
	
	private void connect(String baseURL){
		try {
			res = Jsoup.connect(baseURL).headers(option.getHeaders()).execute();
			body = "HTTP/1.1 " + res.statusCode() + " " + res.statusMessage() + "\n";
			body += mapToString(res.headers());
			body += "\n\n";
			body += res.body();
			urlValidate = true;
		} catch (IOException e) {
			urlValidate = false;
			String[] path = searchFromNode(currentUrl);
			data.add(path, path[path.length - 1], "");
		} 
	}
	
	boolean getUrlValid(){
		return urlValidate;
	}
	
	/**
	 * Rescue to get html
	 * 
	 * @author Tomahawkd
	 */
	
	private void getHerfHtml(){
		
		if (!suspendFlag) {
			
//			data.add(searchFromNode(currentUrl), searchFromNode(currentUrl)[searchFromNode(currentUrl).length -1], "");
			connect(currentUrl);
			
			if (urlValidate) {
				String[] path = searchFromNode(currentUrl);
				data.add(path, path[path.length - 1], body);
				
				try {
					Document doc = res.parse();	
					Elements media = doc.select("[src]");
					for (Element src : media) {
						if (src.attr("abs:src").contains(option.getHost())) {
							currentUrl = src.attr("abs:src");
							if (!currentUrl.equals("") && !result.compareExistUrl(currentUrl)) {
								result.addNewUrl(currentUrl);
								urlValidate = false;
								getHerfHtml();
							}
						}
					}

					Elements imports = doc.select("*[href]");
					for (Element link : imports) {
						if (link.attr("abs:href").contains(option.getHost())) {
							currentUrl = link.attr("abs:href");
							if (!currentUrl.equals("") && !result.compareExistUrl(currentUrl)) {
								result.addNewUrl(currentUrl);
								urlValidate = false;
								getHerfHtml();
							}
						}
					}
				} catch (IOException e) {}
			} 
		}
		
	}
	
	
	/**
	 * Get the path of the file in the host file system.
	 * 
	 * @param url Absolute url
	 * 
	 * @return array contains path
	 * 
	 * @author Tomahawkd
	 */
	
	private String[] searchFromNode(String url) {
		url = url.replace(option.getProtocol() + "://", "");
		return url.split("/");
	}
	
	private String mapToString(Map<String, String> header) {
		String str = "";
		for(String key : header.keySet()) {
			str += key + ": " + header.get(key) + "\n";
		}
		
		return str;
	}
}

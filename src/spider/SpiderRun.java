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
	private Document doc = null;
	private String body;
	private boolean urlValidate;
	private String currentUrl;
	private String hostFilter;
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
		currentUrl = option.getProtocol() + "://" + option.getHost() + ":" + option.getPort() + "/";
		hostFilter = option.getProtocol() + "://" + option.getHost();
		result = new SpiderIndex(hostFilter);
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
			Response res = Jsoup.connect(baseURL).headers(option.getHeaders()).execute();
			body = res.statusCode() + " " + res.statusMessage() + "\n";
			body += mapToString(res.headers());
			body += "\n\n";
			body += res.body();
			urlValidate = true;
		} catch (IOException e) {
			urlValidate = false;
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
				
				data.add(searchFromNode(currentUrl), searchFromNode(currentUrl)[searchFromNode(currentUrl).length -1], body);
				
				try {
					doc = Jsoup.connect(currentUrl).headers(option.getHeaders()).get();				
					Elements media = doc.select("[src]");
					for (Element src : media) {
						if (src.attr("abs:src").contains(hostFilter)) {
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
						if (link.attr("abs:href").contains(hostFilter)) {
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

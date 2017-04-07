package spider;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

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
	
	private boolean suspendFlag;
	
	private SpiderIndex result;
	private SpiderOption option;
	private SpiderData data;
	private SpiderConnection connection;
	
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
		result = new SpiderIndex(option.getProtocol() + "://" + option.getHost());
		connection = new SpiderConnection(option.getHeaders());
		result.setURLMap(crawlLinks(result.getURLMap()));
		getContents(result.getURLMap());
	}
	
	/**
	 * Stop web spider operation.
	 * 
	 * @author Tomahawkd
	 */
	
	public void stop() {
		suspendFlag = true;
	}
	
	/**
	 * Rescue to get html links
	 * 
	 * @param urlList
	 * 
	 * @return 
	 * 
	 * @author Tomahawkd
	 */
	
	private Map<String, Boolean> crawlLinks(Map<String, Boolean> urlMap){
		
		Map<String, Boolean> newURLMap = new LinkedHashMap<String, Boolean>();
		
		if (!suspendFlag) {
			
			for (Map.Entry<String, Boolean> mapping : urlMap.entrySet()) {
//				System.out.println("link:" + mapping.getKey() + "--------check:" + mapping.getValue());
				
				if (!mapping.getValue()) {
					String url = mapping.getKey();
					urlMap.replace(url, false, true);
					try {
						Document doc = Jsoup.connect(url).headers(option.getHeaders()).get();
						
						Elements imports = doc.select("*[href]");
						for (Element link : imports) {
							if (link.attr("abs:href").contains(option.getHost())) {
								String newUrl = link.attr("abs:href");
								if (!newUrl.equals("") && !urlMap.containsKey(newUrl) && !newURLMap.containsKey(newUrl)) {
									newURLMap.put(newUrl, false);
								}
							}
						}
						Elements media = doc.select("[src]");
						for (Element src : media) {
							if (src.attr("abs:src").contains(option.getHost())) {
								String newUrl = src.attr("abs:src");
								if (!newUrl.equals("") && !urlMap.containsKey(newUrl) && !newURLMap.containsKey(newUrl)) {
									newURLMap.put(newUrl, false);
								}
							}
						}
					} catch (IOException e) {}
				}
			}
			if (!newURLMap.isEmpty()) {
				urlMap.putAll(newURLMap);
				urlMap.putAll(crawlLinks(urlMap));
			}
		}
		return urlMap;
		
	}
	
	void getContents(Map<String, Boolean> urlMap) {
		for(String url : urlMap.keySet()) {
			connection.setUrl(url);
			if(connection.Validate()) {
				String content = "";
				content += connection.getHeaders();
				try {
					content += Jsoup.connect(url).headers(option.getHeaders()).get().data();
				} catch (IOException e) {}
				String path[] = searchFromNode(url);
				if (!content.equals("")) {
					data.add(path, path[path.length - 1], content);
				}
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
		if(url.startsWith("http://")) {
			url = url.replace("http://", "http:/");
		} else {
			url = url.replace("https://", "https:/");
		}
		return url.split("/");
	}

}

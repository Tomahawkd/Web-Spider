package spider;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Interface.SiteMapPanel;
import Interface.SpiderPanel;
import data.SpiderData;
import data.SpiderOption;

/**
 * Spider: Web Spider core system
 * 
 * @author Tomahawkd
 */

@SuppressWarnings("restriction")
public class SpiderRun {
	
	/**
	 * flag to guide the spider to start or stop.
	 */
	
	private boolean suspendFlag;
	
	/**
	 * flag to monitor the map is being editing.
	 */
	
	private boolean isEditing;
	
	private Map<String, Boolean> accessedURLs;
	
	/**
	 * URL number in queue to access content.
	 */
	
	private int queue;
	
	/**
	 * Request number has already sent.
	 */
	
	private int requestCounter;
	
	/**
	 * Queue system.
	 * 
	 * @see {@link SpiderIndex}
	 */
	
	private SpiderIndex result;
	
	/**
	 * User's preference.
	 * 
	 * @see {@link SpiderOption}
	 */
	
	private SpiderOption option;
	
	/**
	 * Data write-in operation.
	 * 
	 * @see {@link SpiderData}
	 */
	
	private SpiderData data;
	
	/**
	 * Get content headers.
	 * 
	 * @see {@link SpiderConnection}
	 */
	
	private SpiderConnection connection;
	
	/**
	 * Update queue indicator.
	 * Use <code>refreshQueue</code> method to update indicator.
	 * 
	 * @see {@link SpiderPanel}
	 */
	
	private SpiderPanel spiderPanel;
	
	/**
	 * Update site map tree.
	 * Use <code>updateData</code> method to update tree.
	 * 
	 * @see {@link SiteMapPanel}
	 */
	
	private SiteMapPanel siteMapPanel;
	
	
	
	
	public SpiderRun(SpiderData data, SpiderPanel spiderPanel) {
		suspendFlag = false;
		isEditing = false;
		this.data = data;
		this.spiderPanel = spiderPanel;
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
	
	public void setSiteMap(SiteMapPanel siteMapPanel) {
		this.siteMapPanel = siteMapPanel;
	}
	
	/**
	 * Start web spider operation.
	 * 
	 * @throws nullHostException throws while the host has no parameters
	 * 
	 * @author Tomahawkd
	 */
	
	public void start() throws nullHostException {
		
		//Check if the host is empty
		if (option.getHost().equals("")) throw new nullHostException();
		
		//Set up monitor
		suspendFlag = false;
		isEditing = false;
		
		accessedURLs = new LinkedHashMap<String, Boolean>();
		
		//Put the host in to the queue map
		result = new SpiderIndex(option.getProtocol() + "://" + option.getHost());
		
		//Counter initialize value
		queue = 1;
		requestCounter = 0;
		
		//Set request headers in spider connection operation
		connection = new SpiderConnection(option.getHeaders());
		
		//Get the content in queue
		new Thread(new Runnable() {
			public void run() {
				while(!suspendFlag){
					if(!isEditing) getContents(result.getURLMap());
				}
			}
		}).start();
		
		//Queuing the URL map
		result.setURLMap(crawlLinks(result.getURLMap()));
		
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
	 * Rescue to get HTML links. Wide first.
	 * 
	 * @param urlList
	 * 
	 * @return new queue map contents all URLs
	 * 
	 * @author Tomahawkd
	 */
	
	private Map<String, Boolean> crawlLinks(Map<String, Boolean> urlMap){
		
		//New URLs queue map
		Map<String, Boolean> newURLMap = new LinkedHashMap<String, Boolean>();
		
		if (!suspendFlag) {
			
			for (Map.Entry<String, Boolean> mapping : urlMap.entrySet()) {
				
				//Check if is already accessed
				if (!mapping.getValue()) {
					String url = mapping.getKey();
					
					//Set to already accessed
					urlMap.replace(url, false, true);
					try {
						
						/*	Throws exception if the URL isn't support to parse.
						 * 	Consider it as a end of the current index.
						 */
						Document doc = Jsoup.connect(url).headers(option.getHeaders()).get();
						
						//Page source like HTML etc.
						Elements imports = doc.select("*[href]");
						for (Element link : imports) {
							
							//Filter non-current host URLs
							if (link.attr("abs:href").contains(option.getHost())) {
								
								//Get the absolute URL
								String newUrl = link.attr("abs:href");
								
								//Filter empty URL and exist URL
								if (!newUrl.equals("") && !urlMap.containsKey(newUrl) && !newURLMap.containsKey(newUrl)) {
									newURLMap.put(newUrl, false);
								}
							}
						}
						
						//Media source like image etc.
						Elements media = doc.select("[src]");
						for (Element src : media) {
							
							//Filter non-current host URLs
							if (src.attr("abs:src").contains(option.getHost())) {
								
								//Get the absolute URL
								String newUrl = src.attr("abs:src");

								//Filter empty URL and exist URL
								if (!newUrl.equals("") && !urlMap.containsKey(newUrl) && !newURLMap.containsKey(newUrl)) {
									newURLMap.put(newUrl, false);
								}
							}
						}
						
					} catch (IOException e) {}
				}
			}
			//if the new queue map is not empty then rescue
			if (!newURLMap.isEmpty()) {
				
				isEditing = true;
				
				//Save URLs to queue map in SpiderIndex class
				urlMap.putAll(newURLMap);
				
				isEditing = false;
				
				//Update queue number
				queue = result.getQueue();
				spiderPanel.refreshQueue(queue);
				
				//Rescue section
				
				Map<String, Boolean> childMap = crawlLinks(urlMap);
				
				isEditing = true;

				urlMap.putAll(childMap);
				
				isEditing = false;
			}
		}
		return urlMap;
		
	}
	
	/**
	 * Get URLs' contents and save data to {@link SpiderData}
	 * 
	 * @param urlMapSet Queue map
	 * 
	 * @author Tomahawkd
	 */
	
	void getContents(Map<String, Boolean> urlMapSet) {
		
		Map<String, Boolean> urlMap = new LinkedHashMap<String, Boolean>();
		urlMap.putAll(urlMapSet);
		
		for(Map.Entry<String, Boolean> mapping : urlMap.entrySet()) {
			
			if(!accessedURLs.containsKey(mapping.getKey())) {
				String url = mapping.getKey();
			
				connection.setUrl(url);
				if(connection.Validate()) {
				
					String content = "";
					content += connection.getHeaders();
				
					queue--;
					requestCounter++;
					spiderPanel.refreshQueue(queue);
					spiderPanel.refreshRequestCounter(requestCounter);
				
					siteMapPanel.updateData();
				
					try {
						content += Jsoup.connect(url).headers(option.getHeaders()).get().data();
					} catch (IOException e) {}
				
					String path[] = toPathArray(url);
				
					if (!content.equals("")) {
						data.add(path, content);
					}
					accessedURLs.put(mapping.getKey(), mapping.getValue());
				}
			}
		}
	}
	
	
	/**
	 * Get the path of the file in the host file system.
	 * 
	 * @param url Absolute URL
	 * 
	 * @return array contains path
	 * 
	 * @author Tomahawkd
	 */
	
	private String[] toPathArray(String url) {
		
		String protocol = "";
		
		//To get url's protocol
		if(url.startsWith("http://")) {
			url = url.replace("http://", "");
			protocol = "http://";
		} else {
			url = url.replace("https://", "");
			protocol = "https://";
		}
		
		String[] path = url.split("/");
		
		path[0] = protocol + path[0];
		
		return path;
	}

}

package spider;

import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import Interface.SiteMapPanel;
import Interface.SpiderPanel;
import data.FileIO;
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
	 * Flag to guide the spider to start or stop.
	 */
	
	private boolean suspendFlag;
	
	/**
	 * Flag to indicate the activation of the host-only filter.
	 */
	
	private boolean isHostOnly;
	
	/**
	 * URL counter in queue to access content.
	 */
	
	private int queueCounter;
	
	/**
	 * Request counter has already sent.
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
	 * Spider index write-in operation.
	 * 
	 * @see {@link FileIO}
	 */
	
	private FileIO file;
	
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
	
	
	
	
	
	
	
	public SpiderRun(FileIO file, SpiderPanel spiderPanel) {
		suspendFlag = false;
		this.file = file;
		this.spiderPanel = spiderPanel;
		this.data = file.getDataSet().getSpiderData();
		this.result = file.getDataSet().getSpiderIndex();
		this.queueCounter = file.getDataSet().getQueueCounter();
		this.requestCounter = file.getDataSet().getRequestCounter();
	}
	
	/**
	 * Update all spider data after loading a file.
	 * 
	 * @author Tomahawkd
	 */
	
	public void updateData() {
		this.data = file.getDataSet().getSpiderData();
		this.result = file.getDataSet().getSpiderIndex();
		this.queueCounter = file.getDataSet().getQueueCounter();
		this.requestCounter = file.getDataSet().getRequestCounter();
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
		isHostOnly = option.isHostOnly();
	}
	
	/**
	 * Get site map panel to refresh the contains
	 * 
	 * @param siteMapPanel
	 */
	
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
				
		//Put the host in to the queue map
		result = new SpiderIndex(option.getProtocol() + "://" + option.getHost());
		
		//Save the index to DataSet class
		file.getDataSet().setSpiderIndex(result);
		
		//Counter initialize value
		queueCounter = 1;
		requestCounter = 0;
		
		//Set request headers in spider connection operation
		connection = new SpiderConnection(option.getHeaders());
		
		//Get the content in queue
		new Thread(new Runnable() {
			public void run() {
				while(!suspendFlag){
					getContents(result.getURLMap());
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
	 * A monitor indicate is the first time running the operation 
	 * 
	 * @return a boolean indicate is the first time running the operation 
	 * 
	 * @author Tomahawkd
	 */
	
	public boolean isFirstRun() {
		boolean flag = true;
		
		//Set monitor
		this.queueCounter = file.getDataSet().getQueueCounter();
		this.requestCounter = file.getDataSet().getRequestCounter();
		
		if(requestCounter != 0) {
			flag = false;
		}
		
		return flag;
	}
	
	/**
	 * Resume web spider operation.
	 * 
	 * @author Tomahawkd
	 */
	
	public void resume() {
		
		//Set monitor
		suspendFlag = false;
		
		//Set request headers in spider connection operation
		connection = new SpiderConnection(option.getHeaders());
		
		//Get the content in queue
		new Thread(new Runnable() {
			public void run() {
				while(!suspendFlag){
					getContents(result.getURLMap());
				}
			}
		}).start();
		
		//Queuing the URL map
		result.setURLMap(crawlLinks(result.getURLMap()));
	}
	
	/**
	 * Rescue to get HTML links. Wide algorithm first.
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
		
		if(!suspendFlag) {
					
			try {
				for (Map.Entry<String, Boolean> mapping : urlMap.entrySet()) {
					
					//Check if is already accessed
					if (!mapping.getValue()) {
						String url = mapping.getKey();
					
						//Set to already accessed
						urlMap.replace(url, false, true);
						try {
						
						   /*	Throws exception if the URL isn't support to parse.
						 	* 	Considerate it as the end of the current index.
						 	*/
							Document doc = Jsoup.connect(url).headers(option.getHeaders()).get();
						
							//Page source like HTML etc.
							Elements imports = doc.select("*[href]");
							for (Element link : imports) {
							
								//Filter non-current host URLs
								if (!isHostOnly || link.attr("abs:href").contains(option.getHost())) {
								
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
								if (!isHostOnly || src.attr("abs:src").contains(option.getHost())) {
								
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
			} catch (ConcurrentModificationException e) {}
			
			//if the new queue map is not empty then rescue
			if (!newURLMap.isEmpty()) {
			
				//Save URLs to queue map in SpiderIndex class
				urlMap.putAll(newURLMap);
				
				//Update queue number
				queueCounter = result.getQueue();
				spiderPanel.refreshQueue(queueCounter);
		
		   /*	
			*  Rescue section
			*/	
			
				Map<String, Boolean> childMap = crawlLinks(urlMap);

				//Save URLs to queue map in SpiderIndex class
				urlMap.putAll(childMap);
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
		
		try {
			
			//Copy URL map to a new map to prevent editing while reading
			Map<String, Boolean> urlMap = new LinkedHashMap<String, Boolean>();
			urlMap.putAll(urlMapSet);
			
			for(Map.Entry<String, Boolean> mapping : urlMap.entrySet()) {
				
				//Access the new URLs
				if(!result.getAccessedURLs().containsKey(mapping.getKey())) {
					String url = mapping.getKey();
					
					try{
						//Set connection URL to get response headers, ignore it when got IOException
						connection.setUrl(url);
					
						//Validate the connection
						if(connection.Validate()) {
					
							//Data initialize
							String content = "";
						
							//Write the response headers into data
							content += connection.getHeaders();
					
							//Counters counts
							queueCounter--;
							requestCounter++;
						
							//Refresh the UI components
							spiderPanel.refreshQueue(queueCounter);
							spiderPanel.refreshRequestCounter(requestCounter);
							siteMapPanel.updateData();
					
							try {
							
								/*	Throws exception if the URL isn't support to parse.
								 * 	This kind of page whose data is only have response headers
								 */
								content += Jsoup.connect(url).headers(option.getHeaders()).get().data();
							
							} catch (IOException e) {}
					
							//Get the path array prepared to insert into the nodes
							String path[] = toPathArray(url);
						
							//Filter the timeout pages
							if (!content.equals("")) {
								data.add(path, content);
							}
						}	
					} catch (IOException e) {}
					
						//insert URL into already accessed URLs map
						result.addAccessedURL(mapping.getKey(), mapping.getValue());
				}
			}
		} catch (ConcurrentModificationException e) {}
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
		
		//Get url's protocol
		if(url.startsWith("http://")) {
			url = url.replace("http://", "");
			protocol = "http://";
		} else {
			url = url.replace("https://", "");
			protocol = "https://";
		}
		
		String[] path = url.split("/");
		
		//Put the protocol and the host together
		path[0] = protocol + path[0];
		
		return path;
	}

}

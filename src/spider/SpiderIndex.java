package spider;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Data Index: URL Queue map. Cache only.
 * 
 * @author Tomahawkd
 */

public class SpiderIndex implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Already accessed URLs map by <code>getContents</code> method.
	 */
	
	private Map<String, Boolean> accessedURLs;
	
	/**
	 * All URLs in queue
	 */
	
	private Map<String, Boolean> urlMap;
	
	
	
	
	
	
	
	
	
	
	
	public SpiderIndex(String baseURL){
		urlMap = new LinkedHashMap<String, Boolean>();
		urlMap.put(baseURL, false);
		accessedURLs = new LinkedHashMap<String, Boolean>();
	}
	
	/**
	 * Add a new URL to queue
	 * 
	 * @param newUrl a new URL gotten from <code>SpiderRun</code> class
	 * 
	 * @see {@link SpiderRun}
	 * 
	 * @author Tomahawkd
	 */
	
	void addNewUrl(String newUrl){
		urlMap.put(newUrl, false);
	}
	
	/**
	 * Get URL queue map.
	 * 
	 * @return URL queue map
	 * 
	 * @author Tomahawkd
	 */
	
	Map<String, Boolean> getURLMap() {
		return urlMap;
	}
	
	/**
	 * Set a new URL queue map.
	 * 
	 * @param urlMap URL queue map
	 * 
	 * @author Tomahawkd
	 */
	
	void setURLMap(Map<String, Boolean> urlMap) {
		this.urlMap = urlMap;
	}
	
	/**
	 * Get URL queue map queue.
	 * 
	 * @return Queue length
	 * 
	 * @author Tomahawkd
	 */
	
	int getQueue() {
		return urlMap.size();
	}

	/**
	 * Get a map of URLs which has already got its contents
	 * 
	 * @return already accessed URLs
	 * 
	 * @author Tomahawkd
	 */
	
	Map<String, Boolean> getAccessedURLs() {
		return accessedURLs;
	}

	/**
	 * Add a new URL which has already got its contents
	 * 
	 * @param key The URL
	 * @param value A boolean indicates if it has been parsed to get new URLs
	 * 
	 * @author Tomahawkd
	 */
	
	void addAccessedURL(String key, boolean value) {
		this.accessedURLs.put(key, value);
	}
}

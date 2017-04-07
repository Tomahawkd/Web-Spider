package spider;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Data Index: This is the index of the site map. Cache only.
 * 
 * @author Tomahawkd
 */

public class SpiderIndex {
	private Map<String, Boolean> urlMap;
	
	public SpiderIndex(String baseURL){
		urlMap = new LinkedHashMap<String, Boolean>();
		urlMap.put(baseURL, false);
	}
	
	/**
	 * Add a new url to index
	 * 
	 * @param newUrl a new url gotten from <code>SpiderRun</code> class
	 * 
	 * @see {@link SpiderRun}
	 * 
	 * @author Tomahawkd
	 */
	
	void addNewUrl(String newUrl){
		urlMap.put(newUrl, false);
	}
	
	Map<String, Boolean> getURLMap() {
		return urlMap;
	}
	
	void setURLMap(Map<String, Boolean> urlMap) {
		this.urlMap = urlMap;
	}
}

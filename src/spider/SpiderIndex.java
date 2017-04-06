package spider;

import java.util.ArrayList;

/**
 * Data Index: This is the index of the site map. Cache only.
 * 
 * @author Tomahawkd
 */

public class SpiderIndex {
	private ArrayList<String> existUrl;
	
	public SpiderIndex(String baseURL){
		existUrl = new ArrayList<>();
		existUrl.add(baseURL);
	}
	
	/**
	 * Compare with exist url to comfirm if it is already exist.
	 * 
	 * @param url Absolute url
	 * 
	 * @return a boolean stands its existence
	 * 
	 * @author Tomahawkd
	 */
	
	boolean compareExistUrl(String url){
		boolean flag = false;
		for(String exist : existUrl){
			if(url.equals(exist)) flag = true;
		}
		return flag;
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
		existUrl.add(newUrl);
	}
}

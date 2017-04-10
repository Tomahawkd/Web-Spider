package data;

import java.io.Serializable;

import spider.SpiderIndex;

/**
 * Data: The data class include all class data to be saved to a specific file.
 * 
 * @author Tomahawkd
 *
 */

public class DataSet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * {@link IntercepterOption}
	 */
	
	private IntercepterOption intercepter;
	
	/**
	 * {@link SpiderData}
	 */
	
	private SpiderData spiderData;
	
	/**
	 * {@link SpiderOption}
	 */
	
	private SpiderOption spiderOption;
	
	
	/**
	 * 
	 */
	
	private SpiderIndex spiderIndex;
	
	/**
	 * URL counter in queue to access content.
	 */
	
	private int queueCounter;
	
	/**
	 * Request counter has already sent.
	 */
	
	private int requestCounter;
	
	
	
	
	
	
	
	
	
	
	public DataSet() {
		intercepter = new IntercepterOption();
		spiderData = new SpiderData();
		spiderOption = new SpiderOption();
		spiderIndex = new SpiderIndex("");
		queueCounter = 0;
		requestCounter = 0;
	}

	/**
	 * Get user's intercepter preference
	 * 
	 * @see {@link IntercepterOpiton}
	 * 
	 * @return Intercepter option
	 * 
	 * @author Tomahawkd
	 */

	public IntercepterOption getIntercepterOption() {
		return intercepter;
	}

	/**
	 * Get spider data
	 * 
	 * @see {@link SpiderData}
	 * 
	 * @return spider data node
	 * 
	 * @author Tomahawkd
	 */

	public SpiderData getSpiderData() {
		return spiderData;
	}

	/**
	 * Get user's spider preference
	 * 
	 * @see {@link SpiderOption}
	 * 
	 * @return spider option
	 * 
	 * @author Tomahawkd
	 */
	
	public SpiderOption getSpiderOption() {
		return spiderOption;
	}
	
	/**
	 * Get spider queue
	 * 
	 * @see {@link SpiderData}
	 * 
	 * @return spider data map
	 * 
	 * @author Tomahawkd
	 */

	public SpiderIndex getSpiderIndex() {
		return spiderIndex;
	}
	
	/**
	 * Set spider queue
	 * 
	 * @see {@link SpiderData}
	 * 
	 * @return spider data map
	 * 
	 * @author Tomahawkd
	 */
	
	public void setSpiderIndex(SpiderIndex spiderIndex) {
		this.spiderIndex = spiderIndex;
	}

	
	
	public int getQueueCounter() {
		return queueCounter;
	}

	public void setQueueCounter(int queueCounter) {
		this.queueCounter = queueCounter;
	}

	public int getRequestCounter() {
		return requestCounter;
	}

	public void setRequestCounter(int requestCounter) {
		this.requestCounter = requestCounter;
	}
	
	
}

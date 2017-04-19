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
	
	private IntercepterOption intercepterOption;
	
	/**
	 * {@link SpiderData}
	 */
	
	private SpiderData spiderData;
	
	/**
	 * {@link SpiderOption}
	 */
	
	private SpiderOption spiderOption;
	
	/**
	 * {@link SpiderIndex}
	 */
	
	private SpiderIndex spiderIndex;
	
	private IntercepterData intercepterData;
	
	/**
	 * URL counter in queue to access content.
	 */
	
	private int queueCounter;
	
	/**
	 * Request counter has already sent.
	 */
	
	private int requestCounter;
	
	
	
	
	
	
	
	
	
	
	
	
	public DataSet() {
		intercepterOption = new IntercepterOption();
		intercepterData = new IntercepterData();
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
		return intercepterOption;
	}
	
	/**
	 * Get user's intercepter data
	 * 
	 * @see {@link IntercepterData}
	 * 
	 * @return Intercepter data
	 * 
	 * @author Tomahawkd
	 */

	public IntercepterData getIntercepterData() {
		return intercepterData;
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

	/**
	 * Get the queue count number
	 * 
	 * @return queue count
	 * 
	 * @author Tomahawkd
	 */
	
	public int getQueueCounter() {
		return queueCounter;
	}

	/**
	 * Set a new count number while the spider is operating
	 * 
	 * @param queueCounter
	 * 
	 * @author Tomahawkd
	 */
	
	public void setQueueCounter(int queueCounter) {
		this.queueCounter = queueCounter;
	}

	/**
	 * Get the request count number
	 * 
	 * @return sent-request count
	 * 
	 * @author Tomahawkd
	 */
	
	public int getRequestCounter() {
		return requestCounter;
	}

	/**
	 * Set a new count number while the spider is operating
	 * 
	 * @param requestCounter
	 * 
	 * @author Tomahawkd
	 */
	
	public void setRequestCounter(int requestCounter) {
		this.requestCounter = requestCounter;
	}
	
	
}

package data;

import java.io.Serializable;

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
	private RequestData requestData;
	private IntercepterOption intercepter;
	private SpiderData spiderData;
	private SpiderOption spiderOption;
	
	public DataSet() {
		requestData = new RequestData();
		intercepter = new IntercepterOption();
		spiderData = new SpiderData();
		spiderOption = new SpiderOption();
	}

	/**
	 * Get intercept request data
	 * 
	 * @see RequestData
	 * 
	 * @return Intercepted request data
	 * 
	 * @author Tomahawkd
	 */
	
	public RequestData getRequestData() {
		return requestData;
	}

	/**
	 * Get user's intercepter preference
	 * 
	 * @see IntercepterOpiton
	 * 
	 * @return Intercepter option
	 */

	public IntercepterOption getIntercepterOption() {
		return intercepter;
	}

	/**
	 * Get spider data main node
	 * 
	 * @see SpiderData
	 * 
	 * @return spider data node
	 */

	public SpiderData getSpiderData() {
		return spiderData;
	}

	/**
	 * Get user's spider preference
	 * 
	 * @see SpiderOption
	 * 
	 * @return spider option
	 */
	
	public SpiderOption getSpiderOption() {
		return spiderOption;
	}
	
	
	
	
}

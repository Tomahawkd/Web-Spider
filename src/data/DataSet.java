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
	
	
	
	
	
	
	
	
	public DataSet() {
		intercepter = new IntercepterOption();
		spiderData = new SpiderData();
		spiderOption = new SpiderOption();
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
	 * Get spider data main node
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
	
	
	
	
}

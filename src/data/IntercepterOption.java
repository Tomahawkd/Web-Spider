package data;

import java.io.Serializable;

/**
 * Option: Store intercepter option data
 * 
 * @author Tomahawkd
 */

public class IntercepterOption implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Intercepter port
	 */
	
	private int port;
	
	/**
	 *  Constructor with non-parameter.
	 *  
	 *  All option use preset.
	 *  
	 *  @author Tomahawkd
	 *  
	 */
	
	public IntercepterOption() {
		port = 8080;
	}
	
	/**
	 * Constructor with parameters.
	 * 
	 * Using it to load user's preference.
	 * 
	 * @param port Intercepter listener port preference.
	 * 
	 * @author Tomahawkd
	 */
	
	public IntercepterOption(int port) {
		this.port = port;
	}

	/**
	 * Get the port preference correspond to Intercepter.
	 * 
	 * @return port
	 * 
	 * @author Tomahawkd
	 */
	
	public int getPort() {
		return port;
	}

	/**
	 * Set port preference correspond to Intercepter.
	 * 
	 * @param port a port to listen and intercept
	 * 
	 * @author Tomahawkd
	 */
	
	public void setPort(int port) {
		this.port = port;
	}

	
}

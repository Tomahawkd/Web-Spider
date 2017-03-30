package data;

/**
 * Option: Store intercepter option data
 * 
 * @author Ghost
 */

public class IntercepterOption {
	
	private int port;
	
	/**
	 *  Constructor with non-parameter.
	 *  
	 *  All option use preset.
	 *  
	 *  @author Ghost
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
	 * @param port	Intercepter listener port preference.
	 * 
	 * @author Ghost
	 */
	
	public IntercepterOption(int port) {

		this.port = port;
		
	}

	/**
	 * Get the port preference correspond to Intercepter.
	 * 
	 * @return port
	 * 
	 * @author Ghost
	 */
	
	public int getPort() {
		return port;
	}

	/**
	 * Set port preference correspond to Intercepter.
	 * 
	 * @param portOption a port to listen and intercept
	 * 
	 * @author Ghost
	 */
	
	public void setPort(int port) {
		this.port = port;
	}

	
}

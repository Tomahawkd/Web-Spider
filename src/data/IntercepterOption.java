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
	 * Constructor with non-parameter.
	 * 
	 * All option use preset.
	 * 
	 * @author Tomahawkd
	 * 
	 */

	public IntercepterOption() {
		port = 8080;
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
	 * @param port
	 *            a port to listen and intercept
	 * 
	 * @throws NumberFormatException
	 *             port is not in bound ( 0 < port < 65535 )
	 * 
	 * @author Tomahawkd
	 */

	public void setPort(int port) throws NumberFormatException {

		// Port is not accepted by the system
		if (port < 0 || port > 65535)
			throw new NumberFormatException("Port Invalid");

		this.port = port;
	}

}

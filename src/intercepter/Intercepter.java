package intercepter;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import Interface.IntercepterPanel;
import data.FileIO;

/**
 * Intercepter: Main Execute Class
 * 
 * @author Tomahawkd
 */

public class Intercepter {

	/**
	 * File handler
	 * 
	 * @see {@link FileIO}
	 */

	private FileIO file;

	/**
	 * Thread Pool
	 */

	private ThreadPool threadPool;

	/**
	 * Intercepter panel to update data
	 * 
	 * @see {@link IntercepterPanel}
	 */

	private IntercepterPanel panel;

	/**
	 * Server
	 * 
	 * @see {@link ServerSocket}
	 */

	private ServerSocket server;

	public Intercepter(FileIO file, IntercepterPanel panel) throws IOException {

		// Initialization
		this.file = file;
		this.panel = panel;

		this.server = new ServerSocket(file.getDataSet().getIntercepterOption().getPort());

		// Run 3 threads at the same time
		this.threadPool = new ThreadPool(3);
	}

	/**
	 * Start Server
	 * 
	 * @throws IOException
	 * 
	 * @author Tomahawkd
	 */

	public void start() throws IOException {

		int threadCount = 0;

		while (true) {

			// Accept the connection and block until a connection established
			Socket socket = server.accept();

			// Initialize Server connection
			Server proxy = new Server(file, panel, socket);

			// Saving properties
			if (threadCount > 25) {
				proxy.rejectSaveData();
			}

			// Create thread
			threadPool.execute(proxy);
			threadCount++;
		}
	}

	/**
	 * Stop Server
	 * 
	 * @author Tomahawkd
	 */

	public void stop() {
		threadPool.close();
	}

}

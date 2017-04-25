package intercepter;

import java.io.IOException;

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

	private ThreadPool threadPool;

	private IntercepterPanel panel;

	public Intercepter(FileIO file, IntercepterPanel panel) throws IOException {
		this.file = file;
		this.panel = panel;

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

		Server proxy = new Server(file, panel);

		int threadCount = 0;

		while (true) {

			// Accept the connection and block until a connection established
			proxy.accept();

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

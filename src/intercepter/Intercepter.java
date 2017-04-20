package intercepter;

import java.io.IOException;
import data.FileIO;


/**
 * Intercepter: Main Execute Class
 * 
 * @author Ghost
 */

public class Intercepter {

	
	/**
	 * File handler
	 * 
	 * @see {@link FileIO}
	 */
	
	private FileIO file;
	
	private ThreadPool threadPool;
	
	
	public Intercepter(FileIO file) throws IOException {
		this.file = file;
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
		
		Server proxy = new Server(file);
		
		int threadCount = 0;
		while (threadCount < 20) {
			threadPool.execute(proxy);
			threadCount++;
		}
	}
	
	public void stop() {
		threadPool.close();
	}

}

package intercepter;

import java.io.IOException;
import java.net.ServerSocket;
import data.FileIO;


/**
 * Intercepter: Main Execute Class
 * 
 * @author Ghost
 */

public class Intercepter {

	/**
	 * Server
	 * 
	 * @see {@link ServerSocket}
	 */
	
	private ServerSocket server;
	
	/**
	 * File handler
	 * 
	 * @see {@link FileIO}
	 */
	
	private FileIO file;

	
	
	
	
	
	public Intercepter(FileIO file) throws IOException {
		this.file = file;
		server = new ServerSocket(file.getDataSet().getIntercepterOption().getPort());
	}

	/**
	 * Start Server
	 * 
	 * @throws IOException
	 * 
	 * @author Tomahawkd
	 */
	
	public void start() throws IOException {
		ThreadPool threadPool = new ThreadPool(3);
		Server proxy = new Server(server, file);
		
		int threadCount = 0;
		while (threadCount < 20) {
			threadPool.execute(proxy);
			threadCount++;
		}
	}

}

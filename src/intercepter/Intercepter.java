package intercepter;

import java.io.IOException;

import Interface.IntercepterPanel;
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
	
	private IntercepterPanel panel;
	
	
	public Intercepter(FileIO file, IntercepterPanel panel) throws IOException {
		this.file = file;
		this.panel = panel;
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
			
			//Saving properties
			if(threadCount > 25) {
				proxy.rejectSaveData();
			}
			
			//Create thread
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

package intercepter;

import java.io.IOException;
import java.net.ServerSocket;
import data.FileIO;

public class Intercepter {

	private ServerSocket server;
	private FileIO file;

	public Intercepter(FileIO file) throws IOException {
		this.file = file;
		server = new ServerSocket(file.getDataSet().getIntercepterOption().getPort());
	}

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

package intercepter;

import java.io.*;
import java.net.*;

import data.FileIO;


/**
 * Intercepter: Server to intercept the request data
 * 
 * @author Tomahawkd
 */

public class Server {
	private Socket socket = null;
	private ServerSocket server;
	private FileIO file;
	
	public Server(FileIO file) {
		this.file = file;
	}
	
	public void start() throws IOException {
		server = new ServerSocket(file.getDataSet().getIntercepterOption().getPort());
		socket = server.accept();
	}
	
	public void stop() throws IOException {
		if(socket != null) {
			socket.close();
		}
	}
	
	private void action() throws IOException {
		if (this.socket == null) {
			return;
		}
		
		InterceptData data = new InterceptData();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		
		int lineCount = 0;
		String temp;
		
		while ((temp = br.readLine()) != null) {
			
			//Set host
			if(temp.contains("Host: ")) {
				data.setHost(temp.split(": ")[1]);
			}
			
			//Set request method
			if(lineCount == 0) {
				data.setMethod((temp.split(" ")[0]));
			
			//Headers
			} else if (temp.contains(": ")) {
				String[] header = temp.split(": ");
				data.addRequestHeaderElement(header[0], header[1]);
				
			//Body	
			} else {
				data.addRequestBodyElement(temp);
			}
		}
		br.close();
		
		
		Backend backend = new Backend(data);
		
	}
	
	
}

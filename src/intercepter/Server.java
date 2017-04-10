package intercepter;

import java.io.*;
import java.net.*;

import data.IntercepterOption;

/**
 * Intercepter: Server to intercept the request data
 * 
 * @author Tomahawkd
 */

public class Server {
	private Socket socket = null;
	private boolean suspendFlag;
	private ServerSocket server;
	private IntercepterOption option;
	private RequestData request;
	private Backend backend;
	private String host;
	
	public Server() {
		suspendFlag = false;
		request = new RequestData();
		backend = new Backend(request);
	}
	
	/**
	 * Read user's option
	 * 
	 * @param option user's preference
	 * 
	 * @author Tomhawkd
	 */
	
	public void setOption(IntercepterOption option) {
		this.option = option;
	}
	
	public void start() throws IOException {
		server = new ServerSocket(option.getPort());
		socket = server.accept();
		action();
	}
	
	public void resume() {
		suspendFlag = false;
	}
	
	public void closeSocket() throws IOException {
		if(socket != null) {
			socket.close();
		}
	}
	
	public boolean getSuspend() {
		return suspendFlag;
	}
	
	public Backend getBackend() {
		return backend;
	}
	
	public String getHost() {
		return host;
	}
	
	private void action() throws IOException {
		if (!suspendFlag) {
			if (this.socket == null) {
				return;
			}
			BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			for (String temp = br.readLine(); temp != null; temp = br.readLine()) {
				request.addRequestElement(temp);
				
				//Get the host while read the data
				if (temp.startsWith("Host: ")) {
					host = temp.split(": ")[1];
				}
			}
			br.close();
			suspendFlag = true;
		}
	}
	
	
}

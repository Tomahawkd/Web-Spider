package proxy;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import data.RequestData;

public class ServerSocketListener {
	private Socket socket = null;
	private boolean suspendFlag;
	private ServerSocket server;
	private int port = 8080;
	RequestData request;
	
	public static void main(String[] args) {
		try {
			while(true){
				ServerSocketListener s = new ServerSocketListener(8080);
				s.action();
				if(s.getSuspend()) {
					for(int i = 0; i < s.getData().getRequest().size(); i++){
						System.out.println(s.getData().getRequest().get(i));
					}
					break;
				}
			}
		} catch (IOException e) {
		}
	}
	
	public ServerSocketListener(int port) throws IOException {
		this.port = port;
		suspendFlag = false;
		request = new RequestData();
		server = new ServerSocket(port);
		socket = server.accept();
	}
	
	public void closeSocket() throws IOException {
		socket.close();
	}
	
	public void setSuspend(boolean suspend) {
		suspendFlag = suspend;
	}
	
	public boolean getSuspend() {
		return suspendFlag;
	}
	
	public RequestData getData() {
		return request;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public void action() throws IOException {
		if (this.socket == null){
			return ;
		}
		BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
		for(String temp = br.readLine() ; temp!=null; temp = br.readLine()){
			request.addRequestElement(temp);
		}
		br.close();
		suspendFlag = true;
	}
	
	
}
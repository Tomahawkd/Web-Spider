package proxy;

import java.io.*;
import java.net.*;

import data.IntercepterOption;
import data.RequestData;

/**
 * Intercepter: Intercept http transfer data
 * 
 * @author Tomahawkd
 */

public class ServerSocketListener {
	private Socket socket = null;
	private boolean suspendFlag;
	private ServerSocket server;
	private IntercepterOption option;
	RequestData request;
	
	public static void main(String[] args) {
		IntercepterOption optionTest = new IntercepterOption();
		optionTest.setPort(8080);
		try {
			while(true){
				ServerSocketListener s = new ServerSocketListener();
				s.setOption(optionTest);
				s.start();
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
	
	public ServerSocketListener() {
		suspendFlag = false;
		request = new RequestData();
	}
	
	/**
	 * Read user's option
	 * 
	 * @param option user's preference
	 * 
	 * @author Ghost
	 */
	
	public void setOption(IntercepterOption option) {
		this.option = option;
	}
	
	public void start() throws IOException {
		server = new ServerSocket(option.getPort());
		socket = server.accept();
		action();
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
	
	private void action() throws IOException {
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
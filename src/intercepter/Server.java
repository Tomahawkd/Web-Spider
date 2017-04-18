package intercepter;

import java.io.*;
import java.net.*;

import Interface.IntercepterPanel;
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
	private IntercepterPanel panel;

	private Backend backend;
	private Backend head;
	private boolean isOn;

	public Server(FileIO file, IntercepterPanel panel) {
		this.file = file;
		this.panel = panel;
		isOn = false;
	}
	

	public void start() throws IOException {
		server = new ServerSocket(file.getDataSet().getIntercepterOption().getPort());
		isOn = false;
	}

	public void stop() {
		isOn = false;
	}

	public void resume() {
		isOn = true;
	}

	public void action() throws IOException {

		socket = server.accept();

		if (this.socket == null) {
			return;
		}

		InterceptData data = new InterceptData();

		BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

		String request = "";
		String temp;
		int line = 0;

		while ((temp = br.readLine()) != null) {
			if (temp.endsWith("\r\n"))
				temp = temp.substring(0, (temp.length() - 2));
			if (line == 0 || temp.contains(": ")) {
				request += (temp + "\r\n");
				line++;
			} else {
				if (temp.contains(": ")) {
					request += temp;
				} else {
					request += ("\r\n\r\n" + temp);
				}
				break;
			}

		}
		br.close();

		if (!request.equals("")) {

			data.setRequest(request);

			if (isOn) {
				if (head == null) {
					head = new Backend(data);
					backend = head;
					panel.updateData();
				} else {
					backend.addNext(new Backend(data));
					backend = backend.next();
				}
			} else {
				response(new Backend(data));
			}
		}

	}

	public Backend current() {
		return head;
	}

	public Backend next() {
		if (head.next() != null) {
			head = head.next();
		} else {
			head = null;
		}
		return head;
	}

	public void response(Backend backend) throws IOException {

		socket = server.accept();

		backend.getResponse();

		if (this.socket == null) {
			return;
		}

		PrintWriter pw = new PrintWriter(socket.getOutputStream());
		pw.write(backend.getData().getResponse());
		pw.flush();
		pw.close();
	}
	
	public void sendAll() throws IOException {
		while(head != null) {
			response(head);
			head = head.next();
		}
	}

}

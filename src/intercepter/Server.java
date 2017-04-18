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

	private Backend backend;
	private Backend head;
	private boolean isFirst;
	private boolean isOn;
	private boolean isStart;

	public Server(FileIO file) {
		this.file = file;
		isFirst = true;
		isOn = false;
		isStart = false;
	}
	
	public boolean isStart() {
		return isStart;
	}

	public void start() throws IOException {
		server = new ServerSocket(file.getDataSet().getIntercepterOption().getPort());
		isOn = false;
		isStart = true;
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
				if (isFirst) {
					head = new Backend(data);
					backend = head;
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
			return head;
		} else {
			return null;
		}
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

}

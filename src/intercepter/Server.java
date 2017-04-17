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

	public Server(FileIO file) {
		this.file = file;
		isFirst = true;
		isOn = false;
	}

	public void start() throws IOException {
		server = new ServerSocket(file.getDataSet().getIntercepterOption().getPort());
		isOn = true;
	}

	public void stop() throws IOException {
		isOn = false;
	}

	public void action() throws IOException {

		socket = server.accept();

		if (this.socket == null) {
			return;
		}

		InterceptData data = new InterceptData();

		BufferedReader br = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

		int lineCount = 0;
		String temp;

		while ((temp = br.readLine()) != null) {

			// Set host
			if (temp.contains("Host: ")) {
				data.setHost(temp.split(": ")[1]);
			}

			// Set request method
			if (lineCount == 0) {
				data.setMethod((temp.split(" ")[0]));
				lineCount++;

				// Headers
			} else if (temp.contains(": ")) {
				String[] header = temp.split(": ");
				data.addRequestHeaderElement(header[0], header[1]);

				// Body
			} else {
				data.addRequestBodyElement(temp);
			}
		}
		br.close();

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

		backend.getResponse();

		if (this.socket == null) {
			return;
		}

		PrintWriter pw = new PrintWriter(socket.getOutputStream());
		pw.write(backend.getData().getResponse());
		pw.flush();
	}

}

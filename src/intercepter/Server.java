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

		// Initialize data class for coming session
		InterceptData data = new InterceptData();

		// Get request from client
		InputStream in = this.socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String request = "";
		String line;
		int lineCount = 0;
		String contentLength = "";
		String method = "GET";

		while ((line = br.readLine()) != null) {

			if (lineCount == 0 || line.contains(": ")) {
				request += (line + "\r\n");
				lineCount++;
				if (!line.contains(": ")) {
					method = line.split(" ")[0];
				}
				if (line.startsWith("Content-Length: ")) {
					contentLength = line.split(": ")[1];
				}
			} else {
				break;
			}

		}

		if (method.equals("POST")) {
			int length = Integer.valueOf(contentLength);
			byte[] buffer = new byte[length];
			in.read(buffer, 0, length);
			data.setRequestBody(new String(buffer, 0, buffer.length));
			System.out.println(new String(buffer, 0, buffer.length));
		}

		br.close();

		// Ignore empty request
		if (!request.equals("")) {

			// Set request to data
			data.setRequest(request);

			// Intercepter is on
			if (isOn) {

				// Create chain for intercept queue
				if (head == null) {
					head = new Backend(data);
					backend = head;
					panel.updateData();
				} else {
					backend.addNext(new Backend(data));
					backend = backend.next();
				}

				// Intercepter is off
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

		// Return the data to client
		OutputStream out = socket.getOutputStream();
		out.write(backend.getData().getResponse());
		out.flush();
		out.close();
	}

	public void sendAll() throws IOException {
		while (head != null) {
			response(head);
			head = head.next();
		}
	}

}

package intercepter;

import java.io.*;
import java.net.*;

import data.FileIO;

/**
 * Intercepter: Server to intercept the request data
 * 
 * @author Tomahawkd
 */

public class Server implements Runnable {

	private ServerSocket server;
	private FileIO file;

	public Server(ServerSocket server, FileIO file) {
		this.server = server;
		this.file = file;
	}

	@Override
	public void run() {
		try {
			action();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void action() throws IOException {

		Socket socket = server.accept();

		if (socket == null) {
			return;
		}

		// Initialize data class for coming session
		InterceptData data = new InterceptData();

		// Get request from client
		InputStream in = socket.getInputStream();
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String request = "";
		String body = "\r\n";
		String line;
		int lineCount = 0;
		int length = 0;

		while ((line = br.readLine()) != null) {

			if (lineCount == 0 || line.contains(": ")) {
				request += (line + "\r\n");
				lineCount++;
				if (line.startsWith("Content-Length: ")) {
					try {
						length = Integer.parseInt(line.split(": ")[1]);
					} catch (NumberFormatException e) {
					}
				}
			} else {
				break;
			}

		}

		// Get POST method request body
		if (length != 0) {

			char[] buffer = new char[length];

			br.read(buffer);

			body += new String(buffer);
		}

		br.close();

		// Ignore empty request
		if (!request.equals("")) {

			// Add request body to the request
			request += body;

			// Set request to data
			data.setRequest(request);

			// Get response
			response(new Backend(data));
		}

	}

	public void response(Backend backend) throws IOException {

		backend.getResponse();

		Socket socket = server.accept();

		if (socket == null) {
			return;
		}

		// Return the data to client
		OutputStream out = socket.getOutputStream();
		out.write(backend.getData().getResponse());
		out.flush();
		out.close();

		file.getDataSet().getIntercepterData().setNewData(
				backend.getData().getURLString(), 
				backend.getData().getRequest(), 
				backend.getData().getResponseText());
	}

}

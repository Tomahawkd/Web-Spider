package intercepter;

import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

import Interface.IntercepterPanel;
import data.FileIO;

/**
 * Intercepter: Server to intercept the request data
 * 
 * @author Tomahawkd
 */

class Server implements Runnable {

	/**
	 * Socket connection
	 * 
	 * @see {@link Socket}
	 */

	private Socket socket;

	/**
	 * File handler
	 * 
	 * @see {@link FileIO}
	 */

	private FileIO file;

	/**
	 * Intercepter panel to update data
	 */

	private IntercepterPanel panel;

	/**
	 * Flag indicate the save-data operation
	 */

	private boolean save;

	
	
	
	Server(FileIO file, IntercepterPanel panel, Socket socket) throws IOException {

		//Initialization
		this.file = file;
		this.panel = panel;
		this.socket = socket;

		
		save = true;
		
	}

	
	@Override
	public void run() {
		action();
	}

	
	
	/**
	 * Cache-only data flag
	 */
	
	void rejectSaveData() {
		save = false;
	}

	/**
	 * Listen for request
	 * 
	 * @throws IOException
	 * 
	 * @author Tomahawkd
	 */

	private void action() {

		try {
			if (socket == null) {
				return;
			}

			// Initialize data class for coming session
			InterceptData data = new InterceptData();

			// Get request from client
			InputStream in = socket.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			// Request
			String request = "";
			String body = "\r\n";

			// Buffered reader staff
			String line;
			int lineCount = 0;

			// Body Length
			int length = 0;

			// Read header
			while ((line = br.readLine()) != null) {
				if (lineCount == 0 || line.contains(": ")) {
					request += (line + "\r\n");
					lineCount++;

					// Get body length
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

			// Ignore empty request
			if (!request.equals("")) {

				// Add request body to the request
				request += body;

				// Set request to data
				data.setRequest(request);

				// Get response
				response(new Backend(data));
			}
			
		} catch (IOException e) {
			
			//Notify the user after caught a IOException
			JOptionPane.showMessageDialog(null, 
					"Error occurs while running proxy server, please shutdown and contact the developer.", 
					"Error", JOptionPane.ERROR_MESSAGE);
			
		} finally {
			if(socket != null && socket.isConnected() && !socket.isClosed()) {
				try {
					
					//Close socket
					socket.close();
					
					//Ignore Exception
				} catch (IOException e) {}
			}
		}
		

	}

	/**
	 * Get response and save data
	 * 
	 * @param backend
	 *            request sender
	 * 
	 * @throws IOException
	 * 
	 * @author Tomahawkd
	 */

	private void response(Backend backend) throws IOException {

		// Get response from the server
		backend.getResponse();

		if (socket == null) {
			return;
		}

		// Return the data to client
		OutputStream out = socket.getOutputStream();
		out.write(backend.getData().getResponse());
		out.flush();
		out.close();

		// Save data
		if (save) {

			file.getDataSet().getIntercepterData().add(backend.getData().getURLString(), backend.getData().getRequest(),
					backend.getData().getResponseText());

			//Update panel data
			panel.updateData();
		}
	}
}

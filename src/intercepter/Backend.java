package intercepter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Intercepter: Backend section to edit request data and send to the target host
 * 
 * @author Tomahawkd
 */

class Backend {

	/**
	 * The current session's data
	 * 
	 * @see {@link InterceptData}
	 */

	private InterceptData data;

	Backend(InterceptData data) {
		this.data = data;
	}

	/**
	 * The current session's data
	 * 
	 * @return {@link InterceptData}
	 */

	InterceptData getData() {
		return data;
	}

	/**
	 * Get response from server.
	 * 
	 * @author Tomahawkd and XuanYu
	 */

	void getResponse() {

		Socket socket = null;
		OutputStream out = null;
		InputStream in = null;
		try {

			// Get the server's host and port
			String host = data.getURL().getHost();
			int port = data.getURL().getPort() == -1 ? 80 : data.getURL().getPort();

			/*
			 * Socket
			 */

			socket = new Socket();

			// Properties
			socket.setTcpNoDelay(true);
			socket.setReuseAddress(true);
			socket.setSoTimeout(30000);
			socket.setSoLinger(true, 5);
			socket.setSendBufferSize(1024);
			socket.setReceiveBufferSize(1024);
			socket.setKeepAlive(true);
			socket.setOOBInline(true);
			socket.setTrafficClass(0x04 | 0x10);
			socket.setPerformancePreferences(2, 1, 3);

			/*
			 * Connect to server
			 */

			socket.connect(new InetSocketAddress(host, port), 30000);

			// Send HTTP request
			out = socket.getOutputStream();
			out.write(data.getRequest().getBytes());

			// Accept HTTP response
			in = socket.getInputStream();
			ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
			byte[] buffer = new byte[512];
			int len = -1;
			while ((len = in.read(buffer)) != -1) {
				bytesOut.write(buffer, 0, len);
				bytesOut.flush();
			}

			// Origin byte data array
			byte[] respBuffer = bytesOut.toByteArray();

			// Set response
			data.setResponse(respBuffer);

		} catch (Exception e) {

			/*
			 * Generate time out page and response
			 */

			String responseBody = "<!DOCTYPE html>" + "<html lang=\"en\">" + "<head>" + "<meta charset=\"UTF-8\">"
					+ "<title>Time out</title>" + "</head>" + "<body>" + "<p>TIME OUT</p>" + "</body>" + "</html>";

			String responseHeader = "HTTP/1.1 404 Not Found\r\n" + "Content-Type: text/html; charset=utf-8\r\n"
					+ "Connection: close\r\n" + "Vary: Accept-Encoding\r\n" + "Content-Length: "
					+ responseBody.getBytes().length;

			String response = responseHeader + "\r\n\r\n" + responseBody;

			data.setResponse(response.getBytes());

		} finally {
			if (null != socket && socket.isConnected() && !socket.isClosed()) {
				try {

					// Close socket
					socket.close();

					// Ignore exception
				} catch (IOException e) {
				}
			}
		}
	}

}

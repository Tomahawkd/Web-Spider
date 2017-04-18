package intercepter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Arrays;

/**
 * Intercepter: Backend section to edit request data and send to the target host
 * 
 * @author Tomahawkd
 */

public class Backend {

	
	/**
	 * The current session's data
	 * 
	 * @see {@link InterceptData}
	 */
	
	private InterceptData data;
	
	
	/**
	 * The next backend session
	 */
	
	private Backend next;

	
	
	
	
	
	
	
	
	
	Backend(InterceptData data) {
		this.data = data;
		next = null;
	}

	
	/**
	 * Set the next backend session
	 * 
	 * @param next backend session
	 * 
	 * @author Tomahawkd
	 */
	
	void addNext(Backend next) {
		this.next = next;
	}
	
	
	/**
	 * Get the next backend session
	 * 
	 * @return next backend session
	 * 
	 * @author Tomahawkd
	 */
	
	Backend next() {
		if(next == null) {
			return null;
		} else {
			return next;
		}
	}
	
	
	/**
	 * The current session's data
	 * 
	 * @return {@link InterceptData}
	 */
	
	public InterceptData getData() {
		return data;
	}

	
	/**
	 * Get response from server.
	 * 
	 * @author Tomahawkd and XuanYu
	 */
	
	void getResponse() {
		OutputStream out = null; // 写
		InputStream in = null; // 读
		Socket socket = null; // 客户机
		String respCharset = "UTF-8";
		try {
			String host = data.getURL().getHost();
			int port = data.getURL().getPort() == -1 ? 80 : data.getURL().getPort();
			
			/*
			 * Socket
			 */
			
			socket = new Socket();

			//Properties
			socket.setTcpNoDelay(true);
			socket.setReuseAddress(true);
			socket.setSoTimeout(60000);
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
			socket.connect(new InetSocketAddress(host, port), 60000);
			
			
			/*
			 * Send HTTP request
			 */
			out = socket.getOutputStream();
			out.write(data.getRequest().getBytes());
			
			/*
			 * Accept HTTP response
			 */
			in = socket.getInputStream();
			ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
			byte[] buffer = new byte[512];
			int len = -1;
			while ((len = in.read(buffer)) != -1) {
				bytesOut.write(buffer, 0, len);
			}
			
			//Origin byte data array
			byte[] respBuffer = bytesOut.toByteArray();
			String re = bytesOut.toString();
			System.out.println(re);
			
			/*
			 * Decode bytes to response
			 */
			
			int from = 0;
			int to = 0;
			for (int i = 0; i < respBuffer.length; i++) {
				if ((respBuffer[i] == 99 || respBuffer[i] == 67)											//C
						&& (respBuffer[i + 1] == 111 || respBuffer[i + 1] == 79)							//o
						&& (respBuffer[i + 2] == 110 || respBuffer[i + 2] == 78)							//n
						&& (respBuffer[i + 3] == 116 || respBuffer[i + 3] == 84)							//t
						&& (respBuffer[i + 4] == 101 || respBuffer[i + 4] == 69)							//e
						&& (respBuffer[i + 5] == 110 || respBuffer[i + 5] == 78)							//n
						&& (respBuffer[i + 6] == 116 || respBuffer[i + 6] == 84) 							//t
						&& respBuffer[i + 7] == 45															//-
						&& (respBuffer[i + 8] == 84 || respBuffer[i + 8] == 116)							//T
						&& (respBuffer[i + 9] == 121 || respBuffer[i + 9] == 89)							//y
						&& (respBuffer[i + 10] == 112 || respBuffer[i + 10] == 80)							//p
						&& (respBuffer[i + 11] == 101 || respBuffer[i + 11] == 69)) {						//e
					from = i;
					continue;
				}
				if (from > 0 && to == 0 && respBuffer[i] == 13 && respBuffer[i + 1] == 10) {
					to = i;
					break;
				}
			}
			//Decode Content-Type
			byte[] headerByte = Arrays.copyOfRange(respBuffer, from, to);
			String contentType = new String(headerByte);
			
			//Get charset
			if (contentType.toLowerCase().contains("charset")) {
				respCharset = contentType.substring(contentType.lastIndexOf("=") + 1);
			}
			
			//Decode response
			data.setResponse(bytesOut.toString(respCharset));
		
		} catch (Exception e) {
			e.printStackTrace();
			
			
			/*
			 * Generate time out page and response
			 */
			
			String responseBody = "<!DOCTYPE html>"
					+ "<html lang=\"en\">"
					+ "<head>"
					+ "<meta charset=\"UTF-8\">"
					+ "<title>Time out</title>"
					+ "</head>"
					+ "<body>"
					+ "<p>TIME OUT</p>"
					+ "</body>"
					+ "</html>";
			
			String responseHeader = "HTTP/1.1 404 Not Found\r\n"
					+ "Content-Type: text/html; charset=utf-8\r\n"
					+ "Connection: close\r\n"
					+ "Vary: Accept-Encoding\r\n"
					+ "Content-Length: " + responseBody.getBytes().length;
			
			
			String response = responseHeader + "\r\n\r\n" + responseBody;
			
			data.setResponse(response);

		} finally {
			if (null != socket && socket.isConnected() && !socket.isClosed()) {
				try {
					
					//Close socket
					socket.close();
				} catch (IOException e) {
					System.out.println("关闭客户机Socket时发生异常,堆栈信息如下");
					e.printStackTrace();
				}
			}
		}

		
	}

}

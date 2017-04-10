package intercepter;

import java.io.*;

import data.IntercepterOption;

/**
 * Intercepter: Intercept http transfer data
 * 
 * @author Tomahawkd
 */

public class ServerSocketListener {
	
	public static void main(String[] args) {
		IntercepterOption optionTest = new IntercepterOption();
		optionTest.setPort(8080);
		try {
			while(true){
				Server s = new Server();
				s.setOption(optionTest);
				s.start();
				if(s.getSuspend()) {
					System.out.print(s.getBackend().getRequestData());
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
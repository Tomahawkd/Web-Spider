package intercepter;

import java.io.*;

import data.FileIO;
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
				Server s = new Server(new FileIO());
				s.start();
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
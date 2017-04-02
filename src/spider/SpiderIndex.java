package spider;

import java.util.ArrayList;

public class SpiderIndex {
	private ArrayList<String> existUrl;
	
	public SpiderIndex(String baseURL){
		existUrl = new ArrayList<>();
		existUrl.add(baseURL);
	}
	
	boolean compareExistUrl(String url){
		boolean flag = false;
		for(String exist : existUrl){
			if(url.equals(exist)) flag = true;
		}
		return flag;
	}
	
	
	
	void addNewUrl(String newUrl){
		existUrl.add(newUrl);
	}
	
	//  http://www.baidu.com/more
	// http:   www.baidu.com   more
	//
	String[] searchFromNode(String url) {
		return url.split("/");
	}
}

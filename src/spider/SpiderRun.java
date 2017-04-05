package spider;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import data.SpiderData;
import data.SpiderOption;

/**
 * Spider: Web Spider core system
 * 
 * @author Ghost
 */

@SuppressWarnings("restriction")
public class SpiderRun {
	private Document doc = null;
	private boolean urlValidate;
	private String currentUrl;
	private String hostFilter;
	private SpiderIndex result;
	private boolean suspendFlag;
	private SpiderOption option;
	private SpiderData data;
	
	public SpiderRun(SpiderData data) {
		suspendFlag = false;
		this.data = data;
	}
	
	/**
	 * Get user's option data.
	 * 
	 * @param option user's preference
	 * 
	 * @author Ghost
	 */
	
	public void setOption(SpiderOption option) {
		this.option = option;
	}
	
	/**
	 * Start web spider operation.
	 * 
	 * @throws nullHostException throws while the host has no parameters
	 * 
	 * @author Ghost
	 */
	
	public void start() throws nullHostException {
		if (option.getHost().equals("")) throw new nullHostException();
		suspendFlag = false;
		currentUrl = option.getProtocol() + "://" + option.getHost() + ":" + option.getPort() + "/";
		hostFilter = option.getProtocol() + "://" + option.getHost();
		result = new SpiderIndex(hostFilter, option.getProtocol());
		result.addNewUrl(currentUrl);
		data.setHost(option.getProtocol() + "://" + option.getHost());
		getHerfHtml();
	}
	
	/**
	 * Resume a suspend spider operation.
	 * 
	 * @author Ghost
	 */
	
	public void resume() {
		suspendFlag = false;
		getHerfHtml();
	}
	
	/**
	 * Stop a spider operation.
	 * 
	 * @author Ghost
	 */
	
	public void stop() {
		suspendFlag = true;
	}
	
	/**
	 * Test the host connection.
	 * 
	 * @param baseURL
	 */
	
	private void connect(String baseURL){
		try {
			doc = Jsoup.connect(baseURL).get();
			urlValidate = true;
		} catch (IOException e) {
			urlValidate = false;
		} 
	}
	
	
	
	boolean getUrlValid(){
		return urlValidate;
	}
	
	/**
	 * Rescue to get html
	 * 
	 * @author Ghost
	 */
	
	private void getHerfHtml(){
		
		if (!suspendFlag) {
			
			connect(currentUrl);
			
			if (urlValidate) {
				
				data.add(result.searchFromNode(currentUrl), result.searchFromNode(currentUrl)[result.searchFromNode(currentUrl).length -1], doc);
				Elements media = doc.select("[src]");
				for (Element src : media) {
					if (src.attr("abs:src").contains(hostFilter)) {
						currentUrl = src.attr("abs:src");
						if (!currentUrl.equals("") && !result.compareExistUrl(currentUrl)) {
							result.addNewUrl(currentUrl);
							urlValidate = false;
							getHerfHtml();
						}
					}
				}

				Elements imports = doc.select("*[href]");
				for (Element link : imports) {
					if (link.attr("abs:href").contains(hostFilter)) {
						currentUrl = link.attr("abs:href");
						if (!currentUrl.equals("") && !result.compareExistUrl(currentUrl)) {
							result.addNewUrl(currentUrl);
							urlValidate = false;
							getHerfHtml();
						}
					}
				}
			} 
		}
		
	}
}

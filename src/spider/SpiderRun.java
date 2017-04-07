package spider;

import java.io.IOException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import data.SpiderData;
import data.SpiderOption;

/**
 * Spider: Web Spider core system
 * 
 * @author Tomahawkd
 */

@SuppressWarnings("restriction")
public class SpiderRun {
	
	private boolean suspendFlag;
	
	private SpiderIndex result;
	private SpiderOption option;
	private SpiderData data;
	private SpiderConnection connection;
	
	public SpiderRun(SpiderData data) {
		suspendFlag = false;
		this.data = data;
	}
	
	/**
	 * Get user's option data.
	 * 
	 * @param option user's preference
	 * 
	 * @see {@link SpiderOption}
	 * 
	 * @author Tomahawkd
	 */
	
	public void setOption(SpiderOption option) {
		this.option = option;
	}
	
	/**
	 * Start web spider operation.
	 * 
	 * @throws nullHostException throws while the host has no parameters
	 * 
	 * @author Tomahawkd
	 */
	
	public void start() throws nullHostException {
		if (option.getHost().equals("")) throw new nullHostException();
		suspendFlag = false;
		String url = option.getProtocol() + "://" + option.getHost() + ":" + option.getPort();
		result = new SpiderIndex(option.getHost());
		result.addNewUrl(url);
		connection = new SpiderConnection(option.getHeaders());
		getHerfHtml(url);
	}
	
	/**
	 * Stop a spider operation.
	 * 
	 * @author Tomahawkd
	 */
	
	public void stop() {
		suspendFlag = true;
	}
	
	/**
	 * Rescue to get html
	 * 
	 * @author Tomahawkd
	 */
	
	private void getHerfHtml(String url){
		
		if (!suspendFlag) {
			
			connection.setUrl(url);
			String content = connection.getHeaders() + "\n";
			
			if (connection.Validate()) {
				try {
					Document doc = connection.getResponse().parse();
					content += doc.html();
					
					String[] path = searchFromNode(doc.location());
					data.add(path, path[path.length - 1], content);
				
					Elements imports = doc.select("*[href]");
					for (Element link : imports) {
						if (link.attr("abs:href").contains(option.getHost())) {
							String newUrl = link.attr("abs:href");
							if (!newUrl.equals("") && !result.compareExistUrl(newUrl)) {
								result.addNewUrl(newUrl);
								getHerfHtml(newUrl);
							}
						}
					}
					Elements media = doc.select("[src]");
					for (Element src : media) {
						if (src.attr("abs:src").contains(option.getHost())) {
							String newUrl = src.attr("abs:src");
							if (!newUrl.equals("") && !result.compareExistUrl(newUrl)) {
								result.addNewUrl(newUrl);
								getHerfHtml(newUrl);
							}
						}
					}
				} catch (IOException e) {
					String[] path = searchFromNode(url);
					data.add(path, path[path.length - 1], content);
				}
			} 
		}
		
	}
	
	
	/**
	 * Get the path of the file in the host file system.
	 * 
	 * @param url Absolute url
	 * 
	 * @return array contains path
	 * 
	 * @author Tomahawkd
	 */
	
	private String[] searchFromNode(String url) {
		if(url.startsWith("http://")) {
			url = url.replace("http://", "");
		} else {
			url = url.replace("https://", "");
		}
		return url.split("/");
	}
}

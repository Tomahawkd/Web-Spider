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
	private String currentUrl;
	private SpiderIndex result;
	private boolean suspendFlag;
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
		currentUrl = option.getProtocol() + "://" + option.getHost() + ":" + option.getPort();
		result = new SpiderIndex(option.getHost());
		result.addNewUrl(currentUrl);
		data.setHost(option.getProtocol() + "://" + option.getHost());
		connection = new SpiderConnection(option.getHeaders());
		getHerfHtml();
	}
	
	/**
	 * Resume a suspend spider operation.
	 * 
	 * @author Tomahawkd
	 */
	
	public void resume() {
		suspendFlag = false;
		getHerfHtml();
	}
	
	/**
	 * Stop a spider operation.
	 * 
	 * @author Tomahawkd
	 */
	
	public void stop() {
		suspendFlag = true;
	}
	
	boolean getUrlValid(){
		return connection.Validate();
	}
	
	/**
	 * Rescue to get html
	 * 
	 * @author Tomahawkd
	 */
	
	private void getHerfHtml(){
		
		if (!suspendFlag) {
			
			connection.setUrl(currentUrl);
			String content = connection.getHeaders() + "\n";
			
			if (connection.Validate()) {
				try {
					Document doc = connection.getResponse().parse();
					
					content += doc.data();
					String[] path = searchFromNode(currentUrl);
					data.add(path, path[path.length - 1], content);
				
					Elements imports = doc.select("*[href]");
					for (Element link : imports) {
						if (link.attr("abs:href").contains(option.getHost())) {
							currentUrl = link.attr("abs:href");
							if (!currentUrl.equals("") && !result.compareExistUrl(currentUrl)) {
								result.addNewUrl(currentUrl);
								getHerfHtml();
							}
						}
					}
					Elements media = doc.select("[src]");
					for (Element src : media) {
						if (src.attr("abs:src").contains(option.getHost())) {
							currentUrl = src.attr("abs:src");
							if (!currentUrl.equals("") && !result.compareExistUrl(currentUrl)) {
								result.addNewUrl(currentUrl);
								getHerfHtml();
							}
						}
					}

				} catch (IOException e) {}
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
		url = url.replace(option.getProtocol() + "://", "");
		return url.split("/");
	}
}

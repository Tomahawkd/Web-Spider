package spider;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import data.SpiderOption;


@SuppressWarnings("restriction")
public class SpiderRun {
	private Document doc = null;
	private boolean urlValidate;
	private String currentUrl;
	private String hostFilter;
	private SpiderIndex result;
	private boolean suspendFlag;
	private SpiderOption option;
	
	public SpiderRun() {
		suspendFlag = false;
	}
	
	public void setOption(SpiderOption option) {
		this.option = option;
	}
	
	public void start() throws nullHostException {
		if (option.getHost().equals("")) throw new nullHostException();
		suspendFlag = false;
		currentUrl = option.getProtocol() + "://" + option.getHost() + ":" + option.getPort() + "/";
		hostFilter = option.getProtocol() + "://" + option.getHost();
		result = new SpiderIndex(hostFilter);
		result.addNewUrl(currentUrl);
		getHerfHTML();
	}
	
	public void resume() {
		suspendFlag = false;
		getHerfHTML();
	}
	
	public void stop() {
		suspendFlag = true;
	}
	
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
	
	private void getHerfHTML(){
		
		if (!suspendFlag) {
			connect(currentUrl);
			if (urlValidate) {
				Elements media = doc.select("[src]");
				for (Element src : media) {
					if (src.attr("abs:src").contains(hostFilter)) {
//						System.out.println(src.attr("abs:src"));
						currentUrl = src.attr("abs:src");
						if (!currentUrl.equals("") && !result.compareExistUrl(currentUrl)) {
							result.addNewUrl(currentUrl);
							urlValidate = false;
//							System.out.printf("currentURL=%s\n", currentURL);
							getHerfHTML();
						}
					}
				}

				Elements imports = doc.select("*[href]");
				for (Element link : imports) {
					if (link.attr("abs:href").contains(hostFilter)) {
//						System.out.println(link.attr("abs:href"));
						currentUrl = link.attr("abs:href");
						if (!currentUrl.equals("") && !result.compareExistUrl(currentUrl)) {
							result.addNewUrl(currentUrl);
							urlValidate = false;
//							System.out.printf("currentURL=%s\n", currentURL);
							getHerfHTML();
						}
					}
				}
//			} else {
//				System.out.println("url is not valid");
			} 
		}
		
	}
}

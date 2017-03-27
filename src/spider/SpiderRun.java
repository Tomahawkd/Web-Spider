package spider;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


@SuppressWarnings("restriction")
public class SpiderRun {
	private Document doc = null;
	private boolean urlValidate;
	private String currentURL;
	private String hostFilter;
	private SpiderIndex result;
	private boolean suspendFlag;
	
	public static void main(String[] args){
		SpiderRun spr = new SpiderRun("https", "www.baidu.com", 443);
		spr.start();
	}
	
	public SpiderRun(String protocol, String host, int port){
		suspendFlag = false;
		currentURL = protocol + "://" + host + ":" +port + "/";
		hostFilter = protocol + "://" + host;
		result = new SpiderIndex(hostFilter);
		result.addNewUrl(currentURL);
		
	}
	
	public void start(){
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
			connect(currentURL);
			if (urlValidate) {
				Elements media = doc.select("[src]");
				for (Element src : media) {
					if (src.attr("abs:src").contains(hostFilter)) {
//						System.out.println(src.attr("abs:src"));
						currentURL = src.attr("abs:src");
						if (!currentURL.equals("") && !result.compareExistUrl(currentURL)) {
							result.addNewUrl(currentURL);
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
						currentURL = link.attr("abs:href");
						if (!currentURL.equals("") && !result.compareExistUrl(currentURL)) {
							result.addNewUrl(currentURL);
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

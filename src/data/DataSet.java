package data;

import java.io.Serializable;

public class DataSet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RequestData requestData;
	private IntercepterOption intercepter;
	private SpiderData spiderData;
	private SpiderOption spiderOption;
	
	
	public DataSet() {
		requestData = new RequestData();
		intercepter = new IntercepterOption();
		spiderData = new SpiderData();
		spiderOption = new SpiderOption();
	}

	public RequestData getRequestData() {
		return requestData;
	}


	public IntercepterOption getIntercepterOption() {
		return intercepter;
	}


	public SpiderData getSpiderData() {
		return spiderData;
	}


	public SpiderOption getSpiderOption() {
		return spiderOption;
	}
	
	
	
	
}

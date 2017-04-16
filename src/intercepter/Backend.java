package intercepter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map.Entry;

/**
 * Intercepter: Backend section to edit request data and send to the target host
 * 
 * @author Tomahawkd
 */

class Backend {

	InterceptData data;

	Backend(InterceptData data) {
		this.data = data;
	}

	void getResponse() {
		PrintWriter out = null;
        BufferedReader in = null;
        try {
            URL realUrl = new URL(data.getHost());
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            for (Entry<String, String> mapping : data.getRequestHeader().entrySet()) {
            	if(!mapping.getKey().equals("")) {
            		conn.setRequestProperty(mapping.getKey(), mapping.getValue());
            	}
            }
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(data.getRequestBody());
            // flush输出流的缓冲
            out.flush();
            
            data.setResponseHeader(conn.getHeaderFields());
            
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                data.addResponseBodyElement(line);
            }
        } catch (Exception e) {
            System.out.println("发送请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
	}

}

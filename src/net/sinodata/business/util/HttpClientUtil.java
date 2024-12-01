package net.sinodata.business.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

public class HttpClientUtil {
	
	  public static void doPost(String url,String jsonData) throws UnsupportedEncodingException {
		    String praiseUrl = url; 
		    HttpClient client = new HttpClient();
		    
		    client.getHttpConnectionManager().getParams()
			.setConnectionTimeout(100);               
			client.getHttpConnectionManager().getParams()
			.setSoTimeout(100);
			
		    PostMethod postMethod = new PostMethod(praiseUrl);
		    StringRequestEntity requestEntity = new StringRequestEntity(
		    		jsonData,
		    	    "application/json",
		    	    "UTF-8");
		    postMethod.setRequestEntity(requestEntity);
		    try {
		      int code = client.executeMethod(postMethod);
		      if (code == 200) {
		        postMethod.getResponseBodyAsString();
		      }
		    } catch (IOException e) {
		      e.printStackTrace();
		    }finally {
		    	postMethod.releaseConnection();
		    }
		  }	
}

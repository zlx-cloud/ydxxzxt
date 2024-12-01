	package net.sinodata.business.util;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class ObjectUtil {

	public static RestTemplate getRestTemplate(){
		 RestTemplate restTemplate = new RestTemplate();  
	        StringHttpMessageConverter stringHttpMessageConverter=new StringHttpMessageConverter(Charset.forName("UTF-8"));    
	        List<HttpMessageConverter<?>> list=new ArrayList<HttpMessageConverter<?>>();   
	        list.add(stringHttpMessageConverter);  
	        restTemplate.setMessageConverters(list);
	        return restTemplate;
	}
	
	
}

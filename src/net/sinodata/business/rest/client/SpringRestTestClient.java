package net.sinodata.business.rest.client;

import net.sf.json.JSONObject;
import net.sinodata.business.util.ObjectUtil;

import org.springframework.web.client.RestClientException;

public class SpringRestTestClient {
	
	 public static final String REST_SERVICE_URI = "http://localhost:8080/ydxxzxt/service";  
	 //public static final String REST_SERVICE_URI = "http://20.1.13.13:8080/SpringREST/service"; 
	    private static void listAllUsers(){  
	       
	        //String jsonParam = "{\"name\":\"Aiya\"}"; 
	        String jsonParam="{"
	        	  +"\"FWQQZ_ZCXX\": \"实有人口采集系统\","
	        	  +"\"FWBS\": \"S00111009120000000001\","
	        	  +"\"FWQQ_RQSJ\": \"20170629170707\","
	        	  +"\"FWQQ_NR\":{\"method\":\"GetHouseInfo\",\"params\":{\"houseID\":\"10000000\",\"sfzh\":\"13105619890224\"}},"
	        	  +"\"XXCZRY_XM\": \"王五\","
	        	  +"\"XXCZRY_GMSFHM\": \"130579198607244513\","
	        	  +"\"XXCZRY_GAJGJGDM\": \"110102020000\","
	        	  +"\"FWQQSB_BH\": \"866620054421684\""
	        	  +"}";
              //System.out.println(jsonParam);
	       try {  
	    	   JSONObject jsonObject = JSONObject.fromObject(jsonParam);
	    	   /*Map<String,String> info=new HashMap<String,String>();
	           info.put("param",jsonObject.toString());*/
	    	   String result=ObjectUtil.getRestTemplate().getForObject(REST_SERVICE_URI+"/getBw?param="+jsonParam, String.class);
	    	   
	    	   //String jsonResult = ObjectUtil.getRestTemplate().postForObject(REST_SERVICE_URI+"/postBw", jsonParam, String.class);  
	    	   //System.out.println("testUser~" + result); 
	        } catch (RestClientException e) {  
	            e.printStackTrace();  
	        }  
	    }  
	      
	    public static void main(String[] args) {  
	        listAllUsers(); 
	        
	    }  
}

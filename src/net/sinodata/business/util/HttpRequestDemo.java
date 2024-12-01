package net.sinodata.business.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequestDemo {
    

    /** 
     * 发送HttpPost请求 
     *  
     * @param strURL 
     *            服务地址 
     * @param params 
     *            json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/> 
     * @return 成功:返回json字符串<br/> 
     */  
    public static String sendPost(String strURL, String params) {  
        //System.out.println(strURL);  
        //System.out.println(params);  
        try {  
            URL url = new URL(strURL);// 创建连接  
            HttpURLConnection connection = (HttpURLConnection) url  
                    .openConnection();  
            connection.setDoOutput(true);  
            connection.setDoInput(true);  
            connection.setUseCaches(false);  
            connection.setInstanceFollowRedirects(true);  
            connection.setRequestMethod("POST"); // 设置请求方式  
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式  
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式  
            connection.connect();  
            OutputStreamWriter out = new OutputStreamWriter(  
                    connection.getOutputStream(), "UTF-8"); // utf-8编码  
            out.append(params);  
            out.flush();  
            out.close();  
            // 读取响应  
            int length = (int) connection.getContentLength();// 获取长度  
            InputStream is = connection.getInputStream();  
            if (length != -1) {  
                byte[] data = new byte[length];  
                byte[] temp = new byte[512];  
                int readLen = 0;  
                int destPos = 0;  
                while ((readLen = is.read(temp)) > 0) {  
                    System.arraycopy(temp, 0, data, destPos, readLen);  
                    destPos += readLen;  
                }  
                String result = new String(data, "UTF-8"); // utf-8编码  
                 
                return result;  
            }  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
        return "error"; // 自定义错误信息  
    }  

    public static void main(String[] args) {
    	
    	String jsonParam="{"
  		  +"\"BWLYDKH\":\"8080\","
  		  +"\"BWLYIPDZ\":\"172.16.10.103\","
  		  +"\"FWBS\":\"S00111000000000022001\","
  		  +"\"FFBS\": \"FUN001\","
  		  +"\"FWQQSB_BH\": \"869661020828470\","
  		  +"\"FWQQZ_ZCXX\": \"A00100000000000100003\","
  		  +"\"FWQQ_BWBH\": \"SR020001011052201902181349268854765\","
  		  +"\"FWQQ_NR\":{\"method\":\"queryFW\",\"params\":{}},"
  		  +"\"FWQQ_RQSJ\": \"20190218134926\","
  		  +"\"FWQQ_SJSJLX\": \"service_request\","
  		  +"\"XXCZRY_GAJGJGDM\": \"110102000000\","
  		  +"\"XXCZRY_GMSFHM\": \"111111111111111110\","
  		  +"\"XXCZRY_XM\":\"李晨\""
          +"}";


	   
 
    	  String sr=HttpRequestDemo.sendPost("http://20.1.11.52:5988/http/GA000Comm", jsonParam);
    	  
   
         
    	//System.out.println("返回======"+sr);
    	
    
    	
    	
    	
    
       
    }
    

    
}

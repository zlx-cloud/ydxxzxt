package net.sinodata.business.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import sun.misc.BASE64Encoder;

public class HttpRequest {
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
//	public static String sendGet(String url, String param) {
//	    String message="";
//	    String httpurl=url+param;
//        try {
//            // 创建远程url连接对象
//            URL urladdress = new URL(httpurl);
//            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
//            HttpURLConnection connection = (HttpURLConnection)urladdress.openConnection();
//            // 设置连接方式：get
//            connection.setRequestMethod("GET");
//            // 设置连接主机服务器的超时时间：15000毫秒
//            connection.setConnectTimeout(15000);
//            // 设置读取远程返回的数据时间：60000毫秒
//            connection.setReadTimeout(60000);
//            // 发送请求
//            connection.connect();
//            // 通过connection连接，获取输入流
//            if (connection.getResponseCode() == 200) {
//            	 InputStream inputStream=connection.getInputStream();
//                 byte[] data=new byte[1024];
//                 StringBuffer sb1=new StringBuffer();
//                 int length=0;
//                 while ((length=inputStream.read(data))!=-1){
//                     String s=new String(data, 0,length);
//                     sb1.append(s);
//                 }
//                 message=sb1.toString();
//                 inputStream.close();
//            }
//            //关闭连接
//            connection.disconnect();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
// 
//        return message;
//    }
//	
	 
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url  + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                //System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            //System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**  发送HttpPost请求 
     * @param strURL  服务地址 
     * @param params  json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/> 
     * @return 成功:返回json字符串<br/> 
     */  
	public static String sendPost(String strURL, String params) {  
		  String result = "";
		  String authHost = strURL;
			try {
				URL url = new URL(authHost);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				// 设置请求方式
				connection.setRequestMethod("POST");
	//			for(String key : map.keySet()){
	//	        	connection.setRequestProperty(key,map.get(key).toString()); // 设置Header 
	//         	}
				// 设置是否向HttpURLConnection输出
				connection.setDoOutput(true);
				// 设置是否从httpUrlConnection读入
				connection.setDoInput(true);
				// 设置是否使用缓存
				connection.setUseCaches(false);
				// 设置参数类型是json格式
				connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
				connection.connect();
	//	        String body = requestString;
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));
				writer.write(params);
				writer.close();
	
				int responseCode = connection.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					// 定义 BufferedReader输入流来读取URL的响应
					BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
					String line;
					while ((line = in.readLine()) != null) {
						result += line;
					}
				}
	
			} catch (Exception ex) {
				ex.printStackTrace();
			}

		return result;
	} 
//    public static String sendPost(String strURL, String params) {  
//        System.out.println(strURL);  
//        System.out.println(params);  
//        try {  
//            URL url = new URL(strURL);// 创建连接  
//            HttpURLConnection connection = (HttpURLConnection) url  
//                    .openConnection();  
//            connection.setDoOutput(true);  
//            connection.setDoInput(true);  
//            connection.setUseCaches(false);  
//            connection.setInstanceFollowRedirects(true);  
//            connection.setRequestMethod("POST"); // 设置请求方式  
//            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式  
//            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式  
//            connection.connect();  
//            OutputStreamWriter out = new OutputStreamWriter(  
//                    connection.getOutputStream(), "UTF-8"); // utf-8编码  
//            out.append(params);  
//            out.flush();  
//            out.close();  
//            // 读取响应  
//            int length = (int) connection.getContentLength();// 获取长度  
//            InputStream is = connection.getInputStream();  
//            if (length != -1) {  
//                byte[] data = new byte[length];  
//                byte[] temp = new byte[512];  
//                int readLen = 0;  
//                int destPos = 0;  
//                while ((readLen = is.read(temp)) > 0) {  
//                    System.arraycopy(temp, 0, data, destPos, readLen);  
//                    destPos += readLen;  
//                }  
//                String result = new String(data, "UTF-8"); // utf-8编码  
//                 
//                return result;  
//            }  
//        } catch (IOException e) {  
//            // TODO Auto-generated catch block  
//            e.printStackTrace();  
//        }  
//        return "error"; // 自定义错误信息  
//    }  
 public static void sendPostJson(String strURL, JSONObject jsonObj) {  
    	
        // Create the POST object and add the parameters
        HttpPost httpPost = new HttpPost(strURL);
        StringEntity entity = new StringEntity(jsonObj.toString(), HTTP.UTF_8);
        entity.setContentType("application/json");
        httpPost.setEntity(entity);
        HttpClient client = new DefaultHttpClient();
        try {
			HttpResponse response = client.execute(httpPost);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
 
 public static String getAttString() {
		String attString;
		byte[] strBuffer = null;
		int    flen = 0;
		File xmlfile = new File("D:/home/1.jpg");  
		 try {
			InputStream in = new FileInputStream(xmlfile);
			flen = (int)xmlfile.length();
			strBuffer = new byte[flen];
			in.read(strBuffer, 0, flen);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 attString = new   String(strBuffer);      //构建String时，可用byte[]类型，
	 
		return attString;
	}
 /**
  * 将图片文件转为字符串
  * @param imgFile
  * @return
  */
 public static String getImageStr(String imgFile) {
     // 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
     //String imgFile = "d:\\111.jpg";// 待处理的图片
     InputStream in = null;
     byte[] data = null;
     // 读取图片字节数组
     try {
         in = new FileInputStream(imgFile);
         data = new byte[in.available()];
         in.read(data);
         in.close();
     } catch (IOException e) {
         e.printStackTrace();
     }
     // 对字节数组Base64编码
     BASE64Encoder encoder = new BASE64Encoder();
     // 返回Base64编码过的字节数组字符串
     return encoder.encode(data);
 }
    public static void main(String[] args) {
       
    /*	JSONObject jsonObject=new JSONObject();
    	jsonObject.put("FWQQ_BWBH", "SR020001011052201709191541010000001");
    	jsonObject.put("BWLYIPDZ", "192.168.0.1");
    	jsonObject.put("BWLYDKH", "80");
    	jsonObject.put("FWQQZ_ZCXX", "A00100000000000100005");
    	jsonObject.put("FWBS", "S00111010200000000017");
    	jsonObject.put("FFBS", "ap001");
    	jsonObject.put("FWQQ_RQSJ", "20170629170707");
    	JSONObject jsonObject1=new JSONObject();
    	jsonObject1.put("method", "GetHouseInfo");
    	JSONObject jsonObject2=new JSONObject();
    	jsonObject2.put("houseID", "10000000");
    	jsonObject2.put("sfzh", "13105619890224");
    	jsonObject1.put("params", jsonObject2);
    	jsonObject.put("FWQQ_NR", jsonObject1);
    	
    	jsonObject.put("XXCZRY_XM", "王五");
    	jsonObject.put("XXCZRY_GMSFHM", "130579198607244513");
    	jsonObject.put("XXCZRY_GAJGJGDM","110102020000");
    	jsonObject.put("FWQQSB_BH", "866620054421684");
    	jsonObject.put("FWQQ_SJSJLX", "service_request");*/
    	//jsonObject.put("ATTACHMENT", getImageStr("D:/home/1.jpg"));
    	Calendar ca = Calendar.getInstance();
    	String time = ca.get(ca.YEAR)
    		+""+(ca.get(ca.MONTH)>=9?(ca.get(ca.MONTH)+1):"0"+(ca.get(ca.MONDAY)+1))
    		+""+(ca.get(ca.DATE)>=10?ca.get(ca.DATE):"0"+ca.get(ca.DATE))
    		+""+(ca.get(ca.HOUR_OF_DAY)>=10?ca.get(ca.HOUR_OF_DAY):"0"+ca.get(ca.HOUR_OF_DAY))
    		+""+(ca.get(ca.MINUTE)>=10?ca.get(ca.MINUTE):"0"+ca.get(ca.MINUTE))
    		+""+(ca.get(ca.SECOND)>=10?ca.get(ca.SECOND):"0"+ca.get(ca.SECOND))
    		+""+(ca.get(ca.MILLISECOND)>=100?ca.get(ca.MILLISECOND):(ca.get(ca.MILLISECOND)>=10?"0"+ca.get(ca.MILLISECOND):"00"+ca.get(ca.MILLISECOND)));
    	//System.out.println(time);
//    	String jsonParam="{"
//    			+"\"FWBS\":\"S00101101081000036005\","
//    			+"\"XXCZRY_XM\":\"lichen\","
//    			+"\"XXCZRY_GMSFHM\":\"11111112312335649\","
//    			+"\"FWQQ_RQSJ\":\"20190410141729\","
//    			+"\"FWQQ_BWBH\":\"SR020001031150201904101417290971234\","
//    			+"\"XXCZRY_GAJGJGDM\":\"110108183000\","
//    			+"\"FFBS\":\"FUN001\","
//    			+"\"BWLYDKH\":\"80\","
//    			+"\"FWQQ_NR\":{\"method\":\"postDataHandler\",\"params\":{\"requestCompanyEntity\":{\"companyId\":\"1024\",\"address\":\"北京市海淀区\",\"name\":\"海淀\"},\"name\":\"系统测试\",\"age\":999}},"
//    			+"\"FWQQZ_ZCXX\":\"A00111010810000002901\","
//    			+"\"FWQQ_SJSJLX\":\"service_request\","
//    			+"\"BWLYIPDZ\":\"20.1.31.150\","
//    			+"\"FWQQSB_BH\":\"869661020828490\""
//    			+"}";
//	   
//    	  String sr=HttpRequest.sendPost("http://20.1.11.52:5988/http/GA000Comm", jsonParam);
    	
    	JSONObject jsonObject=new JSONObject();
    	jsonObject.put("FWBS", "S00101101081000036005");
    	jsonObject.put("XXCZRY_XM", "lichen");
    	jsonObject.put("XXCZRY_GMSFHM", "11111112312335649");
    	jsonObject.put("FWQQ_RQSJ", time);
    	jsonObject.put("FWQQ_BWBH", "SR020001031150"+time+"1234");
    	jsonObject.put("XXCZRY_GAJGJGDM", "110108183000");
    	jsonObject.put("FFBS", "FUN001");
      	jsonObject.put("BWLYDKH", "80");
    	jsonObject.put("FWQQZ_ZCXX", "A00111010810000002901");
    	jsonObject.put("FWQQ_SJSJLX","service_request");
    	jsonObject.put("BWLYIPDZ", "20.1.31.150");
    	jsonObject.put("FWQQSB_BH", "869661020828490");
    	
    	JSONObject jsonObject1=new JSONObject();
    	jsonObject1.put("method", "postDataHandler");
    	JSONObject jsonObject2=new JSONObject();
    	JSONObject jsonObject3=new JSONObject();
    	jsonObject3.put("companyId", "1024");
    	jsonObject3.put("address", "北京市海淀区");
    	jsonObject3.put("name", "海淀");
    	
    	jsonObject2.put("name", "系统测试");
    	jsonObject2.put("age", "999");
    	jsonObject2.put("requestCompanyEntity", jsonObject3);
    
    	jsonObject1.put("params", jsonObject2);
    	jsonObject.put("FWQQ_NR", jsonObject1);
    	
  
		String sr = HttpRequest.sendPost("http://20.1.11.52:5988/http/GA000Comm", jsonObject.toString());
   	 // String sr=HttpRequest.sendPost("http://20.1.11.52:5988/http/testflow3", jsonParam);
    	  
    	  //String sr=HttpRequest.sendPost("http://localhost:8080/ydxxzxt/service/verify", jsonParam); 

   	//String sr=HttpRequest.sendPost("http://localhost:8080/ydxxzxt/service/postBw", jsonParam); 
    		//String sr=HttpRequest.sendPost("http://20.1.11.54:8080/ydxxzxt/service/postBw", jsonParam); 

          //String sr=HttpRequest.sendPost("http://localhost:8080/ydxxzxt/service/queryCached", jsonParam); 
    		//String sr=HttpRequest.sendPost("http://20.1.11.54:8080/ydxxzxt/service/postBw", jsonParam); 

    		//http://20.1.11.54
           //String sr=HttpRequest.sendPost("http://localhost:8080/ydxxzxt/service/postResult",jsonParam );
//    	  String sr=HttpRequest.sendPost("http://localhost:8080/ydxxzxt/service/postResult",jsonParam );
    	//System.out.println("返回======"+sr);
    	
    
    	
    	
    	
    
       
    }
    

    
}

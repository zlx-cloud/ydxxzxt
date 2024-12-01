package net.sinodata.business.util;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
public class RequestUtil {
	
	

	/** 
     * 获取请求Body 
     * 
     * @param request 
     * @return 
	 * @throws IOException 
     */  
    public static String getBodyString(HttpServletRequest request) throws IOException {  
    	BufferedReader br = request.getReader();
    	String str, wholeStr = "";
    	while((str = br.readLine()) != null){
    	wholeStr += str;
    	}
    	//System.out.println(wholeStr);

    	return wholeStr;
    }
    
  //二进制读取

    public static String binaryReader(HttpServletRequest request) throws IOException {
    	ServletInputStream ris = request.getInputStream();  
        StringBuilder content = new StringBuilder();  
        byte[] b = new byte[1024];  
        int lens = -1;  
        while ((lens = ris.read(b)) > 0) {  
            content.append(new String(b, 0, lens));  
        }  
        String strcont = content.toString();// 内容  
        return strcont;
    }
	/** 
     * 网站是否正常运转 
     * @return 
     */  
    public static boolean isWebSiteNormal(String urlStr){  
        try {  
            if(urlStr != null && urlStr.indexOf("://") <= 0 ){  
                urlStr = "http://"+urlStr;  
            }  
            URL url = new URL(urlStr);  
            URLConnection con = url.openConnection();  
            BufferedReader in = new BufferedReader(new java.io.InputStreamReader(con  
                    .getInputStream()));  
            con.setConnectTimeout(2000);  
            con.setReadTimeout(6000);  
            String s = "";  
            while ((s = in.readLine()) != null) {  
                if (s.length() > 0) {  
                    return true;  
                }  
            }  
            in.close();  
        } catch (Exception e) {  
           
        }  
        return false;  
    }  
      
    /** 
     * 查看Tomcat7服务是否正在运行 
     * @return 
     */  
    public static boolean isTomcatServiceAlive(){  
        try {  
            Process process = Runtime.getRuntime().exec("lsof -i tcp:8080");  
            InputStreamReader ir = new InputStreamReader(process.getInputStream());  
            LineNumberReader input = new LineNumberReader(ir);  
            String line;  
            while ((line = input.readLine()) != null) {  
                if(line != null && !"".equals(line)){  
                    return true;  
                }         
            }          
        } catch (java.io.IOException e) {       
          
        }  
        return false;  
    }  
      
    /** 
     * 获取tomcat监听的进程 
     * @return 
     */  
    public static String getTomcatTcpIpListener(){  
        StringBuffer str = new StringBuffer();  
        try {  
            Process process = Runtime.getRuntime().exec("lsof -i tcp:8080");  
            InputStreamReader ir = new InputStreamReader(process.getInputStream());  
            LineNumberReader input = new LineNumberReader(ir);  
            String line;  
            while ((line = input.readLine()) != null) {  
                if(line != null && !"".equals(line)){  
                    str.append(line).append("\n");  
                }         
            }          
        } catch (java.io.IOException e) {  
          
        }  
        return str.toString();  
    }  
      
    /** 
     * 获取tomcat监听的进程 
     * @param content 
     * @return 
     */  
    public static Set<Integer> getUids(String content){  
        Pattern pattern = Pattern.compile("java\\s*[0-9]*");  
        Matcher matcher = pattern.matcher(content);  
        Pattern pattern2 = Pattern.compile("[0-9]+");  
        Set<Integer> uidSet = new HashSet<Integer>();  
        while(matcher.find()){  
            Matcher matcher2 = pattern2.matcher(matcher.group());  
            if(matcher2.find()){  
                if(matcher2.group() != null && !"".equals(matcher2.group())){  
                    uidSet.add(Integer.parseInt(matcher2.group()));  
                }  
                      
            }  
        }  
        return uidSet;  
    }  
      
    public static void main(String[] args) {  
    //System.out.println(	isWebSiteNormal("http://20.1.11.54:8080/ydxxzxt/login"));
    //System.out.println(	isTomcatServiceAlive());
    }
}

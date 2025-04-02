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
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import com.alibaba.fastjson.JSONObject;

import sun.misc.BASE64Encoder;

@SuppressWarnings("deprecation")
public class HttpRequest {

	public static String sendGetRequest(String url, Map<String, String> queryParams) {
		String result = "";
		BufferedReader in = null;
		try {
			StringBuffer urlBuffer = new StringBuffer(url);
			urlBuffer.append("?");
			queryParams.forEach((key, value) -> urlBuffer.append(key).append("=").append(value).append("&"));
			String myUrl = urlBuffer.substring(0, urlBuffer.length() - 1);
			URL realUrl = new URL(myUrl);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			// System.out.println("发送GET请求出现异常！" + e);
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

	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				// System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			// System.out.println("发送GET请求出现异常！" + e);
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

	/**
	 * 发送HttpPost请求
	 * 
	 * @param strURL 服务地址
	 * @param params json字符串,例如: "{ \"id\":\"12345\" }" ;其中属性名必须带双引号<br/>
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
			// for(String key : map.keySet()){
			// connection.setRequestProperty(key,map.get(key).toString()); // 设置Header
			// }
			// 设置是否向HttpURLConnection输出
			connection.setDoOutput(true);
			// 设置是否从httpUrlConnection读入
			connection.setDoInput(true);
			// 设置是否使用缓存
			connection.setUseCaches(false);
			// 设置参数类型是json格式
			connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			connection.connect();
			// String body = requestString;
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
		int flen = 0;
		File xmlfile = new File("D:/home/1.jpg");
		try {
			InputStream in = new FileInputStream(xmlfile);
			flen = (int) xmlfile.length();
			strBuffer = new byte[flen];
			in.read(strBuffer, 0, flen);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		attString = new String(strBuffer); // 构建String时，可用byte[]类型，

		return attString;
	}

	/**
	 * 将图片文件转为字符串
	 * 
	 * @param imgFile
	 * @return
	 */
	public static String getImageStr(String imgFile) {
		// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
		// String imgFile = "d:\\111.jpg";// 待处理的图片
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

}

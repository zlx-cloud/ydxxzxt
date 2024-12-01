package net.sinodata.business.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import net.sf.json.JSONObject;
import net.sinodata.business.entity.IntefaceFwzyzcb;
import net.sinodata.business.entity.InterfaceFwzysqb;
import net.sinodata.business.service.ServeManageService;

@Controller
@RequestMapping(value = "/InterfaceManage")
public class InterfaceManageController {

	@Autowired
	ServeManageService serveManageService;

	@RequestMapping(value = "/ServiceManage", method = RequestMethod.POST)
	@ResponseBody
	@Consumes("application/json;charset=UTF-8")
	@Produces("application/json;charset=UTF-8")
	public Object ServiceManage(@RequestBody String jsonInput) {
		Map<String, Object> result = new HashMap<String, Object>();
		JSONObject json = JSONObject.fromObject(jsonInput);
		JSONObject FWQQ_NR = (JSONObject) json.get("FWQQ_NR");
		if(FWQQ_NR.isEmpty()){
			result.put("message", "缺少FWQQ_NR");
			return JSON.toJSON(result);
		}
		String method = FWQQ_NR.optString("method");
		if("".equals(method)){
			result.put("message", "缺少method");
			return JSON.toJSON(result);
		}
		JSONObject params = (JSONObject) FWQQ_NR.get("params");
		String interfaceResult = InterfaceManageController.post(params.toString(),
				"http://localhost:8080"+File.separator+"ydxxzxt"+File.separator
				+"InterfaceManage"+File.separator+method);
				//"http://localhost:8080/ydxxzxt/InterfaceManage/"+method);
		return JSON.toJSON(interfaceResult);
	}
	
	
	/**
	 * 查询服务注册信息
	 * @param jsonInput
	 * @return
	 */
	@RequestMapping(value = "/queryFW", method = RequestMethod.POST)
	@ResponseBody
	@Consumes("application/json;charset=UTF-8")
	@Produces("application/json;charset=UTF-8")
	public Object queryFW(@RequestBody String jsonInput) {
		Map<String, Object> result = new HashMap<String, Object>();// 返回的结果集
		JSONObject json = JSONObject.fromObject(jsonInput);// 入参
		String FWBS = json.optString("FWBS");// 服务标识
		String FWMC = json.optString("FWMC");// 服务名称
		List<IntefaceFwzyzcb> data = serveManageService.queryFW(FWBS, FWMC);// 查询的结果
		result.put("result", data);
		return JSON.toJSON(result);
	}

	/**
	 * 启动服务
	 * @param jsonInput
	 * @return
	 */
	@RequestMapping(value = "/startFW", method = RequestMethod.POST)
	@ResponseBody
	@Consumes("application/json;charset=UTF-8")
	@Produces("application/json;charset=UTF-8")
	public Object startFW(@RequestBody String jsonInput) {
		Map<String, Object> result = new HashMap<String, Object>();// 返回的结果集
		JSONObject json = JSONObject.fromObject(jsonInput);// 入参
		String FWBS = json.optString("FWBS");// 服务标识
		if ("".equals(FWBS)) {
			result.put("message", "请输入服务标识");
		} else {
			String[] arr = FWBS.split(",");
			StringBuffer sb = new StringBuffer();
			for (String i : arr) {
				sb.append("'" + i + "',");
			}
			String fwbss = sb.substring(0, sb.length() - 1);
			int num = serveManageService.startFW(fwbss);
			result.put("result", num+"项服务启动");
		}
		return JSON.toJSON(result);
	}

	/**
	 * 停止服务
	 * @param jsonInput
	 * @return
	 */
	@RequestMapping(value = "/stopFW", method = RequestMethod.POST)
	@ResponseBody
	@Consumes("application/json;charset=UTF-8")
	@Produces("application/json;charset=UTF-8")
	public Object stopFW(@RequestBody String jsonInput) {
		Map<String, Object> result = new HashMap<String, Object>();// 返回的结果集
		JSONObject json = JSONObject.fromObject(jsonInput);// 入参
		String FWBS = json.optString("FWBS");// 服务标识
		if ("".equals(FWBS)) {
			result.put("message", "请输入服务标识");
		} else {
			String[] arr = FWBS.split(",");
			StringBuffer sb = new StringBuffer();
			for (String i : arr) {
				sb.append("'" + i + "',");
			}
			String fwbss = sb.substring(0, sb.length() - 1);
			int num = serveManageService.stopFW(fwbss);
			result.put("result", num+"项服务停止");
		}
		return JSON.toJSON(result);
	}
	
	/**
	 * 查询权限
	 * @param jsonInput
	 * @return
	 */
	@RequestMapping(value = "/queryFWSQ", method = RequestMethod.POST)
	@ResponseBody
	@Consumes("application/json;charset=UTF-8")
	@Produces("application/json;charset=UTF-8")
	public Object queryFWSQ(@RequestBody String jsonInput) {
		Map<String, Object> result = new HashMap<String, Object>();// 返回的结果集
		
		JSONObject json = JSONObject.fromObject(jsonInput);// 入参
		String param1 = json.optString("param1");
		String param2 = json.optString("param2");
		
		Map<String, Object> params = new HashMap<String, Object>();// 查询参数
		if("".equals(param1)&&"".equals(param2)){
		}else if(!"".equals(param1)&&"".equals(param2)){
			params.put("flag", "1");
			params.put("param1", param1);
		}else if("".equals(param1)&&!"".equals(param2)){
			params.put("flag", "1");
			params.put("param1", param2);
		}else{
			params.put("flag", "2");
			params.put("param1", param1);
			params.put("param2", param2);
		}
		List<InterfaceFwzysqb> data = serveManageService.queryFWSQ(params);// 查询的结果
		result.put("result", data);
		return JSON.toJSON(result);
	}
	
	/**
	 * 添加权限
	 * @param jsonInput
	 * @return
	 */
	@RequestMapping(value = "/addFWSQ", method = RequestMethod.POST)
	@ResponseBody
	@Consumes("application/json;charset=UTF-8")
	@Produces("application/json;charset=UTF-8")
	public Object addFWSQ(@RequestBody String jsonInput) {
		Map<String, Object> result = new HashMap<String, Object>();// 返回的结果集
		
		JSONObject json = JSONObject.fromObject(jsonInput);// 入参
		String FWCYFBH = json.optString("FWCYFBH");// 服务参与方标识
		String FWBS = json.optString("FWBS");// 服务标识
		
		if("".equals(FWCYFBH)&&"".equals(FWBS)){
			result.put("message", "请输入服务参与方标识和服务标识");
		}else if(!"".equals(FWCYFBH)&&"".equals(FWBS)){
			result.put("message", "请输入服务标识");
		}else if("".equals(FWCYFBH)&&!"".equals(FWBS)){
			result.put("message", "请输入服务参与方标识");
		}else{
			int num = serveManageService.insertFWSQ(FWCYFBH, FWBS);
			result.put("result", "赋予"+num+"项权限");
		}
		return JSON.toJSON(result);
	}
	
	/**
	 * 删除权限
	 * @param jsonInput
	 * @return
	 */
	@RequestMapping(value = "/deleteFWSQ", method = RequestMethod.POST)
	@ResponseBody
	@Consumes("application/json;charset=UTF-8")
	@Produces("application/json;charset=UTF-8")
	public Object deleteFWSQ(@RequestBody String jsonInput) {
		Map<String, Object> result = new HashMap<String, Object>();// 返回的结果集
		
		JSONObject json = JSONObject.fromObject(jsonInput);// 入参
		String param1 = json.optString("param1");
		String param2 = json.optString("param2");
		
		if("".equals(param1)&&"".equals(param2)){
			result.put("message", "请输入服务参与方标识和服务标识");
			return JSON.toJSON(result);
		}
		
		Map<String, Object> params = new HashMap<String, Object>();// 查询参数
		if(!"".equals(param1)&&"".equals(param2)){
			params.put("flag", "1");
			params.put("param1", param1);
		}else if("".equals(param1)&&!"".equals(param2)){
			params.put("flag", "1");
			params.put("param1", param2);
		}else{
			params.put("flag", "2");
			params.put("param1", param1);
			params.put("param2", param2);
		}
		int num = serveManageService.deleteFWSQ(params);
		result.put("result", "删除"+num+"项权限");
		return JSON.toJSON(result);
	}
	

	/**
	 * 测试接口
	 * @param jsonInput
	 * @return
	 */
	@RequestMapping(value = "/queryTest", method = RequestMethod.POST)
	@ResponseBody
	public Object queryTest(@RequestBody String jsonInput) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("msg", "访问成功");
		return JSON.toJSON(result);
	}
	
	
	/**
	 * 测试接口
	 * @param jsonInput
	 * @return
	 */
	@RequestMapping(value = "/queryFwjkdytj", method = RequestMethod.POST)
	@ResponseBody
	public Object queryFwjkdytj(@RequestBody String jsonInput) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> info = serveManageService.queryFwjkdytj();// 查询的结果
		result.put("data", info);
		return JSON.toJSON(result);
	}
	
	
	/**
	 * 测试接口
	 * @param jsonInput
	 * @return
	 */
	@RequestMapping(value = "/queryZxdytj", method = RequestMethod.POST)
	@ResponseBody
	public Object queryZxdytj(@RequestBody String jsonInput) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> info = serveManageService.queryZxdytj();// 查询的结果
		result.put("data", info);
		return JSON.toJSON(result);
	}
	
	
	/**
	 * 调用接口
	 * @param json 入参
	 * @param URL  接口地址
	 * @return
	 */
	public static String post(String json, String URL) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(URL);
		post.setHeader("Content-Type", "application/json");
		String result = "";
		try {
			StringEntity s = new StringEntity(json, "utf-8");
			s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
			post.setEntity(s);
			// 发送请求
			HttpResponse httpResponse = client.execute(post);
			// 获取响应输入流
			InputStream inStream = httpResponse.getEntity().getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "utf-8"));
			StringBuilder strber = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null)
				strber.append(line + "\n");
			inStream.close();
			result = strber.toString();
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// System.out.println("请求服务器成功，做相应处理");
			} else {
				// System.out.println("请求服务端失败");
			}
		} catch (Exception e) {
			// System.out.println("请求异常");
			throw new RuntimeException(e);
		}
		return result;
	}

	
	public static void main(String[] args) {
		/*
		// 查询服务注册信息
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FWBS", "001");
		map.put("FWMC", "a");
		JSONObject json = JSONObject.fromObject(map);
		System.out.println(json);
		String result = InterfaceManageController.post(json.toString(),
				"http://20.1.11.55:8080/ydxxzxt/InterfaceManage/queryFW");
		System.out.println(result);
		*/
		
		/*
		// 启动服务
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FWBS", "S00100000000000100001,S00100000000000100011,S00100000000000100012");
		JSONObject json = JSONObject.fromObject(map);
		System.out.println(json);
		String result = InterfaceManageController.post(json.toString(),
				"http://localhost:8080/ydxxzxt/InterfaceManage/startFW");
		System.out.println(result);
		*/
		
		/*
		// 停止服务
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FWBS", "S00100000000000100001,S00100000000000100011,S00100000000000100012");
		JSONObject json = JSONObject.fromObject(map);
		String result = InterfaceManageController.post(json.toString(),
				"http://localhost:8080/ydxxzxt/InterfaceManage/stopFW");
		System.out.println(result);
		*/
		
		/*
		// 查询权限
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("param1", "A00100000000000100005");
		map.put("param2", "S00111022300000001001");
		JSONObject json = JSONObject.fromObject(map);
		System.out.println(json);
		String result = InterfaceManageController.post(json.toString(),
				"http://localhost:8080/ydxxzxt/InterfaceManage/queryFWSQ");
		System.out.println(result);
		*/
		
		/*
		// 添加权限
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FWCYFBH", "12321");
		map.put("FWBS", "S00100000000000100011");
		JSONObject json = JSONObject.fromObject(map);
		//String result = InterfaceManageController.post(json.toString(),
				//"http://localhost:8080/ydxxzxt/InterfaceManage/addFWSQ");
		System.out.println(json);
		*/
		
		/*
		// 删除权限
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("param1", "12321");
		map.put("param2", "S00100000000000100011");
		JSONObject json = JSONObject.fromObject(map);
		String result = InterfaceManageController.post(json.toString(),
				"http://localhost:8080/ydxxzxt/InterfaceManage/deleteFWSQ");
		System.out.println(result);
		*/
		
		/*
		//接口入口测试
		//String str = "{\"FWQQ_NR\": {\"method\":\"queryFW\",\"params\":{\"FWBS\":\"001\",\"FWMC\":\"a\"}}}";
		String str = "{\"FWQQ_NR\": {\"method\":\"queryFWSQ\",\"params\":{}}}";
		String result = InterfaceManageController.post(str.toString(),
				"http://localhost:8080/ydxxzxt/InterfaceManage/ServiceManage");
		System.out.println(result);
		*/
		
		/*
		String str = "{"
			+"\"BWLYDKH\": \"36384\","
			+"\"BWLYIPDZ\": \"172.16.10.12\","
			+"\"FWBS\": \"S00111000000000022001\","
			+"\"FFBS\": \"FUN001\","
			+"\"FWQQSB_BH\": \"869661020828470\","
			+"\"FWQQZ_ZCXX\": \"A00100000000000100003\","
			+"\"FWQQ_BWBH\": \"SR020001011052201811091155259109911\","
			+"\"FWQQ_NR\": {\"method\":\"queryFW\",\"params\":{\"FWBS\":\"001\",\"FWMC\":\"a\"}},"
			+"\"FWQQ_RQSJ\": \"20181109115525\","
			+"\"FWQQ_SJSJLX\": \"service_request\","
			+"\"XXCZRY_GAJGJGDM\": \"110102000000\","
			+"\"XXCZRY_GMSFHM\": \"111111111111111110\","
			+"\"XXCZRY_XM\": \"李晨\""
			+"}";
		String result = InterfaceManageController.post(str.toString(),
				"http://20.1.11.52:5988/http/GA000Comm");
		System.out.println(result);
		*/
	}

}

package net.sinodata.business.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import net.sinodata.business.service.SSJKService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;
import net.sinodata.business.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping(value = "/ssjk")
public class SSJKController {
	//private static Logger logger = LoggerFactory.getLogger(SSJKController.class);
	@Autowired
	private SSJKService SSJKService;

	
	/**
	 * 接口端口监听
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "queryIntePort")
	public Object queryIntePort(HttpServletRequest request,
			HttpServletResponse response) {
		
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置时间格式
		List<String> sqlTimes = new ArrayList<String>();//sql查询条件用时间数组
		List<String> echartsTimes = new ArrayList<String>();//echarts展示用时间数组
		List<String> counts1 = new ArrayList<String>();//近1min内访问频次
		List<String> counts2 = new ArrayList<String>();//近1min内异常发生监测
		List<String> counts3 = new ArrayList<String>();//近1min内执行时长
		
		Calendar calendar = Calendar.getInstance();
		
		//获取当前时间
		String currentTime= dateformat.format(calendar.getTime());
		sqlTimes.add(currentTime);
		
		//获取1min内间隔5s的时间
		for(int i=0;i<12;i++){
			calendar.add(Calendar.SECOND, -5);
			sqlTimes.add(dateformat.format(calendar.getTime()));
		}
		
		//按时间顺序排序
		Collections.reverse(sqlTimes);
		
		//查询
		for(int i=0;i<sqlTimes.size()-1;i++){
			echartsTimes.add(sqlTimes.get(i+1).substring(11, 19));
			
			Integer count1 =  SSJKService.selectQqfwCount(sqlTimes.get(i), sqlTimes.get(i+1));
			counts1.add(count1.toString());
			
			Integer count2 =  SSJKService.selectYcCount(sqlTimes.get(i), sqlTimes.get(i+1));
			counts2.add(count2.toString());
			
			Integer count3 =  SSJKService.selectZxTime(sqlTimes.get(i), sqlTimes.get(i+1));
			counts3.add(count3.toString());
		}
		
		request.setAttribute("times", JSON.toJSON(echartsTimes));//日期
		request.setAttribute("counts1", JSON.toJSON(counts1));//次数
		request.setAttribute("counts2", JSON.toJSON(counts2));//次数
		request.setAttribute("counts3", JSON.toJSON(counts3));//次数
		
		return "business/intePortJK";
	}
	
	@RequestMapping(value = "selectQqfwCount", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object[] selectQqfwCount(HttpServletRequest req) {
		String result[] = new String[2];
		
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置时间格式
		
		Calendar calendar = Calendar.getInstance();
		
		String endTime = dateformat.format(calendar.getTime());
		calendar.add(Calendar.SECOND, -5);
		String startTime = dateformat.format(calendar.getTime());
		//System.out.println("========"+startTime+"========"+endTime);
		String  count = SSJKService.selectQqfwCount(startTime,endTime).toString();
		
		result[0] = count;
		result[1] = endTime.substring(11,19);
		return result;
	}
	
	
	@RequestMapping(value = "selectYcCount", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object[] selectYcCount(HttpServletRequest req) {
		String result[] = new String[2];
		
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置时间格式
		
		Calendar calendar = Calendar.getInstance();
		
		String endTime = dateformat.format(calendar.getTime());
		calendar.add(Calendar.SECOND, -5);
		String startTime = dateformat.format(calendar.getTime());
		String  count = SSJKService.selectYcCount(startTime,endTime).toString();
		
		result[0] = count;
		result[1] = endTime.substring(11,19);
		return result;
	}
	
	@RequestMapping(value = "selectZxTime", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object[] selectZxTime(HttpServletRequest req) {
		String result[] = new String[2];
		
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置时间格式
		
		Calendar calendar = Calendar.getInstance();
		
		String endTime = dateformat.format(calendar.getTime());
		calendar.add(Calendar.SECOND, -5);
		String startTime = dateformat.format(calendar.getTime());
		String  count = SSJKService.selectZxTime(startTime,endTime).toString();
		
		result[0] = count;
		result[1] = endTime.substring(11,19);
		return result;
	}
	
	@RequestMapping(value = "queryIntePortEchartsData", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object queryIntePortEchartsData(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultdata = new HashMap<String, Object>();
		List<Map<String, Object>> list = SSJKService.queryInPort();
		List<Map> y = new ArrayList<Map>();// y轴数据 成功
		List<String> x = new ArrayList<String>();
		List<String> ststus = new ArrayList<String>();
		for (Map<String, Object> map : list) {
			 Map<String, Object> map1= new HashMap<String, Object>();
			x.add(map.get("INTERNAME")+"");
			//x.add(map.get("IP")+"");
			if(map.get("STATUS").equals("1")){//'0','成功','1','失败'
				map1.put("value","-"+map.get("EXETIME"));
				map1.put("color","red");
			}
			
			if(map.get("STATUS").equals("0")){//'0','成功','1','失败','2'
				map1.put("value",map.get("EXETIME"));
				map1.put("color","green");
			}
			y.add(map1);
			ststus.add(map.get("IP") + "-" + map.get("EXESTATUS")+"-"+map.get("EXETIME")+"-"+map.get("PORT")+"-"+map.get("INTERNAME"));
		
		}
		net.sf.json.JSONArray yEchart = net.sf.json.JSONArray.fromObject(y);
		resultdata.put("x",  JSON.toJSON(x));
		resultdata.put("y", yEchart);
		resultdata.put("status",  JSON.toJSON(ststus));
		return JSON.toJSON(resultdata);
}
	
	@RequestMapping(value = "queryIntePortGrid", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object queryIntePortGrid(Page page,HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> condition = new HashMap<String, Object>();
		SearchResult result = (SearchResult) SSJKService.InPortPageList(page,condition);
		return result;
	}
	
	
	@RequestMapping(value = "queryByIP")
	public Object queryByIP(HttpServletRequest request,
			HttpServletResponse response) {
		
		String IP = request.getParameter("IP");
		request.setAttribute("IP", IP);
		return "business/IPPortssjk";
	}
	@RequestMapping(value = "queryAllIntePortEchartsData", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object queryAllIntePortEchartsData(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultdata = new HashMap<String, Object>();
		List<String> x = new ArrayList<String>();// x轴数据
		List<Map> y = new ArrayList<Map>();//
		List<String> status = new ArrayList<String>();//
		String IP = request.getParameter("IP");
		List<Map<String, Object>> list =SSJKService.queryByIP(IP, null);
		for (Map<String, Object> map : list) {
			 Map<String, Object> map1= new HashMap<String, Object>();
			 Map<String, Object> map2= new HashMap<String, Object>();
			 Map<String, Object> map3= new HashMap<String, Object>();
			 if(map.get("STATUS").equals("1")){//'0','成功','1','失败'
					map1.put("value","-"+map.get("EXETIME"));
					map1.put("symbol","droplet");
					map3.put("color", "red");
					map2.put("normal", map3);
					map1.put("itemStyle",map2);
				}
				
				if(map.get("STATUS").equals("0")){//'0','成功','1','失败','2'
					map1.put("value",map.get("EXETIME"));
					map1.put("symbol","droplet");
					map3.put("color", "green");
					map2.put("normal", map3);
					map1.put("itemStyle",map2);
				}
				
			x.add(map.get("EXE_TIME") + "");
			y.add(map1);
			//y.add(map.get("EXETIME") + "");
			status.add(map.get("EXE_TIME") + "-" + map.get("EXESTATUS") + "-"
					+ map.get("ID")+"-"+map.get("PORT"));
			
		}
		net.sf.json.JSONArray yEchart = net.sf.json.JSONArray.fromObject(y);
		resultdata.put("name", "");
		resultdata.put("IP", list.get(0).get("IP"));
		resultdata.put("x", JSON.toJSON(x));
		resultdata.put("y", yEchart);
		resultdata.put("status", JSON.toJSON(status));
		request.setAttribute("resultdata", resultdata);
		return JSON.toJSON(resultdata);
	}
	
	@RequestMapping(value = "queryByIPGrid", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object queryByIPGrid(Page page,HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String IP=request.getParameter("IP");
		if(StringUtil.isNotEmpty(IP)){
			
			condition.put("IP",IP);
		}
		SearchResult result = (SearchResult) SSJKService.PageIntePortList(page,condition);
		
		return result;
	}
	
	
	
	
	

}

package net.sinodata.business.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sinodata.business.entity.ConfigInfo;
import net.sinodata.business.entity.JschCommand;
import net.sinodata.business.service.FwtcService;
import net.sinodata.business.service.TjfxService;
import net.sinodata.business.util.HttpRequest;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/monitoring")
public class MonitoringController {
	
	@Autowired(required = false)
	private ConfigInfo configInfo;
	
	@Autowired
	TjfxService tjfxService;
	@Autowired
	FwtcService fwtcService;
	
	@RequestMapping(value = "/zx", method = RequestMethod.GET)
	public String zxMonitoring(HttpServletRequest request) {

		return "business/zxMonitoringManage";
	}
	@RequestMapping(value = "/yyrq", method = RequestMethod.GET)
	public String yyrqMonitoring(HttpServletRequest request) {

		return "business/yyrqMonitoringManage";
	}
	@RequestMapping(value = "/htjc", method = RequestMethod.GET)
	public String htjcMonitoring(HttpServletRequest request) {

		return "business/htjcMonitoringManage";
	}
	@RequestMapping(value = "/fwhd", method = RequestMethod.GET)
	public String fwxtMonitoring(HttpServletRequest request) {

		return "business/fwhdMonitoringManage";
	}
	
	//首页
	@RequestMapping(value = "/shouye", method = RequestMethod.GET)
	public String shouye(HttpServletRequest request) {
		return "business/tjfx/index";
	}
	
	//服务探测
	@RequestMapping(value = "/probe", method = RequestMethod.GET)
	public String probe(Model model, HttpServletRequest request) {
		request.setAttribute("fw", fwtcService.queryFw());
		return "business/fwTcManage";
	}
	
	@RequestMapping(value = "getFf", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getFf(HttpServletRequest req) {
		String fwbs = req.getParameter("fwbs");
		List<Map<String,Object>> ff = new ArrayList<Map<String,Object>>();
		ff = fwtcService.queryFf(fwbs);
		return ff;
	}
	
	//总线活动监测
	@RequestMapping(value = "/serverMonitor", method = RequestMethod.GET)
	public String linuxjc(HttpServletRequest request) {
		
		request.setAttribute("serverurl", JSON.toJSON(configInfo.getServerurl()));
		request.setAttribute("serverurl1", JSON.toJSON(configInfo.getServerurl1()));
		request.setAttribute("serverurl2", JSON.toJSON(configInfo.getServerurl2()));
		request.setAttribute("serverurl3", JSON.toJSON(configInfo.getServerurl3()));
		request.setAttribute("serverurl4", JSON.toJSON(configInfo.getServerurl4()));
		return "business/tjfx/serverMonitor";
	}
	
	
	//当前活跃服务
	@RequestMapping(value = "/currentActiveService", method = RequestMethod.GET)
	public String currentActiveService(HttpServletRequest request) {
		List<Map<String, Object>> list = tjfxService.selectCurrentActiveServiceData();
		//源系统
		List<String> names = new ArrayList<String>();
		//个数
		List<String> values = new ArrayList<String>();
		for (Map<String, Object> i : list) {
			names.add(i.get("Z").toString());
			values.add(i.get("COUNT").toString());
		}
		request.setAttribute("names", JSON.toJSON(names));
		request.setAttribute("values", JSON.toJSON(values));
		return "business/tjfx/currentActiveService";
	}
	@RequestMapping(value = "queryCurrentActiveServiceGrid", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object queryFwEventLog(Page page,HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> condition = new HashMap<String, Object>();
		SearchResult result = (SearchResult) tjfxService.CurrentActiveServicePageList(page,condition);
		return result;
	}
	
	//服务日调用量统计
	@RequestMapping(value = "/serviceUseByDay", method = RequestMethod.GET)
	public String serviceUseByDay(HttpServletRequest request) {
		List<Map<String, Object>> list = tjfxService.serviceUseByDay();
		//日期
		List<String> times = new ArrayList<String>();
		//个数
		List<String> values = new ArrayList<String>();
		for (Map<String, Object> i : list) {
			times.add(i.get("TIME").toString());
			values.add(i.get("COUNT").toString());
		}
		request.setAttribute("times", JSON.toJSON(times));
		request.setAttribute("values", JSON.toJSON(values));
		return "business/tjfx/serviceUseByDay";
	}
	@RequestMapping(value = "queryServiceUseByDayGrid", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object queryServiceUseByDayGrid(Page page,HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> condition = new HashMap<String, Object>();
		SearchResult result = (SearchResult) tjfxService.ServiceUseByDayPageList(page,condition);
		return result;
	}
	
	//服务周调用量统计
	@RequestMapping(value = "/serviceUseByWeek", method = RequestMethod.GET)
	public String serviceUseByWeek(HttpServletRequest request) {
		
		List<String> times = new ArrayList<String>();//日期
		List<String> values = new ArrayList<String>();//个数
		
		SimpleDateFormat mondayFormater=new SimpleDateFormat("yyyyMMdd000000");//周一格式
		SimpleDateFormat sundayFormater=new SimpleDateFormat("yyyyMMdd235959");//周日格式
		SimpleDateFormat currentDateFormater=new SimpleDateFormat("yyyyMMddHHmmss");//当前时间格式
		
		Calendar calendar=new GregorianCalendar();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		String firstDay = mondayFormater.format(calendar.getTime());//本周第一天
		
		Calendar nowTime = Calendar.getInstance();
		String currentTime= currentDateFormater.format(nowTime.getTime());//当前时间
		
		times.add(firstDay.substring(0, 10)+"至"+currentTime.substring(0, 10));//添加本周数据
		values.add(tjfxService.serviceUseByWeek(firstDay, currentTime).toString());//添加本周数据
		
		String monday,sunday;
		for(int i=1;i<=3;i++){
				calendar.add(Calendar.DAY_OF_MONTH,-7);
				monday=mondayFormater.format(calendar.getTime());//上周的周一
			   
			   calendar.add(Calendar.DAY_OF_MONTH,6);
			   sunday=sundayFormater.format(calendar.getTime());//上周的周日
			   
			   times.add(monday.substring(0, 10)+"至"+sunday.substring(0, 10));
			   values.add(tjfxService.serviceUseByWeek(monday, sunday).toString());
			 
			  calendar.add(Calendar.DAY_OF_MONTH,-6);//每次循环结束cal对象的时间均为周一
		   }
		
		Collections.reverse(times);
		Collections.reverse(values);
		request.setAttribute("times", JSON.toJSON(times));
		request.setAttribute("values", JSON.toJSON(values));
		return "business/tjfx/serviceUseByWeek";
	}
	@RequestMapping(value = "queryServiceUseByWeekGrid", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object queryServiceUseByWeekGrid(Page page,HttpServletRequest request,
			HttpServletResponse response) {
		SimpleDateFormat formater=new SimpleDateFormat("yyyyMMdd000000");
		Calendar cal=new GregorianCalendar();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		cal.add(Calendar.DAY_OF_MONTH,-21);
		String monday = formater.format(cal.getTime());
		Map<String, Object> condition = new HashMap<String, Object>();
		condition.put("monday", monday);
		SearchResult result = (SearchResult) tjfxService.ServiceUseByWeekPageList(page,condition);
		return result;
	}
	
	//各分局调用量占比统计
	@RequestMapping(value = "/serviceUseByOrg", method = RequestMethod.GET)
	public String serviceUseByOrg(HttpServletRequest request) {
		List<Map<String, Object>> list = tjfxService.serviceUseByOrg();
		//名称
		List<String> names = new ArrayList<String>();
		
		JSONArray data = new JSONArray();
		for (Map<String, Object> i : list) {
			names.add(i.get("Z")==null?"": i.get("Z").toString());
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("value", i.get("COUNT").toString());
			maps.put("name", i.get("Z")==null?"": i.get("Z").toString());
			data.put(maps);
		}
		request.setAttribute("names", JSON.toJSON(names));
		request.setAttribute("data", data);
		return "business/tjfx/serviceUseByOrg";
	}
	@RequestMapping(value = "queryServiceUseByOrgGrid", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object queryServiceUseByOrgGrid(Page page,HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> condition = new HashMap<String, Object>();
		SearchResult result = (SearchResult) tjfxService.ServiceUseByOrgPageList(page, condition);
		return result;
	}
	
	//各分局调用量日统计
	@RequestMapping(value = "/serviceUseByOrgAndDay", method = RequestMethod.GET)
	public String serviceUseByOrgAndDay(HttpServletRequest request) {
		//近七天的日期
		List<String> times = new ArrayList<String>();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");//设置日期格式为"yyyyMMdd"
		Calendar time = Calendar.getInstance();
		times.add(format.format(time.getTime()));//添加当前日期
		
		for(int i=0;i<=5;i++){
			time.add(Calendar.DATE, -1);
			times.add(format.format(time.getTime()));//添加前一天
		}
		Collections.reverse(times);//日期由远到近
		
		//先查询近7天内调用服务的机构代码及机构名称
		List<Map<String, Object>> list = tjfxService.selectOrgCodeAndName();
				
		//机构名称
		List<String> names = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			if(!names.contains(list.get(i).get("X"))) {
				names.add(list.get(i).get("X").toString());
			}
		}
		//机构名称及近七天的服务调用次数
		JSONArray array = new JSONArray();
		
		for (int i = 0; i < names.size(); i++) {
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("name", names.get(i)==null?"": names.get(i).toString());
			maps.put("type","line");
			//某机构在固定某一天调用服务的次数
			List<String> counts = new ArrayList<String>();
			
			for (int j = 0; j < list.size(); j++) {
				for(int k=0;k<times.size();k++){
					if(times.get(k).equals(list.get(j).get("Z"))&&names.get(i).equals(list.get(j).get("X"))) {
						String count = list.get(j).get("COUNT").toString();
						counts.add(count);
					}
				}
			}
			maps.put("data",counts);
			array.put(maps);
		}
/*		for (Map<String, Object> i : list) {
//			names.add(i.get("JGMC")==null?"": i.get("JGMC").toString());//添加机构名称
			
			Map<String, Object> maps = new HashMap<String, Object>();
			maps.put("name", i.get("X")==null?"": i.get("X").toString());
			maps.put("type","line");
			
			//某机构在固定某一天调用服务的次数
			List<String> counts = new ArrayList<String>();
			
			for(int k=0;k<times.size();k++){
				if(times.get(k).equals(i.get("Z"))&&i.get("X").equals(maps.get("name"))) {
					String count = i.get("COUNT").toString();
					counts.add(count);
				}else {
					String count ="0";
					counts.add(count);
				}
				
				//查询某机构在固定某一天调用服务的次数
//				String count = tjfxService.selectCountByOrgAndDay(times.get(k), i.get("XXCZRY_GAJGJGDM").toString()).toString();
//				counts.add(count);
			}
			maps.put("data",counts);
			array.put(maps);
		}*/
		request.setAttribute("names", JSON.toJSON(names));//机构名称
		request.setAttribute("times", JSON.toJSON(times));//近七天的日期
		request.setAttribute("data", array);
		return "business/tjfx/serviceUseByOrgAndDay";
	}
	@RequestMapping(value = "queryServiceUseByOrgAndDayGrid", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object queryServiceUseByOrgAndDayGrid(Page page,HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> condition = new HashMap<String, Object>();
		SearchResult result = (SearchResult) tjfxService.ServiceUseByOrgAndDayPageList(page, condition);
		return result;
	}
	
	//服务调用Top10
	@RequestMapping(value = "/serviceUseTopTen", method = RequestMethod.GET)
	public String serviceUseTopTen(HttpServletRequest request) {
		List<Map<String, Object>> list = tjfxService.selectServiceUseTopTen();
		//源系统
		List<String> names = new ArrayList<String>();
		//个数
		List<String> values = new ArrayList<String>();
		for (Map<String, Object> i : list) {
			names.add(i.get("Z")==null?"": i.get("Z").toString());
			values.add(i.get("COUNT").toString());
		}
		request.setAttribute("names", JSON.toJSON(names));
		request.setAttribute("values", JSON.toJSON(values));
		return "business/tjfx/serviceUseTopTen";
	}
	@RequestMapping(value = "queryServiceUseTopTenGrid", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object queryServiceUseTopTenGrid(Page page,HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> condition = new HashMap<String, Object>();
		SearchResult result = (SearchResult) tjfxService.ServiceUseTopTenPageList(page, condition);
		return result;
	}
	
	//总线服务调用
	@RequestMapping(value = "/serviceUseByZX", method = RequestMethod.GET)
	public String serviceUseByZX(HttpServletRequest request) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMddHHmmss");//设置时间格式
		List<String> sqlTimes = new ArrayList<String>();//sql查询条件用时间数组
		List<String> echartsTimes = new ArrayList<String>();//echarts展示用时间数组
		List<String> counts = new ArrayList<String>();//近1min内总线服务调用数量
		
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
		
		//查询近1min内总线服务调用数量
		for(int i=0;i<sqlTimes.size()-1;i++){
			echartsTimes.add(sqlTimes.get(i+1).substring(8, 14));
			
			Integer count =  tjfxService.selectCurrentZXInfo(sqlTimes.get(i), sqlTimes.get(i+1));
			counts.add(count.toString());
		}
		
		request.setAttribute("times", JSON.toJSON(echartsTimes));//机构名称
		request.setAttribute("counts", JSON.toJSON(counts));//近七天的日期
		return "business/tjfx/serviceUseByZX";
	}
	@RequestMapping(value = "selectCurrentZXCount", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object[] selectCurrentZXCount(HttpServletRequest req) {
		String result[] = new String[2];
		
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyyMMddHHmmss");//设置时间格式
		
		Calendar calendar = Calendar.getInstance();
		
		String endTime = dateformat.format(calendar.getTime());
		calendar.add(Calendar.SECOND, -5);
		String startTime = dateformat.format(calendar.getTime());
		
		String  count = tjfxService.selectCurrentZXInfo(startTime,endTime).toString();
		
		result[0] = count;
		result[1] = endTime.substring(8,14);
		return result;
	}
	@RequestMapping(value = "queryZXInfoGrid", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object queryZXInfoGrid(Page page,HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> condition = new HashMap<String, Object>();
		
		SimpleDateFormat dateformat=new SimpleDateFormat("yyyyMMddHHmmss");//设置时间格式
		
		Calendar calendar = Calendar.getInstance();
		
		String endTime = dateformat.format(calendar.getTime());
		
		calendar.add(Calendar.MINUTE, -1);
		String startTime = dateformat.format(calendar.getTime());
		
		condition.put("startTime", startTime);
		condition.put("endTime", endTime);
		
		SearchResult result = (SearchResult) tjfxService.ZXInfoPageList(page, condition);
		return result;
	}
	
	@RequestMapping(value = "test", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object test(Page page,HttpServletRequest request,
			HttpServletResponse response) {
		String jsonParam=request.getParameter("params");
		String urlwifi=request.getParameter("urlwifi");
		String result=HttpRequest.sendPost(urlwifi, jsonParam);
		//System.out.println("result========================="+result);
		return result;
	}
	
	// 当前活跃服务
	@RequestMapping(value = "/getCurrentActiveServiceCharts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getCurrentActiveServiceCharts() {
		List<Map<String, Object>> list = tjfxService.selectCurrentActiveServiceData();
		return list;
	}

	// 各分局调用量占比统计
	@RequestMapping(value = "/getServiceUseByOrgCharts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getServiceUseByOrgCharts() {
		List<Map<String, Object>> list = tjfxService.serviceUseByOrg();
		return list;
	}

	// 服务调用Top10
	@RequestMapping(value = "/getServiceUseTopTenCharts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getServiceUseTopTenCharts() {
		List<Map<String, Object>> list = tjfxService.selectServiceUseTopTen();
		return list;
	}

	// 服务日调用量统计
	@RequestMapping(value = "/getServiceUseByDayCharts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getServiceUseByDayCharts() {
		List<Map<String, Object>> list = tjfxService.serviceUseByDay();
		return list;
	}

	// 各分局调用量日统计
	@RequestMapping(value = "/getServiceUseByOrgAndDayCharts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getServiceUseByOrgAndDayCharts() {
		List<Map<String, Object>> list = tjfxService.selectOrgCodeAndName();
		return list;
	}

	// 服务日调用量统计
	@RequestMapping(value = "/getServiceUseByWeekCharts", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getServiceUseByWeekCharts() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 服务周调用量统计
		List<String> serviceUseByWeekTimes = new ArrayList<String>();
		List<String> serviceUseByWeekValues = new ArrayList<String>();
		SimpleDateFormat mondayFormater = new SimpleDateFormat("yyyyMMdd000000");
		SimpleDateFormat sundayFormater = new SimpleDateFormat("yyyyMMdd235959");
		SimpleDateFormat currentDateFormater = new SimpleDateFormat("yyyyMMddHHmmss");
		Calendar calendar = new GregorianCalendar();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
		String firstDay = mondayFormater.format(calendar.getTime());
		Calendar nowtime = Calendar.getInstance();
		String currentTime = currentDateFormater.format(nowtime.getTime());
		serviceUseByWeekTimes.add(firstDay.substring(0, 10) + "至" + currentTime.substring(0, 10));
		serviceUseByWeekValues.add(tjfxService.serviceUseByWeek(firstDay, currentTime).toString());
		String monday, sunday;
		for (int i = 1; i <= 3; i++) {
			calendar.add(Calendar.DAY_OF_MONTH, -7);
			monday = mondayFormater.format(calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, 6);
			sunday = sundayFormater.format(calendar.getTime());
			serviceUseByWeekTimes.add(monday.substring(0, 10) + "至" + sunday.substring(0, 10));
			serviceUseByWeekValues.add(tjfxService.serviceUseByWeek(monday, sunday).toString());
			calendar.add(Calendar.DAY_OF_MONTH, -6);
		}
		Collections.reverse(serviceUseByWeekTimes);
		Collections.reverse(serviceUseByWeekValues);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("X", serviceUseByWeekTimes);
		map.put("Y", JSON.toJSON(serviceUseByWeekValues));
		list.add(map);
		return list;
	}
	
	
	
		// 服务器测试
		@RequestMapping(value = "/getServer", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public Object getServer() {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			JschCommand jschCommand = new JschCommand();
			list=jschCommand.executeCommand(configInfo.getServerurl(), 
		    		configInfo.getServername(), configInfo.getServerpassword(),configInfo.getServerport());
			return list;
		}
		
		// 服务器1测试
		@RequestMapping(value = "/getServer1", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public Object getServer1() {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			JschCommand jschCommand = new JschCommand();
			list=jschCommand.executeCommand(configInfo.getServerurl1(),
		    		configInfo.getServername1(), configInfo.getServerpassword1(),configInfo.getServerport1());
			return list;
		}
		
		
		// 服务2测试
		@RequestMapping(value = "/getServer2", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public Object getServer2() {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			JschCommand jschCommand = new JschCommand();
			list=jschCommand.executeCommand(configInfo.getServerurl2(), 
		    		configInfo.getServername2(), configInfo.getServerpassword2(),configInfo.getServerport2());
			return list;
		}
		
		
		// 服务3测试
		@RequestMapping(value = "/getServer3", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public Object getServer3() {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			JschCommand jschCommand = new JschCommand();
			list=jschCommand.executeCommand(configInfo.getServerurl3(), 
		    		configInfo.getServername3(), configInfo.getServerpassword3(),configInfo.getServerport3());
			return list;
		}
		
		
		// 服务4测试
		@RequestMapping(value = "/getServer4", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public Object getServer4() {
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			JschCommand jschCommand = new JschCommand();
			list=jschCommand.executeCommand(configInfo.getServerurl4(), 
		    		configInfo.getServername4(), configInfo.getServerpassword4(),configInfo.getServerport4());
			//System.out.println(list);
			return list;
		}
		
	// 服务探测
	@RequestMapping(value = "/probeInterface", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object probeInterface(HttpServletRequest request) {
		String fwbs = request.getParameter("fwbs");
		String fwzxdz = request.getParameter("fwzxdz");
		Calendar ca = Calendar.getInstance();
		String time = ca.get(ca.YEAR) + ""
				+ (ca.get(ca.MONTH) >= 9 ? (ca.get(ca.MONTH) + 1) : "0" + (ca.get(ca.MONDAY) + 1)) + ""
				+ (ca.get(ca.DATE) >= 10 ? ca.get(ca.DATE) : "0" + ca.get(ca.DATE)) + ""
				+ (ca.get(ca.HOUR_OF_DAY) >= 10 ? ca.get(ca.HOUR_OF_DAY) : "0" + ca.get(ca.HOUR_OF_DAY)) + ""
				+ (ca.get(ca.MINUTE) >= 10 ? ca.get(ca.MINUTE) : "0" + ca.get(ca.MINUTE)) + ""
				+ (ca.get(ca.SECOND) >= 10 ? ca.get(ca.SECOND) : "0" + ca.get(ca.SECOND)) + ""
				+ (ca.get(ca.MILLISECOND) >= 100 ? ca.get(ca.MILLISECOND)
						: (ca.get(ca.MILLISECOND) >= 10 ? "0" + ca.get(ca.MILLISECOND)
								: "00" + ca.get(ca.MILLISECOND)));
	  	
		JSONObject jsonObject=new JSONObject();
    	jsonObject.put("FWBS",fwbs);
    	jsonObject.put("XXCZRY_XM", "lichen");
    	jsonObject.put("XXCZRY_GMSFHM", "11111112312335649");
    	jsonObject.put("FWQQ_RQSJ", time.substring(0,14));
    	jsonObject.put("FWQQ_BWBH", "SR020001031150"+time+"1234");
    	jsonObject.put("XXCZRY_GAJGJGDM", "110108183000");
    	jsonObject.put("FFBS", "FUN999");
      	jsonObject.put("BWLYDKH", "80");
      	String zcxx = fwtcService.queryFwtgzbs(fwbs);
      	String qqcs = fwtcService.queryFwqqcs(fwbs);
    	jsonObject.put("FWQQZ_ZCXX", zcxx);
    	jsonObject.put("FWQQ_SJSJLX","service_request");
    	jsonObject.put("BWLYIPDZ", "20.1.31.150");
    	jsonObject.put("FWQQSB_BH", "869661020828490");
    	jsonObject.put("FWQQ_NR", qqcs);
    	
  
		String sr = HttpRequest.sendPost(fwzxdz, jsonObject.toString());
		if(!sr.contains("FWBS")) {
			int a =tjfxService.insertFwzytcsbrzb(fwbs, time.substring(0,14));
			sr="fail";
		}
		return sr;
	}
}

package net.sinodata.business.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sinodata.business.entity.Fwzyqqbwcjb;
import net.sinodata.business.entity.FwzyqqbwcjbDownload;
import net.sinodata.business.entity.Fwzyqqbwyccjb;
import net.sinodata.business.entity.FwzyqqbwyccjbDownload;
import net.sinodata.business.service.FwEventLogService;
import net.sinodata.business.service.FwzyffzcbService;
import net.sinodata.business.service.FwzyqqbwcjbService;
import net.sinodata.business.service.FwzyqqbwyccjbService;
import net.sinodata.business.util.DateUtil;
import net.sinodata.business.util.ExcelReaderDown;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.RedisUtil;
import net.sinodata.business.util.SearchResult;
import net.sinodata.business.util.StringUtil;
import net.sinodata.security.vo.ShiroUser;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
@Controller
@RequestMapping(value="/eventLog")
public class FwEventLogController {

	@Autowired
	private FwzyqqbwyccjbService fwzyqqbwyccjbService;
	
	@Autowired
	private FwzyqqbwcjbService fwzyqqbwcjbService;
	
	@Autowired
	private FwzyffzcbService fwzyffzcbService;
	@Autowired
	RedisUtil redisUtil;
	
	@Autowired
	FwEventLogService fwEventLogService;
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(Model model, HttpServletRequest request) {
		Date date = new Date();
		Calendar cal= Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, -2);
		
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		
		request.setAttribute("startTime", DateUtil.formatDate(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
		
//		cal.add(Calendar.DATE, 1);
		request.setAttribute("endTime", DateUtil.formatDate(cal1.getTime(), "yyyy-MM-dd HH:mm:ss"));
		
		request.setAttribute("fwcyf", fwzyqqbwcjbService.selectAllFwcyfzcb());
		
		request.setAttribute("fwzy", fwzyqqbwcjbService.selectAllFwzyzcb());

		return "business/eventLog";
	}
	

	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String qqbwbs = request.getParameter("qqbwbsSearch");
		if(StringUtil.isNotEmpty(qqbwbs)){
			condition.put("qqbwbs", qqbwbs);
		}
		
//		String fwmcfwcyfmcSearch = request.getParameter("fwmcfwcyfmcSearch");
//		if(StringUtil.isNotEmpty(fwmcfwcyfmcSearch)){
//			condition.put("fwmcfwcyfmcSearch", fwmcfwcyfmcSearch);
//		}
		
		String fwbs = request.getParameter("fwbsSearch");
		if(StringUtil.isNotEmpty(fwbs)){
			condition.put("fwbs", fwbs);
		}
		
		String fwqqzZcxx = request.getParameter("fwqqzZcxxSearch");
		if(StringUtil.isNotEmpty(fwqqzZcxx)){
			condition.put("fwqqzZcxx", fwqqzZcxx);
		}
		
		String startTime=request.getParameter("startTimeSearch");
		if(StringUtil.isNotEmpty(startTime)){
			condition.put("startTime", startTime);
		}
		String endTime=request.getParameter("endTimeSearch");
		if(StringUtil.isNotEmpty(endTime)){
			condition.put("endTime", endTime);
		}
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if(!shiroUser.getRoleid().equals("1")){
			condition.put("fwtgzYyxtbh",shiroUser.getId());
		}
		String ffmc = request.getParameter("ffmc");
		if(StringUtil.isNotEmpty(ffmc)){
			condition.put("ffmc", ffmc);
		}


		SearchResult result = fwzyqqbwcjbService.getList(page, condition);
		List<Fwzyqqbwcjb> bwcjbList=(List<Fwzyqqbwcjb>) result.getRows();
		for (int i = 0; i < bwcjbList.size(); i++) {
			String qqkey= bwcjbList.get(i).getQqbwbs()+bwcjbList.get(i).getFwqqSjsjlx();
			String xyjey=bwcjbList.get(i).getQqbwbs()+"service_response";
			try {
				String qqnr=redisUtil.get(qqkey);
				String xynr=redisUtil.get(xyjey);
				bwcjbList.get(i).setFwqqNr(qqnr);
				bwcjbList.get(i).setFwtgNr(xynr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.getSession().setAttribute("logListMap", condition);
		return result;
	}
	
	@RequestMapping(value = "/ycshow", method = RequestMethod.GET)
	public String ycshow(Model model, HttpServletRequest request) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		request.setAttribute("startTime", DateUtil.formatDate(cal.getTime(), "yyyy-MM-dd"));
		
		cal.add(Calendar.DATE, 1);
		request.setAttribute("endTime", DateUtil.formatDate(cal.getTime(), "yyyy-MM-dd"));

		request.setAttribute("fwcyf", fwzyqqbwcjbService.selectAllFwcyfzcb());
		
		request.setAttribute("fwzy", fwzyqqbwcjbService.selectAllFwzyzcb());

		return "business/ycEventLog";
	}

	@RequestMapping(value = "/qwshow", method = RequestMethod.GET)
	public String qmshow(Model model, HttpServletRequest request) {
		return "business/qwEventLog";
	}

	/*
	 * @RequestMapping(value = "/qwlist", method = RequestMethod.POST, produces =
	 * MediaType.APPLICATION_JSON_VALUE)
	 * 
	 * @ResponseBody public Object qmlist(Page page, HttpServletRequest request,
	 * HttpServletResponse response) {
	 * 
	 * Map<String, Object> condition = new HashMap<String, Object>(); String param =
	 * request.getParameter("param"); if(StringUtil.isNotEmpty(param)){
	 * condition.put("param", param); } String operator =
	 * request.getParameter("operator"); if(StringUtil.isNotEmpty(operator)){
	 * condition.put("operator", operator); }
	 * 
	 * request.getSession().setAttribute("qwLogListMap", condition);
	 * 
	 * SearchResult result = fwzyqqbwcjbService.getQwList(page, condition); return
	 * result; }
	 */

	/*
	 * @RequestMapping(value = "/getQw", method = RequestMethod.POST, produces =
	 * MediaType.APPLICATION_JSON_VALUE)
	 * 
	 * @ResponseBody public Object getQw(Page page, HttpServletRequest request,
	 * HttpServletResponse response) { String id = request.getParameter("id");
	 * Map<String,Object> list=fwzyqqbwcjbService.getQw(id); return list; }
	 */

	@RequestMapping(value = "/yclist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object yclist(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String qqbwbs = request.getParameter("qqbwbsSearch");
		if(StringUtil.isNotEmpty(qqbwbs)){
			condition.put("qqbwbs", qqbwbs);
		}
		
		String fwbs = request.getParameter("fwbsSearch");
		if(StringUtil.isNotEmpty(fwbs)){
			condition.put("fwbs", fwbs);
		}
		
		String fwqqzZcxx = request.getParameter("fwqqzZcxxSearch");
		if(StringUtil.isNotEmpty(fwqqzZcxx)){
			condition.put("fwqqzZcxx", fwqqzZcxx);
		}
		String startTime=request.getParameter("startTimeSearch");
		if(StringUtil.isNotEmpty(startTime)){
			condition.put("startTime", startTime);
		}
		String endTime=request.getParameter("endTimeSearch");
		if(StringUtil.isNotEmpty(endTime)){
			condition.put("endTime", endTime);
		}
		SearchResult result = fwzyqqbwyccjbService.getList(page, condition);
		
		List<Fwzyqqbwyccjb> yclogList=(List<Fwzyqqbwyccjb>) result.getRows();
		for (int i = 0; i < yclogList.size(); i++) {
			String nrkey= yclogList.get(i).getQqbwbs()+"request_qqnr";
			String qqqwkey=yclogList.get(i).getQqbwbs()+"request_qqqw";
			try {
				String qqnr=redisUtil.get(nrkey);
				String qqqw=redisUtil.get(qqqwkey);
				yclogList.get(i).setFwqqNr(qqnr);
				yclogList.get(i).setQqqw(qqqw);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		request.getSession().setAttribute("ycLogListMap", condition);
		return result;
	}
	

	@RequestMapping(value = "/getFfList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object getFfList(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		String fwbs = request.getParameter("fwbs");
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("fwbs", fwbs);
		List<Map<String,Object>> list=fwzyffzcbService.getFfList(map);
		return list;
	}
	
	@RequestMapping(value = "eventLogExport")
	public Object eventLogExport(HttpServletRequest request, HttpServletResponse response) {
		// 文件字符输出流
		FileOutputStream toClient = null;
		try {
			Map<String, Object> condition = (Map<String, Object>) request.getSession().getAttribute("logListMap");
			condition.put("start", 0);
			condition.put("end", 100);
			String days ="DAY"+condition.get("startTime").toString().substring(8,10);
			condition.put("days", days);
			List<FwzyqqbwcjbDownload> dataset = fwzyqqbwcjbService.getLogList(condition);
			// 得到项目路径
			String rootpath = request.getSession().getServletContext().getRealPath("/");
			// 文件名
			String fileName = "exportdata.xls";
			// 创建文件字符输出流（项目路径+路径+文件名）
			toClient = new FileOutputStream(rootpath + File.separator + "static" + File.separator + fileName);
			// 创建电子表格
			ExcelReaderDown<FwzyqqbwcjbDownload> export = new ExcelReaderDown<FwzyqqbwcjbDownload>();

			String[] headers = { "报文标识", "应用名称", "服务名称", "方法名称", "请求时间", "响应时间",
					"响应时长", "响应状态" };
			export.setNum(0);
			export.exportExcel("表格数据", headers, dataset, toClient, null);
			toClient.close();
			response.setContentType("text/html;setchar=utf-8");
			File file = new File(rootpath + File.separator + "static" + File.separator + fileName);
			HttpHeaders header = new HttpHeaders();
			header.setContentDispositionFormData("attachment", fileName);
			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), header, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * @RequestMapping(value = "qwLogExport") public Object
	 * qwLogExport(HttpServletRequest request, HttpServletResponse response) { //
	 * 文件字符输出流 FileOutputStream toClient = null; try { Map<String, Object> condition
	 * = (Map<String, Object>) request.getSession().getAttribute("qwLogListMap");
	 * condition.put("start", 0); condition.put("end", 100); List<QwLogDownload>
	 * dataset = fwzyqqbwcjbService.getQwList(condition); // 得到项目路径 String rootpath
	 * = request.getSession().getServletContext().getRealPath("/"); // 文件名 String
	 * fileName = "exportdata.xls"; // 创建文件字符输出流（项目路径+路径+文件名） toClient = new
	 * FileOutputStream(rootpath + File.separator + "static" + File.separator +
	 * fileName); // 创建电子表格 ExcelReaderDown<QwLogDownload> export = new
	 * ExcelReaderDown<QwLogDownload>();
	 * 
	 * String[] headers = { "报文标识", "应用名称", "服务名称", "方法名称", "请求时间", "响应时间", "响应时长",
	 * "响应状态" , "请求内容" , "相应内容" }; export.setNum(0); export.exportExcel("表格数据",
	 * headers, dataset, toClient, null); toClient.close();
	 * response.setContentType("text/html;setchar=utf-8"); File file = new
	 * File(rootpath + File.separator + "static" + File.separator + fileName);
	 * HttpHeaders header = new HttpHeaders();
	 * header.setContentDispositionFormData("attachment", fileName);
	 * header.setContentType(MediaType.APPLICATION_OCTET_STREAM); return new
	 * ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), header,
	 * HttpStatus.CREATED); } catch (Exception e) { e.printStackTrace(); } return
	 * null; }
	 */
	
	@RequestMapping(value = "ycLogExport")
	public Object ycLogExport(HttpServletRequest request, HttpServletResponse response) {
		// 文件字符输出流
		FileOutputStream toClient = null;
		try {
			Map<String, Object> condition = (Map<String, Object>) request.getSession().getAttribute("ycLogListMap");
			condition.put("start", 0);
			condition.put("end", 100);
			List<FwzyqqbwyccjbDownload> dataset = fwzyqqbwyccjbService.getYcList(condition);
			// 得到项目路径
			String rootpath = request.getSession().getServletContext().getRealPath("/");
			// 文件名
			String fileName = "exportdata.xls";
			// 创建文件字符输出流（项目路径+路径+文件名）
			toClient = new FileOutputStream(rootpath + File.separator + "static" + File.separator + fileName);
			// 创建电子表格
			ExcelReaderDown<FwzyqqbwyccjbDownload> export = new ExcelReaderDown<FwzyqqbwyccjbDownload>();

			String[] headers = { "报文标识", "服务名称", "应用名称", "方法名称", "请求时间", "异常字段" };
			export.setNum(0);
			export.exportExcel("表格数据", headers, dataset, toClient, null);
			toClient.close();
			response.setContentType("text/html;setchar=utf-8");
			File file = new File(rootpath + File.separator + "static" + File.separator + fileName);
			HttpHeaders header = new HttpHeaders();
			header.setContentDispositionFormData("attachment", fileName);
			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), header, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//sql执行日志
	@RequestMapping(value = "/showSqlArea", method = RequestMethod.GET)
	public String showSqlArea(Model model, HttpServletRequest request) {
		return "business/sqlAreaLog";
	}
	@RequestMapping(value = "/sqlAreaList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object sqlAreaList(Page page, HttpServletRequest request, HttpServletResponse response) {
		SearchResult result = new SearchResult(new ArrayList<>(),0);
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String dayTime = request.getParameter("dayTime");
		if (StringUtil.isNotEmpty(dayTime)) {
			condition.put("dayTime", dayTime);
			result = fwEventLogService.sqlAreaList(page, condition);
		}
		return result;
	}
	
	@RequestMapping(value = "/lookSql", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String lookSql(HttpServletRequest request) {
		String sqlId = request.getParameter("sqlId");
		String sqlFullText = fwEventLogService.lookSql(sqlId);
		return sqlFullText;
	}
	
}
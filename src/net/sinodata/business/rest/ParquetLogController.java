package net.sinodata.business.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sinodata.business.entity.ConfigInfo;
import net.sinodata.business.entity.EsLogDownload;
import net.sinodata.business.service.FwzyqqbwcjbService;
import net.sinodata.business.util.DateUtil;
import net.sinodata.business.util.ExcelReaderDown;
import net.sinodata.business.util.HttpRequest;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;
import net.sinodata.business.util.StringUtil;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * parquet日志查询
 */
@Controller
@RequestMapping(value = "/parquetLog")
public class ParquetLogController {

	@Autowired
	private FwzyqqbwcjbService fwzyqqbwcjbService;
	@Autowired(required = false)
	private ConfigInfo configInfo;

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(Model model, HttpServletRequest request) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, -3);

		request.setAttribute("endTime", DateUtil.formatDate(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
		request.setAttribute("fwzy", fwzyqqbwcjbService.selectAllFwzyzcb());
		return "business/config/parquetLog";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list(Page page, HttpServletRequest request) throws Exception {
		List<JSONObject> list = new ArrayList<JSONObject>();

		Map<String, String> condition = new HashMap<>();
		condition.put("indexname", configInfo.getParquetLogtable());
		condition.put("max_results", "300");
		String fwbs = request.getParameter("fwbsSearch");
		if (StringUtil.isNotEmpty(fwbs)) {
			condition.put("fwbs", fwbs);
		}
		String ffbs = request.getParameter("ffbsSearch");
		if (StringUtil.isNotEmpty(ffbs)) {
			condition.put("ffbs", ffbs);
		}
		String status = request.getParameter("statusSearch");
		if (StringUtil.isNotEmpty(status)) {
			condition.put("status", status);
		}
		String startTime = request.getParameter("startTimeSearch");
		Date startTimeDate = DateUtil.formatString(startTime, "yyyy-MM-dd HH:mm:ss");
		startTime = DateUtil.formatDate(startTimeDate, "yyyyMMddHHmmss") + "000";
		String endTime = request.getParameter("endTimeSearch");
		Date endTimeDate = DateUtil.formatString(endTime, "yyyy-MM-dd HH:mm:ss");
		endTime = DateUtil.formatDate(endTimeDate, "yyyyMMddHHmmss") + "999";
		condition.put("requestTime", startTime + "," + endTime);

		String str = HttpRequest.sendGetRequest(configInfo.getParquetUrl(), condition);
		if (StringUtils.isNotEmpty(str)) {
			JSONObject jo = JSON.parseObject(str);
			JSONArray data = jo.getJSONArray("data");
			if (data.size() > 0) {
				for (int i = 0; i < data.size(); i++) {
					JSONObject bjLogsJson = data.getJSONObject(i).getJSONObject("bj_logs_json");
					JSONObject requestData = bjLogsJson.getJSONObject("requestData");
					bjLogsJson.put("FWBS", requestData.get("FWBS"));
					bjLogsJson.put("FFBS", requestData.get("FFBS"));
					bjLogsJson.put("FWQQZ_ZCXX", requestData.get("FWQQZ_ZCXX"));
					bjLogsJson.put("FWQQSB_BH", requestData.get("FWQQSB_BH"));
					bjLogsJson.put("XXCZRY_XM", requestData.get("XXCZRY_XM"));
					bjLogsJson.put("XXCZRY_GMSFHM", requestData.get("XXCZRY_GMSFHM"));
					bjLogsJson.put("requestDataJson", requestData.toJSONString());
					Object statusStr = bjLogsJson.get("status");
					Object errorTime = bjLogsJson.get("errorTime");
					if ("00".equals(statusStr)) {
						bjLogsJson.put("statusStr", "正常");
					} else if ("01".equals(statusStr)) {
						bjLogsJson.put("statusStr", "异常");
						bjLogsJson.put("responseTime", errorTime);
					} else if ("02".equals(statusStr)) {
						bjLogsJson.put("statusStr", "校验错误");
						bjLogsJson.put("responseTime", errorTime);
					} else {
						bjLogsJson.put("statusStr", "");
					}
					list.add(bjLogsJson);
				}
			}
		}
		request.getSession().setAttribute("parquetLogSearchCondition", condition);
		return new SearchResult(list, list.size());
	}

	@RequestMapping(value = "parquetLogExport")
	public Object parquetLogExport(HttpServletRequest request, HttpServletResponse response) {
		// 文件字符输出流
		FileOutputStream toClient = null;
		try {
			List<EsLogDownload> list = new ArrayList<EsLogDownload>();
			Map<String, String> condition = (Map<String, String>) request.getSession()
					.getAttribute("parquetLogSearchCondition");
			String str = HttpRequest.sendGetRequest(configInfo.getParquetUrl(), condition);
			JSONObject jo = JSON.parseObject(str);
			JSONArray data = jo.getJSONArray("data");
			if (data.size() > 0) {
				for (int i = 0; i < data.size(); i++) {
					JSONObject bjLogsJson = data.getJSONObject(i).getJSONObject("bj_logs_json");
					EsLogDownload esLogDownload = new EsLogDownload();
					Object uuid = bjLogsJson.get("uuid");
					if (null != uuid) {
						esLogDownload.setUuid(uuid.toString());
					}
					Object notes = bjLogsJson.get("notes");
					if (null != notes) {
						esLogDownload.setNotes(notes.toString());
					}
					Object requestTime = bjLogsJson.get("requestTime");
					if (null != requestTime) {
						esLogDownload.setRequestTime(requestTime.toString());
					}
					Object responseTime = bjLogsJson.get("responseTime");
					if (null != responseTime) {
						esLogDownload.setResponseTime(responseTime.toString());
					}
					Object beforeWallTime = bjLogsJson.get("beforeWallTime");
					if (null != beforeWallTime) {
						esLogDownload.setBeforeWallTime(beforeWallTime.toString());
					}
					Object finishWallTime = bjLogsJson.get("finishWallTime");
					if (null != finishWallTime) {
						esLogDownload.setFinishWallTime(finishWallTime.toString());
					}
					Object begin3Time = bjLogsJson.get("begin3Time");
					if (null != begin3Time) {
						esLogDownload.setBegin3Time(begin3Time.toString());
					}
					Object finish3Time = bjLogsJson.get("finish3Time");
					if (null != finish3Time) {
						esLogDownload.setFinish3Time(finish3Time.toString());
					}
					Object statusObj = bjLogsJson.get("status");
					Object errorTime = bjLogsJson.get("errorTime");
					if ("00".equals(statusObj)) {
						esLogDownload.setStatus("正常");
					} else if ("01".equals(statusObj)) {
						esLogDownload.setStatus("异常");
						if (null != errorTime) {
							esLogDownload.setResponseTime(errorTime.toString());
						}
					} else if ("02".equals(statusObj)) {
						esLogDownload.setStatus("校验错误");
						if (null != errorTime) {
							esLogDownload.setResponseTime(errorTime.toString());
						}
					} else {
						esLogDownload.setStatus("");
					}
					JSONObject requestData = bjLogsJson.getJSONObject("requestData");
					if (null != requestData) {
						Object qqsbbhObj = requestData.get("FWQQSB_BH");
						if (null != qqsbbhObj) {
							esLogDownload.setQqsbbh(qqsbbhObj.toString());
						}
						Object czryxmObj = requestData.get("XXCZRY_XM");
						if (null != czryxmObj) {
							esLogDownload.setCzryxm(czryxmObj.toString());
						}
						Object czryzjhObj = requestData.get("XXCZRY_GMSFHM");
						if (null != czryzjhObj) {
							esLogDownload.setCzryzjh(czryzjhObj.toString());
						}
						esLogDownload.setRequestData(requestData.toJSONString());
					}
					Object responseData = bjLogsJson.get("responseData");
					if (null != responseData) {
						esLogDownload.setResponseData(responseData.toString());
					}

					list.add(esLogDownload);
				}
			}

			// 得到项目路径
			String rootpath = request.getSession().getServletContext().getRealPath("/");
			// 文件名
			String fileName = "parquet_log.xls";
			// 创建文件字符输出流（项目路径+路径+文件名）
			toClient = new FileOutputStream(rootpath + File.separator + "static" + File.separator + fileName);
			// 创建电子表格
			ExcelReaderDown<EsLogDownload> export = new ExcelReaderDown<EsLogDownload>();

			String[] headers = { "报文标识", "备注", "接收请求时间", "响应请求时间", "进入防火墙时间", "离开防火墙时间", "服务请求时间", "服务响应时间", "响应状态",
					"请求设备编号", "操作人员姓名", "操作人员证件号", "请求内容", "响应内容" };
			export.setNum(0);
			export.exportExcel("表格数据", headers, list, toClient, null);
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

	@RequestMapping(value = "/jshs", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object jshs(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		Map map = new HashMap<>();
		String diff3Time = request.getParameter("diff3Time");
		String diffWallTime = request.getParameter("diffWallTime");
		String diffTime = request.getParameter("diffTime");

		if (StringUtils.isNotEmpty(diffTime) && StringUtils.isNotEmpty(diffWallTime)) {
			BigDecimal diff = new BigDecimal(diffTime);
			BigDecimal diffWall = new BigDecimal(diffWallTime);
			map.put("time1", (diff.subtract(diffWall)).divide(new BigDecimal(2)).setScale(2));
			map.put("time5", (diff.subtract(diffWall)).divide(new BigDecimal(2)).setScale(2));
		} else {
			map.put("time1", 0);
			map.put("time5", 0);
		}

		if (StringUtils.isNotEmpty(diffWallTime) && StringUtils.isNotEmpty(diff3Time)) {
			BigDecimal diffWall = new BigDecimal(diffWallTime);
			BigDecimal diff3 = new BigDecimal(diff3Time);
			map.put("time2", (diffWall.subtract(diff3)).divide(new BigDecimal(2)).setScale(2));
			map.put("time4", (diffWall.subtract(diff3)).divide(new BigDecimal(2)).setScale(2));
		} else {
			map.put("time2", 0);
			map.put("time4", 0);
		}

		if (StringUtils.isNotEmpty(diff3Time)) {
			map.put("time3", new BigDecimal(diff3Time));
		} else {
			map.put("time3", 0);
		}

		return map;
	}

}
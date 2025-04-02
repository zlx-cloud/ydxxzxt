package net.sinodata.business.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sinodata.business.entity.ConfigInfo;
import net.sinodata.business.entity.EsLogDownload;
import net.sinodata.business.service.FwzyqqbwcjbService;
import net.sinodata.business.util.DateUtil;
import net.sinodata.business.util.ExcelReaderDown;
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
import com.alibaba.fastjson.JSONObject;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

/**
 * ES日志查询
 */
@Controller
@RequestMapping(value = "/esLog")
public class EsLogController {

	@Autowired
	private ElasticsearchClient restClient;
	@Autowired(required = false)
	private ConfigInfo configInfo;
	@Autowired
	private FwzyqqbwcjbService fwzyqqbwcjbService;
	private final static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private final static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
	private final static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(Model model, HttpServletRequest request) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, -2);

		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);

		request.setAttribute("startTime", DateUtil.formatDate(cal.getTime(), "yyyy-MM-dd HH:mm:ss"));
		request.setAttribute("endTime", DateUtil.formatDate(cal1.getTime(), "yyyy-MM-dd HH:mm:ss"));
		request.setAttribute("fwcyf", fwzyqqbwcjbService.selectAllFwcyfzcb());
		request.setAttribute("fwzy", fwzyqqbwcjbService.selectAllFwzyzcb());
		return "business/config/esLog";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list(Page page, HttpServletRequest request, HttpServletResponse response)
			throws ElasticsearchException, IOException, ParseException {

		Map<String, Object> condition = new HashMap<String, Object>();

		String bwbs = request.getParameter("bwbsSearch");
		if (StringUtil.isNotEmpty(bwbs)) {
			condition.put("bwbsSearch", bwbs);
		}
		String fwqqzZcxx = request.getParameter("fwqqzZcxxSearch");
		if (StringUtil.isNotEmpty(fwqqzZcxx)) {
			condition.put("fwqqzZcxxSearch", fwqqzZcxx);
		}
		String fwbs = request.getParameter("fwbsSearch");
		if (StringUtil.isNotEmpty(fwbs)) {
			condition.put("fwbsSearch", fwbs);
		}
		String ffbs = request.getParameter("ffbsSearch");
		if (StringUtil.isNotEmpty(ffbs)) {
			condition.put("ffbsSearch", ffbs);
		}
		String startTime = request.getParameter("startTimeSearch");
		if (StringUtil.isNotEmpty(startTime)) {
			condition.put("startTimeSearch", startTime);
		}
		String endTime = request.getParameter("endTimeSearch");
		if (StringUtil.isNotEmpty(endTime)) {
			condition.put("endTimeSearch", endTime);
		}
		String keyWordRequestSearch = request.getParameter("keyWordRequestSearch");
		if (StringUtil.isNotEmpty(keyWordRequestSearch)) {
			condition.put("keyWordRequestSearch", keyWordRequestSearch);
		}
		String keyWordResponseSearch = request.getParameter("keyWordResponseSearch");
		if (StringUtil.isNotEmpty(keyWordResponseSearch)) {
			condition.put("keyWordResponseSearch", keyWordResponseSearch);
		}
		String status = request.getParameter("statusSearch");
		if (StringUtil.isNotEmpty(status)) {
			condition.put("statusSearch", status);
		}

		SearchResponse<HashMap> search = restClient
				.search(s -> s.index(configInfo.getLogTable()).query(query -> query.bool(bool -> {
					if (StringUtil.isNotEmpty(bwbs)) {
						bool.must(q -> q.matchPhrase(t -> t.field("bj_logs_json.uuid").query(bwbs)));
					}
					if (StringUtil.isNotEmpty(fwqqzZcxx)) {
						bool.must(q -> q
								.matchPhrase(t -> t.field("bj_logs_json.requestData.FWQQZ_ZCXX").query(fwqqzZcxx)));
					}
					if (StringUtil.isNotEmpty(fwbs)) {
						bool.must(q -> q.matchPhrase(t -> t.field("bj_logs_json.requestData.FWBS").query(fwbs)));
					}
					if (StringUtil.isNotEmpty(ffbs)) {
						bool.must(q -> q.matchPhrase(t -> t.field("bj_logs_json.requestData.FFBS").query(ffbs)));
					}
					if (StringUtil.isNotEmpty(status)) {
						bool.must(q -> q.matchPhrase(t -> t.field("bj_logs_json.status").query(status)));
					}
					if (StringUtil.isNotEmpty(keyWordRequestSearch)) {
						bool.must(q -> q.matchPhrase(
								t -> t.field("bj_logs_json.requestData.FWQQ_NR.params").query(keyWordRequestSearch)));
					}
					if (StringUtil.isNotEmpty(keyWordResponseSearch)) {
						bool.must(q -> q
								.matchPhrase(t -> t.field("bj_logs_json.responseData").query(keyWordResponseSearch)));
					}
					if (StringUtil.isNotEmpty(startTime)) {
						Date startDate = null;
						Date endDate = null;
						try {
							startDate = sdf1.parse(startTime);
							endDate = sdf1.parse(endTime);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						String esStartTime = sdf2.format(startDate) + "000";
						String esEndTime = sdf2.format(endDate) + "999";
						bool.must(r -> r.range(
								t -> t.term(f -> f.field("bj_logs_json.requestTime").gte(esStartTime).lte(esEndTime))));
					}
					return bool;
				})).from(page.getStart()).size(page.getOriginalRows())
						.sort(sort -> sort.field(f -> f.field("bj_logs_json.requestTime").order(SortOrder.Desc)))
						.trackTotalHits(t -> t.enabled(true)), HashMap.class);

		List<Hit<HashMap>> hits = search.hits().hits();
		Iterator<Hit<HashMap>> iterator = hits.iterator();
		List<JSONObject> list = new ArrayList<JSONObject>();
		while (iterator.hasNext()) {
			Hit<HashMap> decodeBeanHit = iterator.next();
			Map<String, Object> docMap = decodeBeanHit.source();
			String json = JSON.toJSONString(docMap);
			JSONObject obj = JSON.parseObject(json);
			JSONObject bjLogsJson = obj.getJSONObject("bj_logs_json");
			JSONObject requestData = bjLogsJson.getJSONObject("requestData");
			bjLogsJson.put("FWBS", requestData.get("FWBS"));
			bjLogsJson.put("FFBS", requestData.get("FFBS"));
			bjLogsJson.put("FWQQZ_ZCXX", requestData.get("FWQQZ_ZCXX"));
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
		request.getSession().setAttribute("esLogSearchCondition", condition);
		return new SearchResult(list, (int) search.hits().total().value());
	}

	@RequestMapping(value = "esLogExport")
	public Object esLogExport(HttpServletRequest request, HttpServletResponse response) {
		// 文件字符输出流
		FileOutputStream toClient = null;
		try {
			Map<String, Object> condition = (Map<String, Object>) request.getSession()
					.getAttribute("esLogSearchCondition");
			Object bwbs = condition.get("bwbsSearch");
			Object fwqqzZcxx = condition.get("fwqqzZcxxSearch");
			Object fwbs = condition.get("fwbsSearch");
			Object ffbs = condition.get("ffbsSearch");
			Object startTime = condition.get("startTimeSearch");
			Object endTime = condition.get("endTimeSearch");
			Object keyWordRequestSearch = condition.get("keyWordRequestSearch");
			Object keyWordResponseSearch = condition.get("keyWordResponseSearch");
			Object status = condition.get("statusSearch");

			SearchResponse<HashMap> search = restClient
					.search(s -> s.index(configInfo.getLogTable()).query(query -> query.bool(bool -> {
						if (null != bwbs) {
							bool.must(q -> q.matchPhrase(t -> t.field("bj_logs_json.uuid").query(bwbs.toString())));
						}
						if (null != fwqqzZcxx) {
							bool.must(q -> q.matchPhrase(
									t -> t.field("bj_logs_json.requestData.FWQQZ_ZCXX").query(fwqqzZcxx.toString())));
						}
						if (null != fwbs) {
							bool.must(q -> q
									.matchPhrase(t -> t.field("bj_logs_json.requestData.FWBS").query(fwbs.toString())));
						}
						if (null != ffbs) {
							bool.must(q -> q
									.matchPhrase(t -> t.field("bj_logs_json.requestData.FFBS").query(ffbs.toString())));
						}
						if (null != status) {
							bool.must(q -> q.matchPhrase(t -> t.field("bj_logs_json.status").query(status.toString())));
						}
						if (null != keyWordRequestSearch) {
							bool.must(q -> q.matchPhrase(t -> t.field("bj_logs_json.requestData.FWQQ_NR.params")
									.query(keyWordRequestSearch.toString())));
						}
						if (null != keyWordResponseSearch) {
							bool.must(q -> q.matchPhrase(
									t -> t.field("bj_logs_json.responseData").query(keyWordResponseSearch.toString())));
						}
						if (null != startTime) {
							Date startDate = null;
							Date endDate = null;
							try {
								startDate = sdf1.parse(startTime.toString());
								endDate = sdf1.parse(endTime.toString());
							} catch (ParseException e) {
								e.printStackTrace();
							}
							String esStartTime = sdf2.format(startDate) + "000";
							String esEndTime = sdf2.format(endDate) + "999";
							bool.must(r -> r.range(t -> t
									.term(f -> f.field("bj_logs_json.requestTime").gte(esStartTime).lte(esEndTime))));
							// f ->
							// f.field("bj_logs_json.requestTime").gte(JsonData.fromJson(esStartTime)).lte(JsonData.fromJson(esEndTime))));
						}
						return bool;
					})).from(0).size(1000)
							.sort(sort -> sort.field(f -> f.field("bj_logs_json.requestTime").order(SortOrder.Desc)))
							.trackTotalHits(t -> t.enabled(true)), HashMap.class);
			List<Hit<HashMap>> hits = search.hits().hits();
			Iterator<Hit<HashMap>> iterator = hits.iterator();
			List<EsLogDownload> list = new ArrayList<EsLogDownload>();
			while (iterator.hasNext()) {
				Hit<HashMap> decodeBeanHit = iterator.next();
				Map<String, Object> docMap = decodeBeanHit.source();
				String json = JSON.toJSONString(docMap);
				JSONObject obj = JSON.parseObject(json);
				JSONObject bjLogsJson = obj.getJSONObject("bj_logs_json");

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
					esLogDownload.setRequestData(requestData.toJSONString());
				}
				Object responseData = bjLogsJson.get("responseData");
				if (null != responseData) {
					esLogDownload.setResponseData(responseData.toString());
				}

				list.add(esLogDownload);
			}

			// 得到项目路径
			String rootpath = request.getSession().getServletContext().getRealPath("/");
			// 文件名
			String fileName = "es_log.xls";
			// 创建文件字符输出流（项目路径+路径+文件名）
			toClient = new FileOutputStream(rootpath + File.separator + "static" + File.separator + fileName);
			// 创建电子表格
			ExcelReaderDown<EsLogDownload> export = new ExcelReaderDown<EsLogDownload>();

			String[] headers = { "报文标识", "备注", "接收请求时间", "响应请求时间", "进入防火墙时间", "离开防火墙时间", "服务请求时间", "服务响应时间", "响应状态",
					"请求内容", "响应内容" };
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
		}else {
			map.put("time3", 0);
		}

		return map;
	}

}
package net.sinodata.business.rest;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
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
import net.sinodata.business.dao.TemporaryDao;
import net.sinodata.business.entity.ConfigInfo;
import net.sinodata.business.service.FwzyqqbwcjbService;
import net.sinodata.business.service.TemporaryService;
import net.sinodata.business.util.DateUtil;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;
import net.sinodata.business.util.StringUtil;

@Controller
@RequestMapping(value = "/temporary")
public class TemporaryController {

	@Autowired
	private TemporaryDao temporaryDao;
	@Autowired
	private TemporaryService temporaryService;
	@Autowired
	private ElasticsearchClient restClient;
	@Autowired(required = false)
	private ConfigInfo configInfo;
	@Autowired
	private FwzyqqbwcjbService fwzyqqbwcjbService;

	/**
	 * 查询报警数据
	 */
	@RequestMapping(value = "/queryWarnCount", method = RequestMethod.GET)
	@ResponseBody
	public int queryWarnCount() {
		return temporaryDao.selectUnReadCount();
	}

	/**
	 * 监控报警
	 */
	@RequestMapping(value = "/jkbjData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list() {
		List<Map<String, Object>> data = temporaryDao.getUnReadWarnListByPage();
		temporaryDao.updateStatusToread();
		return new SearchResult(data, data.size());
	}

	/**
	 * 事件查询汇总
	 */
	@RequestMapping(value = "/sjcxhzPage", method = RequestMethod.GET)
	public String sjcxhzPage(HttpServletRequest request) {
		request.setAttribute("time", DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
		return "business/temp/sjcxhz";
	}

	@RequestMapping(value = "/sjcxhz", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object sjcxhz(Page page, HttpServletRequest request) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String yymc = request.getParameter("yymc");
		String fwmc = request.getParameter("fwmc");
		String ffmc = request.getParameter("ffmc");
		String warnType = request.getParameter("warnType");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		if (StringUtil.isNotEmpty(yymc)) {
			condition.put("yymc", yymc);
		}
		if (StringUtil.isNotEmpty(fwmc)) {
			condition.put("fwmc", fwmc);
		}
		if (StringUtil.isNotEmpty(ffmc)) {
			condition.put("ffmc", ffmc);
		}
		if (StringUtil.isNotEmpty(warnType)) {
			condition.put("warnType", warnType);
		}
		if (StringUtil.isNotEmpty(startTime)) {
			condition.put("startTime", startTime);
		}
		if (StringUtil.isNotEmpty(endTime)) {
			condition.put("endTime", endTime);
		}
		SearchResult result = temporaryService.warnCountList(page, condition);
		return result;
	}

	@RequestMapping(value = "/warnEsLog", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object warnEsLog(Page page, HttpServletRequest request)
			throws ElasticsearchException, IOException, ParseException {
		String FWBS = request.getParameter("FWBS");
		String FFBS = request.getParameter("FFBS");
		String time1 = request.getParameter("startTime").replaceAll("-", "");
		String time2 = request.getParameter("endTime").replaceAll("-", "");

		SearchResponse<HashMap> search = restClient
				.search(s -> s.index(configInfo.getLogTable()).query(query -> query.bool(bool -> {
					bool.must(q -> q.matchPhrase(t -> t.field("bj_logs_json.requestData.FWBS").query(FWBS)));
					bool.must(q -> q.matchPhrase(t -> t.field("bj_logs_json.requestData.FFBS").query(FFBS)));
					bool.must(q -> q.matchPhrase(t -> t.field("bj_logs_json.status").query("01")));
					String startTime = time1 + "000000000";
					String endTime = time2 + "235959999";
					bool.must(r -> r
							.range(t -> t.term(f -> f.field("bj_logs_json.requestTime").gte(startTime).lte(endTime))));
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
		return new SearchResult(list, (int) search.hits().total().value());
	}

	@RequestMapping(value = "/warnChart", method = RequestMethod.GET)
	public String warnChart(HttpServletRequest request) {
		List<String> typeList = Arrays.asList("RISK-RT", "RISK-ERR", "RISK-MIS");
		List<String> timeList = new ArrayList<String>();
		List<String> rtList = new ArrayList<String>();
		List<String> errList = new ArrayList<String>();
		List<String> missList = new ArrayList<String>();
		// 查询数据
		String time1 = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
		List<Map<String, Object>> data = temporaryDao.queryWarnList("", "", "", time1, time1);
		if (data.size() > 0) {
			// 存放key-value
			Map<String, Object> map = new HashMap<String, Object>();
			for (Map<String, Object> o : data) {
				map.put(o.get("KEY").toString(), o.get("VALUE"));
				timeList.add(o.get("TIME").toString());
			}
			timeList = timeList.stream().distinct().collect(Collectors.toList());

			for (String time : timeList) {
				for (String type : typeList) {
					String key = time + "-" + type;
					Object value = map.get(key);
					if (null != value && !"".equals(value)) {
						if ("RISK-RT".equals(type)) {
							rtList.add(value.toString());
						} else if ("RISK-ERR".equals(type)) {
							errList.add(value.toString());
						} else if ("RISK-MIS".equals(type)) {
							missList.add(value.toString());
						}
					} else {
						if ("RISK-RT".equals(type)) {
							rtList.add("0");
						} else if ("RISK-ERR".equals(type)) {
							errList.add("0");
						} else if ("RISK-MIS".equals(type)) {
							missList.add("0");
						}
					}
				}
			}
		}

		int count1 = temporaryDao.fxsjhzCount("", "", "", time1, time1, "RISK-RT");
		int count2 = temporaryDao.fxsjhzCount("", "", "", time1, time1, "RISK-ERR");
		int count3 = temporaryDao.fxsjhzCount("", "", "", time1, time1, "RISK-MIS");
		
		request.setAttribute("times", JSON.toJSON(timeList));
		request.setAttribute("rtList", JSON.toJSON(rtList));
		request.setAttribute("errList", JSON.toJSON(errList));
		request.setAttribute("missList", JSON.toJSON(missList));
		request.setAttribute("fwcyf", fwzyqqbwcjbService.selectAllFwcyfzcb());
		request.setAttribute("fwzy", fwzyqqbwcjbService.selectAllFwzyzcb());
		request.setAttribute("time1", time1);
		request.setAttribute("count1", count1);
		request.setAttribute("count2", count2);
		request.setAttribute("count3", count3);
		return "business/temp/warnChart";
	}

	@RequestMapping(value = "/warnChartSearch", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object[] warnChartSearch(HttpServletRequest request) {
		String yybs = request.getParameter("yybs");
		String fwbs = request.getParameter("fwbs");
		String ffbs = request.getParameter("ffbs");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		Object result[] = new Object[7];
		List<String> typeList = Arrays.asList("RISK-RT", "RISK-ERR", "RISK-MIS");
		List<String> timeList = new ArrayList<String>();
		List<String> rtList = new ArrayList<String>();
		List<String> errList = new ArrayList<String>();
		List<String> missList = new ArrayList<String>();
		// 查询数据
		List<Map<String, Object>> data = temporaryDao.queryWarnList(yybs, fwbs, ffbs, startTime, endTime);
		if (data.size() > 0) {
			// 存放key-value
			Map<String, Object> map = new HashMap<String, Object>();
			for (Map<String, Object> o : data) {
				map.put(o.get("KEY").toString(), o.get("VALUE"));
				timeList.add(o.get("TIME").toString());
			}
			timeList = timeList.stream().distinct().collect(Collectors.toList());

			for (String time : timeList) {
				for (String type : typeList) {
					String key = time + "-" + type;
					Object value = map.get(key);
					if (null != value && !"".equals(value)) {
						if ("RISK-RT".equals(type)) {
							rtList.add(value.toString());
						} else if ("RISK-ERR".equals(type)) {
							errList.add(value.toString());
						} else if ("RISK-MIS".equals(type)) {
							missList.add(value.toString());
						}
					} else {
						if ("RISK-RT".equals(type)) {
							rtList.add("0");
						} else if ("RISK-ERR".equals(type)) {
							errList.add("0");
						} else if ("RISK-MIS".equals(type)) {
							missList.add("0");
						}
					}
				}
			}
		}
		
		int count1 = temporaryDao.fxsjhzCount("", "", "", startTime, endTime, "RISK-RT");
		int count2 = temporaryDao.fxsjhzCount("", "", "", startTime, endTime, "RISK-ERR");
		int count3 = temporaryDao.fxsjhzCount("", "", "", startTime, endTime, "RISK-MIS");

		result[0] = timeList;
		result[1] = rtList;
		result[2] = errList;
		result[3] = missList;
		result[4] = count1;
		result[5] = count2;
		result[6] = count3;
		return result;
	}

	/**
	 * 预警事件查询
	 */
	@RequestMapping(value = "/yjsjcxPage", method = RequestMethod.GET)
	public String yjsjcxPage(HttpServletRequest request) {
		request.setAttribute("time", DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
		return "business/temp/yjsjcx";
	}

	@RequestMapping(value = "/yjsjcx", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object yjsjcx(Page page, HttpServletRequest request) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String yymc = request.getParameter("yymc");
		String fwmc = request.getParameter("fwmc");
		String ffmc = request.getParameter("ffmc");
		String warnType = request.getParameter("warnType");
		String time = request.getParameter("time");

		if (StringUtil.isNotEmpty(yymc)) {
			condition.put("yymc", yymc);
		}
		if (StringUtil.isNotEmpty(fwmc)) {
			condition.put("fwmc", fwmc);
		}
		if (StringUtil.isNotEmpty(ffmc)) {
			condition.put("ffmc", ffmc);
		}
		if (StringUtil.isNotEmpty(warnType)) {
			condition.put("warnType", warnType);
		}
		if (StringUtil.isNotEmpty(time)) {
			condition.put("time", time);
		}
		SearchResult result = temporaryService.yjsjcxList(page, condition);
		return result;
	}

	@RequestMapping(value = "/yjsjcxDetail", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object yjsjcxDetail(Page page, HttpServletRequest request) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String fwbs = request.getParameter("FWBS");
		String ffbs = request.getParameter("FFBS");
		String warnType = request.getParameter("WARN_TYPE");
		String time = request.getParameter("time");
		condition.put("fwbs", fwbs);
		condition.put("ffbs", ffbs);
		condition.put("warnType", warnType);
		condition.put("time", time);

		SearchResult result = temporaryService.yjsjcxDetail(page, condition);
		return result;
	}

	// ================================================================================
	/**
	 * 服务质量分析
	 */
	@RequestMapping(value = "/fwzlfxPage", method = RequestMethod.GET)
	public String fwzlfxPage(HttpServletRequest request) {
		request.setAttribute("fwcyf", fwzyqqbwcjbService.selectAllFwcyfzcb());
		request.setAttribute("time", DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
		return "business/temp/fwzlfx";
	}

	@RequestMapping(value = "/fwzlfxSearch", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object[] fwzlfxSearch(HttpServletRequest request) {
		// 查询条件
		String yybsSearch = request.getParameter("yybs");
		String timeSearch = request.getParameter("time").replaceAll("-", "");

		// 数据集合
		List<String> yybsList = new ArrayList<String>();
		List<String> yymcList = new ArrayList<String>();
		List<String> timeList = new ArrayList<String>();
		Map<String, String> yybsMap = new LinkedHashMap<>();
		Map<String, Object> dataMap = new LinkedHashMap<>();
		List<Map<String, Object>> chartList = new ArrayList<>();

		// 查询数据
		List<Map<String, Object>> dataList = temporaryDao.fwzlfxSearch(yybsSearch, timeSearch);
		if (CollectionUtils.isNotEmpty(dataList)) {
			for (Map<String, Object> map : dataList) {
				yybsList.add((String) map.get("FFQQZ"));
				timeList.add((String) map.get("HOUR"));
				yybsMap.put((String) map.get("FFQQZ"), (String) map.get("NAME"));
				dataMap.put((String) map.get("KEY"), map.get("VALUE"));
			}

			yybsList = yybsList.stream().distinct().collect(Collectors.toList());
			timeList = timeList.stream().distinct().collect(Collectors.toList());

			// 组装图表数据
			for (int i = 0; i < yybsList.size(); i++) {
				Map<String, Object> map = new LinkedHashMap<>();
				yymcList.add(yybsMap.get(yybsList.get(i)));
				map.put("name", yybsMap.get(yybsList.get(i)));
				map.put("type", "line");

				String[] arr = new String[timeList.size()];
				for (int j = 0; j < timeList.size(); j++) {
					String key = yybsList.get(i) + "-" + timeList.get(j);
					Object value = dataMap.get(key);
					if (null == value || "".equals(value)) {
						arr[j] = "0";
					} else {
						arr[j] = value.toString();
					}
				}
				map.put("data", arr);
				chartList.add(map);
			}

		}

		Object result[] = new Object[3];
		result[0] = yymcList;
		result[1] = timeList;
		result[2] = chartList;
		return result;
	}

	@RequestMapping(value = "/fwzlfxList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object fwzlfxList(Page page, HttpServletRequest request) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String yybsSearch = request.getParameter("yybs");
		String timeSearch = request.getParameter("time").replaceAll("-", "");

		if (StringUtil.isNotEmpty(yybsSearch)) {
			condition.put("yybs", yybsSearch);
		}
		if (StringUtil.isNotEmpty(timeSearch)) {
			condition.put("time", timeSearch);
		}
		SearchResult result = temporaryService.fwzlfxList(page, condition);
		return result;
	}

	/**
	 * 用户请求排名分析
	 */
	@RequestMapping(value = "/yhqqpmfxPage", method = RequestMethod.GET)
	public String yhqqpmfxPage(HttpServletRequest request) {
		request.setAttribute("fwcyf", fwzyqqbwcjbService.selectAllFwcyfzcb());
		request.setAttribute("time", DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
		return "business/temp/yhqqpmfx";
	}

	@RequestMapping(value = "/yhqqpmfxSearch", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object[] yhqqpmfxSearch(HttpServletRequest request) {
		// 查询条件
		String yybsSearch = request.getParameter("yybs");
		String timeSearch = request.getParameter("time").replaceAll("-", "");

		// 数据集合
		List<String> yybsList = new ArrayList<String>();
		List<String> yymcList = new ArrayList<String>();
		List<String> timeList = new ArrayList<String>();
		Map<String, String> yybsMap = new LinkedHashMap<>();
		Map<String, Object> dataMap = new LinkedHashMap<>();
		List<Map<String, Object>> chartList = new ArrayList<>();

		// 查询数据
		List<Map<String, Object>> dataList = temporaryDao.fwzlfxSearch(yybsSearch, timeSearch);
		if (CollectionUtils.isNotEmpty(dataList)) {
			for (Map<String, Object> map : dataList) {
				yybsList.add((String) map.get("FFQQZ"));
				timeList.add((String) map.get("HOUR"));
				yybsMap.put((String) map.get("FFQQZ"), (String) map.get("NAME"));
				dataMap.put((String) map.get("KEY"), map.get("VALUE"));
			}

			yybsList = yybsList.stream().distinct().collect(Collectors.toList());
			timeList = timeList.stream().distinct().collect(Collectors.toList());

			// 组装图表数据
			for (int i = 0; i < yybsList.size(); i++) {
				Map<String, Object> map = new LinkedHashMap<>();
				yymcList.add(yybsMap.get(yybsList.get(i)));
				map.put("name", yybsMap.get(yybsList.get(i)));
				map.put("type", "bar");

				String[] arr = new String[timeList.size()];
				for (int j = 0; j < timeList.size(); j++) {
					String key = yybsList.get(i) + "-" + timeList.get(j);
					Object value = dataMap.get(key);
					if (null == value || "".equals(value)) {
						arr[j] = "0";
					} else {
						arr[j] = value.toString();
					}
				}
				map.put("data", arr);
				chartList.add(map);
			}
		}

		Object result[] = new Object[3];
		result[0] = yymcList;
		result[1] = timeList;
		result[2] = chartList;
		return result;
	}

	@RequestMapping(value = "/yhqqpmfxList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object yhqqpmfxList(Page page, HttpServletRequest request) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String yybsSearch = request.getParameter("yybs");
		String timeSearch = request.getParameter("time").replaceAll("-", "");

		if (StringUtil.isNotEmpty(yybsSearch)) {
			condition.put("yybs", yybsSearch);
		}
		if (StringUtil.isNotEmpty(timeSearch)) {
			condition.put("time", timeSearch);
		}
		SearchResult result = temporaryService.yhqqpmfxList(page, condition);
		return result;
	}
	
	
	@RequestMapping(value = "/yjsjhzChart", method = RequestMethod.GET)
	public String yjsjhzChart(HttpServletRequest request) {
		List<String> typeList = Arrays.asList("WARN-FRE", "WARN-KEY");
		List<String> timeList = new ArrayList<String>();
		List<String> freList = new ArrayList<String>();
		List<String> keyList = new ArrayList<String>();
		// 查询数据
		String time1 = DateUtil.formatDate(new Date(), "yyyy-MM-dd");
		List<Map<String, Object>> data = temporaryDao.yjsjhzChart("", "", "", time1, time1);
		if (data.size() > 0) {
			// 存放key-value
			Map<String, Object> map = new HashMap<String, Object>();
			for (Map<String, Object> o : data) {
				map.put(o.get("KEY").toString(), o.get("VALUE"));
				timeList.add(o.get("TIME").toString());
			}
			timeList = timeList.stream().distinct().collect(Collectors.toList());

			for (String time : timeList) {
				for (String type : typeList) {
					String key = time + "-" + type;
					Object value = map.get(key);
					if (null != value && !"".equals(value)) {
						if ("WARN-FRE".equals(type)) {
							freList.add(value.toString());
						} else {
							keyList.add(value.toString());
						}
					} else {
						if ("WARN-FRE".equals(type)) {
							freList.add("0");
						} else {
							keyList.add("0");
						}
					}
				}
			}
		}

		int count1 = temporaryDao.fxsjhzCount("", "", "", time1, time1, "WARN-FRE");
		int count2 = temporaryDao.fxsjhzCount("", "", "", time1, time1, "WARN-KEY");
		
		request.setAttribute("times", JSON.toJSON(timeList));
		request.setAttribute("freList", JSON.toJSON(freList));
		request.setAttribute("keyList", JSON.toJSON(keyList));
		request.setAttribute("fwcyf", fwzyqqbwcjbService.selectAllFwcyfzcb());
		request.setAttribute("fwzy", fwzyqqbwcjbService.selectAllFwzyzcb());
		request.setAttribute("time1", time1);
		request.setAttribute("count1", count1);
		request.setAttribute("count2", count2);
		return "business/temp/yjsjcx";
	}

	@RequestMapping(value = "/yjsjhzChartSearch", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object[] yjsjhzChartSearch(HttpServletRequest request) {
		String yybs = request.getParameter("yybs");
		String fwbs = request.getParameter("fwbs");
		String ffbs = request.getParameter("ffbs");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		Object result[] = new Object[5];
		List<String> typeList = Arrays.asList("WARN-FRE", "WARN-KEY");
		List<String> timeList = new ArrayList<String>();
		List<String> freList = new ArrayList<String>();
		List<String> keyList = new ArrayList<String>();
		// 查询数据
		List<Map<String, Object>> data = temporaryDao.yjsjhzChart(yybs, fwbs, ffbs, startTime, endTime);
		if (data.size() > 0) {
			// 存放key-value
			Map<String, Object> map = new HashMap<String, Object>();
			for (Map<String, Object> o : data) {
				map.put(o.get("KEY").toString(), o.get("VALUE"));
				timeList.add(o.get("TIME").toString());
			}
			timeList = timeList.stream().distinct().collect(Collectors.toList());

			for (String time : timeList) {
				for (String type : typeList) {
					String key = time + "-" + type;
					Object value = map.get(key);
					if (null != value && !"".equals(value)) {
						if ("WARN-FRE".equals(type)) {
							freList.add(value.toString());
						} else {
							keyList.add(value.toString());
						}
					} else {
						if ("WARN-FRE".equals(type)) {
							freList.add("0");
						} else {
							keyList.add("0");
						}
					}
				}
			}
		}
		
		int count1 = temporaryDao.fxsjhzCount("", "", "", startTime, endTime, "WARN-FRE");
		int count2 = temporaryDao.fxsjhzCount("", "", "", startTime, endTime, "WARN-KEY");

		result[0] = timeList;
		result[1] = freList;
		result[2] = keyList;
		result[3] = count1;
		result[4] = count2;
		return result;
	}
	
	
	//======================预警事件查询======================
	@RequestMapping(value = "/yjxxcxPage", method = RequestMethod.GET)
	public String yjxxcxPage(HttpServletRequest request) {
		request.setAttribute("time", DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
		return "business/temp/yjxxcx";
	}

	@RequestMapping(value = "/yjxxcx", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object yjxxcx(Page page, HttpServletRequest request) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String yymc = request.getParameter("yymc");
		String fwmc = request.getParameter("fwmc");
		String ffmc = request.getParameter("ffmc");
		String warnType = request.getParameter("warnType");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		if (StringUtil.isNotEmpty(yymc)) {
			condition.put("yymc", yymc);
		}
		if (StringUtil.isNotEmpty(fwmc)) {
			condition.put("fwmc", fwmc);
		}
		if (StringUtil.isNotEmpty(ffmc)) {
			condition.put("ffmc", ffmc);
		}
		if (StringUtil.isNotEmpty(warnType)) {
			condition.put("warnType", warnType);
		}
		if (StringUtil.isNotEmpty(startTime)) {
			condition.put("startTime", startTime);
		}
		if (StringUtil.isNotEmpty(endTime)) {
			condition.put("endTime", endTime);
		}
		SearchResult result = temporaryService.yjxxcxList(page, condition);
		return result;
	}

	@RequestMapping(value = "/warnEsLog1", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object warnEsLog1(Page page, HttpServletRequest request)
			throws ElasticsearchException, IOException, ParseException {
		String FWBS = request.getParameter("FWBS");
		String FFBS = request.getParameter("FFBS");
		String time1 = request.getParameter("startTime").replaceAll("-", "");
		String time2 = request.getParameter("endTime").replaceAll("-", "");

		SearchResponse<HashMap> search = restClient
				.search(s -> s.index(configInfo.getLogTable()).query(query -> query.bool(bool -> {
					bool.must(q -> q.matchPhrase(t -> t.field("bj_logs_json.requestData.FWBS").query(FWBS)));
					bool.must(q -> q.matchPhrase(t -> t.field("bj_logs_json.requestData.FFBS").query(FFBS)));
					bool.must(q -> q.matchPhrase(t -> t.field("bj_logs_json.status").query("01")));
					String startTime = time1 + "000000000";
					String endTime = time2 + "235959999";
					bool.must(r -> r
							.range(t -> t.term(f -> f.field("bj_logs_json.requestTime").gte(startTime).lte(endTime))));
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
		return new SearchResult(list, (int) search.hits().total().value());
	}

}

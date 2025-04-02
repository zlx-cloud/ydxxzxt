package net.sinodata.business.rest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sinodata.business.entity.ConfigInfo;
import net.sinodata.business.service.FwzyqqbwcjbService;
import net.sinodata.business.util.DateUtil;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;
import net.sinodata.business.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

@Controller
@RequestMapping(value = "/esWarn")
public class EsWarnController {

	@Autowired
	private FwzyqqbwcjbService fwzyqqbwcjbService;
	@Autowired
	private ElasticsearchClient restClient;
	@Autowired(required = false)
	private ConfigInfo configInfo;
	
	private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(Model model, HttpServletRequest request) {
		request.setAttribute("fwcyf", fwzyqqbwcjbService.selectAllFwcyfzcb());
		request.setAttribute("fwzy", fwzyqqbwcjbService.selectAllFwzyzcb());
		request.setAttribute("time", DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
		return "business/config/esWarn";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list(Page page, HttpServletRequest request, HttpServletResponse response)
			throws ElasticsearchException, IOException, ParseException {

		Map<String, Object> condition = new HashMap<String, Object>();

		String fwbs = request.getParameter("fwbsSearch");
		if (StringUtil.isNotEmpty(fwbs)) {
			condition.put("fwbsSearch", fwbs);
		}
		String ffbs = request.getParameter("ffbsSearch");
		if (StringUtil.isNotEmpty(ffbs)) {
			condition.put("ffbsSearch", ffbs);
		}
		String fwqqzZcxx = request.getParameter("fwqqzZcxxSearch");
		if (StringUtil.isNotEmpty(fwqqzZcxx)) {
			condition.put("fwqqzZcxxSearch", fwqqzZcxx);
		}
		
		String time1 = request.getParameter("startTime").replaceAll("-", "");
		String time2 = request.getParameter("endTime").replaceAll("-", "");

		SearchResponse<HashMap> search = restClient
				.search(s -> s.index(configInfo.getLogTable()).query(query -> query.bool(bool -> {
					if (StringUtil.isNotEmpty(fwbs)) {
						bool.must(q -> q.matchPhrase(t -> t.field("bj_logs_json.requestData.FWBS").query(fwbs)));
					}
					if (StringUtil.isNotEmpty(ffbs)) {
						bool.must(q -> q.matchPhrase(t -> t.field("bj_logs_json.requestData.FFBS").query(ffbs)));
					}
					if (StringUtil.isNotEmpty(fwqqzZcxx)) {
						bool.must(q -> q
								.matchPhrase(t -> t.field("bj_logs_json.requestData.FWQQZ_ZCXX").query(fwqqzZcxx)));
					}
					
					String startTime = time1 + "000000000";
					String endTime = time2 + "235959999";
					bool.must(r -> r
							.range(t -> t.term(f -> f.field("bj_logs_json.requestTime").gte(startTime).lte(endTime))));

					bool.must(q -> q.matchPhrase(t -> t.field("bj_logs_json.status").query("00")))
							.mustNot(m -> m.exists(e -> e.field("bj_logs_json.errorTime")))
							.should(should -> should.bool(
									bool1 -> bool1.mustNot(m -> m.exists(e -> e.field("bj_logs_json.finishWallTime")))))
							.should(should -> should.bool(
									bool1 -> bool1.mustNot(m -> m.exists(e -> e.field("bj_logs_json.finish3Time")))))
							.minimumShouldMatch("1");

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
			bjLogsJson.put("statusStr", "正常");
			list.add(bjLogsJson);
		}
		return new SearchResult(list, (int) search.hits().total().value());
	}

}
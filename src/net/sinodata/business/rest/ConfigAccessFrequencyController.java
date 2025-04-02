package net.sinodata.business.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;
import net.sinodata.business.entity.ConfigAccessFrequency;
import net.sinodata.business.entity.ConfigInfo;
import net.sinodata.business.service.ConfigAccessFrequencyService;
import net.sinodata.business.util.DateUtil;
import net.sinodata.business.util.HttpRequest;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.ResponseUtil;
import net.sinodata.business.util.SearchResult;
import net.sinodata.business.util.StringUtil;
import net.sinodata.business.util.UUIDGeneratorUtil;

@Controller
@RequestMapping(value = "/configAccessFrequency")
public class ConfigAccessFrequencyController {

	@Autowired
	private ConfigAccessFrequencyService configAccessFrequencyService;
	@Autowired(required = false)
	private ConfigInfo configInfo;

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(Model model) {
		return "business/config/fwpdpz";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list(Page page, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String consumer = request.getParameter("consumer");
		String consumerName = request.getParameter("consumerName");
		String yybs = request.getParameter("yybs");
		String yymc = request.getParameter("yymc");
		String fwbs = request.getParameter("fwbs");
		String fwmc = request.getParameter("fwmc");
		String ffbs = request.getParameter("ffbs");
		String ffmc = request.getParameter("ffmc");

		if (StringUtil.isNotEmpty(consumer)) {
			condition.put("consumer", consumer);
		}
		if (StringUtil.isNotEmpty(consumerName)) {
			condition.put("consumerName", consumerName);
		}
		if (StringUtil.isNotEmpty(yybs)) {
			condition.put("yybs", yybs);
		}
		if (StringUtil.isNotEmpty(yymc)) {
			condition.put("yymc", yymc);
		}
		if (StringUtil.isNotEmpty(fwbs)) {
			condition.put("fwbs", fwbs);
		}
		if (StringUtil.isNotEmpty(fwmc)) {
			condition.put("fwmc", fwmc);
		}
		if (StringUtil.isNotEmpty(ffbs)) {
			condition.put("ffbs", ffbs);
		}
		if (StringUtil.isNotEmpty(ffmc)) {
			condition.put("ffmc", ffmc);
		}
		SearchResult result = configAccessFrequencyService.list(page, condition);
		return result;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public void addUpdate(ConfigAccessFrequency configAccessFrequency, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		String flag = request.getParameter("flag");
		int i = 0;
		if (StringUtil.isEmpty(flag)) {
			configAccessFrequency.setUpdateTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			i = configAccessFrequencyService.updateByPrimaryKeySelective(configAccessFrequency);
		} else {
			configAccessFrequency.setId(UUIDGeneratorUtil.getUUID());
			configAccessFrequency.setEnabled("N");
			configAccessFrequency.setCreateTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			i = configAccessFrequencyService.insertSelective(configAccessFrequency);
		}
		if (i > 0) {
			result.put("success", "true");
		} else {
			result.put("errorMsg", "保存失败");
		}
		ResponseUtil.write(response, result);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		String id = request.getParameter("id");
		int delNums = configAccessFrequencyService.deleteByPrimaryKey(id);
		if (delNums > 0) {
			result.put("success", true);
			result.put("delNums", delNums);
		} else {
			result.put("errorMsg", "删除失败");
		}
		ResponseUtil.write(response, result);
	}

	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void updateStatus(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String yybs = request.getParameter("yybs");
		String fwbs = request.getParameter("fwbs");
		String enabled = request.getParameter("enabled");
		String updateTime = DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
		int i = configAccessFrequencyService.updateStatus(id, enabled, updateTime);
		JSONObject result = new JSONObject();
		if (i > 0) {
			result.put("success", "true");
			syncBuildData(yybs, fwbs);
		}
		ResponseUtil.write(response, result);
	}

	public void syncBuildData(String yybs, String fwbs) {
		Map<String, String> condition = new HashMap<>();
		condition.put("paramKey", yybs + "_" + fwbs);
		String str = HttpRequest.sendGetRequest(configInfo.getSyncDataUrl(), condition);
		System.out.println(str);
	}

}
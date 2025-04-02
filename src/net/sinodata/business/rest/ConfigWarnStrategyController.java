package net.sinodata.business.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;
import net.sinodata.business.entity.ConfigInfo;
import net.sinodata.business.entity.ConfigWarnStrategy;
import net.sinodata.business.service.ConfigWarnStrategyService;
import net.sinodata.business.util.DateUtil;
import net.sinodata.business.util.HttpRequest;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.ResponseUtil;
import net.sinodata.business.util.SearchResult;
import net.sinodata.business.util.StringUtil;
import net.sinodata.business.util.UUIDGeneratorUtil;
import net.sinodata.security.vo.ShiroUser;

@Controller
@RequestMapping(value = "/configWarnStrategy")
public class ConfigWarnStrategyController {

	@Autowired
	private ConfigWarnStrategyService configWarnStrategyService;
	@Autowired(required = false)
	private ConfigInfo configInfo;

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(Model model) {
		return "business/config/yjclpz";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list(Page page, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String name = request.getParameter("name");
		String yybs = request.getParameter("yybs");
		String yymc = request.getParameter("yymc");
		String fwbs = request.getParameter("fwbs");
		String fwmc = request.getParameter("fwmc");
		String ffbs = request.getParameter("ffbs");
		String ffmc = request.getParameter("ffmc");
		String userName = request.getParameter("userName");
		String startTime = request.getParameter("startTime");

		if (StringUtil.isNotEmpty(name)) {
			condition.put("name", name);
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
		if (StringUtil.isNotEmpty(userName)) {
			condition.put("userName", userName);
		}
		if (StringUtil.isNotEmpty(startTime)) {
			condition.put("startTime", startTime);
		}
		
		SearchResult result = configWarnStrategyService.list(page, condition);
		return result;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public void addUpdate(ConfigWarnStrategy configWarnStrategy, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject result = new JSONObject();
		String flag = request.getParameter("flag");
		int i = 0;
		if (StringUtil.isEmpty(flag)) {
			configWarnStrategy.setUpdateTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			i = configWarnStrategyService.updateByPrimaryKeySelective(configWarnStrategy);
		} else {
			configWarnStrategy.setId(UUIDGeneratorUtil.getUUID());
			configWarnStrategy.setEnabled("N");
			configWarnStrategy.setCreateTime(DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			configWarnStrategy.setUserId(((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getId());
			i = configWarnStrategyService.insertSelective(configWarnStrategy);
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
		int delNums = configWarnStrategyService.deleteByPrimaryKey(id);
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
		int i = configWarnStrategyService.updateStatus(id, enabled, updateTime);
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
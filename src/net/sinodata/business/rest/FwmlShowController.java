package net.sinodata.business.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sinodata.business.service.FwmlShowService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;
import net.sinodata.business.util.StringUtil;

@Controller
@RequestMapping(value = "/fwmlshow")
public class FwmlShowController {

	@Autowired
	FwmlShowService service;

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public String page(Model model, HttpServletRequest request) {
		List<Map<String,Object>> jzInfo = service.listJzInfo();
		request.setAttribute("jzInfo", jzInfo);
		return "business/fwmlshow";
	}

	@RequestMapping(value = "/data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object data(Page page, HttpServletRequest request) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String yymc = request.getParameter("yymc");
		if (StringUtil.isNotEmpty(yymc)) {
			condition.put("yymc", yymc);
		}
		String fwmc = request.getParameter("fwmc");
		if (StringUtil.isNotEmpty(fwmc)) {
			condition.put("fwmc", fwmc);
		}
		String ffbs = request.getParameter("ffbs");
		if (StringUtil.isNotEmpty(ffbs)) {
			condition.put("ffbs", ffbs);
		}
		String ffmc = request.getParameter("ffmc");
		if (StringUtil.isNotEmpty(ffmc)) {
			condition.put("ffmc", ffmc);
		}
		String jgmc = request.getParameter("jgmc");
		if (StringUtil.isNotEmpty(jgmc)) {
			condition.put("jgmc", jgmc);
		}
		String wlfl = request.getParameter("wlfl");
		if (StringUtil.isNotEmpty(wlfl)) {
			condition.put("wlfl", wlfl);
		}
		String czfl = request.getParameter("czfl");
		if (StringUtil.isNotEmpty(czfl)) {
			condition.put("czfl", czfl);
		}
		String jzlbmc = request.getParameter("jzlbmc");
		if (StringUtil.isNotEmpty(jzlbmc)) {
			condition.put("jzlbmc", jzlbmc);
		}
		SearchResult result = service.list(page, condition);
		return result;
	}

}
package net.sinodata.business.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sinodata.business.service.SjtsfwService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;
import net.sinodata.business.util.StringUtil;

@Controller
@RequestMapping(value = "/sjtsfw")
public class SjtsfwController {

	@Autowired
	SjtsfwService service;

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public String page(Model model) {
		return "business/sjtsfw";
	}

	@RequestMapping(value = "/data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object data(Page page, HttpServletRequest request) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String type = request.getParameter("type");
		if (StringUtil.isNotEmpty(type)) {
			condition.put("type", type);
		}
		String name = request.getParameter("name");
		if (StringUtil.isNotEmpty(name)) {
			condition.put("name", name);
		}
		SearchResult result = service.list(page, condition);
		return result;
	}

}
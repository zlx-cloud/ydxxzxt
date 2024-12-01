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

import net.sinodata.business.service.PoliceMemberService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;
import net.sinodata.business.util.StringUtil;

@Controller
@RequestMapping("/policemember")
public class PoliceMemberController {

	@Autowired
	PoliceMemberService service;

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public String page(Model model) {
		return "business/policemember";
	}

	@RequestMapping(value = "/data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object data(Page page, HttpServletRequest request) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String jh = request.getParameter("jh");
		if (StringUtil.isNotEmpty(jh)) {
			condition.put("jh", jh);
		}
		String sfzh = request.getParameter("sfzh");
		if (StringUtil.isNotEmpty(sfzh)) {
			condition.put("sfzh", sfzh);
		}
		String name = request.getParameter("name");
		if (StringUtil.isNotEmpty(name)) {
			condition.put("name", name);
		}
		String jz = request.getParameter("jz");
		if (StringUtil.isNotEmpty(jz)) {
			condition.put("jz", jz);
		}
		String sex = request.getParameter("sex");
		if (StringUtil.isNotEmpty(sex)) {
			condition.put("sex", sex);
		}
		String jg = request.getParameter("jg");
		if (StringUtil.isNotEmpty(jg)) {
			condition.put("jg", jg);
		}
		String phone = request.getParameter("phone");
		if (StringUtil.isNotEmpty(phone)) {
			condition.put("phone", phone);
		}
		SearchResult result = service.list(page, condition);
		return result;
	}

}
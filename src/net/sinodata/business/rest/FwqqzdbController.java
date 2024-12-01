package net.sinodata.business.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sinodata.business.entity.Fwqqzdb;
import net.sinodata.business.service.FwqqzdbService;
import net.sinodata.business.util.ResponseUtil;
import net.sinodata.business.util.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//请求检查字段
@RequestMapping(value="/qqjczd")
public class FwqqzdbController {

	@Autowired
	private FwqqzdbService fwqqzdbService;
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(Model model, HttpServletRequest request) {
		
		return "business/qqjczd";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list(HttpServletRequest request,
			HttpServletResponse response) {
		SearchResult result = fwqqzdbService.getList();
		return result;
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public void createRole(Fwqqzdb fwqqzdb, HttpServletRequest request,
		HttpServletResponse response) {
		JSONObject result=new JSONObject();
		fwqqzdbService.updateFwqqzdb(fwqqzdb);
		result.put("success", true);
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

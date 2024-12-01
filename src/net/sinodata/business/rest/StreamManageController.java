package net.sinodata.business.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sinodata.business.service.FwStreamService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.ResponseUtil;
import net.sinodata.business.util.SearchResult;
import net.sinodata.business.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/streamManage")
public class StreamManageController {
	
	@Autowired
	private FwStreamService streamManageService;
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String menuList(Model model) {

		return "business/streamManage";
	}

	@RequestMapping(value = "/tree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void tree( HttpServletRequest request,
			HttpServletResponse response) {
		JSONArray jsonArray=streamManageService.fwOrgList();
		try {
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String orgid=request.getParameter("orgid");
		
		if(StringUtil.isNotEmpty(orgid)){
			
			condition.put("orgid",orgid);
		}
	    SearchResult result = streamManageService.streamList(page, condition);
		
		return result;
	}
	
	@RequestMapping(value = "/startProcess", method = RequestMethod.POST)
	@ResponseBody
	public void startProcess(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result=new JSONObject();
		String streamId = request.getParameter("streamId");
		if(StringUtil.isNotEmpty(streamId)){
			streamManageService.startEsb(streamId);
			result.put("success", true);
		}else{
			result.put("success", true);
			result.put("errorMsg", "未指定流程");
		}
		
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/stopProcess", method = RequestMethod.POST)
	@ResponseBody
	public void stopProcess(HttpServletRequest request, HttpServletResponse response) {
		JSONObject result=new JSONObject();
		String streamId = request.getParameter("streamId");
		if(StringUtil.isNotEmpty(streamId)){
			streamManageService.stopEsb(streamId);
			result.put("success", true);
		}else{
			result.put("success", true);
			result.put("errorMsg", "未指定流程");
		}
		
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/streamDataList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object streamDatalist(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String streamId=request.getParameter("streamId");
		String state=request.getParameter("state");
		
		if(StringUtil.isNotEmpty(streamId)){
			condition.put("p_id",streamId);
		}
		if(StringUtil.isNotEmpty(state)){
			condition.put("state",state);
		}
	    SearchResult result = streamManageService.streamDataList(page, condition);
		
		return result;
	}
	
	@RequestMapping(value = "/streamList", method = RequestMethod.GET)
	public String streamList(Model model,HttpServletRequest request) {
		request.setAttribute("streamId", request.getParameter("streamId"));
		request.setAttribute("state", request.getParameter("state"));
		return "business/streamList";
	}
}

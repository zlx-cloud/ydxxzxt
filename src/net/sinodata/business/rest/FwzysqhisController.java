package net.sinodata.business.rest;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sinodata.business.service.FwtcService;
import net.sinodata.business.service.FwzysqbhisService;
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

@Controller
@RequestMapping(value = "/fwSqhisManage")
public class FwzysqhisController {
	//private static Logger logger = LoggerFactory.getLogger(FwzysqhisController.class);
	@Autowired
	FwzysqbhisService FwzysqbhisService;
	@Autowired
	FwtcService fwtcService;
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String fwSqhisList(Model model,HttpServletRequest request) {
		String fwbs=request.getParameter("fwbs");
		request.setAttribute("fwbs", fwbs);
		return "business/fwSqhisManage";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String fwbs=request.getParameter("fwbs");
		String fwmc=request.getParameter("fwmc");
		String czsj=request.getParameter("czsj");
		String czls=request.getParameter("czls");
		String ffmc=request.getParameter("ffmc");
		
		
		
		if(StringUtil.isNotEmpty(fwbs)){
			condition.put("fwbs",fwbs);
		}
		if(StringUtil.isNotEmpty(fwmc)){
			condition.put("fwmc",fwmc);
		}
		if(StringUtil.isNotEmpty(czsj)){
			
			condition.put("czsj",czsj);
		}
		if(StringUtil.isNotEmpty(czls)){
			condition.put("czls",czls);
		}
		if(StringUtil.isNotEmpty(ffmc)){
			condition.put("ffmc",ffmc);
		}
	    SearchResult resoult = FwzysqbhisService.List(page, condition);
	   
		return resoult;
	}
	
	@RequestMapping(value = "/showFailHis", method = RequestMethod.GET)
	public String showFailHis(Model model,HttpServletRequest request) {
		String fwbs=request.getParameter("fwbs");
		request.setAttribute("fwbs", fwbs);
		return "business/fwTchisManage";
	}
	
	
	
	@RequestMapping(value = "/faillist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object faillist(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String fwbs=request.getParameter("fwbs");
		
		if(StringUtil.isNotEmpty(fwbs)){
			condition.put("fwbs",fwbs);
		}
		 
	    SearchResult resoult = fwtcService.list(page, condition);
		return resoult;
	}
}

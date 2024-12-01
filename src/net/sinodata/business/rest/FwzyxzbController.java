package net.sinodata.business.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sinodata.business.entity.Fwzyxzb;
import net.sinodata.business.service.FwzyxzbService;
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
@RequestMapping(value="/fwzyxzb")
public class FwzyxzbController {

	@Autowired
	private FwzyxzbService fwzyxzbService;
	
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(Model model, HttpServletRequest request) {
		return "business/fwzyxzb";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String yymc = request.getParameter("yymc");
		if(StringUtil.isNotEmpty(yymc)){
			condition.put("yymc", yymc);
		}
		
		String fwbs = request.getParameter("fwbs");
		if(StringUtil.isNotEmpty(fwbs)){
			condition.put("fwbs", fwbs);
		}
		
		String fwmc = request.getParameter("fwmc");
		if(StringUtil.isNotEmpty(fwmc)){
			condition.put("fwmc", fwmc);
		}
		SearchResult result =fwzyxzbService.xzList(page, condition);
		
		return result;
	}
	

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public void addUpdate(Fwzyxzb Fwzyxzb,HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject result=new JSONObject();
		String flag=request.getParameter("flag");
		int i=0;
			if(!StringUtil.isNotEmpty(flag)){
				i=fwzyxzbService.updateByPrimaryKeySelective(Fwzyxzb);
			}else{
			
			  i=fwzyxzbService.insertSelective(Fwzyxzb);
			}
			
		try {
			if(i>0){
				result.put("success", "true");
			}else{
				result.put("errorMsg", "保存失败");
			}
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/delete",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void delete(HttpServletRequest request,
			HttpServletResponse response,String delIds) {
		JSONObject result=new JSONObject();
		
		try {
			String fwbs=request.getParameter("fwbs");
			int delNums=fwzyxzbService.deleteByPrimaryKey(fwbs);
			
			if(delNums>0){
				result.put("success", true);
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "删除失败");
			}

			ResponseUtil.write(response, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}
	
	
	@RequestMapping(value="/yjtb",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object yjtb(HttpServletRequest request, HttpServletResponse response) {
		String msg="";
		List<Map<String,Object>> zcbList = new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> xzbList = new ArrayList<Map<String,Object>>();
		zcbList=fwzyxzbService.queryFwbsByZcb();
		xzbList=fwzyxzbService.queryFwbsByXzb();
		
		for (int i = 0; i < xzbList.size(); i++) {
			zcbList.remove(xzbList.get(i));
		}
		
		if(zcbList.size()>0) {
			for (int i = 0; i < zcbList.size(); i++) {
				Fwzyxzb Fwzyxzb = new Fwzyxzb();
				String fwbs=zcbList.get(i).get("FWBS").toString();
				Fwzyxzb.setFwbs(fwbs);
				Fwzyxzb.setDayCount("0");
				Fwzyxzb.setHourCount("0");
				Fwzyxzb.setMinuteCount("0");
				Fwzyxzb.setFwxzzt("0");
				Fwzyxzb.setFwzt("0");
				fwzyxzbService.insertSelective(Fwzyxzb);
			}
		}
		return msg;
	}
}

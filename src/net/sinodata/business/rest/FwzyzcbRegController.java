package net.sinodata.business.rest;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sinodata.business.entity.Fwzyzcb;
import net.sinodata.business.entity.FwzyzcbReg;
import net.sinodata.business.service.FwzyzcbRegService;
import net.sinodata.business.service.ServeManageService;
import net.sinodata.business.util.DateUtil;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.ResponseUtil;
import net.sinodata.business.util.SearchResult;
import net.sinodata.business.util.StringUtil;
import net.sinodata.security.vo.ShiroUser;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/fwzyzcbRegManage")
public class FwzyzcbRegController {
	//private static Logger logger = LoggerFactory.getLogger(FwzyzcbRegController.class);

	
	@Autowired
	FwzyzcbRegService FwzyzcbRegService;
	@Autowired
	ServeManageService serveManageService;
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(Model model) {

		return "business/fwzyzcbRegManage";
	}
	@RequestMapping(value = "/shshow", method = RequestMethod.GET)
	public String shshow(Model model) {

		return "business/fwzyzcbRegShManage";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String fwmc=request.getParameter("fwmc");
		String fwbs=request.getParameter("fwbs");
		String status=request.getParameter("status");
		String fwtgzYyxtbh=request.getParameter("fwtgzYyxtbh");
		String sfhcsj=request.getParameter("sfhcsj");
		
		if(StringUtil.isNotEmpty(fwmc)){
			
			condition.put("fwmc",fwmc);
		}
		if(StringUtil.isNotEmpty(fwbs)){
			condition.put("fwbs",fwbs);
		}
		if(StringUtil.isNotEmpty(status)){
			condition.put("status",status);
		}
		if(StringUtil.isNotEmpty(fwtgzYyxtbh)){
			condition.put("fwtgzYyxtbh",fwtgzYyxtbh);
		}
		if(StringUtil.isNotEmpty(sfhcsj)){
			condition.put("sfhcsj",sfhcsj);
		}
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		
      if(!shiroUser.getRoleid().equals("1")){
    	  condition.put("fwtgzYyxtbh","'"+shiroUser.getId()+"'");
		}
	    SearchResult resoult = FwzyzcbRegService.fwzyzcbRegList(page, condition);
	    request.getSession().setAttribute("selectMap", condition);
		return resoult;
	}
	
	
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public void addUpdate(FwzyzcbReg FwzyzcbReg,HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject result=new JSONObject();
		String flag=request.getParameter("flag");
		int i=0;
		    FwzyzcbReg.setStatus("1");
			if(!StringUtil.isNotEmpty(flag)){
				i=FwzyzcbRegService.updateByPrimaryKeySelective(FwzyzcbReg);
			}else{
				try {
					
					FwzyzcbReg.setFwzcRqsj(DateUtil.formatDateToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			  i=FwzyzcbRegService.insertSelective(FwzyzcbReg);
			}
			
					
			
				
			
			
		
		try {
			if(i>0){
				result.put("success", "true");
			}else{
				result.put("errorMsg", "保存失败");
			}
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@RequestMapping(value="/updateStatus",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void updateStatus(HttpServletRequest request,
			HttpServletResponse response,String delIds) {
		String fwbs=request.getParameter("fwbs");
		String fwmc=request.getParameter("fwmc");
		String status=request.getParameter("status");
		FwzyzcbReg record=new FwzyzcbReg();
		record.setFwbs(fwbs);
		record.setStatus(status);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("fwbs",fwbs);
		map.put("fwmc",fwmc);
		map.put("ffbs","FUN999");
		map.put("ffmc","TEST");
		map.put("ffms","用于接口探测");
		map.put("fwqqcs","");
		map.put("fwkg","1");
		FwzyzcbRegService.insertTcb(map);
		int i=FwzyzcbRegService.updateByPrimaryKeySelective(record);
		if(status.equals("0")){
			
			FwzyzcbReg FwzyzcbReg=FwzyzcbRegService.selectByPrimaryKey(fwbs);
			Fwzyzcb Fwzyzcb=new Fwzyzcb();
			ConvertUtils.register(new DateConverter(null), java.util.Date.class);  
			try {
				BeanUtils.copyProperties(Fwzyzcb,FwzyzcbReg);
			i+=	serveManageService.insert(Fwzyzcb);
			} catch (IllegalAccessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		
		JSONObject result=new JSONObject();
		if(i>0){
			result.put("success", "true");
		}
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
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
			
			int delNums=FwzyzcbRegService.deleteByPrimaryKey(fwbs);
			
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
}

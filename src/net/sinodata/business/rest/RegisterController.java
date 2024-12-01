package net.sinodata.business.rest;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sinodata.business.entity.FwcyfzcbReg;
import net.sinodata.business.service.FwcyfzcbRegService;
import net.sinodata.business.service.JgdmbService;
import net.sinodata.business.service.UserTService;
import net.sinodata.business.util.DateUtil;
import net.sinodata.business.util.ResponseUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/register")
public class RegisterController {
	Logger logger = LoggerFactory.getLogger(RegisterController.class);
	@Autowired
	FwcyfzcbRegService FwcyfzcbRegService;
	@Autowired
	JgdmbService JgdmbService;
	@Autowired
	UserTService UserTService;
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(HttpServletRequest request,
			HttpServletResponse response) {
		
		return "account/registerUser";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public void createUser(FwcyfzcbReg user,HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONObject result=new JSONObject();
		user.setStatus("1");
		user.setRoleid("43");
		user.setFwcyfRqsj(DateUtil.formatDateToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		
        if( UserTService.findUserByLoginName(user.getFwcyfYyxtmc()).size()>0){
			
			result.put("errorMsg", "此用户名已经存在");
		}
        /*else if(StringUtil.isEmpty(user.getFwcyfYyxtbh())){
			
			result.put("errorMsg", "保存失败");
		}*/
        
        else{
        	FwcyfzcbRegService.insertSelective(user);
			result.put("success", user.getFwcyfYyxtbh());
		}
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/jgTree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void jgTree(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sssjjg=request.getParameter("sssjjg");
		JSONArray jsonArray=JgdmbService.queryJgdmTreeList(sssjjg,null,null);
		try {
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
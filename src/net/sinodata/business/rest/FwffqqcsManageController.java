package net.sinodata.business.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sinodata.business.entity.Fwzyffqqcsb;
import net.sinodata.business.entity.FwzyffqqcsbKey;
import net.sinodata.business.entity.Fwzyffqqcsbhis;
import net.sinodata.business.service.FwzyffqqcsbService;
import net.sinodata.business.service.FwzyffqqcsbhisService;
import net.sinodata.business.service.ServeManageService;
import net.sinodata.business.util.DateUtil;
import net.sinodata.business.util.ExcelReaderDown;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.ResponseUtil;
import net.sinodata.business.util.SearchResult;
import net.sinodata.business.util.StringUtil;
import net.sinodata.security.vo.ShiroUser;

import org.apache.commons.io.FileUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/fwFFqqcsManage")
public class FwffqqcsManageController {
	//private static Logger logger = LoggerFactory.getLogger(FwffqqcsManageController.class);
	@Autowired
	FwzyffqqcsbService FwzyffqqcsbService;
	@Autowired
	FwzyffqqcsbhisService FwzyffqqcsbhisService;
	@Autowired
	ServeManageService serveManageService;
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(Model model) {

		return "business/fwFFqqcsManage";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String fwbs=request.getParameter("fwbs");
		String ffbs=request.getParameter("ffbs");
		String qqcsbs=request.getParameter("qqcsbs");
		String fwmc=request.getParameter("fwmc");
		String ffmc=request.getParameter("ffmc");
		String qqcsm=request.getParameter("qqcsm");
		
		
		if(StringUtil.isNotEmpty(ffbs)){
			condition.put("ffbs",ffbs);
		}
		if(StringUtil.isNotEmpty(fwbs)){
			condition.put("fwbs",fwbs);
		}
		if(StringUtil.isNotEmpty(qqcsbs)){
			condition.put("qqcsbs",qqcsbs);
		}
		if(StringUtil.isNotEmpty(fwmc)){
			condition.put("fwmc",fwmc);
		}
		if(StringUtil.isNotEmpty(ffmc)){
			condition.put("ffmc",ffmc);
		}
		if(StringUtil.isNotEmpty(qqcsm)){
			condition.put("qqcsm",qqcsm);
		}
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if(!shiroUser.getRoleid().equals("1")){
		condition.put("fwtgzYyxtbh",shiroUser.getId());
		}
	    SearchResult resoult = FwzyffqqcsbService.List(page, condition);
	    request.getSession().setAttribute("selectMap", condition);
		return resoult;
	}
	
	
	
	@RequestMapping(value = "/showHis", method = RequestMethod.GET)
	public String showHis(Model model,HttpServletRequest request) {
		request.setAttribute("fwbs", request.getParameter("fwbs"));
		return "business/fwFFqqcshisManage";
	}
	
	@RequestMapping(value = "/listHis", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listHis(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String fwbs=request.getParameter("fwbs");
		String ffbs=request.getParameter("ffbs");
		String qqcsbs=request.getParameter("qqcsbs");
		String qqcsm=request.getParameter("qqcsm");
		String optType=request.getParameter("optType");
		String optTime=request.getParameter("optTime");
        if(StringUtil.isNotEmpty(optType)){
			
	    condition.put("optType",optType);
		}
       if(StringUtil.isNotEmpty(optTime)){
	
	    condition.put("optTime",optTime);
        }
		
		if(StringUtil.isNotEmpty(ffbs)){
			
			condition.put("ffbs",ffbs);
		}
		if(StringUtil.isNotEmpty(fwbs)){
			condition.put("fwbs",fwbs);
		}
		if(StringUtil.isNotEmpty(qqcsbs)){
			condition.put("qqcsbs",qqcsbs);
		}
		if(StringUtil.isNotEmpty(qqcsm)){
			condition.put("qqcsm",qqcsm);
		}
		/*ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if(!shiroUser.getRoleid().equals("1")){
		condition.put("fwtgzYyxtbh",shiroUser.getId());
		}*/
	    SearchResult resoult = FwzyffqqcsbhisService.List(page, condition);
	   /* request.getSession().setAttribute("selectMap", condition);*/
		return resoult;
	}
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public void addUpdate(Fwzyffqqcsb Fwzyffqqcsb,HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject result=new JSONObject();
		String flag=request.getParameter("flag");
		int i=0;
		Fwzyffqqcsb j=null;
		FwzyffqqcsbKey key=new FwzyffqqcsbKey();
		key.setFfbs(Fwzyffqqcsb.getFfbs());
		key.setFwbs(Fwzyffqqcsb.getFwbs());
		key.setQqcsbs(Fwzyffqqcsb.getQqcsbs());
		//ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		/*Fwzyffqqcsb.setFwtgzYyxtbh(shiroUser.getId());*/
		Fwzyffqqcsb.setFwtgzYyxtbh(serveManageService.selectByPrimaryKey(Fwzyffqqcsb.getFwbs()).getFwtgzYyxtbh());
			if(!StringUtil.isNotEmpty(flag)){
				try {
					this.saveFwFFQqcsHis(Fwzyffqqcsb, "U");
					i=FwzyffqqcsbService.updateByPrimaryKeySelective(Fwzyffqqcsb);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
			    j=FwzyffqqcsbService.selectByPrimaryKey(key);
				if(j==null){
					try {
						this.saveFwFFQqcsHis(Fwzyffqqcsb, "C");
						i=FwzyffqqcsbService.insertSelective(Fwzyffqqcsb);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			
				
			}
			
		
		try {
			if(i>0){
				result.put("success", "true");
			}else{
				if(j!=null){
					result.put("errorMsg", "保存失败!请求参数标识已经存在！");
				}else{
				result.put("errorMsg", "保存失败");
				}
			}
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void saveFwFFQqcsHis(Fwzyffqqcsb key,String optType) throws Exception{
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		Fwzyffqqcsbhis Fwzyffqqcsbhis=null;
		if(optType.equals("C")){
			 Fwzyffqqcsbhis=new Fwzyffqqcsbhis(key);
		}else{
		 Fwzyffqqcsbhis=new Fwzyffqqcsbhis(FwzyffqqcsbService.selectByPrimaryKey(key));
			
		}
	
	   Fwzyffqqcsbhis.setOptApp(shiroUser.getId());
	   Fwzyffqqcsbhis.setOptName(shiroUser.getName());
	   Fwzyffqqcsbhis.setOptTime(DateUtil.formatDateToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
	   Fwzyffqqcsbhis.setOptType(optType);
		FwzyffqqcsbhisService.insertSelective(Fwzyffqqcsbhis);
	}
	@RequestMapping(value="/delete",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void delete(HttpServletRequest request,
			HttpServletResponse response,String delIds) {
		JSONObject result=new JSONObject();
		try {
			String fwbs=request.getParameter("fwbs");
			String ffbs=request.getParameter("ffbs");
			String qqcsbs=request.getParameter("qqcsbs");
			FwzyffqqcsbKey FwzyffqqcsbKey=new FwzyffqqcsbKey();
			FwzyffqqcsbKey.setFwbs(fwbs);
			FwzyffqqcsbKey.setFfbs(ffbs);
			FwzyffqqcsbKey.setQqcsbs(qqcsbs);
			Fwzyffqqcsb Fwzyffqqcsb=new Fwzyffqqcsb();
			Fwzyffqqcsb.setFwbs(fwbs);
			Fwzyffqqcsb.setFfbs(ffbs);
			Fwzyffqqcsb.setQqcsbs(qqcsbs);
			this.saveFwFFQqcsHis(Fwzyffqqcsb, "D");
			int delNums=FwzyffqqcsbService.deleteByPrimaryKey(FwzyffqqcsbKey);
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
	
	 @RequestMapping(value = "exportExcel")
	 public Object exportExcel(HttpServletRequest request,HttpServletResponse response){
		 //文件字符输出流
			FileOutputStream toClient=null;
			try {
				Map<String, Object> condition=(Map<String, Object>) request.getSession().getAttribute("selectMap");
				 int rows=FwzyffqqcsbService.getFwzyffqqcsCountByPage(condition);
				 condition.put("start", (1*rows-rows));
				 condition.put("end", rows);
				 List<Fwzyffqqcsb> dataset=FwzyffqqcsbService.getFwzyffqqcsListByPage(condition);
				//得到项目路径
		        String rootpath = request.getSession().getServletContext().getRealPath("/");
		        //文件名
		        String fileName="exportdata.xls";
		        //创建文件字符输出流（项目路径+路径+文件名）
				toClient = new FileOutputStream(rootpath + File.separator + "static" + File.separator + fileName);
				//创建电子表格
				ExcelReaderDown<Fwzyffqqcsb> export=new ExcelReaderDown<Fwzyffqqcsb>();
				
				String[] headers =  
			        { "服务标识", "方法标识", "请求参数标识","服务名称", "方法名称","请求参数名","请求参数类型","参数长度","参数值域"};  
				//输出表格（电子表格标题，电子表格数据，文件输出流，日期类型转格式，表格存放规则）
				export.setNum(1);
				export.exportExcel("表格数据", headers,dataset, toClient,null);
				toClient.close();
				response.setContentType("text/html;setchar=utf-8");
				File file = new File(rootpath + File.separator + "static" + File.separator + fileName);
				HttpHeaders header = new HttpHeaders();
				header.setContentDispositionFormData("attachment", fileName);
				header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), header, HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}
}

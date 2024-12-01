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
import net.sinodata.business.entity.Fwzyfffhcsb;
import net.sinodata.business.entity.FwzyfffhcsbKey;
import net.sinodata.business.entity.Fwzyfffhcsbhis;
import net.sinodata.business.service.FwzyfffhcsbService;
import net.sinodata.business.service.FwzyfffhcsbhisService;
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
@RequestMapping(value = "/fwFFfhcsManage")
public class FwfffhcsManageController {
	//private static Logger logger = LoggerFactory.getLogger(FwfffhcsManageController.class);
	@Autowired
	FwzyfffhcsbService FwzyfffhcsbService;
	@Autowired
	FwzyfffhcsbhisService FwzyfffhcsbhisService;
	@Autowired
	ServeManageService serveManageService;
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(Model model) {

		return "business/fwFFfhcsManage";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String fwbs=request.getParameter("fwbs");
		String ffbs=request.getParameter("ffbs");
		String fhcsbs=request.getParameter("fhcsbs");
		String fwmc=request.getParameter("fwmc");
		String ffmc=request.getParameter("ffmc");
		String fhcsm=request.getParameter("fhcsm");
		
		
		if(StringUtil.isNotEmpty(ffbs)){
			condition.put("ffbs",ffbs);
		}
		if(StringUtil.isNotEmpty(fwbs)){
			condition.put("fwbs",fwbs);
		}
		if(StringUtil.isNotEmpty(fhcsbs)){
			condition.put("fhcsbs",fhcsbs);
		}
		if(StringUtil.isNotEmpty(fwmc)){
			condition.put("fwmc",fwmc);
		}
		if(StringUtil.isNotEmpty(ffmc)){
			condition.put("ffmc",ffmc);
		}
		if(StringUtil.isNotEmpty(fhcsm)){
			condition.put("fhcsm",fhcsm);
		}
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if(!shiroUser.getRoleid().equals("1")){
		condition.put("fwtgzYyxtbh",shiroUser.getId());
		}
	    SearchResult resoult = FwzyfffhcsbService.List(page, condition);
	    request.getSession().setAttribute("selectMap", condition);
		return resoult;
	}
	
	
	@RequestMapping(value = "/showHis", method = RequestMethod.GET)
	public String showHis(Model model,HttpServletRequest request) {
		request.setAttribute("fwbs", request.getParameter("fwbs"));
		return "business/fwFFfhcshisManage";
	}
	
	@RequestMapping(value = "/listHis", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listHis(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String fwbs=request.getParameter("fwbs");
		String ffbs=request.getParameter("ffbs");
		String fhcsbs=request.getParameter("fhcsbs");
		String fhcsm=request.getParameter("fhcsm");
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
		if(StringUtil.isNotEmpty(fhcsbs)){
			condition.put("fhcsbs",fhcsbs);
		}
		if(StringUtil.isNotEmpty(fhcsm)){
			condition.put("fhcsm",fhcsm);
		}
		
	    SearchResult resoult = FwzyfffhcsbhisService.List(page, condition);
	    
		return resoult;
	}
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public void addUpdate(Fwzyfffhcsb Fwzyfffhcsb,HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject result=new JSONObject();
		String flag=request.getParameter("flag");
		int i=0;
		Fwzyfffhcsb j=null;
		FwzyfffhcsbKey key=new FwzyfffhcsbKey();
		key.setFfbs(Fwzyfffhcsb.getFfbs());
		key.setFhcsbs(Fwzyfffhcsb.getFhcsbs());
		key.setFwbs(Fwzyfffhcsb.getFwbs());
		/*ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		Fwzyfffhcsb.setFwtgzYyxtbh(shiroUser.getId());*/
		Fwzyfffhcsb.setFwtgzYyxtbh(serveManageService.selectByPrimaryKey(Fwzyfffhcsb.getFwbs()).getFwtgzYyxtbh());
			if(!StringUtil.isNotEmpty(flag)){
				try {
					this.saveFwFFFhcsHis(Fwzyfffhcsb, "U");
					i=FwzyfffhcsbService.updateByPrimaryKeySelective(Fwzyfffhcsb);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				
				 j=FwzyfffhcsbService.selectByPrimaryKey(key);
				if(j==null){
					try {
						this.saveFwFFFhcsHis(Fwzyfffhcsb, "C");
						i=FwzyfffhcsbService.insertSelective(Fwzyfffhcsb);
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
					result.put("errorMsg", "保存失败!返回参数标识已经存在！");
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
	public void saveFwFFFhcsHis(Fwzyfffhcsb key,String optType) throws Exception{
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		Fwzyfffhcsbhis Fwzyfffhcsbhis=null;
		if(optType.equals("C")){
			Fwzyfffhcsbhis=new Fwzyfffhcsbhis(key);
		}else{
			Fwzyfffhcsbhis=new Fwzyfffhcsbhis(FwzyfffhcsbService.selectByPrimaryKey(key));
			
		}
		Fwzyfffhcsbhis.setOptApp(shiroUser.getId());
		Fwzyfffhcsbhis.setOptName(shiroUser.getName());
		Fwzyfffhcsbhis.setOptTime(DateUtil.formatDateToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Fwzyfffhcsbhis.setOptType(optType);
		FwzyfffhcsbhisService.insertSelective(Fwzyfffhcsbhis);
	}
	@RequestMapping(value="/delete",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void delete(HttpServletRequest request,
			HttpServletResponse response,String delIds) {
		JSONObject result=new JSONObject();
		try {
			String fwbs=request.getParameter("fwbs");
			String ffbs=request.getParameter("ffbs");
			String fhcsbs=request.getParameter("fhcsbs");
			FwzyfffhcsbKey FwzyfffhcsbKey=new FwzyfffhcsbKey();
			FwzyfffhcsbKey.setFwbs(fwbs);
			FwzyfffhcsbKey.setFfbs(ffbs);
			FwzyfffhcsbKey.setFhcsbs(fhcsbs);
			Fwzyfffhcsb Fwzyfffhcsb=new Fwzyfffhcsb();
			Fwzyfffhcsb.setFwbs(fwbs);
			Fwzyfffhcsb.setFfbs(ffbs);
			Fwzyfffhcsb.setFhcsbs(fhcsbs);
			this.saveFwFFFhcsHis(Fwzyfffhcsb, "D");
			int delNums=FwzyfffhcsbService.deleteByPrimaryKey(FwzyfffhcsbKey);
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
				 int rows=FwzyfffhcsbService.getFwzyfffhcsCountByPage(condition);
				 condition.put("start", (1*rows-rows));
				 condition.put("end", rows);
				 List<Fwzyfffhcsb> dataset=FwzyfffhcsbService.getFwzyfffhcsListByPage(condition);
				//得到项目路径
		        String rootpath = request.getSession().getServletContext().getRealPath("/");
		        //文件名
		        String fileName="exportdata.xls";
		        //创建文件字符输出流（项目路径+路径+文件名）
				toClient = new FileOutputStream(rootpath + File.separator +  "static" + File.separator + fileName);
				//创建电子表格
				ExcelReaderDown<Fwzyfffhcsb> export=new ExcelReaderDown<Fwzyfffhcsb>();
				
				String[] headers =  
			        { "服务标识", "方法标识", "返回参数标识","服务名称","方法名称","返回参数名","返回参数类型","返回参数长度","返回参数值域"};  
				//输出表格（电子表格标题，电子表格数据，文件输出流，日期类型转格式，表格存放规则）
				export.setNum(1);
				export.exportExcel("表格数据", headers,dataset, toClient,null);
				toClient.close();
				response.setContentType("text/html;setchar=utf-8");
				File file = new File(rootpath + File.separator+"static" +File.separator+ fileName);
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

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
import net.sinodata.business.entity.Fwzyffqqcsb;
import net.sinodata.business.entity.FwzyffqqcsbKey;
import net.sinodata.business.entity.Fwzyffqqcsbhis;
import net.sinodata.business.entity.Fwzyffzcb;
import net.sinodata.business.entity.FwzyffzcbKey;
import net.sinodata.business.entity.Fwzyffzcbhis;
import net.sinodata.business.entity.YyFwFfInfo;
import net.sinodata.business.service.FwzyfffhcsbService;
import net.sinodata.business.service.FwzyfffhcsbhisService;
import net.sinodata.business.service.FwzyffqqcsbService;
import net.sinodata.business.service.FwzyffqqcsbhisService;
import net.sinodata.business.service.FwzyffzcbService;
import net.sinodata.business.service.FwzyffzcbhisService;
import net.sinodata.business.service.FwzyqqbwcjbService;
import net.sinodata.business.service.JzlbdmbService;
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
@RequestMapping(value = "/fwFFManage")
public class FwffManageController {
	//private static Logger logger = LoggerFactory.getLogger(FwffManageController.class);
	@Autowired
	FwzyffzcbService FwzyffzcbService;
	@Autowired
	FwzyffqqcsbService FwzyffqqcsbService;
	@Autowired
	FwzyfffhcsbService FwzyfffhcsbService;
	@Autowired
	JzlbdmbService JzlbdmbService;
	@Autowired
	FwzyffzcbhisService FwzyffzcbhisService;
	@Autowired
	FwzyffqqcsbhisService FwzyffqqcsbhisService;
	@Autowired
	FwzyfffhcsbhisService FwzyfffhcsbhisService;
	@Autowired
	ServeManageService serveManageService;
	@Autowired
	private FwzyqqbwcjbService fwzyqqbwcjbService;
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(Model model, HttpServletRequest request) {
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if(!shiroUser.getRoleid().equals("1")){
			request.setAttribute("users", "1");
		}else {
			request.setAttribute("users", "2");
		}
		request.setAttribute("fwcyf", fwzyqqbwcjbService.selectAllFwcyfzcb());
		return "business/fwFFManage";
	}
	@RequestMapping(value = "/jzfl", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object jzfl(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		/*List<Jzlbdmb> l =JzlbdmbService.queryJzlbdmb();
		Jzlbdmb jzlbdmb=new Jzlbdmb();
		jzlbdmb.setJzlbdm("");
		jzlbdmb.setJzlbmc("请选择");
		l.add(jzlbdmb);*/
		 
		return JzlbdmbService.queryJzlbdmb();
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String fwbs=request.getParameter("fwbs");
		String fwmc=request.getParameter("fwmc");
		String ffbs=request.getParameter("ffbs");
		String ffmc=request.getParameter("ffmc");
		String jzfl=request.getParameter("jzfl");
		String sfhcsj=request.getParameter("sfhcsj");
		String fwtgzYyxtbh = request.getParameter("fwtgzYyxtbh");
		if(StringUtil.isNotEmpty(ffbs)){
			condition.put("ffbs",ffbs);
		}
		if(StringUtil.isNotEmpty(fwmc)){
			condition.put("fwmc",fwmc);
		}
		if(StringUtil.isNotEmpty(fwbs)){
			condition.put("fwbs",fwbs);
		}
		if(StringUtil.isNotEmpty(ffmc)){
			condition.put("ffmc",ffmc);
		}
		if(StringUtil.isNotEmpty(jzfl)){
			condition.put("jzfl",jzfl);
		}
		if(StringUtil.isNotEmpty(sfhcsj)){
			condition.put("sfhcsj",sfhcsj);
		}
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if(!shiroUser.getRoleid().equals("1")){
		condition.put("fwtgzYyxtbh",shiroUser.getId());
		}else {
			condition.put("fwtgzYyxtbh",fwtgzYyxtbh);
		}
	    SearchResult resoult = FwzyffzcbService.List(page, condition);
	    request.getSession().setAttribute("selectMap", condition);
		return resoult;
	}
	
	
	
	
	@RequestMapping(value = "/showHis", method = RequestMethod.GET)
	public String showHis(Model model,HttpServletRequest request) {
		request.setAttribute("fwbs", request.getParameter("fwbs"));
		return "business/fwFFhisManage";
	}
	@RequestMapping(value = "/listHis", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listHis(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String fwbs=request.getParameter("fwbs");
		String ffbs=request.getParameter("ffbs");
		String ffmc=request.getParameter("ffmc");
		String jzfl=request.getParameter("jzfl");
		String sfhcsj=request.getParameter("sfhcsj");
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
		if(StringUtil.isNotEmpty(ffmc)){
			condition.put("ffmc",ffmc);
		}
		if(StringUtil.isNotEmpty(jzfl)){
			condition.put("jzfl",jzfl);
		}if(StringUtil.isNotEmpty(sfhcsj)){
			condition.put("sfhcsj",sfhcsj);
		}
		/*ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if(!shiroUser.getRoleid().equals("1")){
		condition.put("fwtgzYyxtbh",shiroUser.getId());
		}*/
	    SearchResult resoult = FwzyffzcbhisService.List(page, condition);
	   
		return resoult;
	}
	
	
	
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public void addUpdate(Fwzyffzcb Fwzyffzcb,HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject result=new JSONObject();
		String flag=request.getParameter("flag");
		
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		Fwzyffzcb.setFwtgzYyxtbh(serveManageService.selectByPrimaryKey(Fwzyffzcb.getFwbs()).getFwtgzYyxtbh());
		int i=0;
		if(!StringUtil.isNotEmpty(flag)){
				try {
					this.saveFFHis(Fwzyffzcb, "U");
					i=FwzyffzcbService.updateByPrimaryKeySelective(Fwzyffzcb);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					this.saveFFHis(Fwzyffzcb, "C");
					i=FwzyffzcbService.insertSelective(Fwzyffzcb);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
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
	public void saveFFHis(Fwzyffzcb Fwzyffzcb,String optType) throws Exception{
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		Fwzyffzcbhis Fwzyffzcbhis=null;
		FwzyffzcbKey key=new FwzyffzcbKey();
		key.setFfbs(Fwzyffzcb.getFfbs());
		key.setFwbs(Fwzyffzcb.getFwbs());
		if(optType.equals("C")){
			 Fwzyffzcbhis=new Fwzyffzcbhis(Fwzyffzcb);
			 Fwzyffzcbhis.setFfbs(FwzyffzcbService.queryFFBSbyFwbs(Fwzyffzcb.getFwbs()));
		}else{
			
			 Fwzyffzcbhis=new Fwzyffzcbhis(FwzyffzcbService.selectByPrimaryKey(key));
		}
		//Fwzyffzcbhis.setOptId(optId);
		Fwzyffzcbhis.setOptApp(shiroUser.getId());
		Fwzyffzcbhis.setOptName(shiroUser.getName());
		Fwzyffzcbhis.setOptTime(DateUtil.formatDateToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		Fwzyffzcbhis.setOptType(optType);
		if(optType.equals("D")){
			FwzyffqqcsbKey FwzyffqqcsbKey=new FwzyffqqcsbKey();
			FwzyffqqcsbKey.setFfbs(Fwzyffzcb.getFfbs());
			FwzyffqqcsbKey.setFwbs(Fwzyffzcb.getFwbs());
		     List<Fwzyffqqcsb> FwzyffqqcsbList=	FwzyffqqcsbService.queryListByFwbsAndFfbs(FwzyffqqcsbKey);
		for(Fwzyffqqcsb f:FwzyffqqcsbList){
			Fwzyffqqcsbhis Fwzyffqqcsbhis=new Fwzyffqqcsbhis(f);
			Fwzyffqqcsbhis.setOptApp(shiroUser.getId());
			Fwzyffqqcsbhis.setOptName(shiroUser.getName());
			Fwzyffqqcsbhis.setOptTime(DateUtil.formatDateToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			Fwzyffqqcsbhis.setOptType(optType);
			FwzyffqqcsbhisService.insertSelective(Fwzyffqqcsbhis);
		}
		    FwzyfffhcsbKey FwzyfffhcsbKey=new FwzyfffhcsbKey();
			FwzyfffhcsbKey.setFfbs(Fwzyffzcb.getFfbs());
			FwzyfffhcsbKey.setFwbs(Fwzyffzcb.getFwbs());
			List<Fwzyfffhcsb> FwzyfffhcsbList=FwzyfffhcsbService.queryListByFfbsAndFwbs(FwzyfffhcsbKey);
			for(Fwzyfffhcsb f:FwzyfffhcsbList){
				Fwzyfffhcsbhis Fwzyfffhcsbhis=new Fwzyfffhcsbhis(f);
				Fwzyfffhcsbhis.setOptApp(shiroUser.getId());
				Fwzyfffhcsbhis.setOptName(shiroUser.getName());
				Fwzyfffhcsbhis.setOptTime(DateUtil.formatDateToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				Fwzyfffhcsbhis.setOptType(optType);
				FwzyfffhcsbhisService.insertSelective(Fwzyfffhcsbhis);
			}
		}
		FwzyffzcbhisService.insertSelective(Fwzyffzcbhis);
	}
	@RequestMapping(value="/delete",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void delete(HttpServletRequest request,
			HttpServletResponse response,String delIds) {
		JSONObject result=new JSONObject();
		int delNums=0;
		try {
			String fwbs=request.getParameter("fwbs");
			String ffbs=request.getParameter("ffbs");
			FwzyffqqcsbKey FwzyffqqcsbKey=new FwzyffqqcsbKey();
			FwzyffqqcsbKey.setFwbs(fwbs);
			FwzyffqqcsbKey.setFfbs(ffbs);
			FwzyffqqcsbKey.setQqcsbs(null);
			FwzyfffhcsbKey FwzyfffhcsbKey=new FwzyfffhcsbKey();
			FwzyfffhcsbKey.setFwbs(fwbs);
			FwzyfffhcsbKey.setFfbs(ffbs);
			FwzyfffhcsbKey.setFhcsbs(null);
			FwzyffzcbKey FwzyffzcbKey=new FwzyffzcbKey();
			FwzyffzcbKey.setFwbs(fwbs);
			FwzyffzcbKey.setFfbs(ffbs);
			Fwzyffzcb Fwzyffzcb=new Fwzyffzcb();
			Fwzyffzcb.setFfbs(ffbs);
			Fwzyffzcb.setFwbs(fwbs);
			this.saveFFHis(Fwzyffzcb, "D");
			delNums+=FwzyffqqcsbService.deleteByPrimaryKey(FwzyffqqcsbKey);
			delNums+=FwzyfffhcsbService.deleteByPrimaryKey(FwzyfffhcsbKey);
			delNums+=FwzyffzcbService.deleteByPrimaryKey(FwzyffzcbKey);
			
			
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
				 int rows=FwzyffzcbService.getFwzyffCountByPage(condition);
				 condition.put("start", (1*rows-rows));
				 condition.put("end", rows);
				 List<Fwzyffzcb> dataset=FwzyffzcbService.getFwzyffListByPage(condition);
				//得到项目路径
		        String rootpath = request.getSession().getServletContext().getRealPath("/");
		        //文件名
		        String fileName="exportdata.xls";
		        //创建文件字符输出流（项目路径+路径+文件名）
				toClient = new FileOutputStream(rootpath + File.separator + "static" + File.separator + fileName);
				//创建电子表格
				ExcelReaderDown<Fwzyffzcb> export=new ExcelReaderDown<Fwzyffzcb>();
				
				String[] headers =  
			        { "服务标识", "方法标识", "服务名称","方法名","方法类","方法描述","警种分类","操作分类","是否缓存数据","缓存数据有效时间（秒）","网络分类","方法类别"};  
				//输出表格（电子表格标题，电子表格数据，文件输出流，日期类型转格式，表格存放规则）
				export.setNum(6);
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
	 
	@RequestMapping(value = "yyFwFfExport")
	public Object yyFwFfExport(HttpServletRequest request, HttpServletResponse response) {
		// 文件字符输出流
		FileOutputStream toClient = null;
		try {
			Map<String, Object> condition = (Map<String, Object>) request.getSession().getAttribute("selectMap");
			int rows = FwzyffzcbService.getYyFwFfInfoCountByPage(condition);
			condition.put("start", (1 * rows - rows));
			condition.put("end", rows);
			List<YyFwFfInfo> dataset = FwzyffzcbService.getYyFwFfListByPage(condition);
			// 得到项目路径
			String rootpath = request.getSession().getServletContext().getRealPath("/");
			// 文件名
			String fileName = "exportdata.xls";
			// 创建文件字符输出流（项目路径+路径+文件名）
			toClient = new FileOutputStream(rootpath + File.separator + "static" + File.separator + fileName);
			// 创建电子表格
			ExcelReaderDown<YyFwFfInfo> export = new ExcelReaderDown<YyFwFfInfo>();

			String[] headers = { "应用标识", "应用名称", "服务标识", "服务名称", "方法标识", "方法名称",
					"方法描述", "URL" , "接口方法类型"};
			export.setNum(0);
			export.exportExcel("表格数据", headers, dataset, toClient, null);
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
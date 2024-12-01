package net.sinodata.business.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sinodata.business.entity.FwzyfffhcsbKey;
import net.sinodata.business.entity.FwzyffqqcsbKey;
import net.sinodata.business.entity.FwzyffzcbKey;
import net.sinodata.business.entity.Fwzytcb;
import net.sinodata.business.entity.Fwzyzcb;
import net.sinodata.business.service.FwzyfffhcsbService;
import net.sinodata.business.service.FwzyffqqcsbService;
import net.sinodata.business.service.FwzyffzcbService;
import net.sinodata.business.service.FwzyqqbwcjbService;
import net.sinodata.business.service.FwzytcbService;
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
@RequestMapping(value = "/serveManage")
public class ServeManageController {
	//private static Logger logger = LoggerFactory.getLogger(ServeManageController.class);
	@Autowired
	ServeManageService serveManageService;
	@Autowired
	FwzyffzcbService FwzyffzcbService;
	@Autowired
	FwzyffqqcsbService FwzyffqqcsbService;
	@Autowired
	FwzyfffhcsbService FwzyfffhcsbService;
	@Autowired
	FwzytcbService fwzytcbService;
	
	@Autowired
	private FwzyqqbwcjbService fwzyqqbwcjbService;
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(Model model) {
		return "business/serveManage";
	}
	@RequestMapping(value = "/fwZcshow", method = RequestMethod.GET)
	public String fwZcshow(Model model, HttpServletRequest request) {
		request.setAttribute("fwcyf", fwzyqqbwcjbService.selectAllFwcyfzcb());
		return "business/fwZcManage";
	}
	@RequestMapping(value = "/fwApZcshow", method = RequestMethod.GET)
	public String fwApZcshow(Model model) {

		return "business/fwApZcManage";
	}
	@RequestMapping(value = "/zxdz", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object zxdz(HttpServletRequest request,
			HttpServletResponse response) {
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		String jgbs=null;
	    if(!shiroUser.getRoleid().equals("1")){
	    	 jgbs=shiroUser.getFwcyfSsfj();
	    	
	    }
		return serveManageService.queryfwzyzxdz(jgbs);
	}
	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		String flag=request.getParameter("flag");
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String fwmc=request.getParameter("fwmc");
		String fwbs=request.getParameter("fwbs");
		String fwtgz=request.getParameter("fwtgz");
		String fwztdm=request.getParameter("fwztdm");
		String fwtgzYyxtbh=request.getParameter("fwtgzYyxtbh");
		String sfhcsj=request.getParameter("sfhcsj");
		
		if(StringUtil.isNotEmpty(fwmc)){
			
			condition.put("fwmc",fwmc);
		}
		if(StringUtil.isNotEmpty(fwbs)){
			condition.put("fwbs",fwbs);
		}
		if(StringUtil.isNotEmpty(fwztdm)){
			condition.put("fwztdm",fwztdm);
		}
		if(StringUtil.isNotEmpty(fwtgzYyxtbh)){
			condition.put("fwtgzYyxtbh",fwtgzYyxtbh);
		}
		if(StringUtil.isNotEmpty(sfhcsj)){
			condition.put("sfhcsj",sfhcsj);
		}
		if(StringUtil.isNotEmpty(fwtgz)){
			condition.put("fwtgz",fwtgz);
		}
		
		if(StringUtil.isNotEmpty(fwtgzYyxtbh)){
			condition.put("fwtgzYyxtbh",fwtgzYyxtbh);
		}
		
      if(StringUtil.isNotEmpty(flag)){
    	  ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
    	  condition.put("fwtgzYyxtbh",shiroUser.getId().toString());
		}
	    SearchResult resoult = serveManageService.fwzyzcbList(page, condition);
	    request.getSession().setAttribute("selectMap", condition);
		return resoult;
	}
	
	
	
	@RequestMapping(value = "/queryTcList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object queryTcList(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String fwmc=request.getParameter("fwmc");
		String fwbs=request.getParameter("fwbs");
		String fwtgz=request.getParameter("fwtgz");
		
		if(StringUtil.isNotEmpty(fwmc)){
			
			condition.put("fwmc",fwmc);
		}
		if(StringUtil.isNotEmpty(fwbs)){
			condition.put("fwbs",fwbs);
		}
		if(StringUtil.isNotEmpty(fwtgz)){
			condition.put("fwtgz",fwtgz);
		}
		
	    SearchResult resoult = fwzytcbService.fwzytcbList(page, condition);
	    request.getSession().setAttribute("selectMap", condition);
		return resoult;
	}
	
	
	@RequestMapping(value = "/tree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void tree( HttpServletRequest request,
			HttpServletResponse response) {
		String flag=request.getParameter("flag");
		JSONArray jsonArray=serveManageService.queryTreeList(flag);
		try {
			ResponseUtil.write(response, jsonArray);
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
		String fwztdm=request.getParameter("fwztdm");
		Fwzyzcb record=new Fwzyzcb();
		record.setFwbs(fwbs);
		record.setFwztdm(fwztdm);
		int i=serveManageService.updateByPrimaryKeySelective(record);
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
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public void addUpdate(Fwzyzcb fwzyzcb,HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject result=new JSONObject();
		String flag=request.getParameter("flag");
		int i=0;
			if(!StringUtil.isNotEmpty(flag)){
				try {
					
					i=serveManageService.updateByPrimaryKeySelective(fwzyzcb);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				try {
					fwzyzcb.setFwzcRqsj(DateUtil.formatDateToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i=serveManageService.insertSelective(fwzyzcb);
			
				
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
	
	@RequestMapping(value = "/fwzctree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void fwzctree( HttpServletRequest request,
			HttpServletResponse response) {
	
		JSONArray jsonArray=serveManageService.queryTreeList();
		try {
			ResponseUtil.write(response, jsonArray);
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
		int delNums=0;
		try {
			String fwbs=request.getParameter("fwbs");
			FwzyffqqcsbKey FwzyffqqcsbKey=new FwzyffqqcsbKey();
			FwzyffqqcsbKey.setFwbs(fwbs);
			FwzyffqqcsbKey.setFfbs(null);
			FwzyffqqcsbKey.setQqcsbs(null);
			delNums+=FwzyffqqcsbService.deleteByPrimaryKey(FwzyffqqcsbKey);
			FwzyfffhcsbKey FwzyfffhcsbKey=new FwzyfffhcsbKey();
			FwzyfffhcsbKey.setFwbs(fwbs);
			FwzyfffhcsbKey.setFfbs(null);
			FwzyfffhcsbKey.setFhcsbs(null);
			delNums+=FwzyfffhcsbService.deleteByPrimaryKey(FwzyfffhcsbKey);
			FwzyffzcbKey FwzyffzcbKey=new FwzyffzcbKey();
			FwzyffzcbKey.setFwbs(fwbs);
			FwzyffzcbKey.setFfbs(null);
			delNums+=FwzyffzcbService.deleteByPrimaryKey(FwzyffzcbKey);
			delNums+=serveManageService.deleteByPrimaryKey(fwbs);
			
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
				 int rows=serveManageService.getFwzyzcbCountByPage(condition);
				 condition.put("start", (1*rows-rows));
				 condition.put("end", rows);
				 List<Fwzyzcb> dataset=serveManageService.getFwzyzcbListByPage(condition);
				//得到项目路径
		        String rootpath = request.getSession().getServletContext().getRealPath("/");
		        //文件名
		        String fileName="exportdata.xls";
		        //创建文件字符输出流（项目路径+路径+文件名）
				toClient = new FileOutputStream(rootpath + File.separator+"static" +File.separator+ fileName);
				//创建电子表格
				ExcelReaderDown<Fwzyzcb> export=new ExcelReaderDown<Fwzyzcb>();
				
				String[] headers =  
			        { "服务提供者名称", "服务标识","服务名称", "服务_入口地址列表", "服务_描述", "服务注册_日期时间", "服务停止_日期时间", "服务提供者_标识","服务状态","服务内容","服务总线地址","服务版本号" ,"开发语言类型代码"};  
				//输出表格（电子表格标题，电子表格数据，文件输出流，日期类型转格式，表格存放规则）
				export.setNum(5);
				export.exportExcel("表格数据", headers,dataset, toClient,"yyyy-MM-dd");
				toClient.close();
				response.setContentType("text/html;setchar=utf-8");
				File file = new File(rootpath + File.separator+"static" + File.separator+fileName);
				HttpHeaders header = new HttpHeaders();
				header.setContentDispositionFormData("attachment", fileName);
				header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), header, HttpStatus.CREATED);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}
	 
	 
	 
	 @RequestMapping(value = "/addUpdateTc", method = RequestMethod.POST)
		@ResponseBody
		public void addUpdateTc(Fwzytcb fwzytcb,HttpServletRequest request,
				HttpServletResponse response) {
			JSONObject result=new JSONObject();
			String flag=request.getParameter("flag");
			int i=0;
				if(!StringUtil.isNotEmpty(flag)){
					try {
						i=fwzytcbService.updateByPrimaryKeySelective(fwzytcb);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else{
					i=fwzytcbService.insertSelective(fwzytcb);
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
}

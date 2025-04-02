package net.sinodata.business.rest;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sinodata.business.dao.FwzyfffhcsbDao;
import net.sinodata.business.dao.FwzyffqqcsbDao;
import net.sinodata.business.dao.FwzyffzcbDao;
import net.sinodata.business.entity.Fwzyfffhcsb;
import net.sinodata.business.entity.FwzyfffhcsbKey;
import net.sinodata.business.entity.Fwzyffqqcsb;
import net.sinodata.business.entity.FwzyffqqcsbKey;
import net.sinodata.business.entity.Fwzyffzcb;
import net.sinodata.business.entity.FwzyffzcbKey;
import net.sinodata.business.entity.Fwzyzcb;
import net.sinodata.business.service.JgdmbService;
import net.sinodata.business.service.ServeManageService;
import net.sinodata.business.service.UserTService;
import net.sinodata.business.util.DateUtil;
import net.sinodata.business.util.ExcelReaderDown;
import net.sinodata.business.util.StringUtil;
import net.sinodata.security.vo.ShiroUser;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/uploadServe")
public class UploadServeController {
	//private static Logger logger = LoggerFactory.getLogger(UploadServeController.class);
	@Autowired
	ServeManageService serveManageService;
	@Autowired
	FwzyffzcbDao FwzyffzcbDao;
	@Autowired
	FwzyffqqcsbDao FwzyffqqcsbDao;
	@Autowired
	FwzyfffhcsbDao FwzyfffhcsbDao;
	@Autowired
	UserTService UserTService;
	@Autowired
	JgdmbService JgdmbService;
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String show(Model model) {

		return "account/uploadServe";
	}
	@RequestMapping(value = "impExcelFwZcSJ",method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object impExcelFwZcSJ(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value="fileFwzc") MultipartFile fileFwzc,
			@RequestParam(value="fileFwff") MultipartFile fileFwff,
			@RequestParam(value="fileFwqqcs") MultipartFile fileFwqqcs,
			@RequestParam(value="fileFwfhcs") MultipartFile fileFwfhcs)  {
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		int num=0;
		int fwfsnum=0;
		int fwqqcsnum=0;
		int fwfhcsnum=0;
		JSONObject resultj=new JSONObject();
		try {
			if(fileFwzc.getOriginalFilename()!=null&& fileFwzc.getOriginalFilename() != ""){
				InputStream is=fileFwzc.getInputStream();
				
			  List<List<String>> list=ExcelReaderDown.readExcel(is); 
			    //-----------------------遍历数据到实体集合开始----服务-------------------------------
			    List<Fwzyzcb> listBean = new ArrayList<Fwzyzcb>();
			    for (int i = 1; i < list.size(); i++) {// i=1是因为第一行不要
			        Fwzyzcb uBean = new Fwzyzcb();
			        List<String> listStr = list.get(i);
			        for (int j = 0; j < listStr.size(); j++) {
			            switch(j){
			                //case 1:uBean.setFwbs(listStr.get(j)); break;// 第一列
			                case 0:uBean.setFwmc(listStr.get(j));break;// 第二列
			                case 1:uBean.setFwRkdzlb(listStr.get(j));break;
			                /*case 2:uBean.setFwIpdz(listStr.get(j));break;
			                case 3:uBean.setFwZxdkhm(listStr.get(j));break;
			                case 4:uBean.setFwLj(listStr.get(j));break;*/
			                case 2:uBean.setFwMs(listStr.get(j));break;
			                case 3:uBean.setFwzcRqsj(DateUtil.formatString(listStr.get(j),"yyyyMMdd"));break;
			                case 4:uBean.setFwtzRqsj(DateUtil.formatString(listStr.get(j),"yyyyMMdd"));break;
			                case 5:uBean.setFwtgzYyxtbh(listStr.get(j));break;
			                case 6:uBean.setFwnrBz(listStr.get(j));break;
			                case 7:uBean.setFwbbh(listStr.get(j));break;
			                case 8:uBean.setFwzykfYylxdm(listStr.get(j));break;
			            }
			            
			            
			        }
			        uBean.setFwztdm("1");
			        /*if( serveManageService.selectByPrimaryKey(uBean.getFwbs())!=null){
			     	   resultj.put("errorMsg", "服务已经存在,请勿重复上传！");
			     	  return resultj;
			        };*/
			        if( UserTService.selectByPrimaryKey(uBean.getFwtgzYyxtbh())==null){
			      	   resultj.put("errorMsg", "服务提供者不存在,请勿上传！");
			      	  return resultj;
			         }else{
			        	 uBean.setJgbs(JgdmbService.selectByPrimaryKey(UserTService.selectByPrimaryKey(uBean.getFwtgzYyxtbh()).getFwcyfSsfj()).getJgbs());
			         };
			        listBean.add(uBean);
			    }
			    num=  serveManageService.insertByBatch(listBean);
			}
			
				
			
			    //服务方法
			if(fileFwff.getOriginalFilename()!=null&& fileFwff.getOriginalFilename() != ""){
				
			    InputStream isFwff=fileFwff.getInputStream();
				List<List<String>> listFwff=ExcelReaderDown.readExcel(isFwff);
			    List<Fwzyffzcb> listBeanFwzyffzcb = new ArrayList<Fwzyffzcb>();
			    for (int i = 1; i < listFwff.size(); i++) {// i=1是因为第一行不要
			    	Fwzyffzcb uBean = new Fwzyffzcb();
			    	
			        List<String> listStr = listFwff.get(i);
			        for (int j = 0; j < listStr.size(); j++) {
			            switch(j){
			                   
			                case 0:uBean.setFwbs(listStr.get(j));break;// 第一列
			                /*case 2:uBean.setFfbs(listStr.get(j));break;*/
			                case 1:uBean.setFfmc(listStr.get(j));break;
			                case 2:uBean.setFfl(listStr.get(j));break;
			                case 3:uBean.setFfms(listStr.get(j));break;
			                case 4:uBean.setJzfl(listStr.get(j));break;
			                case 5:uBean.setCzfl(listStr.get(j));break;
			                case 6:uBean.setSfhcsj(listStr.get(j));break;
			                case 7:uBean.setSjyxsj(new BigDecimal(listStr.get(j)));break;
			                case 8:uBean.setWlfl(listStr.get(j));break;
			                case 9:uBean.setFflb(listStr.get(j));break;
			                case 10:uBean.setUrl(listStr.get(j));break;
			                case 11:uBean.setFflx(listStr.get(j));break;
			                case 12:uBean.setResponseLimit(listStr.get(j));break;
			                case 13:uBean.setResponsePackage(StringUtil.isEmpty(listStr.get(j))?"5":listStr.get(j));break;
			             }
			         }
			        uBean.setFwtgzYyxtbh(serveManageService.selectByPrimaryKey(uBean.getFwbs()).getFwtgzYyxtbh());
			        //uBean.setFfbs(FwzyffzcbDao.queryFFBSbyFwbs(uBean.getFwbs()));
			        /*FwzyffzcbKey FwzyffzcbKey=new FwzyffzcbKey();
			        FwzyffzcbKey.setFfbs(uBean.getFfbs());
			        FwzyffzcbKey.setFwbs(uBean.getFwbs());
			        if( FwzyffzcbDao.selectByPrimaryKey(FwzyffzcbKey)!=null){
			         	   resultj.put("errorMsg", "服务方法已经存在,请勿重复上传！");
			         	  return resultj;
			            };*/
			            if( serveManageService.selectByPrimaryKey(uBean.getFwbs())==null){
			          	   resultj.put("errorMsg", "服务不存在,请勿重复上传！");
			          	  return resultj;
			             };
			             fwfsnum+=  FwzyffzcbDao.insertSelective(uBean);
			        listBeanFwzyffzcb.add(uBean);
			    } 
			    //fwfsnum= FwzyffzcbDao.insertByBatch(listBeanFwzyffzcb);
			}
			    //服务请求参数
			
				if(fileFwqqcs.getOriginalFilename()!=null&& fileFwqqcs.getOriginalFilename() != ""){
			    InputStream isFwqqcs=fileFwqqcs.getInputStream();
				  List<List<String>> listFwqqcs=ExcelReaderDown.readExcel(isFwqqcs); 
			    List<Fwzyffqqcsb> listBeanFwzyffqqcsb = new ArrayList<Fwzyffqqcsb>();
			    for (int i = 1; i < listFwqqcs.size(); i++) {// i=1是因为第一行不要
			    	Fwzyffqqcsb uBean = new Fwzyffqqcsb();
			    	//uBean.setFwtgzYyxtbh(shiroUser.getId());
			        List<String> listStr = listFwqqcs.get(i);
			        for (int j = 0; j < listStr.size(); j++) {
			            switch(j){
			                case 0:uBean.setFwbs(listStr.get(j));break;
			                case 1:uBean.setFfbs(listStr.get(j));break;
			                case 2:uBean.setQqcsbs(listStr.get(j));break;
			                case 3:uBean.setQqcsm(listStr.get(j));break;
			                case 4:uBean.setQqcslx(listStr.get(j));break;
			                case 5:uBean.setQqcscd(new BigDecimal(listStr.get(j)));break;
			                case 6:uBean.setQqcszy(listStr.get(j));break;
			            }
			            
			            
			        }
			        uBean.setFwtgzYyxtbh(serveManageService.selectByPrimaryKey(uBean.getFwbs()).getFwtgzYyxtbh());
			        FwzyffqqcsbKey FwzyffqqcsbKey=new FwzyffqqcsbKey();
			        FwzyffqqcsbKey.setFfbs(uBean.getFfbs());
			        FwzyffqqcsbKey.setQqcsbs(uBean.getQqcsbs());
			        FwzyffqqcsbKey.setFwbs(uBean.getFwbs());
			        if(FwzyffqqcsbDao.selectByPrimaryKey(FwzyffqqcsbKey)!=null){
			        	 resultj.put("errorMsg", "请求参数已经存在,请勿重复上传！");
			          	 return resultj;
			        }
			        FwzyffzcbKey FwzyffzcbKey=new FwzyffzcbKey();
			        FwzyffzcbKey.setFfbs(uBean.getFfbs());
			        FwzyffzcbKey.setFwbs(uBean.getFwbs());
			        if( FwzyffzcbDao.selectByPrimaryKey(FwzyffzcbKey)==null){
			         	   resultj.put("errorMsg", "服务方法不存在,请勿上传！");
			         	  return resultj;
			            };
			            if( serveManageService.selectByPrimaryKey(uBean.getFwbs())==null){
			          	   resultj.put("errorMsg", "服务不存在,请勿上传！");
			          	  return resultj;
			             };
			        listBeanFwzyffqqcsb.add(uBean);
			    } 
			    
			    fwqqcsnum= FwzyffqqcsbDao.insertByBatch(listBeanFwzyffqqcsb);
			}
			  //服务返回参数
			
				
				if(fileFwfhcs.getOriginalFilename()!=null&& fileFwfhcs.getOriginalFilename() != ""){
			    InputStream isFwfhcs=fileFwfhcs.getInputStream();
				  List<List<String>> lisFwfhcs=ExcelReaderDown.readExcel(isFwfhcs); 
			    List<Fwzyfffhcsb> listBeanFwzyfffhcsb = new ArrayList<Fwzyfffhcsb>();
			    for (int i = 1; i < lisFwfhcs.size(); i++) {// i=1是因为第一行不要
			    	Fwzyfffhcsb uBean = new Fwzyfffhcsb();
			    	//uBean.setFwtgzYyxtbh(shiroUser.getId());

			        List<String> listStr = lisFwfhcs.get(i);
			        for (int j = 0; j < listStr.size(); j++) {
			            switch(j){
			                case 0:uBean.setFwbs(listStr.get(j));break;
			                case 1:uBean.setFfbs(listStr.get(j));break;
			                case 2:uBean.setFhcsbs(listStr.get(j));break;
			                case 3:uBean.setFhcsm(listStr.get(j));break;
			                case 4:uBean.setFhcslx(listStr.get(j));break;
			                case 5:uBean.setFhcscd(new BigDecimal(listStr.get(j)));break;
			                case 6:uBean.setFhcszy(listStr.get(j));break;
			               
			            }
			            
			            
			        }
			        uBean.setFwtgzYyxtbh(serveManageService.selectByPrimaryKey(uBean.getFwbs()).getFwtgzYyxtbh());
			        FwzyfffhcsbKey FwzyfffhcsbKey=new FwzyfffhcsbKey();
			        FwzyfffhcsbKey.setFfbs(uBean.getFfbs());
			        FwzyfffhcsbKey.setFhcsbs(uBean.getFhcsbs());
			        FwzyfffhcsbKey.setFwbs(uBean.getFwbs());
			        if(FwzyfffhcsbDao.selectByPrimaryKey(FwzyfffhcsbKey)!=null){
			        	 resultj.put("errorMsg", "返回参数已经存在,请勿重复上传！");
			          	 return resultj;
			        }
			        FwzyffzcbKey FwzyffzcbKey=new FwzyffzcbKey();
			        FwzyffzcbKey.setFfbs(uBean.getFfbs());
			        FwzyffzcbKey.setFwbs(uBean.getFwbs());
			        if( FwzyffzcbDao.selectByPrimaryKey(FwzyffzcbKey)==null){
			         	   resultj.put("errorMsg", "服务方法不存在,请勿上传！");
			         	  return resultj;
			            };
			            if( serveManageService.selectByPrimaryKey(uBean.getFwbs())==null){
			          	   resultj.put("errorMsg", "服务不存在,请勿上传！");
			          	  return resultj;
			             };
			        listBeanFwzyfffhcsb.add(uBean);
			    } 
			    fwfhcsnum= FwzyfffhcsbDao.insertByBatch(listBeanFwzyfffhcsb);
			}
			    
			    
			    
			
			    
			   if(num>0||fwfsnum>0||fwqqcsnum>0||fwfhcsnum>0){
				   resultj.put("success", true);
			   }else{
				   resultj.put("errorMsg", "导入数据出现异常！");
			   }
		} catch (IOException e) {
			 resultj.put("errorMsg", e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultj.put("errorMsg", e.getMessage());
		}
	        
	      return resultj;
		   /*if(num>0){
			   resultj.put("success", true);
			}else{
				
				resultj.put("errorMsg", "导入数据出现异常！");
			}
			try {
				ResponseUtil.write(response, resultj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
	}
	@RequestMapping(value = "downLoadExcelModel")
	public String downLoadExcelModel(HttpServletRequest request,HttpServletResponse response) {
		String path = request.getSession().getServletContext().getRealPath(
	            "/static/doc");
		 String fileName = "";
         String realName = "";
		String flag=request.getParameter("flag");
		if(flag.equals("fwzc")){
			fileName = "服务注册导入模板.xlsx";
	            realName = "fwzc.xlsx";
		}
      if(flag.equals("fwff")){
    		fileName = "服务方法注册导入模板.xlsx";
            realName = "fwff.xlsx";
		}
       if(flag.equals("fwqqcs")){
    		fileName = "服务请求参数导入模板.xlsx";
            realName = "fwqqcs.xlsx";
        }
      if(flag.equals("fwfhcs")){
    		fileName = "服务返回参数导入模板.xlsx";
            realName = "fwfhcs.xlsx";
      }
	          
	           String filePath = path + "/" + realName;
	           try {
	        	   ExcelReaderDown.downLoadFile(filePath, response, fileName, "xlsx");
			} catch (Exception e) {
				e.printStackTrace();
			}
	           return null;
	}
}
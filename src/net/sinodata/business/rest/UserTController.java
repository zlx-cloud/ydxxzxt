package net.sinodata.business.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sinodata.business.dao.FwcyfzcbDao;
import net.sinodata.business.entity.Fwcyfzcb;
import net.sinodata.business.entity.FwcyfzcbReg;
import net.sinodata.business.service.FwcyfzcbRegService;
import net.sinodata.business.service.JgdmbService;
import net.sinodata.business.service.UserTService;
import net.sinodata.business.util.DateUtil;
import net.sinodata.business.util.ExcelReaderDown;
import net.sinodata.business.util.MD5Utils;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.ResponseUtil;
import net.sinodata.business.util.SearchResult;
import net.sinodata.business.util.StringUtil;
import net.sinodata.security.vo.ShiroUser;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
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
public class UserTController {
	//private static Logger logger = LoggerFactory.getLogger(UserTController.class);

	@Autowired
	UserTService UserTService;
	@Autowired
	JgdmbService JgdmbService;
	@Autowired
	FwcyfzcbRegService FwcyfzcbRegService;
	@Autowired
	private FwcyfzcbDao fwcyfzcbDao;

	@RequestMapping(value = "/userT/modifyPassword", method = RequestMethod.POST)
	@ResponseBody
	public void modifyPassword(HttpServletRequest request,
			HttpServletResponse response) {
		String userId=request.getParameter("userId");
		String newPassword=request.getParameter("newPassword");
		Fwcyfzcb userT=new Fwcyfzcb();
		userT.setFwcyfYyxtbh(userId);
		userT.setFwcyfDlmm(newPassword);
		int updateNum=UserTService.updateByPrimaryKeySelective(userT);
		JSONObject result=new JSONObject();
		if(updateNum>0){
			result.put("success", "true");
		}else{
			result.put("success", "true");
			result.put("errorMsg", "修改密码失败！");
		}
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/userT", method = RequestMethod.GET)
	public String userList(Model model) {
		
		return "account/userManage";
	}
	
	@RequestMapping(value = "/userT/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String s_userName=request.getParameter("s_userName");
		String s_roleId=request.getParameter("s_roleId");
		String s_bm=request.getParameter("s_bm");
		String s_yybs=request.getParameter("s_yybs");
		if(StringUtil.isNotEmpty(s_userName)){
			
			condition.put("username",s_userName);
		}
		if(StringUtil.isNotEmpty(s_roleId)){
			condition.put("roleid",s_roleId);
		}
		if(StringUtil.isNotEmpty(s_bm)){
			condition.put("bm",s_bm);
		}
		if(StringUtil.isNotEmpty(s_yybs)){
			condition.put("yybs",s_yybs);
		}
		SearchResult resoult = UserTService.userList(page, condition);
		request.getSession().setAttribute("selectMap", condition);
		return resoult;
	}
	
	@RequestMapping(value = "/userT/add", method = RequestMethod.POST)
	@ResponseBody
	public void createUser(Fwcyfzcb user,HttpServletRequest request,
			HttpServletResponse response) {
		user.setFwcyfDlmm(MD5Utils.getMD5String(user.getFwcyfDlmm()));
		JSONObject result=new JSONObject();
		String flag=request.getParameter("flag");
			if(!StringUtil.isNotEmpty(flag)){
				try {
					//user.setUserid(userId);
					int i=UserTService.updateByPrimaryKeySelective(user);
					if(i==0){
						result.put("errorMsg", "更新失败！");
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				if( UserTService.findUserByLoginName(user.getFwcyfYyxtmc()).size()>0){
					result.put("success", true);
					result.put("errorMsg", "此用户名已经存在");
				}else if(UserTService.selectByPrimaryKey(user.getFwcyfYyxtbh())!=null){
					result.put("success", true);
					result.put("errorMsg", "此标识已经存在");
				}
				else{
					try {
						
						user.setFwcyfRqsj(DateUtil.formatDateToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				UserTService.insertSelective(user);
				result.put("success", true);
				}
			}
			
		
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	@RequestMapping(value="/userT/delete",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void userList(HttpServletRequest request,
			HttpServletResponse response,String delIds) {
		JSONObject result=new JSONObject();
		try {
			int delNums=UserTService.deleteByPrimaryKey(delIds);
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
	
	@RequestMapping(value = "/userT/userDetail", method = RequestMethod.GET)
	public String userDetail(HttpServletRequest request,
			HttpServletResponse response) {
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		Fwcyfzcb fwcyfzcb=	UserTService.selectByPrimaryKey(shiroUser.getId());
		
		request.setAttribute("fwcyfzcb", fwcyfzcb);
		return "account/userDetail";
	}
	@RequestMapping(value = "/userT/jgTree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
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
	
	@RequestMapping(value = "/userT/check", method = RequestMethod.GET)
	public String check(HttpServletRequest request,
			HttpServletResponse response) {
		
		return "account/userCheck";
	}
	

	@RequestMapping(value="/userT/updateStatus",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void updateStatus(HttpServletRequest request,
			HttpServletResponse response,String delIds) throws IllegalAccessException, InvocationTargetException {
		String fwcyfYyxtbh=request.getParameter("fwcyfYyxtbh");
		int i=0;
		JSONObject result=new JSONObject();
		FwcyfzcbReg FwcyfzcbReg=FwcyfzcbRegService.selectByPrimaryKey(fwcyfYyxtbh);
		Fwcyfzcb Fwcyfzcb=new Fwcyfzcb();
		ConvertUtils.register(new DateConverter(null), java.util.Date.class);  
		BeanUtils.copyProperties(Fwcyfzcb,FwcyfzcbReg);
		
		Fwcyfzcb.setFwcyfDlmm(MD5Utils.getMD5String(Fwcyfzcb.getFwcyfDlmm()));
		
			 i=UserTService.insert(Fwcyfzcb);
		
		if(i>0){
			
			FwcyfzcbRegService.deleteByPrimaryKey(fwcyfYyxtbh);
			
			
				result.put("success", "true");
			
		}
		
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/userT/checklist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object checklist(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String s_userName=request.getParameter("s_userName");
		String s_status=request.getParameter("s_status");
		if(StringUtil.isNotEmpty(s_userName)){
			
			condition.put("username",s_userName);
		}
if(StringUtil.isNotEmpty(s_status)){
			
			condition.put("status",s_status);
		}
		SearchResult resoult = FwcyfzcbRegService.userRegList(page, condition);
		return resoult;
	}
	
	@RequestMapping(value = "/userT/exportExcel")
	 public Object exportExcel(HttpServletRequest request,HttpServletResponse response){
		 //文件字符输出流
			FileOutputStream toClient=null;
			try {
				Map<String, Object> condition=(Map<String, Object>) request.getSession().getAttribute("selectMap");
				 int rows=fwcyfzcbDao.getUserCountByPage(condition);
				 condition.put("start", (1*rows-rows));
				 condition.put("end", rows);
				 List<Fwcyfzcb> dataset=fwcyfzcbDao.getUserListByPage(condition);
				//得到项目路径
		        String rootpath = request.getSession().getServletContext().getRealPath("/");
		        //文件名
		        String fileName="exportdata.xls";
		        //创建文件字符输出流（项目路径+路径+文件名）
				toClient = new FileOutputStream(rootpath + File.separator+"static" +File.separator+ fileName);
				//创建电子表格
				ExcelReaderDown<Fwcyfzcb> export=new ExcelReaderDown<Fwcyfzcb>();
				
				String[] headers =  
			        { "服务参与方_标识","服务参与方_名称","服务参与方_描述","服务参与方_注册日期时间",
			        		"服务参与方_所属分局","服务参与方_联系方式","联系人_说明","联系人_姓名","联系电话","电子信箱",
			        		"通信地址","应用编号"};
				//输出表格（电子表格标题，电子表格数据，文件输出流，日期类型转格式，表格存放规则）
				export.setNum(6);
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
}

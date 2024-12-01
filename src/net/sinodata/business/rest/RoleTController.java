package net.sinodata.business.rest;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sinodata.business.entity.RoleT;
import net.sinodata.business.service.RoleTService;
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
public class RoleTController {
	//private static Logger logger = LoggerFactory.getLogger(RoleTController.class);

	@Autowired
	RoleTService RoleTService;
	
	

	@RequestMapping(value = "/roleT", method = RequestMethod.GET)
	public String userList(Model model) {
		
		return "account/roleManage";
	}
	@RequestMapping(value = "/roleT/comBoList", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void comBoList(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		JSONArray jsonArray=new JSONArray();
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("roleid", "");
		jsonObject.put("rolename", "请选择...");
		jsonArray.add(jsonObject);
		jsonArray.addAll(RoleTService.selectByPrimaryKey(null));
		try {
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	@RequestMapping(value = "/roleT/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		
		Map<String, Object> condition = new HashMap<String, Object>();
		String s_roleName=request.getParameter("s_roleName");
		String s_roleId=request.getParameter("s_roleId");
		if(StringUtil.isNotEmpty(s_roleName)){
			
			condition.put("rolename",s_roleName);
		}
		if(StringUtil.isNotEmpty(s_roleId)){
			condition.put("roleid",s_roleId);
		}
	    SearchResult resoult = RoleTService.roleList(page, condition);
		
		return resoult;
	}
	
	@RequestMapping(value = "/roleT/add", method = RequestMethod.POST)
	@ResponseBody
	public void createRole(RoleT role, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject result=new JSONObject();
		
			if(StringUtil.isNotEmpty(role.getRoleid())){
				
				try {
					RoleTService.updateByPrimaryKeySelective(role);
					result.put("success", true);
				} catch (Exception e) {
					result.put("success", true);
					result.put("errorMsg", "保存失败");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				RoleTService.insertSelective(role);
				result.put("success", true);
			}
			
		
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/roleT/delete",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void deleteRole(HttpServletRequest request,
			HttpServletResponse response,String delIds) {
		JSONObject result=new JSONObject();
		try {
			String str[]=delIds.split(",");
			for(int i=0;i<str.length;i++){
				
				if( RoleTService.existUserWithRoleId( str[i])>0){
					result.put("errorIndex", i);
					result.put("errorMsg", "角色下面有用户，不能删除！");
					ResponseUtil.write(response, result);
					return;
				}
			}
			int delNums=RoleTService.deleteByPrimaryKey(delIds);
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
	
	@RequestMapping(value = "/roleT/MenuIdsUpdate", method = RequestMethod.POST)
	@ResponseBody
	public void roleMenuIdsUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		String roleId=request.getParameter("roleId");
		String menuIds=request.getParameter("menuIds");
		JSONObject result=new JSONObject();
		RoleT role =new RoleT();
		role.setRoleid(roleId);
		role.setMenuids(menuIds);
		try {
			int updateNums= RoleTService.updateByPrimaryKeySelective(role);
			  if(updateNums>0){
			  	result.put("success", true);
				}else{
					result.put("errorMsg", "授权失败");
				}
				ResponseUtil.write(response, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

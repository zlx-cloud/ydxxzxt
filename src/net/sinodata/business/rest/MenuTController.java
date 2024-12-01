package net.sinodata.business.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sinodata.business.entity.MenuT;
import net.sinodata.business.entity.RoleT;
import net.sinodata.business.service.MenuTService;
import net.sinodata.business.service.RoleTService;
import net.sinodata.business.util.ResponseUtil;
import net.sinodata.business.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MenuTController {
	//private static Logger logger = LoggerFactory.getLogger(MenuTController.class);

	@Autowired
	MenuTService MenuTService;
	@Autowired
	RoleTService RoleTService;

	@RequestMapping(value = "/menuT", method = RequestMethod.GET)
	public String menuList(Model model) {

		return "account/menuManage";
	}

	@RequestMapping(value="/menuT/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void list(HttpServletRequest request,
			HttpServletResponse response) {
		String parentId=request.getParameter("parentId");
	
		try {
			JSONArray jsonArray=MenuTService.getMenuListByParentId(parentId);
			
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	    @RequestMapping(value="/menuT/MenuTree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public void MenuTree( HttpServletRequest request,
				HttpServletResponse response) {
			String parentid=request.getParameter("parentid");
			String roleid=request.getParameter("roleid");
			String menuIds="";
		List<RoleT> t=	RoleTService.selectByPrimaryKey(roleid);
		if(t.size()>0){
			menuIds=t.get(0).getMenuids();
		}
		  try {
			JSONArray jsonArray=MenuTService.getCheckedMenusByParentId( parentid,menuIds);
			 
			   ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
				
		}
	    
	    @RequestMapping(value="/menuT/treeGridMenu", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public void treeGridMenu( HttpServletRequest request,
				HttpServletResponse response) {
	    	String parentId=request.getParameter("parentId");
	    	JSONArray jsonArray;
			try {
				jsonArray = MenuTService.getListByParentId( parentId);
				ResponseUtil.write(response, jsonArray);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	    	
	    }
	    @RequestMapping(value = "/menuT/add", method = RequestMethod.POST)
		@ResponseBody
		public void createMenu(MenuT menu,HttpServletRequest request,
				HttpServletResponse response) {
	    	String menuId=request.getParameter("menuId");
	    	String parentId =request.getParameter("parentId");
			boolean isLeaf=false;
			JSONObject result=new JSONObject();
			if(StringUtil.isNotEmpty(menuId)){
				menu.setMenuid(menuId);
			}else{
				menu.setParentid(parentId);
			}
			try {
				int saveNums=0;
				if(StringUtil.isNotEmpty(menu.getMenuid())){
					saveNums=MenuTService.updateByPrimaryKeySelective(menu);
				}else{
					
					 menu.setState("open");
					isLeaf=MenuTService.isLeaf(parentId);
					if(isLeaf){
						MenuT menuT=new MenuT();
						menuT.setState("closed");
						menuT.setMenuid(parentId);
					    MenuTService.updateByPrimaryKeySelective(menuT);
					   
					    saveNums=	MenuTService.insertSelective(menu);
						
					}else{
						saveNums=MenuTService.insertSelective(menu);
						
					}
				}
				if(saveNums>0){
					result.put("success", true);
				}else{
					result.put("success", true);
					result.put("errorMsg", "保存失败");
				}
			} catch (Exception e) {
				result.put("success", true);
				result.put("errorMsg", "保存失败");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ResponseUtil.write(response, result);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	    @RequestMapping(value="/menuT/delete",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public void userList(HttpServletRequest request,
				HttpServletResponse response,String delIds) {
			
		String menuId=request.getParameter("menuId");
		String parentId=request.getParameter("parentId");
		JSONObject result=new JSONObject();
		int sonNum=-1;
		try {
			if(!MenuTService.isLeaf(menuId)){
				result.put("errorMsg", "该菜单节点有子节点，不能删除！");
			}else{
				int delNums=0;
				sonNum=MenuTService.getMenuCountByParentId(parentId);
				if(sonNum==1){
					MenuT menuT=new MenuT();
					menuT.setState("open");
					menuT.setMenuid(parentId);
					MenuTService.updateByPrimaryKeySelective(menuT);
					
					delNums=MenuTService.deleteByPrimaryKey(menuId);
				}else{
					delNums=MenuTService.deleteByPrimaryKey(menuId);
				}
				if(delNums>0){
					result.put("success", true);
				}else{
					result.put("errorMsg", "删除失败");
				}
			}
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
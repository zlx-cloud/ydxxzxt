package net.sinodata.business.service.impl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sinodata.business.dao.MenuTDao;
import net.sinodata.business.dao.RoleTDao;
import net.sinodata.business.entity.MenuT;
import net.sinodata.business.entity.RoleT;
import net.sinodata.business.service.MenuTService;

import net.sinodata.business.util.StringUtil;
import net.sinodata.security.vo.ShiroUser;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuTServiceImpl implements MenuTService {
	@Autowired
	private MenuTDao menuDao;
	
	@Autowired
	private RoleTDao roleDao;
	
	

	@Override
	public JSONArray getMenuListByParentId(String parentId) throws Exception {
		JSONArray jsonArray=null;
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		RoleT RoleT=roleDao.selectByPrimaryKey(shiroUser.getRoleid()).get(0);
		if(RoleT!=null){
			
			 jsonArray=this.getMenusByParentId(parentId,RoleT.getMenuids());
		}
		return jsonArray;
	}
	
	public JSONArray getMenusByParentId(String parentId,String menuIds)throws Exception{
		JSONArray jsonArray=this.getMenuByParentId( parentId,menuIds);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			if("open".equals(jsonObject.getString("state"))){
				continue;
			}else{
				jsonObject.put("children", getMenusByParentId(jsonObject.getString("id"),menuIds));
			}
		}
		return jsonArray;
	}
	  
	public JSONArray getMenuByParentId(String parentId,String menuIds)throws Exception{
		JSONArray jsonArray=new JSONArray();
		 List<MenuT> list=menuDao.queryByParentIdAndMenuId(parentId,menuIds);
	
		 for(MenuT rs:list){
			 JSONObject jsonObject=new JSONObject();
				jsonObject.put("id", rs.getMenuid());
				jsonObject.put("menuname", rs.getMenuname());
				if(!hasChildren(rs.getMenuid(), menuIds)){
					jsonObject.put("state", "open");
				}else{
					jsonObject.put("state", rs.getState());				
				}
				//jsonObject.put("state", rs.getState());
				jsonObject.put("iconcls", rs.getIconcls());
				jsonObject.put("menupath", rs.getMenupath());
				jsonObject.put("menudescription", rs.getMenudescription());
				jsonArray.add(jsonObject);
			/* JSONObject jsonObject=new JSONObject();
				jsonObject.put("id", rs.getMenuid());
				jsonObject.put("text", rs.getMenuname());
				if(!hasChildren(rs.getMenuid(), menuIds)){
					jsonObject.put("state", "open");
				}else{
					jsonObject.put("state", rs.getState());				
				}
				jsonObject.put("iconCls", rs.getIconcls());
				JSONObject attributeObject=new JSONObject();
				attributeObject.put("authPath", rs.getMenupath());
				jsonObject.put("attributes", attributeObject);
				jsonArray.add(jsonObject);*/
		 }
		 
		 
		
		return jsonArray;
	}

	private boolean hasChildren(String parentId,String menuIds)throws Exception{
		 List<MenuT> list=menuDao.queryByParentIdAndMenuId(parentId,menuIds);
		 if(list!=null){
			 if(list.size()==0){
				 return false;
			 } 
		 }else{
			 return false;
		 }
		
		return true;
	}
	
	
	
	
	
	
	
	
	@Override
	public JSONArray getCheckedMenusByParentId(String parentId, String menuIds) throws Exception {
		JSONArray jsonArray=this.getCheckedMenuByParentId( parentId,menuIds);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			if("open".equals(jsonObject.getString("state"))){
				continue;
			}else{
				jsonObject.put("children", getCheckedMenusByParentId(jsonObject.getString("id"),menuIds));
			}
		}
		return jsonArray;
	}

	public JSONArray getCheckedMenuByParentId(String parentId,String menuIds)throws Exception{
		JSONArray jsonArray=new JSONArray();
		 List<MenuT> list=menuDao.queryByParentIdAndMenuId(parentId,null);
		
		 for(MenuT rs:list){
			JSONObject jsonObject=new JSONObject();
			String Menuid=rs.getMenuid();
			jsonObject.put("id", Menuid);
			jsonObject.put("text", rs.getMenuname());
			jsonObject.put("state", rs.getState());
			jsonObject.put("iconCls", rs.getIconcls());
			if(StringUtil.isNotEmpty(menuIds)){
				if(StringUtil.existStrArr(Menuid, menuIds.split(","))){
					jsonObject.put("checked", true);
				}	
			}else{
				jsonObject.put("checked", false);
			}
			
			JSONObject attributeObject=new JSONObject();
			attributeObject.put("authPath", rs.getMenupath());
			jsonObject.put("attributes", attributeObject);
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	

	@Override
	public JSONArray getListByParentId(String parentId) throws Exception {
		JSONArray jsonArray=this.getTreeGridAuthByParentId(parentId);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			if("open".equals(jsonObject.getString("state"))){
				continue;
			}else{
				jsonObject.put("children", getListByParentId(jsonObject.getString("id")));
			}
		}
		return jsonArray;
	}
	public JSONArray getTreeGridAuthByParentId(String parentId)throws Exception{
		JSONArray jsonArray=new JSONArray();
		 List<MenuT> list=menuDao.queryByParentIdAndMenuId(parentId,null);
		 for(MenuT rs:list){
		
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id", rs.getMenuid());
			jsonObject.put("text", rs.getMenuname());
			jsonObject.put("state", rs.getState());
			jsonObject.put("iconcls", rs.getIconcls());
			jsonObject.put("menupath", rs.getMenupath());
			jsonObject.put("menudescription", rs.getMenudescription());
			jsonArray.add(jsonObject);
		}
		return jsonArray;
	}
	
	
	
	
	@Override
	public boolean isLeaf(String menuid) throws Exception {
		// TODO Auto-generated method stub
		 List<MenuT> list=menuDao.queryByParentIdAndMenuId(menuid,null);
		 if(list.size()>0){
			 return false;
		 }
		 return true;
	}

	
	@Override
	public int getMenuCountByParentId(String parentId) {
		List<MenuT> list=null;
		 try {
			 list=menuDao.queryByParentIdAndMenuId(parentId,null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list.size();
	}

	@Override
	public int deleteByPrimaryKey(String menuid) {
		// TODO Auto-generated method stub
		return menuDao.deleteByPrimaryKey(menuid);
	}

	@Override
	public int insert(MenuT record) {
		// TODO Auto-generated method stub
		return menuDao.insert(record);
	}

	@Override
	public int insertSelective(MenuT record) {
		// TODO Auto-generated method stub
		return menuDao.insertSelective(record);
	}

	@Override
	public MenuT selectByPrimaryKey(String menuid) {
		// TODO Auto-generated method stub
		return menuDao.selectByPrimaryKey(menuid);
	}

	@Override
	public int updateByPrimaryKeySelective(MenuT record) {
		// TODO Auto-generated method stub
		return menuDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(MenuT record) {
		// TODO Auto-generated method stub
		return menuDao.updateByPrimaryKey(record);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	

	
	
}

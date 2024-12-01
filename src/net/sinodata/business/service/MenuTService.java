package net.sinodata.business.service;

import net.sf.json.JSONArray;
import net.sinodata.business.entity.MenuT;




public interface MenuTService{

	 int deleteByPrimaryKey(String menuid);

	    int insert(MenuT record);

	    int insertSelective(MenuT record);

	    MenuT selectByPrimaryKey(String menuid);

	    int updateByPrimaryKeySelective(MenuT record);

	    int updateByPrimaryKey(MenuT record);
	    
	    JSONArray getMenuListByParentId(String parentId) throws Exception;
	    JSONArray getCheckedMenusByParentId(String parentId,String menuIds) throws Exception;
	    JSONArray getListByParentId(String parentId)throws Exception;
	    public boolean isLeaf(String menuid)throws Exception;
	    
	    int getMenuCountByParentId(String parentId);
	    
	    

	
}

package net.sinodata.business.service;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.RoleT;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;




public interface RoleTService{

	   int deleteByPrimaryKey(String roleid);

	    int insert(RoleT record);

	    int insertSelective(RoleT record);

	    List<RoleT> selectByPrimaryKey(String roleid);

	    int updateByPrimaryKeySelective(RoleT record);

	    int updateByPrimaryKey(RoleT record);
	    public SearchResult roleList(Page page,
				Map<String, Object> condition);
	    int existUserWithRoleId(String roleid);
	
	    
}

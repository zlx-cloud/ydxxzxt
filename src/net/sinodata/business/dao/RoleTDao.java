package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.RoleT;

import org.apache.ibatis.annotations.Param;

public interface RoleTDao {
    int deleteByPrimaryKey(@Param("roleid")String roleid);

    int insert(RoleT record);

    int insertSelective(RoleT record);

   List<RoleT> selectByPrimaryKey(@Param("roleid")String roleid);

    int updateByPrimaryKeySelective(RoleT record);

    int updateByPrimaryKey(RoleT record);
    
    
    List<RoleT> getRoleListByPage( Map<String, Object> condition);
	
	int getRoleCountByPage( Map<String, Object> condition);
	int existUserWithRoleId(@Param("roleid")String roleid);
    
}
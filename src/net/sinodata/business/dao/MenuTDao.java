package net.sinodata.business.dao;

import java.util.List;

import net.sinodata.business.entity.MenuT;

import org.apache.ibatis.annotations.Param;

public interface MenuTDao {
    int deleteByPrimaryKey(String menuid);

    int insert(MenuT record);

    int insertSelective(MenuT record);

    MenuT selectByPrimaryKey(String menuid);

    int updateByPrimaryKeySelective(MenuT record);

    int updateByPrimaryKey(MenuT record);
    
    List<MenuT> queryByParentIdAndMenuId(@Param("parentId")String parentId,@Param("menuIds")String menuIds) throws Exception;
    
    
}
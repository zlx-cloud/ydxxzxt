package net.sinodata.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


import net.sinodata.business.entity.FwOrg;

public interface FwOrgDao {

	List<FwOrg> queryAllList(@Param("path") String path);
}

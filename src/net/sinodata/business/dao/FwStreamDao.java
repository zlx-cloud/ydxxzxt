package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.sinodata.business.entity.FwStream;

public interface FwStreamDao {

	List<FwStream> queryAllList(@Param("path") String path);
	
	List<FwStream> queryStreamByPage(Map<String, Object> condition);
	
	int queryStreamCountByPage(Map<String, Object> condition);
}

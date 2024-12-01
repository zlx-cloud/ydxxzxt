package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

import net.sinodata.business.entity.FwEventLog;

public interface FwEventLogDao {

	public List<FwEventLog> queryLogList(Map<String,Object> condition);
	
	public int queryLogListCount(Map<String,Object> condition);
	
	public FwEventLog selectLogByPkid(@Param("id") String id);
	
	//sql执行日志
	List<Map<String,Object>> sqlAreaList(Map<String,Object> condition);
	int sqlAreaCount(Map<String,Object> condition);
	String lookSql(@Param("sqlId") String sqlId);
	
}

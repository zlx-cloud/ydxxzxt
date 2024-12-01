package net.sinodata.business.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface SSJKService {

	public SearchResult InPortPageList(Page page, Map<String, Object> condition);

	public SearchResult PageIntePortList(Page page,
			Map<String, Object> condition);

	List<Map<String, Object>> queryInPort();

	List<Map<String, Object>> queryByIP(@Param("IP") String taskid,
			@Param("task_exe_time") String task_exe_time);

	List<Map<String, String>> queryIpPort();
	
	//请求访问频次监听
	Integer selectQqfwCount(@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	//异常发生监测
	Integer selectYcCount(@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	//执行时长监测
	Integer selectZxTime(@Param("startTime")String startTime,@Param("endTime")String endTime);
	
}
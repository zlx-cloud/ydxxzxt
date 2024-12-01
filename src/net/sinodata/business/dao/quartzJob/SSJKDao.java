package net.sinodata.business.dao.quartzJob;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface SSJKDao {

	
	
	
	//接口监听
	int addInPortSJ(Map<String, Object> maps);

	List<Map<String, Object>> queryInPort();
	
	Integer queryInPortCount();
	
	List<Map<String, Object>> queryInPortTable(Map map);
	
	Integer queryCountByIP(Map map);
	
	
	List<Map<String, Object>> queryByIP(@Param("IP") String taskid,@Param("task_exe_time") String task_exe_time);
	

	List<Map<String, Object>> queryByIPTable(Map map);
	
	List<Map<String, String>> queryIpPort();
	
	//请求访问频次监听
	Integer selectQqfwCount(@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	//异常发生监测
	Integer selectYcCount(@Param("startTime")String startTime,@Param("endTime")String endTime);
	
	//执行时长监测
	Integer selectZxTime(@Param("startTime")String startTime,@Param("endTime")String endTime);
}
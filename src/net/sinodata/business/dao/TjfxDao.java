package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;


public interface TjfxDao {

	//当前活跃服务
	List<Map<String, Object>> selectCurrentActiveServiceData();
	List<Map<String, Object>> queryCurrentActiveServiceTable(Map<?, ?> map);
	Integer queryCurrentActiveServiceCount();
	
	//服务日调用量统计
	List<Map<String, Object>> serviceUseByDay();
	List<Map<String, Object>> queryServiceUseByDayTable(Map<?, ?> map);
	Integer queryServiceUseByDayCount();
	
	//服务周调用量统计
	Integer serviceUseByWeek(@Param("firstDay")String firstDay,@Param("lastDay")String lastDay);
	List<Map<String, Object>> queryServiceUseByWeekTable(Map<?, ?> map);
	Integer queryServiceUseByWeekCount(Map<?, ?> map);
	
	//各分局调用量占比统计
	List<Map<String, Object>> serviceUseByOrg();
	List<Map<String, Object>> queryServiceUseByOrgTable(Map<?, ?> map);
	Integer queryServiceUseByOrgCount();
	
	//各分局调用量日统计
		//查询近7天内调用服务的机构
		List<Map<String, Object>> selectOrgCodeAndName();
		//查询某机构在固定某一天调用服务的次数
		Integer selectCountByOrgAndDay(@Param("day")String day,@Param("orgCode")String orgCode);
	List<Map<String, Object>> queryServiceUseByOrgAndDayTable(Map<?, ?> map);
	Integer queryServiceUseByOrgAndDayCount();
	
	//服务调用Top10
	List<Map<String, Object>> selectServiceUseTopTen();
	List<Map<String, Object>> queryServiceUseTopTenTable(Map<?, ?> map);
	Integer queryServiceUseTopTenCount();
	
	//总线服务调用统计
	Integer selectCurrentZXInfo(@Param("startTime")String startTime,@Param("endTime")String endTime);
	List<Map<String, Object>> queryZXInfoTable(Map<?, ?> map);
	Integer queryZXInfoCount(Map<?, ?> map);
	
	Integer insertFwzytcsbrzb(@Param("fwbs")String fwbs,@Param("time")String time);
}

package net.sinodata.business.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;

import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface TjfxService {

	//当前活跃服务
	List<Map<String, Object>> selectCurrentActiveServiceData();
	public SearchResult CurrentActiveServicePageList(Page page, Map<String, Object> condition);
	
	//服务日调用量统计
	List<Map<String, Object>> serviceUseByDay();
	public SearchResult ServiceUseByDayPageList(Page page, Map<String, Object> condition);
	
	//服务周调用量统计
	Integer serviceUseByWeek(@Param("firstDay")String firstDay,@Param("lastDay")String lastDay);
	public SearchResult ServiceUseByWeekPageList(Page page, Map<String, Object> condition);
	
	//各分局调用量占比统计
	List<Map<String, Object>> serviceUseByOrg();
	public SearchResult ServiceUseByOrgPageList(Page page, Map<String, Object> condition);
	
	//各分局调用量日统计
		//查询近7天内调用服务的机构
		List<Map<String, Object>> selectOrgCodeAndName();
		//查询某机构在固定某一天调用服务的次数
		Integer selectCountByOrgAndDay(@Param("day")String day,@Param("orgCode")String orgCode);
	public SearchResult ServiceUseByOrgAndDayPageList(Page page, Map<String, Object> condition);
	
	//服务调用Top10
	List<Map<String, Object>> selectServiceUseTopTen();
	public SearchResult ServiceUseTopTenPageList(Page page, Map<String, Object> condition);
	
	//总线服务调用统计
	Integer selectCurrentZXInfo(@Param("startTime")String startTime,@Param("endTime")String endTime);
	public SearchResult ZXInfoPageList(Page page, Map<String, Object> condition);
	
	Integer insertFwzytcsbrzb(@Param("fwbs")String fwbs,@Param("time")String time);
	
}

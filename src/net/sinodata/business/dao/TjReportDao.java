package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

public interface TjReportDao {

	// 应用总数、服务总数、方法总数
	List<Map<String, Object>> getZl();

	// 本月服务调用量统计
	List<Map<String, Object>> fwCountByMonth();

	// 本月各分局服务调用量统计Top10
	List<Map<String, Object>> jgCountByMonth();

	// ========================================================
	// 统计数字
	List<Map<String, Object>> tjsz();

	// 单位时间各单位注册用户量统计及排名
	List<Map<String, Object>> zcyhlRankByTimeAndJg();

	// 单位时间各单位用户登录量统计及排名
	List<Map<String, Object>> yhdllRankByTimeAndJg();

	// 各单位服务注册统计及排名
	List<Map<String, Object>> fwzclByJg();

	// 现有、新增服务资源分类统计与排名
	List<Map<String, Object>> fwzyRankByCzlx();
	List<Map<String, Object>> fwzyRankByYy();
	List<Map<String, Object>> fwzyRankByJzfl();

	// 单位时间各单位请求量统计及排名
	List<Map<String, Object>> qqlRankBySjAndJg();
	// 单位时间服务资源调用量统计
	List<Map<String, Object>> fwzydylRank();
	// 单位时间，应用系统被调用量统计与排名
	List<Map<String, Object>> yyxtdylRank();
	// 单位时间，终端应用请求量统计与排名
	List<Map<String, Object>> zdyyqqlRank();
	// 单位时间，服务资源响应时间统计与排名
	List<Map<String, Object>> fwzyxysjRank();
	// 单位时间，服务资源异常率统计与排名
	List<Map<String, Object>> fwzyyclRank();
	// 单位时间，服务资源使用高峰时段统计与排名
	List<Map<String, Object>> fwzysygfsdRank();
	
	// 单位时间，各单位报文采集量统计及排名
	List<Map<String, Object>> bwcjlRankByJg();
	
}
package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TemporaryDao {

	List<Map<String, Object>> getWarnCountListByPage(Map<String, Object> condition);

	int getWarnCountNumByPage(Map<String, Object> condition);

	List<Map<String, Object>> queryWarnList(@Param("yybs") String yybs, @Param("fwbs") String fwbs,
			@Param("ffbs") String ffbs, @Param("startTime") String startTime, @Param("endTime") String endTime);

	List<Map<String, Object>> getYjxxListByPage(Map<String, Object> condition);

	int getYjxxCountByPage(Map<String, Object> condition);

	List<Map<String, Object>> yjsjcxDetailPage(Map<String, Object> condition);

	int yjsjcxDetailCount(Map<String, Object> condition);

	int selectUnReadCount();

	List<Map<String, Object>> getUnReadWarnListByPage();

	int updateStatusToread();

	List<Map<String, Object>> fwzlfxSearch(@Param("yybs") String yybs, @Param("time") String time);

	List<Map<String, Object>> yhqqpmfxSearch(@Param("yybs") String yybs, @Param("time") String time);

	List<Map<String, Object>> getYhqqpmfxListByPage(Map<String, Object> condition);

	int getYhqqpmfxCountByPage(Map<String, Object> condition);

	List<Map<String, Object>> getFwzlfxListByPage(Map<String, Object> condition);

	int getFwzlfxCountByPage(Map<String, Object> condition);
	
	int fxsjhzCount(@Param("yybs") String yybs, @Param("fwbs") String fwbs,
			@Param("ffbs") String ffbs, @Param("startTime") String startTime, 
			@Param("endTime") String endTime,@Param("type") String type);
	
	List<Map<String, Object>> yjsjhzChart(@Param("yybs") String yybs, @Param("fwbs") String fwbs,
			@Param("ffbs") String ffbs, @Param("startTime") String startTime,
			@Param("endTime") String endTime);
	
	List<Map<String, Object>> getYjxxcxListByPage(Map<String, Object> condition);

	int getYjxxcxNumByPage(Map<String, Object> condition);

}
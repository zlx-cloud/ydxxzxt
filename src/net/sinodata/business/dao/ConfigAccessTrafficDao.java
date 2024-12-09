package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.sinodata.business.entity.ConfigAccessTraffic;

public interface ConfigAccessTrafficDao {

	List<ConfigAccessTraffic> queryListByPage(Map<String, Object> condition);

	int queryListCountByPage(Map<String, Object> condition);

	int insertSelective(ConfigAccessTraffic configAccessTraffic);

	int updateByPrimaryKeySelective(ConfigAccessTraffic configAccessTraffic);

	int deleteByPrimaryKey(@Param("id") String id);

	int updateStatus(@Param("id") String id, @Param("enabled") String enabled, @Param("updateTime") String updateTime);

}
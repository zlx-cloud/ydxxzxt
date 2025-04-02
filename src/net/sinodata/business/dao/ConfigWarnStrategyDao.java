package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.sinodata.business.entity.ConfigWarnStrategy;

public interface ConfigWarnStrategyDao {

	List<ConfigWarnStrategy> queryListByPage(Map<String, Object> condition);

	int queryListCountByPage(Map<String, Object> condition);

	int insertSelective(ConfigWarnStrategy configWarnStrategy);

	int updateByPrimaryKeySelective(ConfigWarnStrategy configWarnStrategy);

	int deleteByPrimaryKey(@Param("id") String id);

	int updateStatus(@Param("id") String id, @Param("enabled") String enabled, @Param("updateTime") String updateTime);

}
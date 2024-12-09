package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.sinodata.business.entity.ConfigAccessFrequency;

public interface ConfigAccessFrequencyDao {

	List<ConfigAccessFrequency> queryListByPage(Map<String, Object> condition);

	int queryListCountByPage(Map<String, Object> condition);

	int insertSelective(ConfigAccessFrequency configAccessFrequency);

	int updateByPrimaryKeySelective(ConfigAccessFrequency configAccessFrequency);

	int deleteByPrimaryKey(@Param("id") String id);

	int updateStatus(@Param("id") String id, @Param("enabled") String enabled, @Param("updateTime") String updateTime);

}
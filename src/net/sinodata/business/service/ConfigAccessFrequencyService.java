package net.sinodata.business.service;

import java.util.Map;

import net.sinodata.business.entity.ConfigAccessFrequency;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface ConfigAccessFrequencyService {

	SearchResult list(Page page, Map<String, Object> condition);

	int insertSelective(ConfigAccessFrequency configAccessFrequency);

	int updateByPrimaryKeySelective(ConfigAccessFrequency configAccessFrequency);

	int deleteByPrimaryKey(String id);

	int updateStatus(String id, String enabled, String updateTime);

}
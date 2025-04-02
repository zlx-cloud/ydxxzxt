package net.sinodata.business.service;

import java.util.Map;

import net.sinodata.business.entity.ConfigRiskStrategy;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface ConfigRiskStrategyService {

	SearchResult list(Page page, Map<String, Object> condition);

	int insertSelective(ConfigRiskStrategy configRiskStrategy);

	int updateByPrimaryKeySelective(ConfigRiskStrategy configRiskStrategy);

	int deleteByPrimaryKey(String id);

	int updateStatus(String id, String enabled, String updateTime);

}
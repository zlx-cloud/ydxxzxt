package net.sinodata.business.service;

import java.util.Map;

import net.sinodata.business.entity.ConfigWarnStrategy;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface ConfigWarnStrategyService {

	SearchResult list(Page page, Map<String, Object> condition);

	int insertSelective(ConfigWarnStrategy configWarnStrategy);

	int updateByPrimaryKeySelective(ConfigWarnStrategy configWarnStrategy);

	int deleteByPrimaryKey(String id);

	int updateStatus(String id, String enabled, String updateTime);

}
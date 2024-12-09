package net.sinodata.business.service;

import java.util.Map;

import net.sinodata.business.entity.ConfigAccessTimeout;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface ConfigAccessTimeoutService {

	SearchResult list(Page page, Map<String, Object> condition);

	int insertSelective(ConfigAccessTimeout configAccessTimeout);

	int updateByPrimaryKeySelective(ConfigAccessTimeout configAccessTimeout);

	int deleteByPrimaryKey(String id);

	int updateStatus(String id, String enabled, String updateTime);

}
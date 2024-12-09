package net.sinodata.business.service;

import java.util.Map;

import net.sinodata.business.entity.ConfigAccessTraffic;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface ConfigAccessTrafficService {

	SearchResult list(Page page, Map<String, Object> condition);

	int insertSelective(ConfigAccessTraffic configAccessTraffic);

	int updateByPrimaryKeySelective(ConfigAccessTraffic configAccessTraffic);

	int deleteByPrimaryKey(String id);

	int updateStatus(String id, String enabled, String updateTime);

}
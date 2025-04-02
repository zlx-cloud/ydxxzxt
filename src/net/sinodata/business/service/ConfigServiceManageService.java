package net.sinodata.business.service;

import java.util.Map;

import net.sinodata.business.entity.ConfigServiceManage;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface ConfigServiceManageService {

	SearchResult list(Page page, Map<String, Object> condition);

	int insertSelective(ConfigServiceManage configServiceManage);

	int updateByPrimaryKeySelective(ConfigServiceManage configServiceManage);

	int deleteByPrimaryKey(String id);

	int updateStatus(String id, String enabled, String updateTime);

}
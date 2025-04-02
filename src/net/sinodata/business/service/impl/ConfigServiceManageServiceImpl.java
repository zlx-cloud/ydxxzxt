package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sinodata.business.dao.ConfigServiceManageDao;
import net.sinodata.business.entity.ConfigServiceManage;
import net.sinodata.business.service.ConfigServiceManageService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

@Service
public class ConfigServiceManageServiceImpl implements ConfigServiceManageService {

	@Autowired
	private ConfigServiceManageDao configServiceManageDao;

	@Override
	public SearchResult list(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<ConfigServiceManage> data = configServiceManageDao.queryListByPage(condition);
		int count = configServiceManageDao.queryListCountByPage(condition);
		return new SearchResult(data, count);
	}

	@Override
	public int insertSelective(ConfigServiceManage configServiceManage) {
		return configServiceManageDao.insertSelective(configServiceManage);
	}

	@Override
	public int updateByPrimaryKeySelective(ConfigServiceManage configServiceManage) {
		return configServiceManageDao.updateByPrimaryKeySelective(configServiceManage);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return configServiceManageDao.deleteByPrimaryKey(id);
	}

	@Override
	public int updateStatus(String id, String enabled, String updateTime) {
		return configServiceManageDao.updateStatus(id, enabled, updateTime);
	}

}
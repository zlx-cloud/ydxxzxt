package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sinodata.business.dao.ConfigAccessTimeoutDao;
import net.sinodata.business.entity.ConfigAccessTimeout;
import net.sinodata.business.service.ConfigAccessTimeoutService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

@Service
public class ConfigAccessTimeoutServiceImpl implements ConfigAccessTimeoutService {

	@Autowired
	private ConfigAccessTimeoutDao configAccessTimeoutDao;

	@Override
	public SearchResult list(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<ConfigAccessTimeout> data = configAccessTimeoutDao.queryListByPage(condition);
		int count = configAccessTimeoutDao.queryListCountByPage(condition);
		return new SearchResult(data, count);
	}

	@Override
	public int insertSelective(ConfigAccessTimeout configAccessTimeout) {
		return configAccessTimeoutDao.insertSelective(configAccessTimeout);
	}

	@Override
	public int updateByPrimaryKeySelective(ConfigAccessTimeout configAccessTimeout) {
		return configAccessTimeoutDao.updateByPrimaryKeySelective(configAccessTimeout);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return configAccessTimeoutDao.deleteByPrimaryKey(id);
	}

	@Override
	public int updateStatus(String id, String enabled, String updateTime) {
		return configAccessTimeoutDao.updateStatus(id, enabled, updateTime);
	}

}
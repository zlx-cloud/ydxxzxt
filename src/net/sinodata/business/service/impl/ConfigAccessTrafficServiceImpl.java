package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sinodata.business.dao.ConfigAccessTrafficDao;
import net.sinodata.business.entity.ConfigAccessTraffic;
import net.sinodata.business.service.ConfigAccessTrafficService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

@Service
public class ConfigAccessTrafficServiceImpl implements ConfigAccessTrafficService {

	@Autowired
	private ConfigAccessTrafficDao configAccessTrafficDao;

	@Override
	public SearchResult list(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<ConfigAccessTraffic> data = configAccessTrafficDao.queryListByPage(condition);
		int count = configAccessTrafficDao.queryListCountByPage(condition);
		return new SearchResult(data, count);
	}

	@Override
	public int insertSelective(ConfigAccessTraffic configAccessTraffic) {
		return configAccessTrafficDao.insertSelective(configAccessTraffic);
	}

	@Override
	public int updateByPrimaryKeySelective(ConfigAccessTraffic configAccessTraffic) {
		return configAccessTrafficDao.updateByPrimaryKeySelective(configAccessTraffic);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return configAccessTrafficDao.deleteByPrimaryKey(id);
	}

	@Override
	public int updateStatus(String id, String enabled, String updateTime) {
		return configAccessTrafficDao.updateStatus(id, enabled, updateTime);
	}

}
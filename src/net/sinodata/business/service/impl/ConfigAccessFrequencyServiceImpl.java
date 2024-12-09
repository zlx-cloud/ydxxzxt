package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sinodata.business.dao.ConfigAccessFrequencyDao;
import net.sinodata.business.entity.ConfigAccessFrequency;
import net.sinodata.business.service.ConfigAccessFrequencyService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

@Service
public class ConfigAccessFrequencyServiceImpl implements ConfigAccessFrequencyService {

	@Autowired
	private ConfigAccessFrequencyDao configAccessFrequencyDao;

	@Override
	public SearchResult list(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<ConfigAccessFrequency> data = configAccessFrequencyDao.queryListByPage(condition);
		int count = configAccessFrequencyDao.queryListCountByPage(condition);
		return new SearchResult(data, count);
	}

	@Override
	public int insertSelective(ConfigAccessFrequency configAccessFrequency) {
		return configAccessFrequencyDao.insertSelective(configAccessFrequency);
	}

	@Override
	public int updateByPrimaryKeySelective(ConfigAccessFrequency configAccessFrequency) {
		return configAccessFrequencyDao.updateByPrimaryKeySelective(configAccessFrequency);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return configAccessFrequencyDao.deleteByPrimaryKey(id);
	}

	@Override
	public int updateStatus(String id, String enabled, String updateTime) {
		return configAccessFrequencyDao.updateStatus(id, enabled, updateTime);
	}

}
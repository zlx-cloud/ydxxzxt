package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sinodata.business.dao.ConfigWarnStrategyDao;
import net.sinodata.business.entity.ConfigWarnStrategy;
import net.sinodata.business.service.ConfigWarnStrategyService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

@Service
public class ConfigWarnStrategyServiceImpl implements ConfigWarnStrategyService {

	@Autowired
	private ConfigWarnStrategyDao configWarnStrategyDao;

	@Override
	public SearchResult list(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<ConfigWarnStrategy> data = configWarnStrategyDao.queryListByPage(condition);
		int count = configWarnStrategyDao.queryListCountByPage(condition);
		return new SearchResult(data, count);
	}

	@Override
	public int insertSelective(ConfigWarnStrategy configWarnStrategy) {
		return configWarnStrategyDao.insertSelective(configWarnStrategy);
	}

	@Override
	public int updateByPrimaryKeySelective(ConfigWarnStrategy configWarnStrategy) {
		return configWarnStrategyDao.updateByPrimaryKeySelective(configWarnStrategy);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return configWarnStrategyDao.deleteByPrimaryKey(id);
	}

	@Override
	public int updateStatus(String id, String enabled, String updateTime) {
		return configWarnStrategyDao.updateStatus(id, enabled, updateTime);
	}

}
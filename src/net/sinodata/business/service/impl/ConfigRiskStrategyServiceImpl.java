package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sinodata.business.dao.ConfigRiskStrategyDao;
import net.sinodata.business.entity.ConfigRiskStrategy;
import net.sinodata.business.service.ConfigRiskStrategyService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

@Service
public class ConfigRiskStrategyServiceImpl implements ConfigRiskStrategyService {

	@Autowired
	private ConfigRiskStrategyDao configRiskStrategyDao;

	@Override
	public SearchResult list(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<ConfigRiskStrategy> data = configRiskStrategyDao.queryListByPage(condition);
		for (ConfigRiskStrategy rs : data) {
			String[] riskArr = rs.getRiskMsg().split(";");
			if ("FAIL".equals(rs.getRiskType())) {
				rs.setF0(riskArr[0].split(":")[1]);
				rs.setF1(riskArr[1].split(":")[1]);
				rs.setF2(riskArr[2].split(":")[1]);
			} else if ("RT".equals(rs.getRiskType())) {
				rs.setRt0(riskArr[0].split(":")[1]);
				rs.setRt1(riskArr[1].split(":")[1]);
				rs.setRt2(riskArr[2].split(":")[1]);
			}

		}
		int count = configRiskStrategyDao.queryListCountByPage(condition);
		return new SearchResult(data, count);
	}

	@Override
	public int insertSelective(ConfigRiskStrategy configRiskStrategy) {
		return configRiskStrategyDao.insertSelective(configRiskStrategy);
	}

	@Override
	public int updateByPrimaryKeySelective(ConfigRiskStrategy configRiskStrategy) {
		return configRiskStrategyDao.updateByPrimaryKeySelective(configRiskStrategy);
	}

	@Override
	public int deleteByPrimaryKey(String id) {
		return configRiskStrategyDao.deleteByPrimaryKey(id);
	}

	@Override
	public int updateStatus(String id, String enabled, String updateTime) {
		return configRiskStrategyDao.updateStatus(id, enabled, updateTime);
	}

}
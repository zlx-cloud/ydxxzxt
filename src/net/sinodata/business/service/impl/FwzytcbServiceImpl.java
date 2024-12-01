package net.sinodata.business.service.impl;

import net.sinodata.business.dao.FwzytcbDao;
import net.sinodata.business.entity.Fwzytcb;
import net.sinodata.business.service.FwzytcbService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FwzytcbServiceImpl implements FwzytcbService {
	@Autowired
	private FwzytcbDao fwzytcbDao;

	@Override
	public int insertSelective(Fwzytcb record) {
		return fwzytcbDao.insertSelective(record);
	}

	@Override
	public int updateByPrimaryKeySelective(Fwzytcb record) {
		return fwzytcbDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public SearchResult fwzytcbList(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Fwzytcb> data = fwzytcbDao.queryTcList(condition);
		int count = fwzytcbDao.getFwzytcbCountByPage(condition);
		return new SearchResult(data, count);
	}

	@Override
	public List<Fwzytcb> queryTcList(Map<String, Object> condition) {
		return fwzytcbDao.queryTcList(condition);
	}

	@Override
	public int getFwzytcbCountByPage(Map<String, Object> condition) {
		return fwzytcbDao.getFwzytcbCountByPage(condition);
	}
}

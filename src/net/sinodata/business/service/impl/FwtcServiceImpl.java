package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import net.sinodata.business.dao.FwtcDao;
import net.sinodata.business.entity.Fwtc;
import net.sinodata.business.service.FwtcService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FwtcServiceImpl implements FwtcService {
	@Autowired
	private FwtcDao fwtcDao;
	@Override
	public int getFwtchisCountByPage(Map<String, Object> condition) {
		return fwtcDao.getFwtchisCountByPage( condition);
	}

	@Override
	public SearchResult list(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Fwtc> data=fwtcDao.getFwtchisListByPage( condition);
		int  count=	fwtcDao.getFwtchisCountByPage( condition);
		return new SearchResult(data,count);
	}

	@Override
	public String queryFwqqcs(String fwbs) {
		return fwtcDao.queryFwqqcs(fwbs);
	}

	@Override
	public String queryFwtgzbs(String fwbs) {
		return fwtcDao.queryFwtgzbs(fwbs);
	}

	@Override
	public List<Map<String, Object>> queryFw() {
		return fwtcDao.queryFw();
	}

	@Override
	public List<Map<String, Object>> queryFf(String fwbs) {
		return fwtcDao.queryFf(fwbs);
	}
	
}

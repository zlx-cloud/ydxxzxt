package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sinodata.business.dao.FwzyqqbwyccjbDao;
import net.sinodata.business.entity.Fwzyqqbwyccjb;
import net.sinodata.business.entity.FwzyqqbwyccjbDownload;
import net.sinodata.business.service.FwzyqqbwyccjbService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

@Service
public class FwzyqqbwyccjbServiceImpl implements FwzyqqbwyccjbService {

	@Autowired
	private FwzyqqbwyccjbDao fwzyqqbwyccjbDao;

	@Override
	public SearchResult getList(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Fwzyqqbwyccjb> list = fwzyqqbwyccjbDao.qyertList(condition);
		int count = fwzyqqbwyccjbDao.qyertListCount(condition);
		return new SearchResult(list, count);
	}

	@Override
	public List<FwzyqqbwyccjbDownload> getYcList(Map<String, Object> condition) {
		return fwzyqqbwyccjbDao.getYcList(condition);
	}

}
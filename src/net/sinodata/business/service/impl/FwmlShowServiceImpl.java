package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sinodata.business.dao.FwmlShowDao;
import net.sinodata.business.service.FwmlShowService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

@Service
public class FwmlShowServiceImpl implements FwmlShowService {

	@Autowired
	private FwmlShowDao dao;

	@Override
	public SearchResult list(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<T> data = dao.getListByPage(condition);
		int count = dao.getCountByPage(condition);
		return new SearchResult(data, count);
	}

	@Override
	public List<Map<String, Object>> listJzInfo() {
		return dao.listJzInfo();
	}

}
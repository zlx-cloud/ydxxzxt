package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sinodata.business.dao.SqxxShowDao;
import net.sinodata.business.entity.SqxxShow;
import net.sinodata.business.service.SqxxShowService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

@Service
public class SqxxShowServiceImpl implements SqxxShowService {

	@Autowired
	private SqxxShowDao dao;

	@Override
	public SearchResult list(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<SqxxShow> data = dao.getListByPage(condition);
		int count = dao.getCountByPage(condition);
		return new SearchResult(data, count);
	}

}
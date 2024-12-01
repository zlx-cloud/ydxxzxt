package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sinodata.business.dao.AuthAplDao;
import net.sinodata.business.service.AuthAplService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

@Service
public class AuthAplServiceImpl implements AuthAplService {

	@Autowired
	private AuthAplDao authAplDao;

	@Override
	public SearchResult listAuthApl(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<T> data = authAplDao.getListByPage(condition);
		int count = authAplDao.getCountByPage(condition);
		return new SearchResult(data, count);
	}

	@Override
	public SearchResult listFwFf(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<T> data = authAplDao.getFwFfListByPage(condition);
		int count = authAplDao.getFwFfCountByPage(condition);
		return new SearchResult(data, count);
	}

	@Override
	public int addAuthApl(Map<String, Object> map) {
		return authAplDao.addAuthApl(map);
	}

	@Override
	public Map<String, Object> getPicInfo(String ID) {
		return authAplDao.getPicInfo(ID);
	}

	@Override
	public SearchResult listSh(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<T> data = authAplDao.getShListByPage(condition);
		int count = authAplDao.getShCountByPage(condition);
		return new SearchResult(data, count);
	}

	@Override
	public int updateSqzt(String ID, String ZT) {
		return authAplDao.updateSqzt(ID, ZT);
	}

	@Override
	public int addFwzysq(String YYBS, String FWBS, String FFBS) {
		return authAplDao.addFwzysq(YYBS, FWBS, FFBS);
	}

}
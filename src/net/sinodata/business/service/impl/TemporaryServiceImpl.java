package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sinodata.business.dao.TemporaryDao;
import net.sinodata.business.service.TemporaryService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

@Service
public class TemporaryServiceImpl implements TemporaryService {

	@Autowired
	private TemporaryDao temporaryDao;

	@Override
	public SearchResult warnCountList(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Map<String, Object>> data = temporaryDao.getWarnCountListByPage(condition);
		int count = temporaryDao.getWarnCountNumByPage(condition);
		return new SearchResult(data, count);
	}

	@Override
	public SearchResult yjsjcxList(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Map<String, Object>> data = temporaryDao.getYjxxListByPage(condition);
		int count = temporaryDao.getYjxxCountByPage(condition);
		return new SearchResult(data, count);
	}

	@Override
	public SearchResult yjsjcxDetail(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Map<String, Object>> data = temporaryDao.yjsjcxDetailPage(condition);
		int count = temporaryDao.yjsjcxDetailCount(condition);
		return new SearchResult(data, count);
	}

	@Override
	public SearchResult yhqqpmfxList(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Map<String, Object>> data = temporaryDao.getYhqqpmfxListByPage(condition);
		int count = temporaryDao.getYhqqpmfxCountByPage(condition);
		return new SearchResult(data, count);
	}

	@Override
	public SearchResult fwzlfxList(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Map<String, Object>> data = temporaryDao.getFwzlfxListByPage(condition);
		int count = temporaryDao.getFwzlfxCountByPage(condition);
		return new SearchResult(data, count);
	}

	@Override
	public SearchResult yjxxcxList(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Map<String, Object>> data = temporaryDao.getYjxxcxListByPage(condition);
		int count = temporaryDao.getYjxxcxNumByPage(condition);
		return new SearchResult(data, count);
	}

	@Override
	public SearchResult jyqqpmfxList(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Map<String, Object>> data = temporaryDao.getJyqqpmfxListByPage(condition);
		int count = temporaryDao.getJyqqpmfxNumByPage(condition);
		return new SearchResult(data, count);
	}

}
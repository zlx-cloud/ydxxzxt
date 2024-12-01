package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sinodata.business.dao.FwEventLogDao;
import net.sinodata.business.entity.FwEventLog;
import net.sinodata.business.service.FwEventLogService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

@Service
public class FwEventLogServiceImpl implements FwEventLogService {

	@Autowired
	private FwEventLogDao fwEventLogDao;
	
	@Override
	public SearchResult logList(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<FwEventLog> logList = fwEventLogDao.queryLogList(condition);
		int count = fwEventLogDao.queryLogListCount(condition);
		return new SearchResult(logList,count);
	}

	@Override
	public SearchResult sqlAreaList(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Map<String,Object>> list = fwEventLogDao.sqlAreaList(condition);
		int count = fwEventLogDao.sqlAreaCount(condition);
		return new SearchResult(list,count);
	}

	@Override
	public String lookSql(String sqlId) {
		return fwEventLogDao.lookSql(sqlId);
	}

}
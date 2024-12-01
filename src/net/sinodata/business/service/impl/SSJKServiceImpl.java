package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import net.sinodata.business.dao.quartzJob.SSJKDao;
import net.sinodata.business.service.SSJKService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SSJKServiceImpl implements SSJKService {
	@Autowired(required = false)
	private SSJKDao SSJKDao;

	@Override
	public SearchResult InPortPageList(Page page, Map<String, Object> condition) {
		// TODO Auto-generated method stub
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Map<String, Object>> data = SSJKDao.queryInPortTable(condition);
		int count = SSJKDao.queryInPortCount();
		return new SearchResult(data, count);
	}

	@Override
	public SearchResult PageIntePortList(Page page,
			Map<String, Object> condition) {
		// TODO Auto-generated method stub
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Map<String, Object>> data = SSJKDao.queryByIPTable(condition);
		int count = SSJKDao.queryCountByIP(condition);
		return new SearchResult(data, count);
	}

	
	
	
	
	
	

	@Override
	public List<Map<String, Object>> queryInPort() {
		// TODO Auto-generated method stub
		return SSJKDao.queryInPort();
	}

	@Override
	public List<Map<String, Object>> queryByIP(String taskid,
			String task_exe_time) {
		// TODO Auto-generated method stub
		return SSJKDao.queryByIP(taskid, task_exe_time);
	}

	@Override
	public List<Map<String, String>> queryIpPort() {
		// TODO Auto-generated method stub
		return SSJKDao.queryIpPort();
	}

	@Override
	public Integer selectQqfwCount(String startTime, String endTime) {
		return SSJKDao.selectQqfwCount(startTime,endTime);
	}

	@Override
	public Integer selectYcCount(String startTime, String endTime) {
		return SSJKDao.selectYcCount(startTime,endTime);
	}

	@Override
	public Integer selectZxTime(String startTime, String endTime) {
		return SSJKDao.selectZxTime(startTime,endTime);
	}

}
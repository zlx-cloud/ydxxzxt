package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sinodata.business.dao.FwzyxzbDao;
import net.sinodata.business.entity.Fwzyxzb;
import net.sinodata.business.service.FwzyxzbService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

@Service
public class FwzyxzbServiceImpl implements FwzyxzbService {
	@Autowired
	private FwzyxzbDao fwzyxzbDao;

	@Override
	public SearchResult xzList(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Fwzyxzb> xzList = fwzyxzbDao.queryList(condition);
		int count = fwzyxzbDao.queryListCount(condition);
		return new SearchResult(xzList,count);
	}

	@Override
	public int updateByPrimaryKeySelective(Fwzyxzb record) {
		return fwzyxzbDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(Fwzyxzb record) {
		return fwzyxzbDao.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(String fwbs) {
		return fwzyxzbDao.deleteByPrimaryKey(fwbs);
	}

	@Override
	public List<Map<String, Object>> queryFwbsByXzb() {
		return fwzyxzbDao.queryFwbsByXzb();
	}

	@Override
	public List<Map<String, Object>> queryFwbsByZcb() {
		return fwzyxzbDao.queryFwbsByZcb();
	}

}

package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sinodata.business.dao.FwzytdjkxzDao;
import net.sinodata.business.entity.Fwzytdjkxz;
import net.sinodata.business.service.FwzytdjkxzService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

@Service
public class FwzytdjkxzServiceImpl implements FwzytdjkxzService {
	@Autowired
	private FwzytdjkxzDao fwzytdjkxzDao;
	
	@Override
	public SearchResult list(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Fwzytdjkxz> list = fwzytdjkxzDao.queryList(condition);
		int count = fwzytdjkxzDao.queryListCount(condition);
		return new SearchResult(list,count);
	}

	@Override
	public int updateByPrimaryKey(Fwzytdjkxz record) {
		return fwzytdjkxzDao.updateByPrimaryKey(record);
	}

	@Override
	public int insertSelective(Fwzytdjkxz record) {
		return fwzytdjkxzDao.insertSelective(record);
	}

	@Override
	public int deleteByPrimaryKey(String wyid) {
		return fwzytdjkxzDao.deleteByPrimaryKey(wyid);
	}


}

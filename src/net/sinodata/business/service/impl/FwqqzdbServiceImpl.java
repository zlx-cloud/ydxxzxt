package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sinodata.business.dao.FwqqzdbDao;
import net.sinodata.business.entity.Fwqqzdb;
import net.sinodata.business.service.FwqqzdbService;
import net.sinodata.business.util.SearchResult;

@Service
public class FwqqzdbServiceImpl implements FwqqzdbService {
	
	@Autowired
	private FwqqzdbDao fwqqzdbDao;

	@Override
	public SearchResult getList() {
		List<Fwqqzdb> list = fwqqzdbDao.getList(null);
		SearchResult result = new SearchResult(list,list!=null?list.size():0);
		return result;
	}

	@Override
	public void updateFwqqzdb(Fwqqzdb fwqqzdb) {
		fwqqzdbDao.updateFwqqzdb(fwqqzdb);

	}
	
	public List<Fwqqzdb> getCheckList(){
		List<Fwqqzdb> list = fwqqzdbDao.getList("1");
		return list;
	}

	@Override
	public List<Map<String, Object>> getResourceList() {
		List<Map<String,Object>> list = fwqqzdbDao.getResourceList();
		return list;
	}

}

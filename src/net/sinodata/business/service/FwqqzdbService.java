package net.sinodata.business.service;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwqqzdb;
import net.sinodata.business.util.SearchResult;

public interface FwqqzdbService {

	public SearchResult getList();
	
	public void updateFwqqzdb(Fwqqzdb fwqqzdb);
	
	public List<Fwqqzdb> getCheckList();
	
	public List<Map<String,Object>> getResourceList();
}

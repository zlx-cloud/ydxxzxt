package net.sinodata.business.service;

import java.util.Map;

import net.sinodata.business.entity.Fwzytdjkxz;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface FwzytdjkxzService {

	public SearchResult list(Page page, Map<String, Object> condition);
	
	int updateByPrimaryKey(Fwzytdjkxz record);
	
	int insertSelective(Fwzytdjkxz record);
	
	int deleteByPrimaryKey(String wyid);
	
}

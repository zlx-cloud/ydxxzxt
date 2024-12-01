package net.sinodata.business.service;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwzyxzb;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface FwzyxzbService {

	public SearchResult xzList(Page page, Map<String, Object> condition);
	
	int updateByPrimaryKeySelective(Fwzyxzb record);
	
	int insertSelective(Fwzyxzb record);
	
	int deleteByPrimaryKey(String fwbs);
	
	public List<Map<String,Object>> queryFwbsByXzb();
	
	public List<Map<String,Object>> queryFwbsByZcb();
}

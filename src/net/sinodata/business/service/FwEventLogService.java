package net.sinodata.business.service;

import java.util.Map;

import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface FwEventLogService {

	public SearchResult logList(Page page, Map<String, Object> condition);
	
	public SearchResult sqlAreaList(Page page, Map<String, Object> condition);
	
	String lookSql(String sqlId);
	
}

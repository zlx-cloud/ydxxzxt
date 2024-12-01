package net.sinodata.business.service;

import java.util.Map;

import net.sf.json.JSONArray;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface FwStreamService {

	public JSONArray fwOrgList();
	
	public SearchResult streamList(Page page, Map<String, Object> condition);
	
	public SearchResult streamDataList(Page page, Map<String, Object> condition);
	
	public boolean getEsbStatus(String streamId);
	
	public String startEsb(String streamId);
	
	public String stopEsb(String streamId);
}

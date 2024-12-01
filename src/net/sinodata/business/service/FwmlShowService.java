package net.sinodata.business.service;

import java.util.List;
import java.util.Map;

import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface FwmlShowService {

	public SearchResult list(Page page, Map<String, Object> condition);

	List<Map<String, Object>> listJzInfo();

}
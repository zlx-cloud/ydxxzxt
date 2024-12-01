package net.sinodata.business.service;

import java.util.Map;

import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface SjtsfwService {

	public SearchResult list(Page page, Map<String, Object> condition);

}
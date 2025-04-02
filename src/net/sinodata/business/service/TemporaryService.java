package net.sinodata.business.service;

import java.util.Map;

import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface TemporaryService {

	SearchResult warnCountList(Page page, Map<String, Object> condition);

	SearchResult yjsjcxList(Page page, Map<String, Object> condition);

	SearchResult yjsjcxDetail(Page page, Map<String, Object> condition);
	
	SearchResult yhqqpmfxList(Page page, Map<String, Object> condition);
	
	SearchResult fwzlfxList(Page page, Map<String, Object> condition);
	
	SearchResult yjxxcxList(Page page, Map<String, Object> condition);

}
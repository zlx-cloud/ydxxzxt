package net.sinodata.business.service;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.FwzyqqbwyccjbDownload;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface FwzyqqbwyccjbService {

	public SearchResult getList(Page page, Map<String, Object> condition);

	public List<FwzyqqbwyccjbDownload> getYcList(Map<String, Object> condition);

}
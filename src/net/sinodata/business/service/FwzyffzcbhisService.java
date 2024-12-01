package net.sinodata.business.service;

import java.util.Map;

import net.sinodata.business.entity.Fwzyffzcbhis;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface FwzyffzcbhisService {

	 int deleteByPrimaryKey(String optId);

	    int insert(Fwzyffzcbhis record);

	    int insertSelective(Fwzyffzcbhis record);

	    Fwzyffzcbhis selectByPrimaryKey(String optId);

	    int updateByPrimaryKeySelective(Fwzyffzcbhis record);

	    int updateByPrimaryKey(Fwzyffzcbhis record);
	    public SearchResult List(Page page,
				Map<String, Object> condition);
}

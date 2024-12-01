package net.sinodata.business.service;

import java.util.Map;

import net.sinodata.business.entity.Fwzyffqqcsbhis;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface FwzyffqqcsbhisService {

	 int deleteByPrimaryKey(String optId);

	    int insert(Fwzyffqqcsbhis record);

	    int insertSelective(Fwzyffqqcsbhis record);

	    Fwzyffqqcsbhis selectByPrimaryKey(String optId);

	    int updateByPrimaryKeySelective(Fwzyffqqcsbhis record);

	    int updateByPrimaryKey(Fwzyffqqcsbhis record);
	    public SearchResult List(Page page,
				Map<String, Object> condition);
}

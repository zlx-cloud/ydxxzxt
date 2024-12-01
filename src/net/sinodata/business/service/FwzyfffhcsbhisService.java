package net.sinodata.business.service;

import java.util.Map;

import net.sinodata.business.entity.Fwzyfffhcsbhis;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface FwzyfffhcsbhisService {

	int deleteByPrimaryKey(String optId);

    int insert(Fwzyfffhcsbhis record);

    int insertSelective(Fwzyfffhcsbhis record);

    Fwzyfffhcsbhis selectByPrimaryKey(String optId);

    int updateByPrimaryKeySelective(Fwzyfffhcsbhis record);

    int updateByPrimaryKey(Fwzyfffhcsbhis record);
    
    public SearchResult List(Page page,
			Map<String, Object> condition);
}

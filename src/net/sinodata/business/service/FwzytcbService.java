package net.sinodata.business.service;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwzytcb;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface FwzytcbService{

    int insertSelective(Fwzytcb record);

    int updateByPrimaryKeySelective(Fwzytcb record);
    
	List<Fwzytcb> queryTcList( Map<String, Object> condition);
	int getFwzytcbCountByPage( Map<String, Object> condition);
	
	public SearchResult fwzytcbList(Page page,
			Map<String, Object> condition);
}

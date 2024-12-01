package net.sinodata.business.service;

import java.util.Map;

import net.sinodata.business.entity.FwzyzcbReg;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface FwzyzcbRegService {

	 int deleteByPrimaryKey(String fwbs);

	    int insert(FwzyzcbReg record);

	    int insertSelective(FwzyzcbReg record);

	    FwzyzcbReg selectByPrimaryKey(String fwbs);

	    int updateByPrimaryKeySelective(FwzyzcbReg record);

	    int updateByPrimaryKey(FwzyzcbReg record);
	    
	    public SearchResult fwzyzcbRegList(Page page,
				Map<String, Object> condition);
	    
	    int insertTcb( Map<String, Object> map);
}

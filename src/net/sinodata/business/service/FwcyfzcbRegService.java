package net.sinodata.business.service;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.FwcyfzcbReg;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface FwcyfzcbRegService {

	int insert(FwcyfzcbReg record);

    int insertSelective(FwcyfzcbReg record);
    
List<FwcyfzcbReg> getUserListByPage( Map<String, Object> condition);
	
	int getUserCountByPage( Map<String, Object> condition);
	

	FwcyfzcbReg selectByPrimaryKey(String fwcyfYyxtbh);
    int deleteByPrimaryKey(String fwcyfYyxtbh);
    
    public SearchResult userRegList(Page page,
			Map<String, Object> condition);
}

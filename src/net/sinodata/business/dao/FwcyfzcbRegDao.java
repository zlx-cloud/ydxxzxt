package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.FwcyfzcbReg;

public interface FwcyfzcbRegDao {
    int insert(FwcyfzcbReg record);

    int insertSelective(FwcyfzcbReg record);
    
    List<FwcyfzcbReg> getUserListByPage( Map<String, Object> condition);
	
	int getUserCountByPage( Map<String, Object> condition);
	

	FwcyfzcbReg selectByPrimaryKey(String fwcyfYyxtbh);
    int deleteByPrimaryKey(String fwcyfYyxtbh);
}
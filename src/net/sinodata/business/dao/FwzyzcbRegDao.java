package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwzyzcb;
import net.sinodata.business.entity.FwzyzcbReg;

public interface FwzyzcbRegDao {
    int deleteByPrimaryKey(String fwbs);

    int insert(FwzyzcbReg record);

    int insertSelective(FwzyzcbReg record);

    FwzyzcbReg selectByPrimaryKey(String fwbs);

    int updateByPrimaryKeySelective(FwzyzcbReg record);

    int updateByPrimaryKey(FwzyzcbReg record);
    
    List<Fwzyzcb> getFwzyzcbRegListByPage( Map<String, Object> condition);
	
   	int getFwzyzcbRegCountByPage( Map<String, Object> condition);
   	
    int insertTcb( Map<String, Object> map);
}
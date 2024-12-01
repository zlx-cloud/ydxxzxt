package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwzyffzcbhis;

public interface FwzyffzcbhisDao {
    int deleteByPrimaryKey(String optId);

    int insert(Fwzyffzcbhis record);

    int insertSelective(Fwzyffzcbhis record);

    Fwzyffzcbhis selectByPrimaryKey(String optId);

    int updateByPrimaryKeySelective(Fwzyffzcbhis record);

    int updateByPrimaryKey(Fwzyffzcbhis record);
    
    List<Fwzyffzcbhis> getFwzyffHisListByPage(Map<String, Object> condition);
    int getFwzyffHisCountByPage( Map<String, Object> condition);
}
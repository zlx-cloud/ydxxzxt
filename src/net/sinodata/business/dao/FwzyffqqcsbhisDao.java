package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwzyffqqcsbhis;

public interface FwzyffqqcsbhisDao {
    int deleteByPrimaryKey(String optId);

    int insert(Fwzyffqqcsbhis record);

    int insertSelective(Fwzyffqqcsbhis record);

    Fwzyffqqcsbhis selectByPrimaryKey(String optId);

    int updateByPrimaryKeySelective(Fwzyffqqcsbhis record);

    int updateByPrimaryKey(Fwzyffqqcsbhis record);
    
    List<Fwzyffqqcsbhis> getFwzyffqqcsHisListByPage(Map<String, Object> condition);
    int getFwzyffqqcsHisCountByPage( Map<String, Object> condition);
}
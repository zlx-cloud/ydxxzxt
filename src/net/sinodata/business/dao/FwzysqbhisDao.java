package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwzysqbhis;
import net.sinodata.business.entity.FwzysqbhisKey;

public interface FwzysqbhisDao {
    int deleteByPrimaryKey(FwzysqbhisKey key);

    int insert(Fwzysqbhis record);

    int insertSelective(Fwzysqbhis record);

    Fwzysqbhis selectByPrimaryKey(FwzysqbhisKey key);

    int updateByPrimaryKeySelective(Fwzysqbhis record);

    int updateByPrimaryKey(Fwzysqbhis record);
    
    List<Fwzysqbhis> getFwzysqbhisListByPage(Map<String, Object> condition);
    int getFwzysqbhisCountByPage( Map<String, Object> condition);
}
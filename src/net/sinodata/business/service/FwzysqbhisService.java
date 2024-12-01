package net.sinodata.business.service;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwzysqbhis;
import net.sinodata.business.entity.FwzysqbhisKey;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;




public interface FwzysqbhisService{

	
    int deleteByPrimaryKey(FwzysqbhisKey key);

    int insert(Fwzysqbhis record);

    int insertSelective(Fwzysqbhis record);

    Fwzysqbhis selectByPrimaryKey(FwzysqbhisKey key);

    int updateByPrimaryKeySelective(Fwzysqbhis record);

    int updateByPrimaryKey(Fwzysqbhis record);
    
    public SearchResult List(Page page,
			Map<String, Object> condition);
    List<Fwzysqbhis> getFwzysqbhisListByPage(Map<String, Object> condition);
    int getFwzysqbhisCountByPage( Map<String, Object> condition);
}

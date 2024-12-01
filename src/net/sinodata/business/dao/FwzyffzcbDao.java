package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwzyffzcb;
import net.sinodata.business.entity.FwzyffzcbKey;
import net.sinodata.business.entity.YyFwFfInfo;

public interface FwzyffzcbDao {
    int deleteByPrimaryKey(FwzyffzcbKey key);

    int insert(Fwzyffzcb record);

    int insertSelective(Fwzyffzcb record);

    Fwzyffzcb selectByPrimaryKey(FwzyffzcbKey key);

    int updateByPrimaryKeySelective(Fwzyffzcb record);

    int updateByPrimaryKey(Fwzyffzcb record);
    
    int insertByBatch(List<Fwzyffzcb> Fwzyffzcb);
    List<Fwzyffzcb> queryListByFwbs(String fwbs);
    
    List<Fwzyffzcb> getFwzyffListByPage(Map<String, Object> condition);
    int getFwzyffCountByPage( Map<String, Object> condition);
    
    List<YyFwFfInfo> getYyFwFfListByPage(Map<String, Object> condition);
    int getYyFwFfInfoCountByPage( Map<String, Object> condition);
    
    String queryFFBSbyFwbs(String fwbs);
    
    String queryURLbyFwbs(Map<String, Object> map);
    
    String queryFFLXbyFwbs(Map<String, Object> map);
    
    Fwzyffzcb selectByFwbsFfbs(Map<String, Object> map);
    
    List<Map<String,Object>> getFfList(Map<String, Object> map);
}
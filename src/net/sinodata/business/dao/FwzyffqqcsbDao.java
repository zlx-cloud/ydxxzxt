package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwzyffqqcsb;
import net.sinodata.business.entity.FwzyffqqcsbKey;

public interface FwzyffqqcsbDao {
    int deleteByPrimaryKey(FwzyffqqcsbKey key);

    int insert(Fwzyffqqcsb record);

    int insertSelective(Fwzyffqqcsb record);

    Fwzyffqqcsb selectByPrimaryKey(FwzyffqqcsbKey key);

    int updateByPrimaryKeySelective(Fwzyffqqcsb record);

    int updateByPrimaryKey(Fwzyffqqcsb record);
    int insertByBatch(List<Fwzyffqqcsb> record);
    List<Fwzyffqqcsb> getFwzyffqqcsListByPage(Map<String, Object> condition);
    int getFwzyffqqcsCountByPage( Map<String, Object> condition);
    
   List<Fwzyffqqcsb> queryListByFwbsAndFfbs(FwzyffqqcsbKey key);
}
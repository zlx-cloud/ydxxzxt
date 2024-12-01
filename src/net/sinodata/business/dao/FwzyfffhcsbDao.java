package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwzyfffhcsb;
import net.sinodata.business.entity.FwzyfffhcsbKey;

public interface FwzyfffhcsbDao {
    int deleteByPrimaryKey(FwzyfffhcsbKey key);

    int insert(Fwzyfffhcsb record);

    int insertSelective(Fwzyfffhcsb record);

    Fwzyfffhcsb selectByPrimaryKey(FwzyfffhcsbKey key);

    int updateByPrimaryKeySelective(Fwzyfffhcsb record);

    int updateByPrimaryKey(Fwzyfffhcsb record);
    
    int insertByBatch(List<Fwzyfffhcsb> record);
    
    List<Fwzyfffhcsb> getFwzyfffhcsListByPage(Map<String, Object> condition);
    int getFwzyfffhcsCountByPage( Map<String, Object> condition);
    
    
   List<Fwzyfffhcsb> queryListByFfbsAndFwbs(FwzyfffhcsbKey key);
}
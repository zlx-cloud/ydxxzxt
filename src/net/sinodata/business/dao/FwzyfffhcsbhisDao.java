package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwzyfffhcsbhis;

public interface FwzyfffhcsbhisDao {
    int deleteByPrimaryKey(String optId);

    int insert(Fwzyfffhcsbhis record);

    int insertSelective(Fwzyfffhcsbhis record);

    Fwzyfffhcsbhis selectByPrimaryKey(String optId);

    int updateByPrimaryKeySelective(Fwzyfffhcsbhis record);

    int updateByPrimaryKey(Fwzyfffhcsbhis record);
    
    List<Fwzyfffhcsbhis> getFwzyfffhcsHisListByPage(Map<String, Object> condition);
    int getFwzyfffhcsHisCountByPage( Map<String, Object> condition);
}
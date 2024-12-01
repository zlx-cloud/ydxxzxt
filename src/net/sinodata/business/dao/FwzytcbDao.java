package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwzytcb;

public interface FwzytcbDao {
    int updateByPrimaryKeySelective(Fwzytcb record);
    int insertSelective(Fwzytcb record);
    List<Fwzytcb> queryTcList(Map<String, Object> condition);
    int getFwzytcbCountByPage( Map<String, Object> condition);
}
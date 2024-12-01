package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.sinodata.business.entity.Fwtc;

public interface FwtcDao {
    List<Fwtc> getFwtchisListByPage(Map<String, Object> condition);
    int getFwtchisCountByPage( Map<String, Object> condition);
    String queryFwqqcs(@Param("fwbs") String fwbs);
    String queryFwtgzbs(@Param("fwbs") String fwbs);
    
    List<Map<String,Object>> queryFw();
    
    List<Map<String,Object>> queryFf(@Param("fwbs") String fwbs);
}
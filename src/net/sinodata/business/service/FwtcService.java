package net.sinodata.business.service;

import java.util.List;
import java.util.Map;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface FwtcService{

    int getFwtchisCountByPage( Map<String, Object> condition);
    
    public SearchResult list(Page page,Map<String, Object> condition);
    
    String queryFwqqcs(String fwbs);
    
    String queryFwtgzbs(String fwbs);
    
    List<Map<String,Object>> queryFw();
    
    List<Map<String,Object>> queryFf(String fwbs);
    
}

package net.sinodata.business.service;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwzyffzcb;
import net.sinodata.business.entity.FwzyffzcbKey;
import net.sinodata.business.entity.YyFwFfInfo;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface FwzyffzcbService {

	 int deleteByPrimaryKey(FwzyffzcbKey key);

	    int insert(Fwzyffzcb record);

	    int insertSelective(Fwzyffzcb record);

	    Fwzyffzcb selectByPrimaryKey(FwzyffzcbKey key);

	    int updateByPrimaryKeySelective(Fwzyffzcb record);

	    int updateByPrimaryKey(Fwzyffzcb record);
	    
	    int insertByBatch(List<Fwzyffzcb> Fwzyffzcb);
	    List<Fwzyffzcb> queryListByFwbs(String fwbs);
	    
	    public SearchResult List(Page page,
				Map<String, Object> condition);
	    List<Fwzyffzcb> getFwzyffListByPage(Map<String, Object> condition);
	    int getFwzyffCountByPage( Map<String, Object> condition);
	    String queryFFBSbyFwbs(String fwbs);
	    
	    String queryURLbyFwbs(Map<String, Object> map);
	    
	    String queryFFLXbyFwbs(Map<String, Object> map);
	    
	    Fwzyffzcb selectByFwbsFfbs(Map<String, Object> map);
	    
	    List<Map<String,Object>> getFfList(Map<String, Object> map);

	    List<YyFwFfInfo> getYyFwFfListByPage(Map<String, Object> condition);
	    int getYyFwFfInfoCountByPage( Map<String, Object> condition);
}

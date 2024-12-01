package net.sinodata.business.service;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwcyfzcb;
import net.sinodata.business.entity.Fwzyqqbwcjb;
import net.sinodata.business.entity.FwzyqqbwcjbDownload;
import net.sinodata.business.entity.Fwzyzcb;
import net.sinodata.business.entity.QwLogDownload;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface FwzyqqbwcjbService{

	 int deleteByPrimaryKey(String qqbwbs);

	    int insert(Fwzyqqbwcjb record);

	    int insertSelective(Fwzyqqbwcjb record);

	    Fwzyqqbwcjb selectByPrimaryKey(String qqbwbs);

	    int updateByPrimaryKeySelective(Fwzyqqbwcjb record);

	    int updateByPrimaryKey(Fwzyqqbwcjb record);
	    
	    public SearchResult getList(Page page,Map<String,Object> condition);
	    
	    public List<FwzyqqbwcjbDownload> getLogList(Map<String,Object> condition);
	    
	    public List<Fwcyfzcb> selectAllFwcyfzcb();
	    
	    public List<Fwzyzcb> selectAllFwzyzcb();

    SearchResult getQwList(Page page, Map<String, Object> condition);
    List<QwLogDownload> getQwList(Map<String, Object> condition);
    
    Map<String, Object> getQw(String id);
}

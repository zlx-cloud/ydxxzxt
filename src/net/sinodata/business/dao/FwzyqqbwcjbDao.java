package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwzyqqbwcjb;
import net.sinodata.business.entity.FwzyqqbwcjbDownload;

public interface FwzyqqbwcjbDao {
    int deleteByPrimaryKey(String qqbwbs);

    int insert(Fwzyqqbwcjb record);

    int insertSelective(Fwzyqqbwcjb record);

    Fwzyqqbwcjb selectByPrimaryKey(String qqbwbs);

    int updateByPrimaryKeySelective(Fwzyqqbwcjb record);

    int updateByPrimaryKey(Fwzyqqbwcjb record);
    
    List<Fwzyqqbwcjb> queryList(Map<String,Object> condition);
    
    int queryListCount(Map<String,Object> condition);

    List<Fwzyqqbwcjb> queryQuartzList(Map<String, Object> condition);

    void insertEsLog(Map<String, Object> map);

    String selectMaxDataTime();
    
    List<FwzyqqbwcjbDownload> getLogList(Map<String, Object> condition);
}
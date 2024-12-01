package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

import net.sinodata.business.entity.Fwzyxzb;

public interface FwzyxzbDao {

	public List<Fwzyxzb> queryList(Map<String,Object> condition);
	
	public int queryListCount(Map<String,Object> condition);
	
	public Fwzyxzb selectByFwbs(@Param("fwbs") String fwbs);
	
	int insertSelective(Fwzyxzb record);
	 
	int updateByPrimaryKeySelective(Fwzyxzb record);
	
	int deleteByPrimaryKey(String fwbs);
	
	public List<Map<String,Object>> queryFwbsByXzb();
	
	public List<Map<String,Object>> queryFwbsByZcb();
}

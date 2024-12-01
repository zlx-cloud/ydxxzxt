package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;
import net.sinodata.business.entity.Fwzytdjkxz;


public interface FwzytdjkxzDao {

	public List<Fwzytdjkxz> queryList(Map<String,Object> condition);
	
	public int queryListCount(Map<String,Object> condition);
	
	int insertSelective(Fwzytdjkxz record);
	 
	int updateByPrimaryKey(Fwzytdjkxz record);
	
	int deleteByPrimaryKey(String fwbs);
	
}

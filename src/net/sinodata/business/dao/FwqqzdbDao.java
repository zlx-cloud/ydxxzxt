package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwqqzdb;
import org.apache.ibatis.annotations.Param;

public interface FwqqzdbDao {

	public List<Fwqqzdb> getList(@Param(value="status") String status);
	
	public Fwqqzdb getFwqqzdb(@Param(value="zdm") String zdm);
	
	public void updateFwqqzdb(Fwqqzdb fwqqzdb);
	
	public List<Map<String,Object>> getResourceList();
}

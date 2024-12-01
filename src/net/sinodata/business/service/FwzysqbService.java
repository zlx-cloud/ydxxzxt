package net.sinodata.business.service;

import java.util.List;
import net.sf.json.JSONArray;
import net.sinodata.business.entity.Fwzysqb;
import net.sinodata.business.entity.FwzysqbKey;




public interface FwzysqbService{

	int deleteByPrimaryKey(FwzysqbKey key);

    int insert(Fwzysqb record);

    int insertSelective(Fwzysqb record);

    Fwzysqb selectByPrimaryKey(FwzysqbKey key);

    int updateByPrimaryKeySelective(Fwzysqb record);

    int updateByPrimaryKey(Fwzysqb record);
    JSONArray queryTreeList(String fwbs);
    List<Fwzysqb> queryFwSqByFwcyfYyxtbh( String fwcyfYyxtbh ,String fwbs);
	    
}

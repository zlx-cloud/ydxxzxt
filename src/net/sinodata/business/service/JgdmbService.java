package net.sinodata.business.service;

import net.sf.json.JSONArray;
import net.sinodata.business.entity.Jgdmb;

public interface JgdmbService{

	int deleteByPrimaryKey(String jgbs);

    int insert(Jgdmb record);

    int insertSelective(Jgdmb record);

    Jgdmb selectByPrimaryKey(String jgbs);

    int updateByPrimaryKeySelective(Jgdmb record);

    int updateByPrimaryKey(Jgdmb record);
    
    JSONArray  queryJgdmTreeList(String sssjjg,String jgmc,String jgbs) throws Exception;
}

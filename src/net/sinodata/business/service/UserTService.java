package net.sinodata.business.service;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.Fwcyfzcb;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface UserTService{

	public List<Fwcyfzcb> findUserByLoginName(String userName);
	
	
	
    public SearchResult userList(Page page,
			Map<String, Object> condition);
	
	
    int deleteByPrimaryKey(String fwcyfYyxtbh);

    int insert(Fwcyfzcb record);

    int insertSelective(Fwcyfzcb record);

    Fwcyfzcb selectByPrimaryKey(String fwcyfYyxtbh);

    int updateByPrimaryKeySelective(Fwcyfzcb record);

    int updateByPrimaryKey(Fwcyfzcb record);
    
    public boolean[] checkInfo(String[] info,String[] num);
}

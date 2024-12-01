package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.sinodata.business.entity.Fwcyfzcb;

public interface FwcyfzcbDao {
    int deleteByPrimaryKey(String fwcyfYyxtbh);

    int insert(Fwcyfzcb record);

    int insertSelective(Fwcyfzcb record);

    Fwcyfzcb selectByPrimaryKey(String fwcyfYyxtbh);

    int updateByPrimaryKeySelective(Fwcyfzcb record);

    int updateByPrimaryKey(Fwcyfzcb record);
    
    public List<Fwcyfzcb> findByLoginName(String username);
    
    List<Fwcyfzcb> getUserListByPage( Map<String, Object> condition);
	
	int getUserCountByPage( Map<String, Object> condition);
	
	List<Fwcyfzcb> queryAllList(@Param("fwcyfYyxtbh") String fwcyfYyxtbh,@Param("fwcyfSsfj") String fwcyfSsfj);
	
	List<Fwcyfzcb> selectAllFwcyfzcb();
}
package net.sinodata.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.sinodata.business.entity.Fwzysqb;
import net.sinodata.business.entity.FwzysqbKey;

public interface FwzysqbDao {
    int deleteByPrimaryKey(FwzysqbKey key);

    int insert(Fwzysqb record);

    int insertSelective(Fwzysqb record);

    Fwzysqb selectByPrimaryKey(FwzysqbKey key);

    int updateByPrimaryKeySelective(Fwzysqb record);

    int updateByPrimaryKey(Fwzysqb record);
    
    List<Fwzysqb> queryFwSqByFwcyfYyxtbh(@Param("fwcyfYyxtbh") String fwcyfYyxtbh ,@Param("fwbs")String fwbs);
}
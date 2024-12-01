package net.sinodata.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import net.sinodata.business.entity.Jgdmb;

public interface JgdmbDao {
    int deleteByPrimaryKey(String jgbs);

    int insert(Jgdmb record);

    int insertSelective(Jgdmb record);

    Jgdmb selectByPrimaryKey(String jgbs);

    int updateByPrimaryKeySelective(Jgdmb record);

    int updateByPrimaryKey(Jgdmb record);
   List<Jgdmb> queryByJgbsAndsssjjg(@Param("sssjjg")String sssjjg,@Param("jgmc")String jgmc,@Param("jgbs")String jgbs);
}
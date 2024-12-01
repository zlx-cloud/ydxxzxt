package net.sinodata.business.dao;

import net.sinodata.business.entity.Fwzyxybwcjb;

public interface FwzyxybwcjbDao {
    int deleteByPrimaryKey(String xybwbs);

    int insert(Fwzyxybwcjb record);

    int insertSelective(Fwzyxybwcjb record);

    Fwzyxybwcjb selectByPrimaryKey(String xybwbs);

    int updateByPrimaryKeySelective(Fwzyxybwcjb record);

    int updateByPrimaryKey(Fwzyxybwcjb record);
}
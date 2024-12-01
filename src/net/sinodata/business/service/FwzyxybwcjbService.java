package net.sinodata.business.service;

import net.sinodata.business.entity.Fwzyxybwcjb;

public interface FwzyxybwcjbService{

	 int deleteByPrimaryKey(String xybwbs);

	    int insert(Fwzyxybwcjb record);

	    int insertSelective(Fwzyxybwcjb record);

	    Fwzyxybwcjb selectByPrimaryKey(String xybwbs);

	    int updateByPrimaryKeySelective(Fwzyxybwcjb record);

	    int updateByPrimaryKey(Fwzyxybwcjb record);
}

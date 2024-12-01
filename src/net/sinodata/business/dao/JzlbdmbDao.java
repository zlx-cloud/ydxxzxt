package net.sinodata.business.dao;

import java.util.List;

import net.sinodata.business.entity.Jzlbdmb;

public interface JzlbdmbDao {
    int insert(Jzlbdmb record);

    int insertSelective(Jzlbdmb record);
    
    List<Jzlbdmb> queryJzlbdmb();
}
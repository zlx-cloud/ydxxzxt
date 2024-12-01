package net.sinodata.business.service;

import java.util.List;
import net.sinodata.business.entity.Jzlbdmb;

public interface JzlbdmbService {

	  int insert(Jzlbdmb record);

	    int insertSelective(Jzlbdmb record);
	    
	    List<Jzlbdmb> queryJzlbdmb();
	
}

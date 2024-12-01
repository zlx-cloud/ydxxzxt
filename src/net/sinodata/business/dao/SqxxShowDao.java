package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import net.sinodata.business.entity.SqxxShow;

public interface SqxxShowDao {

	List<SqxxShow> getListByPage(Map<String, Object> condition);

	int getCountByPage(Map<String, Object> condition);

}

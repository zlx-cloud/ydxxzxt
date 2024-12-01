package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.formula.functions.T;

public interface PoliceMemberDao {

	List<T> getListByPage(Map<String, Object> condition);

	int getCountByPage(Map<String, Object> condition);

}

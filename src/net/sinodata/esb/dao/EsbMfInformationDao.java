package net.sinodata.esb.dao;

import java.util.List;
import java.util.Map;

import net.sinodata.esb.entity.EsbMfInformation;

import org.apache.ibatis.annotations.Param;

public interface EsbMfInformationDao {

	int queryCountAllByPid(@Param("pId") String pId);
	
	int queryCountEndedByPid(@Param("pId") String pId);
	
	int queryCountExceptionByPid(@Param("pId") String pId);
	
	List<EsbMfInformation> queryList(Map<String,Object> condition);
	
	int queryListCount(Map<String,Object> condition);
}

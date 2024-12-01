package net.sinodata.esb.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

import net.sinodata.esb.entity.EsbVarSystem;

public interface EsbVarSystemDao {

	List<EsbVarSystem> queryList(Map<String, Object> condition);
	
	int queryCountList(Map<String, Object> condition);
	
	int delete(@Param("varId") String id);
	
	int insert(EsbVarSystem esbVarSystem);
	
	int update(EsbVarSystem esbVarSystem);
	
	int queryStreamCount(@Param("varId") String varId);
	
	List<EsbVarSystem> queryStreamByVarId(@Param("varId") String varId);
}

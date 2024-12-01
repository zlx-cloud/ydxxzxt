package net.sinodata.esb.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.sinodata.esb.entity.EsbVarRelation;

public interface EsbVarRelationDao {

	List<EsbVarRelation> queryList(Map<String,Object> condition);
	
	int queryListCount(Map<String,Object> condition);
	
	List<EsbVarRelation> queryListByVarId(@Param("varId") String varId);
}

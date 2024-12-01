package net.sinodata.business.service;

import java.util.List;
import java.util.Map;

import net.sinodata.esb.entity.EsbVarRelation;
import net.sinodata.esb.entity.EsbVarSystem;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface EsbVarSystemService {

	public SearchResult esbVarSystemList(Page page, Map<String, Object> condition);
	
	public boolean addVarSystem(EsbVarSystem esbVarSystem);
	
	public boolean updateVarSystem(EsbVarSystem esbVarSystem);
	
	public int deleteVarSystem(String[] ids);
	
	public int queryStreamCount(String varId);
	
	public List<EsbVarRelation> queryStreamByVarId(String varId);
}

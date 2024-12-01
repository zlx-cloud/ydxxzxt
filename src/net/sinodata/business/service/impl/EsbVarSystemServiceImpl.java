package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sinodata.esb.dao.EsbVarRelationDao;
import net.sinodata.esb.dao.EsbVarSystemDao;
import net.sinodata.esb.entity.EsbVarRelation;
import net.sinodata.esb.entity.EsbVarSystem;
import net.sinodata.business.service.EsbVarSystemService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;
import net.sinodata.business.util.UUIDGeneratorUtil;

@Service
public class EsbVarSystemServiceImpl implements EsbVarSystemService {
	
	@Autowired
	private EsbVarSystemDao esbVarSystemDao;
	
	@Autowired
	private EsbVarRelationDao esbVarRelationDao;

	@Override
	public SearchResult esbVarSystemList(Page page,
			Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		int count = esbVarSystemDao.queryCountList(condition);
		List<EsbVarSystem> data = esbVarSystemDao.queryList(condition);
//		List<EsbVarSystem> data = queryList(condition);
		
//		int count = queryCount(condition);
		return new SearchResult(data,count);
	}
	
	@Override
	public boolean addVarSystem(EsbVarSystem esbVarSystem){
		if(esbVarSystem!=null){
			String varId = esbVarSystem.getVarId();
			if(varId==null || varId.equals("")){
				varId = UUIDGeneratorUtil.getUUID();
				esbVarSystem.setVarId(varId);
			}
			String varName = esbVarSystem.getVarName();
			if(varName==null || varName.equals("")){
				return false;
			}
			esbVarSystemDao.insert(esbVarSystem);
						
			return true;
		}
		return false;
	}
		
	
	@Override
	public boolean updateVarSystem(EsbVarSystem esbVarSystem){
		if(esbVarSystem!=null){
			String varId = esbVarSystem.getVarId();
			if(varId==null || varId.equals("")){
				return false;
			}
			esbVarSystemDao.update(esbVarSystem);
			return true;
		}
		return false;
	}
	
	@Override
	public int deleteVarSystem(String[] ids){
		if(ids!=null && ids.length>0){
			int reInt = 0;
			for(int i=0;i<ids.length;i++){
				String varId = ids[i];
				int num = queryStreamCount(varId);
				if(num==0){
					esbVarSystemDao.delete(varId);
					reInt += 1;
				}
			}
			return reInt;
		}
		return 0;
	}
	
	@Override
	public int queryStreamCount(String varId){
		return esbVarSystemDao.queryStreamCount(varId);
	}
	
	@Override
	public List<EsbVarRelation> queryStreamByVarId(String varId){
		return esbVarRelationDao.queryListByVarId(varId);
	}

}

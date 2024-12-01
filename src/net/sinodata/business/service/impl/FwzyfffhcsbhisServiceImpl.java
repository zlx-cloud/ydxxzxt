package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import net.sinodata.business.dao.FwzyfffhcsbhisDao;
import net.sinodata.business.entity.Fwzyfffhcsbhis;
import net.sinodata.business.service.FwzyfffhcsbhisService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FwzyfffhcsbhisServiceImpl implements FwzyfffhcsbhisService {
	@Autowired
	private FwzyfffhcsbhisDao FwzyfffhcsbhisDao;

	
	
	
	
	
	@Override
	public SearchResult List(Page page, Map<String, Object> condition) {
		// TODO Auto-generated method stub
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Fwzyfffhcsbhis> data=	FwzyfffhcsbhisDao.getFwzyfffhcsHisListByPage( condition);
		int  count=	FwzyfffhcsbhisDao.getFwzyfffhcsHisCountByPage( condition);
		return new SearchResult(data,count);
	}

	@Override
	public int deleteByPrimaryKey(String optId) {
		// TODO Auto-generated method stub
		return FwzyfffhcsbhisDao.deleteByPrimaryKey(optId);
	}

	@Override
	public int insert(Fwzyfffhcsbhis record) {
		// TODO Auto-generated method stub
		return FwzyfffhcsbhisDao.insert(record);
	}

	@Override
	public int insertSelective(Fwzyfffhcsbhis record) {
		// TODO Auto-generated method stub
		return FwzyfffhcsbhisDao.insertSelective(record);
	}

	@Override
	public Fwzyfffhcsbhis selectByPrimaryKey(String optId) {
		// TODO Auto-generated method stub
		return FwzyfffhcsbhisDao.selectByPrimaryKey(optId);
	}

	@Override
	public int updateByPrimaryKeySelective(Fwzyfffhcsbhis record) {
		// TODO Auto-generated method stub
		return FwzyfffhcsbhisDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Fwzyfffhcsbhis record) {
		// TODO Auto-generated method stub
		return FwzyfffhcsbhisDao.updateByPrimaryKey(record);
	}

	
	
	
}

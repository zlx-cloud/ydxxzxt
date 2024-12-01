package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import net.sinodata.business.dao.FwzyffqqcsbhisDao;
import net.sinodata.business.entity.Fwzyffqqcsbhis;
import net.sinodata.business.service.FwzyffqqcsbhisService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FwzyffqqcsbhisServiceImpl implements FwzyffqqcsbhisService {
	@Autowired
	private FwzyffqqcsbhisDao FwzyffqqcsbhisDao;

	
	
	@Override
	public SearchResult List(Page page, Map<String, Object> condition) {
		// TODO Auto-generated method stub
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Fwzyffqqcsbhis> data=	FwzyffqqcsbhisDao.getFwzyffqqcsHisListByPage( condition);
		int  count=	FwzyffqqcsbhisDao.getFwzyffqqcsHisCountByPage( condition);
		return new SearchResult(data,count);
	}

	@Override
	public int deleteByPrimaryKey(String optId) {
		// TODO Auto-generated method stub
		return FwzyffqqcsbhisDao.deleteByPrimaryKey(optId);
	}

	@Override
	public int insert(Fwzyffqqcsbhis record) {
		// TODO Auto-generated method stub
		return FwzyffqqcsbhisDao.insert(record);
	}

	@Override
	public int insertSelective(Fwzyffqqcsbhis record) {
		// TODO Auto-generated method stub
		return FwzyffqqcsbhisDao.insertSelective(record);
	}

	@Override
	public Fwzyffqqcsbhis selectByPrimaryKey(String optId) {
		// TODO Auto-generated method stub
		return FwzyffqqcsbhisDao.selectByPrimaryKey(optId);
	}

	@Override
	public int updateByPrimaryKeySelective(Fwzyffqqcsbhis record) {
		// TODO Auto-generated method stub
		return FwzyffqqcsbhisDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Fwzyffqqcsbhis record) {
		// TODO Auto-generated method stub
		return FwzyffqqcsbhisDao.updateByPrimaryKey(record);
	}

	
	
}

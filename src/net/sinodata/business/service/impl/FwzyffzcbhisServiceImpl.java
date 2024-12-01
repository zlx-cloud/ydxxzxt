package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import net.sinodata.business.dao.FwzyffzcbhisDao;
import net.sinodata.business.entity.Fwzyffzcbhis;
import net.sinodata.business.service.FwzyffzcbhisService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FwzyffzcbhisServiceImpl implements FwzyffzcbhisService {
	@Autowired
	private FwzyffzcbhisDao FwzyffzcbhisDao;

	
	
	
	@Override
	public SearchResult List(Page page, Map<String, Object> condition) {
		// TODO Auto-generated method stub
    	condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Fwzyffzcbhis> data=	FwzyffzcbhisDao.getFwzyffHisListByPage( condition);
		int  count=	FwzyffzcbhisDao.getFwzyffHisCountByPage( condition);
		return new SearchResult(data,count);
	}

	@Override
	public int deleteByPrimaryKey(String optId) {
		// TODO Auto-generated method stub
		return FwzyffzcbhisDao.deleteByPrimaryKey(optId);
	}

	@Override
	public int insert(Fwzyffzcbhis record) {
		// TODO Auto-generated method stub
		return FwzyffzcbhisDao.insert(record);
	}

	@Override
	public int insertSelective(Fwzyffzcbhis record) {
		// TODO Auto-generated method stub
		return FwzyffzcbhisDao.insertSelective(record);
	}

	@Override
	public Fwzyffzcbhis selectByPrimaryKey(String optId) {
		// TODO Auto-generated method stub
		return FwzyffzcbhisDao.selectByPrimaryKey(optId);
	}

	@Override
	public int updateByPrimaryKeySelective(Fwzyffzcbhis record) {
		// TODO Auto-generated method stub
		return FwzyffzcbhisDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Fwzyffzcbhis record) {
		// TODO Auto-generated method stub
		return FwzyffzcbhisDao.updateByPrimaryKey(record);
	}

	
	
	
}

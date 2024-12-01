package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import net.sinodata.business.dao.FwzysqbhisDao;
import net.sinodata.business.entity.Fwzysqbhis;
import net.sinodata.business.entity.FwzysqbhisKey;
import net.sinodata.business.service.FwzysqbhisService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FwzysqbhisServiceImpl implements FwzysqbhisService {
	@Autowired
	private FwzysqbhisDao FwzysqbhisDao;

	
	@Override
	public SearchResult List(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Fwzysqbhis> data=	FwzysqbhisDao.getFwzysqbhisListByPage( condition);
		int  count=	FwzysqbhisDao.getFwzysqbhisCountByPage( condition);
		return new SearchResult(data,count);
	}

	@Override
	public List<Fwzysqbhis> getFwzysqbhisListByPage(
			Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return FwzysqbhisDao.getFwzysqbhisListByPage(condition);
	}

	@Override
	public int getFwzysqbhisCountByPage(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return FwzysqbhisDao.getFwzysqbhisCountByPage(condition);
	}

	@Override
	public int deleteByPrimaryKey(FwzysqbhisKey key) {
		// TODO Auto-generated method stub
		return FwzysqbhisDao.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(Fwzysqbhis record) {
		// TODO Auto-generated method stub
		return FwzysqbhisDao.insert(record);
	}

	@Override
	public int insertSelective(Fwzysqbhis record) {
		// TODO Auto-generated method stub
		return FwzysqbhisDao.insertSelective(record);
	}

	@Override
	public Fwzysqbhis selectByPrimaryKey(FwzysqbhisKey key) {
		// TODO Auto-generated method stub
		return FwzysqbhisDao.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKeySelective(Fwzysqbhis record) {
		// TODO Auto-generated method stub
		return FwzysqbhisDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Fwzysqbhis record) {
		// TODO Auto-generated method stub
		return FwzysqbhisDao.updateByPrimaryKey(record);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	

	
	
}

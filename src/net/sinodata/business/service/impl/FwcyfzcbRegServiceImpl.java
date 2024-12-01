package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import net.sinodata.business.dao.FwcyfzcbRegDao;
import net.sinodata.business.entity.FwcyfzcbReg;
import net.sinodata.business.service.FwcyfzcbRegService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FwcyfzcbRegServiceImpl implements FwcyfzcbRegService {
	@Autowired
	private FwcyfzcbRegDao FwcyfzcbRegDao;

	@Override
	public int insert(FwcyfzcbReg record) {
		// TODO Auto-generated method stub
		return FwcyfzcbRegDao.insert(record);
	}

	@Override
	public int insertSelective(FwcyfzcbReg record) {
		// TODO Auto-generated method stub
		return FwcyfzcbRegDao.insertSelective(record);
	}

	@Override
	public SearchResult userRegList(Page page, Map<String, Object> condition) {
		// TODO Auto-generated method stub
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<FwcyfzcbReg> data=	FwcyfzcbRegDao.getUserListByPage( condition);
		int  count=	FwcyfzcbRegDao.getUserCountByPage( condition);
		return new SearchResult(data,count);
	}

	@Override
	public List<FwcyfzcbReg> getUserListByPage(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return FwcyfzcbRegDao.getUserListByPage(condition);
	}

	@Override
	public int getUserCountByPage(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return FwcyfzcbRegDao.getUserCountByPage(condition);
	}

	@Override
	public FwcyfzcbReg selectByPrimaryKey(String fwcyfYyxtbh) {
		// TODO Auto-generated method stub
		return FwcyfzcbRegDao.selectByPrimaryKey(fwcyfYyxtbh);
	}

	@Override
	public int deleteByPrimaryKey(String fwcyfYyxtbh) {
		// TODO Auto-generated method stub
		return FwcyfzcbRegDao.deleteByPrimaryKey(fwcyfYyxtbh);
	}
	
	
	
	
}

package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import net.sinodata.business.dao.FwzyzcbRegDao;
import net.sinodata.business.entity.Fwzyzcb;
import net.sinodata.business.entity.FwzyzcbReg;
import net.sinodata.business.service.FwzyzcbRegService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FwzyzcbRegServiceImpl implements FwzyzcbRegService {
	

	@Autowired
	private FwzyzcbRegDao FwzyzcbRegDao;

	
	
	
	
	
	@Override
	public SearchResult fwzyzcbRegList(Page page, Map<String, Object> condition) {
		// TODO Auto-generated method stub
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Fwzyzcb> data = FwzyzcbRegDao.getFwzyzcbRegListByPage(condition);
		int count = FwzyzcbRegDao.getFwzyzcbRegCountByPage(condition);
		return new SearchResult(data, count);
	}

	@Override
	public int deleteByPrimaryKey(String fwbs) {
		// TODO Auto-generated method stub
		return FwzyzcbRegDao.deleteByPrimaryKey(fwbs);
	}

	@Override
	public int insert(FwzyzcbReg record) {
		// TODO Auto-generated method stub
		return FwzyzcbRegDao.insert(record);
	}

	@Override
	public int insertSelective(FwzyzcbReg record) {
		// TODO Auto-generated method stub
		return FwzyzcbRegDao.insertSelective(record);
	}

	@Override
	public FwzyzcbReg selectByPrimaryKey(String fwbs) {
		// TODO Auto-generated method stub
		return FwzyzcbRegDao.selectByPrimaryKey(fwbs);
	}

	@Override
	public int updateByPrimaryKeySelective(FwzyzcbReg record) {
		// TODO Auto-generated method stub
		return FwzyzcbRegDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(FwzyzcbReg record) {
		// TODO Auto-generated method stub
		return FwzyzcbRegDao.updateByPrimaryKey(record);
	}

	@Override
	public int insertTcb(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return FwzyzcbRegDao.insertTcb(map);
	}
	
	
	
   
	
}

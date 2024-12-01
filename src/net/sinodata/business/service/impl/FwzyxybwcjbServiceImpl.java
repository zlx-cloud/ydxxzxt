package net.sinodata.business.service.impl;

import net.sinodata.business.dao.FwzyxybwcjbDao;
import net.sinodata.business.entity.Fwzyxybwcjb;
import net.sinodata.business.service.FwzyxybwcjbService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FwzyxybwcjbServiceImpl implements FwzyxybwcjbService {
	@Autowired
	private FwzyxybwcjbDao fwzyxybwcjbDao;

	@Override
	public int deleteByPrimaryKey(String xybwbs) {
		// TODO Auto-generated method stub
		return fwzyxybwcjbDao.deleteByPrimaryKey(xybwbs);
	}

	@Override
	public int insert(Fwzyxybwcjb record) {
		// TODO Auto-generated method stub
		return fwzyxybwcjbDao.insert(record);
	}

	@Override
	public int insertSelective(Fwzyxybwcjb record) {
		// TODO Auto-generated method stub
		return fwzyxybwcjbDao.insertSelective(record);
	}

	@Override
	public Fwzyxybwcjb selectByPrimaryKey(String xybwbs) {
		// TODO Auto-generated method stub
		return fwzyxybwcjbDao.selectByPrimaryKey(xybwbs);
	}

	@Override
	public int updateByPrimaryKeySelective(Fwzyxybwcjb record) {
		// TODO Auto-generated method stub
		return fwzyxybwcjbDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Fwzyxybwcjb record) {
		// TODO Auto-generated method stub
		return fwzyxybwcjbDao.updateByPrimaryKey(record);
	}
	
	
	

	
}

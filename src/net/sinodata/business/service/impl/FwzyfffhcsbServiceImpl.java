package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import net.sinodata.business.dao.FwzyfffhcsbDao;
import net.sinodata.business.entity.Fwzyfffhcsb;
import net.sinodata.business.entity.FwzyfffhcsbKey;
import net.sinodata.business.service.FwzyfffhcsbService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FwzyfffhcsbServiceImpl implements FwzyfffhcsbService {
	@Autowired
	private FwzyfffhcsbDao FwzyfffhcsbDao;

	
	
	@Override
	public SearchResult List(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Fwzyfffhcsb> data=	FwzyfffhcsbDao.getFwzyfffhcsListByPage( condition);
		int  count=	FwzyfffhcsbDao.getFwzyfffhcsCountByPage( condition);
		return new SearchResult(data,count);
	}

	@Override
	public List<Fwzyfffhcsb> getFwzyfffhcsListByPage(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return FwzyfffhcsbDao.getFwzyfffhcsListByPage( condition);
	}

	@Override
	public int getFwzyfffhcsCountByPage(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return FwzyfffhcsbDao.getFwzyfffhcsCountByPage( condition);
	}

	@Override
	public int deleteByPrimaryKey(FwzyfffhcsbKey key) {
		// TODO Auto-generated method stub
		return FwzyfffhcsbDao.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(Fwzyfffhcsb record) {
		// TODO Auto-generated method stub
		return FwzyfffhcsbDao.insert(record);
	}

	@Override
	public int insertSelective(Fwzyfffhcsb record) {
		// TODO Auto-generated method stub
		return FwzyfffhcsbDao.insertSelective(record);
	}

	@Override
	public Fwzyfffhcsb selectByPrimaryKey(FwzyfffhcsbKey key) {
		// TODO Auto-generated method stub
		return FwzyfffhcsbDao.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKeySelective(Fwzyfffhcsb record) {
		// TODO Auto-generated method stub
		return FwzyfffhcsbDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Fwzyfffhcsb record) {
		// TODO Auto-generated method stub
		return FwzyfffhcsbDao.updateByPrimaryKey(record);
	}

	@Override
	public int insertByBatch(List<Fwzyfffhcsb> record) {
		// TODO Auto-generated method stub
		return FwzyfffhcsbDao.insertByBatch(record);
	}

	@Override
	public java.util.List<Fwzyfffhcsb> queryListByFfbsAndFwbs(FwzyfffhcsbKey key) {
		// TODO Auto-generated method stub
		return FwzyfffhcsbDao.queryListByFfbsAndFwbs(key);
	}
	
	
	
   
	
}

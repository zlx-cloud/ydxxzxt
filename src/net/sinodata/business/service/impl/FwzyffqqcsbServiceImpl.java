package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import net.sinodata.business.dao.FwzyffqqcsbDao;
import net.sinodata.business.entity.Fwzyffqqcsb;
import net.sinodata.business.entity.FwzyffqqcsbKey;
import net.sinodata.business.service.FwzyffqqcsbService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FwzyffqqcsbServiceImpl implements FwzyffqqcsbService {
	@Autowired
	private FwzyffqqcsbDao FwzyffqqcsbDao;

	
	
	@Override
	public SearchResult List(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Fwzyffqqcsb> data=	FwzyffqqcsbDao.getFwzyffqqcsListByPage( condition);
		int  count=	FwzyffqqcsbDao.getFwzyffqqcsCountByPage( condition);
		return new SearchResult(data,count);
	}

	@Override
	public List<Fwzyffqqcsb> getFwzyffqqcsListByPage(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return FwzyffqqcsbDao.getFwzyffqqcsListByPage( condition);
	}

	@Override
	public int getFwzyffqqcsCountByPage(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return FwzyffqqcsbDao.getFwzyffqqcsCountByPage( condition);
	}

	@Override
	public int deleteByPrimaryKey(FwzyffqqcsbKey key) {
		// TODO Auto-generated method stub
		return FwzyffqqcsbDao.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(Fwzyffqqcsb record) {
		// TODO Auto-generated method stub
		return FwzyffqqcsbDao.insert(record);
	}

	@Override
	public int insertSelective(Fwzyffqqcsb record) {
		// TODO Auto-generated method stub
		return FwzyffqqcsbDao.insertSelective(record);
	}

	@Override
	public Fwzyffqqcsb selectByPrimaryKey(FwzyffqqcsbKey key) {
		// TODO Auto-generated method stub
		return FwzyffqqcsbDao.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKeySelective(Fwzyffqqcsb record) {
		// TODO Auto-generated method stub
		return FwzyffqqcsbDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Fwzyffqqcsb record) {
		// TODO Auto-generated method stub
		return FwzyffqqcsbDao.updateByPrimaryKey(record);
	}

	@Override
	public int insertByBatch(List<Fwzyffqqcsb> record) {
		// TODO Auto-generated method stub
		return FwzyffqqcsbDao.insertByBatch(record);
	}

	@Override
	public java.util.List<Fwzyffqqcsb> queryListByFwbsAndFfbs(FwzyffqqcsbKey key) {
		// TODO Auto-generated method stub
		return FwzyffqqcsbDao.queryListByFwbsAndFfbs(key);
	}
	
	
	
   
	
}

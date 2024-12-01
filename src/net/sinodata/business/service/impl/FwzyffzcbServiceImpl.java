package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import net.sinodata.business.dao.FwzyffzcbDao;
import net.sinodata.business.entity.Fwzyffzcb;
import net.sinodata.business.entity.FwzyffzcbKey;
import net.sinodata.business.entity.YyFwFfInfo;
import net.sinodata.business.service.FwzyffzcbService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FwzyffzcbServiceImpl implements FwzyffzcbService {

	@Autowired
	private FwzyffzcbDao FwzyffzcbDao;

	@Override
	public SearchResult List(Page page, Map<String, Object> condition) {
		// TODO Auto-generated method stub
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Fwzyffzcb> data = FwzyffzcbDao.getFwzyffListByPage(condition);
		int count = FwzyffzcbDao.getFwzyffCountByPage(condition);
		return new SearchResult(data, count);
	}

	@Override
	public int deleteByPrimaryKey(FwzyffzcbKey key) {
		// TODO Auto-generated method stub
		return FwzyffzcbDao.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(Fwzyffzcb record) {
		// TODO Auto-generated method stub
		return FwzyffzcbDao.insert(record);
	}

	@Override
	public int insertSelective(Fwzyffzcb record) {
		// TODO Auto-generated method stub
		return FwzyffzcbDao.insertSelective(record);
	}

	@Override
	public Fwzyffzcb selectByPrimaryKey(FwzyffzcbKey key) {
		// TODO Auto-generated method stub
		return FwzyffzcbDao.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKeySelective(Fwzyffzcb record) {
		// TODO Auto-generated method stub
		return FwzyffzcbDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Fwzyffzcb record) {
		// TODO Auto-generated method stub
		return FwzyffzcbDao.updateByPrimaryKey(record);
	}

	@Override
	public int insertByBatch(List<Fwzyffzcb> Fwzyffzcb) {
		// TODO Auto-generated method stub
		return FwzyffzcbDao.insertByBatch(Fwzyffzcb);
	}

	@Override
	public List<Fwzyffzcb> queryListByFwbs(String fwbs) {
		// TODO Auto-generated method stub
		return FwzyffzcbDao.queryListByFwbs(fwbs);
	}

	@Override
	public java.util.List<Fwzyffzcb> getFwzyffListByPage(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return FwzyffzcbDao.getFwzyffListByPage(condition);
	}

	@Override
	public int getFwzyffCountByPage(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return FwzyffzcbDao.getFwzyffCountByPage(condition);
	}

	@Override
	public String queryFFBSbyFwbs(String fwbs) {
		// TODO Auto-generated method stub
		return FwzyffzcbDao.queryFFBSbyFwbs(fwbs);
	}

	@Override
	public String queryURLbyFwbs(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return FwzyffzcbDao.queryURLbyFwbs(map);
	}

	@Override
	public String queryFFLXbyFwbs(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return FwzyffzcbDao.queryFFLXbyFwbs(map);
	}

	@Override
	public Fwzyffzcb selectByFwbsFfbs(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return FwzyffzcbDao.selectByFwbsFfbs(map);
	}

	@Override
	public java.util.List<Map<String, Object>> getFfList(Map<String, Object> map) {
		return FwzyffzcbDao.getFfList(map);
	}

	@Override
	public java.util.List<YyFwFfInfo> getYyFwFfListByPage(Map<String, Object> condition) {
		return FwzyffzcbDao.getYyFwFfListByPage(condition);
	}

	@Override
	public int getYyFwFfInfoCountByPage(Map<String, Object> condition) {
		return FwzyffzcbDao.getYyFwFfInfoCountByPage(condition);
	}

}
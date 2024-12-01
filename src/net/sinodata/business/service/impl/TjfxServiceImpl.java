package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import net.sinodata.business.dao.TjfxDao;
import net.sinodata.business.service.TjfxService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TjfxServiceImpl implements TjfxService{

	@Autowired
	private TjfxDao tjfxDao;

	//当前活跃服务
	@Override
	public List<Map<String, Object>> selectCurrentActiveServiceData() {
		return tjfxDao.selectCurrentActiveServiceData();
	}
	@Override
	public SearchResult CurrentActiveServicePageList(Page page,
			Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Map<String, Object>> data = tjfxDao.queryCurrentActiveServiceTable(condition);
		int count = tjfxDao.queryCurrentActiveServiceCount();
		return new SearchResult(data, count);
	}

	//服务日调用量统计
	@Override
	public List<Map<String, Object>> serviceUseByDay() {
		return tjfxDao.serviceUseByDay();
	}
	@Override
	public SearchResult ServiceUseByDayPageList(Page page,
			Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Map<String, Object>> data = tjfxDao.queryServiceUseByDayTable(condition);
		int count = tjfxDao.queryServiceUseByDayCount();
		return new SearchResult(data, count);
	}
	
	//服务周调用量统计
	@Override
	public Integer serviceUseByWeek(String firstDay,String lastDay) {
		return tjfxDao.serviceUseByWeek(firstDay,lastDay);
	}
	@Override
	public SearchResult ServiceUseByWeekPageList(Page page,
			Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Map<String, Object>> data = tjfxDao.queryServiceUseByWeekTable(condition);
		int count = tjfxDao.queryServiceUseByWeekCount(condition);
		return new SearchResult(data, count);
	}
	
	//各分局调用量占比统计
	@Override
	public List<Map<String, Object>> serviceUseByOrg() {
		return tjfxDao.serviceUseByOrg();
	}
	@Override
	public SearchResult ServiceUseByOrgPageList(Page page,
			Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Map<String, Object>> data = tjfxDao.queryServiceUseByOrgTable(condition);
		int count = tjfxDao.queryServiceUseByOrgCount();
		return new SearchResult(data, count);
	}
	
	//各分局调用量日统计
		//查询近7天内调用服务的机构
		@Override
		public List<Map<String, Object>> selectOrgCodeAndName() {
			return tjfxDao.selectOrgCodeAndName();
		}
		//查询某机构在固定某一天调用服务的次数
		@Override
		public Integer selectCountByOrgAndDay(String day, String orgCode) {
			return tjfxDao.selectCountByOrgAndDay(day, orgCode);
		}
		@Override
		public SearchResult ServiceUseByOrgAndDayPageList(Page page,
				Map<String, Object> condition) {
			condition.put("start", page.getStart());
			condition.put("end", page.getRows());
			List<Map<String, Object>> data = tjfxDao.queryServiceUseByOrgAndDayTable(condition);
			int count = tjfxDao.queryServiceUseByOrgAndDayCount();
			return new SearchResult(data, count);
		}
		
		//服务调用Top10
		@Override
		public List<Map<String, Object>> selectServiceUseTopTen() {
			return tjfxDao.selectServiceUseTopTen();
		}
		@Override
		public SearchResult ServiceUseTopTenPageList(Page page,
				Map<String, Object> condition) {
			condition.put("start", page.getStart());
			condition.put("end", page.getRows());
			List<Map<String, Object>> data = tjfxDao.queryServiceUseTopTenTable(condition);
			int count = tjfxDao.queryServiceUseTopTenCount();
			return new SearchResult(data, count);
		}
		
		//总线服务调用统计
		@Override
		public Integer selectCurrentZXInfo(String startTime,String endTime) {
			return tjfxDao.selectCurrentZXInfo(startTime,endTime);
		}
		@Override
		public SearchResult ZXInfoPageList(Page page,
				Map<String, Object> condition) {
			condition.put("start", page.getStart());
			condition.put("end", page.getRows());
			List<Map<String, Object>> data = tjfxDao.queryZXInfoTable(condition);
			int count = tjfxDao.queryZXInfoCount(condition);
			return new SearchResult(data, count);
		}
		@Override
		public Integer insertFwzytcsbrzb(String fwbs, String time) {
			return tjfxDao.insertFwzytcsbrzb(fwbs, time);
		}
	
}

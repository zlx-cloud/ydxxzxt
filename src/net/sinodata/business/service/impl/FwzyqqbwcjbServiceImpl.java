package net.sinodata.business.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sinodata.business.dao.FwcyfzcbDao;
import net.sinodata.business.dao.FwzyqqbwcjbDao;
import net.sinodata.business.dao.FwzyzcbDao;
import net.sinodata.business.entity.ConfigInfo;
import net.sinodata.business.entity.Fwcyfzcb;
import net.sinodata.business.entity.Fwzyqqbwcjb;
import net.sinodata.business.entity.FwzyqqbwcjbDownload;
import net.sinodata.business.entity.Fwzyzcb;
import net.sinodata.business.entity.QwLogDownload;
import net.sinodata.business.service.FwzyqqbwcjbService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

import net.sinodata.business.dao.elasticsearch.EsDao;
import net.sinodata.business.util.elasticsearch.QueryBuilderUtil;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FwzyqqbwcjbServiceImpl implements FwzyqqbwcjbService {
	@Autowired
	private FwzyqqbwcjbDao fwzyqqbwcjbDao;
	
	@Autowired
	private FwcyfzcbDao FwcyfzcbDao;
	
	@Autowired
	private FwzyzcbDao fwzyzcbDao;
	@Autowired
	private EsDao esDao;
	@Autowired(required = false)
	private ConfigInfo configInfo;

	@Override
	public int deleteByPrimaryKey(String qqbwbs) {
		// TODO Auto-generated method stub
		return fwzyqqbwcjbDao.deleteByPrimaryKey(qqbwbs);
	}

	@Override
	public int insert(Fwzyqqbwcjb record) {
		// TODO Auto-generated method stub
		return fwzyqqbwcjbDao.insert(record);
	}

	@Override
	public int insertSelective(Fwzyqqbwcjb record) {
		// TODO Auto-generated method stub
		return fwzyqqbwcjbDao.insertSelective(record);
	}

	@Override
	public Fwzyqqbwcjb selectByPrimaryKey(String qqbwbs) {
		// TODO Auto-generated method stub
		return fwzyqqbwcjbDao.selectByPrimaryKey(qqbwbs);
	}

	@Override
	public int updateByPrimaryKeySelective(Fwzyqqbwcjb record) {
		// TODO Auto-generated method stub
		return fwzyqqbwcjbDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Fwzyqqbwcjb record) {
		// TODO Auto-generated method stub
		return fwzyqqbwcjbDao.updateByPrimaryKey(record);
	}
	
	public SearchResult getList(Page page,Map<String,Object> condition){
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		String days ="DAY"+condition.get("startTime").toString().substring(8,10);
		condition.put("days", days);
		List<Fwzyqqbwcjb> list = fwzyqqbwcjbDao.queryList(condition);
		int count = fwzyqqbwcjbDao.queryListCount(condition);
		return new SearchResult(list,count);
	}
	
	public List<Fwcyfzcb> selectAllFwcyfzcb(){
		return FwcyfzcbDao.selectAllFwcyfzcb();
	}
	
	public List<Fwzyzcb> selectAllFwzyzcb(){
		return fwzyzcbDao.queryAllList(null);
	}

	@Override
	public SearchResult getQwList(Page page, Map<String, Object> condition) {

		Integer start=page.getStart();
		Integer rows=page.getRows()/page.getPage();

		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		String param=condition.get("param")==null?"":condition.get("param").toString();
		if(param!=null&&!"".equals(param)){
			String[] columnArr={"fwqqNr","fwtgNr","xxczryGmsfhm","xxczryXm","fwbs","fwqqzZcxx","ffbs","fwqqsbBh","xxczryGajgjgdm","fwqqSjsjlx"};
			queryBuilder.must(QueryBuilderUtil.getQueryStringWialdBuilder(columnArr,param));
//			BoolQueryBuilder paramQueryBuilder = QueryBuilders.boolQuery();
//			paramQueryBuilder.should(QueryBuilderUtil.build("fwqqNr", param, QueryBuilderUtil.LIKE));
//			paramQueryBuilder.should(QueryBuilderUtil.build("fwtgNr", param, QueryBuilderUtil.LIKE));
//			paramQueryBuilder.should(QueryBuilderUtil.build("xxczryXm", param, QueryBuilderUtil.LIKE));
//			paramQueryBuilder.should(QueryBuilderUtil.build("xxczryGmsfhm", param, QueryBuilderUtil.LIKE));
//			queryBuilder.must(paramQueryBuilder);
		}
		String operator=condition.get("operator")==null?"":condition.get("operator").toString();
		if(operator!=null&&!"".equals(operator)){
			String[] columnArr={"xxczryGmsfhm","xxczryXm"};
			queryBuilder.must(QueryBuilderUtil.getQueryStringWialdBuilder(columnArr,operator));
//			queryBuilder.must(QueryBuilders.queryStringQuery(queryStr));
//			BoolQueryBuilder operatorQueryBuilder = QueryBuilders.boolQuery();
//			operatorQueryBuilder.should(QueryBuilderUtil.build("xxczryXm", operator, QueryBuilderUtil.LIKE));
//			operatorQueryBuilder.should(QueryBuilderUtil.build("xxczryGmsfhm", operator, QueryBuilderUtil.LIKE));
//			queryBuilder.must(operatorQueryBuilder);
		}
		SearchResult searchResult = esDao.searchDataPage(start, rows, queryBuilder, configInfo.getEsFwbw()+"*", "startTime.keyword", SortOrder.DESC, false);
		return searchResult;
	}


	@Override
	public Map<String, Object> getQw(String id) {
		QueryBuilder queryBuilder = QueryBuilders.termQuery("id.keyword",id);
		List<Map<String, Object>> search = esDao.search(queryBuilder, configInfo.getEsFwbw());
		Map<String, Object> map = new HashMap<>();
		if(search!=null&&search.size()>0){
			map = search.get(0);
		}
		return map;
	}

	@Override
	public List<FwzyqqbwcjbDownload> getLogList(Map<String, Object> condition) {
		return fwzyqqbwcjbDao.getLogList(condition);
	}

	@Override
	public List<QwLogDownload> getQwList(Map<String, Object> condition) {
		Integer start=(Integer) condition.get("start");
		Integer rows=(Integer) condition.get("end");

		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		String param=condition.get("param")==null?"":condition.get("param").toString();
		if(param!=null&&!"".equals(param)){
			String[] columnArr={"fwqqNr","fwtgNr","xxczryGmsfhm","xxczryXm","fwbs","fwqqzZcxx","ffbs","fwqqsbBh","xxczryGajgjgdm","fwqqSjsjlx"};
			queryBuilder.must(QueryBuilderUtil.getQueryStringWialdBuilder(columnArr,param));
		}
		String operator=condition.get("operator")==null?"":condition.get("operator").toString();
		if(operator!=null&&!"".equals(operator)){
			String[] columnArr={"xxczryGmsfhm","xxczryXm"};
			queryBuilder.must(QueryBuilderUtil.getQueryStringWialdBuilder(columnArr,operator));
		}
		List<Map<String,Object>> searchResult = esDao.searchDataList(start, rows, queryBuilder, configInfo.getEsFwbw()+"*", "startTime.keyword", SortOrder.DESC, false);
		
		List<QwLogDownload> result = new ArrayList<QwLogDownload>();
		for(Map<String,Object> option : searchResult) {
			QwLogDownload i = new QwLogDownload();
			i.setQqbwbs(option.get("qqbwbs").toString());
			i.setFwqqzZcxx(option.get("fwqqzZcxx").toString());
			i.setFwbs(option.get("fwbs").toString());
			i.setFfbs(option.get("ffbs").toString());
			i.setStartTime(option.get("startTime").toString());
			i.setEndTime(option.get("endTime").toString());
			i.setTimeConsuming(option.get("timeConsuming").toString());
			i.setFwtgztdm(option.get("fwtgztdm").toString());
			String fwqqNr = option.get("fwqqNr").toString();
			if(fwqqNr.length()>2000) {
				i.setFwqqNr(option.get("fwqqNr").toString().substring(0,2000));
			}else {
				i.setFwqqNr(fwqqNr);
			}
			String fwtgNr = option.get("fwtgNr").toString();
			if(fwtgNr.length()>2000) {
				i.setFwtgNr(option.get("fwtgNr").toString().substring(0,2000));
			}else {
				i.setFwtgNr(fwtgNr);
			}
			result.add(i);
		}
		return result;
	}


}

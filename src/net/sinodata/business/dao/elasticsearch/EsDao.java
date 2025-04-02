/*
 * package net.sinodata.business.dao.elasticsearch;
 * 
 * import net.sinodata.business.util.SearchResult; import
 * net.sinodata.business.util.elasticsearch.EsJdbc; import
 * org.apache.commons.lang3.StringUtils; import
 * org.elasticsearch.action.bulk.BulkRequest; import
 * org.elasticsearch.action.get.GetRequest; import
 * org.elasticsearch.action.get.GetResponse; import
 * org.elasticsearch.action.index.IndexRequest; import
 * org.elasticsearch.action.search.SearchRequest; import
 * org.elasticsearch.action.search.SearchResponse; import
 * org.elasticsearch.client.RequestOptions; import
 * org.elasticsearch.client.RestHighLevelClient; import
 * org.elasticsearch.client.core.CountRequest; import
 * org.elasticsearch.client.core.CountResponse; import
 * org.elasticsearch.common.text.Text; import
 * org.elasticsearch.index.query.QueryBuilder; import
 * org.elasticsearch.search.SearchHit; import
 * org.elasticsearch.search.SearchHits; import
 * org.elasticsearch.search.builder.SearchSourceBuilder; import
 * org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder; import
 * org.elasticsearch.search.fetch.subphase.highlight.HighlightField; import
 * org.elasticsearch.search.sort.SortOrder; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.stereotype.Repository;
 * 
 * import java.io.IOException; import java.util.*;
 * 
 * @Repository public class EsDao {
 * 
 * @Autowired private EsJdbc esJdbc;
 * 
 * public void index(String esTable, String id, Map<String, ?> data) {
 * RestHighLevelClient client = esJdbc.getRestHighLevelClient(); try {
 * IndexRequest indexRequest = new IndexRequest(esTable) .id(id).source(data);
 * client.index(indexRequest, RequestOptions.DEFAULT); } catch (Exception e) {
 * e.printStackTrace(); } }
 * 
 * public boolean isExist(String esTable, String id) { RestHighLevelClient
 * client = esJdbc.getRestHighLevelClient(); boolean flag = false; try {
 * GetRequest getRequest = new GetRequest(esTable, id); GetResponse getResponse
 * = client.get(getRequest, RequestOptions.DEFAULT); if (getResponse.isExists())
 * { flag = true; } } catch (Exception e) { e.printStackTrace(); } return flag;
 * }
 * 
 * 
 * 
 * public Integer count(QueryBuilder queryBuilder, String... esTable) {
 * 
 * RestHighLevelClient client = esJdbc.getRestHighLevelClient(); Integer num =
 * 0; try { SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
 * searchSourceBuilder.query(queryBuilder); CountRequest countRequest = new
 * CountRequest(esTable); countRequest.source(searchSourceBuilder);
 * CountResponse countResponse = client.count(countRequest,
 * RequestOptions.DEFAULT); num = Math.toIntExact(countResponse.getCount()); }
 * catch (Exception e) { e.printStackTrace(); } return num; }
 * 
 * public List<Map<String, Object>> search(QueryBuilder queryBuilder, String...
 * esTable) { RestHighLevelClient client = esJdbc.getRestHighLevelClient();
 * List<Map<String, Object>> list = new ArrayList<>(); try { SearchSourceBuilder
 * searchSourceBuilder = new SearchSourceBuilder(); if (queryBuilder != null) {
 * searchSourceBuilder.query(queryBuilder); } searchSourceBuilder.size(999);
 * SearchRequest searchRequest = new SearchRequest(esTable);
 * searchRequest.source(searchSourceBuilder); SearchResponse searchResponse =
 * client.search(searchRequest, RequestOptions.DEFAULT); SearchHits hits =
 * searchResponse.getHits(); for (SearchHit hit : hits) {
 * list.add(hit.getSourceAsMap()); } } catch (Exception e) {
 * e.printStackTrace(); } return list; }
 * 
 * 
 * public List<Map<String, Object>> search(QueryBuilder queryBuilder, int from,
 * int size, String... esTable) { RestHighLevelClient client =
 * esJdbc.getRestHighLevelClient(); List<Map<String, Object>> list = new
 * ArrayList<>(); try { SearchSourceBuilder searchSourceBuilder = new
 * SearchSourceBuilder(); if (queryBuilder != null) {
 * searchSourceBuilder.query(queryBuilder); }
 * searchSourceBuilder.from(from).size(size); SearchRequest searchRequest = new
 * SearchRequest(esTable); searchRequest.source(searchSourceBuilder);
 * SearchResponse searchResponse = client.search(searchRequest,
 * RequestOptions.DEFAULT); SearchHits hits = searchResponse.getHits(); for
 * (SearchHit hit : hits) { list.add(hit.getSourceAsMap()); } } catch (Exception
 * e) { e.printStackTrace(); } return list; }
 * 
 *//**
	 * 按照分页查询elasticsearch
	 * 
	 * @param pageNum      第几页
	 * @param pageSize     每页多少条
	 * @param queryBuilder 查询的结构
	 * @param index        表名
	 * @param sortField    需要排序的属性，如果不需要排序，则传入null即可
	 * @param sortOrder    排序的类型，只有 sortField sortOrder均不为空才会进行排序
	 * @param highlight    true为高亮，false为不高亮，默认为true
	 * @return
	 * @throws IOException
	 *//*
		 * public List<Map<String,Object>> searchDataList( int pageNum, int pageSize,
		 * QueryBuilder queryBuilder, String index, String sortField, SortOrder
		 * sortOrder, Boolean highlight) { RestHighLevelClient client =
		 * esJdbc.getRestHighLevelClient(); SearchResponse response = null; try {
		 * response = getSearchResponse(client, pageNum, pageSize, queryBuilder, index,
		 * sortField, sortOrder, highlight); } catch (IOException e) {
		 * e.printStackTrace(); } SearchHits hits = response.getHits(); List<Map<String,
		 * Object>> data = getDataList(hits); return data; }
		 * 
		 * public SearchResult searchDataPage(int pageNum, int rows, QueryBuilder
		 * queryBuilder, String index, String sortField, SortOrder sortOrder, Boolean
		 * highlight) { RestHighLevelClient client = esJdbc.getRestHighLevelClient();
		 * SearchResponse response = null; try { response = getSearchResponse(client,
		 * pageNum, rows, queryBuilder, index, sortField, sortOrder, highlight); } catch
		 * (IOException e) { e.printStackTrace(); } SearchHits hits =
		 * response.getHits(); Integer total =
		 * Math.toIntExact(hits.getTotalHits().value); List<Map<String, Object>>
		 * dataList = getDataList(hits); return new SearchResult(dataList,total); }
		 * 
		 * private static SearchResponse getSearchResponse(RestHighLevelClient client,
		 * int start, int rows, QueryBuilder queryBuilder, String index, String
		 * sortField, SortOrder sortOrder, Boolean highlight) throws IOException { //
		 * int start = pageNum * rows; // int end = pageNum * rows; // //.keyword
		 * SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
		 * sourceBuilder.query(queryBuilder); sourceBuilder.from(start); // 获取记录数，默认10
		 * sourceBuilder.size(rows); if (StringUtils.isNotBlank(sortField) && sortOrder
		 * != null) { sourceBuilder.sort(sortField, sortOrder); }
		 * 
		 * if (highlight == null || highlight) { //设置高亮显示 HighlightBuilder
		 * highlightBuilder = new HighlightBuilder().field("*");
		 * highlightBuilder.preTags("<span style=\"color:red\">");
		 * highlightBuilder.postTags("</span>");
		 * sourceBuilder.highlighter(highlightBuilder); }
		 * 
		 * 
		 * SearchRequest searchRequest = new SearchRequest(index); //
		 * searchRequest.types(ES_TYPE); searchRequest.source(sourceBuilder); return
		 * client.search(searchRequest, RequestOptions.DEFAULT); }
		 * 
		 * private static List<Map<String, Object>> getDataList(SearchHits hits) {
		 * List<Map<String, Object>> data = new ArrayList<>(); //遍历结果 for (SearchHit hit
		 * : hits) { Map<String, Object> source = hit.getSourceAsMap(); //处理高亮片段
		 * Map<String, HighlightField> highlightFields = hit.getHighlightFields();
		 * Set<Map.Entry<String, HighlightField>> entries = highlightFields.entrySet();
		 * Iterator<Map.Entry<String, HighlightField>> iterator = entries.iterator();
		 * while (iterator.hasNext()) { Map.Entry<String, HighlightField> next =
		 * iterator.next(); String key = next.getKey(); HighlightField value =
		 * next.getValue(); if (value != null) { Text[] fragments = value.fragments();
		 * StringBuilder nameTmp = new StringBuilder(); for (Text text : fragments) {
		 * nameTmp.append(text); } source.put(key, nameTmp.toString()); } }
		 * data.add(hit.getSourceAsMap()); } return data; }
		 * 
		 * public void bulkIndex(String esTable, List<Map<String, Object>> dataList) {
		 * RestHighLevelClient client = esJdbc.getRestHighLevelClient(); try {
		 * BulkRequest bulkRequest = new BulkRequest(); for (Map<String, Object> data :
		 * dataList) { bulkRequest.add(new IndexRequest(esTable).opType("create")
		 * .id(data.get("id").toString()).source(data)); } client.bulk(bulkRequest,
		 * RequestOptions.DEFAULT); } catch (Exception e) { e.printStackTrace(); } } }
		 */
/*
 * package net.sinodata.business.util.elasticsearch;
 * 
 * import org.elasticsearch.index.query.BoolQueryBuilder; import
 * org.elasticsearch.index.query.QueryBuilder; import
 * org.elasticsearch.index.query.QueryBuilders; import
 * org.elasticsearch.index.query.QueryStringQueryBuilder;
 * 
 * import java.util.Map; import java.util.regex.Matcher; import
 * java.util.regex.Pattern;
 * 
 *//**
	 * @Name ES QuaryBuilder工具类
	 * @Description ES QuaryBuilder工具类
	 * @Author zcb
	 * @Version V1.0.0
	 * @Since 1.0
	 * @Date 2019/11/01
	 *//*
		 * public class QueryBuilderUtil {
		 * 
		 * 
		 * public static final String EQ = "eq"; public static final String GT = "gt";
		 * public static final String GTE = "gte"; public static final String LT = "lt";
		 * public static final String LTE = "lte"; public static final String LIKE =
		 * "like"; public static final String IN = "in";
		 * 
		 * public static QueryBuilder build(String key, String value, String operation)
		 * { QueryBuilder queryBuilder; switch (operation) { case EQ: queryBuilder =
		 * buildEqQueryBuilder(key, value); break; case GT: queryBuilder =
		 * buildGtQueryBuilder(key, value); break; case GTE: queryBuilder =
		 * buildGteQueryBuilder(key, value); break; case LT: queryBuilder =
		 * buildLtQueryBuilder(key, value); break; case LTE: queryBuilder =
		 * buildLteQueryBuilder(key, value); break; case LIKE: queryBuilder =
		 * buildLikeQueryBuilder(key, value); break; case IN: queryBuilder =
		 * buildInQueryBuilder(key, value); break; default: queryBuilder =
		 * buildEqQueryBuilder(key, value); break; } return queryBuilder; }
		 * 
		 * private static QueryBuilder buildEqQueryBuilder(String key, String value) {
		 * QueryBuilder queryBuilder = QueryBuilders.termQuery(key + ".keyword", value);
		 * return queryBuilder; }
		 * 
		 * private static QueryBuilder buildGtQueryBuilder(String key, String value) {
		 * QueryBuilder queryBuilder = QueryBuilders.rangeQuery(key+
		 * ".keyword").gt(value); return queryBuilder; }
		 * 
		 * private static QueryBuilder buildGteQueryBuilder(String key, String value) {
		 * QueryBuilder queryBuilder = QueryBuilders.rangeQuery(key+
		 * ".keyword").gte(value); return queryBuilder; }
		 * 
		 * private static QueryBuilder buildLtQueryBuilder(String key, String value) {
		 * QueryBuilder queryBuilder = QueryBuilders.rangeQuery(key+
		 * ".keyword").lt(value); return queryBuilder; }
		 * 
		 * private static QueryBuilder buildLteQueryBuilder(String key, String value) {
		 * QueryBuilder queryBuilder = QueryBuilders.rangeQuery(key+
		 * ".keyword").lte(value); return queryBuilder; }
		 * 
		 * public static QueryBuilder buildLikeQueryBuilder(String key, String value) {
		 * boolean nameFlag = checkName(value); QueryBuilder queryBuilder; if (nameFlag)
		 * { queryBuilder = QueryBuilders.boolQuery(); ((BoolQueryBuilder)
		 * queryBuilder).should( QueryBuilders.matchPhraseQuery(key+ ".keyword",
		 * value)); ((BoolQueryBuilder) queryBuilder).should(
		 * QueryBuilders.wildcardQuery(key+ ".keyword", ("*" + value + "*"))); } else {
		 * queryBuilder = QueryBuilders.wildcardQuery(key+ ".keyword", ("*" + value +
		 * "*")); } return queryBuilder; }
		 * 
		 * public static QueryStringQueryBuilder getQueryStringWialdBuilder(String[]
		 * keyArr, String value) { String queryStr = ""; for (int i = 0; i <
		 * keyArr.length; i++) { queryStr+= keyArr[i]+".keyword:*"+ value +"* "; if(i!=
		 * keyArr.length-1){ queryStr+="OR "; } } return
		 * QueryBuilders.queryStringQuery(queryStr); }
		 * 
		 * private static QueryBuilder buildInQueryBuilder(String key, String value) {
		 * value = value.replaceAll("'", ""); String[] valueArr = value.split(",");
		 * BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery(); for (String v :
		 * valueArr) { QueryBuilder eqQueryBuilder = buildEqQueryBuilder(key, v);
		 * queryBuilder.should(eqQueryBuilder); } return queryBuilder; }
		 * 
		 * private static boolean checkName(String name) { String regex =
		 * "[\u4e00-\u9fa5]"; Pattern p = Pattern.compile(regex); Matcher m =
		 * p.matcher(name); if (m.find()) { return true; } return false; }
		 * 
		 * public static QueryBuilder buildSeoQueryBuilder(String query) { //
		 * QueryBuilder queryBuilder = QueryBuilders.queryStringQuery(query);
		 * QueryBuilder queryBuilder = QueryBuilders.wildcardQuery("es_content"+
		 * ".keyword", ("*" + query + "*")); return queryBuilder; }
		 * 
		 * public static QueryBuilder buildSeoDetailQueryBuilder(Map<String, Object>
		 * base) { String certCode = base.get("certCode") == null ? "" :
		 * base.get("certCode").toString(); QueryBuilder queryBuilder =
		 * QueryBuilderUtil.build("certCode", certCode, QueryBuilderUtil.EQ); return
		 * queryBuilder; } }
		 * 
		 * 
		 */
package net.sinodata.business.service;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;

public interface AuthAplService {

	/**
	 * 授权申请列表
	 */
	public SearchResult listAuthApl(Page page, Map<String, Object> condition);

	/**
	 * 服务方法选择列表
	 */
	public SearchResult listFwFf(Page page, Map<String, Object> condition);

	/**
	 * 添加授权申请
	 */
	public int addAuthApl(Map<String, Object> map);

	/**
	 * 查看图片
	 */
	Map<String, Object> getPicInfo(@Param("ID") String ID);

	/**
	 * 授权申请审核列表
	 */
	public SearchResult listSh(Page page, Map<String, Object> condition);

	/**
	 * 授权申请审核
	 */
	int updateSqzt(@Param("ID") String ID, @Param("ZT") String ZT);

	/**
	 * 服务资源授权
	 */
	int addFwzysq(@Param("YYBS") String YYBS, @Param("FWBS") String FWBS, @Param("FFBS") String FFBS);

}
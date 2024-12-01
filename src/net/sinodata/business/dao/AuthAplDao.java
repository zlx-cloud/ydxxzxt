package net.sinodata.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.poi.ss.formula.functions.T;

public interface AuthAplDao {

	/**
	 * 授权申请列表
	 */
	List<T> getListByPage(Map<String, Object> condition);

	int getCountByPage(Map<String, Object> condition);

	/**
	 * 服务方法选择列表
	 */
	List<T> getFwFfListByPage(Map<String, Object> condition);

	int getFwFfCountByPage(Map<String, Object> condition);

	/**
	 * 添加授权申请
	 */
	int addAuthApl(Map<String, Object> map);

	/**
	 * 查看图片
	 */
	Map<String, Object> getPicInfo(@Param("ID") String ID);

	/**
	 * 授权申请审核列表
	 */
	List<T> getShListByPage(Map<String, Object> condition);

	int getShCountByPage(Map<String, Object> condition);

	/**
	 * 授权申请审核
	 */
	int updateSqzt(@Param("ID") String ID, @Param("ZT") String ZT);

	/**
	 * 服务资源授权
	 */
	int addFwzysq(@Param("YYBS") String YYBS, @Param("FWBS") String FWBS, @Param("FFBS") String FFBS);

}
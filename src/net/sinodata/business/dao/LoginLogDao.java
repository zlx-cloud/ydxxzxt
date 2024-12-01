package net.sinodata.business.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

public interface LoginLogDao {

	// 登录日志
	int addLoginLog(@Param("userName") String userName, @Param("statue") String statue,
			@Param("loginTime") Date loginTime);

}
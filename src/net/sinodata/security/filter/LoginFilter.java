package net.sinodata.security.filter;

import java.util.Date;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sinodata.business.dao.LoginLogDao;
import net.sinodata.business.util.DateUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginFilter extends FormAuthenticationFilter{
	@Autowired
	private LoginLogDao dao;
	
	Logger logger = LoggerFactory.getLogger(LoginFilter.class);
	@Override
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		logger.info("login success--" + getUsername(request));
		
		dao.addLoginLog(getUsername(request), "success", 
				DateUtil.formatDateToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		
		String url = this.getSuccessUrl();
		logger.info("redictor to "+url);
		HttpServletRequest hreq = (HttpServletRequest)request;
		HttpServletResponse hresp= (HttpServletResponse)response;
		hresp.sendRedirect(hreq.getContextPath()+url);
		
		return false;
	}
	
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		logger.info("login fail--" + getUsername(request));
		try {
			if(!"".equals(getUsername(request))&&null!=getUsername(request)) {
				dao.addLoginLog(getUsername(request), "fail", 
						DateUtil.formatDateToDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String loginError="用户名或密码错误！";
		request.setAttribute("LOGIN_ERROR_MSG", loginError);
		
		return super.onLoginFailure(token, e, request, response);
	}
}

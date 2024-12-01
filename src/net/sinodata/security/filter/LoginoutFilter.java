package net.sinodata.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

//import net.sinodata.opt.common.vo.ShiroUser;


import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginoutFilter extends LogoutFilter{
	Logger logger = LoggerFactory.getLogger(LoginoutFilter.class);
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response)
			throws Exception {
		Subject subject = getSubject(request, response);
		logger.info("logout--"+subject.getPrincipal());
		
		return super.preHandle(request, response);
	}

}

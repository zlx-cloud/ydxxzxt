package net.sinodata.security.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.web.filter.authc.UserFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessFilter extends UserFilter{

	Logger logger = LoggerFactory.getLogger(UserFilter.class);
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		return super.onAccessDenied(request, WebUtils.toHttp(response));
	}
}

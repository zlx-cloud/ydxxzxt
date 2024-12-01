package net.sinodata.security.util;

import java.util.List;

import net.sinodata.business.entity.Fwcyfzcb;
import net.sinodata.business.service.UserTService;
import net.sinodata.business.util.MD5Utils;
import net.sinodata.business.util.StringUtil;
import net.sinodata.security.vo.ShiroUser;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ShiroDbRealm extends AuthorizingRealm {

	Logger logger = LoggerFactory.getLogger(ShiroDbRealm.class);
	@Autowired
	protected UserTService UserService;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {

		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		Fwcyfzcb user = null;
		String password = null;
		
			password = String.valueOf(token.getPassword());
			
		List<Fwcyfzcb> userlist = UserService.findUserByLoginName(token
				.getUsername());
		if (null != userlist && userlist.size() == 1) {
			user = userlist.get(0);
		}
		/*if (user != null) {
			String password = user.getFwcyfDlmm();
			ShiroUser shiroUser = new ShiroUser(user);
			return new SimpleAuthenticationInfo(shiroUser,
					password.toCharArray(), getName());
		} else {
			return null;
		}*/
		
		if (user != null) {
			ShiroUser shiroUser = new ShiroUser(user);
			if(StringUtil.isNotEmpty(password)){
				//if(user.getFwcyfDlmm().equals(password)){
				if(user.getFwcyfDlmm().equals(MD5Utils.getMD5String(password))){
					return new SimpleAuthenticationInfo(shiroUser, password, getName());
				}else{
					throw new AuthenticationException();
				}
			}else{
				throw new UnknownAccountException();
			}
		} else {
			 throw new AuthenticationException();
		}
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		/*
		 * ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		 * List<Role> userRoles =
		 * accountService.findRolesByLoginName(shiroUser.getLoginName());
		 * SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		 * List<String> roles = new ArrayList<String>();
		 * if(userRoles!=null&&userRoles.size()>0){ for(Role role:userRoles){
		 * roles.add(role.getCode()); } } info.addRoles(roles);
		 */
		return null;
	}

}


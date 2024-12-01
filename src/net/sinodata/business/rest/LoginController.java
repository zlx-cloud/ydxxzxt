package net.sinodata.business.rest;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/login")
public class LoginController {
	private static Logger logger = LoggerFactory.getLogger(LoginController.class);
	

	@RequestMapping(method = RequestMethod.GET)
	public String login() {
		logger.debug("login....");
		return "account/login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String fail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName, Model model) {
		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, userName);
		logger.debug("login..fail.."+FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		return "account/login";
	}
}

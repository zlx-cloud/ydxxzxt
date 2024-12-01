package net.sinodata.business.rest;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sinodata.business.service.MenuTService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/")
public class MainController {
	private static Logger logger = LoggerFactory.getLogger(MainController.class);
/*	@Autowired
	private MenuTDao menuDao;*/
	@Autowired
	MenuTService MenuTService;
	@RequestMapping(method = RequestMethod.GET)
	public String business(HttpServletRequest request) {
		/*List<Menu> menu=menuDao.findAllRecursion();*/
		JSONArray menu = null;
		try {
			menu = MenuTService.getMenuListByParentId("1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("userMenuList", menu);
		logger.info("business");
		return "index";
	}
}

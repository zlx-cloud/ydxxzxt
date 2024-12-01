package net.sinodata.business.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sinodata.business.service.JgdmbService;
import net.sinodata.business.util.ResponseUtil;
import net.sinodata.business.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class OrgTController {
	//private static Logger logger = LoggerFactory.getLogger(OrgTController.class);

	
	@Autowired
	JgdmbService JgdmbService;
	@RequestMapping(value = "/orgT", method = RequestMethod.GET)
	public String menuList(Model model) {

		return "account/orgManage";
	}
	
	 @RequestMapping(value="/orgT/treeGridOrg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
		@ResponseBody
		public void treeGridMenu( HttpServletRequest request,
				HttpServletResponse response) throws Exception {
		
			String jgmc=request.getParameter("jgmc");
			String jgbs=request.getParameter("jgbs");
			String sssjjg=request.getParameter("sssjjg");
			
			if(StringUtil.isNotEmpty(jgmc)||StringUtil.isNotEmpty(jgbs)){
				
				sssjjg="";
			}

		 JSONArray jsonArray=JgdmbService.queryJgdmTreeList(sssjjg,jgmc,jgbs);
			try {
				ResponseUtil.write(response, jsonArray);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			
	    	
	    

}

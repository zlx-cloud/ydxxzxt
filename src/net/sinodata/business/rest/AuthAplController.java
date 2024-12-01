package net.sinodata.business.rest;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import net.sf.json.JSONObject;
import net.sinodata.business.service.AuthAplService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;
import net.sinodata.business.util.StringUtil;
import net.sinodata.security.vo.ShiroUser;
import oracle.sql.BLOB;

@Controller
@RequestMapping(value = "/authapl")
public class AuthAplController {

	@Autowired
	AuthAplService authAplService;

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public String page(Model model) {
		return "business/authapl";
	}

	@RequestMapping(value = "/data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object data(Page page, HttpServletRequest request) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String fwbs = request.getParameter("fwbs");
		if (StringUtil.isNotEmpty(fwbs)) {
			condition.put("fwbs", fwbs);
		}
		String fwmc = request.getParameter("fwmc");
		if (StringUtil.isNotEmpty(fwmc)) {
			condition.put("fwmc", fwmc);
		}
		String ffbs = request.getParameter("ffbs");
		if (StringUtil.isNotEmpty(ffbs)) {
			condition.put("ffbs", ffbs);
		}
		String ffmc = request.getParameter("ffmc");
		if (StringUtil.isNotEmpty(ffmc)) {
			condition.put("ffmc", ffmc);
		}
		String roleId = ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getRoleid();
		if (!roleId.equals("1")) {
			condition.put("yybs", ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getId());
		}
		SearchResult result = authAplService.listAuthApl(page, condition);
		return result;
	}

	@RequestMapping(value = "/fwfflist", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object fwfflist(Page page, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String s_fwbs = request.getParameter("s_fwbs");
		String s_fwmc = request.getParameter("s_fwmc");
		String s_ffbs = request.getParameter("s_ffbs");
		String s_ffmc = request.getParameter("s_ffmc");
		int searchCount = 0;
		if (StringUtil.isNotEmpty(s_fwbs)) {
			condition.put("s_fwbs", s_fwbs);
			searchCount++;
		}
		if (StringUtil.isNotEmpty(s_fwmc)) {
			condition.put("s_fwmc", s_fwmc);
			searchCount++;
		}
		if (StringUtil.isNotEmpty(s_ffbs)) {
			condition.put("s_ffbs", s_ffbs);
			searchCount++;
		}
		if (StringUtil.isNotEmpty(s_ffmc)) {
			condition.put("s_ffmc", s_ffmc);
			searchCount++;
		}
		if(searchCount==0) {
			condition.put("count", 100);
		}
		condition.put("yybs", ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getId());
		SearchResult result = authAplService.listFwFf(page, condition);
		return result;
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject add(HttpServletRequest request, @RequestParam(value = "file") MultipartFile file)
			throws Exception {
		JSONObject result = new JSONObject();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", UUID.randomUUID().toString().replaceAll("-", ""));
		map.put("yybs", ((ShiroUser) SecurityUtils.getSubject().getPrincipal()).getId());
		map.put("fwbs", request.getParameter("fwbs"));
		map.put("ffbs", request.getParameter("ffbs"));
		map.put("sqyy", request.getParameter("sqyy"));
		map.put("sysj", request.getParameter("sysj"));

		if (!file.isEmpty()) {
			map.put("fileName", file.getOriginalFilename());
			map.put("fileContent", file.getBytes());
		}

		map.put("sqzt", "0");
		map.put("sqsj", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

		int count = authAplService.addAuthApl(map);
		if (count > 0) {
			result.put("success", "true");
		} else {
			result.put("errorMsg", "保存失败");
		}
		return result;
	}

	@RequestMapping(value = "/downPic", method = RequestMethod.GET)
	public void downPic(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ID = request.getParameter("ID");
		Map<String, Object> picInfo = authAplService.getPicInfo(ID);

		String filename = (String) picInfo.get("FILENAME");
		filename = URLEncoder.encode(filename, "UTF-8");

		byte[] data = null;
		InputStream ios = null;
		try {
			BLOB blob = ((BLOB) picInfo.get("FILECONTENT"));
			long nsize = blob.length();
			int Nsize = (int) nsize;
			ios = blob.getBinaryStream();
			data = new byte[Nsize];
			ios.read(data);
			ios.close();
			ServletOutputStream out = response.getOutputStream();
			response.setContentType("application/octet-stream;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
			out.write(data);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/lookImage", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void tree(HttpServletRequest request, HttpServletResponse response) {
		String ID = request.getParameter("ID");
		Map<String, Object> picInfo = authAplService.getPicInfo(ID);

		byte[] data = null;
		InputStream ios = null;

		try {
			BLOB blob = ((BLOB) picInfo.get("FILECONTENT"));
			long nsize = blob.length();
			int Nsize = (int) nsize;
			ios = blob.getBinaryStream();
			data = new byte[Nsize];
			ios.read(data);
			ios.close();
			ServletOutputStream out = response.getOutputStream();
			out.write(data);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/authsh", method = RequestMethod.GET)
	public String authsh(Model model) {
		return "business/authsh";
	}

	@RequestMapping(value = "/shData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object shData(Page page, HttpServletRequest request) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String fwbs = request.getParameter("fwbs");
		if (StringUtil.isNotEmpty(fwbs)) {
			condition.put("fwbs", fwbs);
		}
		String fwmc = request.getParameter("fwmc");
		if (StringUtil.isNotEmpty(fwmc)) {
			condition.put("fwmc", fwmc);
		}
		String ffbs = request.getParameter("ffbs");
		if (StringUtil.isNotEmpty(ffbs)) {
			condition.put("ffbs", ffbs);
		}
		String ffmc = request.getParameter("ffmc");
		if (StringUtil.isNotEmpty(ffmc)) {
			condition.put("ffmc", ffmc);
		}
		SearchResult result = authAplService.listAuthApl(page, condition);
		return result;
	}

	@RequestMapping(value = "shbtg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String shbtg(HttpServletRequest request) {
		String ID = request.getParameter("ID");
		String ZT = request.getParameter("ZT");
		int count = authAplService.updateSqzt(ID, ZT);
		if (count == 1) {
			return "success";
		} else {
			return "fail";
		}
	}

	@RequestMapping(value = "shtg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String shtg(HttpServletRequest request) {
		String ID = request.getParameter("ID");
		String ZT = request.getParameter("ZT");
		String YYBS = request.getParameter("YYBS");
		String FWBS = request.getParameter("FWBS");
		String FFBS = request.getParameter("FFBS");
		int count = authAplService.updateSqzt(ID, ZT);
		if (count == 1) {
			authAplService.addFwzysq(YYBS, FWBS, FFBS);
			return "success";
		} else {
			return "fail";
		}
	}

}
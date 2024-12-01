package net.sinodata.business.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sinodata.business.dao.SqxxShowDao;
import net.sinodata.business.entity.SqxxShow;
import net.sinodata.business.service.SqxxShowService;
import net.sinodata.business.util.ExcelReaderDown;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;
import net.sinodata.business.util.StringUtil;

@Controller
@RequestMapping(value = "/sqxxshow")
public class SqxxShowController {

	@Autowired
	SqxxShowService service;

	@Autowired
	SqxxShowDao dao;

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public String page(Model model) {
		return "business/sqxxshow";
	}

	@RequestMapping(value = "/data", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object data(Page page, HttpServletRequest request) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String yybs = request.getParameter("yybs");
		if (StringUtil.isNotEmpty(yybs)) {
			condition.put("yybs", yybs);
		}
		String yymc = request.getParameter("yymc");
		if (StringUtil.isNotEmpty(yymc)) {
			condition.put("yymc", yymc);
		}

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
		SearchResult result = service.list(page, condition);
		request.getSession().setAttribute("sqxxSelectMap", condition);
		return result;
	}

	@RequestMapping(value = "/exportExcel")
	public Object exportExcel(HttpServletRequest request, HttpServletResponse response) {
		// 文件字符输出流
		FileOutputStream toClient = null;
		try {
			@SuppressWarnings("unchecked")
			Map<String, Object> condition = (Map<String, Object>) request.getSession().getAttribute("sqxxSelectMap");
			int rows = dao.getCountByPage(condition);
			condition.put("start", (1 * rows - rows));
			condition.put("end", rows);
			List<SqxxShow> dataset = dao.getListByPage(condition);
			// 得到项目路径
			String rootpath = request.getSession().getServletContext().getRealPath("/");
			// 文件名
			String fileName = "exportdata.xls";
			// 创建文件字符输出流（项目路径+路径+文件名）
			toClient = new FileOutputStream(rootpath + File.separator + "static" + File.separator + fileName);
			// 创建电子表格
			ExcelReaderDown<SqxxShow> export = new ExcelReaderDown<SqxxShow>();

			String[] headers = { "授权方标识", "授权方名称", "服务标识", "服务名称", "方法标识", "方法名称" };
			export.exportExcel("表格数据", headers, dataset, toClient, "yyyy-MM-dd");
			toClient.close();
			response.setContentType("text/html;setchar=utf-8");
			File file = new File(rootpath + File.separator + "static" + File.separator + fileName);
			HttpHeaders header = new HttpHeaders();
			header.setContentDispositionFormData("attachment", fileName);
			header.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), header, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
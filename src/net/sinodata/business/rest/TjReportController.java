package net.sinodata.business.rest;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;
import net.sinodata.business.service.TjReportService;

@Controller
@RequestMapping("/tjReport")
public class TjReportController {

	@Autowired
	TjReportService service;

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public String page(HttpServletRequest request) {

		// 应用总数、服务总数、方法总数
		List<Map<String, Object>> zlMap = service.getZl();
		for (Map<String, Object> map : zlMap) {
			request.setAttribute(map.get("NAME").toString(), map.get("COUNT").toString());
		}

		// 本月服务调用量统计Top10
		List<Map<String, Object>> fwzl = service.fwCountByMonth();
		List<String> names = new ArrayList<String>();
		List<String> values = new ArrayList<String>();
		for (Map<String, Object> i : fwzl) {
			names.add(i.get("FWMC").toString());
			values.add(i.get("NUM").toString());
		}
		request.setAttribute("names", JSON.toJSON(names));
		request.setAttribute("values", JSON.toJSON(values));

		// 本月各分局服务调用量统计Top10
		List<Map<String, Object>> jgzl = service.jgCountByMonth();
		List<String> jgNames = new ArrayList<String>();
		List<String> jgValues = new ArrayList<String>();
		for (Map<String, Object> i : jgzl) {
			jgNames.add(i.get("JGMC").toString());
			jgValues.add(i.get("NUM").toString());
		}
		request.setAttribute("jgNames", JSON.toJSON(jgNames));
		request.setAttribute("jgValues", JSON.toJSON(jgValues));

		return "business/tjReport";
	}

	@RequestMapping(value = "/downReport", method = RequestMethod.GET)
	public void downReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// word参数
		Map<String, Object> reportMap = new HashMap<String, Object>();

		// 标题
		String title = new SimpleDateFormat("YYYY年MM月").format(new Date()) + " 统计报告";
		reportMap.put("title", title);

		// 应用总数、服务总数、方法总数
		List<Map<String, Object>> zlMap = service.getZl();
		for (Map<String, Object> map : zlMap) {
			reportMap.put(map.get("NAME").toString(), map.get("COUNT").toString());
		}

		// 本月服务调用量统计Top10
		List<Map<String, Object>> fwMap = service.fwCountByMonth();
		int fwMapCount = fwMap.size();
		if (fwMapCount == 0) {
			for (int i = 0; i < 10; i++) {
				reportMap.put("fw_name_" + i, '-');
				reportMap.put("fw_count_" + i, '-');
			}
		}
		if (fwMapCount > 0 && fwMapCount < 10) {
			for (int i = 0; i < fwMapCount; i++) {
				reportMap.put("fw_name_" + i, fwMap.get(i).get("FWMC").toString());
				reportMap.put("fw_count_" + i, fwMap.get(i).get("NUM").toString());
			}
			for (int i = fwMapCount; i < 10; i++) {
				reportMap.put("fw_name_" + i, '-');
				reportMap.put("fw_count_" + i, '-');
			}
		}
		if (fwMapCount == 10) {
			for (int i = 0; i < 10; i++) {
				reportMap.put("fw_name_" + i, fwMap.get(i).get("FWMC").toString());
				reportMap.put("fw_count_" + i, fwMap.get(i).get("NUM").toString());
			}
		}

		// 本月各分局服务调用量统计Top10
		List<Map<String, Object>> jgMap = service.jgCountByMonth();
		int jgMapCount = jgMap.size();
		if (jgMapCount == 0) {
			for (int i = 0; i < 10; i++) {
				reportMap.put("org_name_" + i, '-');
				reportMap.put("org_count_" + i, '-');
			}
		}
		if (jgMapCount > 0 && jgMapCount < 10) {
			for (int i = 0; i < jgMapCount; i++) {
				reportMap.put("org_name_" + i, jgMap.get(i).get("JGMC").toString());
				reportMap.put("org_count_" + i, jgMap.get(i).get("NUM").toString());
			}
			for (int i = jgMapCount; i < 10; i++) {
				reportMap.put("org_name_" + i, '-');
				reportMap.put("org_count_" + i, '-');
			}
		}
		if (jgMapCount == 10) {
			for (int i = 0; i < 10; i++) {
				reportMap.put("org_name_" + i, jgMap.get(i).get("JGMC").toString());
				reportMap.put("org_count_" + i, jgMap.get(i).get("NUM").toString());
			}
		}

		String path = request.getSession().getServletContext().getRealPath("/") + File.separator + "static"
				+ File.separator + "word" + File.separator;
		try {
			Configuration configuration = new Configuration(new Version("2.3.0"));
			configuration.setDefaultEncoding("UTF-8");
			configuration.setDirectoryForTemplateLoading(new File(path));

			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

			Template template = configuration.getTemplate("report.ftl", "UTF-8");
			Writer out = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
			template.process(reportMap, out);
			out.close();

			response.setContentType("application/msword");
			response.setHeader("Content-Disposition", "attachment; filename=report.doc");
			response.setContentLength(outputStream.size());
			OutputStream outputstream = response.getOutputStream();
			outputStream.writeTo(outputstream);
			outputStream.close();
			outputstream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/tjfxNew", method = RequestMethod.GET)
	public String tjfxNew() {
		return "business/tjfxNew";
	}

	@RequestMapping(value = "/tjsz", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object tjsz() {
		List<Map<String, Object>> result = service.tjsz();
		return result;
	}

	@RequestMapping(value = "/zcyhlRankByTimeAndJg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object zcyhlRankByTimeAndJg() {
		List<Map<String, Object>> result = service.zcyhlRankByTimeAndJg();
		return result;
	}

	@RequestMapping(value = "/yhdllRankByTimeAndJg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object yhdllRankByTimeAndJg() {
		List<Map<String, Object>> result = service.yhdllRankByTimeAndJg();
		return result;
	}

	@RequestMapping(value = "/fwzclByJg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object fwzclByJg() {
		List<Map<String, Object>> result = service.fwzclByJg();
		return result;
	}

	@RequestMapping(value = "/fwzyRankByCzlx", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object fwzyRankByCzlx() {
		List<Map<String, Object>> result = service.fwzyRankByCzlx();
		return result;
	}

	@RequestMapping(value = "/fwzyRankByYy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object fwzyRankByYy() {
		List<Map<String, Object>> result = service.fwzyRankByYy();
		return result;
	}

	@RequestMapping(value = "/bwcjlRankByJg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object bwcjlRankByJg() {
		List<Map<String, Object>> result = service.bwcjlRankByJg();
		return result;
	}
	
	@RequestMapping(value = "/fwzyRankByJzfl", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object fwzyRankByJzfl() {
		List<Map<String, Object>> result = service.fwzyRankByJzfl();
		return result;
	}
	
	@RequestMapping(value = "/qqlRankBySjAndJg", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object qqlRankBySjAndJg() {
		List<Map<String, Object>> result = service.qqlRankBySjAndJg();
		return result;
	}

	@RequestMapping(value = "/fwzydylRank", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object fwzydylRank() {
		List<Map<String, Object>> result = service.fwzydylRank();
		return result;
	}
	
	@RequestMapping(value = "/yyxtdylRank", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object yyxtdylRank() {
		List<Map<String, Object>> result = service.yyxtdylRank();
		return result;
	}
	
	@RequestMapping(value = "/zdyyqqlRank", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object zdyyqqlRank() {
		List<Map<String, Object>> result = service.zdyyqqlRank();
		return result;
	}
	
	@RequestMapping(value = "/fwzyyclRank", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object fwzyyclRank() {
		List<Map<String, Object>> result = service.fwzyyclRank();
		return result;
	}
	
	@RequestMapping(value = "/fwzysygfsdRank", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object fwzysygfsdRank() {
		List<Map<String, Object>> result = service.fwzysygfsdRank();
		return result;
	}
	
	@RequestMapping(value = "/fwzyxysjRank", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object fwzyxysjRank() {
		List<Map<String, Object>> result = service.fwzyxysjRank();
		return result;
	}
	
}
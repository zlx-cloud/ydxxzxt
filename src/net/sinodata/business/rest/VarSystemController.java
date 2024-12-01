package net.sinodata.business.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sinodata.esb.entity.EsbVarRelation;
import net.sinodata.esb.entity.EsbVarSystem;
import net.sinodata.business.service.EsbVarSystemService;
import net.sinodata.business.service.FwStreamService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.ResponseUtil;
import net.sinodata.business.util.SearchResult;
import net.sinodata.business.util.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/varSystem")
public class VarSystemController {
	
	@Autowired
	private EsbVarSystemService esbVarSystemService;
	
	@Autowired
	private FwStreamService fwStreamService;
	
	@RequestMapping(value="/show", method = RequestMethod.GET)
	public String showVarSystem(Model model) {
		
		return "business/varSystemManage";
	}

	@RequestMapping(value = "/list", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object list(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String varName=request.getParameter("varName");
		
		if(StringUtil.isNotEmpty(varName)){
			
			condition.put("varName",varName);
		}
		SearchResult result = esbVarSystemService.esbVarSystemList(page, condition);
		return result;
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public void createRole(EsbVarSystem esbVarSystem, HttpServletRequest request,
			HttpServletResponse response) {
		JSONObject result=new JSONObject();
		String isLoad = request.getParameter("isLoad");
		
			if(StringUtil.isNotEmpty(esbVarSystem.getVarId())){
				/*int num = esbVarSystemService.queryStreamCount(esbVarSystem.getVarId());
				if(num>0){
					result.put("success", true);
					result.put("errorMsg", "该接口地址已被使用，不可修改");
				}else{*/
				
					esbVarSystemService.updateVarSystem(esbVarSystem);
					if("1".equals(isLoad)){
						List<EsbVarRelation> streamList = esbVarSystemService.queryStreamByVarId(esbVarSystem.getVarId());
						if(streamList!=null && streamList.size()>0){
							for(EsbVarRelation stream:streamList){
								if(fwStreamService.getEsbStatus(stream.getPid()) && !"esb_opt".equals(stream.getPid())){
									fwStreamService.stopEsb(stream.getPid());
									fwStreamService.startEsb(stream.getPid());
								}
							}
						}
					}
					result.put("success", true);
				//}
			}else{
				esbVarSystemService.addVarSystem(esbVarSystem);
				result.put("success", true);
			}
			
		
		try {
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/delete",method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void deleteRole(HttpServletRequest request,
			HttpServletResponse response,String delIds) {
		JSONObject result=new JSONObject();
		try {
			if(delIds!=null){
				String[] ids = delIds.split(",");
				for(int i=0;i<ids.length;i++){
					String varId = ids[i];
					int num = esbVarSystemService.queryStreamCount(varId);
					if(num>0){
						
						result.put("errorMsg", "接口地址已被使用，不可删除");
					}else{
						int delNums = esbVarSystemService.deleteVarSystem(ids);
						if(delNums>0){
							result.put("success", true);
							result.put("delNums", delNums);
						}else{
							result.put("errorMsg", "删除失败");
						}	
					}
				}
				
			}
			ResponseUtil.write(response, result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@RequestMapping(value="/showStreamByVarId", method = RequestMethod.GET)
	public String showStreamByVarId(Model model,HttpServletRequest request) {
		
		return "business/varSystemManage";
	}
	
	@RequestMapping(value = "/listByVarId", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object listByVarId(Page page, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> condition = new HashMap<String, Object>();
		String var_id=request.getParameter("var_id");
		
		if(StringUtil.isNotEmpty(var_id)){
			
			condition.put("var_id",var_id);
		}
		SearchResult result = esbVarSystemService.esbVarSystemList(page, condition);
		return result;
	}
	
}

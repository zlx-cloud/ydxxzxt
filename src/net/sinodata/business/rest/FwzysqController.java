package net.sinodata.business.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sinodata.business.entity.Fwzyffzcb;
import net.sinodata.business.entity.Fwzysqb;
import net.sinodata.business.entity.FwzysqbKey;
import net.sinodata.business.service.FwzyffzcbService;
import net.sinodata.business.service.FwzyqqbwcjbService;
import net.sinodata.business.service.FwzysqbService;
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
@RequestMapping(value = "/fwSqManage")
public class FwzysqController {
	//private static Logger logger = LoggerFactory.getLogger(FwzysqController.class);

	
	@Autowired
	FwzysqbService FwzysqbService;
	@Autowired
	FwzyffzcbService FwzyffzcbService;
	@Autowired
	private FwzyqqbwcjbService fwzyqqbwcjbService;
	
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public String menuList(Model model, HttpServletRequest request) {
		request.setAttribute("fwcyf", fwzyqqbwcjbService.selectAllFwcyfzcb());
		return "business/fwSqManage";
	}
	
	
	@RequestMapping(value = "/tree", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public void tree( HttpServletRequest request,
			HttpServletResponse response) {
		String fwbs=request.getParameter("fwbs");
		JSONArray jsonArray=FwzysqbService.queryTreeList(fwbs);
		try {
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  	
	@RequestMapping(value = "/fwSq", method = RequestMethod.POST)
	@ResponseBody
	public void fwSq(HttpServletRequest request,
			HttpServletResponse response) {
		int updateNums=0;
		JSONObject result=new JSONObject();
		String fwsqIds=request.getParameter("fwsqIds");
		String fwbs=request.getParameter("fwbs");
		List<String> oldlist=new ArrayList<String>();
		List<String> newlist=new ArrayList<String>();
		List<Fwzysqb> l= FwzysqbService.queryFwSqByFwcyfYyxtbh(null, fwbs);
		
		if(StringUtil.isNotEmpty(fwsqIds)&&l.size()>0){
			for(String index:fwsqIds.split(",")){
				newlist.add(index);
			}
		
		for(Fwzysqb s:l){
			oldlist.add(s.getFwcyfYyxtbh());
		}
		
		/**
         * 比较新旧id,获取要添加的id和要移除的id
         */
		 Map<String, Object> all=	this.compareArry(oldlist, newlist);
		List<String> deteleIds= (List<String>) all.get("delete_arry");// 要移除的角色id
		List<String> addIds=(List<String>)  all.get("add_arry");// 要添加的角色id
		List<String> common_date=(List<String>)  all.get("common_date");//
		
		if(deteleIds.size()>0){
			for(String str:deteleIds){
				FwzysqbKey FwzysqbKey=new FwzysqbKey();
				FwzysqbKey.setFfbs(null);
				FwzysqbKey.setFwbs(fwbs);
				FwzysqbKey.setFwcyfYyxtbh(str);
				int i= FwzysqbService.deleteByPrimaryKey(FwzysqbKey);
				updateNums+=i;
			}
			
		}
		if(addIds.size()>0){
			for(String str:addIds){
				List<Fwzyffzcb> list=FwzyffzcbService.queryListByFwbs(fwbs);
				for(Fwzyffzcb Fwzyffzcb:list){
						Fwzysqb Fwzysqb=new Fwzysqb();
						Fwzysqb.setFwbs(fwbs);
						Fwzysqb.setFfbs(Fwzyffzcb.getFfbs());
						Fwzysqb.setFwcyfYyxtbh(str);
					int	j= FwzysqbService.insertSelective(Fwzysqb);
						updateNums+=j;
				
			}
			}
		}
		
		}else{
			if(!StringUtil.isNotEmpty(fwsqIds)&&l.size()==0){
				updateNums=-1;
			}else{
			if(!StringUtil.isNotEmpty(fwsqIds)){
				FwzysqbKey FwzysqbKey=new FwzysqbKey();
				FwzysqbKey.setFfbs(null);
				FwzysqbKey.setFwbs(fwbs);
				FwzysqbKey.setFwcyfYyxtbh(null);
				updateNums= FwzysqbService.deleteByPrimaryKey(FwzysqbKey);
				if(updateNums==0){
					updateNums=1;
				}
			}
			if(l.size()==0){
				List<Fwzyffzcb> list=FwzyffzcbService.queryListByFwbs(fwbs);
				
					for(String index:fwsqIds.split(",")){
					
					for(Fwzyffzcb Fwzyffzcb:list){
						Fwzysqb Fwzysqb=new Fwzysqb();
						Fwzysqb.setFwbs(fwbs);
						Fwzysqb.setFfbs(Fwzyffzcb.getFfbs());
						Fwzysqb.setFwcyfYyxtbh(index);
						int k= FwzysqbService.insertSelective(Fwzysqb);
						updateNums+=k;
					}
					
				
			}
		}
		}
		}
		try {
			
			  if(updateNums>0){
			  	result.put("success", true);
				}else{
					if(updateNums==-1){
						result.put("errorMsg", "请选择要授权的参与方!");
					}else{
					result.put("errorMsg", "授权失败");
				}
					
				}
				ResponseUtil.write(response, result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 
	
	
	/**
     * 
     * 比较2个数组，获取相对于数组1相同的元素、相对于数组2相同的元素，2个数组相同的元素
     *
     * @param t1 旧的数组
     * @param t2 新的数组
     * 
     * @return 返回3种类型的集合
     * 
     * @date 2016年8月26日 下午5:34:49
     */
    public static Map<String, Object> compareArry(List<String> t1, List<String> t2) {
        /**
         * 集合A (新的数据有，旧的数据没有，即为要添加的数据)
         */
        List<String> listA=new ArrayList<String>();
        for (String str1:t2) {//遍历新的数组元素
            if (!t1.contains(str1)) {//旧的数组中不包含新的元素（即为要添加的元素）
                listA.add(str1);
            }
        }

        /**
         * 集合B (旧的数据中有，新的数据没有，即为要删除的数据)
         */
        List<String> listB = new ArrayList<String>();
        for(String str2:t1){//遍历旧的数组元素
            if( !t2.contains(str2)){//新的数组中不包含旧的数组（即为要删除的元素）
                listB.add(str2);
            }
        }

        /**
         * 集合C (旧的数据中有，新的数据有，即为共同的数据)
         */
        List<String> listC = new ArrayList<String>();
        for(String str3:t1){//遍历旧的数组元素
            if( t2.contains(str3)){//新的数组中包含旧的数组（即为共同的元素）
                listC.add(str3);
            }
        }


        Map<String, Object> map=new HashMap<String, Object>();
        map.put("add_arry", listA);
        map.put("delete_arry", listB);
        map.put("common_date", listC);
        return map;
    }
}

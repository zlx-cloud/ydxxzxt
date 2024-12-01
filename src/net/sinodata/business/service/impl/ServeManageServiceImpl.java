package net.sinodata.business.service.impl;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sinodata.business.dao.FwcyfzcbDao;
import net.sinodata.business.dao.FwzyzcbDao;
import net.sinodata.business.dao.JgdmbDao;
import net.sinodata.business.entity.Fwcyfzcb;
import net.sinodata.business.entity.Fwzyfffhcsb;
import net.sinodata.business.entity.Fwzyffqqcsb;
import net.sinodata.business.entity.Fwzyffzcb;
import net.sinodata.business.entity.Fwzyzcb;
import net.sinodata.business.entity.IntefaceFwzyzcb;
import net.sinodata.business.entity.InterfaceFwzysqb;
import net.sinodata.business.service.ServeManageService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;
import net.sinodata.security.vo.ShiroUser;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServeManageServiceImpl implements ServeManageService {

	@Autowired
	private FwzyzcbDao fwzyzcbDao;
	@Autowired
	private FwcyfzcbDao fwcyfzcbDao;
	@Autowired
	private JgdmbDao jgdmbDao;
	@Override
	public SearchResult fwzyzcbList(Page page, Map<String, Object> condition) {
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<Fwzyzcb> data = fwzyzcbDao.getFwzyzcbListByPage(condition);
		int count = fwzyzcbDao.getFwzyzcbCountByPage(condition);
		return new SearchResult(data, count);
	}

	
	
	
	
	@Override
	public JSONArray queryTreeList() {
		JSONArray jsonArray = new JSONArray();
		   List<Fwcyfzcb> listFwcyfzcb=fwcyfzcbDao.queryAllList(null,null);
		   for(Fwcyfzcb fwcyfzcb:listFwcyfzcb){
				JSONObject jsonObject = new JSONObject();
			
				jsonObject.put("text", jgdmbDao.selectByPrimaryKey(fwcyfzcb.getFwcyfSsfj()).getJgmc());
				jsonObject.put("id", fwcyfzcb.getFwcyfSsfj());
				List<Fwcyfzcb> listmc=fwcyfzcbDao.queryAllList(null,fwcyfzcb.getFwcyfSsfj());
				
				if(listmc.size()>0){
					jsonObject.put("state", "closed");
				}
				JSONArray jsonArraymc = new JSONArray();
				for (Fwcyfzcb fwcyfzcbmc : listmc) {
				
					JSONObject jsonObjectmc = new JSONObject();
					jsonObjectmc.put("id", fwcyfzcbmc.getFwcyfYyxtbh());
					jsonObjectmc.put("text", fwcyfzcbmc.getFwcyfYyxtmc());
					
					jsonArraymc.add(jsonObjectmc);
				
					jsonObject.put("children", jsonArraymc);
					
				}
				jsonArray.add(jsonObject);	
		   }
			// TODO Auto-generated method stub
			return jsonArray;
	}





	@Override
	public List<Fwzyzcb> getFwzyzcbListByPage(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return fwzyzcbDao.getFwzyzcbListByPage(condition);
	}

	@Override
	public int getFwzyzcbCountByPage(Map<String, Object> condition) {
		// TODO Auto-generated method stub
		return fwzyzcbDao.getFwzyzcbCountByPage(condition);
	}

	@Override
	public JSONArray queryTreeList(String flag) {
		
		JSONArray jsonArray = new JSONArray();
		String fwcyfYyxtbh=null;
		ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		if(!shiroUser.getRoleid().equals("1")&&(flag.equals("fwzcffqqfh")||flag.equals("fwzc")||flag.equals("fwRegzc"))){
			fwcyfYyxtbh=shiroUser.getId();
		}
        
		
		List<Fwcyfzcb> listFwcyfzcb=fwcyfzcbDao.queryAllList(fwcyfYyxtbh,null);
		for(Fwcyfzcb fwcyfzcb:listFwcyfzcb){
			JSONObject jsonObject = new JSONObject();
		
			jsonObject.put("text", jgdmbDao.selectByPrimaryKey(fwcyfzcb.getFwcyfSsfj()).getJgmc());
			String id="";
			List<Fwcyfzcb> listmc=fwcyfzcbDao.queryAllList(fwcyfYyxtbh,fwcyfzcb.getFwcyfSsfj());
			
			if(listmc.size()>0){
				jsonObject.put("state", "closed");
			}
			JSONArray jsonArraymc = new JSONArray();
			for (Fwcyfzcb fwcyfzcbmc : listmc) {
				JSONObject jsonObjectmc = new JSONObject();
				if(flag.equals("fwRegzc")){
					
					id=fwcyfzcb.getFwcyfSsfj();
					jsonObjectmc.put("id", fwcyfzcbmc.getFwcyfYyxtbh());
				}else{
					id+="'"+fwcyfzcbmc.getFwcyfYyxtbh()+"',";
					jsonObjectmc.put("id", "'"+fwcyfzcbmc.getFwcyfYyxtbh()+"'");
				}
				jsonObjectmc.put("text", fwcyfzcbmc.getFwcyfYyxtmc());
				
				JSONArray jsonArrayfwzyzcb = new JSONArray();
				List<Fwzyzcb> list = fwzyzcbDao.queryAllList(fwcyfzcbmc.getFwcyfYyxtbh());
				
				for (Fwzyzcb fwzyzcb : list) {
					JSONObject jsonObjectfwzyzcb = new JSONObject();
					jsonObjectfwzyzcb.put("id", fwzyzcb.getFwbs());
					jsonObjectfwzyzcb.put("text", fwzyzcb.getFwmc());
					
					
					
					List<Fwzyffzcb> listFwzyffzcb = fwzyzcb.getFwzyffzcbList();
					if (listFwzyffzcb.size() > 0) {
						jsonObjectfwzyzcb.put("state", "closed");
						JSONArray jsonArrayfwzyffzcb = new JSONArray();
						for (Fwzyffzcb fwzyffzcb : listFwzyffzcb) {
							JSONObject jsonObjectfwzyffzcb = new JSONObject();
							jsonObjectfwzyffzcb.put("id", fwzyffzcb.getFfbs());
							jsonObjectfwzyffzcb.put("text", fwzyffzcb.getFfms());
							
							List<Fwzyffqqcsb> listFwzyffqqcsb = fwzyffzcb
									.getFwzyffqqcsbList();
							List<Fwzyfffhcsb> listFwzyfffhcsb = fwzyffzcb
									.getFwzyfffhcsbList();
							JSONArray jsonArrayqqfh = new JSONArray();
							if (listFwzyffqqcsb.size() > 0) {
								jsonObjectfwzyffzcb.put("state", "closed");
								JSONArray jsonArrayfwzyffqqcsb = new JSONArray();
								JSONObject jsonObjectqq = new JSONObject();
								jsonObjectqq.put("id", "");
								jsonObjectqq.put("text", "请求参数");
								
								for(Fwzyffqqcsb fwzyffqqcsb:listFwzyffqqcsb){
									jsonObjectqq.put("state", "closed");
									JSONObject jsonObjectfwzyffqqcsb = new JSONObject();
									jsonObjectfwzyffqqcsb.put("id", fwzyffqqcsb.getQqcsbs());
									jsonObjectfwzyffqqcsb.put("text", fwzyffqqcsb.getQqcsm());
									jsonArrayfwzyffqqcsb.add(jsonObjectfwzyffqqcsb);
								}
								jsonObjectqq.put("children", jsonArrayfwzyffqqcsb);
								jsonArrayqqfh.add(jsonObjectqq);
							}
							if (listFwzyfffhcsb.size() > 0) {
								JSONArray jsonArrayfwzyfffhcsb = new JSONArray();
							JSONObject jsonObjectfh = new JSONObject();
							jsonObjectfh.put("id", "");
							jsonObjectfh.put("text", "返回参数");
							
							for(Fwzyfffhcsb fwzyfffhcsb:listFwzyfffhcsb){
								jsonObjectfh.put("state", "closed");
								JSONObject jsonObjectfwzyfffhcsb = new JSONObject();
								jsonObjectfwzyfffhcsb.put("id", fwzyfffhcsb.getFhcsbs());
								jsonObjectfwzyfffhcsb.put("text", fwzyfffhcsb.getFhcsm());
								jsonArrayfwzyfffhcsb.add(jsonObjectfwzyfffhcsb);
							}
							jsonObjectfh.put("children", jsonArrayfwzyfffhcsb);
							jsonArrayqqfh.add(jsonObjectfh);
							}
							
							if(flag.equals("fwml")){
								jsonObjectfwzyffzcb.put("children", jsonArrayqqfh);
								jsonArrayfwzyffzcb.add(jsonObjectfwzyffzcb);
								jsonObjectfwzyzcb.put("children", jsonArrayfwzyffzcb);
							}
							if(flag.equals("fwzcffqqfh")){
								jsonObjectfwzyffzcb.put("state", "open");
								jsonArrayfwzyffzcb.add(jsonObjectfwzyffzcb);
								jsonObjectfwzyzcb.put("children", jsonArrayfwzyffzcb);
							}
							if(flag.equals("fwzc")){
								jsonObjectfwzyzcb.put("state", "open");
							}
						}
					
					}
					jsonArrayfwzyzcb.add(jsonObjectfwzyzcb);
					
					if(!flag.equals("fwRegzc")){
						jsonObjectmc.put("children", jsonArrayfwzyzcb);
					}
					
					}
				
				
				jsonArraymc.add(jsonObjectmc);
				jsonObject.put("id", id.substring(0, id.length()-1));
				jsonObject.put("children", jsonArraymc);
			}
			jsonArray.add(jsonObject);	
			}
			
			
			
			
		return jsonArray;
	}

	@Override
	public List<Map<String, String>> queryfwzyzxdz(String jgbs) {
		// TODO Auto-generated method stub
		return fwzyzcbDao.queryfwzyzxdz(jgbs);
	}





	@Override
	public int deleteByPrimaryKey(String fwbs) {
		// TODO Auto-generated method stub
		return fwzyzcbDao.deleteByPrimaryKey(fwbs);
	}

	@Override
	public int insert(Fwzyzcb record) {
		// TODO Auto-generated method stub
		return fwzyzcbDao.insert(record);
	}

	@Override
	public int insertSelective(Fwzyzcb record) {
		// TODO Auto-generated method stub
		return fwzyzcbDao.insertSelective(record);
	}

	@Override
	public Fwzyzcb selectByPrimaryKey(String fwbs) {
		// TODO Auto-generated method stub
		return fwzyzcbDao.selectByPrimaryKey(fwbs);
	}

	@Override
	public int updateByPrimaryKeySelective(Fwzyzcb record) {
		// TODO Auto-generated method stub
		return fwzyzcbDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Fwzyzcb record) {
		// TODO Auto-generated method stub
		return fwzyzcbDao.updateByPrimaryKey(record);
	}

	@Override
	public int insertByBatch(List<Fwzyzcb> Fwzyzcb) {
		// TODO Auto-generated method stub
		return fwzyzcbDao.insertByBatch(Fwzyzcb);
	}




	//接口
	@Override
	public List<IntefaceFwzyzcb> queryFW(String FWBS, String FWMC) {
		return fwzyzcbDao.queryFW(FWBS, FWMC);
	}
	@Override
	public int startFW(String fwbss) {
		return fwzyzcbDao.startFW(fwbss);
	}
	@Override
	public int stopFW(String fwbss) {
		return fwzyzcbDao.stopFW(fwbss);
	}
	@Override
	public List<InterfaceFwzysqb> queryFWSQ(Map<String, Object> map) {
		return fwzyzcbDao.queryFWSQ(map);
	}
	@Override
	public int insertFWSQ(String FWCYFBH, String FWBS) {
		return fwzyzcbDao.insertFWSQ(FWCYFBH, FWBS);
	}
	@Override
	public int deleteFWSQ(Map<String, Object> map) {
		return fwzyzcbDao.deleteFWSQ(map);
	}





	@Override
	public List<Map<String, Object>> queryFwjkdytj() {
		return fwzyzcbDao.queryFwjkdytj();
	}

	@Override
	public List<Map<String, Object>> queryZxdytj() {
		return fwzyzcbDao.queryZxdytj();
	}

}

package net.sinodata.business.service.impl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sinodata.business.dao.FwcyfzcbDao;
import net.sinodata.business.dao.FwzysqbDao;
import net.sinodata.business.dao.JgdmbDao;
import net.sinodata.business.entity.Fwcyfzcb;
import net.sinodata.business.entity.Fwzysqb;
import net.sinodata.business.entity.FwzysqbKey;
import net.sinodata.business.service.FwzysqbService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FwzysqbServiceImpl implements FwzysqbService {
	@Autowired
	private FwzysqbDao fwzysqbDao;
	
	@Autowired
	private FwcyfzcbDao fwcyfzcbDao;
	@Autowired
	private JgdmbDao jgdmbDao;
	
	
	
	
	@Override
	public List<Fwzysqb> queryFwSqByFwcyfYyxtbh(String fwcyfYyxtbh, String fwbs) {
		// TODO Auto-generated method stub
		return fwzysqbDao.queryFwSqByFwcyfYyxtbh(fwcyfYyxtbh, fwbs);
	}

	@Override
	public JSONArray queryTreeList(String fwbs) {
       JSONArray jsonArray = new JSONArray();
	   List<Fwcyfzcb> listFwcyfzcb=fwcyfzcbDao.queryAllList(null,null);
	   for(Fwcyfzcb fwcyfzcb:listFwcyfzcb){
			JSONObject jsonObject = new JSONObject();
		
			jsonObject.put("text", jgdmbDao.selectByPrimaryKey(fwcyfzcb.getFwcyfSsfj()).getJgmc());
			jsonObject.put("id", "");
			List<Fwcyfzcb> listmc=fwcyfzcbDao.queryAllList(null,fwcyfzcb.getFwcyfSsfj());
			
			if(listmc.size()>0){
				jsonObject.put("state", "closed");
			}
			JSONArray jsonArraymc = new JSONArray();
			for (Fwcyfzcb fwcyfzcbmc : listmc) {
			
				JSONObject jsonObjectmc = new JSONObject();
				jsonObjectmc.put("id", fwcyfzcbmc.getFwcyfYyxtbh());
				jsonObjectmc.put("text", fwcyfzcbmc.getFwcyfYyxtmc());
				if(fwzysqbDao.queryFwSqByFwcyfYyxtbh(fwcyfzcbmc.getFwcyfYyxtbh(),fwbs).size()>0){
					jsonObjectmc.put("checked", true);
				}else{
					jsonObjectmc.put("checked", false);
				};
				jsonArraymc.add(jsonObjectmc);
			
				jsonObject.put("children", jsonArraymc);
				
			}
			jsonArray.add(jsonObject);	
	   }
		// TODO Auto-generated method stub
		return jsonArray;
	}

	@Override
	public int deleteByPrimaryKey(FwzysqbKey key) {
		// TODO Auto-generated method stub
		return fwzysqbDao.deleteByPrimaryKey(key);
	}

	@Override
	public int insert(Fwzysqb record) {
		// TODO Auto-generated method stub
		return fwzysqbDao.insert(record);
	}

	@Override
	public int insertSelective(Fwzysqb record) {
		// TODO Auto-generated method stub
		return fwzysqbDao.insertSelective(record);
	}

	@Override
	public Fwzysqb selectByPrimaryKey(FwzysqbKey key) {
		// TODO Auto-generated method stub
		return fwzysqbDao.selectByPrimaryKey(key);
	}

	@Override
	public int updateByPrimaryKeySelective(Fwzysqb record) {
		// TODO Auto-generated method stub
		return fwzysqbDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Fwzysqb record) {
		// TODO Auto-generated method stub
		return fwzysqbDao.updateByPrimaryKey(record);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	

	
	
}

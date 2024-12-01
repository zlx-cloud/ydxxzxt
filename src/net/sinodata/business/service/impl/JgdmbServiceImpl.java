package net.sinodata.business.service.impl;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sinodata.business.dao.JgdmbDao;
import net.sinodata.business.entity.Jgdmb;
import net.sinodata.business.service.JgdmbService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JgdmbServiceImpl implements JgdmbService {
	
	@Autowired
	private JgdmbDao jgdmbDao;

	
	public JSONArray getJgdmByParentId(String sssjjg,String jgmc,String jgbs)throws Exception{
		JSONArray jsonArray=new JSONArray();
		 List<Jgdmb> list=jgdmbDao.queryByJgbsAndsssjjg(sssjjg, jgmc, jgbs);
		 for(Jgdmb Jgdmb:list){
			 JSONObject jsonObject = new JSONObject();
				jsonObject.put("id", Jgdmb.getJgbs());
				jsonObject.put("text", Jgdmb.getJgmc());
				jsonObject.put("jgywmc", Jgdmb.getJgywmc());
				if(!hasChildren( Jgdmb.getJgbs())){
					jsonObject.put("state", "open");
				}else{
					jsonObject.put("state", "closed");				
				}
				jsonArray.add(jsonObject);
		 }
		 return jsonArray;
	}
	
	private boolean hasChildren(String sssjjg)throws Exception{
		 List<Jgdmb> list=jgdmbDao.queryByJgbsAndsssjjg(sssjjg,null,null);
		 if(list!=null){
			 if(list.size()==0){
				 return false;
			 } 
		 }else{
			 return false;
		 }
		
		return true;
	}
	@Override
	public JSONArray queryJgdmTreeList(String sssjjg,String jgmc,String jgbs) throws Exception {
		JSONArray jsonArray=this.getJgdmByParentId(sssjjg, jgmc, jgbs);
		/*for(int i=0;i<jsonArray.size();i++){
			JSONObject jsonObject=jsonArray.getJSONObject(i);
			if("open".equals(jsonObject.getString("state"))){
				continue;
			}else{
				jsonObject.put("children", queryJgdmTreeList(jsonObject.getString("id"),null, null));
			}
		}*/
		return jsonArray;
	}

	
	
	@Override
	public int deleteByPrimaryKey(String jgbs) {
		// TODO Auto-generated method stub
		return jgdmbDao.deleteByPrimaryKey(jgbs);
	}

	@Override
	public int insert(Jgdmb record) {
		// TODO Auto-generated method stub
		return jgdmbDao.insert(record);
	}

	@Override
	public int insertSelective(Jgdmb record) {
		// TODO Auto-generated method stub
		return jgdmbDao.insertSelective(record);
	}

	@Override
	public Jgdmb selectByPrimaryKey(String jgbs) {
		// TODO Auto-generated method stub
		return jgdmbDao.selectByPrimaryKey(jgbs);
	}

	@Override
	public int updateByPrimaryKeySelective(Jgdmb record) {
		// TODO Auto-generated method stub
		return jgdmbDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Jgdmb record) {
		// TODO Auto-generated method stub
		return jgdmbDao.updateByPrimaryKey(record);
	}

	
	
	

	
}

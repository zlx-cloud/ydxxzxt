package net.sinodata.business.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import net.sf.json.JSONArray;
import net.sinodata.business.entity.Fwzyzcb;
import net.sinodata.business.entity.IntefaceFwzyzcb;
import net.sinodata.business.entity.InterfaceFwzysqb;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;





public interface ServeManageService{

	int deleteByPrimaryKey(String fwbs);

    int insert(Fwzyzcb record);

    int insertSelective(Fwzyzcb record);

    Fwzyzcb selectByPrimaryKey(String fwbs);

    int updateByPrimaryKeySelective(Fwzyzcb record);

    int updateByPrimaryKey(Fwzyzcb record);
    
   List<Fwzyzcb> getFwzyzcbListByPage( Map<String, Object> condition);
	
	int getFwzyzcbCountByPage( Map<String, Object> condition);
	
	   public SearchResult fwzyzcbList(Page page,
				Map<String, Object> condition);
	   JSONArray queryTreeList(String flag);
	  
	   int insertByBatch(List<Fwzyzcb> Fwzyzcb);
	   
	   JSONArray queryTreeList();
	   
	   List<Map<String,String>> queryfwzyzxdz(String jgbs);
	   
	   //接口
   			//查询、启动、停止服务
	   		List<IntefaceFwzyzcb> queryFW(@Param("FWBS")String FWBS,@Param("FWMC")String FWMC);
	   		int startFW(@Param("fwbss")String fwbss);
	   		int stopFW(@Param("fwbss")String fwbss);
	   		//查询权限、添加权限、删除权限
	    	List<InterfaceFwzysqb> queryFWSQ(Map<String, Object> map);
	    	int insertFWSQ(@Param("FWCYFBH")String FWCYFBH,@Param("FWBS")String FWBS);
	    	int deleteFWSQ(Map<String, Object> map);
	    	
	    List<Map<String,Object>> queryFwjkdytj();
	    	 
	    List<Map<String,Object>> queryZxdytj();
}

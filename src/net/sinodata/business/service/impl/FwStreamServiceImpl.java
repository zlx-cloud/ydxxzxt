package net.sinodata.business.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apusic.esb.base.communication.CommClient;
import com.apusic.esb.base.communication.socket.client.CommSocketClient;
import com.apusic.esb.base.connector.InterfaceManager;
import com.apusic.esb.base.usermanager.IUserLoginManager;
import com.apusic.esb.base.usermanager.model.ESBUserToken;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sinodata.business.dao.FwOrgDao;
import net.sinodata.business.dao.FwStreamDao;
import net.sinodata.business.entity.FwOrg;
import net.sinodata.business.entity.FwStream;
import net.sinodata.business.service.FwStreamService;
import net.sinodata.business.util.Page;
import net.sinodata.business.util.SearchResult;
import net.sinodata.esb.dao.EsbMfInformationDao;
import net.sinodata.esb.entity.EsbMfInformation;

@Service
public class FwStreamServiceImpl implements FwStreamService {

	@Autowired
	private FwOrgDao fwOrgDao;
	
	@Autowired
	private FwStreamDao fwStreamDao;
	
	@Autowired
	private EsbMfInformationDao esbMfInformationDao;
	
	private CommClient client;
	
	@Override
	public JSONArray fwOrgList() {
		JSONArray jsonArray = getJson(null);
		
		return jsonArray;
	}
	
	private JSONArray getJson(String path){
		List<FwOrg> orgList = fwOrgDao.queryAllList(path);
		if(orgList!=null && orgList.size()>0){
			JSONArray jsonArray = new JSONArray();
			for(FwOrg org:orgList){
				if(org!=null){
					JSONObject childObject = new JSONObject();
					childObject.put("id", org.getId());
					childObject.put("text", org.getName());
					JSONArray childArray = getJson(org.getPath());
					childObject.put("children", childArray);
					jsonArray.add(childObject);
				}
			}
			return jsonArray;
		}
		return null;
	}
	
	public SearchResult streamList(Page page, Map<String, Object> condition){
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<FwStream> reList = fwStreamDao.queryStreamByPage(condition);
		if(reList!=null && reList.size()>0){
			String ids = "";
			for(FwStream fwStream:reList){
				if(fwStream!=null){
					int allNum = esbMfInformationDao.queryCountAllByPid(fwStream.getId());
					fwStream.setAllNum(allNum+"");
					int endedNum = esbMfInformationDao.queryCountEndedByPid(fwStream.getId());
					fwStream.setSuccessNum(endedNum+"");
					int exceptionNum = esbMfInformationDao.queryCountExceptionByPid(fwStream.getId());
					fwStream.setFailureNum(exceptionNum+"");
					if(ids.equals("")){
						ids+=fwStream.getId();
					}else{
						ids+=","+fwStream.getId();
					}
				}
			}
			if(ids!=null && !ids.equals("")){
				String status = getEsbOpt(ids,"status");
				
				if(status!=null && !status.equals("")){
					String[] statuses = status.split(",");
					if(reList.size()==statuses.length){
						for(int s=0;s<reList.size();s++){
							FwStream fwStream = reList.get(s);
							if(fwStream!=null){
								fwStream.setStarted(statuses[s]);
							}
						}
					}
				}
			}
			
		}
		int count = fwStreamDao.queryStreamCountByPage(condition);
		return new SearchResult(reList, count);
	}
	
	public SearchResult streamDataList(Page page, Map<String, Object> condition){
		condition.put("start", page.getStart());
		condition.put("end", page.getRows());
		List<EsbMfInformation> reList = esbMfInformationDao.queryList(condition);
		int count = esbMfInformationDao.queryListCount(condition);
		return new SearchResult(reList, count);
	}
	
	public boolean getEsbStatus(String streamId){
			String re = getEsbOpt(streamId,"status");
			return Boolean.parseBoolean(re);
	}
	
	public String startEsb(String streamId){
		String isStart = getEsbOpt(streamId,"start");
		if(isStart!=null && isStart.equals("true")){
			return streamId;
		}
		return "";
	}
	
	public String stopEsb(String streamId){
		String isStart = getEsbOpt(streamId,"stop");
		if(isStart!=null && isStart.equals("true")){
			return streamId;
		}
		return "";
	}
	
	private String getEsbOpt(String ids,String opt){
		Properties prop = new Properties();
		InputStream is = getClass().getResourceAsStream("../../../../../application.properties");
		try {
			prop.load(is);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		String esburl = prop.getProperty("esb.process.url").trim();
		BufferedReader in = null;
		String urlPath = "http://"+esburl+":5988/http/opt?flowid="+ids+"&opt="+opt;
		String result = "";
		try {
			URL url = new URL(urlPath);
			URLConnection conn = url.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0(compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.connect();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while((line = in.readLine())!=null){
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally{
			try{
				if(in!=null){
					in.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private InterfaceManager getInterfaceManager(){
		InterfaceManager interfaceManager = null;
		try {
			Properties prop = new Properties();
			InputStream is = getClass().getResourceAsStream("../../../../../application.properties");
			prop.load(is);
			String esbUrl = prop.getProperty("esb.process.url").trim();
			int esbPort = Integer.parseInt(prop.getProperty("esb.process.port").trim());
			String esbUser = prop.getProperty("esb.process.user").trim();
			String esbPassword = prop.getProperty("esb.process.password").trim();
			
			client = CommSocketClient.getCommClient(esbUrl, esbPort);
			IUserLoginManager manager = (IUserLoginManager)client.getProxy(IUserLoginManager.class);
			ESBUserToken token = manager.login(esbUser, esbPassword);
			String routeName = "AESB";
			client.setUserToken(token);
			interfaceManager = InterfaceManager.getRemote(client, routeName, 3000);
		} catch (Exception e) {
			return null;
		}
		return interfaceManager;
	}

}

package net.sinodata.business.util;

import java.io.InputStream;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Properties;

import net.sinodata.business.entity.FwEventLog;

public class LogWriteDb {
	
	public static String isWrite = "";

	private Connection getConnection(){
		try {
			Properties prop = new Properties();
			InputStream is = getClass().getResourceAsStream("../../../../application.properties");
			prop.load(is);
			String driver = prop.getProperty("jdbc.driver").trim();
			String url = prop.getProperty("jdbc.url").trim();
			String user = prop.getProperty("jdbc.username").trim();
			String password = prop.getProperty("jdbc.password").trim();
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(url, user, password);
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int writeDb(String cl){
		try {
			List<FwEventLog> logList = null;
			if(cl==null || cl.equals("")){
				return 0;
			}
			if(cl.equals("1")){
				isWrite = "1";
				logList = LogWrite1.logList;
			}
			if(cl.equals("2")){
				isWrite = "2";
				logList = LogWrite2.logList;
			}
			if(logList!=null && logList.size()>0){
				Connection conn = getConnection();
				//conn.setAutoCommit(false);
				int reInt = 0;
				PreparedStatement pstat = conn.prepareStatement("insert into fw_event_log(src_sys_name,src_sys_id,src_biz_user_id,src_device_imei," +
						"dest_sys_name,dest_sys_id,dest_sys_interface,dest_sys_interface_desc,dest_device_addr,dest_device_port,event_tracing_id," +
						"event_type,event_result,event_result_desc,event_message,event_start_time,event_end_time,id,request_content,response_content," +
						"exception_content) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,empty_clob(),empty_clob(),empty_clob())");
				for(FwEventLog log:logList){
					pstat.setString(1,log.getSrc_sys_name());
					pstat.setString(2,log.getSrc_sys_id());
					pstat.setString(3,log.getSrc_biz_user_id());
					pstat.setString(4,log.getSrc_device_imei());
					pstat.setString(5,log.getDest_sys_name());
					pstat.setString(6,log.getDest_sys_id());
					pstat.setString(7,log.getDest_sys_interface());
					pstat.setString(8,log.getDest_sys_interface_desc());
					pstat.setString(9,log.getDest_device_addr());
					pstat.setString(10,log.getDest_device_port());
					pstat.setString(11,log.getEvent_tracing_id());
					pstat.setString(12,log.getEvent_type());
					pstat.setString(13,log.getEvent_result());
					pstat.setString(14,log.getEvent_result_desc());
					pstat.setString(15,log.getEvent_message());
					pstat.setString(16,log.getEvent_start_time());
					pstat.setString(17,log.getEvent_end_time());
					String id = UUIDGeneratorUtil.getUUID();
					pstat.setString(18,id);
					pstat.execute();
					if(log.getRequest_content()!=null && !log.getRequest_content().equals("")){
						PreparedStatement clobStat = conn.prepareStatement("select request_content from fw_event_log where id=? for update");
						clobStat.setString(1, id);
						ResultSet rs = clobStat.executeQuery();
						rs.next();
						Clob requestContent = rs.getClob(1);
						requestContent.setString(1, log.getRequest_content());
						
						clobStat = conn.prepareStatement("update fw_event_log set request_content=? where id=?");
						clobStat.setClob(1, requestContent);
						clobStat.setString(2, id);
						clobStat.execute();
					}
					
					if(log.getResponse_content()!=null && !log.getResponse_content().equals("")){
						PreparedStatement clobStat = conn.prepareStatement("select response_content from fw_event_log where id=? for update");
						clobStat.setString(1, id);
						ResultSet rs = clobStat.executeQuery();
						rs.next();
						Clob responseContent = rs.getClob(1);
						responseContent.setString(1, log.getResponse_content());
						
						clobStat = conn.prepareStatement("update fw_event_log set response_content=? where id=?");
						clobStat.setClob(1, responseContent);
						clobStat.setString(2, id);
						clobStat.execute();
					}
					
					if(log.getException_content()!=null && !log.getException_content().equals("")){
						PreparedStatement clobStat = conn.prepareStatement("select exception_content from fw_event_log where id=? for update");
						clobStat.setString(1, id);
						ResultSet rs = clobStat.executeQuery();
						rs.next();
						Clob exceptionContent = rs.getClob(1);
						exceptionContent.setString(1, log.getException_content());
						
						clobStat = conn.prepareStatement("update fw_event_log set exception_content=? where id=?");
						clobStat.setClob(1, exceptionContent);
						clobStat.setString(2, id);
						clobStat.execute();
					}
					
					reInt += 1;
				}
				pstat.close();
				conn.close();
				//conn.commit();
				
				if(cl.equals("1")){
					if(LogWrite1.logList!=null){
						LogWrite1.logList.clear();
					}
				}
				if(cl.equals("2")){
					if(LogWrite2.logList!=null){
						LogWrite2.logList.clear();
					}
				}
				isWrite = "";
				return reInt;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}

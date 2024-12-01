package net.sinodata.business.util.eventLogWrite;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.sinodata.business.entity.Fwzyqqbwyccjb;

public class ExceptionLogWriteDb {
	
	public static String isWrite;
	
	private Connection getConnection(){
		try {
			Properties prop = new Properties();
			InputStream is = getClass().getResourceAsStream("../../../../../application.properties");
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
	
	public int writeLogToDb(String cl){
		if(cl==null || cl.equals("")){
			return 0;
		}
		List<Fwzyqqbwyccjb> logList = new ArrayList<Fwzyqqbwyccjb>();
		
		if(cl.equals("1")){
			isWrite = "1";
			logList = ExceptionLogWrite.logList1;
		}
		
		if(cl.equals("2")){
			isWrite = "2";
			logList = ExceptionLogWrite.logList2;
		}
		
		if(cl.equals("3")){
			isWrite = "3";
			logList = ExceptionLogWrite.logList3;
		}
		//if(logList!=null && logList.size()>0){
		if(logList.size()>0){
			Connection conn = getConnection();
			int reInt = 0;
			try {
				PreparedStatement pStat = conn.prepareStatement("insert into fwzyqqbwyccjb (qqbwbs, fwqq_ip, fwqq_dk, fwqqz_zcxx, fwbs, ffbs, " +
						"fwqq_rqsj, xxczry_xm, xxczry_gmsfhm, xxczry_gajgjgdm, fwqqsb_bh, fwqq_sjsjlx, fwqq_nr, start_time,qqqw,id) " +
						"values(?,?,?,?,?,?,?,?,?,?,?,?,empty_clob(),?,empty_clob(),?)");
//				PreparedStatement clobNrStat = conn.prepareStatement("select FWQQ_NR from fwzyqqbwyccjb where id=? for update");
//				PreparedStatement clobNrUpdateStat = conn.prepareStatement("update fwzyqqbwyccjb set FWQQ_NR=? where id=?");
//				
//				PreparedStatement clobQwStat = conn.prepareStatement("select QQQW from fwzyqqbwyccjb where id=? for update");
//				PreparedStatement clobQwUpdateStat = conn.prepareStatement("update fwzyqqbwyccjb set QQQW=? where id=?");
//				
//				PreparedStatement exceptionStat = conn.prepareStatement("insert into fwzyqqbwyccjb_cols(QQBWBS,EXCEPTION_COLS) values(?,?)");
				for(Fwzyqqbwyccjb log:logList){
					if(log!=null){
						pStat.setString(1, log.getQqbwbs());
						pStat.setString(2, log.getFwqqIp());
						pStat.setString(3, log.getFwqqDk());
						pStat.setString(4, log.getFwqqzZcxx());
						pStat.setString(5, log.getFwbs());
						pStat.setString(6, log.getFfbs());
						pStat.setString(7, log.getFwqqRqsj());
						pStat.setString(8, log.getXxczryXm());
						pStat.setString(9, log.getXxczryGmsfhm());
						pStat.setString(10, log.getXxczryGajgjgdm());
						pStat.setString(11, log.getFwqqsbBh());
						pStat.setString(12, log.getFwqqSjsjlx());
						pStat.setString(13, log.getStartTime());
						pStat.setString(14, log.getId());
						pStat.execute();
						
//						if(log.getExceptionCols()!=null && !exceptionStat.equals("")){
//							String[] cols = log.getExceptionCols().split(",");
//							for(String col:cols){
//								exceptionStat.setString(1, log.getQqbwbs());
//								exceptionStat.setString(2, col);
//								exceptionStat.execute();
//							}
//						}
						
//						if(log.getFwqqNr()!=null && !log.getFwqqNr().equals("")){
//							//获取请求内容Clob
//							clobNrStat.setString(1, log.getId());
//							ResultSet rs = clobNrStat.executeQuery();
//							while(rs.next()){
//								Clob requestContent = rs.getClob(1);
//								requestContent.setString(1, log.getFwqqNr());
//								
//								//更新请求内容Clob
//								clobNrUpdateStat.setClob(1, requestContent);
//								clobNrUpdateStat.setString(2, log.getId());
//								clobNrUpdateStat.execute();
//							}
//						}
						
//						if(log.getQqqw()!=null && !log.getQqqw().equals("")){
//							//获取请求全文Clob
//							clobQwStat.setString(1, log.getId());
//							ResultSet rs = clobQwStat.executeQuery();
//							while(rs.next()){
//								Clob requestContent = rs.getClob(1);
//								requestContent.setString(1, log.getQqqw());
//								
//								//更新请求全文Clob
//								clobQwUpdateStat.setClob(1, requestContent);
//								clobQwUpdateStat.setString(2, log.getId());
//								clobQwUpdateStat.execute();
//							}
//							
//							
//						}
						
						reInt += 1;
					}
				}
				pStat.close();
//				clobNrStat.close();
//				clobNrUpdateStat.close();
//				clobQwStat.close();
//				clobQwUpdateStat.close();
//				exceptionStat.close();
				conn.close();
				//conn.commit();
				
				//清空列表及重置写入参数
				if(cl.equals("1")){
					if(ExceptionLogWrite.logList1.size()>0){
						ExceptionLogWrite.logList1.clear();
					}
				}
				
				if(cl.equals("2")){
					if(ExceptionLogWrite.logList2.size()>0){
						ExceptionLogWrite.logList2.clear();
					}
				}
				
				if(cl.equals("3")){
					if(ExceptionLogWrite.logList3.size()>0){
						ExceptionLogWrite.logList3.clear();
					}
				}
				isWrite = "";
				return reInt;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return 0;
	}
}

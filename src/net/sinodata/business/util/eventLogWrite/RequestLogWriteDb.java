package net.sinodata.business.util.eventLogWrite;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.alibaba.fastjson.JSONObject;

import net.sinodata.business.entity.Fwzyqqbwcjb;
import net.sinodata.business.util.HttpClientUtil;

public class RequestLogWriteDb {
	
	public static String isWrite;
	public static String http_url = null;
	public static String driver = null;
	public static String url = null;
	public static String user = null;
	public static String password = null;
	
	static {
		try {
			Properties prop = new Properties();
			InputStream is = ResponseLogWriteDb.class.getResourceAsStream("/application.properties");
			prop.load(is);
			 driver = prop.getProperty("jdbc.driver").trim();
			 url = prop.getProperty("jdbc.url").trim();
			 user = prop.getProperty("jdbc.username").trim();
			 password = prop.getProperty("jdbc.password").trim();
			 http_url = prop.getProperty("http_request_url").trim();
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	private Connection getConnection(){
		try {
//			Properties prop = new Properties();
//			InputStream is = getClass().getResourceAsStream("../../../../../application.properties");
//			prop.load(is);
//			String driver = prop.getProperty("jdbc.driver").trim();
//			String url = prop.getProperty("jdbc.url").trim();
//			String user = prop.getProperty("jdbc.username").trim();
//			String password = prop.getProperty("jdbc.password").trim();
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
		List<Fwzyqqbwcjb> logList = new ArrayList<Fwzyqqbwcjb>();
		
		if(cl.equals("1")){
			isWrite = "1";
			logList = RequestLogWrite.logList1;
		}
		
		if(cl.equals("2")){
			isWrite = "2";
			logList = RequestLogWrite.logList2;
		}
		
		if(cl.equals("3")){
			isWrite = "3";
			logList = RequestLogWrite.logList3;
		}
		
		if(cl.equals("4")){
			isWrite = "4";
			logList = RequestLogWrite.logList4;
		}
		
		if(cl.equals("5")){
			isWrite = "5";
			logList = RequestLogWrite.logList5;
		}
		
		if(logList.size()>0){
			Connection conn = getConnection();
//			SparkSession spark=SparkUtil.getSparkSession();
//			Date date1 = new Date();
//			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
//			String year = sdf1.format(date1).substring(0, 4);
//			String month = sdf1.format(date1).substring(4, 6);
//			String day = sdf1.format(date1).substring(6, 8);
//			Encoder<Fwzyqqbwcjb> fwzyqqbwcjbEncoder = Encoders.bean(Fwzyqqbwcjb.class);
//			Dataset<Fwzyqqbwcjb> javaBeanDS = spark.createDataset(logList,fwzyqqbwcjbEncoder);
//			javaBeanDS.show();
//			javaBeanDS.write().mode(SaveMode.Append).parquet("file:///u02/parquet/request/year="+year+"/month="+month+"/day="+day);
			for(Fwzyqqbwcjb rlog:logList) {
				try {
					HttpClientUtil.doPost(http_url,JSONObject.toJSONString(rlog));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			
			int reInt = 0;
			try {
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
				String days = sdf.format(date).substring(6, 8);
				PreparedStatement pStat = conn.prepareStatement("insert into fwzyqqbwcjb (qqbwbs, fwqq_ip, fwqq_dk, fwqqz_zcxx, fwbs, ffbs, " +
						"fwqq_rqsj, xxczry_xm, xxczry_gmsfhm, xxczry_gajgjgdm, fwqqsb_bh, fwqq_sjsjlx, fwqq_nr, start_time,qqqw,id,seq_qqbwid,yysbs,days) " +
						"values(?,?,?,?,?,?,?,?,?,?,?,?,empty_clob(),?,empty_clob(),?,?,?,?)");
				PreparedStatement clobNrStat = conn.prepareStatement("select FWQQ_NR from FWZYQQBWCJB where id=? for update");
				PreparedStatement clobNrUpdateStat = conn.prepareStatement("update FWZYQQBWCJB set FWQQ_NR=? where id=?");
				
				for(Fwzyqqbwcjb log:logList){
					try{
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
							
							String seq_sql = "select seq_qqbwid.nextval as seq_id from dual";
							PreparedStatement seq_pstmt =conn.prepareStatement(seq_sql);
							ResultSet seq_rs=seq_pstmt.executeQuery();
							seq_rs.next();
							int seq_id=seq_rs.getInt(1);
							seq_rs.close();
							seq_pstmt.close();
							
							pStat.setLong(15, seq_id);
							
							pStat.setString(16, log.getYysbs());
							pStat.setString(17, days);
							pStat.execute();
							reInt += 1;
						}
					}catch(Exception e){
						e.printStackTrace();
						continue;
					}
				}
				pStat.close();
				clobNrStat.close();
				clobNrUpdateStat.close();
				conn.close();
				
				//清空列表及重置写入参数
				if(cl.equals("1")){
					if(RequestLogWrite.logList1.size()>0){
						RequestLogWrite.logList1 =null;
						RequestLogWrite.logList1 =new ArrayList<Fwzyqqbwcjb>();
//						RequestLogWrite.logList1.clear();
					}
				}
				
				if(cl.equals("2")){
					if(RequestLogWrite.logList2.size()>0){
						RequestLogWrite.logList2 =null;
						RequestLogWrite.logList2 =new ArrayList<Fwzyqqbwcjb>();
//						RequestLogWrite.logList2.clear();
					}
				}
				
				if(cl.equals("3")){
					if(RequestLogWrite.logList3.size()>0){
						RequestLogWrite.logList3 =null;
						RequestLogWrite.logList3 =new ArrayList<Fwzyqqbwcjb>();
//						RequestLogWrite.logList3.clear();
					}
				}
				
				if(cl.equals("4")){
					if(RequestLogWrite.logList4.size()>0){
						RequestLogWrite.logList4 =null;
						RequestLogWrite.logList4 =new ArrayList<Fwzyqqbwcjb>();
//						RequestLogWrite.logList4.clear();
					}
				}
				
				if(cl.equals("5")){
					if(RequestLogWrite.logList5.size()>0){
						RequestLogWrite.logList5 =null;
						RequestLogWrite.logList5 =new ArrayList<Fwzyqqbwcjb>();
//						RequestLogWrite.logList5.clear();
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

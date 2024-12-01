package net.sinodata.business.util.eventLogWrite;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;


import com.alibaba.fastjson.JSONObject;

import net.sinodata.business.entity.Fwzyxybwcjb;
import net.sinodata.business.util.HttpClientUtil;


public class ResponseLogWriteDb {

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
			 http_url = prop.getProperty("http_response_url").trim();
			 
			 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private Connection getConnection() {
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

	public int writeLogToDb(String cl) {
		if (cl == null || cl.equals("")) {
			return 0;
		}
		List<Fwzyxybwcjb> logList = new ArrayList<Fwzyxybwcjb>();

		if (cl.equals("1")) {
			isWrite = "1";
			logList = ResponseLogWrite.logList1;
		}

		if (cl.equals("2")) {
			isWrite = "2";
			logList = ResponseLogWrite.logList2;
		}

		if (cl.equals("3")) {
			isWrite = "3";
			logList = ResponseLogWrite.logList3;
		}
		
		if (cl.equals("4")) {
			isWrite = "4";
			logList = ResponseLogWrite.logList4;
		}
		
		if (cl.equals("5")) {
			isWrite = "5";
			logList = ResponseLogWrite.logList5;
		}
		
		if (logList.size() > 0) {
			Connection conn = getConnection();
//			SparkSession spark=SparkUtil.getSparkSession();
//			Date date1 = new Date();
//			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
//			String year = sdf1.format(date1).substring(0, 4);
//			String month = sdf1.format(date1).substring(4, 6);
//			String day = sdf1.format(date1).substring(6, 8);
//			Encoder<Fwzyxybwcjb> fwzyxybwcjbEncoder = Encoders.bean(Fwzyxybwcjb.class);
//			Dataset<Fwzyxybwcjb> javaBeanDS = spark.createDataset(logList,fwzyxybwcjbEncoder);
//			javaBeanDS.show();
//			javaBeanDS.write().mode(SaveMode.Append).parquet("file:///u02/parquet/response/year="+year+"/month="+month+"/day="+day);
			
			for(Fwzyxybwcjb rlog:logList) {
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
				PreparedStatement pStat = conn
						.prepareStatement("insert into fwzyxybwcjb (xybwbs, fwtgz_zcxx, fwbs, ffbs, ffms, xxczry_xm, "
								+ "xxczry_gmsfhm, xxczry_gajgjgdm, fwqqsb_bh, fwtg_rqsj, fwtg_ip, fwtg_dk, fwtgztdm, fwtgztms, fwtg_sjsjlx, fwtg_nr, "
								+ "end_time,xyqw,id,days) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,empty_clob(),?,empty_clob(),?,?)");
				PreparedStatement clobNrStat = conn.prepareStatement("select FWTG_NR from Fwzyxybwcjb where id=? for update");
				PreparedStatement clobNrUpdateStat = conn.prepareStatement("update Fwzyxybwcjb set FWTG_NR=? where id=?");

				for (Fwzyxybwcjb log : logList) {
					try {
						if (log != null) {
							pStat.setString(1, log.getXybwbs());
							pStat.setString(2, log.getFwtgzZcxx());
							pStat.setString(3, log.getFwbs());
							pStat.setString(4, log.getFfbs());
							pStat.setString(5, log.getFfms());
							pStat.setString(6, log.getXxczryXm());
							pStat.setString(7, log.getXxczryGmsfhm());
							pStat.setString(8, log.getXxczryGajgjgdm());
							pStat.setString(9, log.getFwqqsbBh());
							pStat.setString(10, log.getFwtgRqsj());
							pStat.setString(11, log.getFwtgIp());
							pStat.setString(12, log.getFwtgDk());
							pStat.setString(13, log.getFwtgztdm());
							pStat.setString(14, log.getFwtgztms());
							pStat.setString(15, log.getFwtgSjsjlx());
							pStat.setString(16, log.getEndTime());
							pStat.setString(17, log.getId());
							pStat.setString(18, days);
							pStat.execute();
							reInt += 1;
						}
					} catch (Exception e) {
						e.printStackTrace();
						continue;
					}
				}
				pStat.close();
				clobNrStat.close();
				clobNrUpdateStat.close();

				conn.close();

				// 清空列表及重置写入参数
				if (cl.equals("1")) {
					if (ResponseLogWrite.logList1.size() > 0) {
						// ResponseLogWrite.logList1.clear();
						ResponseLogWrite.logList1 = null;
						ResponseLogWrite.logList1 = new ArrayList<Fwzyxybwcjb>();
					}
				}

				if (cl.equals("2")) {
					if (ResponseLogWrite.logList2.size() > 0) {
						// ResponseLogWrite.logList2.clear();
						ResponseLogWrite.logList2 = null;
						ResponseLogWrite.logList2 = new ArrayList<Fwzyxybwcjb>();
					}
				}

				if (cl.equals("3")) {
					if (ResponseLogWrite.logList3.size() > 0) {
						// ResponseLogWrite.logList3.clear();
						ResponseLogWrite.logList3 = null;
						ResponseLogWrite.logList3 = new ArrayList<Fwzyxybwcjb>();
					}
				}
				
				if (cl.equals("4")) {
					if (ResponseLogWrite.logList4.size() > 0) {
						// ResponseLogWrite.logList4.clear();
						ResponseLogWrite.logList4 = null;
						ResponseLogWrite.logList4 = new ArrayList<Fwzyxybwcjb>();
					}
				}
				
				if (cl.equals("5")) {
					if (ResponseLogWrite.logList5.size() > 0) {
						// ResponseLogWrite.logList5.clear();
						ResponseLogWrite.logList5 = null;
						ResponseLogWrite.logList5 = new ArrayList<Fwzyxybwcjb>();
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

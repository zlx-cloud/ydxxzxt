package net.sinodata.business.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Properties;

import net.sf.json.JSONObject;


public class ProbeInterface {
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
	
	public void autoProbe(){
		Connection conn = getConnection();
		try {
			PreparedStatement cxstat = conn.prepareStatement(" select * from FWZYTCB where FWKG=1 ");
			PreparedStatement pStat = conn.prepareStatement(" insert into FWZYTCSBRZB (FWBS, TCSJ)  values(?,?)");
			PreparedStatement bsStat = conn.prepareStatement(" select FWTGZ_YYXTBH from FWZYZCB where FWBS=?");
			ResultSet rs = cxstat.executeQuery();
			while(rs.next()){
				Calendar ca = Calendar.getInstance();
				String time = ca.get(ca.YEAR) + ""
						+ (ca.get(ca.MONTH) >= 9 ? (ca.get(ca.MONTH) + 1) : "0" + (ca.get(ca.MONDAY) + 1)) + ""
						+ (ca.get(ca.DATE) >= 10 ? ca.get(ca.DATE) : "0" + ca.get(ca.DATE)) + ""
						+ (ca.get(ca.HOUR_OF_DAY) >= 10 ? ca.get(ca.HOUR_OF_DAY) : "0" + ca.get(ca.HOUR_OF_DAY)) + ""
						+ (ca.get(ca.MINUTE) >= 10 ? ca.get(ca.MINUTE) : "0" + ca.get(ca.MINUTE)) + ""
						+ (ca.get(ca.SECOND) >= 10 ? ca.get(ca.SECOND) : "0" + ca.get(ca.SECOND)) + ""
						+ (ca.get(ca.MILLISECOND) >= 100 ? ca.get(ca.MILLISECOND)
								: (ca.get(ca.MILLISECOND) >= 10 ? "0" + ca.get(ca.MILLISECOND)
										: "00" + ca.get(ca.MILLISECOND)));
				JSONObject jsonObject=new JSONObject();
				bsStat.setString(1,rs.getString(1));
				ResultSet rs1 = bsStat.executeQuery();
				jsonObject.put("BWLYDKH", "8080");
				jsonObject.put("BWLYIPDZ", "172.016.010.103");
				jsonObject.put("FFBS",rs.getString(3));
				jsonObject.put("FWBS",rs.getString(1));
				jsonObject.put("FWQQSB_BH","869661020828470");
				while(rs1.next()){
					jsonObject.put("FWQQZ_ZCXX",rs1.getString(1));
				}
				
				jsonObject.put("FWQQ_BWBH", "SR020001031150"+time+"1234");
				jsonObject.put("FWQQ_NR",rs.getString(6));
				jsonObject.put("FWQQ_RQSJ",time.substring(0,14));
				jsonObject.put("FWQQ_SJSJLX","service_request");
				jsonObject.put("XXCZRY_GAJGJGDM","110102000000");
				jsonObject.put("XXCZRY_GMSFHM","111111111111111110");
				jsonObject.put("XXCZRY_XM","123");
				jsonObject.put("YYSBS","dx");
		  
				String sr = HttpRequest.sendPost("http://20.1.11.52:5988/http/GA000Comm", jsonObject.toString());
				if(!sr.contains("FWBS")) {
					pStat.setString(1, rs.getString(1));
					pStat.setString(2, time.substring(0,14));
					pStat.execute();
				}
				
			}
			
			cxstat.close();
			bsStat.close();
			pStat.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

package net.sinodata.business.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class RequestLimit {
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
	
	public void autoLimit(){
		Connection conn = getConnection();
		try {
			PreparedStatement cxstat = conn.prepareStatement(" select * from FWZYXZB t where FWXZZT=1 ");
			
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			String time =sdf.format(date);
			
			Date date1 = new Date();
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHH");
			String time1 =sdf1.format(date1);
			
			PreparedStatement pStat = conn.prepareStatement(" select sum(num) from FWZYBWTJB_DAY t where 1=1 and tjrq=? group by ? ");
			
			PreparedStatement pStat1 = conn.prepareStatement(" select sum(num) from FWZYBWTJB_HOUR t where 1=1 and tjrq=? group by ? ");

			
			PreparedStatement bsStat = conn.prepareStatement(" update FWZYZCB set FWZTDM='2' where FWBS=? ");
			ResultSet rs = cxstat.executeQuery();
			while(rs.next()){
				if("".equals(rs.getString(4))) {
					pStat1.setString(1, time1);
					pStat1.setString(2, rs.getString(1));
//					pStat1.setString(2, "S00111000000000022001");
					ResultSet rs1 = pStat1.executeQuery();
					while(rs1.next()){
						String hourNum=rs1.getString(1);
						if(Integer.parseInt(rs.getString(5))<=Integer.parseInt(hourNum)) {
							bsStat.setString(1, rs.getString(1));
							bsStat.execute();
						}
					}
					
				}else {
					pStat.setString(1, time);
//					pStat.setString(2, "S00111000000000022001");
					pStat.setString(2, rs.getString(1));
					ResultSet rs2 = pStat.executeQuery();
					while(rs2.next()){
						String dayNum=rs2.getString(1);
						if(Integer.parseInt(rs.getString(4))<=Integer.parseInt(dayNum)) {
							bsStat.setString(1, rs.getString(1));
							bsStat.execute();
						}
					}
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

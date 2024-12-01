package net.sinodata.business.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	
	public static Date formatString(String str,String format) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	
	public static Date formatDateToDate(Date date,String forma) throws Exception{
		SimpleDateFormat format=new SimpleDateFormat(forma);
	    Long time=date.getTime();  
	    String d = format.format(time);  
	    Date dat=format.parse(d); 	
		return dat;
	}
	
	public static String timestamp(Date log_timestamp) throws Exception{
	//////时间戳(log_timestamp)
			
			if(log_timestamp==null){
				log_timestamp = new Date();
			}
			Calendar ca = Calendar.getInstance();
			ca.setTime(log_timestamp);
			//时间戳格式为yyyy-mm-dd hh:mi:ss,fff
			String time = ca.get(ca.YEAR)+"-"+(ca.get(ca.MONTH)<9?"0"+(ca.get(ca.MONTH)+1):(ca.get(ca.MONTH)+1))+"-"+(ca.get(ca.DATE)<10?"0"+ca.get(ca.DATE):ca.get(ca.DATE))+" "
				+(ca.get(ca.HOUR_OF_DAY)<10?"0"+ca.get(ca.HOUR_OF_DAY):ca.get(ca.HOUR_OF_DAY))+":"+(ca.get(ca.MINUTE)<10?"0"+ca.get(ca.MINUTE):ca.get(ca.MINUTE))+":"
				+(ca.get(ca.SECOND)<10?"0"+ca.get(ca.SECOND):ca.get(ca.SECOND))+","
				+(ca.get(ca.MILLISECOND)<10?"00"+ca.get(ca.MILLISECOND):(ca.get(ca.MILLISECOND)<100?"0"+ca.get(ca.MILLISECOND):ca.get(ca.MILLISECOND)));
			return time;
	}

}

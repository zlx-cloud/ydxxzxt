package net.sinodata.business.util;

import java.util.ArrayList;
import java.util.List;

import net.sinodata.business.entity.FwEventLog;

public class LogWrite1 {

	public static List<FwEventLog> logList;
	
	public void addLog(FwEventLog log){
		if(logList==null){
			logList = new ArrayList<FwEventLog>();
		}
//		System.out.println("logsize:"+logList.size());
//		if(logList.size()>=500){
//			LogWrite2 write2 = new LogWrite2();
//			write2.addLog(log);
//			LogWriteDb write = new LogWriteDb();
//			write.writeDb("1");
//			logList.clear();
//		}else{
		logList.add(log);
//		}
	}
}

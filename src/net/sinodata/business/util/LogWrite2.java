package net.sinodata.business.util;

import java.util.ArrayList;
import java.util.List;

import net.sinodata.business.entity.FwEventLog;

public class LogWrite2 {

	public static List<FwEventLog> logList;
	
	public void addLog(FwEventLog log){
		if(logList==null){
			logList = new ArrayList<FwEventLog>();
		}
//		System.out.println("logsize:"+logList.size());
//		if(logList.size()>=500){
//			LogWrite1 write1 = new LogWrite1();
//			write1.addLog(log);
//			LogWriteDb write = new LogWriteDb();
//			write.writeDb("2");
//			logList.clear();
//		}else{
		logList.add(log);
//		}
	}
}

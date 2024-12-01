package net.sinodata.business.util;

import net.sinodata.business.entity.FwEventLog;

public class LogWrite {

	public static int logNum = 1;
	
	public void addLog(FwEventLog log){
		if(logNum==1){
			if(LogWrite1.logList!=null && LogWrite1.logList.size()>=500){
				logNum = 2;
				addLog(log);
				LogWriteDb write = new LogWriteDb();
				if(!"1".equals(LogWriteDb.isWrite)){
					write.writeDb("1");
				}
			}else{
				LogWrite1 logWrite = new LogWrite1();
				logWrite.addLog(log);
			}
		}
		if(logNum==2){
			if(LogWrite2.logList!=null && LogWrite2.logList.size()>=500){
				logNum = 1;
				addLog(log);
				LogWriteDb write = new LogWriteDb();
				if(!"2".equals(LogWriteDb.isWrite)){
					write.writeDb("2");
				}
			}else{
				LogWrite2 logWrite = new LogWrite2();
				logWrite.addLog(log);
			}
		}
	}
	
	public void writeLog(){
		//System.out.println("write log start");
		if(logNum==1 && !"1".equals(LogWriteDb.isWrite)){
			logNum = 2;
			LogWriteDb write = new LogWriteDb();
			write.writeDb("1");
		}
		
		if(logNum==2 && !"2".equals(LogWriteDb.isWrite)){
			logNum = 1;
			LogWriteDb write = new LogWriteDb();
			write.writeDb("2");
		}
		//System.out.println("write log end");
	}
}

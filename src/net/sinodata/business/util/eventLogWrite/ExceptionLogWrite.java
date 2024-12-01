package net.sinodata.business.util.eventLogWrite;

import java.util.ArrayList;
import java.util.List;

import net.sinodata.business.entity.Fwzyqqbwyccjb;
import net.sinodata.business.util.UUIDGeneratorUtil;

public class ExceptionLogWrite {
	public static List<Fwzyqqbwyccjb> logList1 = new ArrayList<Fwzyqqbwyccjb>();
	
	public static List<Fwzyqqbwyccjb> logList2 = new ArrayList<Fwzyqqbwyccjb>();
	
	public static List<Fwzyqqbwyccjb> logList3 = new ArrayList<Fwzyqqbwyccjb>();
	
	public static int logNum = 1;
	
	public void addRequestLog(Fwzyqqbwyccjb log){
		if(log!=null){
			log.setId(UUIDGeneratorUtil.getUUID());
		}
		if(logNum==1){
			if(logList1==null){
				logList1 = new ArrayList<Fwzyqqbwyccjb>();
			}
			if(logList1.size()>=499){
				addLog(log);
				logNum = 2;
				if(!"1".equals(ExceptionLogWriteDb.isWrite)){
					ExceptionLogWriteDb writeDb = new ExceptionLogWriteDb();
					writeDb.writeLogToDb("1");
				}
			}else{
				addLog(log);
			}
		}
		
		else if(logNum==2){
			if(logList2==null){
				logList2 = new ArrayList<Fwzyqqbwyccjb>();
			}
			if(logList2.size()>=499){
				addLog(log);
				logNum = 3;
				if(!"2".equals(ExceptionLogWriteDb.isWrite)){
					ExceptionLogWriteDb writeDb = new ExceptionLogWriteDb();
					writeDb.writeLogToDb("2");
				}
			}else{
				addLog(log);
			}
		}
		
		else if(logNum==3){
			if(logList3==null){
				logList3 = new ArrayList<Fwzyqqbwyccjb>();
			}
			if(logList3.size()>=499){
				addLog(log);
				logNum = 1;
				if(!"3".equals(ExceptionLogWriteDb.isWrite)){
					ExceptionLogWriteDb writeDb = new ExceptionLogWriteDb();
					writeDb.writeLogToDb("3");
				}
			}else{
				addLog(log);
			}
		}
		
	}
	
	private void addLog(Fwzyqqbwyccjb log){
		if(logNum==1){
			logList1.add(log);
		}
		if(logNum==2){
			logList2.add(log);
		}
		if(logNum==3){
			logList3.add(log);
		}
	}
	
	public void autoWriteLog(){
		//System.out.println("write exception log start");
		int reInt = 0;
		String num = "";
		if(logNum==1){
			if(!"1".equals(ExceptionLogWriteDb.isWrite)){
				logNum = 2;
				num = "1";
				ExceptionLogWriteDb writeDb = new ExceptionLogWriteDb();
				reInt = writeDb.writeLogToDb(num);
			}
		}else if(logNum==2){
			if(!"2".equals(ExceptionLogWriteDb.isWrite)){
				logNum = 3;
				num = "2";
				ExceptionLogWriteDb writeDb = new ExceptionLogWriteDb();
				reInt = writeDb.writeLogToDb(num);
			}
		}else if(logNum==3){
			if(!"3".equals(ExceptionLogWriteDb.isWrite)){
				logNum = 1;
				num = "3";
				ExceptionLogWriteDb writeDb = new ExceptionLogWriteDb();
				reInt = writeDb.writeLogToDb(num);
			}
		}
		
		//System.out.println("write exception log"+num+" num"+reInt+" end");
	}
}

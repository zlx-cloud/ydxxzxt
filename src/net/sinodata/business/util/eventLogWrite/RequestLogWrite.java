package net.sinodata.business.util.eventLogWrite;

import java.util.ArrayList;
import java.util.List;

import net.sinodata.business.entity.Fwzyqqbwcjb;
import net.sinodata.business.util.UUIDGeneratorUtil;

public class RequestLogWrite {

	public static List<Fwzyqqbwcjb> logList1 = new ArrayList<Fwzyqqbwcjb>();
	
	public static List<Fwzyqqbwcjb> logList2 = new ArrayList<Fwzyqqbwcjb>();
	
	public static List<Fwzyqqbwcjb> logList3 = new ArrayList<Fwzyqqbwcjb>();
	
	public static List<Fwzyqqbwcjb> logList4 = new ArrayList<Fwzyqqbwcjb>();
	
	public static List<Fwzyqqbwcjb> logList5 = new ArrayList<Fwzyqqbwcjb>();
	
	public static int logNum = 1;
	
	public void addRequestLog(Fwzyqqbwcjb log){
		if(log!=null){
			log.setId(UUIDGeneratorUtil.getUUID());
		}
		if(logNum==1){
			if(logList1==null){
				logList1 = new ArrayList<Fwzyqqbwcjb>();
			}
			if(logList1.size()>=1999){
				addLog(log);
				logNum = 2;
				if(!"1".equals(RequestLogWriteDb.isWrite)){
					RequestLogWriteDb writeDb = new RequestLogWriteDb();
					writeDb.writeLogToDb("1");
				}
			}else{
				addLog(log);
			}
		}
		
		else if(logNum==2){
			if(logList2==null){
				logList2 = new ArrayList<Fwzyqqbwcjb>();
			}
			if(logList2.size()>=1999){
				addLog(log);
				logNum = 3;
				if(!"2".equals(RequestLogWriteDb.isWrite)){
					RequestLogWriteDb writeDb = new RequestLogWriteDb();
					writeDb.writeLogToDb("2");
				}
			}else{
				addLog(log);
			}
		}
		
		else if(logNum==3){
			if(logList3==null){
				logList3 = new ArrayList<Fwzyqqbwcjb>();
			}
			if(logList3.size()>=1999){
				addLog(log);
				logNum = 1;
				if(!"3".equals(RequestLogWriteDb.isWrite)){
					RequestLogWriteDb writeDb = new RequestLogWriteDb();
					writeDb.writeLogToDb("3");
				}
			}else{
				addLog(log);
			}
		}
		
		else if(logNum==4){
			if(logList4==null){
				logList4 = new ArrayList<Fwzyqqbwcjb>();
			}
			if(logList4.size()>=1999){
				addLog(log);
				logNum = 1;
				if(!"4".equals(RequestLogWriteDb.isWrite)){
					RequestLogWriteDb writeDb = new RequestLogWriteDb();
					writeDb.writeLogToDb("4");
				}
			}else{
				addLog(log);
			}
		}
		
		else if(logNum==5){
			if(logList5==null){
				logList5 = new ArrayList<Fwzyqqbwcjb>();
			}
			if(logList5.size()>=1999){
				addLog(log);
				logNum = 1;
				if(!"5".equals(RequestLogWriteDb.isWrite)){
					RequestLogWriteDb writeDb = new RequestLogWriteDb();
					writeDb.writeLogToDb("5");
				}
			}else{
				addLog(log);
			}
		}
		
	}
	
	private void addLog(Fwzyqqbwcjb log){
		if(logNum==1){
			logList1.add(log);
		}
		if(logNum==2){
			logList2.add(log);
		}
		if(logNum==3){
			logList3.add(log);
		}
		if(logNum==4){
			logList4.add(log);
		}
		if(logNum==5){
			logList5.add(log);
		}
	}
	
	public void autoWriteLog(){
		//System.out.println("write request log start");
		int reInt = 0;
		String num = "";
		if(logNum==1){
			if(!"1".equals(RequestLogWriteDb.isWrite)){
				logNum = 2;
				num = "1";
				RequestLogWriteDb writeDb = new RequestLogWriteDb();
				reInt = writeDb.writeLogToDb(num);
			}
		}else if(logNum==2){
			if(!"2".equals(RequestLogWriteDb.isWrite)){
				logNum = 3;
				num = "2";
				RequestLogWriteDb writeDb = new RequestLogWriteDb();
				reInt = writeDb.writeLogToDb(num);
			}
		}else if(logNum==3){
			if(!"3".equals(RequestLogWriteDb.isWrite)){
				logNum = 1;
				num = "3";
				RequestLogWriteDb writeDb = new RequestLogWriteDb();
				reInt = writeDb.writeLogToDb(num);
			}
		}else if(logNum==4){
			if(!"4".equals(RequestLogWriteDb.isWrite)){
				logNum = 1;
				num = "4";
				RequestLogWriteDb writeDb = new RequestLogWriteDb();
				reInt = writeDb.writeLogToDb(num);
			}
		}else if(logNum==5){
			if(!"5".equals(RequestLogWriteDb.isWrite)){
				logNum = 1;
				num = "5";
				RequestLogWriteDb writeDb = new RequestLogWriteDb();
				reInt = writeDb.writeLogToDb(num);
			}
		}
		
		//System.out.println("write request log"+num+" num"+reInt+" end");
	}
}

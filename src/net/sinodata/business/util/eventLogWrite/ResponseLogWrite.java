package net.sinodata.business.util.eventLogWrite;

import java.util.ArrayList;
import java.util.List;

import net.sinodata.business.entity.Fwzyxybwcjb;
import net.sinodata.business.util.UUIDGeneratorUtil;

public class ResponseLogWrite {
	public static List<Fwzyxybwcjb> logList1 = new ArrayList<Fwzyxybwcjb>();
	
	public static List<Fwzyxybwcjb> logList2 = new ArrayList<Fwzyxybwcjb>();
	
	public static List<Fwzyxybwcjb> logList3 = new ArrayList<Fwzyxybwcjb>();
	
	public static List<Fwzyxybwcjb> logList4 = new ArrayList<Fwzyxybwcjb>();
	
	public static List<Fwzyxybwcjb> logList5 = new ArrayList<Fwzyxybwcjb>();
	
	public static int logNum = 1;
	
	public void addResponseLog(Fwzyxybwcjb log){
		if(log!=null){
			log.setId(UUIDGeneratorUtil.getUUID());
		}
		if(logNum==1){
			if(logList1==null){
				logList1 = new ArrayList<Fwzyxybwcjb>();
			}
			if(logList1.size()>=1999){
				addLog(log);
				logNum = 2;
				if(!"1".equals(ResponseLogWriteDb.isWrite)){
					ResponseLogWriteDb writeDb = new ResponseLogWriteDb();
					writeDb.writeLogToDb("1");
				}
			}else{
				addLog(log);
			}
		}
		
		else if(logNum==2){
			if(logList2==null){
				logList2 = new ArrayList<Fwzyxybwcjb>();
			}
			if(logList2.size()>=1999){
				addLog(log);
				logNum = 3;
				if(!"2".equals(ResponseLogWriteDb.isWrite)){
					ResponseLogWriteDb writeDb = new ResponseLogWriteDb();
					writeDb.writeLogToDb("2");
				}
			}else{
				addLog(log);
			}
		}
		
		else if(logNum==3){
			if(logList3==null){
				logList3 = new ArrayList<Fwzyxybwcjb>();
			}
			if(logList3.size()>=1999){
				addLog(log);
				logNum = 1;
				if(!"3".equals(ResponseLogWriteDb.isWrite)){
					ResponseLogWriteDb writeDb = new ResponseLogWriteDb();
					writeDb.writeLogToDb("3");
				}
			}else{
				addLog(log);
			}
		}
		
		else if(logNum==4){
			if(logList4==null){
				logList4 = new ArrayList<Fwzyxybwcjb>();
			}
			if(logList4.size()>=1999){
				addLog(log);
				logNum = 1;
				if(!"4".equals(ResponseLogWriteDb.isWrite)){
					ResponseLogWriteDb writeDb = new ResponseLogWriteDb();
					writeDb.writeLogToDb("4");
				}
			}else{
				addLog(log);
			}
		}
		
		
		else if(logNum==5){
			if(logList5==null){
				logList5 = new ArrayList<Fwzyxybwcjb>();
			}
			if(logList5.size()>=1999){
				addLog(log);
				logNum = 1;
				if(!"5".equals(ResponseLogWriteDb.isWrite)){
					ResponseLogWriteDb writeDb = new ResponseLogWriteDb();
					writeDb.writeLogToDb("5");
				}
			}else{
				addLog(log);
			}
		}
	}
	
	private void addLog(Fwzyxybwcjb log){
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
		//System.out.println("write response log start");
		int reInt = 0;
		String num = "";
		if(logNum==1){
			if(!"1".equals(ResponseLogWriteDb.isWrite)){
				logNum = 2;
				num = "1";
				ResponseLogWriteDb writeDb = new ResponseLogWriteDb();
				reInt = writeDb.writeLogToDb(num);
			}
		}else if(logNum==2){
			if(!"2".equals(ResponseLogWriteDb.isWrite)){
				logNum = 3;
				num = "2";
				ResponseLogWriteDb writeDb = new ResponseLogWriteDb();
				reInt = writeDb.writeLogToDb(num);
			}
		}else if(logNum==3){
			if(!"3".equals(ResponseLogWriteDb.isWrite)){
				logNum = 1;
				num = "3";
				ResponseLogWriteDb writeDb = new ResponseLogWriteDb();
				reInt = writeDb.writeLogToDb(num);
			}
		}else if(logNum==4){
			if(!"4".equals(ResponseLogWriteDb.isWrite)){
				logNum = 1;
				num = "4";
				ResponseLogWriteDb writeDb = new ResponseLogWriteDb();
				reInt = writeDb.writeLogToDb(num);
			}
		}else if(logNum==5){
			if(!"5".equals(ResponseLogWriteDb.isWrite)){
				logNum = 1;
				num = "5";
				ResponseLogWriteDb writeDb = new ResponseLogWriteDb();
				reInt = writeDb.writeLogToDb(num);
			}
		}
		
		//System.out.println("write response log"+num+" num"+reInt+" end");
	}
}

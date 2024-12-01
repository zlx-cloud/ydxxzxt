package net.sinodata.business.util;

import java.util.Calendar;
import java.util.Date;

public class LogFormat {
	
	public LogFormat(Date log_timestamp,String log_level,String username,String dest_username,String action,String action_type,
			String action_level,String result,String addr,String port,
			String remote_addr,String remote_port,String message,String TrancingID,String device_id){
		this.setLog_timestamp(log_timestamp);
		this.setLog_level(log_level);
		this.setUsername(username);
		this.setDest_username(dest_username);
		this.setAction(action);
		this.setAction_type(action_type);
		this.setAction_level(action_level);
		this.setResult(result);
		this.setAddr(addr);
		this.setPort(port);
		this.setRemote_addr(remote_addr);
		this.setRemote_port(remote_port);
		this.setMessage(message);
		this.setTrancingID(TrancingID);
		this.setDevice_id(device_id);
	}
	
	public LogFormat(){}
	
	
	private Date log_timestamp;
	
	private String log_level;
	
	private String username;
	
	private String dest_username;
	
	private String action;
	
	private String action_type;
	
	private String action_level;
	
	private String result;
	
	private String addr;
	
	private String port;
	
	private String remote_addr;
	
	private String remote_port;
	
	private String message;
	
	private String trancingID;
	private String  BWLYIPDZ;
	private String BWLYDKH;
	private String BWMDIPDZ;
	private String BWMDDKH;
	private String BWRQ;
	private String device_id;
	
	public String getDevice_id() {
		return device_id;
	}

	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}

	public String getBWLYIPDZ() {
		return BWLYIPDZ;
	}

	public void setBWLYIPDZ(String bWLYIPDZ) {
		BWLYIPDZ = bWLYIPDZ;
	}

	public String getBWLYDKH() {
		return BWLYDKH;
	}

	public void setBWLYDKH(String bWLYDKH) {
		BWLYDKH = bWLYDKH;
	}

	public String getBWMDIPDZ() {
		return BWMDIPDZ;
	}

	public void setBWMDIPDZ(String bWMDIPDZ) {
		BWMDIPDZ = bWMDIPDZ;
	}

	public String getBWMDDKH() {
		return BWMDDKH;
	}

	public void setBWMDDKH(String bWMDDKH) {
		BWMDDKH = bWMDDKH;
	}

	public String getBWRQ() {
		return BWRQ;
	}

	public void setBWRQ(String bWRQ) {
		BWRQ = bWRQ;
	}

	public String getTrancingID() {
		return trancingID;
	}

	public void setTrancingID(String trancingID) {
		this.trancingID = trancingID;
	}
	public Date getLog_timestamp() {
		return log_timestamp;
	}

	public void setLog_timestamp(Date log_timestamp) {
		this.log_timestamp = log_timestamp;
	}

	public String getLog_level() {
		return log_level;
	}

	public void setLog_level(String log_level) {
		this.log_level = log_level;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDest_username() {
		return dest_username;
	}

	public void setDest_username(String dest_username) {
		this.dest_username = dest_username;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getAction_type() {
		return action_type;
	}

	public void setAction_type(String action_type) {
		this.action_type = action_type;
	}

	public String getAction_level() {
		return action_level;
	}

	public void setAction_level(String action_level) {
		this.action_level = action_level;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getRemote_addr() {
		return remote_addr;
	}

	public void setRemote_addr(String remote_addr) {
		this.remote_addr = remote_addr;
	}

	public String getRemote_port() {
		return remote_port;
	}

	public void setRemote_port(String remote_port) {
		this.remote_port = remote_port;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * @author Ma Weiliang
	 * 传入LogFormat对象，按照审计中心提供的日志格式，将对象内的信息按照一定顺序排列
	 * @param logFormat
	 * @return 按照规定格式，排列好的字符串
	 */
	public String getLogFormat(LogFormat logFormat){
		
		String reStr = "";
		
		//////时间戳(log_timestamp)
		Date log_timestamp = logFormat.getLog_timestamp();
		if(log_timestamp==null){
			log_timestamp = new Date();
		}
		Calendar ca = Calendar.getInstance();
		ca.setTime(log_timestamp);
		//时间戳格式为yyyy-mm-dd hh:mi:ss,fff
		String time = ca.get(ca.YEAR)+"-"+(ca.get(ca.MONTH)<9?"0"+(ca.get(ca.MONTH)+1):(ca.get(ca.MONTH)+1))+"-"+(ca.get(ca.DATE)<10?"0"+ca.get(ca.DATE):ca.get(ca.DATE))+" "
			+(ca.get(ca.HOUR)<10?"0"+ca.get(ca.HOUR):ca.get(ca.HOUR))+":"+(ca.get(ca.MINUTE)<10?"0"+ca.get(ca.MINUTE):ca.get(ca.MINUTE))+":"
			+(ca.get(ca.SECOND)<10?"0"+ca.get(ca.SECOND):ca.get(ca.SECOND))+","
			+(ca.get(ca.MILLISECOND)<10?"00"+ca.get(ca.MILLISECOND):(ca.get(ca.MILLISECOND)<100?"0"+ca.get(ca.MILLISECOND):ca.get(ca.MILLISECOND)));
		reStr += time+",";
		
		//////日志级别(log_level)，包括ERROR/WARN/INFO/TRACE
		String log_level = logFormat.getLog_level();
		if(log_level==null){
			log_level = "";
		}
		reStr += "log_level:"+log_level+",";
		
		//////源用户，事件的发起方(username)
		String username = logFormat.getUsername();
		if(username==null){
			username = "";
		}
		reStr += "username:"+username+",";
	//////设备编号
			String Device_id = logFormat.getDevice_id();
			if(Device_id==null){
				Device_id = "";
			}
			reStr += "device_id:"+Device_id+",";
		//////目的用户，事件的接受方(dest_username)
		String dest_username = logFormat.getDest_username();
		if(dest_username==null){
			dest_username = "";
		}
		reStr += "dest_username:"+dest_username+",";
		
	//////事件TrancingID
			String TrancingID = logFormat.getTrancingID();
			if(TrancingID==null){
				TrancingID = "";
			}
			reStr += "trancingID:"+TrancingID+",";
		//////事件名称(action)
		String action = logFormat.getAction();
		if(action==null){
			action = "";
		}
		reStr += "action:"+action+",";
		
		//////事件类型(action_type)
		String action_type = logFormat.getAction_type();
		if(action_type==null){
			action_type = "";
		}
		reStr += "action_type:"+action_type+",";
		
		//////事件级别，表示事件发生时的严重程度(action_level)，包括NORMAL/WARN/CRITICAL
		String action_level = logFormat.getAction_level();
		if(action_level==null){
			action_level = "";
		}
		reStr += "action_level:"+action_level+",";
		
		//////事件结果(result)，包括Failed/Success
		String result = logFormat.getResult();
		if(result==null){
			result = "";
		}
		reStr += "result:"+result+",";
		
		//////源地址，事件发起方的IP或hostname(addr)
		String addr = logFormat.getAddr();
		if(addr==null){
			addr = "";
		}
		reStr += "addr:"+addr+",";
		
		//////源端口，事件发起方的port(port)
		String port = logFormat.getPort();
		if(port==null){
			port = "";
		}
		reStr += "port:"+port+",";
		
		//////目的地址，事件接受方的IP或hostname(remote_addr)
		String remote_addr = logFormat.getRemote_addr();
		if(remote_addr==null){
			remote_addr = "";
		}
		reStr += "remote_addr:"+remote_addr+",";
		
		//////目的端口，事件接受方的port(remote_port)
		String remote_port = logFormat.getRemote_port();
		if(remote_port==null){
			remote_port = "";
		}
		reStr += "remote_port:"+remote_port+",";
		
		//////事件的额外描述，可以包含业务自有的其他字段(message)
		String message = logFormat.getMessage();
		if(message==null){
			message = "";
		}
		reStr += "message:"+message;
		
		return reStr;
	}
}

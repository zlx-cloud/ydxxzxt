package net.sinodata.esb.entity;

public class EsbMfInformation {

	private String id;
	
	private String pid;
	
	private String pname;
	
	private String pnodeList;
	
	private String startTime;
	
	private String endTime;
	
	private String runTime;
	
	private String state;
	
	private String runtimeException;
	
	private String exceptionData;
	
	private String exceptionNodeAlias;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getPnodeList() {
		return pnodeList;
	}

	public void setPnodeList(String pnodeList) {
		this.pnodeList = pnodeList;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getRuntimeException() {
		return runtimeException;
	}

	public void setRuntimeException(String runtimeException) {
		this.runtimeException = runtimeException;
	}

	public String getExceptionData() {
		return exceptionData;
	}

	public void setExceptionData(String exceptionData) {
		this.exceptionData = exceptionData;
	}

	public String getExceptionNodeAlias() {
		return exceptionNodeAlias;
	}

	public void setExceptionNodeAlias(String exceptionNodeAlias) {
		this.exceptionNodeAlias = exceptionNodeAlias;
	}

	public String getRunTime() {
		return runTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	
}

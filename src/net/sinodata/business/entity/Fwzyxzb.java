package net.sinodata.business.entity;

public class Fwzyxzb {
	private String fwbs;//服务标识
	private String fwxzzt;//服务限制状态 0 不启用  1启用
	private String fwzt;//服务状态  0启用 1禁用
	private String dayCount;//按天的使用上限
	private String hourCount;//按小时的使用上限
	private String minuteCount;//按分钟的使用上限
	
	private String yymc;//应用名称
	private String fwmc;//服务名称
	
	public String getFwbs() {
		return fwbs;
	}
	public void setFwbs(String fwbs) {
		this.fwbs = fwbs;
	}
	public String getFwxzzt() {
		return fwxzzt;
	}
	public void setFwxzzt(String fwxzzt) {
		this.fwxzzt = fwxzzt;
	}
	public String getFwzt() {
		return fwzt;
	}
	public void setFwzt(String fwzt) {
		this.fwzt = fwzt;
	}
	public String getDayCount() {
		return dayCount;
	}
	public void setDayCount(String dayCount) {
		this.dayCount = dayCount;
	}
	public String getHourCount() {
		return hourCount;
	}
	public void setHourCount(String hourCount) {
		this.hourCount = hourCount;
	}
	public String getMinuteCount() {
		return minuteCount;
	}
	public void setMinuteCount(String minuteCount) {
		this.minuteCount = minuteCount;
	}
	public String getYymc() {
		return yymc;
	}
	public void setYymc(String yymc) {
		this.yymc = yymc;
	}
	public String getFwmc() {
		return fwmc;
	}
	public void setFwmc(String fwmc) {
		this.fwmc = fwmc;
	}
	 
}
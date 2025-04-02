package net.sinodata.business.entity;

/**
 * 服务管控
 */
public class ConfigServiceManage {

	private String id;
	private String consumer;
	private String consumerName;
	private String yybs;
	private String yymc;
	private String fwbs;
	private String fwmc;
	private String ffbs;
	private String ffmc;
	private String ffms;
	private String concurrency;
	private String waitTime;
	private String serviceLevel;
	private String enabled;
	private String createTime;
	private String updateTime;

	public ConfigServiceManage() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getYybs() {
		return yybs;
	}

	public void setYybs(String yybs) {
		this.yybs = yybs;
	}

	public String getYymc() {
		return yymc;
	}

	public void setYymc(String yymc) {
		this.yymc = yymc;
	}

	public String getFwbs() {
		return fwbs;
	}

	public void setFwbs(String fwbs) {
		this.fwbs = fwbs;
	}

	public String getFwmc() {
		return fwmc;
	}

	public void setFwmc(String fwmc) {
		this.fwmc = fwmc;
	}

	public String getFfbs() {
		return ffbs;
	}

	public void setFfbs(String ffbs) {
		this.ffbs = ffbs;
	}

	public String getFfmc() {
		return ffmc;
	}

	public void setFfmc(String ffmc) {
		this.ffmc = ffmc;
	}

	public String getFfms() {
		return ffms;
	}

	public void setFfms(String ffms) {
		this.ffms = ffms;
	}

	public String getConcurrency() {
		return concurrency;
	}

	public void setConcurrency(String concurrency) {
		this.concurrency = concurrency;
	}

	public String getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}

	public String getServiceLevel() {
		return serviceLevel;
	}

	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	public String getEnabled() {
		return enabled;
	}

	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getConsumer() {
		return consumer;
	}

	public void setConsumer(String consumer) {
		this.consumer = consumer;
	}

	public String getConsumerName() {
		return consumerName;
	}

	public void setConsumerName(String consumerName) {
		this.consumerName = consumerName;
	}

}
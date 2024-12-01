package net.sinodata.business.entity;

public class SqxxShow {

	private String yybs;
	private String yymc;
	private String fwbs;
	private String fwmc;
	private String ffbs;
	private String ffmc;

	public SqxxShow() {
	}
	
	public SqxxShow(String yybs, String yymc, String fwbs, String fwmc, String ffbs, String ffmc) {
		this.yybs = yybs;
		this.yymc = yymc;
		this.fwbs = fwbs;
		this.fwmc = fwmc;
		this.ffbs = ffbs;
		this.ffmc = ffmc;
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

}
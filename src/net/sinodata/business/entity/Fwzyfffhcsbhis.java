package net.sinodata.business.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Fwzyfffhcsbhis {
    private String optId;

    private String optType;

    private String optApp;

    private String optName;

    private Date optTime;

    private String ffbs;

    private String fhcsbs;

    private String fhcsm;

    private String fhcslx;

    private BigDecimal fhcscd;

    private String fhcszy;

    private String fwbs;

    private String fwtgzYyxtbh;
    
	private String fwmc;
    private String ffmc;
    public Fwzyfffhcsbhis(){
    	
    }
   public Fwzyfffhcsbhis(Fwzyfffhcsb Fwzyfffhcsb){
	this.ffbs=Fwzyfffhcsb.getFfbs();
    this.fhcsbs=Fwzyfffhcsb.getFhcsbs();
    this.fhcsm=Fwzyfffhcsb.getFhcsm();
    this.fhcslx=Fwzyfffhcsb.getFhcslx();
    this.fhcscd=Fwzyfffhcsb.getFhcscd();
    this.fhcszy=Fwzyfffhcsb.getFhcszy();
    this.fwbs=Fwzyfffhcsb.getFwbs();
    this.fwtgzYyxtbh=Fwzyfffhcsb.getFwtgzYyxtbh();
}
    public String getOptId() {
        return optId;
    }

    public void setOptId(String optId) {
        this.optId = optId;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public String getOptApp() {
        return optApp;
    }

    public void setOptApp(String optApp) {
        this.optApp = optApp;
    }

    public String getOptName() {
        return optName;
    }

    public void setOptName(String optName) {
        this.optName = optName;
    }

    public Date getOptTime() {
        return optTime;
    }

    public void setOptTime(Date optTime) {
        this.optTime = optTime;
    }

    public String getFfbs() {
        return ffbs;
    }

    public void setFfbs(String ffbs) {
        this.ffbs = ffbs;
    }

    public String getFhcsbs() {
        return fhcsbs;
    }

    public void setFhcsbs(String fhcsbs) {
        this.fhcsbs = fhcsbs;
    }

    public String getFhcsm() {
        return fhcsm;
    }

    public void setFhcsm(String fhcsm) {
        this.fhcsm = fhcsm;
    }

    public String getFhcslx() {
        return fhcslx;
    }

    public void setFhcslx(String fhcslx) {
        this.fhcslx = fhcslx;
    }

    public BigDecimal getFhcscd() {
        return fhcscd;
    }

    public void setFhcscd(BigDecimal fhcscd) {
        this.fhcscd = fhcscd;
    }

    public String getFhcszy() {
        return fhcszy;
    }

    public void setFhcszy(String fhcszy) {
        this.fhcszy = fhcszy;
    }

    public String getFwbs() {
        return fwbs;
    }

    public void setFwbs(String fwbs) {
        this.fwbs = fwbs;
    }

    public String getFwtgzYyxtbh() {
        return fwtgzYyxtbh;
    }

    public void setFwtgzYyxtbh(String fwtgzYyxtbh) {
        this.fwtgzYyxtbh = fwtgzYyxtbh;
    }
	public String getFwmc() {
		return fwmc;
	}
	public void setFwmc(String fwmc) {
		this.fwmc = fwmc;
	}
	public String getFfmc() {
		return ffmc;
	}
	public void setFfmc(String ffmc) {
		this.ffmc = ffmc;
	}
    
}
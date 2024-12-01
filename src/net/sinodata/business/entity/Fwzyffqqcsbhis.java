package net.sinodata.business.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Fwzyffqqcsbhis {
    private String optId;

    private String optType;

    private String optApp;

    private String optName;

    private Date optTime;

    private String ffbs;

    private String qqcsbs;

    private String qqcsm;

    private String qqcslx;

    private BigDecimal qqcscd;

    private String qqcszy;

    private String fwbs;

    private String fwtgzYyxtbh;
	private String fwmc;
    private String ffmc;
    public Fwzyffqqcsbhis(){
    	
    }
    public Fwzyffqqcsbhis(Fwzyffqqcsb Fwzyffqqcsb){
       this.ffbs=Fwzyffqqcsb.getFfbs();
       this.qqcsbs=Fwzyffqqcsb.getQqcsbs();
       this.qqcsm=Fwzyffqqcsb.getQqcsm();
       this.qqcslx=Fwzyffqqcsb.getQqcslx();
       this.qqcscd=Fwzyffqqcsb.getQqcscd();
       this.qqcszy=Fwzyffqqcsb.getQqcszy();
       this.fwbs=Fwzyffqqcsb.getFwbs();
       this.fwtgzYyxtbh=Fwzyffqqcsb.getFwtgzYyxtbh();
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

    public String getQqcsbs() {
        return qqcsbs;
    }

    public void setQqcsbs(String qqcsbs) {
        this.qqcsbs = qqcsbs;
    }

    public String getQqcsm() {
        return qqcsm;
    }

    public void setQqcsm(String qqcsm) {
        this.qqcsm = qqcsm;
    }

    public String getQqcslx() {
        return qqcslx;
    }

    public void setQqcslx(String qqcslx) {
        this.qqcslx = qqcslx;
    }

    public BigDecimal getQqcscd() {
        return qqcscd;
    }

    public void setQqcscd(BigDecimal qqcscd) {
        this.qqcscd = qqcscd;
    }

    public String getQqcszy() {
        return qqcszy;
    }

    public void setQqcszy(String qqcszy) {
        this.qqcszy = qqcszy;
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
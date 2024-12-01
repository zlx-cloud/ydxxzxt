package net.sinodata.business.entity;

import java.math.BigDecimal;
import java.util.Date;

import net.sinodata.business.util.StringUtil;

public class Fwzyffzcbhis {
    private String optId;

    private String optType;

    private String optApp;

    private String optName;

    private Date optTime;

    private String ffbs;

    private String ffmc;

    private String ffl;

    private String ffms;

    private String fwbs;

    private String jzfl;

    private String czfl;

    private String sfhcsj;

    private BigDecimal sjyxsj;

    private String fwtgzYyxtbh;
    private String fwmc;
    private String jzlbmc;
    private String wlfl;
    private String fflb;
    public Fwzyffzcbhis(){
    	
    }
    public Fwzyffzcbhis(Fwzyffzcb Fwzyffzcb){
       this.ffbs=StringUtil.isEmpty(Fwzyffzcb.getFfbs())?null:Fwzyffzcb.getFfbs();
       this.ffmc=Fwzyffzcb.getFfmc();
       this.ffl=Fwzyffzcb.getFfl();
       this.ffms=Fwzyffzcb.getFfms();
       this.fwbs=Fwzyffzcb.getFwbs();
       this.jzfl=Fwzyffzcb.getJzfl();
       this.czfl=Fwzyffzcb.getCzfl();
       this.sfhcsj=Fwzyffzcb.getSfhcsj();
       this.sjyxsj=Fwzyffzcb.getSjyxsj();
       this.fwtgzYyxtbh=Fwzyffzcb.getFwtgzYyxtbh();
       this.wlfl=Fwzyffzcb.getWlfl();
       this.fflb=Fwzyffzcb.getFflb();
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

    public String getFfmc() {
        return ffmc;
    }

    public void setFfmc(String ffmc) {
        this.ffmc = ffmc;
    }

    public String getFfl() {
        return ffl;
    }

    public void setFfl(String ffl) {
        this.ffl = ffl;
    }

    public String getFfms() {
        return ffms;
    }

    public void setFfms(String ffms) {
        this.ffms = ffms;
    }

    public String getFwbs() {
        return fwbs;
    }

    public void setFwbs(String fwbs) {
        this.fwbs = fwbs;
    }

    public String getJzfl() {
        return jzfl;
    }

    public void setJzfl(String jzfl) {
        this.jzfl = jzfl;
    }

    public String getCzfl() {
        return czfl;
    }

    public void setCzfl(String czfl) {
        this.czfl = czfl;
    }

    public String getSfhcsj() {
        return sfhcsj;
    }

    public void setSfhcsj(String sfhcsj) {
        this.sfhcsj = sfhcsj;
    }

    public BigDecimal getSjyxsj() {
        return sjyxsj;
    }

    public void setSjyxsj(BigDecimal sjyxsj) {
        this.sjyxsj = sjyxsj;
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
	public String getJzlbmc() {
		return jzlbmc;
	}
	public void setJzlbmc(String jzlbmc) {
		this.jzlbmc = jzlbmc;
	}
	public String getWlfl() {
		return wlfl;
	}
	public void setWlfl(String wlfl) {
		this.wlfl = wlfl;
	}
	public String getFflb() {
		return fflb;
	}
	public void setFflb(String fflb) {
		this.fflb = fflb;
	}
    
}
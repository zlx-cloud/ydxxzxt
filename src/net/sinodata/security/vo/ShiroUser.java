package net.sinodata.security.vo;

import java.io.Serializable;
import java.util.Date;

import net.sinodata.business.entity.Fwcyfzcb;

import com.google.common.base.Objects;

public class ShiroUser implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2117324715101996906L;
	
	    private String id;
	    public  String loginName;

	    private String name;

	    private String password;

	    private String fwcyfMs;

	    private Date fwcyfRqsj;

	    private String fwcyfSsfj;

	    private String fwcyfLxfs;

	    private String lxrSm;

	    private String lxrXm;

	    private String lxdh;

	    private String dzxx;

	    private String txdz;

	    private String roleid;



	public ShiroUser(Fwcyfzcb Fwcyfzcb ) {
		this.id=Fwcyfzcb.getFwcyfYyxtbh();
	    this.loginName=Fwcyfzcb.getFwcyfYyxtmc();
        this.name=Fwcyfzcb.getFwcyfYyxtmc();
        this.password=Fwcyfzcb.getFwcyfDlmm();
        this.fwcyfMs=Fwcyfzcb.getFwcyfMs();
        this.fwcyfRqsj=Fwcyfzcb.getFwcyfRqsj();
       
	    this.fwcyfSsfj=Fwcyfzcb.getFwcyfSsfj();
        
	    this.fwcyfLxfs=Fwcyfzcb.getFwcyfLxfs();
      
	    this.lxrSm=Fwcyfzcb.getLxrSm();
      
	    this.lxrXm=Fwcyfzcb.getLxrXm();
     
	    this.lxdh=Fwcyfzcb.getLxdh();
    
	    this.dzxx=Fwcyfzcb.getDzxx();
      
	    this.txdz=Fwcyfzcb.getTxdz();
        
	    this.roleid=Fwcyfzcb.getRoleid();
	   
	}
	
	

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	@Override
	public String toString() {
		return id+","+loginName;
	}
	/**
	 * 重载hashCode,只计算userName;
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(loginName);
	}
	
	/**
	 * 重载equals,只计算loginName;
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ShiroUser other = (ShiroUser) obj;
		if (loginName == null) {
			if (other.loginName != null) {
				return false;
			}
		} else if (!loginName.equals(other.loginName)) {
			return false;
		}
		return true;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getLoginName() {
		return loginName;
	}



	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getFwcyfMs() {
		return fwcyfMs;
	}



	public void setFwcyfMs(String fwcyfMs) {
		this.fwcyfMs = fwcyfMs;
	}



	public Date getFwcyfRqsj() {
		return fwcyfRqsj;
	}



	public void setFwcyfRqsj(Date fwcyfRqsj) {
		this.fwcyfRqsj = fwcyfRqsj;
	}



	public String getFwcyfSsfj() {
		return fwcyfSsfj;
	}



	public void setFwcyfSsfj(String fwcyfSsfj) {
		this.fwcyfSsfj = fwcyfSsfj;
	}



	public String getFwcyfLxfs() {
		return fwcyfLxfs;
	}



	public void setFwcyfLxfs(String fwcyfLxfs) {
		this.fwcyfLxfs = fwcyfLxfs;
	}



	public String getLxrSm() {
		return lxrSm;
	}



	public void setLxrSm(String lxrSm) {
		this.lxrSm = lxrSm;
	}



	public String getLxrXm() {
		return lxrXm;
	}



	public void setLxrXm(String lxrXm) {
		this.lxrXm = lxrXm;
	}



	public String getLxdh() {
		return lxdh;
	}



	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}



	public String getDzxx() {
		return dzxx;
	}



	public void setDzxx(String dzxx) {
		this.dzxx = dzxx;
	}



	public String getTxdz() {
		return txdz;
	}



	public void setTxdz(String txdz) {
		this.txdz = txdz;
	}



	public String getRoleid() {
		return roleid;
	}



	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}
	
	

	
	



	

	


}	

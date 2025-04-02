package net.sinodata.business.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("application")
public class ConfigInfo {

	@Value("${serverurl}")
	private String serverurl;// 服务器的url
	@Value("${servername}")
	private String servername;// 服务器的账号
	@Value("${serverpassword}")
	private String serverpassword;// 服务器的密码
	@Value("${serverport}")
	private int serverport;// 服务器的端口

	@Value("${serverurl1}")
	private String serverurl1;// 服务器1的url
	@Value("${servername1}")
	private String servername1;// 服务器1的账号
	@Value("${serverpassword1}")
	private String serverpassword1;// 服务器1的密码
	@Value("${serverport1}")
	private int serverport1;// 服务器1的端口

	@Value("${serverurl2}")
	private String serverurl2;// 服务器2的url
	@Value("${servername2}")
	private String servername2;// 服务器2的账号
	@Value("${serverpassword2}")
	private String serverpassword2;// 服务器2的密码
	@Value("${serverport2}")
	private int serverport2;// 服务器2的端口

	@Value("${serverurl3}")
	private String serverurl3;// 服务器3的url
	@Value("${servername3}")
	private String servername3;// 服务器3的账号
	@Value("${serverpassword3}")
	private String serverpassword3;// 服务器3的密码
	@Value("${serverport3}")
	private int serverport3;// 服务器3的端口

	@Value("${serverurl4}")
	private String serverurl4;// 服务器4的url
	@Value("${servername4}")
	private String servername4;// 服务器4的账号
	@Value("${serverpassword4}")
	private String serverpassword4;// 服务器4的密码
	@Value("${serverport4}")
	private int serverport4;// 服务器4的端口
	@Value("${elasticsearch.table.fwbw}")
	private String esFwbw;// 服务器4的端口

	@Value("${new_es.ip}")
	private String ip;
	@Value("${new_es.port}")
	private int port;
	@Value("${new_es.username}")
	private String username;
	@Value("${new_es.password}")
	private String password;
	@Value("${new_es.logtable}")
	private String logTable;

	@Value("${parquet.url}")
	private String parquetUrl;
	@Value("${parquet.logtable}")
	private String parquetLogtable;
	
	@Value("${sync_data_url}")
	private String syncDataUrl;

	public String getServerurl() {
		return serverurl;
	}

	public void setServerurl(String serverurl) {
		this.serverurl = serverurl;
	}

	public String getServername() {
		return servername;
	}

	public void setServername(String servername) {
		this.servername = servername;
	}

	public String getServerpassword() {
		return serverpassword;
	}

	public void setServerpassword(String serverpassword) {
		this.serverpassword = serverpassword;
	}

	public String getServerurl1() {
		return serverurl1;
	}

	public void setServerurl1(String serverurl1) {
		this.serverurl1 = serverurl1;
	}

	public String getServername1() {
		return servername1;
	}

	public void setServername1(String servername1) {
		this.servername1 = servername1;
	}

	public String getServerpassword1() {
		return serverpassword1;
	}

	public void setServerpassword1(String serverpassword1) {
		this.serverpassword1 = serverpassword1;
	}

	public String getServerurl2() {
		return serverurl2;
	}

	public void setServerurl2(String serverurl2) {
		this.serverurl2 = serverurl2;
	}

	public String getServername2() {
		return servername2;
	}

	public void setServername2(String servername2) {
		this.servername2 = servername2;
	}

	public String getServerpassword2() {
		return serverpassword2;
	}

	public void setServerpassword2(String serverpassword2) {
		this.serverpassword2 = serverpassword2;
	}

	public String getServerurl3() {
		return serverurl3;
	}

	public void setServerurl3(String serverurl3) {
		this.serverurl3 = serverurl3;
	}

	public String getServername3() {
		return servername3;
	}

	public void setServername3(String servername3) {
		this.servername3 = servername3;
	}

	public String getServerpassword3() {
		return serverpassword3;
	}

	public void setServerpassword3(String serverpassword3) {
		this.serverpassword3 = serverpassword3;
	}

	public String getServerurl4() {
		return serverurl4;
	}

	public void setServerurl4(String serverurl4) {
		this.serverurl4 = serverurl4;
	}

	public String getServername4() {
		return servername4;
	}

	public void setServername4(String servername4) {
		this.servername4 = servername4;
	}

	public String getServerpassword4() {
		return serverpassword4;
	}

	public void setServerpassword4(String serverpassword4) {
		this.serverpassword4 = serverpassword4;
	}

	public int getServerport() {
		return serverport;
	}

	public void setServerport(int serverport) {
		this.serverport = serverport;
	}

	public int getServerport1() {
		return serverport1;
	}

	public void setServerport1(int serverport1) {
		this.serverport1 = serverport1;
	}

	public int getServerport2() {
		return serverport2;
	}

	public void setServerport2(int serverport2) {
		this.serverport2 = serverport2;
	}

	public int getServerport3() {
		return serverport3;
	}

	public void setServerport3(int serverport3) {
		this.serverport3 = serverport3;
	}

	public int getServerport4() {
		return serverport4;
	}

	public void setServerport4(int serverport4) {
		this.serverport4 = serverport4;
	}

	public String getEsFwbw() {
		return esFwbw;
	}

	public void setEsFwbw(String esFwbw) {
		this.esFwbw = esFwbw;
	}

	public String getLogTable() {
		return logTable;
	}

	public void setLogTable(String logTable) {
		this.logTable = logTable;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getParquetUrl() {
		return parquetUrl;
	}

	public void setParquetUrl(String parquetUrl) {
		this.parquetUrl = parquetUrl;
	}

	public String getParquetLogtable() {
		return parquetLogtable;
	}

	public void setParquetLogtable(String parquetLogtable) {
		this.parquetLogtable = parquetLogtable;
	}

	public String getSyncDataUrl() {
		return syncDataUrl;
	}

	public void setSyncDataUrl(String syncDataUrl) {
		this.syncDataUrl = syncDataUrl;
	}
	
}

package net.sinodata.business.entity;

public class FwOrg {

	private String id;
	
	private String name;
	
	private String path;
	
	private String parentpath;
	
	private String status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getParentpath() {
		return parentpath;
	}

	public void setParentpath(String parentpath) {
		this.parentpath = parentpath;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

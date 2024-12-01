package net.sinodata.esb.entity;

public class EsbVarSystem {

	private String varId;
	
	private String varName;
	
	private String varValue;
	
	private String varDesc;
	
	private String modifyTime;
	
	private String streamNum;

	public String getStreamNum() {
		return streamNum;
	}

	public void setStreamNum(String streamNum) {
		this.streamNum = streamNum;
	}

	public String getVarId() {
		return varId;
	}

	public void setVarId(String varId) {
		this.varId = varId;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getVarValue() {
		return varValue;
	}

	public void setVarValue(String varValue) {
		this.varValue = varValue;
	}

	public String getVarDesc() {
		return varDesc;
	}

	public void setVarDesc(String varDesc) {
		this.varDesc = varDesc;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}
}

package net.sinodata.business.entity;

public class EsLogDownload {

	private String uuid;
	private String notes;
	private String requestTime;
	private String responseTime;
	private String beforeWallTime;
	private String finishWallTime;
	private String begin3Time;
	private String finish3Time;
	private String status;
	private String requestData;
	private String responseData;

	public EsLogDownload() {
		super();
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getBeforeWallTime() {
		return beforeWallTime;
	}

	public void setBeforeWallTime(String beforeWallTime) {
		this.beforeWallTime = beforeWallTime;
	}

	public String getFinish3Time() {
		return finish3Time;
	}

	public void setFinish3Time(String finish3Time) {
		this.finish3Time = finish3Time;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public String getBegin3Time() {
		return begin3Time;
	}

	public void setBegin3Time(String begin3Time) {
		this.begin3Time = begin3Time;
	}

	public String getResponseData() {
		return responseData;
	}

	public void setResponseData(String responseData) {
		this.responseData = responseData;
	}

	public String getFinishWallTime() {
		return finishWallTime;
	}

	public void setFinishWallTime(String finishWallTime) {
		this.finishWallTime = finishWallTime;
	}

	public String getRequestData() {
		return requestData;
	}

	public void setRequestData(String requestData) {
		this.requestData = requestData;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
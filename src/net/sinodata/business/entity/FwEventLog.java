package net.sinodata.business.entity;

public class FwEventLog {
	private String src_sys_name;
	private String src_sys_id;
	private String src_biz_user_id;
	private String src_device_imei;
	private String dest_sys_name;
	private String dest_sys_id;
	private String dest_sys_interface;
	private String dest_sys_interface_desc;
	private String dest_device_addr;
	private String dest_device_port;
	private String event_tracing_id;
	private String event_type;
	private String event_result;
	private String event_result_desc;
	private String event_message;
	private String event_start_time;
	private String event_end_time;
	private String request_content;
	private String response_content;
	private String exception_content;
	private String id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getSrc_sys_name() {
		return src_sys_name;
	}
	public void setSrc_sys_name(String src_sys_name) {
		this.src_sys_name = src_sys_name;
	}
	public String getSrc_sys_id() {
		return src_sys_id;
	}
	public void setSrc_sys_id(String src_sys_id) {
		this.src_sys_id = src_sys_id;
	}
	public String getSrc_biz_user_id() {
		return src_biz_user_id;
	}
	public void setSrc_biz_user_id(String src_biz_user_id) {
		this.src_biz_user_id = src_biz_user_id;
	}
	public String getSrc_device_imei() {
		return src_device_imei;
	}
	public void setSrc_device_imei(String src_device_imei) {
		this.src_device_imei = src_device_imei;
	}
	public String getDest_sys_name() {
		return dest_sys_name;
	}
	public void setDest_sys_name(String dest_sys_name) {
		this.dest_sys_name = dest_sys_name;
	}
	public String getDest_sys_id() {
		return dest_sys_id;
	}
	public void setDest_sys_id(String dest_sys_id) {
		this.dest_sys_id = dest_sys_id;
	}
	public String getDest_sys_interface() {
		return dest_sys_interface;
	}
	public void setDest_sys_interface(String dest_sys_interface) {
		this.dest_sys_interface = dest_sys_interface;
	}
	public String getDest_sys_interface_desc() {
		return dest_sys_interface_desc;
	}
	public void setDest_sys_interface_desc(String dest_sys_interface_desc) {
		this.dest_sys_interface_desc = dest_sys_interface_desc;
	}
	public String getDest_device_addr() {
		return dest_device_addr;
	}
	public void setDest_device_addr(String dest_device_addr) {
		this.dest_device_addr = dest_device_addr;
	}
	public String getDest_device_port() {
		return dest_device_port;
	}
	public void setDest_device_port(String dest_device_port) {
		this.dest_device_port = dest_device_port;
	}
	public String getEvent_tracing_id() {
		return event_tracing_id;
	}
	public void setEvent_tracing_id(String event_tracing_id) {
		this.event_tracing_id = event_tracing_id;
	}
	public String getEvent_type() {
		return event_type;
	}
	public void setEvent_type(String event_type) {
		this.event_type = event_type;
	}
	public String getEvent_result() {
		return event_result;
	}
	public void setEvent_result(String event_result) {
		this.event_result = event_result;
	}
	public String getEvent_result_desc() {
		return event_result_desc;
	}
	public void setEvent_result_desc(String event_result_desc) {
		this.event_result_desc = event_result_desc;
	}
	public String getEvent_message() {
		return event_message;
	}
	public void setEvent_message(String event_message) {
		this.event_message = event_message;
	}
	public String getEvent_start_time() {
		return event_start_time;
	}
	public void setEvent_start_time(String event_start_time) {
		this.event_start_time = event_start_time;
	}
	public String getEvent_end_time() {
		return event_end_time;
	}
	public void setEvent_end_time(String event_end_time) {
		this.event_end_time = event_end_time;
	}
	public String getRequest_content() {
		return request_content;
	}
	public void setRequest_content(String request_content) {
		this.request_content = request_content;
	}
	public String getResponse_content() {
		return response_content;
	}
	public void setResponse_content(String response_content) {
		this.response_content = response_content;
	}
	public String getException_content() {
		return exception_content;
	}
	public void setException_content(String exception_content) {
		this.exception_content = exception_content;
	}

}

package com.wm.brta.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ServiceChannelWorkorderDTO {

	
	Integer number;
	String type;
	String description;
	String status_primary;
	String  status_extended;
	Date dispatch_date_time;
	
	ServiceChannelLocationDTO location;
	ServiceChannelAuditInfoDTO audit_info;
	
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus_primary() {
		return status_primary;
	}
	public void setStatus_primary(String status_primary) {
		this.status_primary = status_primary;
	}
	public String getStatus_extended() {
		return status_extended;
	}
	public void setStatus_extended(String status_extended) {
		this.status_extended = status_extended;
	}
	public Date getDispatch_date_time() {
		return dispatch_date_time;
	}
	public void setDispatch_date_time(Date dispatch_date_time) {
		this.dispatch_date_time = dispatch_date_time;
	}
	public ServiceChannelLocationDTO getLocation() {
		return location;
	}
	public void setLocation(ServiceChannelLocationDTO location) {
		this.location = location;
	}
	public ServiceChannelAuditInfoDTO getAudit_info() {
		return audit_info;
	}
	public void setAudit_info(ServiceChannelAuditInfoDTO audit_info) {
		this.audit_info = audit_info;
	}
	@Override
	public String toString() {
		return "ServiceChannelWorkorderDTO [number=" + number + ", type="
				+ type + ", description=" + description + ", status_primary="
				+ status_primary + ", status_extended=" + status_extended
				+ ", dispatch_date_time=" + dispatch_date_time + ", location="
				+ location + ", audit_info=" + audit_info + "]";
	}
	
	
	
}
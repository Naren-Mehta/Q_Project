package com.wm.brta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wm.brta.constants.ServiceChannelEnumConstants;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavedWorkOrdersToServiceChannel {
	
	private String status_primary;
	private ServiceChannelEnumConstants status_extended;
	private String comments;
	private String check_out_date_time;
	private String check_in_date_time;
	private Integer workOrderNumber;
	SavedWorkOrderLocationToServiceChannel location;

	public String getStatus_primary() {
		return status_primary;
	}
	public void setStatus_primary(String status_primary) {
		this.status_primary = status_primary;
	}
	
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	public ServiceChannelEnumConstants getStatus_extended() {
		return status_extended;
	}
	public void setStatus_extended(ServiceChannelEnumConstants status_extended) {
		this.status_extended = status_extended;
	}
	
	
	public SavedWorkOrderLocationToServiceChannel getLocation() {
		return location;
	}
	public void setLocation(SavedWorkOrderLocationToServiceChannel location) {
		this.location = location;
	}
	
	public Integer getWorkOrderNumber() {
		return workOrderNumber;
	}
	public void setWorkOrderNumber(Integer workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}
	public String getCheck_out_date_time() {
		return check_out_date_time;
	}
	public void setCheck_out_date_time(String check_out_date_time) {
		this.check_out_date_time = check_out_date_time;
	}
	public String getCheck_in_date_time() {
		return check_in_date_time;
	}
	public void setCheck_in_date_time(String check_in_date_time) {
		this.check_in_date_time = check_in_date_time;
	}
	@Override
	public String toString() {
		return "SavedWorkOrdersToServiceChannel [status_primary="
				+ status_primary + ", status_extended=" + status_extended
				+ ", comments=" + comments + ", check_out_date_time="
				+ check_out_date_time + ", check_in_date_time="
				+ check_in_date_time + ", workOrderNumber=" + workOrderNumber
				+ ", location=" + location + "]";
	}
	
	
	
}

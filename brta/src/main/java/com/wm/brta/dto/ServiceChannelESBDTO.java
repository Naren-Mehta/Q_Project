package com.wm.brta.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ServiceChannelESBDTO {

	
	String request_tracking_id;
	List<ServiceChannelWorkorderDTO> workorder_servicechannel;
	
	
	public String getRequest_tracking_id() {
		return request_tracking_id;
	}
	public void setRequest_tracking_id(String request_tracking_id) {
		this.request_tracking_id = request_tracking_id;
	}
	public List<ServiceChannelWorkorderDTO> getWorkorder_servicechannel() {
		return workorder_servicechannel;
	}
	public void setWorkorder_servicechannel(
			List<ServiceChannelWorkorderDTO> workorder_servicechannel) {
		this.workorder_servicechannel = workorder_servicechannel;
	}
	@Override
	public String toString() {
		return "ServiceChannelESBDTO [request_tracking_id="
				+ request_tracking_id + ", workorder_servicechannel="
				+ workorder_servicechannel + "]";
	}
	
	
	
	
	
}
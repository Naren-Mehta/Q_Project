package com.wm.brta.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class ServiceChannelAuditInfoDTO {

	Date created_date_time;

	public Date getCreated_date_time() {
		return created_date_time;
	}

	public void setCreated_date_time(Date created_date_time) {
		this.created_date_time = created_date_time;
	}

	@Override
	public String toString() {
		return "ServiceChannelAuditInfoDTO [created_date_time="
				+ created_date_time + "]";
	}

	
	
	
	
}

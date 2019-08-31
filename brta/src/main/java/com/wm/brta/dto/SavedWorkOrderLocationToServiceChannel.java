package com.wm.brta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavedWorkOrderLocationToServiceChannel {

	
	SavedWorkOrderAddressToServiceChannel address;

	public SavedWorkOrderAddressToServiceChannel getAddress() {
		return address;
	}

	public void setAddress(SavedWorkOrderAddressToServiceChannel address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "SavedWorkOrderLocationToServiceChannel [address=" + address
				+ "]";
	}

	
	
}

package com.wm.brta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SavedWorkOrderAddressToServiceChannel {

	private String longitude;
	private String latitude;
	
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	@Override
	public String toString() {
		return "SavedWorkOrderAddressToServiceChannel [latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}
	
}

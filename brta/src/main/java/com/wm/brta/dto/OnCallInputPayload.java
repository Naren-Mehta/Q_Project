package com.wm.brta.dto;

public class OnCallInputPayload 
{
	private Integer userId;
	private String onCallDate;
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getOnCallDate() {
		return onCallDate;
	}
	public void setOnCallDate(String onCallDate) {
		this.onCallDate = onCallDate;
	}
	
	
	
	
	@Override
	public String toString() {
		return "OnCallInputPayload [userId=" + userId + ", onCallDate="
				+ onCallDate + "]";
	}
	
	
	

}

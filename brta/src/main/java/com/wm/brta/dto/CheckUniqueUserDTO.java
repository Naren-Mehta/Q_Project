package com.wm.brta.dto;

public class CheckUniqueUserDTO {

	Integer supplierId;
	String emailId;
	String mobileNumber;
	
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	@Override
	public String toString() {
		return "CheckUniqueUser [supplierId=" + supplierId + ", emailId="
				+ emailId + ", mobileNumber=" + mobileNumber + "]";
	}

	
	
}
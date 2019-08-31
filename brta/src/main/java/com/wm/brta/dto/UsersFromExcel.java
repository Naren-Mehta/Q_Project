package com.wm.brta.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.wm.brta.domain.Supplier;

@JsonInclude(Include.NON_NULL)
public class UsersFromExcel {
	
	private String firstName;
	private String lastName;
	private String mobilePhone;
	private String emailId;
	private Supplier supplier;

	
	@JsonProperty("FIRST_NAME")
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@JsonProperty("LAST_NAME")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@JsonProperty("MOBILE_PHONE")
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@JsonProperty("EMAIL_ID")
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@Override
	public String toString() {
		return "UsersFromExcel [firstName=" + firstName + ", lastName="
				+ lastName + ", mobilePhone=" + mobilePhone + ", emailId="
				+ emailId  + supplier
				+ "]";
	}
	
	
	
	
	
	
}
package com.wm.brta.dto;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
@Component

public class UserDTO {
	
	private String firstName;
	private String lastName;
	private String role;
	private Long userId;
	private String mobilePhone;

	private String message;
	private String emailId;
	@JsonIgnore
	private boolean authenticationErrorFlag=false;
	
	
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	public UserDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRole() {
		return role;
	}
	
	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	

	public boolean isAuthenticationErrorFlag() {
		return authenticationErrorFlag;
	}

	public void setAuthenticationErrorFlag(boolean authenticationErrorFlag) {
		this.authenticationErrorFlag = authenticationErrorFlag;
	}
	
	

	@Override
	public String toString() {
		return "UserDTO [firstName=" + firstName + ", lastName=" + lastName
				+ ", role=" + role + ", userId=" + userId + ", mobilePhone="
				+ mobilePhone + ", message=" + message + ", emailId=" + emailId
				+ ", authenticationErrorFlag=" + authenticationErrorFlag + "]";
	}
	
	@PostConstruct
	public void reset(){
		this.userId=null;
		this.firstName=null;
		this.lastName = null;
	
	}
	
	
	

}

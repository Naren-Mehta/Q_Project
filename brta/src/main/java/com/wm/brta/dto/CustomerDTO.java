package com.wm.brta.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CustomerDTO {
	
	private Integer customerId;
	private  String customerName;
	private String legacyVendorId;
	private Integer customerStateId;
	private Date createdDate;
	private Date updatedAt;
	private String updatedBy;
	
	public CustomerDTO(){}
	
	public CustomerDTO(Integer customerId, String customerName,
			String legacyVendorId, Integer customerStateId, Date createdDate,
			Date updatedAt, String updatedBy) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.legacyVendorId = legacyVendorId;
		this.customerStateId = customerStateId;
		this.createdDate = createdDate;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getLegacyVendorId() {
		return legacyVendorId;
	}
	public void setLegacyVendorId(String legacyVendorId) {
		this.legacyVendorId = legacyVendorId;
	}
	public Integer getCustomerStateId() {
		return customerStateId;
	}
	public void setCustomerStateId(Integer customerStateId) {
		this.customerStateId = customerStateId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "CustomerDTO [customerId=" + customerId + ", customerName="
				+ customerName + ", legacyVendorId=" + legacyVendorId
				+ ", customerStateId=" + customerStateId + ", createdDate="
				+ createdDate + ", updatedAt=" + updatedAt + ", updatedBy="
				+ updatedBy + "]";
	}
	

	
	
	
	
	
}
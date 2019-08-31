package com.wm.brta.dto;

public class GetCustomerSiteDTO {

	Integer customerId;
	Integer supplierId;
	
	
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	@Override
	public String toString() {
		return "GetCustomerSiteDTO [customerId=" + customerId + ", supplierId="
				+ supplierId + "]";
	}
	
	
	
}

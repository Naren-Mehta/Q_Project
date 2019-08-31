package com.wm.brta.dao;

import java.sql.Date;

public class AssignDriverDTO {

	private Integer customerId;
	private Integer supplierId;
	private String state;
	private Date startDate;
	private Date endDate;
	
	
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
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "AssignDriverDTO [customerId=" + customerId + ", supplierId="
				+ supplierId + ", state=" + state + ", startDate=" + startDate
				+ ", endDate=" + endDate + "]";
	}
	
	
	
}
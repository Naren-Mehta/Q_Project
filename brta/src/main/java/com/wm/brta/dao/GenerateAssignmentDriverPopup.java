package com.wm.brta.dao;

import java.sql.Date;



public class GenerateAssignmentDriverPopup {
	
	private Integer customerSiteId;
	private Integer supplierId;
	private String Destination;
	private Integer dayDriver;
	private Integer nightDriver;
	private Date assignmentStartDate;
	private Date assignmentEndDate;

	
	
	public Integer getCustomerSiteId() {
		return customerSiteId;
	}
	public void setCustomerSiteId(Integer customerSiteId) {
		this.customerSiteId = customerSiteId;
	}
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	public String getDestination() {
		return Destination;
	}
	public void setDestination(String destination) {
		Destination = destination;
	}
	public Integer getDayDriver() {
		return dayDriver;
	}
	public void setDayDriver(Integer dayDriver) {
		this.dayDriver = dayDriver;
	}
	public Integer getNightDriver() {
		return nightDriver;
	}
	public void setNightDriver(Integer nightDriver) {
		this.nightDriver = nightDriver;
	}
	
	
	
		public Date getAssignmentStartDate() {
		return assignmentStartDate;
	}
	public void setAssignmentStartDate(Date assignmentStartDate) {
		this.assignmentStartDate = assignmentStartDate;
	}
	public Date getAssignmentEndDate() {
		return assignmentEndDate;
	}
	public void setAssignmentEndDate(Date assignmentEndDate) {
		this.assignmentEndDate = assignmentEndDate;
	}
	@Override
	public String toString() {
		return "GenerateAssignmentDriverPopup [customerSiteId="
				+ customerSiteId + ", supplierId=" + supplierId
				+ ", Destination=" + Destination + ", dayDriver=" + dayDriver
				+ ", nightDriver=" + nightDriver + ", assignmentStartDate="
				+ assignmentStartDate + ", assignmentEndDate="
				+ assignmentEndDate + "]";
	}
	
	
	

}

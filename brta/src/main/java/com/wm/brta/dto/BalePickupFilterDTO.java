package com.wm.brta.dto;


import java.sql.Date;

public class BalePickupFilterDTO {

	private Integer customerId;
	private Integer sellCustomerId;
	private Integer supplierId;
	private Integer incidentId;
	private Date startDate;
	private Date endDate;
	private Integer tripId;
	
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
	public Integer getSellCustomerId() {
		return sellCustomerId;
	}
	public void setSellCustomerId(Integer sellCustomerId) {
		this.sellCustomerId = sellCustomerId;
	}
	public Integer getTripId() {
		return tripId;
	}
	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}
	public Integer getIncidentId() {
		return incidentId;
	}
	public void setIncidentId(Integer incidentId) {
		this.incidentId = incidentId;
	}
	@Override
	public String toString() {
		return "BalePickupFilterDTO [customerId=" + customerId
				+ ", sellCustomerId=" + sellCustomerId + ", supplierId="
				+ supplierId + ", incidentId=" + incidentId + ", startDate="
				+ startDate + ", endDate=" + endDate + ", tripId=" + tripId
				+ "]";
	}
	
	
	
}	
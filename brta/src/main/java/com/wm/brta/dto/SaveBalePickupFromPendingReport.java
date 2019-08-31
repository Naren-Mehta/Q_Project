package com.wm.brta.dto;

public class SaveBalePickupFromPendingReport {
	
	
	private Integer buyCustomerSiteId;
	private Integer supplierId;
	private Integer materialId;
	private Integer tripId;
	private Integer driverId;
	private String pickupDate;
	private String comment;
	private Integer frequencyId;
	private String frequency;
	private Integer frequencyDay;

	public Integer getBuyCustomerSiteId() {
		return buyCustomerSiteId;
	}
	public void setBuyCustomerSiteId(Integer buyCustomerSiteId) {
		this.buyCustomerSiteId = buyCustomerSiteId;
	}
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	public Integer getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}
	public Integer getTripId() {
		return tripId;
	}
	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}
	public String getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}
	
	
	public Integer getDriverId() {
		return driverId;
	}
	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}
	
	
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	
	
	
	public String getFrequency() {
		return frequency;
	}
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	public Integer getFrequencyDay() {
		return frequencyDay;
	}
	public void setFrequencyDay(Integer frequencyDay) {
		this.frequencyDay = frequencyDay;
	}
	@Override
	public String toString() {
		return "SaveBalePickupFromPendingReport [buyCustomerSiteId="
				+ buyCustomerSiteId + ", supplierId=" + supplierId
				+ ", materialId=" + materialId + ", tripId=" + tripId
				+ ", driverId=" + driverId + ", pickupDate=" + pickupDate
				+ ", comment=" + comment + ", frequencyId=" + frequencyId
				+ ", frequency=" + frequency + ", frequencyDay=" + frequencyDay
				+ "]";
	}
	public Integer getFrequencyId() {
		return frequencyId;
	}
	public void setFrequencyId(Integer frequencyId) {
		this.frequencyId = frequencyId;
	}
	
	
	
	
	
	
	
	

}
package com.wm.brta.domain;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "balepickupsummaryview")
@JsonInclude(Include.NON_NULL)
public class EditPendingReportDetails {

	@Id
	@Column(name = "tripid")
	private Integer tripId;

	@Column(name = "materialid")
	private Integer materialId;

	@Column(name = "pickupdate")
	private Date pickupDate;
	
	@Column(name = "buycustomersiteid")
	private Integer buyCustomerSiteId;
	
	@Column(name = "supplierid") 		        
	 private Integer supplierId; 
	
	 @Column(name = "driverid") 		        
	 private Integer driverId; 
	 

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

	public Date getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}
	
	

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
	
	

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	@Override
	public String toString() {
		return "EditPendingReportDetailsDTO [materialId=" + materialId
				+ ", tripId=" + tripId + ", pickupDate=" + pickupDate + "]";
	}

}

package com.wm.brta.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Savedpickups")
public class SavedPickups {

	@Id
	@Column(name = "balepickupassignmentid")
	private Integer balePickupAssignmentId;

	@Column(name = "buycustomersitename")
	private String buyCustomerSiteName;

	@Column(name = "buycustomerhousenumber")
	private String buyCustomerHouseNumber;

	@Column(name = "buycustomeraddress1")
	private String buyCustomerAddress1;

	@Column(name = "buycustomeraddress2")
	private String buyCustomerAddress2;

	@Column(name = "buycustomeraddress3")
	private String buyCustomerAddress3;

	@Column(name = "buycustomeraddress4")
	private String buyCustomerAddress4;

	@Column(name = "buycustomeraddress5")
	private String buyCustomerAddress5;

	@Column(name = "buycustomerpostcode")
	private Integer buyCustomerPostCode;

	@Column(name = "driverid")
	private Integer driverId;
	
	@Column(name = "buycustomersiteid")
	private Integer buyCustomerSiteId;
	
	@Column(name = "sellcustomersiteid")
	private Integer sellCustomerSiteId;

	@Column(name = "pickupdate")
	private String pickupDate;

	public Integer getBalePickupAssignmentId() {
		return balePickupAssignmentId;
	}

	public void setBalePickupAssignmentId(Integer balePickupAssignmentId) {
		this.balePickupAssignmentId = balePickupAssignmentId;
	}

	public String getBuyCustomerSiteName() {
		return buyCustomerSiteName;
	}

	public void setBuyCustomerSiteName(String buyCustomerSiteName) {
		this.buyCustomerSiteName = buyCustomerSiteName;
	}

	public String getBuyCustomerHouseNumber() {
		return buyCustomerHouseNumber;
	}

	public void setBuyCustomerHouseNumber(String buyCustomerHouseNumber) {
		this.buyCustomerHouseNumber = buyCustomerHouseNumber;
	}

	public String getBuyCustomerAddress1() {
		return buyCustomerAddress1;
	}

	public void setBuyCustomerAddress1(String buyCustomerAddress1) {
		this.buyCustomerAddress1 = buyCustomerAddress1;
	}

	public String getBuyCustomerAddress2() {
		return buyCustomerAddress2;
	}

	public void setBuyCustomerAddress2(String buyCustomerAddress2) {
		this.buyCustomerAddress2 = buyCustomerAddress2;
	}

	public String getBuyCustomerAddress3() {
		return buyCustomerAddress3;
	}

	public void setBuyCustomerAddress3(String buyCustomerAddress3) {
		this.buyCustomerAddress3 = buyCustomerAddress3;
	}

	public String getBuyCustomerAddress4() {
		return buyCustomerAddress4;
	}

	public void setBuyCustomerAddress4(String buyCustomerAddress4) {
		this.buyCustomerAddress4 = buyCustomerAddress4;
	}

	public String getBuyCustomerAddress5() {
		return buyCustomerAddress5;
	}

	public void setBuyCustomerAddress5(String buyCustomerAddress5) {
		this.buyCustomerAddress5 = buyCustomerAddress5;
	}

	public Integer getBuyCustomerPostCode() {
		return buyCustomerPostCode;
	}

	public void setBuyCustomerPostCode(Integer buyCustomerPostCode) {
		this.buyCustomerPostCode = buyCustomerPostCode;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public Integer getBuyCustomerSiteId() {
		return buyCustomerSiteId;
	}

	public void setBuyCustomerSiteId(Integer buyCustomerSiteId) {
		this.buyCustomerSiteId = buyCustomerSiteId;
	}

	public Integer getSellCustomerSiteId() {
		return sellCustomerSiteId;
	}

	public void setSellCustomerSiteId(Integer sellCustomerSiteId) {
		this.sellCustomerSiteId = sellCustomerSiteId;
	}

	public String getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}

	@Override
	public String toString() {
		return "SavedPickups [balePickupAssignmentId=" + balePickupAssignmentId
				+ ", buyCustomerSiteName=" + buyCustomerSiteName
				+ ", buyCustomerHouseNumber=" + buyCustomerHouseNumber
				+ ", buyCustomerAddress1=" + buyCustomerAddress1
				+ ", buyCustomerAddress2=" + buyCustomerAddress2
				+ ", buyCustomerAddress3=" + buyCustomerAddress3
				+ ", buyCustomerAddress4=" + buyCustomerAddress4
				+ ", buyCustomeraddress5=" + buyCustomerAddress5
				+ ", buyCustomerPostCode=" + buyCustomerPostCode
				+ ", driverId=" + driverId + ", buyCustomerSiteId="
				+ buyCustomerSiteId + ", sellCustomerSiteId="
				+ sellCustomerSiteId + ", pickupDate=" + pickupDate + "]";
	}

	
	
	
}
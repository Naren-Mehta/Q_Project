package com.wm.brta.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class PickupDetailsFromExcel {
	private String buyCustomer;
	private String storeName;
	private String pickupDate;
	private String sellCUstomer;
	private String sellCustomerLocation;
	private String deliveryDate;
	private String releaseNumber;
	private String materialProfile;
	private Integer balesPicked;
	private Integer balesRemaining;
	private String supplierName;
	private String driverName;
	private String incident;
	private String comments;
	private Integer bol;
	private Integer destinationGross;
	private Integer destinationTare;
	
	
	@JsonProperty("DestinationGross")
	public Integer getdestinationGross() {
		return destinationGross;
	}
	public void setdestinationGross(Integer destinationGross) {
		this.destinationGross = destinationGross;
	}
	@JsonProperty("DestinationTare")
	public Integer getdestinationTare() {
		return destinationTare;
	}
	public void setdestinationTare(Integer destinationTare) {
		this.destinationTare = destinationTare;
	}
	@JsonProperty("BuyCustomer")
	public String getBuyCustomer() {
		return buyCustomer;
	}
	public void setBuyCustomer(String buyCustomer) {
		this.buyCustomer = buyCustomer;
	}
	@JsonProperty("StoreName")
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	@JsonProperty("PickupDate")
	public String getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}
	@JsonProperty("SellCustomer")
	public String getSellCUstomer() {
		return sellCUstomer;
	}
	public void setSellCUstomer(String sellCUstomer) {
		this.sellCUstomer = sellCUstomer;
	}
	@JsonProperty("SellCustomerLocation")
	public String getSellCustomerLocation() {
		return sellCustomerLocation;
	}
	public void setSellCustomerLocation(String sellCustomerLocation) {
		this.sellCustomerLocation = sellCustomerLocation;
	}
	@JsonProperty("DeliveryDate")
	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	@JsonProperty("ReleaseNumber")
	public String getReleaseNumber() {
		return releaseNumber;
	}
	public void setReleaseNumber(String releaseNumber) {
		this.releaseNumber = releaseNumber;
	}
	@JsonProperty("MaterialProfile")
	public String getMaterialProfile() {
		return materialProfile;
	}
	public void setMaterialProfile(String materialProfile) {
		this.materialProfile = materialProfile;
	}
	@JsonProperty("BalesPicked")
	public Integer getBalesPicked() {
		return balesPicked;
	}
	public void setBalesPicked(Integer balesPicked) {
		this.balesPicked = balesPicked;
	}
	@JsonProperty("BalesRemaining")
	public Integer getBalesRemaining() {
		return balesRemaining;
	}
	public void setBalesRemaining(Integer balesRemaining) {
		this.balesRemaining = balesRemaining;
	}
	@JsonProperty("SupplierName")
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	@JsonProperty("DriverName")
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	@JsonProperty("Incident")
	public String getIncident() {
		return incident;
	}
	public void setIncident(String incident) {
		this.incident = incident;
	}
	@JsonProperty("Comments")
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	@Override
	public String toString() {
		return "PickupDetailsFromExcel [buyCustomer=" + buyCustomer
				+ ", storeName=" + storeName + ", pickupDate=" + pickupDate
				+ ", sellCUstomer=" + sellCUstomer + ", sellCustomerLocation="
				+ sellCustomerLocation + ", deliveryDate=" + deliveryDate
				+ ", releaseNumber=" + releaseNumber + ", materialProfile="
				+ materialProfile + ", balesPicked=" + balesPicked
				+ ", balesRemaining=" + balesRemaining + ", supplierName="
				+ supplierName + ", driverName=" + driverName + ", incident="
				+ incident + ", comments=" + comments + "]";
	}
	@JsonProperty("Bol")
	public Integer getBol() {
		return bol;
	}
	public void setBol(Integer bol) {
		this.bol = bol;
	}

	
}	
	
	
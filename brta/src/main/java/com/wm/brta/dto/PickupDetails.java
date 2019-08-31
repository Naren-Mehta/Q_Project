package com.wm.brta.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wm.brta.domain.Image;
import com.wm.brta.domain.IncidentType;

@JsonInclude(Include.NON_NULL)
public class PickupDetails {

	

	private Integer storeId;
	private String storeName;
	private String address;
	private String pickupDate;
	private String oldPickupDate;
	private Integer oldMaterialId;
	private Integer oldDriverId;
	private String deliveryDate;
	private Integer balesPicked;
	private Integer balesRemaining;
	private List<Commodity> materialList;
	private IncidentType incidentType;
	private Integer BOL;
	private Destination destination;
	private String comments;
	private boolean incident;
	private Integer userId;
	private Integer grossWeight;
	private Integer tareWeight;
	private List<String> images;
	private List<Image> tripImages;
	private List<DriverDTO> drivers;
	private String releaseNotes;
	private Integer destinationId;
	private Boolean isServiceChannelCustomer;
	private Integer tripId;
	private boolean destinationDrop = true;
	private String vehiclePlateNo;
	private String longitude;
	private String latitude;
	
	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}

	public Boolean getIsServiceChannelCustomer() {
		return isServiceChannelCustomer;
	}

	public void setIsServiceChannelCustomer(Boolean isServiceChannelCustomer) {
		this.isServiceChannelCustomer = isServiceChannelCustomer;
	}
	
	public Integer getBalesPicked() {
		return balesPicked;
	}

	public void setBalesPicked(Integer balesPicked) {
		this.balesPicked = balesPicked;
	}

	public Integer getBalesRemaining() {
		return balesRemaining;
	}

	public void setBalesRemaining(Integer balesRemaining) {
		this.balesRemaining = balesRemaining;
	}

	public IncidentType getIncidentType() {
		return incidentType;
	}

	public void setIncidentType(IncidentType incidentType) {
		this.incidentType = incidentType;
	}

	public Integer getBOL() {
		return BOL;
	}

	public void setBOL(Integer bOL) {
		BOL = bOL;
	}

	public Destination getDestination() {
		return destination;
	}

	public void setDestination(Destination destination) {
		this.destination = destination;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean isIncident() {
		return incident;
	}

	public void setIncident(boolean incident) {
		this.incident = incident;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public List<Image> getTripImages() {
		return tripImages;
	}

	public void setTripImages(List<Image> tripImages) {
		this.tripImages = tripImages;
	}

	public List<Commodity> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<Commodity> materialList) {
		this.materialList = materialList;
	}

	public List<DriverDTO> getDrivers() {
		return drivers;
	}

	public void setDrivers(List<DriverDTO> drivers) {
		this.drivers = drivers;
	}

	public Integer getTripId() {
		return tripId;
	}

	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}

	public boolean isDestinationDrop() {
		return destinationDrop;
	}

	public void setDestinationDrop(boolean destinationDrop) {
		this.destinationDrop = destinationDrop;
	}

	public String getVehiclePlateNo() {
		return vehiclePlateNo;
	}

	public void setVehiclePlateNo(String vehiclePlateNo) {
		this.vehiclePlateNo = vehiclePlateNo;
	}

	public Integer getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Integer grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Integer getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(Integer tareWeight) {
		this.tareWeight = tareWeight;
	}

	public String getReleaseNotes() {
		return releaseNotes;
	}

	public void setReleaseNotes(String releaseNotes) {
		this.releaseNotes = releaseNotes;
	}

	public Integer getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(Integer destinationId) {
		this.destinationId = destinationId;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getOldPickupDate() {
		return oldPickupDate;
	}

	public void setOldPickupDate(String oldPickupDate) {
		this.oldPickupDate = oldPickupDate;
	}

	public Integer getOldMaterialId() {
		return oldMaterialId;
	}

	public void setOldMaterialId(Integer oldMaterialId) {
		this.oldMaterialId = oldMaterialId;
	}

	public Integer getOldDriverId() {
		return oldDriverId;
	}

	public void setOldDriverId(Integer oldDriverId) {
		this.oldDriverId = oldDriverId;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "PickupDetails [storeId=" + storeId + ", storeName=" + storeName
				+ ", address=" + address + ", pickupDate=" + pickupDate
				+ ", oldPickupDate=" + oldPickupDate + ", oldMaterialId="
				+ oldMaterialId + ", oldDriverId=" + oldDriverId
				+ ", deliveryDate=" + deliveryDate + ", balesPicked="
				+ balesPicked + ", balesRemaining=" + balesRemaining
				+ ", materialList=" + materialList + ", incidentType="
				+ incidentType + ", BOL=" + BOL + ", destination="
				+ destination + ", comments=" + comments + ", incident="
				+ incident + ", userId=" + userId + ", grossWeight="
				+ grossWeight + ", tareWeight=" + tareWeight + ", images="
				+ images + ", tripImages=" + tripImages + ", drivers="
				+ drivers + ", releaseNotes=" + releaseNotes
				+ ", destinationId=" + destinationId
				+ ", isServiceChannelCustomer=" + isServiceChannelCustomer
				+ ", tripId=" + tripId + ", destinationDrop=" + destinationDrop
				+ ", vehiclePlateNo=" + vehiclePlateNo + ", longitude="
				+ longitude + ", latitude=" + latitude + "]";
	}

}
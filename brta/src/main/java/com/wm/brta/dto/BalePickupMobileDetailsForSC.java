package com.wm.brta.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wm.brta.constants.ServiceChannelEnumConstants;

/**
 * @author nmehta
 *
 */
@JsonInclude(Include.NON_NULL)
public class BalePickupMobileDetailsForSC {


	private Integer customerSite;
	private String checkinDate;
	private String oldCheckinDate;
	private String checkoutDate;
	private ServiceChannelEnumConstants status;
	private Integer driver;
	private Integer supplierId;
	private Integer balesPicked;
	private Integer balesRemaining;
	private Integer material;
	private Integer oldMaterialId;
	private Integer oldDriverId;
	private String applicationType;
	private String incidentDescription;
	private String incidentNotes;
	private String resolution;
	private String latitude;
	private String longitude;
	private String updatedDevice;
	private String storeName;
	private Integer bol;
	private String timeZone;
	private String checkInLatitude;
	private String checkInLongitude;
	

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	private List<Commodity> materialList;
	private String truckId;

	public String getTruckId() {
		return truckId;
	}

	public void setTruckId(String truckId) {
		this.truckId = truckId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCustomerSite() {
		return customerSite;
	}

	public void setCustomerSite(Integer customerSite) {
		this.customerSite = customerSite;
	}

	public String getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(String checkinDate) {
		this.checkinDate = checkinDate;
	}

	public String getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(String checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public ServiceChannelEnumConstants getStatus() {
		return status;
	}

	public void setStatus(ServiceChannelEnumConstants status) {
		this.status = status;
	}

	public Integer getDriver() {
		return driver;
	}

	public void setDriver(Integer driver) {
		this.driver = driver;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
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

	public Integer getMaterial() {
		return material;
	}

	public void setMaterial(Integer material) {
		this.material = material;
	}

	public List<Commodity> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<Commodity> materialList) {
		this.materialList = materialList;
	}
	
	

	public Integer getOldMaterialId() {
		return oldMaterialId;
	}

	public void setOldMaterialId(Integer oldMaterialId) {
		this.oldMaterialId = oldMaterialId;
	}
	
	private Integer id;
	public String getIncidentDescription() {
		return incidentDescription;
	}

	public void setIncidentDescription(String incidentDescription) {
		this.incidentDescription = incidentDescription;
	}

	public String getIncidentNotes() {
		return incidentNotes;
	}

	public void setIncidentNotes(String incidentNotes) {
		this.incidentNotes = incidentNotes;
	}


	public String getOldCheckinDate() {
		return oldCheckinDate;
	}

	public void setOldCheckinDate(String oldCheckinDate) {
		this.oldCheckinDate = oldCheckinDate;
	}
	
	

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	
	

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Integer getOldDriverId() {
		return oldDriverId;
	}

	public void setOldDriverId(Integer oldDriverId) {
		this.oldDriverId = oldDriverId;
	}

	@Override
	public String toString() {
		return "BalePickupMobileDetailsForSC [customerSite=" + customerSite
				+ ", checkinDate=" + checkinDate + ", oldCheckinDate="
				+ oldCheckinDate + ", checkoutDate=" + checkoutDate
				+ ", status=" + status + ", driver=" + driver + ", supplierId="
				+ supplierId + ", balesPicked=" + balesPicked
				+ ", balesRemaining=" + balesRemaining + ", material="
				+ material + ", oldMaterialId=" + oldMaterialId
				+ ", oldDriverId=" + oldDriverId + ", applicationType="
				+ applicationType + ", incidentDescription="
				+ incidentDescription + ", incidentNotes=" + incidentNotes
				+ ", resolution=" + resolution + ", latitude=" + latitude
				+ ", longitude=" + longitude + ", updatedDevice="
				+ updatedDevice + ", storeName=" + storeName + ", bol=" + bol
				+ ", timeZone=" + timeZone + ", checkInLatitude="
				+ checkInLatitude + ", checkInLongitude=" + checkInLongitude
				+ ", materialList=" + materialList + ", truckId=" + truckId
				+ ", id=" + id + "]";
	}

	public String getUpdatedDevice() {
		return updatedDevice;
	}

	public void setUpdatedDevice(String updatedDevice) {
		this.updatedDevice = updatedDevice;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Integer getBol() {
		return bol;
	}

	public void setBol(Integer bol) {
		this.bol = bol;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getCheckInLatitude() {
		return checkInLatitude;
	}

	public void setCheckInLatitude(String checkInLatitude) {
		this.checkInLatitude = checkInLatitude;
	}

	public String getCheckInLongitude() {
		return checkInLongitude;
	}

	public void setCheckInLongitude(String checkInLongitude) {
		this.checkInLongitude = checkInLongitude;
	}

	

}
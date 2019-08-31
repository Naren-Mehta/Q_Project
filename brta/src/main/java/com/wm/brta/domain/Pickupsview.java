package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "Pickupsview")
@JsonInclude(Include.NON_NULL)
public class Pickupsview {

	@Id 
	@Column(name = "tripid")
	private Integer tripId;
	
	@Column(name = "balepickupassignmentid")
	private Integer balePickupAssignmentId;

	@Column(name = "driver")
	private Integer driver;

	@Column(name = "supplierid")
	private Integer supplierId;

	@Column(name = "weeknumber")
	private Integer weekNumber;

	@Column(name = "day")
	private Integer day;

	@Column(name = "divertdate")
	private Date divertDate;

	@Column(name = "diverteduserid")
	private Integer divertedUserId;

	@Column(name = "buycustomersiteid")
	private Integer buyCustomerSiteId;

	@Column(name = "sellcustomersiteid")
	private Integer sellCustomerSiteId;

	@Column(name = "materialid")
	private Integer materialId;

	@Column(name = "balespicked")
	private Integer balesPicked;

	@Column(name = "balesremaining")
	private Integer balesRemaining;

	@Column(name = "pickupdate")
	private Date pickupDate;

	@Column(name = "avgbaleweight")
	private Double avgBaleWeight;

	@Column(name = "pickedbaletotalweight")
	private Double pickedBaleTotalWeight;

	@Column(name = "bol")
	private Integer BOL;

	@Column(name = "incident")
	private Integer incident;

	@Column(name = "incidenttype")
	private Integer incidentType;

	@Column(name = "comments")
	private String comments;

	@Column(name = "enabled")
	private Boolean enabled;

	@Column(name = "loadtripid")
	private String loadTripId;

	@Column(name = "childloadtripid")
	private String childLoadTripId;

	@Column(name = "buycustomersitename")
	private String buyCustomerSiteName;

	@Column(name = "buycustomerlocationid")
	private Integer buyCustomerLocationId;

	@Column(name = "sellcustomersitename")
	private String sellCustomerSiteName;

	@Column(name = "sellcustomerlocationid")
	private Integer sellCustomerLocationId;

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
	private String buyCustomerPostCode;

	@Column(name = "buycustomertelno")
	private String buyCustomerTelNo;

	@Column(name = "sellcustomerhousenumber")
	private String sellCustomerHouseNumber;

	@Column(name = "sellcustomeraddress1")
	private String sellCustomerAddress1;

	@Column(name = "sellcustomeraddress2")
	private String sellCustomerAddress2;

	@Column(name = "sellcustomeraddress3")
	private String sellCustomerAddress3;

	@Column(name = "sellcustomeraddress4")
	private String sellCustomerAddress4;

	@Column(name = "sellcustomeraddress5")
	private String sellCustomerAddress5;

	@Column(name = "sellcustomerpostcode")
	private String sellCustomerPostCode;

	@Column(name = "sellcustomertelno")
	private String sellCustomerTelNo;

	@Column(name = "materialname")
	private String materialName;

	@Column(name = "materialshortname")
	private String materialShortName;

	@Column(name = "incidentname")
	private String incidentName;

	@Column(name = "incidentenabled")
	private String incidentEnabled;

	@Column(name = "buycustomerid")
	private Integer buyCustomerId;

	@Column(name = "buycustomername")
	private String buyCustomerName;

	@Column(name = "buycustomeralternativesearchreference")
	private String buyCustomerAlternativeSearchReference;

	@Column(name = "sellcustomerid")
	private Integer sellCustomerId;

	@Column(name = "sellcustomername")
	private String sellCustomerName;

	@Column(name = "sellcustomeralternativesearchreference")
	private String sellCustomerAlternativeSearchReference;

	@Column(name = "tripdriverfirstname")
	private String tripDriverFirstName;

	@Column(name = "tripdriverlastname")
	private String tripDriverLastName;

	@Column(name = "tripdriveremail")
	private String tripDriverEmail;

	@Column(name = "tripdrivermobile")
	private String tripDriverMobile;

	@Column(name = "releaseno")
	private String releaseNo;

	@Column(name = "buycustomersitealternativesearchreference")
	private String buyCustomerSiteAlternativeSearchReference;

	@Column(name = "sellcustomersitealternativesearchreference")
	private String sellCustomerSiteAlternativeSearchReference;
	
	@Column(name ="baselocationdrop")
	private Boolean baseLocationDrop;

	public Integer getBalePickupAssignmentId() {
		return balePickupAssignmentId;
	}

	public void setBalePickupAssignmentId(Integer balePickupAssignmentId) {
		this.balePickupAssignmentId = balePickupAssignmentId;
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

	public Integer getWeekNumber() {
		return weekNumber;
	}

	public void setWeekNumber(Integer weekNumber) {
		this.weekNumber = weekNumber;
	}

	public Integer getDay() {
		return day;
	}

	public void setDay(Integer day) {
		this.day = day;
	}

	public Date getDivertDate() {
		return divertDate;
	}

	public void setDivertDate(Date divertDate) {
		this.divertDate = divertDate;
	}

	public Integer getDivertedUserId() {
		return divertedUserId;
	}

	public void setDivertedUserId(Integer divertedUserId) {
		this.divertedUserId = divertedUserId;
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

	public Integer getTripId() {
		return tripId;
	}

	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
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

	public Date getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}

	public Double getAvgBaleWeight() {
		return avgBaleWeight;
	}

	public void setAvgBaleWeight(Double avgBaleWeight) {
		this.avgBaleWeight = avgBaleWeight;
	}

	public Double getPickedBaleTotalWeight() {
		return pickedBaleTotalWeight;
	}

	public void setPickedBaleTotalWeight(Double pickedBaleTotalWeight) {
		this.pickedBaleTotalWeight = pickedBaleTotalWeight;
	}

	public Integer getBOL() {
		return BOL;
	}

	public void setBOL(Integer bOL) {
		BOL = bOL;
	}

	public Integer getIncident() {
		return incident;
	}

	public void setIncident(Integer incident) {
		this.incident = incident;
	}

	public Integer getIncidentType() {
		return incidentType;
	}

	public void setIncidentType(Integer incidentType) {
		this.incidentType = incidentType;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getLoadTripId() {
		return loadTripId;
	}

	public void setLoadTripId(String loadTripId) {
		this.loadTripId = loadTripId;
	}

	public String getChildLoadTripId() {
		return childLoadTripId;
	}

	public void setChildLoadTripId(String childLoadTripId) {
		this.childLoadTripId = childLoadTripId;
	}

	public String getBuyCustomerSiteName() {
		return buyCustomerSiteName;
	}

	public void setBuyCustomerSiteName(String buyCustomerSiteName) {
		this.buyCustomerSiteName = buyCustomerSiteName;
	}

	public Integer getBuyCustomerLocationId() {
		return buyCustomerLocationId;
	}

	public void setBuyCustomerLocationId(Integer buyCustomerLocationId) {
		this.buyCustomerLocationId = buyCustomerLocationId;
	}

	public String getSellCustomerSiteName() {
		return sellCustomerSiteName;
	}

	public void setSellCustomerSiteName(String sellCustomerSiteName) {
		this.sellCustomerSiteName = sellCustomerSiteName;
	}

	public Integer getSellCustomerLocationId() {
		return sellCustomerLocationId;
	}

	public void setSellCustomerLocationId(Integer sellCustomerLocationId) {
		this.sellCustomerLocationId = sellCustomerLocationId;
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

	public String getBuyCustomerPostCode() {
		return buyCustomerPostCode;
	}

	public void setBuyCustomerPostCode(String buyCustomerPostCode) {
		this.buyCustomerPostCode = buyCustomerPostCode;
	}

	public String getBuyCustomerTelNo() {
		return buyCustomerTelNo;
	}

	public void setBuyCustomerTelNo(String buyCustomerTelNo) {
		this.buyCustomerTelNo = buyCustomerTelNo;
	}

	public String getSellCustomerHouseNumber() {
		return sellCustomerHouseNumber;
	}

	public void setSellCustomerHouseNumber(String sellCustomerHouseNumber) {
		this.sellCustomerHouseNumber = sellCustomerHouseNumber;
	}

	public String getSellCustomerAddress1() {
		return sellCustomerAddress1;
	}

	public void setSellCustomerAddress1(String sellCustomerAddress1) {
		this.sellCustomerAddress1 = sellCustomerAddress1;
	}

	public String getSellCustomerAddress2() {
		return sellCustomerAddress2;
	}

	public void setSellCustomerAddress2(String sellCustomerAddress2) {
		this.sellCustomerAddress2 = sellCustomerAddress2;
	}

	public String getSellCustomerAddress3() {
		return sellCustomerAddress3;
	}

	public void setSellCustomerAddress3(String sellCustomerAddress3) {
		this.sellCustomerAddress3 = sellCustomerAddress3;
	}

	public String getSellCustomerAddress4() {
		return sellCustomerAddress4;
	}

	public void setSellCustomerAddress4(String sellCustomerAddress4) {
		this.sellCustomerAddress4 = sellCustomerAddress4;
	}

	public String getSellCustomerAddress5() {
		return sellCustomerAddress5;
	}

	public void setSellCustomerAddress5(String sellCustomerAddress5) {
		this.sellCustomerAddress5 = sellCustomerAddress5;
	}

	public String getSellCustomerPostCode() {
		return sellCustomerPostCode;
	}

	public void setSellCustomerPostCode(String sellCustomerPostCode) {
		this.sellCustomerPostCode = sellCustomerPostCode;
	}

	public String getSellCustomerTelNo() {
		return sellCustomerTelNo;
	}

	public void setSellCustomerTelNo(String sellCustomerTelNo) {
		this.sellCustomerTelNo = sellCustomerTelNo;
	}

	public String getMaterialName() {
		return materialName;
	}

	public void setMaterialName(String materialName) {
		this.materialName = materialName;
	}

	public String getMaterialShortName() {
		return materialShortName;
	}

	public void setMaterialShortName(String materialShortName) {
		this.materialShortName = materialShortName;
	}

	public String getIncidentName() {
		return incidentName;
	}

	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	public String getIncidentEnabled() {
		return incidentEnabled;
	}

	public void setIncidentEnabled(String incidentEnabled) {
		this.incidentEnabled = incidentEnabled;
	}

	public Integer getBuyCustomerId() {
		return buyCustomerId;
	}

	public void setBuyCustomerId(Integer buyCustomerId) {
		this.buyCustomerId = buyCustomerId;
	}

	public String getBuyCustomerName() {
		return buyCustomerName;
	}

	public void setBuyCustomerName(String buyCustomerName) {
		this.buyCustomerName = buyCustomerName;
	}

	public String getBuyCustomerAlternativeSearchReference() {
		return buyCustomerAlternativeSearchReference;
	}

	public void setBuyCustomerAlternativeSearchReference(
			String buyCustomerAlternativeSearchReference) {
		this.buyCustomerAlternativeSearchReference = buyCustomerAlternativeSearchReference;
	}

	public Integer getSellCustomerId() {
		return sellCustomerId;
	}

	public void setSellCustomerId(Integer sellCustomerId) {
		this.sellCustomerId = sellCustomerId;
	}

	public String getSellCustomerName() {
		return sellCustomerName;
	}

	public void setSellCustomerName(String sellCustomerName) {
		this.sellCustomerName = sellCustomerName;
	}

	public String getSellCustomerAlternativeSearchReference() {
		return sellCustomerAlternativeSearchReference;
	}

	public void setSellCustomerAlternativeSearchReference(
			String sellCustomerAlternativeSearchReference) {
		this.sellCustomerAlternativeSearchReference = sellCustomerAlternativeSearchReference;
	}

	public String getTripDriverFirstName() {
		return tripDriverFirstName;
	}

	public void setTripDriverFirstName(String tripDriverFirstName) {
		this.tripDriverFirstName = tripDriverFirstName;
	}

	public String getTripDriverLastName() {
		return tripDriverLastName;
	}

	public void setTripDriverLastName(String tripDriverLastName) {
		this.tripDriverLastName = tripDriverLastName;
	}

	public String getTripDriverEmail() {
		return tripDriverEmail;
	}

	public void setTripDriverEmail(String tripDriverEmail) {
		this.tripDriverEmail = tripDriverEmail;
	}

	public String getTripDriverMobile() {
		return tripDriverMobile;
	}

	public void setTripDriverMobile(String tripDriverMobile) {
		this.tripDriverMobile = tripDriverMobile;
	}

	public String getReleaseNo() {
		return releaseNo;
	}

	public void setReleaseNo(String releaseNo) {
		this.releaseNo = releaseNo;
	}

	public String getBuyCustomerSiteAlternativeSearchReference() {
		return buyCustomerSiteAlternativeSearchReference;
	}

	public void setBuyCustomerSiteAlternativeSearchReference(
			String buyCustomerSiteAlternativeSearchReference) {
		this.buyCustomerSiteAlternativeSearchReference = buyCustomerSiteAlternativeSearchReference;
	}

	public String getSellCustomerSiteAlternativeSearchReference() {
		return sellCustomerSiteAlternativeSearchReference;
	}

	public void setSellCustomerSiteAlternativeSearchReference(
			String sellCustomerSiteAlternativeSearchReference) {
		this.sellCustomerSiteAlternativeSearchReference = sellCustomerSiteAlternativeSearchReference;
	}

	public Boolean isBaseLocationDrop() {
		return baseLocationDrop;
	}

	public void setBaseLocationDrop(Boolean baseLocationDrop) {
		this.baseLocationDrop = baseLocationDrop;
	}


	
}

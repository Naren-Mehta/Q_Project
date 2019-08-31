package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "getmaterialbycustomerandsupplier")
public class MaterialByCustomerAndSupplierSP {

	@Id
	@Column(name = "tripid")
	private Integer tripId;

	@Column(name = "materialid")
	private Integer materialId;

	@Column(name = "buycustomersiteid")
	private Integer buyCustomerSiteId;

	@Column(name = "description")
	private String description;

	@Column(name = "shortname")
	private String shortName;

	@Column(name = "pickupdate")
	private Date pickupDate;

	@Column(name = "balespicked")
	private Integer balesPicked;

	@Column(name = "balesremaining")
	private Integer balesRemaining;

	@Column(name = "bol")
	private Integer bol;

	@Column(name = "releaseno")
	private Integer releaseno;

	@Column(name = "grossweight")
	private Double grossWeight;

	@Column(name = "tareweight")
	private Double tareWeight;

	@Column(name = "userid")
	private Integer userId;

	@Column(name = "supplierid")
	private Integer supplierId;

	@Column(name = "vehicleplateno")
	private String vehiclePlateNo;

	@Column(name = "incident")
	private Boolean incident;

	@Column(name = "incidenttype")
	private Integer incidentTypeId;

	@Column(name = "incidentdescription")
	private String incidentDescription;

	@Column(name = "comments")
	private String comments;

	public Integer getBuyCustomerSiteId() {
		return buyCustomerSiteId;
	}

	public void setBuyCustomerSiteId(Integer buyCustomerSiteId) {
		this.buyCustomerSiteId = buyCustomerSiteId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Date getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
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

	public Integer getTripId() {
		return tripId;
	}

	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}

	public Integer getBol() {
		return bol;
	}

	public void setBol(Integer bol) {
		this.bol = bol;
	}

	public Integer getReleaseno() {
		return releaseno;
	}

	public void setReleaseno(Integer releaseno) {
		this.releaseno = releaseno;
	}

	public Double getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Double getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(Double tareWeight) {
		this.tareWeight = tareWeight;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getVehiclePlateNo() {
		return vehiclePlateNo;
	}

	public void setVehiclePlateNo(String vehiclePlateNo) {
		this.vehiclePlateNo = vehiclePlateNo;
	}

	public Boolean getIncident() {
		return incident;
	}

	public void setIncident(Boolean incident) {
		this.incident = incident;
	}

	public Integer getIncidentTypeId() {
		return incidentTypeId;
	}

	public void setIncidentTypeId(Integer incidentTypeId) {
		this.incidentTypeId = incidentTypeId;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getIncidentDescription() {
		return incidentDescription;
	}

	public void setIncidentDescription(Integer String) {
		this.incidentDescription = incidentDescription;
	}

	@Override
	public String toString() {
		return "MaterialByCustomerAndSupplierSP [tripId=" + tripId
				+ ", materialId=" + materialId + ", buyCustomerSiteId="
				+ buyCustomerSiteId + ", description=" + description
				+ ", shortName=" + shortName + ", pickupDate=" + pickupDate
				+ ", balesPicked=" + balesPicked + ", balesRemaining="
				+ balesRemaining + ", bol=" + bol + ", releaseno=" + releaseno
				+ ", grossWeight=" + grossWeight + ", tareWeight=" + tareWeight
				+ ", userId=" + userId + ", supplierId=" + supplierId
				+ ", vehiclePlateNo=" + vehiclePlateNo + ", incident="
				+ incident + ", incidentTypeId=" + incidentTypeId
				+ ", incidentDescription=" + incidentDescription
				+ ", comments=" + comments + "]";
	}

}
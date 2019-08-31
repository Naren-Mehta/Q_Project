package com.wm.brta.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "materialinfo")
public class MaterialInfo {

	@Id
	@Column(name = "materialid")
	private Integer materialId;
	
	@Column(name = "balepickupassignmentid")
	private Integer balePickupAssignmentId;

	@Column(name = "description")
	private String description;

	@Column(name = "shortname")
	private String shortName;

	@Column(name = "balespicked")
	private Integer balesPicked;

	@Column(name = "balesremaining")
	private Integer balesRemaining;
	
	@Column(name = "pickupdate")
	private String pickupDate;

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
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

	public Integer getBalePickupAssignmentId() {
		return balePickupAssignmentId;
	}

	public void setBalePickupAssignmentId(Integer balePickupAssignmentId) {
		this.balePickupAssignmentId = balePickupAssignmentId;
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

	public String getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}

	@Override
	public String toString() {
		return "MaterialInfo [materialId=" + materialId
				+ ", balePickupAssignmentId=" + balePickupAssignmentId
				+ ", description=" + description + ", shortName=" + shortName
				+ ", balesPicked=" + balesPicked + ", balesRemaining="
				+ balesRemaining + ", pickupDate=" + pickupDate + "]";
	}

	
	
}
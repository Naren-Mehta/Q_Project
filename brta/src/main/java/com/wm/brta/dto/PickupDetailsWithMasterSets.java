package com.wm.brta.dto;

import java.util.List;

import com.wm.brta.domain.IncidentType;
import com.wm.brta.domain.Material;

public class PickupDetailsWithMasterSets {
	
	
	PickupDetails pickupDetails;
	List<Material> materials;
	List<IncidentType> incidentTypes;
	List<Commodity> serviceChannelMaterial;
	
	public PickupDetails getPickupDetails() {
		return pickupDetails;
	}
	public void setPickupDetails(PickupDetails pickupDetails) {
		this.pickupDetails = pickupDetails;
	}
	
	
	public List<Commodity> getServiceChannelMaterial() {
		return serviceChannelMaterial;
	}
	public void setServiceChannelMaterial(List<Commodity> materialListServiceChannel) {
		this.serviceChannelMaterial = materialListServiceChannel;
	}
	public List<Material> getMaterials() {
		return materials;
	}
	public void setMaterials(List<Material> materials) {
		this.materials = materials;
	}
	public List<IncidentType> getIncidentTypes() {
		return incidentTypes;
	}
	public void setIncidentTypes(List<IncidentType> incidentTypes) {
		this.incidentTypes = incidentTypes;
	}
	@Override
	public String toString() {
		return "PickupDetailsWithMasterSets [pickupDetails=" + pickupDetails
				+ ", materials=" + materials + ", incidentTypes="
				+ incidentTypes + ", serviceChannelMaterial="
				+ serviceChannelMaterial + "]";
	}
	
}

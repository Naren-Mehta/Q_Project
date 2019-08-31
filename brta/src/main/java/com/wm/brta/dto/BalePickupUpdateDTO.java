package com.wm.brta.dto;

import java.util.Date;

import com.wm.brta.domain.BalePickupAssignment;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.IncidentType;
import com.wm.brta.domain.Material;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.User;

public class BalePickupUpdateDTO {
	
	private Integer tripId;
	private BalePickupAssignment balePickupAssignment;
	private CustomerSite buyCustomerSite;
	private Material material;
	private Integer buyLegacyVendorId;
	private Integer CompanyoutletId;
	private Integer balesPicked;
	private Integer balesRemaining;
	private Date pickupDate;
	private Float averageBaleWeight;
	private Float pickedBaleTotalWeight;
	private CustomerSite sellCustomerSite;
	private Integer sellLegacyVendorId;
	private Integer BOL;
	private boolean incident;
	private IncidentType incidentType;
	private String Comments;
	private Date createDate;
	private Date updatedAt;
	private boolean enabled;
	private Supplier supplier;
	private Integer weekNumber;
	private Integer day;
	private User user;
	private String loadTripId;
	private String childLoadTripId;
	public Integer getTripId() {
		return tripId;
	}
	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}
	public BalePickupAssignment getBalePickupAssignment() {
		return balePickupAssignment;
	}
	public void setBalePickupAssignment(BalePickupAssignment balePickupAssignment) {
		this.balePickupAssignment = balePickupAssignment;
	}
	public CustomerSite getBuyCustomerSite() {
		return buyCustomerSite;
	}
	public void setBuyCustomerSite(CustomerSite buyCustomerSite) {
		this.buyCustomerSite = buyCustomerSite;
	}
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	public Integer getBuyLegacyVendorId() {
		return buyLegacyVendorId;
	}
	public void setBuyLegacyVendorId(Integer buyLegacyVendorId) {
		this.buyLegacyVendorId = buyLegacyVendorId;
	}
	public Integer getCompanyoutletId() {
		return CompanyoutletId;
	}
	public void setCompanyoutletId(Integer companyoutletId) {
		CompanyoutletId = companyoutletId;
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
	public Float getAverageBaleWeight() {
		return averageBaleWeight;
	}
	public void setAverageBaleWeight(Float averageBaleWeight) {
		this.averageBaleWeight = averageBaleWeight;
	}
	public Float getPickedBaleTotalWeight() {
		return pickedBaleTotalWeight;
	}
	public void setPickedBaleTotalWeight(Float pickedBaleTotalWeight) {
		this.pickedBaleTotalWeight = pickedBaleTotalWeight;
	}
	public CustomerSite getSellCustomerSite() {
		return sellCustomerSite;
	}
	public void setSellCustomerSite(CustomerSite sellCustomerSite) {
		this.sellCustomerSite = sellCustomerSite;
	}
	public Integer getSellLegacyVendorId() {
		return sellLegacyVendorId;
	}
	public void setSellLegacyVendorId(Integer sellLegacyVendorId) {
		this.sellLegacyVendorId = sellLegacyVendorId;
	}
	public Integer getBOL() {
		return BOL;
	}
	public void setBOL(Integer bOL) {
		BOL = bOL;
	}
	public boolean isIncident() {
		return incident;
	}
	public void setIncident(boolean incident) {
		this.incident = incident;
	}
	public IncidentType getIncidentType() {
		return incidentType;
	}
	public void setIncidentType(IncidentType incidentType) {
		this.incidentType = incidentType;
	}
	public String getComments() {
		return Comments;
	}
	public void setComments(String comments) {
		Comments = comments;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	
	
	

}

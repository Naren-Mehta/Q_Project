package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "[balepickuptrip]")
public class BalePickupTrip {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tripid", nullable = false, unique = true)
	private Integer tripId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "buycustomersiteid", nullable = false)
	private CustomerSite buyCustomerSite;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "materialid", nullable = false)
	private Material material;

	@Column(name = "pickupdate", nullable = false)
	private Date pickupDate;
	
	@Column(name = "deliverydate")
	private Date deliveryDate;

	@Column(name = "balespicked", nullable = false)
	private Integer balesPicked;

	@Column(name = "balesremaining", nullable = false)
	private Integer balesRemaining;

	@Column(name = "bol", nullable = false)
	private Integer BOL;

	@Column(name = "releaseno", nullable = false)
	private String releaseNo;

	@Column(name = "grossweight", nullable = false)
	private Integer grossWeight;

	@Column(name = "tareweight", nullable = false)
	private Integer tareWeight;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "userid", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supplierid", nullable = false)
	private Supplier supplier;

	@Column(name = "vehicleplateno", nullable = false)
	private String vehiclePlateNo;

	@Column(name = "incident", nullable = false)
	private Boolean incident;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "incidenttype", nullable = false)
	private IncidentType incidentType;

	@Column(name = "comments", nullable = false)
	private String Comments;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sellcustomersiteid", nullable = false)
	private CustomerSite sellCustomerSite;

	@Column(name = "baselocationdrop", nullable = false)
	private String baseLocationDrop;

	@Column(name = "enabled", nullable = false)
	private boolean enabled;

	@Column(name = "createdate", nullable = false)
	private Date createDate;
	
	@Column(name = "updatedat", nullable = false)
	private Date updatedAt;
	
	@Column(name = "updatedby", nullable = false)
	private String updatedBy;
	
	@Column(name = "pendingresolutiontripid")
	private Integer pendingResolutionTripId;
	
	@Column(name = "pendingresolutioncomments")
	private String pendingResolutionComments;

	@Column(name = "avgbaleweight")
	private Integer avgBaleWeight;
	
	public Integer getTripId() {
		return tripId;
	}

	public void setTripId(Integer tripId) {
		this.tripId = tripId;
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

	public Integer getBOL() {
		return BOL;
	}

	public void setBOL(Integer bOL) {
		BOL = bOL;
	}

	public String getReleaseNo() {
		return releaseNo;
	}

	public void setReleaseNo(String releaseNo) {
		this.releaseNo = releaseNo;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	public String getVehiclePlateNo() {
		return vehiclePlateNo;
	}

	public void setVehiclePlateNo(String vehiclePlateNo) {
		this.vehiclePlateNo = vehiclePlateNo;
	}

	public Boolean isIncident() {
		return incident;
	}

	public void setIncident(Boolean incident) {
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

	public CustomerSite getSellCustomerSite() {
		return sellCustomerSite;
	}

	public void setSellCustomerSite(CustomerSite sellCustomerSite) {
		this.sellCustomerSite = sellCustomerSite;
	}

	public String getBaseLocationDrop() {
		return baseLocationDrop;
	}

	public void setBaseLocationDrop(String baseLocationDrop) {
		this.baseLocationDrop = baseLocationDrop;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "BalePickupTrip [tripId=" + tripId + ", buyCustomerSite="
				+ buyCustomerSite + ", material=" + material + ", pickupDate="
				+ pickupDate + ", deliveryDate=" + deliveryDate
				+ ", balesPicked=" + balesPicked + ", balesRemaining="
				+ balesRemaining + ", BOL=" + BOL + ", releaseNo=" + releaseNo
				+ ", grossWeight=" + grossWeight + ", tareWeight=" + tareWeight
				+ ", user=" + user + ", supplier=" + supplier
				+ ", vehiclePlateNo=" + vehiclePlateNo + ", incident="
				+ incident + ", incidentType=" + incidentType + ", Comments="
				+ Comments + ", sellCustomerSite=" + sellCustomerSite
				+ ", baseLocationDrop=" + baseLocationDrop + ", enabled="
				+ enabled + ", createDate=" + createDate + ", updatedAt="
				+ updatedAt + ", updatedBy=" + updatedBy
				+ ", pendingResolutionTripId=" + pendingResolutionTripId
				+ ", pendingResolutionComments=" + pendingResolutionComments
				+ ", avgBaleWeight=" + avgBaleWeight + "]";
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Integer getPendingResolutionTripId() {
		return pendingResolutionTripId;
	}

	public void setPendingResolutionTripId(Integer pendingResolutionTripId) {
		this.pendingResolutionTripId = pendingResolutionTripId;
	}

	public String getPendingResolutionComments() {
		return pendingResolutionComments;
	}

	public void setPendingResolutionComments(String pendingResolutionComments) {
		this.pendingResolutionComments = pendingResolutionComments;
	}

	public Integer getAvgBaleWeight() {
		return avgBaleWeight;
	}

	public void setAvgBaleWeight(Integer avgBaleWeight) {
		this.avgBaleWeight = avgBaleWeight;
	}

	public Boolean getIncident() {
		return incident;
	}

	public BalePickupTrip() {
		super();
	}
	
	public BalePickupTrip(Integer tripId) {
		super();
		this.tripId=tripId;
	}
	
	
	


}

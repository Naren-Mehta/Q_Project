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
import javax.persistence.Transient;

@Entity
@Table(name="[balepickupassignment]")
public class BalePickupAssignment {
	
	
	private Integer balePickupAssignmentId;
	private User user;
	private String buyLegacyVendorId;
	private String sellLegacyVendorId;
	private Date createDate;
	private Date updatedAt;
	private String updatedBy;
	private BalePickupCustomerSiteConfiguration balePickCustomerSiteConfiguration;
	private Supplier supplier;
	private Integer weekNumber;
	private Integer day;
	private Boolean disabled;
	private Date divertDate;
	private User divertedUser;
	
	private CustomerSite buyCustomerSite;
	private CustomerSite sellCustomerSite;
	
	private Integer frequency;
	
	
	private String nextPickupDate;
	
	private String pickupDate;
	
	
	
	@Transient
	public String getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(String pickupDate) {
		this.pickupDate = pickupDate;
	}
	
	
	@Transient
	public String getNextPickupDate() {
		return nextPickupDate;
	}
	public void setNextPickupDate(String nextPickupDate) {
		this.nextPickupDate = nextPickupDate;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "balepickupassignmentid", unique = true, nullable = false)
	public Integer getBalePickupAssignmentId() {
		return balePickupAssignmentId;
	}
	public void setBalePickupAssignmentId(Integer balePickupAssignmentId) {
		this.balePickupAssignmentId = balePickupAssignmentId;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="userid",nullable=false)
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Column(name="buylegacyvendorid",nullable=false)
	public String getBuyLegacyVendorId() {
		return buyLegacyVendorId;
	}
	public void setBuyLegacyVendorId(String buyLegacyVendorId) {
		this.buyLegacyVendorId = buyLegacyVendorId;
	}
	@Column(name="selllegacyvendorid",nullable=false)
	public String getSellLegacyVendorId() {
		return sellLegacyVendorId;
	}
	public void setSellLegacyVendorId(String sellLegacyVendorId) {
		this.sellLegacyVendorId = sellLegacyVendorId;
	}
	@Column(name="createdate",nullable=false)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name="updatedat",nullable=false)
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Column(name="updatedby",nullable=false)
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="balepickupcustomersiteconfigurationid")
	public BalePickupCustomerSiteConfiguration getBalePickCustomerSiteConfiguration() {
		return balePickCustomerSiteConfiguration;
	}
	public void setBalePickCustomerSiteConfiguration(
			BalePickupCustomerSiteConfiguration balePickupCustomerSiteConfiguration) {
		this.balePickCustomerSiteConfiguration = balePickupCustomerSiteConfiguration;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="supplierid",nullable =false)
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	@Column(name="weeknumber",nullable =false)
	public Integer getWeekNumber() {
		return weekNumber;
	}
	
	public void setWeekNumber(Integer weekNumber) {
		this.weekNumber = weekNumber;
	}
	@Column(name="day",nullable =false)
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	@Column(name="disabled",nullable =false)
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	
	
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="buycustomersiteid")
	public CustomerSite getBuyCustomerSite() {
		return buyCustomerSite;
	}
	public void setBuyCustomerSite(CustomerSite buyCustomerSite) {
		this.buyCustomerSite = buyCustomerSite;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="sellcustomersiteid")
	public CustomerSite getSellCustomerSite() {
		return sellCustomerSite;
	}
	public void setSellCustomerSite(CustomerSite sellCustomerSite) {
		this.sellCustomerSite = sellCustomerSite;
	}
	
	@Column(name="frequencyid")
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	@Column(name="divertdate")
	public Date getDivertDate() {
		return divertDate;
	}
	public void setDivertDate(Date divertDate) {
		this.divertDate = divertDate;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="diverteduserid",nullable=false)
	public User getDivertedUser() {
		return divertedUser;
	}
	public void setDivertedUser(User divertedUser) {
		this.divertedUser = divertedUser;
	}
	
	
	
	
	

}

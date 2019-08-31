package com.wm.brta.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "[customersite]")
public class CustomerSite {

	

	private Integer customerSiteId;
	private Customer customer;
	private Location location;
	private String siteName;
	private SalesTerritory salesTerritory;
	private Integer customerServiceRepId;
	private Integer salesRepId;
	@Override
	public String toString() {
		return "CustomerSite [customerSiteId=" + customerSiteId + ", siteName="
				+ siteName + ", salesTerritory=" + salesTerritory
				+ ", hasfrequency=" + hasfrequency + ", balePickupStartDate="
				+ balePickupStartDate + ", balePickupEndDate="
				+ balePickupEndDate + ", frequency=" + frequency
				+ ", frequencyDay=" + frequencyDay + "]";
	}

	private String sysUserName;
	private Date createdDate;
	private Date updatedAt;
	private String updatedBy;
	
	@Column(name = "hasfrequency")
	private Boolean hasfrequency;
	
	private Float avgBaleWeight;
	private Date balePickupStartDate;
	private Date balePickupEndDate;

	@Column(name = "isdeleted")
	private Boolean isdeleted;

	@Column(name = "customersitestateid")
	private Integer customersitestateid;

	private List<BalePickupMaterialConfiguration> balePickupMaterialConfiguration;
	private List<BalePickupSupplierSiteConfiguration> BalePickupSupplierSiteConfiguration;
	private List<String> configuredMaterials;
	private List<String> configuredSupplierSites;

	private String alternativeSearchReference;
	@OneToOne(fetch = FetchType.EAGER)
	private Integer frequency;

	private Integer frequencyDay;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "customerSite")
	@JsonBackReference(value = "materialConfigurations")
	@JsonIgnore
	public List<BalePickupMaterialConfiguration> getBalePickupMaterialConfiguration() {
		return balePickupMaterialConfiguration;
	}

	public void setBalePickupMaterialConfiguration(
			List<BalePickupMaterialConfiguration> balePickupMaterialConfiguration) {
		this.balePickupMaterialConfiguration = balePickupMaterialConfiguration;
	}

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "customerSite")
	@JsonBackReference(value = "supplierSiteConfigurations")
	@JsonIgnore
	public List<BalePickupSupplierSiteConfiguration> getBalePickupSupplierSiteConfiguration() {
		return BalePickupSupplierSiteConfiguration;
	}

	public void setBalePickupSupplierSiteConfiguration(
			List<BalePickupSupplierSiteConfiguration> balePickupSupplierSiteConfiguration) {
		BalePickupSupplierSiteConfiguration = balePickupSupplierSiteConfiguration;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customersiteid", unique = true, nullable = false)
	public Integer getCustomerSiteId() {
		return customerSiteId;
	}

	public void setCustomerSiteId(Integer customerSiteId) {
		this.customerSiteId = customerSiteId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customerid", nullable = false)
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "locationid", nullable = false)
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Column(name = "sitename", nullable = false)
	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "salesterritoryid", nullable = false)
	public SalesTerritory getSalesTerritory() {
		return salesTerritory;
	}

	public void setSalesTerritory(SalesTerritory salesTerritory) {
		this.salesTerritory = salesTerritory;
	}

	@Column(name = "customerservicerepid", nullable = false)
	public Integer getCustomerServiceRepId() {
		return customerServiceRepId;
	}

	public void setCustomerServiceRepId(Integer customerServiceRepId) {
		this.customerServiceRepId = customerServiceRepId;
	}

	@Column(name = "salesrepid", nullable = false)
	public Integer getSalesRepId() {
		return salesRepId;
	}

	public void setSalesRepId(Integer salesRepId) {
		this.salesRepId = salesRepId;
	}

	@Column(name = "sysusername")
	public String getSysUserName() {
		return sysUserName;
	}

	public void setSysUserName(String sysUserName) {
		this.sysUserName = sysUserName;
	}

	@Column(name = "createdate", nullable = false)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "updatedat", nullable = false)
	public Date getUpdatedAt() {

		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Column(name = "updatedby", nullable = false)
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	

	@Column(name = "balepickupstartdate")
	public Date getBalePickupStartDate() {
		return balePickupStartDate;
	}

	public void setBalePickupStartDate(Date balePickupStartDate) {
		this.balePickupStartDate = balePickupStartDate;
	}

	@Column(name = "balepickupenddate")
	public Date getBalePickupEndDate() {
		return balePickupEndDate;
	}

	public void setBalePickupEndDate(Date balePickupEndDate) {
		this.balePickupEndDate = balePickupEndDate;
	}

	@Column(name = "avgbaleweight")
	public Float getAvgBaleWeight() {
		return avgBaleWeight;
	}

	public void setAvgBaleWeight(Float avgBaleWeight) {
		this.avgBaleWeight = avgBaleWeight;
	}

	@Transient
	public List<String> getConfiguredSupplierSites() {
		return configuredSupplierSites;
	}

	public void setConfiguredSupplierSites(List<String> configuredSupplierSites) {
		this.configuredSupplierSites = configuredSupplierSites;
	}

	@Transient
	public List<String> getConfiguredMaterials() {
		return configuredMaterials;
	}

	public void setConfiguredMaterials(List<String> configuredMaterials) {
		this.configuredMaterials = configuredMaterials;
	}

	@Column(name = "alternativesearchreference")
	public String getAlternativeSearchReference() {
		return alternativeSearchReference;
	}

	public void setAlternativeSearchReference(String alternativeSearchReference) {
		this.alternativeSearchReference = alternativeSearchReference;
	}

	// @OneToOne(fetch = FetchType.EAGER)
	// @JoinColumn(name="frequencyid",nullable=false)

	@Column(name = "frequencyid")
	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	@Column(name = "frequencyday")
	public Integer getFrequencyDay() {
		return frequencyDay;
	}

	public void setFrequencyDay(Integer frequencyDay) {
		this.frequencyDay = frequencyDay;
	}

	

	public Boolean getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	

	public Integer getCustomersitestateid() {
		return customersitestateid;
	}

	public void setCustomersitestateid(Integer customersitestateid) {
		this.customersitestateid = customersitestateid;
	}


	public Boolean getHasfrequency() {
		return hasfrequency;
	}

	public void setHasfrequency(Boolean hasfrequency) {
		this.hasfrequency = hasfrequency;
	}

	public CustomerSite(Integer customerSiteId, String siteName) {
		super();
		this.customerSiteId = customerSiteId;
		this.siteName = siteName;
	}

	public CustomerSite(Integer customerSiteId, String siteName, Integer customerId) {
		super();
		this.customerSiteId = customerSiteId;
		this.siteName = siteName;
		 Customer customer= new Customer();
		 customer.setCustomerId(customerId);
		 this.customer =customer;
	}
	
	public CustomerSite(Integer customerId)
	{
		super();
		this.customer.setCustomerId(customerId);
	}
	public CustomerSite() {
		super();
	}

	public CustomerSite(Integer customerId, String customerName,
			String legacyVendorId, Integer customerStateId,
			Date customerCreatedDate, Date customerUpdatedAt,
			String customerUpdatedBy, Integer customerSiteId, String siteName,
			Date createdDate, Date updatedAt, String updatedBy,
			Float avgBaleWeight, Date balePickupStartDate,
			Date balePickupEndDate) {
		super();
		this.customer = new Customer(customerId, customerName, legacyVendorId,
				customerStateId, customerCreatedDate, customerUpdatedAt,
				customerUpdatedBy);
		this.customerSiteId = customerSiteId;
		this.siteName = siteName;
		this.createdDate = createdDate;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
		this.avgBaleWeight = avgBaleWeight;
		this.balePickupStartDate = balePickupStartDate;
		this.balePickupEndDate = balePickupEndDate;
	}

}

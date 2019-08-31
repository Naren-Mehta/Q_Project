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
@Table(name = "[balepickupsupplierconfiguration]")
public class BalePickupSupplierConfiguration {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "balepickupsupplierconfigurationid", unique = true, nullable = false)
	private Integer balePickupSupplierConfigurationId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "buycustomersiteid", nullable = false)
	private CustomerSite customerSite;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supplierid", nullable = false)
	private Supplier supplier;

	@Column(name = "createdate", nullable = false)
	private Date createDate;

	@Column(name = "updatedat", nullable = false)
	private Date updatedAt;

	@Column(name = "enabled", nullable = false)
	private Boolean enabled;

	@Column(name = "updatedby", nullable = false)
	private String updatedBy;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "driver1id", nullable = false)
	private User driver1;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "driver2id", nullable = false)
	private User driver2;
	
	
	@Column(name="startdate")
	private Date startDate;
	
	@Column(name="enddate")
	private Date endDate;
	
	

	public Integer getBalePickupSupplierConfigurationId() {
		return balePickupSupplierConfigurationId;
	}

	public void setBalePickupSupplierConfigurationId(
			Integer balePickupSupplierConfigurationId) {
		this.balePickupSupplierConfigurationId = balePickupSupplierConfigurationId;
	}

	public CustomerSite getCustomerSite() {
		return customerSite;
	}

	public void setCustomerSite(CustomerSite customerSite) {
		this.customerSite = customerSite;
	}

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
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

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public User getDriver1() {
		return driver1;
	}

	public void setDriver1(User driver1) {
		this.driver1 = driver1;
	}

	public User getDriver2() {
		return driver2;
	}

	public void setDriver2(User driver2) {
		this.driver2 = driver2;
	}
	
	

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "BalePickupSupplierConfiguration [balePickupSupplierConfigurationId="
				+ balePickupSupplierConfigurationId
				+ ", customerSite="
				+ customerSite
				+ ", supplier="
				+ supplier
				+ ", createDate="
				+ createDate
				+ ", updatedAt="
				+ updatedAt
				+ ", enabled="
				+ enabled
				+ ", updatedBy="
				+ updatedBy
				+ ", driver1="
				+ driver1
				+ ", driver2=" + driver2 + "]";
	}

	public BalePickupSupplierConfiguration(CustomerSite customerSite,
			Supplier supplier, Date createDate, Date updatedAt,
			Boolean enabled, String updatedBy) {
		super();
		this.customerSite = customerSite;
		this.supplier = supplier;
		this.createDate = createDate;
		this.updatedAt = updatedAt;
		this.enabled = enabled;
		this.updatedBy = updatedBy;
	}

	public BalePickupSupplierConfiguration() {
		super();
	}
	
	public BalePickupSupplierConfiguration(Integer balePickupSupplierConfigurationId) {
		super();
		this.balePickupSupplierConfigurationId=balePickupSupplierConfigurationId;
	}
	
	
	
	
	


	
	
}
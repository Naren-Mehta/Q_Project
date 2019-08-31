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
@Table(name="[balepickupsuppliersiteconfiguration]")
public class BalePickupSupplierSiteConfiguration {
	
	private Integer balePickupSupplierSiteConfigurationId;
	private CustomerSite customerSite;
	private SupplierSite supplierSite;
	private Date createDate;
	private Date updatedAt;
	private Boolean enabled;
	private String updatedBy;
	
	
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="balepickupsuppliersiteconfigurationid",unique = true,nullable = false)
	public Integer getBalePickupSupplierSiteConfigurationId() {
		return balePickupSupplierSiteConfigurationId;
	}


	public void setBalePickupSupplierSiteConfigurationId(
			Integer balePickupSupplierSiteConfigurationId) {
		this.balePickupSupplierSiteConfigurationId = balePickupSupplierSiteConfigurationId;
	}

   @ManyToOne(fetch= FetchType.EAGER)
   @JoinColumn(name="buycustomersiteid",nullable = false)
   
	public CustomerSite getCustomerSite() {
		return customerSite;
	}


	public void setCustomerSite(CustomerSite customerSite) {
		this.customerSite = customerSite;
	}

	@ManyToOne(fetch= FetchType.EAGER)
	  @JoinColumn(name="suppliersiteid",nullable = false)
	public SupplierSite getSupplierSite() {
		return supplierSite;
	}


	public void setSupplierSite(SupplierSite supplierSite) {
		this.supplierSite = supplierSite;
	}

   @Column(name="createdate",nullable =false)
	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

   @Column(name="updatedat",nullable = false)
	public Date getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	 @Column(name="enabled",nullable = false)
	public Boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	 @Column(name="updatedby",nullable = false)
	public String getUpdatedBy() {
		return updatedBy;
	}


	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

   


	@Override
	public String toString() {
		return "BalePickupSupplierSiteConfiguration [balePickupSupplierSiteConfigurationId="
				+ balePickupSupplierSiteConfigurationId
				+ ", supplierSite="
				+ supplierSite
				+ ", customerSite="
				+ customerSite
				+ ", createDate="
				+ createDate
				+ ", updatedAt="
				+ updatedAt
				+ ", enabled=" + enabled + ", updatedBy=" + updatedBy + "]";
	}
}

package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;



@Entity
@Table(name ="[getpendingpickups]")
@JsonInclude(Include.NON_NULL)
public class PendingReport {
	@Id
	@Column(name = "customersiteid")
	private Integer customerSiteId;
	
	@Column(name = "customerid")
	private Integer customerId;
	
	@Column(name = "customername")
	private String customerName;
	
	/*@Column(name = "alternativesearchreference")
	private String alternativeSearchReference;
	*/
	@Column(name = "sitename")
	private String siteName;
	
	@Column(name = "frequencyid") //check type
	private Integer frequencyId;
	
	@Column(name = "frequency") //check type
	private String frequency;
	
	@Column(name = "frequencyday")
	private String frequencyDay;
	
	@Column(name = "suppliername")
	private String supplierName;
	
	@Column(name="lastpickupdate")
	private Date lastPickupDate;
	
	
	public Integer getCustomerSiteId() {
		return customerSiteId;
	}

	public void setCustomerSiteId(Integer customerSiteId) {
		this.customerSiteId = customerSiteId;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Integer getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(Integer frequencyId) {
		this.frequencyId = frequencyId;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFrequencyDay() {
		return frequencyDay;
	}

	public void setFrequencyDay(String frequencyDay) {
		this.frequencyDay = frequencyDay;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	
	

	public Date getLastPickupDate() {
		return lastPickupDate;
	}

	public void setLastPickupDate(Date lastPickupDate) {
		this.lastPickupDate = lastPickupDate;
	}

	@Override
	public String toString() {
		return "PendingReport [customerSiteId=" + customerSiteId
				+ ", customerId=" + customerId + ", customerName="
				+ customerName + ", siteName=" + siteName + ", frequencyId="
				+ frequencyId + ", frequency=" + frequency + ", frequencyDay="
				+ frequencyDay + ", supplierName=" + supplierName + "]";
	}
	
	
	
	
}
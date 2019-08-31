package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "storelistsetupview")
@JsonInclude(Include.NON_NULL)
public class StoreListView {

	@Id
	@Column(name = "customersiteid")
	private Integer customerSiteId;

	@Column(name = "customerid")
	private Integer customerId;

	@Column(name = "sitename")
	private String siteName;

	@Column(name = "locationid")
	private Integer locationId;

	@Column(name = "balepickupstartdate")
	private Date balePickupStartDate;

	@Column(name = "balepickupenddate")
	private Date balePickupEndDate;

	@Column(name = "customersitealternativesearchreference")
	private String customerSiteAlternativeSearchReference;

	@Column(name = "housenumber")
	private String houseNumber;

	@Column(name = "address1")
	private String address1;

	@Column(name = "address2")
	private String address2;

	@Column(name = "address3")
	private String address3;

	@Column(name = "address4")
	private String address4;

	@Column(name = "address5")
	private String address5;

	@Column(name = "postcode")
	private String postCode;

	@Column(name = "telno")
	private String telNo;

	@Column(name = "sell_customer_available")
	private String sellCustomerAvailable;

	@Column(name = "supplier_available")
	private String supplierAvailable;

	@Column(name = "materialavailable")
	private String materialAvailable;

	@Column(name = "customeralternativesearchreference")
	private String customerAlternativeSearchReference;

	@Column(name = "customername")
	private String customerName;

	@Column(name = "frequencyid")
	private Integer frequencyId;

	@Column(name = "frequency")
	private String frequency;

	@Column(name = "frequencyday")
	private Integer frequencyDay;

	@Transient
	private String address;

	@Transient
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

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

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public Date getBalePickupStartDate() {
		return balePickupStartDate;
	}

	public void setBalePickupStartDate(Date balePickupStartDate) {
		this.balePickupStartDate = balePickupStartDate;
	}

	public Date getBalePickupEndDate() {
		return balePickupEndDate;
	}

	public void setBalePickupEndDate(Date balePickupEndDate) {
		this.balePickupEndDate = balePickupEndDate;
	}

	public String getCustomerSiteAlternativeSearchReference() {
		return customerSiteAlternativeSearchReference;
	}

	public void setCustomerSiteAlternativeSearchReference(
			String customerSiteAlternativeSearchReference) {
		this.customerSiteAlternativeSearchReference = customerSiteAlternativeSearchReference;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getAddress4() {
		return address4;
	}

	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	public String getAddress5() {
		return address5;
	}

	public void setAddress5(String address5) {
		this.address5 = address5;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getTelNo() {
		return telNo;
	}

	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	public String getSellCustomerAvailable() {
		return sellCustomerAvailable;
	}

	public void setSellCustomerAvailable(String sellCustomerAvailable) {
		this.sellCustomerAvailable = sellCustomerAvailable;
	}

	public String getSupplierAvailable() {
		return supplierAvailable;
	}

	public void setSupplierAvailable(String supplierAvailable) {
		this.supplierAvailable = supplierAvailable;
	}

	public String getMaterialAvailable() {
		return materialAvailable;
	}

	public void setMaterialAvailable(String materialAvailable) {
		this.materialAvailable = materialAvailable;
	}

	public String getCustomerAlternativeSearchReference() {
		return customerAlternativeSearchReference;
	}

	public void setCustomerAlternativeSearchReference(
			String customerAlternativeSearchReference) {
		this.customerAlternativeSearchReference = customerAlternativeSearchReference;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public Integer getFrequencyDay() {
		return frequencyDay;
	}

	public void setFrequencyDay(Integer frequencyDay) {
		this.frequencyDay = frequencyDay;
	}

	@Override
	public String toString() {
		return "StoreListView [customerSiteId=" + customerSiteId
				+ ", customerId=" + customerId + ", siteName=" + siteName
				+ ", locationId=" + locationId + ", balePickupStartDate="
				+ balePickupStartDate + ", balePickupEndDate="
				+ balePickupEndDate
				+ ", customerSiteAlternativeSearchReference="
				+ customerSiteAlternativeSearchReference + ", houseNumber="
				+ houseNumber + ", address1=" + address1 + ", address2="
				+ address2 + ", address3=" + address3 + ", address4="
				+ address4 + ", address5=" + address5 + ", postCode="
				+ postCode + ", telNo=" + telNo + ", sellCustomerAvailable="
				+ sellCustomerAvailable + ", supplierAvailable="
				+ supplierAvailable + ", materialAvailable="
				+ materialAvailable + ", customerAlternativeSearchReference="
				+ customerAlternativeSearchReference + ", customerName="
				+ customerName + "]";
	}

	public StoreListView(Integer customerSiteId, String siteName) {
		this.customerSiteId = customerSiteId;
		this.siteName = siteName;
	}

	public StoreListView() {

	}

}
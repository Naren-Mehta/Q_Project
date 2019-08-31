package com.wm.brta.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "getsupplierpickups")
public class SupplierPickupsSP {

	@Id
	@Column(name = "customersiteid")
	private Integer customerSiteId;

	@Column(name = "customerid")
	private Integer customerId;
	
	@Column(name = "sitename")
	private String siteName;

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
	
	@Column(name = "customername")
	private String customerName;
	
	@Column(name = "driver1id")
	private Integer driver1Id;

	@Column(name = "driver2id")
	private Integer driver2Id;
	
	
	@Column(name = "driver1_firstname")
	private String driver1FirstName;
	
	@Column(name = "driver1_middlename")
	private String driver1MiddleName;
	
	@Column(name = "driver_1_lastname")
	private String driver1LastName;
	
	@Column(name = "driver1_mobilephone")
	private String driver1MobilePhone;
	
	@Column(name = "driver1_email")
	private String driver1Email;
	
	@Column(name = "driver2_firstname")
	private String driver2FirstName;
	
	@Column(name = "driver2_middlename")
	private String driver2MiddleName;
	
	@Column(name = "driver2_lastname")
	private String driver2LastName;
	
	@Column(name = "driver2_mobilephone")
	private String driver2MobilePhone;
	
	@Column(name = "driver2_email")
	private String driver2Email;
	

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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getDriver1Id() {
		return driver1Id;
	}

	public void setDriver1Id(Integer driver1Id) {
		this.driver1Id = driver1Id;
	}

	public Integer getDriver2Id() {
		return driver2Id;
	}

	public void setDriver2Id(Integer driver2Id) {
		this.driver2Id = driver2Id;
	}

	public String getDriver1FirstName() {
		return driver1FirstName;
	}

	public void setDriver1FirstName(String driver1FirstName) {
		this.driver1FirstName = driver1FirstName;
	}

	public String getDriver1MiddleName() {
		return driver1MiddleName;
	}

	public void setDriver1MiddleName(String driver1MiddleName) {
		this.driver1MiddleName = driver1MiddleName;
	}

	public String getDriver1LastName() {
		return driver1LastName;
	}

	public void setDriver1LastName(String driver1LastName) {
		this.driver1LastName = driver1LastName;
	}

	public String getDriver1MobilePhone() {
		return driver1MobilePhone;
	}

	public void setDriver1MobilePhone(String driver1MobilePhone) {
		this.driver1MobilePhone = driver1MobilePhone;
	}

	public String getDriver1Email() {
		return driver1Email;
	}

	public void setDriver1Email(String driver1Email) {
		this.driver1Email = driver1Email;
	}

	public String getDriver2FirstName() {
		return driver2FirstName;
	}

	public void setDriver2FirstName(String driver2FirstName) {
		this.driver2FirstName = driver2FirstName;
	}

	public String getDriver2MiddleName() {
		return driver2MiddleName;
	}

	public void setDriver2MiddleName(String driver2MiddleName) {
		this.driver2MiddleName = driver2MiddleName;
	}

	public String getDriver2LastName() {
		return driver2LastName;
	}

	public void setDriver2LastName(String driver2LastName) {
		this.driver2LastName = driver2LastName;
	}

	public String getDriver2MobilePhone() {
		return driver2MobilePhone;
	}

	public void setDriver2MobilePhone(String driver2MobilePhone) {
		this.driver2MobilePhone = driver2MobilePhone;
	}

	public String getDriver2Email() {
		return driver2Email;
	}

	public void setDriver2Email(String driver2Email) {
		this.driver2Email = driver2Email;
	}

	
	
	
}
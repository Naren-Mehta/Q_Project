package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "driversupplierpickupsview")
public class DriverSupplierPickupsView {
	
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

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
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

	public String getDriver1_FirstName() {
		return driver1_FirstName;
	}

	public void setDriver1_FirstName(String driver1_FirstName) {
		this.driver1_FirstName = driver1_FirstName;
	}

	public String getDriver1_MiddleName() {
		return driver1_MiddleName;
	}

	public void setDriver1_MiddleName(String driver1_MiddleName) {
		this.driver1_MiddleName = driver1_MiddleName;
	}

	public String getDriver_1_LastName() {
		return driver_1_LastName;
	}

	public void setDriver_1_LastName(String driver_1_LastName) {
		this.driver_1_LastName = driver_1_LastName;
	}

	public String getDriver1_Email() {
		return driver1_Email;
	}

	public void setDriver1_Email(String driver1_Email) {
		this.driver1_Email = driver1_Email;
	}

	public String getDriver1_MobilePhone() {
		return driver1_MobilePhone;
	}

	public void setDriver1_MobilePhone(String driver1_MobilePhone) {
		this.driver1_MobilePhone = driver1_MobilePhone;
	}

	public String getDriver2_FirstName() {
		return driver2_FirstName;
	}

	public void setDriver2_FirstName(String driver2_FirstName) {
		this.driver2_FirstName = driver2_FirstName;
	}

	public String getDriver2_MiddleName() {
		return driver2_MiddleName;
	}

	public void setDriver2_MiddleName(String driver2_MiddleName) {
		this.driver2_MiddleName = driver2_MiddleName;
	}

	public String getDriver2_LastName() {
		return driver2_LastName;
	}

	public void setDriver2_LastName(String driver2_LastName) {
		this.driver2_LastName = driver2_LastName;
	}

	public String getDriver2_Email() {
		return driver2_Email;
	}

	public void setDriver2_Email(String driver2_Email) {
		this.driver2_Email = driver2_Email;
	}

	public String getDriver2_MobilePhone() {
		return driver2_MobilePhone;
	}

	public void setDriver2_MobilePhone(String driver2_MobilePhone) {
		this.driver2_MobilePhone = driver2_MobilePhone;
	}

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
	
	@Column(name = "supplierid")
	private Integer supplierId;
	
	@Column(name = "driver1id")
	private Integer driver1Id;
	
	@Column(name = "driver2id")
	private Integer driver2Id;
	
	@Column(name = "driver1_firstname")
	private String driver1_FirstName;
	
	@Column(name = "driver1_middlename")
	private String driver1_MiddleName;
	
	@Column(name = "driver_1_lastname")
	private String driver_1_LastName;
	
	@Column(name = "driver1_email")
	private String driver1_Email;
	
	@Column(name = "driver1_mobilephone")
	private String driver1_MobilePhone;
	

	@Column(name = "driver2_firstname")
	private String driver2_FirstName;
	
	@Column(name = "driver2_middlename")
	private String driver2_MiddleName;
	
	@Column(name = "driver2_lastname")
	private String driver2_LastName;
	
	@Column(name = "driver2_email")
	private String driver2_Email;
	
	@Column(name = "driver2_mobilephone")
	private String driver2_MobilePhone;
	
	@Column(name = "startdate")
	private Date startDate;
	
	@Column(name = "enddate")
	private Date endDate;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public DriverSupplierPickupsView(Integer driver1Id, Integer driver2Id) {
		super();
		this.driver1Id = driver1Id;
		this.driver2Id = driver2Id;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public DriverSupplierPickupsView(){
		
	}
	
	

}

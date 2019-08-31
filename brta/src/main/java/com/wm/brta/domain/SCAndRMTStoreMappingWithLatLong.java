package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "[scandrmtstoremappingwithlatlong]")
public class SCAndRMTStoreMappingWithLatLong {

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer Id;
	
	
	@Column(name = "scstoreid", nullable = false)
	private Integer scStoreId;
	
	@Column(name = "storename", nullable = false)
	private String storeName;
	
	@Column(name = "balecustomerid", nullable = false)
	private Integer baleCustomerId;
	
	@Column(name = "shortname", nullable = false)
	private String shortName;
	
	@Column(name = "fullname", nullable = false)
	private String fullName;
	
	@Column(name = "longitude", nullable = false)
	private String longitude;
	
	@Column(name = "latitude", nullable = false)
	private String latitude;
	
	@Column(name = "field1", nullable = false)
	private String field1;
	
	@Column(name = "field2", nullable = false)
	private Integer field2;
	
	@Column(name = "createddate", nullable = false)
	private Date createdDate;
	
	@Column(name = "updateddate", nullable = false)
	private Date updatedDate;
	
	@Column(name = "updatedby", nullable = false)
	private Date updatedBy;

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getScStoreId() {
		return scStoreId;
	}

	public void setScStoreId(Integer scStoreId) {
		this.scStoreId = scStoreId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Integer getBaleCustomerId() {
		return baleCustomerId;
	}

	public void setBaleCustomerId(Integer baleCustomerId) {
		this.baleCustomerId = baleCustomerId;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public Integer getField2() {
		return field2;
	}

	public void setField2(Integer field2) {
		this.field2 = field2;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Date getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Date updatedBy) {
		this.updatedBy = updatedBy;
	}

	public SCAndRMTStoreMappingWithLatLong(Integer scStoreId, String longitude, String latitude)
	{
		super();
		this.scStoreId=scStoreId;
		this.longitude=longitude;
		this.latitude= latitude;
		
	}
	public SCAndRMTStoreMappingWithLatLong() {
		super();
	}

		
	@Override
	public String toString() {
		return "SCAndRMTStoreMappingWithLatLong [Id=" + Id + ", scStoreId="
				+ scStoreId + ", storeName=" + storeName + ", baleCustomerId="
				+ baleCustomerId + ", shortName=" + shortName + ", fullName="
				+ fullName + ", longitude=" + longitude + ", latitude="
				+ latitude + ", field1=" + field1 + ", field2=" + field2
				+ ", createdDate=" + createdDate + ", updatedDate="
				+ updatedDate + ", updatedBy=" + updatedBy + "]";
	}

	
}
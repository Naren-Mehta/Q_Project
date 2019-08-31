package com.wm.brta.dto;


/**
 * @author nmehta
 *
 */
public class SupplierPickupsMobileDTO {
	
	private Integer userId;
	private String date;
	private String storeSearchString;
	private String vehiclePlateNo;
	
	private Boolean only10RecordFlag=false;
	private Integer driverPickupMax=10;
	private Integer driverPickupOffset;
	private Integer supplierId;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getStoreSearchString() {
		return storeSearchString;
	}
	public void setStoreSearchString(String storeSearchString) {
		this.storeSearchString = storeSearchString;
	}
	
	
	
	public String getVehiclePlateNo() {
		return vehiclePlateNo;
	}
	public void setVehiclePlateNo(String vehiclePlateNo) {
		this.vehiclePlateNo = vehiclePlateNo;
	}
	
	
	public Boolean getOnly10RecordFlag() {
		return only10RecordFlag;
	}
	public void setOnly10RecordFlag(Boolean only10RecordFlag) {
		this.only10RecordFlag = only10RecordFlag;
	}
	public Integer getDriverPickupMax() {
		return driverPickupMax;
	}
	public void setDriverPickupMax(Integer driverPickupMax) {
		this.driverPickupMax = driverPickupMax;
	}
	public Integer getDriverPickupOffset() {
		return driverPickupOffset;
	}
	public void setDriverPickupOffset(Integer driverPickupOffset) {
		this.driverPickupOffset = driverPickupOffset;
	}
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	@Override
	public String toString() {
		return "SupplierPickupsMobileDTO [userId=" + userId + ", date=" + date
				+ ", storeSearchString=" + storeSearchString
				+ ", vehiclePlateNo=" + vehiclePlateNo + ", only10RecordFlag="
				+ only10RecordFlag + ", driverPickupMax=" + driverPickupMax
				+ ", driverPickupOffset=" + driverPickupOffset
				+ ", supplierId=" + supplierId + "]";
	}
	
	
	
	
	
		
}

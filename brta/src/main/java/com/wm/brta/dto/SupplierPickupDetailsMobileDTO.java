package com.wm.brta.dto;


/**
 * @author nmehta
 *
 */
public class SupplierPickupDetailsMobileDTO {
	
	private Integer userId;
	private String date;
	private String storeId;
	private String vehiclePlateNo;
	private String storeName;
	
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
	public String getStoreId() {
		return storeId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	
	
	public String getVehiclePlateNo() {
		return vehiclePlateNo;
	}
	public void setVehiclePlateNo(String vehiclePlateNo) {
		this.vehiclePlateNo = vehiclePlateNo;
	}
	@Override
	public String toString() {
		return "SupplierPickupDetailsMobileDTO [userId=" + userId + ", date="
				+ date + ", storeId=" + storeId + ", vehiclePlateNo="
				+ vehiclePlateNo + ", storeName=" + storeName + "]";
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	
	
	
	
		
}

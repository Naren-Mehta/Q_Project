package com.wm.brta.dto;

import java.util.List;

public class CompleteTripInputPayLoad 
{
	
	private Integer userId;
	private String date;
	private String deliveryDate;
	private List<Integer> storeIdList;
	private Integer destinationId;
	private String releaseNo;
	private Integer grossWeight;
	private Integer tareWeight;
	
	private Integer tripId;
	
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
	public List<Integer> getStoreIdList() {
		return storeIdList;
	}
	public void setStoreIdList(List<Integer> storeIdList) {
		this.storeIdList = storeIdList;
	}
	public Integer getDestinationId() {
		return destinationId;
	}
	public void setDestinationId(Integer destinationId) {
		this.destinationId = destinationId;
	}
	//changed to sting releaseno by deepak
	public String getReleaseNo() {
		return releaseNo;
	}
	public void setReleaseNo(String releaseNo) {
		this.releaseNo = releaseNo;
	}
	public Integer getGrossWeight() {
		return grossWeight;
	}
	public void setGrossWeight(Integer grossWeight) {
		this.grossWeight = grossWeight;
	}
	public Integer getTareWeight() {
		return tareWeight;
	}
	public void setTareWeight(Integer tareWeight) {
		this.tareWeight = tareWeight;
	}
	

	public Integer getTripId() {
		return tripId;
	}
	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}
	
	public String getDeliveryDate() {
		return deliveryDate;
	}
	
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	@Override
	public String toString() {
		return "CompleteTripInputPayLoad [userId=" + userId + ", date=" + date
				+ ", storeIdList=" + storeIdList + ", destinationId="
				+ destinationId + ", releaseNo=" + releaseNo + ", grossWeight="
				+ grossWeight + ", tareWeight=" + tareWeight + "]";
	}
	
	
}

package com.wm.brta.dto;

public class PendingStoreReportDTO {
	
	
	private Integer buyCustomerId;
	private Integer supplierId;
	
	
	public Integer getBuyCustomerId() {
		return buyCustomerId;
	}
	public void setBuyCustomerId(Integer buyCustomerId) {
		this.buyCustomerId = buyCustomerId;
	}
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	@Override
	public String toString() {
		return "PendingStoreReportDTO [buyCustomerId=" + buyCustomerId
				+ ", supplierId=" + supplierId + "]";
	}
	
	

	


}

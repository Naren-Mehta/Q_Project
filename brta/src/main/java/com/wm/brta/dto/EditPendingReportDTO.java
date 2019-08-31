package com.wm.brta.dto;

public class EditPendingReportDTO {
	
	
	private Integer buyCustomerSiteId;
	private Integer supplierId;
	
	public Integer getBuyCustomerSiteId() {
		return buyCustomerSiteId;
	}
	public void setBuyCustomerSiteId(Integer buyCustomerSiteId) {
		this.buyCustomerSiteId = buyCustomerSiteId;
	}
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	@Override
	public String toString() {
		return "EditPendingReportDTO [buyCustomerSiteId=" + buyCustomerSiteId
				+ ", supplierId=" + supplierId + "]";
	}
	


}

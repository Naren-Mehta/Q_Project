package com.wm.brta.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class CustomerSitesDTO {
	private Integer customerSiteId;
	private String siteName;
	
	
	public Integer getCustomerSiteId() {
		return customerSiteId;
	}
	public void setCustomerSiteId(Integer customerSiteId) {
		this.customerSiteId = customerSiteId;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	@Override
	public String toString() {
		return "CustomerSitesDTO [customerSiteId=" + customerSiteId
				+ ", siteName=" + siteName + "]";
	}
	
	public CustomerSitesDTO(Integer customerSiteId, String siteName) {
		super();
		this.customerSiteId = customerSiteId;
		this.siteName = siteName;
	}
	
	
	public CustomerSitesDTO() {
		super();
		
	}

}

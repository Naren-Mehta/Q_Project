package com.wm.brta.dto;

import java.sql.Date;
import java.util.List;

import com.wm.brta.domain.Customer;

public class StoreInputDTO {

	private Customer buyCustomer;
	private String state;
	private Date startDate;
	private Date endDate;
	private List<Integer> customerSiteIdList;

	public Customer getBuyCustomer() {
		return buyCustomer;
	}
	public void setBuyCustomer(Customer buyCustomer) {
		this.buyCustomer = buyCustomer;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	public List<Integer> getCustomerSiteIdList() {
		return customerSiteIdList;
	}
	public void setCustomerSiteIdList(List<Integer> customerSiteIdList) {
		this.customerSiteIdList = customerSiteIdList;
	}
	@Override
	public String toString() {
		return "StoreInputDTO [buyCustomer=" + buyCustomer + ", state=" + state
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", customerSiteIdList=" + customerSiteIdList + "]";
	}
	
	
	
	
	
	
}

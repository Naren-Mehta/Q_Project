package com.wm.brta.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.wm.brta.domain.PendingReport;
import com.wm.brta.util.XSSUtils;

@JsonInclude(Include.NON_NULL)
public class PendingReportDTO {
	private Integer customerSiteId;
	private Integer customerId;
	private String customerName;
	private String siteName;
	
	private Integer frequencyId;
	
	private String frequency;
	
	private String frequencyDay;
	
	private String supplierName;
	
	private Date lastPickupDate;
	
	
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public Integer getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(Integer frequencyId) {
		this.frequencyId = frequencyId;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getFrequencyDay() {
		return frequencyDay;
	}

	public void setFrequencyDay(String frequencyDay) {
		this.frequencyDay = frequencyDay;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	
	
	

	public Date getLastPickupDate() {
		return lastPickupDate;
	}

	public void setLastPickupDate(Date lastPickupDate) {
		this.lastPickupDate = lastPickupDate;
	}

	@Override
	public String toString() {
		return "PendingReport [customerSiteId=" + customerSiteId
				+ ", customerId=" + customerId + ", customerName="
				+ customerName + ", siteName=" + siteName + ", frequencyId="
				+ frequencyId + ", frequency=" + frequency + ", frequencyDay="
				+ frequencyDay + ", supplierName=" + supplierName + "]";
	}

	public PendingReportDTO(PendingReport pendingReport) {
		super();
		this.customerSiteId = pendingReport.getCustomerSiteId();
		this.customerId = Integer.parseInt(XSSUtils.stripXSS(pendingReport.getCustomerId().toString()));
		this.customerName = XSSUtils.stripXSS(pendingReport.getCustomerName());
		this.siteName = pendingReport.getSiteName();
		this.frequencyId = pendingReport.getFrequencyId();
		this.frequency = pendingReport.getFrequency();
		this.frequencyDay = pendingReport.getFrequencyDay();
		this.supplierName = pendingReport.getSupplierName();
		this.lastPickupDate = pendingReport.getLastPickupDate();
	}
	
	
	
	
	
	
}
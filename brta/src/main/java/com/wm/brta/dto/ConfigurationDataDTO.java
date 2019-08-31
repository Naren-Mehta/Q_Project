package com.wm.brta.dto;

import java.sql.Date;
import java.util.List;

import com.wm.brta.domain.BalePickupCustomerSiteConfiguration;
import com.wm.brta.domain.BalePickupMaterialConfiguration;
import com.wm.brta.domain.BalePickupSupplierConfiguration;
import com.wm.brta.domain.Frequency;

public class ConfigurationDataDTO {
	
	List<BalePickupSupplierConfiguration> supplierConfig;
	List<BalePickupMaterialConfiguration> materialConfig;
	List<BalePickupCustomerSiteConfiguration> customerSiteConfig;
	
	List<Integer> customerSiteIdList;
	Date balePickupStartDate;
	Date balePickupEndDate;
	
	Frequency frequency;
	Integer frequencyDay;

	boolean editAction;
	
	public List<BalePickupSupplierConfiguration> getSupplierConfig() {
		return supplierConfig;
	}
	public void setSupplierConfig(
			List<BalePickupSupplierConfiguration> supplierConfig) {
		this.supplierConfig = supplierConfig;
	}
	public List<BalePickupMaterialConfiguration> getMaterialConfig() {
		return materialConfig;
	}
	public void setMaterialConfig(
			List<BalePickupMaterialConfiguration> materialConfig) {
		this.materialConfig = materialConfig;
	}
	public List<BalePickupCustomerSiteConfiguration> getCustomerSiteConfig() {
		return customerSiteConfig;
	}
	public void setCustomerSiteConfig(
			List<BalePickupCustomerSiteConfiguration> customerSiteConfig) {
		this.customerSiteConfig = customerSiteConfig;
	}
	public boolean isEditAction() {
		return editAction;
	}
	public void setEditAction(boolean editAction) {
		this.editAction = editAction;
	}
	
	
	
	
	public List<Integer> getCustomerSiteIdList() {
		return customerSiteIdList;
	}
	public void setCustomerSiteIdList(List<Integer> customerSiteIdList) {
		this.customerSiteIdList = customerSiteIdList;
	}
	public Date getBalePickupStartDate() {
		return balePickupStartDate;
	}
	public void setBalePickupStartDate(Date balePickupStartDate) {
		this.balePickupStartDate = balePickupStartDate;
	}
	public Date getBalePickupEndDate() {
		return balePickupEndDate;
	}
	public void setBalePickupEndDate(Date balePickupEndDate) {
		this.balePickupEndDate = balePickupEndDate;
	}
	
	
	
	public Frequency getFrequency() {
		return frequency;
	}
	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}
	public Integer getFrequencyDay() {
		return frequencyDay;
	}
	public void setFrequencyDay(Integer frequencyDay) {
		this.frequencyDay = frequencyDay;
	}
	@Override
	public String toString() {
		return "ConfigurationDataDTO [supplierConfig=" + supplierConfig
				+ ", materialConfig=" + materialConfig
				+ ", customerSiteConfig=" + customerSiteConfig
				+ ", customerSiteIdList=" + customerSiteIdList
				+ ", balePickupStartDate=" + balePickupStartDate
				+ ", balePickupEndDate=" + balePickupEndDate + ", editAction="
				+ editAction + "]";
	}
	
	
	
	
	
	
	

}

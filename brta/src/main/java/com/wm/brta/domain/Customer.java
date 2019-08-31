package com.wm.brta.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "[customer]")
public class Customer {

	private Integer customerId;
	private String customerName;
	private String legacyVendorId;
	private Integer customerStateId;
	private Date createdDate;
	private Date updatedAt;
	private String updatedBy;
	private List<BaleRouteCustomerMapping> baleRouteMappingsForBuyCustomers;
	private List<BaleRouteCustomerMapping> baleRouteMappingsForSellCustomers;
	private List<CustomerSite> customerSites;
	private List<BaleRouteCustomerMaterialMapping> baleRouteCustomerMaterialMappings;
	private List<BaleRouteCustomerSupplierMapping> baleRouteCustomerSupplierMappings;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
	@JsonBackReference(value = "customerSites")
	public List<CustomerSite> getCustomerSites() {
		return customerSites;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName="
				+ customerName + ", legacyVendorId=" + legacyVendorId
				+ ", customerStateId=" + customerStateId + ", createdDate="
				+ createdDate + ", updatedAt=" + updatedAt + ", updatedBy="
				+ updatedBy + ", baleRouteMappingsForBuyCustomers="
				+ baleRouteMappingsForBuyCustomers
				+ ", baleRouteMappingsForSellCustomers="
				+ baleRouteMappingsForSellCustomers + ", customerSites="
				+ customerSites + ", baleRouteCustomerMaterialMappings="
				+ baleRouteCustomerMaterialMappings
				+ ", baleRouteCustomerSupplierMappings="
				+ baleRouteCustomerSupplierMappings + "]";
	}

	public void setCustomerSites(List<CustomerSite> customerSites) {
		this.customerSites = customerSites;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "sellCustomer")
	@JsonBackReference(value = "baleroutemappingsforsellcustomer")
	public List<BaleRouteCustomerMapping> getBaleRouteMappingsForSellCustomers() {
		return baleRouteMappingsForSellCustomers;
	}

	public void setBaleRouteMappingsForSellCustomers(
			List<BaleRouteCustomerMapping> baleRouteMappingsForSellCustomers) {
		this.baleRouteMappingsForSellCustomers = baleRouteMappingsForSellCustomers;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customerid", nullable = false)
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Column(name = "customername", nullable = false)
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "legacyvendorid", nullable = true)
	public String getLegacyVendorId() {
		return legacyVendorId;
	}

	public void setLegacyVendorId(String legacyVendorId) {
		this.legacyVendorId = legacyVendorId;
	}

	@Column(name = "customerstateid", nullable = false)
	public Integer getCustomerStateId() {
		return customerStateId;
	}

	public void setCustomerStateId(Integer customerStateId) {
		this.customerStateId = customerStateId;
	}

	@Column(name = "createdate", nullable = false)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "updatedat", nullable = false)
	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Column(name = "updatedby", nullable = false)
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "buyCustomer")
	@JsonBackReference(value = "baleroutemappingsforbuycustomer")
	public List<BaleRouteCustomerMapping> getBaleRouteMappingsForBuyCustomers() {
		return baleRouteMappingsForBuyCustomers;
	}

	public void setBaleRouteMappingsForBuyCustomers(
			List<BaleRouteCustomerMapping> baleRouteMappingsForBuyCustomers) {
		this.baleRouteMappingsForBuyCustomers = baleRouteMappingsForBuyCustomers;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "buyCustomer")
	@JsonBackReference(value = "baleroutematerialmappingsforbuycustomer")
	public List<BaleRouteCustomerMaterialMapping> getBaleRouteCustomerMaterialMappings() {
		return baleRouteCustomerMaterialMappings;
	}

	public void setBaleRouteCustomerMaterialMappings(
			List<BaleRouteCustomerMaterialMapping> baleRouteCustomerMaterialMappings) {
		this.baleRouteCustomerMaterialMappings = baleRouteCustomerMaterialMappings;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "buyCustomer")
	@JsonBackReference(value = "baleroutesuppliermappingsforbuycustomer")
	public List<BaleRouteCustomerSupplierMapping> getBaleRouteCustomerSupplierMappings() {
		return baleRouteCustomerSupplierMappings;
	}

	public void setBaleRouteCustomerSupplierMappings(
			List<BaleRouteCustomerSupplierMapping> baleRouteCustomerSupplierMappings) {
		this.baleRouteCustomerSupplierMappings = baleRouteCustomerSupplierMappings;
	}

	public Customer() {
	}

	public Customer(Integer customerId, String customerName,
			String legacyVendorId, Integer customerStateId, Date createdDate,
			Date updatedAt, String updatedBy) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
		this.legacyVendorId = legacyVendorId;
		this.customerStateId = customerStateId;
		this.createdDate = createdDate;
		this.updatedAt = updatedAt;
		this.updatedBy = updatedBy;
	}

	public Customer(Integer customerId, String customerName) {
		super();
		this.customerId = customerId;
		this.customerName = customerName;
	}
	
	public Customer(Integer customerId) {
		super();
		this.customerId = customerId;
		
	}

}

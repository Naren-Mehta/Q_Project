package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="[baleroutecustomersuppliermapping]")
public class BaleRouteCustomerSupplierMapping {
	
	private Customer buyCustomer;
	private Supplier supplierId;
	private Integer baleRouteCustomerSupplierMappingId;
	private Date createdDate;
	private Date updatedAt;
	private String updatedBy;
	
	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "buycustomerid",  nullable = false)
	public Customer getBuyCustomer() {
		return buyCustomer;
	}
	public void setBuyCustomer(Customer buyCustomer) {
		this.buyCustomer = buyCustomer;
	}
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="supplierid",nullable=false)
	public Supplier getSupplier() {
		return supplierId;
	}
	public void setSupplier(Supplier supplier) {
		this.supplierId = supplier;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "baleroutecustomersuppliermappingid", unique = true, nullable = false)
	public Integer getBaleRouteCustomerSupplierMappingId() {
		return baleRouteCustomerSupplierMappingId;
	}
	public void setBaleRouteCustomerSupplierMappingId(
			Integer baleRouteCustomerSupplierMappingId) {
		this.baleRouteCustomerSupplierMappingId = baleRouteCustomerSupplierMappingId;
	}
	
	@Column(name = "createdate", unique = true, nullable = false)
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name = "updatedat", unique = true, nullable = false)
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Column(name = "updatedby", unique = true, nullable = false)
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}

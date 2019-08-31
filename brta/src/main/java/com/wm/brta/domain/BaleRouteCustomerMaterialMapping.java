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
import javax.persistence.Table;

@Entity
@Table(name="[baleroutecustomermaterialmapping]")
public class BaleRouteCustomerMaterialMapping 
{
	private Integer baleRouteCustomerMaterialMappingId;
	private Customer buyCustomer;
	private Material material;
	private Date createDate;
	private Date updatedAt;
	private String updatedBy;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="baleroutecustomermaterialmappingid",unique=true,nullable=false)
	public Integer getBaleRouteCustomerMaterialMappingId() {
		return baleRouteCustomerMaterialMappingId;
	}
	
	public void setBaleRouteCustomerMaterialMappingId(
			Integer baleRouteCustomerMaterialMappingId) {
		this.baleRouteCustomerMaterialMappingId = baleRouteCustomerMaterialMappingId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="buycustomerid",nullable=false)
	public Customer getBuyCustomer() {
		return buyCustomer;
	}
	public void setBuyCustomer(Customer buyCustomer) {
		this.buyCustomer = buyCustomer;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="materialid",nullable=false)
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
	}
	@Column(name="createdate",nullable=false)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name="updatedat",nullable=false)
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Column(name="updatedby",nullable=false)
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	@Override
	public String toString() {
		return "BaleRouteCustomerMaterialMapping [baleRouteCustomerMaterialMappingId="
				+ baleRouteCustomerMaterialMappingId
				+ ", buyCustomer="
				+ buyCustomer
				+ ", material="
				+ material
				+ ", createDate="
				+ createDate
				+ ", updatedAt="
				+ updatedAt
				+ ", updatedBy="
				+ updatedBy + "]";
	}	

}

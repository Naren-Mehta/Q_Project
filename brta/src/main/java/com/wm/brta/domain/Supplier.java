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
@Table(name = "[supplier]")
public class Supplier {

	private Integer supplierId;
	private String description;
	private Date createdDate;
	private Date updatedDate;
	private String updatedBy;
	private List<User> users;
	private List<SupplierSite> supplierSites;

	@Column(name = "isdeleted")
	private Boolean isdeleted;

	
	private List<BaleRouteCustomerMapping> baleRouteMappingsForSupplier;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "supplier")
	@JsonBackReference(value = "baleroutemappingsforsupplier")
	public List<BaleRouteCustomerMapping> getBaleRouteMappingsForSupplier() {
		return baleRouteMappingsForSupplier;
	}

	public void setBaleRouteMappingsForSupplier(
			List<BaleRouteCustomerMapping> baleRouteMappingsForSupplier) {
		this.baleRouteMappingsForSupplier = baleRouteMappingsForSupplier;
	}
	
	public Supplier() {
	}

	public Supplier(Integer supplierId, String description) {
		super();
		this.supplierId = supplierId;
		this.description = description;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "supplierid", unique = true, nullable = false)
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	@Column(name = "description", nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "createdate", nullable = false)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "updatedat", nullable = false)
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Column(name = "updatedby", nullable = false)
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "supplier")
	@JsonBackReference(value = "users")
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	@Override
	public String toString() {	
		return "Supplier [supplierId=" + supplierId + ", description="
				+ description + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + ", updatedBy=" + updatedBy
				+ ", users=" + users + "]";
	}

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "supplier")
	@JsonBackReference(value = "suppliersites")
	public List<SupplierSite> getSupplierSites() {
		return supplierSites;
	}

	public void setSupplierSites(List<SupplierSite> supplierSites) {
		this.supplierSites = supplierSites;
	}

	public Boolean getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	

	

}

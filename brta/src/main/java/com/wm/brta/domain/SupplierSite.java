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
@Table(name = "[suppliersite]")
public class SupplierSite {

	private Integer supplierSiteId;
	private String siteName;
	private Location location;
	private Supplier supplier;
	private Date createDate;
	private Date updatedAt;
	private String updatedBy;

	@Column(name = "isdeleted")
	private Boolean isdeleted;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "suppliersiteid", unique = true, nullable = false)
	public Integer getSupplierSiteId() {
		return supplierSiteId;
	}

	public void setSupplierSiteId(Integer supplierSiteId) {
		this.supplierSiteId = supplierSiteId;
	}

	@Column(name = "sitename", nullable = false)
	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "locationid", nullable = false)
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supplierid", nullable = false)
	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@Column(name = "createdate", nullable = false)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	@Override
	public String toString() {
		return "SupplierSite [supplierSiteId=" + supplierSiteId + ", siteName="
				+ siteName + ", location=" + location + ", supplier="
				+ supplier + ", createDate=" + createDate + ", updatedAt="
				+ updatedAt + ", updatedBy=" + updatedBy + ", isdeleted="
				+ isdeleted + "]";
	}

	public Boolean getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	

}

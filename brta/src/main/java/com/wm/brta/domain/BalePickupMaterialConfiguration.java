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
@Table(name = "[balepickupmaterialconfiguration]")
public class BalePickupMaterialConfiguration {

	private Integer balePickupMaterialConfigurationId;
	private CustomerSite customerSite;
	private Material material;
	private Date createDate;
	private Date updatedAt;
	private Boolean enabled;
	private String updatedBy;
	private Integer avgBaleWeight;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "balepickupmaterialconfigurationid", unique = true, nullable = false)
	public Integer getBalePickupMaterialConfigurationId() {
		return balePickupMaterialConfigurationId;
	}

	public void setBalePickupMaterialConfigurationId(
			Integer balePickupMaterialConfigurationId) {
		this.balePickupMaterialConfigurationId = balePickupMaterialConfigurationId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "buycustomersiteid", nullable = false)
	public CustomerSite getCustomerSite() {
		return customerSite;
	}

	public void setCustomerSite(CustomerSite buyCustomerSite) {
		this.customerSite = buyCustomerSite;
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

	@Column(name = "enabled", nullable = false)
	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
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
		return "BalePickupMaterialConfiguration [balePickupMaterialConfigurationId="
				+ balePickupMaterialConfigurationId
				+ ", customerSite="
				+ customerSite
				+ ", material="
				+ material
				+ ", createDate="
				+ createDate
				+ ", updatedAt="
				+ updatedAt
				+ ", enabled="
				+ enabled + ", updatedBy=" + updatedBy + "]";
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "materialid", nullable = false)
	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	@Column(name = "avgbaleweight")
	public Integer getAvgBaleWeight() {
		return avgBaleWeight;
	}

	public void setAvgBaleWeight(Integer avgBaleWeight) {
		this.avgBaleWeight = avgBaleWeight;
	}

	public BalePickupMaterialConfiguration(CustomerSite customerSite,
			Material material, Date createDate, Date updatedAt,
			Boolean enabled, String updatedBy, Integer avgBaleWeight) {
		super();
		this.customerSite = customerSite;
		this.material = material;
		this.createDate = createDate;
		this.updatedAt = updatedAt;
		this.enabled = enabled;
		this.updatedBy = updatedBy;
		this.avgBaleWeight = avgBaleWeight;
	}

	public BalePickupMaterialConfiguration() {
		super();
	}

	public BalePickupMaterialConfiguration(
			Material material,Integer avgBaleWeight) {
		super();
		this.material = material;
		this.avgBaleWeight = avgBaleWeight;
	}
	
	

}

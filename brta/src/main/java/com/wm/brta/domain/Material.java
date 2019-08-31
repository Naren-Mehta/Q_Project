package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "[material]")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Material {
	public Material(){
		
	}

	private Integer materialId;
	private String description;
	private String shortName;
	private Date createDate;
	private Date updatedAt;
	private String updatedBy;

	@Column(name = "isdeleted")
	private Boolean isdeleted;

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "materialid", unique = true, nullable = false)
	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	@Column(name = "description", nullable = false)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "shortname")
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
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


	public Boolean getIsdeleted() {
		return isdeleted;
	}

	public void setIsdeleted(Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}

	public Material(Integer materialId, String description) {
		super();
		this.materialId = materialId;
		this.description = description;
	}
	@Override
	public String toString() {
		return "Material [materialId=" + materialId + ", description="
				+ description + ", shortName=" + shortName + ", createDate="
				+ createDate + ", updatedAt=" + updatedAt + ", updatedBy="
				+ updatedBy + "]";
	}

}

package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="[incidenttype]")
public class IncidentType {
	
	private Integer    incidentTypeId;
	private String  incidentDescription;
	private Date    createdDate;
	private Boolean enabled;
	private Date    updatedAt;
	private String  updatedBy;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="incidenttypeid",nullable=false,unique=true)
	public Integer getIncidentTypeId() {
		return incidentTypeId;
	}
	public void setIncidentTypeId(Integer incendentTypeId) {
		this.incidentTypeId = incendentTypeId;
	}
	@Column(name="incidentdescription",nullable=false,unique=true)
	public String getIncidentDescription() {
		return incidentDescription;
	}
	public void setIncidentDescription(String incidentDescription) {
		this.incidentDescription = incidentDescription;
	}
	@Column(name="createdate",nullable=false,unique=true)
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name="enabled",nullable=false,unique=true)
	public Boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	@Column(name="updatedat",nullable=false,unique=true)
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	@Column(name="updatedby",nullable=false,unique=true)
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	@Override
	public String toString() {
		return "IncidentType [incidentTypeId=" + incidentTypeId
				+ ", incidentDescription=" + incidentDescription
				+ ", createdDate=" + createdDate + ", enabled=" + enabled
				+ ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + "]";
	}
	
	
	
}

package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="[frequency]")
public class Frequency {
	
	@Id 
	@Column(name = "frequencyid")
	private Integer frequencyId;
	
	@Column(name = "description")
	private String description;

	@Column(name = "createdate")
	private Date createDate;

	@Column(name = "updatedat")
	private Date updatedAt;

	@Column(name = "updatedby")
	private String updatedBy;

	@Column(name = "field1")
	private String field1;

	@Column(name = "field2")
	private String field2;

	@Column(name = "enabled")
	private Boolean enabled;
	

	public Integer getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(Integer frequencyId) {
		this.frequencyId = frequencyId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public String getField2() {
		return field2;
	}

	public void setField2(String field2) {
		this.field2 = field2;
	}

	public Boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "Frequency [frequencyId=" + frequencyId + ", description="
				+ description + ", createDate=" + createDate + ", updatedAt="
				+ updatedAt + ", updatedBy=" + updatedBy + ", field1=" + field1
				+ ", field2=" + field2 + ", enabled=" + enabled + "]";
	}
}

package com.wm.brta.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="role")
public class Role {
	
	@Id
	@Column(name="roleid", unique=true,nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roleId;
	
	@Column(name="roledesc", nullable=false)
	private String roleDescription;
	
	@Column(name="enabled",nullable=false)
	private Boolean isEnabled;
	
	@Column(name="createdate",nullable=false)
	private Date  createDate;
	
	@Column(name="updatedby")
	private String updatedBy;
	
	@Column(name="updatedat")
	private Date updatedDate;
	
	
	@Transient
	@ManyToOne(fetch = FetchType.EAGER)
	private List<UserAccount> userAccount;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleDescription() {
		return roleDescription;
	}

	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}

	public Boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleDescription="
				+ roleDescription + ", isEnabled=" + isEnabled
				+ ", createDate=" + createDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", userAccount="
				+ userAccount + "]";
	}
	
	
	
	
}
package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "[servicechannelcustomers]")
public class ServiceChannelCustomers {


	private Integer id;
	private Long suscriberId;
	private Integer customerId;
	private Date createdDate;
	private Date updatedDate;
	private String updatedBy;
	
	private boolean enabled;
	
	
	
	@Id
	@Column(name = "id", nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
	@Column(name = "customerid", nullable = false)
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	
	

	@Column(name = "createddate", nullable = true)
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "updateddate", nullable = true)
	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Column(name = "updatedby", nullable = true)
	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "suscriberid", nullable = false)
	public Long getSuscriberId() {
		return suscriberId;
	}

	public void setSuscriberId(Long suscriberId) {
		this.suscriberId = suscriberId;
	}

	@Column(name = "enabled", nullable = false)	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}	
	
	

	public ServiceChannelCustomers(Integer id, Long suscriberId, Integer customerId, Boolean enabled){
		super();
		this.id=id;
		this.suscriberId = suscriberId;
		this.customerId = customerId;
		this.enabled = enabled;
	}
	
	
	public ServiceChannelCustomers() {
		super();
		
	}
	
}

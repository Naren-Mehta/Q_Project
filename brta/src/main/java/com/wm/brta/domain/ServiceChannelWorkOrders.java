package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "[servicechannelworkorders]")
public class ServiceChannelWorkOrders {  

	public ServiceChannelWorkOrders() {}
	
	
	@Column(name = "createddate", nullable = true)
	private Date createdDate=new Date();
	
	@Column(name = "updateddate", nullable = true)
	private Date updatedDate=new Date();
	
	@Column(name = "updatedby", nullable = true)  
	private String updatedBy;
	@Column(name = "requesttrackingid", nullable = true)  
	private String requestTrackingId;
	
	@Column(name = "store", nullable = false)  
	private String store;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, unique = true)
	private Integer Id;
	public Integer getId() {
		return Id;
	}
	public void setId(Integer id) {
		Id = id;
	}
	
	
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}


	@Column(name = "workordernumber", nullable = false)
	private Integer workOrderNumber;
	public Integer getWorkOrderNumber() {
		return workOrderNumber;
	}

	public void setWorkOrderNumber(Integer workOrderNumber) {
		this.workOrderNumber = workOrderNumber;
	}
	
	
	@Column(name = "storeid", nullable = false)
	private Integer storeId;
	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	
	
	@Column(name = "calldate", nullable = false)
	
	private Date callDate;
	public Date getCallDate() {
		return callDate;
	}

	public void setCallDate(Date callDate) {
		this.callDate = callDate;
	}
	
	
	
	@Column(name = "scheduleddate", nullable = false)
	
	private Date scheduledDate;
	public Date getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(Date scheduledDate) {
		this.scheduledDate = scheduledDate;
	}
	
	
	
	
	public String getUpdatedBy() {
		return "By Service Channel";
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = "By Service Channel";
	}

	public String getRequestTrackingId() {
		return requestTrackingId;
	}
	public void setRequestTrackingId(String requestTrackingId) {
		this.requestTrackingId = requestTrackingId;
	}




	public Date getCreatedDate() {
		
		return new Date( );
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;;
	}



	

	public Date getUpdatedDate() {
		
		return new Date();
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	



	
	@Override
	public String toString() {
		return "ServiceChannelWorkOrders [createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + ", updatedBy=" + updatedBy
				+ ", requestTrackingId=" + requestTrackingId + ", store="
				+ store + ", Id=" + Id + ", workOrderNumber=" + workOrderNumber
				+ ", storeId=" + storeId + ", callDate=" + callDate
				+ ", scheduledDate=" + scheduledDate + "]";
	}


}
	

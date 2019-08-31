	package com.wm.brta.domain;


	import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wm.brta.constants.ServiceChannelEnumConstants;



	@Entity
	@Table(name = "[servicechannelfailedworkorders]")
	public class ServiceChannelFailedWorkOrders {  

		public ServiceChannelFailedWorkOrders() {
			
		}
		
		
		
		
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
		
		
		@Column(name = "status_primary", nullable = false)
		private String status_Primary;
		
		@Enumerated(EnumType.STRING)
		@Column(name = "status_extended", nullable = false)  
		private ServiceChannelEnumConstants status_Extended;
			
		
		
		@Column(name = "comments", nullable = false)  
		private String comments;
		
		
		@Column(name = "check_in_date_time", nullable = false)  
		private String check_In_Date_Time;	
		
		
		@Column(name = "check_out_date_time", nullable = true)  
		private String check_Out_Date_Time;	
		
		
		@Column(name = "workordernumber", nullable = true)  
		private Integer workOrderNumber;	
		
		
		
		@Column(name = "latitude", nullable = true)  
		private String latitude;
		
		
		@Column(name = "longitude", nullable = true)  
		private String longitude;
		@Override
		public String toString() {
			return "ServiceChannelFailedWorkOrders [Id=" + Id
					+ ", status_Primary=" + status_Primary
					+ ", status_Extended=" + status_Extended + ", comments="
					+ comments + ", check_In_Date_Time=" + check_In_Date_Time
					+ ", check_Out_Date_Time=" + check_Out_Date_Time
					+ ", workOrderNumber=" + workOrderNumber + ", latitude="
					+ latitude + ", longitude=" + longitude + "]";
		}
		public String getStatus_Primary() {
			return status_Primary;
		}
		public void setStatus_Primary(String status_Primary) {
			this.status_Primary = status_Primary;
		}
		
		public String getComments() {
			return comments;
		}
		public void setComments(String comments) {
			this.comments = comments;
		}
		public String getCheck_In_Date_Time() {
			return check_In_Date_Time;
		}
		public void setCheck_In_Date_Time(String check_In_Date_Time) {
			this.check_In_Date_Time = check_In_Date_Time;
		}
		public String getCheck_Out_Date_Time() {
			return check_Out_Date_Time;
		}
		public void setCheck_Out_Date_Time(String check_Out_Date_Time) {
			this.check_Out_Date_Time = check_Out_Date_Time;
		}
		public Integer getWorkOrderNumber() {
			return workOrderNumber;
		}
		public void setWorkOrderNumber(Integer workOrderNumber) {
			this.workOrderNumber = workOrderNumber;
		}
		public String getLatitude() {
			return latitude;
		}
		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}
		public String isLongitude() {
			return longitude;
		}
		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}
		public ServiceChannelEnumConstants getStatus_Extended() {
			return status_Extended;
		}
		public void setStatus_Extended(ServiceChannelEnumConstants status_Extended) {
			this.status_Extended = status_Extended;
		}
		public String getLongitude() {
			return longitude;
		}
		
		
		
		
	
	}

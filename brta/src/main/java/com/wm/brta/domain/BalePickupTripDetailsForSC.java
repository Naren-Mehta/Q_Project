package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.wm.brta.constants.ServiceChannelEnumConstants;

@Entity
@Table(name = "[balepickuptripdetailsforsc]")
public class BalePickupTripDetailsForSC {

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

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "driver")
	private User userId;

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "material", nullable = false)
	private Material material;

	public Material getMaterial() {
		return material;
	}

	public void setMaterial(Material material) {
		this.material = material;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customersite", nullable = false)
	private CustomerSite customerSite;

	public CustomerSite getCustomerSite() {
		return customerSite;
	}

	public void setCustomerSite(CustomerSite customerSite) {
		this.customerSite = customerSite;
	}

	@Column(name = "checkindatetime", nullable = false)
	private Date checkinDateTime;

	public Date getCheckinDateTime() {
		return checkinDateTime;
	}

	public void setCheckinDateTime(Date checkinDateTime) {
		this.checkinDateTime = checkinDateTime;
	}

	@Column(name = "checkoutdatetime")
	private Date checkoutDateTime;

	public Date getCheckoutDateTime() {
		return checkoutDateTime;
	}

	public void setCheckoutDateTime(Date checkoutDateTime) {
		this.checkoutDateTime = checkoutDateTime;
	}

	@Column(name = "truckid", nullable = false)
	private String truckId;

	public String getTruckId() {
		return truckId;
	}

	public void setTruckId(String truckId) {
		this.truckId = truckId;
	}

	@Column(name = "resolution", nullable = false)
	private String resolution;

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ServiceChannelEnumConstants status;

	public ServiceChannelEnumConstants getStatus() {
		return status;
	}

	public void setStatus(ServiceChannelEnumConstants onsite) {
		this.status = onsite;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supplierid", nullable = false)
	private Supplier supplier;

	public Supplier getSupplier() {
		return supplier;
	}

	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

	@Column(name = "balespicked", nullable = false)
	private Integer balesPicked;

	public Integer getBalesPicked() {
		return balesPicked;
	}

	public void setBalesPicked(Integer balesPicked) {
		this.balesPicked = balesPicked;
	}

	@Column(name = "balesremaining", nullable = false)
	private Integer balesRemaining;

	public Integer getBalesRemaining() {
		return balesRemaining;
	}

	public void setBalesRemaining(Integer balesRemaining) {
		this.balesRemaining = balesRemaining;
	}
	
	
	@Column(name = "latitude", nullable = false)
	private String latitude;

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	
	@Column(name = "longitude", nullable = false)
	private String longitude;

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	
	@Column(name = "updateddevice", nullable = false)
	private String updatedDevice;

	public String getUpdatedDevice() {
		return updatedDevice;
	}

	public void setUpdatedDevice(String updatedDevice) {
		this.updatedDevice = updatedDevice;
	}

	
	
	@Column(name = "utccheckindatetime", nullable = false)
	private String uTCCheckinDateTime;
	
	
	
	@Column(name = "utccheckoutdatetime", nullable = true)
	private String uTCCheckoutDateTime;
	
	@Column(name = "timezone", nullable = false)
	private String timeZone;
	
	
	
	@Column(name = "bol", nullable = true)
	private Integer bol;
	
	
	@Column(name = "storename", nullable = false)
	private String storeName;
	
	
	
	public BalePickupTripDetailsForSC() {
		super();
	}

	public BalePickupTripDetailsForSC(Integer balesPicked,
			Integer balesRemaining, ServiceChannelEnumConstants status,
			String truckId, String resolution, Integer materialId,
			String description, String checkInLatitude , String checkInLongitude) {
		super();
		this.balesPicked = balesPicked;
		this.balesRemaining = balesRemaining;
		this.status = status;
		this.truckId = truckId;
		this.resolution = resolution;
		this.checkInLatitude = checkInLatitude;
		this.checkInLongitude = checkInLongitude;
		this.material = new Material(materialId, description);

	}


	public BalePickupTripDetailsForSC(ServiceChannelEnumConstants status, Date checkinDateTime, Date checkoutDateTime,
			String resolution, String  latitude,
			String longitude, Integer bol, String storeName, String updatedDevice, Integer balesRemaining,  String checkInLatitude, String checkInLongitude){
		super();
		this.status=status;
		this.checkinDateTime=checkinDateTime;
		this.checkoutDateTime=checkoutDateTime;
		this.resolution=resolution;
		this.latitude=latitude;
		this.longitude=longitude;
		this.bol=bol;
		this.storeName=storeName;
		this.updatedDevice=updatedDevice;
		this.balesRemaining = balesRemaining;
		
		this.checkInLatitude = checkInLatitude;
		this.checkInLongitude = checkInLongitude;
		
	}
			
			
			
			
			
	@Override
	public String toString() {
		return "BalePickupTripDetailsForSC [Id=" + Id + ", userId=" + userId
				+ ", material=" + material + ", customerSite=" + customerSite
				+ ", checkinDateTime=" + checkinDateTime
				+ ", checkoutDateTime=" + checkoutDateTime + ", truckId="
				+ truckId + ", resolution=" + resolution + ", status=" + status
				+ ", supplier=" + supplier + ", balesPicked=" + balesPicked
				+ ", balesRemaining=" + balesRemaining + ", latitude="
				+ latitude + ", longitude=" + longitude + ", updatedDevice="
				+ updatedDevice + ", uTCCheckinDateTime=" + uTCCheckinDateTime
				+ ", uTCCheckoutDateTime=" + uTCCheckoutDateTime
				+ ", timeZone=" + timeZone + ", bol=" + bol + ", storeName="
				+ storeName + ", checkInLatitude=" + checkInLatitude
				+ ", checkInLongitude=" + checkInLongitude + "]";
	}

	public BalePickupTripDetailsForSC(Integer id, Date checkinDateTime,
			Date checkoutDateTime) {
		super();
		this.Id = id;
		this.checkinDateTime = checkinDateTime;
		this.checkoutDateTime = checkoutDateTime;

	}

	public Integer getBol() {
		return bol;
	}

	public void setBol(Integer bol) {
		this.bol = bol;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getuTCCheckinDateTime() {
		return uTCCheckinDateTime;
	}

	public void setuTCCheckinDateTime(String uTCCheckinDateTime) {
		this.uTCCheckinDateTime = uTCCheckinDateTime;
	}

	public String getuTCCheckoutDateTime() {
		return uTCCheckoutDateTime;
	}

	public void setuTCCheckoutDateTime(String uTCCheckoutDateTime) {
		this.uTCCheckoutDateTime = uTCCheckoutDateTime;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	
	@Column(name = "checkinlatitude", nullable = false)
	private String checkInLatitude;

	public String getCheckInLatitude() {
		return checkInLatitude;
	}

	public void setCheckInLatitude(String checkInLatitude) {
		this.checkInLatitude = checkInLatitude;
	}
	

	
	@Column(name = "checkinlongitude", nullable = false)
	private String checkInLongitude;

	public String getCheckInLongitude() {
		return checkInLongitude;
	}

	public void setCheckInLongitude(String checkInLongitude) {
		this.checkInLongitude = checkInLongitude;
	}

	

	
	
	
	
}

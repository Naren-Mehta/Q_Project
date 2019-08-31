package com.wm.brta.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@Entity
@Table(name = "balepickupsummaryview")
@JsonInclude(Include.NON_NULL)
public class BalePickupSummaryView {

	@Id
	@Column(name = "tripid") 		        
	private Integer tripId;
	
	@Column(name = "buycustomerid")	
	private Integer buyCustomerId;
	
	@Column(name = "buycustomername")
	private String BuyCustomerName;
	
	@Column(name ="buycustomeralternatesearchreference")
	private String buyCustomerAlternateSearchReference;
	
	@Column(name = "buycustomersiteid")
	private Integer buyCustomerSiteId;
	
	@Column(name = "buycustomersitename")	    		  
	private String buyCustomerSiteName;
	
	@Column(name = "buycustomerlocationid")   
	private Integer buyCustomerLocationId;
	
	@Column(name = "buycustomerhousenumber")
	private String buyCustomerHouseNumber;
	
	@Column(name = "buycustomeraddress1")
	private String buyCustomerAddress1;
		    						  
	@Column(name = "buycustomeraddress2")
	private String buyCustomerAddress2;
	
	@Column(name = "buycustomeraddress3")
	private String buyCustomerAddress3;
	
	 @Column(name = "buycustomeraddress4")
	 private String buyCustomerAddress4;
	 
	 @Column(name = "buycustomeraddress5") 
	 private String buyCustomerAddress5;

	 @Column(name = "buycusstomerpostcode") 	
	 private String buyCusstomerPostcode;
	 
	 @Column(name = "buycustomertelno")
	 private String buyCustomerTelNo;
	 
	 @Column(name = "buycustomersitealternatesearchreference")
	 private String buyCustomerSiteAlternateSearchReference;
	 
	 @Column(name = "sellcustomerid")
	 private Integer sellCustomerId;
	 
	 @Column(name = "sellcustomername")
	 private String sellCustomerName;
	 
	 @Column(name = "sellcustomeralternatesearchreference")
	 private String sellCustomerAlternateSearchReference;
	 
	 @Column(name = "sellcustomersitealternatesearchreference")
	 private String sellCustomerSiteAlternateSearchReference;
	 
	 @Column(name = "sellcustomersiteid") 	
	 private Integer sellCustomerSiteId;
	 
	 @Column(name = "sellcustomersitename") 		        
	 private String sellCustomerSiteName;
	 
	 @Column(name = "sellcustomerlocationid") 		        
	 private Integer sellCustomerLocationId; 
	 
	 @Column(name = "sellcustomerhousenumber") 		        
	 private String sellCustomerHouseNumber;
	 
	 @Column(name = "sellcustomeraddress1") 		        
	 private String sellCustomerAddress1; 
	 
	 @Column(name = "sellcustomeraddress2") 		        
	 private String sellCustomerAddress2; 
	 
	 @Column(name = "sellcustomeraddress3") 		        
	 private String sellCustomerAddress3; 
	 
	 @Column(name = "sellcustomeraddress4") 		        
	 private String sellCustomerAddress4; 
	 
	 @Column(name = "sellcustomeraddress5") 		        
	 private String sellCustomerAddress5; 
	 
	 @Column(name = "sellcustomerpostcode") 		        
	 private String sellCustomerPostcode; 
	 
	 @Column(name = "sellcustomertelno") 		        
	 private String  sellCustomerTelNo;
	 
	 @Column(name = "supplierid") 		        
	 private Integer supplierId; 
	 
	 @Column(name = "suppliername") 		        
	 private String supplierName; 
	 
	 @Column(name = "driverid") 		        
	 private Integer driverId; 
	 
	 @Column(name = "driverfirstname") 		        
	 private String  driverFirstName; 
	 
	 @Column(name = "driverlastname") 		        
	 private String driverLastName; 
	 
	 @Column(name = "driveremail") 		        
	 private String  driverEmail; 
	 
	 @Column(name = "drivermobilephone") 		        
	 private String driverMobilePhone; 
	 
	 @Column(name = "materialid") 		        
	 private Integer materialId; 
	 
	 @Column(name = "materialdescription") 		       
	 private String materialDescription; 
	 
	 @Column(name = "materialshortname") 		        
	 private String  materialShortName; 
	 
	 @Column(name = "pickupdate") 		        
	 private Date pickupDate;
	 
	 @Column(name = "balespicked") 		        
	 private Integer balesPicked;
	 
	 @Column(name = "balesremaining") 		       
	 private Integer balesRemaining; 
	 
	 @Column(name = "bol") 		       //check this naming convention
	 private Integer bOL; 
	 
	 @Column(name = "releaseno") 		        
	 private String releaseNo;
	 
	 @Column(name = "grossweight") 		        
	 private Integer grossWeight;
	 
	 @Column(name = "tareweight") 		        
	 private Integer tareWeight;
	 
	 @Column(name = "vehicleplateno") 		        
	 private String vehiclePlateNo;
	  
	 @Column(name = "comments") 		        
	 private String comments;
	 
	 @Column(name = "incidenttypeid") 		        
	 private Integer incidentTypeId; 
	 
	 @Column(name = "incidentdescription") 		        
	 private String incidentDescription; 
	 
	 @Column(name="deliverydate") 
	 private Date deliveryDate;
	 
	 @Column(name="imageavailable") 
	 private String imageAvailable;
	 
	 @Column(name="avgbaleweight") 
	 private Integer avgBaleWeight;

	public Integer getBuyCustomerId() {
		return buyCustomerId;
	}

	public void setBuyCustomerId(Integer buyCustomerId) {
		this.buyCustomerId = buyCustomerId;
	}

	public String getBuyCustomerName() {
		return BuyCustomerName !=null ? BuyCustomerName : "";
	}

	public void setBuyCustomerName(String buyCustomerName) {
		BuyCustomerName = buyCustomerName;
	}

	public String getBuyCustomerAlternateSearchReference() {
		return buyCustomerAlternateSearchReference !=null?buyCustomerAlternateSearchReference:"";
	}

	public void setBuyCustomerAlternateSearchReference(
			String buyCustomerAlternateSearchReference) {
		this.buyCustomerAlternateSearchReference = buyCustomerAlternateSearchReference;
	}

	public Integer getBuyCustomerSiteId() {
		return buyCustomerSiteId;
	}

	public void setBuyCustomerSiteId(Integer buyCustomerSiteId) {
		this.buyCustomerSiteId = buyCustomerSiteId;
	}

	public String getBuyCustomerSiteName() {
		return buyCustomerSiteName !=null?buyCustomerSiteName:"";
	}

	public void setBuyCustomerSiteName(String buyCustomerSiteName) {
		this.buyCustomerSiteName = buyCustomerSiteName;
	}

	public Integer getBuyCustomerLocationId() {
		return buyCustomerLocationId;
	}

	public void setBuyCustomerLocationId(Integer buyCustomerLocationId) {
		this.buyCustomerLocationId = buyCustomerLocationId;
	}

	public String getBuyCustomerHouseNumber() {
		return buyCustomerHouseNumber;
	}

	public void setBuyCustomerHouseNumber(String buyCustomerHouseNumber) {
		this.buyCustomerHouseNumber = buyCustomerHouseNumber;
	}

	public String getBuyCustomerAddress1() {
		return buyCustomerAddress1;
	}

	public void setBuyCustomerAddress1(String buyCustomerAddress1) {
		this.buyCustomerAddress1 = buyCustomerAddress1;
	}

	public String getBuyCustomerAddress2() {
		return buyCustomerAddress2;
	}

	public void setBuyCustomerAddress2(String buyCustomerAddress2) {
		this.buyCustomerAddress2 = buyCustomerAddress2;
	}

	public String getBuyCustomerAddress3() {
		return buyCustomerAddress3;
	}

	public void setBuyCustomerAddress3(String buyCustomerAddress3) {
		this.buyCustomerAddress3 = buyCustomerAddress3;
	}

	public String getBuyCustomerAddress4() {
		return buyCustomerAddress4;
	}

	public void setBuyCustomerAddress4(String buyCustomerAddress4) {
		this.buyCustomerAddress4 = buyCustomerAddress4;
	}

	public String getBuyCustomerAddress5() {
		return buyCustomerAddress5;
	}

	public void setBuyCustomerAddress5(String buyCustomerAddress5) {
		this.buyCustomerAddress5 = buyCustomerAddress5;
	}

	public String getBuyCusstomerPostcode() {
		return buyCusstomerPostcode;
	}

	public void setBuyCusstomerPostcode(String buyCusstomerPostcode) {
		this.buyCusstomerPostcode = buyCusstomerPostcode;
	}

	public String getBuyCustomerTelNo() {
		return buyCustomerTelNo;
	}

	public void setBuyCustomerTelNo(String buyCustomerTelNo) {
		this.buyCustomerTelNo = buyCustomerTelNo;
	}

	public Integer getSellCustomerSiteId() {
		return sellCustomerSiteId;
	}

	public void setSellCustomerSiteId(Integer sellCustomerSiteId) {
		this.sellCustomerSiteId = sellCustomerSiteId;
	}

	public String getSellCustomerSiteName() {
		return sellCustomerSiteName  !=null?sellCustomerSiteName:"";
	}

	public void setSellCustomerSiteName(String sellCustomerSiteName) {
		this.sellCustomerSiteName = sellCustomerSiteName;
	}

	public Integer getSellCustomerLocationId() {
		return sellCustomerLocationId;
	}

	public void setSellCustomerLocationId(Integer sellCustomerLocationId) {
		this.sellCustomerLocationId = sellCustomerLocationId;
	}

	public String getSellCustomerHouseNumber() {
		return sellCustomerHouseNumber;
	}

	public void setSellCustomerHouseNumber(String sellCustomerHouseNumber) {
		this.sellCustomerHouseNumber = sellCustomerHouseNumber;
	}

	public String getSellCustomerAddress1() {
		return sellCustomerAddress1;
	}

	public void setSellCustomerAddress1(String sellCustomerAddress1) {
		this.sellCustomerAddress1 = sellCustomerAddress1;
	}

	public String getSellCustomerAddress2() {
		return sellCustomerAddress2;
	}

	public void setSellCustomerAddress2(String sellCustomerAddress2) {
		this.sellCustomerAddress2 = sellCustomerAddress2;
	}

	public String getSellCustomerAddress3() {
		return sellCustomerAddress3;
	}

	public void setSellCustomerAddress3(String sellCustomerAddress3) {
		this.sellCustomerAddress3 = sellCustomerAddress3;
	}

	public String getSellCustomerAddress4() {
		return sellCustomerAddress4;
	}

	public void setSellCustomerAddress4(String sellCustomerAddress4) {
		this.sellCustomerAddress4 = sellCustomerAddress4;
	}

	public String getSellCustomerAddress5() {
		return sellCustomerAddress5;
	}

	public void setSellCustomerAddress5(String sellCustomerAddress5) {
		this.sellCustomerAddress5 = sellCustomerAddress5;
	}

	public String getSellCustomerPostcode() {
		return sellCustomerPostcode;
	}

	public void setSellCustomerPostcode(String sellCustomerPostcode) {
		this.sellCustomerPostcode = sellCustomerPostcode;
	}

	public String getSellCustomerTelNo() {
		return sellCustomerTelNo;
	}

	public void setSellCustomerTelNo(String sellCustomerTelNo) {
		this.sellCustomerTelNo = sellCustomerTelNo;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName  !=null?supplierName:"";
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public Integer getDriverId() {
		return driverId;
	}

	public void setDriverId(Integer driverId) {
		this.driverId = driverId;
	}

	public String getDriverFirstName() {
		return driverFirstName;
	}

	public void setDriverFirstName(String driverFirstName) {
		this.driverFirstName = driverFirstName;
	}

	public String getDriverLastName() {
		return driverLastName;
	}

	public void setDriverLastName(String driverLastName) {
		this.driverLastName = driverLastName;
	}

	public String getDriverEmail() {
		return driverEmail;
	}

	public void setDriverEmail(String driverEmail) {
		this.driverEmail = driverEmail;
	}

	public String getDriverMobilePhone() {
		return driverMobilePhone;
	}

	public void setDriverMobilePhone(String driverMobilePhone) {
		this.driverMobilePhone = driverMobilePhone;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public String getMaterialDescription() {
		return materialDescription!=null?materialDescription:""; 
	}

	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}

	public String getMaterialShortName() {
		return   materialShortName!=null?materialShortName:""; 
	}

	public void setMaterialShortName(String materialShortName) {
		this.materialShortName = materialShortName;
	}

	public Date getPickupDate() {
		return pickupDate;
	}

	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}

	public Integer getBalesPicked() {
		return balesPicked;
	}

	public void setBalesPicked(Integer balesPicked) {
		this.balesPicked = balesPicked;
	}

	public Integer getBalesRemaining() {
		return balesRemaining;
	}

	public void setBalesRemaining(Integer balesRemaining) {
		this.balesRemaining = balesRemaining;
	}

	public Integer getbOL() {
		return bOL;
	}

	public void setbOL(Integer bOL) {
		this.bOL = bOL;
	}

	public String getReleaseNo() {
		return   releaseNo!=null?releaseNo:""; 
	}

	public void setReleaseNo(String releaseNo) {
		this.releaseNo = releaseNo;
	}

	public Integer getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Integer grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Integer getTareWeight() {
		return tareWeight;
	}

	public void setTareWeight(Integer tareWeight) {
		this.tareWeight = tareWeight;
	}

	public Integer getTripId() {
		return tripId;
	}

	public void setTripId(Integer tripId) {
		this.tripId = tripId;
	}

	public String getVehiclePlateNo() {
		return vehiclePlateNo;
	}

	public void setVehiclePlateNo(String vehiclePlateNo) {
		this.vehiclePlateNo = vehiclePlateNo;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	
	public Integer getSellCustomerId() {
		return sellCustomerId;
	}

	public void setSellCustomerId(Integer sellCustomerId) {
		this.sellCustomerId = sellCustomerId;
	}

	public String getSellCustomerName() {
		return   sellCustomerName!=null?sellCustomerName:""; 
	}

	public void setSellCustomerName(String sellCustomerName) {
		this.sellCustomerName = sellCustomerName;
	}

	public String getSellCustomerAlternateSearchReference() {
		return  sellCustomerAlternateSearchReference!=null?sellCustomerAlternateSearchReference:""; 
	}

	public void setSellCustomerAlternateSearchReference(
			String sellCustomerAlternateSearchReference) {
		this.sellCustomerAlternateSearchReference = sellCustomerAlternateSearchReference;
	}

	public String getBuyCustomerSiteAlternateSearchReference() {
		return    buyCustomerSiteAlternateSearchReference!=null?buyCustomerSiteAlternateSearchReference:"";
	}

	public void setBuyCustomerSiteAlternateSearchReference(
			String buyCustomerSiteAlternateSearchReference) {
		this.buyCustomerSiteAlternateSearchReference = buyCustomerSiteAlternateSearchReference;
	}

	public String getSellCustomerSiteAlternateSearchReference() {
		return   sellCustomerSiteAlternateSearchReference!=null?sellCustomerSiteAlternateSearchReference:"";
	}

	public void setSellCustomerSiteAlternateSearchReference(
			String sellCustomerSiteAlternateSearchReference) {
		this.sellCustomerSiteAlternateSearchReference = sellCustomerSiteAlternateSearchReference;
	}

	public Integer getIncidentTypeId() {
		return incidentTypeId;
	}

	public void setIncidentTypeId(Integer incidentTypeId) {
		this.incidentTypeId = incidentTypeId;
	}

	public String getIncidentDescription() {
		return     incidentDescription!=null?incidentDescription:"";
	}

	public void setIncidentDescription(String incidentDescription) {
		this.incidentDescription = incidentDescription;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getImageAvailable() {
		return   imageAvailable!=null?imageAvailable:"";
	}

	public void setImageAvailable(String imageAvailable) {
		this.imageAvailable = imageAvailable;
	}

	public Integer getAvgBaleWeight() {
		return avgBaleWeight;
	}

	public void setAvgBaleWeight(Integer avgBaleWeight) {
		this.avgBaleWeight = avgBaleWeight;
	} 
	 		 	
	
}

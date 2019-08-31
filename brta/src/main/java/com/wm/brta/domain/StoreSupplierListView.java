package com.wm.brta.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//@IdClass(StoreSupplierListView.class)
@Table(name = "storesupplierlistview")
public class StoreSupplierListView implements Serializable{
	

	@Id
	@Column(name="sequenceno")
	private Integer sequenceNo;

	@Column(name = "buycustomersiteid")
	private Integer buyCustomerSiteId;
	
	@Column(name = "buycustomerid")
	private Integer buyCustomerId;

	@Column(name = "buycustomersitename")
	private String buyCustomerSiteName;

	@Column(name = "buycustomerlocationid")
	private Integer buyCustomerLocationId;

	@Column(name = "balepickupstartdate")
	private Date balePickupStartDate;

	@Column(name = "buycustomeralternativesearchreference")
	private String buyCustomerAlternativeSearchReference;

	@Column(name = "balepickupenddate") 
	private Date balePickupEndDate;

	@Column(name = "supplierid")
	private Integer supplierId;

	@Column(name = "supplierdescription")
	private String supplierDescription;

	@Column(name = "materialid")
	private Integer materialId;
	
	@Column(name = "avgbaleweight")
	private Integer avgBaleWeight;

	@Column(name = "materialdescription")
	private String materialDescription;

	@Column(name = "shortname")
	private String shortName;

	@Column(name = "sellcustomersiteid")
	private Integer sellCustomerSiteId;

	@Column(name = "sellcustomersitename")
	private String sellCustomerSiteName;

	@Column(name = "sellcustomerlocationid")
	private Integer sellCustomerLocationId;

	@Column(name = "sellcustomeralternativesearchreference")
	private String sellCustomerAlternativeSearchReference;

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

	@Column(name = "buycustomerpostcode")
	private String buyCustomerPostcode;

	@Column(name = "buycustomertelno")
	private String buyCustomerTelNo;

	@Column(name = "buycustomername")
	private String buyCustomerName;
	
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
	private String sellCustomerTelNo;
	
	
	@Column(name = "frequencyid")
	private Integer frequencyId;
	
	@Column(name = "frequency")
	private String frequency;

	@Column(name = "frequencyday")
	private Integer frequencyDay;
	
	
	
	@Column(name = "driver1id")
	private Integer driver1Id;
	
	@Column(name = "driver2id")
	private Integer driver2Id;
	
	@Column(name = "driver1_firstname")
	private String driver1_FirstName;

	@Column(name = "driver_1_lastname")
	private String driver_1_LastName;
	
	@Column(name = "driver1_email")
	private String driver1_Email;
	
	@Column(name = "driver1_mobilephone")
	private String driver1_MobilePhone;


	@Column(name = "driver2_firstname")
	private String driver2_FirstName;
	
	
	@Column(name = "driver2_lastname")
	private String driver2_LastName;
	
	@Column(name = "driver2_email")
	private String driver2_Email;
	
	@Column(name = "driver2_mobilephone")
	private String driver2_MobilePhone;
	
	
	@Column(name = "startdate")
	private Date startDate;
	
	@Column(name = "enddate")
	private Date endDate;
	
	
	
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public Integer getDriver1Id() {
		return driver1Id;
	}

	public void setDriver1Id(Integer driver1Id) {
		this.driver1Id = driver1Id;
	}

	public Integer getDriver2Id() {
		return driver2Id;
	}

	public void setDriver2Id(Integer driver2Id) {
		this.driver2Id = driver2Id;
	}

	public String getDriver1_FirstName() {
		return driver1_FirstName;
	}

	public void setDriver1_FirstName(String driver1_FirstName) {
		this.driver1_FirstName = driver1_FirstName;
	}

	public String getDriver_1_LastName() {
		return driver_1_LastName;
	}

	public void setDriver_1_LastName(String driver_1_LastName) {
		this.driver_1_LastName = driver_1_LastName;
	}

	public String getDriver1_Email() {
		return driver1_Email;
	}

	public void setDriver1_Email(String driver1_Email) {
		this.driver1_Email = driver1_Email;
	}

	public String getDriver1_MobilePhone() {
		return driver1_MobilePhone;
	}

	public void setDriver1_MobilePhone(String driver1_MobilePhone) {
		this.driver1_MobilePhone = driver1_MobilePhone;
	}

	public String getDriver2_FirstName() {
		return driver2_FirstName;
	}

	public void setDriver2_FirstName(String driver2_FirstName) {
		this.driver2_FirstName = driver2_FirstName;
	}

	public String getDriver2_LastName() {
		return driver2_LastName;
	}

	public void setDriver2_LastName(String driver2_LastName) {
		this.driver2_LastName = driver2_LastName;
	}

	public String getDriver2_Email() {
		return driver2_Email;
	}

	public void setDriver2_Email(String driver2_Email) {
		this.driver2_Email = driver2_Email;
	}

	public String getDriver2_MobilePhone() {
		return driver2_MobilePhone;
	}

	public void setDriver2_MobilePhone(String driver2_MobilePhone) {
		this.driver2_MobilePhone = driver2_MobilePhone;
	}

	public Integer getBuyCustomerSiteId() {
		return buyCustomerSiteId;
	}

	public void setBuyCustomerSiteId(Integer buyCustomerSiteId) {
		this.buyCustomerSiteId = buyCustomerSiteId;
	}

	public Integer getBuyCustomerId() {
		return buyCustomerId;
	}

	public void setBuyCustomerId(Integer buyCustomerId) {
		this.buyCustomerId = buyCustomerId;
	}

	public String getBuyCustomerSiteName() {
		return buyCustomerSiteName;
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

	public Date getBalePickupStartDate() {
		return balePickupStartDate;
	}

	public void setBalePickupStartDate(Date balePickupStartDate) {
		this.balePickupStartDate = balePickupStartDate;
	}

	public String getBuyCustomerAlternativeSearchReference() {
		return buyCustomerAlternativeSearchReference;
	}

	public void setBuyCustomerAlternativeSearchReference(
			String buyCustomerAlternativeSearchReference) {
		this.buyCustomerAlternativeSearchReference = buyCustomerAlternativeSearchReference;
	}

	public Date getBalePickupEndDate() {
		return balePickupEndDate;
	}

	public void setBalePickupEndDate(Date balePickupEndDate) {
		this.balePickupEndDate = balePickupEndDate;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierDescription() {
		return supplierDescription;
	}

	public void setSupplierDescription(String supplierDescription) {
		this.supplierDescription = supplierDescription;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Integer getAvgBaleWeight() {
		return avgBaleWeight;
	}

	public void setAvgBaleWeight(Integer avgBaleWeight) {
		this.avgBaleWeight = avgBaleWeight;
	}

	public String getMaterialDescription() {
		return materialDescription;
	}

	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public Integer getSellCustomerSiteId() {
		return sellCustomerSiteId;
	}

	public void setSellCustomerSiteId(Integer sellCustomerSiteId) {
		this.sellCustomerSiteId = sellCustomerSiteId;
	}

	public String getSellCustomerSiteName() {
		return sellCustomerSiteName;
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

	public String getSellCustomerAlternativeSearchReference() {
		return sellCustomerAlternativeSearchReference;
	}

	public void setSellCustomerAlternativeSearchReference(
			String sellCustomerAlternativeSearchReference) {
		this.sellCustomerAlternativeSearchReference = sellCustomerAlternativeSearchReference;
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

	public String getBuyCustomerPostcode() {
		return buyCustomerPostcode;
	}

	public void setBuyCustomerPostcode(String buyCustomerPostcode) {
		this.buyCustomerPostcode = buyCustomerPostcode;
	}

	public String getBuyCustomerTelNo() {
		return buyCustomerTelNo;
	}

	public void setBuyCustomerTelNo(String buyCustomerTelNo) {
		this.buyCustomerTelNo = buyCustomerTelNo;
	}


	public String getBuyCustomerName() {
		return buyCustomerName;
	}

	public void setBuyCustomerName(String buyCustomerName) {
		this.buyCustomerName = buyCustomerName;
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
	
	

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Integer getFrequencyDay() {
		return frequencyDay;
	}

	public void setFrequencyDay(Integer frequencyDay) {
		this.frequencyDay = frequencyDay;
	}

	
	
	public Integer getFrequencyId() {
		return frequencyId;
	}

	public void setFrequencyId(Integer frequencyId) {
		this.frequencyId = frequencyId;
	}

	@Override
	public String toString() {
		return "StoreSupplierListView [buyCustomerSiteId=" + buyCustomerSiteId
				+ ", buyCustomerId=" + buyCustomerId + ", buyCustomerSiteName="
				+ buyCustomerSiteName + ", buyCustomerLocationId="
				+ buyCustomerLocationId + ", balePickupStartDate="
				+ balePickupStartDate
				+ ", buyCustomerAlternativeSearchReference="
				+ buyCustomerAlternativeSearchReference
				+ ", balePickupEndDate=" + balePickupEndDate + ", supplierId="
				+ supplierId + ", supplierDescription=" + supplierDescription
				+ ", materialId=" + materialId + ", avgBaleWeight="
				+ avgBaleWeight + ", materialDescription="
				+ materialDescription + ", shortName=" + shortName
				+ ", sellCustomerSiteId=" + sellCustomerSiteId
				+ ", sellCustomerSiteName=" + sellCustomerSiteName
				+ ", sellCustomerLocationId=" + sellCustomerLocationId
				+ ", sellCustomerAlternativeSearchReference="
				+ sellCustomerAlternativeSearchReference
				+ ", buyCustomerHouseNumber=" + buyCustomerHouseNumber
				+ ", buyCustomerAddress1=" + buyCustomerAddress1
				+ ", buyCustomerAddress2=" + buyCustomerAddress2
				+ ", buyCustomerAddress3=" + buyCustomerAddress3
				+ ", buyCustomerAddress4=" + buyCustomerAddress4
				+ ", buyCustomerAddress5=" + buyCustomerAddress5
				+ ", buyCustomerPostcode=" + buyCustomerPostcode
				+ ", buyCustomerTelNo=" + buyCustomerTelNo
				+ ", buyCustomerName=" + buyCustomerName
				+ ", sellCustomerHouseNumber=" + sellCustomerHouseNumber
				+ ", sellCustomerAddress1=" + sellCustomerAddress1
				+ ", sellCustomerAddress2=" + sellCustomerAddress2
				+ ", sellCustomerAddress3=" + sellCustomerAddress3
				+ ", sellCustomerAddress4=" + sellCustomerAddress4
				+ ", sellCustomerAddress5=" + sellCustomerAddress5
				+ ", sellCustomerPostcode=" + sellCustomerPostcode
				+ ", sellCustomerTelNo=" + sellCustomerTelNo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((avgBaleWeight == null) ? 0 : avgBaleWeight.hashCode());
		result = prime
				* result
				+ ((balePickupEndDate == null) ? 0 : balePickupEndDate
						.hashCode());
		result = prime
				* result
				+ ((balePickupStartDate == null) ? 0 : balePickupStartDate
						.hashCode());
		result = prime
				* result
				+ ((buyCustomerAddress1 == null) ? 0 : buyCustomerAddress1
						.hashCode());
		result = prime
				* result
				+ ((buyCustomerAddress2 == null) ? 0 : buyCustomerAddress2
						.hashCode());
		result = prime
				* result
				+ ((buyCustomerAddress3 == null) ? 0 : buyCustomerAddress3
						.hashCode());
		result = prime
				* result
				+ ((buyCustomerAddress4 == null) ? 0 : buyCustomerAddress4
						.hashCode());
		result = prime
				* result
				+ ((buyCustomerAddress5 == null) ? 0 : buyCustomerAddress5
						.hashCode());
		result = prime
				* result
				+ ((buyCustomerAlternativeSearchReference == null) ? 0
						: buyCustomerAlternativeSearchReference.hashCode());
		result = prime
				* result
				+ ((buyCustomerHouseNumber == null) ? 0
						: buyCustomerHouseNumber.hashCode());
		result = prime * result
				+ ((buyCustomerId == null) ? 0 : buyCustomerId.hashCode());
		result = prime
				* result
				+ ((buyCustomerLocationId == null) ? 0 : buyCustomerLocationId
						.hashCode());
		result = prime * result
				+ ((buyCustomerName == null) ? 0 : buyCustomerName.hashCode());
		result = prime
				* result
				+ ((buyCustomerPostcode == null) ? 0 : buyCustomerPostcode
						.hashCode());
		result = prime
				* result
				+ ((buyCustomerSiteId == null) ? 0 : buyCustomerSiteId
						.hashCode());
		result = prime
				* result
				+ ((buyCustomerSiteName == null) ? 0 : buyCustomerSiteName
						.hashCode());
		result = prime
				* result
				+ ((buyCustomerTelNo == null) ? 0 : buyCustomerTelNo.hashCode());
		result = prime * result
				+ ((frequency == null) ? 0 : frequency.hashCode());
		result = prime * result
				+ ((frequencyDay == null) ? 0 : frequencyDay.hashCode());
		result = prime * result
				+ ((frequencyId == null) ? 0 : frequencyId.hashCode());
		result = prime
				* result
				+ ((materialDescription == null) ? 0 : materialDescription
						.hashCode());
		result = prime * result
				+ ((materialId == null) ? 0 : materialId.hashCode());
		result = prime
				* result
				+ ((sellCustomerAddress1 == null) ? 0 : sellCustomerAddress1
						.hashCode());
		result = prime
				* result
				+ ((sellCustomerAddress2 == null) ? 0 : sellCustomerAddress2
						.hashCode());
		result = prime
				* result
				+ ((sellCustomerAddress3 == null) ? 0 : sellCustomerAddress3
						.hashCode());
		result = prime
				* result
				+ ((sellCustomerAddress4 == null) ? 0 : sellCustomerAddress4
						.hashCode());
		result = prime
				* result
				+ ((sellCustomerAddress5 == null) ? 0 : sellCustomerAddress5
						.hashCode());
		result = prime
				* result
				+ ((sellCustomerAlternativeSearchReference == null) ? 0
						: sellCustomerAlternativeSearchReference.hashCode());
		result = prime
				* result
				+ ((sellCustomerHouseNumber == null) ? 0
						: sellCustomerHouseNumber.hashCode());
		result = prime
				* result
				+ ((sellCustomerLocationId == null) ? 0
						: sellCustomerLocationId.hashCode());
		result = prime
				* result
				+ ((sellCustomerPostcode == null) ? 0 : sellCustomerPostcode
						.hashCode());
		result = prime
				* result
				+ ((sellCustomerSiteId == null) ? 0 : sellCustomerSiteId
						.hashCode());
		result = prime
				* result
				+ ((sellCustomerSiteName == null) ? 0 : sellCustomerSiteName
						.hashCode());
		result = prime
				* result
				+ ((sellCustomerTelNo == null) ? 0 : sellCustomerTelNo
						.hashCode());
		result = prime * result
				+ ((sequenceNo == null) ? 0 : sequenceNo.hashCode());
		result = prime * result
				+ ((shortName == null) ? 0 : shortName.hashCode());
		result = prime
				* result
				+ ((supplierDescription == null) ? 0 : supplierDescription
						.hashCode());
		result = prime * result
				+ ((supplierId == null) ? 0 : supplierId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StoreSupplierListView other = (StoreSupplierListView) obj;
		if (avgBaleWeight == null) {
			if (other.avgBaleWeight != null)
				return false;
		} else if (!avgBaleWeight.equals(other.avgBaleWeight))
			return false;
		if (balePickupEndDate == null) {
			if (other.balePickupEndDate != null)
				return false;
		} else if (!balePickupEndDate.equals(other.balePickupEndDate))
			return false;
		if (balePickupStartDate == null) {
			if (other.balePickupStartDate != null)
				return false;
		} else if (!balePickupStartDate.equals(other.balePickupStartDate))
			return false;
		if (buyCustomerAddress1 == null) {
			if (other.buyCustomerAddress1 != null)
				return false;
		} else if (!buyCustomerAddress1.equals(other.buyCustomerAddress1))
			return false;
		if (buyCustomerAddress2 == null) {
			if (other.buyCustomerAddress2 != null)
				return false;
		} else if (!buyCustomerAddress2.equals(other.buyCustomerAddress2))
			return false;
		if (buyCustomerAddress3 == null) {
			if (other.buyCustomerAddress3 != null)
				return false;
		} else if (!buyCustomerAddress3.equals(other.buyCustomerAddress3))
			return false;
		if (buyCustomerAddress4 == null) {
			if (other.buyCustomerAddress4 != null)
				return false;
		} else if (!buyCustomerAddress4.equals(other.buyCustomerAddress4))
			return false;
		if (buyCustomerAddress5 == null) {
			if (other.buyCustomerAddress5 != null)
				return false;
		} else if (!buyCustomerAddress5.equals(other.buyCustomerAddress5))
			return false;
		if (buyCustomerAlternativeSearchReference == null) {
			if (other.buyCustomerAlternativeSearchReference != null)
				return false;
		} else if (!buyCustomerAlternativeSearchReference
				.equals(other.buyCustomerAlternativeSearchReference))
			return false;
		if (buyCustomerHouseNumber == null) {
			if (other.buyCustomerHouseNumber != null)
				return false;
		} else if (!buyCustomerHouseNumber.equals(other.buyCustomerHouseNumber))
			return false;
		if (buyCustomerId == null) {
			if (other.buyCustomerId != null)
				return false;
		} else if (!buyCustomerId.equals(other.buyCustomerId))
			return false;
		if (buyCustomerLocationId == null) {
			if (other.buyCustomerLocationId != null)
				return false;
		} else if (!buyCustomerLocationId.equals(other.buyCustomerLocationId))
			return false;
		if (buyCustomerName == null) {
			if (other.buyCustomerName != null)
				return false;
		} else if (!buyCustomerName.equals(other.buyCustomerName))
			return false;
		if (buyCustomerPostcode == null) {
			if (other.buyCustomerPostcode != null)
				return false;
		} else if (!buyCustomerPostcode.equals(other.buyCustomerPostcode))
			return false;
		if (buyCustomerSiteId == null) {
			if (other.buyCustomerSiteId != null)
				return false;
		} else if (!buyCustomerSiteId.equals(other.buyCustomerSiteId))
			return false;
		if (buyCustomerSiteName == null) {
			if (other.buyCustomerSiteName != null)
				return false;
		} else if (!buyCustomerSiteName.equals(other.buyCustomerSiteName))
			return false;
		if (buyCustomerTelNo == null) {
			if (other.buyCustomerTelNo != null)
				return false;
		} else if (!buyCustomerTelNo.equals(other.buyCustomerTelNo))
			return false;
		if (frequency == null) {
			if (other.frequency != null)
				return false;
		} else if (!frequency.equals(other.frequency))
			return false;
		if (frequencyDay == null) {
			if (other.frequencyDay != null)
				return false;
		} else if (!frequencyDay.equals(other.frequencyDay))
			return false;
		if (frequencyId == null) {
			if (other.frequencyId != null)
				return false;
		} else if (!frequencyId.equals(other.frequencyId))
			return false;
		if (materialDescription == null) {
			if (other.materialDescription != null)
				return false;
		} else if (!materialDescription.equals(other.materialDescription))
			return false;
		if (materialId == null) {
			if (other.materialId != null)
				return false;
		} else if (!materialId.equals(other.materialId))
			return false;
		if (sellCustomerAddress1 == null) {
			if (other.sellCustomerAddress1 != null)
				return false;
		} else if (!sellCustomerAddress1.equals(other.sellCustomerAddress1))
			return false;
		if (sellCustomerAddress2 == null) {
			if (other.sellCustomerAddress2 != null)
				return false;
		} else if (!sellCustomerAddress2.equals(other.sellCustomerAddress2))
			return false;
		if (sellCustomerAddress3 == null) {
			if (other.sellCustomerAddress3 != null)
				return false;
		} else if (!sellCustomerAddress3.equals(other.sellCustomerAddress3))
			return false;
		if (sellCustomerAddress4 == null) {
			if (other.sellCustomerAddress4 != null)
				return false;
		} else if (!sellCustomerAddress4.equals(other.sellCustomerAddress4))
			return false;
		if (sellCustomerAddress5 == null) {
			if (other.sellCustomerAddress5 != null)
				return false;
		} else if (!sellCustomerAddress5.equals(other.sellCustomerAddress5))
			return false;
		if (sellCustomerAlternativeSearchReference == null) {
			if (other.sellCustomerAlternativeSearchReference != null)
				return false;
		} else if (!sellCustomerAlternativeSearchReference
				.equals(other.sellCustomerAlternativeSearchReference))
			return false;
		if (sellCustomerHouseNumber == null) {
			if (other.sellCustomerHouseNumber != null)
				return false;
		} else if (!sellCustomerHouseNumber
				.equals(other.sellCustomerHouseNumber))
			return false;
		if (sellCustomerLocationId == null) {
			if (other.sellCustomerLocationId != null)
				return false;
		} else if (!sellCustomerLocationId.equals(other.sellCustomerLocationId))
			return false;
		if (sellCustomerPostcode == null) {
			if (other.sellCustomerPostcode != null)
				return false;
		} else if (!sellCustomerPostcode.equals(other.sellCustomerPostcode))
			return false;
		if (sellCustomerSiteId == null) {
			if (other.sellCustomerSiteId != null)
				return false;
		} else if (!sellCustomerSiteId.equals(other.sellCustomerSiteId))
			return false;
		if (sellCustomerSiteName == null) {
			if (other.sellCustomerSiteName != null)
				return false;
		} else if (!sellCustomerSiteName.equals(other.sellCustomerSiteName))
			return false;
		if (sellCustomerTelNo == null) {
			if (other.sellCustomerTelNo != null)
				return false;
		} else if (!sellCustomerTelNo.equals(other.sellCustomerTelNo))
			return false;
		if (sequenceNo == null) {
			if (other.sequenceNo != null)
				return false;
		} else if (!sequenceNo.equals(other.sequenceNo))
			return false;
		if (shortName == null) {
			if (other.shortName != null)
				return false;
		} else if (!shortName.equals(other.shortName))
			return false;
		if (supplierDescription == null) {
			if (other.supplierDescription != null)
				return false;
		} else if (!supplierDescription.equals(other.supplierDescription))
			return false;
		if (supplierId == null) {
			if (other.supplierId != null)
				return false;
		} else if (!supplierId.equals(other.supplierId))
			return false;
		return true;
	}
	
	
	
	
}

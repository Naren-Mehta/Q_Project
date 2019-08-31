package com.wm.brta.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name="[user]")
@JsonInclude(Include.NON_NULL)
public class User {
	

	
	private Long userId;
	private String firstName;
	private String middleName;
	private String lastName;
	private String emailId;
	private String mobilePhone;
	private String otherPhone;
	private Boolean isEnabled;
	private Date  createDate;
	private String updatedBy;
	private Date updatedDate;
	private Supplier supplier;
	private Boolean isExternal;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@Transient
	private List<UserAccount> userAccount;

	
	private String statusFilter;


	private Integer supplierFilter;
	private Integer roleId;
	
	public User(){
		
	}
	
	public User(Long userId, String firstName, String lastName, String emailId,
			String mobilePhone, Boolean isEnabled, Date createDate,
			String updatedBy, Date updatedDate, Integer supplierId, String description, Integer roleId) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.mobilePhone = mobilePhone;
		this.isEnabled = isEnabled;
		this.createDate = createDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
		this.supplier=new Supplier(supplierId,description);
		this.roleId=roleId;
	}
	
	
	public User(Long userId, String firstName, String lastName, String emailId,
			String mobilePhone, Boolean isEnabled, Date createDate,
			String updatedBy, Date updatedDate, Integer supplierId, String description) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.mobilePhone = mobilePhone;
		this.isEnabled = isEnabled;
		this.createDate = createDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
		this.supplier=new Supplier(supplierId,description);
		
	}
	
	
	public User(Long userId, String firstName, String lastName, String emailId,
			String mobilePhone, Boolean isEnabled, Integer supplierId, String description) {
		super();
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.mobilePhone = mobilePhone;
		this.isEnabled = isEnabled;
		this.createDate = createDate;
		this.updatedBy = updatedBy;
		this.updatedDate = updatedDate;
		this.supplier=new Supplier(supplierId, description);
		this.roleId=roleId;
	}
	
	@Transient
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	@Transient
	public String getStatusFilter() {
		return statusFilter;
	}
	public void setStatusFilter(String statusFilter) {
		this.statusFilter = statusFilter;
	}
	@Transient
	public Integer getSupplierFilter() {
		return supplierFilter;
	}
	public void setSupplierFilter(Integer supplierFilter) {
		this.supplierFilter = supplierFilter;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid", unique = true, nullable = false)
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	@Column(name = "firstname",  nullable = false)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Column(name = "middlename")
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	@Column(name = "lastname",  nullable = false)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Column(name = "email",  nullable = false)
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	@Column(name = "mobilephone",  nullable = false)
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	
	@Column(name = "phone")
	public String getOtherPhone() {
		return otherPhone;
	}
	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
	}
	
	@Column(name = "enabled",  nullable = false)
	public Boolean isEnabled() {
		return isEnabled;
	}
	public void setEnabled(Boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
  @Column(name = "createdate")
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Column(name = "updatedby")
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	@Column(name = "updatedat")
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "supplierid",  nullable = false)
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	 @Column(name = "isexternal")
	public Boolean isExternal() {
		return false;
	}

	public void setExternal(Boolean isExternal) {
		this.isExternal = false;
	}


	@Override
	public String toString() {
		return "User [userId=" + userId + ", firstName=" + firstName
				+ ", middleName=" + middleName + ", lastName=" + lastName
				+ ", emailId=" + emailId + ", mobilePhone=" + mobilePhone
				+ ", otherPhone=" + otherPhone + ", isEnabled=" + isEnabled
				+ ", createDate=" + createDate + ", updatedBy=" + updatedBy
				+ ", updatedDate=" + updatedDate + ", supplier=" + supplier
				+ ", isExternal=" + isExternal + ", userAccount=" + userAccount
				+ ", statusFilter=" + statusFilter + ", supplierFilter="
				+ supplierFilter + ", roleId=" + roleId + "]";
	}


	
}

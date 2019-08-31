package com.wm.brta.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "[appsupportenablemodules]")
@JsonInclude(Include.NON_NULL)
public class AppSupportEnableModules {
	
	
	AppSupportEnableModules(){
		super();
	}

	public AppSupportEnableModules(String moduleName){
		super();
		
		this.moduleName=moduleName;
		
		
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

	@Column(name = "modulename", nullable = false)
	private String moduleName;
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	@Column(name = "enabled", nullable = false)
	private Boolean enabled;
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "AppSupportEnableModules [Id=" + Id + ", moduleName="
				+ moduleName + ", enabled=" + enabled + "]";
	}
	
	
	
}

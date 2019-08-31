package com.wm.brta.dto;

/**
 * @author nmehta
 *
 */
public class Commodity
{
	private Integer materialId;
	private String shortName;
	private Integer balesPicked;
	private Integer balesRemaining;
	private Integer oldMaterialId;
	private Boolean isDeleted;
	
	public Integer getMaterialId() {
		return materialId;
	}
	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}
	
	public Integer getOldMaterialId() {
		return oldMaterialId;
	}
	public void setOldMaterialId(Integer oldMaterialId) {
		this.oldMaterialId = oldMaterialId;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public Commodity(){
		
	}
	public Commodity(Integer materialId, String shortName, Integer balesPicked,
			Integer balesRemaining) {
		super();
		this.materialId = materialId;
		this.shortName = shortName;
		this.balesPicked = balesPicked;
		this.balesRemaining = balesRemaining;
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
	
	
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
	@Override
	public String toString() {
		return "Commodity [materialId=" + materialId + ", shortName="
				+ shortName + ", balesPicked=" + balesPicked
				+ ", balesRemaining=" + balesRemaining + ", oldMaterialId="
				+ oldMaterialId + ", isDeleted=" + isDeleted + "]";
	}
	
	

}

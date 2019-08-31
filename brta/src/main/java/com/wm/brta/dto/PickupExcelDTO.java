package com.wm.brta.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class PickupExcelDTO {

	private List<PickupDetailsFromExcel> picupExcelList;

	public List<PickupDetailsFromExcel> getPicupExcelList() {
		return picupExcelList;
	}

	public void setPicupExcelList(List<PickupDetailsFromExcel> picupExcelList) {
		this.picupExcelList = picupExcelList;
	}

	@Override
	public String toString() {
		return "PickupExcelDTO [picupExcelList=" + picupExcelList + "]";
	}
	
	
	
	
}
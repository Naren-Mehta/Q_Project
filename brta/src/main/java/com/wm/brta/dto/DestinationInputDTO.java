package com.wm.brta.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class DestinationInputDTO {
	
	private List<Integer> storeIdList;

	public List<Integer> getStoreIdList() {
		return storeIdList;
	}

	public void setStoreIdList(List<Integer> storeIdList) {
		this.storeIdList = storeIdList;
	}

	@Override
	public String toString() {
		return "DestinationInputDTO [storeIdList=" + storeIdList + "]";
	}
	
	
	
}
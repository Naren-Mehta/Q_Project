package com.wm.brta.dto;

import java.util.List;

import com.wm.brta.domain.BalePickupAssignment;

public class BalePickupAssignmentDTO {
	@Override
	public String toString() {
		return "BalePickupAssignmentDTO [assignments=" + assignments
				+ ", oldAssignments=" + oldAssignments + ", action=" + action
				+ "]";
	}
	List<BalePickupAssignment> assignments;
	List<BalePickupAssignment> oldAssignments;
	
	List<Integer>  customerSiteIdList;

	
	public List<BalePickupAssignment> getOldAssignments() {
		return oldAssignments;
	}
	public void setOldAssignments(List<BalePickupAssignment> oldAssignments) {
		this.oldAssignments = oldAssignments;
	}
	String action;
	
	
	public List<BalePickupAssignment> getAssignments() {
		return assignments;
	}
	public void setAssignments(List<BalePickupAssignment> assignments) {
		this.assignments = assignments;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public List<Integer> getCustomerSiteIdList() {
		return customerSiteIdList;
	}
	public void setCustomerSiteIdList(List<Integer> customerSiteIdList) {
		this.customerSiteIdList = customerSiteIdList;
	}
	
	
	
	
	
}

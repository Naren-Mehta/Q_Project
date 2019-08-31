package com.wm.brta.dto;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class UserExcelDTO {

	private List<UsersFromExcel> usersFromExcelList;

	public List<UsersFromExcel> getUsersFromExcelList() {
		return usersFromExcelList;
	}

	public void setUsersFromExcelList(List<UsersFromExcel> usersFromExcelList) {
		this.usersFromExcelList = usersFromExcelList;
	}

	
}
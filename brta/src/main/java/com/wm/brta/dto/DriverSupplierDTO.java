package com.wm.brta.dto;

import com.wm.brta.domain.Role;
import com.wm.brta.domain.User;

public class DriverSupplierDTO {
	
	private Role role;
	private User user;
	
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@Override
	public String toString() {
		return "DriverSupplierDTO [role=" + role + ", user=" + user + "]";
	}

	
	
}

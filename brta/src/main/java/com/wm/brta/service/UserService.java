package com.wm.brta.service;

import java.util.List;
import java.util.Map;

import com.wm.brta.domain.Role;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.User;
import com.wm.brta.dto.CheckUniqueUserDTO;
import com.wm.brta.dto.DriverSupplierDTO;
import com.wm.brta.dto.UserDTO;

public interface UserService {
	
	public List<User> getAllUsersWithFilters(User user);
	public List<User> getActiveUsers(Supplier supplierSelected);
	public List<User> getOnlyDrivers(Integer supplierId);
	public List<User> getAllUsersFromSupplierId(Integer supplierId);
	public Boolean checkMobileNoUnique(String mobileNo);
	public Boolean checkEmailIdUnique(String emailId);
	public User checkUniqueUser(CheckUniqueUserDTO checkUniqueUser);

	public User getUserObjectFromUserId(Long userId);
	public List<Role> getAllRoleFromUser(User user, String roleName);
	public Map add(DriverSupplierDTO driverSupplierDTO, UserDTO userDTO) throws Exception;


}

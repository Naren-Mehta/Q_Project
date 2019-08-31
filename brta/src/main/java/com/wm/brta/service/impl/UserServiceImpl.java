package com.wm.brta.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wm.brta.dao.UserDao;
import com.wm.brta.domain.IncidentType;
import com.wm.brta.domain.Role;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.User;
import com.wm.brta.dto.CheckUniqueUserDTO;
import com.wm.brta.dto.DriverSupplierDTO;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.ApplicationService;
import com.wm.brta.service.UserService;
import com.wm.brta.util.BaleUtils;
import com.wm.brta.utility.MailServiceUtility;

@Component("userService")
@Transactional
public class UserServiceImpl implements ApplicationService<User>, UserService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	UserDao userDao;

	@Override
	public Map add(DriverSupplierDTO driverSupplierDTO, UserDTO userDTO)
			throws Exception {
		User persistenceObject = driverSupplierDTO.getUser();
		Role role = driverSupplierDTO.getRole();

		persistenceObject.setCreateDate(new Date());
		persistenceObject.setUpdatedDate(new Date());
		persistenceObject.setUpdatedBy(userDTO.getFirstName());
		persistenceObject.setExternal(false);

		String msg = "fail";
		Boolean isValidUserDetails = false;
		String mobilePhone = persistenceObject.getMobilePhone();
		String email = persistenceObject.getEmailId().trim();

		if ((BaleUtils.isValidEmailAddress(email))
				&& (mobilePhone != null && mobilePhone.matches("[0-9]+") && mobilePhone
						.length() == 10)) {
			isValidUserDetails = true;
		}

		if (isValidUserDetails) {
			msg = userDao.save(persistenceObject, role);
			if (msg == "success") {
				try{
					if(role.getRoleDescription().equals("Driver")){
						MailServiceUtility.sendMail(persistenceObject);
					}
				}catch(Exception e){
					LOGGER.error("====Exception while sending Email======");
					
				}
			}
			
		}
		Map map = new HashMap();
		map.put("status", msg);
		return map;
	}

	@Override
	public LinkedList<User> delete(User object) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> edit(User user) throws Exception {
		user.setUpdatedDate(new Date());

		long id = 0;
		List<User> users = new ArrayList<User>();
		
		try {
			Boolean status = userDao.updateUserRole(user.getUserId(),
					user.getRoleId());

			if (status) {
				userDao.merge(user);
			}
			//users = getAllUsersWithFilters(user);
		} catch (Exception e) {
			LOGGER.error("===exception edit user====");
		}

		return users;
	}

	@Override
	public List<User> getAllUsersWithFilters(User user) {
		List<User> users = userDao.getAllUsers(user);
		return users;
	}

	@Override
	public List<User> getAll(Supplier supplier) throws Exception {
		List<User> users = userDao.getAllUsers(supplier);

		return users;
	}

	@Override
	public List<User> getOnlyDrivers(Integer supplierId) {
		List<User> users = userDao.getOnlyDrivers(supplierId);

		return users;
	}

	@Override
	public List<IncidentType> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getActiveUsers(Supplier supplierSelected) {
		List<User> users = userDao.getAllActiveUsers(supplierSelected);
		return users;
	}

	@Override
	public Boolean checkMobileNoUnique(String mobileNo) {

		Boolean status = false;
		status = userDao.checkMobileNoUnique(mobileNo);

		return status;
	}

	@Override
	public Boolean checkEmailIdUnique(String emailId) {

		Boolean status = false;
		status = userDao.checkEmailIdUnique(emailId);

		return status;
	}

	@Override
	public List<User> add(User persistenceObject, UserDTO userDto)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> getAllUsersFromSupplierId(Integer supplierId) {
		// TODO Auto-generated method stub
		List<User> users = userDao.getAllUsersFromSupplierId(supplierId);
		return users;
	}

	@Override
	public User checkUniqueUser(CheckUniqueUserDTO checkUniqueUserDTO) {

		User users = null;
		users = userDao.checkUniqueUser(checkUniqueUserDTO);

		return users;
	}

	@Override
	public User getUserObjectFromUserId(Long userId) {
		User user = userDao.getUserById(userId);
		return user;
	}

	@Override
	public List<Role> getAllRoleFromUser(User user, String roleName) {

		List<Role> roles = userDao.getAllRoleFromUser(user, roleName);
		return roles;
	}

}

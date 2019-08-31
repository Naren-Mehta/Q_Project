package com.wm.brta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wm.brta.constants.ApplicationCommonConstants;
import com.wm.brta.dao.UserDao;
import com.wm.brta.domain.Role;
import com.wm.brta.domain.User;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.LoginService;

@Component(value = "supplierLoginService")
@Transactional
public class SupplierLoginServiceImpl implements LoginService {

	@Autowired
	UserDao userDao;

	@Autowired
	UserDTO userDTO;

	@Override
	public UserDTO authenticate(User inputUser) throws Exception {

		
	//	System.out.println("==authenticate supplier service====");
		List<User> users = userDao.getUserByEmailAndPhone(inputUser);

		if(users!=null && users.size() >0){
			
			if(users.size() == 1){
				User user = users.get(0);
				List<Role> roles = userDao.getAllRoleFromUser(user, "Supplier");
				Boolean roalMatched = false;
				

				if (roles != null && roles.size() > 0) {
					roalMatched = true;
				}

				if (roalMatched) {
					userDTO.setUserId(user.getUserId());
					userDTO.setFirstName(user.getFirstName());
					userDTO.setAuthenticationErrorFlag(false);
					userDTO.setLastName(user.getLastName());
					userDTO.setMobilePhone(user.getMobilePhone());
					userDTO.setEmailId(user.getEmailId());
					userDTO.setRole(roles.get(0).getRoleDescription());

					userDTO.setMessage(ApplicationCommonConstants.SUPPLIER_AUTHENTICATION_SUCCESSFULL_MESSAGE);
				} else {
					userDTO.setFirstName(null);
					userDTO.setLastName(null);
					userDTO.setEmailId(inputUser.getEmailId());
					userDTO.setMobilePhone(inputUser.getMobilePhone());
					userDTO.setAuthenticationErrorFlag(true);
					userDTO.setMessage(ApplicationCommonConstants.SUPPLIER_AUTHENTICATION_UNSUCCESSFULL_MESSAGE);
				}
			}else{
				userDTO.setFirstName(null);
				userDTO.setLastName(null);
				userDTO.setEmailId(inputUser.getEmailId());
				userDTO.setMobilePhone(inputUser.getMobilePhone());
				userDTO.setAuthenticationErrorFlag(true);
				userDTO.setMessage(ApplicationCommonConstants.MULTIPLE_DRIVER_FOUND_MESSAGE);
			}
			

			return userDTO;
		}else{
			userDTO.setFirstName(null);
			userDTO.setLastName(null);
			userDTO.setEmailId(inputUser.getEmailId());
			userDTO.setMobilePhone(inputUser.getMobilePhone());
			userDTO.setAuthenticationErrorFlag(true);
			userDTO.setMessage(ApplicationCommonConstants.SUPPLIER_AUTHENTICATION_UNSUCCESSFULL_MESSAGE);
			return userDTO;
		}
	}

}

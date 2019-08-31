package com.wm.brta.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.User;
import com.wm.brta.dto.CheckUniqueUserDTO;
import com.wm.brta.dto.DriverSupplierDTO;
import com.wm.brta.dto.OutputResponse;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.dto.UserExcelDTO;
import com.wm.brta.dto.UsersFromExcel;
import com.wm.brta.service.ApplicationService;
import com.wm.brta.service.UserService;

@RestController
public class UserMgmtController {

	@Autowired
	ApplicationService userService;

	@Autowired
	UserService userServiceUtility;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UserMgmtController.class);

	@RequestMapping(value = "/ui/userMgmt/add/user", method = RequestMethod.POST)
    public @ResponseBody Map addUser(@RequestBody DriverSupplierDTO driverSupplierDTO,HttpServletRequest request) {
		
		Map map=new HashMap();
		HttpSession session = request.getSession();
		UserDTO userDto = (UserDTO)session.getAttribute("user");
		User user=driverSupplierDTO.getUser();
		//Role role=driverSupplierDTO.getRole();

		if (userDto!=null){
			user.setUpdatedBy(userDto.getFirstName());	
			  try {
					  map = userServiceUtility.add(driverSupplierDTO,userDto);

				} catch (Exception e) {
					LOGGER.error("Error:UserMgmtController:add(user,userDto)=====>" +e);				
				}
		}

		return map;
	}

	@RequestMapping(value = "/ui/userMgmt/edit/user", method = RequestMethod.POST)
	public @ResponseBody List<User> editUser(@RequestBody User user,
			HttpServletRequest request) {
		List<User> users = null;
		HttpSession session = request.getSession();
		UserDTO userDto = (UserDTO) session.getAttribute("user");
		if (userDto != null)
			user.setUpdatedBy(userDto.getFirstName());
		try {
			users = userService.edit(user);
		} catch (Exception e) {
			LOGGER.error("Error:UserMgmtController:edit(user)=====>" + e);
		}

		return users;
	}

	@RequestMapping(value = "/ui/userMgmt/get/user/byfilters", method = RequestMethod.POST)
	public @ResponseBody List<User> getAllUsers(@RequestBody User user) {
		List<User> users = null;
		try {
			users = userServiceUtility.getAllUsersWithFilters(user);
		} catch (Exception e) {
			LOGGER.error("Error:UserMgmtController:getAllUsersWithFilters===1==>"
					+ e);
		}
		return users;
	}

	@RequestMapping(value = "/ui/userMgmt/get/user/all", method = RequestMethod.POST)
	public @ResponseBody List<User> getAllUsers(
			@RequestBody Supplier supplierSelected) {
		List<User> users = null;

		try {
			users = userService.getAll(supplierSelected);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:UserMgmtController:getAll(supplierSelected)"
					+ e);
		}
		return users;
	}
	
	@RequestMapping(value = "/ui/userMgmt/get/driverOnly", method = RequestMethod.POST)
	public @ResponseBody List<User> getOnlyDrivers(
			@RequestBody Integer supplierId) {
		List<User> users = null;

		try {
			users = userServiceUtility.getOnlyDrivers(supplierId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:UserMgmtController:getAll(supplierSelected)"
					+ e);
		}
		return users;
	}
	
	
	//by deepak
	
	@RequestMapping(value = "/ui/userMgmt/get/user/supplierusers", method = RequestMethod.POST)
	public @ResponseBody List<User> getAllUsersFromSupplierId(@RequestBody Integer supplierIdList) {
		List<User> users = null;

		try {
			users = userServiceUtility.getAllUsersFromSupplierId(supplierIdList);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:UserMgmtController:getAll(supplierSelected)"
					+ e);
		}
		return users;
	}
	

	@RequestMapping(value = "/ui/userMgmt/get/user/active", method = RequestMethod.POST)
	public @ResponseBody List<User> getAllActiveUsers(
			@RequestBody Supplier supplierSelected) {
		List<User> users = null;

		try {
			users = userServiceUtility.getActiveUsers(supplierSelected);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:UserMgmtController:getActiveUsers=====>" + e);
		}
		return users;
	}

	@RequestMapping(value = "/ui/userMgmt/check/mobileNumber", method = RequestMethod.POST)
	public @ResponseBody Map checkMobileNumberUniqueOrNot(
			@RequestBody String mobileNo) {

		Map result = new HashMap();
		Boolean status = false;
		try {
			status = userServiceUtility.checkMobileNoUnique(mobileNo);
			result.put("status", status);
		} catch (Exception e) {
			LOGGER.error("Error:UserMgmtController:checkMobileNoUnique=====>"
					+ e);
		}
		return result;
	}

	@RequestMapping(value = "/ui/userMgmt/check/emailIdUnique", method = RequestMethod.POST)
	public @ResponseBody Map checkEmailIdUniqueOrNot(@RequestBody String emailId) {

		Map result = new HashMap();
		Boolean status = false;
		try {

			status = userServiceUtility.checkEmailIdUnique(emailId);

			result.put("status", status);
		} catch (Exception e) {
			LOGGER.error("Error:UserMgmtController:checkEmailIdUnique=====>"
					+ e);
		}

		return result;
	}

	@RequestMapping(value = "/ui/userMgmt/get/user/byid", method = RequestMethod.POST)
	public String getUserById(@RequestBody User user) {
		return "";
	}

	@RequestMapping(value = "/ui/userMgmt/update/user/byid", method = RequestMethod.POST)
	public String editUser() {
		return "";
	}

	@RequestMapping(value = "/ui/userMgmt/upload/usersFromExcel", method = RequestMethod.POST)
	@ResponseBody
	public OutputResponse uploadBalePickUpsFromExcel(
			@RequestBody UserExcelDTO userExcelDTO, HttpServletRequest request) {

		HttpSession session = request.getSession();

		OutputResponse response = new OutputResponse();

		List<UsersFromExcel> usersFromExcelList = userExcelDTO
				.getUsersFromExcelList();

		Integer count = 0;
		UserDTO userDto = (UserDTO) session.getAttribute("user");

		for (UsersFromExcel userFromExcel : usersFromExcelList) {
			try {
				User user = new User();
				user.setFirstName(userFromExcel.getFirstName());
				user.setLastName(userFromExcel.getLastName());
				user.setEmailId(userFromExcel.getEmailId());
				user.setMobilePhone(userFromExcel.getMobilePhone());
				user.setSupplier(userFromExcel.getSupplier());
				user.setEnabled(true);
				if (userDto != null) {
					user.setUpdatedBy(userDto.getFirstName());
				}
				List<User> users = userService.add(user, userDto);
				count++;
			} catch (Exception e) {
				LOGGER.error("===exception while createing user====" + e);
			}
		}

		String message = count + " users uploaded ";
		response.setMessage(message);
		return response;
	}
	
	@RequestMapping(value = "/ui/userMgmt/check/checkuniqueUser", method = RequestMethod.POST)
	public @ResponseBody User  checkUniqueUser(@RequestBody CheckUniqueUserDTO checkUniqueUser) {

		
	
		User users = null;
		try {
			users = userServiceUtility.checkUniqueUser(checkUniqueUser);
			
		} catch (Exception e) {
			LOGGER.error("Error:UserMgmtController:checkUniqueUser=====>"
					+ e);
		}

		return users;
	}

}

package com.wm.brta.controller.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wm.brta.domain.User;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.LoginService;
import com.wm.brta.service.SupplierService;
import com.wm.brta.util.BaleUtils;
import com.wm.brta.utility.UserDetailsUtility;

@Controller
public class SupplierController {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LoginController.class);

	@Autowired
	private UserDetailsUtility userDetails;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	@Qualifier(value = "supplierLoginService")
	private LoginService supplierLoginService;
	@RequestMapping(value = "/supplier/authenticate", method = RequestMethod.POST)
	public ResponseEntity<UserDTO> authenticateDriver(
			HttpServletRequest request, @RequestBody User user) {


		UserDTO userDTO = null;
		try {
			userDTO = supplierLoginService.authenticate(user);

			Integer supplierId = supplierService.getSupplierIdFromUser(userDTO
					.getUserId());
			HttpSession session = request.getSession();
			Double versionNo =BaleUtils.versionNo;
			session.setAttribute("versionNo", versionNo);
			session.setAttribute("user", userDTO);
			session.setAttribute("sm_groups", userDTO.getRole());
			session.setAttribute("role", userDTO.getRole());
			session.setAttribute("supplierId", supplierId);


		} catch (Exception e) {
			LOGGER.error("==authenticateDriver==="+e);
		}
		if (userDTO.isAuthenticationErrorFlag()) {
			return new ResponseEntity<UserDTO>(userDTO,HttpStatus.OK);
		} else{
			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
		}
	}

}

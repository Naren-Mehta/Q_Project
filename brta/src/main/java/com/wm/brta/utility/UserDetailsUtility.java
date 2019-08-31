package com.wm.brta.utility;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wm.brta.dto.UserDTO;
import com.wm.brta.util.XSSUtils;

@Component
public class UserDetailsUtility {

	@Autowired
	private UserDTO userDto;

	public UserDTO getUserDetails(HttpServletRequest request) throws Exception {

		
		String firstName =request.getHeader("sm_fname");
		String lastName = request.getHeader("sm_lname");
		String sm_groups = request.getHeader("sm_groups");
		
		if (firstName != null && !firstName.isEmpty()) {
			firstName = XSSUtils.stripXSS(request.getHeader("sm_fname").replace('\n', ' ').trim());
		}

		if (lastName != null && !lastName.isEmpty()) {
			lastName = XSSUtils.stripXSS(request.getHeader("sm_lname").replace('\n', ' ').trim());
		} 
		
		String role = null;
		
		if (sm_groups != null && !sm_groups.isEmpty()) {
			role = extractRole(request.getHeader("sm_groups")
					.replace('\n', ' ').trim());
			role=XSSUtils.stripXSS(role);
		}
		
		
		UserDTO newUserDto=new UserDTO();
		newUserDto.setFirstName(firstName);
		newUserDto.setLastName(lastName);
		newUserDto.setRole(role);
		return newUserDto;

	}

	private static String extractRole(String smGroup) {
		String[] roleFields = smGroup.split(",");

		String roleReturned = null;
		outerloop: for (String roles : roleFields) {

			String[] tempArray = roles.split("=");
			for (String role : tempArray) {

				if (role.contains("ROBRCSR") || role.contains("ROBRMANAGER")) {
					roleReturned = role;
					break outerloop;
				}
			}
		}

		return roleReturned;
	}

	/*
	 * public static void main(String[] args) {
	 * 
	 * extractRole("cn=cn,ou=ROBRMANAGER,ou=roles,ou=app"); }
	 */

}

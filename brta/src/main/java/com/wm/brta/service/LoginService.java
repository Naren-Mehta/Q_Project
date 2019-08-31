package com.wm.brta.service;

import com.wm.brta.domain.User;
import com.wm.brta.dto.UserDTO;

public interface LoginService {
	
	public UserDTO authenticate(User user) throws Exception;
	

}

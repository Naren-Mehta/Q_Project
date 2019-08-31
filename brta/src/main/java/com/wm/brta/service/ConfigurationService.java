package com.wm.brta.service;

import com.wm.brta.dto.ConfigurationDataDTO;
import com.wm.brta.dto.UserDTO;

public interface ConfigurationService {

	public String addSupplierMaterialConfigData(ConfigurationDataDTO configurationDataDTO,  UserDTO user);

}

package com.wm.brta.controller.web;

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
import com.wm.brta.dto.ConfigurationDataDTO;
import com.wm.brta.dto.OutputResponse;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.ConfigurationService;


@RestController
public class ConfigurationDataController 
{
	
	@Autowired
	ConfigurationService configurationService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationDataController.class);

	@RequestMapping(value="/ui/common/add/suppliermaterialconfiguration", method=RequestMethod.POST)
	public @ResponseBody OutputResponse addConfigurationData(@RequestBody ConfigurationDataDTO configDTO, HttpServletRequest request){
		
		OutputResponse response = new OutputResponse();
		String status = null;
		HttpSession session = request.getSession();
		UserDTO userDto = (UserDTO)session.getAttribute("user");
		try{
		
		status = configurationService.addSupplierMaterialConfigData(configDTO,userDto);
		}catch(Exception e){
			LOGGER.error("Error:ConfigurationDataController:addSupplierMaterialConfigData=====>" +e);
		}
		return response;
	}

}

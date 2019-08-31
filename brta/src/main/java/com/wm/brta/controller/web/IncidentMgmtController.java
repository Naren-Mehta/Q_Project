package com.wm.brta.controller.web;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import com.wm.brta.domain.IncidentType;
import com.wm.brta.dto.Destination;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.ApplicationService;
import com.wm.brta.service.CustomerService;
import com.wm.brta.service.IncidentTypeService;


@RestController
public class IncidentMgmtController {
	
	@Autowired
	ApplicationService incidentService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	IncidentTypeService incidentTypeService;
	

	
	private static final Logger LOGGER = LoggerFactory.getLogger(IncidentMgmtController.class);
  
	
	@RequestMapping(value = "/ui/incidentMgmt/add/incidenttype", method = RequestMethod.POST)
    public @ResponseBody List<IncidentType> addUser(@RequestBody IncidentType incident,HttpServletRequest request) {
		
		List<IncidentType>incidents = null;
		HttpSession session = request.getSession();
		UserDTO userDto = (UserDTO)session.getAttribute("user");
		if (userDto!=null)
			incident.setUpdatedBy(userDto.getFirstName());	
        try {
        	incidents = incidentService.add(incident,userDto);
		} catch (Exception e) {
			
			LOGGER.error("Error:IncidentMgmtController:add(incident,userDto)=====>" +e);
		}
        return incidents;
    }
	
	
	@RequestMapping(value = "/ui/incidentMgmt/edit/incidenttype", method = RequestMethod.POST)
    public @ResponseBody List<IncidentType> editUser(@RequestBody IncidentType incident,HttpServletRequest request) {
		
		
		
		
		List<IncidentType>incidents = null;
		HttpSession session = request.getSession();
		UserDTO userDto = (UserDTO)session.getAttribute("user");
		if (userDto!=null)
			incident.setUpdatedBy(userDto.getFirstName());	
        try {
        	incidents = incidentService.edit(incident);
		} catch (Exception e) {
			LOGGER.error("Error:IncidentMgmtController:edit(incident)=====>" +e);
		}
        return incidents;
    }
	
    
    @RequestMapping(value="/ui/common/get/incidenttype/all",method=RequestMethod.GET)
    public @ResponseBody List<IncidentType> getAllIncidentTypes() {
    	List<IncidentType>incidentTypes = null;
		
			
        try {
        	incidentTypes = incidentService.getAll();
		} catch (Exception e) {
			LOGGER.error("Error:IncidentMgmtController:getAllIncidentTypes=====>" +e);
		}
        return incidentTypes;
    }
    
    
    //by deepak
    
   
    
    
    @RequestMapping(value="/ui/common/get/incidentTypeFromStatus",method=RequestMethod.POST)
    public @ResponseBody List<IncidentType> getAllIncidentTypeFromStatus(@RequestBody String status) {
    	List<IncidentType>incidentTypes = null;
		
			
        try {
        	incidentTypes = incidentTypeService.getAllIncidentTypeFromStatus(status);
		} catch (Exception e) {
			LOGGER.error("Error:IncidentMgmtController:getAllIncidentTypes=====>" +e);
		}
        return incidentTypes;
    }
    
   
    
    @RequestMapping(value="/ui/incidentMgmt/check/incidenttype",method=RequestMethod.POST)
    public @ResponseBody Map checkIncidenttypeUniqueOrNot(@RequestBody String incidenttype) {


    	Map result = new HashMap();
    	Boolean status= false;
    	try{
    	status=incidentTypeService.checkIncidenttypeUniqueOrNot(incidenttype);
        
    	result.put("status",status);
    	}catch(Exception e){
    		LOGGER.error("Error:IncidentMgmtController:checkIncidenttypeUniqueOrNot=====>" +e);
    	}
        return result;
    }
    
    @RequestMapping(value="/ui/incidentMgmt/get/incidentType",method=RequestMethod.POST)
	public @ResponseBody HashSet<Destination> getIncidentTypeOfStore(@RequestBody String storeName){
		
		HashSet<Destination> destinations = null;
		
		
        try {
        	destinations = customerService.getAllDestination(storeName);
        	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:IncidentMgmtController:getAllDestination=====>" +e);		}
        return destinations;
	}
	
    @RequestMapping(value="/ui/incidentMgmt/check/checkIncidentbyNameStatus",method=RequestMethod.POST)
  	public @ResponseBody Map getIncidentbyNameStatus(@RequestBody IncidentType incident){
  
  		
    	Map result = new HashMap();
    	Boolean status= false;
  		
  		
    	try{
        	status=incidentTypeService.getIncidentbyNameStatus(incident);
            
        	result.put("status",status);
        	}catch(Exception e){
        		LOGGER.error("Error:IncidentMgmtController:checkIncidenttypeUniqueOrNot=====>" +e);
        	}
            return result;
        }
	
     
    
}

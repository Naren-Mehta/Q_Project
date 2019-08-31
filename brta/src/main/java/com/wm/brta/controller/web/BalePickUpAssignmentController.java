package com.wm.brta.controller.web;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wm.brta.dao.AssignDriverDTO;
import com.wm.brta.dao.GenerateAssignmentDriverPopup;
import com.wm.brta.domain.BalePickupAssignment;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.DriverSupplierPickupsView;
import com.wm.brta.domain.StoreSupplierListView;
import com.wm.brta.dto.ActivityListInputPayload;
import com.wm.brta.dto.AssignmentFilterDTO;
import com.wm.brta.dto.BalePickupAssignmentDTO;
import com.wm.brta.dto.Destination;
import com.wm.brta.dto.OutputResponse;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.BalePickupService;

@Controller
public class BalePickUpAssignmentController 
{
	
	@Autowired
	BalePickupService balePickupService;
	private static final Logger LOGGER = LoggerFactory.getLogger(BalePickUpAssignmentController.class);

	
	@RequestMapping(value="/ui/assignMgmt/pickups",method = RequestMethod.POST)
	@ResponseBody
	public HashSet<Destination> getAllDestinationsForWeb(@RequestBody ActivityListInputPayload activitylistPayLoad){
		
		HashSet<Destination>destinationsToReturn = null;
		try{
		
		destinationsToReturn= balePickupService.getAllDestinations(activitylistPayLoad);
		}catch(Exception e){
			LOGGER.error("Error:BalePickUpAssignmentController:getAllDestinations=====>"+ e);
		}
		
		return destinationsToReturn;
		
		
	}
	
	
	@RequestMapping(value="/ui/assignMgmt/add/pickupassignments",method = RequestMethod.POST)
	@ResponseBody
	public OutputResponse addPickuoAssignments(@RequestBody BalePickupAssignmentDTO assignmentDTO,HttpServletRequest request){
		OutputResponse response = new OutputResponse();
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO)session.getAttribute("user");
		try{
		 balePickupService.addBalePickupAssignments(assignmentDTO,user);
		}catch(Exception e){
			LOGGER.error("Error:BalePickUpAssignmentController:addBalePickupAssignments=====>"+ e);
		}
		
		return response;
		
		
	}
	
	
	@RequestMapping(value="/ui/assignMgmt/getAll/pickupassignments",method = RequestMethod.POST)
	@ResponseBody
	
	public Set<BalePickupAssignment> getAllPickupAssignments(@RequestBody AssignmentFilterDTO assignmentFilter){
		
	Set<BalePickupAssignment> assignments=null;	 
    try{
		assignments = balePickupService.getPickupAssignments(assignmentFilter);
    }catch(Exception e){
    	LOGGER.error("Error:BalePickUpAssignmentController:getPickupAssignments=====>" +e);
    }
		return assignments;
	}
	
	
	/*by deepak for store supplier list*/
	
@RequestMapping(value="/ui/assignMgmt/getAll/storeSupplierList",method = RequestMethod.POST)
	@ResponseBody
	public List<StoreSupplierListView> getStoreSupplierList(@RequestBody AssignDriverDTO assignDriverDTO){
		
		
		
	List<StoreSupplierListView> assignments=null;	
    try{
		assignments = balePickupService.getSupplierStoreList(assignDriverDTO);
		
    }catch(Exception e){
    	LOGGER.error("Error:BalePickUpAssignmentController:storeSupplierList=====>" +e);
    }
		return assignments;
	}
	
	
	
	
	
	/*End */
	
	
	
	///get/assignments/bycustomerSite
	
@RequestMapping(value="/ui/assignMgmt/get/assignments/bycustomerSite",method = RequestMethod.POST)
	@ResponseBody
	
	public Set<BalePickupAssignment> getAllPickupAssignmentsByCustomerSite(@RequestBody CustomerSite customerSite){
		
	Set<BalePickupAssignment> assignments=null;	 
	
    try{
		assignments = balePickupService.getPickupAssignmentsForCustomerSite(customerSite);
    }catch(Exception e){
    	LOGGER.error("Error:BalePickUpAssignmentController:getPickupAssignmentsForCustomerSite=====>" +e);
    }
		
		return assignments;
		
	
	}
	
	///update/pickupassignments
	
	@RequestMapping(value="/ui/assignMgmt/update/pickupassignments",method = RequestMethod.POST)
	@ResponseBody
	public OutputResponse updatePickupAssignmentstoAssignActivity(@RequestBody List<BalePickupAssignment> assignments,HttpServletRequest request){
		
		OutputResponse response = new OutputResponse();
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO)session.getAttribute("user");
		 balePickupService.updateBalePickupAssignmentstoAssignActivity(assignments,user);
		
		return response;
		
		
	}
	
	//by deepak for driver assign popup 
	
	@RequestMapping(value="/ui/assignMgmt/update/drivers",method = RequestMethod.POST)
	@ResponseBody
	public OutputResponse update(@RequestBody List<GenerateAssignmentDriverPopup> generateAssignmentDriverPopupList,HttpServletRequest request){
	
		
		OutputResponse response = new OutputResponse();
		
		for(GenerateAssignmentDriverPopup generateAssignmentDriverPopup:generateAssignmentDriverPopupList){
			Boolean status = balePickupService.updateStoreSupplierDriver(generateAssignmentDriverPopup);
		
			if(status){
			
				balePickupService.updateAssignmentDates(generateAssignmentDriverPopup);
			}
		}
		 
		return response;
		
		
	}
	
	/*end*/
	
	/* by deepakdriver dashboard*/
	
	@RequestMapping(value="/ui/assignMgmt/display/driversDashboard",method = RequestMethod.POST)
	@ResponseBody
	public List<DriverSupplierPickupsView> update(@RequestBody AssignDriverDTO assignDriverDTO,HttpServletRequest request){
		
	
		List <DriverSupplierPickupsView> driverSupplierPickupsView=null;
	try{	
		driverSupplierPickupsView = balePickupService.showDriverDashboard(assignDriverDTO);
	}catch(Exception e){
		LOGGER.error("Exception inside driversDashboard"+e);
	}
		return driverSupplierPickupsView;
		
		
	}
	
	//end getAssignedDriver
	
	
	@RequestMapping(value="/ui/assignMgmt/add/newdriver",method = RequestMethod.POST)
	@ResponseBody
	public OutputResponse assignNewDriver(@RequestBody BalePickupAssignment assignment){
		OutputResponse response = new OutputResponse();
		
		try{
			String status = balePickupService.assignNewDriver(assignment);
			response.setMessage(status);
		}
		catch(Exception e){
			LOGGER.error("Error:BalePickUpAssignmentController:assignNewDriver=====>" +e);
		}
		
		return response;
	}
	
	@RequestMapping(value="/ui/assignMgmt/remove/newdriver",method = RequestMethod.POST)
	@ResponseBody
	public OutputResponse removeNewDriver(@RequestBody BalePickupAssignment assignment){
		OutputResponse response = new OutputResponse();
		
		try{
			String status = balePickupService.removeNewDriver(assignment);
			response.setMessage(status);
		}
		catch(Exception e){
			LOGGER.error("Error:BalePickUpAssignmentController:removeNewDriver=====>" +e);
		}
		
		return response;
	}
	

}
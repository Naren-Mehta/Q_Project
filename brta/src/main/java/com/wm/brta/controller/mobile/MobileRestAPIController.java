package com.wm.brta.controller.mobile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wm.brta.dao.BalePickupTripDAO;
import com.wm.brta.dao.ServiceChannelDAO;
import com.wm.brta.domain.User;
import com.wm.brta.dto.ActivityListInputPayload;
import com.wm.brta.dto.BalePickupMobileDetailsForSC;
import com.wm.brta.dto.Commodity;
import com.wm.brta.dto.CompleteTripInputPayLoad;
import com.wm.brta.dto.Destination;
import com.wm.brta.dto.DestinationInputDTO;
import com.wm.brta.dto.OnCallInputPayload;
import com.wm.brta.dto.OutputResponse;
import com.wm.brta.dto.PickupDetails;
import com.wm.brta.dto.PickupDetailsWithMasterSets;
import com.wm.brta.dto.SupplierPickupDetailsMobileDTO;
import com.wm.brta.dto.SupplierPickupsMobileDTO;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.BalePickupService;
import com.wm.brta.service.BaleRouteTripService;
import com.wm.brta.service.LoginService;
import com.wm.brta.service.ServiceChannelService;

@RestController
public class MobileRestAPIController {

	@Autowired
	@Qualifier(value = "driverLoginService")
	private LoginService driverLoginService;

	@Autowired
	BaleRouteTripService baleRouteTripService;

	@Autowired
	BalePickupService balePickupService;
	
	@Autowired
	ServiceChannelDAO serviceChannelDAO;
	
	@Autowired
	ServiceChannelService serviceChannelService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MobileRestAPIController.class);

	@RequestMapping(value = "/service/authenticate/driver", method = RequestMethod.POST)
	public ResponseEntity<UserDTO> authenticateDriver(@RequestBody User user) {

		UserDTO userDTO = null;
		try {
			userDTO = driverLoginService.authenticate(user);

		} catch (Exception e) {
			LOGGER.error("Error in authenticateDriver "+e);
		}
		if (userDTO.isAuthenticationErrorFlag()) {
			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.UNAUTHORIZED);
		} else
			return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
	}

	// test this
	@RequestMapping(value = "/service/search/pickups", method = RequestMethod.POST)
	@ResponseBody
	public List<PickupDetails> getAllPickupsForSupplier(
			@RequestBody SupplierPickupsMobileDTO supplierPickupsMobileDTO) {
		
	

		List<PickupDetails> destinationsToReturn = balePickupService
				.getAllPickUpsFromSupplier(supplierPickupsMobileDTO);

		return destinationsToReturn;

	}

	@RequestMapping(value = "/service/search/top10pickups", method = RequestMethod.POST)
	@ResponseBody
	public List<PickupDetails> getTopTenPickupsForSupplier(
			@RequestBody SupplierPickupsMobileDTO supplierPickupsMobileDTO) {

		
	
		supplierPickupsMobileDTO.setOnly10RecordFlag(true);

		if (supplierPickupsMobileDTO.getDriverPickupOffset() == null) {
			supplierPickupsMobileDTO.setDriverPickupOffset(0);
		}

		List<PickupDetails> destinationsToReturn = balePickupService
				.getAllPickUpsFromSupplier(supplierPickupsMobileDTO);

		return destinationsToReturn;

	}

	@RequestMapping(value = "/service/pickups", method = RequestMethod.POST)
	@ResponseBody
	public List<PickupDetails> getAllSavedPickups(
			@RequestBody SupplierPickupsMobileDTO supplierPickupsMobileDTO) {

		List<PickupDetails> destinationsToReturn = balePickupService
				.getAllSavedPickUps(supplierPickupsMobileDTO);

		return destinationsToReturn;

	}

	@RequestMapping(value = "/service/pickupdetails", method = RequestMethod.POST)
	@ResponseBody
	public PickupDetailsWithMasterSets getPickupDetailsForSupplier(
			@RequestBody SupplierPickupDetailsMobileDTO supplierPickupsMobileDTO) {

		PickupDetailsWithMasterSets pickupDetailsWithMasterSets = new PickupDetailsWithMasterSets();

		pickupDetailsWithMasterSets = balePickupService
				.getPickupDetailsForSupplier(supplierPickupsMobileDTO);

		return pickupDetailsWithMasterSets;

	}

	@RequestMapping(value = "/service/onCallPickups", method = RequestMethod.POST)
	@ResponseBody
	public HashSet<Destination> getOnCallPickups(
			@RequestBody OnCallInputPayload inputPayLoad) {
		HashSet<Destination> destinations = new HashSet<Destination>();

		destinations = balePickupService.getAllPickupsForOnCall(inputPayLoad);

		return destinations;

	}

	@RequestMapping(value = "/service/onCallPickupsNew", method = RequestMethod.POST)
	@ResponseBody
	public List<PickupDetails> getOnCallPickupsNew(
			@RequestBody OnCallInputPayload inputPayLoad) {
		List<PickupDetails> destinations = new ArrayList<PickupDetails>();

		destinations = balePickupService
				.getAllPickupsForOnCallNew(inputPayLoad);

		return destinations;

	}

	@RequestMapping(value = "service/add/newtripdetails", method = RequestMethod.POST)
	public OutputResponse addTripDetailsNew(@RequestBody PickupDetails pickupDetails) {

		OutputResponse response = new OutputResponse();
		String status = "";
		try {

			Integer id = baleRouteTripService.addTripDetailsNew(pickupDetails);
		
			if (id == 0) {

				status = "Insertion Failure";

			} else {
				status = "Successfull";
			}
			response.setMessage(status);
		} catch (Exception e) {
			status = "Insertion Failure";
			response.setMessage(status);
			
		}

		return response;
	}

	@RequestMapping(value = "service/add/savecheckincheckoutdates", method = RequestMethod.POST)
	public OutputResponse saveCheckInCheckOut(@RequestBody BalePickupMobileDetailsForSC balePickupMobileDetailsForSC) {
		
		OutputResponse response = new OutputResponse();
		String status = "";
		String ESBstatus =null;
		Integer deletedCommoditiy=-1;
		Integer id=0;
		
		try {
			
			List<Commodity> materialList = balePickupMobileDetailsForSC.getMaterialList();
			if(materialList!=null){
				for (Commodity material : materialList) {
					if(material.getIsDeleted() !=null && material.getIsDeleted()==true){
						
						balePickupMobileDetailsForSC.setMaterial(material.getMaterialId());
						deletedCommoditiy = serviceChannelService.deleteCheckInCheckOut(balePickupMobileDetailsForSC);
						
					}else{
						
						if(material.getOldMaterialId() !=null){
							balePickupMobileDetailsForSC.setOldMaterialId(material.getOldMaterialId());
						}
						balePickupMobileDetailsForSC.setMaterial(material.getMaterialId());
						balePickupMobileDetailsForSC.setBalesPicked(material.getBalesPicked());
						balePickupMobileDetailsForSC.setBalesRemaining(material.getBalesRemaining());
						id = baleRouteTripService.saveCheckInCheckOut(balePickupMobileDetailsForSC);
						
					}
				}

			}else{
				
				id = baleRouteTripService.saveCheckInCheckOut(balePickupMobileDetailsForSC);
				
			}
			
			if (id == 0) {
				status = "Failed";
			} else {
				
				if(deletedCommoditiy==-1){
				ESBstatus = serviceChannelService.getSavedWorkorderForServiceChannel(balePickupMobileDetailsForSC,id);
				
				}
			}
			response.setMessage(status);
		} catch (Exception e) {
			status = "Insertion Failure";
			response.setMessage(status);
			
		}
		
		return response;
	}

	@RequestMapping(value = "/service/get/balescountforsc", method = RequestMethod.POST)
	@ResponseBody
	public HashSet<Commodity> retriveBalesForSC(
			@RequestBody BalePickupMobileDetailsForSC balePickupMobileDetailsForSC) {
		HashSet<Commodity> counts = new HashSet<Commodity>();
		counts = baleRouteTripService
				.retriveBalesCountForSC(balePickupMobileDetailsForSC);
		return counts;

	}

	@RequestMapping(value = "/service/destinations", method = RequestMethod.POST)
	@ResponseBody
	public List<Destination> getDestinations(
			@RequestBody DestinationInputDTO destinationInputDTO) {

		List<Integer> storeIdList = destinationInputDTO.getStoreIdList();

		List<Destination> destinations = baleRouteTripService
				.getAllDestinations(storeIdList);

		return destinations;

	}

	@RequestMapping("service/healthCheck")
	public String healthCheck() {
		return "ta da! brta app is working";
	}

	@RequestMapping(value = "service/update/completetrip", method = RequestMethod.POST)
	public OutputResponse completeTrip(
			@RequestBody CompleteTripInputPayLoad completeTripPayLoad) {
		OutputResponse response = new OutputResponse();
		baleRouteTripService.completeTrip(completeTripPayLoad);

		return response;
	}

	@RequestMapping(value = "service/get/savedonlytrips", method = RequestMethod.POST)
	public HashSet<Destination> getSavedOnlyTrips(
			@RequestBody ActivityListInputPayload inputPayload) {

		HashSet<Destination> pickupDetails = baleRouteTripService
				.getSavedOnlyTrips(inputPayload);

		return pickupDetails;
	}

	@RequestMapping(value = "service/get/savedonlytripsNew", method = RequestMethod.POST)
	public List<PickupDetails> getSavedOnlyTripsNew(
			@RequestBody ActivityListInputPayload inputPayload) {

		List<PickupDetails> pickupDetails = baleRouteTripService
				.getSavedOnlyTripsNew(inputPayload);

		return pickupDetails;
	}

	@RequestMapping(value = "service/pickUp/imageFromName", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getPickupImagesFromTripAndName(
			@RequestBody String imgData) {
		com.google.gson.JsonParser parser = new com.google.gson.JsonParser();

		
		JsonObject json = (JsonObject) parser.parse(imgData);
		Gson gson = new Gson();
		String imageName = gson.toJson(json.get("imageName"));
		imageName = imageName.replace("\"", "");

		
		List<String> images = new ArrayList<String>();

		String image = balePickupService.getBalePickupImagesFromName(imageName
				.toString());
	
		if (!image.isEmpty()) {
			images.add(image);
		}

		return images;
	}

}
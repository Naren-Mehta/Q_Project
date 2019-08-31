package com.wm.brta.controller.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wm.brta.domain.BalePickupSummaryView;
import com.wm.brta.dto.BalePickupFilterDTO;
import com.wm.brta.dto.BalePickupMobileDetailsForSC;
import com.wm.brta.dto.Commodity;
import com.wm.brta.dto.CompleteTripInputPayLoad;
import com.wm.brta.dto.OutputResponse;
import com.wm.brta.dto.PickupDetails;
import com.wm.brta.dto.PickupDetailsFromExcel;
import com.wm.brta.dto.PickupExcelDTO;
import com.wm.brta.service.BalePickupService;
import com.wm.brta.service.BaleRouteTripService;
import com.wm.brta.service.ServiceChannelService;
import com.wm.brta.util.BaleUtils;

@RestController
public class BalePickupTripController {

	@Autowired
	BaleRouteTripService baleRouteTripService;

	@Autowired
	BalePickupService balePickupService;
	
	@Autowired
	ServiceChannelService serviceChannelService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BalePickupTripController.class);

	@RequestMapping(value = "/ui/tripMgmt/add/tripdetails", method = RequestMethod.POST)
	public OutputResponse addTripDetailsForWeb(@RequestBody PickupDetails pickupDetails) {
		OutputResponse response = new OutputResponse();
		String ESBstatus = null;
		try {
			
			Integer tripId = baleRouteTripService.addTripDetailsNew(pickupDetails);
		
			String status = "";

			if (tripId > 0) {
				CompleteTripInputPayLoad completeTripInputPayLoad = new CompleteTripInputPayLoad();
				
				completeTripInputPayLoad.setTripId(tripId);

				completeTripInputPayLoad.setGrossWeight(pickupDetails
						.getGrossWeight());
				completeTripInputPayLoad.setTareWeight(pickupDetails
						.getTareWeight());

				List<Integer> storeIdList = new ArrayList<Integer>();
				storeIdList.add(pickupDetails.getStoreId());
				completeTripInputPayLoad.setStoreIdList(storeIdList);
				completeTripInputPayLoad.setDeliveryDate(pickupDetails.getDeliveryDate());
				completeTripInputPayLoad.setDate(pickupDetails.getPickupDate());
				completeTripInputPayLoad.setReleaseNo(pickupDetails.getReleaseNotes());
				completeTripInputPayLoad.setDestinationId(pickupDetails.getDestinationId());
				completeTripInputPayLoad.setUserId(pickupDetails.getUserId());

				baleRouteTripService.completeTrip(completeTripInputPayLoad);
				
					for (Commodity commodity : pickupDetails.getMaterialList()) {
					
						BalePickupMobileDetailsForSC balePickupMobileDetailsForSC = new BalePickupMobileDetailsForSC();
					//	balePickupMobileDetailsForSC.setTruckId(truckId);
						Integer balePicked = commodity.getBalesPicked() != null ? commodity.getBalesPicked() : 0;
						Integer baleRemaining = commodity.getBalesRemaining() != null ? commodity.getBalesRemaining() : 0;
								
								
						
						balePickupMobileDetailsForSC.setBalesPicked(balePicked);
						balePickupMobileDetailsForSC.setBalesRemaining(baleRemaining);
						balePickupMobileDetailsForSC.setStoreName(pickupDetails.getStoreName());
						balePickupMobileDetailsForSC.setBol(pickupDetails.getBOL());
						
						balePickupMobileDetailsForSC.setMaterial(commodity.getMaterialId());
						balePickupMobileDetailsForSC.setOldMaterialId(pickupDetails.getOldMaterialId());
						balePickupMobileDetailsForSC.setOldCheckinDate(pickupDetails.getOldPickupDate());
						if(pickupDetails.isIncident()){
						balePickupMobileDetailsForSC.setIncidentDescription(pickupDetails.getIncidentType().getIncidentDescription()!= null ?
								pickupDetails.getIncidentType().getIncidentDescription():""	);
						balePickupMobileDetailsForSC.setIncidentNotes(pickupDetails.getComments()!=null ? pickupDetails.getComments():"");
						}
						balePickupMobileDetailsForSC.setCustomerSite(pickupDetails.getStoreId());
						balePickupMobileDetailsForSC.setDriver(pickupDetails.getUserId());
						balePickupMobileDetailsForSC.setOldDriverId(pickupDetails.getOldDriverId());

						balePickupMobileDetailsForSC.setApplicationType("Web");	
						if(!pickupDetails.getPickupDate().equals(pickupDetails.getOldPickupDate())){
							balePickupMobileDetailsForSC.setCheckoutDate(BaleUtils.appendTimetoDate(pickupDetails.getPickupDate()));	
							balePickupMobileDetailsForSC.setCheckinDate(BaleUtils.appendTimetoDate(pickupDetails.getPickupDate()));
						}
						
						balePickupMobileDetailsForSC.setUpdatedDevice("Web");
						
						Integer id = baleRouteTripService.saveCheckInCheckOut(balePickupMobileDetailsForSC);
						ESBstatus = serviceChannelService.getSavedWorkorderForServiceChannel(balePickupMobileDetailsForSC,id);
					}
				
				
			}
			if (tripId == 0) {
		
				status = "Insertion Failure";
			} else {
				status = "Successfull";
			}
			response.setMessage(status);
		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripController:addTripDetails=====>"
					+ e);
		}

		return response;
	}

	@RequestMapping(value = "/ui/tripMgmt/update/completetrip", method = RequestMethod.POST)
	public OutputResponse completeTripForWeb(
			@RequestBody CompleteTripInputPayLoad completeTripPayLoad) {
		OutputResponse response = new OutputResponse();
		try {
			baleRouteTripService.completeTrip(completeTripPayLoad);
		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripController:completeTrip=====>"
					+ e);
		}

		return response;
	}

	@RequestMapping(value = "/ui/common/getAll/pickUps", method = RequestMethod.POST)
	@ResponseBody
	public List<BalePickupSummaryView> getAllPickUps(
			@RequestBody BalePickupFilterDTO balePickupFilter) {

		
		
		List<BalePickupSummaryView> assignments = new ArrayList<BalePickupSummaryView>();
		try {

			assignments = balePickupService.getPickUps(balePickupFilter);
		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripController:getPickUps=====>" + e);
		}
		return assignments;
	}

	@RequestMapping(value = "/ui/common/getAll/pickUpsForDARAndRMT", method = RequestMethod.POST)
	@ResponseBody
	public List<BalePickupSummaryView> getAllPickUpsForDAR(
			@RequestBody BalePickupFilterDTO balePickupFilter) {
		
		

		List<BalePickupSummaryView> assignments = new ArrayList<BalePickupSummaryView>();
		try {
			assignments = balePickupService.getPickUpsForDAR(balePickupFilter);
		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripController:getPickUps=====>" + e);
		}
		return assignments;
	}

	@RequestMapping(value = "/ui/tripMgmt/pickUp/images", method = RequestMethod.POST)
	@ResponseBody
	public List<String> getPickupImages(@RequestBody Long tripId) {

		List<String> images =new ArrayList<String>();
		try{
			
			String number = tripId.toString();
			number = number.substring(6, number.length());
			Integer originalTripId = Integer.parseInt(number);

			images = balePickupService
					.getBalePickupImages(originalTripId);
		
		}catch(Exception e){
			LOGGER.error("===Exception in tripMgmt/pickUp/images==="+e);
			
		}
		
		return images;
	}

	@RequestMapping(value = "/ui/tripMgmt/upload/pickUpsFromExcel", method = RequestMethod.POST)
	@ResponseBody
	public OutputResponse uploadBalePickUpsFromExcel(
			@RequestBody PickupExcelDTO pickupExcelDTO) {

		OutputResponse response = new OutputResponse();

		List<PickupDetailsFromExcel> pickupDetailsFromExcelList = pickupExcelDTO.getPicupExcelList();
		
		
	
		Integer exceptionRecord=2;
		String status = "";
		String successfulStatus="";
		String incompleteStatus="";
		String failStatus="";
		String finalmsg="";
		Integer successfulcount=0;
		Integer incompletecount=0;
		
		Boolean exceptionoccured=true;
	
		 outerloop:
		for (PickupDetailsFromExcel obj : pickupDetailsFromExcelList) {
			exceptionRecord++;
			if(exceptionoccured){
			PickupDetailsFromExcel pickupDetailsFromExcel = obj;
			
			Map<Integer, String> map = balePickupService.createPickupDetailsObject(pickupDetailsFromExcel);
			for (Map.Entry<Integer, String> entry : map.entrySet()) {
				Integer mapKey = entry.getKey();
		
				status = entry.getValue();

				if (mapKey != null && mapKey > 0) {
							
					
					if(status.equalsIgnoreCase("Data Saved Successfully.")){// 1 record uploaded data saved successfully.
						//Data saved but trip incomplete; Sell Customer Site requires input.
					successfulcount++;	
						}
					if(status.equalsIgnoreCase("Data saved but trip incomplete; Sell Customer Site requires input.")){
						incompletecount++;
						
					}														
				}else if(mapKey== 0){
					failStatus = " Unable to Upload Line Number "+exceptionRecord+" ,"+ status+" Please Upload excel again after fixing Line Number "+ exceptionRecord+".";
					
					exceptionoccured=false;
					break outerloop;
				}
				else{
					failStatus = " Unable to upload record number "+exceptionRecord;
					break outerloop;
				}
			}
				
				
			}
			
		}
		if(successfulcount>0){
			successfulStatus="Line 2 (Sample Line Item) in file not uploaded. "+ successfulcount +" Records Uploaded. Data Saved Successfully. ";
			}
		if((successfulcount==0)&&(incompletecount>0)){
			incompleteStatus ="Line 2 (Sample Line Item) in file not uploaded.";
		}
		if(incompletecount>0){
			incompleteStatus =incompleteStatus+" "+ incompletecount+" Incomplete Records Uploaded, Data Saved but Trip Incomplete; Sell Customer Site Requires Input.";
			}
		
		
			finalmsg= successfulStatus+incompleteStatus+failStatus;
			response.setMessage(finalmsg);
	
		return response;
	}
	
	@RequestMapping(value = "/ui/tripMgmt/sampleexceldownload", method = RequestMethod.GET)
	public void sampleExcelDownload(HttpServletResponse response) {
		
	
		
		try {
			
			BaleUtils.writeXLSXFile(response);
		
			//BaleUtils.fileWithColor(response);
		} catch (Exception e) {
			LOGGER.error("Error: sampleExcelDownload=====>"
					+ e);
		}
	}

	
	
}

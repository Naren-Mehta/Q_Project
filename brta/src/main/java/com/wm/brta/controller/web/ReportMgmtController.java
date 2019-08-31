package com.wm.brta.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wm.brta.domain.EditPendingReportDetails;
import com.wm.brta.domain.PendingReport;
import com.wm.brta.dto.EditPendingReportDTO;
import com.wm.brta.dto.PendingReportDTO;
import com.wm.brta.dto.PendingStoreReportDTO;
import com.wm.brta.dto.SaveBalePickupFromPendingReport;
import com.wm.brta.service.BalePickupService;


@RestController
public class ReportMgmtController {

	@Autowired
	BalePickupService balePickupService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportMgmtController.class);

	
	@RequestMapping(value = "/ui/reportMgmt/getAll/pendingReports", method = RequestMethod.POST)
	@ResponseBody
	public Set<PendingReportDTO> getAllPendingReports(@RequestBody PendingStoreReportDTO pendingStoreReportDTO){
		Set<PendingReportDTO> pendingreports = new HashSet<PendingReportDTO>();
		try{
		pendingreports =balePickupService.getAllPendingPickups(pendingStoreReportDTO);
		}catch(Exception e){
			LOGGER.error("Error:ReportMgmtController:getAllPendingPickups(pendingStoreReportDTO)=====>" +e);
		}
		return pendingreports;
	}
	
	@RequestMapping(value = "/ui/reportMgmt/getAll/getAllBalePickupDates", method = RequestMethod.POST)
	@ResponseBody
	public List<EditPendingReportDetails> getAllBalePickupDates(@RequestBody EditPendingReportDTO editPendingReportDTO){
		List<EditPendingReportDetails> pendingreports = new ArrayList<EditPendingReportDetails>();
		
		
		try{
		pendingreports =balePickupService.getAllBalePickupDates(editPendingReportDTO);
		}catch(Exception e){
			LOGGER.error("Error:ReportMgmtController: getAllBalePickupDates (editPendingReportDTO)=====>" +e);
		}
		return pendingreports;
	}
	
	@RequestMapping(value = "/ui/reportMgmt/savePickupFromPendingStore", method = RequestMethod.POST)
	@ResponseBody
	public Map savePickupFromPendingStore(@RequestBody SaveBalePickupFromPendingReport saveBalePickupFromPendingReport){
		
		
		Map returnMap= new HashMap();
		
		try{
			returnMap =balePickupService.savePickupFromPendingStore(saveBalePickupFromPendingReport);
		}catch(Exception e){
			LOGGER.error("Error:ReportMgmtController: savePickupFromPendingStore (saveBalePickupFromPendingReport)=====>" +e);
		}
		return returnMap;
	}
	


}

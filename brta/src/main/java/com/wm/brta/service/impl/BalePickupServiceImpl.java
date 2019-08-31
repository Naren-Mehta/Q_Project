package com.wm.brta.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wm.brta.constants.ApplicationCommonConstants;
import com.wm.brta.dao.AssignDriverDTO;
import com.wm.brta.dao.BalePickUpAssignmentDAO;
import com.wm.brta.dao.BalePickupMaterialConfigurationDAO;
import com.wm.brta.dao.BalePickupTripDAO;
import com.wm.brta.dao.CustomerSiteDAO;
import com.wm.brta.dao.GenerateAssignmentDriverPopup;
import com.wm.brta.dao.IncidentTypeDao;
import com.wm.brta.dao.MasterDataDao;
import com.wm.brta.dao.MaterialDAO;
import com.wm.brta.dao.ServiceChannelDAO;
import com.wm.brta.dao.UserDao;
/*import com.wm.brta.data.domain.Pickupsview;*/
/*import com.wm.brta.data.dto.BalePickupFilterDTO;
 import com.wm.brta.data.dto.BalePickupUpdateDTO;*/
import com.wm.brta.domain.BalePickupAssignment;
import com.wm.brta.domain.BalePickupMaterialConfiguration;
import com.wm.brta.domain.BalePickupSummaryView;
import com.wm.brta.domain.BalePickupTrip;
import com.wm.brta.domain.Customer;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.DriverPickups;
import com.wm.brta.domain.DriverSupplierPickupsView;
import com.wm.brta.domain.EditPendingReportDetails;
import com.wm.brta.domain.Frequency;
import com.wm.brta.domain.Image;
import com.wm.brta.domain.IncidentType;
import com.wm.brta.domain.Material;
import com.wm.brta.domain.MaterialByCustomerAndSupplierSP;
import com.wm.brta.domain.PendingReport;
import com.wm.brta.domain.SCAndRMTStoreMappingWithLatLong;
import com.wm.brta.domain.ServiceChannelWorkOrders;
import com.wm.brta.domain.StoreSupplierListView;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.SupplierPickupDetailsSP;
import com.wm.brta.domain.SupplierPickupsSP;
import com.wm.brta.domain.SupplierSavedPickupsSP;
import com.wm.brta.domain.User;
import com.wm.brta.dto.ActivityListInputPayload;
import com.wm.brta.dto.AssignmentFilterDTO;
import com.wm.brta.dto.BalePickupAssignmentDTO;
import com.wm.brta.dto.BalePickupFilterDTO;
import com.wm.brta.dto.BalePickupMobileDetailsForSC;
import com.wm.brta.dto.Commodity;
import com.wm.brta.dto.CompleteTripInputPayLoad;
import com.wm.brta.dto.Destination;
import com.wm.brta.dto.DriverDTO;
import com.wm.brta.dto.EditPendingReportDTO;
import com.wm.brta.dto.OnCallInputPayload;
import com.wm.brta.dto.PendingReportDTO;
import com.wm.brta.dto.PendingStoreReportDTO;
import com.wm.brta.dto.PickupDetails;
import com.wm.brta.dto.PickupDetailsFromExcel;
import com.wm.brta.dto.PickupDetailsWithMasterSets;
import com.wm.brta.dto.SaveBalePickupFromPendingReport;
import com.wm.brta.dto.SupplierPickupDetailsMobileDTO;
import com.wm.brta.dto.SupplierPickupsMobileDTO;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.BalePickupService;
import com.wm.brta.service.BaleRouteTripService;
import com.wm.brta.service.CustomerService;
import com.wm.brta.service.ServiceChannelService;
import com.wm.brta.util.BaleUtils;
import com.wm.brta.util.PickupInformation;

@Transactional
@Component("balePickupService")
public class BalePickupServiceImpl implements BalePickupService {
	private static final Logger LOGGER = LoggerFactory.getLogger(BalePickupServiceImpl.class);

	@Autowired
	BaleRouteTripService baleRouteTripService;

	@Autowired
	BalePickUpAssignmentDAO balePickupAssignment;

	@Autowired
	CustomerSiteDAO customerSiteDAO;

	@Autowired
	BalePickupMaterialConfigurationDAO balePickupMaterialConfigurationDAO;

	@Autowired
	IncidentTypeDao incidentTypeDao;

	@Autowired
	MasterDataDao masterDataDAO;

	@Autowired
	UserDao userDao;

	@Autowired
	MaterialDAO materialDao;

	@Autowired
	BalePickupTripDAO balePickupTripDAO;

	@Autowired
	CustomerService customerService;
	
	@Autowired
	ServiceChannelService serviceChannelService;
	
	@Autowired
	ServiceChannelDAO serviceChannelDAO;

	

	@Override
	public HashSet<Destination> getAllDestinations(
			ActivityListInputPayload activityListPayload) {

		List<PickupInformation> weekNumbersAndDays = new ArrayList<PickupInformation>();

		List<Integer> days = null;
		try {
			weekNumbersAndDays = getAllWeekNumbersAndDays(activityListPayload
					.getDates());
			// days = getAllDays(activityListPayload.getDates());
		} catch (ParseException e) {
			
			LOGGER.error("==Exception in BalePickupServiceImpl==" + e);
		}
		List<BalePickupAssignment> assignmentsForUser = null;
		List<BalePickupAssignment> assignmentsOnCall = null;
		List<BalePickupAssignment> assignments = new ArrayList<BalePickupAssignment>();

		
		boolean inputHasTodayDate = false;
		assignmentsForUser = balePickupAssignment
				.getAllAssignments(activityListPayload.getUserId());
		assignmentsOnCall = balePickupAssignment.getAllOnCallAssignments();
		for (String date : activityListPayload.getDates()) {
		
			Date dateToday = new Date();
			SimpleDateFormat format = new SimpleDateFormat();
			format = new SimpleDateFormat("MM/dd/yyyy");
			String dateFormat = format.format(dateToday);
			if (dateFormat.equals(date)) {
				inputHasTodayDate = true;
			}

		}
		if (inputHasTodayDate)
			assignmentsForUser.addAll(assignmentsOnCall);
		// ignore assignments diverted to another user

		for (BalePickupAssignment assignmentForUser : assignmentsForUser) {
			if (assignmentForUser.getFrequency() != 3) {
				for (PickupInformation pickupInformation : weekNumbersAndDays) {

					if (assignmentForUser.getWeekNumber().equals(
							pickupInformation.getWeekNumber())
							&& assignmentForUser.getDay().equals(
									pickupInformation.getDayNumber())
							&& (assignmentForUser.getDivertedUser() == null || (assignmentForUser
									.getDivertedUser() != null && assignmentForUser
									.getDivertedUser().getUserId().intValue() == activityListPayload
									.getUserId()))) {

						assignmentForUser.setPickupDate(pickupInformation
								.getPickupDate());
						assignments.add(assignmentForUser);
					}
				}
			} else {
				// assignments.add(assignmentForUser);
			}
		}

		// get trips ,if any, for the assignments

		List<BalePickupTrip> trips = balePickupTripDAO.getTripsForAssignments(
				assignments, activityListPayload.getDates());

		HashSet<PickupDetails> pickupDetailsList = new HashSet<PickupDetails>();
		HashSet<Destination> destinations = new HashSet<Destination>();
		loop1: for (BalePickupAssignment assignment : assignments) {

			PickupDetails pickUpDetails = new PickupDetails();

			pickUpDetails.setStoreId(assignment.getBuyCustomerSite()
					.getCustomerSiteId());

			pickUpDetails.setStoreName(assignment.getBuyCustomerSite()
					.getSiteName());
			Destination destination = new Destination();
			if (assignment.getSellCustomerSite() != null)
				destination.setDestinationId(assignment.getSellCustomerSite()
						.getCustomerSiteId());
			pickUpDetails.setDestination(destination);
			String address = assignment.getBuyCustomerSite().getLocation()
					.getHouseNumber()
					+ " "
					+

					assignment.getBuyCustomerSite().getLocation().getAddress3()
					+ " "
					+ assignment.getBuyCustomerSite().getLocation()
							.getAddress4()
					+ " "
					+ assignment.getBuyCustomerSite().getLocation()
							.getAddress5()
					+ " "
					+ assignment.getBuyCustomerSite().getLocation()
							.getPostCode();
			pickUpDetails.setAddress(address);
			if (assignment.getFrequency() != 3)
				pickUpDetails.setPickupDate(assignment.getPickupDate());
			else {
				Date dateToday = new Date();
				SimpleDateFormat format = new SimpleDateFormat();
				format = new SimpleDateFormat("MM/dd/yyyy");
				pickUpDetails.setPickupDate(format.format(dateToday));
			}

			pickupDetailsList.add(pickUpDetails);

		}
		if (assignments != null) {
			// assignments = removeDuplicateSellCustomers(assignments);
			for (BalePickupAssignment assignment : assignments) {
				if (assignment.getSellCustomerSite() != null) {
					Destination destination = new Destination();
					HashSet<PickupDetails> pickupsForDestination = new HashSet<PickupDetails>();
					destination.setName(assignment.getSellCustomerSite()
							.getSiteName());
					destination.setDestinationId(assignment
							.getSellCustomerSite().getCustomerSiteId());

					for (PickupDetails pickupDetails : pickupDetailsList) {

						if (pickupDetails.getDestination().getDestinationId() == destination
								.getDestinationId()) {
							pickupsForDestination.add(pickupDetails);
						}
					}
					/*
					 * destination.setPickupDetails(pickupsForDestination); if
					 * (destination.getPickupDetails().size() != 0)
					 * destinations.add(destination);
					 */

				}
			}

		}

		return destinations;
	}

	@Override
	public List<PickupDetails> getAllDestinationsNew(
			ActivityListInputPayload activityListPayload) {

		List<String> dates = activityListPayload.getDates();
		Integer userId = activityListPayload.getUserId();
		List<DriverPickups> allDriverPickups = new ArrayList<DriverPickups>();
		List<PickupDetails> pickupDetailsList = new ArrayList<PickupDetails>();

		for (String date : dates) {
			try {
				Integer day = BaleUtils.getDay(date);
				Integer weekNumber = BaleUtils.getWeekNumber(date);
				List<DriverPickups> driverPickups = balePickupAssignment
						.getDriverPickupsByDate(userId, date, day, weekNumber);

				if (driverPickups != null && driverPickups.size() > 0) {
					allDriverPickups.addAll(driverPickups);
				}
			} catch (Exception e) {
				LOGGER.error("Error  : getAllDestinationsNew == > " + e);
			}
		}

		if (allDriverPickups.size() > 0) {
			for (DriverPickups driverPickup : allDriverPickups) {
				PickupDetails pickupDetails = new PickupDetails();
				pickupDetails.setStoreId(driverPickup.getBuyCustomerSiteId());

				pickupDetails.setStoreName(driverPickup
						.getBuyCustomerSiteName());

				String address = driverPickup.getBuyCustomerHouseNumber() + " "
						+ driverPickup.getBuyCustomerAddress3() + " "
						+ driverPickup.getBuyCustomerAddress4() + " "
						+ driverPickup.getBuyCustomerAddress5() + " "
						+ driverPickup.getBuyCustomerPostCode();

				pickupDetails.setAddress(address);
				pickupDetails.setPickupDate(driverPickup.getPickupDate());

				Destination destination = new Destination();
				destination.setDestinationId(driverPickup
						.getSellCustomerSiteId());

				pickupDetails.setDestination(destination);
				// pickupDetails.setIncident(driverPickup);
				// pickupDetails.setDestinationDrop(driverPickup);

				pickupDetailsList.add(pickupDetails);
			}
		}

		return pickupDetailsList;
	}

	@Override
	public List<PickupDetails> getAllPickUpsFromSupplier(
			SupplierPickupsMobileDTO supplierPickupsMobileDTO) {

		Integer userId = supplierPickupsMobileDTO.getUserId();
		String date = supplierPickupsMobileDTO.getDate();
		
		List<PickupDetails> pickupDetailsList = new ArrayList<PickupDetails>();

		List<SupplierPickupsSP> supplierPickups = new ArrayList<SupplierPickupsSP>();
		User user = userDao.getUserById(Long.valueOf(userId));
		Supplier supplier = user.getSupplier();
		try {
			supplierPickups = balePickupAssignment.getAllPickupsForSupplier(
					supplier, supplierPickupsMobileDTO.getStoreSearchString(),
					userId, supplierPickupsMobileDTO.getVehiclePlateNo());
			if(supplierPickupsMobileDTO.getOnly10RecordFlag()){
				if(supplierPickups!=null && supplierPickups.size() >0){
					int offsetBase= supplierPickupsMobileDTO.getDriverPickupOffset();
					int offsetTop= offsetBase+10;
					
					if(offsetBase <  supplierPickups.size() ){
						if(offsetTop < supplierPickups.size()){
							supplierPickups=supplierPickups.subList(offsetBase,offsetTop);
						}else{
							supplierPickups=supplierPickups.subList(offsetBase,supplierPickups.size());
						}
						
//						supplierPickups=Lists.reverse(supplierPickups);
					}else{
						supplierPickups.clear();
					}	
				}
			}
			
			
		} catch (Exception e) {
			LOGGER.error("Error  : getAllPickUpsFromSupplier == > " + e);
		}

		
		String vehiclePlateNo = supplierPickupsMobileDTO.getVehiclePlateNo();

		if (supplierPickups.size() > 0) {
			for (SupplierPickupsSP supplierPickupsSP : supplierPickups) {

				PickupDetails pickupDetails = new PickupDetails();
				pickupDetails.setStoreId(supplierPickupsSP.getCustomerSiteId());

				pickupDetails.setStoreName(supplierPickupsSP.getSiteName());

				String address = supplierPickupsSP.getHouseNumber() + " "
						+ supplierPickupsSP.getAddress3() + " "
						+ supplierPickupsSP.getAddress4() + " "
						+ supplierPickupsSP.getAddress5() + " "
						+ supplierPickupsSP.getPostCode();

				pickupDetails.setAddress(address);
				pickupDetails.setPickupDate(BaleUtils
						.convertDateFormat(new Date()));

				List<Commodity> materialList = new ArrayList<Commodity>();
				String pickupDate = pickupDetails.getPickupDate();

				List<MaterialByCustomerAndSupplierSP> materialByCustomerAndSupplierProList = balePickupAssignment
						.getMateralByBuyCustomerAndSupplier(
								supplierPickupsSP.getCustomerSiteId(),
								supplier.getSupplierId(), vehiclePlateNo,
								pickupDate);

				

				if (materialByCustomerAndSupplierProList.size() > 0) {
					for (MaterialByCustomerAndSupplierSP materialByCustomerAndSupplier : materialByCustomerAndSupplierProList) {
						Commodity commodity = new Commodity();

						commodity.setShortName(materialByCustomerAndSupplier
								.getDescription());
						commodity.setMaterialId(materialByCustomerAndSupplier
								.getMaterialId());
						commodity.setBalesPicked(materialByCustomerAndSupplier
								.getBalesPicked());
						commodity
								.setBalesRemaining(materialByCustomerAndSupplier
										.getBalesRemaining());

						pickupDetails.setBOL(materialByCustomerAndSupplier
								.getBol());
						materialList.add(commodity);
					}
				}

				List<DriverDTO> drivers = new ArrayList<DriverDTO>();
				if (supplierPickupsSP.getDriver1Id() != null) {
					DriverDTO driver1 = new DriverDTO();
					driver1.setUserId(supplierPickupsSP.getDriver1Id());
					driver1.setFirstName(supplierPickupsSP
							.getDriver1FirstName());
					driver1.setMiddleName(supplierPickupsSP
							.getDriver1MiddleName());
					driver1.setLastName(supplierPickupsSP.getDriver1LastName());
					driver1.setEmailId(supplierPickupsSP.getDriver1Email());
					driver1.setMobilePhone(supplierPickupsSP
							.getDriver1MobilePhone());
					drivers.add(driver1);
				}

				if (supplierPickupsSP.getDriver2Id() != null) {
					DriverDTO driver2 = new DriverDTO();
					driver2.setUserId(supplierPickupsSP.getDriver2Id());
					driver2.setFirstName(supplierPickupsSP
							.getDriver2FirstName());
					driver2.setMiddleName(supplierPickupsSP
							.getDriver2MiddleName());
					driver2.setLastName(supplierPickupsSP.getDriver2LastName());
					driver2.setEmailId(supplierPickupsSP.getDriver2Email());
					driver2.setMobilePhone(supplierPickupsSP
							.getDriver2MobilePhone());
					drivers.add(driver2);
				}

				pickupDetails.setDrivers(drivers);
				pickupDetails.setMaterialList(materialList);
				pickupDetailsList.add(pickupDetails);
				
				materialList=getCommoditiesFromSCTable(supplierPickupsSP,supplierPickupsMobileDTO,supplier);
			
				pickupDetails.setMaterialList(materialList);
			
			
			
			}
		}
		return pickupDetailsList;
	}

	@Override
	public List<PickupDetails> getAllSavedPickUps(
			SupplierPickupsMobileDTO supplierPickupsMobileDTO) {

		Integer userId = supplierPickupsMobileDTO.getUserId();
		List<PickupDetails> pickupDetailsList = new ArrayList<PickupDetails>();

		List<SupplierSavedPickupsSP> supplierPickupList = new ArrayList<SupplierSavedPickupsSP>();
		
		User user = userDao.getUserById(Long.valueOf(userId));
		Supplier supplier = user.getSupplier();
		String vehiclePlateNo=supplierPickupsMobileDTO.getVehiclePlateNo();

		try {
			supplierPickupList = balePickupAssignment.getAllSavedPickups(supplier,
					userId,vehiclePlateNo);
			
		} catch (Exception e) {
			LOGGER.error("Error  : getAllPickUpsFromSupplier == > " + e);
		}
		
		
		
		if (supplierPickupList.size() > 0) {
			for (SupplierSavedPickupsSP supplierPickupsSP : supplierPickupList) {

				PickupDetails pickupDetails = new PickupDetails();
				pickupDetails.setStoreId(supplierPickupsSP.getCustomerSiteId());

				pickupDetails.setStoreName(supplierPickupsSP.getSiteName());

				String address = supplierPickupsSP.getHouseNumber() + " "
						+ supplierPickupsSP.getAddress3() + " "
						+ supplierPickupsSP.getAddress4() + " "
						+ supplierPickupsSP.getAddress5() + " "
						+ supplierPickupsSP.getPostCode();

				pickupDetails.setAddress(address);
				pickupDetails.setPickupDate(BaleUtils
						.convertDateFormat(supplierPickupsSP.getPickupDate()));

				List<Commodity> materialList = new ArrayList<Commodity>();

				List<MaterialByCustomerAndSupplierSP> materialByCustomerAndSupplierProList = balePickupAssignment
						.getMateralByBuyCustomerAndSupplier(supplierPickupsSP
								.getCustomerSiteId(), supplier.getSupplierId(),
								vehiclePlateNo, BaleUtils
										.convertDateFormat(supplierPickupsSP
												.getPickupDate()));

			

				if (materialByCustomerAndSupplierProList.size() > 0) {
					for (MaterialByCustomerAndSupplierSP materialByCustomerAndSupplier : materialByCustomerAndSupplierProList) {
						Commodity commodity = new Commodity();

						commodity.setShortName(materialByCustomerAndSupplier
								.getDescription());
						commodity.setMaterialId(materialByCustomerAndSupplier
								.getMaterialId());
						commodity.setBalesPicked(materialByCustomerAndSupplier
								.getBalesPicked());
						commodity
								.setBalesRemaining(materialByCustomerAndSupplier
										.getBalesRemaining());

						pickupDetails.setBOL(materialByCustomerAndSupplier
								.getBol());
						materialList.add(commodity);
					}
				}

				List<DriverDTO> drivers = new ArrayList<DriverDTO>();
				if (supplierPickupsSP.getDriver1Id() != null) {
					DriverDTO driver1 = new DriverDTO();
					driver1.setUserId(supplierPickupsSP.getDriver1Id());
					driver1.setFirstName(supplierPickupsSP
							.getDriver1FirstName());
					driver1.setMiddleName(supplierPickupsSP
							.getDriver1MiddleName());
					driver1.setLastName(supplierPickupsSP.getDriver1LastName());
					driver1.setEmailId(supplierPickupsSP.getDriver1Email());
					driver1.setMobilePhone(supplierPickupsSP
							.getDriver1MobilePhone());
					drivers.add(driver1);
				}

				if (supplierPickupsSP.getDriver2Id() != null) {
					DriverDTO driver2 = new DriverDTO();
					driver2.setUserId(supplierPickupsSP.getDriver2Id());
					driver2.setFirstName(supplierPickupsSP
							.getDriver2FirstName());
					driver2.setMiddleName(supplierPickupsSP
							.getDriver2MiddleName());
					driver2.setLastName(supplierPickupsSP.getDriver2LastName());
					driver2.setEmailId(supplierPickupsSP.getDriver2Email());
					driver2.setMobilePhone(supplierPickupsSP
							.getDriver2MobilePhone());
					drivers.add(driver2);
				}

				pickupDetails.setDrivers(drivers);
				pickupDetails.setMaterialList(materialList);
				pickupDetailsList.add(pickupDetails);
				
				
			
				try{
					materialList=getCommoditiesFromSCTable(supplierPickupsSP,supplierPickupsMobileDTO,supplier);
					
				}catch(Exception e){
					LOGGER.error("Exception while fetching material from SC table========"+e);
					
				}
				

				pickupDetails.setMaterialList(materialList);
								
			}
		}
		
		return pickupDetailsList;
	}
	
	
	public  List<Commodity> getCommoditiesFromSCTable(SupplierPickupsSP supplierPickupsSP,SupplierPickupsMobileDTO supplierPickupsMobileDTO , Supplier supplier){
		Integer customerSiteId=supplierPickupsSP.getCustomerSiteId();
		Long driver1=Long.parseLong(supplierPickupsSP.getDriver1Id()+"");
		Long driver2=Long.parseLong(supplierPickupsSP.getDriver2Id()+"");
				
		List<Commodity> materialList = new ArrayList<Commodity>();

		HashSet<Commodity> hashSet = balePickupTripDAO.retriveMaterialfromSC(driver1,driver2,supplier.getSupplierId(),
				customerSiteId,
				supplierPickupsMobileDTO.getDate(),supplierPickupsMobileDTO.getVehiclePlateNo());
		
		materialList.clear();
		
		if(hashSet.size()>0){
		for (Commodity com : hashSet) {
			
			Commodity comm = new Commodity();
			comm.setMaterialId(com.getMaterialId());
			comm.setShortName(com.getShortName());

			comm.setBalesPicked(com.getBalesPicked());
			comm.setBalesRemaining(com.getBalesRemaining());
			materialList.add(comm);
				}
			}
		
		return materialList;
	}
	
	public  List<Commodity> getCommoditiesFromSCTable(SupplierSavedPickupsSP supplierPickupsSP,SupplierPickupsMobileDTO supplierPickupsMobileDTO , Supplier supplier){
		
		
		Integer customerSiteId=supplierPickupsSP.getCustomerSiteId();
		Long driver1=Long.parseLong(supplierPickupsSP.getDriver1Id()+"");
		
		
		Long driver2=Long.parseLong(supplierPickupsSP.getDriver2Id()+"");
	

		List<Commodity> materialList = new ArrayList<Commodity>();

		
		
		HashSet<Commodity> hashSet = balePickupTripDAO.retriveMaterialfromSC(driver1,driver2,supplier.getSupplierId(),
				customerSiteId,
				BaleUtils
				.convertDateFormat(supplierPickupsSP
						.getPickupDate()),supplierPickupsMobileDTO.getVehiclePlateNo());
		
		materialList.clear();
		
		if(hashSet.size()>0){
		for (Commodity com : hashSet) {
			
			Commodity comm = new Commodity();
			comm.setMaterialId(com.getMaterialId());
			comm.setShortName(com.getShortName());

			comm.setBalesPicked(com.getBalesPicked());
			comm.setBalesRemaining(com.getBalesRemaining());
			materialList.add(comm);
				}
			}
		
		return materialList;
	}

	@Override
	public PickupDetailsWithMasterSets getPickupDetailsForSupplier(SupplierPickupDetailsMobileDTO supplierPickupDetailsMobileDTO) {

		List<SupplierPickupDetailsSP> supplierPickups = new ArrayList<SupplierPickupDetailsSP>();
		List<Commodity> savedMaterialList = new ArrayList<Commodity>();
		List<Material> materials = new ArrayList<Material>();
		List<IncidentType> incidentTypes = incidentTypeDao.getAllActiveIncidents();
		PickupDetailsWithMasterSets pickupDetailsWithMasterSets = new PickupDetailsWithMasterSets();
		PickupDetails pickupDetails = new PickupDetails();
		SCAndRMTStoreMappingWithLatLong SCAndRMTStoreDetails = null;
		Integer userId = supplierPickupDetailsMobileDTO.getUserId();
		User user = userDao.getUserById(Long.valueOf(userId));
		Supplier supplier = user.getSupplier();
		
		
		
		try {
			supplierPickups = balePickupAssignment.getPickupDetailsForSupplier(supplier, supplierPickupDetailsMobileDTO.getStoreId());
		} catch (Exception e) {
			LOGGER.error("Error  : getPickupDetailsForSupplier == > " + e);
		}

		String vehiclePlateNo = supplierPickupDetailsMobileDTO.getVehiclePlateNo();
		
		
		if (supplierPickups.size() > 0) {
			for (SupplierPickupDetailsSP driverPickup : supplierPickups) {

				List<Material> materialOnlyList = balePickupAssignment.getAllMaterialConfiguationMappingsByCustomerSite(driverPickup.getCustomerSiteId());

				if (materials != null && materials.size() > 0) {
					materials.addAll(materialOnlyList);
					Set<Material> uniqueMaterial = new HashSet<Material>(materials);
					materials = new ArrayList<Material>(uniqueMaterial);
					
				} else {
					materials = materialOnlyList;
				}

				pickupDetails.setStoreId(driverPickup.getCustomerSiteId());
				pickupDetails.setStoreName(driverPickup.getSiteName());
				String address = driverPickup.getHouseNumber() + " "
						+ driverPickup.getAddress3() + " "
						+ driverPickup.getAddress4() + " "
						+ driverPickup.getAddress5() + " "
						+ driverPickup.getPostCode();

				pickupDetails.setAddress(address);
				pickupDetails.setPickupDate(BaleUtils.convertDateFormat(new Date()));

				String pickupDate = pickupDetails.getPickupDate();
				List<MaterialByCustomerAndSupplierSP> materialByCustomerAndSupplierProList = balePickupAssignment
						.getMateralByBuyCustomerAndSupplier(driverPickup.getCustomerSiteId(),supplier.getSupplierId(), vehiclePlateNo,pickupDate);

				LinkedHashSet<Image> allImages = new LinkedHashSet<Image>();
				List<Integer> tripIdList = new ArrayList<Integer>();
				String comments = "";
			
				
				
				
				if (materialByCustomerAndSupplierProList.size() > 0) {
					for (MaterialByCustomerAndSupplierSP materialByCustomerAndSupplier : materialByCustomerAndSupplierProList) {

						Commodity commodity = new Commodity();
						commodity.setMaterialId(materialByCustomerAndSupplier.getMaterialId());
						commodity.setShortName(materialByCustomerAndSupplier.getDescription());
						commodity.setBalesPicked(materialByCustomerAndSupplier.getBalesPicked());
						commodity.setBalesRemaining(materialByCustomerAndSupplier.getBalesRemaining());
						pickupDetails.setBOL(materialByCustomerAndSupplier.getBol());
						comments = materialByCustomerAndSupplier.getComments();
						pickupDetails.setTripId(materialByCustomerAndSupplier.getTripId());
						tripIdList.add(materialByCustomerAndSupplier.getTripId());
						savedMaterialList.add(commodity);
						
					if (materialByCustomerAndSupplier.getIncident() == true) {
							IncidentType incidentType = new IncidentType();
							incidentType.setIncidentTypeId(materialByCustomerAndSupplier.getIncidentTypeId());
							incidentType.setIncidentDescription(materialByCustomerAndSupplier.getIncidentDescription());
							pickupDetails.setIncidentType(incidentType);
							pickupDetails.setIncident(true);
						}

					}

				}
				

				
				SCAndRMTStoreDetails = serviceChannelDAO.getRMTStoreIdByStoreName(supplierPickupDetailsMobileDTO.getStoreName());
				 if(SCAndRMTStoreDetails !=null) {
					 Integer workOrderNumber =-1;
					 pickupDetails.setLatitude(SCAndRMTStoreDetails.getLatitude());
					 pickupDetails.setLongitude(SCAndRMTStoreDetails.getLongitude());
					 ServiceChannelWorkOrders serviceChannelWorkOrders =  new ServiceChannelWorkOrders();
					 serviceChannelWorkOrders.setStoreId(SCAndRMTStoreDetails.getScStoreId());
					 serviceChannelWorkOrders.setCallDate(BaleUtils.convertStringToDate(supplierPickupDetailsMobileDTO.getDate()));
					 
					 workOrderNumber = serviceChannelDAO.retriveWOByStoreIdAndPickupDate(serviceChannelWorkOrders);
								
							 if(workOrderNumber!=-1){
								
								 pickupDetails.setBOL(workOrderNumber);
								 pickupDetails.setIsServiceChannelCustomer(true);
							 }else{
								
								 pickupDetails.setIsServiceChannelCustomer(false);
							}
				 }else{
						pickupDetails.setIsServiceChannelCustomer(false);
					}
				 
				 pickupDetails.setComments(comments);

				if (tripIdList != null && tripIdList.size() > 0) {
					for (Integer tripId : tripIdList) {
						LinkedHashSet<Image> balePickupImages = balePickupAssignment
								.getBalePickupImages(tripId);
						allImages.addAll(balePickupImages);
					}
				}

				Map<String, Image> map = new LinkedHashMap<String, Image>();
				for (Image img : allImages) {
					map.put(img.getPath(), img);
				}

				allImages.clear();

				List<Image> imageList = new ArrayList<Image>();
				imageList.addAll(map.values());

				pickupDetails.setTripImages(imageList);
				pickupDetails.setMaterialList(savedMaterialList);
			}
			
			Integer customerSiteId=(Integer.parseInt(supplierPickupDetailsMobileDTO.getStoreId()));
			Long driver1=0L;
			Long driver2=0L;
			savedMaterialList.clear();
			
			List<DriverSupplierPickupsView> driverList = userDao.getUserFromStoreId(customerSiteId,supplier.getSupplierId());
			
				for(DriverSupplierPickupsView list: driverList){
					
					 driver1 = Long.valueOf(list.getDriver1Id());
					 driver2 = Long.valueOf(list.getDriver2Id());
				}
			
			
			HashSet<Commodity> hashSet = balePickupTripDAO.retriveMaterialfromSC(driver1,driver2,supplier.getSupplierId(),customerSiteId,
					supplierPickupDetailsMobileDTO.getDate(),supplierPickupDetailsMobileDTO.getVehiclePlateNo());
			
			
			
		if(hashSet.size()>0){
		for (Commodity com : hashSet) {
			
			Commodity comm = new Commodity();
			comm.setMaterialId(com.getMaterialId());
			comm.setShortName(com.getShortName());

			comm.setBalesPicked(com.getBalesPicked());
			comm.setBalesRemaining(com.getBalesRemaining());
			savedMaterialList.add(comm);
				}
			}
		
		pickupDetails.setMaterialList(savedMaterialList);
		
		}
	
		pickupDetailsWithMasterSets.setMaterials(materials);
		pickupDetailsWithMasterSets.setIncidentTypes(incidentTypes);
		pickupDetailsWithMasterSets.setPickupDetails(pickupDetails);
		
		return pickupDetailsWithMasterSets;
	}

	private List<BalePickupAssignment> removeDuplicateSellCustomers(List<BalePickupAssignment> assignments) {
		HashSet<Integer> sellCustomerIds = new HashSet<Integer>();
		List<BalePickupAssignment> tempAssignments = new ArrayList<BalePickupAssignment>();
		for (int i = 0; i < assignments.size(); i++) {
			boolean equalFlag = false;
			innerLoop: for (int j = i + 1; j < assignments.size(); j++) {
				if (assignments
						.get(i)
						.getSellCustomerSite()
						.getCustomerSiteId()
						.equals(assignments.get(j).getSellCustomerSite()
								.getCustomerSiteId())) {
					tempAssignments.add(assignments.get(i));
					break innerLoop;
				}
			}
		}
		for (BalePickupAssignment assignment : tempAssignments) {
			assignments.remove(assignment);
		}

		return assignments;
	}

	private List<PickupInformation> getAllWeekNumbersAndDays(List<String> dates)
			throws ParseException {

		List<PickupInformation> weekAndDay = new ArrayList<PickupInformation>();
		for (String date : dates) {
			String format = "MM/dd/yyyy";
			SimpleDateFormat df = new SimpleDateFormat(format);
			Date dateFormat = df.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateFormat);
			int weeknumber = cal.get(Calendar.WEEK_OF_MONTH);
			if (cal.get(Calendar.DAY_OF_WEEK) == 1)
				weeknumber = weeknumber - 1;

			int day = cal.get(Calendar.DAY_OF_WEEK);
			day = day - 1;
			if (day == 0)
				day = 7;

			PickupInformation pickupInformation = new PickupInformation();
			pickupInformation.setPickupDate(date);
			pickupInformation.setWeekNumber(weeknumber);
			pickupInformation.setDayNumber(day);

			weekAndDay.add(pickupInformation);
		}
		return weekAndDay;
	}

	private List<Integer> getAllDays(List<String> dates) throws ParseException {
		List<Integer> days = new ArrayList<Integer>();
		for (String date : dates) {
			String format = "MM/dd/yyyy";

			SimpleDateFormat df = new SimpleDateFormat(format);
			Date dateFormat = df.parse(date);
			Calendar cal = Calendar.getInstance();

			cal.setTime(dateFormat);
			// cal.setFirstDayOfWeek(cal.get(Calendar.MONDAY));
			int day = cal.get(Calendar.DAY_OF_WEEK);
			day = day - 1;
			if (day == 0)
				day = 7;
			days.add(day);
		}
		return days;
	}

	@Override
	public PickupDetailsWithMasterSets getPickupDetails(PickupDetails pickup) {

		return null;

	}

	@Override
	public void addBalePickupAssignments(BalePickupAssignmentDTO assignmentDTO,
			UserDTO user) {

		

		if (assignmentDTO.getAction().equals("delete")
				&& assignmentDTO.getAssignments() != null
				&& assignmentDTO.getAssignments().size() != 0
				&& assignmentDTO.getCustomerSiteIdList() != null
				&& assignmentDTO.getCustomerSiteIdList().size() > 0) {

			
			balePickupAssignment.disableBalePickupAssignments(assignmentDTO
					.getCustomerSiteIdList());

		}

		if (assignmentDTO.getAction().equals("delete")
				&& assignmentDTO.getOldAssignments() != null
				&& assignmentDTO.getOldAssignments().size() != 0
				&& assignmentDTO.getAssignments() != null
				&& assignmentDTO.getAssignments().size() != 0) {

			
			balePickupAssignment.delete(assignmentDTO.getOldAssignments());

		}
		for (BalePickupAssignment assignment : assignmentDTO.getAssignments()) {
			assignment.setCreateDate(new Date());
			assignment.setUpdatedAt(new Date());
			assignment.setUpdatedBy(user.getFirstName());
			balePickupAssignment.persist(assignment);
		}

	}

	public void disableBalePickupsAssignments(
			List<BalePickupAssignment> assignments) {

		balePickupAssignment.delete(assignments);
	}

	@Override
	public Set<BalePickupAssignment> getPickupAssignments(
			AssignmentFilterDTO assignmentFilter) {
		List<CustomerSite> buyCustomerSites = new ArrayList<CustomerSite>(
				masterDataDAO.getAllCustomerSitesForBuyCustomer(
						assignmentFilter.getBuyCustomer(),
						assignmentFilter.getState()));

		Integer frequency = assignmentFilter.getFrequency();
		HashSet<Integer> dayList = assignmentFilter.getDayList();
		HashSet<Integer> monthWeekList = assignmentFilter.getMonthWeekList();

		List<User> users = new ArrayList<User>();
		users.add(assignmentFilter.getUser());
		List<CustomerSite> sellCustomerSites = null;
		if (assignmentFilter.getDestination() != null) {
			sellCustomerSites = new ArrayList<CustomerSite>(
					masterDataDAO.getAllCustomerSitesForBuyCustomer(
							assignmentFilter.getDestination(),
							assignmentFilter.getState()));

		}

		Supplier supplier = assignmentFilter.getSupplier();
		Set<BalePickupAssignment> assignments = new HashSet<BalePickupAssignment>();
		Set<BalePickupAssignment> newAssignments = new HashSet<BalePickupAssignment>();
		assignments = balePickupAssignment.getAllAssignmentForActivity(
				buyCustomerSites, users, sellCustomerSites, supplier,
				frequency, dayList, monthWeekList);

		for (BalePickupAssignment assignment : assignments) {

			boolean isCompleted = false;
			List<BalePickupTrip> trips = balePickupTripDAO
					.getTripsForAssignment(assignment
							.getBalePickupAssignmentId());

			if (!isCompleted) {

				if (assignment.getFrequency() != 3) {
					assignment.setNextPickupDate(BaleUtils
							.convertWeekNumberAndDayToDate(assignment
									.getWeekNumber(), assignment.getDay(),
									assignment.getBuyCustomerSite()
											.getBalePickupStartDate()));
				} else {
					DateFormat _dateFormat = new SimpleDateFormat("MM/dd/yyyy");
					Date date = new Date();
					assignment.setNextPickupDate(_dateFormat.format(date));
				}

				newAssignments.add(assignment);
			}
		}

	
		return newAssignments;
	}

	// by deepak for Store Assignment List
	@Override
	public List<StoreSupplierListView> getSupplierStoreList(
			AssignDriverDTO assignDriverDTO) {
		List<StoreSupplierListView> storesupplierlist = new ArrayList<StoreSupplierListView>();

		storesupplierlist = masterDataDAO
				.getStoreSupplierViewList(assignDriverDTO);

		return storesupplierlist;
	}

	// end

	@Override
	public void updateBalePickupAssignmentstoAssignActivity(
			List<BalePickupAssignment> assignments, UserDTO user) {
		for (BalePickupAssignment assignment : assignments) {

			assignment.setUpdatedAt(new Date());
			assignment.setUpdatedBy(user.getFirstName());
			balePickupAssignment.merge(assignment);
		}

	}

	@Override
	public HashSet<Destination> getAllPickupsForOnCall(
			OnCallInputPayload inputPayLoad) {
		List<BalePickupAssignment> assignments = null;

		assignments = balePickupAssignment.getAllOnCallAssignments();

		List<PickupDetails> pickupDetailsList = new ArrayList<PickupDetails>();
		HashSet<Destination> destinations = new HashSet<Destination>();
		loop1: for (BalePickupAssignment assignment : assignments) {

			PickupDetails pickUpDetails = new PickupDetails();

			pickUpDetails.setStoreId(assignment.getBuyCustomerSite()
					.getCustomerSiteId());

			pickUpDetails.setStoreName(assignment.getBuyCustomerSite()
					.getSiteName());
			Destination destination = new Destination();
			if (assignment.getSellCustomerSite() != null)
				destination.setDestinationId(assignment.getSellCustomerSite()
						.getCustomerSiteId());
			pickUpDetails.setDestination(destination);
			String address = assignment.getBuyCustomerSite().getLocation()
					.getHouseNumber()
					+ " "
					+

					assignment.getBuyCustomerSite().getLocation().getAddress3()
					+ " "
					+ assignment.getBuyCustomerSite().getLocation()
							.getAddress4()
					+ " "
					+ assignment.getBuyCustomerSite().getLocation()
							.getAddress5()
					+ " "
					+ assignment.getBuyCustomerSite().getLocation()
							.getPostCode();
			pickUpDetails.setAddress(address);

			pickUpDetails.setPickupDate(inputPayLoad.getOnCallDate());

			pickupDetailsList.add(pickUpDetails);

		}
		if (assignments != null) {
			// assignments = removeDuplicateSellCustomers(assignments);
			for (BalePickupAssignment assignment : assignments) {

				Destination destination = new Destination();
				HashSet<PickupDetails> pickupsForDestination = new HashSet<PickupDetails>();
				if (assignment.getSellCustomerSite() != null)
					destination.setName(assignment.getSellCustomerSite()
							.getSiteName());
				if (assignment.getSellCustomerSite() != null)
					destination.setDestinationId(assignment
							.getSellCustomerSite().getCustomerSiteId());

				for (PickupDetails pickupDetails : pickupDetailsList) {

					if (pickupDetails.getDestination().getDestinationId() == destination
							.getDestinationId()) {
						pickupsForDestination.add(pickupDetails);
					}
				}
				// destination.setPickupDetails(pickupsForDestination);

				destinations.add(destination);

			}
			destinations = removeDestinationsWithoutDestination(destinations);
		}

		return destinations;
	}

	public List<PickupDetails> getAllPickupsForOnCallNew(
			OnCallInputPayload inputPayLoad) {

		String date = inputPayLoad.getOnCallDate();
		Integer userId = inputPayLoad.getUserId();
		List<DriverPickups> allDriverPickups = new ArrayList<DriverPickups>();
		List<PickupDetails> pickupDetailsList = new ArrayList<PickupDetails>();

		try {
			Integer day = -1;
			Integer weekNumber = -1;
			List<DriverPickups> driverPickups = balePickupAssignment
					.getDriverPickupsByDate(userId, date, day, weekNumber);

			if (driverPickups != null && driverPickups.size() > 0) {
				allDriverPickups.addAll(driverPickups);
			}
		} catch (Exception e) {
			LOGGER.error("Error  : getAllPickupsForOnCallNew == > " + e);
		}

		if (allDriverPickups.size() > 0) {
			for (DriverPickups driverPickup : allDriverPickups) {
				PickupDetails pickupDetails = new PickupDetails();
				pickupDetails.setStoreId(driverPickup.getBuyCustomerSiteId());

				pickupDetails.setStoreName(driverPickup
						.getBuyCustomerSiteName());

				String address = driverPickup.getBuyCustomerHouseNumber() + " "
						+ driverPickup.getBuyCustomerAddress3() + " "
						+ driverPickup.getBuyCustomerAddress4() + " "
						+ driverPickup.getBuyCustomerAddress5() + " "
						+ driverPickup.getBuyCustomerPostCode();

				pickupDetails.setAddress(address);
				pickupDetails.setPickupDate(driverPickup.getPickupDate());

				Destination destination = new Destination();
				destination.setDestinationId(driverPickup
						.getSellCustomerSiteId());

				pickupDetails.setDestination(destination);
				// pickupDetails.setIncident(driverPickup);
				// pickupDetails.setDestinationDrop(driverPickup);

				pickupDetailsList.add(pickupDetails);
			}
		}

		return pickupDetailsList;
	}

	private HashSet<Destination> removeDestinationsWithoutDestination(
			HashSet<Destination> destinations) {
		HashSet<Destination> destinationTemp = new HashSet<Destination>();
		for (Destination destination : destinations) {
			if (destination.getDestinationId() == null) {
				destinationTemp.add(destination);
			}
		}
		for (Destination destination : destinationTemp) {
			destinations.remove(destination);
		}
		return destinations;
	}

	private String convertWeekNumberAndDayToDate(int weekNumber, int day,
			Date startDate) {

		if (startDate == null) {
			startDate = new Date("01/02/1970");
		}

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		if (day == 7) {
			day = 0;
			weekNumber = weekNumber + 1;
		}
		cal.set(Calendar.WEEK_OF_MONTH, weekNumber);
		boolean entryFlag = false;
		cal.set(Calendar.MONTH, cal.getTime().getMonth());
		cal.set(Calendar.DAY_OF_WEEK, day + 1);
		String dateToreturn = sdf.format(cal.getTime());
		Calendar startDateCalendar = Calendar.getInstance();
		Calendar todaysDatecalendar = Calendar.getInstance();
		Calendar dateToReturnCalendar = Calendar.getInstance();
		if (startDate.after(new Date())) {
			if (startDate.after(new Date(dateToreturn))) {
				cal.set(Calendar.WEEK_OF_MONTH, weekNumber);
				startDateCalendar.setTime(startDate);
				cal.set(Calendar.MONTH,
						startDateCalendar.getTime().getMonth() + 1);
				cal.set(Calendar.DAY_OF_WEEK, day + 1);
				dateToreturn = sdf.format(cal.getTime());

			}

		}

		if ((startDate.before(new Date()) || startDate.equals(new Date()))) {

			if (new Date().getMonth() == (new Date(dateToreturn)).getMonth()
					&& new Date().getYear() == (new Date(dateToreturn))
							.getYear()
					&& new Date().getDate() == (new Date(dateToreturn))
							.getDate()) {

				cal.set(Calendar.WEEK_OF_MONTH, weekNumber);
				todaysDatecalendar.setTime(new Date());
				cal.set(Calendar.MONTH, todaysDatecalendar.getTime().getMonth());
				cal.set(Calendar.DAY_OF_WEEK, day + 1);
				dateToreturn = sdf.format(cal.getTime());
			}

			else if (new Date().after(new Date(dateToreturn))) {

				cal.set(Calendar.WEEK_OF_MONTH, weekNumber);
				todaysDatecalendar.setTime(new Date());
				cal.set(Calendar.MONTH,
						todaysDatecalendar.getTime().getMonth() + 1);
				cal.set(Calendar.DAY_OF_WEEK, day + 1);
				dateToreturn = sdf.format(cal.getTime());

			}

		}

		return dateToreturn;

	}

	@Override
	public Set<BalePickupAssignment> getPickupAssignmentsForCustomerSite(
			CustomerSite customerSite) {
		HashSet<BalePickupAssignment> assignments = balePickupAssignment
				.getPickupAssignmentsForCustomerSite(customerSite);
		return assignments;
	}

	// for all bale pick ups
	@Override
	public List<BalePickupSummaryView> getPickUps(
			BalePickupFilterDTO balePickupFilter) {

		List<BalePickupSummaryView> assignments = new ArrayList<BalePickupSummaryView>();
		assignments = balePickupAssignment.getAllPickups(balePickupFilter);
		return assignments;
	}

	@Override
	public List<BalePickupSummaryView> getPickUpsForDAR(
			BalePickupFilterDTO balePickupFilter) {

		List<BalePickupSummaryView> assignments = new ArrayList<BalePickupSummaryView>();
		assignments = balePickupAssignment
				.getAllPickupsForDAR(balePickupFilter);
		return assignments;
	}

	@Override
	public List<Frequency> getAllFrequency() {
		List<Frequency> list = balePickupAssignment.getAllFrequencies();
		
		Iterator<Frequency> it = list.iterator();
		while (it.hasNext()) {
			if (it.next().getFrequencyId() == 1) { // not to iterate oncall
				it.remove();
			}
		}
		return list;
	}

	@Override
	public Set<PendingReportDTO> getAllPendingPickups(
			PendingStoreReportDTO pendingStoreReportDTO) {
		Set<PendingReportDTO> pendingreports = new HashSet<PendingReportDTO>();
		Set<PendingReportDTO> allPendingReports = new HashSet<PendingReportDTO>();
		List<Frequency> frequency = getAllFrequency();
		
		String startDate = null;
		String endDate = null;

		try {
		

			Integer buyCustomerId = pendingStoreReportDTO.getBuyCustomerId();
			Integer supplierId = pendingStoreReportDTO.getSupplierId();
			Integer currentFrequency;
			Iterator<Frequency> itrerator = frequency.iterator();
			while (itrerator.hasNext()) {
				currentFrequency = itrerator.next().getFrequencyId();
				if (currentFrequency == 2) {
					List<String> dateList = BaleUtils.getLastOneWeekDates();
					
					startDate = dateList.get(0);
					endDate = dateList.get(1);
					pendingreports = balePickupAssignment.getPendingPickups(
							buyCustomerId, supplierId, currentFrequency,
							startDate, endDate);
					allPendingReports.addAll(pendingreports);
				} else if (currentFrequency == 3) {
					List<String> dateList = BaleUtils.getLastTwoWeekDates();
					
					startDate = dateList.get(0);
					endDate = dateList.get(1);
					pendingreports = balePickupAssignment.getPendingPickups(
							buyCustomerId, supplierId, currentFrequency,
							startDate, endDate);
					allPendingReports.addAll(pendingreports);
				} else if (currentFrequency == 4) {
					List<String> dateList = BaleUtils.getLastMonthDates();
					
					startDate = dateList.get(0);
					endDate = dateList.get(1);
					pendingreports = balePickupAssignment.getPendingPickups(
							buyCustomerId, supplierId, currentFrequency,
							startDate, endDate);
					allPendingReports.addAll(pendingreports);

				}

			}
			if (!pendingreports.isEmpty()) {
				allPendingReports.addAll(pendingreports);
			}

		} catch (Exception e) {
			LOGGER.error("==Exception in BalePickUpServiceIMPL==" + e);

		}

		return allPendingReports;

	}

	// for all bale pick ups
	@Override
	public List<String> getBalePickupImages(Integer balePickupId) {

		List<String> byteArrayList = new ArrayList<String>();
		try {
			LinkedHashSet<Image> balePickupImages = balePickupAssignment
					.getBalePickupImages(balePickupId);

			List<Image> imageList = new ArrayList<Image>(balePickupImages);
			byteArrayList = new ArrayList<String>();

			if (imageList.size() > 0) {
				for (Image image : imageList) {
					String filePath = "/brtaresources" + image.getPath();
					String encodedString = BaleUtils
							.encodedBase64String(filePath);
					if (encodedString.length() > 0) {
						byteArrayList.add(encodedString);
					}
				}
			}
		} catch (Exception e) {
			LOGGER.error("===exception while filtering Images====");
		}

		

		return byteArrayList;
	}

	@Override
	public String assignNewDriver(BalePickupAssignment assignment)
			throws Exception {
		String status = null;
		if (assignment == null) {
			status = ApplicationCommonConstants.INSERTION_FAILURE_MESSAGE;
		}
		balePickupAssignment.mergeAssignment(assignment);
		status = ApplicationCommonConstants.INSERTION_SUCCESSFULL_MESSAGE;
		return status;
	}

	@Override
	public Map<Integer, String> createPickupDetailsObject(
			PickupDetailsFromExcel pickupDetailsFromExcel) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		PickupDetails pickupDetails = new PickupDetails();
		Boolean checkStatus = false;
		Boolean checkcustomerSiteStatus = false;
		Integer tripId = 0;
		Integer materialExcelSC=0;
		Material material = null;
		int supplierId = 0;
		List<User> user = null;
		String firstName = null;
		String lastName = null;
		Customer buyCustomers = null;
		Boolean checkdriverforsupplier = false;
		Supplier suppliersFromFE = null;
		String[] dateParts = null;
		String[] dekiverydateParts = null;

		//System.out.println("From Excel  " + pickupDetailsFromExcel.toString());
		try {

			try {
				if (pickupDetailsFromExcel.getdestinationGross() != null && pickupDetailsFromExcel.getdestinationTare() != null) {
					pickupDetailsFromExcel.setdestinationGross((pickupDetailsFromExcel.getdestinationGross().intValue()));
					pickupDetailsFromExcel.setdestinationTare((pickupDetailsFromExcel.getdestinationTare().intValue()));
				}
			} catch (Exception e) {
				map.put(0,
						"Please enter digits in DestinationGross & DestinationTare ");
				LOGGER.error("Error in parsing gross and tare weight to int--------->"+ e);
				return map;
			}

			try {
				if (pickupDetailsFromExcel.getdestinationGross() != null
						&& pickupDetailsFromExcel.getdestinationTare() != null) {
					if (pickupDetailsFromExcel.getdestinationGross() < pickupDetailsFromExcel
							.getdestinationTare()) {
						map.put(0,"Gross Weight should greater than Tare Weight");
						return map;
					}
				}
			} catch (Exception e) {

				map.put(0,"Error in Gross & Tare weight while comparing both.!");
				return map;
			}

			buyCustomers = masterDataDAO.getAllBuyCustomerByName(pickupDetailsFromExcel.getBuyCustomer().trim());
			

			if (buyCustomers == null) {
				map.put(0, "Buy Customer not found in Database");
				return map;

			}

			CustomerSite customerSite = customerSiteDAO.getSellCustomerSiteFromName(pickupDetailsFromExcel.getStoreName().trim(), 
					buyCustomers.getCustomerId());

			if (customerSite != null) {

				if (customerSiteDAO.getCustomerAndCustomerSiteRelatedOrNot(buyCustomers.getCustomerId(),customerSite.getCustomerSiteId())) {

					pickupDetails.setStoreId(customerSite.getCustomerSiteId());
					pickupDetails.setStoreName(pickupDetailsFromExcel.getStoreName().trim());
					checkcustomerSiteStatus = true;

					Set<Material> materials = balePickupMaterialConfigurationDAO
							.getAllMaterialFromCustomerSite(customerSite.getCustomerSiteId());

					if (!materials.isEmpty()) {

						for (Material materialObj : materials) {
							if (materialObj.getDescription().equals(pickupDetailsFromExcel.getMaterialProfile().trim())) {
								material = materialObj;
								checkStatus = true;
								break;
							}

						}

						if (material == null) {
							map.put(0, "Material not found");
							return map;
						}
					}
				} else {
					map.put(0, "Store is not related to Buy Customer.");
					return map;
				}

			} else {
				map.put(0, "Store name not found");
				return map;
			}
			if (pickupDetailsFromExcel.getIncident() != null && pickupDetailsFromExcel.getIncident() != "") {
				
				IncidentType incident = incidentTypeDao.getIncidentbyName(pickupDetailsFromExcel.getIncident().trim());

				if (incident == null) {
					map.put(0, "Incident Name not found in Database");
					return map;
				} else {
					pickupDetails.setIncidentType(incident);
				}
			}

			suppliersFromFE = masterDataDAO.getSupplierFromSupplierName(pickupDetailsFromExcel.getSupplierName().trim());

			if (suppliersFromFE == null) {
				map.put(0, "Supplier Name does not match records.");
				return map;
			}

			supplierId = suppliersFromFE.getSupplierId();
				
			try {
				if (!masterDataDAO.getSupplierAndCustomerSiteRelatedOrNot(supplierId, customerSite.getCustomerSiteId())) {
					map.put(0, "Customer Site is not related to Supplier.");
					return map;
				}

			} catch (Exception e) {
				LOGGER.error("==Error while getting Supplier Config===="	+ e);
			}

			try {
				String[] driverName = pickupDetailsFromExcel.getDriverName().trim().split("\\s+");
				firstName = driverName[0];
				lastName = driverName[1];

			} catch (Exception e) {
				LOGGER.error("Error while parsing user name \n" + e);
				map.put(0, "Please enter both First Name and Last Name");
				return map;
			}

			
			user = masterDataDAO.getUserFromUserName(firstName, lastName);
			

			if (user != null && user.size() > 0) {
				for (User userdetails : user) {
					if (userdetails.getSupplier().getSupplierId() == supplierId) {
						checkStatus = true;
						pickupDetails.setUserId(userdetails.getUserId().intValue());
						checkdriverforsupplier = true;
						break;
					}
				}
			} else {
				map.put(0, "User not found in Database");
				return map;
			}

			if (!checkdriverforsupplier) {
				map.put(0, "Driver is not related to Supplier");
				return map;
			}

			if (checkStatus && checkcustomerSiteStatus) {

				List<Commodity> commodities = new ArrayList<Commodity>();
				Commodity commodity = new Commodity();
				commodity.setMaterialId(material.getMaterialId());
				materialExcelSC = material.getMaterialId();
				commodity.setShortName(material.getShortName());
				commodity.setBalesPicked(pickupDetailsFromExcel
						.getBalesPicked());
				commodity.setBalesRemaining(pickupDetailsFromExcel
						.getBalesRemaining());
				commodities.add(commodity);

				pickupDetails.setMaterialList(commodities);
				String pickDate = "";

				try {
					pickDate = pickupDetailsFromExcel.getPickupDate();
					if ((pickDate.contains("-"))) {
						map.put(0," Please enter the date in MM/DD/YYYY format! Exp 10/25/2017");
						return map;
					}

					try {
						dateParts = pickDate.trim().split("/");

						if (dateParts[0] != null && dateParts[1] != null && dateParts[2] != null) {

						}

					} catch (Exception e) {
						map.put(0,
								" Please enter the date in MM/DD/YYYY format! Exp 10/25/2017");
						LOGGER.error(" Error while parsing piuckupdate using /.\n   "+ e);
						return map;
					}
					try {
						
						Integer monthinInt = Integer.valueOf(dateParts[0]);// 2
						Integer dayinInt = Integer.valueOf(dateParts[1]);// 27
						Integer yearinInt = Integer.valueOf(dateParts[2]);// 18
					
						String month = dateParts[0];
						String day = dateParts[1];
						String year = dateParts[2];
						
						if (month.matches("[0-9]+") && day.matches("[0-9]+")
								&& year.matches("[0-9]+")) {

							if (month.length() < 2)
								month = "0" + month;
						
							if (day.length() < 2)
								day = "0" + day;
						
							if (year.length() == 2) {
								year = "20" + year;
								yearinInt = Integer.valueOf(year);
								
							}
							
							if (year.length() != 4) {
								map.put(0," Please enter the year in YYYY format! Exp 12/24/2017");
								return map;
							}

							if (dayinInt > 31 || dayinInt < 1) {
								map.put(0, " Please enter valid day in date.");
								return map;
							}
							if (monthinInt > 12 || monthinInt < 1) {
								map.put(0, " Please enter valid month in date.");
								return map;
							}
							int currentYear = Calendar.getInstance().get(Calendar.YEAR);
							
						
							if (((yearinInt-currentYear)>10)||((currentYear-yearinInt)>10)){
								map.put(0,
										" Please enter valid pickup date upto 10 years from current date");
								
								return map;
							}
							pickDate = month + "/" + day + "/" + year;
							pickupDetails.setPickupDate(pickDate);
						} else {
							
							map.put(0," Please enter digits in date columns format! Exp 12/24/2017");
							return map;
						}
					} catch (Exception e) {
						LOGGER.error("Error while converting string to int in pickup date in manual upload /n "+ e);
						map.put(0," Please enter digits in date columns format! Exp 12/29/2017");
						return map;
					}
				} catch (Exception e) {
					
					map.put(0," Please enter the date in MM/DD/YYYY format! Exp 02/30/2017");
					return map;
				}

				pickupDetails.setBalesPicked(pickupDetailsFromExcel.getBalesPicked());
				pickupDetails.setBalesRemaining(pickupDetailsFromExcel.getBalesRemaining());
				pickupDetails.setBOL(pickupDetailsFromExcel.getBol());

				if (pickupDetailsFromExcel.getComments() != null) {
					pickupDetails.setComments(pickupDetailsFromExcel
							.getComments().trim());
				}

				
				
				if (pickupDetailsFromExcel.getSellCUstomer() != null && pickupDetailsFromExcel.getSellCustomerLocation() != null) {
					CompleteTripInputPayLoad completeTripPayLoad = new CompleteTripInputPayLoad();

					Customer sellCustomer = masterDataDAO.getAllBuyCustomerByName(pickupDetailsFromExcel.getSellCUstomer().trim());
					
					if (sellCustomer != null) {

						CustomerSite sellCustomerSite = customerSiteDAO.getSellCustomerSiteFromName(pickupDetailsFromExcel.getSellCustomerLocation().trim(),
								sellCustomer.getCustomerId());
						if (sellCustomerSite != null) {

							if (!customerSiteDAO.getCustomerAndCustomerSiteRelatedOrNot(sellCustomer.getCustomerId(),sellCustomerSite.getCustomerSiteId())) {
								map.put(0," Sell CustomerSite is not related to Sell Customer.");
								return map;
							}
						} else {
							map.put(0,"Sell Customer Location not found in database.");
							return map;
						}
						
						boolean mappingStatus = masterDataDAO.checkBuyCustSellCustSuppAvailability(buyCustomers.getCustomerId(),sellCustomer.getCustomerId(), supplierId);
								
								
						if(!mappingStatus){
							map.put(0,"Buy CUstomer,SellCustomer and Supplier are not related!");
							return map;
						}
						
						
						if (pickupDetailsFromExcel.getDeliveryDate() != null) {
							String excelDeliveryDate = pickupDetailsFromExcel.getDeliveryDate();
							String deliveryDate = "";

							if ((excelDeliveryDate.contains("-"))) {
							
								map.put(0," Please enter the date in MM/DD/YYYY format! Exp 10/25/2017");
								return map;
							}

							try {
								dekiverydateParts = excelDeliveryDate.trim().split("/");

								if (dekiverydateParts[0] != null && dekiverydateParts[1] != null && dekiverydateParts[2] != null) {

								}

							} catch (Exception e) {
								map.put(0," Please enter the date in MM/DD/YYYY format! Exp 10/25/2017");

							
								return map;
							}
							try {

								String month = dekiverydateParts[0];
								String day = dekiverydateParts[1];
								String year = dekiverydateParts[2];
								

								if (month.matches("[0-9]+") && day.matches("[0-9]+") && year.matches("[0-9]+")) {

									if (month.length() < 2)
										month = "0" + month;
									if (day.length() < 2)
										day = "0" + day;
									if (year.length() == 2)
										year = "20" + year;

									if (year.length() != 4) {
										map.put(0," Please enter the year in YYYY format ! Exp 05/09/2017");
										return map;
									}
									try {
										Integer dayinInt = Integer.valueOf(day);
										Integer monthinInt = Integer.valueOf(month);
										Integer yearinInt = Integer.valueOf(year);
										
										if (dayinInt > 31 || dayinInt < 1) {
											map.put(0," Please enter valid day in date");
											return map;
										}
										if (monthinInt > 12 || monthinInt < 1) {
											map.put(0," Please enter valid month in date");
											return map;
										}
										int currentYeardel = Calendar.getInstance().get(Calendar.YEAR);
										if (((yearinInt-currentYeardel)>10)||((currentYeardel-yearinInt)>10)){
											map.put(0,
													" Please enter valid delivery date upto 10 years from current date");
											return map;
										
										}
									} catch (Exception e) {
										
										map.put(0,"Please enter digits in date columns format! Exp 12/25/2017");
										return map;
									}
								} else {
									map.put(0," Please enter digits in date columns format! Exp 12/25/2017");
									return map;
								}
								deliveryDate = month + "/" + day + "/" + year;
							} catch (Exception e) {
								
								map.put(0," Please enter the date in MM/DD/YYYY format! Exp 10/25/2017");
								return map;
							}

							completeTripPayLoad.setDeliveryDate(deliveryDate);
							if (pickupDetailsFromExcel.getReleaseNumber() != null) {
								completeTripPayLoad.setReleaseNo(pickupDetailsFromExcel.getReleaseNumber().trim());
							}
									
							completeTripPayLoad.setDestinationId(sellCustomerSite.getCustomerSiteId());
							completeTripPayLoad.setUserId(pickupDetails.getUserId());
							completeTripPayLoad.setGrossWeight(pickupDetailsFromExcel.getdestinationGross());
							completeTripPayLoad.setTareWeight(pickupDetailsFromExcel.getdestinationTare());
							completeTripPayLoad.setDate(pickDate);
							List<Integer> list = new ArrayList<Integer>();
							list.add(pickupDetails.getStoreId());
							completeTripPayLoad.setStoreIdList(list);
							
							
							
							tripId = baleRouteTripService.addTripDetailsNew(pickupDetails);
							

							if (tripId > 0){
								completeTripPayLoad.setTripId(tripId);
								baleRouteTripService.completeTrip(completeTripPayLoad);
							}else{

								
								map.put(0," Unable to save trip,TripID not generated."); //replaced 1 by 0

								return map;

							}

						} else {
							map.put(0," Unable to complete trip since Delivery Date is not provided");
							
							return map;
						}
					} else {
						map.put(0, " Sell Customer does not match records.");
						
						return map;
					}
				} else {
					tripId = baleRouteTripService.addTripDetailsNew(pickupDetails);	
					
					map.put(1,"Data saved but trip incomplete; Sell Customer Site requires input.");
					
					return map;
				}

			} else {
				map.put(0," Data not saved, please validate mandatory fields are provided.");
			
				return map;
			}

		} catch (Exception e) {
			map.put(0,"Exception Occured while saving Data Please check all fields have correct value");

			
			return map;
		}
		map.put(1, "Data Saved Successfully.");
		return map;
		
	}

	private String convertWeekNumberAndDayToDate(int weekNumber, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		if (day == 7) {
			day = 0;
			weekNumber = weekNumber + 1;
		}
		cal.set(Calendar.WEEK_OF_MONTH, weekNumber);
		cal.set(Calendar.MONTH, cal.getTime().getMonth());
		cal.set(Calendar.DAY_OF_WEEK, day + 1);
		String dateToreturn = sdf.format(cal.getTime());
		return dateToreturn;

	}

	@Override
	public String removeNewDriver(BalePickupAssignment assignment) {
		assignment.setDivertDate(null);
		assignment.setDivertedUser(null);
		balePickupAssignment.merge(assignment);
		return ApplicationCommonConstants.INSERTION_SUCCESSFULL_MESSAGE;
	}

	@Override
	public String getBalePickupImagesFromName(String imageName) {

		String filePath = "/brtaresources" + imageName;
		
		String encodedString = BaleUtils.encodedBase64String(filePath);
		
		return encodedString;
	}

	@Override
	public Boolean updateStoreSupplierDriver(
			GenerateAssignmentDriverPopup generateAssignmentDriverPopup) {
		Boolean status = false;

		int assignment;
		assignment = balePickupAssignment
				.updateDriverForStoreSupplierList(generateAssignmentDriverPopup);

		if (assignment > 0) {
			status = true;
		}

		return status;

	}

	@Override
	public void updateAssignmentDates(
			GenerateAssignmentDriverPopup generateAssignmentDriverPopup) {
		Boolean status = false;

		int assignment;
		assignment = balePickupAssignment
				.updateAssignmentDates(generateAssignmentDriverPopup);

	}

	@Override
	public List<DriverSupplierPickupsView> showDriverDashboard(
			AssignDriverDTO assignDriverDTO) {
		List<DriverSupplierPickupsView> assignments = balePickupAssignment
				.getAssignmentDrivers(assignDriverDTO);
		return assignments;

	}

	@Override
	public List<EditPendingReportDetails> getAllBalePickupDates(
			EditPendingReportDTO editPendingReportDTO) {

		List<EditPendingReportDetails> editPendingReportDTOs = balePickupTripDAO
				.getAllBalePickupDates(editPendingReportDTO);
		return editPendingReportDTOs;
	}

	@Override
	public Map savePickupFromPendingStore(
			SaveBalePickupFromPendingReport saveBalePickupFromPendingReport) {

		CustomerSite buyCustomerSite = customerSiteDAO
				.getCustomerSiteFromId(saveBalePickupFromPendingReport
						.getBuyCustomerSiteId());
		User user = userDao.getUserObjectFromUserId(Long
				.valueOf(saveBalePickupFromPendingReport.getDriverId()));
		Material material = materialDao
				.getMaterialFromId(saveBalePickupFromPendingReport
						.getMaterialId());
		Supplier supplier = masterDataDAO
				.getSupplierFromSupplierId(saveBalePickupFromPendingReport
						.getSupplierId());

		BalePickupMaterialConfiguration balePickupMaterialConfiguration = null;
		if (material != null && buyCustomerSite != null) {
			balePickupMaterialConfiguration = masterDataDAO
					.getBalePickupMaterialConfigurationForMaterial(
							buyCustomerSite.getCustomerSiteId(),
							material.getMaterialId());

		}
		

		Date pickupDate = new Date();

		String frequency = saveBalePickupFromPendingReport.getFrequency();
		Integer frequencyId = saveBalePickupFromPendingReport.getFrequencyId();
		Integer frequencyDay = saveBalePickupFromPendingReport
				.getFrequencyDay();
		// List<Frequency> allFrequency=getAllFrequency();
		

		if (frequency.equalsIgnoreCase("Weekly")) { // 2
			
			pickupDate = BaleUtils.getDateBefor7Days();
		} else if (frequency.equalsIgnoreCase("Every Other Week")) { // 3
			
			pickupDate = BaleUtils.getDateBefor7Days();
		} else if (frequency.equalsIgnoreCase("Monthly")) { // 4
			
			pickupDate = BaleUtils.getLastDateOfPreviousMonth();
		}

		BalePickupTrip balePickupTrip = new BalePickupTrip();
		balePickupTrip.setBuyCustomerSite(buyCustomerSite);
		balePickupTrip.setMaterial(material);
		balePickupTrip.setSupplier(supplier);
		balePickupTrip.setPickupDate(pickupDate);
		balePickupTrip.setBalesPicked(0);
		balePickupTrip.setBOL(0);
		balePickupTrip.setUser(user);
		balePickupTrip
				.setPendingResolutionTripId(saveBalePickupFromPendingReport
						.getTripId());
		balePickupTrip
				.setPendingResolutionComments(saveBalePickupFromPendingReport
						.getComment());
		;
		balePickupTrip.setUpdatedAt(new Date());
		balePickupTrip.setUpdatedBy(user.getFirstName());
		balePickupTrip.setEnabled(true);
		balePickupTrip.setCreateDate(new Date());

		if (balePickupMaterialConfiguration != null) {
			balePickupTrip.setAvgBaleWeight(balePickupMaterialConfiguration
					.getAvgBaleWeight());
		} else {
			balePickupTrip.setAvgBaleWeight(1);
		}

		Integer tripId = balePickupTripDAO.saveTripDetails(balePickupTrip);

	

		Map returnStatus = new HashMap();

		if (tripId != null && tripId > 0) {
			returnStatus.put("status", "Successful");
		} else {
			returnStatus.put("status", "Fail");

		}

		return returnStatus;

	}

	
	public Integer deleteSCTripRecord(BalePickupMobileDetailsForSC sc)
	{
		Integer id=0;
		id= balePickupTripDAO.deleteSCTripRecord(sc);
		return id;
	}
}
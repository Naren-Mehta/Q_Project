package com.wm.brta.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import com.wm.brta.constants.ServiceChannelEnumConstants;
import com.wm.brta.dao.BalePickUpAssignmentDAO;
import com.wm.brta.dao.BalePickupTripDAO;
import com.wm.brta.dao.ConfigurationDao;
import com.wm.brta.dao.CustomerSiteDAO;
import com.wm.brta.dao.IncidentTypeDao;
import com.wm.brta.dao.MasterDataDao;
import com.wm.brta.dao.UserDao;
import com.wm.brta.domain.BalePickupAssignment;
import com.wm.brta.domain.BalePickupMaterialConfiguration;
import com.wm.brta.domain.BalePickupTrip;
import com.wm.brta.domain.BalePickupTripDetailsForSC;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.Image;
import com.wm.brta.domain.Material;
import com.wm.brta.domain.MaterialInfo;
import com.wm.brta.domain.SavedPickups;
import com.wm.brta.domain.StoreSupplierListView;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.User;
import com.wm.brta.dto.ActivityListInputPayload;
import com.wm.brta.dto.BalePickupMobileDetailsForSC;
import com.wm.brta.dto.Commodity;
import com.wm.brta.dto.CompleteTripInputPayLoad;
import com.wm.brta.dto.Destination;
import com.wm.brta.dto.PickupDetails;
import com.wm.brta.service.BaleRouteTripService;
import com.wm.brta.util.BaleUtils;

@Transactional
@Component("baleRouteTripService")
public class BaleRouteTripServiceImpl implements BaleRouteTripService {

	@Autowired
	BalePickUpAssignmentDAO balePickupAssignment;

	@Autowired
	IncidentTypeDao incidentType;

	@Autowired
	MasterDataDao masterDataDAO;

	@Autowired
	BalePickupTripDAO balePickupTripDAO;

	@Autowired
	CustomerSiteDAO customerSiteDAO;

	@Autowired
	ConfigurationDao configDao;

	@Autowired
	UserDao userDao;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BalePickupServiceImpl.class);
	
	
	

	@Override
	public Integer saveCheckInCheckOut(BalePickupMobileDetailsForSC balePickupMobileDetailsForSC) {

		
		
		Integer saveOrUpdate = 0;
		String resolution="";
		BalePickupTripDetailsForSC sc =null;
		try{
		Set<BalePickupTripDetailsForSC> resultAssignments = null;
		User userforSupplier = userDao.getUserById(Long.valueOf(balePickupMobileDetailsForSC.getDriver()));
		Supplier supplier = userforSupplier.getSupplier();
		balePickupMobileDetailsForSC.setSupplierId(supplier.getSupplierId());
		ServiceChannelEnumConstants status = BaleUtils.getStatus(balePickupMobileDetailsForSC);
		balePickupMobileDetailsForSC.setStatus(status);
		
			
			if(balePickupMobileDetailsForSC.getIncidentNotes()==null){
				balePickupMobileDetailsForSC.setIncidentNotes("");
			}
	
			if(balePickupMobileDetailsForSC.getIncidentDescription()==null){
				balePickupMobileDetailsForSC.setIncidentDescription("");
			}
		resolution = "Bales Picked:"+ balePickupMobileDetailsForSC.getBalesPicked() +","
				+ " Bales Remaining: "+balePickupMobileDetailsForSC.getBalesRemaining() +","
				+ balePickupMobileDetailsForSC.getIncidentDescription()+","+balePickupMobileDetailsForSC.getIncidentNotes();
		
		try {
			resultAssignments = balePickupTripDAO.checkSCTripRecordExist(balePickupMobileDetailsForSC);

			if (resultAssignments != null) {

				BalePickupTripDetailsForSC first = resultAssignments.iterator().next();
				if (first.getCheckinDateTime() != null && (first.getCheckoutDateTime() != null || (balePickupMobileDetailsForSC
								.getCheckoutDate() != null && balePickupMobileDetailsForSC.getCheckoutDate() != ""))) {

					if (balePickupMobileDetailsForSC.getBalesPicked() > 0 && balePickupMobileDetailsForSC.getBalesRemaining()< 10) {
						balePickupMobileDetailsForSC.setStatus(ServiceChannelEnumConstants.Complete);
					} else {
						balePickupMobileDetailsForSC.setStatus(ServiceChannelEnumConstants.INCOMPLETE);
					}

				} else if (first.getCheckinDateTime() != null && first.getCheckoutDateTime() == null) {
					balePickupMobileDetailsForSC.setStatus(ServiceChannelEnumConstants.OnSite);
				}
					balePickupMobileDetailsForSC.setResolution(resolution);	
					balePickupMobileDetailsForSC.setUpdatedDevice(balePickupMobileDetailsForSC.getUpdatedDevice());
					saveOrUpdate = balePickupTripDAO.updateCheckInCheckout(balePickupMobileDetailsForSC, first.getId());
						
			} else {
				
				sc = new BalePickupTripDetailsForSC();
				sc.setStatus(status);
				sc.setBalesRemaining(balePickupMobileDetailsForSC.getBalesRemaining());
				sc.setTimeZone(balePickupMobileDetailsForSC.getTimeZone());
				if(balePickupMobileDetailsForSC.getCheckinDate()!=null && balePickupMobileDetailsForSC.getCheckinDate()!=""){
					sc.setCheckinDateTime(BaleUtils.convertStringToDateSC(balePickupMobileDetailsForSC.getCheckinDate()));
					sc.setuTCCheckinDateTime(BaleUtils.getIsoDateForServiceChannel(sc.getCheckinDateTime()));
					
					if (balePickupMobileDetailsForSC.getCheckoutDate() != null && balePickupMobileDetailsForSC.getCheckoutDate() != "") {
						sc.setCheckoutDateTime(BaleUtils.convertStringToDateSC(balePickupMobileDetailsForSC.getCheckoutDate()));
						sc.setuTCCheckoutDateTime(BaleUtils.getIsoDateForServiceChannel(sc.getCheckoutDateTime()));
					} else {
						sc.setCheckoutDateTime(null);
					}
					
				}else{
					sc.setCheckinDateTime(new Date());
					sc.setuTCCheckinDateTime(BaleUtils.getIsoDateForServiceChannel(sc.getCheckinDateTime()));
					sc.setCheckoutDateTime(null);
					//sc.setStatus(ServiceChannelEnumConstants.Incomplete);
				}
					if(balePickupMobileDetailsForSC.getBol()!=null){
						sc.setBol(balePickupMobileDetailsForSC.getBol());
				}
				if(balePickupMobileDetailsForSC.getCheckInLongitude()!=null && balePickupMobileDetailsForSC.getCheckInLatitude()!=null){
					sc.setCheckInLatitude(balePickupMobileDetailsForSC.getCheckInLatitude());
					sc.setCheckInLongitude(balePickupMobileDetailsForSC.getCheckInLongitude());
					
				}else{
					sc.setCheckInLatitude(null);
					sc.setCheckInLongitude(null);
					
				}
				sc.setStoreName(balePickupMobileDetailsForSC.getStoreName());
				sc.setBalesPicked(balePickupMobileDetailsForSC.getBalesPicked());
				Material material = new Material();
				material.setMaterialId(balePickupMobileDetailsForSC.getMaterial());
				sc.setMaterial(material);
				supplier.setSupplierId(userforSupplier.getSupplier().getSupplierId());
				sc.setSupplier(supplier);
				CustomerSite cs = new CustomerSite();
				cs.setCustomerSiteId(balePickupMobileDetailsForSC.getCustomerSite());
				sc.setCustomerSite(cs);
				User user = new User();
				user.setUserId(Long.valueOf(balePickupMobileDetailsForSC.getDriver()));
				sc.setUserId(user);
				sc.setTruckId(balePickupMobileDetailsForSC.getTruckId());
				sc.setResolution(resolution);
				sc.setUpdatedDevice(balePickupMobileDetailsForSC.getUpdatedDevice());
				
			
				saveOrUpdate = balePickupTripDAO.saveCheckInCheckOut(sc);

			}
		} catch (Exception e) {
			LOGGER.error("Exception in saveCheckInCheckOut-------------> "+ e);
			
			saveOrUpdate=0;

		}
		}catch(Exception e){
			LOGGER.error("Exception in Outer catch saveCheckInCheckOut"  +e);
			saveOrUpdate=0;
		}

		
		return saveOrUpdate;
	}

	@Override
	public HashSet<Commodity> retriveBalesCountForSC(
			BalePickupMobileDetailsForSC balePickupMobileDetailsForSC) {

		HashSet<Commodity> resultAssignments = new HashSet<Commodity>();

		BalePickupTripDetailsForSC sc = new BalePickupTripDetailsForSC();
		try {

			User userforSupplier = userDao.getUserById(Long
					.valueOf(balePickupMobileDetailsForSC.getDriver()));
			Supplier supplier = new Supplier();
			supplier.setSupplierId(userforSupplier.getSupplier()
					.getSupplierId());
			sc.setSupplier(supplier);
			CustomerSite cs = new CustomerSite();
			cs.setCustomerSiteId(balePickupMobileDetailsForSC.getCustomerSite());
			sc.setCustomerSite(cs);
			User user = new User();
			user.setUserId(Long.valueOf(balePickupMobileDetailsForSC
					.getDriver()));
			sc.setUserId(user);
			sc.setTruckId(balePickupMobileDetailsForSC.getTruckId());
			resultAssignments = balePickupTripDAO.retriveBalesRecordsForSC(sc);

		} catch (Exception e) {

		}
		return resultAssignments;
	}

	@Override
	public Integer addTripDetailsNew(PickupDetails pickupDetails) {

		int id = 0;

		String status = null;
		boolean errorFlag = false;

		User user = userDao
				.getUserById(Long.valueOf(pickupDetails.getUserId()));
		Supplier supplier = user.getSupplier();

		CustomerSite buyCustomerSite = customerSiteDAO
				.getCustomerSiteFromId(pickupDetails.getStoreId());

		List<Integer> tripIdList = new ArrayList<Integer>();
		Integer tripId = pickupDetails.getTripId();

		if (buyCustomerSite != null) {
			try {

				BalePickupMaterialConfiguration balePickupMaterialConfiguration = null;

				for (Commodity commodity : pickupDetails.getMaterialList()) {

					balePickupMaterialConfiguration = masterDataDAO
							.getBalePickupMaterialConfigurationForMaterial(
									pickupDetails.getStoreId(),
									commodity.getMaterialId());

					BalePickupTrip tripDetails = new BalePickupTrip();

					tripDetails.setBuyCustomerSite(buyCustomerSite);

					Material material = new Material();
					material.setMaterialId(commodity.getMaterialId());
					material.setDescription(commodity.getShortName());

					tripDetails.setMaterial(material);

					try {
						String dateconvert = pickupDetails.getPickupDate();
						

						tripDetails.setPickupDate(BaleUtils
								.convertStringToDate(dateconvert));
					} catch (Exception e) {
						LOGGER.error("Error occured while parsing date in BalerouteTripServiceImpl \n"
										+ e);
					}
					if (pickupDetails.getBOL() == null)
						pickupDetails.setBOL(0);
					Integer avgBaleWeight = 1;
					if (balePickupMaterialConfiguration != null) {
						avgBaleWeight = balePickupMaterialConfiguration
								.getAvgBaleWeight() != null ? balePickupMaterialConfiguration
								.getAvgBaleWeight() : 1;
					}
					tripDetails.setAvgBaleWeight(avgBaleWeight);

					Integer balePicked = commodity.getBalesPicked() != null ? commodity
							.getBalesPicked() : 0;
					Integer baleRemaining = commodity.getBalesRemaining() != null ? commodity
							.getBalesRemaining() : 0;

					tripDetails.setBalesPicked(balePicked);
					tripDetails.setBalesRemaining(baleRemaining);
					tripDetails.setBOL(pickupDetails.getBOL());
					tripDetails.setCreateDate(new Date());
					tripDetails.setSupplier(supplier);
					tripDetails.setComments(pickupDetails.getComments());
					tripDetails.setIncident(pickupDetails.isIncident());
					tripDetails
							.setIncidentType(pickupDetails.getIncidentType());
					tripDetails.setUser(user);
					tripDetails.setUpdatedAt(new Date());
					tripDetails.setUpdatedBy(user.getFirstName());
					tripDetails.setEnabled(true);

					String vehiclePlateNo = pickupDetails.getVehiclePlateNo();

					if (vehiclePlateNo != null) {
						vehiclePlateNo = vehiclePlateNo.trim();
					}

					tripDetails.setVehiclePlateNo(vehiclePlateNo);
					tripDetails.setReleaseNo(pickupDetails.getReleaseNotes());

					id = balePickupTripDAO.saveTripDetails(tripDetails);

					if (id == 0) {
						errorFlag = true;
						status = "Insertion Failure";
						break;
					} else {
						tripIdList.add(id);
					}

					if (pickupDetails.isIncident() && id != 0
							&& pickupDetails.getImages() != null
							&& pickupDetails.getImages().size() > 0) {

						try {
							balePickupTripDAO.deleteFileFromTripId(tripId);
							int count = 1;
							for (String image : pickupDetails.getImages()) {

								byte[] imageByte = Base64.decodeBase64(image);

								String path = "/INCIDENT_IMAGE_"
										+ buyCustomerSite.getCustomerSiteId()
										+ "_" + new Date().getDate() + "_"
										+ new Date().getMonth() + "_"
										+ new Date().getHours() + "_" + count;

								

								File file = new File("/brtaresources" + path);

								count = count + 1;
								FileOutputStream outputStream = new FileOutputStream(
										file);
								outputStream.write(imageByte);
								outputStream.close();

								Image imageObject = new Image();
								imageObject.setTripId(id);
								// imageObject.setImageUId(id);

								imageObject.setPath(path);
								imageObject.setCreateDate(new Date());
								imageObject.setEnabled(true);
								imageObject.setUpdatedAt(new Date());
								imageObject.setUpdatedBy("mobile client");
								balePickupTripDAO.saveImageDetails(imageObject);

							}
						} catch (Exception e) {
							LOGGER.error("exception occured while saving image"
											+ e);
							
						}

					}

				}

				if (tripId != null && id != 0) {
					balePickupTripDAO.updateFile(id, tripId);
					balePickupTripDAO.updatePendingResolutionTrip(id, tripId);
				} else if (pickupDetails.getStoreId() != null
						&& pickupDetails.getPickupDate() != null
						&& supplier.getSupplierId() != null && id != 0) {
					balePickupTripDAO.updatePendingResolutionTrip1(id,
							pickupDetails.getStoreId(),
							pickupDetails.getPickupDate(),
							supplier.getSupplierId());
				}

				if (id == 0) {
					return 0;
				}

				

				if (tripId != null && tripId > 0) {
					balePickupTripDAO.deleteFileFromTripId(tripId);
					balePickupTripDAO.deleteTripFromTripId(tripId);
				}

				else if (pickupDetails.getStoreId() != null
						&& pickupDetails.getPickupDate() != null
						&& supplier.getSupplierId() != null) {

					for (Commodity commodity : pickupDetails.getMaterialList()) {
						balePickupTripDAO.deleteFile(tripIdList,
								pickupDetails.getStoreId(),
								pickupDetails.getPickupDate(),
								supplier.getSupplierId(),
								commodity.getMaterialId());

						balePickupTripDAO.deleteTrip(tripIdList,
								pickupDetails.getStoreId(),
								pickupDetails.getPickupDate(),
								supplier.getSupplierId(),
								commodity.getMaterialId());
					}

				}

				

			} catch (Exception e) {
				LOGGER.error("Error in addTripDetailsNew of BaleRouteTripServiceImpl"+e);
				status = "Insertion Failure";
			}
		}

		if (!errorFlag)
			status = "Successfull";
		else
			status = "Insertion Failure";
		return id;
	}

	private Integer getWeekNumber(String date) throws ParseException {

		String format = "MM/dd/yyyy";

		SimpleDateFormat df = new SimpleDateFormat(format);
		Date dateFormat = df.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateFormat);

		int weeknumber = cal.get(Calendar.WEEK_OF_MONTH);

		if (cal.get(Calendar.DAY_OF_WEEK) == 1)
			weeknumber = weeknumber - 1;

		return weeknumber;
	}

	private Integer getDay(String date) throws ParseException {

		String format = "MM/dd/yyyy";

		SimpleDateFormat df = new SimpleDateFormat(format);
		Date dateFormat = df.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateFormat);

		int day = cal.get(Calendar.DAY_OF_WEEK);
		day = day - 1;
		if (day == 0)
			day = 7;

		return day;
	}

	@Override
	public void completeTrip(CompleteTripInputPayLoad completeTripPayLoad) {

		List<Integer> storeIdList = completeTripPayLoad.getStoreIdList();

		User user = userDao.getUserById(Long.valueOf(completeTripPayLoad
				.getUserId()));

		if (user != null) {
			Supplier supplier = user.getSupplier();

			for (Integer storeId : storeIdList) {
				Integer tripId = completeTripPayLoad.getTripId();
				Date pickupDate = BaleUtils
						.convertStringToDate(completeTripPayLoad.getDate());
				List<BalePickupTrip> trips = new ArrayList<BalePickupTrip>();

				if (tripId != null && tripId > 0) {
					trips = balePickupTripDAO.getTripFromTripId(tripId);
				} else {
					trips = balePickupTripDAO
							.getAllTripsFromStoreIdAndSupplierAndUser(storeId,
									supplier, user, pickupDate);
				}

				CustomerSite sellCustomerSite = null;
				if (completeTripPayLoad.getDestinationId() != null) {
					sellCustomerSite = customerSiteDAO
							.getCustomerSiteFromId(completeTripPayLoad
									.getDestinationId());
				}

				for (BalePickupTrip trip : trips) {

					trip.setReleaseNo(completeTripPayLoad.getReleaseNo());

					if (completeTripPayLoad.getDeliveryDate() != null) {
						trip.setDeliveryDate(BaleUtils
								.convertStringToDate(completeTripPayLoad
										.getDeliveryDate()));

					}
					trip.setGrossWeight(completeTripPayLoad.getGrossWeight());
					trip.setTareWeight(completeTripPayLoad.getTareWeight());
					trip.setSellCustomerSite(sellCustomerSite);
					balePickupTripDAO.completeTrip(trip);

				}
			}

		}

	}

	private String generateParentTripId(String sellCustomerSiteName, Date date,
			String firstName, String lastName, Integer assignmentId) {

		String id = "";
		String siteNamePrefix = sellCustomerSiteName.substring(0, 3)
				.toUpperCase();

		String userPrefix = lastName.substring(0, 1).toUpperCase()
				+ firstName.substring(0, 1).toUpperCase();
		SimpleDateFormat format = new SimpleDateFormat();
		format = new SimpleDateFormat("MMddyy");
		String datePrefix = format.format(date);
		id = siteNamePrefix + userPrefix + datePrefix;

		// System.out.println("===id===" + id);
		return id;

	}

	@Override
	public List<Destination> getAllDestinations(List<Integer> storeIdList) {

		List<Destination> destinations = new ArrayList<Destination>();

		if (storeIdList != null && storeIdList.size() == 1) {

			for (Integer storeId : storeIdList) {
				HashSet<StoreSupplierListView> uniqueStoreListView = new HashSet<StoreSupplierListView>(
						masterDataDAO.getStoreSupplierViewList(storeId));

				List<StoreSupplierListView> storeSupplierListViews = new ArrayList<StoreSupplierListView>(
						uniqueStoreListView);

				if (storeSupplierListViews != null
						&& storeSupplierListViews.size() > 0) {
					for (StoreSupplierListView storeSupplierListView : storeSupplierListViews) {
						Destination destination = new Destination();
						destination.setDestinationId(storeSupplierListView
								.getSellCustomerSiteId());
						destination.setName(storeSupplierListView
								.getSellCustomerSiteName());

						String address = storeSupplierListView
								.getSellCustomerHouseNumber()
								+ " "
								+ storeSupplierListView
										.getSellCustomerAddress3()
								+ " "
								+ storeSupplierListView
										.getSellCustomerAddress4()
								+ " "
								+ storeSupplierListView
										.getSellCustomerAddress5()
								+ " "
								+ storeSupplierListView
										.getSellCustomerPostcode();

						destination.setAddress(address);

						destinations.add(destination);
					}
				}

			}
		} else {
			List<StoreSupplierListView> commonStoreSupplierListViewSupplierList = new ArrayList<StoreSupplierListView>();
			for (Integer storeId : storeIdList) {
				List<StoreSupplierListView> storeSupplierListViews = masterDataDAO
						.getStoreSupplierViewList(storeId);
				if (commonStoreSupplierListViewSupplierList.size() == 0) {
					commonStoreSupplierListViewSupplierList = storeSupplierListViews;
				} else {

					List<StoreSupplierListView> common = new ArrayList<StoreSupplierListView>();
					for (StoreSupplierListView commonStoreSupplier : commonStoreSupplierListViewSupplierList) {
						for (StoreSupplierListView newStoreSupplier : storeSupplierListViews) {

							if (commonStoreSupplier.getSellCustomerSiteId()
									.equals(newStoreSupplier
											.getSellCustomerSiteId())) {
								common.add(commonStoreSupplier);
							}
						}
					}
					commonStoreSupplierListViewSupplierList = common;

					if (common.size() == 0) {
						break;
					}
				}

			}

			for (StoreSupplierListView storeSupplierListView : commonStoreSupplierListViewSupplierList) {
				Destination destination = new Destination();
				destination.setDestinationId(storeSupplierListView
						.getSellCustomerSiteId());
				destination.setName(storeSupplierListView
						.getSellCustomerSiteName());
				destinations.add(destination);
			}

		}

		if (destinations.size() > 1) {
			for (int i = 0; i < destinations.size(); i++) {

				for (int j = i + 1; j < destinations.size(); j++) {
					if (destinations.get(i).getDestinationId()
							.equals(destinations.get(j).getDestinationId())) {
						destinations.remove(j);
						j--;
					}
				}

			}
		}
		return destinations;
	}

	@Override
	public HashSet<Destination> getSavedOnlyTrips(
			ActivityListInputPayload inputPayload) {
		ListMultimap<Integer, Integer> weekNumbersAndDays = null;
		List<Integer> days = null;
		try {
			weekNumbersAndDays = getAllWeekNumbersAndDays(inputPayload
					.getDates());
			
		} catch (ParseException e) {
			
			LOGGER.error("Error in getSavedOnlyTrips of BaleRouteTripServiceImpl"+e);
		}
		List<BalePickupAssignment> assignmentsForUser = null;
		List<BalePickupAssignment> assignmentsOnCall = null;
		List<BalePickupAssignment> assignments = new ArrayList<BalePickupAssignment>();

		
		boolean inputHasTodayDate = false;
		assignmentsForUser = balePickupAssignment
				.getAllAssignments(inputPayload.getUserId());
		assignmentsOnCall = balePickupAssignment.getAllOnCallAssignments();
		for (String date : inputPayload.getDates()) {
			
			Date dateToday = new Date();
			SimpleDateFormat format = new SimpleDateFormat();
			format = new SimpleDateFormat("MM/dd/yyyy");
			String dateFormat = format.format(dateToday);
			if (dateFormat.equals(date)) {
				inputHasTodayDate = true;
			}

		}
		if (inputHasTodayDate) // get on call assignments only if payload
								// contains current date.
			assignmentsForUser.addAll(assignmentsOnCall);
		
		for (BalePickupAssignment assignmentForUser : assignmentsForUser) {
			if (assignmentForUser.getFrequency() == 3) {
				assignments.add(assignmentForUser);
			}

			Set<Integer> entries = weekNumbersAndDays.keySet();
			for (Integer entry : entries) {
				List<Integer> valuesForEachKey = weekNumbersAndDays.get(entry);
				for (Integer value : valuesForEachKey) {

					if (assignmentForUser.getWeekNumber().equals(entry)
							&& assignmentForUser.getDay().equals(value)
							&& (assignmentForUser.getDivertedUser() == null || (assignmentForUser
									.getDivertedUser() != null && assignmentForUser
									.getDivertedUser().getUserId().intValue() == inputPayload
									.getUserId()))) {

						assignments.add(assignmentForUser);
					}
				}
			}
		}

		// get trips ,if any, for the assignments

		List<BalePickupTrip> trips = balePickupTripDAO.getTripsForAssignments(
				assignments, inputPayload.getDates());

		List<PickupDetails> pickupDetailsList = new ArrayList<PickupDetails>();
		HashSet<Destination> destinations = new HashSet<Destination>();
		loop1: for (BalePickupAssignment assignment : assignments) {
			boolean hasTrip = false;
			List<Commodity> commodities = new ArrayList<Commodity>();
			
			if (trips != null && trips.size() != 0) {
				for (BalePickupTrip trip : trips) {

					Commodity commodity = new Commodity();
					commodity.setBalesPicked(trip.getBalesPicked());
					commodity.setBalesRemaining(trip.getBalesRemaining());
					commodity.setMaterialId(trip.getMaterial().getMaterialId());
					commodity.setShortName(trip.getMaterial().getDescription());
					commodities.add(commodity);

				}
			}
			if (hasTrip) {
				PickupDetails pickUpDetails = new PickupDetails();

				pickUpDetails.setStoreId(assignment.getBuyCustomerSite()
						.getCustomerSiteId());

				pickUpDetails.setStoreName(assignment.getBuyCustomerSite()
						.getSiteName());
				Destination destination = new Destination();
				destination.setDestinationId(assignment.getSellCustomerSite()
						.getCustomerSiteId());
				pickUpDetails.setDestination(destination);
				String address = assignment.getBuyCustomerSite().getLocation()
						.getHouseNumber()
						+ " "
						+

						assignment.getBuyCustomerSite().getLocation()
								.getAddress3()
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
				pickUpDetails.setMaterialList(commodities);
				if (assignment.getFrequency() != 3)
					pickUpDetails.setPickupDate(BaleUtils
							.convertWeekNumberAndDayToDate(
									assignment.getWeekNumber(),
									assignment.getDay()));
				else {
					Date dateToday = new Date();
					SimpleDateFormat format = new SimpleDateFormat();
					format = new SimpleDateFormat("MM/dd/yyyy");
					pickUpDetails.setPickupDate(format.format(dateToday));
				}

				pickupDetailsList.add(pickUpDetails);
			}

		}
		if (assignments != null) {
			// assignments = removeDuplicateSellCustomers(assignments);
			for (BalePickupAssignment assignment : assignments) {
				if (balePickupTripDAO.getTripsForAssignment(
						assignment.getBalePickupAssignmentId()).size() != 0) {

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

	public List<PickupDetails> getSavedOnlyTripsNew(
			ActivityListInputPayload inputPayload) {
		List<String> dates = inputPayload.getDates();
		Integer userId = inputPayload.getUserId();
		List<SavedPickups> allSavedPickups = new ArrayList<SavedPickups>();
		List<PickupDetails> pickupDetailsList = new ArrayList<PickupDetails>();

		for (String date : dates) {
			try {
				List<SavedPickups> savedPickups = balePickupAssignment
						.getSavedOnlyTrip(userId, date);

				if (savedPickups != null && savedPickups.size() > 0) {
					allSavedPickups.addAll(savedPickups);
				}
			} catch (Exception e) {
				LOGGER.error("Error  : getAllDestinationsNew == > " + e);
			}
		}

		if (allSavedPickups.size() > 0) {
			for (SavedPickups pickups : allSavedPickups) {

				List<MaterialInfo> materials = balePickupAssignment
						.getMaterialByAssignmentId(
								pickups.getBalePickupAssignmentId(),
								pickups.getPickupDate());
				List<Commodity> commodities = new ArrayList<Commodity>();
				for (MaterialInfo material : materials) {
					Commodity commodity = new Commodity();
					commodity.setBalesPicked(material.getBalesPicked());
					commodity.setBalesRemaining(material.getBalesRemaining());
					commodity.setShortName(material.getShortName());
					commodity.setMaterialId(material.getMaterialId());
					commodities.add(commodity);

				}

				PickupDetails pickupDetails = new PickupDetails();
				pickupDetails.setMaterialList(commodities);
				pickupDetails.setStoreId(pickups.getBuyCustomerSiteId());

				pickupDetails.setStoreName(pickups.getBuyCustomerSiteName());

				String address = pickups.getBuyCustomerHouseNumber() + " "
						+ pickups.getBuyCustomerAddress3() + " "
						+ pickups.getBuyCustomerAddress4() + " "
						+ pickups.getBuyCustomerAddress5() + " "
						+ pickups.getBuyCustomerPostCode();

				pickupDetails.setAddress(address);
				pickupDetails.setPickupDate(pickups.getPickupDate());

				Destination destination = new Destination();
				destination.setDestinationId(pickups.getSellCustomerSiteId());

				pickupDetails.setDestination(destination);
				// pickupDetails.setIncident(driverPickup);
				// pickupDetails.setDestinationDrop(driverPickup);

				pickupDetailsList.add(pickupDetails);
			}
		}

		return pickupDetailsList;
	}

	private List<BalePickupAssignment> removeDuplicateSellCustomers(
			List<BalePickupAssignment> assignments) {
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

	private ListMultimap<Integer, Integer> getAllWeekNumbersAndDays(
			List<String> dates) throws ParseException {
		ListMultimap<Integer, Integer> weekAndDay = ArrayListMultimap.create();
		for (String date : dates) {
			String format = "MM/dd/yyyy";
			SimpleDateFormat df = new SimpleDateFormat(format);
			Date dateFormat = df.parse(date);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dateFormat);
			// cal.setFirstDayOfWeek(cal.get(Calendar.MONDAY));
			int weeknumber = cal.get(Calendar.WEEK_OF_MONTH);
			if (cal.get(Calendar.DAY_OF_WEEK) == 1)
				weeknumber = weeknumber - 1;

			int day = cal.get(Calendar.DAY_OF_WEEK);
			day = day - 1;
			if (day == 0)
				day = 7;
			weekAndDay.put(weeknumber, day);
			// weekNumbersAndDates.add(weekAndDay);
		}
		return weekAndDay;
	}

}
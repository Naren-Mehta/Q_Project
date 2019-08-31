package com.wm.brta.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wm.brta.domain.BalePickupAssignment;
import com.wm.brta.domain.BalePickupTrip;
import com.wm.brta.domain.BalePickupTripDetailsForSC;
import com.wm.brta.domain.EditPendingReportDetails;
import com.wm.brta.domain.Image;
import com.wm.brta.domain.Material;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.User;
import com.wm.brta.dto.BalePickupMobileDetailsForSC;
import com.wm.brta.dto.Commodity;
import com.wm.brta.dto.EditPendingReportDTO;
import com.wm.brta.util.BaleUtils;

@Repository
public class BalePickupTripDAO {

	@Autowired
	protected SessionFactory sessionFactory;

	@Autowired
	BalePickUpAssignmentDAO assignmentDAO;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BalePickupTripDAO.class);

	public Integer saveTripDetails(BalePickupTrip persistentObject) {
		Integer i = 0;
		try {
			;
			Session session = sessionFactory.getCurrentSession();
			i = (Integer) session.save(persistentObject);

		} catch (Exception e) {
			LOGGER.error("Exception in Trip DAO: Save TripDetails" + e);
			return 0;
		}
		return i;

	}

	public Integer saveImageDetails(Image persistentObject) {
		
		Integer saveiD=0;
		try{
		Session session = sessionFactory.getCurrentSession();
		 saveiD = (Integer) session.save(persistentObject);
		}catch(Exception e){
			LOGGER.error("Error in saveImageDetails "+e);
			return -1;
		}
		return saveiD;
	}

	public List<BalePickupTrip> getAllTripsFromStoreIdAndSupplierAndUser(
			Integer storeId, Supplier supplier, User user, Date pickupDate) {
		Session session = sessionFactory.getCurrentSession();
		List<BalePickupTrip> trips = null;
		try{
	Query query = session.createQuery("select new BalePickupTrip(trip.tripId) from BalePickupTrip trip where"
						+ " trip.buyCustomerSite.customerSiteId =:storeId and trip.supplier.supplierId =:supplierId "
						+ " and trip.user.userId=:userId and trip.pickupDate =:date and trip.enabled=true");

		query.setParameter("storeId", storeId);
		query.setParameter("supplierId", supplier.getSupplierId());
		query.setParameter("date", pickupDate);
		query.setParameter("userId", user.getUserId());

		trips = query.list();
		}catch(Exception e){
			LOGGER.error("Error in getAllTripsFromStoreIdAndSupplierAndUser "+e);
			return null;
		}
		return trips;
	}

	public List<BalePickupTrip> getTripFromTripId(Integer tripId) {
		Session session = sessionFactory.getCurrentSession();
		List<BalePickupTrip> trips = null;
			try{
		Query query = session
				.createQuery("select new BalePickupTrip(trip.tripId) from BalePickupTrip trip where"
						+ " trip.tripId =:tripId and trip.enabled=true");

		query.setParameter("tripId", tripId);

		trips = query.list();
			}catch(Exception e){
				LOGGER.error("Error: Error in getTripFromTripId "+e);
				return null;
			}
		return trips;
	}

	public void completeTrip(BalePickupTrip trip) {

		Session session = sessionFactory.getCurrentSession();

		try {
			Query query = session
					.createQuery("update BalePickupTrip trip set trip.releaseNo =:releaseNo , trip.deliveryDate =:deliveryDate ,"
							+ " trip.grossWeight =:grossWeight , trip.tareWeight =:tareWeight, trip.sellCustomerSite =:sellCustomerSite "
							+ "where trip.tripId =:tripId and trip.enabled=true");
			query.setParameter("releaseNo", trip.getReleaseNo());
			query.setParameter("deliveryDate", trip.getDeliveryDate());
			query.setParameter("grossWeight", trip.getGrossWeight());
			query.setParameter("tareWeight", trip.getTareWeight());
			query.setParameter("sellCustomerSite", trip.getSellCustomerSite());
			query.setParameter("tripId", trip.getTripId());

			int tripUpdatedValue = query.executeUpdate();
			
		} catch (Exception e) {
			LOGGER.error("Error in completeTrip : " + e);
		}

	}

	public List<BalePickupTrip> getAllTripsWithMaterial(
			HashSet<Commodity> commodities, int weekNumber, int day,
			Integer storeId, String date) {
		List<BalePickupTrip> trips = null;
		Date pickupDate = new Date(date);
		try {
			Session session = sessionFactory.getCurrentSession();
			trips = new ArrayList<BalePickupTrip>();
			List<Integer> materialIds = new ArrayList<Integer>();
			for (Commodity commodity : commodities) {
				materialIds.add(commodity.getMaterialId());
			}
			Query query = session
					.createQuery("from BalePickupTrip where material.materialId in :materialids and weekNumber =:weekNumber and day=:day"
							+ " and buyCustomerSite.customerSiteId =:storeId and pickupDate =:pickupDate");
			query.setParameterList("materialids", materialIds);
			query.setParameter("weekNumber", weekNumber);
			query.setParameter("day", day);
			query.setParameter("storeId", storeId);
			query.setParameter("pickupDate", pickupDate);
			trips = query.list();
		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripDAO:getAllTripsWithMaterial=====>"
					+ e);
		}
		return trips;
	}

	

	public void deleteTrip(List tripIdList, Integer storeId, String date,
			Integer supplierId, Integer materialId, Integer BOL) {
		Session session = sessionFactory.getCurrentSession();
		List<Integer> ids = new ArrayList<Integer>();

		
		Date pickupDate = BaleUtils.convertStringToDate(date);

		try {
			Query query = session
					.createQuery("delete BalePickupTrip where "
							+ " buyCustomerSite.customerSiteId =:storeId and "
							+ " pickupDate =:pickupDate and materialId =:materialId and "
							+ " supplier.supplierId =:supplierId and bol =:bol and tripId not in (:tripIdList)");

			query.setParameterList("tripIdList", tripIdList);
			query.setParameter("storeId", storeId);
			query.setParameter("pickupDate", pickupDate);
			query.setParameter("supplierId", supplierId);
			query.setParameter("materialId", materialId);
			query.setParameter("bol", BOL);
			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripDAO:deleteTrip=====>" + e);
		}

	}

	public void deleteTrip(List tripIdList, Integer storeId, String date,
			Integer supplierId, Integer materialId) {
		Session session = sessionFactory.getCurrentSession();
		List<Integer> ids = new ArrayList<Integer>();

		/*
		 * for(Commodity trip:assignmentId){ ids.add(trip.getCommodityId()); }
		 */

		Date pickupDate = BaleUtils.convertStringToDate(date);

		try {
			Query query = session
					.createQuery("delete BalePickupTrip where "
							+ " buyCustomerSite.customerSiteId =:storeId and "
							+ " pickupDate =:pickupDate and materialId =:materialId and "
							+ " supplier.supplierId =:supplierId and tripId not in (:tripIdList)");

			query.setParameterList("tripIdList", tripIdList);
			query.setParameter("storeId", storeId);
			query.setParameter("pickupDate", pickupDate);
			query.setParameter("supplierId", supplierId);
			query.setParameter("materialId", materialId);
			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripDAO:deleteTrip=====>" + e);
		}

	}

	public void deleteTripFromTripId(Integer tripId) {
		Session session = sessionFactory.getCurrentSession();
		List<Integer> ids = new ArrayList<Integer>();


		try {
			Query query = session.createQuery("delete BalePickupTrip where "
					+ " tripId =:tripId");
			query.setParameter("tripId", tripId);
			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripDAO: deleteTripFromTripId=====>"
					+ e);
		}

	}

	public void updatePendingResolutionTrip(Integer newTripId, Integer tripId) {
		Session session = sessionFactory.getCurrentSession();
		List<Integer> ids = new ArrayList<Integer>();

		try {
			Query query = session
					.createQuery("update BalePickupTrip trip set trip.pendingResolutionTripId =:newTripId where "
							+ " trip.pendingResolutionTripId =:tripId");
			query.setParameter("newTripId", newTripId);
			query.setParameter("tripId", tripId);
			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripDAO: updatePendingResolutionTrip=====>"
					+ e);
		}

	}

	public void updatePendingResolutionTrip1(Integer newTripId,
			Integer storeId, String date, Integer supplierId, Integer BOL) {
		Session session = sessionFactory.getCurrentSession();
		List<Integer> ids = new ArrayList<Integer>();

		Date pickupDate = BaleUtils.convertStringToDate(date);

		try {
			Query query = session
					.createQuery("update BalePickupTrip trip set trip.pendingResolutionTripId =:newTripId where "
							+ " trip.pendingResolutionTripId in (select trip1.tripId from BalePickupTrip trip1 where "
							+ " trip1.buyCustomerSite.customerSiteId =:storeId and "
							+ " trip1.pickupDate =:pickupDate and "
							+ " trip1.supplier.supplierId =:supplierId and"
							+ " trip1.bol =:bol and trip1.tripId !=:newTripId1)");

			query.setParameter("newTripId", newTripId);
			query.setParameter("newTripId1", newTripId);
			query.setParameter("storeId", storeId);
			query.setParameter("pickupDate", pickupDate);
			query.setParameter("supplierId", supplierId);
			query.setParameter("bol", BOL);
			query.executeUpdate();

		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripDAO: updatePendingResolutionTrip1=====>"
					+ e);
		}

	}

	public void updatePendingResolutionTrip1(Integer newTripId,
			Integer storeId, String date, Integer supplierId) {
		Session session = sessionFactory.getCurrentSession();
		List<Integer> ids = new ArrayList<Integer>();

		Date pickupDate = BaleUtils.convertStringToDate(date);

		try {
			Query query = session
					.createQuery("update BalePickupTrip trip set trip.pendingResolutionTripId =:newTripId where "
							+ " trip.pendingResolutionTripId in (select trip1.tripId from BalePickupTrip trip1 where "
							+ " trip1.buyCustomerSite.customerSiteId =:storeId and "
							+ " trip1.pickupDate =:pickupDate and "
							+ " trip1.supplier.supplierId =:supplierId and trip1.tripId !=:newTripId1)");

			query.setParameter("newTripId", newTripId);
			query.setParameter("newTripId1", newTripId);
			query.setParameter("storeId", storeId);
			query.setParameter("pickupDate", pickupDate);
			query.setParameter("supplierId", supplierId);
			query.executeUpdate();

		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripDAO: updatePendingResolutionTrip1=====>"
					+ e);
		}

	}

	public void updateFile(Integer newTripId, Integer oldTripId) {

		Session session = sessionFactory.getCurrentSession();

		try {
			Query query = session
					.createQuery("update Image set tripId =:newTripId where tripId =:oldTripId");
			query.setParameter("newTripId", newTripId);
			query.setParameter("oldTripId", oldTripId);
			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripDAO: deleteFile=====>" + e);
		}

	}

	public void deleteFile(List tripIdList, Integer storeId, String date,
			Integer supplierId, Integer materialId, Integer BOL) {
		Session session = sessionFactory.getCurrentSession();
		List<Integer> ids = new ArrayList<Integer>();

		Date pickupDate = BaleUtils.convertStringToDate(date);

		try {
			Query query = session
					.createQuery("delete Image where tripId in (select tripId from BalePickupTrip where"
							+ " buyCustomerSite.customerSiteId =:storeId and pickupDate =:pickupDate "
							+ "and supplier.supplierId =:supplierId and material.materialId =:materialId and bol =:bol"
							+ " and tripId not in (:tripIdList))");
			query.setParameterList("tripIdList", tripIdList);
			query.setParameter("storeId", storeId);
			query.setParameter("pickupDate", pickupDate);
			query.setParameter("supplierId", supplierId);
			query.setParameter("materialId", materialId);
			query.setParameter("bol", BOL);
			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripDAO: deleteFile=====>" + e);
		}

	}

	public void deleteFile(List tripIdList, Integer storeId, String date,
			Integer supplierId, Integer materialId) {
		Session session = sessionFactory.getCurrentSession();
		List<Integer> ids = new ArrayList<Integer>();

		Date pickupDate = BaleUtils.convertStringToDate(date);

		try {
			Query query = session
					.createQuery("delete Image where tripId in (select tripId from BalePickupTrip where"
							+ " buyCustomerSite.customerSiteId =:storeId and pickupDate =:pickupDate "
							+ "and supplier.supplierId =:supplierId and material.materialId =:materialId "
							+ " and tripId not in (:tripIdList))");
			query.setParameterList("tripIdList", tripIdList);
			query.setParameter("storeId", storeId);
			query.setParameter("pickupDate", pickupDate);
			query.setParameter("supplierId", supplierId);
			query.setParameter("materialId", materialId);
			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripDAO: deleteFile=====>" + e);
		}

	}

	public void deleteFileFromTripId(Integer tripId) {
		Session session = sessionFactory.getCurrentSession();

		try {
			Query query = session
					.createQuery("delete Image where tripId =:tripId");
			query.setParameter("tripId", tripId);
			query.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripDAO:deleteImage=====>" + e);
		}

	}

	public List<BalePickupTrip> getTripsForAssignments(
			List<BalePickupAssignment> assignments, List<String> dates) {
		Session session = sessionFactory.getCurrentSession();
		List<BalePickupTrip> trips = null;
		List<Date> javaDates = new ArrayList<Date>();
		List<Integer> ids = new ArrayList<Integer>();
		for (BalePickupAssignment assignment : assignments) {
			ids.add(assignment.getBalePickupAssignmentId());
		}
		for (String date : dates) {
			javaDates.add(new Date(date));
		}

		try {
			Query query = session
					.createQuery("from BalePickupTrip where balePickupAssignment.balePickupAssignmentId in :ids "
							+ " and pickupDate in :dates");
			query.setParameterList("ids", ids);
			query.setParameterList("dates", javaDates);
			trips = query.list();
		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripDAO:getTripsForAssignments=====>"
					+ e);
		}

		return trips;
	}

	public List<BalePickupTrip> getTripsForAssignment(Integer assignmentId) {
		Session session = sessionFactory.getCurrentSession();
		List<BalePickupTrip> trips = null;
		List<Integer> ids = new ArrayList<Integer>();

		try {
			Query query = session
					.createQuery("from BalePickupTrip where balePickupAssignment.balePickupAssignmentId =:id");
			query.setParameter("id", assignmentId);
			trips = query.list();
		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripDAO:getTripsForAssignment=====>"
					+ e);
		}

		return trips;
	}

	public List<BalePickupTrip> getAllSavedOnlyTrips(Integer userId,
			List<String> pickupDates, List<BalePickupAssignment> assignments) {
		List<BalePickupTrip> trips = null;
		try{
		List<java.sql.Date> dates = new ArrayList<java.sql.Date>();
		for (String date : pickupDates) {
			java.util.Date utilDate = new java.util.Date(date);
			java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
			dates.add(sqlDate);
		}
		
		Session session = sessionFactory.getCurrentSession();

		Query query = session
				.createQuery("from BalePickupTrip where user.userId=:userId and pickupDate in "
						+ ":pickupDates and loadTripId is null and childLoadTripId is null and balePickupAssignment in :assignments");
		query.setParameter("userId", Long.parseLong(userId.toString()));
		query.setParameterList("pickupDates", dates);
		query.setParameterList("assignments", assignments);
		trips = query.list();
		}catch(Exception e){
			LOGGER.error("Error in getAllSavedOnlyTrips "+e);
			return null;
		}
		return trips;
	}

	public void deleteImagesForTrip(Integer assignmentId, int weekNumber,
			int day, Integer storeId, String date, Integer userId) {
		Session session = sessionFactory.getCurrentSession();
		List<Integer> ids = new ArrayList<Integer>();
		Date pickupDate = new Date(date);
		java.sql.Date sqlDate = new java.sql.Date(pickupDate.getTime());
		List<BalePickupTrip> trips = new ArrayList<BalePickupTrip>();
		List<Integer> tripIds = new ArrayList<Integer>();
		try {
			BalePickupAssignment assignment = assignmentDAO
					.getAssignmentById(assignmentId);
			Query query = session
					.createQuery("from BalePickupTrip where balePickupAssignment =:assignment and weekNumber =:weekNumber and day=:day"
							+ " and buyCustomerSite.customerSiteId =:storeId and pickupDate =:pickupDate and user.userId =:userId");
			query.setParameter("assignment", assignment);
			query.setParameter("weekNumber", weekNumber);
			query.setParameter("day", day);
			query.setParameter("storeId", storeId);
			query.setParameter("pickupDate", sqlDate);
			query.setParameter("userId", Long.valueOf(userId));
			trips = query.list();

			for (BalePickupTrip trip : trips) {
				tripIds.add(trip.getTripId());

			}
			Query queryForImages = session
					.createQuery("delete Image i where i.tripId in :tripids");
			queryForImages.setParameterList("tripids", tripIds);
			queryForImages.executeUpdate();
		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripDAO:deleteImagesForTrip=====>"
					+ e);
		}

	}

	public List<EditPendingReportDetails> getAllBalePickupDates(
			EditPendingReportDTO editPendingReportDTO) {

		Session session = sessionFactory.getCurrentSession();
		List<EditPendingReportDetails> pendingReportDetailsDTOs = new ArrayList<EditPendingReportDetails>();

		try {
			Query query = session
					.createQuery(" from EditPendingReportDetails  where supplierId =:supplierId "
							+ "and buyCustomerSiteId=:buyCustomerSiteId order by pickupDate desc");
			query.setParameter("supplierId",
					editPendingReportDTO.getSupplierId());
			query.setParameter("buyCustomerSiteId",
					editPendingReportDTO.getBuyCustomerSiteId());
			pendingReportDetailsDTOs = query.list();
		} catch (Exception e) {
			LOGGER.error("==Exception getAllBalePickupDates==" + e);
		}
		return pendingReportDetailsDTOs;
	}
	
	
	
	public Integer deleteSCTripRecord(BalePickupMobileDetailsForSC sc) {
	
		Integer deletedId =0;

		Session session = sessionFactory.getCurrentSession();
		
		try {
			
	String checkRecordQuery = "delete from BalePickupTripDetailsForSC sc where sc.supplier.supplierId =:supplierId and "
			+ " sc.material.materialId =:material and "
			+ " sc.customerSite.customerSiteId =:customerSiteId "
			+ " and sc.checkinDateTime between :startOfDay and :endOfDay";
	
	if (sc.getTruckId() != null) {
		checkRecordQuery = checkRecordQuery
				+ " and sc.truckId =:truckId";
	}
	Query query = session.createQuery(checkRecordQuery);
	try {
		query.setParameter("supplierId", sc.getSupplierId());
		if (sc.getOldMaterialId() != null) {
			query.setParameter("material", sc.getOldMaterialId());
		} else {
			query.setParameter("material", sc.getMaterial());
		}
		
		if (sc.getOldCheckinDate() != null
				&& sc.getOldCheckinDate() != "") {
			query.setParameter("startOfDay", BaleUtils
					.getStartOfDay(BaleUtils.convertStringToDateSC(sc
							.getOldCheckinDate())));
			query.setParameter("endOfDay", BaleUtils
					.getEndOfDay(BaleUtils.convertStringToDateSC(sc
							.getOldCheckinDate())));
		} else {
			if (sc.getCheckinDate() != null
					&& sc.getCheckinDate() != "") {
				
				query.setParameter("startOfDay", BaleUtils
						.getStartOfDay(BaleUtils
								.convertStringToDateSC(sc
										.getCheckinDate())));
				query.setParameter("endOfDay", BaleUtils
						.getEndOfDay(BaleUtils.convertStringToDateSC(sc
								.getCheckinDate())));
			} else {
				
				query.setParameter("startOfDay",
						BaleUtils.getStartOfDay(new Date()));
				query.setParameter("endOfDay",
						BaleUtils.getEndOfDay(new Date()));
			}
		}

		query.setParameter("customerSiteId", sc.getCustomerSite());

		if (sc.getTruckId() != null) {
			query.setParameter("truckId", sc.getTruckId());
		}
		
	} catch (Exception e) {
		LOGGER.error("==Exception in inner try checkSCTripRecordExist=="+e);
	}
			deletedId  = query.executeUpdate();
			
		
		} catch (Exception e) {
			LOGGER.error("==Exception in checkSCTripRecordExist==" + e);
		}
		return deletedId;
			}

	public Set<BalePickupTripDetailsForSC> checkSCTripRecordExist(
			BalePickupMobileDetailsForSC sc) {

		

		Session session = sessionFactory.getCurrentSession();
		List<BalePickupTripDetailsForSC> balePickupTripDetailsForSC = new ArrayList<BalePickupTripDetailsForSC>();
		Set<BalePickupTripDetailsForSC> resultAssignments = new HashSet<BalePickupTripDetailsForSC>();

		try {

			
			String checkRecordQuery = "select new BalePickupTripDetailsForSC(sc.Id,sc.checkinDateTime, sc.checkoutDateTime) "
					+ "from BalePickupTripDetailsForSC sc "
					+ " where sc.supplier.supplierId =:supplierId and "
					+ " sc.material.materialId =:material and "
					+ " sc.customerSite.customerSiteId =:customerSiteId "
					+ " and sc.checkinDateTime between :startOfDay and :endOfDay";

			if (sc.getTruckId() != null) {
				checkRecordQuery = checkRecordQuery
						+ " and sc.truckId =:truckId";
			}
			Query query = session.createQuery(checkRecordQuery);
			try {
				query.setParameter("supplierId", sc.getSupplierId());

				if (sc.getOldMaterialId() != null) {
					query.setParameter("material", sc.getOldMaterialId());
				} else {
					query.setParameter("material", sc.getMaterial());
				}



				if (sc.getOldCheckinDate() != null
						&& sc.getOldCheckinDate() != "") {
					query.setParameter("startOfDay", BaleUtils
							.getStartOfDay(BaleUtils.convertStringToDateSC(sc
									.getOldCheckinDate())));
					query.setParameter("endOfDay", BaleUtils
							.getEndOfDay(BaleUtils.convertStringToDateSC(sc
									.getOldCheckinDate())));
				} else {
					if (sc.getCheckinDate() != null
							&& sc.getCheckinDate() != "") {
						
						query.setParameter("startOfDay", BaleUtils
								.getStartOfDay(BaleUtils
										.convertStringToDateSC(sc
												.getCheckinDate())));
						query.setParameter("endOfDay", BaleUtils
								.getEndOfDay(BaleUtils.convertStringToDateSC(sc
										.getCheckinDate())));
					} else {
						
						query.setParameter("startOfDay",
								BaleUtils.getStartOfDay(new Date()));
						query.setParameter("endOfDay",
								BaleUtils.getEndOfDay(new Date()));
					}
				}

				query.setParameter("customerSiteId", sc.getCustomerSite());

				if (sc.getTruckId() != null) {
					query.setParameter("truckId", sc.getTruckId());
				}
			} catch (Exception e) {
				LOGGER.error("==Exception in first checkSCTripRecordExist==" + e);
			}
			balePickupTripDetailsForSC = query.list();
			

			if (balePickupTripDetailsForSC != null
					&& balePickupTripDetailsForSC.size() != 0) {
				resultAssignments = new HashSet<BalePickupTripDetailsForSC>(
						balePickupTripDetailsForSC);
				
			} else {
				resultAssignments = null;
				
			}
		} catch (Exception e) {
			LOGGER.error("==Exception in checkSCTripRecordExist==" + e);
		}
		return resultAssignments;
	}

	public Integer saveCheckInCheckOut(BalePickupTripDetailsForSC persistentObject) {
		Integer i = 0;

		try {
			;
			Session session = sessionFactory.getCurrentSession();
			i = (Integer) session.save(persistentObject);

		} catch (Exception e) {
			LOGGER.error("Exception is saving data of BalePickupTripDetailsForSC"	+ e);
			return 0;
		}
		
		return i;

	}

	public Integer updateCheckInCheckout(BalePickupMobileDetailsForSC sc,Integer id) {
	
		Session session = sessionFactory.getCurrentSession();
		Integer i = 0;

		try {

			String strQuery = " update BalePickupTripDetailsForSC sc set sc.balesPicked =:balesPicked, "
					+ " sc.balesRemaining =:balesRemaining, sc.status =:status, sc.material =:material, sc.resolution =:resolution ";
			
			
			if (sc.getCheckInLatitude() != null && sc.getCheckInLongitude() != null) {
				strQuery=strQuery+" ,sc.checkInLatitude =:latitude, sc.checkInLongitude =:longitude";
			}

			if (sc.getCheckoutDate() != null && sc.getCheckoutDate() != "") {
				
				strQuery = " update BalePickupTripDetailsForSC sc set sc.balesPicked =:balesPicked, sc.checkoutDateTime =:checkoutDateTime, "
						+ " sc.uTCCheckoutDateTime =:uTCCheckoutDateTime, sc.balesRemaining =:balesRemaining, sc.status =:status, "
						+ " sc.material =:material, sc.resolution =:resolution ";
				if (sc.getLatitude() != null && sc.getLongitude() != null) {
					
					strQuery = strQuery	+ " ,sc.latitude =:latitude, sc.longitude =:longitude ";
				}
						
			}
			
			
			if (sc.getBol()!=null) {
				
				strQuery = strQuery	+ " ,sc.bol =:bol ";
			}
			
			
			if (sc.getCheckinDate() != null && sc.getCheckinDate() != ""
					&& sc.getApplicationType() != null
					&& sc.getApplicationType().equals("Web")) {
				if (!sc.getCheckinDate().equals(sc.getOldCheckinDate())) {
					strQuery = strQuery	+ " ,sc.checkinDateTime =:checkinDateTime, sc.uTCCheckinDateTime =:uTCCheckinDateTime ";

				}
			}
			
	
			
			if(sc.getDriver()!=null){
				strQuery = strQuery	+ " ,sc.userId =:userId ";
			}

			strQuery = strQuery + " where sc.id =:id";
			
			Query query = session.createQuery(strQuery);
			query.setParameter("id", id);
			query.setParameter("balesPicked", sc.getBalesPicked());
			query.setParameter("balesRemaining", sc.getBalesRemaining());
			query.setParameter("status", sc.getStatus());
			query.setParameter("resolution", sc.getResolution());
			
			if (sc.getLatitude() != null && sc.getLongitude() != null) {
				query.setParameter("latitude", sc.getLatitude());
				query.setParameter("longitude", sc.getLongitude());
			}
			
			if (sc.getCheckInLatitude() != null && sc.getCheckInLongitude() != null) {
				query.setParameter("latitude", sc.getCheckInLatitude());
				query.setParameter("longitude", sc.getCheckInLongitude());
			}
		

			if (sc.getCheckinDate() != null && sc.getCheckinDate() != "" && sc.getApplicationType() != null	&& sc.getApplicationType().equals("Web")) {
				query.setParameter("checkinDateTime", BaleUtils.convertStringToDateSC(sc.getCheckinDate()));
				query.setParameter("uTCCheckinDateTime", BaleUtils.getIsoDateForServiceChannel(BaleUtils.convertStringToDateSC(sc.getCheckinDate())));
			}

			Material material = new Material();
			material.setMaterialId(sc.getMaterial());
			query.setParameter("material", material);
			User user =new User();
			user.setUserId(Long.parseLong(sc.getDriver()+""));
			
			query.setParameter("userId", user);
			
			if (sc.getCheckoutDate() != null && sc.getCheckoutDate() != "") {
				query.setParameter("checkoutDateTime",	BaleUtils.convertStringToDateSC(sc.getCheckoutDate()));
				query.setParameter("uTCCheckoutDateTime", BaleUtils.getIsoDateForServiceChannel(BaleUtils.convertStringToDateSC(sc.getCheckoutDate())));
			}
			
			
			if (sc.getBol()!=null) {
				query.setParameter("bol", sc.getBol());
			}
			
			i = query.executeUpdate();
		}

		catch (RuntimeException e) {
			
			LOGGER.error("Error: balePickupTripDAO: updateCheckInCheckout=====>" + e);
		}
	
		return id;
	}

	public HashSet<Commodity> retriveMaterialfromSC(Long driver1, Long driver2,
			Integer supplierId, Integer customerSiteId, String date,
			String vehiclePlateNo) {

		List<Commodity> balePickupTripDetailsForSC = new ArrayList<Commodity>();
		HashSet<Commodity> resultAssignments = new HashSet<Commodity>();
		Session session = sessionFactory.getCurrentSession();

		try {

			String createdQuery = "";

			
			if(vehiclePlateNo!=null && vehiclePlateNo!=""){
				createdQuery = "select new com.wm.brta.dto.Commodity(comm.material.materialId,comm.material.description,comm.balesPicked,comm.balesRemaining) "
						+ " from BalePickupTripDetailsForSC comm where comm.customerSite.customerSiteId =:customerSiteId and "
						+ " (comm.userId.userId =:driver1 or comm.userId.userId =:driver2) and comm.supplier.supplierId =:supplierId "
						+ " and comm.checkinDateTime between :startOfDay and :endOfDay and comm.truckId =:vehiclePlateNo ";
			}else{
				createdQuery = "select new com.wm.brta.dto.Commodity(comm.material.materialId,comm.material.description,comm.balesPicked,comm.balesRemaining) "
						+ " from BalePickupTripDetailsForSC comm where comm.customerSite.customerSiteId =:customerSiteId and "
						+ " (comm.userId.userId =:driver1 or comm.userId.userId =:driver2) and comm.supplier.supplierId =:supplierId "
						+ " and comm.checkinDateTime between :startOfDay and :endOfDay and comm.truckId is null";
			}
			Query query = session.createQuery(createdQuery);

			query.setParameter("supplierId", supplierId);
			query.setParameter("driver1", driver1);
			query.setParameter("driver2", driver2);
			query.setParameter("customerSiteId", customerSiteId);
			if(vehiclePlateNo!=null && vehiclePlateNo!=""){
				query.setParameter("vehiclePlateNo", vehiclePlateNo);
			}

			Date newDate = new Date();

			try {
				newDate = BaleUtils.convertStringToDate(date);
				
			} catch (Exception e) {
				LOGGER.error("===exception while convertin date======="+e);
				newDate = new Date();
			}

			query.setParameter("startOfDay", BaleUtils.getStartOfDay(newDate));
			query.setParameter("endOfDay", BaleUtils.getEndOfDay(newDate));

			balePickupTripDetailsForSC = query.list();

			
			if (balePickupTripDetailsForSC != null
					&& balePickupTripDetailsForSC.size() != 0) {
				resultAssignments = new HashSet<Commodity>(
						balePickupTripDetailsForSC);

			}

		} catch (RuntimeException e) {
			
			LOGGER.error("Error: balePickupTripDAO: retriveMaterialforPickupDetails=====>"
					+ e);
		}

		return resultAssignments;

	}

	public HashSet<Commodity> retriveBalesRecordsForSC(
			BalePickupTripDetailsForSC sc) {

		List<Commodity> balePickupTripDetailsForSC = new ArrayList<Commodity>();
		HashSet<Commodity> resultAssignments = new HashSet<Commodity>();
		Session session = sessionFactory.getCurrentSession();

		try {

			String createdQuery = "select new com.wm.brta.dto.Commodity(comm.material.materialId,comm.material.description,comm.balesPicked,comm.balesRemaining) "
					+ " from BalePickupTripDetailsForSC comm where comm.supplier.supplierId =:supplierId and "
					+ " comm.userId.userId =:driver ";

			if (sc.getTruckId() != null) {
				createdQuery = createdQuery + " and truckId =:truckId";
			}

			Query query = session.createQuery(createdQuery);

			query.setParameter("supplierId", sc.getSupplier().getSupplierId());
			query.setParameter("driver",
					Long.valueOf(sc.getUserId().getUserId()));

			if (sc.getTruckId() != null) {
				query.setParameter("truckId", sc.getTruckId());
			}

			balePickupTripDetailsForSC = query.list();

			if (balePickupTripDetailsForSC != null
					&& balePickupTripDetailsForSC.size() != 0) {
				resultAssignments = new HashSet<Commodity>(
						balePickupTripDetailsForSC);

			}

		} catch (RuntimeException e) {
		
			LOGGER.error("Error: balePickupTripDAO: retriveBalesRecordsForSC=====>"
					+ e);
		}
		
		return resultAssignments;
	}








public List<BalePickupMobileDetailsForSC> retriveSCRecordByStoreId(Integer customerSite) {

	List<BalePickupMobileDetailsForSC> balePickupMobileDetailsForSC = new ArrayList<BalePickupMobileDetailsForSC>();
	List<BalePickupMobileDetailsForSC> results = new ArrayList<BalePickupMobileDetailsForSC>();
	Session session = sessionFactory.getCurrentSession();

	try {

		Query query = session.createQuery(" from BalePickupMobileDetailsForSC  where customerSite =:customerSite");
		
		query.setParameter("customerSite", customerSite);
		

		balePickupMobileDetailsForSC = query.list();

		if (balePickupMobileDetailsForSC != null	&& balePickupMobileDetailsForSC.size() != 0) {
			results = new ArrayList<BalePickupMobileDetailsForSC>(balePickupMobileDetailsForSC);

		}

	} catch (RuntimeException e) {
		
		LOGGER.error("Error: balePickupTripDAO: retriveBalesRecordsForSC=====>"	+ e);
	}
	
	return results;
}
}


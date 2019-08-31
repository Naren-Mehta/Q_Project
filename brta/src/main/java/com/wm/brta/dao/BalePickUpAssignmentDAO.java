package com.wm.brta.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
import com.wm.brta.domain.BalePickupSummaryView;
import com.wm.brta.domain.BalePickupTrip;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.DriverPickups;
import com.wm.brta.domain.DriverSupplierPickupsView;
import com.wm.brta.domain.Frequency;
import com.wm.brta.domain.Image;
import com.wm.brta.domain.Material;
import com.wm.brta.domain.MaterialByCustomerAndSupplierSP;
import com.wm.brta.domain.MaterialInfo;
import com.wm.brta.domain.PendingReport;
import com.wm.brta.domain.Pickupsview;
import com.wm.brta.domain.SavedPickups;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.SupplierPickupDetailsSP;
import com.wm.brta.domain.SupplierPickupsSP;
import com.wm.brta.domain.SupplierSavedPickupsSP;
import com.wm.brta.domain.User;
import com.wm.brta.dto.BalePickupFilterDTO;
import com.wm.brta.dto.Destination;
import com.wm.brta.dto.PendingReportDTO;

@Repository
public class BalePickUpAssignmentDAO {

	@Autowired
	protected SessionFactory sessionFactory;

	private static final Logger LOGGER = LoggerFactory.getLogger(BalePickUpAssignmentDAO.class);

	public List<BalePickupAssignment> getAllAssignments(int userId) {

		List<BalePickupAssignment> assignments = null;
		Session session = sessionFactory.getCurrentSession();
		try {

			Query query = session
					.createQuery("from BalePickupAssignment where (userId=:userId or divertedUserId=:divertedUserId "
							+ ") and " + "disabled=:disabledFlag");
			query.setParameter("userId", userId);
			query.setParameter("divertedUserId", userId);

			query.setParameter("disabledFlag", false);
			assignments = query.list();
		}

		catch (RuntimeException e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:getAllAssignments=====>"
					+ e);

		}
		return assignments;

	}

	public BalePickupAssignment getAssignmentById(int id) {

		List<BalePickupAssignment> assignments = null;

		Session session = sessionFactory.getCurrentSession();

		try {

			Query query = session
					.createQuery("from BalePickupAssignment where id =:id and disabled = false");
			query.setParameter("id", id);

			assignments = query.list();
		}

		catch (RuntimeException e) {

			LOGGER.error("Error:BalePickUpAssignmentDAO:getAssignmentById=====>"
					+ e);
		}
		return assignments.get(0);

	}

	public List<BalePickupTrip> getTripDetails(Integer assignmentId,
			int userId, String pickupDate) {
		List<BalePickupTrip> trips = new ArrayList<BalePickupTrip>();
		try {
			Session session = sessionFactory.getCurrentSession();
			BalePickupAssignment assignment = getAssignmentById(assignmentId);
			Date formatDate = new Date(pickupDate);

			Query query = session
					.createQuery("from BalePickupTrip"
							+ " where  balePickupAssignment =:assignment and userId =:userId and pickupDate =:formatDate");

			query.setParameter("userId", userId);
			query.setParameter("formatDate", formatDate);
			query.setParameter("assignment", assignment);
			trips = query.list();
		} catch (Exception e) {

			LOGGER.error("Error:BalePickUpAssignmentDAO:getTripDetails=====>"
					+ e);
		}
		if (trips.size() != 0)
			return trips;
		else
			return null;
	}

	public BalePickupAssignment getAssignmentDetails(int assignmentId,
			int weekNumber, int day) {
		Session session = sessionFactory.getCurrentSession();
		List<BalePickupAssignment> assignments = new ArrayList<BalePickupAssignment>();
		try{
		Query query = session
				.createQuery("from BalePickupAssignment"
						+ " where balePickupAssignmentId =:assignmentId and weekNumber =:weekNumber and day=:day and disabled = false ");
		query.setParameter("weekNumber", weekNumber);
		query.setParameter("day", day);
		query.setParameter("assignmentId", assignmentId);
		
		 assignments = query.list();
		}catch(Exception e){
			LOGGER.error("Error: Error in getAssignmentDetails  "+e);
		}
		if (assignments != null && assignments.size() != 0)
			return assignments.get(0);
		else
			return null;
	}

	public List<Material> getAllMaterialConfiguationMappingsByCustomerSite(
			Integer customerSiteId) {
		Session session = sessionFactory.getCurrentSession();
		List<Material> materialMappings = null;
		try {
		Query query = session
				.createQuery("select materialConfig.material from BalePickupMaterialConfiguration materialConfig "
						+ "where materialConfig.customerSite.customerSiteId=:customerSiteId and materialConfig.enabled=true");
		query.setParameter("customerSiteId", customerSiteId);
	
			materialMappings = query.list();

		} catch (RuntimeException e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:getAllMaterialConfiguationMappingsByCustomerSite=====>"
					+ e);
		}
		return materialMappings;
	}

	// public List<BalePickupMaterialConfiguration>
	// getAllMaterialConfiguationMappingsByCustomerSite(
	// Integer customerSiteId) {
	// Session session = sessionFactory.getCurrentSession();
	// List<BalePickupMaterialConfiguration> materialMappings = null;
	//
	// Query query = session
	// .createQuery("from BalePickupMaterialConfiguration where buyCustomerSiteId=:customerSiteId and enabled=true");
	// query.setParameter("customerSiteId", customerSiteId);
	// try {
	// materialMappings = query.list();
	//
	// } catch (RuntimeException e) {
	// LOGGER.error("Error:BalePickUpAssignmentDAO:getAllMaterialConfiguationMappingsByCustomerSite=====>"
	// + e);
	// }
	// return materialMappings;
	// }

	public void persist(BalePickupAssignment assignment) {

		Session session = sessionFactory.getCurrentSession();
		try {
			CustomerSite customerSite = (CustomerSite) session.get(
					"com.wm.brta.domain.CustomerSite", assignment
							.getBuyCustomerSite().getCustomerSiteId());
			customerSite.setHasfrequency(true);
			session.merge(customerSite);
			session.persist(assignment);
		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:persist=====>" + e);
		}
	}

	public Set<BalePickupAssignment> getAllAssignmentForActivity(
			List<CustomerSite> buyCustomerSites, List<User> users,
			List<CustomerSite> sellCustomerSites, Supplier supplier,
			Integer frequency, HashSet<Integer> dayList,
			HashSet<Integer> monthWeekList) {
		Session session = sessionFactory.getCurrentSession();

		String queryStr = "from BalePickupAssignment where buyCustomerSite in :buyCustomerSites "
				+ "and supplier =:supplier and (user =:user or user is null) and disabled = false";

		if ((frequency != null) && (!dayList.isEmpty())) {
			queryStr = "from BalePickupAssignment where buyCustomerSite in :buyCustomerSites "
					+ "and supplier =:supplier and (user =:user or user is null) and "
					+ "frequency =:frequency and day in :dayList and disabled = false";
		} else if ((frequency != null) && (!monthWeekList.isEmpty())) {

			queryStr = "from BalePickupAssignment where buyCustomerSite in :buyCustomerSites "
					+ "and supplier =:supplier and (user =:user or user is null) and "
					+ "frequency =:frequency and weekNumber in :monthWeekList and disabled = false";
		} else if (frequency != null) {

			queryStr = "from BalePickupAssignment where buyCustomerSite in :buyCustomerSites "
					+ "and supplier =:supplier and (user =:user or user is null) and frequency =:frequency"
					+ " and disabled = false";
		}

		Set<BalePickupAssignment> resultAssignments = new HashSet<BalePickupAssignment>();

		try {
			Query query = session.createQuery(queryStr);

			query.setParameterList("buyCustomerSites", buyCustomerSites);
			query.setParameter("user", users.get(0));
			query.setParameter("supplier", supplier);

			if (frequency != null) {
				query.setParameter("frequency", frequency);
			}
			if (!dayList.isEmpty()) {
				query.setParameterList("dayList", dayList);
			}
			if (!monthWeekList.isEmpty()) {
				query.setParameterList("monthWeekList", monthWeekList);
			}

			List<BalePickupAssignment> assignments = query.list();
			resultAssignments = new HashSet<BalePickupAssignment>(assignments);
		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:getAllAssignmentForActivity=====>"
					+ e);
		}
		return resultAssignments;

	}

	public void merge(BalePickupAssignment assignment) {
		Session session = sessionFactory.getCurrentSession();
		try {
			session.merge(assignment);
		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:merge=====>" + e);

		}

	}

	public List<BalePickupAssignment> getAllOnCallAssignments() {
		List<BalePickupAssignment> assignments = null;

		Session session = sessionFactory.getCurrentSession();

		try {

			Query query = session
					.createQuery("from BalePickupAssignment where  weekNumber =:weekNumber and day =:day"
							+ " and disabled=:disabledFlag");
			query.setParameter("weekNumber", -1);
			query.setParameter("day", -1);
			query.setParameter("disabledFlag", false);
			assignments = query.list();
		}

		catch (RuntimeException e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:getAllOnCallAssignments=====>"
					+ e);

		}
		return assignments;
	}

	

	public Integer updateDriverForStoreSupplierList(GenerateAssignmentDriverPopup generateAssignmentDriverPopup) {
		
		String querySt = null;
		int i = 0;
		Session session = sessionFactory.getCurrentSession();

		try {

			querySt = "update BalePickupSupplierConfiguration set driver1.userId =:dayDriver,"
					+ " driver2.userId =:nightDriver where buyCustomerSiteId =:customerId and supplierId =:supplierId";


			Query query = session.createQuery(querySt);

			query.setParameter("customerId",
					generateAssignmentDriverPopup.getCustomerSiteId());
			query.setParameter("supplierId",
					generateAssignmentDriverPopup.getSupplierId());
			query.setParameter("dayDriver",
					Long.valueOf(generateAssignmentDriverPopup.getDayDriver()));

			if (generateAssignmentDriverPopup.getNightDriver() != null) {

				query.setParameter("nightDriver",
						Long.valueOf(generateAssignmentDriverPopup
								.getNightDriver()));
			} else {
				query.setParameter("nightDriver", null);
			}

			i = query.executeUpdate();
		}

		catch (RuntimeException e) {
			
			LOGGER.error("Error:BalePickUpAssignmentDAO: updateDriverForStoreSupplierList=====>"+ e);

		}
	
		return i;
	}

	/* end */

	public Integer updateAssignmentDates(
			GenerateAssignmentDriverPopup generateAssignmentDriverPopup) {
		
		String querySt = null;
		int i = 0;
		Session session = sessionFactory.getCurrentSession();

		try {

			querySt = "update BalePickupSupplierConfiguration set startDate =:balePickupStartDate, endDate =:balePickupEndDate"
					+ "  where buyCustomerSiteId =:customerId and supplierId =:supplierId";


			Query query = session.createQuery(querySt);

			query.setParameter("customerId",
					generateAssignmentDriverPopup.getCustomerSiteId());
			query.setParameter("supplierId",
					generateAssignmentDriverPopup.getSupplierId());

			query.setDate("balePickupStartDate",
					generateAssignmentDriverPopup.getAssignmentStartDate());

			query.setDate("balePickupEndDate",
					generateAssignmentDriverPopup.getAssignmentEndDate());

			
			i = query.executeUpdate();

		
		}

		catch (RuntimeException e) {
			
			LOGGER.error("Error:BalePickUpAssignmentDAO: updateAssignmentDates=====>"
					+ e);

		}
	
		return i;
	}

	public HashSet<BalePickupAssignment> getPickupAssignmentsForCustomerSite(
			CustomerSite customerSite) {

		List<BalePickupAssignment> assignments = null;
		HashSet<BalePickupAssignment> hashedResults = null;
		Session session = sessionFactory.getCurrentSession();

		try {

			Query query = session
					.createQuery("from BalePickupAssignment where buyCustomerSite.customerSiteId=:customerSiteId and"
							+ " disabled = false");
			query.setParameter("customerSiteId",
					customerSite.getCustomerSiteId());
			assignments = query.list();
			hashedResults = new HashSet<BalePickupAssignment>(assignments);

		} catch (Exception e) {

			LOGGER.error("Error:BalePickUpAssignmentDAO:getPickupAssignmentsForCustomerSite=====>"
					+ e);

		}
		return hashedResults;
	}

	public void delete(List<BalePickupAssignment> assignments) {
		Session session = sessionFactory.getCurrentSession();
		List<Integer> ids = new ArrayList<Integer>();
		for (BalePickupAssignment balePickupAssignment : assignments) {
			ids.add(balePickupAssignment.getBalePickupAssignmentId());
		}

	
		if (ids.size() <= 0) {

			return;
		}

		try {
			Query query = session
					.createQuery("update BalePickupAssignment balePickupAss set balePickupAss.disabled = true where"
							+ " balePickupAss.balePickupAssignmentId in :ids");
			query.setParameterList("ids", ids);
			query.executeUpdate();
		} catch (RuntimeException e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:delete=====>" + e);
		} finally {

		}

	}

	public void disableBalePickupAssignments(List<Integer> customerSiteIds) {

	

		Session session = sessionFactory.getCurrentSession();

		try {
			Query query = session
					.createQuery("update BalePickupAssignment balePickupAss set balePickupAss.disabled = true where"
							+ " balePickupAss.buyCustomerSite.customerSiteId in :ids");
			query.setParameterList("ids", customerSiteIds);
			query.executeUpdate();
		} catch (Exception e) {

			LOGGER.error("Error:BalePickUpAssignmentDAO:disableBalePickupAssignments=====>"
					+ e);
		} finally {

		}

	}

	// added by deepak

	public List<Frequency> getAllFrequencies() {
		List<Frequency> listedResults = null;
		Session session = sessionFactory.getCurrentSession();

		try {

			Query query = session
					.createQuery("from Frequency where enabled = true");

			listedResults = query.list();

		} catch (Exception e) {

			LOGGER.error("Error:BalePickUpAssignmentDAO:getFrequency=====>" + e);

		}
		return listedResults;

	}

	// ended

	public Set<PendingReportDTO> getPendingPickups(Integer buyCustomerId,
			Integer supplierId, Integer currentFrequency, String startDate,
			String endDate) {

		Set<PendingReport> pendingReportSet = null;
		Set<PendingReportDTO> pendingReportDTOSet = new HashSet<PendingReportDTO>();

		Session session = sessionFactory.getCurrentSession();

		try {
			Query query = session
					.createSQLQuery(
							"{CALL GetPendingPickups"
									+ "(:buyCustomerId,:supplierId,:frequencyId,:fromDate,:toDate)}")
					.addEntity(PendingReport.class);

			query.setParameter("buyCustomerId", buyCustomerId);
			query.setParameter("supplierId", supplierId);
			query.setParameter("frequencyId", currentFrequency);
			query.setParameter("fromDate", startDate);
			query.setParameter("toDate", endDate);

			List<PendingReport> result = query.list();

			pendingReportSet = new HashSet<PendingReport>(result);
			
			for(PendingReport pendingReports:pendingReportSet){
				PendingReportDTO pendingReportDTO=new PendingReportDTO(pendingReports);
				pendingReportDTOSet.add(pendingReportDTO);
			}

		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:getPendingPickups=====>"
					+ e);
		}

		return pendingReportDTOSet;

	}

	public List<DriverPickups> getDriverPickupsByDate(Integer driverId,
			String pickupDate, Integer day, Integer weekNumber) {

		Session session = sessionFactory.getCurrentSession();
		List<DriverPickups> driverPickups = new ArrayList<DriverPickups>();

		try {
			Query query = session.createSQLQuery(
					"{CALL GetDriverPickupsByDate"
							+ "(:driverId,:pickupDate,:weekNumber,:day)}")
					.addEntity(DriverPickups.class);

			query.setParameter("driverId", driverId);
			query.setParameter("pickupDate", pickupDate);
			query.setParameter("weekNumber", weekNumber);
			query.setParameter("day", day);

			driverPickups = query.list();
			LOGGER.info(" driverPickups " + driverPickups.size());

		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:getDriverPickupsByDate=====>"
					+ e);
		}

		return driverPickups;

	}

	public List<SupplierPickupsSP> getAllPickupsForSupplier(Supplier supplier,
			String storeSearch, Integer driverId, String vehiclePlateNo) {
		Session session = sessionFactory.getCurrentSession();

		List<SupplierPickupsSP> supplierPickups = new ArrayList<SupplierPickupsSP>();
		List<SupplierPickupsSP> supplierPickupsUpdated = new ArrayList<SupplierPickupsSP>();
		try {
			Query query = session
					.createSQLQuery(
							"{CALL GetSupplierPickups(:supplierId,:storeSearch,:driverId,:vehiclePlateNo)}")
					.addEntity(SupplierPickupsSP.class);

			query.setParameter("supplierId", supplier.getSupplierId());
			query.setParameter("storeSearch", storeSearch);
			query.setParameter("driverId", driverId);
			query.setParameter("vehiclePlateNo", vehiclePlateNo);

			supplierPickups = query.list();
			
			if(supplierPickups!=null && supplierPickups.size()>0){
				
				for(SupplierPickupsSP supplierPickup : supplierPickups){
					
					Integer driver1Id=supplierPickup.getDriver1Id();
				    Integer driver2Id=supplierPickup.getDriver2Id();
				    
				    if((driver1Id !=null && driver1Id.equals(driverId)) || (driver2Id !=null && driver2Id.equals(driverId))){
				    	supplierPickupsUpdated.add(supplierPickup);
				    }
					
				}
			}
		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO: getAllPickupsForSupplier=====>"+ e);
		}
		return supplierPickupsUpdated;
	}

	public List<SupplierSavedPickupsSP> getAllSavedPickups(Supplier supplier,Integer driverId, String vehiclePlateNo) {
		
		Session session = sessionFactory.getCurrentSession();
		
		List<SupplierSavedPickupsSP> supplierPickups = new ArrayList<SupplierSavedPickupsSP>();

		try {
			Query query = session.createSQLQuery(
					"{CALL GetSupplierSavedPickups(:supplierId,:driverId, :vehiclePlateNo)}")
					.addEntity(SupplierSavedPickupsSP.class);

			query.setParameter("supplierId", supplier.getSupplierId());
			query.setParameter("driverId", driverId);
			query.setParameter("vehiclePlateNo", vehiclePlateNo);

			supplierPickups = query.list();
			

			Set<SupplierSavedPickupsSP> supplierPickupsUnique = new LinkedHashSet<SupplierSavedPickupsSP>(supplierPickups);
			supplierPickups= new ArrayList<SupplierSavedPickupsSP>(supplierPickupsUnique);
			

		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO: getAllSavedPickups  =====>"
					+ e);
			
		}
		return supplierPickups;
	}

	public List<SupplierPickupDetailsSP> getPickupDetailsForSupplier(
			Supplier supplier, String buyCustomerSiteId) {
		Session session = sessionFactory.getCurrentSession();

		List<SupplierPickupDetailsSP> supplierPickupDetails = new ArrayList<SupplierPickupDetailsSP>();

		try {
			Query query = session
					.createSQLQuery(
							"{CALL GetSupplierPickupDetails(:buyCustomerSiteId,:supplierId)}")
					.addEntity(SupplierPickupDetailsSP.class);

			query.setParameter("supplierId", supplier.getSupplierId());
			query.setParameter("buyCustomerSiteId", buyCustomerSiteId);

			supplierPickupDetails = query.list();

			// LOGGER.info(" supplierPickups==> " + supplierPickups.size());

		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO: getPickupDetailsForSupplier=====>"
					+ e);
		}
		return supplierPickupDetails;
	}

	public List<MaterialByCustomerAndSupplierSP> getMateralByBuyCustomerAndSupplier(
			Integer buyCustomerSiteId, Integer supplierId,
			String vehiclePlateNo, String pickupDate) {

				Session session = sessionFactory.getCurrentSession();
		List<MaterialByCustomerAndSupplierSP> materialByCustomerAndSupplierProList = new ArrayList<MaterialByCustomerAndSupplierSP>();

				
		try {
			Query query = session
					.createSQLQuery(
							"{CALL GetMateralByBuyCustomerAndSupplier"+ "(:buyCustomerSiteId,:supplierId,:vehiclePlateNo,:pickupDate)}")
					.addEntity(MaterialByCustomerAndSupplierSP.class);

			query.setParameter("buyCustomerSiteId", buyCustomerSiteId);
			query.setParameter("supplierId", supplierId);
			query.setParameter("vehiclePlateNo", vehiclePlateNo);
			query.setParameter("pickupDate", pickupDate);

			materialByCustomerAndSupplierProList = query.list();
			
		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO: getMateralByBuyCustomerAndSupplier=====>"
					+ e);
		}

		return materialByCustomerAndSupplierProList;

	}

	public List<SavedPickups> getSavedOnlyTrip(Integer driverId,
			String pickupDate) {

		Session session = sessionFactory.getCurrentSession();
		List<SavedPickups> savedPickups = new ArrayList<SavedPickups>();

		try {
			Query query = session.createSQLQuery(
					"{CALL GetSavedPickups" + "(:driverId,:pickupDate)}")
					.addEntity(SavedPickups.class);

			query.setParameter("driverId", driverId);
			query.setParameter("pickupDate", pickupDate);

			savedPickups = query.list();
			LOGGER.info(" driverPickups " + savedPickups.size());

		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:getSavedOnlyTrip=====>"
					+ e);
		}

		return savedPickups;

	}

	public List<MaterialInfo> getMaterialByAssignmentId(Integer assignmehtaId,
			String pickupDate) {

		Session session = sessionFactory.getCurrentSession();
		List<MaterialInfo> savedPickups = new ArrayList<MaterialInfo>();

		try {
			Query query = session.createSQLQuery(
					"{CALL GetMaterialsByAssignment"
							+ "(:assignmehtaId,:pickupDate)}").addEntity(
					MaterialInfo.class);

			query.setParameter("assignmehtaId", assignmehtaId);
			query.setParameter("pickupDate", pickupDate);

			savedPickups = query.list();
			LOGGER.info(" driverPickups " + savedPickups.size());

		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:getMaterialByAssignmentId=====>"
					+ e);
		}

		return savedPickups;

	}

	
	public List<BalePickupSummaryView> getAllPickups(BalePickupFilterDTO balePickupFilter) {
		
		Session session = sessionFactory.getCurrentSession();
		List<BalePickupSummaryView> pickupList = null;
		try{
		String stringQuery = null;
		stringQuery = "from BalePickupSummaryView";
		
		if (balePickupFilter.getCustomerId() != null) {
			if (balePickupFilter.getSupplierId() != null) {
				if (balePickupFilter.getStartDate() != null) {
					if (balePickupFilter.getEndDate() != null) {
						stringQuery = "from BalePickupSummaryView where buyCustomerId=:buyCustomerId and "
								+ " supplierId=:supplierId and pickupDate between :startDate and :endDate";
					} else {
						stringQuery = "from BalePickupSummaryView where buyCustomerId=:buyCustomerId and "
								+ " supplierId=:supplierId and pickupDate >=:startDate";
					}
				} else {
					if (balePickupFilter.getEndDate() != null) {
						stringQuery = "from BalePickupSummaryView where buyCustomerId=:buyCustomerId and "
								+ " supplierId=:supplierId and pickupDate <=:endDate";
					} else {
						stringQuery = "from BalePickupSummaryView where buyCustomerId=:buyCustomerId and "
								+ " supplierId=:supplierId";
					}
				}
			} else {
				if (balePickupFilter.getStartDate() != null) {
					if (balePickupFilter.getEndDate() != null) {
						stringQuery = "from BalePickupSummaryView where buyCustomerId=:buyCustomerId and "
								+ "  pickupDate between :startDate and :endDate";
					} else {
						stringQuery = "from BalePickupSummaryView where buyCustomerId=:buyCustomerId and "
								+ "  pickupDate >=:startDate";
					}
				} else {
					if (balePickupFilter.getEndDate() != null) {
						stringQuery = "from BalePickupSummaryView where buyCustomerId=:buyCustomerId and "
								+ "  pickupDate <=:endDate";
					} else {
						stringQuery = "from BalePickupSummaryView where buyCustomerId=:buyCustomerId ";
					}
				}
			}
		} else {
			if (balePickupFilter.getSupplierId() != null) {
				if (balePickupFilter.getStartDate() != null) {
					if (balePickupFilter.getEndDate() != null) {
						stringQuery = "from BalePickupSummaryView where "
								+ " supplierId=:supplierId and pickupDate between :startDate and :endDate";
					} else {
						stringQuery = "from BalePickupSummaryView where "
								+ " supplierId=:supplierId and pickupDate >=:startDate";
					}
				} else {
					if (balePickupFilter.getEndDate() != null) {
						stringQuery = "from BalePickupSummaryView where "
								+ " supplierId=:supplierId and pickupDate <=:endDate";
					} else {
						stringQuery = "from BalePickupSummaryView where "
								+ " supplierId=:supplierId ";
					}
				}
			} else {
				if (balePickupFilter.getStartDate() != null) {
					if (balePickupFilter.getEndDate() != null) {
						stringQuery = "from BalePickupSummaryView where pickupDate between :startDate and :endDate";
					} else {
						stringQuery = "from BalePickupSummaryView where "
								+ "  pickupDate >=:startDate";
					}
				} else {
					if (balePickupFilter.getEndDate() != null) {
						stringQuery = "from BalePickupSummaryView where pickupDate <=:endDate";
					} else {
						stringQuery = "from BalePickupSummaryView";
					}
				}
			}
		}
	Query query = session.createQuery(stringQuery);

		if (balePickupFilter.getCustomerId() != null) {
			query.setParameter("buyCustomerId",
					balePickupFilter.getCustomerId());

		}

		if (balePickupFilter.getSupplierId() != null) {
			query.setParameter("supplierId", balePickupFilter.getSupplierId());

		}

		if (balePickupFilter.getStartDate() != null) {
			query.setDate("startDate", balePickupFilter.getStartDate());

		}

		if (balePickupFilter.getEndDate() != null) {
			query.setDate("endDate", balePickupFilter.getEndDate());

		}

	
			pickupList = query.list();
		} catch (Exception e) {
			LOGGER.error("Error while extraction data from view getAllPickups method" + e);
			return null;
		}
	
		
		return pickupList;
	}

	public List<BalePickupSummaryView> getAllPickupsForDAR(BalePickupFilterDTO balePickupFilter) {
		Session session = sessionFactory.getCurrentSession();

		List<BalePickupSummaryView> pickupList = null;
		String stringQuery = null;
		try {
		stringQuery = "from BalePickupSummaryView where pickupDate between :startDate and :endDate";

		if (balePickupFilter.getCustomerId() != null) {
			stringQuery = stringQuery + " and buyCustomerId=:buyCustomerId";
		}

		if (balePickupFilter.getSellCustomerId() != null) {
			stringQuery = stringQuery + " and sellCustomerId=:sellCustomerId";
		}

		if (balePickupFilter.getSupplierId() != null) {
			stringQuery = stringQuery + " and supplierId=:supplierId";
		}

		if (balePickupFilter.getIncidentId() != null) {
			stringQuery = stringQuery + " and incidentTypeId=:incidentTypeId";
		}

		Query query = session.createQuery(stringQuery);
		query.setDate("startDate", balePickupFilter.getStartDate());
		query.setDate("endDate", balePickupFilter.getEndDate());

		if (balePickupFilter.getCustomerId() != null) {
			query.setParameter("buyCustomerId",
					balePickupFilter.getCustomerId());
		}

		if (balePickupFilter.getSupplierId() != null) {
			query.setParameter("supplierId", balePickupFilter.getSupplierId());

		}

		if (balePickupFilter.getSellCustomerId() != null) {
			query.setParameter("sellCustomerId",
					balePickupFilter.getSellCustomerId());
		}

		if (balePickupFilter.getIncidentId() != null) {
			query.setParameter("incidentTypeId",
					balePickupFilter.getIncidentId());
		}

		
			pickupList = query.list();
		} catch (Exception e) {
			LOGGER.error("Error while extraction data from view getAllPickupsForDAR method"	+ e);
		}
		
		return pickupList;
	}

	public LinkedHashSet<Image> getBalePickupImages(Integer balePickupId) {
			
		Session session = sessionFactory.getCurrentSession();
		LinkedHashSet<Image> resultBalePickupImages = null;
		try{
		String stringQuery = "from Image where tripId =:balePickupId ";

		Query query = session.createQuery(stringQuery);
		query.setParameter("balePickupId", balePickupId);
		
			
		List<Image> imageList = query.list();

		resultBalePickupImages = new LinkedHashSet<Image>(imageList);
			}catch(Exception e){
				LOGGER.error("Error: Error in getBalePickupImages  "+e);
				return null;
			}
		return resultBalePickupImages;

	}

	public void mergeAssignment(BalePickupAssignment assignment) {
		Session session = sessionFactory.getCurrentSession();

		try {
			session.merge(assignment);
		} catch (Exception e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:mergeAssignment=====>"
					+ e);
		}

	}

	public HashSet<Destination> getDestination(String storeName) {
		Session session = sessionFactory.getCurrentSession();
		HashSet<Destination> destinations = new HashSet<Destination>();
		try{
		String stringQuery = "from Pickupsview where buyCustomerSiteName =:storeName ";

		Query query = session.createQuery(stringQuery);

		query.setParameter("storeName", storeName);
			
		List<Pickupsview> destinationList = query.list();

		

		for (Pickupsview pickupsview : destinationList) {
			Destination destination = new Destination();
			destination.setName(pickupsview.getSellCustomerSiteName());
			destination.setDestinationId(pickupsview.getSellCustomerSiteId());
			destinations.add(destination);
		}
			}catch(Exception e){
				LOGGER.error("Error: BalePickUpAssignmentDAO: getDestination =====>"+ e);
				return null;
			}
		return destinations;

	}

	public BalePickupAssignment getAssignment(Integer customerSiteId,
			Integer day, Integer weekNumber) {
		
		BalePickupAssignment balePickupAssignment = null;
		try{
		Session session = sessionFactory.getCurrentSession();

		String stringQuery = "from BalePickupAssignment where buyCustomerSite.customerSiteId =:customerSiteId and"
				+ " day =:day and weekNumber =:weekNumber and disabled = false";

		Query query = session.createQuery(stringQuery);

		query.setParameter("customerSiteId", customerSiteId);
		query.setParameter("day", day);
		query.setParameter("weekNumber", weekNumber);
		
		List<BalePickupAssignment> balePickupAssignmentList = query.list();

		if (!balePickupAssignmentList.isEmpty()) {
			balePickupAssignment = balePickupAssignmentList.get(0);
		}
		}catch(Exception e){
			LOGGER.error("Error: BalePickUpAssignmentDAO: getAssignment =====>"+ e);
			return null;
		}
		return balePickupAssignment;
	}

	public List<DriverSupplierPickupsView> getAssignmentDrivers(AssignDriverDTO assignDriverDTO) {
		
		Session session = sessionFactory.getCurrentSession();
		List<DriverSupplierPickupsView> driverSupplierPickupsView = null;
		String stringQuery = "from DriverSupplierPickupsView ";
		try {
		if (assignDriverDTO.getCustomerId() != null) {
			if (assignDriverDTO.getSupplierId() != null) {

				if (assignDriverDTO.getStartDate() != null) {
					if (assignDriverDTO.getEndDate() != null) {
						stringQuery = "from DriverSupplierPickupsView where supplierId=:supplierId and customerId=:customerId "
								+ "  and startDate <=:endDate and (endDate is null or endDate >=:startDate)";
					} else {
						stringQuery = "from DriverSupplierPickupsView where supplierId=:supplierId and customerId=:customerId "
								+ "  and (endDate is null or endDate >=:startDate)";
					}
				} else {
					if (assignDriverDTO.getEndDate() != null) {
						stringQuery = "from DriverSupplierPickupsView where supplierId=:supplierId and customerId=:customerId "
								+ "  and startDate <=:endDate";
					} else {
						stringQuery = "from DriverSupplierPickupsView where supplierId=:supplierId and customerId=:customerId";
					}
				}

			} else {
				if (assignDriverDTO.getStartDate() != null) {
					if (assignDriverDTO.getEndDate() != null) {
						stringQuery = "from DriverSupplierPickupsView where customerId=:customerId "
								+ "  and startDate <=:endDate and (endDate is null or endDate >=:startDate)";
					} else {
						stringQuery = "from DriverSupplierPickupsView where customerId=:customerId "
								+ "  and (endDate is null or endDate >=:startDate)";
					}
				} else {
					if (assignDriverDTO.getEndDate() != null) {
						stringQuery = "from DriverSupplierPickupsView where  customerId=:customerId "
								+ "  and startDate <=:endDate";
					} else {
						stringQuery = "from DriverSupplierPickupsView where customerId=:customerId";
					}
				}
			}
		} else {
			if (assignDriverDTO.getSupplierId() != null) {

				if (assignDriverDTO.getStartDate() != null) {
					if (assignDriverDTO.getEndDate() != null) {
						stringQuery = "from DriverSupplierPickupsView where supplierId=:supplierId  "
								+ "  and startDate <=:endDate and (endDate is null or endDate >=:startDate)";
					} else {
						stringQuery = "from DriverSupplierPickupsView where supplierId=:supplierId "
								+ "  and (endDate is null or endDate >=:startDate)";
					}
				} else {
					if (assignDriverDTO.getEndDate() != null) {
						stringQuery = "from DriverSupplierPickupsView where supplierId=:supplierId  "
								+ "  and startDate <=:endDate";
					} else {
						stringQuery = "from DriverSupplierPickupsView where supplierId=:supplierId ";
					}
				}

			} else {
				if (assignDriverDTO.getStartDate() != null) {
					if (assignDriverDTO.getEndDate() != null) {
						stringQuery = "from DriverSupplierPickupsView where startDate <=:endDate and (endDate is null or endDate >=:startDate)";
					} else {
						stringQuery = "from DriverSupplierPickupsView where (endDate is null or endDate >=:startDate)";
					}
				} else {
					if (assignDriverDTO.getEndDate() != null) {
						stringQuery = "from DriverSupplierPickupsView where  startDate <=:endDate";
					} else {
						stringQuery = "from DriverSupplierPickupsView";
					}
				}
			}
		}
		
		Query query = session.createQuery(stringQuery);

		if (assignDriverDTO.getCustomerId() != null) {
			query.setParameter("customerId", assignDriverDTO.getCustomerId());
		}
		if (assignDriverDTO.getSupplierId() != null) {
			query.setParameter("supplierId", assignDriverDTO.getSupplierId());
		}
		if (assignDriverDTO.getEndDate() != null) {
			query.setDate("endDate", assignDriverDTO.getEndDate());
		}
		if (assignDriverDTO.getStartDate() != null) {
			query.setDate("startDate", assignDriverDTO.getStartDate());
		}

		
		
			driverSupplierPickupsView = query.list();
			

		} catch (Exception e) {
			LOGGER.error("Exception" + e);
			return null;
		}
		return driverSupplierPickupsView;
	}
}

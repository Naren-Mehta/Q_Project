package com.wm.brta.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wm.brta.domain.BalePickupTripDetailsForSC;
import com.wm.brta.domain.Customer;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.SCAndRMTStoreMappingWithLatLong;
import com.wm.brta.domain.ServiceChannelCustomers;
import com.wm.brta.domain.ServiceChannelFailedWorkOrders;
import com.wm.brta.domain.ServiceChannelWorkOrders;
import com.wm.brta.dto.BalePickupMobileDetailsForSC;
import com.wm.brta.util.BaleUtils;

@Repository
@Transactional
public class ServiceChannelDAO {
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServiceChannelDAO.class);
	
	@SuppressWarnings("unchecked")
	public Integer persistWorkOrdersFromESB(List<ServiceChannelWorkOrders> persistentObject) {
		Integer count = 0;
		try {
			
		for(Iterator<ServiceChannelWorkOrders> i = persistentObject.iterator(); i.hasNext(); ) {
			  ServiceChannelWorkOrders workOrder = i.next();
			  ServiceChannelWorkOrders sCWO = new ServiceChannelWorkOrders( );
			  workOrder.setUpdatedBy(sCWO.getUpdatedBy());
			  workOrder.setUpdatedDate(sCWO.getUpdatedDate());
			  workOrder.setCreatedDate(sCWO.getCreatedDate());
			 
			  Session session = sessionFactory.getCurrentSession();
			Integer id= (Integer) session.save(workOrder);
			count++;
		}
			} catch (Exception e) {
				LOGGER.error("Exception in ServiceChannelDAO DAO: persistWorkOrdersFromESB" + e);
			
			return 0;
		}
			
		return count;

	
	}	
	
	public SCAndRMTStoreMappingWithLatLong  getRMTStoreIdByStoreName(String storeName) {

		
		List <SCAndRMTStoreMappingWithLatLong> rMTStoreIdWithLatLong =null;
		SCAndRMTStoreMappingWithLatLong rMTStoreDetails =null;
	

		try {
		
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("select new SCAndRMTStoreMappingWithLatLong (rmt.scStoreId,rmt.longitude,rmt.latitude) from "
					+ " SCAndRMTStoreMappingWithLatLong rmt where rmt.storeName =:storeName ");
				
			query.setParameter("storeName", storeName);
			rMTStoreIdWithLatLong = query.list();
			
			if (rMTStoreIdWithLatLong != null && rMTStoreIdWithLatLong.size()>0) {
				rMTStoreDetails = rMTStoreIdWithLatLong.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("--------------------ERROR in getRMTStoreIdByStoreName---------- \n "+ e);
			return rMTStoreDetails;
		}
		
	
		return rMTStoreDetails;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public boolean findServiceChannelCustomers(Integer customerId ) {
				
		
		boolean customerExist = false;
		Session session = sessionFactory.getCurrentSession();
			  try {
				  
	Query query = session.createQuery("from ServiceChannelCustomers scc where scc.customerId =:customerId ");
					query.setParameter("customerId", customerId);

					List<ServiceChannelCustomers> customerSiteList = query.list();
					if (customerSiteList != null && customerSiteList.size()>0) {
						customerExist = true;
					}
	
			} catch (Exception e) {
				LOGGER.error("Exception in ServiceChannelDAO DAO: findServiceChannelCustomers" + e);
		
			return false;
		}
			
		return customerExist;

	
	}
	
	@SuppressWarnings("unchecked")
	public List<ServiceChannelWorkOrders> retriveSCWorkOrders(Integer storeId) {

		 List<ServiceChannelWorkOrders> serviceChannelWorkOrders = null;
		 List<ServiceChannelWorkOrders> sCWorkOrder = null;
		
		
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery("from ServiceChannelWorkOrders where storeId =:storeId ");
			query.setParameter("storeId", storeId);

			
			sCWorkOrder= query.list();
			if (sCWorkOrder != null && sCWorkOrder.size()>0) {
				
				serviceChannelWorkOrders = new ArrayList<ServiceChannelWorkOrders>(sCWorkOrder);
			}
			
		} catch (Exception e) {
			LOGGER.error("====ERROR in ServiceChannelDAO:  retriveSCWorkOrders===== "+ e);
		}
		
		
		return serviceChannelWorkOrders;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	public Integer retriveWOByStoreIdAndPickupDate(ServiceChannelWorkOrders serviceChannelWorkOrders) {

		 Integer serviceChannelWorkOrder = 0;
		 List<ServiceChannelWorkOrders> sCWorkOrder = null;
		 Date pickupDate = serviceChannelWorkOrders.getCallDate();
		
		
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery("from ServiceChannelWorkOrders where storeId =:storeId and callDate <= :pickupDate and scheduledDate >=:pickupDate");
			query.setParameter("storeId", serviceChannelWorkOrders.getStoreId());
			query.setDate("pickupDate",pickupDate);
			
			sCWorkOrder= query.list();
			if (sCWorkOrder != null && sCWorkOrder.size()>0) {
				
				serviceChannelWorkOrder = sCWorkOrder.get(0).getWorkOrderNumber();
			}
			
		} catch (Exception e) {
			LOGGER.error("====ERROR in ServiceChannelDAO:  retriveWOByStoreIdAndPickupDate===== "+ e);
		}
	
		
		return serviceChannelWorkOrder;
	}
	 
	
	
	public ServiceChannelWorkOrders getWODetailsFromWorkOrderNumber(Integer workOrderNumber){
		ServiceChannelWorkOrders serviceChannelWorkOrders =null;
		List<ServiceChannelWorkOrders> sCWorkOrder =null; 
		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery("from ServiceChannelWorkOrders where workOrderNumber =:workOrderNumber ");
			query.setParameter("workOrderNumber", workOrderNumber);

			
			sCWorkOrder= query.list();
			if (sCWorkOrder != null && sCWorkOrder.size()>0) {
				
				serviceChannelWorkOrders = sCWorkOrder.get(0);
			}
			
		} catch (Exception e) {
			LOGGER.error("====ERROR in ServiceChannelDAO:  getAllWODetailsFromWorkOrderNumber===== "+ e);
		}
		
	
		return serviceChannelWorkOrders;
	}
	
		
		
	

	@SuppressWarnings("unchecked")
	public BalePickupTripDetailsForSC retriveSCWorkOrdersByID(Integer Id) {

		BalePickupTripDetailsForSC serviceChannelWorkOrders = null;
		 List<BalePickupTripDetailsForSC> sCWorkOrder = null;
		
		
		Session session = sessionFactory.getCurrentSession();
		try {
		
			
			Query query = session.createQuery("select new BalePickupTripDetailsForSC(scc.status,scc.checkinDateTime,scc.checkoutDateTime,scc.resolution, "
					+ " scc.latitude,scc.longitude, scc.bol , scc.storeName, scc.updatedDevice, scc.balesRemaining, scc.checkInLatitude, scc.checkInLongitude ) from "
					+ " BalePickupTripDetailsForSC scc where scc.Id =:Id ");
			query.setParameter("Id", Id);

			
			sCWorkOrder= query.list();
			if (sCWorkOrder != null && sCWorkOrder.size()>0) {
			
				serviceChannelWorkOrders = sCWorkOrder.get(0);
				
			}
			
		} catch (Exception e) {
			LOGGER.error("====ERROR in ServiceChannelDAO:  retriveSCWorkOrdersByID ===== "+ e);
		}
		
	
		return serviceChannelWorkOrders;
	}
	
	
	public Integer getCustIdFromSiteIdForSC(Integer customerSiteId) { 
		Integer customerId = 0;
		
		Session session = sessionFactory.getCurrentSession();	
		try {			
			Query query = session.createQuery("select new Customer(customerSite.customer.customerId) from CustomerSite customerSite"
					+ " where customerSite.customerSiteId =:customerSiteId ");
			query.setParameter("customerSiteId", customerSiteId);
			List<Customer> customerSiteList = query.list();
			if (customerSiteList != null) {
				customerId = customerSiteList.get(0).getCustomerId();
			}
		} catch (Exception e) {
			LOGGER.error("====ERROR in getCustIdFromSiteIdForSC "	+ e);
		}
	
		return customerId;
	}
	
	
	public List<ServiceChannelCustomers> getEnabledSCCustomers ( ) { 
		List<ServiceChannelCustomers> suscriberIdList = null;
	
	
		Session session = sessionFactory.getCurrentSession();
		
		try {
			
			Query query = session.createQuery("select new ServiceChannelCustomers(scc.id,scc.suscriberId,scc.customerId,scc.enabled) from "
					+ " ServiceChannelCustomers scc where  scc.enabled =:enabled ");
			query.setParameter("enabled", true);
			suscriberIdList = query.list();
			
			
		} catch (Exception e) {
			LOGGER.error("====ERROR in getCustIdFromSiteIdForSC "	+ e);
		}
			return suscriberIdList;
	}
	
	
	
   
	public boolean persistESBWorkOrders(ServiceChannelWorkOrders workOrders) {
		Integer id=-1;
		Boolean status= false;
		Boolean validaterecord = false;
		try {
			 validaterecord = validateWorkOrderRecordExistance(workOrders);
		
			if(validaterecord!=true){
			Session session = sessionFactory.getCurrentSession();			
			session.setFlushMode(FlushMode.ALWAYS);
			 id= (Integer) session.save(workOrders);
			
			if(id>0){
				session.flush();
				session.clear();
				status=true;
			}else{
				session.flush();
				session.clear();
			}
		}else{
			status = false;
		}
			} catch (Exception e) {
				LOGGER.error("Exception in ServiceChannelDAO DAO: persistWorkOrdersFromESB" + e +"\n");
				LOGGER.error("Trying to push in ServiceChannelFailedWorkOrders Table");
		}	
		return status;
	}	



	public Integer persistFailedWorkOrdersTOESB(ServiceChannelFailedWorkOrders workOrders) {
		Integer id=-1;
		
		try {
			Session session = sessionFactory.getCurrentSession();

			 id= (Integer) session.save(workOrders);
			
				
			} catch (Exception e) {
				LOGGER.error("Error in persistFailedWorkOrdersTOESB   "+ e);
		}	
		return id;
	}
	
	@SuppressWarnings("unchecked")
   
	public Boolean validateWorkOrderRecordExistance(ServiceChannelWorkOrders workOrders) {

		
		Boolean workOrderRecord=false;
		List<ServiceChannelWorkOrders> workOrder=new ArrayList<ServiceChannelWorkOrders> ();
		try {
			Session session = sessionFactory.getCurrentSession();
			
			Query query = session.createQuery("from ServiceChannelWorkOrders sc where sc.workOrderNumber =:workOrderNumber"
					+ " and sc.storeId =:storeId and sc.scheduledDate =:scheduledDate and sc.callDate =:callDate"); 
			query.setParameter("storeId",workOrders.getStoreId());
			query.setParameter("workOrderNumber",workOrders.getWorkOrderNumber());
			query.setParameter("scheduledDate", BaleUtils.toNearestWholeMinute(workOrders.getScheduledDate()));
			query.setParameter("callDate",BaleUtils.toNearestWholeMinute(workOrders.getCallDate()));
			workOrder = query.list();
			if (workOrder != null && workOrder.size()>0) {
				workOrderRecord= true;
			}
		}catch(Exception e){
			LOGGER.error("Error in ServiceChannelDAO: validateWorkOrderRecordExistance"+e);
		}
	
	return workOrderRecord;
	}
	
	
	public Integer getCustomerIdByStoreId(Integer storeId) {

		Integer customerId = 0;

		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery("from CustomerSite where customersiteid =:storeId ");
			query.setParameter("customersiteid", storeId);

			List<CustomerSite> customerSiteList = query.list();
			if (customerSiteList != null && customerSiteList.size()>0) {
				customerId = customerSiteList.get(0).getCustomer().getCustomerId();
			}
			
		} catch (Exception e) {
			LOGGER.error("====ERROR in getBuyCustomerSiteFromCustomerSiteId "+ e);
		}
		
		return customerId;
	}
	
	public void deleteTripFromBalePickupTrip(BalePickupMobileDetailsForSC balePickupMobileDetailsForSC) {
		Session session = sessionFactory.getCurrentSession();
		
		
		
	

		try {
			
			String imageDeleteQuery="delete Image where tripId in (select tripId from BalePickupTrip where"
							+ " buyCustomerSite.customerSiteId =:storeId and pickupDate =:pickupDate "
							+ " and supplier.supplierId =:supplierId and material.materialId =:materialId) ";
			
			String checkRecordQuery = "delete from BalePickupTrip sc where sc.supplier.supplierId =:supplierId and "
					+ " sc.material.materialId =:materialId and "
					+ " sc.buyCustomerSite.customerSiteId =:storeId "
					+ " and sc.pickupDate between :startOfDay and :endOfDay";
			
			Query query = session.createQuery(imageDeleteQuery);
				
			Query queryForBale = session.createQuery(checkRecordQuery);

			
			query.setParameter("storeId", balePickupMobileDetailsForSC.getCustomerSite());
			query.setParameter("supplierId", balePickupMobileDetailsForSC.getSupplierId());			
			query.setParameter("materialId", balePickupMobileDetailsForSC.getMaterial());
			query.setDate("pickupDate", BaleUtils.convertStringToDate(balePickupMobileDetailsForSC.getCheckinDate()));
			queryForBale.setParameter("storeId", balePickupMobileDetailsForSC.getCustomerSite());
			queryForBale.setParameter("supplierId", balePickupMobileDetailsForSC.getSupplierId());			
			queryForBale.setParameter("materialId", balePickupMobileDetailsForSC.getMaterial());
			queryForBale.setDate("startOfDay", BaleUtils.getStartOfDay(
					BaleUtils.convertStringToDate(balePickupMobileDetailsForSC.getCheckinDate())));
			queryForBale.setDate("endOfDay", BaleUtils.getEndOfDay(
					BaleUtils.convertStringToDate(balePickupMobileDetailsForSC.getCheckinDate())));
			
			int i = query.executeUpdate();
			int y = queryForBale.executeUpdate();
			
			
		} catch (Exception e) {
			LOGGER.error("Error:BalePickupTripDAO:deleteTrip=====>" + e);
		}

	}

}

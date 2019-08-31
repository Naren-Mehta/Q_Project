package com.wm.brta.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wm.brta.constants.ServiceChannelEnumConstants;
import com.wm.brta.dao.BalePickupTripDAO;
import com.wm.brta.dao.ServiceChannelDAO;
import com.wm.brta.domain.BalePickupTripDetailsForSC;
import com.wm.brta.domain.SCAndRMTStoreMappingWithLatLong;
import com.wm.brta.domain.ServiceChannelCustomers;
import com.wm.brta.domain.ServiceChannelFailedWorkOrders;
import com.wm.brta.domain.ServiceChannelWorkOrders;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.User;
import com.wm.brta.dto.BalePickupMobileDetailsForSC;
import com.wm.brta.dto.SavedWorkOrderAddressToServiceChannel;
import com.wm.brta.dto.SavedWorkOrderLocationToServiceChannel;
import com.wm.brta.dto.SavedWorkOrdersToServiceChannel;
import com.wm.brta.dto.ServiceChannelESBDTO;
import com.wm.brta.dto.ServiceChannelWorkorderDTO;
import com.wm.brta.service.BalePickupService;
import com.wm.brta.service.ServiceChannelService;
import com.wm.brta.service.UserService;
import com.wm.brta.util.BaleUtils;

//@Transactional
@Component("scerviceChannelService")
public class ServiceChannelServiceImpl implements ServiceChannelService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceChannelServiceImpl.class);
	@Autowired
	ServiceChannelDAO serviceChannelDAO;
	
	@Autowired
	UserService userService;
	
	@Autowired
	BalePickupTripDAO balePickupTripDAO;
	
	@Autowired
	BalePickupService balePickupService;

	@Override
	public Integer serviceChannelWorkOrders(
			List<ServiceChannelWorkOrders> scWorkOrder) {
		Integer id = 0;

		id = serviceChannelDAO.persistWorkOrdersFromESB(scWorkOrder);

		return id;

	}

	@Override
	public boolean customerIdFromServiceChannelWorkOrders(Integer id) {
		boolean custId = false;

		custId = serviceChannelDAO.findServiceChannelCustomers(id);

		return custId;

	}

	
	@Override
	public List<ServiceChannelWorkOrders> retriveSCWorkOrders(Integer storeId) {
		List<ServiceChannelWorkOrders> workOrders = null;

		workOrders = serviceChannelDAO.retriveSCWorkOrders(storeId);

		return workOrders;

	}
	
	
	
	@Override
	public Integer retriveSCWOByStoreIdAndPickupDate(ServiceChannelWorkOrders serviceChannelWorkOrders) {
		Integer workOrder = 0;
		workOrder = serviceChannelDAO.retriveWOByStoreIdAndPickupDate(serviceChannelWorkOrders);
		return workOrder;

	}
	
	@Override
	public Integer deleteCheckInCheckOut(BalePickupMobileDetailsForSC balePickupMobileDetailsForSC) {
		
		
		Integer id=-1;
		
		try {
			
			User userforSupplier = userService.getUserObjectFromUserId(Long.valueOf(balePickupMobileDetailsForSC.getDriver()));
			Supplier supplier = userforSupplier.getSupplier();
			balePickupMobileDetailsForSC.setSupplierId(supplier.getSupplierId());
			
			
			
			
			serviceChannelDAO.deleteTripFromBalePickupTrip(balePickupMobileDetailsForSC);
			id = balePickupService.deleteSCTripRecord(balePickupMobileDetailsForSC);
			
		}catch(Exception e){
			LOGGER.error("===Exception in  deleteCheckInCheckOut====="+e);
			
		}
		
		return id;
		
	}
	
	public SCAndRMTStoreMappingWithLatLong  getRMTStoreIdByStoreName(String storeName){
		SCAndRMTStoreMappingWithLatLong sCAndRMTStoreMappingWithLatLong=null;
		
		sCAndRMTStoreMappingWithLatLong = serviceChannelDAO.getRMTStoreIdByStoreName(storeName);
		
		
		return sCAndRMTStoreMappingWithLatLong;
	}
	
	
	
	

	@Override
	public Integer getCustIdFromSiteIdForSC(Integer customerSiteId) {
		Integer id = 0;
		id = serviceChannelDAO.getCustIdFromSiteIdForSC(customerSiteId);

		return id;
	}

	@Override
	public List<ServiceChannelCustomers> serviceChannelCustomers() {
		List<ServiceChannelCustomers> ServiceChannelCustomers = null;

		ServiceChannelCustomers = serviceChannelDAO.getEnabledSCCustomers();

		return ServiceChannelCustomers;

	}

	
	public boolean workOrdersByESB(ServiceChannelESBDTO workOrdersByESB) {
		
		Boolean recordStatus = false;
		Boolean isSuccessful = true;
		List<ServiceChannelWorkOrders> scWorkOrderList = new ArrayList<ServiceChannelWorkOrders>();
		try {
			String requestTrackingId = workOrdersByESB.getRequest_tracking_id();
			List<ServiceChannelWorkorderDTO> serviceChannelWorkorderDTOList = workOrdersByESB
					.getWorkorder_servicechannel();

			for (ServiceChannelWorkorderDTO scwodto : serviceChannelWorkorderDTOList) {
				ServiceChannelWorkOrders scwoObj = createSCWorkOrderObject(scwodto);
				
				if(scwoObj!=null){
					scwoObj.setRequestTrackingId(requestTrackingId);
					scWorkOrderList.add(scwoObj);
				}
				
			}

		} catch (Exception e) {
			isSuccessful = false;
			LOGGER.error("Error in ServiceChannelServiceImpl status:  workOrdersByESB");
		}

		if (isSuccessful) {
			System.out.println("=====================Successful==============");
			try {
				for (ServiceChannelWorkOrders workOrder : scWorkOrderList) {
					recordStatus = serviceChannelDAO
							.persistESBWorkOrders(workOrder);

					if (recordStatus) {
						recordStatus = true;
					}
				}
			} catch (Exception e) {
				LOGGER.error("Exception in ServiceChannelService: workOrdersByESB"
								+ e);
			
			}

		}

		return recordStatus;

	}

	public static ServiceChannelWorkOrders createSCWorkOrderObject(ServiceChannelWorkorderDTO scwodto) {
		ServiceChannelWorkOrders serviceChannelWorkOrders = new ServiceChannelWorkOrders();
		try {
			
			serviceChannelWorkOrders.setWorkOrderNumber(scwodto.getNumber());
			serviceChannelWorkOrders.setCallDate(scwodto.getAudit_info().getCreated_date_time());
			serviceChannelWorkOrders.setScheduledDate(scwodto.getDispatch_date_time());
			serviceChannelWorkOrders.setStoreId(Integer.parseInt(scwodto.getLocation().getStore_id()));
			serviceChannelWorkOrders.setStore(scwodto.getLocation().getName());
			serviceChannelWorkOrders.setUpdatedBy("baleuser");
		} catch (Exception e) {
			serviceChannelWorkOrders=null;
			LOGGER.error("\n Error in ServiceChannelServiceIMPL: createSCWorkOrderObject");
		

		}
				
		return serviceChannelWorkOrders;
	}

	@Override
	public boolean fetchWorkorderNumbersFromESB() {
		
		
		Boolean recordStatus = false;
		List<ServiceChannelCustomers> serviceChannelActiveCustomers = serviceChannelCustomers();

		if (serviceChannelActiveCustomers != null
				&& serviceChannelActiveCustomers.size() > 0) {
			for (ServiceChannelCustomers scCustomers : serviceChannelActiveCustomers) {
				Long subscriberId = scCustomers.getSuscriberId();
				
				Integer offset = 0;

				while (offset < 100000) {
					
					
					Boolean exitLoop=false;
					
					try {
						String url = BaleUtils.createAPIUrlForFetchingWorkOrderFromESB(subscriberId, offset);
						HttpMethod methodType = HttpMethod.GET;
						ResponseEntity<?> responseEntity = null;

						
						responseEntity = BaleUtils.getResponseFromEsbWorkOrderAPI(url,methodType, new ServiceChannelESBDTO());
					

						if (responseEntity != null) {
							Integer statusValue = responseEntity
									.getStatusCode().value();

							
							if (statusValue == 200) {
								ServiceChannelESBDTO serChannelESBDTO = (ServiceChannelESBDTO) responseEntity
										.getBody();

								recordStatus = workOrdersByESB(serChannelESBDTO);
								recordStatus=true;
								if(!recordStatus){
									exitLoop=true;
								}
							} else if (statusValue == 204) {
								
								exitLoop=true;
							} else {
								/*Boolean flag = failedWorkOrderObject(
										subscriberId, offset, statusValue);*/
								
								exitLoop=true;
							}
						} else {
							exitLoop=true;
							
						}

					} catch (Exception e) {
						LOGGER.error("===Exception in  fetchWorkorderNumbersFromESB====="+e);
					}
					
					if(exitLoop){
						break;
					}else{
						offset += 50;
					}	
				}
			}
		}
		
		
		
		return recordStatus;
	}

	@Scheduled(cron = "0 45 23 * * 6")
	public void reportCurrentTime() {
		
		
		try {
			Boolean status = fetchWorkorderNumbersFromESB();
			
		} catch (Exception e) {
			
			LOGGER.error("===Exception in  reportCurrentTime====="+e);
		}

	}

	@Override
	public ServiceChannelESBDTO esbAPICalling() {

		return null;
	}

	public boolean failedWorkOrderObject(Long subscriberId, Integer offset,
			Integer statusValue) {
		boolean flag = false;
		/*ServiceChannelFailedWorkOrders esbWorkOrder = new ServiceChannelFailedWorkOrders();
		esbWorkOrder.setSuscriberId(subscriberId.toString());
		esbWorkOrder
				.setRequestTrackingId(BaleUtils.generateUniqueIdByCalader());
		esbWorkOrder.setLimit(25);
		esbWorkOrder.setOffSet(offset);
		esbWorkOrder.setRequestStatus(statusValue.toString());
		esbWorkOrder.setErrorMessage("Need to look");
		esbWorkOrder.setCreatedDate(new Date());
		esbWorkOrder.setUpdatedDate(new Date());
		esbWorkOrder.setUpdatedBy("baleuser");
		esbWorkOrder.setIsresolved(false);

		serviceChannelDAO.persistFailedESBWorkOrders(esbWorkOrder);
*/
		return flag;
	}

	
	//by deepak for sending back to ESB
	
	
	
	@Override
	public String getSavedWorkorderForServiceChannel(BalePickupMobileDetailsForSC balePickupMobileDetailsForSC,Integer Id) {
		SCAndRMTStoreMappingWithLatLong sCAndRMTStoreMappingWithLatLong=null;
		SavedWorkOrdersToServiceChannel workOrder = null; 
		ServiceChannelWorkOrders serviceChannelWorkOrders =null;
		String status ="failed";
		try{
		sCAndRMTStoreMappingWithLatLong = getRMTStoreIdByStoreName(balePickupMobileDetailsForSC.getStoreName());
			if(sCAndRMTStoreMappingWithLatLong!=null){
						
			workOrder = retriveSCWorkOrdersByID(Id);
			
			 if(workOrder!=null && workOrder.getWorkOrderNumber()!=null) { //when we just done check in from mobile BOL is absent. NPE
				
				  serviceChannelWorkOrders =  serviceChannelDAO.getWODetailsFromWorkOrderNumber(workOrder.getWorkOrderNumber());
				 if(serviceChannelWorkOrders!=null){
					
					 	status= sendBackWorkOrdersToESB(workOrder , serviceChannelWorkOrders.getRequestTrackingId());
					 	//status = "Passed";
				 }else{
					
					status ="failed";
				 }
			 }else{
				
				 status ="failed";
			 }
		}else{
			
			status ="failed";
		}
		
			
	}catch(Exception e){
		status ="failed";
		LOGGER.error("Error in ServiceChannelService : getSavedWorkorderForServiceChannel  " +e);
		return status;
	}
		return status;
	}
	
	@Override
	public SavedWorkOrdersToServiceChannel retriveSCWorkOrdersByID(Integer Id) {
		BalePickupTripDetailsForSC serviceChannelWorkOrders =null;
		SavedWorkOrdersToServiceChannel workOrders = null;
		SavedWorkOrderAddressToServiceChannel savedWorkOrderAddressToServiceChannel = new SavedWorkOrderAddressToServiceChannel();
		SavedWorkOrderLocationToServiceChannel savedWorkOrderLocationToServiceChannel = new SavedWorkOrderLocationToServiceChannel();
		
		try{
		serviceChannelWorkOrders = serviceChannelDAO.retriveSCWorkOrdersByID(Id); // WO from BalePickupTripDetailsForSC
		if(serviceChannelWorkOrders!=null){
			
			if(!(serviceChannelWorkOrders.getUpdatedDevice()==null || serviceChannelWorkOrders.getUpdatedDevice().equalsIgnoreCase("Web"))){
			
			workOrders = new SavedWorkOrdersToServiceChannel();
			workOrders.setWorkOrderNumber(serviceChannelWorkOrders.getBol());
			
				if(serviceChannelWorkOrders.getCheckoutDateTime()!=null){
					
					
					
					if(serviceChannelWorkOrders.getBalesRemaining()<10){
						workOrders.setStatus_primary("Completed");
						workOrders.setStatus_extended(ServiceChannelEnumConstants.PendingConfirmation); //pending confirmation if complete 
					}else{
						workOrders.setStatus_primary("INPROGRESS");
						workOrders.setStatus_extended(ServiceChannelEnumConstants.INCOMPLETE);
					}
					
					workOrders.setComments(serviceChannelWorkOrders.getResolution());
					workOrders.setCheck_out_date_time(BaleUtils.getIsoDateForServiceChannel(serviceChannelWorkOrders.getCheckoutDateTime())); 
					
					savedWorkOrderAddressToServiceChannel.setLongitude(serviceChannelWorkOrders.getLongitude());
					savedWorkOrderAddressToServiceChannel.setLatitude(serviceChannelWorkOrders.getLatitude());
					savedWorkOrderLocationToServiceChannel.setAddress(savedWorkOrderAddressToServiceChannel);
					workOrders.setLocation(savedWorkOrderLocationToServiceChannel);
			}else{
				
			
				workOrders.setStatus_primary("In Progress");
				workOrders.setStatus_extended(serviceChannelWorkOrders.getStatus()); //ONSITE
				workOrders.setCheck_in_date_time(BaleUtils.getIsoDateForServiceChannel(serviceChannelWorkOrders.getCheckinDateTime())); //String
				savedWorkOrderAddressToServiceChannel.setLongitude(serviceChannelWorkOrders.getCheckInLongitude());
				savedWorkOrderAddressToServiceChannel.setLatitude(serviceChannelWorkOrders.getCheckInLatitude());	
				savedWorkOrderLocationToServiceChannel.setAddress(savedWorkOrderAddressToServiceChannel);
				workOrders.setLocation(savedWorkOrderLocationToServiceChannel);
				}
			}else{
				
			}
			
		}else{
			System.out.println("serviceChannelWorkOrders is null not able to find WO from BalePickupTripDetailsForSC Table");
		}
		}catch(Exception e){
			LOGGER.error("Error in ServiceChannelService : retriveSCWorkOrdersByID  " +e);
			return null;
		}
		return workOrders;

	}
	@Override
	public String sendBackWorkOrdersToESB(SavedWorkOrdersToServiceChannel  serviceChannelWorkOrders, String requestTrackingId) {
		String responseFromESB = "failed";
		Integer id=-1;
		try {
			HttpMethod methodType = HttpMethod.PUT;
			String url = BaleUtils.createAPIUrlForSendingWorkOrdersToESB(serviceChannelWorkOrders.getWorkOrderNumber());
			responseFromESB = BaleUtils.updateWorkOrderToServiceChannel(url,methodType,serviceChannelWorkOrders,requestTrackingId);
		}catch(Exception e){
			LOGGER.error("-----Exception in sendBackWorkOrdersToESB---------------	"+e);			 
		}
			if(responseFromESB.equals("failed")){
				id = saveFailedServiceChannelWorkOrder(serviceChannelWorkOrders);
			
					
			}
		return responseFromESB;
	}
	
	
	@Override
	public Integer saveFailedServiceChannelWorkOrder(SavedWorkOrdersToServiceChannel savedWorkOrdersToServiceChannel) {
		// TODO Auto-generated method stub
		
		
		ServiceChannelFailedWorkOrders serviceChannelFailedWorkOrders = new ServiceChannelFailedWorkOrders();
		Integer id = -1;
		try{
		if(savedWorkOrdersToServiceChannel!=null){		
			serviceChannelFailedWorkOrders.setWorkOrderNumber(savedWorkOrdersToServiceChannel.getWorkOrderNumber());	
			serviceChannelFailedWorkOrders.setCheck_In_Date_Time(savedWorkOrdersToServiceChannel.getCheck_in_date_time());
			serviceChannelFailedWorkOrders.setCheck_Out_Date_Time(savedWorkOrdersToServiceChannel.getCheck_out_date_time());
			serviceChannelFailedWorkOrders.setComments(savedWorkOrdersToServiceChannel.getComments());
			serviceChannelFailedWorkOrders.setStatus_Primary(savedWorkOrdersToServiceChannel.getStatus_primary());
			
			
			
			if(savedWorkOrdersToServiceChannel.getStatus_extended().toString().equals("On Site")){ 
				serviceChannelFailedWorkOrders.setStatus_Extended(ServiceChannelEnumConstants.OnSite);
			}else {
				if(savedWorkOrdersToServiceChannel.getStatus_extended().toString().equals("Incomplete")){
					serviceChannelFailedWorkOrders.setStatus_Extended(ServiceChannelEnumConstants.INCOMPLETE);
				}
				if(savedWorkOrdersToServiceChannel.getStatus_extended().toString().equals("Pending Confirmation")){
					serviceChannelFailedWorkOrders.setStatus_Extended(ServiceChannelEnumConstants.PendingConfirmation);
				}
			}
			if(savedWorkOrdersToServiceChannel.getLocation().getAddress().getLatitude()!=null){
			serviceChannelFailedWorkOrders.setLatitude(savedWorkOrdersToServiceChannel.getLocation().getAddress().getLatitude());
			serviceChannelFailedWorkOrders.setLongitude(savedWorkOrdersToServiceChannel.getLocation().getAddress().getLongitude());
			}
		}
	
		
		id = serviceChannelDAO.persistFailedWorkOrdersTOESB(serviceChannelFailedWorkOrders);
		}catch(Exception e){
			LOGGER.error("---Error in saveFailedServiceChannelWorkOrder-----------	"+e);
			return -1;
		}
		
		return id;
	}
	
}
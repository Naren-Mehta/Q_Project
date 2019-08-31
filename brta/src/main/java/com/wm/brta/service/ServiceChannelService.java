package com.wm.brta.service;

import java.util.List;

import com.wm.brta.domain.SCAndRMTStoreMappingWithLatLong;
import com.wm.brta.domain.ServiceChannelCustomers;
import com.wm.brta.domain.ServiceChannelWorkOrders;
import com.wm.brta.dto.BalePickupMobileDetailsForSC;
import com.wm.brta.dto.SavedWorkOrdersToServiceChannel;
import com.wm.brta.dto.ServiceChannelESBDTO;

public interface ServiceChannelService {

	public Integer  serviceChannelWorkOrders(List<ServiceChannelWorkOrders> scWorkOrder);
	
	public boolean customerIdFromServiceChannelWorkOrders(Integer id);
	
	public SavedWorkOrdersToServiceChannel retriveSCWorkOrdersByID(Integer storeId);
	
	public List<ServiceChannelWorkOrders>  retriveSCWorkOrders(Integer storeId);
	
	public Integer getCustIdFromSiteIdForSC(Integer customerSiteId);
	
	public List<ServiceChannelCustomers> serviceChannelCustomers();
	
	public boolean workOrdersByESB(ServiceChannelESBDTO workOrdersByESB);
	
	public ServiceChannelESBDTO esbAPICalling();
	
	public boolean fetchWorkorderNumbersFromESB();
	
	public void reportCurrentTime();
	
	public Integer retriveSCWOByStoreIdAndPickupDate(ServiceChannelWorkOrders serviceChannelWorkOrders);
	
	public String sendBackWorkOrdersToESB(SavedWorkOrdersToServiceChannel serviceChannelWorkOrders, String requestTrackingId );
	
	public SCAndRMTStoreMappingWithLatLong  getRMTStoreIdByStoreName(String storeName);
	
	public String getSavedWorkorderForServiceChannel(BalePickupMobileDetailsForSC balePickupMobileDetailsForSC ,Integer Id);
	
	public Integer deleteCheckInCheckOut(BalePickupMobileDetailsForSC balePickupMobileDetailsForSC);
	
	public Integer saveFailedServiceChannelWorkOrder(SavedWorkOrdersToServiceChannel serviceChannelWorkOrders);
}

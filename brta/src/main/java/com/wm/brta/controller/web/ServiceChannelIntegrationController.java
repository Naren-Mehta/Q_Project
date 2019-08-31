package com.wm.brta.controller.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wm.brta.domain.SCAndRMTStoreMappingWithLatLong;
import com.wm.brta.domain.ServiceChannelCustomers;
import com.wm.brta.domain.ServiceChannelWorkOrders;
import com.wm.brta.service.ServiceChannelService;

@RestController
public class ServiceChannelIntegrationController {

	@Autowired
	ServiceChannelService serviceChannelService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServiceChannelIntegrationController.class);
		
	@RequestMapping(value = "/ui/servicechannel/esb/pushworkorders", method = RequestMethod.POST)
	public Integer String(@RequestBody List<ServiceChannelWorkOrders> scWorkOrder) {

		Integer id = serviceChannelService.serviceChannelWorkOrders(scWorkOrder);
		return id;
	}

	@RequestMapping(value = "/ui/servicechannel/esb/fetchworkordernumbers", method = RequestMethod.GET)
	public Boolean fetchWorkorderNumbersFromESB()  {
		boolean status= serviceChannelService.fetchWorkorderNumbersFromESB();
		return true;
	}
	
	
	@RequestMapping(value = "/ui/common/get/serviceChannelCustomers", method = RequestMethod.GET)
	@ResponseBody  List<ServiceChannelCustomers> getServiceChannelCustomers(){
		
		List<ServiceChannelCustomers> serviceChannelCustomers = null;
		serviceChannelCustomers= serviceChannelService.serviceChannelCustomers();
		return serviceChannelCustomers;
	}
	
		@RequestMapping(value ="/ui/common/get/fetchWorkOrdersByStoreId", method=RequestMethod.POST)
		@ResponseBody
		public List<ServiceChannelWorkOrders> getWorkOrdersFromStoreId(@RequestBody Integer storeId){
			
			List<ServiceChannelWorkOrders> serviceChannelCustomers = null;
			try{
			
			serviceChannelCustomers= serviceChannelService.retriveSCWorkOrders(storeId);
			}catch(Exception e){
				LOGGER.error("getWorkOrdersFromStoreId"+e);
			}
			return serviceChannelCustomers;
	}	
		
		
		
		@RequestMapping(value ="/ui/common/get/getWOByStoreIdAndPickupDate", method=RequestMethod.POST)
		@ResponseBody
		public Integer getWOFromStoreIdAndPickupDated(@RequestBody ServiceChannelWorkOrders serviceChannelWorkOrders){
			SCAndRMTStoreMappingWithLatLong	sCAndRMTStoreMappingWithLatLong =null;
			Integer worOrderNumber = 0;
			
			try{
				
				sCAndRMTStoreMappingWithLatLong = serviceChannelService.getRMTStoreIdByStoreName(serviceChannelWorkOrders.getStore());
				if(sCAndRMTStoreMappingWithLatLong!=null){
					serviceChannelWorkOrders.setStoreId(sCAndRMTStoreMappingWithLatLong.getScStoreId());
				}
			
			worOrderNumber= serviceChannelService.retriveSCWOByStoreIdAndPickupDate(serviceChannelWorkOrders);
			}catch(Exception e){
				LOGGER.error("getWorkOrdersByStoreId  " +e);
			}
			return worOrderNumber;
	}	
		
		
		@RequestMapping(value ="/ui/common/get/findServiceChannelCustomer", method=RequestMethod.POST)
		@ResponseBody
		public Boolean findServiceChannelCustomer(@RequestBody Integer customerId){
			
			boolean customerFound=false;
			customerFound = serviceChannelService.customerIdFromServiceChannelWorkOrders(customerId);
			
			return customerFound;
		}
		
		
		
}

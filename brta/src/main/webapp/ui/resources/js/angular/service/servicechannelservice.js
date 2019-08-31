brtaApp.service('serviceChannelService', function($http,applicationContextURL){
	return {
		
		serviceChannelCustomers: function(){
		return $http.get(applicationContextURL+"/ui/common/get/serviceChannelCustomers").	
	then(function(response){
	
	return response.data;
		
	},function(errResponse){
		
		
		
	});
		
		},	
		getWorkOrdersFromStoreId: function(storeId){
			return $http.post(applicationContextURL+"/ui/common/get/fetchWorkOrdersByStoreId",storeId).	
		then(function(response){
		
		
		return response.data;
			
		},function(errResponse){
			
			
			
		});
		}	,
		
		findServiceChannelCustomer: function(customerId){
			return $http.post(applicationContextURL+"/ui/common/get/findServiceChannelCustomer",customerId).	
			then(function(response){
		return response.data;
			
		},function(errResponse){
			
			
			
		});
		}	,
	
	getAllSellCustomersForBuyCustomerAndSupplier: function(buyCustomerId, supplierId){
	
	var idList=[];
	idList.push(buyCustomerId);
	idList.push(supplierId);
	return $http.post(applicationContextURL+"/ui/common/get/sellcustomer/bybuycustomerandsupplier",idList).
	then(function(response){
		return response.data;
	
   },function(errResponse){
	
	
	});
},
	getWOByStoreIdAndPickupDate: function(serviceChannelWO){
		
	
		return $http.post(applicationContextURL+"/ui/common/get/getWOByStoreIdAndPickupDate",serviceChannelWO).	
	then(function(response){
	

	return response.data;
		
	},function(errResponse){
		
		
		
	});
	}	
}

});

	
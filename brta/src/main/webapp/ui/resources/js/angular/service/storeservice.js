brtaApp.service('storeConfigService', function($http,applicationContextURL){
	
	return {
		addConfigurationData: function(configData){
		return $http.post(applicationContextURL+"/ui/common/add/suppliermaterialconfiguration",configData)	
	then(function(response){
	
	return response.data;
		
	},function(errResponse){
		
		
		
	});
		
		}
	}
	
	
});



brtaApp.service('pickupAssignmentService', function($http,applicationContextURL){
	
	return {
		get: function(configData){
		return $http.post(applicationContextURL+"/ui/common/add/suppliermaterialconfiguration",configData)	
	then(function(response){
	
	return response.data;
		
	},function(errResponse){
		
		
		
	});
		
		},
		
		getAssignmentsByCustomerSite: function(customerSite){
			return $http.post(applicationContextURL+"/ui/assignMgmt/get/assignments/bycustomerSite",customerSite)	
		then(function(response){
		
		return response.data;
			
		},function(errResponse){
			
			
			
		});
			
			},
		
		addAssignmentList: function(listOfAssignments){
			return $http.post(applicationContextURL+"/ui/assignMgmt/add/pickupassignments",listOfAssignments)	
			then(function(response){
		
			return response.data;
				
			},function(errResponse){
				return response.data;
				
				
			});
			
		},
		getAllAssignments:function(assignmentFilterDTO){
			return $http.post(applicationContextURL+"/ui/assignMgmt/getAll/pickupassignments",assignmentFilterDTO)	
			then(function(response){
			
			return response.data;
				
			},function(errResponse){
				
				
				
			});
		},
		/*Store Supplier List Phase2 Deepak Kasgar */
		
		getStoreSupplierList:function(assignDriverDTO){
			return $http.post(applicationContextURL+"/ui/assignMgmt/getAll/storeSupplierList",assignDriverDTO)	
			then(function(response){
			return response.data;
			},function(errResponse){
				
				
				
			});
		},
		/*store supplier end*/
		
		/*start of store supplier popup*/
		assignDriverPopup:function(generateAssignmentDriverPopup){
			return $http.post(applicationContextURL+"/ui/assignMgmt/update/drivers",generateAssignmentDriverPopup)	
			then(function(response){
		
			return response.data;
				
			},function(errResponse){
				return response.data;
				
				
			}); 
		},
		
		/*by deepak Driver one*/
		getAssignedDriver:function(assignDriverDTO){

			return $http.post(applicationContextURL+"/ui/assignMgmt/display/driversDashboard",assignDriverDTO)	
			then(function(response){
			
			return response.data;
				
			},function(errResponse){
				return response.data;
				
				
			}); 
		},
		
		getDestinationFromStoreIdList:function(storeIdList){

			return $http.post(applicationContextURL+"/ui/common/get/destinationsFromStoreIdList",storeIdList)	
			then(function(response){
		
			return response.data;
				
			},function(errResponse){
				return response.data;
				
				
			}); 
		}
	
		
		
	}
});
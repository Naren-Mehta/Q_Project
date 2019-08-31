brtaApp.service('reportService', function($http,applicationContextURL){
	
	return {
		
		getAllIncidentTypes: function(){
		return $http.post(applicationContextURL+"/ui/common/get/incidenttype/all")	
	then(function(response){
	
	return response.data;
	
	
		
	},function(errResponse){
		
		
		
	});
		
		},
		getAllPickups: function(configData){
			return $http.post(applicationContextURL+"/ui/common/getAll/pickUps",configData)	
		then(function(response){
		
		return response.data;
			
		},function(errResponse){
			
			
		});
		
	},
	
	getAllPickupsDARAndRMT: function(configData){
		return $http.post(applicationContextURL+"/ui/common/getAll/pickUpsForDARAndRMT",configData)	
	then(function(response){
	
	return response.data;
		
	},function(errResponse){
		
		
		
	});
	
},
		getPendingPickups: function(configData){
		return $http.post(applicationContextURL+"/ui/reportMgmt/getAll/pendingReports",configData)	
	then(function(response){
	return response.data;
		
	},function(errResponse){
		
		
		
	});
		
		},
		getAllBalePickupDates: function(pendingReportDTO){
			return $http.post(applicationContextURL+"/ui/reportMgmt/getAll/getAllBalePickupDates",pendingReportDTO)	
		then(function(response){
		return response.data;
			
		},function(errResponse){
			
			
			
		});
			
			},
			
			getSavePendingReport: function(savePendingReportData){
				return $http.post(applicationContextURL+"/ui/reportMgmt/savePickupFromPendingStore",savePendingReportData)	
			then(function(response){
			return response.data;
				
			},function(errResponse){
				
				
				
			});
				
				}
	
	}
	
});


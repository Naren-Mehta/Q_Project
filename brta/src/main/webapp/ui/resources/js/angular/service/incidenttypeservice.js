brtaApp.service('incidentTypeService',function($http,applicationContextURL){
	
	return {
		addIncidentType: function(incidentType){
			return $http.post(applicationContextURL+'/ui/incidentMgmt/add/incidenttype',
					incidentType).then(function(response){
					
				        return response.data;
				        
					},function(errResponse){
						
						return $q.reject(errResponse);
					
					});
		
			
		},
		
		editIncidentType: function(incidentType){
			return $http.post(applicationContextURL+'/ui/incidentMgmt/edit/incidenttype',
					incidentType).then(function(response){
					
				        return response.data;
				        
					},function(errResponse){
						
						return $q.reject(errResponse);
					
					});
		
			
		},
		
		getAllIncidentTypes: function(){
			return $http.get(applicationContextURL+'/ui/common/get/incidenttype/all').then(function(response){
					
				        return response.data;
				        
					},function(errResponse){
						
						return $q.reject(errResponse);
					
					});
			
		},
		
		
		
		getAllincidentTypeFromStatus: function(status){
			return $http.post(applicationContextURL+'/ui/common/get/incidentTypeFromStatus',status).then(function(response){
					
				        return response.data;
				        
					},function(errResponse){
						
						return $q.reject(errResponse);
					
					});
			
		},
		
		checkIncidentUnique: function(incidentType){
			return $http.post(applicationContextURL+'/ui/incidentMgmt/check/incidenttype',incidentType).then(function(response){
				        return response.data;
				        
					},function(errResponse){
						
						return $q.reject(errResponse);
					
					});
			
		},
		
		checkIncidentbyNameStatus: function(incident){
			
			return $http.post(applicationContextURL+'/ui/incidentMgmt/check/checkIncidentbyNameStatus',incident).then(function(response){
				        return response.data;
				        
					},function(errResponse){
						
						return $q.reject(errResponse);
					
					});
			
		},
		
		
		}
	
})
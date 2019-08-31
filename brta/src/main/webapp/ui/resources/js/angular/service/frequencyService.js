brtaApp.service('frequencyService', function($http,applicationContextURL){
	
	return {
		getAllFrequency: function(){
		return $http.get(applicationContextURL+"/ui/common/get/frequency")	
	then(function(response){
	
	return response.data;
		
	},function(errResponse){
		
		
		
	});
		
		}
	}
	
	
});

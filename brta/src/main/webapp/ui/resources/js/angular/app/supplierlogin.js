brtaApp
		.controller(
				"supplierloginController",
				function(applicationContextURL, $scope, $rootScope, $http,$location,$window,
						supplierUserService) {

						
						$scope.errorMessage="";
						$scope.supplierLogin=function(emailId, mobilePhone){
							
							var user={};
							user.emailId=emailId;
							user.mobilePhone=mobilePhone;
							
							

							
							supplierUserService.getSupplierLogin(user).then(function(response){
								
								 if(response!= undefined && response!= undefined && response.message=='Supplier Authenticated Succssfully'){
									 $window.location.href='../supplier/';
								 }else{
									 $scope.errorMessage=response.message;
								 }
							});
							
							
						}
			
		
});
				


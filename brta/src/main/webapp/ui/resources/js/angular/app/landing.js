brtaApp.controller("sideMenuController",function(applicationContextURL,$rootScope,$scope,$http,userService){
	
	
	
			var user=window.user;
		
	
	$rootScope.user=JSON.parse(window.user);	
	
	$scope.sideMenu =function(){
		
	 	 if($rootScope.user ==undefined){
	 		 $scope.user={};
	 	 }
	 	 
	 	$rootScope.user.role = window.role;	

		if($scope.user.role=='ROBRCSR')
			{
			$scope.incidentManagement = true;
			$scope.showstoreManagement = true;
			$scope.userManagement = true;
			$scope.storeAssignment = true;
			$scope.balePickup = true;
			$scope.reports = true;
			$scope.needHelp = true;
		}
		if($scope.user.role=='ROBRMANAGER'){
			$scope.incidentManagement = true;
			$scope.showstoreManagement = true;
			$scope.userManagement = true;
			$scope.storeAssignment = true;
			$scope.balePickup = true;
			$scope.reports = true;
			$scope.needHelp = true;
		}
		
		if($scope.user.role=='ROBRSUPPLIER')
		{
		$scope.showstoreManagement = true;
		$scope.userManagement = true;
		$scope.storeAssignment = true;
		$scope.balePickup = true;
		$scope.reports = true;
		$scope.needHelp = true;
		}
		
		
		if($scope.user.role=='Supplier')
		{
		$scope.incidentManagement = true;
			$scope.showstoreManagement = true;
			$scope.userManagement = true;
			$scope.storeAssignment = true;
			$scope.balePickup = true;
			$scope.reports = true;
			$scope.needHelp = true;
		}
		
	}
	
	if($rootScope.user !=undefined){
		$scope.user=$rootScope.user;
		$scope.sideMenu();
	}else{
		userService.getLoggedInUserDetails().then(function(response){
			$scope.user = response;
			$rootScope.user=$scope.user;
			$scope.sideMenu();
		});
	}
	
	
	
	
	$scope.backClickFun=function(){
		$rootScope.backClick=!$rootScope.backClick;
	}
});


brtaApp.controller("welcomeUserController",function(applicationContextURL,$scope,$http,userService){

        	$scope.greetingMessage = "";
			
        	var currDate = new Date();
			
        	if(currDate.getHours()>=0 && currDate.getHours()<12){
        		$scope.greetingMessage = "Good Morning";
        	}
        	if(currDate.getHours()>=12 && currDate.getHours()<16){
        		$scope.greetingMessage = "Good Afternoon";
        	}
        	
        	if(currDate.getHours()>=16 && currDate.getHours()<24){
        		$scope.greetingMessage = "Good Evening";
        	}
});

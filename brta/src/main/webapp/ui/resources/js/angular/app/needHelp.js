brtaApp.controller("needHelpController",function(applicationContextURL,$scope,customerService,$rootScope,$filter,$timeout,$interval,$location, $window,$http,userService,supplierService,DTOptionsBuilder, DTColumnBuilder,Idle, Keepalive, $uibModal){

	$scope.redirect = function(){
	     if($rootScope.user.role =='Supplier'){
	      window.location = "#/storesupplierForSupplier";
		  $(".store_menu a").attr("class", "active");
		  $(".pickup_menu a").attr("class", "");
	      }else{
	        window.location = "#/storemanagement";
			$(".store_menu a").attr("class", "active");
		  $(".pickup_menu a").attr("class", "");
	      }
	  }
		
	var currentUrl=$location.url();
					
					
					
					if(currentUrl.indexOf("needHelp")!=-1 || currentUrl.indexOf("needHelpForSupplier")!=-1){
						//$scope.balePickupTab=true;
						//$scope.pendingStoreReportTab = false;
						$(".needHelp_menu a").attr("class", "active");
						$(".pickup_menu a").attr("class", "");
						$(".user_menu a").attr("class", "");
						$(".incident_menu a").attr("class", "");
						$(".report_menu a").attr("class", "");
						$(".store_menu a").attr("class", "");
						
					}
					
	
});
//QA code

brtaApp.controller("balepickupController",function(applicationContextURL, $scope, $rootScope, $http,$filter ,$timeout,$interval,$compile,
						userService, balepickupService, customerService,supplierService,serviceChannelService, storeConfigService,
						pickupAssignmentService, incidentTypeService,reportService, Lightbox,DTOptionsBuilder, DTColumnBuilder,Idle, Keepalive, $uibModal,$routeParams,$route,$location) {
	
	
	if($scope.buyCustomers==undefined){
		customerService.getAllBuyCustomers().then(function(response) {	
								$scope.buyCustomers = response;
								
								$rootScope.buyCustomers=response;
								
							});
	}
	
	
	
	console.log("$scope.buyCustomers" +JSON.stringify($rootScope.buyCustomers));
	$scope.$route = $route;
	$scope.authorizedBalePickup = false;
	$scope.authorizedPR=false;
	var storeNameForSC=null;
	var role=window.role;
	var supplierId=window.supplierId;
	$scope.isSupplierRole=false;
	var serviceChannelWO={};
	$scope.isEdit=false;
	$scope.scWorkOrder=false;
	var bolExist = -1;
	$scope.servicechannelCustomers ={};
	
	$rootScope.supplierId=supplierId;
	
	 if($rootScope.user ==undefined){
		 $rootScope.user={};
 	
 	 }
 	$rootScope.user.role = window.role;	
	
							
	var currentExcelUploadsupplier={};
	if($rootScope.user.role =='Supplier'){
		$scope.isSupplierRole=true;
		$scope.disableSubmitBtnPR=false;
		$scope.storeListView=false;
		$scope.assignActivityTab=true;
			
	supplierService.getAllSuppliers().then(function(response){
	   $scope.suppliers = response;
		  for (var i = 0; i < $scope.suppliers.length; i++) {
			 if($scope.suppliers[i].supplierId==$rootScope.supplierId){
				currentExcelUploadsupplier= $scope.suppliers[i].description;
				break;
			 	}
		  }
	});  
		
	}else{
		$scope.disableSubmitBtnPR=true;
	}
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
	  $(".pickup_menu a").attr("class", "");
					var columns = [];
					$scope.addEditDriverPopup=false;
					
					$scope.baleData;
					if(user.role=='ROBRCSR' || user.role=='ROBRMANAGER'){
						$scope.baleData=false;
					}else if(user.role=='Supplier' && user.firstName !=undefined){
						$scope.baleData=false;
					}
					
					//$scope.balePickupTab=true;
					var currentUrl=$location.url();
					
					
					
					if(currentUrl.indexOf("balepickup")!=-1 || currentUrl.indexOf("balepickupForSupplier")!=-1){
						$scope.balePickupTab=true;
						$scope.pendingStoreReportTab = false;
						$(".pickup_menu a").attr("class", "active");
						$(".user_menu a").attr("class", "");
						$(".incident_menu a").attr("class", "");
						$(".report_menu a").attr("class", "");
						$(".store_menu a").attr("class", "");
						
					}
					if(currentUrl.indexOf("pendingStoreReportTab")!= -1 || currentUrl.indexOf("balepickupForPendingSupplier")!=-1){
						$scope.pendingStoreReportTab = true;						
						$scope.balePickupTab=false;	
						$(".pickup_menu a").attr("class", "active");
						$(".user_menu a").attr("class", "");
						$(".incident_menu a").attr("class", "");
						$(".report_menu a").attr("class", "");
						$(".store_menu a").attr("class", "");						
					}
					
					
					$scope.editingData = [];
					$scope.commodities = [];
					$scope.dashboardbalepickuplist={};
					$scope.loaderActiveBalePickup = false;
					$scope.loadertest = false;
				//	$scope.disableSubmitBtn = true;
					$scope.disableAddPickupBtn = true;

						
	
	
	
	
	//reset filter - naren
	$scope.resetBalePickupFilter= function(){
		
		$scope.suppliersForCustomerSelected  = $scope.suppliersForCustomerSelectedOriginal;
		
		if($rootScope.user.role =='Supplier'){
		
		$scope.buyCustomerSelectedBP= undefined;
		$scope.startDate= undefined;
		$scope.endDate= undefined;
		$scope.jsonForBalePickupExport=[];
		$scope.exportDatax = [];
		$scope.balePickupList=[];
		$scope.authorizedBalePickup = false;
		$scope.uploadExcelMessage=[];
		$scope.scChannel={};
		$scope.preWordoder = [];
		$scope.workOrders=[];
		var serviceChannelWO={};
		storeNameForSC=null;
		}else{
		$scope.buyCustomers = $rootScope.buyCustomers;
		$scope.buyCustomerSelectedBP = undefined;
		$scope.buyCustomerSelectedBP = undefined;
		$scope.supplierSelectedBP = undefined;
		$scope.startDate = undefined;
		$scope.endDate = undefined;
		$scope.jsonForBalePickupExport=[];
		$scope.exportDatax = [];
		$scope.balePickupList=[];
		$scope.authorizedBalePickup = false;
		$scope.uploadExcelMessage=[];
		$scope.scChannel={};
		$scope.preWordoder=[];
		$scope.workOrders=[];
		storeNameForSC=null;
		}	
		
	}
	
	$scope.updatebalePickupDriver = function(supplier) {
			userService.getOnlyDriversForSupplierSite(supplier.supplierId).then(function(response) {
									$scope.drivers = response;
								 });
					}	

					
	
	$scope.getSellCustomer = function(buyCustomer){	
	$rootScope.buyCustomerForSellCustomer= buyCustomer;
	var unknownSellCustomer={};

	 if($rootScope.user.role =='Supplier'){
		
		 customerService.getAllSellCustomersForBuyCustomerAndSupplier(buyCustomer.customerId, $rootScope.selectedSupplier.supplierId).then(function(response) {
			 $scope.sellCustomersForCustomerSelected = response;
			 unknownSellCustomer.customerName="UNKNOWN";
		$scope.sellCustomersForCustomerSelected.push(unknownSellCustomer);
			 	});
	 }else{
	customerService.getAllSellCustomersForBuyCustomer(buyCustomer.customerId).then(function(response) {
		$scope.sellCustomersForCustomerSelected = response;
		unknownSellCustomer.customerName="UNKNOWN";
		$scope.sellCustomersForCustomerSelected.push(unknownSellCustomer);
			});
	 }
		
		
	}
	
	$scope.getCustomerSite = function(buyCustomer){
		
		if($scope.isSupplierRole){
	

			var customerSiteDetails={};
			customerSiteDetails.supplierId=$rootScope.supplierId;
			customerSiteDetails.customerId=buyCustomer.customerId;
			
			customerService.getCustomerSiteFromBuyCustomerAndSupplier(customerSiteDetails).then(function(response){
			$scope.getCustomerSiteFromBuyCustomerSite = response;
		
		});
		}else{
			customerService.getCustomerSiteFromBuyCustomerSite(buyCustomer).then(function(response){
				$scope.getCustomerSiteFromBuyCustomerSite = response;
				
				});
				
		}
	}
	$scope.getSuppliersFromCustomerSite = function(balepickuplist){
		 storeNameForSC= balepickuplist.siteName;
		
	 supplierService.allSupplierFromCustomerSite(balepickuplist.customerSiteId).then(function(response) {
		 $scope.allSupplierFromCustomerSite = response;
		
		 if($rootScope.user.role =='Supplier'){
			 for(var q=0;q<$scope.allSupplierFromCustomerSite.length;q++){
				 if($scope.allSupplierFromCustomerSite[q].supplierId == $rootScope.supplierId){
				 currentExcelUploadsupplier = $scope.editPickupData.supplierName = $scope.allSupplierFromCustomerSite[q];
				 var defaultSupplierId= $scope.allSupplierFromCustomerSite[q].supplierId;
				userService.getOnlyDriversForSupplierSite($scope.editPickupData.supplierName.supplierId).then(function(response) {
								$scope.drivers = response;
	
		 });
			}
			break;
		 }
		 
		 
	}
	});
		}
		
		$scope.getServiceChannelWorkOrder =function(){
			 var isBuyCustomerExist="";
			 
			 var bolExist=undefined;
			 
			 if($scope.balePickupObjectForSC!=undefined && $scope.balePickupObjectForSC.bOL!=undefined){
				 bolExist=$scope.balePickupObjectForSC.bOL;
			 }
			 
			 
			 
			console.log("bolExist:  "+bolExist);
				//console.log("BuyCustomerId:  "+JSON.stringify($scope.editPickupData.buycustomerName.customerId));
				//console.log("StoreName:  "+storeNameForSC);
				//console.log("Date:  "+$scope.editPickupData.startDateBalePickup);
				
				
			if($scope.editPickupData.buycustomerName!=undefined && storeNameForSC!=null && $scope.editPickupData.startDateBalePickup!=null){
								
				
				var originDate =new Date($scope.editPickupData.startDateBalePickup);
				
var newformat_originDate = new Date();

var day = originDate.getUTCDate()+1;
console.log();
var hour = newformat_originDate.getUTCHours();
var minute = newformat_originDate.getUTCMinutes();
var second = newformat_originDate.getUTCSeconds();
originDate.setUTCDate(day);
originDate.setUTCHours(hour);
originDate.setUTCMinutes(minute)
originDate.setUTCSeconds(second)

 $scope.endDate = originDate;
var new_originDate = new Date($scope.endDate);

				
			serviceChannelService.findServiceChannelCustomer($scope.editPickupData.buycustomerName.customerId).then(function(response){
				isBuyCustomerExist = response;
				
				if(isBuyCustomerExist==true){
						
						serviceChannelWO.callDate=new_originDate;		
						serviceChannelWO.store=storeNameForSC;
						
						serviceChannelService.getWOByStoreIdAndPickupDate(serviceChannelWO).then(function(response){
								$scope.workOrders = response;
								if($scope.workOrders!=0){
								$scope.editPickupData.bOL = $scope.workOrders;
								$scope.scWorkOrder=true;
								
								}else{
									$scope.scWorkOrder=false;
									$scope.editPickupData.bOL='';
								}
								});
			
			}else{
				$scope.scWorkOrder=false;
				$scope.editPickupData.bOL='';
				if(bolExist!=undefined){
					$scope.editPickupData.bOL=bolExist;
				}
				console.log("Not SC Customer WO");
				
			}
			});
		}
		}
		
	//by deepak for	mapping b/w BuyCustSupp against SellCust	
		$scope.getSellCustFromBuyCustAndSupp = function(balepickuplist){
			 if($rootScope.user.role =='Supplier'){
			
			customerService.getAllSellCustomersForBuyCustomerAndSupplier($rootScope.buyCustomerForSellCustomer.customerId, $rootScope.selectedSupplier.supplierId).then(function(response) {
				$scope.sellCustomersForCustomerSelected = response;
			
				var unknownSellCustomer={};
				unknownSellCustomer.customerName="UNKNOWN";
				$scope.sellCustomersForCustomerSelected.push(unknownSellCustomer);
						
				 });
			
			 }else{
				 
				 customerService.getAllSellCustomersForBuyCustomerAndSupplier($rootScope.buyCustomerForSellCustomer.customerId,balepickuplist.supplierId).then(function(response) {
				$scope.sellCustomersForCustomerSelected = response;
			
				var unknownSellCustomer={};
				unknownSellCustomer.customerName="UNKNOWN";
				$scope.sellCustomersForCustomerSelected.push(unknownSellCustomer);
						
				 });
				 
			 }
			
			
		}
	
	//End by deepak for	mapping b/w BuyCustSupp against SellCust
		
	$scope.getMatrialsForCustomerSiteId = function(balepickuplist){
		
	customerService.getMaterialDetailsByMaterialID(balepickuplist.customerSiteId).then(function(response) {
	$scope.getMaterialDetailsByMaterialID = response;
	});
		
	}
	
	$scope.getSaleCustomerLocation = function(balepickuplist){
		$scope.destinationSitesForDestinationSelected=[];
		$scope.editPickupData.sellCustomerSite=undefined;
		
		if(balepickuplist.customerName=='UNKNOWN'){
			var unknownCustomerSite={};
			unknownCustomerSite.siteName="UNKNOWN";
			$scope.destinationSitesForDestinationSelected.push(unknownCustomerSite);
			$scope.editPickupData.sellCustomerSite=unknownCustomerSite;
		}else{
			var sellcustomerlist=[];
		sellcustomerlist.push(balepickuplist);
	
		customerService.getCustomerSitesForBuyCustomer(sellcustomerlist).then(function(response) {
											$scope.destinationSitesForDestinationSelected = response;		
	//										
		});
		}
		

	}	
			
	var date=new Date();			
	var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
	var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
	$scope.startDate=firstDay;
	
	$scope.StartDateFun = function() { 
						$scope.StartDate.opened = true;
						$scope.dateOptionsStart = {
							datepickerMode : 'day',
							minMode : 'day',
							startingDay : 1,
							showWeeks : false,
							formatMonth : 'MMM',
							formatYear : 'yyyy',
							monthColumns : 4,
						}
						$scope.$watch("startDate", function(newValue, oldValue) {		
							$("#startDateval").focus();
							});
					};
					$scope.StartDate = {
						opened : false
					};
	
	 $scope.EndDateFun = function() {
					$scope.EndDate.opened = true;
					$scope.dateOptionEnd = {
							datepickerMode:'day',
							minMode:'day',
							startingDay: 1,
							showWeeks:false,
							formatMonth:'MMM',
							formatYear: 'yyyy',
							monthColumns:4,
					 }
					 $scope.$watch("endDate", function(newValue, oldValue) {		
							$("#endDateval").focus();
							});
				  };

				  $scope.EndDate = {
					opened: false
				};
					
	$scope.StartDateBalePPickopupFun = function() {
			
						$scope.StartDateBalePickup.opened = true;
						$scope.dateOptionsStart = {
							datepickerMode : 'day',
							minMode : 'day',
							startingDay : 1,
							showWeeks : false,
							formatMonth : 'MMM',
							formatYear : 'yyyy',
							monthColumns : 4,
						}
						$scope.$watch("editPickupData.startDateBalePickup", function(newValue, oldValue) {		
							$("#startDate_popup").focus();
							});
							
							
					};
					
				/*	$scope.assigningHourMinuteSecond_= function(){
						 var originDate =new Date($scope.editPickupData.startDateBalePickup );
						 
						 var newformat_originDate = new Date();
						 var hour = newformat_originDate.getUTCHours();
						 var minute = newformat_originDate.getUTCMinutes();
						 var second = newformat_originDate.getUTCSeconds();
						 
						 originDate.setUTCHours(hour);
						 originDate.setUTCMinutes(minute)
						 originDate.setUTCSeconds(second)
						 
						 $scope.editPickupData.startDateBalePickup = originDate;
						 var new_originDate = new Date($scope.editPickupData.startDateBalePickup);
						 console.log("final " + new_originDate);					

						} */
					$scope.StartDateBalePickup = {
						opened : false
					};
	
	 $scope.EndDatePopupFun = function() {
					$scope.EndDatePopup.opened = true;
					$scope.dateOptionEnd = {
							datepickerMode:'day',
							minMode:'day',
							startingDay: 1,
							showWeeks:false,
							formatMonth:'MMM',
							formatYear: 'yyyy',
							monthColumns:4,
					 }
					 $scope.$watch("editPickupData.deliveryDate", function(newValue, oldValue) {		
							$("#endDate_popup").focus();
							})
				  };

				  $scope.EndDatePopup = {
					opened: false
				};
									
	
	
	 $scope.getUniqueSupplier=function(collection, keyname) {
              var output = [], 
                  keys = [];

              angular.forEach(collection, function(item) {
                  var key = item[keyname];
                  if(keys.indexOf(key) === -1) {
                      keys.push(key);
                      output.push(item);
                  }
              });
        return output;
   };
   
			
			
		supplierService.getAllSuppliers().then(function(response) {
								$rootScope.allSuppliers=$scope.suppliersForCustomerSelected=$scope.suppliersForCustomerSelectedOriginal=response;
								if($rootScope.user.role=="Supplier"){		
				for(k=0;k<$scope.suppliersForCustomerSelected.length;k++){		
					if($scope.suppliersForCustomerSelected[k].supplierId == $rootScope.supplierId){	
						$scope.editPickupData={};					
						var defaultSupplierId = $scope.suppliersForCustomerSelected[k].supplierId;
						$scope.supplierSelectedPR=$scope.supplierSelectedBP = JSON.stringify($scope.suppliersForCustomerSelected[k]);		
				
					}		
							
				}
				
				$scope.getCustomersFromSupplier(defaultSupplierId);				
				}
		});
			
			
			
			$scope.getBuyCustomersFromSupplier=function(supplier){
															
						if(supplier !=undefined && supplier!="" && supplier!=null){
							customerService.getBuyCustomersFromSupplierDAR(supplier.supplierId).then(function(response) {
								$scope.buyCustomers=response;	
							});	
						}else{
							$scope.buyCustomers=$rootScope.buyCustomers;

						}
					}
			
			

	
	
	
					//scroll bar starts here
					 $scope.dtOptionsemptyBalePickup = DTOptionsBuilder.newOptions()
										  .withOption('order', [])
											.withOption('scrollX', true)
											 .withOption('bScrollCollapse', true)
											 .withOption('bAutoWidth', true)
											//.withOption('tabIndex', '-1')				 
											.withOption('bFilter', false)
											.withOption('info', false)
											.withOption('lengthChange', false)
											.withOption('bPaginate', false)
											.withOption('ordering', false);
											//.withDOM('Blfrtip');
											
					// $scope.hideEmptyTable = function(){	
						// $("#balePickupSummaryDetailsEmpty_wrapper").css("display", "none");
						
					// }
					setTimeout(function(){
						$("#balePickupSummaryDetailsEmpty_wrapper tbody").css("display", "none");
					}, 0)
		
		//by deepak for mapping between Cust & Supplier
		
		$scope.getFilteredValuesBP=function(buyCustomerSelectedBP,supplierSelectedBP){
	  
	    if(buyCustomerSelectedBP !=undefined && buyCustomerSelectedBP!="" && buyCustomerSelectedBP!=null){
		   buyCustomerSelectedBP=JSON.parse(buyCustomerSelectedBP);
	   }else{
		   buyCustomerSelectedBP =undefined;
	   }
	   
	   if(supplierSelectedBP !=undefined && supplierSelectedBP!="" && supplierSelectedBP!=null){
		   supplierSelectedBP=JSON.parse(supplierSelectedBP);
	   }else{
		   supplierSelectedBP =undefined;
	   }
	   
		
		
	   if((buyCustomerSelectedBP==undefined || buyCustomerSelectedBP=="" || buyCustomerSelectedBP==null) && (supplierSelectedBP ==undefined || supplierSelectedBP=="" || supplierSelectedBP==null)){
		   console.log("0 0");
		 
				supplierService.getAllSuppliers().then(function(response) {
				$scope.suppliersForCustomerSelected=response;
				
			});		
			
				customerService.getAllBuyCustomers().then(function(response) {
				$scope.buyCustomers = response;					
			});	
				$scope.disableSubmitBtnPR=true;
	   }

		if((buyCustomerSelectedBP!=undefined && buyCustomerSelectedBP!="" && buyCustomerSelectedBP!=null) && (supplierSelectedBP ==undefined || supplierSelectedBP=="" || supplierSelectedBP==null)){
			 console.log("1 0");
			customerService.getSuppliersFromBuyCustomerForDAR(buyCustomerSelectedBP.customerId).then(function(response) {
					$scope.suppliersForCustomerSelected = response;	
					});	
			customerService.getAllBuyCustomers().then(function(response) {
				$scope.buyCustomers = response;					
			});		 
		}
		
		if((buyCustomerSelectedBP==undefined || buyCustomerSelectedBP=="" || buyCustomerSelectedBP==null) && (supplierSelectedBP!=undefined && supplierSelectedBP!="" && supplierSelectedBP!=null)){
			 console.log("0 1");
			 
			 customerService.getBuyCustomersFromSupplierDAR(supplierSelectedBP.supplierId).then(function(response) {
						$scope.buyCustomers=response;	
					});
					supplierService.getAllSuppliers().then(function(response) {
				$scope.suppliersForCustomerSelected=response;
				
			});		
		}
		
		if(buyCustomerSelectedBP !=undefined && buyCustomerSelectedBP!="" && buyCustomerSelectedBP!=null && supplierSelectedBP !=undefined && supplierSelectedBP!="" && supplierSelectedBP!=null){
			 console.log("1 1")
			
			 customerService.getSuppliersFromBuyCustomerForDAR(buyCustomerSelectedBP.customerId).then(function(response) {
					$scope.suppliersForCustomerSelected = response;	
					});	
					
			customerService.getBuyCustomersFromSupplierDAR(supplierSelectedBP.supplierId).then(function(response) {
						$scope.buyCustomers=response;	
					});
		}
		   
		   
		}
		
		
		//End by deepak for mapping between Cust & Supplier			
	$scope.getBalePickups = function(buyCustomer, supplier,	startDate, endDate) {
		
					$scope.loaderActiveBalePickup = true;
						var balePickupFilterDTO = {};
						if((buyCustomer!=null) &&(buyCustomer!=undefined) &&(buyCustomer!="")){
							var buyCustomer =JSON.parse(buyCustomer);
						balePickupFilterDTO.customerId = buyCustomer.customerId;
						}
						if((supplier!=null)&&(supplier!=undefined)&&(supplier!="")){
							var supplier =JSON.parse(supplier);
						balePickupFilterDTO.supplierId = supplier.supplierId;
						}
							
						
						if(startDate!=undefined && startDate!=''){
							 var startDateUTC =  Date.UTC(startDate.getUTCFullYear(), startDate.getUTCMonth(),
						 startDate.getUTCDate()+1,startDate.getUTCHours(), startDate.getUTCMinutes(), startDate.getUTCSeconds());
						 startDate= new Date(startDateUTC);	
						}

						if(endDate!=undefined && endDate!=''){
							 var endDateUTC =  Date.UTC(endDate.getUTCFullYear(), endDate.getUTCMonth(), 
									 endDate.getUTCDate()+1,endDate.getUTCHours(), endDate.getUTCMinutes(), endDate.getUTCSeconds());
						 endDate= new Date(endDateUTC);
						}
						balePickupFilterDTO.startDate = startDate;
						balePickupFilterDTO.endDate = endDate;
						
						console.log("balePickupFilterDTO"+JSON.stringify(balePickupFilterDTO));
						
						balepickupService.getAllPickups(balePickupFilterDTO)
								.then(function(response) {
						$scope.balePickupList = response.data;
						$scope.loaderActiveBalePickup = false;
						$scope.jsonForBalePickupExport=[];
						$scope.fileName = "BalePickup";
						$scope.exportDatax = [];
					
						$scope.exportDatax.push(["Buy Customer", "StoreName", "Pickup Date","Sell Customer","Sell CUstomer Location","Delivery Date", "Release Number", "Destination Gross","Destination Tare","Material Profile","Bales Picked","Bales Remaining", "Supplier Name", "Driver Name", "Incident", "Comments"]);
					
					for(var i=0;i<$scope.balePickupList.length;i++){
						
						
						if($scope.balePickupList[i].pickupDate != undefined){
							$scope.balePickupList[i].pickupDate=$filter('date')($scope.balePickupList[i].pickupDate, 'MM/dd/yyyy');
							
						}else{
							$scope.balePickupList[i].pickupDate='';
						}				
							
						
						if($scope.balePickupList[i].deliveryDate != undefined){
							$scope.balePickupList[i].deliveryDate=$filter('date')($scope.balePickupList[i].deliveryDate, 'MM/dd/yyyy');
							
						}else{
							$scope.balePickupList[i].deliveryDate='';
						}
						
						var name = $scope.balePickupList[i].driverFirstName +" "+$scope.balePickupList[i].driverLastName;
						var comments = $scope.balePickupList[i].comments!=undefined ? $scope.balePickupList[i].comments:"";
						var grossWeight = $scope.balePickupList[i].grossWeight!=undefined ? $scope.balePickupList[i].grossWeight:"";
						var tareWeight =  $scope.balePickupList[i].tareWeight!=undefined ? $scope.balePickupList[i].tareWeight:"";
						var balesRemaining = $scope.balePickupList[i].balesRemaining!=undefined ? $scope.balePickupList[i].balesRemaining:0;
						var balesPicked = $scope.balePickupList[i].balesPicked!=undefined ? $scope.balePickupList[i].balesPicked:0;
						var ifUnknownSellCust = (($scope.balePickupList[i].deliveryDate!=undefined && $scope.balePickupList[i].releaseNo!=undefined && $scope.balePickupList[i].grossWeight!=undefined && $scope.balePickupList[i].tareWeight!=undefined ) ? 'UNKNOWN' :'');
                        var sellCustomerNames = (($scope.balePickupList[i].sellCustomerName!='')? $scope.balePickupList[i].sellCustomerName : ifUnknownSellCust);
                                         
                        var ifUnknownSellLoc = ( ($scope.balePickupList[i].deliveryDate!=undefined && $scope.balePickupList[i].releaseNo!=undefined && $scope.balePickupList[i].grossWeight!=undefined && $scope.balePickupList[i].tareWeight!=undefined ) ? 'UNKNOWN' :'');
                        var sellCustomerSiteNames = (($scope.balePickupList[i].sellCustomerSiteName!='')? $scope.balePickupList[i].sellCustomerSiteName : ifUnknownSellLoc);
                         
						
						$scope.balePickupList[i].name = name;
						$scope.balePickupList[i].comments = comments;
						$scope.balePickupList[i].grossWeight = grossWeight;
						$scope.balePickupList[i].tareWeight = tareWeight;
						$scope.balePickupList[i].balesPicked = balesPicked;
						$scope.balePickupList[i].balesRemaining = balesRemaining;
						$scope.balePickupList[i].sellCustomerName = sellCustomerNames;
                        $scope.balePickupList[i].sellCustomerSiteName=sellCustomerSiteNames;
						
						
			  
						
						$scope.jsonForBalePickupExport.push({
							"Buy Customer":$scope.balePickupList[i].buyCustomerName,
							"StoreName":$scope.balePickupList[i].buyCustomerSiteName,
							"Pickup Date":$scope.balePickupList[i].pickupDate,
							"Sell Customer":$scope.balePickupList[i].sellCustomerName,
							"Sell Customer Location":$scope.balePickupList[i].sellCustomerSiteName,
							"Delivery Date":$scope.balePickupList[i].deliveryDate,
							"Release Number":$scope.balePickupList[i].releaseNo,
							"Destination Gross":$scope.balePickupList[i].grossWeight,
							"Destination Tare":$scope.balePickupList[i].tareWeight,
							"Material Profile":$scope.balePickupList[i].materialDescription,
							"Bales Picked":$scope.balePickupList[i].balesPicked,
							"Bales Remaining":$scope.balePickupList[i].balesRemaining,
							"Supplier Name":$scope.balePickupList[i].supplierName,
							"Driver Name":$scope.balePickupList[i].name,
							"Incident":$scope.balePickupList[i].incidentDescription,
							"Comments":$scope.balePickupList[i].comments 
							
							})	
							
					
				$scope.exportDatax.push([$scope.balePickupList[i].buyCustomerName, $scope.balePickupList[i].buyCustomerSiteName, $scope.balePickupList[i].pickupDate,
				                         $scope.balePickupList[i].sellCustomerName, $scope.balePickupList[i].sellCustomerSiteName, 
				                         $scope.balePickupList[i].deliveryDate, $scope.balePickupList[i].releaseNo, $scope.balePickupList[i].grossWeight,
				                         $scope.balePickupList[i].tareWeight, $scope.balePickupList[i].materialDescription, $scope.balePickupList[i].balesPicked,
				                         $scope.balePickupList[i].balesRemaining, $scope.balePickupList[i].supplierName,$scope.balePickupList[i].name,
				                         $scope.balePickupList[i].incidentDescription,$scope.balePickupList[i].comments.toString()]);
				
													
				$scope.jsonForDAReportExport = $filter('orderBy')(
								$scope.jsonForDAReportExport, 'PickupDate');
					}
					
					$scope.authorizedBalePickup=true;						
							
							 $scope.dtColumnsBalePickup = [
										   DTColumnBuilder.newColumn('buyCustomerName').withTitle('Buy Customer'),
										   DTColumnBuilder.newColumn('buyCustomerSiteName').withTitle('Store Name'),
										   DTColumnBuilder.newColumn('pickupDate').withOption('sType','date').withTitle('Pickup Date'),
										   DTColumnBuilder.newColumn('sellCustomerName').withTitle('Sell Customer'),
										   DTColumnBuilder.newColumn('sellCustomerSiteName').withTitle('Sell Customer Location'),
										   DTColumnBuilder.newColumn('deliveryDate').withOption('sType','date').withTitle('Delivery Date'),
										   DTColumnBuilder.newColumn('releaseNo').withTitle('Release Number'),
										   DTColumnBuilder.newColumn('grossWeight').withTitle('Destination Gross'),
										   DTColumnBuilder.newColumn('tareWeight').withTitle('Destination Tare'),
										   DTColumnBuilder.newColumn('materialDescription').withTitle('Material Profile'),
										   DTColumnBuilder.newColumn('balesPicked').withTitle('Bales Picked'),
										   DTColumnBuilder.newColumn('balesRemaining').withTitle('Bales Remaining'),
										   DTColumnBuilder.newColumn('supplierName').withTitle('Supplier Name'),
										   DTColumnBuilder.newColumn('name').withTitle('Driver Name'),
										   DTColumnBuilder.newColumn('incidentDescription').withTitle('Incident'),
										   DTColumnBuilder.newColumn('comments').withTitle('Comments'),
										   
										   DTColumnBuilder.newColumn('').withTitle('Incident Picture').notSortable().renderWith(function(data, type, full, meta) {
                      
					   $scope.imageVal =  full.imageAvailable;
					
						var htmlTemp;
						if($scope.imageVal == "Y"){
							 htmlTemp='<img '
									+'src="/brta/app/ui/resources/images/column_incident-management2.png"'
									+'ng-click="openLightboxModal('+full.tripId+');"'
									+'tabIndex="16"'
									+'style="height: 33px;margin-top:-10px;margin-bottom:-10px">';		
									
							
						}else{
							 htmlTemp='<p></p>'	;	
							
							
						}
						return htmlTemp;	
										}),	
										
										  DTColumnBuilder.newColumn('').withTitle('Edit').notSortable().renderWith(function(data, type, full, meta) {
									var htmlTemp='<div></div>';
												
												
										htmlTemp='<div style="width: 20px; padding-top: 0px !important; padding-bottom: 0px !important">'+
												'<img src="/brta/app/ui/resources/images/editdata.png"'+ 
									'ng-click="assignTabIndexBalePickup();balepickuppopupCalling('+full.tripId+')"'+
									'class="editImageAlignment" style="height: 40px;margin-top:-10px;margin-bottom:-10px"></div>';
									
									
									
									return htmlTemp;	
										}) 
									   ];  
									  
									  
								 $scope.dtOptionsBalePickup= DTOptionsBuilder.newOptions().withOption('data', $scope.balePickupList).withOption('createdRow', function (row, data, dataIndex) {

									$compile(angular.element(row).contents())($scope);
								}).withOption('headerCallback', function( thead, data, start, end, display ) {
									$compile(angular.element(thead).contents())($scope);
								}).withOption('scrollX', true)
											 .withOption('bScrollCollapse', true)
											 .withOption('bAutoWidth', true)
										 
								.withOption('bFilter', true)
								
								.withOption('responsive', false);
								
							});
				};
	$scope.downloadBalePdf = function() {
		
var data = [];		


if($scope.jsonForBalePickupExport !=undefined && $scope.jsonForBalePickupExport.length >0){


data=$scope.jsonForBalePickupExport;		

	var doc = new jsPDF('p', 'mm', [650, 550]); 
	 doc.setFontSize(13);
	doc.autoTable(headerTableBalePickup, data, {	
	
	styles : {
			overflow : 'linebreak'
		},
	
 
  margin: { top: 50, left: 20, right: 20, bottom: 0 },
   drawHeaderCell: function (cell, data) {
	    if (cell.raw === 'ID') {
	        cell.styles.fontSize= 15;
	       cell.styles.textColor = [255,0,0];
	    } else {
	        cell.styles.textColor = 255;
	        cell.styles.fontSize = 10;

	    }
	},
	   createdCell: function (cell, data) {
	    if (cell.raw === 'Jack') {
	       cell.styles.fontSize= 15;
	       cell.styles.textColor = [255,0,0];
	    } 
	}

});		
	doc.save('BalePickup.pdf');	
}
	
}
	
	var headerTableBalePickup = [		
                   { title: "BUY CUSTOMER", dataKey: "Buy Customer" },		
                     { title: "STORE NAME", dataKey: "StoreName" },		
                     { title: "PICKUP DATE", dataKey: "Pickup Date" },		
                     { title: "SELL CUSTOMER", dataKey: "Sell Customer" },		
                     { title: "SELL CUSTOMER LOCATION", dataKey: "Sell Customer Location" },
					 { title: "DELIVERY DATE", dataKey: "Delivery Date" },	
					 { title: "RELEASE NUMBER", dataKey: "Release Number" }, 
					 { title: "DESTINATION GROSS", dataKey: "Destination Gross" }, 	
					 { title: "DESTINATION TARE", dataKey: "Destination Tare" }, 	 
                     { title: "MATERIAL PROFILE", dataKey: "Material Profile" },
                     { title: "BALES PICKED", dataKey: "Bales Picked" },	
					 { title: "BALES REMAINING", dataKey: "Bales Remaining" },	
					 { title: "SUPPLIER NAME", dataKey: "Supplier Name" },
                     { title: "DRIVER NAME", dataKey: "Driver Name" },		
                     { title: "INCIDENT", dataKey: "Incident" },		
                     { title: "COMMENTS", dataKey: "Comments" }	
                ];
	
		

	$scope.balePickupPopupClose = function(){
		$scope.disableStoreName=false;
		$scope.addEditDriverPopup=false;
		storeNameForSC=null;
	}	
					
	
		$scope.adddatabalepickup = function(){
			$scope.currentBalePickupRecord=undefined;

				$scope.editPickupData={};
				$scope.editPickupData.materialPickedWeight=0;
				$scope.editPickupData.balesRemaining=0;
				$scope.invalidBalePickupStartDateError ='';
					

				$scope.isEdit=false;
				$scope.disableStoreName=false;
				$scope.disableBuyCustomerName=false;
				$scope.addEditDriverPopup=true;
			if($rootScope.user.role=="Supplier"){
				$rootScope.selectedSupplier = $scope.editPickupData.supplierName = JSON.parse($scope.supplierSelectedBP);
				userService.getOnlyDriversForSupplierSite($scope.editPickupData.supplierName.supplierId).then(function(response) {
									$scope.drivers = response;
				});
			}
		}
		$scope.balepickuppopupOpen=function(customerSiteId){
			$scope.balepickuppopup(balePickupObject);
		}
			

$scope.balepickuppopupCalling=function(balePickupId) {
	var balePickupObject = $filter('filter')($scope.balePickupList, function (d) {return d.tripId === balePickupId;})[0];
						$scope.balepickuppopup(balePickupObject);
						
						$scope.balePickupObjectForSC=balePickupObject;
						$scope.invalidBalePickupStartDateError ='';
						
						//added by prema (pop up)
						$scope.loaderActiveBalePickup = true;
						setTimeout(function(){
							 $scope.loaderActiveBalePickup = false;
							 },4000);
						
						if($scope.editPickupData.deliveryDate == null || $scope.editPickupData.deliveryDate == "Invalid Date"){
							$scope.editPickupData.deliveryDate = "";
							} 
						//ended by prema
						
}			
		$scope.balepickuppopup = function(dashboardbalepickuplist) {	
	
		$scope.scWorkOrder={};
		serviceChannelService.serviceChannelCustomers().then(function(response) {
			$scope.servicechannelCustomers =response;
		});

		for(var e=0;e<$scope.servicechannelCustomers.length;e++){
			if($scope.servicechannelCustomers.customerId == dashboardbalepickuplist.buyCustomerId){
				$scope.scWorkOrder=true;
				break;
		}else{
			$scope.scWorkOrder=false;
		}
	}

		$scope.currentBalePickupRecord=dashboardbalepickuplist;
		$scope.disableStoreName=true;
		$scope.disableBuyCustomerName=true;
		$scope.isEdit=true;
		$scope.loadertest=true;
		$scope.editPickupData={};
		$scope.serviceChannelWO={};
		
		var selectedCustomerName={};
		var selectedMaterialname={};
		var selectedStorename={};
		var selectedSuppliername={};
		var selectedSellCustomer={};
		var selectedSellCustomerSite={};
		var selectedDriverFirstName={};
		var selectedDriverLastName={};
		var selectedIncident={};
		var selectedTareWeight={};
		var selectedGrossWeight={};
		var selectedReleasenumber={};
		var selectedComments={};
		var selectedDeliveryDate={};
		var selectedPickupDate={};
		
		
		//editPickupData.sellCustomer
				
		for(var i=0;i<$scope.buyCustomers.length ;i++){
			if($scope.buyCustomers[i].customerName == dashboardbalepickuplist.buyCustomerName){
				
				$scope.editPickupData.buycustomerName=$scope.buyCustomers[i];
								
				customerService.getAllSellCustomersForBuyCustomerAndSupplier($scope.buyCustomers[i].customerId,dashboardbalepickuplist.supplierId).then(function(response) {
				$scope.sellCustomersForCustomerSelected = response;
				var unknownSellCustomer={};
				unknownSellCustomer.customerName="UNKNOWN";
				$scope.sellCustomersForCustomerSelected.push(unknownSellCustomer);
				
				

				
				if(dashboardbalepickuplist.sellCustomerId !=undefined){
					 for(var u=0;u<$scope.sellCustomersForCustomerSelected.length;u++){
				 if($scope.sellCustomersForCustomerSelected[u].customerId == dashboardbalepickuplist.sellCustomerId){
					$scope.editPickupData.sellCustomer = $scope.sellCustomersForCustomerSelected[u];
					break;
				 }			
				 }
				 var sellcustomerlist=[];
				 
				 if($scope.editPickupData.sellCustomer !=undefined){
					 sellcustomerlist.push($scope.editPickupData.sellCustomer);
				 }
				 
				 setTimeout(function(){
					 if(sellcustomerlist.length >0){
		customerService.getCustomerSitesForBuyCustomer(sellcustomerlist).then(function(response) {
			$scope.destinationSitesForDestinationSelected = response;	
			
				
			for(var f=0;f<$scope.destinationSitesForDestinationSelected.length;f++){
				if($scope.destinationSitesForDestinationSelected[f].customerSiteId== dashboardbalepickuplist.sellCustomerSiteId){
					$scope.editPickupData.sellCustomerSite = $scope.destinationSitesForDestinationSelected[f];
												
				}
			}
		});
	}
					 },1000);
				 
	
				 
				}else{
					
					//console.log(dashboardbalepickuplist);
					
					if((dashboardbalepickuplist.deliveryDate!=undefined && dashboardbalepickuplist.deliveryDate!="")
						&& (dashboardbalepickuplist.releaseNo!=undefined && dashboardbalepickuplist.releaseNo!="") 
					&& (dashboardbalepickuplist.grossWeight!=undefined && dashboardbalepickuplist.grossWeight!="" )
					&& (dashboardbalepickuplist.tareWeight!=undefined || dashboardbalepickuplist.tareWeight!=undefined)){
						var unknownSellCustomer={};
							
					 unknownSellCustomer.customerName="UNKNOWN";
					// $scope.sellCustomersForCustomerSelected.push(unknownSellCustomer);
				
					$scope.editPickupData.sellCustomer=unknownSellCustomer;
					

					var unknownSellCustomerSite={};
			unknownSellCustomerSite.siteName="UNKNOWN";
			
			if($scope.destinationSitesForDestinationSelected ==undefined){
				$scope.destinationSitesForDestinationSelected=[];
			}
			$scope.destinationSitesForDestinationSelected.push(unknownSellCustomerSite);
			$scope.editPickupData.sellCustomerSite=unknownSellCustomerSite;
					}
					

					
				}
				
				})
				
			}
			
		}
		
		setTimeout(function(){
		customerService.getCustomerSiteFromBuyCustomerSite($scope.editPickupData.buycustomerName).then(function(response){
		
		 $scope.getCustomerSiteFromBuyCustomerSite = response;
		
		for(var j=0;j<$scope.getCustomerSiteFromBuyCustomerSite.length;j++){
			if($scope.getCustomerSiteFromBuyCustomerSite[j].siteName == dashboardbalepickuplist.buyCustomerSiteName){
				
				$scope.editPickupData.storeName=$scope.getCustomerSiteFromBuyCustomerSite[j];
				
				serviceChannelWO.store = $scope.editPickupData.storeName.siteName;
				storeNameForSC = serviceChannelWO.store;
				
				break;
			
			}
		}
		
		
		
	 supplierService.allSupplierFromCustomerSite($scope.editPickupData.storeName.customerSiteId).then(function(response) {
		 $scope.allSupplierFromCustomerSite = response;
		 for(var k=0;k<$scope.allSupplierFromCustomerSite.length;k++){
		if(dashboardbalepickuplist.supplierName ==$scope.allSupplierFromCustomerSite[k].description){
			$scope.editPickupData.supplierName=$scope.allSupplierFromCustomerSite[k];
			
	
		 break;
		}
		 }
	
	
	
	userService.getOnlyDriversForSupplierSite($scope.editPickupData.supplierName.supplierId).then(function(response) {
									$scope.drivers = response;
						
						 for(var g=0;g<$scope.drivers.length;g++){
				if($scope.drivers[g].userId==dashboardbalepickuplist.driverId){
				$scope.editPickupData.driverNames=$scope.drivers[g];
				break;
				}
			}
	
	})
	})
	customerService.getMaterialDetailsByMaterialID($scope.editPickupData.storeName.customerSiteId).then(function(response) {
	$scope.getMaterialDetailsByMaterialID = response;
	for(var j=0;j<$scope.getMaterialDetailsByMaterialID.length;j++){
		if( dashboardbalepickuplist.materialShortName ==$scope.getMaterialDetailsByMaterialID[j].shortName){
			$scope.editPickupData.materialprofile = $scope.getMaterialDetailsByMaterialID[j];
			break;
	
		}
	}
		})
	 });	
		},2000);


		setTimeout(function(){
		incidentTypeService.getAllIncidentTypes().then(function(response) {
		$scope.incidentTypeList = response;
		for(var l=0;l<$scope.incidentTypeList.length;l++){
			
			if($scope.incidentTypeList[l].incidentDescription == dashboardbalepickuplist.incidentDescription){
			$scope.editPickupData.Incidents=$scope.incidentTypeList[l];
			break;
			}
			}
	});
	},2000);
	
			$scope.editPickupData.materialPickedWeight= dashboardbalepickuplist.balesPicked;
			$scope.editPickupData.balesRemaining = dashboardbalepickuplist.balesRemaining;
			
			
			
			
			var date =new Date(dashboardbalepickuplist.pickupDate);		
			
			$scope.editPickupData.grossWeight =dashboardbalepickuplist.grossWeight;
			$scope.editPickupData.tareWeight =dashboardbalepickuplist.tareWeight;
			$scope.editPickupData.releaseNoselected =dashboardbalepickuplist.releaseNo;
			$scope.editPickupData.commentsselected =dashboardbalepickuplist.comments;
			$scope.editPickupData.bOL =dashboardbalepickuplist.bOL;
			
			$scope.editPickupData.startDateBalePickup =convertDateToString(dashboardbalepickuplist.pickupDate);
			$scope.editPickupData.deliveryDate =convertDateToString(dashboardbalepickuplist.deliveryDate);
			var pickupDate =new Date(dashboardbalepickuplist.pickupDate);		
			var deliveryDate =new Date(dashboardbalepickuplist.deliveryDate);		
			$scope.editPickupData.deliveryDate = deliveryDate;
			$scope.editPickupData.startDateBalePickup = pickupDate ;
			
			setTimeout(function(){
				
				$scope.getServiceChannelWorkOrder();
			},3000);
			
			
			
			
			
			$scope.addEditDriverPopup=true;	
	
			$scope.dashboardbalepickuplist= dashboardbalepickuplist;
			
			setTimeout(function(){
				
			$scope.loadertest=false;
		}, 2000);
		}	

					
					$scope.editBalePickup = function() {
						$scope.loaderActiveBalePickup = true;
						$scope.validationError=false;
						
												
						$scope.editPickupData.error={};
						if($scope.editPickupData.buycustomerName== undefined || $scope.editPickupData.buycustomerName== null || $scope.editPickupData.buycustomerName== ""  ){
							$scope.editPickupData.error.buycustomerNameError= true;
							$scope.validationError=true;
						}else{
							
						}
						
						//by deepak
						
						if($scope.editPickupData.materialPickedWeight > 0)
						{
							if($scope.editPickupData.bOL == undefined || $scope.editPickupData.bOL == ""  || $scope.editPickupData.bOL == null || $scope.editPickupData.bOL <=0 ){
								$scope.editPickupData.error.bolError= true;
							$scope.validationError=true;
						}else{
							
						}
						
						}
						if($scope.editPickupData.storeName== undefined || $scope.editPickupData.storeName== null || $scope.editPickupData.storeName== "" ){
							$scope.editPickupData.error.storeNameError= true;
							$scope.validationError=true;
						}
						
						
						if($scope.editPickupData.materialprofile== undefined || $scope.editPickupData.materialprofile== null || $scope.editPickupData.materialprofile== ""){
							$scope.editPickupData.error.materialprofileError= true;
							$scope.validationError=true;
						}

						if(($scope.editPickupData.materialPickedWeight== undefined) || ($scope.editPickupData.materialPickedWeight== null) || ($scope.editPickupData.materialPickedWeight ==='')){
							$scope.editPickupData.error.materialPickedWeightError= true;
							$scope.validationError=true;
						}
						
						
						
						if($scope.editPickupData.materialPickedWeight!= undefined && $scope.editPickupData.materialPickedWeight!= null && $scope.editPickupData.materialPickedWeight!=''){

							if($scope.editPickupData.materialPickedWeight<0){

								$scope.validationError=true;
								$scope.editPickupData.error.invalidMaterialPickedWeightError= true;
								$scope.editPickupData.error.materialPickedWeightError= false;
							
							}
						}
						
						
						if($scope.editPickupData.balesRemaining== undefined || $scope.editPickupData.balesRemaining== null || $scope.editPickupData.balesRemaining==='' ){
							$scope.editPickupData.error.balesRemainingError= true;
							$scope.validationError=true;
						}
						
						if($scope.editPickupData.balesRemaining!= undefined && $scope.editPickupData.balesRemaining!= null && $scope.editPickupData.balesRemaining!= ''){
							if($scope.editPickupData.balesRemaining<0){
								$scope.validationError=true;
								$scope.editPickupData.error.invalidBalesRemainingError= true;
								$scope.editPickupData.error.balesRemainingError= false;
							
							}
						}
						
						if($scope.editPickupData.startDateBalePickup== undefined || $scope.editPickupData.startDateBalePickup== null || $scope.editPickupData.startDateBalePickup== "" ){
							$scope.editPickupData.error.startDateBalePickupError= true;
							$scope.validationError=true;
						}


						if($scope.editPickupData.driverNames== undefined || $scope.editPickupData.driverNames== null || $scope.editPickupData.driverNames== "" ){
							$scope.editPickupData.error.driverNamesError= true;
							$scope.validationError=true;
						}




						
						if($scope.editPickupData.sellCustomer != undefined ){
							
								if($scope.editPickupData.sellCustomerSite== undefined || $scope.editPickupData.sellCustomerSite== null || $scope.editPickupData.sellCustomerSite== "" ){
							$scope.editPickupData.error.sellCustomerSiteError= true;
							$scope.validationError=true;
						}
						
						if($scope.editPickupData.tareWeight!= undefined && $scope.editPickupData.grossWeight!= undefined){
							 //$scope.validationError=false;
							 $scope.editPickupData.error.grossWeightError= false;
							 $scope.editPickupData.error.tareWeightError= false;
							 
							var tareWeight = new Number($scope.editPickupData.tareWeight);
							var grossWeight = new Number($scope.editPickupData.grossWeight);
							
							if(tareWeight > grossWeight){
								$scope.editPickupData.error.invalidGrossWeightError= true;
							$scope.validationError=true;
							}
						}
						
						 if($scope.editPickupData.grossWeight== undefined || $scope.editPickupData.grossWeight== null || $scope.editPickupData.grossWeight=== "" 
							 ||  $scope.editPickupData.grossWeight== ""){
							 $scope.editPickupData.error.grossWeightError= true;
							 $scope.validationError=true;
						 }
						 
						  if($scope.editPickupData.tareWeight== undefined || $scope.editPickupData.tareWeight== null || $scope.editPickupData.tareWeight=== ""
						||  $scope.editPickupData.tareWeight== ""){
							 $scope.editPickupData.error.tareWeightError= true;
							 $scope.validationError=true;
						 }
						
						 if($scope.editPickupData.deliveryDate== undefined || $scope.editPickupData.deliveryDate== null || $scope.editPickupData.deliveryDate== ""  ){
							$scope.editPickupData.error.deliveryDateError= true;
							 $scope.validationError=true;
						 }
						 
						  if($scope.editPickupData.releaseNoselected== undefined || $scope.editPickupData.releaseNoselected== null || $scope.editPickupData.releaseNoselected== ""  ){
							$scope.editPickupData.error.releaseNoselectedError= true;
							 $scope.validationError=true;
						 }
						
						
						}
						if($scope.editPickupData.supplierName== undefined || $scope.editPickupData.supplierName== null || $scope.editPickupData.supplierName== "" ){
							$scope.editPickupData.error.supplierNameError= true;
							$scope.validationError=true;
						}
						
						if(!$scope.validationError){
						var dateconvert = (new Date($scope.editPickupData.startDateBalePickup)).toLocaleDateString();
						var date2 = new Date(dateconvert);
						var dateString = (date2.getMonth()+1<=9?'0'+(date2.getMonth()+1) :date2.getMonth()+1)+'-'+(date2.getDate()<= 9?'0'+date2.getDate():date2.getDate())+'-'+date2.getFullYear();
						var deliverydateconvert = (new Date($scope.editPickupData.deliveryDate)).toLocaleDateString();
						var deliverydate = new Date(deliverydateconvert);
						var deliverydateString = (deliverydate.getMonth()+1<=9?'0'+(deliverydate.getMonth()+1) :deliverydate.getMonth()+1)+'-'+(deliverydate.getDate()<= 9?'0'+deliverydate.getDate():deliverydate.getDate())+'-'+deliverydate.getFullYear();
						
						
						var obj = {};
						
						if(($scope.editPickupData.bOL!=null)||($scope.editPickupData.bOL!=undefined)){
						obj.bol = $scope.editPickupData.bOL;
						}else{
							obj.bol=0;
						}
						
						if(($scope.editPickupData.Incidents!=undefined) && ($scope.editPickupData.Incidents!=null) && (!angular.equals($scope.editPickupData.Incidents, {}) )){
							obj.incident=true;	
						obj.incidentType = $scope.editPickupData.Incidents;
						}else{
							obj.incident=false;
						}
						
						
					
						var commodityObj = {};
						var materialList = [];
							commodityObj.materialId = $scope.editPickupData.materialprofile.materialId;
							commodityObj.shortName = $scope.editPickupData.materialprofile.shortName;
							commodityObj.balesPicked = $scope.editPickupData.materialPickedWeight;
							commodityObj.balesRemaining = $scope.editPickupData.balesRemaining;
							materialList.push(commodityObj);
							obj.materialList=materialList;
		
						obj.storeId = $scope.editPickupData.storeName.customerSiteId;
						obj.storeName = $scope.editPickupData.storeName.siteName;
						
						obj.userId = $scope.editPickupData.driverNames.userId;
						
						obj.pickupDate = dateString; // $scope.editPickupData.startDateBalePickup;
						
						if($scope.currentBalePickupRecord!=undefined){
							
							
							obj.oldPickupDate= $scope.currentBalePickupRecord.pickupDate;
							obj.oldPickupDate=obj.oldPickupDate.replace(/\//g, "-");
							obj.oldMaterialId=$scope.currentBalePickupRecord.materialId;
							obj.oldDriverId=$scope.currentBalePickupRecord.driverId;
							
						}
						if($scope.editPickupData.sellCustomerSite!=undefined){
							if($scope.editPickupData.sellCustomerSite.siteName != 'UNKNOWN'){
								obj.destinationId = $scope.editPickupData.sellCustomerSite.customerSiteId;
							}
						
						obj.grossWeight = $scope.editPickupData.grossWeight;
						obj.tareWeight = $scope.editPickupData.tareWeight;
						obj.deliveryDate =deliverydateString;
						obj.releaseNotes= $scope.editPickupData.releaseNoselected;

						}
						
						obj.tripId = $scope.dashboardbalepickuplist.tripId;
						obj.comments = $scope.editPickupData.commentsselected;
						
						var images=[];
						if($scope.editPickupData.uploadIncidentFile !=undefined){
							if($scope.editPickupData.uploadIncidentFile.length > 0){
								for(var i=0;i<$scope.editPickupData.uploadIncidentFile.length;i++){
								images.push($scope.editPickupData.uploadIncidentFile[i].base64);
							}
							}
							
						}
						
						
						obj.images=images;
						
						
						
						balepickupService.editBalePickup(obj).then(function(response) {
			
											if (response != null
													&& response != "") {
										
											
												$scope.addEditDriverPopup=false;
												
												$scope.getBalePickups($scope.buyCustomerSelectedBP,$scope.supplierSelectedBP, $scope.startDate,$scope.endDate);
																								$scope.loaderActiveBalePickup = false;

											}

										})
						}else{
							$scope.loaderActiveBalePickup = false;

						}
					};

				
				// Added by Naren
				  $scope.getCustomersFromSupplier =function (selectedSupplierId){
						customerService.getBuyCustomersFromSupplierDAR(selectedSupplierId).then(function(response) {
						$scope.buyCustomers=response;	
					}); 
  }
					
					
////code added by deepak for Phase2					
					
				
					$scope.getBuyCustomerDetails = function(buyCustomer) {

					
					if(!$scope.isSupplierRole){
						$scope.loaderBalePickup = true;
						if (buyCustomer != null) {
							customerService.getSuppliersFromBuyCustomerForDAR(buyCustomer.customerId).then(function(response) {
							$scope.suppliersForCustomerSelected = response;		
							});

							customerService.getMatrialsForBuyCustomer(
									buyCustomer.customerId).then(function(response) {
								$scope.commodities = response;
							});

						} else {
							$scope.suppliersForCustomerSelected = $rootScope.allSuppliers;
						}

					

						$scope.loaderBalePickup = false;
					}
					
					};

					$scope.getUsersFromSupplier = function(supplierSelected) {
						$scope.disableAddPickupBtn = false;
					
						if(supplierSelected!=null && supplierSelected!=undefined && supplierSelected!="" ){
						userService.getAllUsersForSupplierSite(supplierSelected)
								.then(function(response) {
									$scope.driverList = response;
								});
					}
					}

					$scope.getDriverPhoneNo = function(driver) {
						$scope.pickup.driverPhoneNo = driver.mobilePhone;
						$scope.pickup.userId = driver.userId;
					}

					$scope.getDestinationForUser = function(userId, pickupDate) {

						var pickupDateList = [];
						pickupDateList.push(convertDateToString(pickupDate));

						var setDto = {};
						setDto.userId = userId;
						setDto.dates = pickupDateList;

						balepickupService
								.getDestinationForUserAndDate(setDto)
								.then(
										function(response) {

											$scope.destinationList = response.data;
											$scope.storeNameList = [];
											$scope.pickupDetails = [];
											$scope.destinationNames = [];

											for (var i = 0; i < $scope.destinationList.length; i++) {
												$scope.destinationNames
														.push($scope.destinationList[i].name);
												var pickupDetails = $scope.destinationList[i].pickupDetails;
												for (var j = 0; j < pickupDetails.length; j++) {

													$scope.pickupDetails
															.push(pickupDetails[j]);
													$scope.storeNameList
															.push(pickupDetails[j].storeName);
												}
											}
										});

					}

					
					$scope.setPickupAssignmentId = function(storeName) {
						var destinationId = undefined;
						for (var i = 0; i < $scope.pickupDetails.length; i++) {
							if ($scope.pickupDetails[i].storeName == storeName) {
								destinationId = $scope.pickupDetails[i].destination.destinationId
								$scope.pickup.balePickupAssignmentId = $scope.pickupDetails[i].assignmentId;
								$scope.pickup.storeId = $scope.pickupDetails[i].storeId;
							}
						}

						if (destinationId != undefined) {
							$scope.destinationNames = [];
							for (var j = 0; j < $scope.destinationList.length; j++) {

								if ($scope.destinationList[j].destinationId == destinationId)
									$scope.destinationNames
											.push($scope.destinationList[j].name);
							}
						}
					}

					incidentTypeService
							.getAllIncidentTypes()
							.then(
									function(response) {
										$scope.incidentTypeList = response;
										$scope.incidentTypes = [];

										for (var i = 0; i < $scope.incidentTypeList.length; i++) {
											if ($scope.incidentTypeList[i].enabled == true) {
												$scope.incidentTypes
														.push($scope.incidentTypeList[i].incidentDescription);
											}
										}

									});

					$scope.getIncidentObject = function(incidentName) {

						var incidentObj = {};
						for (var i = 0; i < $scope.incidentTypeList.length; i++) {

							if ($scope.incidentTypeList[i].incidentDescription == incidentName) {
								incidentObj = $scope.incidentTypeList[i];
								break;
							}

						}

						return incidentObj;
					}

					$scope.getDestinationobject = function(name) {

						var destinationObj = {};
						for (var i = 0; i < $scope.destinationList.length; i++) {

							if ($scope.destinationList[i].name == name) {
								destinationObj = $scope.destinationList[i];
								break;
							}

						}

						return destinationObj;
					}

					var originalBalePickupWithoutEdit = {};

					$scope.modify = function(balePickup) {
						$scope.editingData[balePickup.tripId] = true;

						originalBalePickupWithoutEdit = angular
								.copy(balePickup);
						$scope.getDestinationForUser(balePickup.driver,
								balePickup.pickupDate);
					};

					$scope.cancel = function(balePickup) {

						for (var i = 0; i < $scope.balePickupList.length; i++) {

							if ($scope.balePickupList[i].tripId == balePickup.tripId) {
								$scope.balePickupList[i] = originalBalePickupWithoutEdit
								break;
							}

						}

						$scope.editingData[balePickup.tripId] = false;
					};

					

					$scope.showAddNewPickupForm = function() {
						$scope.addNewBalePickupView = true;
						$scope.pickup = {};
					}

					var convertDateToString = function(date) {
						var date = new Date(date);
						var dateStr = (date.getMonth() + 1) + '/'
								+ date.getDate() + '/' + date.getFullYear()
						return dateStr;
					}

					$scope.saveNewBalePickup = function(balePickup) {
						
						$scope.loaderActiveBalePickup = true;
						$scope.error = false;

						if (balePickup.driverName == ""
								|| balePickup.driverName == null) {
							$scope.driverName_error = true;
							$scope.error = true;
							$scope.loaderActiveBalePickup = false;
						}

						if (balePickup.driverPhoneNo == ""
								|| balePickup.driverPhoneNo == null) {
							$scope.driverPhoneNo_error = true;
							$scope.error = true;
							$scope.loaderActiveBalePickup = false;
						}

						if (balePickup.storeName == ""
								|| balePickup.storeName == null) {
							$scope.storeName_error = true;
							$scope.error = true;
							$scope.loaderActiveBalePickup = false;

						}

						if (balePickup.pickupDate == ""
								|| balePickup.pickupDate == null) {
							$scope.pickupDate_error = true;
							$scope.error = true;
							$scope.loaderActiveBalePickup = false;

						}

						if (balePickup.destination == ""
								|| balePickup.destination == null) {
							$scope.destination_error = true;
							$scope.error = true;
							$scope.loaderActiveBalePickup = false;

						}

						if (balePickup.commodities == ""
								|| balePickup.commodities == null) {
							$scope.commodity_error = true;
							$scope.error = true;
						}

						if (balePickup.balePicked == ""
								|| balePickup.balePicked == null) {
							$scope.balePicked_error = true;
							$scope.error = true;
							$scope.loaderActiveBalePickup = false;

						}

						if (balePickup.baleRemaining == ""
								|| balePickup.baleRemaining == null) {
							$scope.baleRemaining_error = true;
							$scope.error = true;
							$scope.loaderActiveBalePickup = false;

						}

						if (!$scope.error) {
							var commodities = [];

							var commodityObj = {};
							commodityObj.commodityId = balePickup.commodities.materialId
							commodityObj.name = balePickup.commodities.description
							commodityObj.balesPicked = balePickup.balePicked
							commodityObj.balesRemaining = balePickup.baleRemaining
							commodities.push(commodityObj);

							var obj = {};
							obj.assignmentId = balePickup.balePickupAssignmentId;

							obj.userId = balePickup.userId;
							obj.storeId = balePickup.storeId;
							obj.storeName = balePickup.storeName;
							obj.address = balePickup.destination;
							obj.pickupDate = convertDateToString(balePickup.pickupDate);
							obj.balesPicked = balePickup.balePicked;
							obj.balesRemaining = balePickup.baleRemaining;
							obj.comments = balePickup.incidentComment;
							obj.destination = $scope
									.getDestinationobject(balePickup.destination);
							;
							obj.commodities = commodities;
							obj.incidentType = $scope
									.getIncidentObject(balePickup.incidentType);

							if (Object.keys(obj.incidentType).length == 0) {
								obj.incident = false;
								obj.incidentType = null;
							}
							obj.bol = "0";

			

							balepickupService
									.addNewBalePickup(obj)
									.then(
											function(response) {
												if (response != null
														&& response != "") {

													var completeTripInputPayLoad = {};
													completeTripInputPayLoad.userId = balePickup.userId
													completeTripInputPayLoad.date = convertDateToString(balePickup.pickupDate);

													var completePickups = [];
													completePickups.push(obj);

													completeTripInputPayLoad.completePickups = completePickups;

											//	

													balepickupService
															.completeBalePickup(
																	completeTripInputPayLoad)
															.then(
																	function(
																			response) {
																		console
																				.log("---completeBalePickup---"
																						+ JSON
																								.stringify(response));
																	});

													$scope.addNewBalePickupView = false;
													$scope.pickup = {};
													$scope
															.getBalePickups(
																	$scope.buyCustomerSelected,
																	$scope.supplierSelected,
																	$scope.startDate,
																	$scope.endDate);
												}
											});
						}

						$scope.loaderActiveBalePickup = false;

					}
						
						
				
					$scope.uploadFile = function(files) {

						$scope.$apply(function() {
							$scope.message = "";
							$scope.selectedFileForUpload = files[0];
						})
					}
					// Parse Excel Data
					$scope.ParseExcelDataAndSave = function() {
						$scope.uploadExcelMessage=[];		
						$scope.loaderActiveBalePickup = true;
						var file = $scope.selectedFileForUpload;
						var ext=file.name.substr(file.name.lastIndexOf('.') + 1);
						
						if(ext == "xlsx"){
						
						if (file) {
							var reader = new FileReader();
							reader.onload = function(e) {
								if (!e) {var data = reader.content;} else {var data = e.target.result; }
								var workbook = XLSX.read(data, {
									type : 'binary'
							});
								var sheetName = workbook.SheetNames[0];
								var excelData = XLSX.utils.sheet_to_row_object_array(workbook.Sheets[sheetName]);
								console.log(excelData);
								var colValues =[];
								var headerColValues =[];
								var first_sheet_name = workbook.SheetNames[0];
									var worksheet = workbook.Sheets[first_sheet_name];
									var cells = Object.keys(worksheet);
									
									

									for (var i = 0; i < Object.keys(cells).length; i++) {
										
										
										console.log(cells[i]);
										var thenum = cells[i].replace( /^\D+/g, '');
										if( thenum == 1)
										{
											
											headerColValues.push(worksheet[cells[i]].v.trim()); //Contails Dummy Data
										}
									}
									
									if((headerColValues[0]=="BuyCustomer" && headerColValues[1]=="StoreName" && headerColValues[2]=="PickupDate" && headerColValues[3]=="SellCustomer" && headerColValues[4]=="SellCustomerLocation" && headerColValues[5]=="DeliveryDate" && headerColValues[6]=="ReleaseNumber" && headerColValues[7]=="DestinationGross" && headerColValues[8]=="DestinationTare" && headerColValues[9]=="MaterialProfile" && headerColValues[10]=="BalesPicked" && headerColValues[11]=="BalesRemaining" && headerColValues[12]=="BOL/TicketNo" && headerColValues[13]=="SupplierName" && headerColValues[14]=="DriverName" && headerColValues[15]=="Incident" && headerColValues[16]=="Comments")){
									
									for (var i = 1; i < Object.keys(cells).length; i++) {
										if( cells[i].indexOf('2') > -1)
										{
											colValues.push(worksheet[cells[i]]); //Contails Dummy Data
										}
									}
									
									if(colValues.length>0){
										
										for(var z= 0;z<excelData.length;z++){
										
									if(excelData[z].BuyCustomer!=undefined){
										excelData[z].BuyCustomer = excelData[z].BuyCustomer.trim();
									}
									if(excelData[z].StoreName!=undefined){
										excelData[z].StoreName = excelData[z].StoreName.trim();
									}
									if(excelData[z].PickupDate!=undefined){
										excelData[z].PickupDate = excelData[z].PickupDate.trim();
									}
									if(excelData[z].SellCustomer!=undefined){
										excelData[z].SellCustomer = excelData[z].SellCustomer.trim();
									}
									if(excelData[z].SellCustomerLocation!=undefined){
										excelData[z].SellCustomerLocation = excelData[z].SellCustomerLocation.trim();
									}
									if(excelData[z].DeliveryDate!=undefined){
										excelData[z].DeliveryDate = excelData[z].DeliveryDate.trim();
									}
									if(excelData[z].ReleaseNumber!=undefined){
										excelData[z].ReleaseNumber = excelData[z].ReleaseNumber.trim();
									}
									if(excelData[z].DestinationGross!=undefined){
										excelData[z].DestinationGross = excelData[z].DestinationGross.trim();
									}
									if(excelData[z].DestinationTare!=undefined){
										excelData[z].DestinationTare = excelData[z].DestinationTare.trim();
									}
									if(excelData[z].MaterialProfile!=undefined){
										excelData[z].MaterialProfile = excelData[z].MaterialProfile.trim();
									}
									if(excelData[z].BalesPicked!=undefined){
										excelData[z].BalesPicked = excelData[z].BalesPicked.trim();
									}
									if(excelData[z].BalesRemaining!=undefined){
										excelData[z].BalesRemaining = excelData[z].BalesRemaining.trim();
									}
									if(excelData[z]["BOL/TicketNo"]!=undefined){
										excelData[z]["BOL/TicketNo"] = excelData[z]["BOL/TicketNo"].trim();
									}
									if(excelData[z].SupplierName!=undefined){
										excelData[z].SupplierName = excelData[z].SupplierName.trim();
									}
									if(excelData[z].DriverName!=undefined){
										excelData[z].DriverName = excelData[z].DriverName.trim();
									}
									if(excelData[z].Incident!=undefined){
										excelData[z].Incident = excelData[z].Incident.trim();
									}
									if(excelData[z].Comments!=undefined){
										excelData[z].Comments = excelData[z].Comments.trim();
									}
									
									
									}
								
										excelData= excelData.slice(1);
										
									}
								
									var uniqueStandards=[];
						uniqueStandards = arrUnique(excelData);
						
								
							if(uniqueStandards==null){
								console.log("before trimimg"+ JSON.stringify(excelData));
								if (excelData.length >= 1) {
									
									$scope.saveData(excelData);										
								} else {
									$scope.loaderActiveBalePickup = false;
									$scope.uploadExcelMessage.push("No data found in excel");
									$scope.errorOnExcel=true;	
									document.getElementById("uploadCaptureInputFile").value = "";
									$scope.selectedFileForUpload=null;
								}
							}else{
									$scope.loaderActiveBalePickup = false;
									var record1=uniqueStandards[0];
									record1=record1+3;
									var record2=uniqueStandards[1]
									record2=record2+3
									
									$scope.uploadExcelMessage.push("Row Number "+record2+" and Row Number"+record1+" are duplicate. Please remove the duplicate record and upload again. ");
									$scope.errorOnExcel=true;
									document.getElementById("uploadCaptureInputFile").value = "";
									$scope.selectedFileForUpload=null;									
									}
							}else{
									$scope.uploadExcelMessage.push("Excel headers are not correct. Use sample excel for reference ! ");
									$scope.errorOnExcel=true;
									document.getElementById("uploadCaptureInputFile").value = "";
									$scope.selectedFileForUpload=null;
									$scope.loaderActiveBalePickup = false;
								}			
							}
							}
							reader.onerror = function(ex) {
								console.log(ex);
							}

							reader.readAsArrayBuffer(file);
					
						}else{
							$scope.loaderActiveBalePickup = false;
									$scope.uploadExcelMessage.push("Please upload excel in .xlsx");
									$scope.errorOnExcel=true;
									document.getElementById("uploadCaptureInputFile").value = "";
									$scope.selectedFileForUpload=null;
						}
						
					}
						function arrUnique(arr) {
						
								var cleaned = [];
								var BreakException = {};
								try {
									for(var i=0;i<arr.length;i++){
									var unique = true;
										for(var j=0;j<i;j++){
									
											if (_.isEqual(arr[i], arr[j])){ 
										unique = false;
										cleaned[0]=i;
										cleaned[1]=j;
										throw BreakException;
										}
									}
								}
								return null;
							}catch (e) {
							  if (e == BreakException) 
								console.log("cleaned ----------->  "+cleaned);
								  return cleaned;
							}
						}
						
						
						
					$scope.saveData = function(excelData) {
						$scope.errorOnExcel=false;
						var pickUpList = {};
						
							
						
						$scope.uploadExcelMessage=[];
						pickUpList.picupExcelList = excelData;
						//console.log("pickUpList.picupExcelList"+JSON.stringify(pickUpList.picupExcelList));
						
					
						
						for(var k=0;k<pickUpList.picupExcelList.length;k++){ 
						
						
								//console.log(pickUpList.picupExcelList[k].PickupDate);
						
							if(pickUpList.picupExcelList[k].SupplierName!=undefined && pickUpList.picupExcelList[k].BuyCustomer!=undefined && 
								pickUpList.picupExcelList[k].StoreName!= undefined && pickUpList.picupExcelList[k].PickupDate!=undefined && 
								
								pickUpList.picupExcelList[k].MaterialProfile!=undefined && pickUpList.picupExcelList[k].DriverName!=undefined &&
								pickUpList.picupExcelList[k].BalesPicked!=undefined && pickUpList.picupExcelList[k].BalesRemaining!=undefined){
									
									
										
									
								pickUpList.picupExcelList[k].Bol=pickUpList.picupExcelList[k]["BOL/TicketNo"];
									if(pickUpList.picupExcelList[k].DeliveryDate!= undefined ){
										
										if(new Date(pickUpList.picupExcelList[k].DeliveryDate)< new Date(pickUpList.picupExcelList[k].PickupDate)){
											
											
											$scope.errorOnExcel=true;
									$scope.uploadExcelMessage.push(" Delivery Date should be greater than Pickup Date. ");
										
										}
									}
								
								if(pickUpList.picupExcelList[k]["BOL/TicketNo"]!=undefined){
									
								  var n = Math.floor(Number(pickUpList.picupExcelList[k]["BOL/TicketNo"]));
									 var btic = new RegExp("^[0-9]*$");
								  	if(!( String(n) === pickUpList.picupExcelList[k]["BOL/TicketNo"] && n >= 0) && (!(btic.test(pickUpList.picupExcelList[k].Bol)))){
										
									$scope.errorOnExcel=true;
									$scope.uploadExcelMessage.push(" BOL/TicketNo should be positive number. ");
									
								}
								if(((String(n)).length)>8){
									$scope.errorOnExcel=true;
									$scope.uploadExcelMessage.push(" Maxixmum BOL/TicketNo length is 8. ");
								}
								}
								
								if(pickUpList.picupExcelList[k].DestinationGross!=undefined){
									  var n = Math.floor(Number(pickUpList.picupExcelList[k].DestinationGross));
									  
									  
								if(!( String(n) === pickUpList.picupExcelList[k].DestinationGross && n >= 0)){
									
									
										$scope.errorOnExcel=true;
										$scope.uploadExcelMessage.push(" DestinationGross should be positive number. ");
										
									}
									if(((String(n)).length)>8){
									$scope.errorOnExcel=true;
									$scope.uploadExcelMessage.push(" Maxixmum DestinationGross length is 8. ");
								}
									}
							
								if(pickUpList.picupExcelList[k].DestinationTare!=undefined){
									  var n = Math.floor(Number(pickUpList.picupExcelList[k].DestinationTare));
									  
									 
								if(!( String(n) === pickUpList.picupExcelList[k].DestinationTare && n >= 0)){
									
									
										$scope.errorOnExcel=true;
										$scope.uploadExcelMessage.push(" DestinationTare should be positive number. ");
										
									}
									if(((String(n)).length)>8){
									$scope.errorOnExcel=true;
									$scope.uploadExcelMessage.push(" Maxixmum DestinationTare length is 8. ");
								}
									}	
								
								if((pickUpList.picupExcelList[k].SellCustomer!=undefined && pickUpList.picupExcelList[k].SellCustomer.toUpperCase()==='UNKNOWN')
									||(pickUpList.picupExcelList[k].SellCustomerLocation!=undefined &&pickUpList.picupExcelList[k].SellCustomerLocation.toUpperCase()==='UNKNOWN')){ 
								
									$scope.errorOnExcel=true;
									$scope.uploadExcelMessage.push(" 'Unknown' is not allowed in Sell Customer and Sell Customer Site fields. ");
								}
								
								if(pickUpList.picupExcelList[k].ReleaseNumber!=undefined){
									var n = Math.floor(Number(pickUpList.picupExcelList[k].ReleaseNumber));
									var re = new RegExp("^[A-Za-z0-9-]*$");
									
									if (!(re.test(pickUpList.picupExcelList[k].ReleaseNumber)) || ((String(n)).length)==0 || pickUpList.picupExcelList[k].ReleaseNumber=="") {
										
									    $scope.errorOnExcel=true;
										$scope.uploadExcelMessage.push(" ReleaseNumber should contains Alphabets Numbers and Hypens only without spaces. ");
										
									}
									if(((String(n)).length)>20){
									
									$scope.errorOnExcel=true;
									$scope.uploadExcelMessage.push(" Maxixmum ReleaseNumber length is 20. ");
								}
										
									
									}	
									
					if((Number(pickUpList.picupExcelList[k].BalesRemaining)>-1) && (Number(pickUpList.picupExcelList[k].BalesPicked) > -1)) {
						
						if(((pickUpList.picupExcelList[k].BalesRemaining.length)>4) || ((pickUpList.picupExcelList[k].BalesPicked.length) > 4)) {
							$scope.uploadExcelMessage.push(" Bales Picked and Bales Remaining should be positive max four digit number.");
									$scope.errorOnExcel=true;
						}
						
						if(pickUpList.picupExcelList[k].BalesPicked >0 && (pickUpList.picupExcelList[k]["BOL/TicketNo"]==undefined || (Number(pickUpList.picupExcelList[k]["BOL/TicketNo"]) ==0))){
							$scope.errorOnExcel=true;
							$scope.uploadExcelMessage.push(" BOL/TicketNo is mandatory if bales picked is greater than zero. ");
						}
						
					}else{
									$scope.uploadExcelMessage.push(" Bales Picked and Bales Remaining should be positive number.");
									$scope.errorOnExcel=true;
								}
					
					
					if(pickUpList.picupExcelList[k].Comments!=undefined && pickUpList.picupExcelList[k].Comments.length>140){
						
						$scope.uploadExcelMessage.push("Comments max length is 140 characters.");
									$scope.errorOnExcel=true;
								}
					
				
					
					if(pickUpList.picupExcelList[k].SellCustomer!=undefined || pickUpList.picupExcelList[k].SellCustomerLocation!=undefined ||
							pickUpList.picupExcelList[k].DeliveryDate!= undefined || pickUpList.picupExcelList[k].ReleaseNumber!=undefined ||
							pickUpList.picupExcelList[k].DestinationGross!=undefined || pickUpList.picupExcelList[k].DestinationTare!=undefined){
						var emptyFieldsArray = [];
						var lineNumberArray=[];
						var counter =0;
						
					if(pickUpList.picupExcelList[k].SellCustomer==undefined){ 
						emptyFieldsArray[counter]="Sell Customer";
						lineNumberArray[counter]=Number(k)+3;
						
						counter++;
					}
					if(pickUpList.picupExcelList[k].SellCustomerLocation== undefined ){
						emptyFieldsArray[counter]="Sell Customer Location";
						lineNumberArray[counter]=Number(k)+3;
						counter++;
					}		
					if(pickUpList.picupExcelList[k].DeliveryDate==undefined){
						emptyFieldsArray[counter]="Delivery Date";
						lineNumberArray[counter]=Number(k)+3;
						counter++;
					}	
					if(pickUpList.picupExcelList[k].ReleaseNumber==undefined){
						emptyFieldsArray[counter] ="Release Number";
						lineNumberArray[counter]=Number(k)+3;
						counter++;
					}
					if(pickUpList.picupExcelList[k].DestinationGross==undefined){
						emptyFieldsArray[counter] ="Destination Gross";
						lineNumberArray[counter]=Number(k)+3;
						counter++;
					}
					if(pickUpList.picupExcelList[k].DestinationTare==undefined){
						emptyFieldsArray[counter] ="Destination Tare";
						lineNumberArray[counter]=Number(k)+3;
						counter++;
					}								
					
					
					
					
					for(var i=0;i<counter;i++){
					$scope.uploadExcelMessage.push("Please enter "+emptyFieldsArray[i]+".* Excel Line Number "+lineNumberArray[i]);
							$scope.errorOnExcel=true;
				
				}
						
						
					}else{
						
					}		
							}else{
									var emptyFields = [];
									var counter =0;
									var lineNumber=[];
									
								if(pickUpList.picupExcelList[k].BuyCustomer==undefined){ 
									emptyFields[counter]="BuyCustomer";
									lineNumber[counter]=Number(k)+3;
									counter++;
									
								}
								if(pickUpList.picupExcelList[k].StoreName== undefined ){
									emptyFields[counter]="StoreName";
									lineNumber[counter]=Number(k)+3;
									counter++;
								}		
								if(pickUpList.picupExcelList[k].PickupDate==undefined){
									emptyFields[counter]="PickupDate";
									lineNumber[counter]=Number(k)+3;
									counter++;
								}	
								if(pickUpList.picupExcelList[k].MaterialProfile==undefined){
									emptyFields[counter] ="MaterialProfile";
									lineNumber[counter]=Number(k)+3;
									counter++;
								}
								if(pickUpList.picupExcelList[k].BalesPicked==undefined){
									emptyFields[counter] ="BalesPicked";
									lineNumber[counter]=Number(k)+3;
									counter++;
								}
								if(pickUpList.picupExcelList[k].BalesRemaining==undefined){
									emptyFields[counter] ="BalesRemaining";
									lineNumber[counter]=Number(k)+3;
									counter++;
								}								
								if(pickUpList.picupExcelList[k].SupplierName==undefined){
									emptyFields[counter] ="SupplierName";
									lineNumber[counter]=Number(k)+3;
									counter++;
								}
								if(pickUpList.picupExcelList[k].DriverName==undefined){
									emptyFields[counter] ="DriverName";
									lineNumber[counter]=Number(k)+3;
									counter++;
								}
								
								
								for(var i=0;i<counter;i++){
								$scope.uploadExcelMessage.push(emptyFields[i]+" is mandatory field.* Excel Line Number "+lineNumber[i]);
										$scope.errorOnExcel=true;
							
							}
						}
							
							if($rootScope.user.role =='Supplier'){
							if(pickUpList.picupExcelList[k].SupplierName != currentExcelUploadsupplier){
								$scope.uploadExcelMessage.push("Cannot Upload Excel for Another Supplier");
								$scope.errorOnExcel=true;
							}
							}
						}
							if(!$scope.errorOnExcel){
												balepickupService.pickupUploadFromExcel(pickUpList).then(function(response) {
											$scope.uploadExcelMessage.push(response.data.message);
											$scope.getBalePickups($scope.buyCustomerSelectedBP,$scope.supplierSelectedBP, $scope.startDate,$scope.endDate);
											document.getElementById("uploadCaptureInputFile").value = "";
							
						$scope.selectedFileForUpload=false;	
						$scope.loaderActiveBalePickup = false;	
											
										//	console.log("$scope.uploadExcelMessage"+JSON.stringify($scope.uploadExcelMessage));
								});
											
												

										
							
						}else{
							document.getElementById("uploadCaptureInputFile").value = "";
							$scope.loaderActiveBalePickup = false;			
							$scope.selectedFileForUpload=false;		
						}
					}

					
				
	  $scope.exportData = function () {
		  
		  balepickupService.downloadSampleExcel()
								.then(function(response) {
									console.log("===")
								});

		  
		  /*

	
		
            var workbook = new $.ig.excel.Workbook($.ig.excel.WorkbookFormat.excel2007);
            var sheet = workbook.worksheets().add('BalePickupSample');
			
			sheet.rows(0).cellFormat().font().bold(true);
			sheet.rows(1).cellFormat().fill($.ig.excel.CellFill.createSolidFill('#DCDCDC'));
			
		
			// Populate the sheet with data
            sheet.getCell('A1').value('BuyCustomer');
            sheet.getCell('B1').value('StoreName');
            sheet.getCell('C1').value('PickupDate');
            sheet.getCell('D1').value('SellCustomer');
            sheet.getCell('E1').value('SellCustomerLocation');
			 sheet.getCell('F1').value('DeliveryDate');
            sheet.getCell('G1').value('ReleaseNumber');
            sheet.getCell('H1').value('DestinationGross');
            sheet.getCell('I1').value('DestinationTare');
            sheet.getCell('J1').value('MaterialProfile');
			sheet.getCell('K1').value('BalesPicked');
			sheet.getCell('L1').value('BalesRemaining');
			sheet.getCell('M1').value('BOL/TicketNo');
			sheet.getCell('N1').value('SupplierName');
			sheet.getCell('O1').value('DriverName');
			sheet.getCell('P1').value('Incident');
			sheet.getCell('Q1').value('Comments');
			
			// Populate the sheet with data
			
            sheet.getCell('A2').value('ACME INDUSTRIES');
            sheet.getCell('B2').value('ACM1234');
            sheet.getCell('C2').value('02/28/2018');
            sheet.getCell('D2').value('WASTE MANAGEMENT INC.');
            sheet.getCell('E2').value('HOUSTON');
			 sheet.getCell('F2').value('02/28/2018');
            sheet.getCell('G2').value(12345678);
            sheet.getCell('H2').value(80000);
            sheet.getCell('I2').value(40000);
            sheet.getCell('J2').value('OCC - BALED');
			sheet.getCell('K2').value(40);
			sheet.getCell('L2').value(1);
			sheet.getCell('M2').value(98765432);
			sheet.getCell('N2').value('XYZ LOGISTICS');
			sheet.getCell('O2').value('JOHN DOE');
			sheet.getCell('P2').value('BROKEN BALES');
			sheet.getCell('Q2').value('NONE');
			
            // Save the workbook
            $scope.saveWorkbook(workbook, "BalePickupSample.xlsx");
			*/
        
    };
	
	
	 $scope.saveWorkbook=function(workbook, name) {
            workbook.save({ type: 'blob' }, function (data) {
                saveAs(data, name);
            }, function (error) {
                console.log('Error exporting: : ' + error);
            });
        }
		
		
	 $scope.items = [{
		BuyCustomer: "ACME INDUSTRIES",
        StoreName: "ACM1234",
        PickupDate: "2/28/2018",
		SellCustomer: "WASTE MANAGEMENT INC.",
		SellCustomerLocation: "HOUSTON",
		DeliveryDate: "2/28/2018",
		ReleaseNumber: 12345678,
		DestinationGross: 80000,
		DestinationTare:40000,
		MaterialProfile: "OCC - BALED",
		BalesPicked: 40,
		BalesRemaining: 1,
		Bol: 98765432,
		SupplierName: "XYZ LOGISTICS",
		DriverName: "JOHN DOE",
		Incident: "BROKEN BALES",
		Comments: "NONE"
    }];
	
	
					


				
					$scope.openLightboxModal = function(tripId) {
					//console.log("tripId"+tripId);
						$scope.loaderBalePickup = true;
					
						$scope.images = [];
						var text = "";
						var possible = "123456789123456789123456789";
						for (var i = 0; i < 6; i++){
							text += possible.charAt(Math.floor(Math.random() * possible.length));
						}
						
						
						
						
						var newTripId=text+tripId;
						//console.log("===newTripId="+newTripId);
						balepickupService.getBalePickUpImages(newTripId).then(function(response) {
											for (var i = 0; i < response.data.length; i++) {
												
												var urlObject = {
													'url' : 'data:image/gif;base64,'+ response.data[i]
												}

												$scope.images.push(urlObject);
											}
											if ($scope.images.length > 0) {
											
												Lightbox.openModal($scope.images, 0);
												$scope.loaderBalePickup = false;
											} else {
											
												$scope.loaderBalePickup = false;
											}
										});

					};
					
					$scope.addDriverPickupDataClose = function(){					
					$scope.addDriverPickupData = false;
					}
					$scope.resDtOptionsPickupSummary=DTOptionsBuilder.newOptions().withPaginationType('full_numbers')
					.withDisplayLength(10)
					.withOption('order', [])
					.withOption('scrollX', true)
					 .withOption('bScrollCollapse', true)
					 .withOption('bAutoWidth', true)
							 
					.withOption('bFilter', true)
					
					.withOption('responsive', false);
					

					if(window.innerWidth >= 1545) {
						$scope.resDtColumnsPickupSummary = [DTColumnBuilder.newColumn(0),
									DTColumnBuilder.newColumn(1),
									DTColumnBuilder.newColumn(2),
									DTColumnBuilder.newColumn(3),
									DTColumnBuilder.newColumn(4),
									DTColumnBuilder.newColumn(5),
									DTColumnBuilder.newColumn(6),
									DTColumnBuilder.newColumn(7),
									DTColumnBuilder.newColumn(8),
									DTColumnBuilder.newColumn(9),
									DTColumnBuilder.newColumn(10),
									DTColumnBuilder.newColumn(11),
									DTColumnBuilder.newColumn(12),
									DTColumnBuilder.newColumn(13),
									DTColumnBuilder.newColumn(14).notSortable(),
									DTColumnBuilder.newColumn(15).notSortable()
									];
						}
						$scope.pendingStoreReportDataTableOptions=DTOptionsBuilder.newOptions().withPaginationType('full_numbers')
					.withDisplayLength(10)
					.withOption('order', [])
					.withOption('"bautoWidth": false')
					//.withOption('tabIndex', '-1')
					.withOption('bFilter', true)
					

					.withOption('responsive', false);
					

					if(window.innerWidth >= 1545) {
						$scope.pendingStoreReportDataTableColumns = [DTColumnBuilder.newColumn(0),
									DTColumnBuilder.newColumn(1),
									DTColumnBuilder.newColumn(2),
									DTColumnBuilder.newColumn(3),
									DTColumnBuilder.newColumn(4),									
									DTColumnBuilder.newColumn(5).notSortable()
									];
						}
						
					
					
					//$(".refreshTooltip").tooltip({  });
					//$(".pdfTooltip").tooltip({  });
					$interval( function(){
						$('#balePickupSummaryDetails_filter input').prop('title','Search By Buy Customer, Store Name, Sell Customer, Sell Customer Location , Material Profile, Bales Picked, Bales Remaining,Supplier Name,Driver Name,Incident,Comments');
						$('#pendingStoreReportDataTable_filter input').prop('title','Search By Buy Customer, Store Name, Supplier Name, Frequency');
						
						$('[data-toggle="tooltip"]').tooltip(); 
						
						$('[data-toggle="tooltip"]').on('shown.bs.tooltip', function () {
							  $timeout(function(){
								$('[data-toggle="tooltip"]').tooltip('hide')
								},3000)
							})
						},1000);
						// commented by prema
				// $interval( function(){
						// $('#pendingStoreReportDataTable_filter input').prop('title','Search By Buy Customer, Store Name, Supplier Name, Frequency');},1000);
	
					
					$scope.resetPendingReportFilters= function(){
						
						$scope.suppliersForCustomerSelected = $scope.suppliersForCustomerSelectedOriginal ;
						if($rootScope.user.role =='Supplier'){
							$scope.buyCustomerSelectedPR=undefined;
							$scope.balePickupLists =[];
							$scope.jsonForPendingReportExport=[];
							$scope.exportDatay=[];
							$scope.checkSubmitBtn();
							$scope.authorizedPR=false;
							
						}else{
							$scope.buyCustomerSelectedPR=undefined;
							$scope.supplierSelectedPR=undefined;
							$scope.buyCustomers= $rootScope.buyCustomers;
							$scope.balePickupLists =[];
							$scope.jsonForPendingReportExport=[];
							$scope.exportDatay =[];
							$scope.checkSubmitBtn();
							$scope.authorizedPR=false;
							
					}
				}
	$scope.getPendingBalePickups = function(buyCustomerId,supplierId){
	
	$scope.loaderPendingReport = true;
		var balePickupLists={};
    	var PendingStoreReportDTO = {};
		if(buyCustomerId!=null && buyCustomerId!=undefined && buyCustomerId!=""){
			var buyCustomerId =JSON.parse(buyCustomerId);
    	PendingStoreReportDTO.buyCustomerId = buyCustomerId.customerId;}
		if(supplierId!=null && supplierId!=undefined && supplierId!="" ){
			var supplierId =JSON.parse(supplierId);
    	PendingStoreReportDTO.supplierId = supplierId.supplierId;}
		
		console.log("PendingStoreReportDTO"+JSON.stringify(PendingStoreReportDTO));
    	reportService.getPendingPickups(PendingStoreReportDTO).then(function(response){
			
		
    	$scope.balePickupLists = response.data;
		
		
		
    	
		
	$scope.jsonForPendingReportExport=[];
	$scope.fileName = "Pending Store";
	$scope.exportDatay = [];
					
						$scope.exportDatay.push(["Buy Customer", "StoreName", "Frequency","Supplier Name", "Last Pickup Date"]);
						
					for(var i=0;i<$scope.balePickupLists.length;i++){
				
						if($scope.balePickupLists[i].lastPickupDate != undefined){
							$scope.balePickupLists[i].lastPickupDate=$filter('date')($scope.balePickupLists[i].lastPickupDate, 'MM/dd/yyyy');
							
						}else{
							$scope.balePickupLists[i].lastPickupDate='';
						}				
							
									
							
							$scope.balePickupLists[i].frequencyC =$scope.balePickupLists[i].frequency +"("+$scope.balePickupLists[i].frequencyDay+")";
						
						$scope.jsonForPendingReportExport.push({
							"Buy Customer":$scope.balePickupLists[i].customerName,
							"StoreName":$scope.balePickupLists[i].siteName,
							"Frequency":$scope.balePickupLists[i].frequencyC,
							"Supplier Name":$scope.balePickupLists[i].supplierName,
							"Pickup Date":$scope.balePickupLists[i].lastPickupDate	
						});
						
						$scope.exportDatay.push([$scope.balePickupLists[i].customerName, $scope.balePickupLists[i].siteName, $scope.balePickupLists[i].frequencyC,$scope.balePickupLists[i].supplierName, $scope.balePickupLists[i].lastPickupDate]);
						
						 $scope.jsonForPendingReportExport = $filter('orderBy')(
						 $scope.jsonForPendingReportExport, 'StoreName');
					}	 
						 $scope.authorizedPR=true;						
							
							 $scope.dtColumnsPR = [
										   DTColumnBuilder.newColumn('customerName').withTitle('Buy Customer'),
										   DTColumnBuilder.newColumn('siteName').withTitle('Store Name'),
										   DTColumnBuilder.newColumn('frequencyC').withTitle('Frequency'),
										   DTColumnBuilder.newColumn('supplierName').withTitle('Supplier Name'),
										   DTColumnBuilder.newColumn('lastPickupDate').withOption('sType','date').withTitle('Last Pickup Date'),
										    DTColumnBuilder.newColumn('').withTitle('Resolve').notSortable().renderWith(function(data, type, full, meta) {
									var htmlTemp='<div></div>';
												
										htmlTemp='<div style="width: 20px; padding-top: 0px !important; padding-bottom: 0px !important">'+
												'<img src="/brta/app/ui/resources/images/editdata.png"'+ 
									'ng-click="editPendingReportDetailsOpen('+full.customerSiteId+');assignTabIndexBalePickup();"'+
									'class="editImageAlignment" style="height: 40px;margin-top:-10px;margin-bottom:-10px"></div>';
									
									return htmlTemp;	
										})
										  
									   ];  
									  
										 
										 $scope.dtOptionsPR= DTOptionsBuilder.newOptions().withOption('data', $scope.balePickupLists).withOption('createdRow', function (row, data, dataIndex) {
								
									$compile(angular.element(row).contents())($scope);
								}).withOption('headerCallback', function( thead, data, start, end, display ) {
									$compile(angular.element(thead).contents())($scope);
								});
		
		$scope.loaderPendingReport = false;
		});
    };
	
			
$scope.downloadPendingStorePdf = function() {	
var data = [];		

if($scope.jsonForPendingReportExport !=undefined && $scope.jsonForPendingReportExport.length >0){

data=$scope.jsonForPendingReportExport;		
	
	var doc = new jsPDF('p', 'pt');
	 doc.setFontSize(13);
	doc.autoTable(headerTablePendingReport, data, {	
	
 
  margin: { top: 50, left: 20, right: 20, bottom: 0 },
  styles : {
		overflow : 'linebreak'
	},
  
   drawHeaderCell: function (cell, data) {
	    if (cell.raw === 'ID') {
	        cell.styles.fontSize= 15;
	       cell.styles.textColor = [255,0,0];
	    } else {
	        cell.styles.textColor = 255;
	        cell.styles.fontSize = 10;

	    }
	},
	   createdCell: function (cell, data) {
	    if (cell.raw === 'Jack') {
	       cell.styles.fontSize= 15;
	       cell.styles.textColor = [255,0,0];
	    } 
	}

});		
	doc.save('Pending Store.pdf');
}		
}
	
	
	
	
	var headerTablePendingReport = [		
                     { title: "BUY CUSTOMER", dataKey: "Buy Customer" },		
                     { title: "STORE NAME", dataKey: "StoreName" },		
					 { title: "FREQUENCY", dataKey: "Frequency" },		
                     { title: "SUPPLIER NAME", dataKey: "Supplier Name" },	
                     { title: "LAST PICKUP DATE", dataKey: "Pickup Date" }
                ];
				
				
	$scope.getPickupDates=function(supplierId,buyCustomerSiteId){
		
		var pendingReportDTO={};
		pendingReportDTO.supplierId=supplierId;
		pendingReportDTO.buyCustomerSiteId=buyCustomerSiteId;
		

		reportService.getAllBalePickupDates(pendingReportDTO).then(function(response){
			if(response.data.length >0){
							$scope.pendingReportDetails=response.data;
							
							for (var i = 0; i < $scope.pendingReportDetails.length; i++) {									
								var dateformatconversion = $scope.pendingReportDetails[i].pickupDate;
								function formatDate(date) {
									var d = new Date(date),
										month = '' + (d.getMonth() + 1),
										day = '' + d.getDate(),
										year = d.getFullYear();

									if (month.length < 2) month = '0' + month;
									if (day.length < 2) day = '0' + day;
									return [month,day,year].join('/');
								}
								
								$scope.pendingReportDetails[i].pickupDate = formatDate(dateformatconversion);
							}
			}else{
				$scope.noPickupDetailsFound=true;
				$scope.noPickupDetailsFoundErrorMessege=true;// added by prema
				$scope.pendingResolutionPopUp.pendingDetails=null;
				$scope.pendingReportDetails=null; 
			}
	
		})
		
		
	}		


$scope.editPendingReportDetailsOpen=function(pendingReportId){
		var pendingReportObject = $filter('filter')($scope.balePickupLists, function (d) {return d.customerSiteId === pendingReportId;})[0];
		$scope.editPendingReportDetails(pendingReportObject);
		$scope.noPickupDetailsFound=true;
}

//added by prema
$scope.pendingReportPickupdate= function(){
			$scope.noPickupDetailsFound=false;
			$scope.noPickupDetailsFoundErrorMessege=false;
		}
//ended by prema
				
	$scope.editPendingReportDetails = function(pendingReport){

		$scope.noPickupDetailsFound=false;
		$scope.noPickupDetailsFoundErrorMessege=false;// added by prema
		
		$scope.pendingResolutionPopUp={};
		$scope.pendingResolutionPopUp.frequency=pendingReport.frequency;
		$scope.pendingResolutionPopUp.frequencyDay=pendingReport.frequencyDay;
		for(var i=0;i<$scope.suppliersForCustomerSelected.length; i++){
			if($scope.suppliersForCustomerSelected[i].description == pendingReport.supplierName){
				$scope.pendingResolutionPopUp.supplierSelected=$scope.suppliersForCustomerSelected[i];
				$scope.getPickupDates($scope.suppliersForCustomerSelected[i].supplierId, pendingReport.customerSiteId);
				break;
			}
		}
		
		$scope.pendingResolutionAction=true;
	}
	
	$scope.pendingResolutionActionClose = function(){
		$scope.pendingResolutionPopUp.pendingDetails=null;
				$scope.pendingResolutionAction=false;
	}
	
	$scope.checkSubmitBtn=function(){
		if($rootScope.user.role !='Supplier'){
		if(($scope.buyCustomerSelectedPR !=undefined && $scope.buyCustomerSelectedPR!="" && $scope.buyCustomerSelectedPR!=null) || 
				($scope.supplierSelectedPR  !=undefined && $scope.supplierSelectedPR!="" && $scope.supplierSelectedPR!=null)){
			
			$scope.disableSubmitBtnPR=false;
		}else{
			
			$scope.disableSubmitBtnPR=true;
		}
		}
		}
		
	
  
    $scope.startDateOptionsBalePickup = {
  };
    
    
    $scope.dateValidation=function(){
    	
    	if($scope.startDate!=undefined && $scope.endDate!=undefined){
    		if($scope.startDate.getTime() > $scope.endDate.getTime() ){
    			$scope.endDate=$scope.startDate;
    		
    		}else{
    			$scope.endDateErrorMessage=false;
    		}
    	}
    }	
    
    
    
    
  
   $scope.$watch("startDate", function(newStart) {

     $scope.endDateOptionsBalePickup = {
      minDate: new Date($scope.startDate)
   }
    
      if (newStart > $scope.endDate) {
        $scope.endDate = newStart;
      }
 }) ;	
 
 
 $scope.savePendingResolutionDetails=function(){
	
	 $scope.loaderPendingReport = true;
	 $scope.saveValidate=false;
	 if($scope.pendingResolutionPopUp != undefined){
		 
		 if($scope.pendingResolutionPopUp.supplierSelected == undefined || $scope.pendingResolutionPopUp.supplierSelected == "" || $scope.pendingResolutionPopUp.supplierSelected == null){
			 $scope.pendingResolutionPopUp.supplierSelectedError="Please select Supplier"
			 $scope.saveValidate=true;
		 }
		 if($scope.pendingResolutionPopUp.pendingDetails == undefined || $scope.pendingResolutionPopUp.pendingDetails == "" || $scope.pendingResolutionPopUp.pendingDetails == null){
			 $scope.pendingResolutionPopUp.pendingDetailsErrorMessage="Please select Pickup Date"
			 $scope.saveValidate=true;
		 }
		 
		  
		 if(!$scope.saveValidate){
			 
			 var savePendingReport={}
			 savePendingReport.supplierId=$scope.pendingResolutionPopUp.supplierSelected.supplierId;
			 savePendingReport.materialId=$scope.pendingResolutionPopUp.pendingDetails.materialId;
			 savePendingReport.tripId=$scope.pendingResolutionPopUp.pendingDetails.tripId;	
			 savePendingReport.pickupDate=$scope.pendingResolutionPopUp.pendingDetails.pickupDate;	
			 savePendingReport.buyCustomerSiteId=$scope.pendingResolutionPopUp.pendingDetails.buyCustomerSiteId;
			 savePendingReport.driverId=$scope.pendingResolutionPopUp.pendingDetails.driverId;
			 savePendingReport.frequency=$scope.pendingResolutionPopUp.frequency
			 savePendingReport.frequencyDay=$scope.pendingResolutionPopUp.frequencyDay
			 savePendingReport.comment=$scope.pendingResolutionPopUp.comment;

			 reportService.getSavePendingReport(savePendingReport).then(function(response){
				 $scope.getPendingBalePickups($scope.buyCustomerSelectedPR,$scope.supplierSelectedPR);
				 $scope.pendingResolutionAction=false;
				 $scope.loaderPendingReport = false;
			 });
			 
		 }
		 
		 
	 }else{ 
		 $scope.disableSavePendingReportBtn=true;
	 }
	 
	
	 
 }
 
 
 
 
 
 // validate balepickup date && delivery date
 
 $scope.dateValidationBalePickupPopup=function(){
	if($scope.editPickupData.startDateBalePickup!=undefined && $scope.editPickupData.deliveryDate!=undefined){
		if($scope.editPickupData.startDateBalePickup.getTime() > $scope.editPickupData.deliveryDate.getTime() ){
			
			$scope.editPickupData.deliveryDate=$scope.editPickupData.startDateBalePickup;
		//	$scope.endDateErrorMessage=true;
		
		}else{
			//$scope.endDateErrorMessage=false;
		}
	}
 }
 
 
//==========================timeout===========================

      function closeModals() {
	
        if ($scope.warning) {
          $scope.warning.close();
          $scope.warning = null;
        }

        if ($scope.timedout) {
          $scope.timedout.close();
          $scope.timedout = null;
        }
      }

      $scope.$on('IdleStart', function() {
		  
	
        closeModals();

        $scope.warning = $uibModal.open({
          templateUrl: 'warning-dialog.html',
          windowClass: 'modal-danger',
		  backdrop: 'static'
        });
      });

      $scope.$on('IdleEnd', function() {
	
        closeModals();
      });

      $scope.$on('IdleTimeout', function() {
	
         closeModals();
        $scope.timedout = $uibModal.open({
          templateUrl: 'timedout-dialog.html',
          windowClass: 'modal-danger',
		  backdrop: 'static',
		  keyboard: false,
		  scope: $scope
        });
      });

      $scope.start = function() {
	
        closeModals();
        Idle.watch();
        $scope.started = true;
      };

      $scope.stop = function() {
	
        closeModals();
        Idle.unwatch();
        $scope.started = false;

      };
$(document).ready(function(){
						$('[data-toggle="tooltip"]').tooltip();   
					});
					$('[data-toggle="tooltip"]').on('shown.bs.tooltip', function () {
					  $timeout(function(){
						$('[data-toggle="tooltip"]').tooltip('hide')
						},3000)
					})
//commented by prema
	// $interval(function() {
		// var downloadExcelTooltip = angular.element( document.querySelector( "[title='Download Excel']" ) ).tooltip();
							
	                // },1000);			
				
				
			  $interval(function() {

				 
				var balePickupSummaryDetails_filterTabIndex = angular.element( document.querySelectorAll( "#balePickupSummaryDetails_filter input" ) );
					balePickupSummaryDetails_filterTabIndex.prop('tabindex','-1');
							
				var sortingTab = angular.element( document.querySelectorAll( ".sorting" ) );
					sortingTab.prop('tabindex','-1');
				var sorting_ascTab = angular.element( document.querySelectorAll( ".sorting_asc" ) );
					sorting_ascTab.prop('tabindex','-1');
				var pendingStoreReportDataTable_filterTabIndex = angular.element( document.querySelectorAll( "#pendingStoreReportDataTable_filter input" ) );
					pendingStoreReportDataTable_filterTabIndex.prop('tabindex','-1');

		  },1000);
      
      
							
							
							$scope.addTabIndex = function (){
							$timeout(function(){
								var balePickupSummaryDetails_lengthTabindex = angular.element( document.querySelector( "[name='balePickupSummaryDetails_length']" ) );
								balePickupSummaryDetails_lengthTabindex.prop('tabindex','0');
								
								var pendingStoreReportDataTable_lengthTabindex = angular.element( document.querySelector( "[name='pendingStoreReportDataTable_length']" ) );
								pendingStoreReportDataTable_lengthTabindex.prop('tabindex','10');	
								
								var paginate_buttonTabIndex = angular.element( document.querySelectorAll( ".paginate_button" ) );
								paginate_buttonTabIndex.prop('tabindex','0');
								
								var paginate_buttonTabIndeximg = angular.element( document.querySelectorAll( "[src='/brta/app/ui/resources/images/column_incident-management2.png']" ) );
								paginate_buttonTabIndeximg.prop('tabindex','0');
								
								var balePickupSummaryDetails_filterTabIndex = angular.element( document.querySelectorAll( "#balePickupSummaryDetails_filter input" ) );
								balePickupSummaryDetails_filterTabIndex.prop('tabindex','-1');
								var pendingStoreReportDataTable_filterTabIndex = angular.element( document.querySelectorAll( "#pendingStoreReportDataTable_filter input" ) );
								pendingStoreReportDataTable_filterTabIndex.prop('tabindex','-1');
								
							},1000);
						}
						$scope.addTabIndex();
						
						
						$scope.assignTabIndexBalePickup = function() {
		
							var assignedTabIndexValue = angular.element(document.querySelectorAll(".btn-success"));
							 assignedTabIndexValue.attr('tabindex',"-1");
							 
							var paginate_buttonTabIndex = angular.element( document.querySelectorAll( ".paginate_button " ) );
								paginate_buttonTabIndex.prop('tabindex','-1');
							var assignedtabsIndexValue = angular.element(document.querySelectorAll(".tabsIndexValue"));
								assignedtabsIndexValue.attr('tabindex',"-1");
								
							var assignedselectTabIndexValue = angular.element(document.querySelectorAll(".selectTabIndexValue"));
								 assignedselectTabIndexValue.attr('tabindex',"-1");
								 
							var selectTabIndexValueForUploadTabIndexValue = angular.element(document.querySelectorAll(".selectTabIndexValueForUpload"));
								 selectTabIndexValueForUploadTabIndexValue.attr('tabindex',"-1");
								 
							var assignedselectTabIndexValue = angular.element(document.querySelectorAll(".selectDownloadTabIndexValue"));
								 assignedselectTabIndexValue.attr('tabindex',"-1");	
								 
							var balePickupSummaryDetails_lengthTabindex = angular.element( document.querySelector( "[name='balePickupSummaryDetails_length']" ) );
								balePickupSummaryDetails_lengthTabindex.prop('tabindex','-1');
								
							var pendingStoreReportDataTable_lengthtab = angular.element( document.querySelector( "[name='pendingStoreReportDataTable_length']" ) );
								pendingStoreReportDataTable_lengthtab.prop('tabindex','-1'); 
								
							var paginate_buttonTabIndeximg = angular.element( document.querySelectorAll( "[src='/brta/app/ui/resources/images/column_incident-management2.png']" ) );
								paginate_buttonTabIndeximg.prop('tabindex','-1');								
								 
								
							
							var addDriverIndexValue5TabIndex = angular.element( document.querySelectorAll( ".close_Button " ) );
								addDriverIndexValue5TabIndex.prop('tabindex','-1');							
							}
							
						$scope.assignTabIndexclose = function() {
							 var assignedtabsIndexValue = angular.element(document.querySelectorAll(".tabsIndexValue"));
								assignedtabsIndexValue.attr('tabindex',"1");
								
								 var assignedselectTabIndexValue = angular.element(document.querySelectorAll(".selectTabIndexValue"));
								 assignedselectTabIndexValue.attr('tabindex',"2");
								 
								 var selectTabIndexValueForUploadTabIndexValue = angular.element(document.querySelectorAll(".selectTabIndexValueForUpload"));
								 selectTabIndexValueForUploadTabIndexValue.attr('tabindex',"3");
								 
								 var assignedselectTabIndexValue = angular.element(document.querySelectorAll(".selectDownloadTabIndexValue"));
								 assignedselectTabIndexValue.attr('tabindex',"13");	
								 
								var pendingStoreReportDataTable_lengthTabindex = angular.element( document.querySelector( "[name='pendingStoreReportDataTable_length']" ) );
								pendingStoreReportDataTable_lengthTabindex.prop('tabindex','14');
								
								var balePickupSummaryDetails_lengthTabindex1 = angular.element( document.querySelector( "[name='balePickupSummaryDetails_length']" ) );
								balePickupSummaryDetails_lengthTabindex1.prop('tabindex','17');
								
								var paginate_buttonTabIndex = angular.element( document.querySelectorAll( ".paginate_button " ) );
								paginate_buttonTabIndex.prop('tabindex','18');
								
								var paginate_buttonTabIndeximg = angular.element( document.querySelectorAll( "[src='/brta/app/ui/resources/images/column_incident-management2.png']" ) );
								paginate_buttonTabIndeximg.prop('tabindex','0');
							}
							
						$scope.assignbackIndexValue = function(){
								var balePickupSummaryDetails_lengthTabindex = angular.element( document.querySelector( "[name='balePickupSummaryDetails_length']" ) );
								balePickupSummaryDetails_lengthTabindex.prop('tabindex','17');
								
								var paginate_buttonTabIndex = angular.element( document.querySelectorAll( ".paginate_button " ) );
								paginate_buttonTabIndex.prop('tabindex','18');
						}
						$scope.StartDateFun_tabindex = function(){							
							$(".selectTabIndexValue1 ").prop('tabindex','0');
							var paginate_buttonTabIndeximg = angular.element( document.querySelectorAll( "[src='/brta/app/ui/resources/images/column_incident-management2.png']" ) );
								paginate_buttonTabIndeximg.prop('tabindex','0');
							$( "[name='balePickupSummaryDetails_length']" ).prop('tabindex','0');
							$( ".paginate_button " ).prop('tabindex','0');
							
						}
						$scope.EndDateFun_tabindex = function(){
							$(".selectTabIndexValue2 ").prop('tabindex','0');
							var paginate_buttonTabIndeximg = angular.element( document.querySelectorAll( "[src='/brta/app/ui/resources/images/column_incident-management2.png']" ) );
								paginate_buttonTabIndeximg.prop('tabindex','0');
							$( "[name='balePickupSummaryDetails_length']" ).prop('tabindex','0');
							$( ".paginate_button " ).prop('tabindex','0');
							$(".selectTabIndexValue-index ").prop('tabindex','8');
						}
						$scope.submitButton_Tabindex= function(){
							$(".selectTabIndexValue2 ").prop('tabindex','0');
							var paginate_buttonTabIndeximg = angular.element( document.querySelectorAll( "[src='/brta/app/ui/resources/images/column_incident-management2.png']" ) );
								paginate_buttonTabIndeximg.prop('tabindex','0');
							$( "[name='balePickupSummaryDetails_length']" ).prop('tabindex','0');
							$( ".paginate_button " ).prop('tabindex','0');
						}

					$scope.executeExcel=function(){
							$('#excelDownload').keypress(function (e) {
							 var key = e.which;
							 if(key == 13)  // the enter key code
							  {
								$('#excelDownload').click();
								return false;  
							  }
							});
							
						}
					$scope.executeExcel_pending=function(){
						$('#excelDownload_pending').keypress(function (e) {
							 var key = e.which;
							 if(key == 13)  // the enter key code
							  {
								$('#excelDownload_pending').click();
								return false;  
							  }
							});
					}
					
					
					
					
					
		//to avoid more than limit characters.  bolCount 
					var releaseNumberCount= document.getElementById('releaseNumberCount');

					
					releaseNumberCount.onpaste = function(e){
						var max = releaseNumberCount.getAttribute("maxlength");
						e.clipboardData.getData('text/plain').slice(0, max)
					};
						
					
					var balespickedCount= document.getElementById('balespickedCount');

					
					balespickedCount.onpaste = function(e){
						var max = balespickedCount.getAttribute("maxlength");
						e.clipboardData.getData('text/plain').slice(0, max)
					};
						
										
					var balesremainingCount= document.getElementById('balesremainingCount');

					
					balesremainingCount.onpaste = function(e){
						var max = balesremainingCount.getAttribute("maxlength");
						e.clipboardData.getData('text/plain').slice(0, max)
					};
					
					
					var commentCount= document.getElementById('commentCount');

					
					commentCount.onpaste = function(e){
						var max = commentCount.getAttribute("maxlength");
						e.clipboardData.getData('text/plain').slice(0, max)
					};
					
					
					var bolCount= document.getElementById('bolCount');

					
					bolCount.onpaste = function(e){
						var max = bolCount.getAttribute("maxlength");
						e.clipboardData.getData('text/plain').slice(0, max)
					};
					
					
					var grossCount= document.getElementById('grossCount');

					
					grossCount.onpaste = function(e){
						var max = grossCount.getAttribute("maxlength");
						e.clipboardData.getData('text/plain').slice(0, max)
					};
					
					var tareCount= document.getElementById('tareCount');

					
					tareCount.onpaste = function(e){
						var max = tareCount.getAttribute("maxlength");
						e.clipboardData.getData('text/plain').slice(0, max)
					};
					
					
					
					
					
					var imageformat = document.getElementById('imageformat');
					
					imageformat.onchange = function(e){

					
						$scope.validationError=false;
						$scope.editPickupData.error={};
						
					if ('files' in imageformat) {
						
						
					
						
						if(imageformat.files.length == 0){
							$scope.editPickupData.incidentImagesLength=true;
							$scope.editPickupData.incidentExtension=true;
						}
						
						else if(imageformat.files.length>3){
						
							this.value='';
							$scope.editPickupData.incidentImagesLength=false;
							
						}
						else{
						
							
							$scope.editPickupData.incidentImagesLength=true;
							
						var ext=this.value.substr(this.value.lastIndexOf('.') + 1);
						
							if(!(ext=="ai" || ext=="bmp" || ext=="gif"|| ext=="ico"||ext=="jpeg"||ext=="jpg" ||ext=="png"||ext=="ps"
								||ext=="psd"||ext=="svg"||ext=="tif"||ext=="tiff"||ext=="AI" || ext=="BMP" || ext=="GIF"|| ext=="ICO"
								||ext=="JPEG"||ext=="JPG" ||ext=="PNG"||ext=="PS"||ext=="PSD"||ext=="SVG"||ext=="TIF"||	ext=="TIFF")){
								
									this.value='';
									$scope.editPickupData.incidentExtension=false;
						}	else{
									$scope.editPickupData.incidentExtension=true;
						}
					
						}
						
				
				   if($scope.editPickupData.incidentExtension==false){
							$scope.editPickupData.error.incidentExtension= true;
							 $scope.validationError=true;
						 }else{
						 }
						 
					 
						 if($scope.editPickupData.incidentImagesLength==false){
							$scope.editPickupData.error.incidentImagesLength= true;
							 $scope.validationError=true;
						 }else{
						 }
					} 
				};
				
				$scope.removeInputValueBalePickup = function(){					
					$scope.dtOptionsBalePickup=DTOptionsBuilder.newOptions()
					.withOption('bFilter', true);
				}
				$scope.removeInputValueBalePending = function(){					
							$scope.dtOptionsPR=DTOptionsBuilder.newOptions()
							.withOption('bFilter', true);
				}
				
				//Prema code added by Dk
				
				function pickupStartdateDiff(dateold,datenew){
						var ynew = datenew.getFullYear();
						var mnew = datenew.getMonth();
						var dnew = datenew.getDate();
						var yold = dateold.getFullYear();
						var mold = dateold.getMonth();
						var dold = dateold.getDate();
						var diff = ynew-yold;
						if(mold>mnew)diff--;
						else{
							if(mold==mnew){
								if(dold>dnew)diff--;
							}
						}
						
						
						
						if(diff == 0){
							
						}
						 else if(diff>10){
							$scope.invalidBaleStartDateError="Select upto 10 years";
								$scope.startDate=firstDay;
						}
						
						 // else if(diff<-10){
							// $scope.invalidBaleStartDateError="Select upto 10 years";
								// $scope.startDate=firstDay;
						// }					
						
					}
				
				$scope.checkFromDateValidation=function(){
					// Pickup Data Summary
						var baleStartDateValueFromId= $("#startDateval").val();	
						$scope.invalidBaleStartDateError=undefined;
						if(baleStartDateValueFromId != ''){
							if(baleStartDateValueFromId != undefined  && ($scope.startDate ==undefined || $scope.startDate ==null) ){	
								$scope.invalidBaleStartDateError="Invalid Start Date";
								$scope.startDate=firstDay;
							} else if(baleStartDateValueFromId != undefined && $scope.startDate !=undefined){
								dateold = new Date(firstDay);
								datenew = new Date(baleStartDateValueFromId);
								pickupStartdateDiff(dateold, datenew);
										}
						}
										
					
					
					}
					
					function pickupEndDateDiff(dateold,datenew){
						var ynew = datenew.getFullYear();
						var mnew = datenew.getMonth();
						var dnew = datenew.getDate();
						var yold = dateold.getFullYear();
						var mold = dateold.getMonth();
						var dold = dateold.getDate();
						var diff = ynew-yold;
						if(mold>mnew)diff--;
						else{
							if(mold==mnew){
								if(dold>dnew)diff--;
							}
						}
						
						
						
						if(diff == 0){
							
						}
						 else if(diff>10){
							$scope.invalidBaleEndtDateDateError="Select upto 10 years";
								$scope.endDate=null;
						}
						
						 // else if(diff<-10){
							// $scope.invalidBaleEndtDateDateError="Select upto 10 years";
								// $scope.endDate=null;
						// }					
						
					}
					
					$scope.checkToDateValidation = function(){
						var baleEndtDateValueFromId= $("#endDateval").val();	
						$scope.invalidBaleEndtDateDateError=undefined;
						if(baleEndtDateValueFromId != ''){
							if(baleEndtDateValueFromId != undefined  && ($scope.endDate ==undefined || $scope.endDate ==null) ){	
								$scope.invalidBaleEndtDateDateError="Invalid End Date";
								$scope.endDate=null;
							}else if(baleEndtDateValueFromId != undefined && $scope.endDate !=undefined){
								dateold = new Date(firstDay);
								datenew = new Date(baleEndtDateValueFromId);
								pickupEndDateDiff(dateold, datenew);
										}
						}
					}
					
					
					
					function dateDiff(dateold,datenew){
						var ynew = datenew.getFullYear();
						var mnew = datenew.getMonth();
						var dnew = datenew.getDate();
						var yold = dateold.getFullYear();
						var mold = dateold.getMonth();
						var dold = dateold.getDate();
						var diff = ynew-yold;
						if(mold>mnew)diff--;
						else{
							if(mold==mnew){
								if(dold>dnew)diff--;
							}
						}
						
						
						
						if(diff == 0){
							
						}
						 else if(diff>10){
							$scope.invalidBalePickupStartDateError="Select upto 10 years ";
							$scope.editPickupData.startDateBalePickup=new Date();
						}
						
						 else if(diff<-10){
							$scope.invalidBalePickupStartDateError="Select upto 10 years";
							$scope.editPickupData.startDateBalePickup=new Date();
						}					
						
					}
					
					
					
					$scope.checkValidPopupstartDate=function(){
						var balePickupStartDateValueFromId= $("#startDate_popup").val();
						$scope.invalidBalePickupStartDateError=undefined;	
						if(balePickupStartDateValueFromId != ''){
							if(balePickupStartDateValueFromId != undefined  && ($scope.editPickupData.startDateBalePickup ==undefined || $scope.editPickupData.startDateBalePickup ==null) ){	
								$scope.invalidBalePickupStartDateError="Invalid Pickup Date";
								$scope.editPickupData.startDateBalePickup=firstDay;
							}else if(balePickupStartDateValueFromId != undefined && $scope.editPickupData.startDateBalePickup !=undefined){
								dateold = new Date(firstDay);
								datenew = new Date(balePickupStartDateValueFromId);
								dateDiff(dateold, datenew);
										}
										
											
						}
												
						
						
					}
					function deliveryDateDiff(dateold,datenew){
						
						var ynew = datenew.getFullYear();
						var mnew = datenew.getMonth();
						var dnew = datenew.getDate();
						var yold = dateold.getFullYear();
						var mold = dateold.getMonth();
						var dold = dateold.getDate();
						var diff = ynew-yold;
						if(mold>mnew)diff--;
						else{
							if(mold==mnew){
								if(dold>dnew)diff--;
							}
						}
						
						
						console.log("diff"+diff);
						if(diff == 0){
							
						}
						 else if(diff>10){
							$scope.invalidBaleEndDateError="Select upto 10 years";
							//$scope.editPickupData.error={};
							$scope.editPickupData.error.deliveryDateError= false;
							
							 $scope.validationError=false;
							$scope.editPickupData.deliveryDate="";		
						}
						
						 else if(diff<-10){
							$scope.invalidBaleEndDateError="Select upto 10 years";
							$scope.editPickupData.error.deliveryDateError= false;
							$scope.validationError=false;
								$scope.editPickupData.deliveryDate="";		
						}
						
						
					}
					
					
					$scope.checkValidPopupEndDate=function(){
					// edit bale pickup
						var baleEndDateValueFromId= $("#endDate_popup").val();	
						$scope.invalidBaleEndDateError=undefined;
						console.log(baleEndDateValueFromId);
						if(baleEndDateValueFromId != ''){
							if(baleEndDateValueFromId != undefined  && ($scope.editPickupData.deliveryDate ==undefined || $scope.editPickupData.deliveryDate ==null) ){	
								$scope.invalidBaleEndDateError="Invalid Delivery Date.";
								$scope.editPickupData.error.deliveryDateError= false;
								$scope.editPickupData.deliveryDate="";								
							} else if(baleEndDateValueFromId != undefined && $scope.editPickupData.deliveryDate !=undefined){
								dateold = new Date(firstDay);
								datenew = new Date(baleEndDateValueFromId);
								deliveryDateDiff(dateold, datenew);
										}
						}
								
						
					}
					
					
					
					
					
				});
brtaApp.controller("reportController",function(applicationContextURL,$scope,$http,$filter,$interval,$timeout,reportService,Lightbox,$rootScope,
								customerService,incidentTypeService,supplierService,DTOptionsBuilder,DTColumnBuilder,Idle,Keepalive,$uibModal,$routeParams,$route,$location){
	$scope.$route = $route;
	$scope.authorized=false;
	$scope.authorizedRMT=false;
	var role=window.role;		
	 var supplierId=window.supplierId;		
	 	 $scope.isSupplierRole=false;	
	 	 if($rootScope.user ==undefined){
	 		$rootScope.user={};
	 	 }
	 $rootScope.user.role = window.role;			
	$rootScope.supplierId=supplierId;

		customerService.getAllBuyCustomers().then(
							function(response) {
								$scope.buyCustomers = response;
								$scope.buyCustomersOriginal = response;
								$rootScope.buyCustomers=response;
							});
	
		var defaultSupplierId = null;
				supplierService.getAllSuppliers().then(
							function(response) {
								
								$scope.suppliersForCustomerSelectedOriginal=$scope.suppliersForCustomerSelected=response;
									if($rootScope.user.role =='Supplier'){	
										for(var i=0;i<$scope.suppliersForCustomerSelected.length;i++){		
											if($scope.suppliersForCustomerSelected[i].supplierId == $rootScope.supplierId){	
												 defaultSupplierId = $scope.suppliersForCustomerSelected[i].supplierId; 
												$scope.supplierSelected = JSON.stringify($scope.suppliersForCustomerSelected[i]);
												break;					
											}		
										}
										$scope.getCustomersFromSupplier(defaultSupplierId);
									}
									
									$scope.loaderDetailedReport=false;
								});
	
		 $scope.getCustomersFromSupplier =function (supplierId){
		
			customerService.getBuyCustomersFromSupplierDAR(supplierId).then(function(response) {
						$scope.buyCustomers=response;	
					});
					
				customerService.getSellCustomersFromSupplierDAR(supplierId).then(function(response) {
					$scope.sellCustomersForCustomerSelected = response;
				});
		    
		
			}
	$scope.redirect = function(){
	     if($rootScope.user.role =='Supplier'){
	      window.location = "#/storesupplierForSupplier";
		  $(".store_menu a").attr("class", "active");
		  $(".report_menu a").attr("class", "");
	      }else{
	        window.location = "#/storemanagement";
			$(".store_menu a").attr("class", "active");
		  $(".report_menu a").attr("class", "");
	      }
	  }		
		$(".report_menu a").attr("class", "");	
			
	
	if($rootScope.user.role =='Supplier'){	

		$scope.isSupplierRole=true;		
	$scope.loaderDetailedReport=true;	
				
	}

		
		$scope.detailedActtivityReportManagement = true;
		$scope.rmtImportReportManagement = false;
		$scope.pendingStoreReportManagement = false;
		
		$scope.detailedActtivityReportManagementOpen = function(){
			$scope.detailedActtivityReportManagement = true;
			$scope.rmtImportReportManagement = false;
			$scope.pendingStoreReportManagement = false;
		}
		$scope.rmtImportReportManagementOpen = function(){
			$scope.detailedActtivityReportManagement = false;
			$scope.rmtImportReportManagement = true;
			$scope.pendingStoreReportManagement = false;
		}
		$scope.pendingStoreReportManagementOpen = function(){
			$scope.detailedActtivityReportManagement = false;
			$scope.rmtImportReportManagement = false;
			$scope.pendingStoreReportManagement = true;
		}
	
		var date=new Date();			
		var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
		var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
		//$scope.disableSubmitBtn = true;
		//$scope.disableSubmitBtnRMT=true;
		
		
	$scope.startDateRMT=$scope.startDARDate=firstDay;	
	$scope.endDateRMT=$scope.endDARDate=lastDay;
	
	$scope.resetDetailedActivityReport=function(){
		
		
		if($rootScope.user.role =='Supplier'){
		$scope.buyCustomerSelected=undefined;
		$scope.sellCustomerSelected=undefined;
		$scope.getCustomersFromSupplier(defaultSupplierId);
		$scope.incidentSelected=undefined;
		$scope.startDARDate=undefined;
		$scope.endDARDate=undefined;
		$scope.isSupplierRole=true;	
		$scope.jsonForDAReportExport=[];
		$scope.exportDetailedActivityData=[];
		$scope.balePickupList = [];
		$scope.disableSubmitBtn=true;
		$scope.authorized=false;
		

	}else{
		$scope.suppliersForCustomerSelected=$scope.suppliersForCustomerSelectedOriginal
		$scope.sellCustomersForCustomerSelected = $scope.sellCustomersForCustomerSelectedOriginal;
		$scope.buyCustomers= $scope.buyCustomersOriginal;
		$scope.buyCustomerSelected=undefined;
		$scope.sellCustomerSelected=undefined;
		$scope.supplierSelected=undefined;
		$scope.incidentSelected=undefined;
		$scope.startDARDate=undefined;
		$scope.endDARDate=undefined;
		$scope.jsonForDAReportExport=[];
		$scope.exportDetailedActivityData=[];
		$scope.balePickupList = [];
		$scope.disableSubmitBtn=true;
		$scope.authorized=false;
		
	}
	}
	
	$scope.resetFiltersRMT=function(){
		$scope.sellCustomersForCustomerSelected=$scope.sellCustomersForCustomerSelectedOriginal; 
		$scope.buyCustomers= $scope.buyCustomersOriginal;
		$scope.buyCustomerSelectedRMT=undefined;
		$scope.sellCustomerSelectedRMT=undefined;
		$scope.startDateRMT=undefined;
		$scope.endDateRMT=undefined;
		$scope.jsonForRMTReportExport=[];
		$scope.exportRMTReportData=[];
		$scope.balePickupListRMT=[];
		$scope.disableSubmitBtnRMT=true;
		$scope.authorizedRMT=false;
	}
	
	
	
	
	
	$scope.StartupDateDARFun = function() {
						$scope.StartDateDAR.opened=true;
						$scope.dateOptionsStart = {
							datepickerMode : 'day',
							minMode : 'day',
							// maxDate: new Date(2020, 5, 22),
							//minDate: new Date(2017, 4, 22),
							startingDay : 1,
							showWeeks : false,
							formatMonth : 'MMM',
							formatYear : 'yyyy',
							monthColumns : 4,
						}
						$scope.$watch("startDARDate", function(newValue, oldValue) {		
							$("#startDARDate_popup").focus();
							});
					};
					$scope.StartDateDAR= {
						opened : false
					}; 
	
	 $scope.EndDateDARFun = function() {
		 
					   $scope.EndDARDate.opened = true;
					$scope.dateOptionEnd = {
							datepickerMode:'day',
							minMode:'day',
						   // maxDate: new Date(2020, 5, 22),
							//minDate: new Date(2017, 4, 22),
							startingDay: 1,
							showWeeks:false,
							formatMonth:'MMM',
							formatYear: 'yyyy',
							monthColumns:4,
					 }
					 $scope.$watch("endDARDate", function(newValue, oldValue) {		
							$("#endDARDate_popup").focus();
							});
				  };

				  $scope.EndDARDate = {
					opened: false
					
				};
		
	$interval( function(){
						$('#detailedActivityReportDataTable_filter input').prop('title','Search By Buy Customer,Store Name,Pickup Date,Sell Customer,Sell Customer Site,Material Profile,Bales Picked,Bales Remaining,Incident Type,Incident Picture,Driver Names');
						$('#rmtImportReportDataTable_filter input').prop('title','Search By Date,Vendor Code,Customer Code,Material Code,Release No,Reference,Notes,Weight,Bales Remaining ');
						
						$('[data-toggle="tooltip"]').tooltip(); 
						
						$('[data-toggle="tooltip"]').on('shown.bs.tooltip', function () {
							  $timeout(function(){
								$('[data-toggle="tooltip"]').tooltip('hide')
								},3000)
							})
						},1000);
		//commented by prema				
	// $interval( function(){
						// $('#rmtImportReportDataTable_filter input').prop('title','Search By Date,Vendor Code,Customer Code,Material Code,Release No,Reference,Notes,Weight,Bales Remaining ');},1000);
						

	
	$scope.detailedActivityReportDataTableOptions=DTOptionsBuilder.newOptions().withPaginationType('full_numbers')
					.withDisplayLength(10)					
					.withOption('order', [])
					.withOption('lengthChange', true)
					.withOption('bFilter', true)
					.withOption('tabIndex', '-1')
					.withDOM('Blfrtip')
					.withOption('responsive', false);


		
					
						if(window.innerWidth >= 992) {
						$scope.resDtColumnsstoreListColumns = [DTColumnBuilder.newColumn(0).notSortable(),
									DTColumnBuilder.newColumn(1),
									DTColumnBuilder.newColumn(2),
									DTColumnBuilder.newColumn(3),
									DTColumnBuilder.newColumn(4),
									DTColumnBuilder.newColumn(5),
									DTColumnBuilder.newColumn(6),
									DTColumnBuilder.newColumn(7),
									DTColumnBuilder.newColumn(8),
									DTColumnBuilder.newColumn(9),
									DTColumnBuilder.newColumn(10)];
						}
	$scope.rmtImportReportDataTableOptions=DTOptionsBuilder.newOptions().withPaginationType('full_numbers')
					.withDisplayLength(10)
					.withOption('order', [[1, 'asc']])
					.withOption('lengthChange', true)
					.withOption('tabIndex', '-1')
					.withOption('bFilter', true)
					.withOption('responsive', false);
		
					
						if(window.innerWidth >= 992) {
						$scope.rmtImportReportDataTableColumns = [DTColumnBuilder.newColumn(0).notSortable(),
									DTColumnBuilder.newColumn(1),
									DTColumnBuilder.newColumn(2),
									DTColumnBuilder.newColumn(3),
									DTColumnBuilder.newColumn(4),
									DTColumnBuilder.newColumn(5),
									DTColumnBuilder.newColumn(6),
									DTColumnBuilder.newColumn(7),									
									DTColumnBuilder.newColumn(8),
									DTColumnBuilder.newColumn(9)];
						}
		$scope.pendingStoreReportDataTableOptions=DTOptionsBuilder.newOptions().withPaginationType('full_numbers')
					.withDisplayLength(10)
					.withOption('order', [[1, 'asc']])
					.withOption('lengthChange', true)
					.withOption('bFilter', true)
					.withOption('tabIndex', '-1')
					.withDOM('Blfrtip')
					.withOption('responsive', true);
		
					
						if(window.innerWidth >= 992) {
						$scope.pendingStoreReportDataTableColumns = [DTColumnBuilder.newColumn(0),
									DTColumnBuilder.newColumn(1),
									DTColumnBuilder.newColumn(2),
									DTColumnBuilder.newColumn(3),
									DTColumnBuilder.newColumn(4),
									DTColumnBuilder.newColumn(5).notSortable()];
						}
	$scope.selectAllIncidentTypeObj={
				"incidentTypeId":"1234",
				"incidentDescription":"All Incidents"
					}

	$scope.editingData = [];
	
	
	var currentUrl=$location.url();
					
					if(currentUrl.indexOf("reports")!=-1){
						$scope.detailedActivityReportTab=true;
						$(".pickup_menu a").attr("class", "");
						$(".user_menu a").attr("class", "");
						$(".incident_menu a").attr("class", "");
						$(".report_menu a").attr("class", "active");
						$(".needHelp_menu a").attr("class", "");
						$(".store_menu a").attr("class", "");
					}else if(currentUrl.indexOf("rmtReportTab")!= -1){
						$scope.rmtReportTab = true;
						$(".pickup_menu a").attr("class", "");
						$(".user_menu a").attr("class", "");
						$(".incident_menu a").attr("class", "");
						$(".report_menu a").attr("class", "active");
						$(".needHelp_menu a").attr("class", "");
						$(".store_menu a").attr("class", "");
					}
	
	$scope.disableSubmitBtnPR=true;

	$scope.disableAddPickupBtn=true;
	
	$scope.checkSubmitBtn=function(){
		
	if($rootScope.user.role =='Supplier'){
		
		if( ($scope.startDARDate  !=undefined ) && ($scope.endDARDate  !=undefined )){
			$scope.disableSubmitBtn=false;
		}
		else{
			$scope.disableSubmitBtn = true;
		}
		
	}
	if($rootScope.user.role !='Supplier'){
		
		if(($scope.startDARDate  !=undefined ) && ($scope.endDARDate  !=undefined )){
			$scope.disableSubmitBtn=false;
		}
		else{
			$scope.disableSubmitBtn = true;
		}
		if(($scope.startDateRMT  !=undefined ) && ($scope.endDateRMT  !=undefined )){
			
			$scope.disableSubmitBtnRMT=false;
		}
		else{
			$scope.disableSubmitBtnRMT = true;
		}
	}
	}
			
		
	customerService.getAllBuyCustomers().then(function(response){
		$scope.buyCustomers = response;
	
	});
	
	$scope.getUniqueObjectsFromList=function(collection, keyname) {
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
   
   
   
   $scope.getSupplierForDAR = function(buyCustomer) {

   if(!$scope.isSupplierRole){
	   if (buyCustomer != null) {
							angular
									.forEach(
											$scope.suppliersWithCustomer,
											function(value, key) {
												if (key == buyCustomer.customerId) {
													$scope.suppliersForCustomerSelected = value;
												}
											});
						} else {
							$scope.suppliersForCustomerSelected = $scope.suppliersForCustomerSelectedOriginal;
						}
   }
						

					}
   
	
	
		customerService.getAllSellCustomers().then(function(response) {
							$scope.sellCustomersForCustomerSelected = response;
							$scope.sellCustomersForCustomerSelectedOriginal=$scope.sellCustomersForCustomerSelected;  	
						});
		
			
	incidentTypeService.getAllIncidentTypes().then(function(response){
		$scope.incidentTypes = response;
			/*
		var c=$scope.incidentTypes.length+1;
		var incident= new String('All Incidents' + c)
		var incident = {};
		
		$scope.incidentTypes.splice(0,0,incident);*/
		});
		
		
		
	$scope.getBuyCustomerDetailsForRMT = function(buyCustomer)
		{
			
	
	
	if(buyCustomer !=undefined && buyCustomer!="" && buyCustomer!=null){
		customerService.getAllSellCustomersForBuyCustomer(buyCustomer.customerId).then(function(response) {
			$scope.sellCustomersForCustomerSelected = response;	

});	
	}else{
		$scope.sellCustomersForCustomerSelected=$scope.sellCustomersForCustomerSelectedOriginal; 
		$scope.suppliersForCustomerSelected=$scope.suppliersForCustomerSelectedOriginal;
	}
		
		};
		
		
		
   
   
   //by deepak kasgar for RMT Dependent filters
   
   $scope.getFilteredValuesRMT=function(buyCustomerSelectedRMT,sellCustomerSelectedRMT){
	  
	  
	 
	    if(buyCustomerSelectedRMT !=undefined && buyCustomerSelectedRMT!="" && buyCustomerSelectedRMT!=null){
		   buyCustomerSelectedRMT=JSON.parse(buyCustomerSelectedRMT);
	   }else{
		   buyCustomerSelectedRMT=undefined;
	   }
	   
	   if(sellCustomerSelectedRMT !=undefined && sellCustomerSelectedRMT!="" && sellCustomerSelectedRMT!=null){
		   sellCustomerSelectedRMT=JSON.parse(sellCustomerSelectedRMT);
	   }else{
		   sellCustomerSelectedRMT=undefined;
	   }
	   
	  
	   if((buyCustomerSelectedRMT==undefined || buyCustomerSelectedRMT=="" || buyCustomerSelectedRMT==null) && (sellCustomerSelectedRMT ==undefined || sellCustomerSelectedRMT=="" || sellCustomerSelectedRMT==null)){
		   console.log("0 0");
					customerService.getAllSellCustomers().then(function(response) {
					$scope.sellCustomersForCustomerSelected = response;
			});
			
				customerService.getAllBuyCustomers().then(function(response) {
				$scope.buyCustomers = response;					
			});	
	   }

		if((buyCustomerSelectedRMT!=undefined && buyCustomerSelectedRMT!="" && buyCustomerSelectedRMT!=null) && (sellCustomerSelectedRMT ==undefined || sellCustomerSelectedRMT=="" || sellCustomerSelectedRMT==null)){
			 console.log("1 0");
			 customerService.getAllSellCustomersForBuyCustomer(buyCustomerSelectedRMT.customerId).then(function(response) {
				 $scope.sellCustomersForCustomerSelected = response;	
				 });
			customerService.getAllBuyCustomers().then(function(response) {
				$scope.buyCustomers = response;					
			});		 

		}
		
		if((buyCustomerSelectedRMT==undefined || buyCustomerSelectedRMT=="" || buyCustomerSelectedRMT==null) && (sellCustomerSelectedRMT!=undefined && sellCustomerSelectedRMT!="" && sellCustomerSelectedRMT!=null)){
			 console.log("0 1");
			 
				customerService.getAllBuyCustomersForSellCustomer(sellCustomerSelectedRMT.customerId).then(function(response) {
					$scope.buyCustomers = response;
					});
				
				customerService.getAllSellCustomers().then(function(response) {
					$scope.sellCustomersForCustomerSelected = response;
			});
					
					
		}
		
		if(buyCustomerSelectedRMT !=undefined && buyCustomerSelectedRMT!="" && buyCustomerSelectedRMT!=null && sellCustomerSelectedRMT !=undefined && sellCustomerSelectedRMT!="" && sellCustomerSelectedRMT!=null){
			 console.log("1 1")
			 customerService.getAllSellCustomersForBuyCustomer(buyCustomerSelectedRMT.customerId).then(function(response) {
				 $scope.sellCustomersForCustomerSelected = response;
			 });
			 customerService.getAllBuyCustomersForSellCustomer(sellCustomerSelectedRMT.customerId).then(function(response) {
					$scope.buyCustomers = response;
					});
		}
		   
		   
		}
   
   
   
   
   //END by deepak kasgar for RMT Dependent filters
   
   
   $scope.getFilteredValues=function(buyCustomer,sellCustomer,supplier){
	 
	
	    if(buyCustomer !=undefined && buyCustomer!="" && buyCustomer!=null){
		   buyCustomer=JSON.parse(buyCustomer);
	   }
	   
	   if(sellCustomer !=undefined && sellCustomer!="" && sellCustomer!=null){
		   sellCustomer=JSON.parse(sellCustomer);
	   }
	   
	   if(supplier !=undefined && supplier!="" && supplier!=null){
		   supplier=JSON.parse(supplier);
	   }
	   
	   if((buyCustomer==undefined || buyCustomer=="" || buyCustomer==null) && (sellCustomer ==undefined || sellCustomer=="" || sellCustomer==null) && (supplier ==undefined || supplier=="" || supplier==null)){
		   
		console.log("0 0 0");
				customerService.getAllSellCustomers().then(function(response) {
					$scope.sellCustomersForCustomerSelected = response;
	 
			});
			
			customerService.getAllBuyCustomers().then(function(response) {
				$scope.buyCustomers = response;
									
			});
			
			supplierService.getAllSuppliers().then(function(response) {
				$scope.suppliersForCustomerSelectedOriginal=$scope.suppliersForCustomerSelected=response;
				
			});								
		   
		   
		}
		 if((buyCustomer ==undefined || buyCustomer=="" || buyCustomer==null) && (sellCustomer ==undefined || sellCustomer=="" || sellCustomer==null) && (supplier !=undefined && supplier!="" && supplier!=null)){
			 
			 console.log("0 0 1");
			 
				customerService.getBuyCustomersFromSupplierDAR(supplier.supplierId).then(function(response) {
						$scope.buyCustomers=response;	
					});
					
				customerService.getSellCustomersFromSupplierDAR(supplier.supplierId).then(function(response) {
					$scope.sellCustomersForCustomerSelected = response;
				});
				supplierService.getAllSuppliers().then(function(response) {
				$scope.suppliersForCustomerSelectedOriginal=$scope.suppliersForCustomerSelected=response;
				
			});	
				
		    
		}
		if((buyCustomer ==undefined || buyCustomer=="" || buyCustomer==null)&& (sellCustomer!=undefined && sellCustomer!="" && sellCustomer!=null) && (supplier ==undefined || supplier=="" || supplier==null)){
		   
		   console.log("0  1  0");
		   
			   customerService.getAllBuyCustomersForSellCustomer(sellCustomer.customerId).then(function(response) {
					$scope.buyCustomers = response;
					});
					
				customerService.getAllSuppliersFromSellCustomer(sellCustomer.customerId).then(function(response) {
					$scope.suppliersForCustomerSelected=response;	
					});
				customerService.getAllSellCustomers().then(function(response) {
					$scope.sellCustomersForCustomerSelected = response;
	 
			});	
		   
		}
		if((buyCustomer ==undefined || buyCustomer=="" || buyCustomer==null) && (sellCustomer !=undefined && sellCustomer!="" && sellCustomer!=null) && (supplier !=undefined && supplier!="" && supplier!=null)){
			
			 console.log("0 1 1");
			 
				customerService.getAllBuyCustomerFromSellCustomerAndSupplier(sellCustomer.customerId,supplier.supplierId).then(function(response) {
					$scope.buyCustomers=response;	
					});
					
				customerService.getAllSuppliersFromSellCustomer(sellCustomer.customerId).then(function(response) {
					 $scope.suppliersForCustomerSelected=response;	
					 });
					
		   
		  
		   
		}
		if((buyCustomer !=undefined && buyCustomer!="" && buyCustomer!=null) && (sellCustomer ==undefined || sellCustomer=="" || sellCustomer==null) && (supplier ==undefined || supplier=="" || supplier==null)){
			
			console.log("1 0 0");
			
				customerService.getAllSellCustomersForBuyCustomer(buyCustomer.customerId).then(function(response) {
				 $scope.sellCustomersForCustomerSelected = response;	
				 });
				 
			
				customerService.getSuppliersFromBuyCustomerForDAR(buyCustomer.customerId).then(function(response) {
				 $scope.suppliersForCustomerSelected = response;		
				 });	
		
				customerService.getAllBuyCustomers().then(function(response) {
				$scope.buyCustomers = response;
									
			});
			
		}
		
		
		
		if((buyCustomer !=undefined && buyCustomer!="" && buyCustomer!=null) && ((sellCustomer==undefined || sellCustomer=="" || sellCustomer==null) && 
		supplier!=undefined && supplier!="" && supplier!=null)){
			
			 console.log("1 0 1");
				customerService.getAllSellCustomersForBuyCustomerAndSupplier(buyCustomer.customerId, supplier.supplierId).then(function(response) {
					$scope.sellCustomersForCustomerSelected = response;	
				 });
				 
			customerService.getSuppliersFromBuyCustomerForDAR(buyCustomer.customerId).then(function(response) {
					$scope.suppliersForCustomerSelected = response;	
					});	
			
			customerService.getBuyCustomersFromSupplierDAR(supplier.supplierId).then(function(response) {
						$scope.buyCustomers=response;	
					});	
		 
		}
		
		
		if((buyCustomer !=undefined && buyCustomer!="" && buyCustomer!=null) && (sellCustomer !=undefined && sellCustomer!="" && sellCustomer!=null) && (supplier ==undefined || supplier=="" || supplier==null)){
		   
		   console.log("1 1 0");
		   
		   customerService.getAllBuyCustomersForSellCustomer(sellCustomer.customerId).then(function(response) {
					$scope.buyCustomers = response;
					});
			
				
					
		    customerService.getAllSuppliersFromBuyAndSellCustomer(buyCustomer.customerId,sellCustomer.customerId).then(function(response) {
				 $scope.suppliersForCustomerSelected=response;	
				 });
				 
			customerService.getAllSellCustomersForBuyCustomer(buyCustomer.customerId).then(function(response) {
				 $scope.sellCustomersForCustomerSelected = response;	
				 });
				 	 
				 
				  
		}
		
		
		if(buyCustomer !=undefined && buyCustomer!="" && buyCustomer!=null && sellCustomer !=undefined && sellCustomer!="" && sellCustomer!=null && supplier !=undefined && supplier!="" && supplier!=null){
		   
		    console.log("1 1 1");
				
				customerService.getAllSuppliersFromBuyAndSellCustomer(buyCustomer.customerId,sellCustomer.customerId).then(function(response) {
				 $scope.suppliersForCustomerSelected=response;	
				 });
				 
				 customerService.getAllSellCustomersForBuyCustomerAndSupplier(buyCustomer.customerId, supplier.supplierId).then(function(response) {
					$scope.sellCustomersForCustomerSelected = response;	
				 });
				 
				 customerService.getAllBuyCustomerFromSellCustomerAndSupplier(sellCustomer.customerId,supplier.supplierId).then(function(response) {
					$scope.buyCustomers=response;	
					});
					
			
			
		}
   }
					
		
		
		// Get Sell Customer from Buy Customer
		$scope.getSellCustomerFromBuyCustomerForDAR = function(buyCustomer)
		{
			
			var buyCustomer=JSON.parse(buyCustomer);
		
			
	if(buyCustomer !=undefined && buyCustomer!="" && buyCustomer!=null){
		customerService.getAllSellCustomersForBuyCustomer(buyCustomer.customerId).then(function(response) {
			$scope.sellCustomersForCustomerSelected = response;	
});	
	}else{
		$scope.sellCustomersForCustomerSelected=$scope.sellCustomersForCustomerSelectedOriginal; 
		$scope.suppliersForCustomerSelected=$scope.suppliersForCustomerSelectedOriginal;
	}
		
		};
		
		
		// Get Supplier from Buy Customer
		$scope.getSuppliersFromBuyCustomerForDAR = function(buyCustomer)
		{
			
			
			var buyCustomer=JSON.parse(buyCustomer);
			
			
	if(buyCustomer !=undefined && buyCustomer!="" && buyCustomer!=null){
		customerService.getSuppliersFromBuyCustomerForDAR(buyCustomer.customerId).then(function(response) {
			$scope.suppliersForCustomerSelected = response;	
		});	
	}else{
		$scope.sellCustomersForCustomerSelected=$scope.sellCustomersForCustomerSelectedOriginal; 
		$scope.suppliersForCustomerSelected=$scope.suppliersForCustomerSelectedOriginal;
	}
		
		};
		
		

		// Get Buy Customer from Sell Customer
		$scope.getBuyCustomerFromSellCustomerForDAR = function(sellCustomer)
		{
			var sellCustomer=JSON.parse(sellCustomer);
			
			if(sellCustomer !=undefined && sellCustomer!="" && sellCustomer!=null){
				customerService.getAllBuyCustomersForSellCustomer(sellCustomer.customerId).then(function(response) {
					$scope.buyCustomers=response;	
				});	
		
		
	}else{
		$scope.buyCustomers=$rootScope.buyCustomers;
		$scope.suppliersForCustomerSelected=$scope.suppliersForCustomerSelectedOriginal;
	}
		
		};
		
		
		// Get Supplier from Sell Customer
		$scope.getSuppliersFromSellCustomerForDAR = function(sellCustomer)
		{
			
			var sellCustomer=JSON.parse(sellCustomer);
			
			
	if(sellCustomer !=undefined && sellCustomer!="" && sellCustomer!=null){
		customerService.getSuppliersFromSellCustomerForDAR(sellCustomer.customerId).then(function(response) {
			$scope.suppliersForCustomerSelected = response;	
		//	console.log("==suppliersForCustomerSelected==="+JSON.stringify(suppliersForCustomerSelected));
		});	
	}else{
		$scope.buyCustomers=$rootScope.buyCustomers;
		$scope.suppliersForCustomerSelected=$scope.suppliersForCustomerSelectedOriginal;
	}
		
		};
		
		
		// Get Buy Customer from Supplier
		$scope.getBuyCustomerFromSupplierForDAR = function(supplier)
		{
			var supplier=JSON.parse(supplier);
			
			if(supplier !=undefined && supplier!="" && supplier!=null){
				customerService.getBuyCustomersFromSupplierDAR(supplier.supplierId).then(function(response) {
					$scope.buyCustomers=response;	
				});	
		
		
	}else{
		$scope.buyCustomers=$rootScope.buyCustomers;
		//$scope.suppliersForCustomerSelected=$scope.suppliersForCustomerSelectedOriginal;
	}
		
		};
		
		
		// Get Supplier from Sell Customer
		$scope.getSellCustomerFromSupplierForDAR = function(supplier)
		{
			
			var supplier=JSON.parse(supplier);
			
			
	if(supplier !=undefined && supplier!="" && supplier!=null){
		customerService.getSellCustomersFromSupplierDAR(supplier.supplierId).then(function(response) {
			$scope.sellCustomersForCustomerSelected = response;	
		
		});	
	}else{
		$scope.sellCustomersForCustomerSelected=$scope.sellCustomersForCustomerSelectedOriginal;
		
	}
		
		};
		

		$scope.dtOptionsEmptyTable= DTOptionsBuilder.newOptions()
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
		setTimeout(function(){
						$("#detailedActivityReportDataTableEmpty_wrapper tbody").css("display", "none");
					}, 0)
		
		$scope.dtOptions = DTOptionsBuilder.newOptions()
										.withOption('order', [])
											.withOption('scrollX', true)
											 .withOption('bScrollCollapse', true)
											 .withOption('bAutoWidth', true)
											//.withOption('tabIndex', '-1')				 
											.withOption('bFilter', false)
											.withOption('info', false)
											.withOption('lengthChange', false)
											.withOption('bPaginate', false)
											.withOption('ordering', false)
											.withDOM('Blfrtip');
	
		$scope.hideEmptyReportTable = function(){	
						$("#detailedActivityReportDataTableEmpty_wrapper").css("display", "none");						
					}
		setTimeout(function(){				
			$("#detailedActivityReportDataTableEmpty_wrapper tbody").css("display", "none");
		},0)
		$scope.dtOptionsRMTEmptyTable= DTOptionsBuilder.newOptions()
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
		setTimeout(function(){
						$("#rmtImportReportDataTable_wrapper tbody").css("display", "none");
					}, 0)
		
		$scope.getAllPickupsForDAR = function(buyCustomer,sellCustomerSelected,supplier ,incidentType ,startDate, endDate){
		$scope.loaderDetailedReport=true;
			if($scope.selectAllIncident == true){
				incidentType=$scope.selectAllIncidentTypeObj
			}
		var balePickupFilterDTO = {};
		
		if((buyCustomer!=undefined)&&(buyCustomer!=null)&&(buyCustomer!="") ){
				var buyCustomer =JSON.parse(buyCustomer);
		balePickupFilterDTO.customerId = buyCustomer.customerId;}
		else{
		balePickupFilterDTO.customerId=null;}
		if((sellCustomerSelected!=undefined)&&(sellCustomerSelected!=null)&&(sellCustomerSelected!="")){
			var sellCustomerSelected =JSON.parse(sellCustomerSelected);	
		balePickupFilterDTO.sellCustomerId=sellCustomerSelected.customerId;}
		else{
		balePickupFilterDTO.sellCustomerId=null;}
		if((supplier!=undefined)&&(supplier!="")&&(supplier!=null)){
			var supplier =JSON.parse(supplier);
		balePickupFilterDTO.supplierId = supplier.supplierId;}
		else{
		balePickupFilterDTO.supplierId=null;}
		if((incidentType!=undefined)&&(incidentType!=null)&&(incidentType!="")){
		balePickupFilterDTO.incidentId = incidentType.incidentTypeId;}
			else{
			balePickupFilterDTO.incidentType=null;}
	
	
	 var startDateUTC =  Date.UTC(startDate.getUTCFullYear(), startDate.getUTCMonth(),
						 startDate.getUTCDate()+1,startDate.getUTCHours(), startDate.getUTCMinutes(), startDate.getUTCSeconds());
						 startDate= new Date(startDateUTC);
						
						 var endDateUTC =  Date.UTC(endDate.getUTCFullYear(), endDate.getUTCMonth(), 
								 endDate.getUTCDate()+1,endDate.getUTCHours(), endDate.getUTCMinutes(), endDate.getUTCSeconds());
						 endDate= new Date(endDateUTC);
	
		balePickupFilterDTO.startDate = startDate;
    	balePickupFilterDTO.endDate = endDate;
		
		console.log("balePickupFilterDTO"+JSON.stringify(balePickupFilterDTO));
		
		reportService.getAllPickupsDARAndRMT(balePickupFilterDTO).then(function(response){
	
    	$scope.balePickupList = response.data;
		$scope.loaderDetailedReport=false;
		
			$scope.jsonForDAReportExport=[];
			$scope.fileNameDetailActivity = "DetailActivityReport";
			$scope.exportDetailedActivityData = [];
			$scope.exportDetailedActivityData.push(["Buy Customer", "StoreName", "Pickup Date", "Sell Customer", "Sell Customer Site", "Delivery Date", 							"Material Profile", "Bales Picked", "Bales Remaining", "Incident Type", "Incident Picture", "Driver Name"]);
			
				for(var i=0;i<$scope.balePickupList.length;i++){
						
						var ifUnknownSellCust = (($scope.balePickupList[i].deliveryDate!=undefined && $scope.balePickupList[i].releaseNo!=undefined && $scope.balePickupList[i].grossWeight!=undefined && $scope.balePickupList[i].tareWeight!=undefined ) ? 'UNKNOWN' :'');
			
						$scope.balePickupList[i].sellCustomerName = (($scope.balePickupList[i].sellCustomerName!='')? $scope.balePickupList[i].sellCustomerName : ifUnknownSellCust);
			
						 var ifUnknownSellLoc = ( ($scope.balePickupList[i].deliveryDate!=undefined && $scope.balePickupList[i].releaseNo!=undefined && $scope.balePickupList[i].grossWeight!=undefined && $scope.balePickupList[i].tareWeight!=undefined ) ? 'UNKNOWN' :'');
						 
						$scope.balePickupList[i].sellCustomerSiteName = (($scope.balePickupList[i].sellCustomerSiteName!='')? $scope.balePickupList[i].sellCustomerSiteName : ifUnknownSellLoc);
                         

						var driverName= $scope.balePickupList[i].driverFirstName+" "+	$scope.balePickupList[i].driverLastName;
						var image = $scope.balePickupList[i].imageAvailable=='Y' ? "Yes":"No";
						
						if($scope.balePickupList[i].pickupDate != undefined){
							$scope.balePickupList[i].pickupDate=$filter('date')($scope.balePickupList[i].pickupDate, 'MM/dd/yyyy');
						}else{
							$scope.balePickupList[i].pickupDate='';
						}
						
						if($scope.balePickupList[i].deliveryDate!= undefined){
							$scope.balePickupList[i].deliveryDate =$filter('date')($scope.balePickupList[i].deliveryDate, 'MM/dd/yyyy');
						}else{
							$scope.balePickupList[i].deliveryDate ='';
						}
						
						
						if($scope.balePickupList[i].balesPicked!= undefined){
							
							}else{
							$scope.balePickupList[i].balesPicked =0;
						}
						
						if($scope.balePickupList[i].balesRemaining!= undefined){
							
							}else{
							$scope.balePickupList[i].balesRemaining =0;
						}
						
						
						
						
						$scope.balePickupList[i].driverName=driverName;
						$scope.balePickupList[i].imageAvailable=image;
						
						$scope.jsonForDAReportExport.push({
							"Buy Customer":$scope.balePickupList[i].buyCustomerName,
							"StoreName":$scope.balePickupList[i].buyCustomerSiteName,
							"PickupDate":$scope.balePickupList[i].pickupDate,
							"Sell Customer":$scope.balePickupList[i].sellCustomerName,
							"Sell Customer Site":$scope.balePickupList[i].sellCustomerSiteName,
							"Delivery Date": $scope.balePickupList[i].deliveryDate,
							"Material Profile":$scope.balePickupList[i].materialDescription,
							"Bales Picked":$scope.balePickupList[i].balesPicked,
							"Bales Remaining":$scope.balePickupList[i].balesRemaining,
							"Incident Type":$scope.balePickupList[i].incidentDescription,
							"Incident Picture":image,
							"Driver Name":$scope.balePickupList[i].driverName
					
								});
								
							$scope.jsonForDAReportExport = $filter('orderBy')(
							$scope.jsonForDAReportExport, 'PickupDate');
							
							$scope.exportDetailedActivityData.push([$scope.balePickupList[i].buyCustomerName, $scope.balePickupList[i].buyCustomerSiteName, $scope.balePickupList[i].pickupDate,
				                         $scope.balePickupList[i].sellCustomerName, $scope.balePickupList[i].sellCustomerSiteName, 
				                         $scope.balePickupList[i].deliveryDate, $scope.balePickupList[i].materialDescription, $scope.balePickupList[i].balesPicked,
				                         $scope.balePickupList[i].balesRemaining, 
				                         $scope.balePickupList[i].incidentDescription,image, $scope.balePickupList[i].driverName]);

								
					}
					
					$scope.authorized=true;						
							
							 $scope.dtColumns = [
										   DTColumnBuilder.newColumn('buyCustomerName').withTitle('<span class="tr_width">Buy <span class="tableWrapping">Customer</span></span>'),
										   
										   DTColumnBuilder.newColumn('buyCustomerSiteName').withTitle('<span class="tr_width" >Store <span class="tableWrapping">Name</span></span>'),
										   
										  DTColumnBuilder.newColumn('pickupDate').withOption('sType','date').withTitle('<span class="tr_width">Pickup <span class="tableWrapping">Date</span></span>'),
										   
										   DTColumnBuilder.newColumn('sellCustomerName').withTitle('Sell Customer'),
										   
										   DTColumnBuilder.newColumn('sellCustomerSiteName').withTitle('Sell Customer Site'),
										   
										   DTColumnBuilder.newColumn('deliveryDate').withOption('sType','date').withTitle('<span class="tr_width">Delivery <span class="tableWrapping"> Date </span></span>'),
										   
										   DTColumnBuilder.newColumn('materialDescription').withTitle('<span class="tr_width">Material <span class="tableWrapping">Profile</span></span>'),
										   
										   DTColumnBuilder.newColumn('balesPicked').withTitle('<span class="tr_width">Bales <span class="tableWrapping">Picked<span></span>'),
										   
										   DTColumnBuilder.newColumn('balesRemaining').withTitle('<span class="tr_width">Bales <span class="tableWrapping">Remaining</span></span>'),
										   
										   DTColumnBuilder.newColumn('incidentDescription').withTitle('<span class="tr_width">Incident <span class="tableWrapping">Type</span></span>'),
										   
										   DTColumnBuilder.newColumn('imageAvailable').withTitle('<span class="tr_width" style="width:150px !important">Incident<span class="tableWrapping"> Picture</span></span>'),
										   
										   DTColumnBuilder.newColumn('driverName').withTitle('<span class="tr_width">Driver <span class="tableWrapping">Name</span></span>')
										  
									   ];  
									  
									    $scope.dtOptions = DTOptionsBuilder.newOptions()
										.withOption('scrollX', true)
											 .withOption('bScrollCollapse', true)
											 //.withOption('sScrollY', fasle)
											 .withOption('sScrollXInner', "100%")
											 
											 
											 .withOption('data', $scope.balePickupList);
					 
		});
    };
	
	
	
	$scope.detailedActivityReportPdf = function() {		
var data = [];		

if($scope.jsonForDAReportExport !=undefined && $scope.jsonForDAReportExport.length >0){

data=$scope.jsonForDAReportExport;	
	var doc = new jsPDF('p', 'mm', [550, 500]);
	 doc.setFontSize(13);
	doc.autoTable(headerTableDAReport, data, {	
		styles : {
			overflow : 'linebreak'
		},
	
 
  margin: { top: 10, left: 10, right: 10, bottom: 0 },
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
	doc.save('DetailedActivityReport.pdf');
}


		
}
	
	
	
	
	var headerTableDAReport = [		
                     { title: "BUY CUSTOMER", dataKey: "Buy Customer" },		
                     { title: "STORE NAME", dataKey: "StoreName" },		
                     { title: "PICKUP DATE", dataKey: "PickupDate" },		
                     { title: "SELL CUSTOMER", dataKey: "Sell Customer" },		
                     { title: "SELL CUSTOMER SITE", dataKey: "Sell Customer Site" },	
					 { title: "DELIVERY DATE" , dataKey: "Delivery Date"},			
                     { title: "MATERIAL PROFILE", dataKey: "Material Profile" },
                     { title: "BALES PICKED", dataKey: "Bales Picked" },	
					 { title: "BALES REMAINING", dataKey: "Bales Remaining" },		
                     { title: "INCIDENT TYPE", dataKey: "Incident Type" },		
                     { title: "INCIDENT PICTURE", dataKey: "Incident Picture" },		
                     { title: "DRIVER NAME", dataKey: "Driver Name" }	
                ];
	
	
	
	//END OF DETAILED ACTIVITY Report
	
	
	//START OF RMT IMPORT REPORT
	
	
	$scope.StartDateFunRMT = function() {
						$scope.StartDateRMT.opened=true;
						$scope.dateOptionsStart = {
							datepickerMode : 'day',
							minMode : 'day',
							startingDay : 1,
							showWeeks : false,
							formatMonth : 'MMM',
							formatYear : 'yyyy',
							monthColumns : 4,
						}
						$scope.$watch("startDateRMT", function(newValue, oldValue) {		
							$("#startDateRMT_popup").focus();
							});
					};
					$scope.StartDateRMT = {
						opened : false
					}; 
	
	 $scope.EndDateFunRMT = function() {
		 
					   $scope.EndDateRMT.opened = true;
					$scope.dateOptionEnd = {
							datepickerMode:'day',
							minMode:'day',
							startingDay: 1,
							showWeeks:false,
							formatMonth:'MMM',
							formatYear: 'yyyy',
							monthColumns:4,
					 }
					 $scope.$watch("startDateRMT", function(newValue, oldValue) {		
							$("#endDateRMT_popup").focus();
							});
				  };

				  $scope.EndDateFunRMT = {
					opened: false
					
				};
	
			// var buyCustomerSelectedRMT={};
			// var sellCustomerSelectedRMT={};
			// var startDate={};
			// var endDate={};
			
	
    $scope.getBalePickupsRMT = function(buyCustomerSelectedRMT ,sellCustomerSelectedRMT ,startDateRMT, endDateRMT){
		 
	    $scope.loaderRmtReport=true;
    	var balePickupFilterDTO = {};
		console.log("sellCustomerSelectedRMT"+JSON.stringify(sellCustomerSelectedRMT));
		
		if(($scope.buyCustomerSelectedRMT!=null)&&($scope.buyCustomerSelectedRMT!=undefined)&& ($scope.buyCustomerSelectedRMT!="")){
    	var buyCustomer =JSON.parse($scope.buyCustomerSelectedRMT);
		balePickupFilterDTO.customerId = buyCustomer.customerId;}
		else
		balePickupFilterDTO.customerId=null;
	
		if(($scope.sellCustomerSelectedRMT!=null)&&($scope.sellCustomerSelectedRMT!=undefined) && ($scope.sellCustomerSelectedRMT!="")){
		var sellCustomer =JSON.parse($scope.sellCustomerSelectedRMT);
		balePickupFilterDTO.sellCustomerId = sellCustomer.customerId;}
		else
		balePickupFilterDTO.sellCustomerId=null;		
		
		 var startDateUTC =  Date.UTC(startDateRMT.getUTCFullYear(), startDateRMT.getUTCMonth(),
						 startDateRMT.getUTCDate()+1,startDateRMT.getUTCHours(), startDateRMT.getUTCMinutes(), startDateRMT.getUTCSeconds());
						 startDateRMT= new Date(startDateUTC);
						
						 var endDateUTC =  Date.UTC(endDateRMT.getUTCFullYear(), endDateRMT.getUTCMonth(), 
								 endDateRMT.getUTCDate()+1,endDateRMT.getUTCHours(), endDateRMT.getUTCMinutes(), endDateRMT.getUTCSeconds());
						 endDateRMT= new Date(endDateUTC);
		
		balePickupFilterDTO.startDate = startDateRMT;
    	balePickupFilterDTO.endDate = endDateRMT;
	
		console.log("$scope.balePickupFilterDTO"+ JSON.stringify(balePickupFilterDTO));
    	reportService.getAllPickupsDARAndRMT(balePickupFilterDTO).then(function(response){
    		$scope.balePickupListRMT = response.data;
		    $scope.loaderRmtReport= false;
			//console.log("$scope.balePickupListRMT"+ JSON.stringify($scope.balePickupListRMT));
			
		$scope.jsonForRMTReportExport=[];		
		$scope.fileNameRMTReport = "RMT Import Report";
		$scope.exportRMTReportData = [];
		$scope.exportRMTReportData.push(["Date", "Vendor Code", "Customer Code", "Material Code", "Release No", "Reference", "Notes", "Weight", "Ex. Rebate", "Ex. Sale", "Ex. Cost", "Bales", "Bales Remaining", "Destination Weight"]);
		
					for(var i=0;i<$scope.balePickupListRMT.length;i++){
						
						
					
						$scope.balePickupListRMT[i].reference=$scope.balePickupListRMT[i].reference == undefined ? "" : $scope.balePickupListRMT[i].reference;
						$scope.balePickupListRMT[i].releaseNo=$scope.balePickupListRMT[i].releaseNo == undefined ? "" : $scope.balePickupListRMT[i].releaseNo;
						
						
						var ifUnknownSellLoc = ( ($scope.balePickupListRMT[i].deliveryDate!=undefined && $scope.balePickupListRMT[i].releaseNo!=undefined && $scope.balePickupListRMT[i].grossWeight!=undefined && $scope.balePickupListRMT[i].tareWeight!=undefined ) ? 'UNKNOWN' :'');
						 
						$scope.balePickupListRMT[i].sellCustomerSiteName = (($scope.balePickupListRMT[i].sellCustomerSiteName!='')? $scope.balePickupListRMT[i].sellCustomerSiteName : ifUnknownSellLoc);
                         
						 
						if($scope.balePickupListRMT[i].pickupDate != undefined){
							$scope.balePickupListRMT[i].pickupDate=$filter('date')($scope.balePickupListRMT[i].pickupDate, 'MM/dd/yyyy');
							
						}else{
							$scope.balePickupListRMT[i].pickupDate='';
						}				
										
										
						var vendor_code=(($scope.balePickupListRMT[i].buyCustomerSiteAlternateSearchReference !=null) ? $scope.balePickupListRMT[i].buyCustomerSiteAlternateSearchReference : $scope.balePickupListRMT[i].buyCustomerAlternateSearchReference);				
										
						var customer_code=(($scope.balePickupListRMT[i].sellCustomerSiteAlternateSearchReference !=null) ? $scope.balePickupListRMT[i].sellCustomerSiteAlternateSearchReference : $scope.balePickupListRMT[i].sellCustomerAlternateSearchReference);
						
						var weight=($scope.balePickupListRMT[i].avgBaleWeight * $scope.balePickupListRMT[i].balesPicked);
						
						var destination_weight=(($scope.balePickupListRMT[i].grossWeight !=undefined ? $scope.balePickupListRMT[i].grossWeight : 0) - 
							($scope.balePickupListRMT[i].tareWeight !=undefined ?$scope.balePickupListRMT[i].tareWeight:0));
						
						var balesRemaining = $scope.balePickupListRMT[i].balesRemaining!=undefined ? $scope.balePickupListRMT[i].balesRemaining:0;
						var balesPicked = $scope.balePickupListRMT[i].balesPicked!=undefined ? $scope.balePickupListRMT[i].balesPicked:0;
						
						
						$scope.balePickupListRMT[i].vendor_code=vendor_code;
						
						$scope.balePickupListRMT[i].customer_code=customer_code
						$scope.balePickupListRMT[i].weight=weight;
						$scope.balePickupListRMT[i].destination_weight=	destination_weight;
						$scope.balePickupListRMT[i].balesPicked = balesPicked;
						$scope.balePickupListRMT[i].balesRemaining = balesRemaining;
						
						$scope.jsonForRMTReportExport.push({
							"Pickup Date":$scope.balePickupListRMT[i].pickupDate,
							"Vendor Code":vendor_code,			
							"Customer Code":customer_code,
							"Material Code":$scope.balePickupListRMT[i].materialShortName,
							"Release No":$scope.balePickupListRMT[i].releaseNo,
							"Reference":"",
							"Notes":"",
							"Weight": weight,
							"Ex. Rebate":"",
							"Ex. Sale":"",
							"Ex. Cost":"",
							"Bales":$scope.balePickupListRMT[i].balesPicked,
							"Bales Remaining":$scope.balePickupListRMT[i].balesRemaining,
							"Destination Weight":destination_weight
								});
							
							$scope.exportRMTReportData.push([$scope.balePickupListRMT[i].pickupDate, $scope.balePickupListRMT[i].vendor_code, $scope.balePickupListRMT[i].customer_code,
				                         $scope.balePickupListRMT[i].materialShortName, $scope.balePickupListRMT[i].releaseNo, 
				                         $scope.balePickupListRMT[i].reference, $scope.balePickupListRMT[i].notes, $scope.balePickupListRMT[i].weight,
				                         $scope.balePickupListRMT[i].exRebate, $scope.balePickupListRMT[i].exSale, $scope.balePickupListRMT[i].exCost,
				                         $scope.balePickupListRMT[i].balesPicked, $scope.balePickupListRMT[i].balesRemaining,$scope.balePickupListRMT[i].destination_weight]);
					
					}
					
						$scope.authorizedRMT=true;						
							
							 $scope.dtColumnsRMT = [
										   DTColumnBuilder.newColumn('pickupDate').withOption('sType','date').withTitle('<span class="tr_width">Pickup <span class="tableWrapping">Date</span></span>'),
										   
										   DTColumnBuilder.newColumn('buyCustomerSiteName').withTitle('<span class="tr_width">Buy <span class="tableWrapping">Customer</span><span class="tableWrapping">Site </span></span>'),
										   
										   DTColumnBuilder.newColumn('sellCustomerSiteName').withTitle('Sell Customer Site '),
										   
										   DTColumnBuilder.newColumn('materialDescription').withTitle('<span class="tr_width">Material <span class="tableWrapping">Profile</span>	</span>'),
										   
										   DTColumnBuilder.newColumn('reference').withTitle('Reference'),
										   DTColumnBuilder.newColumn('weight').withTitle('Weight'),
										   DTColumnBuilder.newColumn('balesPicked').withTitle('<span class="tr_width">Bales <span class="tableWrapping">Picked</span></span>'),
										   DTColumnBuilder.newColumn('balesRemaining').withTitle('<span class="tr_width">Bales <span class="tableWrapping">Remaining</span></span>'),
										   
										   DTColumnBuilder.newColumn('releaseNo').withTitle('<span class="tr_width">Release <span class="tableWrapping">No</span></span>'),
										   
										   DTColumnBuilder.newColumn('destination_weight').withTitle('<span class="tr_width">Destination <span class="tableWrapping">Weight</span></span>')
										  
									   ];  
									  
									    $scope.dtOptionsRMT = DTOptionsBuilder.newOptions()
										.withOption('scrollX', true)
											 .withOption('bScrollCollapse', true)
											 .withOption('bAutoWidth', true)
										.withOption('data', $scope.balePickupListRMT);
		
		
    	});
    };
	
	
$scope.downloadRMTPdf = function() {	
var data = [];		


if($scope.jsonForRMTReportExport !=undefined && $scope.jsonForRMTReportExport.length >0){


data=$scope.jsonForRMTReportExport;			
	var doc = new jsPDF('p', 'mm', [650, 550]); 
	 doc.setFontSize(8);
	doc.autoTable(headerTableRMTImportReport, data, {	
	styles : {
			overflow : 'linebreak'
		},
	
 
  margin: { top: 50, left: 20, right: 20, bottom: 0 },
   drawHeaderCell: function (cell, data) {
	    if (cell.raw === 'ID') {
	        cell.styles.fontSize= 8;
	       cell.styles.textColor = [255,0,0];
	    } else {
	        cell.styles.textColor = 255;
	        cell.styles.fontSize = 8;

	    }
	},
	   createdCell: function (cell, data) {
	    if (cell.raw === 'Jack') {
	       cell.styles.fontSize= 8;
	       cell.styles.textColor = [255,0,0];
	    } 
	}

});		
	doc.save('RMT Import Report.pdf');	
}
	
}
	
	
	
	
	var headerTableRMTImportReport = [		
                     { title: "DATE", dataKey: "Pickup Date" },		
                     { title: "VENDOR CODE", dataKey: "Vendor Code" },		
					 { title: "CUSTOMER CODE", dataKey: "Customer Code" },		
                     { title: "MATERIAL CODE", dataKey: "Material Code" },		
                     { title: "RELEASE NO", dataKey: "Release No" },
					 { title: "REFERENCE", dataKey: "Reference" },
                     { title: "NOTES", dataKey: "Notes" },	
					 { title: "WEIGHT", dataKey: "Weight" },		
                     { title: "EX. REBATE", dataKey: "Ex. Rebate" },		
                     { title: "EX. SALE", dataKey: "Ex. Salee" },		
                     { title: "EX. COST", dataKey: "Ex. Cost" },
					 { title: "BALES", dataKey: "Bales" },		
                     { title: "BALES REMAINING", dataKey: "Bales Remaining" },
					 { title:"DESTINATION WEIGHT",dataKey:"Destination Weight"	}				 
                ];
	
	
	//END of RMT REPORT
	
	
	
	
	
	
	
	
	
	
	
    $scope.getPendingBalePickups = function(buyCustomerId,supplierId){

	$scope.loaderPendingReport = true;

    	var PendingStoreReportDTO = {};
    	PendingStoreReportDTO.buyCustomerId = buyCustomerId.customerId;
    	PendingStoreReportDTO.supplierId = supplierId.supplierId;
    	reportService.getPendingPickups(PendingStoreReportDTO).then(function(response){
    	$scope.balePickupLists = response.data;
		$scope.loaderPendingReport = false;
    	});
    };
    $scope.EndDateFun = function() {
    	$scope.EndDate.opened = true;
    	$scope.dateOptionsss = {
            datepickerMode:'day',
            minMode:'day',
            startingDay: 1,
            showWeeks:false,
            formatMonth:'MMM',
            formatYear: 'yyyy',
            monthColumns:4,
            }
  };
  $scope.EndDate = {
    opened: false
    };
   $scope.StartDateFun = function() {
  $scope.StartDate.opened = true;
    $scope.dateOptionsStart = {
            datepickerMode:'day',
            minMode:'day',
            startingDay: 1,
            showWeeks:false,
            formatMonth:'MMM',
            formatYear: 'yyyy',
            monthColumns:4,
            }
    };
    $scope.StartDate = {
    opened: false
  };
   $scope.EndDateFunRMT = function(){
	   $scope.EndDateRMT.opened = true;
    $scope.dateOptionsEnd = {
            datepickerMode:'day',
            minMode:'day',
            startingDay: 1,
            showWeeks:false,
            formatMonth:'MMM',
            formatYear: 'yyyy',
            monthColumns:4,
     }
  };
  $scope.EndDateRMT = {
    opened: false
    };
   $scope.StartDateFunRMT = function() {
  $scope.StartDateRMT.opened = true;
    $scope.dateOptionsStart = {
            datepickerMode:'day',
            minMode:'day',
            startingDay: 1,
            showWeeks:false,
            formatMonth:'MMM',
            formatYear: 'yyyy',
            monthColumns:4,
     }
    };
    $scope.StartDateRMT = {
    opened: false
  };
   
  
		 
		
		
		
		
		
		$scope.pendingResolutionAction = true;
		$scope.pendingResolutionActionClose = function(){
		$scope.pendingResolutionAction =false;
		}
		
		
	$scope.checkValidDate=function(){
	
	
	// Detail activity report
	var darStartDateValueFromId= $("#startDARDate_popup").val();	
	var darEndDateValueFromId= $("#endDARDate_popup").val();	
	
	
	$scope.invalidDARStartDateError=undefined;
	$scope.invalidDAREndDateError=undefined;
	
	if(darStartDateValueFromId != ''){
		if(darStartDateValueFromId != undefined  && ($scope.startDARDate ==undefined || $scope.startDARDate ==null) ){	
			$scope.invalidDARStartDateError="Invalid Start Date";
			$scope.startDARDate=firstDay;
		}
	}
	
	if(darEndDateValueFromId != ''){
		if(darEndDateValueFromId != undefined  && ($scope.endDARDate ==undefined || $scope.endDARDate ==null) ){	
			$scope.invalidDAREndDateError="Invalid End Date";
			$scope.endDARDate=lastDay;
		}
	}
	
	$scope.checkSubmitBtn();
	
	
	// RMT
	var rmtStartDateValueFromId= $("#startDateRMT_popup").val();	
	var rmtEndDateValueFromId= $("#endDateRMT_popup").val();	
	
	
	$scope.invalidRMTStartDateError=undefined;
	$scope.invalidRMTEndDateError=undefined;
	
	if(rmtStartDateValueFromId != ''){
		if(rmtStartDateValueFromId != undefined  && ($scope.startDateRMT ==undefined || $scope.startDateRMT ==null) ){	
			$scope.invalidRMTStartDateError="Invalid Start Date";
			$scope.startDateRMT=firstDay;
		}
	}
	
	if(rmtEndDateValueFromId != ''){
		if(rmtEndDateValueFromId != undefined  && ($scope.endDateRMT ==undefined || $scope.endDateRMT ==null) ){	
			$scope.invalidRMTEndDateError="Invalid End Date";
			$scope.endDateRMT=lastDay;
		}
	}
	
	}	
	
$scope.dateValidation=function(){
	
	if($scope.startDARDate!=undefined && $scope.endDARDate!=undefined){
		if($scope.startDARDate.getTime() > $scope.endDARDate.getTime() ){
			$scope.endDARDate=$scope.startDARDate;
		//	$scope.endDateErrorMessage=true;
		}else{
			$scope.endDateErrorMessage=false;
		}
	}
	
	if($scope.startDateRMT!=undefined && $scope.endDateRMT!=undefined){
		if($scope.startDateRMT.getTime() > $scope.endDateRMT.getTime() ){
			$scope.endDateRMT=$scope.startDARDate;
		//	$scope.endDateErrorMessage=true;
		}else{
			$scope.endDateErrorMessage=false;
		}
	}
		
}
		
		  
    $scope.startDateOptionsDAR = {
  };
  
   $scope.$watch("startDARDate", function(newStart) {
     $scope.endDateOptionsDAR = {
      minDate: new Date($scope.startDARDate)
   }
    
      if (newStart > $scope.endDARDate) {
        $scope.endDARDate = newStart;
      }
 }) ;
 
 $scope.startDateOptionsRMT = {
  };
  
   $scope.$watch("startDateRMT", function(newStart) {
     $scope.endDateOptionsRMT = {
      minDate: new Date($scope.startDateRMT)
   }
    
      if (newStart > $scope.endDateRMT) {
        $scope.endDateRMT = newStart;
      }
 }) ;
 
 
		
	
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

//===============	

		
				  $interval(function() {

						 // var downloadExcelTooltip = angular.element( document.querySelector( "[title='Download Excel']" ) ).tooltip();                                           
						  //var downloadExcelTooltip = angular.element( document.querySelector( "[title='Download PDF']" ) ).tooltip();
						  var detailedActivityReportDataTable_filterTabIndex = angular.element( document.querySelectorAll( "#detailedActivityReportDataTable_filter input" ) );
								detailedActivityReportDataTable_filterTabIndex.prop('tabindex','-1');
								var pendingStoreReportDataTable_filterTabIndex = angular.element( document.querySelectorAll( "#rmtImportReportDataTable_filter input" ) );
								pendingStoreReportDataTable_filterTabIndex.prop('tabindex','-1');	
						  
				  },1000);
				  
				  
					
				$scope.addTabIndex = function (){
							$timeout(function(){
								var detailedActivityReportDataTable_lengthTabindex = angular.element( document.querySelector( "[name='detailedActivityReportDataTable_length']" ) );
								detailedActivityReportDataTable_lengthTabindex.prop('tabindex','15');
								
								var rmtImportReportDataTable_lengthTabindex = angular.element( document.querySelector( "[name='rmtImportReportDataTable_length']" ) );
								rmtImportReportDataTable_lengthTabindex.prop('tabindex','13');	
								
								var paginate_buttonTabIndex = angular.element( document.querySelectorAll( ".paginate_button" ) );
								paginate_buttonTabIndex.prop('tabindex','16');
								
								var detailedActivityReportDataTable_filterTabIndex = angular.element( document.querySelectorAll( "#detailedActivityReportDataTable_filter input" ) );
								detailedActivityReportDataTable_filterTabIndex.prop('tabindex','-1');
								var pendingStoreReportDataTable_filterTabIndex = angular.element( document.querySelectorAll( "#rmtImportReportDataTable_filter input" ) );
								pendingStoreReportDataTable_filterTabIndex.prop('tabindex','-1');
								
								
								
							},1000);
						}
						$scope.StartupDateDARFun_tabindex =function(){
						$(".StartupDateDARFun_tabindex1").prop('tabindex','0');
						}
						$scope.endDateDARFun_tabindex =function(){
						$(".StartupDateDARFun_value").prop('tabindex','9');
						$(".endDateDARFun_tabindex1").prop('tabindex','0');
						}
						$scope.StartDateFunRMT_tabindex =function(){
						$(".StartDateFunRMT_tabindex1").prop('tabindex','0');
						}
						$scope.endDateFunRMT_tabindex =function(){
						$(".startDateFunRMT_value").prop('tabindex','9');
						$(".endDateFunRMT_tabindex1").prop('tabindex','0');
						}
				  
					
					$(document).ready(function(){
						$('[data-toggle="tooltip"]').tooltip();   
					});
					$('[data-toggle="tooltip"]').on('shown.bs.tooltip', function () {
					  // do something…
					  $timeout(function(){
						$('[data-toggle="tooltip"]').tooltip('hide')
						},3000)
					});
					$scope.executeExcel_detailedActivity=function(){
						$('#idetailedactivityExcelData').keypress(function (e) {
							 var key = e.which;
							 if(key == 13)  // the enter key code
							  {
								$('#idetailedactivityExcelData').click();
								return false;  
							  }
							});
					} 
					$scope.executeExcel_rmtimport=function(){
						$('#rmtimportExcelData').keypress(function (e) {
							 var key = e.which;
							 if(key == 13)  // the enter key code
							  {
								$('#rmtimportExcelData').click();
								return false;  
							  }
							});
					}
					
					$scope.removeInputValueDetailActivity = function(){					
					$scope.dtOptions=DTOptionsBuilder.newOptions()
					.withOption('bFilter', true);
				}
				$scope.removeInputValueRMTimport = function(){					
							$scope.dtOptionsRMT=DTOptionsBuilder.newOptions()
							.withOption('bFilter', true);
							$scope.invalidRMTEndDateError= false;
				}
				$scope.rmtfutureDate= function(){
					
				var rmtStartDateValueFromId= $("#startDateRMT_popup").val();	
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
							
						}else if(diff>10){
									$scope.invalidRMTStartDateError="Select upto 10 years";
									$scope.startDateRMT=firstDay;
									$scope.endDateRMT=lastDay;
									
								}
							}
							var rmtStartDateValueFromId= $("#startDateRMT_popup").val();	
							dateold = new Date(firstDay);
							datenew = new Date(rmtStartDateValueFromId);
							dateDiff(dateold, datenew);
			}
				// $scope.rmtPastDate=function() {
							// var UserDate = $("#startDateRMT_popup").val();
							// var ToDate = new Date();
							  
							// if (new Date(UserDate).getTime() < ToDate.getTime()) {
								// $scope.invalidRMTStartDateError="Select upto 10 years";
								// $scope.startDateRMT=firstDay;
							// }
					// }
				
			$scope.rmtfutureToDate= function(){
					
				var rmtEndDateValueFromId= $("#endDateRMT_popup").val();	
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
							
								}else if(diff>10){
									$scope.invalidRMTEndDateError="Select upto 10 years";
									$scope.endDateRMT=lastDay;
									
								}
							}
							var rmtEndDateValueFromId= $("#endDateRMT_popup").val();	
							dateold = new Date(firstDay);
							datenew = new Date(rmtEndDateValueFromId);
							dateDiff(dateold, datenew);
			}
				// $scope.rmtPastToDate=function() {
							// var UserDate = $("#endDateRMT_popup").val();	
							// var ToDate = new Date();
							  
							// if (new Date(UserDate).getTime() < ToDate.getTime()) {
								// $scope.invalidRMTEndDateError="Select upto 10 years";
								// $scope.endDateRMT=lastDay;
							// }
					// }
					
				
			$scope.detailActivityfutureDate= function(){
				var darStartDateValueFromId= $("#startDARDate_popup").val();
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
							
						}else if(diff>10){
									$scope.invalidDARStartDateError="Select upto 10 years";
									$scope.startDARDate=firstDay;
									$scope.endDARDate=lastDay;
								}
							}
							var darStartDateValueFromId= $("#startDARDate_popup").val();	

							dateold = new Date(firstDay);
							datenew = new Date(darStartDateValueFromId);
							dateDiff(dateold, datenew);					
			}
				// $scope.pdetailActivityPastFromDate=function() {
							// var UserDate = $("#startDARDate_popup").val();
							// var ToDate = new Date();
							  
							// if (new Date(UserDate).getTime() < ToDate.getTime()) {
								// $scope.invalidDARStartDateError="Select upto 10 years";
								// $scope.startDARDate=firstDay;
							// }
					// }
					
				
			$scope.detailActivityTofutureDate= function(){
				var darEndDateValueFromId= $("#endDARDate_popup").val();
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
						}else if(diff>10){
									$scope.invalidDAREndDateError="Select upto 10 years";
									$scope.endDARDate=lastDay;
								}
							}
							var darEndDateValueFromId= $("#endDARDate_popup").val();	

							dateold = new Date(firstDay);
							datenew = new Date(darEndDateValueFromId);
							dateDiff(dateold, datenew);					
			}
				// $scope.pdetailActivityPastToDate=function() {
							// var UserDate = $("#endDARDate_popup").val();
							// var ToDate = new Date();
							  
							// if (new Date(UserDate).getTime() < ToDate.getTime()) {
								// $scope.invalidDAREndDateError="Select upto 10 years from now";
								// $scope.endDARDate=lastDay;
							// }
					// }				
					
	});

	
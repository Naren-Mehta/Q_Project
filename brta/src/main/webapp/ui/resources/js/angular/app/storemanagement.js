brtaApp
		.controller(
				"storeManagementController",
				function(applicationContextURL, $scope, $rootScope, $http,$timeout, $interval, $filter, userService, customerService, supplierService,
						balepickupService, storeConfigService,frequencyService,
						pickupAssignmentService,DTOptionsBuilder, DTColumnBuilder,Idle, Keepalive, $uibModal,$compile,$routeParams,$route,$location) {


					$scope.supplierSelectedAct=undefined;	
					$scope.$route = $route;
					$scope.storeListViewValid=false;
					$scope.storeSupplierViewValid=false;
					$scope.assignmentViewValid=false;
					$scope.setDataLabel="Configure";
					$scope.isSupplierRole=false;
					$scope.disableSupplier = false;
					$scope.enableSupplier = true;
					$scope.enableAssignemnt = false;
					$scope.disableAssignemnt = true;
					 var defaultSupplierId = undefined; 
					 var role=window.role;
					 var supplierId=window.supplierId;
					 
					 if($rootScope.user ==undefined){
					 		$rootScope.user={};
					 	 }
					 	$rootScope.user.role = window.role;					 	 	
					 	
					$rootScope.supplierId=supplierId;
					$scope.loaderStoreSupplierList=false;
					
					 $scope.redirect = function(){
					     if($rootScope.user.role =='Supplier'){
					      window.location = "#/storesupplierForSupplier";
					      }else{
					        window.location = "#/storemanagement";
					      }
					  }
					  var currentUrl=$location.url();
						if(currentUrl.indexOf("storemanagement")!=-1 || currentUrl.indexOf("storesupplierlist")!=-1 || currentUrl.indexOf("assignments")!=-1 ||
						currentUrl.indexOf("storesupplierForSupplier")!=-1 || currentUrl.indexOf("assignmentForSupplier")!=-1 ){
							$(".pickup_menu a").attr("class", "");
						$(".user_menu a").attr("class", "");
						$(".incident_menu a").attr("class", "");
						$(".report_menu a").attr("class", "");
						$(".needHelp_menu a").attr("class", "");
						$(".store_menu a").attr("class", "active");						
						}
					 
					 
					if($rootScope.user.role =='Supplier'){
						$scope.isSupplierRole=true;
						$scope.loaderStoreSupplierList = true;		
						$scope.storeListView=false;
						$scope.assignActivityTab=true;
						$scope.disableSupplier = true;
						$scope.enableSupplier = false;
						$scope.enableAssignemnt = true;
						$scope.disableAssignemnt = false;
						$scope.storeManagementList = false;
						$scope.storeManagementSupplier = true;
						}	
						if($rootScope.user.role !='Supplier'){
						 $scope.storeManagementList = true;
						 $scope.storeManagementSupplier = false;
					}	
					
					
$scope.supplierselectedDayDrivers = [];
$scope.supplierselectedNightDrivers =[] ;

		$scope.setDataLoader=false;
		$scope.started = false;
		$scope.endDateErrorMessage=false;
		$scope.endDateSetDataPopupErrorMessage=false;
		
		$scope.storeManagementAssignments = false;
		
		$scope.storeManagementListOpen = function(){
			$scope.storeManagementList = true;
			$scope.storeManagementSupplier = false;
			$scope.storeManagementAssignments = false;
		}
		$scope.storeManagementSupplierOpen = function(){
			$scope.storeManagementList = false;
			$scope.storeManagementSupplier = true;
			$scope.storeManagementAssignments = false;
		}
		$scope.storeManagementAssignmentsOpen = function(){
			$scope.storeManagementList = false;
			$scope.storeManagementSupplier = false;
			$scope.storeManagementAssignments = true;
		}
		

			 

			
var date=new Date();			
var firstDay = new Date(date.getFullYear(), date.getMonth() ,1);
var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);

$scope.startDate=$scope.startDateSSL=$scope.assignmentDateStart=firstDay;
$scope.endDate=lastDay;
				
					$scope.driverSupplierView={};
					$scope.loaderStoreList=false;
					
					$scope.loaderAssignment=false;
					$scope.loaderAssignment=false;
					$scope.storeFilteredItems=[];
					$scope.customerSite=false;
					$scope.customerSite.checked=false;
					//$scope.storeListView = true;
					var currentUrl=$location.url();
					
					
					if(!$scope.isSupplierRole){
						$scope.storeListView=true;
					$scope.assignActivityTab=false;
					$scope.assignment=false;
					}else{
						$scope.storeListView=false;
					$scope.assignActivityTab=true;
					$scope.assignment=false;
					}
					
					
					
					if(currentUrl.indexOf("storemanagement")!=-1 ){
						$scope.storeListView=true;
					$scope.assignActivityTab=false;
					$scope.assignment=false;
					}else if(currentUrl.indexOf("storesupplierlist")!= -1 || currentUrl.indexOf("storesupplierForSupplier")!=-1){
						$scope.storeListView=false;
					$scope.assignActivityTab=true;
					$scope.assignment=false;
					}else if( currentUrl.indexOf("assignment")!=-1 || currentUrl.indexOf("assignmentForSupplier")!=-1){
						$scope.storeListView=false;
					$scope.assignActivityTab=false;
					$scope.assignment=true;
					}
					
					$scope.storeListCheckAllBtn=true;
					$scope.storeSupplierCheckAllBtn = true;
					$scope.storeListAllAssignBtn=true;
					$scope.setFrequencyPopup = false;
					$scope.loaderActiveActivityList = false;
					$scope.supplierSite = {};
					$scope.material = {};
					$scope.setDataBtn = true;
					$scope.setDataApplyBtn= true;
					$scope.assignBtn = true;
					$scope.assignActivityPopupBtn = true;
					$scope.storeListViewBtn = true; 

					$scope.updateFieldsForAssignActivity={};
					$scope.updateAssignmentPopup={};
					$scope.updateAssignDriverPopup={};
					$scope.setDatapopup={};
					$scope.buyCustomerSelectedAct={};
					$scope.assignmentSupplierSelected=undefined;
					$scope.setDatapopup.supplierSelected=[];
					$scope.setDatapopup.balePickupStartDate=new Date();
					$rootScope.materialsProfile=[];
					$rootScope.sellCustomer=[];
					$rootScope.materials=[];
					$rootScope.sellCustomersForCustomerSelected=[];
					$rootScope.materialsSupplierProfile=[];
					$rootScope.storeSupplierMaterial=[];
					$rootScope.suppliermatchdata=[];
					$rootScope.customerSiteIDs=[];
					$rootScope.saved=[];
					$rootScope.customerId=[];	
					$rootScope.supplierFound=[];
					$scope.sellCustomerFound=[];
					$scope.foundMaterials=[];
					$scope.sellCustomerSiteFound=[];
					$scope.sellCustomerPopupFound=[];
					$scope.disablestoreListViewBtn =true;		
					
					
					if($rootScope.user.role=="Supplier"){		
									
					 $scope.disablestoreListViewBtn=false;		
							
						}else{		
							 $scope.disablestoreListViewBtn=true;		
						}
					
				
						
if ($scope.startDate != undefined && $scope.endDate != undefined) {
							$scope.storeListViewBtn = false;
						}

											
					customerService.getAllBuyCustomers().then(
							function(response) {
								$scope.buyCustomers = response;
								$rootScope.buyCustomers=response;
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
   
   
    $scope.getCustomersFromSupplier =function (selectedSupplierId){
		console.log("selectedSupplierId"+selectedSupplierId);
		
		customerService.getBuyCustomersFromSupplierDAR(selectedSupplierId).then(function(response) {
						$scope.buyCustomers=response;	
					});
										
   }
   
   
   	$scope.getBuyCustomerDetails = function(buyCustomer) {

		
	if(!$scope.isSupplierRole){
		if (buyCustomer != null) {
			
						customerService.getSuppliersFromBuyCustomerForDAR(buyCustomer.customerId).then(function(response) {
							$scope.suppliersForCustomerSelected = response;		
						});
							
							customerService.getMatrialsForBuyCustomer(
									buyCustomer.customerId).then(function(response) {
								$scope.commodities = response;
							});

						} else {
							$scope.suppliersForCustomerSelected = $scope.suppliersForCustomerSelectedOriginal;
						}

	}
						
					

						$scope.loaderBalePickup = false;
						
						
					};
					
					
					supplierService.getAllSuppliers().then(function(response) {
								$rootScope.allSuppliers=$scope.suppliersForCustomerSelected=$scope.suppliersForCustomerSelectedOriginal=response;
								$rootScope.allSuppliersSelectedDefaultValue=$scope.suppliersForCustomerSelected;	
								if($rootScope.user.role =="Supplier"){	
									for(var p=0;p<$rootScope.allSuppliersSelectedDefaultValue.length;p++){								
										if($rootScope.supplierId == $rootScope.allSuppliersSelectedDefaultValue[p].supplierId){
											 defaultSupplierId=$rootScope.allSuppliersSelectedDefaultValue[p].supplierId;
										$scope.assignmentSupplierSelected =	$scope.supplierSelectedAct =JSON.stringify($rootScope.allSuppliersSelectedDefaultValue[p]);
									
											
												break;
										}
										}
										console.log("$scope.supplierSelectedAct.supplierId"+defaultSupplierId);
									$scope.getCustomersFromSupplier(defaultSupplierId);
										}
										$scope.loaderStoreSupplierList=false;
						});
							
					$scope.frequencyTimeValue = "2";
					
					
					$scope.getBuyCustomersFromSupplier=function(supplier){
												
			
						if(supplier !=undefined && supplier!="" && supplier!=null){
							customerService.getBuyCustomersFromSupplierDAR(supplier.supplierId).then(function(response) {
								$scope.buyCustomers=response;	
							});	
						}else{
							$scope.buyCustomers=$rootScope.buyCustomers
							
							

						}
					}
					
					
					$scope.updateCustomerSite=function(dataVal,modelValue){
						
						$scope.modeltemp.selectall=false;
						var currentCustomerSite = $filter('filter')($scope.customerSites, function (d) {return d.customerSiteId === dataVal;})[0];
						
						$scope.addToSelectedCustomerSites(currentCustomerSite,modelValue);
					}
					
					$scope.updateCustomerSiteForSSV=function(dataVal,modelValue){
						
						$scope.Suppliertemp.selectall=false;
						var currentCustomerSite = $filter('filter')($scope.baleAssignments, function (d) {return d.sequenceNo === dataVal;})[0];
						
						$scope.addToSelectedStoreSupplierList(currentCustomerSite,modelValue);
					}
					
					$scope.getCustomerSites = function(buyCustomer,state,startDate,endDate) {
										
						$scope.loaderStoreList=true;
						$scope.setFrequencyBtn = true;
						$scope.selectall = false;
						$scope.storeListCheckAllBtn=false;
												
						 var startDateUTC =  Date.UTC(startDate.getUTCFullYear(), startDate.getUTCMonth(),
						 startDate.getUTCDate()+1,startDate.getUTCHours(), startDate.getUTCMinutes(), startDate.getUTCSeconds());
						 startDate= new Date(startDateUTC);
						
						 var endDateUTC =  Date.UTC(endDate.getUTCFullYear(), endDate.getUTCMonth(), endDate.getUTCDate()+1,endDate.getUTCHours(), endDate.getUTCMinutes(), endDate.getUTCSeconds());
						 endDate= new Date(endDateUTC);
						var storeDto={};
						storeDto.buyCustomer=buyCustomer;
						storeDto.state=state;
						storeDto.startDate=startDate;
						storeDto.endDate=endDate;
						
						customerService.getCustomerSites(storeDto).then(function(response) {
							$scope.loaderStoreList = false;
							$scope.setDataLoader=false;
						     $scope.storeListCheckAllBtn=false;
							$scope.customerSites = response;
							//$scope.customerSitesDisplay = $scope.customerSites
							$scope.jsonForStoreListExport=[];
							$scope.storeListfileName = "StoreListView";
							$scope.exporStoreListData = [];
							$scope.exporStoreListData.push(["Customer", "Store", "Address","Data Configuration"]);
					for(var i=0;i<$scope.customerSites.length;i++){
						
					
						
						$scope.jsonForStoreListExport.push({
							"Customer":$scope.customerSites[i].customerName,
							"Store":$scope.customerSites[i].siteName,
							"Address":$scope.customerSites[i].address,
							"Data Configuration":$scope.customerSites[i].status
					
								});
								$scope.exporStoreListData.push([$scope.customerSites[i].customerName, $scope.customerSites[i].siteName, $scope.customerSites[i].address,$scope.customerSites[i].status]);
								
						 $scope.jsonForStoreListExport = $filter('orderBy')(
						 $scope.jsonForStoreListExport, 'Store');
						 
					}
					
					
					$scope.storeListViewValid=true;
					
					 $scope.dtInstanceSLV = {};
					 
					 
					  $scope.dtColumnsSLV = [
										  DTColumnBuilder.newColumn('Select').withTitle('<div class="custom-check checkboxMarginremv">'
									+'	<input type="checkbox" id="selectall" class="checkboxTabindex" tabindex="0" ng-disabled="storeListCheckAllBtn"'
									+'	ng-model="modeltemp.selectall" ng-change="triggeredSelectAllSLV()"> <label for="selectall" class="selctall_label">'
									+'	<div class="select"></div></label> </div>'
								    +'	<span class="selectCheckBoxAlign">Select</span>').notSortable().withOption("searchable", false).renderWith(function(data, type, full, meta) {
									
									var htmlTemp='<div class="custom-check"><input type="checkbox" tabindex="0" class="checkboxTabindex" id="'+full.customerSiteId+'" '
											+' ng-model="checked_'+full.customerSiteId+'" '
											+'ng-change="updateCustomerSite('+full.customerSiteId+',checked_'+full.customerSiteId+');configureTooltip()" /> '
											+' <label  for="'+full.customerSiteId+'"></label> </div>'; 
									return htmlTemp;	
										}),
										   DTColumnBuilder.newColumn('customerName').withTitle('Customer'),
										   DTColumnBuilder.newColumn('siteName').withTitle('Store'),
										   DTColumnBuilder.newColumn('address').withTitle('Address'),
										   DTColumnBuilder.newColumn('status').withTitle('Data Configuration')
									   ];  
									  
							  $scope.dtOptionsSLV = DTOptionsBuilder.newOptions().withOption('data', $scope.customerSites).withOption('createdRow', function (row, data, dataIndex) {						
									$compile(angular.element(row).contents())($scope);
								}).withOption('headerCallback', function( thead, data, start, end, display ) {
									$compile(angular.element(thead).contents())($scope);
								}).withOption('stateSave', false);
								
								
								 //code to remove sorting arrow in Datatable start
	 
	 $interval(function() {
                                                $("#storeListViewReacords th:first-child").css("background-image", "none");
                                                $("#listResources th:first-child").css("background-image", "none");
                                });

		//	 code to remove sorting arrow in Datatable start					
			
								
											
						});
						
						
						if(buyCustomer){
								customerService.getMatrialsForBuyCustomer(buyCustomer.customerId)
								.then(function(response) {
									$scope.materials = response;

								});
								
								customerService.getAllSellCustomersForBuyCustomer(
								buyCustomer.customerId).then(function(response) {
							$scope.sellCustomersForCustomerSelected = response;
						});
								}else{
								customerService.getAllMatrials()
								.then(function(response) {
									$scope.materials = response;

								});
								customerService.getAllSellCustomers().then(function(response) {
							$scope.sellCustomersForCustomerSelected = response;
						});
								}
								
								
								$scope.modeltemp.selectall=false;
								
								
									if($scope.selectedCustomerSites!=undefined && $scope.selectedCustomerSites.length >0){
							
							for(var i=0;i<$scope.selectedCustomerSites.length;i++){
							var name="checked_"+$scope.selectedCustomerSites[i].customerSiteId;
							$scope[name]=false;	
							}
							
						}
								
						$scope.selectedCustomerSites=[];
						$scope.setDataBtn=true;						

						
					}
					
					
					

					
					// Reset Store list : by Naren
					$scope.refreshStoreList = function(){
					$scope.setDataBtn=true;
					
					$scope.modeltemp.selectall=false;
				if($scope.customerSites!=undefined && $scope.customerSites.length >0){
							
							for(var i=0;i<$scope.customerSites.length;i++){
							var name="checked_"+$scope.customerSites[i].customerSiteId;
							$scope[name]=false;	
							//$scope.customerSites[i].checked = false;
							}
							
						}
					
					
					$scope.buyCustomerSelected=undefined;
					$scope.storeListViewValid=false;
					$scope.statesInStoreList=undefined; 
					$scope.startDate=undefined;
					$scope.endDate=undefined;
					$scope.customerSites=[];
					$scope.jsonForStoreListExport=[];
					$scope.exporStoreListData = [];
					$scope.storeListViewBtn=true;
					}
					//
					
					// Reset Store Supplier list : by Naren
					$scope.refreshStoreSupplierList = function(){
						
						
						$scope.assignActivityPopupBtn=true;
						
						$scope.suppliersForCustomerSelected =$scope.suppliersForCustomerSelectedOriginal;
																		
						if($scope.baleAssignments!=undefined && $scope.baleAssignments.length >0){
							
							for(var i=0;i<$scope.baleAssignments.length;i++){
							var name="checkedSSV_"+$scope.baleAssignments[i].sequenceNo;
							$scope[name]=false;	
							//$scope.baleAssignments[i].checked = false;
							}
							
						}
						
						$scope.storeSupplierCustomerSelected=undefined;
						$scope.Suppliertemp.selectall=false;
						if($rootScope.user.role =='Supplier'){
							$scope.state=undefined; 
							$scope.startDateSSL=undefined;
							$scope.endDateSSL=undefined;
							$scope.supplierSelectedAct=$scope.supplierSelectedAct;
							$scope.baleAssignments=[];
							$scope.jsonForStoreSupplierExport=[];
							$scope.exporStoreSupplierData=[];
							$scope.storeSupplierViewValid=false;
					}else{
					$scope.buyCustomers = $rootScope.buyCustomers;
					$scope.state=undefined;
					$scope.startDateSSL=undefined;
					$scope.endDateSSL=undefined;
					$scope.supplierSelectedAct=undefined;
					$scope.baleAssignments=[];
					$scope.jsonForStoreSupplierExport=[];
					$scope.exporStoreSupplierData=[];
					$scope.storeSupplierViewValid=false;
					
					
					
					
					}
					}
					//
					
					
					// Reset Assignment list : by Naren
					$scope.refreshAssignments = function(){
						
						
						$scope.suppliersForCustomerSelected =$scope.suppliersForCustomerSelectedOriginal;
						if($rootScope.user.role =='Supplier'){
					$scope.buyCustomerSelectedAct=undefined;
					$scope.assignmentDateStart=undefined;
					$scope.assignmentDateEnd=undefined;
				
					$scope.getCustomersFromSupplier(defaultSupplierId);
					$scope.driverSupplierPickupsView=[];
					$scope.exportAssignmentData=[];
					$scope.jsonForAssignmentExport=[];
					$scope.assignmentViewValid=false;		
							
						}else{
					$scope.buyCustomers = $rootScope.buyCustomers;		
					$scope.buyCustomerSelectedAct=undefined;
					$scope.assignmentSupplierSelected=undefined;
					$scope.assignmentDateStart=undefined;
					$scope.assignmentDateEnd=undefined;
					$scope.driverSupplierPickupsView=[];
					$scope.jsonForAssignmentExport=[];
					$scope.exportAssignmentData=[];
					$scope.assignmentViewValid=false;
					
						}
					}
					
					//
					
					$scope.resDtOptionsstoreList=DTOptionsBuilder.newOptions().withPaginationType('full_numbers')
					.withDisplayLength(10)
					.withOption('order', [])
					.withOption('bFilter', true)
					.withOption('deferRender', true)
					
					 .withOption('tabIndex', '-1')
					 .withDOM('Blfrtip')
					
					.withOption('responsive', true);
		
					
						if(window.innerWidth >= 992) {
						$scope.resDtColumnsstoreList = [DTColumnBuilder.newColumn(0).notSortable(),
									DTColumnBuilder.newColumn(1),
									DTColumnBuilder.newColumn(2),
									DTColumnBuilder.newColumn(3),
									DTColumnBuilder.newColumn(4)];
						}
					
						$scope.resDtOptionsstoreSupplier=DTOptionsBuilder.newOptions().withPaginationType('full_numbers')
						.withDisplayLength(10)
						.withOption('order', [])
						.withOption('lengthChange', true)
						.withOption('bFilter', true)
						.withOption('tabIndex', '-1')
						.withDOM('Blfrtip')
						/*.withButtons([				       

					         {
					    	  	extend: 'pdf',
					    	  	text: '<img src ="/brta/app/ui/resources/images/new_pdf.png" class="selectTabIndexValue" title="Download PDF" tabindex="14">',					    	 
					    	  	pageSize: 'A3',
					    	  	pageOrientation: 'landscape'	
					    	  	
					        },
							{
								extend: 'excel',
								text:      ' <img src ="/brta/app/ui/resources/images/new_excel.png" class="selectTabIndexValue" title="Download Excel" tabindex="14">',					        
								extension: '.xlsx'
							} 
							]) */
						.withOption('responsive', true);
									
										if(window.innerWidth >= 992) {
										$scope.resDtColumnsstoreSupplier = [DTColumnBuilder.newColumn(0).notSortable(),
													DTColumnBuilder.newColumn(1),
													DTColumnBuilder.newColumn(2),
													DTColumnBuilder.newColumn(3),
													DTColumnBuilder.newColumn(4),
													DTColumnBuilder.newColumn(5),
													DTColumnBuilder.newColumn(6),
													DTColumnBuilder.newColumn(7),
													DTColumnBuilder.newColumn(8),
													DTColumnBuilder.newColumn(9).notSortable()
													];
													
										}
					
					$scope.resDtOptionsAssignment=DTOptionsBuilder.newOptions().withPaginationType('full_numbers')
						.withDisplayLength(10)
						.withOption('order', [])
						.withOption('lengthChange', true)
						.withOption('bFilter', true)
						.withOption('tabIndex', '-1')
						.withDOM('Blfrtip')
						/*.withButtons([				       

					         {
					    	  	extend: 'pdf',
					    	  	text: '<img src ="/brta/app/ui/resources/images/new_pdf.png" title="Download PDF" tabindex="13" class="selectTabIndexValue">',					    	 
					    	  	pageSize: 'A3',
					    	  	pageOrientation: 'landscape'	
					    	  	
					        },
							{
								extend: 'excel',
								text:      ' <img src ="/brta/app/ui/resources/images/new_excel.png" title="Download Excel" tabindex="13" class="selectTabIndexValue">',					        
								extension: '.xlsx'
							} 
							]) */
						.withOption('responsive', true);
						
						
										if(window.innerWidth >= 992) {
										$scope.resDtColumnsAssignment = [DTColumnBuilder.newColumn(0),
													DTColumnBuilder.newColumn(1),
													DTColumnBuilder.newColumn(2),
													DTColumnBuilder.newColumn(3),
													DTColumnBuilder.newColumn(4),
													DTColumnBuilder.newColumn(5),
													DTColumnBuilder.newColumn(6).notSortable()
													];
										}


										
						
					  
					
						$scope.assignTabIndex = function() {
							 var assignedTabIndexValue = angular.element(document.querySelectorAll("[tabindex]"));
							 assignedTabIndexValue.attr('tabindex',"-1");
							}
							//added by prema 
						$scope.generateAssignmentTabindex = function() {
							 var assignedTabIndexValue = angular.element(document.querySelectorAll("[tabindex]"));
							 assignedTabIndexValue.attr('tabindex',"-1");
							 var storeListViewReacords_lengthTabindex = angular.element( document.querySelector( "[name='listResources_length']" ) );
								storeListViewReacords_lengthTabindex.prop('tabindex','-1');
							$(".generateAssinmenttab").prop('tabindex','1');
							}
							//ended by prema 
						$scope.assignTabIndexclose = function() {
							 var assignedtabsIndexValue = angular.element(document.querySelectorAll(".tabsIndexValue"));
								assignedtabsIndexValue.attr('tabindex',"1");
								 var assignedselectTabIndexValue = angular.element(document.querySelectorAll(".selectTabIndexValue"));
								 assignedselectTabIndexValue.attr('tabindex',"2");
								 //var assignedcheckboxTabindex = angular.element(document.querySelectorAll(".checkboxTabindex"));
								 //assignedcheckboxTabindex.attr('tabindex',"15");
								var storeListViewReacords_lengthTabindex = angular.element( document.querySelector( "[name='storeListViewReacords_length']" ) );
								storeListViewReacords_lengthTabindex.prop('tabindex','14');
								var storeListViewReacords_lengthTabindex = angular.element( document.querySelector( "[name='listResources_length']" ) );
								storeListViewReacords_lengthTabindex.prop('tabindex','13');
								var storeListViewReacords_lengthTabindex = angular.element( document.querySelector( "[name='listResourcesAssignment_length']" ) );
								storeListViewReacords_lengthTabindex.prop('tabindex','12');
								//var paginate_buttonTabIndex = angular.element( document.querySelectorAll( ".setdataPopupAlignment " ) );
								//paginate_buttonTabIndex.prop('tabindex','16');
								var paginate_buttonTabIndex = angular.element( document.querySelectorAll( ".paginate_button " ) );
								paginate_buttonTabIndex.prop('tabindex','17');
							}
											

						$interval( function(){
						$('#storeListViewReacords_filter input').prop('title','Search By Customer, Store, Address, Data Configuration');
						$('#listResources_filter input').prop('title','Search By Customer Name, Store Name, Supplier Name, Sell Customer Site Name, Maaterial Profile');
						$('#listResourcesAssignment_filter input').prop('title','Search By Customer, Store Name, Day Driver, Night Driver ');
						
						$('[data-toggle="tooltip"]').tooltip(); 
						},1000);
					
						
					$scope.selectedCustomerSites = [];
					
					$scope.addToSelectedCustomerSites = function(customerSite,modalValue) {	
					
					if(modalValue){
						$scope.selectedCustomerSites.push(customerSite);
						}else{
							var index = $scope.selectedCustomerSites.indexOf(customerSite);
							$scope.selectedCustomerSites.splice(index,1);
						}
					
						if($scope.selectedCustomerSites.length > 0){
							$scope.setDataBtn= false;
						}else{
							$scope.setDataBtn= true;
						}
						
						
						
						if($scope.modeltemp.selectall == false){
							var table = $('#storeListViewReacords').DataTable();
					var filteredRowsSLV=table.rows( { filter : 'applied',page: "all"} ).data();
					$scope.checkSelectAllButtonEnableOrDisable(filteredRowsSLV,$scope.selectedCustomerSites,'SLV');
						
						}
					
						
					}

					
					
					$scope.removeMaterial=function(material){
				var index=$scope.setDatapopup.materialSelected.indexOf(material);
							$scope.setDatapopup.materialSelected.splice(index,1);
				}				
					
					$scope.updateMaterial = function(material) {
			if($scope.setDatapopup.materialSelected == undefined){
				$scope.setDatapopup.materialSelected=[];
			}
						var index=$scope.setDatapopup.materialSelected.indexOf(material);						
						if(index == -1){
							
						$scope.setDatapopup.materialSelected
														.push(material);
						}	


						
					for(var i=0;i<$scope.setDatapopup.materialSelected.length;i++){
					if($scope.setDatapopup.materialSelected[i].avgBaleWeight==undefined || $scope.setDatapopup.materialSelected[i].avgBaleWeight==""){
						$scope.setDatapopup.materialSelected[i].avgBaleWeight=1;
					}
	
						}						
						
					}
					
					$scope.updateSellCustomerFromSupplierAndBuyCustomer= function (supplier){
						
						
						$scope.sellCustomersForSetDataPopUp=[];
						$scope.setDatapopup.sellCustomerSelected=[];
						$scope.destinationSitesForDestinationSelected=[];
						$scope.setDatapopup.sellCustomerSite=[];
						$scope.setDatapopup.sellCustomerSiteSelected=[];
						$scope.setDatapopup.sellCustomer=undefined;
												
						for(var i=0;i<$scope.selectedCustomerSites.length;i++){
							var customerId =  $scope.selectedCustomerSites[i].buyCustomerId!=undefined ? $scope.selectedCustomerSites[i].buyCustomerId : $scope.selectedCustomerSites[i].customerId; 
							
							customerService.getAllSellCustomersForBuyCustomerAndSupplier(customerId, supplier.supplierId).then(function(response) {
								
								
								if($scope.sellCustomersForSetDataPopUp.length>0){
									
								var commonSellCustomer = [];
								for (var c = 0; c < $scope.sellCustomersForSetDataPopUp.length; c++) {
									for (var c1 = 0; c1 < response.length; c1++) {
										if ($scope.sellCustomersForSetDataPopUp[c].customerId === response[c1].customerId) {
												commonSellCustomer.push($scope.sellCustomersForSetDataPopUp[c]);
											}
									}
								}

								$scope.sellCustomersForSetDataPopUp = commonSellCustomer;
								}else{
								$scope.sellCustomersForSetDataPopUp = response;	
								}
								
							});
							
							
							
						}
					}
					
					 $scope.removeSupplier=function(supplier){
						 
						 if($scope.setDatapopup.supplierSelected == undefined){
							 $scope.setDatapopup.supplierSelected=[];
						 }
				 var index=$scope.setDatapopup.supplierSelected.indexOf(supplier);
							 $scope.setDatapopup.supplierSelected.splice(index,1);
				}
				
				
					$scope.updateSupplier = function(supplier) {
		if($scope.setDatapopup.supplierSelected == undefined){
			$scope.setDatapopup.supplierSelected=[];
		}
						var index=$scope.setDatapopup.supplierSelected.indexOf(supplier);						
						if(index == -1){
						$scope.setDatapopup.supplierSelected
														.push(supplier);					}						
						
					}
					
					
					
					$scope.removeSellCustomer= function(sellCustomer){
						
						var newSellCustomerSiteSelectedCopy=[];
						
					if($scope.setDatapopup.sellCustomerSelected == undefined){
						$scope.setDatapopup.sellCustomerSelected=[];
						}
						
						for(var x=0;x<$scope.setDatapopup.sellCustomerSelected.length;x++){
							if(sellCustomer.customerId ==$scope.setDatapopup.sellCustomerSelected[x].customerId){
								var index=$scope.setDatapopup.sellCustomerSelected.indexOf($scope.setDatapopup.sellCustomerSelected[x]);
								$scope.setDatapopup.sellCustomerSelected.splice( index, 1);
							}
						}	

       if($scope.setDatapopup.sellCustomerSiteSelected!=undefined){
	for(var i=0;i<$scope.setDatapopup.sellCustomerSiteSelected.length;i++){
		
		if(sellCustomer.customerId !=$scope.setDatapopup.sellCustomerSiteSelected[i].customer.customerId){		
			newSellCustomerSiteSelectedCopy.push($scope.setDatapopup.sellCustomerSiteSelected[i]);			
			
			 if($scope.destinationSitesForDestinationSelected!=undefined){
				 				
				var index = $scope.destinationSitesForDestinationSelected.indexOf($scope.destinationSitesForDestinationSelected[i]);
			 $scope.destinationSitesForDestinationSelected.splice(index, 1); 
			 }
		}
		
	}
	
	
	
	
	$scope.setDatapopup.sellCustomerSiteSelected=newSellCustomerSiteSelectedCopy;
	
}






if($scope.setDatapopup.sellCustomerSite!=undefined){

$scope.setDatapopup.sellCustomerSite=newSellCustomerSiteSelectedCopy;
	
	
	}	if($scope.setDatapopup.sellCustomerSelected.length > 0){
							customerService
								.getCustomerSitesForBuyCustomer(
										$scope.setDatapopup.sellCustomerSelected)
								.then(
										function(response) {
											
											$scope.destinationSitesForDestinationSelected=[];

											$scope.destinationSitesForDestinationSelected = response;
											$scope.loaderActiveActivityList = false;
										});
						}else{
							$scope.destinationSitesForDestinationSelected=[];
							$scope.setDatapopup.sellCustomerSiteSelected=[];
							$scope.setDatapopup.sellCustomerSite=[];
						}
						
	
					}
					
					
					$scope.updateSellCustomer= function(sellCustomer){

						if($scope.setDatapopup.sellCustomerSelected == undefined){
						$scope.setDatapopup.sellCustomerSelected=[];
						}
						
						var index=$scope.setDatapopup.sellCustomerSelected.indexOf(sellCustomer);
						if(index == -1){
						$scope.setDatapopup.sellCustomerSelected
									 .push(sellCustomer);			
						
						}
																		
						if($scope.setDatapopup.sellCustomerSelected.length > 0){
							customerService
								.getCustomerSitesForBuyCustomer(
										$scope.setDatapopup.sellCustomerSelected)
								.then(
										function(response) {

											$scope.destinationSitesForDestinationSelected = response;
											$scope.loaderActiveActivityList = false;
										});
						}else{
							$scope.destinationSitesForDestinationSelected=[];
							$scope.setDatapopup.sellCustomerSiteSelected=[];
						}
							}
					
					
					
					
				$scope.removesellCustomerSite=function(sellCustomerSite){
				 
				 if($scope.setDatapopup.sellCustomerSiteSelected == undefined){
				 $scope.setDatapopup.sellCustomerSiteSelected=[];
				 }
				 
				 var index=$scope.setDatapopup.sellCustomerSiteSelected.indexOf(sellCustomerSite);
				 
				 
							 $scope.setDatapopup.sellCustomerSiteSelected.splice(index,1);
				}
				
				
					$scope.updateSellCustomerSite = function(sellCustomerSite) {
							 if($scope.setDatapopup.sellCustomerSiteSelected == undefined){
				 $scope.setDatapopup.sellCustomerSiteSelected=[];
				 }
				 						var index=$scope.setDatapopup.sellCustomerSiteSelected.indexOf(sellCustomerSite);

						if(index == -1){
						$scope.setDatapopup.sellCustomerSiteSelected
														.push(sellCustomerSite);					}						
						

					}
					
					
					
					
					
					// Stor Listed View ===================
					
					$scope.getAllUsersForSupplier = function() {
						
						userService.getAllUsersForSupplierSite(
								$scope.supplierSelectedAct).then(
								function(response) {
									$scope.drivers = response;
									
									
								})
					}
					$scope.getAllUsersForSupplierAssignment = function() {
						
						userService.getAllUsersForSupplierSite(
								$scope.assignmentSupplierSelected).then(
								function(response) {
									$scope.drivers = response;
									
									
								})
					}

					$scope.day = {};
					$scope.day.weeklymon = false;
					$scope.day.weeklytue = false;
					$scope.day.weeklywed = false;
					$scope.day.weeklythu = false;
					$scope.day.weeklyfri = false;
					$scope.day.weeklysat = false;
					$scope.day.weeklysun = false;

					$scope.dropdown = {};
					$scope.errorDropdown = false;

				
					$scope.setSupplierSiteValue = [];
					$scope.setMaterialValue = [];
					$scope.checkSetDataPopupBtn = function() {

						if (($scope.setDatapopup.supplierSiteSelected != undefined && $scope.setDatapopup.supplierSiteSelected.length > 0)
								&& ($scope.setDatapopup.materialSelected != undefined && $scope.setDatapopup.materialSelected.length > 0)
								&& ($scope.setDatapopup.balePickupStartDate != undefined)) {

							var btnName = "setDataApplyBtn_"
									+ $scope.customerSiteIdForBtn
							$scope[btnName] = false;
						}else if(($scope.setDatapopup.supplierSiteSelected == undefined || $scope.setDatapopup.supplierSiteSelected.length == 0)
								|| ($scope.setDatapopup.materialSelected == undefined || $scope.setDatapopup.materialSelected.length == 0)
								|| ($scope.setDatapopup.balePickupStartDate = undefined)){
						var btnName = "setDataApplyBtn_"
									+ $scope.customerSiteIdForBtn
							$scope[btnName] = true;
							}
							}
					$scope.checkStoreListViewBtn = function() {
							
						if ($scope.startDate != undefined && $scope.endDate != undefined && $scope.startDate != null && $scope.endDate != null) {
							$scope.storeListViewBtn = false;
							
						}else{
							
							$scope.storeListViewBtn = true;
						}
					}
					
			
					  $scope.frequencySubCategories = {
					  			"On Call":["NA"],
								 "Weekly":["1","2","3", "4", "5", "6", "7"],
								"Every Other Week":["1","2","3", "4", "5", "6", "7"],
								 "Monthly":["1","2","3", "4", "5", "6", "7","8","9","10","11","12","13", "14", "15", "16", "17","18","19","20","21","22","23", "24", "25", "26", "27","28","29","30","31"]
								};
								
					 $scope.frequencySelectedCategory = "";
					// $scope.frequencySelectedSubCategoryValues = [];
					 $scope.frequencySelectedSubCategory = [];
					 
					 // $scope.$watch('recommandedFrequency', function(newValue) {	
						// $scope.frequencySelectedSubCategoryValues = $scope.frequencySubCategories[newValue];
						
					  // });
					  
					  
					  
					  
					  $scope.updateFrequency = function(frequency){
					  	
					  $scope.setDatapopup.frequencyTimeValue = undefined;
					  
					  if(frequency.description != 'On Call'){
						   $scope.setDatapopup.frequencyTimeValue ="1";
					  }else{
						  $scope.setDatapopup.frequencyTimeValue ="NA";
					  }

					  $scope.setDatapopup.frequencySelectedSubCategoryValues = $scope.frequencySubCategories[frequency.description];
					  }
					 
					 
					 
					
					$scope.pageSize = 10;
					$scope.currentPage = 1;
					$scope.pageChangeHandlerStore = function(num) {
						$scope.currentPage = num;
						$scope.selectall=false;
					};
					
					

					$scope.addresses = [
					                    {'state':'AL'},
					                    {'state':'AK'},
					                    {'state':'AZ'},
					                    {'state':'AR'},
					                    {'state':'CA'},
					                    {'state':'CO'},
					                    {'state':'CT'},
					                    {'state':'DE'},
					                    {'state':'FL'},
					                    {'state':'GA'},
					                    {'state':'HI'},
					                    {'state':'ID'},
					                    {'state':'IL'},
					                    {'state':'IN'},
					                    {'state':'IA'},
					                    {'state':'KS'},
					                    {'state':'KY'},
					                    {'state':'LA'},
					                    {'state':'ME'},
					                    {'state':'MD'},
					                    {'state':'MA'},
					                    {'state':'MI'},
					                    {'state':'MN'},
					                    {'state':'MS'},
					                    {'state':'MO'},
					                    {'state':'MT'},
					                    {'state':'NE'},
					                    {'state':'NV'},
					                    {'state':'NH'},
					                    {'state':'NJ'},
					                    {'state':'NM'},
					                    {'state':'NY'},
					                    {'state':'NC'},
					                    {'state':'ND'},
					                    {'state':'OH'},
					                    {'state':'OK'},
					                    {'state':'OR'},
					                    {'state':'PA'},
					                    {'state':'RI'},
					                    {'state':'SC'},
					                    {'state':'SD'},
					                    {'state':'TN'},
					                    {'state':'TX'},
					                    {'state':'UT'},
					                    {'state':'VT'},
					                    {'state':'VA'},
					                    {'state':'WA'},
					                    {'state':'WV'},
					                    {'state':'WI'},
					                    {'state':'WY'}
					                  ];

					                $scope.lov_state = [
					                    {'lookupCode':'AL',	'description': 'Alabama - AL'},
										{'lookupCode':'AK',	'description': 'Alaska - AK'},
										{'lookupCode':'AZ',	'description': 'Arizona - AZ'},
										{'lookupCode':'AR',	'description': 'Arkansas - AR'},
										{'lookupCode':'CA',	'description': 'California - CA'},
										{'lookupCode':'CO',	'description': 'Colorado - CO'},
										{'lookupCode':'CT',	'description': 'Connecticut - CT'},
										{'lookupCode':'DE',	'description': 'Delaware - DE'},
										{'lookupCode':'FL',	'description': 'Florida - FL'},
										{'lookupCode':'GA',	'description': 'Georgia - GA'},
										{'lookupCode':'HI',	'description': 'Hawaii - HI'},
										{'lookupCode':'ID',	'description': 'Idaho - ID'},
										{'lookupCode':'IL',	'description': 'Illinois - IL'},
										{'lookupCode':'IN',	'description': 'Indiana - IN'},
										{'lookupCode':'IA',	'description': 'Iowa - IA'},
										{'lookupCode':'KS',	'description': 'Kansas - KS'},
										{'lookupCode':'KY',	'description': 'Kentucky - KY'},
										{'lookupCode':'LA',	'description': 'Louisiana - LA'},
										{'lookupCode':'ME',	'description': 'Maine - ME'},
										{'lookupCode':'MD',	'description': 'Maryland - MD'},
										{'lookupCode':'MA',	'description': 'Massachusetts - MA'},
										{'lookupCode':'MI',	'description': 'Michigan - MI'},
										{'lookupCode':'MN',	'description': 'Minnesota - MN'},
										{'lookupCode':'MS',	'description': 'Mississippi - MS'},
										{'lookupCode':'MO',	'description': 'Missouri - MO'},
										{'lookupCode':'MT',	'description': 'Montana - MT'},
										{'lookupCode':'NE',	'description': 'Nebraska - NE'},
										{'lookupCode':'NV',	'description': 'Nevada - NV'},
										{'lookupCode':'NH',	'description': 'New Hampshire - NH'},
										{'lookupCode':'NJ',	'description': 'New Jersey - NJ'},
										{'lookupCode':'NM',	'description': 'New Mexico - NM'},
										{'lookupCode':'NY',	'description': 'New York - NY'},
										{'lookupCode':'NC',	'description': 'North Carolina - NC'},
										{'lookupCode':'ND',	'description': 'North Dakota - ND'},
										{'lookupCode':'OH',	'description': 'Ohio - OH'},
										{'lookupCode':'OK',	'description': 'Oklahoma - OK'},
										{'lookupCode':'OR',	'description': 'Oregon - OR'},
										{'lookupCode':'PA',	'description': 'Pennsylvania - PA'},
										{'lookupCode':'RI',	'description': 'Rhode Island - RI'},
										{'lookupCode':'SC',	'description': 'South Carolina - SC'},
										{'lookupCode':'SD',	'description': 'South Dakota - SD'},
										{'lookupCode':'TN',	'description': 'Tennessee - TN'},
										{'lookupCode':'TX',	'description': 'Texas - TX'},
										{'lookupCode':'UT',	'description': 'Utah - UT'},
										{'lookupCode':'VT',	'description': 'Vermont - VT'},
										{'lookupCode':'VA',	'description': 'Virginia - VA'},
										{'lookupCode':'WA',	'description': 'Washington  - WA'},
										{'lookupCode':'WV',	'description': 'West Virginia - WV'},
										{'lookupCode':'WI',	'description': 'Wisconsin - WI'},
										{'lookupCode':'WY',	'description': 'Wyoming - WY'}
					                ];
					
					
					
					$scope.modeltemp = {}
					$scope.modeltemp.selectall  = false;
					
					$scope.checkAll = function(dtInstanceSLV) {
						
						var oTable = dtInstanceSLV.dataTable;
							
						//$scope.dtOptionsSLV.withOption('stateSave', true);
						var filteredRowsSLV = oTable._('tr', {"filter": "applied", "page": "all"});
						
						$scope.customerSite.checked=true;					
						$scope.customerSitesSorted = $scope.customerSites;
					
						if($scope.modeltemp.selectall == true ){
							if(filteredRowsSLV !=undefined && filteredRowsSLV.length >0){
							if($scope.customerSitesSorted.length == filteredRowsSLV.length){
								for (var i = 0; i < $scope.customerSitesSorted.length; i++) {

							//$scope.customerSitesSorted[i].checked = true;
							
							var name="checked_"+$scope.customerSitesSorted[i].customerSiteId;
							$scope[name]=true;
							
							$scope.addToSelectedCustomerSites($scope.customerSitesSorted[i],true);
						
								}
							}else{
								
								if($scope.modeltemp.selectall == true ){
									for (var i = 0; i < filteredRowsSLV.length; i++) {
									for (var j = 0; j < $scope.customerSitesSorted.length; j++) {
										if(filteredRowsSLV[i].customerSiteId == $scope.customerSitesSorted[j].customerSiteId){
											var name="checked_"+$scope.customerSitesSorted[j].customerSiteId;
											$scope[name]=true;
							
							$scope.addToSelectedCustomerSites($scope.customerSitesSorted[i],true);
							break;
										}
									}
								}
						}	
							}
						}
						}else{
							for (var i = 0; i < $scope.customerSitesSorted.length; i++) {
							//$scope.customerSitesSorted[i].checked = false;
							var name="checked_"+$scope.customerSitesSorted[i].customerSiteId;
							$scope[name]=false;
							$scope.selectedCustomerSites=[];
							$scope.setDataBtn= true;
							//$scope.addToSelectedCustomerSites($scope.customerSitesSorted[i],false);
							}
						}
						
						$scope.selectedCustomerSites=$scope.removeDuplicateObj($scope.selectedCustomerSites,"customerSiteId");
						
						
						};
						
						

					
					$scope.removeDuplicateObj= function(collection,keyname){
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
					}
					$scope.pageSize = 10;
					$scope.currentPageAssign = 1;					
					$scope.pageChangeHandlerAssign = function(numa) {						
						$scope.currentPageAssign = numa;
						$scope.selectallInAssign =false;
					};
					
					
					$scope.Suppliertemp = {}
					$scope.Suppliertemp.selectall  = false;
					
					
					$scope.triggeredSelectAllSSV=function(){
						setTimeout(function () {
							angular.element('#triggerSSVSelect').trigger('click');
						}, 0);
					}
					
					$scope.triggeredSelectAllSLV=function(){
						setTimeout(function () {
							angular.element('#triggerSLVSelect').trigger('click');
						}, 0);
					}
					
					$scope.checkAllAssign = function(dtInstanceSSV1) {
						var oTable = dtInstanceSSV1.dataTable;
										
						
						var filteredRowsSSV = oTable._('tr', {"filter": "applied", "page": "all"});
						
						
						if($scope.Suppliertemp.selectall === true){
							if(filteredRowsSSV !=undefined && filteredRowsSSV.length >0){
								
								
								 if($scope.baleAssignments.length == filteredRowsSSV.length){
							for (var i = 0; i < $scope.baleAssignments.length; i++) {
						
									$scope.assignBtn=false;	

									 	var name="checkedSSV_"+$scope.baleAssignments[i].sequenceNo;
										$scope[name]=true;
									$scope.addToSelectedStoreSupplierList($scope.baleAssignments[i],true);
															
								
							
						} 
						 }else{
							 
							 for(var i=0;i<filteredRowsSSV.length;i++){
							

							 for(var j=0;j<$scope.baleAssignments.length;j++){
								
							 if(filteredRowsSSV[i].sequenceNo == $scope.baleAssignments[j].sequenceNo){
								 
								 $scope.assignBtn=false;	

									 	var name="checkedSSV_"+$scope.baleAssignments[j].sequenceNo;
										$scope[name]=true;
									//$scope.baleAssignments[j].checked = true;									
									$scope.addToSelectedStoreSupplierList($scope.baleAssignments[j],true);

								/*if($scope.baleAssignments[j].balePickupStartDate !=null 
								&& $scope.baleAssignments[j].configuredSupplierSites !=''
								&& $scope.baleAssignments[j].configuredSupplierSites !='' ) {
									$scope.baleAssignments[j].checked = true;
								}
								*/
								break;														
							 }
							}
						 }
						 }
							}
						}else{
							$scope.selectedCustomerStoresSites=[];
							for (var i = 0; i < $scope.baleAssignments.length; i++) {
							var name="checkedSSV_"+$scope.baleAssignments[i].sequenceNo;
										$scope[name]=false;
									//$scope.baleAssignments[i].checked = false;									
									$scope.assignBtn= true;
									$scope.addToSelectedStoreSupplierList($scope.baleAssignments[i],false);
						}
						}

						$scope.assignmentList=$scope.removeDuplicates($scope.assignmentList,"balePickupAssignmentId");
						
					};
					
					
					
						$scope.removeDuplicates= function(data,value){
					var assign = [], 
                  assignmentkeys = [];
				  angular.forEach(data, function(item) {
                  var key = item[value];
                  if(assignmentkeys.indexOf(key) === -1) {
                      assignmentkeys.push(key);
                      assign.push(item);
                  }
              });
			return assign;
					}
					
					
					$scope.getSelectedDays = function(selectedDays) {
						var weeklySelectedDays = [];
						for ( var key in selectedDays) {
							if (selectedDays[key] == false) {
								delete selectedDays[key]
							}
						}
						weeklySelectedDays = Object.keys(selectedDays);

						return weeklySelectedDays;
					}
					
					$scope.createAssignmentObj = function(weeklySelectedDays,
							weekNo, frequency, buyCustomerSite) {
						for (var k = 0; k < weeklySelectedDays.length; k++) {
							var assignment = {};
							assignment.weekNumber = weekNo;
							assignment.frequency = frequency;
							assignment.day = weeklySelectedDays[k];
							assignment.buyCustomerSite = buyCustomerSite;
							assignment.supplier = $scope.supplierSelected;
							$scope.listOfAssignments.push(assignment);

						}
					}
					

					$scope.balePickUpSupplierSiteConfig = {};
					$scope.balePickUpMaterialConfig = {}


					$scope.applyConfigurationData = function(action) {
						
					
						
						if($scope.currentCustomerSite != undefined){
							action='edit'
						}
						
						$scope.setDataLoader=true;
					if (action == 'set') {
							$scope.editAction = false;
						}else{
							$scope.editAction = true;
							
						}	
						
						
					var configData = {}
					var supplierSite = {};

					$scope.balePickupSupplierSiteConfigList = [];
					$scope.materialConfigList = [];
					$scope.error = {};

						
						$scope.errorPopupFlag = false;

						if ($scope.setDatapopup.balePickupStartDate == undefined
								|| $scope.setDatapopup.balePickupStartDate == null
								|| $scope.setDatapopup.balePickupStartDate == "") {
							$scope.error.errorDateFlag = true;
							$scope.errorPopupFlag = true;

						}
						if ($scope.setDatapopup.supplierSelected == undefined
						|| $scope.setDatapopup.supplierSelected == null
								|| $scope.setDatapopup.supplierSelected.length==0
								|| $scope.setDatapopup.supplierSelected == "") {
							$scope.error.errorServiceProviderFlag = true;
							$scope.errorPopupFlag = true;
							

						}
						
						if ($scope.setDatapopup.materialSelected == undefined || !($scope.setDatapopup.materialSelected.length >0 )
								|| $scope.setDatapopup.materialSelected == null
								|| $scope.setDatapopup.materialSelected == "") {
							
							$scope.error.errorMaterialFlag = true;
							$scope.errorPopupFlag = true;
						}
						
						
						if ($scope.setDatapopup.sellCustomer == undefined
								|| $scope.setDatapopup.sellCustomer == null
								||!($scope.setDatapopup.sellCustomer.length > 0)
								|| $scope.setDatapopup.sellCustomer == "") {
							
							$scope.error.errorSellCustomerFlag = true;
							$scope.errorPopupFlag = true;
						}
						
						
						if ($scope.setDatapopup.sellCustomerSite == undefined
								|| $scope.setDatapopup.sellCustomerSite == null
								|| ! ($scope.setDatapopup.sellCustomerSite.length > 0)
								|| $scope.setDatapopup.sellCustomerSite == "") {
							
							
							$scope.error.errorSellCustomerSiteFlag = true;
							$scope.errorPopupFlag = true;
						}
						
						if($scope.setDatapopup.frequency == undefined
								|| $scope.setDatapopup.frequency == null
								|| $scope.setDatapopup.frequency == ""){
							$scope.error.errorFrequencyFlag = true;
							$scope.errorPopupFlag = true;
						}
						
						if($scope.setDatapopup.frequencyTimeValue == undefined
								|| $scope.setDatapopup.frequencyTimeValue == null
								|| $scope.setDatapopup.frequencyTimeValue == ""){
							$scope.error.errorFrequencyTimeValueFlag = true;
							$scope.errorPopupFlag = true;
						}
						
	
						$scope.isupdateAssignmentPopup=false;
						
						if (!$scope.errorPopupFlag) {
						
							$scope.error.errorDateFlag = false;
							$scope.error.errorServiceProviderFlag = false;
							$scope.error.errorMaterialFlag = false;
		
											
								var configData = {}	
								
								$scope.balePickupSupplierConfigList=[];		
								$scope.materialConfigList=[];
								$scope.balePickUpCustomerSiteList=[];
								$scope.customerSiteIdList=[];
								
								for (var i = 0; i < $scope.selectedCustomerSites.length; i++) {
									if(action == 'edit'){
										$scope.customerSiteIdList.push($scope.selectedCustomerSites[i].buyCustomerSiteId);
									}else{
										$scope.customerSiteIdList.push($scope.selectedCustomerSites[i].customerSiteId);

									}
								}
								

									for (var j = 0; j < $scope.setDatapopup.supplierSelected.length; j++) {
							
								$scope.balePickUpSupplierConfig = {};
			
								$scope.balePickUpSupplierConfig.supplier = $scope.setDatapopup.supplierSelected[j];
								$scope.balePickupSupplierConfigList
										.push($scope.balePickUpSupplierConfig);
									}

								
								for (var k = 0; k < $scope.setDatapopup.materialSelected.length; k++) {
								$scope.balePickUpMaterialConfig = {};
								$scope.balePickUpMaterialConfig.material = $scope.setDatapopup.materialSelected[k];
								$scope.balePickUpMaterialConfig.avgBaleWeight = $scope.setDatapopup.materialSelected[k].avgBaleWeight;
								$scope.materialConfigList
										.push($scope.balePickUpMaterialConfig);
								}
								
								
								for (var l = 0; l < $scope.setDatapopup.sellCustomerSiteSelected.length; l++) {
								$scope.balePickUpCustomerSiteConfig = {};
								$scope.balePickUpCustomerSiteConfig.sellCustomerSite = $scope.setDatapopup.sellCustomerSiteSelected[l];								
								$scope.balePickUpCustomerSiteList
										.push($scope.balePickUpCustomerSiteConfig);
								}
								
								
								configData.supplierConfig=$scope.balePickupSupplierConfigList;
								configData.materialConfig=$scope.materialConfigList;
								configData.customerSiteConfig=$scope.balePickUpCustomerSiteList;
								configData.customerSiteIdList = $scope.customerSiteIdList;
								
					            var startDate=$scope.setDatapopup.balePickupStartDate;
					            var endDate= $scope.setDatapopup.balePickupEndDate;
					            
															
					           /* if(startDate!=undefined && startDate!=''){
									console.log("====start date==="+startDate);
					            var startDateUTC =  Date.UTC(startDate.getUTCFullYear(), startDate.getUTCMonth(),
					             startDate.getUTCDate(),0,0,0);
					             startDate= new Date(startDateUTC);	
								 console.log("====start date new==="+startDate);
					            }*/
					            
					           /*  if(endDate!=undefined && endDate!=''){
									console.log("====end date==="+endDate);

					            	 var endDateUTC =  Date.UTC(endDate.getUTCFullYear(), endDate.getUTCMonth(), endDate.getUTCDate(),0,0,0);
					             endDate= new Date(endDateUTC);
								 console.log("====end date new==="+endDate);

					            }*/
								configData.balePickupStartDate = startDate;
								configData.balePickupEndDate = endDate;
								
								
								configData.frequency = $scope.setDatapopup.frequency;
								
								if($scope.setDatapopup.frequencyTimeValue == 'NA'){
									configData.frequencyDay = null;
								}else{
									configData.frequencyDay = $scope.setDatapopup.frequencyTimeValue;
								}
								
								console.log(configData);
								
							storeConfigService
									.addConfigurationData(configData)
									.then(
											function(response) {
												$scope.setDatapopup.materialSelected = [];
												$scope.setDatapopup.supplierSelected = [];
												$scope.setDataPopupContentClose();
												$scope.assignactivityclose();
												$scope.showAddDataSuccessMessage = true;
												$scope.isupdateAssignmentPopup=false
											
												
												for(var i=0;i<$scope.selectedCustomerSites.length;i++){
													
								var dynamicCheckbox="checked_"+$scope.selectedCustomerSites[i].customerSiteId;
													$scope[dynamicCheckbox]=false;
												}
												$scope.selectedCustomerSites=[];
												$scope.setDataBtn=true;

												
											
												
												if(action === 'edit'){
													
													
													
													$scope.setDataLoader=false;
													
													for(var i=0;i<$scope.selectedCustomerStoresSites.length;i++){
														var dynamicCheckbox="checkedSSV_"+$scope.selectedCustomerStoresSites[i].sequenceNo;
														$scope[dynamicCheckbox]=false;
														
													}
													
													$scope.getAllAssignmentsWithFilters();

												
													
												}else{				
													var buyCustomer=$scope.buyCustomerSelected;
													var state=$scope.statesInStoreList;
													var startDate=$scope.startDate;
													var endDate=$scope.endDate;
													
													$scope.getCustomerSites(buyCustomer,state,startDate,endDate);
													
												}
												

																
												
											});
											
											
									
						}else{
							$scope.setDataLoader=false;
						}
						
						

					}
					
					
					$scope.setFrequency = function() {
						$scope.setFrequencyPopupContent = true;
					};
					
					
					
					/*by deepak*/	
						
				$scope.selectedCustomerStoresSites = [];
				var singlesupplier = {};
				
				$scope.addToSelectedStoreSupplierList = function(assignment, modelValue) {
					
					
					if(modelValue){
						
						if($scope.selectedCustomerStoresSites == undefined){
							$scope.selectedCustomerStoresSites=[];
						}
												
						$scope.selectedCustomerStoresSites.push(assignment);

						
					}else{
						var index = $scope.selectedCustomerStoresSites.indexOf(assignment);
						$scope.selectedCustomerStoresSites.splice(index,1);
					}
					
					
					
					
					if($scope.selectedCustomerStoresSites.length > 0){
						$scope.assignActivityPopupBtn= false;
						$scope.startDateAssignmentPopup=null;
						$scope.endDateAssignmentPopup=null;
					}else{
						$scope.selectedCustomerStoresSites = [];
						$scope.assignActivityPopupBtn= true;
						$scope.startDateAssignmentPopup=null;
						$scope.endDateAssignmentPopup=null;
					}
					
					
					if($scope.Suppliertemp.selectall == false){
						var table = $('#listResources').DataTable();
					var filteredRowsSSV=table.rows( { filter : 'applied',page: "all"} ).data();
					$scope.checkSelectAllButtonEnableOrDisable(filteredRowsSSV,$scope.selectedCustomerStoresSites,'SSV');
					}
					
					
					
				}
				
				
				
				
					var assignDriverPopupDTO = {};
					
					
					$scope.addAssignDriverPopupCalling=function(dataVal){
						var driverSupplierView = $filter('filter')($scope.driverSupplierPickupsView, function (d) {return d.customerSiteId === dataVal;})[0];
						$scope.addAssignDriverPopup(driverSupplierView);
						$scope.invalidGenAssPopupEndDateError = "";
					}
					
			$scope.addAssignDriverPopup = function(driverSupplierView) {
				
				$scope.saveDriverBtn =true;
								 
				$scope.dayDriverSelectedinPopup={};
				$scope.nightDriverSelectedinPopup={};
				$scope.storeIdList=[];
				var isUniqueSupplier=true;
				var supplierId;
				$scope.currentAssignmentSupplier=undefined;
				$scope.currentAssignmentCustomerSite=undefined;
				
				if(driverSupplierView != undefined){
					isUniqueSupplier=true;
					$scope.storeIdList.push(driverSupplierView.customerSiteId);
					supplierId=driverSupplierView.supplierId;
					$scope.currentAssignmentSupplier=driverSupplierView.supplierId;
					$scope.currentAssignmentCustomerSite=driverSupplierView.customerSiteId;
					
					
					
					if(driverSupplierView.startDate== '' || driverSupplierView.startDate ==undefined){
						$scope.startDateAssignmentPopup=new Date();
					}else{
						$scope.startDateAssignmentPopup=new Date(driverSupplierView.startDate);
					}
					
					$scope.endDateAssignmentPopup=new Date(driverSupplierView.endDate);
				}else{
					
					isUniqueSupplier=true;
					
					for(var i=0;i<$scope.selectedCustomerStoresSites.length;i++){
							$scope.storeIdList.push($scope.selectedCustomerStoresSites[i].buyCustomerSiteId);
					}
						
					supplierId= $scope.selectedCustomerStoresSites[0].supplierId;
				
	
				
				if($scope.selectedCustomerStoresSites.length > 1){
					
					for (var i = 0; i < $scope.selectedCustomerStoresSites.length; i++) {
						for (var j = i+1; j < $scope.selectedCustomerStoresSites.length; j++) {
								if($scope.selectedCustomerStoresSites[i].supplierId != $scope.selectedCustomerStoresSites[j].supplierId){
								isUniqueSupplier=false;
								break;
							}
						}
					}
					
				}
				
				
				
				if( $scope.selectedCustomerStoresSites[0].startDate == null){
					$scope.startDateAssignmentPopup = new Date();
				}else{	
				$scope.startDateAssignmentPopup=$scope.selectedCustomerStoresSites[0].startDate;
				$scope.endDateAssignmentPopup=$scope.selectedCustomerStoresSites[0].endDate;
					}	
				}
				
				
				if(isUniqueSupplier){
					
					frequencyService.getAllFrequency().then(
						 function(response) {
							 $scope.recommandedFrequency=response.data;
						 });
					
							$scope.isupdateFieldsForAssignActivity = true;
							
						if($scope.storeIdList.length >0){
							pickupAssignmentService.getDestinationFromStoreIdList($scope.storeIdList)
									.then(function(response) {
										$scope.destinationList= response.data;

									})
						}						
							
							
						userService.getOnlyDriversForSupplierSite(supplierId)
									.then(function(response) {
										
								$scope.supplierselecteddrivers = response;	
								$scope.supplierselectedDayDrivers = $scope.supplierselecteddrivers ;
								$scope.supplierselectedNightDrivers =$scope.supplierselecteddrivers ;
								
								if(driverSupplierView!=undefined){

										for(var j=0;j<$scope.supplierselecteddrivers.length;j++){

											if(driverSupplierView.driver1Id !=undefined){
												if($scope.supplierselecteddrivers[j].userId == driverSupplierView.driver1Id){
												$scope.dayDriverSelectedinPopup=$scope.supplierselecteddrivers[j];
												$scope.updateNightDrivers($scope.dayDriverSelectedinPopup);
											}
											}
											
											if(driverSupplierView.driver2Id !=undefined){
																							
											if($scope.supplierselecteddrivers[j].userId == driverSupplierView.driver2Id){
												$scope.nightDriverSelectedinPopup=$scope.supplierselecteddrivers[j];
												$scope.updateDayDrivers($scope.nightDriverSelectedinPopup);
											}
											}
										}
										
										if($scope.dayDriverSelectedinPopup!=undefined && $scope.dayDriverSelectedinPopup.userId!=undefined && $scope.nightDriverSelectedinPopup!=undefined && $scope.nightDriverSelectedinPopup!= ''){
							$scope.saveDriverBtn =false;
						}else{
							$scope.saveDriverBtn =true;
						}
							
								}else{
									for(var j=0;j<$scope.supplierselecteddrivers.length;j++){

										if($scope.selectedCustomerStoresSites[0].driver1Id !=undefined){
											if($scope.supplierselecteddrivers[j].userId == $scope.selectedCustomerStoresSites[0].driver1Id){
											$scope.dayDriverSelectedinPopup=$scope.supplierselecteddrivers[j];
											$scope.updateNightDrivers($scope.dayDriverSelectedinPopup);
										}
										}
										
										if($scope.selectedCustomerStoresSites[0].driver2Id !=undefined){
																						

										if($scope.supplierselecteddrivers[j].userId == $scope.selectedCustomerStoresSites[0].driver2Id){
											$scope.nightDriverSelectedinPopup=$scope.supplierselecteddrivers[j];
											$scope.updateDayDrivers($scope.nightDriverSelectedinPopup);
										}
										}
									}
									
									
									if($scope.dayDriverSelectedinPopup!=undefined && $scope.dayDriverSelectedinPopup.userId!=undefined && $scope.nightDriverSelectedinPopup!=undefined && $scope.nightDriverSelectedinPopup!= ''){
							$scope.saveDriverBtn =false;
						}else{
							$scope.saveDriverBtn =true;
						}
								}
								
									});
					
					
					
				}else{
					$scope.ifmultiplesupplersselected=true;
				}
						
			}
			
			
			
			$scope.updateNightDrivers= function(dayDriver){
				
				var nightDriver=$scope.nightDriverSelectedinPopup;
				
			var driverList= [];
		
			if(dayDriver!=undefined){
			for(var i=0;i<$scope.supplierselecteddrivers.length;i++){
				if($scope.supplierselecteddrivers[i].userId != dayDriver.userId){
					driverList.push($scope.supplierselecteddrivers[i])
				}else{
				}
			}
			
			
			$scope.supplierselectedNightDrivers = driverList;
			}else{
				$scope.supplierselectedNightDrivers = $scope.supplierselecteddrivers;
			}
			
			
			
			
			if(dayDriver!=undefined && dayDriver != '' && dayDriver.userId!=undefined && nightDriver!=undefined && nightDriver != '' && nightDriver.userId!=undefined){
				$scope.saveDriverBtn=false;
			}else{
				$scope.saveDriverBtn=true;
			}
			
			
			}
			
			$scope.updateDayDrivers= function(nightDriver){
				
				var driverList= [];
				if(nightDriver!=undefined){
					
					var driverList= [];
			for(var i=0;i<$scope.supplierselecteddrivers.length;i++){
				if($scope.supplierselecteddrivers[i].userId != nightDriver.userId){
					driverList.push($scope.supplierselecteddrivers[i])
				}
			}
				$scope.supplierselectedDayDrivers = driverList;
			
			}else{
				$scope.supplierselectedDayDrivers = $scope.supplierselecteddrivers;
			}
			
			if($scope.dayDriverSelectedinPopup!=undefined && $scope.dayDriverSelectedinPopup!= '' && $scope.dayDriverSelectedinPopup.userId!=undefined  && nightDriver!=undefined && nightDriver != '' && nightDriver.userId!=undefined){
				$scope.saveDriverBtn=false;
			}else{
				$scope.saveDriverBtn=true;
			}
			
			
						
			}
			

				var generateAssignmentDriverPopup={};
				
				
			$scope.saveDriverDetails= function(dayDriverSelectedinPopup,nightDriverSelectedinPopup,assignmentStartDate,assignmentEndDate){
				
				$scope.loaderAssignment=true;
				
				if($scope.selectedCustomerStoresSites==undefined){
					$scope.selectedCustomerStoresSites=[];
				}
				
				var generateAssignmentDriverPopupList =[];
				
				
				if($scope.currentAssignmentSupplier != undefined && $scope.currentAssignmentCustomerSite != undefined){
					var generateAssignmentDriverPopup={};
					generateAssignmentDriverPopup.customerSiteId= $scope.currentAssignmentCustomerSite;
					generateAssignmentDriverPopup.supplierId=$scope.currentAssignmentSupplier;
					generateAssignmentDriverPopup.dayDriver=dayDriverSelectedinPopup.userId;
					if(nightDriverSelectedinPopup == undefined){
					generateAssignmentDriverPopup.nightDriver=null;
				}else{
					generateAssignmentDriverPopup.nightDriver=nightDriverSelectedinPopup.userId;
				}	
				
				
				console.log("===assignmentStartDate=="+assignmentStartDate);
				console.log("===assignmentEndDate=="+assignmentEndDate);
				
				
				/*
				if(assignmentStartDate!=undefined){
					var assignmentStartDateUTC =  Date.UTC(assignmentStartDate.getUTCFullYear(), assignmentStartDate.getUTCMonth(),
				assignmentStartDate.getUTCDate()+1,assignmentStartDate.getUTCHours(), assignmentStartDate.getUTCMinutes(), assignmentStartDate.getUTCSeconds());
				assignmentStartDate= new Date(assignmentStartDateUTC);
				}
				
				if(assignmentEndDate!=undefined){
					var assignmentEndDateUTC =  Date.UTC(assignmentEndDate.getUTCFullYear(), assignmentEndDate.getUTCMonth(),
				assignmentEndDate.getUTCDate()+1,assignmentEndDate.getUTCHours(), assignmentEndDate.getUTCMinutes(), assignmentEndDate.getUTCSeconds());
				assignmentEndDate= new Date(assignmentEndDateUTC);
				}
				*/
				
				
				
				generateAssignmentDriverPopup.assignmentStartDate=assignmentStartDate;
				generateAssignmentDriverPopup.assignmentEndDate=assignmentEndDate;
				
				generateAssignmentDriverPopupList.push(generateAssignmentDriverPopup);
				
				}
				
				
				if($scope.selectedCustomerStoresSites.length >0){
					for(var i=0;i< $scope.selectedCustomerStoresSites.length ;i++){
						var generateAssignmentDriverPopup={};
						generateAssignmentDriverPopup.customerSiteId= $scope.selectedCustomerStoresSites[i].buyCustomerSiteId;
						generateAssignmentDriverPopup.supplierId=$scope.selectedCustomerStoresSites[i].supplierId;
						generateAssignmentDriverPopup.dayDriver=dayDriverSelectedinPopup.userId;
						
						if(nightDriverSelectedinPopup == undefined){
					generateAssignmentDriverPopup.nightDriver=null;
				}else{
					generateAssignmentDriverPopup.nightDriver=nightDriverSelectedinPopup.userId;
				}	
				
				generateAssignmentDriverPopup.assignmentStartDate=assignmentStartDate;
				generateAssignmentDriverPopup.assignmentEndDate=assignmentEndDate;
				
				
				generateAssignmentDriverPopupList.push(generateAssignmentDriverPopup);
					}	
				}
				pickupAssignmentService.assignDriverPopup(generateAssignmentDriverPopupList)
									.then(function(response) {
					

					
										if($scope.currentAssignmentSupplier != undefined && $scope.currentAssignmentCustomerSite != undefined){
											$scope.showAssignedDrivers();
											$scope.loaderAssignment=false;											
										}else{
											
											if($scope.selectedCustomerStoresSites != undefined && $scope.selectedCustomerStoresSites.length >0){
												for(var x=0;x<$scope.selectedCustomerStoresSites.length;x++){
													var name="checkedSSV_"+$scope.selectedCustomerStoresSites[x].sequenceNo;
													$scope[name]=false;
													//$scope.baleAssignments[i].checked = false;
												}
											}
											$scope.getAllAssignmentsWithFilters();
																						

											$scope.loaderAssignment=false;
										}

									$scope.assignactivityclose();
									$scope.assignActivityPopupBtn= true;
									$scope.updateDriverclose();
									})
				
				
			}
			
				/*END by deepak*/							
						
							
					
					
					$scope.setdataPopup = function(customerSiteId) {
					//	$scope.destinationSitesForDestinationSelected=[];
					
					var currentCustomerSite=undefined;
					if(customerSiteId !=undefined || customerSiteId!=null){
						 currentCustomerSite = $filter('filter')($scope.baleAssignments, function (d) {return d.sequenceNo === customerSiteId;})[0];

					}
					
					var customerSite=currentCustomerSite;

						$scope.currentCustomerSite=currentCustomerSite;

						var action='set';
						$scope.setDataPopup = true;
						$scope.setDataLoader=true;
						$scope.frequencyIdSelected=undefined;
						$scope.frequencyDaySelected=undefined;
						$scope.currentCustomerSiteId=undefined;
						$scope.startDaySelected=undefined;
						$scope.endDaySelected=undefined;
						$scope.setDatapopup={};
						
						$scope.suppliersForSetDataPopUp=[];
						$scope.sellCustomersForSetDataPopUp=[];
						$scope.setDatapopup.sellCustomerSite=[];
						
						if($scope.selectedCustomerStoresSites == undefined){
							$scope.selectedCustomerStoresSites =[];
						}
						
						var customerIdList=[];
						
						//store supplerlist view edit date - set data popup - by naren
						
						if(customerSite !=undefined){
												
												
							$scope.setDataLabel="Edit";
							customerIdList.push(customerSite.buyCustomerId);
							
							customerService.getMatrialsForBuyCustomer(customerSite.buyCustomerId)
								.then(function(response) {
									$scope.materials = response;

								});
							
							
							//balePickupEndDate
							$scope.selectedCustomerSites=[];
							$scope.selectedCustomerSites.push(customerSite);

								$scope.currentCustomerSiteId=customerSite.buyCustomerSiteId ;
								$scope.frequencyIdSelected=customerSite.frequencyId != null ? customerSite.frequencyId :undefined;
								$scope.frequencyDaySelected=customerSite.frequencyDay != null ? customerSite.frequencyDay :undefined;;
								$scope.startDaySelected=customerSite.balePickupStartDate != null ? customerSite.balePickupStartDate :undefined;
								$scope.endDaySelected=customerSite.balePickupEndDate != null ? customerSite.balePickupEndDate :undefined;
								var action='edit';
							
							$scope.currentBuyCustomerId=customerSite.buyCustomerId;
							
						}else{
							
							angular.forEach($scope.selectedCustomerSites, function(customerSite) {
								var customerId = customerSite.customerId
								if(customerIdList.indexOf(customerId) === -1) {
								customerIdList.push(customerId);
								}
							});
													
							
							
													
							
							if($scope.selectedCustomerSites.length == 1){
							if($scope.selectedCustomerSites[0].status  == 'Complete'){
								
								
								$scope.currentBuyCustomerId=$scope.selectedCustomerSites[0].customerId
								$scope.currentCustomerSiteId=$scope.selectedCustomerSites[0].customerSiteId;
								$scope.frequencyIdSelected=$scope.selectedCustomerSites[0].frequencyId
								$scope.frequencyDaySelected=$scope.selectedCustomerSites[0].frequencyDay ;
			
								$scope.startDaySelected=$scope.selectedCustomerSites[0].balePickupStartDate
								$scope.endDaySelected=$scope.selectedCustomerSites[0].balePickupEndDate
								var action='edit';
							}
						 }
						}
						
												 
						for(var i=0;i<customerIdList.length;i++){
								
								customerService.getAllSellCustomersForBuyCustomer(customerIdList[i]).then(function(response) {
																
									if($scope.sellCustomersForSetDataPopUp.length >0){
										
										var commonSellCustomer=[];
										for (var c = 0; c < $scope.sellCustomersForSetDataPopUp.length; c++) {
												for (var c1 = 0; c1 < response.length; c1++) {
													if ($scope.sellCustomersForSetDataPopUp[c].customerId === response[c1].customerId) {
													commonSellCustomer.push($scope.sellCustomersForSetDataPopUp[c]);
													}
												}
											}
											
													$scope.sellCustomersForSetDataPopUp=commonSellCustomer;
													}else{
														$scope.sellCustomersForSetDataPopUp=response;
													}
													$scope.sellCustomersForSetDataPopUp=$scope.getUniqueObjectsFromList($scope.sellCustomersForSetDataPopUp,'customerName');
													
								});	
								
								
								
								customerService.getSuppliersFromBuyCustomerForDAR(customerIdList[i]).then(function(response) {
									
										if($scope.suppliersForSetDataPopUp.length >0){
											
											var commonValues=[];
											
											for (var c = 0; c < $scope.suppliersForSetDataPopUp.length; c++) {
												for (var c1 = 0; c1 < response.length; c1++) {
													if ($scope.suppliersForSetDataPopUp[c].supplierId === response[c1].supplierId) {
													commonValues.push($scope.suppliersForSetDataPopUp[c]);
													}
												}
											}
											
											
													$scope.suppliersForSetDataPopUp=commonValues;
													}else{
														$scope.suppliersForSetDataPopUp=response;
													}
													
													$scope.suppliersForSetDataPopUp=$scope.getUniqueObjectsFromList($scope.suppliersForSetDataPopUp,'supplierId');
								});	
							}
							
							
							
						
						$scope.setDatapopup.balePickupStartDate=new Date();
						
					$scope.setDatapopup.materialSelected=[];

						$rootScope.BodyOverflow = true;
						
						frequencyService.getAllFrequency().then(
						 function(response) {
							 $scope.recommandedFrequency=response.data;
							   
							  if($scope.frequencyIdSelected !=undefined){
								   for(var i=0;i<$scope.recommandedFrequency.length;i++){
								  if($scope.frequencyIdSelected == $scope.recommandedFrequency[i].frequencyId){
									  
									  $scope.frequencySelected=$scope.recommandedFrequency[i];									  
									  break;
								  }
							  }
							 
							  $scope.setDatapopup.frequency=$scope.frequencySelected;
							//  $scope.setDatapopup.frequencyTimeValue =$scope.frequencyDaySelected;

							
							
							

							  $scope.setDatapopup.frequencySelectedSubCategoryValues = $scope.frequencySubCategories[$scope.setDatapopup.frequency.description];
							  
							  							
							if($scope.frequencyDaySelected !=undefined){
								$scope.setDatapopup.frequencyTimeValue =$scope.frequencyDaySelected+"";
							}else{
								$scope.setDatapopup.frequencyTimeValue ='NA';
							}
							
							  }
							  
						 });
						if (action == 'set') {
							$scope.editAction = false;
							$scope.setDatapopup={};
							$scope.setDatapopup.balePickupStartDate=new Date();
							$scope.setDataLoader=false;
							$scope.destinationSitesForDestinationSelected =[];
						}
					
						if (action == 'edit') {
							
								 supplierService.getSuppliersFromCustomerSite($scope.currentCustomerSiteId).then(
									 function(response) {							 
									 for(var h=0;h<$scope.allSuppliers.length;h++){
										 for(var h1=0;h1<response.length;h1++){
											 if(response[h1] == $scope.allSuppliers[h].supplierId){
												 $scope.supplierFound=$scope.allSuppliers[h];
												 $scope.updateSupplier($scope.allSuppliers[h]);
												 break;
											 }
										 }
									 }
									 $scope.setDatapopup.supplier=$scope.supplierFound;

									 });
									 
									 
									 
									 customerService.getCustomerSitesForCustomerSiteId($scope.currentCustomerSiteId).then(
									function(response) {
										
										$scope.sellCustomerSiteFound=response;
										
										$scope.sellCustomerFound=[];

										customerService.getCustomerId(response).then(
											function(response) {
																						
												for(var i=0;i<response.length;i++){
													for(var k=0;k<$scope.sellCustomersForCustomerSelected.length;k++){
														if(response[i]==$scope.sellCustomersForCustomerSelected[k].customerId){
															$scope.sellCustomerFound.push($scope.sellCustomersForCustomerSelected[k]);
															$scope.updateSellCustomer($scope.sellCustomersForCustomerSelected[k]);													
														}					
													}
												}
												$scope.setDatapopup.sellCustomer=$scope.sellCustomerFound;
												$scope.setDatapopup.sellCustomer=$scope.getUniqueObjectsFromList($scope.setDatapopup.sellCustomer,'customerId');
											});
									});
									
									
									
									 var frequencySelected ={}
							
									
									customerService.getMatrialsForCustomerSiteId($scope.currentCustomerSiteId).then(
									function(response) {
										
											$scope.selectedMaterialFound=[];
											for(var j=0;j<$scope.materials.length;j++){
												
											angular.forEach(response, function(value, key) {
											if(key==$scope.materials[j].materialId){
												$scope.materials[j].avgBaleWeight=value;
												$scope.selectedMaterialFound.push($scope.materials[j]);	
												$scope.updateMaterial($scope.materials[j]);
											};
											});
										}
											

										$scope.setDatapopup.materials=$scope.selectedMaterialFound;
										$scope.setDatapopup.materials=$scope.getUniqueObjectsFromList($scope.setDatapopup.materials,'materialId');
									});
									
									if($scope.startDaySelected !=undefined){

										$scope.setDatapopup.balePickupStartDate=$scope.startDaySelected;
									}else{
										$scope.setDatapopup.balePickupStartDate=new Date();
									}
									
									if($scope.endDaySelected !=undefined){
										$scope.setDatapopup.balePickupEndDate=$scope.endDaySelected;
	
									}
									
									 $timeout( function(){

										$scope.sellCustomerSiteSelected=[];
										customerService.getAllSellCustomersForBuyCustomerAndSupplier($scope.currentBuyCustomerId,$scope.setDatapopup.supplier.supplierId).then(function(response) {
											
											$scope.sellCustomersForSetDataPopUp  = response;
										});
										
										if($scope.destinationSitesForDestinationSelected!=undefined){
												for(var i=0;i<$scope.destinationSitesForDestinationSelected.length;i++){
											for(var j=0;j<$scope.sellCustomerSiteFound.length;j++){
												if($scope.destinationSitesForDestinationSelected[i].customerSiteId == $scope.sellCustomerSiteFound[j]){
												$scope.sellCustomerSiteSelected.push($scope.destinationSitesForDestinationSelected[i]);
											}
											}
											
										}
										}
									
										
										$scope.setDatapopup.sellCustomerSiteSelected=$scope.sellCustomerSiteSelected;
										
										$scope.setDatapopup.sellCustomerSite=$scope.sellCustomerSiteSelected;
										
										$scope.setDatapopup.sellCustomerSite=$scope.getUniqueObjectsFromList($scope.setDatapopup.sellCustomerSite,'customerSiteId');
										
										$scope.setDataLoader=false;
										}, 5000 );
										

									
						}
					
					
					
					}
					
					
					
					
					/*start by deepak*/
					
					
					$scope.updateAssignmentPopup = function(assignment) {
					
				
						$scope.isupdateAssignmentPopup=true;
						
							customerService.getMatrialsForCustomerSiteId(assignment.buyCustomerSiteId).then(
									function(response) {
										
										$rootScope.materialResult=response;
										
										
									for(var i=0; i<$rootScope.materialResult.length; i++){
										for(var j=0;j<$rootScope.materialsSupplierProfile.length;j++){
											if($rootScope.materialResult[i]==$rootScope.materialsSupplierProfile[j].materialId){
												
												$rootScope.storeSupplierMaterial.push($rootScope.materialsSupplierProfile[j]);		
															
											}
										}
									}	
									});	
						
								supplierService.getSuppliersFromCustomerSite(assignment.buyCustomerSiteId).then(
									function(response) {
								
								$scope.result=response;
									for(var i=0; i<$scope.result.length;i++){
										
										for(var j=0; j<$scope.allSuppliers.length;j++){
											if($scope.allSuppliers[j].supplierId==$scope.result[i]){
											
											$rootScope.suppliermatchdata.push($scope.allSuppliers[j]);
											}
										}
									}		
							}); 
								
								customerService.getCustomerSitesForCustomerSiteId(assignment.buyCustomerSiteId).then(
									function(response) {
										$rootScope.customerSiteIDs=response;
									
									customerService.getCustomerId($rootScope.customerSiteIDs).then(
									function(response) {
										$rootScope.customerId=response;
									
									for(var i=0;i<$rootScope.customerSiteIDs.length;i++){
											for(j=0;j<$rootScope.sellCustomer.length;j++){
												if($rootScope.sellCustomer[j].customerId==$rootScope.customerId[i]){
													$rootScope.saved.push($rootScope.sellCustomer[j]);
											}
											}
										}	
									
									customerService
								.getCustomerSitesForBuyCustomer($rootScope.saved)
								.then(
										function(response) {
											$scope.sellCustomerSiteDumpSelected = response;											
										})
									})										
									
								});
									
						}
					
					
					/*end by deepak*/
					
					
					/*Assignment Tab DriverUpdate by deepak*/
						
							$scope.updateAssignDriverPopup = function(driverSupplierView) {
								$scope.isupdateAssignDriverPopup=true;
								$scope.driverSupplierView=driverSupplierView;
						userService.getAllUsersForSupplierId(driverSupplierView.supplierId)
									.then(function(response) {
								$scope.driverList = response;
									});
							}
					
					
					
					
					
					
						
					/*end*/
					
					$scope.setDataPopupContentClose = function() {
						$rootScope.BodyOverflow = false;
						angular.forEach($scope.materials, function(value, key) {
							value.checked = false;
							value.avgBaleWeight = "";
						})
						angular.forEach($scope.supplierSites, function(value,
								key) {
							value.checked = false;
						})
						$scope.setDatapopup.supplierSelected = [];
						$scope.setDatapopup.materialSelected = [];
						$scope.foundMaterials=[];
						$scope.sellCustomerSiteFound=[];
						$rootScope.supplierFound=[];
						$scope.sellCustomerPopupFound=[];
						$scope.sellCustomerFound=[];
						$scope.setDataPopup = false; // Popup Close function
						$scope.isupdateAssignmentPopup=false;
						
						if($scope.error == undefined){
							$scope.error=[];
						}
						$scope.error.errorDateFlag = false;
						$scope.error.errorServiceProviderFlag = false;
						$scope.error.errorMaterialFlag = false;
						$scope.error.errorSellCustomerFlag = false;
						$scope.error.errorSellCustomerSiteFlag = false;
						$scope.error.errorFrequencyFlag = false;
						$scope.error.errorFrequencyTimeValueFlag = false;
						
					}



					$scope.selectedStores = [];
					$scope.toggleSelection = function toggleSelection(
							customerSite) {
						var idx = $scope.selectedStores.indexOf(customerSite);
						// Is currently selected
						if (idx > -1) {
							$scope.selectedStores.splice(idx, 1);
						}
						// Is newly selected
						else {
							$scope.selectedStores.push(customerSite);
						}
					};
					// Assign Activity
					$scope.singleSelect1Value = "Select Driver Name";
					$scope.showSingleSelect1 = function() {
					$scope.ss3 = false;
					$scope.ss2 = false;
					$scope.ss1 = !$scope.ss1;
					}
					$scope.singleChange = function(singleSelect1) {
					$scope.singleSelect1Value = singleSelect1;
					}

					$scope.singlecustomerValue = "Select Sell Customer";
					$scope.showSinglecustomer = function() {
					$scope.ss1 = false;
					$scope.ss3 = false;
					$scope.ss2 = !$scope.ss2;
					}
					$scope.singleChangeoncustomer = function(customerlist) {
					$scope.singlecustomerValue = customerlist;
					}
					$scope.singledestinationValue = "Select Destination";
					$scope.selectdestination = function() {
					$scope.ss1 = false;
					$scope.ss2 = false;
					$scope.ss3 = !$scope.ss3;
					}
					$scope.selectdestinationpart = function(destination) {
					$scope.singledestinationValue = destination;
					}

					$scope.assignactivity = function() {

						$scope.ss1 = false;
						$scope.ss2 = false;
						$scope.ss3 = false;
						$scope.isupdateFieldsForAssignActivity = true;
					}
					$scope.assignactivityclose = function() {
					$scope.isupdateFieldsForAssignActivity = false;
					}

					$scope.setFrequencyNumber = function(number) {

						if (number == 1) {
							$scope.frequency.monthly = undefined;
							$scope.frequency.oncall = undefined;

						}

						else if (number == 2) {
							$scope.frequency.weeklyfilter = undefined;
							$scope.frequency.oncall = undefined;
						} else if (number == 3) {
							$scope.frequency.oncall = {
								"3" : true
							};
							$scope.frequency.weeklyfilter = undefined;
							$scope.frequency.monthly = undefined;

						}
					}
					
					$scope.assignmentSupplierSelected=undefined;
						$scope.buyCustomerSelectedAct=undefined;
						
					$scope.showAssignedDrivers = function() {
												
						$scope.loaderAssignment=true;
						var assignDriverDTO={};
						
						if($scope.buyCustomerSelectedAct!= undefined && $scope.buyCustomerSelectedAct!=null && $scope.buyCustomerSelectedAct!=""){
							var buyCustomerSelectedAct =JSON.parse($scope.buyCustomerSelectedAct);
						assignDriverDTO.customerId = buyCustomerSelectedAct.customerId;
						}
						if($scope.assignmentSupplierSelected!= undefined && $scope.assignmentSupplierSelected!=null && $scope.assignmentSupplierSelected!="" ){
							var assignmentSupplierSelected =JSON.parse($scope.assignmentSupplierSelected);
						assignDriverDTO.supplierId=assignmentSupplierSelected.supplierId;
						}
						
						
						var startDate=$scope.assignmentDateStart;
						var endDate=$scope.assignmentDateEnd;
						
						if(startDate!=undefined && startDate!=''){
						 var startDateUTC =  Date.UTC(startDate.getUTCFullYear(), startDate.getUTCMonth(),
						 startDate.getUTCDate()+1,startDate.getUTCHours(), startDate.getUTCMinutes(), startDate.getUTCSeconds());
						 
						 startDate= new Date(startDateUTC);	
						}
						
						
						if(endDate!=undefined && endDate!=''){
							 var endDateUTC =  Date.UTC(endDate.getUTCFullYear(), endDate.getUTCMonth(), endDate.getUTCDate()+1,
							 endDate.getUTCHours(), endDate.getUTCMinutes(), endDate.getUTCSeconds());
						 endDate= new Date(endDateUTC);
						}
						
						assignDriverDTO.startDate=startDate;
						assignDriverDTO.endDate=endDate;
						pickupAssignmentService.getAssignedDriver(assignDriverDTO).then(function(response) {
							$scope.loaderAssignment=false;			
							$scope.driverSupplierPickupsView=response.data;					
							$scope.assignmentViewValid=true;

							$scope.jsonForAssignmentExport=[];
							
							$scope.assignemntfileName = "Assignment";
							$scope.exportAssignmentData = [];
							$scope.exportAssignmentData.push(["Customer", "StoreName", "Day Driver","Night Driver", "Assigned Date From", "Assigned Date To"]);
	
					for(var i=0;i<$scope.driverSupplierPickupsView.length;i++){
						
						var dayDriver = ($scope.driverSupplierPickupsView[i].driver1_FirstName||"")+" "+($scope.driverSupplierPickupsView[i].driver_1_LastName||"");
						
						var nightDriver=($scope.driverSupplierPickupsView[i].driver2_FirstName||"")+" "+($scope.driverSupplierPickupsView[i].driver2_LastName||"");
						
						
						$scope.driverSupplierPickupsView[i].dayDriver=dayDriver;
						$scope.driverSupplierPickupsView[i].nightDriver=nightDriver;
						
						
						if($scope.driverSupplierPickupsView[i].startDate != undefined){
							$scope.driverSupplierPickupsView[i].startDate=$filter('date')($scope.driverSupplierPickupsView[i].startDate, 'MM/dd/yyyy');
						}else{
							$scope.driverSupplierPickupsView[i].startDate='';
						}
						
						if($scope.driverSupplierPickupsView[i].endDate != undefined){
							$scope.driverSupplierPickupsView[i].endDate=$filter('date')($scope.driverSupplierPickupsView[i].endDate, 'MM/dd/yyyy');
						}else{
							$scope.driverSupplierPickupsView[i].endDate='';
						}
						
						/*
						$scope.driverSupplierPickupsView[i].startDate=startDate;
						$scope.driverSupplierPickupsView[i].endDate=endDate;
						*/
						
						
						//$scope.driverSupplierPickupsView[i].endDate=new Date($scope.driverSupplierPickupsView[i].endDate);
						
						$scope.jsonForAssignmentExport.push({
							"Customer":$scope.driverSupplierPickupsView[i].customerName,
							"StoreName":$scope.driverSupplierPickupsView[i].siteName,
							"Day Driver":dayDriver,
							"Night Driver":nightDriver,
							"Assigned Date From":$scope.driverSupplierPickupsView[i].startDate ,
							"Assigned Date To": $scope.driverSupplierPickupsView[i].endDate
								
							});
							
							
							$scope.exportAssignmentData.push([$scope.driverSupplierPickupsView[i].customerName, $scope.driverSupplierPickupsView[i].siteName, dayDriver,nightDriver, $scope.driverSupplierPickupsView[i].startDate, $scope.driverSupplierPickupsView[i].endDate]);
							
							
							
							
							 $scope.jsonForAssignmentExport = $filter('orderBy')(
						 $scope.jsonForAssignmentExport, 'StoreName');
					}
					
					
					
					 $scope.dtColumnsAV = [
										   DTColumnBuilder.newColumn('customerName').withTitle('Customer'),
										   DTColumnBuilder.newColumn('siteName').withTitle('Store'),
										   DTColumnBuilder.newColumn('dayDriver').withTitle('Day Driver'),
										   DTColumnBuilder.newColumn('nightDriver').withTitle('Night Driver'),
										    DTColumnBuilder.newColumn('startDate').withTitle('Assigned Date From'),
											 DTColumnBuilder.newColumn('endDate').withTitle('Assigned Date To'),
											  DTColumnBuilder.newColumn('').withTitle('Edit').notSortable().renderWith(function(data, type, full, meta) {
									var htmlTemp='<div></div>';
												
												
										htmlTemp='<div style="width: 20px; padding-top: 0px !important; padding-bottom: 0px !important">'+
												'<img src="/brta/app/ui/resources/images/editdata.png"'+ 
									'ng-click="assignTabIndex();assignmentPopupOpenTabindex();addAssignDriverPopupCalling('+full.customerSiteId+')"'+
									'class="editImageAlignment" style="height: 40px;margin-top:-10px;margin-bottom:-10px"></div>';
									
									
									
									return htmlTemp;	
										})
									   ];  
									  
							  $scope.dtOptionsAV= DTOptionsBuilder.newOptions().withOption('data', $scope.driverSupplierPickupsView).withOption('createdRow', function (row, data, dataIndex) {
								
									$compile(angular.element(row).contents())($scope);
								}).withOption('headerCallback', function( thead, data, start, end, display ) {
									$compile(angular.element(thead).contents())($scope);
								});
					
					
					
											
							})
							
							
					}
				
				
				
				$scope.checkSelectAllButtonEnableOrDisable = function(filteredRows,selectedCustomerSites,viewPage){
					
						if(filteredRows!=undefined && selectedCustomerSites!=undefined && 
						filteredRows.length == selectedCustomerSites.length){
							for(var i=0;i<filteredRows.length;i++){
								var valueMatched=false;
								for(var j=0;j<selectedCustomerSites.length;j++){
									
									if(viewPage === 'SLV'){
										if (filteredRows[i].customerSiteId === selectedCustomerSites[j].customerSiteId) {
										valueMatched=true;
										break;
									}
									}else{
										if (filteredRows[i].sequenceNo === selectedCustomerSites[j].sequenceNo) {
										valueMatched=true;
										break;
									}
									}
									
								}
								
								if(viewPage == 'SLV'){
										if(valueMatched){
							          		$scope.modeltemp.selectall=true;
							          }else{
							          		$scope.modeltemp.selectall=false;
								      		break;
							          }
								}else{
										if(valueMatched){
							          		$scope.Suppliertemp.selectall=true;
							          }else{
							          		$scope.Suppliertemp.selectall=false;
								      		break;
							          }
								}
								
								
							          
							    }
								
						}else{
							if(viewPage == 'SLV'){
								$scope.modeltemp.selectall=false;
							}else{
								$scope.Suppliertemp.selectall=false;
							}
							
						}
					}

				$scope.updateSearchFilterSLV= function(){
					
					$('#storeListViewReacords').on('search.dt', function() {
						var table = $('#storeListViewReacords').DataTable();
						
						var searchValue = $('.dataTables_filter input').val();
						
						var filteredRowsSLV=table.rows( { filter : 'applied',page: "all"} ).data();
						
						$scope.checkSelectAllButtonEnableOrDisable(filteredRowsSLV, $scope.selectedCustomerSites,'SLV');
						
						
						if(searchValue != undefined && searchValue!=''){
							if($scope.modeltemp.selectall == true && filteredRowsSLV!=undefined && filteredRowsSLV.length>0 ){
								
								/*for (var i = 0; i < $scope.customerSites.length; i++) {
									var name="checked_"+$scope.customerSites[i].customerSiteId;
										$scope[name]=false;
									$scope.addToSelectedCustomerSites($scope.customerSites[i],false);
								}
								for(var i=0;i<filteredRowsSLV.length;i++){
									
									var name="checked_"+filteredRowsSLV[i].customerSiteId;
										$scope[name]=true;
									$scope.addToSelectedCustomerSites(filteredRowsSLV[i],true);
								}*/
								

							}
							
						}else{
							if($scope.modeltemp.selectall == true && filteredRowsSLV!=undefined && filteredRowsSLV.length>0 ){
								for(var i=0;i<filteredRowsSLV.length;i++){
									var name="checked_"+filteredRowsSLV[i].customerSiteId;
										$scope[name]=true;
									$scope.addToSelectedCustomerSites(filteredRowsSLV[i],true);
									
								}
							}
						}
						 
					}); 

				}				
					
					
					
				
				
				$scope.updateSearchFilterSSV= function(){					
					$('#listResources').on('search.dt', function() {
						var table = $('#listResources').DataTable();
						
						var value = $('.dataTables_filter input').val();
						
						var filteredRowsSSV=table.rows( { filter : 'applied',page: "all"} ).data();
						
						$scope.checkSelectAllButtonEnableOrDisable(filteredRowsSSV, $scope.selectedCustomerStoresSites,'SSV');
						
						if(value != undefined && value!=''){
							if($scope.Suppliertemp.selectall == true && filteredRowsSSV!=undefined && filteredRowsSSV.length>0 ){
								
								/*for (var i = 0; i < $scope.baleAssignments.length; i++) {
									var name="checkedSSV_"+$scope.baleAssignments[i].sequenceNo;
										$scope[name]=false;
									$scope.assignBtn= true;
									$scope.addToSelectedStoreSupplierList($scope.baleAssignments[i],false);
								}
								for(var i=0;i<filteredRowsSSV.length;i++){
									
									var name="checkedSSV_"+filteredRowsSSV[i].sequenceNo;
										$scope[name]=true;
									$scope.assignBtn= false;
									$scope.addToSelectedStoreSupplierList(filteredRowsSSV[i],true);
								}*/
								
							}
							
							/*else{
								for (var i = 0; i < $scope.baleAssignments.length; i++) {
									var name="checkedSSV_"+$scope.baleAssignments[i].sequenceNo;
										$scope[name]=false;
									$scope.assignBtn= true;
									$scope.addToSelectedStoreSupplierList($scope.baleAssignments[i],false);
								}
								
								
							}*/
						}
					
						else{

							if($scope.Suppliertemp.selectall == true && filteredRowsSSV!=undefined && filteredRowsSSV.length>0 ){
								for(var i=0;i<filteredRowsSSV.length;i++){
									
									var name="checkedSSV_"+filteredRowsSSV[i].sequenceNo;
										$scope[name]=true;
									$scope.assignBtn= false;
									$scope.addToSelectedStoreSupplierList(filteredRowsSSV[i],true);
									
								}
							}
						}
						 
					}); 

				}
				

					
								
					//$scope.storeSupplierCustomerSelected={};
					$scope.getAllAssignmentsWithFilters = function() {
						
						$scope.assignActivityPopupBtn=true;
						
						if($scope.selectedCustomerStoresSites!=undefined && $scope.selectedCustomerStoresSites.length >0){
							
							for(var i=0;i<$scope.selectedCustomerStoresSites.length;i++){
							var name="checkedSSV_"+$scope.selectedCustomerStoresSites[i].sequenceNo;
							$scope[name]=false;	
							}
							
						}
						
						$scope.selectedCustomerStoresSites=[];
						$scope.showSuccessMessageActivity = false;
						$scope.dropdown.errorDropDownAssignVendorFlag = false;
						$scope.assignBtn = true;
						$scope.selectallInAssign = false;
						$scope.storeListAllAssignBtn = true;
						$scope.dropdown = {};
						$scope.loaderStoreSupplierList = true;
						var assignDriverDTO = {};
						if(($scope.storeSupplierCustomerSelected!=undefined)&&($scope.storeSupplierCustomerSelected!=null) && ($scope.storeSupplierCustomerSelected!="")){
						var storeSupplierCustomerSelected =JSON.parse($scope.storeSupplierCustomerSelected);	
						assignDriverDTO.customerId = storeSupplierCustomerSelected.customerId;}
						if(($scope.supplierSelectedAct!=undefined)&&($scope.supplierSelectedAct!=null)  && ($scope.supplierSelectedAct!="")){
						 var supplierSelectedAct =JSON.parse($scope.supplierSelectedAct);	
						assignDriverDTO.supplierId = supplierSelectedAct.supplierId;}
						if(($scope.state!=null)||($scope.state!=undefined)){
						assignDriverDTO.state = $scope.state;}
						
						var startDate=$scope.startDateSSL;
						var endDate=$scope.endDateSSL;
						
						if(startDate!=undefined && startDate!=''){
						var startDateUTC =  Date.UTC(startDate.getUTCFullYear(), startDate.getUTCMonth(),
						 startDate.getUTCDate()+1,0,0,0);
						 startDate= new Date(startDateUTC);	
						}
						
						
						if(endDate!=undefined && endDate!=''){
							 var endDateUTC =  Date.UTC(endDate.getUTCFullYear(), endDate.getUTCMonth(), endDate.getUTCDate()+1,0,0,0);
						 endDate= new Date(endDateUTC);
						}
						
						
						assignDriverDTO.startDate = startDate;
						assignDriverDTO.endDate = endDate;
						console.log("assignDriverDTO"+JSON.stringify(assignDriverDTO));
						
				pickupAssignmentService.getStoreSupplierList(assignDriverDTO)
								.then(function(response) {
											$scope.baleAssignments=[];
											$scope.baleAssignments = response.data;
											$scope.loaderStoreSupplierList = false;
											$scope.storeListAllAssignBtn = false;
											$scope.storeSupplierCheckAllBtn = false;
					$scope.jsonForStoreSupplierExport=[];
					
					$scope.storeSupplierfileName = "Store Supplier List";
					$scope.exporStoreSupplierData = [];
					$scope.exporStoreSupplierData.push(["Customer Name", "StoreName", "Supplier Name","Sell Customer Site Name", "Material Profile", "Frequency", "Start Date","End Date" ]);
	
					for(var i=0;i<$scope.baleAssignments.length;i++){
						
						var frequency=($scope.baleAssignments[i].frequency ?$scope.baleAssignments[i].frequency: "" )+ 
							($scope.baleAssignments[i].frequencyDay!=null ?  "("+$scope.baleAssignments[i].frequencyDay+")":" ");
						
						
						
						if($scope.baleAssignments[i].balePickupStartDate != undefined){
							$scope.baleAssignments[i].balePickupStartDateAsString=$filter('date')($scope.baleAssignments[i].balePickupStartDate, 'MM/dd/yyyy');
						//	alert("$scope.baleAssignments[i].balePickupStartDateAsString"+ $scope.baleAssignments[i].balePickupStartDateAsString);
						}else{
							$scope.baleAssignments[i].balePickupStartDateAsString='';
						}
						
						if($scope.baleAssignments[i].balePickupEndDate!= undefined){
							$scope.baleAssignments[i].balePickupEndDateAsString =$filter('date')($scope.baleAssignments[i].balePickupEndDate, 'MM/dd/yyyy');
						}else{
							$scope.baleAssignments[i].balePickupEndDateAsString ='';
						}
						
						
						
						$scope.baleAssignments[i].frequencyNew=frequency;
						
						
						$scope.jsonForStoreSupplierExport.push({
							"Customer Name":$scope.baleAssignments[i].buyCustomerName,
							"StoreName":$scope.baleAssignments[i].buyCustomerSiteName,
							"Supplier Name":$scope.baleAssignments[i].supplierDescription,
							"Sell Customer Site Name":$scope.baleAssignments[i].sellCustomerSiteName,
							"Material Profile":$scope.baleAssignments[i].materialDescription,
							"Frequency":frequency,	
							"Start Date":$scope.baleAssignments[i].balePickupStartDateAsString,
							"End Date":$scope.baleAssignments[i].balePickupEndDateAsString
										})

							$scope.exporStoreSupplierData.push([$scope.baleAssignments[i].buyCustomerName, $scope.baleAssignments[i].buyCustomerSiteName, $scope.baleAssignments[i].supplierDescription,$scope.baleAssignments[i].sellCustomerSiteName, $scope.baleAssignments[i].materialDescription, $scope.baleAssignments[i].frequency, $scope.baleAssignments[i].balePickupStartDateAsString, $scope.baleAssignments[i].balePickupEndDateAsString]);
							
										 $scope.jsonForStoreSupplierExport = $filter('orderBy')(
											$scope.jsonForStoreSupplierExport, 'StoreName');
													
								}
								
								$scope.storeSupplierViewValid=true;
								
								$scope.dtInstanceSSV={};
					  $scope.dtColumnsSSV = [
										  DTColumnBuilder.newColumn('Select').withTitle(
										'<div class="custom-check checkboxMarginremv">'
										+'<input type="checkbox" id="selectallInAssign"'
										+'	ng-disabled="storeSupplierCheckAllBtn"'
										+'	ng-model="Suppliertemp.selectall"'
										+'	ng-change="triggeredSelectAllSSV()" tabindex="0" class="checkboxTabindex">'
										+'<label for="selectallInAssign" class="selctall_label">'
										+'<div class="select"></div></label></div> <span class="selectCheckBoxAlign">Select</span>')
										.notSortable().withOption("searchable", false).renderWith(function(data, type, full, meta) {
									var htmlTemp='<div class="custom-check"><input type="checkbox" tabindex="0" class="checkboxTabindex" id="'+full.sequenceNo+'" '
											+' ng-model="checkedSSV_'+full.sequenceNo+'" '
											+'ng-change="updateCustomerSiteForSSV('+full.sequenceNo+',checkedSSV_'+full.sequenceNo+');generateAssignmentTooltip()" /> '
											+' <label  for="'+full.sequenceNo+'"></label> </div>'; 
									return htmlTemp;	
										}),
										   DTColumnBuilder.newColumn('buyCustomerName').withTitle('<span class="tr_width">Customer <span class="tableWrapping">Name</span></span>'),
										   
										   DTColumnBuilder.newColumn('buyCustomerSiteName').withTitle('<span class="tr_width">Store <span class="tableWrapping">Name</span></span>'),
										   
										   DTColumnBuilder.newColumn('supplierDescription').withTitle('Supplier Name'),
										   DTColumnBuilder.newColumn('sellCustomerSiteName').withTitle('<span class="tr_width" style="width: 18% !important;">Sell Customer  Site Name</span>'),
										   DTColumnBuilder.newColumn('materialDescription').withTitle('<span class="tr_width">Material <span class="tableWrapping">Profile</span></span>'),
										   DTColumnBuilder.newColumn('frequencyNew').withTitle('<span class="tr_width">Frequency </span>'),
										     DTColumnBuilder.newColumn('balePickupStartDateAsString').withOption('sType','date').withTitle('<span class="tr_width">Start Date</span>'),
										   DTColumnBuilder.newColumn('balePickupEndDateAsString').withOption('sType','date').withTitle('<span class="tr_width">End Date</span>	'),
										   DTColumnBuilder.newColumn('').withTitle('<span class="tr_width" style="width: 20px !important;">Edit</span>').notSortable().renderWith(function(data, type, full, meta) {
									var htmlTemp='<div></div>';
																		
									if(!$scope.isSupplierRole){
										htmlTemp='<div style="width: 20px; padding-top: 0px !important; padding-bottom: 0px !important">'+
									'<img src="/brta/app/ui/resources/images/editdata.png" '+
									'ng-click="setdataPopup('+full.sequenceNo+');assignTabIndex();" class="editImageAlignment" style="height: 40px; margin-top:-10px ; margin-bottom:-10px;!important"></div>';
									}else{
										htmlTemp='<div style="width: 20px; padding-top: 0px !important; padding-bottom: 0px !important">'+
										'<img src="/brta/app/ui/resources/images/disabled-edit.png" ng-disabled="isSupplierRole"'+
									
									'class="editImageAlignment save-changes" style="height: 40px; margin-top:-10px ; margin-bottom:-10px;!important">';
									}
									
									
									return htmlTemp;	
										})
									   ]; 

			  
							  $scope.dtOptionsSSV = DTOptionsBuilder.fromFnPromise().withOption('data', $scope.baleAssignments).withOption('createdRow', function (row, data, dataIndex) {
								  
									$compile(angular.element(row).contents())($scope);
								}).withOption('headerCallback', function( thead, data, start, end, display ) {
									$compile(angular.element(thead).contents())($scope);
								}).withOption('stateSave', false)
								.withOption('scrollX', true);
								});
								
								
							if($scope.buyCustomerSelected){
								
								customerService.getMatrialsForBuyCustomer($scope.buyCustomerSelected.customerId)
								.then(function(response) {
									$scope.materials = response;
									
								});
								
								customerService.getAllSellCustomersForBuyCustomer(
								$scope.buyCustomerSelected.customerId).then(function(response) {
							$scope.sellCustomersForCustomerSelected = response;
						});
								
								$scope.loaderAssignment=false
								}else{
								customerService.getAllMatrials()
								.then(function(response) {
									$scope.materials = response;

								});
								customerService.getAllSellCustomers().then(function(response) {
							$scope.sellCustomersForCustomerSelected = response;
						});
								
								$scope.loaderAssignment=false
								}
								
								$scope.Suppliertemp.selectall=false;
								$scope.selectedCustomerStoresSites=[];
								
								 $interval(function() {
                                                $("#storeListViewReacords th:first-child").css("background-image", "none");
                                                $("#listResources th:first-child").css("background-image", "none");
                                });
								
								
								
					}
					
					
					
					
					$scope.assignmentList = [];

					$scope.getDestinationSites = function() {
						customerService
								.getCustomerSitesForBuyCustomer($scope.setDatapopup.sellCustomerDestination)
								.then(function(response) {
											$scope.destinationSitesForDestinationSelected = response;
											$scope.loaderActiveActivityList = false;
										});
					}
					
					
		
					
					$scope.StartDateFun = function() {
						$scope.StartDate.opened = true;
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
						$scope.$watch("startDate", function(newValue, oldValue) {		
							$("#startDateval_store").focus();
							});
					};
					$scope.StartDate = {
						opened : false
					};
					
					$scope.StartupDateFun = function() {
						
						
						$scope.StartupDate.opened=true;
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
						$scope.$watch("startDateSSL", function(newValue, oldValue) {		
							$("#startDateSSL_storelist").focus();
							});
					};
					$scope.StartupDate= {
						opened : false
					}; 
					
					$scope.AssignmentStartDate = function() {
						$scope.AssignmentDateStart.opened=true;
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
						$scope.$watch("assignmentDateStart", function(newValue, oldValue) {		
							$("#assignmentDateStart_assign").focus();
							});
					};
					$scope.AssignmentDateStart= {
						opened : false
					};
					
					 $scope.EndDateFun = function() {
		 
					   $scope.EndDate.opened = true;
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
					 $scope.$watch("endDate", function(newValue, oldValue) {		
							$("#endDateval_store").focus();
							});
				  };

				  $scope.EndDate = {
					opened: false
					
				};
					
					
					
					
					
					$scope.EndupDateFun = function() {
						$scope.EndupDate.opened = true;
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
						$scope.$watch("endDateSSL", function(newValue, oldValue) {		
							$("#endDateSSL_storelist").focus();
							});
					};
					$scope.EndupDate= {
						opened : false
					};
					
					$scope.AssignmentEndDate = function() {
						$scope.AssignmentDateEnd.opened=true;
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
						$scope.$watch("assignmentDateEnd", function(newValue, oldValue) {
							
							$("#assignmentDateEnd_assign").focus();
							});
					};
					$scope.AssignmentDateEnd= {
						opened : false
					};
					
					
					
					
					$scope.assignmentPopupStartDateFun = function() {
						
						
						$scope.AssignmentStartDatePopup.opened=true;
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
						$scope.$watch("startDateAssignmentPopup", function(newValue, oldValue) {
							$("#startDateAssignmentPopup_date").focus();
							});
					};
					$scope.AssignmentStartDatePopup= {
						opened : false
					};
					
					
					
					
					$scope.assignmentPopupEndDateFun = function() {
						
						
						$scope.AssignmentEndDatePopup.opened=true;
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
						$scope.$watch("endDateAssignmentPopup", function(newValue, oldValue) {		
							$("#endDateAssignmentPopup_date").focus();
							});
					};
					$scope.AssignmentEndDatePopup= {
						opened : false
					};

					
					
					
					$scope.showAssignDriverPopup = function(assignment) {
					$scope.assignmentForNewDriver = assignment;
					$scope.assignNewDriver = true;
					$scope.otherdrivers = [];
						userService.getAllActiveUsersForSupplierSite($scope.supplierSelectedAct)
								.then(function(response) {
											$scope.drivers = response;
											
											
											angular.forEach($scope.drivers,
															function(value, key) {
																if (assignment.user.userId != value.userId) {
																	$scope.otherdrivers
																			.push(value);
																}
															})
										})
					}
					$scope.closeAssignNewDriverPopup = function() {
					$scope.assignNewDriver = false;
					}
					$scope.addDivertedUserToAssignment = function(assignment) {
											assignment.divertDate = new Date(assignment.nextPickupDate);

					balepickupService.addNewDriver(assignment).then(
								function() {
									$scope.assignNewDriver = false;
								});

					}

					$scope.addresses = [
					                    {'state':'AL'},
					                    {'state':'AK'},
					                    {'state':'AZ'},
					                    {'state':'AR'},
					                    {'state':'CA'},
					                    {'state':'CO'},
					                    {'state':'CT'},
					                    {'state':'DE'},
					                    {'state':'FL'},
					                    {'state':'GA'},
					                    {'state':'HI'},
					                    {'state':'ID'},
					                    {'state':'IL'},
					                    {'state':'IN'},
					                    {'state':'IA'},
					                    {'state':'KS'},
					                    {'state':'KY'},
					                    {'state':'LA'},
					                    {'state':'ME'},
					                    {'state':'MD'},
					                    {'state':'MA'},
					                    {'state':'MI'},
					                    {'state':'MN'},
					                    {'state':'MS'},
					                    {'state':'MO'},
					                    {'state':'MT'},
					                    {'state':'NE'},
					                    {'state':'NV'},
					                    {'state':'NH'},
					                    {'state':'NJ'},
					                    {'state':'NM'},
					                    {'state':'NY'},
					                    {'state':'NC'},
					                    {'state':'ND'},
					                    {'state':'OH'},
					                    {'state':'OK'},
					                    {'state':'OR'},
					                    {'state':'PA'},
					                    {'state':'RI'},
					                    {'state':'SC'},
					                    {'state':'SD'},
					                    {'state':'TN'},
					                    {'state':'TX'},
					                    {'state':'UT'},
					                    {'state':'VT'},
					                    {'state':'VA'},
					                    {'state':'WA'},
					                    {'state':'WV'},
					                    {'state':'WI'},
					                    {'state':'WY'}
					                  ];

					                $scope.lov_state = [
					                    {'lookupCode':'AL',	'description': 'Alabama - AL'},
										{'lookupCode':'AK',	'description': 'Alaska - AK'},
										{'lookupCode':'AZ',	'description': 'Arizona - AZ'},
										{'lookupCode':'AR',	'description': 'Arkansas - AR'},
										{'lookupCode':'CA',	'description': 'California - CA'},
										{'lookupCode':'CO',	'description': 'Colorado - CO'},
										{'lookupCode':'CT',	'description': 'Connecticut - CT'},
										{'lookupCode':'DE',	'description': 'Delaware - DE'},
										{'lookupCode':'FL',	'description': 'Florida - FL'},
										{'lookupCode':'GA',	'description': 'Georgia - GA'},
										{'lookupCode':'HI',	'description': 'Hawaii - HI'},
										{'lookupCode':'ID',	'description': 'Idaho - ID'},
										{'lookupCode':'IL',	'description': 'Illinois - IL'},
										{'lookupCode':'IN',	'description': 'Indiana - IN'},
										{'lookupCode':'IA',	'description': 'Iowa - IA'},
										{'lookupCode':'KS',	'description': 'Kansas - KS'},
										{'lookupCode':'KY',	'description': 'Kentucky - KY'},
										{'lookupCode':'LA',	'description': 'Louisiana - LA'},
										{'lookupCode':'ME',	'description': 'Maine - ME'},
										{'lookupCode':'MD',	'description': 'Maryland - MD'},
										{'lookupCode':'MA',	'description': 'Massachusetts - MA'},
										{'lookupCode':'MI',	'description': 'Michigan - MI'},
										{'lookupCode':'MN',	'description': 'Minnesota - MN'},
										{'lookupCode':'MS',	'description': 'Mississippi - MS'},
										{'lookupCode':'MO',	'description': 'Missouri - MO'},
										{'lookupCode':'MT',	'description': 'Montana - MT'},
										{'lookupCode':'NE',	'description': 'Nebraska - NE'},
										{'lookupCode':'NV',	'description': 'Nevada - NV'},
										{'lookupCode':'NH',	'description': 'New Hampshire - NH'},
										{'lookupCode':'NJ',	'description': 'New Jersey - NJ'},
										{'lookupCode':'NM',	'description': 'New Mexico - NM'},
										{'lookupCode':'NY',	'description': 'New York - NY'},
										{'lookupCode':'NC',	'description': 'North Carolina - NC'},
										{'lookupCode':'ND',	'description': 'North Dakota - ND'},
										{'lookupCode':'OH',	'description': 'Ohio - OH'},
										{'lookupCode':'OK',	'description': 'Oklahoma - OK'},
										{'lookupCode':'OR',	'description': 'Oregon - OR'},
										{'lookupCode':'PA',	'description': 'Pennsylvania - PA'},
										{'lookupCode':'RI',	'description': 'Rhode Island - RI'},
										{'lookupCode':'SC',	'description': 'South Carolina - SC'},
										{'lookupCode':'SD',	'description': 'South Dakota - SD'},
										{'lookupCode':'TN',	'description': 'Tennessee - TN'},
										{'lookupCode':'TX',	'description': 'Texas - TX'},
										{'lookupCode':'UT',	'description': 'Utah - UT'},
										{'lookupCode':'VT',	'description': 'Vermont - VT'},
										{'lookupCode':'VA',	'description': 'Virginia - VA'},
										{'lookupCode':'WA',	'description': 'Washington  - WA'},
										{'lookupCode':'WV',	'description': 'West Virginia - WV'},
										{'lookupCode':'WI',	'description': 'Wisconsin - WI'},
										{'lookupCode':'WY',	'description': 'Wyoming - WY'}
					                ];
					

					
					
					
					
					
					/**multi select code start*/

					$scope.OptionsList = [ {
						name : 'Blaza Tony'
					}, {
						name : 'Dsouza Alex'
					}, {
						name : 'Dsouza Crest	'
					}, {
						name : 'Blaza Tony4'
					}, {
						name : 'Blaza Tony5'
					}, {
						name : 'Option6'
					}, {
						name : 'Option7'
					} ];
					$scope.Sellcustomers = [ {
						name : 'Blaza Tony'
					}, {
						name : 'Dsouza Alex'
					}, {
						name : 'Dsouza Crest	'
					}, {
						name : 'Blaza Tony4'
					}, {
						name : 'Blaza Tony5'
					}, {
						name : 'Option6'
					}, {
						name : 'Option7'
					} ];
					$scope.selectdestinations = [ {
						name : 'Blaza Tony'
					}, {
						name : 'Dsouza Alex'
					}, {
						name : 'Dsouza Crest	'
					}, {
						name : 'Blaza Tony4'
					}, {
						name : 'Blaza Tony5'
					}, {
						name : 'Option6'
					}, {
						name : 'Option7'
					} ];

					$scope.supplierCheckbox = {};
					$scope.materialCheckbox = {};
					$scope.sellCustomerSiteCheckbox={};
					$scope.sellCustomerCheckbox={};
					
					$scope.setDatapopup.supplierSelected = [];
					$scope.setDatapopup.materialSelected = [];
					
					$scope.supplierCheckbox.siteName = "Select Supplier Name";
					$scope.materialCheckbox.description = "Select Material Profile";
					$scope.sellCustomerSiteCheckbox.description = "Select Sell Customer Site";
					$scope.sellCustomerCheckbox.description="Select Sell Customer"
					
					$scope.showSingleSelect1 = function() {
					$scope.ss3 = false;
					$scope.ss2 = false;
					$scope.ss1 = !$scope.ss1;

					}
					$scope.singleChange = function(singleSelect1) {
						
					$scope.singleSelect1Value = singleSelect1;
					}

					

					

					$scope.singlecustomerValue = "Select commodity Type";
					$scope.showSinglecustomer = function() {
					$scope.ss1 = false;
					$scope.ss3 = false;
					$scope.ss2 = !$scope.ss2;

					}
					$scope.singleChangeoncustomer = function(customerlist) {
					$scope.singlecustomerValue = customerlist;
					}

					/**multi select code end**/

					/**Assign popup Start**/
					$scope.OptionsList = [ {
						name : 'Blaza Tony'
					}, {
						name : 'Dsouza Alex'
					}, {
						name : 'Dsouza Crest	'
					}, {
						name : 'Blaza Tony4'
					}, {
						name : 'Blaza Tony5'
					}, {
						name : 'Option6'
					}, {
						name : 'Option7'
					} ];
					$scope.Sellcustomers = [ {
						name : 'Blaza Tony'
					}, {
						name : 'Dsouza Alex'
					}, {
						name : 'Dsouza Crest	'
					}, {
						name : 'Blaza Tony4'
					}, {
						name : 'Blaza Tony5'
					}, {
						name : 'Option6'
					}, {
						name : 'Option7'
					} ];
					$scope.selectdestinations = [ {
						name : 'Blaza Tony'
					}, {
						name : 'Dsouza Alex'
					}, {
						name : 'Dsouza Crest	'
					}, {
						name : 'Blaza Tony4'
					}, {
						name : 'Blaza Tony5'
					}, {
						name : 'Option6'
					}, {
						name : 'Option7'
					} ];

					$scope.singleSelect1Value = "Select Driver Name";

					$scope.showSingleSelect1 = function() {
						$scope.ss3 = false;
						$scope.ss2 = false;
						$scope.ss1 = !$scope.ss1;

					}
					
					$scope.showSellCustomerCheckBox = function() {
						$scope.sellCustomerCheckBox = !$scope.sellCustomerCheckBox;

					}
					
					
					$scope.showSellCustomerSiteCheckBox = function() {
						$scope.sellCustomerSiteCheckBox = !$scope.sellCustomerSiteCheckBox;

					}
					
					$scope.singleChange = function(singleSelect1) {
						$scope.singleSelect1Value = singleSelect1;
					}

					$scope.singlecustomerValue = "Select Sell Customer";

					$scope.showSinglecustomer = function() {
						$scope.ss1 = false;
						$scope.ss3 = false;
						$scope.ss2 = !$scope.ss2;

					}
					$scope.singleChangeoncustomer = function(customerlist) {
						$scope.singlecustomerValue = customerlist;
					}

					$scope.singledestinationValue = "Select Destination";

					$scope.selectdestination = function() {
						$scope.ss1 = false;
						$scope.ss2 = false;
						$scope.ss3 = !$scope.ss3;
					}
					$scope.selectdestinationpart = function(destination) {
						$scope.singledestinationValue = destination;
					}

					// $scope.assignactivity = function() {
						// $scope.popParams = {};

						// $scope.ss1 = false;
						// $scope.ss2 = false;
						// $scope.ss3 = false;
						// $scope.updateFieldsForAssignActivity = true;
					// }
					// $scope.assignactivityclose = function() {
						// $scope.updateFieldsForAssignActivity = false;
					// }

					$scope.showDeleteNewDriverPopup = function(assignment) {
						$scope.newDriverAssignment = assignment;
						$scope.newDriverDeletePopup = true;

					}

					$scope.closeAssignNewDriverDeletePopup = function() {
						$scope.newDriverDeletePopup = false;
					}

					$scope.removeDivertedUserToAssignment = function() {
						balepickupService.removeNewDriver(
								$scope.newDriverAssignment).then(function() {
							$scope.newDriverDeletePopup = false;
							$scope.getAllAssignmentsWithFilters();
						});
					}
					
		
  
  
  
  
  
   $scope.StartDateSetDataPopupFun = function() {
  $scope.StartDateSetDataPopup.opened = true;
    $scope.dateOptionsStartSetDataPopupFun = {
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
	 /*$scope.$watch("setDatapopup.balePickupStartDate", function(newValue, oldValue) {		
							$("#balePickupStartDate_setDatapopup").focus();
							});*/
	   
  };

  $scope.StartDateSetDataPopup = {
    opened: false
  };
  
  
  
    $scope.startDateOptionsAssignmentList = {
  };
  
   $scope.$watch("assignmentDateStart", function(newStart) {
     $scope.endDateOptionsAssignmentList = {
      minDate: new Date($scope.assignmentDateStart)
   }
    
      if (newStart > $scope.assignmentDateEnd) {
        $scope.assignmentDateEnd = newStart;
      }
 }) ;
  
  $scope.startDateOptionsStoreSupplierList = {
  };
  
   $scope.$watch("startDateSSL", function(newStart) {
     $scope.endDateOptionsStoreSupplierList = {
      minDate: new Date($scope.startDateSSL)
   }
    
      if (newStart > $scope.endDateSSL) {
        $scope.endDateSSL = newStart;
      }
 }) ;
  
  
  
   $scope.startDateOptionsStoreList = {
  };
  
   $scope.$watch("startDate", function(newStart) {
    $scope.endDateOptionsStoreList = {
      minDate: new Date($scope.startDate)
    }
      if (newStart > $scope.endDate) {
        $scope.endDate = newStart;
		$scope.dateValidation();
      }
  });
  
    $scope.startDateOptionsAssignmentPopUp = {
  };
  
		 $scope.EndDateSetDataPopupFun = function() {

       $scope.EndDateSetDataPopup.opened = true;
    $scope.dateOptionEndSetDataPopupFun = {
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
	 $scope.$watch("setDatapopup.balePickupEndDate", function(newValue, oldValue) {		
							$("#balePickupEndDate_setDatapopup").focus();
							});
  };
  
  
   $scope.startDateOptionsSetData = {
   };
  
   $scope.$watch("setDatapopup.balePickupStartDate", function(newStart) {
	   
    
		$scope.endDateOptionsSetData = {
      minDate: new Date($scope.setDatapopup.balePickupStartDate)
    }
      if (newStart > $scope.setDatapopup.balePickupEndDate) {
		  if($scope.setDatapopup.balePickupEndDate!=undefined && $scope.setDatapopup.balePickupEndDate !=null && $scope.setDatapopup.balePickupEndDate!= '' ){
			  
         $scope.setDatapopup.balePickupEndDate = newStart;
		  }
		 $scope.dateValidationSetDataPopup();
       }
   
   
   })
  
  
  

  $scope.EndDateSetDataPopup = {
    opened: false
	
  };

  var headerTableStoreList = [
  { title: "CUSTOMER", dataKey: "Customer" },		
  { title: "STORE", dataKey: "Store" },		
  { title: "ADDRESS", dataKey: "Address" },		
  { title: "DATA CONFIGURATION", dataKey: "Data Configuration" }		
 		
];	
   
var headerTableStoreSupplier = [		
                     { title: "CUSTOMER NAME", dataKey: "Customer Name" },		
                     { title: "STORE NAME", dataKey: "StoreName" },		
                     { title: "SUPPLIER NAME", dataKey: "Supplier Name" },	
					 { title: "SELL CUSTOMER SITE NAME", dataKey: "Sell Customer Site Name" },					 
                     { title: "MATERIAL PROFILE", dataKey: "Material Profile" },
					 { title: "FREQUENCY", dataKey: "Frequency" },					 
                     { title: "START DATE", dataKey: "Start Date" },
					 { title: "END DATE", dataKey: "End Date" }		
                    		
                 ];	
var headerTableAssignment = [		
                    { title: "CUSTOMER", dataKey: "Customer" },		
                    { title: "STORE", dataKey: "StoreName" },		
                    { title: "DAY DRIVER", dataKey: "Day Driver" },		
                    { title: "NIGHT DRIVER", dataKey: "Night Driver" },
					{ title: "ASSIGNED DATE FROM", dataKey: "Assigned Date From" },		
                    { title: "ASSIGNED DATE TO", dataKey: "Assigned Date To" }						
                   		
                ];		
$scope.storeListPdf = function() {		
var data = [];		




if($scope.jsonForStoreListExport !=undefined && $scope.jsonForStoreListExport.length >0){
	data=$scope.jsonForStoreListExport;		

	var doc = new jsPDF('p', 'mm', [650, 550]); 
	 doc.setFontSize(13);
     // doc.text(30, 30, 'Store List View');		
			
		
doc.autoTable(headerTableStoreList, data, {	
	
 
  margin: { top: 50, left: 20, right: 20, bottom: 0 },
  styles : {
			overflow : 'linebreak'
		},
   drawHeaderCell: function (cell, data) {
	    if (cell.raw === 'ID') {//paint.Name header red
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
	doc.save('StoreListView.pdf');	
}

	
}

$scope.storeSupplierPdf = function() {		
	 var data = [];		
	 
	 if($scope.jsonForStoreSupplierExport !=undefined && $scope.jsonForStoreSupplierExport.length >0){
data=$scope.jsonForStoreSupplierExport;		
	 		
	 	var doc = new jsPDF('p', 'mm', [650, 550]); 
	 	 doc.setFontSize(13);
     	// doc.text(30, 30, 'Store Supplier');		
	 			
	   		
	   doc.autoTable(headerTableStoreSupplier, data, {		
	     // styles: {fillColor: [100, 255, 255]},		
	     // columnStyles: {		
	     // 	id: {fillColor: 255}		
	     // },		
	     // margin: {top: 60},		
	     // addPageContent: function(data) {		
	     // 	doc.text("Header", 40, 30);		
	     // }
	     margin: { top: 50, left: 20, right: 20, bottom: 0 },
		  styles : {
			overflow : 'linebreak'
		},
	     drawHeaderCell: function (cell, data) {
	    if (cell.raw === 'ID') {//paint.Name header red
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
	 	doc.save('Store Supplier List.pdf');
		}
	 			
	 }		
$scope.assignmentPdf = function() {		
	 var data = [];		
	 
	 
	 if($scope.jsonForAssignmentExport !=undefined && $scope.jsonForAssignmentExport.length >0){
	 data=$scope.jsonForAssignmentExport;		
	 		
	 	var doc = new jsPDF('p', 'mm', [650, 550]); 
	 	doc.setFontSize(13);
     	// doc.text(30, 30, 'Store Assignment');		
	   		
	   doc.autoTable(headerTableAssignment, data, {		
	     // styles: {fillColor: [100, 255, 255]},		
	     // columnStyles: {		
	     // 	id: {fillColor: 255}		
	     // },		
	     // margin: {top: 60},		
	     // addPageContent: function(data) {		
	     // 	doc.text("Header", 40, 30);		
	     // }
	     margin: { top: 50, left: 20, right: 20, bottom: 0 },
		  styles : {
			overflow : 'linebreak'
		},
	     drawHeaderCell: function (cell, data) {
	    if (cell.raw === 'ID') {//paint.Name header red
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
	 	doc.save('Assignment.pdf');	
	 }
	 
	 		
	 }	
	 
						
								

	$scope.updateDriverclose = function() {
						$scope.isupdateAssignDriverPopup = false;
					} 

	$scope.editAssignmentContentClose = function() {
		$rootScope.suppliermatchdata=[];
		$rootScope.customerSiteIDs=[];
		$rootScope.saved=[];
		$rootScope.customerId=[];
		$scope.sellCustomerSiteDumpSelected=[];
		$rootScope.storeSupplierMaterial=[];
		
						$scope.isupdateAssignmentPopup = false;
					}
$scope.multiplesupplieridselected = function(){
		$scope.ifmultiplesupplersselected=false;

}


		

							
function assigndateDiff(dateold,datenew){
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
							$scope.invalidGenAssPopupStartDateError="Select upto 10 years";
							$scope.startDateAssignmentPopup=new Date();
							
						}
						
						 else if(diff<-10){
							$scope.invalidGenAssPopupStartDateError="Select upto 10 years";
							$scope.startDateAssignmentPopup=new Date();
							
						}					
						
					}
					
					
function assignTodateDiff(dateold,datenew){
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
							$scope.invalidGenAssPopupEndDateError="Select upto 10 years";
							$scope.endDateAssignmentPopup=null;
						}
						
						 else if(diff<-10){
							$scope.invalidGenAssPopupEndDateError="Select upto 10 years";
							$scope.endDateAssignmentPopup=null;
						}					
						
					}
					
					function configFromdateDiff(dateold,datenew){
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
							$scope.invalidSetDataStartDateError="Select upto 10 years";
			$scope.setDatapopup.balePickupStartDate=new Date();
						}
						
						 else if(diff<-10){
							$scope.invalidSetDataStartDateError="Select upto 10 years";
			$scope.setDatapopup.balePickupStartDate=new Date();
						}					
						
					}
					
					function configTodateDiff(dateold,datenew){
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
							$scope.invalidSetDataEndDateError="Select upto 10 years";
			$scope.setDatapopup.balePickupEndDate=null;
						}
						
						 else if(diff<-10){
							$scope.invalidSetDataEndDateError="Select upto 10 years";
			$scope.setDatapopup.balePickupEndDate=null;
						}					
						
					}

$scope.checkValidDate=function(){
	
	
	// Assignment tag
	var assignmentEndDateValueFromId= $("#assignmentDateEnd_assign").val();	
	var assignmentStartDateValueFromId= $("#assignmentDateStart_assign").val();	
	
	
	$scope.invalidAssignmentEndDateError=undefined;
	$scope.invalidAssignmentStartDateError=undefined;
	
	if(assignmentStartDateValueFromId != ''){
		if(assignmentStartDateValueFromId != undefined  && ($scope.assignmentDateStart ==undefined || $scope.assignmentDateStart ==null) ){	
			$scope.invalidAssignmentStartDateError="Invalid Date From";
			$scope.assignmentDateStart=firstDay;
		}
	}
	
	if(assignmentEndDateValueFromId != ''){
		if(assignmentEndDateValueFromId != undefined  && ($scope.assignmentDateEnd ==undefined || $scope.assignmentDateEnd ==null) ){	
			$scope.invalidAssignmentEndDateError="Invalid Date To";
			$scope.assignmentDateEnd=null;
		}
	}
	
	
	// store list view
	
	var storeListViewEndDateValueFromId= $("#startDateval_store").val();	
	var storeListViewStartDateValueFromId= $("#endDateval_store").val();	
	
	
	$scope.invalidSLVToDateError=undefined;
	$scope.invalidSLVFromDateError=undefined;
	
	

	
	if(storeListViewStartDateValueFromId != ''){
		if(storeListViewStartDateValueFromId != undefined  && ($scope.startDate ==undefined || $scope.startDate ==null) ){	
			$scope.invalidSLVFromDateError="Invalid From Date";
			$scope.startDate=firstDay;
		}
	}
	
	if(storeListViewEndDateValueFromId != ''){
		if(storeListViewEndDateValueFromId != undefined  && ($scope.endDate ==undefined || $scope.endDate ==null) ){	
			$scope.invalidSLVToDateError="Invalid To Date";
			$scope.endDate=lastDay;
		}
	}
	
	$scope.checkStoreListViewBtn();
	
	
	
	// store sulllier view
	var storeSupplierListViewStartDateValueFromId= $("#startDateSSL_storelist").val();	
	var storeSupplierListViewEndDateValueFromId= $("#endDateSSL_storelist").val();	
	
	
	$scope.invalidSSLToDateError=undefined;
	$scope.invalidSSLFromDateError=undefined;
	
	

	
	if(storeSupplierListViewStartDateValueFromId != ''){
		if(storeSupplierListViewStartDateValueFromId != undefined  && ($scope.startDateSSL ==undefined || $scope.startDateSSL ==null) ){	
			$scope.invalidSSLFromDateError="Invalid From Date";
			$scope.startDateSSL=firstDay;
		}
	}
	
	if(storeSupplierListViewEndDateValueFromId != ''){
		if(storeSupplierListViewEndDateValueFromId != undefined  && ($scope.endDateSSL ==undefined || $scope.endDateSSL ==null) ){	
			$scope.invalidSSLToDateError="Invalid To Date";
			$scope.endDateSSL=null;
		}
	}
	
	
	//Set Data Pop updateAssignDriverPopup
	
	var setDataPopupStartDateValueFromId= $("#balePickupStartDate_setDatapopup").val();	
	var setDataPopupEndDateValueFromId= $("#balePickupEndDate_setDatapopup").val();	
	
	
	$scope.invalidSetDataStartDateError=undefined;
	$scope.invalidSetDataEndDateError=undefined;
		
	if(setDataPopupStartDateValueFromId != ''){
		if(setDataPopupStartDateValueFromId != undefined  && ($scope.setDatapopup.balePickupStartDate ==undefined || $scope.setDatapopup.balePickupStartDate ==null) ){	
			$scope.invalidSetDataStartDateError="Invalid Start Date";
			$scope.setDatapopup.balePickupStartDate=new Date();
		}else if(setDataPopupStartDateValueFromId != undefined && $scope.setDatapopup.balePickupStartDate !=undefined){
								dateold = new Date(firstDay);
							datenew = new Date(setDataPopupStartDateValueFromId);
							configFromdateDiff(dateold, datenew);
							
										}
	}
	
	if(setDataPopupEndDateValueFromId != ''){
		if(setDataPopupEndDateValueFromId != undefined  && ($scope.setDatapopup.balePickupEndDate ==undefined || $scope.setDatapopup.balePickupEndDate ==null) ){	
			$scope.invalidSetDataEndDateError="Invalid End Date";
			$scope.setDatapopup.balePickupEndDate=null;
		}else if(setDataPopupEndDateValueFromId != undefined && $scope.setDatapopup.balePickupEndDate !=undefined){
								dateold = new Date(firstDay);
							datenew = new Date(setDataPopupEndDateValueFromId);
							configTodateDiff(dateold, datenew);
							
										}
	}
	
	//Generate assignment pop updateAssignDriverPopup
	
	var genAssPopupStartDateValueFromId= $("#startDateAssignmentPopup_date").val();	
	var genAssPopupEndDateValueFromId= $("#endDateAssignmentPopup_date").val();	
	$scope.invalidGenAssPopupStartDateError=undefined;
	$scope.invalidGenAssPopupEndDateError=undefined;
	if(genAssPopupStartDateValueFromId != ''){
		if(genAssPopupStartDateValueFromId != undefined  && ($scope.startDateAssignmentPopup ==undefined || $scope.startDateAssignmentPopup ==null) ){	
			$scope.invalidGenAssPopupStartDateError="Invalid Assignment Start Date";
			$scope.startDateAssignmentPopup=new Date();
		}else if(genAssPopupStartDateValueFromId != undefined && $scope.startDateAssignmentPopup !=undefined){
								dateold = new Date(firstDay);
							datenew = new Date(genAssPopupStartDateValueFromId);
							assigndateDiff(dateold, datenew);
							
										}
		
	}
	
	
	
	
}
$scope.getDateFromAssignment= function(){
	
var genAssPopupStartDateValueFromId= $("#startDateAssignmentPopup_date").val();	
	var genAssPopupEndDateValueFromId= $("#endDateAssignmentPopup_date").val();	
	$scope.invalidGenAssPopupStartDateError=undefined;
	$scope.invalidGenAssPopupEndDateError=undefined;
if(genAssPopupEndDateValueFromId != ''){
		if(genAssPopupEndDateValueFromId != undefined  && ($scope.endDateAssignmentPopup ==undefined || $scope.endDateAssignmentPopup ==null) ){	
			$scope.invalidGenAssPopupEndDateError="Invalid Assignment End Date";
			$scope.endDateAssignmentPopup=null;
		}
		else if(genAssPopupEndDateValueFromId != undefined && $scope.endDateAssignmentPopup !=undefined){
								dateold = new Date(firstDay);
							datenew = new Date(genAssPopupEndDateValueFromId);
							assignTodateDiff(dateold, datenew);
										}
	}
}

$scope.dateValidation=function(){
	
	if($scope.startDate!=undefined && $scope.endDate!=undefined){
		if($scope.startDate.getTime() > $scope.endDate.getTime() ){
			
			$scope.endDate=$scope.startDate;
		}else{
			$scope.endDateErrorMessage=false;
		}
	}
	
	if($scope.startDateSSL!=undefined && $scope.endDateSSL!=undefined){
		if($scope.startDateSSL.getTime() > $scope.endDateSSL.getTime() ){
			$scope.endDateSSL=$scope.startDateSSL;
		//	$scope.endDateErrorMessage=true;
		}else{
			$scope.endDateErrorMessage=false;
		}
	}
		
	if($scope.assignmentDateStart!=undefined && $scope.assignmentDateEnd!=undefined){
		if($scope.assignmentDateStart.getTime() > $scope.assignmentDateEnd.getTime() ){
			$scope.assignmentDateEnd=$scope.assignmentDateStart;
		//	$scope.endDateErrorMessage=true;
		}else{
			$scope.endDateErrorMessage=false;
		}
	}
	
	if($scope.startDateAssignmentPopup!=undefined && $scope.endDateAssignmentPopup!=undefined){
		
		$scope.startDateAssignmentPopup= new Date($scope.startDateAssignmentPopup);
		$scope.endDateAssignmentPopup= new Date($scope.endDateAssignmentPopup);
	//alert("$scope.startDateAssignmentPopup.getTime()"+$scope.startDateAssignmentPopup.getTime());
		if($scope.startDateAssignmentPopup.getTime() > $scope.endDateAssignmentPopup.getTime() ){
			$scope.endDateAssignmentPopup=$scope.startDateAssignmentPopup;
		//	$scope.endDateErrorMessage=true;
		}else{
			$scope.endDateErrorMessage=false;
		}
	}
	
}

$scope.dateValidationSetDataPopup=function(){
	
	
	$scope.endDateSetDataPopupErrorMessage=false;
	if($scope.setDatapopup != undefined){
		if($scope.setDatapopup.balePickupStartDate!=undefined && $scope.setDatapopup.balePickupEndDate!=undefined){
					
					
		var startDate=new Date($scope.setDatapopup.balePickupStartDate);
		var endDate=new Date($scope.setDatapopup.balePickupEndDate);
		
		startDate.setHours(0,0,0,0);
		endDate.setHours(0,0,0,0);

		
			 $scope.setDatapopup.balePickupStartDate=startDate;
			 $scope.setDatapopup.balePickupEndDate=endDate;

		 if($scope.setDatapopup.balePickupStartDate.getTime() > $scope.setDatapopup.balePickupEndDate.getTime() ){
			 $scope.setDatapopup.balePickupEndDate=$scope.setDatapopup.balePickupStartDate;
			 
			$scope.endDateSetDataPopupErrorMessage=false;
		  }else{
			$scope.endDateSetDataPopupErrorMessage=false;
		 }
	 }else{
		 //$scope.endDateSetDataPopupErrorMessage=false;
	 }
	}else{
		$scope.endDateSetDataPopupErrorMessage=false;
	}
	
}

		
		
//===============

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
/*
	var clickOnUploadFile =   $timeout(function() {
						angular.element('#storemanagementExcelData').keypress(function (e) {
							 var key = e.which;
							 if(key == 13)  // the enter key code
							  {
							angular.element('#storemanagementExcelData').triggerHandler('click');
							  return false;  }
							})
					    },9000);
						$scope.$on("$destroy", function() {						
							$timeout.cancel(clickOnUploadFile);
						
					});
						
		 var clickOnUploadFileInSupplier =   $timeout(function() {
						angular.element('#supplierListExcelData').keypress(function (e) {
							 var key = e.which;
							 if(key == 13)  // the enter key code
							  {
							angular.element('#supplierListExcelData').triggerHandler('click');
							  return false;  }
							})
					    },9000);
						$scope.$on("$destroy", function() {						
							$timeout.cancel(clickOnUploadFileInSupplier);
						
					});	
						
						
	var clickOnUploadFileInAssignemnt =   $timeout(function() {
						angular.element('#assignemntExcelData').keypress(function (e) {
							 var key = e.which;
							 if(key == 13)  // the enter key code
							  {
							angular.element('#assignemntExcelData').triggerHandler('click');
							  return false;  }
							})
					    },9000);
						$scope.$on("$destroy", function() {						
							$timeout.cancel(clickOnUploadFileInAssignemnt);
						
					});	
						
						
					*/	
						
						/*$(".refreshTooltip").tooltip({delay: { show: 500, hide: 0 }});
						$timeout(function(){	
						$('.refreshTooltip').on('focus', function () {
							  $(this).tooltip('hide')
							  
							})
							},5000);*/
						
						//$(".pdfTooltip").tooltip({  });
						$(".excelTooltip").tooltip({  });
						$('[data-toggle="tooltip"]').tooltip(); 
						$scope.addTabIndex = function (){
							$timeout(function(){					 
								var storeListViewReacords_lengthTabindex = angular.element( document.querySelector( "[name='storeListViewReacords_length']" ) );
								storeListViewReacords_lengthTabindex.prop('tabindex','14');
								var paginate_buttonTabIndex = angular.element( document.querySelectorAll( ".paginate_button " ) );
								paginate_buttonTabIndex.prop('tabindex','17');
								var storeListViewReacords_filterTabIndex = angular.element( document.querySelectorAll( "#storeListViewReacords_filter input" ) );
								storeListViewReacords_filterTabIndex.prop('tabindex','-1');
								
								var listResources_lengthTabindex = angular.element( document.querySelector( "[name='listResources_length']" ) );
								listResources_lengthTabindex.prop('tabindex','15');
								var paginate_buttonTabIndex = angular.element( document.querySelectorAll( ".paginate_button " ) );
								paginate_buttonTabIndex.prop('tabindex','17');
								var storeListViewReacords_filterTabIndex = angular.element( document.querySelectorAll( "#listResources_filter input" ) );
								storeListViewReacords_filterTabIndex.prop('tabindex','-1');
								
								var listResourcesAssignment_lengthTabindex = angular.element( document.querySelector( "[name='listResourcesAssignment_length']" ) );
								listResourcesAssignment_lengthTabindex.prop('tabindex','15');
								var paginate_buttonTabIndex = angular.element( document.querySelectorAll( ".paginate_button " ) );
								paginate_buttonTabIndex.prop('tabindex','17');
								var listResourcesAssignment_filterTabIndex = angular.element( document.querySelectorAll( "#listResourcesAssignment_filter input" ) );
								listResourcesAssignment_filterTabIndex.prop('tabindex','-1');
								
							},5000);
						}
					$scope.configureTooltip=function(){						
						$(document).ready(function(){
						$('[data-toggle="tooltip"]').tooltip();   
					});
					$('[data-toggle="tooltip"]').on('shown.bs.tooltip', function () {
					  // do somethingâ€¦
					  $timeout(function(){
						$('[data-toggle="tooltip"]').tooltip('hide')
						},3000)
					})
					}
					$scope.generateAssignmentTooltip = function(){
						
						$(document).ready(function(){
						$('[data-toggle="tooltip"]').tooltip();   
					});
					$('[data-toggle="tooltip"]').on('shown.bs.tooltip', function () {
					  // do somethingâ€¦
					  $timeout(function(){
						$('[data-toggle="tooltip"]').tooltip('hide')
						},3000)
					})
					}
					
						$interval(function() {
							
	                       //var downloadExcelTooltip = angular.element( document.querySelector( "[title='Download Excel']" ) ).tooltip();												   
	                        //var downloadExcelTooltip = angular.element( document.querySelector( "[title='Download PDF']" ) ).tooltip();
							
							$(".sorting_asc" ).prop('tabindex','-1');							
							$(".sorting").prop('tabindex','-1');	
							$("#storeListViewReacords_filter input").prop('tabindex','-1');
							$("#listResources_filter input").prop('tabindex','-1');
							$("#listResourcesAssignment_filter input").prop('tabindex','-1');
							
	                },1000);
					
					$scope.startDateFun_tabindex = function(){
							$(".selectTabIndexValue1").prop('tabindex','0');
							$( "[name='storeListViewReacords_length']" ).prop('tabindex','0');
							$( ".paginate_button " ).prop('tabindex','1');
					}
					$scope.endDateFun_tabindex = function(){
							$(".selectedTabValue").prop('tabindex','8');
							$(".selectTabIndexValue2").prop('tabindex','0');
							$( "[name='storeListViewReacords_length']" ).prop('tabindex','0');
							$( ".paginate_button " ).prop('tabindex','0');
					}
					$scope.startDateFun_tabPopup = function(){							
							$( "[name='storeListViewReacords_length']" ).prop('tabindex','-1');
							$( "[name='listResources_length']" ).prop('tabindex','-1');
							$(".selectTabindex_popup").prop('tabindex','0');
					}
					$scope.configuredata_tabindexvalue = function(){
						$( "[name='storeListViewReacords_length']" ).prop('tabindex','-1');
					}
					$scope.StartupDateFun_tabindexValue =function(){
						$(".selectedtab-storeList").prop('tabindex','0');
						$( "[name='storeListViewReacords_length']" ).prop('tabindex','0');
						//$( ".paginate_button " ).prop('tabindex','1');
					}
					$scope.endDateFun_tabindexValue =function(){
						$(".selectedtab-storeList1-value").prop('tabindex','8');
						$(".selectedtab-storeList1").prop('tabindex','0');
						$( "[name='storeListViewReacords_length']" ).prop('tabindex','0');
						//$( ".paginate_button " ).prop('tabindex','1');
					}
					$scope.startDateFun_tabindexAssign =function(){
						$(".selectedtab-storeList2").prop('tabindex','0');
						$( "[name='storeListViewReacords_length']" ).prop('tabindex','0');
						//$( ".paginate_button " ).prop('tabindex','1');
					}
					$scope.endDateFun_tabindexAssign =function(){
						$(".selectedtab-storeList3-value").prop('tabindex','8');
						$(".selectedtab-storeList3").prop('tabindex','0');
						$( "[name='storeListViewReacords_length']" ).prop('tabindex','0');
						//$( ".paginate_button " ).prop('tabindex','1');
					}
					$scope.assignmentTab =function(){
						$("[name='listResourcesAssignment_length']").prop('tabindex','0');						
						$( ".paginate_button " ).prop('tabindex','0');
					}
					$scope.assignmentTabStoreList =function(){
						$("[name='storeListViewReacords_length']").prop('tabindex','0');
						$("[name='listResources_length']").prop('tabindex','0');
						$( ".paginate_button " ).prop('tabindex','0');
						$(".checkboxTabindex").prop('tabindex','0');
					}
					
					$scope.assignmentPopupstratdateTabindex=function(){
						$(".assignmentPopupTabindex1").prop('tabindex','0');
						$( "[name='listResourcesAssignment_length']" ).prop('tabindex','-1');
						$( ".paginate_button " ).prop('tabindex','-1');
					}
					$scope.assignmentPopupOpenTabindex=function(){
						$( "[name='listResourcesAssignment_length']" ).prop('tabindex','-1');
						$( ".paginate_button " ).prop('tabindex','-1');
						$( ".selectItemPopUp " ).prop('tabindex','1');
						$( "#selectItem" ).prop('tabindex','1');
						$( "#selectedNightDriverTab" ).prop('tabindex','1');						
						$("#selectItem").focus();
					}
					$scope.assignmentPopupSaveTabindex=function(){
						$( "[name='listResourcesAssignment_length']" ).prop('tabindex','0');
						$( ".paginate_button " ).prop('tabindex','0');
					}
					$scope.assignmentPopupEndDateTabindex=function(){
					$(".assignmentPopupTabindex-value").prop('tabindex','3');	
					$(".assignmentPopupTabindex2").prop('tabindex','0');
					$( "[name='listResourcesAssignment_length']" ).prop('tabindex','-1');					
					}
					$scope.endDateSetDataPopupFun_TabIndexPopUp=function(){
						$(".selectTabindex_popup-tabValue").prop('tabindex', '4');
						$(".endDateSetDataPopupFun_TabIndex").prop('tabindex','0');
						
					}
					
					 
					$(document).ready(function(){
						$('[data-toggle="tooltip"]').tooltip();   
					});
					$('[data-toggle="tooltip"]').on('shown.bs.tooltip', function () {
					  // do something
					  $timeout(function(){
						$('[data-toggle="tooltip"]').tooltip('hide')
						},3000)
					})
					$scope.executeExcel_store=function(){
						$('#storemanagementExcelData').keypress(function (e) {
							 var key = e.which;
							 if(key == 13)  // the enter key code
							  {
								$('#storemanagementExcelData').click();
								return false;  
							  }
							});
					}
					$scope.executeExcel_storeList=function(){
						$('#supplierListExcelData').keypress(function (e) {
							 var key = e.which;
							 if(key == 13)  // the enter key code
							  {
								$('#supplierListExcelData').click();
								return false;  
							  }
							});
					}
					$scope.executeExcel_assign=function(){
						$('#assignemntExcelData').keypress(function (e) {
							 var key = e.which;
							 if(key == 13)  // the enter key code
							  {
								$('#assignemntExcelData').click();
								return false;  
							  }
							});
					}
					
					$scope.updateMaterialProfile = function(){
						//$('#ui-select-choices-row-4-0 .execute_updateMaterial').click();
						//$('#ui-select-choices-row-4-1 .execute_updateMaterial').click();
						$(".tabindexmaterial").prop('tabindex','0');
		}
		$scope.removeInputValueStoreSupplier = function(){					
					$scope.dtOptionsSSV=DTOptionsBuilder.newOptions()
					.withOption('bFilter', true);
					$scope.invalidSSLToDateError=undefined;
		}
		$scope.removeInputValueStoreList = function(){					
					$scope.dtOptionsSLV=DTOptionsBuilder.newOptions()
					.withOption('bFilter', true);
					$scope.invalidSLVToDateError=undefined;
		}
		$scope.removeInputValueStoreAssignment = function(){					
					$scope.dtOptionsAV=DTOptionsBuilder.newOptions()
					.withOption('bFilter', true);
					$scope.invalidAssignmentEndDateError=undefined;
		}
			
		$scope.assignmentSEndFutureDate = function(){
				var assignmentEndDateValueFromId= $("#assignmentDateEnd_assign").val();	
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
								
								if(diff>10){
									$scope.invalidAssignmentEndDateError="Select upto 10 years";
									$scope.assignmentDateEnd=null;
								}
							}
							var assignmentEndDateValueFromId= $("#assignmentDateEnd_assign").val();	

							dateold = new Date(firstDay);
							datenew = new Date(assignmentEndDateValueFromId);
							dateDiff(dateold, datenew);					
			}
				


		$scope.assignmentStartFutureDate = function(){
				var assignmentStartDateValueFromId= $("#assignmentDateStart_assign").val();	
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
								
								if(diff>10){
									$scope.invalidAssignmentStartDateError="Select upto 10 years";
									$scope.assignmentDateStart=firstDay;
								}
							}
							var assignmentStartDateValueFromId= $("#assignmentDateStart_assign").val();

							dateold = new Date(firstDay);
							datenew = new Date(assignmentStartDateValueFromId);
							dateDiff(dateold, datenew);					
			}
				
		
		$scope.storeListStartFutureDate = function(){
				var storeListViewEndDateValueFromId= $("#startDateval_store").val();	
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
								
								if(diff>10){
									$scope.invalidSLVFromDateError="Select upto 10 years ";
									$scope.startDate=firstDay;
								}
							}
							var storeListViewEndDateValueFromId= $("#startDateval_store").val();	

							dateold = new Date(firstDay);
							datenew = new Date(storeListViewEndDateValueFromId);
							dateDiff(dateold, datenew);					
			}
				
		$scope.storeListSToFutureDate = function(){
				var storeListViewStartDateValueFromId= $("#endDateval_store").val();
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
								
								if(diff>10){
									$scope.invalidSLVToDateError="Select upto 10 years ";
									$scope.endDate=lastDay;
								}
							}
							var storeListViewStartDateValueFromId= $("#endDateval_store").val();

							dateold = new Date(firstDay);
							datenew = new Date(storeListViewStartDateValueFromId);
							dateDiff(dateold, datenew);					
			}
				
		
		
	
	$scope.storeSupplierStartFutureDate = function(){
				var storeSupplierListViewStartDateValueFromId= $("#startDateSSL_storelist").val();	
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
								
								if(diff>10){
									$scope.invalidSSLFromDateError="Select upto 10 years ";
									$scope.startDateSSL=firstDay;
								}
							}
							var storeSupplierListViewStartDateValueFromId= $("#startDateSSL_storelist").val();	

							dateold = new Date(firstDay);
							datenew = new Date(storeSupplierListViewStartDateValueFromId);
							dateDiff(dateold, datenew);					
			}
				
		
		$scope.storeSupplierToFutureDate = function(){
				var storeSupplierListViewEndDateValueFromId= $("#endDateSSL_storelist").val();	
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
								
								if(diff>10){
									$scope.invalidSSLToDateError="Select upto 10 years ";
									$scope.endDateSSL=null;
								}
							}
							var storeSupplierListViewEndDateValueFromId= $("#endDateSSL_storelist").val();		

							dateold = new Date(firstDay);
							datenew = new Date(storeSupplierListViewEndDateValueFromId);
							dateDiff(dateold, datenew);					
			}
				
		
		$scope.setDataPopUpFromFutureDate = function(){
		var setDataPopupStartDateValueFromId= $("#balePickupStartDate_setDatapopup").val();		
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
								
								if(diff>10){
									$scope.invalidSetDataStartDateError="Select upto 10 years ";
									$scope.setDatapopup.balePickupStartDate=new Date();
								}
							}
							var setDataPopupStartDateValueFromId= $("#balePickupStartDate_setDatapopup").val();			

							dateold = new Date(firstDay);
							datenew = new Date(setDataPopupStartDateValueFromId);
							dateDiff(dateold, datenew);					
			}
				
		
		$scope.setDataPopUpToFutureDate = function(){
		var setDataPopupEndDateValueFromId= $("#balePickupEndDate_setDatapopup").val();		
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
								
								if(diff>10){
									$scope.invalidSetDataEndDateError="Select upto 10 years ";
									$scope.setDatapopup.balePickupEndDate=null;
								}
							}
							var setDataPopupEndDateValueFromId= $("#balePickupEndDate_setDatapopup").val();			

							dateold = new Date(firstDay);
							datenew = new Date(setDataPopupEndDateValueFromId);
							dateDiff(dateold, datenew);					
			}
			
		//by Deepak for mapping between Supp BC and SC
 $scope.getFilteredValuesSSL=function(customerSSL,supplierSSL){
	
	  
	    if(customerSSL !=undefined && customerSSL!="" && customerSSL!=null){
		   customerSSL=JSON.parse(customerSSL);
	   }else{
			customerSSL=undefined;
	   }
	   
	   if(supplierSSL !=undefined && supplierSSL!="" && supplierSSL!=null){
		   supplierSSL=JSON.parse(supplierSSL);
	   }else{
			supplierSSL=undefined;
	   }
	
	   
	   if((customerSSL==undefined || customerSSL=="" || customerSSL==null) && (supplierSSL ==undefined || supplierSSL=="" || supplierSSL==null)){
		   console.log("0 0");
				customerService.getAllBuyCustomers().then(function(response) {
				$scope.buyCustomers = response;
									
			});
			
			supplierService.getAllSuppliers().then(function(response) {
				$scope.suppliersForCustomerSelected=response;
				
			});	
	   }

		if((customerSSL!=undefined && customerSSL!="" && customerSSL!=null) && (supplierSSL ==undefined || supplierSSL=="" || supplierSSL==null)){
			 console.log("1 0");
			 customerService.getSuppliersFromBuyCustomerForDAR(customerSSL.customerId).then(function(response) {
				 $scope.suppliersForCustomerSelected = response;		
				 });
				 customerService.getAllBuyCustomers().then(function(response) {
				$scope.buyCustomers = response;
									
			});
		}
		
		if((customerSSL==undefined || customerSSL=="" || customerSSL==null) && (supplierSSL!=undefined && supplierSSL!="" && supplierSSL!=null)){
			 console.log("0 1");
			 
			
			 	customerService.getBuyCustomersFromSupplierDAR(supplierSSL.supplierId).then(function(response) {
						$scope.buyCustomers=response;	
					});
					
				supplierService.getAllSuppliers().then(function(response) {
				$scope.suppliersForCustomerSelected=response;
				
			});		
		}
		
		if(customerSSL !=undefined && customerSSL!="" && customerSSL!=null && supplierSSL !=undefined && supplierSSL!="" && supplierSSL!=null){
			 console.log("1 1")
			customerService.getBuyCustomersFromSupplierDAR(supplierSSL.supplierId).then(function(response) {
						$scope.buyCustomers=response;	
					});
			 customerService.getSuppliersFromBuyCustomerForDAR(customerSSL.customerId).then(function(response) {
				 $scope.suppliersForCustomerSelected = response;		
				 });
		}
		   
		   
		}
   		
				
		//by Deepak for mapping between Supp BC and SC
		
		
});
				

brtaApp.controller("incidentTypeManagementController",function(applicationContextURL,$scope,customerService,$rootScope,$http,$interval,$location,$timeout,
$filter,incidentTypeService, DTOptionsBuilder, DTColumnBuilder,Idle, Keepalive,$compile, $uibModal){
	
	
	
	 var role=window.role;
	 var supplierId=window.supplierId;
	 	 $scope.isSupplierRole=false;
		 $scope.isEdit=false;
		 $scope.authorizedIncMgmt =false;
		 $scope.incStatus=["Active","Inactive"];
		 $scope.enableIncident=true;
		$scope.disableIncident=false;
		
	 	 if($rootScope.user ==undefined){
	 		$rootScope.user={};
	 	
	 	 }
	 	$rootScope.user.role = window.role;	
	 	
	if($scope.buyCustomers==undefined){
		customerService.getAllBuyCustomers().then(function(response) {
								$scope.buyCustomers = response;
								$rootScope.buyCustomers=response;
			});
	}
	
		 
	$rootScope.supplierId=supplierId;
	
	$scope.redirect = function(){
	     if($rootScope.user.role =='Supplier'){
	      window.location = "#/storesupplierForSupplier";
		  $(".store_menu a").attr("class", "active");
		  $(".incident_menu a").attr("class", "");
	      }else{
	        window.location = "#/storemanagement";
			$(".store_menu a").attr("class", "active");
			$(".incident_menu a").attr("class", "");
	      }
	  }
	  $(".incident_menu a").attr("class", "");
	  
	  var currentUrl=$location.url();
					if(currentUrl.indexOf("incidentmanagement")!=-1 || currentUrl.indexOf("incidentmanagementForSupplier")!=-1){
						$(".pickup_menu a").attr("class", "");
						$(".user_menu a").attr("class", "");
						$(".incident_menu a").attr("class", "active");
						$(".report_menu a").attr("class", "");
						$(".store_menu a").attr("class", "");
						$(".needHelp_menu a").attr("class", "");
					}

	
	if($rootScope.user.role =='Supplier'){
		$scope.isSupplierRole=true;
		$scope.enableIncident=false;
		$scope.disableIncident=true;
		$interval( function(){
		var addNewInccidentCSSProperty =angular.element( document.querySelector( "#IncidentRecords_wrapper .dt-buttons" )).css("top", "-72px");
		},1000);
		
		$timeout( function(){
						$('#IncidentRecords_filter ').css("margin-top", "15.2%;");},1000)
		
			
	}
					
	
	$scope.loaderIncidentMgmtView=true;
	 $scope.showaddincidenttype = function(){
		   $scope.addIncidentType = true;
		   $scope.incidentType = {};
		   $scope.incidentType.enabled="Active";
		   $scope.error = false;

			
	   }
	
	
	
	$scope.resetFilter= function (){
		$scope.status=undefined;
		$scope.authorizedIncMgmt=false;
		$scope.incidentTypes=[];
		$scope.jsonForIncidentExport=[];
		$scope.exportIncidentData = [];
	}
	
	
	$scope.status="Active"
	 incidentTypeService.getAllincidentTypeFromStatus($scope.status).then(function(response){
							$scope.incidentfileName = "IncidentManagement";
							$scope.exportIncidentData = [];
							$scope.exportIncidentData.push(["Incident", "Status"]);
		
			 $scope.incidentTypes = response;
			
		 for (var i = 0, length = $scope.incidentTypes.length; i < length; i++) {
			 $scope.incidentTypes[i].enabled =$scope.incidentTypes[i].enabled ? "Active" :"Inactive";
			     
			    }
		 
			 $scope.jsonForIncidentExport=[];
					for(var i=0;i<$scope.incidentTypes.length;i++){
						
						$scope.jsonForIncidentExport.push({
							
							"Incident":$scope.incidentTypes[i].incidentDescription,
							 "Status":$scope.incidentTypes[i].enabled
						
					
								});
					$scope.exportIncidentData.push([$scope.incidentTypes[i].incidentDescription, $scope.incidentTypes[i].enabled]);			
								
					}
						$scope.authorizedIncMgmt=true;						
							
							 $scope.dtColumnsIncMgmt = [
										   DTColumnBuilder.newColumn('incidentDescription').withTitle('Incident'),
										   DTColumnBuilder.newColumn('enabled').withTitle('Status'),
										  
										DTColumnBuilder.newColumn('').withTitle('Edit').notSortable().renderWith(function(data, type, full, meta) {
									var htmlTemp='<div></div>';
												
										if(!$scope.isSupplierRole){		
										htmlTemp='<div style="width: 20px; padding-top: 0px !important; padding-bottom: 0px !important">'+
												'<img src="/brta/app/ui/resources/images/editdata.png"'+ 
									'ng-click="addEditIncident('+full.incidentTypeId+');removeTabIndex()"'+
									'class="editImageAlignment" style="height: 40px;margin-top:-10px;margin-bottom:-10px"></div>';
										}else{
											htmlTemp='<div style="width: 20px; padding-top: 0px !important; padding-bottom: 0px !important">'+
										'<img src="/brta/app/ui/resources/images/disabled-edit.png" ng-disabled="isSupplierRole"'+
									
									'class="editImageAlignment save-changes" style="height: 40px; margin-top:-10px ; margin-bottom:-10px;!important">';
									}
									return htmlTemp;	
										}) 
										   
										     ];
				
						$scope.dtOptionsIncMgmt= DTOptionsBuilder.newOptions().withOption('data', $scope.incidentTypes).withOption('createdRow', function (row, data, dataIndex) {
								
									$compile(angular.element(row).contents())($scope);
								}).withOption('headerCallback', function( thead, data, start, end, display ) {
									$compile(angular.element(thead).contents())($scope);
								});
						 
					$scope.loaderIncidentMgmtView=false;
		 
	 });
	 
	 

	 $scope.getAllincidentTypeFromStatus=function()
	 {
		var incStatus={};
		incStatus = $scope.status ;
		$scope.loaderIncidentMgmtView=true;
		$scope.incidentfileName = "IncidentManagement";
		$scope.exportIncidentData = [];
		$scope.exportIncidentData.push(["Incident", "Status"]);
	if($scope.status == undefined || $scope.status  == null || $scope.status  == ''){
		
		incStatus="All";

	}
	incidentTypeService.getAllincidentTypeFromStatus(incStatus).then(function(response){
			 $scope.incidentTypes = response;
			
		 for (var i = 0, length = $scope.incidentTypes.length; i < length; i++) {
			 $scope.incidentTypes[i].enabled =$scope.incidentTypes[i].enabled ? "Active" :"Inactive";
			     
			    }
		 
			 $scope.jsonForIncidentExport=[];
					for(var i=0;i<$scope.incidentTypes.length;i++){
						
						$scope.jsonForIncidentExport.push({
							
							"Incident":$scope.incidentTypes[i].incidentDescription,
							 "Status":$scope.incidentTypes[i].enabled
						
					
								});
				$scope.exportIncidentData.push([$scope.incidentTypes[i].incidentDescription, $scope.incidentTypes[i].enabled]);					
								
					}
					
					$scope.authorizedIncMgmt=true;						
							
							 $scope.dtColumnsIncMgmt = [
										   DTColumnBuilder.newColumn('incidentDescription').withTitle('Incident'),
										   DTColumnBuilder.newColumn('enabled').withTitle('Status'),
										  
										DTColumnBuilder.newColumn('').withTitle('Edit').notSortable().renderWith(function(data, type, full, meta) {
									var htmlTemp='<div></div>';
												
										if(!$scope.isSupplierRole){		
										htmlTemp='<div style="width: 20px; padding-top: 0px !important; padding-bottom: 0px !important">'+
												'<img src="/brta/app/ui/resources/images/editdata.png"'+ 
									'ng-click="addEditIncident('+full.incidentTypeId+');removeTabIndex()"'+
									'class="editImageAlignment" style="height: 40px;margin-top:-10px;margin-bottom:-10px"></div>';
										}else{
											htmlTemp='<div style="width: 20px; padding-top: 0px !important; padding-bottom: 0px !important">'+
										'<img src="/brta/app/ui/resources/images/disabled-edit.png" ng-disabled="isSupplierRole"'+
									
									'class="editImageAlignment save-changes" style="height: 40px; margin-top:-10px ; margin-bottom:-10px;!important">';
									}
									return htmlTemp;	
										}) 
										   
										     ];
				
						$scope.dtOptionsIncMgmt= DTOptionsBuilder.newOptions().withOption('data', $scope.incidentTypes).withOption('createdRow', function (row, data, dataIndex) {
								
									$compile(angular.element(row).contents())($scope);
								}).withOption('headerCallback', function( thead, data, start, end, display ) {
									$compile(angular.element(thead).contents())($scope);
								});
						 
					$scope.loaderIncidentMgmtView=false;
	 });
	 }
	 
	 
	 
	 $scope.addEditIncident=function(incidentTypeId){
		
						var incidentData = $filter('filter')($scope.incidentTypes, function (d) {return d.incidentTypeId === incidentTypeId;})[0];					
						$scope.editIncident(incidentData);
					}
	 
	 var headerTableIncidentReport = [		
                     { title: "INCIDENT", dataKey: "Incident" },		
                     { title: "STATUS", dataKey: "Status" }
                ];
	
	$scope.incidentManagementPdf = function() {		
var data = [];		

if($scope.jsonForIncidentExport !=undefined && $scope.jsonForIncidentExport.length >0){
	data=$scope.jsonForIncidentExport;		
	var doc = new jsPDF('p', 'pt');
	 doc.setFontSize(13);
	doc.autoTable(headerTableIncidentReport, data, {	
	
 
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
	doc.save('IncidentManagment.pdf');	
}
	
}
	
	 
	 
	 $scope.IncidenetOptionRecords =  DTOptionsBuilder.newOptions()
										  .withOption('order', [])
											.withOption('scrollX', false)
											 .withOption('bScrollCollapse', false)
											 .withOption('bAutoWidth', false)
											//.withOption('tabIndex', '-1')				 
											.withOption('bFilter', false)
											.withOption('info', false)
											.withOption('lengthChange', false)
											.withOption('bPaginate', false)
											.withOption('ordering', false);
											//.withDOM('Blfrtip');
		 $interval( function(){
						$("#IncidentRecordsEmpty_wrapper tbody").css("display", "none");
					})

					
						if(window.innerWidth >= 992) {
						$scope.resDtColumnIncidentList = [DTColumnBuilder.newColumn(0),
									DTColumnBuilder.newColumn(1),																		
									DTColumnBuilder.newColumn(2).notSortable()];
						}
	 
	
	//==========================by deepak modify Incident management===========================
	
		 $scope.addIncident = function(){
			 $scope.incMgmt={};
			 $scope.isEdit=false;
			 $scope.addEditIncidentPopup=true;
	 }
	 
		$scope.incMgmtPopupClose = function(){
			$scope.addEditIncidentPopup=false;	
	}
	
	
		$scope.addEditIncDetails  = function(incMgmt){
			$scope.checkincStatus={};
			$scope.incMgmt.error={};
			$scope.validationError=false;
			$scope.check={};
			
		if($scope.incMgmt.incidentDescription == undefined ||$scope.incMgmt.incidentDescription == "" || $scope.incMgmt.incidentDescription== null )
           {
			$scope.incMgmt.error.incidentNameError= true;
			$scope.validationError=true;
			$scope.loaderIncidentMgmtView=false;
		   }else{
			   
		   }
		 if($scope.incMgmt.incStatus == undefined ||$scope.incMgmt.incStatus == "" || $scope.incMgmt.incStatus== null )
           {
			$scope.incMgmt.error.incStatusError= true;
			$scope.validationError=true;
			$scope.loaderIncidentMgmtView=false;
		   }else{
			   
		   }
		if($scope.incMgmt.incStatus == 'Active'){
				$scope.incMgmt.enabled = true;
			}
				else{
					$scope.incMgmt.enabled = false;
			}
		
		if(!$scope.validationError){
			
			incidentTypeService.checkIncidentbyNameStatus(incMgmt).then(function(response){
				$scope.checkincStatus = response.status;
			
			
			if(response.status == true){
			  $scope.incMgmt.error.incDuplicateError= true;
			  $scope.validationError=true;
			}else{
				
					if($scope.isEdit==false){
						
						incidentTypeService.addIncidentType(incMgmt).then(function(response){
						$scope.getAllincidentTypeFromStatus($scope.status);
						$scope.incMgmtPopupClose();
					});
				}else{
					//console.log("Edit");
						incidentTypeService.editIncidentType(incMgmt).then(function(response){
						$scope.getAllincidentTypeFromStatus($scope.status);
						$scope.incMgmtPopupClose();
					});				
				}
			}
				});
			}else{
				
			}
		}
		
		$scope.editIncident=function(incidentData){
		
			$scope.incMgmt={};
			$scope.isEdit=true;
			$scope.incMgmt.incidentDescription = incidentData.incidentDescription;
			$scope.incMgmt.incStatus =incidentData.enabled;
			$scope.incMgmt.createdDate = incidentData.createdDate;
			$scope.incMgmt.incidentTypeId = incidentData.incidentTypeId
			$scope.addEditIncidentPopup=true;
			
		}
	
//==========================timeout===========================

      function closeModals() {
	//	  console.log("===closeModals==");
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
		  
//		  console.log("===IdleStart==");
        closeModals();

        $scope.warning = $uibModal.open({
          templateUrl: 'warning-dialog.html',
          windowClass: 'modal-danger',
		  backdrop: 'static'
        });
      });

      $scope.$on('IdleEnd', function() {
//		  console.log("===IdleEnd==");
        closeModals();
      });

      $scope.$on('IdleTimeout', function() {
	//	  console.log("===IdleTimeout==");
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
//		  console.log("===start==");
        closeModals();
        Idle.watch();
        $scope.started = true;
      };

      $scope.stop = function() {
	//	  console.log("===stop==");
        closeModals();
        Idle.unwatch();
        $scope.started = false;

      };

//===============
	
	$(document).ready(function(){
						$('[data-toggle="tooltip"]').tooltip();   
					});
					$('[data-toggle="tooltip"]').on('shown.bs.tooltip', function () {
					  $timeout(function(){
						$('[data-toggle="tooltip"]').tooltip('hide')
						},3000)
					});
		$scope.executeExcel_incident=function(){
						$('#incidentExcelData').keypress(function (e) {
							 var key = e.which;
							 if(key == 13)  // the enter key code
							  {
								$('#incidentExcelData').click();
								return false;  
							  }
							});
					} 			
					
	$interval( function(){
						$('#IncidentRecords_filter input').prop('title','Search By Incident');
						
						$('[data-toggle="tooltip"]').tooltip(); 
						$('[data-toggle="tooltip"]').on('shown.bs.tooltip', function () {					 
							  $timeout(function(){
								$('[data-toggle="tooltip"]').tooltip('hide')
								},3000)
							})
						},1000);
	
	//$interval(function() {

        //var downloadExcelTooltip = angular.element( document.querySelector( "[title='Download Excel']" ) ).tooltip(); 
        //var downloadExcelTooltip = angular.element( document.querySelector( "[title='Download PDF']" ) ).tooltip();                                          

//},1000);
/*
//commented by prema
$timeout(function(){	
		var IncidentRecords_lengthTabindex = angular.element( document.querySelector( "[name='IncidentRecords_length']" ) );
									IncidentRecords_lengthTabindex.prop('tabindex','10');
		var paginate_buttonTabIndex = angular.element( document.querySelectorAll( ".paginate_button " ) );
								paginate_buttonTabIndex.prop('tabindex','11');
		},1000);
$scope.addTabIndex = function (){
	$timeout(function(){	
		var IncidentRecords_lengthTabindex = angular.element( document.querySelector( "[name='IncidentRecords_length']" ) );
									IncidentRecords_lengthTabindex.prop('tabindex','10');
		var paginate_buttonTabIndex = angular.element( document.querySelectorAll( ".paginate_button " ) );
								paginate_buttonTabIndex.prop('tabindex','18');
		},1000);
	}
	//comment ended by prema
	*/
$scope.removeInputIncidentValue = function(){					
	$scope.dtOptionsIncMgmt=DTOptionsBuilder.newOptions()
	.withOption('bFilter', true);
}
//adding by prema
$scope.removeTabIndex = function() {
			  
				var assignedTabIndexValue = angular.element(document.querySelectorAll("[tabindex]"));
							 assignedTabIndexValue.attr('tabindex',"-1");
											
				var navigation_menu = angular.element(document.querySelectorAll(".a2"));
											navigation_menu.attr('tabindex',"-1");
				var incident_lengthTabindex = angular.element( document.querySelector( "[name='IncidentRecords_length']" ) );
									incident_lengthTabindex.prop('tabindex','-1');
				$("#IncidentRecords_filter input").prop('tabindex','-1');					
			}
			
		$scope.assignTabIndexclose = function(){
			var navigation_menu = angular.element(document.querySelectorAll(".a2"));
									navigation_menu.attr('tabindex',"1");
			var incident_lengthTabindex = angular.element( document.querySelector( "[name='IncidentRecords_length']" ) );
									incident_lengthTabindex.prop('tabindex','11');
									
			var paginate_buttonTabIndex = angular.element( document.querySelectorAll( ".paginate_button " ) );
									paginate_buttonTabIndex.prop('tabindex','12');
									
			 var assignedtabsIndexValue = angular.element(document.querySelectorAll(".incidentTabsIndex"));
									assignedtabsIndexValue.attr('tabindex',"5");
			$interval( function(){
			$("#incidentExcelData").removeAttr("tabindex");
			$("#incidentExcelData").attr("tabindex", "5");
			$("#emptyExcelIcon").removeAttr("tabindex");
			$("#emptyExcelIcon").attr("tabindex", "5");
			},2000);								
		}

//ended by prema



});
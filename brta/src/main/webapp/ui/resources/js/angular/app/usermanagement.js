brtaApp.controller("userManagementController",function(applicationContextURL,$scope,customerService,$rootScope,$filter,$timeout,$interval,$location, $window,$http,userService,supplierService,DTOptionsBuilder, DTColumnBuilder,Idle,$compile, Keepalive, $uibModal){

	
	$scope.addUserRow = false;
	$rootScope.user.role = window.role;	
	 $scope.roleType=["Driver","Supplier"];
	 var role=window.role;
	 var supplierId=window.supplierId;
	 $scope.authorizedUserMgmt = false;
	 $scope.isEdit=false;
	 $rootScope.supplierId=supplierId;
	$scope.isSupplierRole=false;
	 if($rootScope.user ==undefined){
		 $rootScope.user={};
 	
 	 }
	 
	if($scope.buyCustomers==undefined){
		customerService.getAllBuyCustomers().then(
							function(response) {
								$scope.buyCustomers = response;
								$rootScope.buyCustomers=response;
							});
	}
				if($rootScope.user.role =='Supplier'){
					
						$scope.isSupplierRole=true;
						$scope.loaderUserManagement=true;	
						$scope.storeManagementList = false;
						$scope.assignActivityTab=true;
					}					
					
					
					$scope.redirect = function(){
					     if($rootScope.user.role =='Supplier'){
					      window.location = "#/storesupplierForSupplier";
						  $(".store_menu a").attr("class", "active");
							$(".user_menu a").attr("class", "");
					      }else{
					        window.location = "#/storemanagement";
							$(".store_menu a").attr("class", "active");
							$(".user_menu a").attr("class", "");
					      }
					  }
					$(".user_menu a").attr("class", "");
	
	
					var currentUrl=$location.url();	
					
					if(currentUrl.indexOf("users")!=-1 || currentUrl.indexOf("usersForSupplier")!=-1){
						$(".pickup_menu a").attr("class", "");
						$(".user_menu a").attr("class", "active");
						$(".incident_menu a").attr("class", "");
						$(".report_menu a").attr("class", "");
						$(".store_menu a").attr("class", "");
						$(".needHelp_menu a").attr("class", "");						
					}
	  
	
  
	supplierService.getAllSuppliers().then(function(response){
	   $scope.suppliers = response;
	  $scope.supplierForpopup ={};
	  if($rootScope.user.role =='Supplier')
		   {
			   for (var i = 0; i < $scope.suppliers.length; i++) {
			 if($scope.suppliers[i].supplierId==supplierId){
				$scope.supplierFilter = $scope.suppliers[i].supplierId;
				$scope.supplierForpopup = $scope.suppliers[i];
				
			 	}
		 	}
			
		$scope.roleType=[];
		$scope.roleType.push("Driver");
	
		   }
		    $scope.loaderUserManagement=false;
		   
		 
	});
	
	// FOr reseting Filter data - by naren
	$scope.resetFilteredData=function (){
		
		$scope.user={};
		if($rootScope.user.role =='Supplier'){
			$scope.statusFilter=undefined;
			$scope.users=[];	
			 $scope.authorizedUserMgmt = false;
			$scope.jsonForUserManagementExport=[];
			$scope.exportUserData = [];
		}else{
			 $scope.authorizedUserMgmt = false;
			$scope.supplierFilter=undefined;
			$scope.statusFilter=undefined;
			$scope.users=[];	
			$scope.jsonForUserManagementExport=[];
			$scope.exportUserData = [];
		}
	}
	
	
   // by deepak
   

   $scope.dtOptionsemptyBalePickup = DTOptionsBuilder.newOptions()
										  .withOption('order', [])
											.withOption('scrollX', false)

											 .withOption('bScrollCollapse', true)
											 .withOption('bAutoWidth', true)
											//.withOption('tabIndex', '-1')				 
											.withOption('bFilter', false)
											.withOption('info', false)
											.withOption('lengthChange', false)
											.withOption('bPaginate', false)
											.withOption('ordering', false);
											//.withDOM('Blfrtip');
 

   $interval( function(){
						$("#userManagementemptyTable_wrapper tbody").css("display", "none");
					})

  
	
	$scope.addUser = false;
	$scope.editingData = {};
	

	$scope.getFilteredData = function(){
	
		
	
		$scope.loaderUserManagement=true;
		var user = {};
		user.supplierFilter = $scope.supplierFilter;
		user.statusFilter = $scope.statusFilter;
		var supplier = {};
		supplier.supplierId = user.supplierId;
		user.supplier = supplier;
		
		
	userService.getFilteredUsers(user).then(function(response){
			$scope.users = response;
			
	//	console.log("$scope.users"+JSON.stringify($scope.users));
			$scope.loaderUserManagement= false;
			    
				for (var i = 0, length = $scope.users.length; i < length; i++) {
					if($scope.users[i].roleId ==1 ){
						$scope.users[i].roleName='Driver';
					}else{
						$scope.users[i].roleName='Supplier';
					}
				  $scope.editingData[$scope.users[i].id] = false;
				}
					$scope.loaderActiveUserMgmt=false;
					
					
				//	console.log($scope.users);
					

				$scope.userfileName = "UserManagement";
				$scope.exportUserData = [];
				$scope.exportUserData.push(["Last Name", "First Name", "EmailAddress","Supplier Name", "Role", "Mobile", "Status"]);

				
				$scope.jsonForUserManagementExport=[];
					for(var i=0;i<$scope.users.length;i++){
						var userRole = $scope.users[i].roleId==2 ? 'Supplier':'Driver';
						var userStatus = $scope.users[i].enabled ?"Active" :"Inactive";
						$scope.users[i].userRole = userRole;
						$scope.users[i].userStatus = userStatus;
						$scope.jsonForUserManagementExport.push({
							"Last Name":$scope.users[i].lastName,
							"First Name":$scope.users[i].firstName,
							"EmailAddress":$scope.users[i].emailId,
							"Supplier Name":$scope.users[i].supplier.description,
							"Role": $scope.users[i].userRole,
							"Mobile":$scope.users[i].mobilePhone,
							"Status":$scope.users[i].userStatus
							
							

							})						

						$scope.exportUserData.push([$scope.users[i].lastName, $scope.users[i].firstName, $scope.users[i].emailId,$scope.users[i].supplier.description, $scope.users[i].userRole, $scope.users[i].mobilePhone, $scope.users[i].userStatus]);
					}

						$scope.authorizedUserMgmt=true;						
							
							 $scope.dtColumnsUsermgmt = [
										   DTColumnBuilder.newColumn('lastName').withTitle('Last Name'),
										   DTColumnBuilder.newColumn('firstName').withTitle('First Name'),
										   DTColumnBuilder.newColumn('emailId').withTitle('Email Address'),
										   DTColumnBuilder.newColumn('supplier.description').withTitle('Supplier Name'),
										   DTColumnBuilder.newColumn('userRole').withTitle('Role'),
										   DTColumnBuilder.newColumn('mobilePhone').withTitle('Mobile'),
										   DTColumnBuilder.newColumn('userStatus').withTitle('Status'),
										DTColumnBuilder.newColumn('').withTitle('Edit').notSortable().renderWith(function(data, type, full, meta) {
									var htmlTemp='<div></div>';
												
											if($scope.isSupplierRole){
											
												if(full.userRole=='Driver'){
											
										htmlTemp='<div style="width: 20px; padding-top: 0px !important; padding-bottom: 0px !important">'+
												'<img src="/brta/app/ui/resources/images/editdata.png"'+ 
									'ng-click="addEditDriver('+full.userId+');addTabIndexToPopup();removeTabIndex()"'+
									'class="editImageAlignment" style="height: 40px;margin-top:-10px;margin-bottom:-10px"></div>';
												}else{
												
									htmlTemp='<div style="width: 20px; padding-top: 0px !important; padding-bottom: 0px !important">'+
										'<img src="/brta/app/ui/resources/images/disabled-edit.png" ng-disabled="isSupplierRole"'+
									
									'class="editImageAlignment save-changes" style="height: 40px; margin-top:-10px ; margin-bottom:-10px;!important">';
												}
											}else{
										htmlTemp='<div style="width: 20px; padding-top: 0px !important; padding-bottom: 0px !important">'+
												'<img src="/brta/app/ui/resources/images/editdata.png"'+ 
									'ng-click="addEditDriver('+full.userId+');addTabIndexToPopup();removeTabIndex()"'+
									'class="editImageAlignment" style="height: 40px;margin-top:-10px;margin-bottom:-10px"></div>';
									}		
									
									return htmlTemp;	
										}) 
										   
										     ];
				
						$scope.dtOptions= DTOptionsBuilder.newOptions().withOption('data', $scope.users).withOption('createdRow', function (row, data, dataIndex) {
								
									$compile(angular.element(row).contents())($scope);
								}).withOption('headerCallback', function( thead, data, start, end, display ) {
									$compile(angular.element(thead).contents())($scope);
								});
													
										 
		});
		
	}
	
	$scope.addEditDriver=function(userID){
		
						var driverSupplierUser = $filter('filter')($scope.users, function (d) {return d.userId === userID;})[0];
						$scope.editDriverSuppilerDetails(driverSupplierUser);
					}
   
  
   $scope.editDriverSuppilerDetails = function(driverSupplierUser){
	   //console.log("driverSupplierUser  "+JSON.stringify(driverSupplierUser));
		$scope.userMgmtData = {};
		$scope.addEditUserPopup=true;
		$scope.isEdit=true;
		$scope.loaderUserManagement=true;
		$scope.userMgmtData.userLastName = driverSupplierUser.lastName;
		$scope.userMgmtData.userFirstName = driverSupplierUser.firstName;
		$scope.userMgmtData.userEmailAddress = driverSupplierUser.emailId;
		$scope.userMgmtData.userMobileNumber = driverSupplierUser.mobilePhone;
		$scope.userMgmtData.activeInactive=driverSupplierUser.userStatus;
		$scope.userMgmtData.driverSupplierRole =driverSupplierUser.roleName;
		$scope.userMgmtData.userId=driverSupplierUser.userId;
		$scope.userMgmtData.createDate=driverSupplierUser.createDate;
		$scope.userMgmtData.updatedBy=driverSupplierUser.updatedBy;
		$scope.userMgmtData.updatedDate=driverSupplierUser.updatedDate;
		$scope.userMgmtData.external=driverSupplierUser.external;
		$scope.userMgmtData.supplierFilter=driverSupplierUser.supplier.supplierId;
		$scope.supplierscopy=$scope.suppliers;
		
		
		if($rootScope.user.role =='Supplier')
		   {
			$scope.suppliers=[];
			$scope.suppliers.push($scope.supplierForpopup);
			$scope.userMgmtData.supplierName = $scope.suppliers[0];
			$scope.roleType=[];
			$scope.roleType.push("Driver");
			$scope.userMgmtData.driverSupplierRole = $scope.user.roleSelected="Driver";
	
		   }else{
			  
			for(var q=0;q<$scope.supplierscopy.length;q++){
				
				if($scope.supplierscopy[q].supplierId==driverSupplierUser.supplier.supplierId){
					$scope.userMgmtData.supplierName = driverSupplierUser.supplier;
					
				}
				
			}
		   } 
		
		$scope.loaderUserManagement=false;
	   
   }
   
   
	$scope.addUser = false;
	$scope.addEditUserDetails  = function(userMgmtData){
		$scope.userMgmtData.error={};
		$scope.validationError=false;
		$scope.loaderUserManagement=true;
		$scope.validationUserMgmtError=false;
		

		if($scope.userMgmtData.userLastName == undefined ||$scope.userMgmtData.userLastName == "" || $scope.userMgmtData.userLastName== null )
           {
			$scope.userMgmtData.error.userLastNameError= true;
			$scope.validationError=true;
			$scope.loaderUserManagement=false;
		   }else{
			   
		   }
		
		if($scope.userMgmtData.userFirstName == undefined || $scope.userMgmtData.userFirstName == "" || $scope.userMgmtData.userFirstName== null )
         {
			$scope.userMgmtData.error.userFirstNameError= true;
			$scope.validationError=true;
		   }else{
			   
		   }
		 
		if($scope.userMgmtData.userMobileNumber != undefined && $scope.userMgmtData.userMobileNumber != "" && $scope.userMgmtData.userMobileNumber != null )
        {
		 if($scope.userMgmtData.userMobileNumber.length <10){
				$scope.userMgmtData.error.userMobileNumberLengthError = true;
				$scope.validationError=true;
				$scope.loaderUserManagement=false;
			}else{
				
			}
		
		}else{
				$scope.userMgmtData.error.userMobileNumberMandatoryError = true;
				$scope.validationError=true;
				$scope.loaderUserManagement=false;
		}
				
		
		 
		 if($scope.userMgmtData.userEmailAddress != undefined && $scope.userMgmtData.userEmailAddress != "" && $scope.userMgmtData.userEmailAddress!= null){
			
			 var emailPatternMatch = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
				
			 if(emailPatternMatch.test(userMgmtData.userEmailAddress)){
			 }else{
				    $scope.userMgmtData.error.userEmailAddressFormatError = true;
				    $scope.validationError=true;
					$scope.loaderUserManagement=false;
			 }
			 }else{
					$scope.userMgmtData.error.userEmailAddressError= true;
					$scope.validationError=true;
					$scope.loaderUserManagement=false;
			 }
					
			if($scope.userMgmtData.driverSupplierRole == undefined || $scope.userMgmtData.driverSupplierRole == "" || $scope.userMgmtData.driverSupplierRole== null )
			{
				$scope.userMgmtData.error.driverSupplierRoleError = true;
				$scope.validationError = true;
				$scope.loaderUserManagement=false;
			 }else{
			 }
			 if($scope.userMgmtData.supplierName == undefined || $scope.userMgmtData.supplierName == "" || $scope.userMgmtData.supplierName== null )
			{
				$scope.userMgmtData.error.supplierName = true;
				$scope.validationError = true;
				$scope.loaderUserManagement=false;
			 }else{
			 }
		 
			if($scope.userMgmtData.activeInactive == undefined || $scope.userMgmtData.activeInactive == "" || $scope.userMgmtData.activeInactive== null )
			 {
				$scope.userMgmtData.error.activeInactiveError= true;
				$scope.validationError=true;
			   }else{
				   
			   }
		 
		
	
			if(!$scope.validationError)
			{
				 var userDetails ={};
				var checkUniqueUser={};
				var driverSupplierDTO = {};
				var role={};
				var user={};
				var statusflag= false;
				checkUniqueUser.mobileNumber=$scope.userMgmtData.userMobileNumber;
				checkUniqueUser.emailId=$scope.userMgmtData.userEmailAddress;
				checkUniqueUser.supplierId=$scope.userMgmtData.supplierName.supplierId;
				userService.checkUniqueUser(checkUniqueUser).then(function(response){
				userDetails = response.data;
				
					
			 if($scope.userMgmtData.activeInactive=='Active'){
						
							forEditActiveInactive=user.enabled = true;
							
					}else{
							forEditActiveInactive=user.enabled = false;
					}
						
			
	  
		
		if(userDetails !=""){
			
			if(userMgmtData.userId == userDetails.userId ){
				
			}else{
				statusflag = true;	
				
			}
			
		}
		
		
		
	   if(statusflag ===true){
		
		  $scope.userMgmtData.error.duplicate= true;
		  $scope.validationError=true;
		  $scope.userMgmtData.error.userMobileNumberMandatoryError = true;
		  $scope.validationError=true;
		  $scope.loaderUserManagement=false;

	   }else
	   {
		
		   var supplier = {};
		   var forEditActiveInactive={};
			supplier.supplierId = userMgmtData.supplierName.supplierId;
			user.supplier = {};
			user.supplier = supplier;
			userMgmtData.statusFilter = $scope.statusFilter;
			userMgmtData.supplierFilter = $scope.supplierFilter;
		
		 if(userMgmtData.activeInactive=='Active'){
			
				forEditActiveInactive=user.enabled = true;
		}else{
				forEditActiveInactive=user.enabled = false;
		}	
			
			
		
		if($scope.userMgmtData.driverSupplierRole=='Driver'){
			role.roleId=1;
			role.roleDescription='Driver';
			$scope.userDriver='Driver';
		}else{
			role.roleId=2;
			role.roleDescription='Supplier';
			$scope.userDriver='Supplier';
		}
		
		
		user.firstName = $scope.userMgmtData.userFirstName;
		user.lastName = $scope.userMgmtData.userLastName;
		user.mobilePhone = $scope.userMgmtData.userMobileNumber;
		user.emailId = $scope.userMgmtData.userEmailAddress;
		user.roleSelected = $scope.userMgmtData.driverSupplierRole;
		user.supplier= supplier;
		driverSupplierDTO.user =user;
		driverSupplierDTO.role =role;
		
		
		if($scope.isEdit==false){
			
		userService.addUser(driverSupplierDTO).then(function(response){
	
			if(response.status=='success'){
			
				$scope.userMgmtPopupClose();
				$scope.loaderUserManagement=false;
				$scope.getFilteredData ();
			}
			else{
	
				$scope.loaderUserManagement=false;
			}
	}); 
	   }	else{
			
		 console.log("$scope.userMgmtData in else"+JSON.stringify($scope.userMgmtData));
		 var driverSupplierEditDTO={};
		 var supplier={};
		
		
		supplier.supplierId=$scope.userMgmtData.supplierName.supplierId;		
		driverSupplierEditDTO.userId=$scope.userMgmtData.userId;
		driverSupplierEditDTO.firstName=$scope.userMgmtData.userFirstName;
		driverSupplierEditDTO.lastName= $scope.userMgmtData.userLastName;
		driverSupplierEditDTO.emailId=$scope.userMgmtData.userEmailAddress;
		driverSupplierEditDTO.mobilePhone=$scope.userMgmtData.userMobileNumber;
		driverSupplierEditDTO.createDate=$scope.userMgmtData.createDate;
		driverSupplierEditDTO.updatedBy=$scope.userMgmtData.updatedBy;
		driverSupplierEditDTO.updatedDate=$scope.userMgmtData.updatedDate;
		driverSupplierEditDTO.supplier=supplier;
		driverSupplierEditDTO.roleId=role.roleId;
		driverSupplierEditDTO.roleName=role.roleDescription;
		driverSupplierEditDTO.external=$scope.userMgmtData.external;
		driverSupplierEditDTO.enabled=forEditActiveInactive;
		driverSupplierEditDTO.roleName=$scope.userMgmtData.driverSupplierRole;
		driverSupplierEditDTO.supplierFilter=$scope.userMgmtData.supplierName.supplierId;
		driverSupplierEditDTO.role=role;
		
		
		    userService.editUser(driverSupplierEditDTO).then(function(response){
		
		
			if(response.status=='success'){
			
				$scope.userMgmtPopupClose();
				$scope.loaderUserManagement=false;
				$scope.getFilteredData ();
			}
			else{
				$scope.userMgmtPopupClose();
				$scope.loaderUserManagement=false;
				$scope.getFilteredData ();			}
	}); 
		   
		   
		   
	   }
   }
   });
  	
		}else{
			$scope.loaderUserManagement=false;
		}
	}
	
	
	
	// for avoiding alphabet copy paste in mobile text box -- created by naren
	 $scope.pasteOptions = function(e) {
    var regex = /^\d+$/;
	var clipboardData = event.clipboardData || window.clipboardData || event.originalEvent.clipboardData;
    var pastedData = clipboardData.getData('text/plain');
    if(pastedData.match(regex) === null) {
      e.preventDefault();
      return false;      
    }
	};
	// end
    
   
    
    
    
    
     $scope.getUserType=function(){
		 return 'Add';
    	
     }
	 
	 
	 
	 
	  

var headerTableUserManagement = [
  { title: "LAST NAME", dataKey: "Last Name" },		
  { title: "FIRST NAME", dataKey: "First Name" },		
  { title: "EMAIL ADDRESS", dataKey: "EmailAddress" },		
  { title: "SUPPLIER NAME", dataKey: "Supplier Name" },
  { title: "ROLE", dataKey: "Role" },		
  { title: "MOBILE", dataKey: "Mobile" },		
  { title: "STATUS", dataKey: "Status" } 
 		
];
	 
	 
	 $scope.downloadUserPdf=function() {	
	 
var data = [];		


if($scope.jsonForUserManagementExport !=undefined && $scope.jsonForUserManagementExport.length >0){

data=$scope.jsonForUserManagementExport;		
	var doc = new jsPDF('p', 'mm', [650, 550]); 
	 doc.setFontSize(17);
		
doc.autoTable(headerTableUserManagement, data, {	
	
 
  margin: { top: 50, left: 20, right: 20, bottom: 0 },
  styles : {
		overflow : 'linebreak'
	},
   drawHeaderCell: function (cell, data) {
	    if (cell.raw === 'ID') {// paint.Name header red // paint.Name header red
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
	doc.save('UserManagement.pdf');	
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

//===============
$(document).ready(function(){
						$('[data-toggle="tooltip"]').tooltip();   
					});
					$('[data-toggle="tooltip"]').on('shown.bs.tooltip', function () {
					  $timeout(function(){
						$('[data-toggle="tooltip"]').tooltip('hide')
						},3000)
					});
					
		$scope.executeExcel_user=function(){
						$('#usermanagementExcelData').keypress(function (e) {
							 var key = e.which;
							 if(key == 13)  // the enter key code
							  {
								$('#usermanagementExcelData').click();
								return false;  
							  }
							});
		
			} 
//commented by prema			
      // $interval(function() {

                                                   

  // },1000);
  $scope.addTabIndex = function (){
	$timeout(function(){	
		var userManagementTable_lengthTabindex = angular.element( document.querySelector( "[name='userManagementTable_length']" ) );
									userManagementTable_lengthTabindex.prop('tabindex','11');
		var paginate_buttonTabIndex = angular.element( document.querySelectorAll( ".paginate_button " ) );
								paginate_buttonTabIndex.prop('tabindex','30');
		},5000);
	}		
	
	//by deepak for popup
	
	 $scope.driverSupplierRole=["Driver","Supplier"];
	 $scope.activeInactive=["Active","Inactive"];
	 $scope.getUserType=function(){
		 return 'Add';
     }
	 
	$scope.addDriverSupplierUserMgmt = function(){
	
		$scope.user.enabled = 'Active';
	     $scope.userMgmtData={};
		$scope.error = false;
		$scope.addUserRow = true;
	    $scope.addUser = true;
	    $scope.user = {};
		$scope.addEditUserPopup=true;
	   
	   if($scope.supplierFilter != undefined){
		  $scope.user.supplier={};
	   $scope.user.supplier.supplierId = $scope.supplierFilter; 
	   
	   }
	   
	   
	    if($rootScope.user.role =='Supplier')
		   {
			$scope.userMgmtData={};
			$scope.suppliers=[];
		
			$scope.suppliers.push($scope.supplierForpopup);
			
		//	console.log($scope.suppliers[0].description);
			$scope.userMgmtData.supplierName = $scope.suppliers[0];
			 $scope.user.supplier.supplierId = $scope.supplierFilter;
			
		$scope.roleType=[];
		$scope.roleType.push("Driver");
		$scope.userMgmtData.driverSupplierRole = $scope.user.roleSelected="Driver";
	
		   }
	   
	}
	
	$scope.userMgmtPopupClose = function(){
		 
			$scope.userMgmtData={};
			$scope.addEditUserPopup=false;
		   }
	$interval( function(){
        $('#userManagementTable_filter input').prop('title','Search By Last Name, First Name, Email Address, Supplier Name, Role, Mobile');
		$('[data-toggle="tooltip"]').tooltip(); 
		$('[data-toggle="tooltip"]').on('shown.bs.tooltip', function () {
					  $timeout(function(){
						$('[data-toggle="tooltip"]').tooltip('hide')
						},3000)
					})
		$('.sorting_asc').each(function () { $(this).attr('tabindex', -1);});
		$('.sorting').each(function () { $(this).attr('tabindex', -1);});
		},1000);
		
    $scope.removeInputUserValue = function(){                   
        $scope.dtOptions=DTOptionsBuilder.newOptions()
        .withOption('bFilter', true);
		}
		//commented by prema
	// $interval(function() {
		// $('.sorting_asc').each(function () { $(this).attr('tabindex', -1);});
		// $('.sorting').each(function () { $(this).attr('tabindex', -1);});
	// })
	$scope.addTabIndexToPopup = function(){
		$("#userManagementTable_filter input").prop('tabindex','-1');
        $(".paginate_button " ).prop('tabindex','-1');
        $( "[name='userManagementTable_length']" ).prop('tabindex','-1');
	}
	
		$scope.removeTabIndex = function() {
			  var assignedtabsIndexValue = angular.element(document.querySelectorAll(".userTabsIndex"));
											assignedtabsIndexValue.attr('tabindex',"-1");
				var navigation_menu = angular.element(document.querySelectorAll(".a2"));
											navigation_menu.attr('tabindex',"-1");
			}
			
		$scope.assignTabIndexclose = function(){
			var navigation_menu = angular.element(document.querySelectorAll(".a2"));
									navigation_menu.attr('tabindex',"1");
			var userDetails_lengthTabindex = angular.element( document.querySelector( "[name='userManagementTable_length']" ) );
									userDetails_lengthTabindex.prop('tabindex','11');
									
			var paginate_buttonTabIndex = angular.element( document.querySelectorAll( ".paginate_button " ) );
									paginate_buttonTabIndex.prop('tabindex','12');
									
			 var assignedtabsIndexValue = angular.element(document.querySelectorAll(".userTabsIndex"));
									assignedtabsIndexValue.attr('tabindex',"5");
			$interval( function(){
			$("#usermanagementExcelData").removeAttr("tabindex");
			$("#usermanagementExcelData").attr("tabindex", "5");
			},2000);								
		}
	
});
var brtaApp = angular.module('brtaApp',['ngRoute','xeditable','changeStatus','showDay','convertToString',
                                        'ui.bootstrap','angularUtils.directives.dirPagination','angular-js-xlsx'
                                        ,'bootstrapLightbox','naif.base64','base64','datatables', 'ui.select',
                                        'ngSanitize','ngCsv','ngIdle', 'ngMessages','ngPatternRestrict','sly', 'ngRoute']).constant('applicationContextURL','/brta/app');
										
										
										
	brtaApp.filter('range', function() {
	  return function(input, total) {
		total = parseInt(total);
		for (var i=0; i<total; i++)
		  input.push(i);
		return input;
	  };
	  });

brtaApp.directive('limitTo', [function() {
    return {
        restrict: "A",
        link: function(scope, elem, attrs) {
            var limit = parseInt(attrs.limitTo);
            angular.element(elem).on("keypress", function(e) {
                if (this.value.length == limit) e.preventDefault();
            });
        }
    }
}])

brtaApp.directive('openMenuByClick', function ($timeout) {
    return {
        link: function (scope, element, attrs) {
               element.bind('click', function () {
                 
                 $timeout(function () {
                     $("#"+attrs.openMenuByClick).find('input').click();
                });
                
              
            });
        }
    };
});
brtaApp.directive('excelExport',
    function () {
      return {
        restrict: 'A',
        scope: {
        	fileName: "@",
            data: "&exportData"
        },
        replace: true,
	   template: '<img src="/brta/app/ui/resources/images/new_excel.png"'+ 
									'ng-click="download()"'+ 'title="Download Excel"' + 'data-toggle="tooltip"' +
									'class="" tabindex="0">',
        link: function (scope, element) {
        	
        	scope.download = function() {

        		function datenum(v, date1904) {
            		if(date1904) v+=1462;
            		var epoch = Date.parse(v);
            		return (epoch - new Date(Date.UTC(1899, 11, 30))) / (24 * 60 * 60 * 1000);
            	};
            	
            	function getSheet(data, opts) {
            		var ws = {};
            		var range = {s: {c:10000000, r:10000000}, e: {c:0, r:0 }};
            		for(var R = 0; R != data.length; ++R) {
            			for(var C = 0; C != data[R].length; ++C) {
            				if(range.s.r > R) range.s.r = R;
            				if(range.s.c > C) range.s.c = C;
            				if(range.e.r < R) range.e.r = R;
            				if(range.e.c < C) range.e.c = C;
            				var cell = {v: data[R][C] };
            				if(cell.v == null) continue;
            				var cell_ref = XLSX.utils.encode_cell({c:C,r:R});
            				
            				if(typeof cell.v === 'number') cell.t = 'n';
            				else if(typeof cell.v === 'boolean') cell.t = 'b';
            				else if(cell.v instanceof Date) {
            					cell.t = 'n'; cell.z = XLSX.SSF._table[14];
            					cell.v = datenum(cell.v);
            				}
            				else cell.t = 's';
            				
            				ws[cell_ref] = cell;
            			}
            		}
            		if(range.s.c < 10000000) ws['!ref'] = XLSX.utils.encode_range(range);
            		return ws;
            	};
            	
            	function Workbook() {
            		if(!(this instanceof Workbook)) return new Workbook();
            		this.SheetNames = [];
            		this.Sheets = {};
            	}
            	 
            	var wb = new Workbook(), ws = getSheet(scope.data());
            	/* add worksheet to workbook */
            	wb.SheetNames.push(scope.fileName);
            	wb.Sheets[scope.fileName] = ws;
            	var wbout = XLSX.write(wb, {bookType:'xlsx', bookSST:true, type: 'binary'});

            	function s2ab(s) {
            		var buf = new ArrayBuffer(s.length);
            		var view = new Uint8Array(buf);
            		for (var i=0; i!=s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF;
            		return buf;
            	}
            	
        		saveAs(new Blob([s2ab(wbout)],{type:"application/octet-stream"}), scope.fileName+'.xlsx');
        		
        	};
        
        }
      };
    }
 );

// Start Only allow character inputs
brtaApp.directive('replace', function() {
  return {
    require: 'ngModel',
    scope: {
      regex: '@replace',
      with: '@with'
    }, 
    link: function(scope, element, attrs, model) {
      model.$parsers.push(function(val) {
        if (!val) { return; }
        var regex = new RegExp(scope.regex);
        var replaced = val.replace(regex, scope.with); 
        if (replaced !== val) {
          model.$setViewValue(replaced);
          model.$render();
        }         
        return replaced;         
      });
    }
  };
});

brtaApp.directive('lettersOnly', function() {
  return {
    replace: true,
    template: '<input replace="[^a-zA-Z]" with="">'
  };
})
//END  Only allow character inputs


brtaApp.directive('onlyNumeric', function() {
  return {
    require: 'ngModel',
    link: function (scope, element, attr, ngModelCtrl) {
      function fromUser(text) {
        var transformedInput = text.replace(/[^0-9]/g, '');
       
        if(transformedInput !== text) {
            ngModelCtrl.$setViewValue(transformedInput);
            ngModelCtrl.$render();
        }
        return transformedInput;
      }
      ngModelCtrl.$parsers.push(fromUser);
    }
  }; 
});


/*
brtaApp.service('userDetails', function($http,applicationContextURL){
	return {
		getLoggedInUserDetails: function(){
		return $http.get(applicationContextURL+"/ui/common/getdetails/user").	
	then(function(response){
	
	return response.data;
		
	},function(errResponse){
		
		
		
	});
		
		}
	}
});*/

angular.module('changeStatus', []).filter('filterStatus', function() {
	return function(status){
	if(status==true){
		return 'Active';
	}
	else{
		return 'Inactive';
	}
	}
})


angular.module('showDay', []).filter('filterShowDay', function() {

	return function(frequency,weekNumber,dayNumber){
	
		var str = "" ;
		if(frequency==1){
			str= str+'Weekly ';
			str = str + 'Week '+weekNumber
		}
		
		if(frequency==2){
			str = str+'Monthly ';
			str = str + 'Week '+weekNumber
		}
		
	
	if(dayNumber==1){
		return str+'Monday';
	}
	else if(dayNumber==2){
		return str+'Tuesday';
	}
	else if(dayNumber==3){
		return str+'Wednesday';
	}
	else if(dayNumber==4){
		return str+'Thursday';
	}
	else if(dayNumber==5){
		return str+'Friday';
	}
	else if(dayNumber==6){
		return str+'Saturday';
	}
	else if(dayNumber==7){
		return str+'Sunday';
	}
	}
})



angular.module('convertToString', []).filter('convertArrayToString', function() {
	return function(array){
	  if(array!=null && array.length!=0){
		  return array.join(",");
	  }
	}
})

brtaApp.config(function($routeProvider) {
	
    $routeProvider
        // route for the home page
        .when('/supplierLogin', {
            templateUrl : '../ui/views/supplierlogin.html',
            controller  : 'supplierloginController'
            
        })

        .when('/users', {
            templateUrl : 'ui/views/usermanagement.html',
            controller  : 'userManagementController',
            resolve: {
                message: function(){
                    return 'Driver';
    					}
    					}
        })
		
        .when('/incidentmanagement', {
            templateUrl : 'ui/views/incidenttypemanagement.html',
            controller  : 'incidentTypeManagementController'
        })
		.when('/needHelp', {
            templateUrl : 'ui/views/needHelp.html',
            controller  : 'needHelpController'
        })
		.when('/needHelpForSupplier', {
            templateUrl : '../ui/views/needHelp.html',
            controller  : 'needHelpController'
        })
        
        .when('/storemanagement', {
            templateUrl : 'ui/views/storemanagement.html',
            controller  : 'storeManagementController',
			 activetab: 'storeListView',
			 
			
        })
		
		.when('/storesupplierlist', {
            templateUrl : 'ui/views/storemanagement.html',
            controller  : 'storeManagementController',
			 activetab: 'assignActivityTab'
        })
		.when('/assignments', {
            templateUrl : 'ui/views/storemanagement.html',
            controller  : 'storeManagementController',
			 activetab: 'assignment'
        })

        
        .when('/balepickup', {
            templateUrl : 'ui/views/balepickup.html',
            controller  : 'balepickupController',
            activetab: 'balePickupTab'
        })
        .when('/pendingStoreReportTab', {
            templateUrl : 'ui/views/balepickup.html',
            controller  : 'balepickupController',
            activetab: 'pendingStoreReportTab'
        })
        
        .when('/reports', {
            templateUrl : 'ui/views/reports.html',
            controller  : 'reportController',
            activetab: 'detailedActivityReportTab'
        })
        .when('/rmtReportTab', {
            templateUrl : 'ui/views/reports.html',
            controller  : 'reportController',
            activetab: 'rmtReportTab'
        })
		
		 .when('/usersForSupplier', {
            templateUrl : '../ui/views/usermanagement.html',
            controller  : 'userManagementController',
            resolve: {
                message: function(){
                    return 'Driver';
    					}
    					}
        })
		
        .when('/incidentmanagementForSupplier', {
            templateUrl : '../ui/views/incidenttypemanagement.html',
            controller  : 'incidentTypeManagementController'
        })
        
        .when('/storesupplierForSupplier', {
            templateUrl : '../ui/views/storemanagement.html',
            controller  : 'storeManagementController',
			activetab: 'assignActivityTab'
        })
		.when('/assignmentForSupplier', {
            templateUrl : '../ui/views/storemanagement.html',
            controller  : 'storeManagementController',
			activetab: 'assignment'
        })        
        .when('/balepickupForSupplier', {
            templateUrl : '../ui/views/balepickup.html',
            controller  : 'balepickupController',
			activetab: 'balePickupTab'
        })
		.when('/balepickupForPendingSupplier', {
            templateUrl : '../ui/views/balepickup.html',
            controller  : 'balepickupController',
			activetab: 'pendingStoreReportTab'
        })
        
        .when('/reportsForSupplier', {
            templateUrl : '../ui/views/reports.html',
            controller  : 'reportController',
			activetab : 'detailedActivityReportTab'
        })

                 
       
})
.run(function(userService,$location,$rootScope,$route){


var user=window.user;

	
	$rootScope.user=JSON.parse(window.user);	

	
	if($rootScope.user!=undefined){
				   
		var user =$rootScope.user;
		var location = $location.url();
				
		if(user.role=='ROBRCSR' || user.role=='ROBRMANAGER'){

			if(location== '' || location == undefined || location == '/'){
				$location.path('/storemanagement');
				
			}else{
				$location.path(location);
			}
				
			}
		
		 else if(user.role=='Supplier' && user.firstName !=undefined){
			
			 if(location== '' || location == undefined || location == '/'){
				$location.path('/storesupplierForSupplier');
				
			}else{
				$location.path(location);
			}
			
			}else if(user.role=='Supplier'){
				
				$location.path('/supplierLogin');
			}
				
	}else{
		
		
	userService.getLoggedInUserDetails().then(function(response){
		 var user = response;


		 if(user.role=='ROBRCSR' || user.role=='ROBRMANAGER' || user.role=='ROBRSUPPLIER'){
			 if(location== '' || location == undefined || location == '/'){
				$location.path('/storemanagement');
				
			}else{
				$location.path(location);
			}
			 /*if(location!= '/storemanagement'){
				$location.path(location);
				return
			}
				$location.path('/storemanagement');*/
				
			}
			
			
		 /*if(user.role=='ROBRMANAGER'){
			 if(location!= '/storemanagement'){
				$location.path(location);
				return
			}
				$location.path('/storemanagement');
			}
			
			
		 if(user.role=='ROBRSUPPLIER'){
			 if(location!= '/storemanagement'){
				$location.path(location);
				return
			}
				$location.path('/storemanagement');
			}*/
			
			
		 if(user.role=='Supplier' && user.firstName !=undefined){
			
			 if(location== '' || location == undefined || location == '/'){
				$location.path('/storesupplierForSupplier');
				
			}else{
				$location.path(location);
			}
			 /*if(location!= '/storesupplierForSupplier'){
				$location.path(location);
				return
			}
				$location.path('/storesupplierForSupplier');*/
				
				
			}else if(user.role=='Supplier'){
		
				$location.path('/supplierLogin');
				
			}
			
			
			
	 });
	}
})

brtaApp.config(function(IdleProvider, KeepaliveProvider) {
  IdleProvider.idle(10*60); // 10 minutes idle
  IdleProvider.timeout(5); // after 30 seconds idle, time the user out
  KeepaliveProvider.interval(2); // 5 minute keep-alive ping
}).run(function($rootScope,Idle,$location,$window) {
	Idle.watch();
    $rootScope.$on('IdleTimeout', function() {
		
    });
});


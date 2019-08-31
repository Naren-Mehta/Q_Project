brtaApp.service('userService', function($http,$q, applicationContextURL) {
	return {
		getLoggedInUserDetails : function() {
			return $http.get(applicationContextURL + "/ui/common/getdetails/user")
					.then(function(response) {
						return response.data;

					}, function(errResponse) {

						

					});

		},/*
		
		getLoggedInUserDetailsForSupplier : function() {
			return $http.get(applicationContextURL + "/ui/common/getdetails/user")
					.then(function(response) {
						return response.data;

					}, function(errResponse) {

						

					});
		},*/

		addUser : function(driverSupplierDTO) {
			return $http.post(applicationContextURL + '/ui/userMgmt/add/user',
					driverSupplierDTO).then(function(response) {
				
				return response.data;

			}, function(errResponse) {
				
				
				return $q.reject(errResponse);

			});

		},
		editUser : function(user) {
			return $http.post(applicationContextURL + '/ui/userMgmt/edit/user',
					user).then(function(response) {
				
				return response.data;

			}, function(errResponse) {
				
				return $q.reject(errResponse);

			});

		},

		getAllUsers : function() {
			return $http.get(applicationContextURL + '/ui/userMgmt/get/user/all')
					.then(function(response) {
						
						return response.data;

					}, function(errResponse) {
						
						return $q.reject(errResponse);

					});

		},

		getFilteredUsers : function(user) {
			return $http.post(
					applicationContextURL + '/ui/userMgmt/get/user/byfilters',
					user).then(function(response) {
				
				return response.data;

			}, function(errResponse) {
				
				return $q.reject(errResponse);

			});

		},
		editUser : function(user) {
			return $http.post(applicationContextURL + '/ui/userMgmt/edit/user',
					user).then(function(response) {
				
				return response.data;

			}, function(errResponse) {
				
				return $q.reject(errResponse);

			});

		},
		checkUniqueMobileNo : function(mobileNo) {
			return $http.post(
					applicationContextURL + '/ui/userMgmt/check/mobileNumber',
					mobileNo).then(function(response) {
			
				return response.data;

			}, function(errResponse) {
				
				return $q.reject(errResponse);

			});

		},
		
		checkUniqueUser : function(checkUniqueUser) {
			return $http.post(
					applicationContextURL + '/ui/userMgmt/check/checkuniqueUser',
					checkUniqueUser).then(function(response) {
			
				return response;

			}, function(errResponse) {
				
				return $q.reject(errResponse);

			});

		},
		checkUniqueEmail : function(emailId) {
			return $http.post(
					applicationContextURL + '/ui/userMgmt/check/emailIdUnique',
					emailId).then(function(response) {
			
				return response.data;

			}, function(errResponse) {
				
				return $q.reject(errResponse);

			});

		},

		getAllUsersForSupplierSite : function(supplierSite) {
			return $http.post(applicationContextURL + "/ui/userMgmt/get/user/all",
					supplierSite).then(function(response) {

				return response.data;

			}, function(errResponse) {

				

			});
		},
		
		getOnlyDriversForSupplierSite : function(supplierId) {
			return $http.post(applicationContextURL + "/ui/userMgmt/get/driverOnly",
					supplierId).then(function(response) {

				return response.data;

			}, function(errResponse) {

				

			});
		},
		
		getAllUsersForSupplierId : function(supplierId) {
			return $http.post(applicationContextURL + "/ui/userMgmt/get/user/supplierusers",
					supplierId).then(function(response) {

				return response.data;

			}, function(errResponse) {

				

			});
		},
		getAllActiveUsersForSupplierSite : function(supplierSite) {
			return $http.post(applicationContextURL + "/ui/userMgmt/get/user/active",
					supplierSite).then(function(response) {

				return response.data;

			}, function(errResponse) {

				

			});

		},
		userUploadFromExcel : function(userList){
		
		

			return $http.post(applicationContextURL
					+ "/ui/userMgmt/upload/usersFromExcel", userList)
			then(function(response) {
				return response.data;

			}, function(errResponse) {

				

			});

		}
	}
});
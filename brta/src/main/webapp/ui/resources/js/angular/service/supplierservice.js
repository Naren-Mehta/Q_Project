brtaApp.service('supplierUserService', function($http, applicationContextURL) {
	return {
		getSupplierLogin : function(user) {
			return $http.post(applicationContextURL + "/supplier/authenticate",user)
					.then(function(response) {
						
						return response.data;
					}, function(errResponse) {
						

					});

		}
	}
});
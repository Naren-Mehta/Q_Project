brtaApp.service('balepickupService', function($http, applicationContextURL) {

	return {
		getAllPickups : function(configData) {
			return $http.post(applicationContextURL + "/ui/common/getAll/pickUps",
					configData)
			then(function(response) {
				return response.data;

			}, function(errResponse) {

				console.error('Error while adding configuration data');

			});

		},
		
		downloadSampleExcel : function() {
			return $http.get(applicationContextURL + "/ui/tripMgmt/sampleexceldownload")
			then(function(response) {
				
				console.log("===hi====");
				return response.data;

			}, function(errResponse) {

				console.error('Error while adding configuration data');

			});

		},

		addNewDriver : function(assignment) {
			return $http.post(applicationContextURL + "/ui/assignMgmt/add/newdriver",
					assignment)
			then(function(response) {

				return response.data;

			}, function(errResponse) {

				console.error('Error while adding new driver');

			});
		},

		removeNewDriver : function(assignment) {
			return $http.post(applicationContextURL + "/ui/assignMgmt/remove/newdriver",
					assignment)
			then(function(response) {

				return response.data;

			}, function(errResponse) {

				console.error('Error while removing new driver');

			});
		},

		editBalePickup : function(balePickup) {
			return $http.post(applicationContextURL
					+ "/ui/tripMgmt/add/tripdetails", balePickup)
			then(function(response) {
				return response.data;
			}, function(errResponse) {

				console.error('Error while adding configuration data');

			});

		},
		addNewBalePickup : function(balePickup) {
			return $http.post(applicationContextURL
					+ "/ui/tripMgmt/add/tripdetails", balePickup)
			then(function(response) {
				return response.data;

			}, function(errResponse) {

				console.error('Error while adding configuration data');

			});

		},
		getAllDestination : function(storeName) {

			return $http.post(applicationContextURL + "/ui/assignMgmt/get/destination",
					storeName)
			then(function(response) {
				return response.data;

			}, function(errResponse) {

				console.error('Error while adding configuration data');

			});

		},
		getDestinationForUserAndDate : function(setDto) {

			return $http.post(applicationContextURL + "/ui/assignMgmt/pickups",
					setDto)
			then(function(response) {
				return response.data;

			}, function(errResponse) {

				console.error('Error while adding configuration data');

			});

		},

		getBalePickUpImages : function(tripId) {

			return $http.post(applicationContextURL + "/ui/tripMgmt/pickUp/images", tripId)
			then(function(response) {
				return response.data;

			}, function(errResponse) {

				console.error('Error while adding configuration data');

			});
		},
		
		pickupUploadFromExcel : function(pickUpList) {
			return $http.post(applicationContextURL
					+ "/ui/tripMgmt/upload/pickUpsFromExcel", pickUpList)
			then(function(response) {
				return response.data;

			}, function(errResponse) {

				console.error('Error while adding new driver');

			});
		},

		completeBalePickup : function(pickUp) {

			return $http.post(applicationContextURL
					+ "/ui/tripMgmt/update/completetrip", pickUp)
			then(function(response) {
				return response.data;
			}, function(errResponse) {

				console.error('Error while adding new driver');

			});
		},

	}

});
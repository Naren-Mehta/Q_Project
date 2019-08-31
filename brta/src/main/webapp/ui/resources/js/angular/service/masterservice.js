brtaApp.service('supplierService', function($http,applicationContextURL){
	return {
		getAllSuppliers: function(){
		return $http.get(applicationContextURL+"/ui/common/getAllSupplier").	
	then(function(response){
	
	return response.data;
		
	},function(errResponse){
		
		
		
	});
		
		},
		getAllSupplierSites: function(supplier){
			
			return $http.post(applicationContextURL+"/ui/common/get/suppliersite/bysupplier",supplier).	
		then(function(response){
		
		return response.data;
			
		},function(errResponse){
			
			
			
		});
			
			},
				
				
			allSupplierFromCustomerSite: function(customerSiteId){
			
			return $http.post(applicationContextURL+"/ui/common/get/allSupplierFromCustomerSite",customerSiteId).	
		then(function(response){
		
		return response.data;
			
		},function(errResponse){
			
			
			
		});
			
			},
			
			getSuppliersFromCustomerSite: function(customerSiteId){
			
				return $http.post(applicationContextURL+"/ui/common/get/supplierFromCustomerSite",customerSiteId).	
			then(function(response){
			
			return response.data;
				
			},function(errResponse){
				
				
				
			});
				
				}
	}
});

	
brtaApp.service('customerService', function($http,$rootScope,$q,applicationContextURL){

	return {
		getAllBuyCustomers: function(){
		
		if($rootScope.buyCustomers != undefined){
		return this.populateData($rootScope.buyCustomers);;
		}
		
		return $http.get(applicationContextURL+"/ui/common/get/buycustomers/all").	
	then(function(response){
	$rootScope.buyCustomers=response.data
	return response.data;
		
	},function(errResponse){
		
	});
		
		},
	
	
	getCustomerSites: function(storeDto){
		return $http.post(applicationContextURL+"/ui/common/getAllCustomersites",storeDto).
		then(function(response){
			return response.data;
		
	   },function(errResponse){
		
		
		});
	
},

getCustomerSitesForCustomerSiteId: function(customerSiteId){
		return $http.post(applicationContextURL+"/ui/common/getCustomerSitesFromCustomerSiteId",customerSiteId).
		then(function(response){
			return response.data;
		
	   },function(errResponse){
		
		
		});
	
},

/*by deepak*/		
getCustomerId: function(customerIds){		
		return $http.post(applicationContextURL+"/ui/common/getCustomerIds",customerIds).		
		then(function(response){		
			return response.data;		
				
	   },function(errResponse){		
				
			
		});		
			
}, /*end*/
	
	getCustomerSitesForBuyCustomer: function(customers){
		return $http.post(applicationContextURL+"/ui/common/get/customersites/bybuycustomer",customers).
		then(function(response){
			return response.data;
		
	   },function(errResponse){
		
		
		});
	
},

getAllSuppliersFromSellCustomer: function(sellCustomerId){
	
	return $http.post(applicationContextURL+"/ui/common/get/allSupplier/allSuppliersFromSellCustomer",sellCustomerId).
	then(function(response){
		return response.data;
	
   },function(errResponse){
	
	
	});
},
getSuppliersForBuyCustomer: function(buyCustomer){
	return $http.post(applicationContextURL+"/ui/common/getSupplier/bybuycustomer",buyCustomer).
	then(function(response){
		return response.data;
	
   },function(errResponse){
	
	
	});

},


getEnabledModules: function(){
	return $http.get(applicationContextURL+"/ui/common/getEnabledModules").
	then(function(response){
		return response.data;
   },function(errResponse){
	
	
	});
},



getAllSuppliersWithBuyCustomer: function(){

		if($rootScope.suppliersWithCustomer != undefined){
		return this.populateDataForMap($rootScope.suppliersWithCustomer);
		}

	return $http.get(applicationContextURL+"/ui/common/get/allSupplierWithBuyCustomer").
	then(function(response){
	$rootScope.suppliersWithCustomer= response.data;
		return response.data;
   },function(errResponse){
	
	
	});

},
getMatrialsForBuyCustomer: function(buyCustomer){
	return $http.post(applicationContextURL+"/ui/common/get/material/bycustomer",buyCustomer).
	then(function(response){
		return response.data;
   },function(errResponse){
	
	
	});
},

getMatrialsForCustomerSiteId: function(customerSiteId){
	return $http.post(applicationContextURL+"/ui/common/getMaterialForCustomerSiteId",customerSiteId).
	then(function(response){
		return response.data;
   },function(errResponse){
	
	
	});
},

getMaterialDetailsByMaterialID: function(materialId){
	return $http.post(applicationContextURL+"/ui/common/getMaterialDetailsByMaterialId",materialId).
	then(function(response){
		return response.data;
   },function(errResponse){
	
	
	});
},




getAllMatrials: function(){
	return $http.get(applicationContextURL+"/ui/common/getAllmaterials").
	then(function(response){
		return response.data;
   },function(errResponse){
	
	
	});
},
getAllSellCustomersForBuyCustomer: function(buyCustomerId){
	return $http.post(applicationContextURL+"/ui/common/get/sellcustomer/bybuycustomer",buyCustomerId).
	then(function(response){
		return response.data;
	
   },function(errResponse){
	
	
	});
},

getAllSellCustomersForBuyCustomerAndSupplier: function(buyCustomerId, supplierId){
	
	var idList=[];
	idList.push(buyCustomerId);
	idList.push(supplierId);
	return $http.post(applicationContextURL+"/ui/common/get/sellcustomer/bybuycustomerandsupplier",idList).
	then(function(response){
		return response.data;
	
   },function(errResponse){
	
	
	});
},

getSuppliersFromBuyCustomerForDAR: function(buyCustomerId){
	return $http.post(applicationContextURL+"/ui/common/get/supplier/bybuycustomer",buyCustomerId).
	then(function(response){
		return response.data;
	
   },function(errResponse){
	
	
	});
},

getAllBuyCustomersForSellCustomer: function(sellCustomerId){
	return $http.post(applicationContextURL+"/ui/common/get/buycustomer/bysellcustomer",sellCustomerId).
	then(function(response){
		return response.data;
	
   },function(errResponse){
	
	
	});
},

getBuyCustomersFromSupplierDAR: function(supplierId){
	return $http.post(applicationContextURL+"/ui/common/get/buycustomer/bysupplier",supplierId).
	then(function(response){
		return response.data;
	
   },function(errResponse){
	
	
	});
},

getAllSuppliersFromBuyAndSellCustomer: function(buyCustomerId,sellCustomerId){
	
	
	var idList=[];
	idList.push(buyCustomerId);
	idList.push(sellCustomerId);
	return $http.post(applicationContextURL+"/ui/common/get/supplier/byBuyAndSellCustomer",idList).
	then(function(response){
		return response.data;
	
   },function(errResponse){
	
	
	});
},


getAllBuyCustomerFromSellCustomerAndSupplier: function(buyCustomerId,supplierId){
	
	var idList=[];
	idList.push(buyCustomerId);
	idList.push(supplierId);
	return $http.post(applicationContextURL+"/ui/common/get/buycustomer/bysellCustomerandsupplier",idList).
	then(function(response){
		return response.data;
	
   },function(errResponse){
	
	
	});
},


getSellCustomersFromSupplierDAR: function(supplierId){
	return $http.post(applicationContextURL+"/ui/common/get/sellcustomer/bysupplier",supplierId).
	then(function(response){
		return response.data;
	
   },function(errResponse){
	
	
	});
},


getSuppliersFromSellCustomerForDAR: function(sellCustomerId){
	return $http.post(applicationContextURL+"/ui/common/get/supplier/bysellcustomer",sellCustomerId).
	then(function(response){
		return response.data;
	
   },function(errResponse){
	
	
	});
},


getCustomerSiteFromBuyCustomerSite: function(customer){
	return $http.post(applicationContextURL+"/ui/common/get/customersitefromcustomer",customer.customerId).
	then(function(response){
		return response.data;
	
   },function(errResponse){
	
	
	});
},
getCustomerSiteFromBuyCustomerAndSupplier: function(customerSiteDetails){
	return $http.post(applicationContextURL+"/ui/common/get/customersitefrombuycustomerandsupplier",customerSiteDetails).
	then(function(response){
		return response.data;
	
   },function(errResponse){
	
	
	});
},
getAllSellCustomers: function(){
	return $http.get(applicationContextURL+"/ui/common/getAllSellCustomer").
	then(function(response){
		return response.data;
	
   },function(errResponse){
	
	
	});
},
populateData: function(data) {
    var deferred = $q.defer();
    var items = [];
    for (i = 0; i < data.length; i++) {
        items.push(data[i]);
    }
    deferred.resolve(items);
    return deferred.promise;
},
populateDataForMap: function(data) {

var deferred = $q.defer();
  var items = {};

  angular.forEach( data, function(value,key){
      items[key]=value;
  });

 deferred.resolve(items);
  return deferred.promise;
  
}

}
});
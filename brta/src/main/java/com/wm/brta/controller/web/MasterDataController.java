
package com.wm.brta.controller.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wm.brta.domain.Customer;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.Frequency;
import com.wm.brta.domain.Material;
import com.wm.brta.domain.StoreListView;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.SupplierSite;
import com.wm.brta.dto.CustomerDTO;
import com.wm.brta.dto.CustomerSitesDTO;
import com.wm.brta.dto.Destination;
import com.wm.brta.dto.GetCustomerSiteDTO;
import com.wm.brta.dto.StoreInputDTO;
import com.wm.brta.service.BaleRouteTripService;
import com.wm.brta.service.CustomerService;
import com.wm.brta.service.FrequencyService;
import com.wm.brta.service.MasterService;
import com.wm.brta.service.SupplierService;

@RestController
public class MasterDataController {

	@Autowired
	MasterService supplierMasterService;

	@Autowired
	FrequencyService frequencyService;

	@Autowired
	SupplierService supplierService;
	
	@Autowired
	BaleRouteTripService baleRouteTripService;

	@Autowired
	CustomerService customerService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MasterDataController.class);

	@RequestMapping(value = "/ui/common/getAllSupplier", method = RequestMethod.GET)
	public @ResponseBody HashSet<Supplier> getAllSuppliers() {
		HashSet<Supplier> suppliers = null;

		try {
			suppliers = supplierMasterService.getAll();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getAll=====>" + e);
		}
		return suppliers;
	}

	@RequestMapping(value = "/ui/common/get/supplierFromCustomerSite", method = RequestMethod.POST)
	public @ResponseBody List<Integer> getSupplierFromCustomerSite(
			@RequestBody Integer customerSiteId) {
		List<Integer> suppliers = null;

		try {
			suppliers = supplierService
					.getSupplierFromCustomerSite(customerSiteId);

		} catch (Exception e) {
			
			LOGGER.error("Error:MasterDataController:getAll=====>" + e);
		}
		return suppliers;
	}

	@RequestMapping(value = "/ui/common/getAllCustomersites", method = RequestMethod.POST)
	public @ResponseBody List<StoreListView> getAllCustomerSites(
			@RequestBody StoreInputDTO storeInputDTO) {
		
	
		List<StoreListView> storeListViews = null;

		try {
			storeListViews = customerService.getCustomerSites(storeInputDTO);

		} catch (Exception e) {
			
			
			LOGGER.error("Error:MasterDataController:getAllCustomersites=====>"
					+ e);
		}
		return storeListViews;
	}

	
	@RequestMapping(value = "/ui/common/getCustomerSitesFromCustomerSiteId", method = RequestMethod.POST)
	public @ResponseBody List<Integer> getCustomerSitesFromCustomerSiteId(
			@RequestBody Integer customerSiteId) {

		List<Integer> sellCustomerSiteId = null;

		try {
			sellCustomerSiteId = customerService
					.getCustomerSitesFromCustomerSiteId(customerSiteId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getCustomerSitesFromCustomerSiteId=====>"
					+ e);
		}
		return sellCustomerSiteId;
	}

	// by deepak
	@RequestMapping(value = "/ui/common/getCustomerIds", method = RequestMethod.POST)
	public @ResponseBody List<Integer> getCustomerIds(
			@RequestBody List<Integer> customerSiteIds) {

		List<Integer> sellCustomerSiteIdList = new ArrayList<Integer>();

		for (Integer customerSiteId : customerSiteIds) {
			try {
				List<Integer> sellCustomerSiteId = customerService
						.getCustomerNameandId(customerSiteId);
				sellCustomerSiteIdList.addAll(sellCustomerSiteId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				LOGGER.error("Error:MasterDataController:getCustomerIds=====>"
						+ e);
			}
		}
		
		if(sellCustomerSiteIdList!=null && sellCustomerSiteIdList.size()>0){
			Set<Integer> uniqueSellCustomerSiteIdSet =new HashSet<Integer>(sellCustomerSiteIdList);
			sellCustomerSiteIdList=new ArrayList<Integer>(uniqueSellCustomerSiteIdSet);
		}

		return sellCustomerSiteIdList;
	}

	@RequestMapping(value = "/ui/common/get/customersites/bybuycustomer", method = RequestMethod.POST)
	public @ResponseBody List<CustomerSite> getAllCustomerSitesForBuyCustomer(
			@RequestBody List<Customer> customerList) {
		List<CustomerSite> storeListViews = new ArrayList<CustomerSite>();

		try {
			for (Customer customer : customerList) {
				List<CustomerSite> customerSites = customerService
						.getCustomerSitesForCustomer(customer);
				storeListViews.addAll(customerSites);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:/customersites/bybuycustomer=====>"
					+ e);
		}
		return storeListViews;
	}

	@RequestMapping(value = "/ui/common/getSupplier/bybuycustomer", method = RequestMethod.POST)
	public @ResponseBody HashSet<Supplier> getAllSuppliersForBuyCustomer(
			@RequestBody Customer customer) {
		HashSet<Supplier> suppliers = null;

		try {
			suppliers = supplierService.getAllSuppliersByBuyCustomer(customer.getCustomerId());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getAllSuppliersByBuyCustomer=====>"
					+ e);
		}
		return suppliers;
	}

	//by deepak get supplier from Sell Custoimer
	
	@RequestMapping(value = "/ui/common/get/allSupplier/allSuppliersFromSellCustomer", method = RequestMethod.POST)
	public @ResponseBody HashSet<Supplier> getAllSuppliersFromSellCustomer(
			@RequestBody Integer sellCustomerId) {
		HashSet<Supplier> suppliers = null;

		try {
			suppliers = supplierService.getAllSuppliersFromSellCustomer(sellCustomerId);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getAllSuppliersFromSellCustomer=====>"
					+ e);
		}
		return suppliers;
	}
	
	
	@RequestMapping(value = "/ui/common/getEnabledModules", method = RequestMethod.GET)
	public @ResponseBody List<String>  getEnabledModulesNameForAppSupport() {
		
		List<String>  moduleNamesWithFlag = new ArrayList<String>();
		
		
		moduleNamesWithFlag = customerService.getAllEnabledModulesForAppSupport();
		
		return moduleNamesWithFlag;
		
		
	}
	
	
	@RequestMapping(value = "/ui/common/get/allSupplierWithBuyCustomer", method = RequestMethod.GET)
	public @ResponseBody Map<String, List<Supplier>> getAllSupplierWithBuyCustomer() {
		HashSet<Supplier> suppliers = null;

		
		List<Integer> buyCustomerIds = customerService.getAllBuyCustomerIds();
		Set<Supplier> supplierSet = new HashSet<Supplier>();

		Map<String, List<Supplier>> suppliersWithCustomer = new HashMap<String, List<Supplier>>();

		try {
			for (Integer customerId : buyCustomerIds) {
				suppliers = supplierService
						.getAllSuppliersByBuyCustomer(customerId);
				suppliersWithCustomer.put(customerId.toString(),
						new ArrayList<Supplier>(suppliers));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getAllSupplierWithBuyCustomer=====>"
					+ e);
		}
		return suppliersWithCustomer;
	}

	@RequestMapping(value = "/ui/common/get/allSupplierFromCustomerSite", method = RequestMethod.POST)
	public @ResponseBody List<Supplier> allSupplierFromCustomerSite(
			@RequestBody Integer customerSiteId) {
		List<Supplier> suppliers = null;

		try {
			suppliers = supplierService
					.getSupplierFromCustomerSiteId(customerSiteId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController: allSupplierFromCustomerSite=====>"
					+ e);
		}
		return suppliers;
	}

	@RequestMapping(value = "/ui/common/get/sellcustomer/bybuycustomer", method = RequestMethod.POST)
	public @ResponseBody HashSet<CustomerDTO> getAllSellCustomersForBuyCustomer(
			@RequestBody Integer customerId) {
		
	
		HashSet<CustomerDTO> sellCustomers = null;

		try {
			sellCustomers = customerService
					.getAllSellCustomersForBuyCustomer(customerId);

		} catch (Exception e) {
		
			LOGGER.error("Error:MasterDataController:getAllSellCustomersForBuyCustomer=====>"
					+ e);
		}
		return sellCustomers;
	}
	
	
	@RequestMapping(value = "/ui/common/get/sellcustomer/bybuycustomerandsupplier", method = RequestMethod.POST)
	public @ResponseBody HashSet<CustomerDTO> getAllSellCustomersForBuyCustomerAndSupplier(
			@RequestBody List<Integer> idList) {
		
	
		HashSet<CustomerDTO> sellCustomers = null;
		
		Integer buyCustomerId=idList.get(0);
		Integer supplierId=idList.get(1);
		
		try {
			sellCustomers = customerService
					.getAllSellCustomersForBuyCustomerAndSupplier(buyCustomerId, supplierId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getAllSellCustomersForBuyCustomer=====>"
					+ e);
		}
		return sellCustomers;
	}
	
	
	@RequestMapping(value = "/ui/common/get/supplier/bybuycustomer", method = RequestMethod.POST)
	public @ResponseBody HashSet<Supplier> getAllSuppliersForBuyCustomer(
			@RequestBody Integer customerId) {
		
		HashSet<Supplier> suppliers = null;

		try {
			suppliers = customerService
					.getAllSuppliersForBuyCustomer(customerId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getAllSuppliersForBuyCustomer=====>"
					+ e);
		}
		return suppliers;
	}
	
	
	@RequestMapping(value = "/ui/common/get/buycustomer/bysellcustomer", method = RequestMethod.POST)
	public @ResponseBody HashSet<CustomerDTO> getAllBuyCustomersForSellCustomer(
			@RequestBody Integer customerId) {
		
		HashSet<CustomerDTO> sellCustomers = null;

		try {
			sellCustomers = customerService
					.getAllBuyCustomersForSellCustomer(customerId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getAllSellCustomersForBuyCustomer=====>"
					+ e);
		}
		return sellCustomers;
	}
	
	@RequestMapping(value = "/ui/common/get/supplier/bysellcustomer", method = RequestMethod.POST)
	public @ResponseBody HashSet<Supplier> getAllSuppliersForSellCustomer(
			@RequestBody Integer customerId) {
		
		HashSet<Supplier> suppliers = null;

		try {
			suppliers = customerService
					.getAllSuppliersForSellCustomer(customerId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getAllSuppliersForBuyCustomer=====>"
					+ e);
		}
		return suppliers;
	}
	
	//get buy and sell customer from supplier
	
	@RequestMapping(value = "/ui/common/get/buycustomer/bysupplier", method = RequestMethod.POST)
	public @ResponseBody HashSet<CustomerDTO> getAllBuyCustomersForSupplier(
			@RequestBody Integer supplierId) {
		
		HashSet<CustomerDTO> sellCustomers = null;

		try {
			sellCustomers = customerService
					.getAllBuyCustomersForSupplier(supplierId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getAllSellCustomersForBuyCustomer=====>"
					+ e);
		}
		return sellCustomers;
	}
	
	@RequestMapping(value = "/ui/common/get/supplier/byBuyAndSellCustomer", method = RequestMethod.POST)
	public @ResponseBody HashSet<Supplier> getAllSuppliersFromBuyAndSellCustomer(
			@RequestBody List<Integer> idList ) {
		
		HashSet<Supplier> suppliers = null;

		Integer buyCustomerId= idList.get(0);
		Integer sellCustomerId= idList.get(1);
		try {
			
			suppliers = customerService.getAllSuppliersFromBuyAndSellCustomer(buyCustomerId,sellCustomerId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getAllSellCustomersForBuyCustomer=====>"
					+ e);
		}
		return suppliers;
	}
	
	@RequestMapping(value = "/ui/common/get/buycustomer/bysellCustomerandsupplier", method = RequestMethod.POST)
	public @ResponseBody HashSet<CustomerDTO> getAllBuyCustomersForSellCustomerAndSupplier(
			@RequestBody List<Integer> idList) {
		
		HashSet<CustomerDTO> buyCustomers = null;

		Integer sellCustomerId= idList.get(0);
		Integer supplierId= idList.get(1);
		
		try {
			buyCustomers = customerService
					.getAllBuyCustomersForSellCustomerAndSupplier(sellCustomerId,supplierId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getAllSellCustomersForBuyCustomer=====>"
					+ e);
		}
		return buyCustomers;
	}
	
	@RequestMapping(value = "/ui/common/get/sellcustomer/bysupplier", method = RequestMethod.POST)
	public @ResponseBody HashSet<CustomerDTO> getAllSellCustomersForSupplier(
			@RequestBody Integer supplierId) {
		
		HashSet<CustomerDTO> sellCustomers = null;

		try {
			sellCustomers = customerService
					.getAllSellCustomersForSupplier(supplierId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getAllSellCustomersForBuyCustomer=====>"
					+ e);
		}
		return sellCustomers;
	}
	
	
	
	/////////////
	
	
	//by deepak
	@RequestMapping(value = "/ui/common/get/customersitefromcustomer", method = RequestMethod.POST)
	public @ResponseBody List<CustomerSitesDTO> getCustomerSiteFromCustomer(@RequestBody Integer customerId) {
		List<CustomerSitesDTO> customersites = new ArrayList<CustomerSitesDTO>(); ;
	
		try {
			customersites = customerService.getCustomerSiteFromCustomer(customerId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:customersitefrombuycustomer=====>"+ e);
		}
		return customersites;
	}
	

//end
	
	@RequestMapping(value = "/ui/common/get/customersitefrombuycustomerandsupplier", method = RequestMethod.POST)
	public @ResponseBody List<CustomerSitesDTO> getCustomerSiteFromCustomerAndSupplier(@RequestBody GetCustomerSiteDTO customerSiteDTO) {
		List<CustomerSitesDTO> customersites = new ArrayList<CustomerSitesDTO>(); ;
	
		try {
			customersites = customerService.getCustomerSiteFromCustomerAndSupplier(customerSiteDTO);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:customersitefrombuycustomer=====>"+ e);
		}
		return customersites;
	}
	
	
	@RequestMapping(value = "/ui/common/getAllSellCustomer", method = RequestMethod.GET)
	public @ResponseBody List<CustomerDTO> getAllSellCustomer() {
		List<CustomerDTO> sellCustomers = null;

		try {
			sellCustomers = customerService.getAllSellCustomers();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getAllSellCustomersForBuyCustomer=====>"
					+ e);
		}
		return sellCustomers;
	}

	@RequestMapping(value = "/ui/common/get/suppliersite/bysupplier", method = RequestMethod.POST)
	public @ResponseBody HashSet<SupplierSite> getAllSupplierSitesBySupplier(
			@RequestBody Supplier supplier) {
		HashSet<SupplierSite> supplierSites = null;

		try {
			supplierSites = supplierService
					.getAllSupplierSitesBySupplier(supplier);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getAllSupplierSitesBySupplier=====>"
					+ e);
		}
		return supplierSites;
	}

	@RequestMapping(value = "/ui/common/get/material/bycustomer", method = RequestMethod.POST)
	public @ResponseBody HashSet<Material> getAllMaterialsByCustomer(
			@RequestBody Integer customerId) {
		HashSet<Material> materials = null;

		try {
			materials = customerService.getAllMaterialsByCustomer(customerId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:/ui/common/get/material/bycustome=====>"
					+ e);
		}
		return materials;
	}

	@RequestMapping(value = "/ui/common/getAllmaterials", method = RequestMethod.GET)
	public @ResponseBody List<Material> getAllMaterials() {
		List<Material> materials = null;

		try {
			materials = customerService.getAllMaterials();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getAllmaterials=====>"
					+ e);
		}
		return materials;
	}

	//by deepak to get material details from material id
	
	@RequestMapping(value = "/ui/common/getMaterialDetailsByMaterialId", method = RequestMethod.POST)
	public @ResponseBody List<Material> getMaterialDetailsFromMaterialID(@RequestBody Integer customerSiteId) {
		List<Material> materials =null;

		try {
			materials = customerService.getMaterialDetailsByCustomerSite(customerSiteId);
		} catch (Exception e) {
			LOGGER.error("Error:MasterDataController:getMaterialDetailsByMaterialId=====>"
					+ e);
		}
		return materials;
	}
	
	
	
	
	@RequestMapping(value = "/ui/common/getMaterialForCustomerSiteId", method = RequestMethod.POST)
	public @ResponseBody Map<Integer,Integer> getMaterialForCustomerSite(@RequestBody Integer customerSiteId) {
		Map<Integer,Integer>  materials = null;

		try {
		
			materials = customerService.getMaterialForCustomerSite(customerSiteId);
		
		} catch (Exception e) {
		
			
			LOGGER.error("Error:MasterDataController:getMaterialForCustomerSiteId=====>"
					+ e);
		}
		return materials;
	}
	

	@RequestMapping(value = "/ui/common/get/buycustomers/all", method = RequestMethod.GET)
	public @ResponseBody List<Customer> getAllBuyCustomers() {
		List<Customer> buyCustomers = null;

		try {
			buyCustomers = customerService.getAllBuyCustomers();

		} catch (Exception e) {
			
			LOGGER.error("Error:MasterDataController:getAllBuyCustomers=====>"
					+ e);
		}
		return buyCustomers;
	}

	@RequestMapping(value = "/ui/common/get/destination", method = RequestMethod.POST)
	public @ResponseBody HashSet<Destination> getDestinationOfStore(
			@RequestBody String storeName) {

		HashSet<Destination> destinations = null;

		try {
			destinations = customerService.getAllDestination(storeName);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getAllDestination=====>"
					+ e);
		}
		return destinations;
	}

	@RequestMapping(value = "/ui/common/get/frequency", method = RequestMethod.GET)
	public @ResponseBody List<Frequency> getFrequency() {

		List<Frequency> frequencyList = null;

		try {
			frequencyList = frequencyService.getAllFrequency();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error:MasterDataController:getAllDestination=====>"
					+ e);
		}
		return frequencyList;
	}
	
	@RequestMapping(value = "/ui/common/get/destinationsFromStoreIdList", method = RequestMethod.POST)
	public @ResponseBody List<Destination> getdestinationsFromStoreIdList(@RequestBody List<Integer> storeIdList) {

		
		
		
		List<Destination> destinations = baleRouteTripService
				.getAllDestinations(storeIdList);
		
		
		
		return destinations;
	}
	
	

}

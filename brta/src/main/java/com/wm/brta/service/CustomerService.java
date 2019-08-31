package com.wm.brta.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import com.wm.brta.domain.Customer;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.Material;
import com.wm.brta.domain.StoreListView;
import com.wm.brta.domain.Supplier;
import com.wm.brta.dto.CustomerDTO;
import com.wm.brta.dto.CustomerSitesDTO;
import com.wm.brta.dto.Destination;
import com.wm.brta.dto.GetCustomerSiteDTO;
import com.wm.brta.dto.StoreInputDTO;

public interface CustomerService {

	public List<Customer> getAllBuyCustomers();

	public List<Integer> getAllBuyCustomerIds();

	// public Customer getAllBuyCustomerByName(String buyCustomerName);
	public List<StoreListView> getCustomerSites(StoreInputDTO storeInputDTO);

	public List<Integer> getCustomerSitesFromCustomerSiteId(
			Integer customerSiteId);

	public List<CustomerSite> getCustomerSitesForCustomer(Customer customer);

	public HashSet<Material> getAllMaterialsByCustomer(Integer customer);

	public List<Material> getAllMaterials();

	// public List<Integer> getMaterialForCustomerSite(Integer customerSiteId);
	public Map<Integer, Integer> getMaterialForCustomerSite(
			Integer customerSiteId);

	public HashSet<CustomerDTO> getAllSellCustomersForBuyCustomer(
			Integer buyCustomer);
	
	public HashSet<CustomerDTO> getAllSellCustomersForBuyCustomerAndSupplier(
			Integer buyCustomer, Integer supplierId);

	public HashSet<Supplier> getAllSuppliersForBuyCustomer(Integer buyCustomer);

	public HashSet<CustomerDTO> getAllBuyCustomersForSellCustomer(
			Integer sellCustomer);
	
	public HashSet<CustomerDTO> getAllBuyCustomersForSellCustomerAndSupplier(
			Integer buyCustomer,Integer supplierId);
	
	public HashSet<CustomerDTO> getAllBuyCustomersForSupplier(
			Integer supplierId);
	
	public HashSet<CustomerDTO> getAllSellCustomersForSupplier(
			Integer supplierId);

	public HashSet<Supplier> getAllSuppliersForSellCustomer(Integer buyCustomer);

	public List<CustomerDTO> getAllSellCustomers();

	public List<Material> getMaterialDetailsByCustomerSite(Integer customerSite);

	public HashSet<Destination> getAllDestination(String storeName);

	public List<Integer> getCustomerNameandId(Integer customerSiteId);

	public List<CustomerSitesDTO> getCustomerSiteFromCustomer(Integer customerId);

	public List<CustomerSitesDTO> getCustomerSiteFromCustomerAndSupplier(
			GetCustomerSiteDTO getCustomerSiteDTO);

	public HashSet<Supplier> getAllSuppliersFromBuyAndSellCustomer(
			Integer buyCustomerId, Integer sellCustomerId);
	
	public List<String>  getAllEnabledModulesForAppSupport();

}

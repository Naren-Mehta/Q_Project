package com.wm.brta.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wm.brta.dao.BalePickUpAssignmentDAO;
import com.wm.brta.dao.MasterDataDao;
import com.wm.brta.domain.BaleRouteCustomerMapping;
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
import com.wm.brta.service.CustomerService;
import com.wm.brta.service.MasterService;
import com.wm.brta.util.BaleUtils;

@Component("customerService")
@Transactional
public class CustomerServiceImpl implements MasterService, CustomerService {
	@Autowired
	MasterDataDao masterDao;

	@Autowired
	BalePickUpAssignmentDAO balePickUpAssignmentDAO;

	@Override
	public HashSet<BaleRouteCustomerMapping> getAll() throws Exception {
		return null;

	}

	@Override
	public List<Customer> getAllBuyCustomers() {
		List<Customer> buyCustomerList = masterDao.getAllBuyCustomers();
		return buyCustomerList;
	}

	// @Override
	// public Customer getAllBuyCustomerByName(String buyCustomerName) {
	// Customer buyCustomer =
	// masterDao.getAllBuyCustomerByName(buyCustomerName);
	// return buyCustomer;
	// }

	@Override
	public List<Integer> getAllBuyCustomerIds() {
		List<Integer> buyCustomerIdList = masterDao.getAllBuyCustomerIds();
		return buyCustomerIdList;
	}

	@Override
	public List<StoreListView> getCustomerSites(StoreInputDTO storeInputDTO) {
		List<StoreListView> storeListViews = new ArrayList<StoreListView>();
		storeListViews = masterDao
				.getAllCustomerSitesForStoreView(storeInputDTO);

		for (StoreListView store : storeListViews) {
			String completeAddress = "";
			completeAddress = completeAddress
					+ (store.getHouseNumber() != null ? store.getHouseNumber()
							: "");

			completeAddress = completeAddress
					+ (store.getAddress1() != null ? (" " + store.getAddress1())
							: "");

			completeAddress = completeAddress
					+ (store.getAddress2() != null ? (" " + store.getAddress2())
							: "");
			completeAddress = completeAddress
					+ (store.getAddress3() != null ? (" " + store.getAddress3())
							: "");
			completeAddress = completeAddress
					+ (store.getAddress4() != null ? (" " + store.getAddress4())
							: "");

			completeAddress = completeAddress
					+ (store.getPostCode() != null ? (" " + store.getPostCode())
							: "");
			completeAddress = completeAddress
					+ (store.getAddress5() != null ? (" " + store.getAddress5())
							: "");

			store.setAddress(completeAddress);

			//
			// String startDate = "12-01-2018";
			// String endDate = "12-09-2018";
			//
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

				String dtoStartDateStr = BaleUtils
						.convertDateFormat(storeInputDTO.getStartDate());
				String dtoEndDateStr = BaleUtils
						.convertDateFormat(storeInputDTO.getEndDate());
				String storeStartDateStr = store.getBalePickupStartDate() != null ? BaleUtils
						.convertDateFormat(store.getBalePickupStartDate())
						: null;
				String storeEndDateStr = store.getBalePickupEndDate() != null ? BaleUtils
						.convertDateFormat(store.getBalePickupEndDate()) : null;

				if (store.getSellCustomerAvailable() != null
						&& store.getSellCustomerAvailable().equals("Y")
						&& store.getSupplierAvailable() != null
						&& store.getSupplierAvailable().equals("Y")
						&& store.getMaterialAvailable() != null
						&& store.getMaterialAvailable().equals("Y")
						&& (storeStartDateStr != null
								&& (sdf.parse(dtoEndDateStr).after(
										sdf.parse(storeStartDateStr)) || sdf
										.parse(dtoEndDateStr).equals(
												sdf.parse(storeStartDateStr))) && (storeEndDateStr == null || (sdf
								.parse(storeEndDateStr).after(
										sdf.parse(dtoStartDateStr)) || sdf
								.parse(storeEndDateStr).equals(
										sdf.parse(dtoStartDateStr)))))) {

					store.setStatus("Complete");
				} else {
					store.setStatus("Incomplete");
				}
			} catch (Exception e) {

			}

		}

		return storeListViews;
	}

	@Override
	public List<Integer> getCustomerSitesFromCustomerSiteId(
			Integer customerSiteId) {

		List<Integer> sellCustomerSiteId = masterDao
				.getCustomerSitesFromCustomerSite(customerSiteId);

		return sellCustomerSiteId;
	}

	// by deepak
	@Override
	public List<Integer> getCustomerNameandId(Integer customerSiteId) {

		List<Integer> customersId = masterDao.getCustomerIds(customerSiteId);
		return customersId;
	}

	@Override
	public List<CustomerSite> getCustomerSitesForCustomer(Customer customer) {
		Set<CustomerSite> customerSites = masterDao
				.getAllCustomerSitesForBuyCustomerWithLessInfo(customer, null);
		List<CustomerSite> customerSiteList = new ArrayList<CustomerSite>(
				customerSites);

		return customerSiteList;
	}

	@Override
	public HashSet<Material> getAllMaterialsByCustomer(Integer customerId) {
		List<Material> materialList = masterDao
				.getAllMaterialMappingsByCustomer(customerId);

		HashSet<Material> materials = new HashSet<Material>(materialList);
		return materials;
	}

	@Override
	public List<Material> getAllMaterials() {
		List<Material> materials = masterDao.getAllMaterials();
		return materials;
	}

	@Override
	public List<Material> getMaterialDetailsByCustomerSite(
			Integer customerSiteId) {

		List<Material> materials = new ArrayList<Material>();
		materials = masterDao.getMaterialDetails(customerSiteId);

		return materials;
	}

	/*
	 * @Override public List<Integer> getMaterialForCustomerSite(Integer
	 * customerSiteId) { List<Integer> materialsId = null; materialsId =
	 * masterDao .getMaterialForCustomerSite(customerSiteId);
	 * 
	 * return materialsId; }
	 */

	@Override
	public Map<Integer, Integer> getMaterialForCustomerSite(
			Integer customerSiteId) {
		Map<Integer, Integer> materialsId = null;
		
		materialsId = masterDao.getMaterialForCustomerSite(customerSiteId);
		
		return materialsId;
	}

	@Override
	public List<String>  getAllEnabledModulesForAppSupport() {
		List<String>  enabledCustomers = null;
		
		enabledCustomers = masterDao.getEnabledModules();
		
		return enabledCustomers;
	}
	
	
	@Override
	public HashSet<CustomerDTO> getAllSellCustomersForBuyCustomer(
			Integer buyCustomerId) {

		HashSet<CustomerDTO> sellCustomers = masterDao
				.getAllSellCustomersForBuyCustomer(buyCustomerId);

		// List<Customer> buyCustomerList= masterDao.getAllBuyCustomers();
		// HashSet<Customer> buyCustomers = new
		// HashSet<Customer>(buyCustomerList);
		return sellCustomers;
	}

	@Override
	public HashSet<CustomerDTO> getAllSellCustomersForBuyCustomerAndSupplier(
			Integer buyCustomerId, Integer supplierId) {

		HashSet<CustomerDTO> sellCustomers = masterDao
				.getAllSellCustomersForBuyCustomerAndSupplier(buyCustomerId,
						supplierId);

		// List<Customer> buyCustomerList= masterDao.getAllBuyCustomers();
		// HashSet<Customer> buyCustomers = new
		// HashSet<Customer>(buyCustomerList);
		return sellCustomers;
	}

	@Override
	public HashSet<Supplier> getAllSuppliersForBuyCustomer(Integer buyCustomerId) {

		HashSet<Supplier> suppliers = masterDao
				.getAllSuppliersForBuyCustomer(buyCustomerId);

		// List<Customer> buyCustomerList= masterDao.getAllBuyCustomers();
		// HashSet<Customer> buyCustomers = new
		// HashSet<Customer>(buyCustomerList);
		return suppliers;
	}

	@Override
	public HashSet<CustomerDTO> getAllBuyCustomersForSellCustomer(
			Integer sellCustomerId) {

		HashSet<CustomerDTO> sellCustomers = masterDao
				.getAllBuyCustomersForSellCustomer(sellCustomerId);

		// List<Customer> buyCustomerList= masterDao.getAllBuyCustomers();
		// HashSet<Customer> buyCustomers = new
		// HashSet<Customer>(buyCustomerList);
		return sellCustomers;
	}

	@Override
	public HashSet<CustomerDTO> getAllBuyCustomersForSellCustomerAndSupplier(
			Integer sellCustomerId, Integer supplierId) {

		HashSet<CustomerDTO> sellCustomers = masterDao
				.getAllBuyCustomersForSellCustomerAndSupplier(sellCustomerId,
						supplierId);

		// List<Customer> buyCustomerList= masterDao.getAllBuyCustomers();
		// HashSet<Customer> buyCustomers = new
		// HashSet<Customer>(buyCustomerList);
		return sellCustomers;
	}

	@Override
	public HashSet<CustomerDTO> getAllBuyCustomersForSupplier(Integer supplierId) {

		HashSet<CustomerDTO> sellCustomers = masterDao
				.getAllBuyCustomersForSupplier(supplierId);

		// List<Customer> buyCustomerList= masterDao.getAllBuyCustomers();
		// HashSet<Customer> buyCustomers = new
		// HashSet<Customer>(buyCustomerList);
		return sellCustomers;
	}

	@Override
	public HashSet<Supplier> getAllSuppliersFromBuyAndSellCustomer(
			Integer buyCustomerId, Integer sellCustomerId) {

		HashSet<Supplier> suppliers = masterDao
				.getAllSuppliersFromBuyAndSellCustomer(buyCustomerId,
						sellCustomerId);

		// List<Customer> buyCustomerList= masterDao.getAllBuyCustomers();
		// HashSet<Customer> buyCustomers = new
		// HashSet<Customer>(buyCustomerList);
		return suppliers;
	}

	@Override
	public HashSet<CustomerDTO> getAllSellCustomersForSupplier(
			Integer supplierId) {

		HashSet<CustomerDTO> sellCustomers = masterDao
				.getAllSellCustomersForSupplier(supplierId);

		// List<Customer> buyCustomerList= masterDao.getAllBuyCustomers();
		// HashSet<Customer> buyCustomers = new
		// HashSet<Customer>(buyCustomerList);
		return sellCustomers;
	}

	@Override
	public HashSet<Supplier> getAllSuppliersForSellCustomer(
			Integer sellCustomerId) {

		HashSet<Supplier> suppliers = masterDao
				.getAllSuppliersForSellCustomer(sellCustomerId);
		return suppliers;
	}

	@Override
	public List<CustomerSitesDTO> getCustomerSiteFromCustomer(Integer customerId) {

		List<CustomerSitesDTO> mappedCustomers = masterDao
				.getCustomerSites(customerId);

		return mappedCustomers;
	}

	@Override
	public List<CustomerSitesDTO> getCustomerSiteFromCustomerAndSupplier(
			GetCustomerSiteDTO getCustomerSiteDTO) {

		List<CustomerSitesDTO> mappedCustomers = masterDao
				.getCustomerSitesFromCustomerAndSupplier(getCustomerSiteDTO);

		return mappedCustomers;
	}

	@Override
	public List<CustomerDTO> getAllSellCustomers() {
		List<CustomerDTO> sellCustomers = masterDao.getAllSellCustomers();

		return sellCustomers;
	}

	@Override
	public HashSet<Destination> getAllDestination(String storeName) {

		HashSet<Destination> destinations = new HashSet<Destination>();

		destinations = balePickUpAssignmentDAO.getDestination(storeName);
		return destinations;
	}

}

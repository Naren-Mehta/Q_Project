package com.wm.brta.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wm.brta.domain.BalePickupCustomerSiteConfiguration;
import com.wm.brta.domain.BalePickupMaterialConfiguration;
import com.wm.brta.domain.BalePickupSupplierConfiguration;
import com.wm.brta.domain.BalePickupSupplierSiteConfiguration;
import com.wm.brta.domain.BaleRouteCustomerMapping;
import com.wm.brta.domain.Customer;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.Material;
import com.wm.brta.domain.StoreListView;
import com.wm.brta.domain.StoreSupplierListView;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.SupplierSite;
import com.wm.brta.domain.User;
import com.wm.brta.dto.CustomerDTO;
import com.wm.brta.dto.CustomerSitesDTO;
import com.wm.brta.dto.GetCustomerSiteDTO;
import com.wm.brta.dto.StoreInputDTO;

@Repository
public class MasterDataDao {

	@Autowired
	protected SessionFactory sessionFactory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(MasterDataDao.class);

	public HashSet<Supplier> getAllSuppliers() {
		Session session = sessionFactory.getCurrentSession();
		List<Supplier> suppliers = null;
		HashSet<Supplier> hashedSuppliers = null;
		try {
		Query query = session.createQuery("select new Supplier(supplier.supplierId , supplier.description) from Supplier supplier where supplier.isdeleted=false");
		
			suppliers = query.list();
			hashedSuppliers = new HashSet<Supplier>(suppliers);
		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllSuppliers=====>" + e);
		}
		return hashedSuppliers;
	}
	
	
	public HashSet<Supplier> getAllSuppliersFromSellCustomer(Integer sellCustomerId) {
		Session session = sessionFactory.getCurrentSession();
		List<Supplier> supplierList = null;
		HashSet<Supplier> hashedSupplierList = null;

		try {
		Query query = session.createQuery("select distinct new Supplier(supplier.supplierId, supplier.description) from BaleRouteCustomerMapping  "
		+ " where sellCustomer.customerId =:sellCustomerId and supplier.isdeleted=false");
		
		
		
		query.setParameter("sellCustomerId", sellCustomerId);
	
			supplierList = query.list();
			hashedSupplierList = new HashSet<Supplier>(supplierList);
		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao: getAllSuppliersFromSellCustomer=====>"
					+ e);
		}
		return hashedSupplierList;
		
	}
	
	
	public List<BalePickupSupplierConfiguration> getSupplierConfigurationFromSupplierAndCustomerSite(Integer supplierId, Integer customerSiteId) {
		
		Session session = sessionFactory.getCurrentSession();
		List<BalePickupSupplierConfiguration> supplierConfigList = new ArrayList<BalePickupSupplierConfiguration>();
		Supplier supplier = null;
		try {
		Query query = session
				.createQuery("from BalePickupSupplierConfiguration where customerSite.customerSiteId =:customerSiteId and supplier.supplierId=:supplierId and supplier.isdeleted=false");
		
		
			query.setParameter("supplierId", supplierId);
			query.setParameter("customerSiteId", customerSiteId);
			supplierConfigList = query.list();
			
		} catch (RuntimeException e) {
			LOGGER.error("Error:getSupplierConfigurationFromSupplierAndCustomerSite=====>" + e);
		}
		return supplierConfigList;
	}
	
	
	public Boolean getSupplierAndCustomerSiteRelatedOrNot(Integer supplierId, Integer customerSiteId) {
		
		Boolean supplierAndCustomerSiteRelated=false;
		Session session = sessionFactory.getCurrentSession();
		try {
		Query query = session
				.createQuery("select new BalePickupSupplierConfiguration(supplierConfig.balePickupSupplierConfigurationId) "
						+ " from BalePickupSupplierConfiguration supplierConfig where supplierConfig.customerSite.customerSiteId =:customerSiteId "
						+ " and supplierConfig.supplier.supplierId=:supplierId");
		List<BalePickupSupplierConfiguration> supplierConfigList = new ArrayList<BalePickupSupplierConfiguration>();
		
			query.setParameter("supplierId", supplierId);
			query.setParameter("customerSiteId", customerSiteId);
			supplierConfigList = query.list();
			if(supplierConfigList!=null && supplierConfigList.size() >0){
				supplierAndCustomerSiteRelated=true;
			}
		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao: getSupplierAndCustomerSiteRelatedOrNot=====>" + e);
		}
		return supplierAndCustomerSiteRelated;
	}
	
	
	

	public Supplier getSupplierFromSupplierId(Integer supplierId) {
		
		Session session = sessionFactory.getCurrentSession();
		List<Supplier> suppliers = new ArrayList<Supplier>();
		Supplier supplier = null;
		try {
		Query query = session
				.createQuery("select new Supplier(sup.supplierId,sup.description) from Supplier sup where sup.supplierId=:supplierId");
		
			query.setParameter("supplierId", supplierId);
			suppliers = query.list();
			if (suppliers != null && suppliers.size() > 0) {
				supplier = suppliers.get(0);
			}
		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getSupplierFromSupplierId=====>" + e);
		}
		return supplier;
	}
	
	public Supplier getSupplierFromSupplierName(String supplierName) {
		
		Session session = sessionFactory.getCurrentSession();
		List<Supplier> suppliers = null;
		Supplier supplier = null;
		try {
		Query query = session
				.createQuery("select new Supplier(sup.supplierId,sup.description) from Supplier sup where sup.description=:supplierName");
		
			 suppliers = new ArrayList<Supplier>();
			query.setParameter("supplierName", supplierName);
			suppliers = query.list();
			if (suppliers != null && suppliers.size() > 0) {
				supplier = suppliers.get(0);
			}
		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getSupplierFromSupplierName=====>" + e);
		}
		return supplier;
	}

	// modified by deepak
	public List<Integer> getSuppliersFromCustomerSite(Integer customerSiteId) {
		Session session = sessionFactory.getCurrentSession();
		List<Integer> supplierIdList = null;
		try {
		Query query = session
				.createQuery("select supplier.supplierId from BalePickupSupplierConfiguration where customerSite.customerSiteId =:customerSiteId and enabled= true");
		
			query.setParameter("customerSiteId", customerSiteId);
			supplierIdList = query.list();

		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getSuppliersFromCustomerSite=====>"
					+ e);
		}
	
		return supplierIdList;
	}

	public List<Supplier> getSuppliersFromCustomerSiteId(Integer customerSiteId) {
		Session session = sessionFactory.getCurrentSession();
		List<Supplier> suppliers = new ArrayList<Supplier>();
		try {
		Query query = session
				.createQuery("select new Supplier(supplierConfig.supplier.supplierId , supplierConfig.supplier.description) "
						+ " from BalePickupSupplierConfiguration supplierConfig where "
						+ "supplierConfig.customerSite.customerSiteId =:customerSiteId and supplierConfig.enabled= true");
		
			query.setParameter("customerSiteId", customerSiteId);
			suppliers = query.list();
			
		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getSuppliersFromCustomerSite=====>"+ e);
		}
		return suppliers;
	}

	public List<User> getUserFromUserName(String firstName,String lastName) {

		Session session = sessionFactory.getCurrentSession();
List<User> users =null;
		
		try {
		Query query = session.createQuery("select new User(usr.userId, usr.firstName, usr.lastName, usr.emailId, usr.mobilePhone,usr.enabled,"
						+ " usr.createDate,usr.updatedBy,usr.updatedDate,usr.supplier.supplierId, usr.supplier.description) from User usr where firstName =:firstName and lastName =:lastName and enabled=true");
		
			users = new ArrayList<User>();
			query.setParameter("firstName", firstName);
			query.setParameter("lastName", lastName);
			users = query.list();
			
			} catch (RuntimeException e) {
			LOGGER.error("====e===" + e);

		}
	
		return users;
	}

	

	public List<CustomerDTO> getAllSellCustomers() {
		Session session = sessionFactory.getCurrentSession();
		List<CustomerDTO> sellCustomers = new ArrayList<CustomerDTO>();

		try {
		Query query = session
				.createQuery("select DISTINCT new Customer(sellCustomer.customerId,sellCustomer.customerName,"
						+ "sellCustomer.legacyVendorId,sellCustomer.customerStateId,sellCustomer.createdDate,"
						+ "sellCustomer.updatedAt,sellCustomer.updatedBy) from BaleRouteCustomerMapping");
		
			
			sellCustomers = query.list();
			
			
		} catch (RuntimeException e) {
		
			LOGGER.error("Error:MasterDataDao:getAllSellCustomers =====>" + e);
		}
		return sellCustomers;
	}

	public List<Customer> getAllBuyCustomers() {
		Session session = sessionFactory.getCurrentSession();
		List<Customer> sellCustomers = new ArrayList<Customer>();

		try {
		Query query = session
				.createQuery("select distinct new Customer(customerMapping.buyCustomer.customerId,customerMapping.buyCustomer.customerName, "
						+ "customerMapping.buyCustomer.legacyVendorId, customerMapping.buyCustomer.customerStateId,customerMapping.buyCustomer.createdDate,"
						+ "customerMapping.buyCustomer.updatedAt,customerMapping.buyCustomer.updatedBy) "
						+ " from BaleRouteCustomerMapping customerMapping");
		
			sellCustomers = query.list();
		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllBuyCustomers=====>" + e);
		}
		return sellCustomers;
	}
	
	
	
	public Customer getAllBuyCustomerByName(String customerName) {
		Session session = sessionFactory.getCurrentSession();
		Customer buyCustomer=null;
		try {
		Query query = session
				.createQuery("select new Customer(cust.customerId,cust.customerName) from Customer cust where cust.customerName =:customerName");
		List<Customer> buyCustomers = new ArrayList<Customer>();
		
		
			query.setParameter("customerName",customerName);
			buyCustomers = query.list();
			
			if(buyCustomers!=null && buyCustomers.size()>0){
				buyCustomer=buyCustomers.get(0);
			}
		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllBuyCustomers=====>" + e);
		}
		return buyCustomer;
	}

	public List<Integer> getAllBuyCustomerIds() {

		Session session = sessionFactory.getCurrentSession();
			List<Integer> buyCustomerIds = new ArrayList<Integer>();
		
		try {	
		Query query = session
				.createQuery("select customerMapping.buyCustomer.customerId from BaleRouteCustomerMapping customerMapping");
		
			buyCustomerIds = query.list();
		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllBuyCustomers=====>" + e);
		}
		return buyCustomerIds;
	}

	public HashSet<CustomerDTO> getAllSellCustomersForBuyCustomer(
			Integer buyCustomerId) {
		Session session = sessionFactory.getCurrentSession();
		List<CustomerDTO> sellCustomers = new ArrayList<CustomerDTO>();
		HashSet<CustomerDTO> mappedCustomers = new HashSet<CustomerDTO>();
		try {

		Query query = session
				.createQuery("select distinct new Customer(sellCustomer.customerId,sellCustomer.customerName,"
						+ "sellCustomer.legacyVendorId,sellCustomer.customerStateId,sellCustomer.createdDate,"
						+ "sellCustomer.updatedAt,sellCustomer.updatedBy) from BaleRouteCustomerMapping where buycustomerid =:buyCustomerId");

		query.setParameter("buyCustomerId", buyCustomerId);
		
			sellCustomers = query.list();
			mappedCustomers = new HashSet<CustomerDTO>(sellCustomers);

		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllCustomers  11=====>" + e);
		}
		return mappedCustomers;
	}
	
	public HashSet<CustomerDTO> getAllSellCustomersForBuyCustomerAndSupplier(
			Integer buyCustomerId,Integer supplierId) {
		Session session = sessionFactory.getCurrentSession();
		List<CustomerDTO> sellCustomers = new ArrayList<CustomerDTO>();
		HashSet<CustomerDTO> mappedCustomers = new HashSet<CustomerDTO>();
		try {
		Query query = session
				.createQuery("select distinct new Customer(sellCustomer.customerId,sellCustomer.customerName,"
						+ "sellCustomer.legacyVendorId,sellCustomer.customerStateId,sellCustomer.createdDate,"
						+ "sellCustomer.updatedAt,sellCustomer.updatedBy) from BaleRouteCustomerMapping where buycustomerid =:buyCustomerId "
						+ " and supplier.supplierId=:supplierId");

		query.setParameter("buyCustomerId", buyCustomerId);
		query.setParameter("supplierId", supplierId);
		
			sellCustomers = query.list();
			mappedCustomers = new HashSet<CustomerDTO>(sellCustomers);

		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllCustomers  11=====>" + e);
		}
		return mappedCustomers;
	}
	
	public HashSet<CustomerDTO> getAllBuyCustomersForSellCustomer(
			Integer sellCustomerId) {
		Session session = sessionFactory.getCurrentSession();
		List<CustomerDTO> sellCustomers = new ArrayList<CustomerDTO>();
		HashSet<CustomerDTO> mappedCustomers = new HashSet<CustomerDTO>();
		try {

		Query query = session
				.createQuery("select distinct new Customer(buyCustomer.customerId,buyCustomer.customerName,"
						+ "buyCustomer.legacyVendorId,buyCustomer.customerStateId,buyCustomer.createdDate,"
						+ "buyCustomer.updatedAt,buyCustomer.updatedBy) from BaleRouteCustomerMapping where sellcustomerid =:sellCustomerId");

		query.setParameter("sellCustomerId", sellCustomerId);
		
			sellCustomers = query.list();
			mappedCustomers = new HashSet<CustomerDTO>(sellCustomers);

		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllCustomers  11=====>" + e);
		}
		return mappedCustomers;
	}
	
	public HashSet<CustomerDTO> getAllBuyCustomersForSellCustomerAndSupplier(
			Integer sellCustomerId, Integer supplierId) {
		Session session = sessionFactory.getCurrentSession();
		List<CustomerDTO> sellCustomers = new ArrayList<CustomerDTO>();
		HashSet<CustomerDTO> mappedCustomers = new HashSet<CustomerDTO>();
		try {
		Query query = session
				.createQuery("select distinct new Customer(buyCustomer.customerId,buyCustomer.customerName,"
						+ "buyCustomer.legacyVendorId,buyCustomer.customerStateId,buyCustomer.createdDate,"
						+ "buyCustomer.updatedAt,buyCustomer.updatedBy) from BaleRouteCustomerMapping where sellcustomerid =:sellCustomerId "
						+ " and supplier.supplierId =:supplierId");

		query.setParameter("sellCustomerId", sellCustomerId);
		query.setParameter("supplierId", supplierId);
		
			sellCustomers = query.list();
			mappedCustomers = new HashSet<CustomerDTO>(sellCustomers);

		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllCustomers  11=====>" + e);
		}
		return mappedCustomers;
	}
	
	////
	
	public HashSet<CustomerDTO> getAllBuyCustomersForSupplier(
			Integer supplierId) {
		Session session = sessionFactory.getCurrentSession();
		List<CustomerDTO> sellCustomers = new ArrayList<CustomerDTO>();
		HashSet<CustomerDTO> mappedCustomers = new HashSet<CustomerDTO>();
		try {

		Query query = session
				.createQuery("select distinct new Customer(buyCustomer.customerId,buyCustomer.customerName,"
						+ "buyCustomer.legacyVendorId,buyCustomer.customerStateId,buyCustomer.createdDate,"
						+ "buyCustomer.updatedAt,buyCustomer.updatedBy) from BaleRouteCustomerMapping where supplier.supplierId =:supplierId");

		query.setParameter("supplierId", supplierId);
		
			sellCustomers = query.list();
			mappedCustomers = new HashSet<CustomerDTO>(sellCustomers);

		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllCustomers  11=====>" + e);
		}
		return mappedCustomers;
	}
	
	public HashSet<Supplier> getAllSuppliersFromBuyAndSellCustomer(
			Integer buyCustomerId,Integer sellCustomerId) {
		
		Session session = sessionFactory.getCurrentSession();
		List<Supplier> supplierList = new ArrayList<Supplier>();
		HashSet<Supplier> supplier = new HashSet<Supplier>();
		
		try {	
		Query query = session
				.createQuery("select distinct new Supplier(mapping.supplier.supplierId, mapping.supplier.description) from BaleRouteCustomerMapping mapping "
						+ " where mapping.buyCustomer.customerId =:buyCustomerId and"
						+ " mapping.sellCustomer.customerId =:sellCustomerId and mapping.supplier.isdeleted=false");

		query.setParameter("buyCustomerId", buyCustomerId);
		query.setParameter("sellCustomerId", sellCustomerId);
		
			supplierList = query.list();
			supplier = new HashSet<Supplier>(supplierList);

		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllCustomers  11=====>" + e);
		}
		
		return supplier;
	}
	
	
	public HashSet<CustomerDTO> getAllSellCustomersForSupplier(
			Integer supplierId) {
		Session session = sessionFactory.getCurrentSession();

		Query query = session
				.createQuery("select distinct new Customer(sellCustomer.customerId,sellCustomer.customerName,"
						+ "sellCustomer.legacyVendorId,sellCustomer.customerStateId,sellCustomer.createdDate,"
						+ "sellCustomer.updatedAt,sellCustomer.updatedBy) from BaleRouteCustomerMapping where supplier.supplierId =:supplierId");

		query.setParameter("supplierId", supplierId);
		List<CustomerDTO> sellCustomers = new ArrayList<CustomerDTO>();
		HashSet<CustomerDTO> mappedCustomers = new HashSet<CustomerDTO>();
		try {
			sellCustomers = query.list();
			mappedCustomers = new HashSet<CustomerDTO>(sellCustomers);

		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllCustomers  11=====>" + e);
		}
		return mappedCustomers;
	}
	
	
	
	
	
	public HashSet<Supplier> getAllSuppliersForBuyCustomer(Integer buyCustomerId) {
		
		Session session = sessionFactory.getCurrentSession();
		List<Supplier> suppliers = new ArrayList<Supplier>();
		HashSet<Supplier> supplierSet = new HashSet<Supplier>();
		try {
		Query query = session
				.createQuery("select distinct new Supplier(mapping.supplier.supplierId, mapping.supplier.description) from BaleRouteCustomerMapping mapping "
						+ " where mapping.buyCustomer.customerId =:buyCustomerId and mapping.supplier.isdeleted=false");

		query.setParameter("buyCustomerId", buyCustomerId);
		
		
			suppliers = query.list();
			supplierSet = new HashSet<Supplier>(suppliers);
		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllCustomers  11=====>" + e);
		}
		return supplierSet;
	}
	
	
	public HashSet<Supplier> getAllSuppliersForSellCustomer(
			Integer sellCustomerId) {
		
		Session session = sessionFactory.getCurrentSession();

		List<Supplier> suppliers = new ArrayList<Supplier>();
		HashSet<Supplier> supplierSet = new HashSet<Supplier>();
		try {
		
		Query query = session
				.createQuery("select distinct new Supplier(mapping.supplier.supplierId, mapping.supplier.description) from BaleRouteCustomerMapping mapping "
						+ " where mapping.sellCustomer.customerId =:sellCustomerId and mapping.supplier.isdeleted=false");

		query.setParameter("sellCustomerId", sellCustomerId);
		
			suppliers = query.list();
		
			supplierSet = new HashSet<Supplier>(suppliers);

		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllCustomers  11=====>" + e);
		}
		return supplierSet;
	}
	
	public List<Customer> getAllSellCustomersForBuyCustomerExcel(
			Customer buyCustomer) {
		Session session = sessionFactory.getCurrentSession();

		List<Customer> sellCustomers = new ArrayList<Customer>();
		
		try {
		Query query = session
				.createQuery("select new Customer(sellCustomer.customerId,sellCustomer.customerName,"
						+ "sellCustomer.legacyVendorId,sellCustomer.customerStateId,sellCustomer.createdDate,"
						+ "sellCustomer.updatedAt,sellCustomer.updatedBy) from BaleRouteCustomerMapping where buycustomerid =:buyCustomerId");

		query.setParameter("buyCustomerId", buyCustomer.getCustomerId());
		
			sellCustomers = query.list();
			} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllCustomers  11=====>" + e);
		}
		return sellCustomers;
	}

	// by deeepak for cusomer site

	public List<CustomerSitesDTO> getCustomerSites(Integer customerId) {
	
		Session session = sessionFactory.getCurrentSession();
		List<CustomerSitesDTO> customersiteDaoList = new ArrayList<CustomerSitesDTO>();

		List<StoreListView> customersites = new ArrayList<StoreListView>();
		try {
		Query query = null;
		String queryStr = null;
		queryStr="select new StoreListView(customerSiteId , siteName) from StoreListView where customerId =:customerId and"
				+ " sellCustomerAvailable ='Y' and supplierAvailable ='Y' and materialAvailable='Y' ";
//		queryStr = "select new CustomerSite(customerSiteId , siteName) from CustomerSite where customerId =:customerId";
		query = session.createQuery(queryStr);
		query.setParameter("customerId", customerId);
		

			// customersites= query.list();
			customersites = query.list();
			for (StoreListView customerSite : customersites) {
				CustomerSitesDTO customerSitesDTO = new CustomerSitesDTO();
				customerSitesDTO.setCustomerSiteId(customerSite
						.getCustomerSiteId());
				customerSitesDTO.setSiteName(customerSite.getSiteName());
				customersiteDaoList.add(customerSitesDTO);
			}

		} catch (Exception e) {
			LOGGER.error("Error:MasterDataDao:getCustomerSites =====>   " + e);
		}
		
		return customersiteDaoList;

	}
	
	
	public List<CustomerSite> getCustomerSitesFromID(Integer customerId) {
		
		Session session = sessionFactory.getCurrentSession();
		List<CustomerSite> customersiteDaoList = new ArrayList<CustomerSite>();

		try {
		Query query = null;
		String queryStr = null;
		queryStr = "select new CustomerSite(customerSiteId , siteName) from CustomerSite where customerId =:customerId";
		query = session.createQuery(queryStr);
		query.setParameter("customerId", customerId);
			
			customersiteDaoList = query.list();
			

		} catch (Exception e) {
			LOGGER.error("Error:MasterDataDao: getCustomerSitesFromID=====>   " + e);
		}
	
		return customersiteDaoList;

	}
	


	public List<CustomerSitesDTO> getCustomerSitesFromCustomerAndSupplier(
			GetCustomerSiteDTO getCustomerSiteDTO) {
		
		Session session = sessionFactory.getCurrentSession();
		List<CustomerSitesDTO> customersiteDaoList = new ArrayList<CustomerSitesDTO>();

		List<CustomerSite> customersites = new ArrayList<CustomerSite>();

		Query query = null;
		String queryStr = null;
		try {
		queryStr = "select new CustomerSite(customerSite.customerSiteId , customerSite.siteName) from BalePickupSupplierConfiguration "
				+ " where supplier.supplierId =:supplierId and customerSite.customer.customerId=:customerId and enabled=true";
		query = session.createQuery(queryStr);
		query.setParameter("customerId", getCustomerSiteDTO.getCustomerId());
		query.setParameter("supplierId", getCustomerSiteDTO.getSupplierId());

		

			// customersites= query.list();
			
			customersites = query.list();
			for (CustomerSite customerSite : customersites) {
				CustomerSitesDTO customerSitesDTO = new CustomerSitesDTO();
				customerSitesDTO.setCustomerSiteId(customerSite
						.getCustomerSiteId());
				customerSitesDTO.setSiteName(customerSite.getSiteName());
				customersiteDaoList.add(customerSitesDTO);
			}

		} catch (Exception e) {
			LOGGER.error("Error:MasterDataDao: getCustomerSitesFromCustomerAndSupplier=====>   " + e);
		}
		
		return customersiteDaoList;

	}

	// by deepak for store view list

	public List<StoreSupplierListView> getStoreSupplierViewList(
			AssignDriverDTO assignDriverDTO) {
		Session session = sessionFactory.getCurrentSession();
		List<StoreSupplierListView> storesupplierlist = null;

		Query query = null;
		String queryStr = null;

		try {
			queryStr = "from StoreSupplierListView";

			if (assignDriverDTO.getSupplierId() != null) {
				if (assignDriverDTO.getCustomerId() != null) {
					if (assignDriverDTO.getState() != null) {
						if (assignDriverDTO.getStartDate() != null) {
							if (assignDriverDTO.getEndDate() != null) {
								queryStr = "from StoreSupplierListView where supplierId=:supplierId and buyCustomerId=:customerId "
										+ " and buyCustomerAddress4=:state and balePickupStartDate <=:endDate and (balePickupEndDate is null or balePickupEndDate >=:startDate)";

							} else {
								queryStr = "from StoreSupplierListView where supplierId=:supplierId and buyCustomerId=:customerId "
										+ " and buyCustomerAddress4=:state and (balePickupEndDate is null or balePickupEndDate >=:startDate)";
							}
						} else {
							if (assignDriverDTO.getEndDate() != null) {
								queryStr = "from StoreSupplierListView where supplierId=:supplierId and buyCustomerId=:customerId "
										+ " and buyCustomerAddress4=:state and balePickupStartDate <=:endDate";

							} else {
								queryStr = "from StoreSupplierListView where supplierId=:supplierId and buyCustomerId=:customerId "
										+ " and buyCustomerAddress4=:state ";
							}
						}
					} else {
						if (assignDriverDTO.getStartDate() != null) {
							if (assignDriverDTO.getEndDate() != null) {
								queryStr = "from StoreSupplierListView where supplierId=:supplierId and buyCustomerId=:customerId "
										+ "  and balePickupStartDate <=:endDate and (balePickupEndDate is null or balePickupEndDate >=:startDate)";

							} else {
								queryStr = "from StoreSupplierListView where supplierId=:supplierId and buyCustomerId=:customerId "
										+ "  and (balePickupEndDate is null or balePickupEndDate >=:startDate)";
							}
						} else {
							if (assignDriverDTO.getEndDate() != null) {
								queryStr = "from StoreSupplierListView where supplierId=:supplierId and buyCustomerId=:customerId "
										+ "  and balePickupStartDate <=:endDate";

							} else {
								queryStr = "from StoreSupplierListView where supplierId=:supplierId and buyCustomerId=:customerId ";
							}
						}
					}

				} else {
					if (assignDriverDTO.getState() != null) {
						if (assignDriverDTO.getStartDate() != null) {
							if (assignDriverDTO.getEndDate() != null) {
								queryStr = "from StoreSupplierListView where supplierId=:supplierId"
										+ " and buyCustomerAddress4=:state and balePickupStartDate <=:endDate and (balePickupEndDate is null or balePickupEndDate >=:startDate)";

							} else {
								queryStr = "from StoreSupplierListView where supplierId=:supplierId "
										+ " and buyCustomerAddress4=:state and (balePickupEndDate is null or balePickupEndDate >=:startDate)";
							}
						} else {
							if (assignDriverDTO.getEndDate() != null) {
								queryStr = "from StoreSupplierListView where supplierId=:supplierId "
										+ " and buyCustomerAddress4=:state and balePickupStartDate <=:endDate";

							} else {
								queryStr = "from StoreSupplierListView where supplierId=:supplierId "
										+ " and buyCustomerAddress4=:state ";
							}
						}
					} else {
						if (assignDriverDTO.getStartDate() != null) {
							if (assignDriverDTO.getEndDate() != null) {
								queryStr = "from StoreSupplierListView where supplierId=:supplierId "
										+ "  and balePickupStartDate <=:endDate and (balePickupEndDate is null or balePickupEndDate >=:startDate)";

							} else {
								queryStr = "from StoreSupplierListView where supplierId=:supplierId "
										+ "  and (balePickupEndDate is null or balePickupEndDate >=:startDate)";
							}
						} else {
							if (assignDriverDTO.getEndDate() != null) {
								queryStr = "from StoreSupplierListView where supplierId=:supplierId "
										+ "  and balePickupStartDate <=:endDate";

							} else {
								queryStr = "from StoreSupplierListView where supplierId=:supplierId";
							}
						}
					}
				}
			} else {
				if (assignDriverDTO.getCustomerId() != null) {
					if (assignDriverDTO.getState() != null) {
						if (assignDriverDTO.getStartDate() != null) {
							if (assignDriverDTO.getEndDate() != null) {
								queryStr = "from StoreSupplierListView where  buyCustomerId=:customerId "
										+ " and buyCustomerAddress4=:state and balePickupStartDate <=:endDate and (balePickupEndDate is null or balePickupEndDate >=:startDate)";

							} else {
								queryStr = "from StoreSupplierListView where buyCustomerId=:customerId "
										+ " and buyCustomerAddress4=:state and (balePickupEndDate is null or balePickupEndDate >=:startDate)";
							}
						} else {
							if (assignDriverDTO.getEndDate() != null) {
								queryStr = "from StoreSupplierListView where buyCustomerId=:customerId "
										+ " and buyCustomerAddress4=:state and balePickupStartDate <=:endDate";

							} else {
								queryStr = "from StoreSupplierListView where buyCustomerId=:customerId "
										+ " and buyCustomerAddress4=:state ";
							}
						}
					} else {
						if (assignDriverDTO.getStartDate() != null) {
							if (assignDriverDTO.getEndDate() != null) {
								queryStr = "from StoreSupplierListView where buyCustomerId=:customerId "
										+ "  and balePickupStartDate <=:endDate and (balePickupEndDate is null or balePickupEndDate >=:startDate)";

							} else {
								queryStr = "from StoreSupplierListView where buyCustomerId=:customerId "
										+ "  and (balePickupEndDate is null or balePickupEndDate >=:startDate)";
							}
						} else {
							if (assignDriverDTO.getEndDate() != null) {
								queryStr = "from StoreSupplierListView where buyCustomerId=:customerId "
										+ "  and balePickupStartDate <=:endDate";

							} else {
								queryStr = "from StoreSupplierListView where  buyCustomerId=:customerId ";
							}
						}
					}

				} else {
					if (assignDriverDTO.getState() != null) {
						if (assignDriverDTO.getStartDate() != null) {
							if (assignDriverDTO.getEndDate() != null) {
								queryStr = "from StoreSupplierListView where buyCustomerAddress4=:state "
										+ "and balePickupStartDate <=:endDate and (balePickupEndDate is null or balePickupEndDate >=:startDate)";

							} else {
								queryStr = "from StoreSupplierListView where buyCustomerAddress4=:state and (balePickupEndDate is null or balePickupEndDate >=:startDate)";
							}
						} else {
							if (assignDriverDTO.getEndDate() != null) {
								queryStr = "from StoreSupplierListView where buyCustomerAddress4=:state and balePickupStartDate <=:endDate";

							} else {
								queryStr = "from StoreSupplierListView where buyCustomerAddress4=:state ";
							}
						}
					} else {
						if (assignDriverDTO.getStartDate() != null) {
							if (assignDriverDTO.getEndDate() != null) {
								queryStr = "from StoreSupplierListView where balePickupStartDate <=:endDate "
										+ "and (balePickupEndDate is null or balePickupEndDate >=:startDate)";

							} else {
								queryStr = "from StoreSupplierListView where (balePickupEndDate is null or balePickupEndDate >=:startDate)";
							}
						} else {
							if (assignDriverDTO.getEndDate() != null) {
								queryStr = "from StoreSupplierListView where balePickupStartDate <=:endDate";

							} else {
								queryStr = "from StoreSupplierListView";
							}
						}
					}
				}
			}

			query = session.createQuery(queryStr);

			// if(assignDriverDTO.getEndDate()!=null){
			// query.setParameter("endDate", assignDriverDTO.getEndDate()); }
			// query.setParameter("startDate", assignDriverDTO.getStartDate());
			if (assignDriverDTO.getSupplierId() != null) {
				query.setParameter("supplierId",
						assignDriverDTO.getSupplierId());
			}
			if (assignDriverDTO.getCustomerId() != null) {
				query.setParameter("customerId",
						assignDriverDTO.getCustomerId());
			}

			if (assignDriverDTO.getState() != null) {
				query.setParameter("state", assignDriverDTO.getState());
			}

			if (assignDriverDTO.getStartDate() != null) {
				query.setDate("startDate", assignDriverDTO.getStartDate());
			}

			if (assignDriverDTO.getEndDate() != null) {
				query.setDate("endDate", assignDriverDTO.getEndDate());
			}

			storesupplierlist = query.list();
			
		} catch (Exception e) {
			LOGGER.error("in MasterDataDao StoreSupplierListView method( ): "
							+ e);
		}
		return storesupplierlist;
	}

	public List<StoreSupplierListView> getStoreSupplierViewList(Integer storeId) {
		Session session = sessionFactory.getCurrentSession();
		List<StoreSupplierListView> storesupplierlist = new ArrayList<StoreSupplierListView>();

		Query query = null;
		String queryStr = null;

		try {
			queryStr = "from StoreSupplierListView where buyCustomerSiteId =:storeId";
			query = session.createQuery(queryStr);
			query.setParameter("storeId", storeId);
			storesupplierlist = query.list();

		} catch (Exception e) {
			LOGGER.error("in MasterDataDao getStoreSupplierViewList method( ): "
							+ e);
		}
		return storesupplierlist;
	}

	public List<StoreListView> getAllCustomerSitesForStoreView(
			StoreInputDTO storeInputDTO) {
			
		Customer customer = storeInputDTO.getBuyCustomer();
		String state = storeInputDTO.getState();
		Date startDate = storeInputDTO.getStartDate();
		Date endDate = storeInputDTO.getEndDate();

		Session session = sessionFactory.getCurrentSession();
		List<CustomerSite> customerSites = null;

		Query query = null; // where balePickupStartDate between :startDate and
							// :endDate
		String queryStr = "from StoreListView";
		List<StoreListView> storeListViewList = null;
		try {
		if (customer != null) {
			if (state != null) {
				if (storeInputDTO.getCustomerSiteIdList() != null
						&& storeInputDTO.getCustomerSiteIdList().size() > 0) {
					queryStr = queryStr
							+ " where customerId=:customerId and locationId in (select locationId from Location where address4=:state)"
							+ " and customerSiteId in (:customerSiteIdList) order by siteName";
				} else {
					queryStr = queryStr
							+ " where customerId=:customerId and locationId in (select locationId from Location where address4=:state) order by siteName";
				}

			} else {
				if (storeInputDTO.getCustomerSiteIdList() != null
						&& storeInputDTO.getCustomerSiteIdList().size() > 0) {
					queryStr = queryStr
							+ " where customerId=:customerId and customerSiteId in (:customerSiteIdList) order by siteName";
				} else {
					queryStr = queryStr
							+ " where customerId=:customerId order by siteName";
				}
			}
		} else {
			if (state != null) {
				if (storeInputDTO.getCustomerSiteIdList() != null
						&& storeInputDTO.getCustomerSiteIdList().size() > 0) {
					queryStr = queryStr
							+ " where  locationId in (select locationId from Location where address4=:state)"
							+ " and customerSiteId in (:customerSiteIdList) order by siteName";
				} else {
					queryStr = queryStr
							+ " where  locationId in (select locationId from Location where address4=:state) order by siteName";
				}

			} else {
				if (storeInputDTO.getCustomerSiteIdList() != null
						&& storeInputDTO.getCustomerSiteIdList().size() > 0) {
					queryStr = queryStr
							+ " where customerSiteId in (:customerSiteIdList) order by siteName";
				} else {
					queryStr = queryStr + " order by siteName";
				}
			}
		}

		query = session.createQuery(queryStr);
		/*
		 * query.setParameter("startDate", startDate);
		 * query.setParameter("endDate", endDate);
		 */

		if (customer != null) {
			query.setParameter("customerId", customer.getCustomerId());
		}
		if (state != null) {
			query.setParameter("state", state);
		}

		if (storeInputDTO.getCustomerSiteIdList() != null
				&& storeInputDTO.getCustomerSiteIdList().size() > 0) {
			query.setParameterList("customerSiteIdList",
					storeInputDTO.getCustomerSiteIdList());
		}

		
			

			storeListViewList = query.list();

		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllCustomerSitesForBuyCustomer=====>"
					+ e);
		}
		return storeListViewList;
	}

	// modified by deepak
	public List<Integer> getCustomerSitesFromCustomerSite(Integer customerSiteId) {

		Session session = sessionFactory.getCurrentSession();
		List<Integer> sellCustomerSiteId = null;

		try {
		Query query = session
				.createQuery(" select distinct sellCustomerSite.customerSiteId from BalePickupCustomerSiteConfiguration where buyCustomerSite.customerSiteId =:customerSiteId and enabled= true");
		// Query query =
		// session.createQuery(" select sellCustomerSite.customerId from BalePickupCustomerSiteConfiguration where sellCustomerSite.customerSiteId in :LIST and enabled= true");

		
			query.setParameter("customerSiteId", customerSiteId);
			sellCustomerSiteId = query.list();

			

		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao: getCustomerSitesFromCustomerSiteId=====>"
					+ e);
		}

		return sellCustomerSiteId;
	} /* end */

	public List<CustomerSite> getCustomerSitesFromCustomerSiteId(
			Integer customerSiteId) {

		Session session = sessionFactory.getCurrentSession();
		List<CustomerSite> customerSites = new ArrayList<CustomerSite>();
		List<BalePickupCustomerSiteConfiguration> balePickupCustomerSiteConfigurationList = null;

		
			
		try {
		Query query = session
				.createQuery("from BalePickupCustomerSiteConfiguration where buyCustomerSite.customerSiteId =:customerSiteId and enabled= true");
		query.setParameter("customerSiteId", customerSiteId);
			balePickupCustomerSiteConfigurationList = query.list();
			for (BalePickupCustomerSiteConfiguration balePickupCustomerSiteConfiguration : balePickupCustomerSiteConfigurationList) {
				customerSites.add(balePickupCustomerSiteConfiguration
						.getSellCustomerSite());
			}
		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao: getCustomerSitesFromCustomerSiteId=====>"
					+ e);
		}

		return customerSites;
	}

	// by deepak

	public List<Integer> getCustomerIds(Integer customerSiteId) {
		
		List<Integer> entities = new ArrayList<Integer>();
		Session session = sessionFactory.getCurrentSession();
		try {
		Query query = session
				.createQuery("select customer.customerId from CustomerSite "
						+ "where customerSiteId =:customerSiteId");

		query.setParameter("customerSiteId", customerSiteId);
		
			entities = query.list();
		
		} catch (Exception e) {
			LOGGER.error("Error in MasterDataDao:  getCustomerIds  "+e);
		}

		

		if (entities != null && entities.size() > 0) {
			Set<Integer> uniqueEntities = new HashSet<Integer>(entities);
			List<Integer> uniqueEntitiesList = new ArrayList<Integer>(
					uniqueEntities);
	

			entities = uniqueEntitiesList;
		}

		

		return entities;

	}

	public HashSet<CustomerSite> getAllCustomerSitesForBuyCustomer(
			Customer selectedBuyCustomer, String state) {
		Session session = sessionFactory.getCurrentSession();
		List<CustomerSite> customerSites = null;

		Query query = null;
		String queryStr = null;
		HashSet<CustomerSite> hashedCustomerSites = null;
		try {
		queryStr = "from CustomerSite where customer.customerId=:customerId";

		if (state != null) {

			queryStr = "from CustomerSite where customer.customerId=:customerId and "
					+ " location.locationId in (select locationId from Location where address4=:state)";
		}
		query = session.createQuery(queryStr);

		query.setParameter("customerId", selectedBuyCustomer.getCustomerId());
		if (state != null) {

			query.setParameter("state", state);

		}

		
			customerSites = query.list();
			for (CustomerSite customerSite : customerSites) {
				List<BalePickupMaterialConfiguration> materialConfig = customerSite
						.getBalePickupMaterialConfiguration();
				List<BalePickupSupplierSiteConfiguration> supplierSiteConfig = customerSite
						.getBalePickupSupplierSiteConfiguration();
				List<String> configuredMaterials = new ArrayList<String>();
				List<String> configuredSupplierSites = new ArrayList<String>();
				for (BalePickupMaterialConfiguration materialConfiguration : materialConfig) {
					configuredMaterials.add(materialConfiguration.getMaterial()
							.getDescription()
							+ "("
							+ materialConfiguration.getAvgBaleWeight()
							+ " lbs)");
				}
				customerSite.setConfiguredMaterials(configuredMaterials);
				for (BalePickupSupplierSiteConfiguration supplierSiteConfiguration : supplierSiteConfig) {
					configuredSupplierSites.add(supplierSiteConfiguration
							.getSupplierSite().getSiteName());
				}
				customerSite.setConfiguredMaterials(configuredMaterials);
				customerSite
						.setConfiguredSupplierSites(configuredSupplierSites);

			}
			hashedCustomerSites = new HashSet<CustomerSite>(customerSites);
		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllCustomerSitesForBuyCustomer=====>"
					+ e);
		}
		return hashedCustomerSites;
	}

	public HashSet<CustomerSite> getAllCustomerSitesForBuyCustomerWithLessInfo(
			Customer selectedBuyCustomer, String state) {
		Session session = sessionFactory.getCurrentSession();
		List<CustomerSite> customerSites = null;

		Query query = null;
		String queryStr = null;
		HashSet<CustomerSite> hashedCustomerSites = null;
		try {
		queryStr = "select new CustomerSite(customer.customerId,customer.customerName,"
				+ "customer.legacyVendorId,customer.customerStateId,customer.createdDate,"
				+ "customer.updatedAt,customer.updatedBy, customerSiteId,siteName,createdDate"
				+ ",updatedAt,updatedBy,avgBaleWeight,balePickupStartDate,balePickupEndDate) from CustomerSite where customer.customerId=:customerId and siteName not like 'VOID%' ";

		if (state != null) {

			queryStr = "select new CustomerSite(customer.customerId,customer.customerName,"
					+ "customer.legacyVendorId,customer.customerStateId,customer.createdDate,"
					+ "customer.updatedAt,customer.updatedBy, customerSiteId,siteName,createdDate"
					+ ",updatedAt,updatedBy,avgBaleWeight,balePickupStartDate,balePickupEndDate) "
					+ " from CustomerSite where customer.customerId=:customerId and siteName not like 'VOID%'"
					+ " and location.locationId in (select locationId from Location where address4=:state)";
		}
		query = session.createQuery(queryStr);

		query.setParameter("customerId", selectedBuyCustomer.getCustomerId());
		if (state != null) {
			query.setParameter("state", state);
		}

		
			customerSites = query.list();
			hashedCustomerSites = new HashSet<CustomerSite>(customerSites);
		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllCustomerSitesForBuyCustomer=====>"
					+ e);
		}
		return hashedCustomerSites;
	}

	public HashSet<Supplier> getAllSupplierSitesForBuyCustomer(
			Integer customerId) {
		Session session = sessionFactory.getCurrentSession();
		List<Supplier> supplierList = null;
		HashSet<Supplier> hashedSupplierList = null;
		try {

		Query query = session
				.createQuery("select supplierMapping.supplier from BaleRouteCustomerSupplierMapping supplierMapping "
						+ " where supplierMapping.buyCustomer.customerId=:customerId");
		query.setParameter("customerId", customerId);
		
			supplierList = query.list();
			hashedSupplierList = new HashSet<Supplier>(supplierList);
		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllSupplierSitesForBuyCustomer=====>"
					+ e);
		}
		return hashedSupplierList;
	}

	public HashSet<SupplierSite> getAllSupplierSitesForSupplier(
			Supplier supplier) {
		Session session = sessionFactory.getCurrentSession();
		List<SupplierSite> supplierSites = null;
		HashSet<SupplierSite> hashedSupplierSites = null;
		try {
		Query query = session
				.createQuery("from SupplierSite where supplierId=:supplierId");
		query.setParameter("supplierId", supplier.getSupplierId());
		
			supplierSites = query.list();
			hashedSupplierSites = new HashSet<SupplierSite>(supplierSites);
		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllSupplierSitesForSupplier=====>"
					+ e);
		}
		return hashedSupplierSites;
	}

	

	public List<Material> getAllMaterialMappingsByCustomer(Integer customerId) {
		Session session = sessionFactory.getCurrentSession();
		List<Material> materials = null;
		try {
		Query query = session
				.createQuery("select mapping.material from BaleRouteCustomerMaterialMapping mapping where mapping.buyCustomer.customerId=:customerId "
						+ "and mapping.material.isdeleted=false");
		query.setParameter("customerId", customerId);
		
			materials = query.list();

		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllMaterialMappingsByCustomer=====>"
					+ e);
		}
		return materials;
	}

	
	public List<Material> getAllMaterials() {
		Session session = sessionFactory.getCurrentSession();
		List<Material> materials = null;
		try {
		Query query = session
				.createQuery("select distinct material from BaleRouteCustomerMaterialMapping");
		
			materials = query.list();

		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllMaterialMappingsByCustomer=====>"
					+ e);
		}

	

		return materials;
	}

	// bydeepak
	public List<Material> getMaterialDetails(Integer customerSiteId) {
		Session session = sessionFactory.getCurrentSession();
		List<Material> materialDetails = new ArrayList<Material>();
		try {
		Query query = session
				.createQuery("select materialConfig.material from BalePickupMaterialConfiguration materialConfig "
						+ "where materialConfig.customerSite.customerSiteId =:customerSiteId and materialConfig.enabled= true");

		
			query.setParameter("customerSiteId", customerSiteId);
			materialDetails = query.list();

		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllMaterialMappingsByCustomer=====>"
					+ e);
		}
	
		return materialDetails;
	}

	
	
	public List<String> getEnabledModules() {
		Session session = sessionFactory.getCurrentSession();
		
		List<String> enabledModule = new ArrayList<String>();
		try{
			Query query = session
				.createQuery(" select modules.moduleName from AppSupportEnableModules modules where modules.enabled= true");
			enabledModule = query.list();
			
			
		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDaogetEnabledModules-------------->"+ e);
		}

	
		return enabledModule;
	}
	
	public Map<Integer, Integer> getMaterialForCustomerSite(
			Integer customerSiteId) {
		Session session = sessionFactory.getCurrentSession();
		List<BalePickupMaterialConfiguration> balePickupMaterialConfigurations = new ArrayList<BalePickupMaterialConfiguration>();

		
		Map<Integer, Integer> materialWithAvgBaleWeight = new HashMap<Integer, Integer>();
		try {
		Query query = session
				.createQuery(" select new BalePickupMaterialConfiguration(config.material,config.avgBaleWeight) from BalePickupMaterialConfiguration "
						+ " config where config.customerSite.customerSiteId =:customerSiteId and config.enabled= true");
	
		
			query.setParameter("customerSiteId", customerSiteId);
			balePickupMaterialConfigurations = query.list();
		

			for (BalePickupMaterialConfiguration config : balePickupMaterialConfigurations) {
				materialWithAvgBaleWeight.put(config.getMaterial()
						.getMaterialId(), config.getAvgBaleWeight());
			}
		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao:getAllMaterialMappingsByCustomer===111==>"
					+ e);
		}

		

		return materialWithAvgBaleWeight;
	}

	public void mergeCustomerSite(CustomerSite customerSite) {
		Session session = sessionFactory.getCurrentSession();
		try {
	
			//session.merge(customerSite);
			Query query = session
					.createQuery("update  CustomerSite customerSite set customerSite.balePickupStartDate =:balePickupStartDate , "
							+ " customerSite.balePickupEndDate =:balePickupEndDate , customerSite.frequency =:frequency ,"
							+ " customerSite.frequencyDay =:frequencyDay where customerSite.customerSiteId =:customerSiteId ");
			
			query.setParameter("balePickupStartDate", customerSite.getBalePickupStartDate());
			query.setParameter("balePickupEndDate", customerSite.getBalePickupEndDate());
			query.setParameter("frequency", customerSite.getFrequency());
			query.setParameter("frequencyDay", customerSite.getFrequencyDay());
			query.setParameter("customerSiteId", customerSite.getCustomerSiteId());
			int i=query.executeUpdate();
			
		} catch (Exception e) {
			LOGGER.error("Error:MasterDataDao:mergeCustomerSite=====>" + e);
		}

	}

	public BalePickupCustomerSiteConfiguration getCustomerSitesConfigFromCustomerSiteId(
			Integer customerSiteId) {
		//System.out.println("customerSiteId" + customerSiteId);
		Session session = sessionFactory.getCurrentSession();
		BalePickupCustomerSiteConfiguration balePickupCustomerSiteConfiguration = null;
		
		
		try {
		Query query = session
				.createQuery("from BalePickupCustomerSiteConfiguration where buyCustomerSite.customerSiteId =:customerSiteId and enabled= true");
		List<BalePickupCustomerSiteConfiguration> balePickupCustomerSiteConfigurationList = 
				new ArrayList<BalePickupCustomerSiteConfiguration>();
			query.setParameter("customerSiteId", customerSiteId);
			balePickupCustomerSiteConfigurationList = query.list();
			//System.out.println(balePickupCustomerSiteConfigurationList.size());

			if (balePickupCustomerSiteConfigurationList != null
					&& balePickupCustomerSiteConfigurationList.size() > 0) {
				balePickupCustomerSiteConfiguration = balePickupCustomerSiteConfigurationList
						.get(0);
			}

		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao234: getCustomerSitesFromCustomerSiteId=====>"
					+ e);
		}
		

		return balePickupCustomerSiteConfiguration;
	}

	public BalePickupMaterialConfiguration getBalePickupMaterialConfigurationForMaterial(
			Integer customerSiteId, Integer materialId) {
		
		List<BalePickupMaterialConfiguration> balePickupMaterialConfigurationList = 
				new ArrayList<BalePickupMaterialConfiguration>();

		BalePickupMaterialConfiguration balePickupMaterialConfiguration = null;
		try {
		Session session = sessionFactory.getCurrentSession();
		Query query = session
				.createQuery("select new BalePickupMaterialConfiguration(materialConfig.material ,materialConfig.avgBaleWeight ) "
						+ " from BalePickupMaterialConfiguration materialConfig "
						+ " where materialConfig.customerSite.customerSiteId =:customerSiteId and materialConfig.material.materialId=:materialId "
						+ "  and materialConfig.enabled= true");
		
			query.setParameter("customerSiteId", customerSiteId);
			query.setParameter("materialId", materialId);
			balePickupMaterialConfigurationList = query.list();
			

			if (balePickupMaterialConfigurationList != null
					&& balePickupMaterialConfigurationList.size() > 0) {
				balePickupMaterialConfiguration = balePickupMaterialConfigurationList
						.get(0);
			}

		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao234: getBalePickupMaterialConfigurationForMaterial=====>"
					+ e);
		}
		

		return balePickupMaterialConfiguration;
	}
	
	public boolean checkBuyCustSellCustSuppAvailability(Integer buyCustomerId, Integer sellCustomerId, Integer supplierId){
		
		Session session = sessionFactory.getCurrentSession();
		boolean flag=false;
		Query query = session
			.createQuery("select new BaleRouteCustomerMapping(mapping.baleRouteCustomermappingId) from BaleRouteCustomerMapping mapping  where mapping.buyCustomer.customerId =:buyCustomerId and mapping.supplier.supplierId =:supplierId"
					+ " and mapping.sellCustomer.customerId =:sellcustomerid");
		try {
			List<BaleRouteCustomerMapping> mapping = new ArrayList<BaleRouteCustomerMapping>();
			query.setParameter("supplierId", supplierId);
			query.setParameter("sellcustomerid", sellCustomerId);
			query.setParameter("buyCustomerId", buyCustomerId);
			mapping = query.list();
		
			if(mapping!=null && mapping.size() >0){
				flag=true;
			}
		} catch (RuntimeException e) {
			LOGGER.error("Error:MasterDataDao: getSupplierAndCustomerSiteRelatedOrNot=====>" + e);
		}
		return flag;
	}

}

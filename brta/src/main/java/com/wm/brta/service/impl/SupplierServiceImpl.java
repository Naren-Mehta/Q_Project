package com.wm.brta.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wm.brta.dao.MasterDataDao;
import com.wm.brta.dao.UserDao;
import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.SupplierSite;
import com.wm.brta.service.MasterService;
import com.wm.brta.service.SupplierService;


@Component("supplierMasterService")
@Transactional
public class SupplierServiceImpl implements MasterService<Supplier>,SupplierService {

	@Autowired
    MasterDataDao masterDao;
	
	@Autowired
    UserDao userDao;
	
	@Override
	public HashSet<Supplier> getAll() throws Exception {
		HashSet<Supplier>suppliers = new HashSet<Supplier>();
		suppliers = masterDao.getAllSuppliers();
		
		
		return suppliers;
	}
	
	@Override
	public List<Integer> getSupplierFromCustomerSite(Integer customerSiteId) throws Exception {
		List<Integer>suppliers =null;
		suppliers = masterDao.getSuppliersFromCustomerSite(customerSiteId);
		
		
		return suppliers;
	}

	@Override
	public HashSet<Supplier> getAllSuppliersByBuyCustomer(Integer customerId) {
		HashSet<Supplier>suppliers = masterDao.getAllSupplierSitesForBuyCustomer(customerId);
		
		return suppliers;
	}

	@Override
	public HashSet<SupplierSite> getAllSupplierSitesBySupplier(Supplier supplier) {
		HashSet<SupplierSite>supplierSites = new HashSet<SupplierSite>();
		supplierSites = masterDao.getAllSupplierSitesForSupplier(supplier);
		return supplierSites;
	}
	
	@Override
	public Integer getSupplierIdFromUser(Long userId){
		Integer supplierId= userDao.getSupplierIdFromUser(userId);
		return supplierId;
	}
	
	@Override
	public HashSet<Supplier> getAllSuppliersFromSellCustomer(Integer sellCustomerId) {
		HashSet<Supplier>suppliers = masterDao.getAllSuppliersFromSellCustomer(sellCustomerId);
		
		return suppliers;
	}
	

	@Override
	public List<Supplier> getSupplierFromCustomerSiteId(Integer customerSiteId) throws Exception {
		List<Supplier>suppliers = new ArrayList<Supplier>();
		suppliers = masterDao.getSuppliersFromCustomerSiteId(customerSiteId);
		
		return suppliers;
	}

}

package com.wm.brta.service;

import java.util.HashSet;
import java.util.List;

import com.wm.brta.domain.Supplier;
import com.wm.brta.domain.SupplierSite;

public interface SupplierService
{
	
	public HashSet<Supplier>getAllSuppliersByBuyCustomer(Integer customerId);
	public HashSet<SupplierSite> getAllSupplierSitesBySupplier(Supplier supplier);
	public  List<Supplier> getSupplierFromCustomerSiteId(Integer customerSiteId) throws Exception;
	public Integer getSupplierIdFromUser(Long userId);
	public  List<Integer> getSupplierFromCustomerSite(Integer customerSiteId) throws Exception;
	public HashSet<Supplier> getAllSuppliersFromSellCustomer(Integer sellCustomerId);

}

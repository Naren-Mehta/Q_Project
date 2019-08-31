package com.wm.brta.dao;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.wm.brta.domain.BalePickupMaterialConfiguration;
import com.wm.brta.domain.CustomerSite;

@Repository
public class CustomerSiteDAO {

	@Autowired
	protected SessionFactory sessionFactory;

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerSiteDAO.class);

	public CustomerSite getBuyCustomerSiteFromName(String storeName) {

		CustomerSite customerSite = null;

		try {
		
			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("select new CustomerSite(cs.customerSiteId,cs.siteName,cs.customer.customerId) "
							+ " from CustomerSite cs where cs.siteName =:storeName ");
				
			query.setParameter("storeName", storeName);

			List<CustomerSite> customerSiteList = query.list();
			
			if (customerSiteList != null && customerSiteList.size()==1)
				{
				customerSite = customerSiteList.get(0);
				}
				
				
		} catch (Exception e) {
			System.out.println("error"+e);
		}

		return customerSite;
	}
	
	
	public CustomerSite getSellCustomerSiteFromName(String storeName,Integer sellCustomerId) {
		
		System.out.println("sellCustomerId = "+storeName);
		System.out.println("sellCustomerId = "+sellCustomerId);
		System.out.println("====================================================================");
		CustomerSite customerSite = null;

		try {
		
			Session session = sessionFactory.getCurrentSession();
			Query query = session
					.createQuery("select new CustomerSite(cs.customerSiteId,cs.siteName,cs.customer.customerId) "
							+ " from CustomerSite cs where cs.siteName =:storeName ");
				
			query.setParameter("storeName", storeName);

			List<CustomerSite> customerSiteList = query.list();
			
			if (customerSiteList != null) {
				if(customerSiteList.size()==1)
				{
				customerSite = customerSiteList.get(0);
				}
				if(customerSiteList.size()>1 )
				{
					int j=0;
				for(int i=0;i<customerSiteList.size();i++)
					{
					System.out.println("====================================================================");
						customerSite = customerSiteList.get(j);
						System.out.println("Sell CUstomer Site Id = "+customerSite.getCustomerSiteId());
						System.out.println("sellCustomerId = "+sellCustomerId);
					boolean flag = getCustomerAndCustomerSiteRelatedOrNot(sellCustomerId,customerSite.getCustomerSiteId());
						if(flag)
						{
							
							break;
						}
						j++;
					}
					
				
				}
			}
		} catch (Exception e) {
			System.out.println("error"+e);
		}

		return customerSite;
	}
	
	
	public Boolean getCustomerAndCustomerSiteRelatedOrNot(Integer customerId, Integer customerSiteId) {

		Boolean isCustomerSiteRelatedToCustomer= false;
		try {
			Session session = sessionFactory.getCurrentSession();
						Query query = session
					.createQuery("select new CustomerSite(cs.customerSiteId,cs.siteName) from CustomerSite cs where cs.customerSiteId =:customerSiteId"
							+ " and cs.customer.customerId =:customerId ");
						
			query.setParameter("customerSiteId", customerSiteId);
			query.setParameter("customerId", customerId);
			
			List<CustomerSite> customerSiteList = query.list();
			
			if (customerSiteList != null && customerSiteList.size()>0) {
				isCustomerSiteRelatedToCustomer=true;
			}
		} catch (Exception e) {
			LOGGER.error("====ERROR in getCustomerAndCustomerSiteRelatedOrNot= "+ e);
		}
		
		
		System.out.println("isCustomerSiteRelatedToCustomer status = "+isCustomerSiteRelatedToCustomer);
		System.out.println("====================================================================");
		return isCustomerSiteRelatedToCustomer;
	}
	
	public CustomerSite getCustomerSiteFromId(Integer customerSiteId) {

		CustomerSite customerSite = null;

		try {
			Session session = sessionFactory.getCurrentSession();
			List<BalePickupMaterialConfiguration> materialMappings = null;

			Query query = session
					.createQuery("select new CustomerSite(customerSite.customerSiteId,customerSite.siteName) "
							+ "from CustomerSite customerSite where customerSiteId =:customerSiteId ");
			query.setParameter("customerSiteId", customerSiteId);

			List<CustomerSite> customerSiteList = query.list();
			if (customerSiteList.size()>0 && customerSiteList!= null) {
				customerSite = customerSiteList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("====ERROR in getCustomerSiteFromId "+ e);
		}

		return customerSite;
	}

	public CustomerSite getBuyCustomerSiteFromCustomerSiteId(
			Integer sellcustomerSiteId) {

		CustomerSite customerSite = null;

		Session session = sessionFactory.getCurrentSession();
		List<BalePickupMaterialConfiguration> materialMappings = null;

		try {
			Query query = session
					.createQuery("from CustomerSite cs where cs.customerSiteId =:sellcustomerSiteId ");
			query.setParameter("sellcustomerSiteId", sellcustomerSiteId);

			List<CustomerSite> customerSiteList = query.list();
			if (customerSiteList != null) {
				customerSite = customerSiteList.get(0);
			}
		} catch (Exception e) {
			LOGGER.error("====ERROR in getBuyCustomerSiteFromCustomerSiteId "+ e);
		}

		return customerSite;
	}
	
	
	
	
	public CustomerSite getBuyCustomerSiteFromCustomerName(
			String siteName) {

		CustomerSite customerSite = null;

		Session session = sessionFactory.getCurrentSession();
		try {
			Query query = session.createQuery("from CustomerSite where siteName =:siteName ");
			query.setParameter("siteName", siteName);

			List<CustomerSite> customerSiteList = query.list();
			if (customerSiteList != null && customerSiteList.size()>0) {
				customerSite = customerSiteList.get(0);
			}
			
		} catch (Exception e) {
			LOGGER.error("====ERROR in getBuyCustomerSiteFromCustomerSiteId "+ e);
		}

		return customerSite;
	}

	
	
	

public CustomerSite getSellCustomerNameForExcel(String siteName, Integer Id) {

	CustomerSite customerSite = null;

	Session session = sessionFactory.getCurrentSession();
	try {
		Query query = session.createQuery("from CustomerSite where siteName =:siteName and customerid :=id");
		query.setParameter("siteName", siteName);
		query.setParameter("customerid", Id);

		List<CustomerSite> customerSiteList = query.list();
		if (customerSiteList != null && customerSiteList.size()>0) {
			customerSite = customerSiteList.get(0);
		}
		
	} catch (Exception e) {
		LOGGER.error("====ERROR in getSellCustomerNameForExcel "+ e);
	}

	return customerSite;
}







}





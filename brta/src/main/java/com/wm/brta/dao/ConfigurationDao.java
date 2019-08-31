package com.wm.brta.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wm.brta.domain.BalePickupCustomerSiteConfiguration;
import com.wm.brta.domain.BalePickupMaterialConfiguration;
import com.wm.brta.domain.BalePickupSupplierConfiguration;
import com.wm.brta.domain.BalePickupSupplierSiteConfiguration;
import com.wm.brta.domain.CustomerSite;
import com.wm.brta.domain.Material;


@Repository
public class ConfigurationDao
{

	@Autowired
    protected SessionFactory sessionFactory; 
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigurationDao.class);

	
	public int addSupplierSiteConfigData(
			BalePickupSupplierSiteConfiguration supplierSiteConfig
			) {
		
		Session session = sessionFactory.getCurrentSession();
		int id=0;
		CustomerSite customerSite = supplierSiteConfig.getCustomerSite();
		session.merge(customerSite);
		
		try {
			
			 id = (int)session.save(supplierSiteConfig);
			 } catch (RuntimeException e) {
			
			
		LOGGER.error("Error:ConfigurationDao:addSupplierSiteConfigData=====>" +e);
		} finally {
			
		}
		return id;
		
	}
	
	public int addSupplierConfigData(
			BalePickupSupplierConfiguration supplierConfig
			) {
		
		Session session = sessionFactory.getCurrentSession();
		int id=0;
		
		try {
			 id = (int)session.save(supplierConfig);	
		} catch (RuntimeException e) {
			
		LOGGER.error("Error:ConfigurationDao:addSupplierSiteConfigData=====>" +e);
		} finally {
			
		}
		return id;
		
	}
	
	public int addMaterialConfigData(
			BalePickupMaterialConfiguration materialConfig
			) {
		
		Session session = sessionFactory.getCurrentSession();
		int id=0;		
		try {
			 id = (int)session.save(materialConfig);
		} catch (RuntimeException e) {
			LOGGER.error("Error:ConfigurationDao:addMaterialConfigData=====>" +e);
		} finally {
		}
		return id;
		
	}
	
	
	public int addCustomerSiteConfig(
			BalePickupCustomerSiteConfiguration balePickupCustomerSiteConfig
			) {
		
		Session session = sessionFactory.getCurrentSession();
		int id=0;		
		try {
		
			 id = (int)session.save(balePickupCustomerSiteConfig);
			
		} catch (RuntimeException e) {
			
			
			LOGGER.error("Error:ConfigurationDao:addMaterialConfigData=====>" +e);
		} finally {
			
		}
		return id;
		
	}
	
	

	public void deleteSupplierConfigurationRecordsForCustomerSite(
			CustomerSite customerSite) {
		Session session = sessionFactory.getCurrentSession();
		try{
		Query query = session.createQuery("update BalePickupSupplierConfiguration set enabled=false where customerSite.customerSiteId"
				+ " =:customerSiteId");
		query.setParameter("customerSiteId", customerSite.getCustomerSiteId());
		int x= query.executeUpdate();
	
		}
		catch(Exception e){
			LOGGER.error("Error:ConfigurationDao:deleteSupplierConfigurationRecordsForCustomerSite=====>" +e);
			
		}
		
	}
	
	
	public void deleteCustomerSiteConfigurationRecordsForCustomerSite(
			CustomerSite customerSite) {
		Session session = sessionFactory.getCurrentSession();
		try{
		Query query = session.createQuery("update BalePickupCustomerSiteConfiguration set enabled=false where buyCustomerSite.customerSiteId"
				+ " =:customerSiteId");
		query.setParameter("customerSiteId", customerSite.getCustomerSiteId());
		query.executeUpdate();
		}
		catch(Exception e){
			LOGGER.error("Error:ConfigurationDao:deleteCustomerSiteConfigurationRecordsForCustomerSite=====>" +e);
			
		}
		
	}
	
	
	
	public void deleteMaterialConfigurationRecordsForCustomerSite(
			CustomerSite customerSite) {
		Session session = sessionFactory.getCurrentSession();
		try{
		Query query = session.createQuery("update BalePickupMaterialConfiguration set enabled=false where customerSite.customerSiteId"
				+ " =:customerSiteId");
		query.setParameter("customerSiteId", customerSite.getCustomerSiteId());
		query.executeUpdate();
		}
        catch(Exception e){
        LOGGER.error("Error:ConfigurationDao:deleteMaterialConfigurationRecordsForCustomerSite=====>" +e);
			
		}
		
	}
	
	public void deleteSupplierConfigurationRecordsForCustomerSite(
			List<String> customerSiteList) {
		Session session = sessionFactory.getCurrentSession();
		try{
		Query query = session.createQuery("update BalePickupSupplierConfiguration set enabled=false where customerSite.siteName"
				+ " IN :customerSiteList");
		query.setParameterList("customerSiteList", customerSiteList);
		int x= query.executeUpdate();
	
		}
		catch(Exception e){
			LOGGER.error("Error:ConfigurationDao:deleteSupplierConfigurationRecordsForCustomerSite=====>" +e);
			
		}
		
	}
	
	
	public void deleteCustomerSiteConfigurationRecordsForCustomerSite(
			List<String> customerSiteList) {
		Session session = sessionFactory.getCurrentSession();
		try{
		Query query = session.createQuery("update BalePickupCustomerSiteConfiguration set enabled=false where buyCustomerSite.siteName "
				+ " IN :customerSiteList");
		query.setParameterList("customerSiteList", customerSiteList);
		query.executeUpdate();
		}
		catch(Exception e){
			LOGGER.error("Error:ConfigurationDao:deleteCustomerSiteConfigurationRecordsForCustomerSite=====>" +e);
			
		}
		
	}
	
	
	
	public void deleteMaterialConfigurationRecordsForCustomerSite(
			List<String> customerSiteList) {
		Session session = sessionFactory.getCurrentSession();
		try{
		Query query = session.createQuery("update BalePickupMaterialConfiguration set enabled=false where customerSite.siteName"
				+ " IN :customerSiteList");
		query.setParameterList("customerSiteList",customerSiteList);
		query.executeUpdate();
		}
        catch(Exception e){
        LOGGER.error("Error:ConfigurationDao:deleteMaterialConfigurationRecordsForCustomerSite=====>" +e);
			
		}
		
	}
	
	public BalePickupMaterialConfiguration getMaterialConfigByCustomerSiteAndMaterial
	(CustomerSite buyCustomerSite,Material material){
		List<BalePickupMaterialConfiguration> balePickupMaterialConfig = null;
		Session session = sessionFactory.getCurrentSession();
		try{
		Query query = session.createQuery(" from BalePickupMaterialConfiguration where customerSite and enabled=true"
				+ " =:customerSite and material =:material");
		query.setParameter("customerSite", buyCustomerSite);
		query.setParameter("material",material);
		balePickupMaterialConfig = query.list();
		}
        catch(Exception e){
        	LOGGER.error("Error:ConfigurationDao:getMaterialConfigByCustomerSiteAndMaterial=====>" +e);
        }
		
		
		return balePickupMaterialConfig.get(0);
		
		
	}
	
	
	

}

package com.wm.brta.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wm.brta.domain.Material;


@Repository
public class BalePickupMaterialConfigurationDAO {

	@Autowired
	protected SessionFactory sessionFactory;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BalePickupMaterialConfigurationDAO.class);


	public Set<Material> getAllMaterialFromCustomerSite(Integer customerSiteId) {
		Set<Material> materialSet = new HashSet<Material>();
		try{
		Session session = sessionFactory.getCurrentSession();

		String stringQuery = "select materialConfig.material from BalePickupMaterialConfiguration materialConfig where materialConfig.customerSite.customerSiteId =:customerSiteId and enabled=true";
		Query query = session.createQuery(stringQuery);
		query.setParameter("customerSiteId", customerSiteId);

		List<Material> materialList = query.list();
		materialSet= new HashSet<Material>(materialList);
		}catch(Exception e){
			LOGGER.error("Error in getAllMaterialFromCustomerSite "+e);
			return null;
		}
		return materialSet;
	}
}

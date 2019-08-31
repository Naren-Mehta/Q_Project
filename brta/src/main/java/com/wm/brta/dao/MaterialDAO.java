package com.wm.brta.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wm.brta.domain.Material;


@Repository
public class MaterialDAO {
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MaterialDAO.class);

	
	public  Material getMaterialFromId(Integer materialId){
		
		Material material=null;
		Session session = sessionFactory.getCurrentSession();

		String stringQuery = "from Material where materialId =:materialId";
		
		
		try{
			Query query = session.createQuery(stringQuery);
			query.setParameter("materialId", materialId);
			List<Material> materialList = query.list();

			if(materialList !=null && materialList.size() >0 ){
				material=materialList.get(0);
			}
		}
		catch(Exception e){
			LOGGER.error("==getMaterialFromId===="+e);
		}


		
		return material;
	}
	
}

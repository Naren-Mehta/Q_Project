package com.wm.brta.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wm.brta.domain.Frequency;

@Repository
public class FrequencyDAO {
	
	@Autowired
	protected SessionFactory sessionFactory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(BalePickUpAssignmentDAO.class);
	
	public List<Frequency>  getAllFrequency(){
		
		List<Frequency> frequencies=new ArrayList<Frequency>();
		
		Session session = sessionFactory.getCurrentSession();
		try {

			Query query = session
					.createQuery("from Frequency where enabled = true");
			
			frequencies = query.list();
		}

		catch (RuntimeException e) {
			LOGGER.error("Error:BalePickUpAssignmentDAO:getAllAssignments=====>"
					+ e);

		}
		return frequencies;
	}

}

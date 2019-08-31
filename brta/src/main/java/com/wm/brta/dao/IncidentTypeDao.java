package com.wm.brta.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.wm.brta.domain.IncidentType;

@Repository
public class IncidentTypeDao {

	@Autowired
	protected SessionFactory sessionFactory;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(IncidentTypeDao.class);

	public List<IncidentType> getAllIncidents() {
		Session session = sessionFactory.getCurrentSession();

		Query query = null;
		List<IncidentType> users = null;
		try {
		query = session
				.createQuery("from IncidentType where enabled=true order by createdDate desc");

		
		
			users = query.list();
		} catch (RuntimeException e) {
			LOGGER.error("Error:IncidentTypeDao:getAllIncidents=====>" + e);
			return null;
		}
		return users;
	}

	public IncidentType getIncidentbyName(String incidentDescription) {
		Session session = sessionFactory.getCurrentSession();

		Query query = null;
		List<IncidentType> incidentList = null;
		IncidentType incident = null;
		try {
		query = session
				.createQuery("from IncidentType where incidentDescription =:incidentDescription and enabled=true");
		query.setParameter("incidentDescription", incidentDescription);
		
		
			incidentList = query.list();

			if (incidentList != null && incidentList.size() > 0) {
				incident = incidentList.get(0);
			}
			
		} catch (RuntimeException e) {
			LOGGER.error("Error:IncidentTypeDao:getAllIncidents=====>" + e);
		}
		return incident;
	}

	public long save(IncidentType persistenceObject) {

		Session session = sessionFactory.getCurrentSession();
		int id = 0;

		try {

			id = (int) session.save(persistenceObject);

		} catch (RuntimeException e) {

			LOGGER.error("Error:IncidentTypeDao:save=====>" + e);
		} finally {

		}
		return id;
	}

	public void merge(IncidentType incident) {

		

		Session session = sessionFactory.getCurrentSession();
		try {

			session.merge(incident);

		} catch (RuntimeException e) {

			LOGGER.error("Error:IncidentTypeDao:merge=====>" + e);
		} finally {

		}

	}

	public List<IncidentType> getAllActiveIncidents() {
		Session session = sessionFactory.getCurrentSession();

		Query query = null;
		List<IncidentType> incidentTypes = null;
		try {
		query = session
				.createQuery("from IncidentType where enabled=true order by createdDate desc");

		
			incidentTypes = query.list();
		} catch (RuntimeException e) {
			LOGGER.error("Error:IncidentTypeDao:getAllActiveIncidents=====>"
					+ e);
		}
		return incidentTypes;
	}

	public List<IncidentType> getAllIncidentAccordingRoStatus(String status) {
		Session session = sessionFactory.getCurrentSession();

		Query query = null;
		List<IncidentType> incidentTypes = null;
		try {
		if (status != null) {
			if (status.equals("All")) {
				query = session
						.createQuery("from IncidentType order by createdDate desc");

			} else {
				query = session
						.createQuery("from IncidentType where enabled=:status order by createdDate desc");
				query.setParameter("status", status.equals("Active") ? true
						: false);
			}
		} else {
			query = session
					.createQuery("from IncidentType order by createdDate desc");
		}

		
			incidentTypes = query.list();
		} catch (RuntimeException e) {
			LOGGER.error("Error:IncidentTypeDao:getAllActiveIncidents=====>"+ e);
			return null;
		}
		return incidentTypes;
	}

	public Boolean checkIncidenttypeUnique(String incidentDescription) {
		Session session = sessionFactory.getCurrentSession();
		List<IncidentType> incidentTypes = null;
		Boolean status = false;
		try {
		Query query = session
				.createQuery("from IncidentType where incidentDescription =:incidentDescription order by createdDate desc");
		query.setParameter("incidentDescription", incidentDescription);

			incidentTypes = query.list();
			if (incidentTypes.size() > 0) {
				status = true;
			}
		} catch (RuntimeException e) {
			LOGGER.error("Error:IncidentTypeDao:checkIncidenttypeUnique=====>"
					+ e);
		} finally {

		}
		return status;

	}

	public Boolean getIncidentbyNameStatus(IncidentType incident) {

		String incidentDescription = incident.getIncidentDescription();
		boolean enabled = incident.isEnabled();
		Session session = sessionFactory.getCurrentSession();
		List<IncidentType> incidentTypes = null;
		Boolean incStatus = false;
		try {
		String sqlQ = "from IncidentType where incidentDescription =:incidentDescription ";
		
		if (incident.getIncidentTypeId() != null) {
			sqlQ = sqlQ + " and incidentTypeId !=:incidentTypeId ";
		}
		
		Query query = session.createQuery(sqlQ);
		// and enabled =:enabled
		

			query.setParameter("incidentDescription", incidentDescription);
			if (incident.getIncidentTypeId() != null) {
				query.setParameter("incidentTypeId",
						incident.getIncidentTypeId());
			}

			incidentTypes = query.list();
			if (incidentTypes.size() > 0) {
				incStatus = true;
			}
		} catch (RuntimeException e) {
			LOGGER.error("Error:IncidentTypeDao:getIncidentbyNameStatus=====>"	+ e);
		}
		return incStatus;

	}
}

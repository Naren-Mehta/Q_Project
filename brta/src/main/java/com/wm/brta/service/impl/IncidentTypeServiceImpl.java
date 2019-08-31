package com.wm.brta.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wm.brta.dao.IncidentTypeDao;
import com.wm.brta.domain.IncidentType;
import com.wm.brta.domain.Supplier;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.ApplicationService;
import com.wm.brta.service.IncidentTypeService;

@Component("incidentService")
@Transactional
public class IncidentTypeServiceImpl implements
		ApplicationService<IncidentType>, IncidentTypeService {

	@Autowired
	IncidentTypeDao incidentTypeDao;

	@Override
	public List<IncidentType> add(IncidentType persistenceObject,
			UserDTO userDTO) throws Exception {
		persistenceObject.setCreatedDate(new Date());
		persistenceObject.setUpdatedAt(new Date());
		persistenceObject.setUpdatedBy(userDTO.getFirstName());
		List<IncidentType> incidents = new ArrayList<IncidentType>();
		long id = 0;
		id = incidentTypeDao.save(persistenceObject);
		if (id != 0) {

			incidents = getAll();
		}
		return incidents;
	}

	@Override
	public LinkedList delete(IncidentType object) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IncidentType> getAll() {
		List<IncidentType> incidents = incidentTypeDao.getAllIncidents();

		return incidents;
	}

	@Override
	public List edit(IncidentType incident) throws Exception {
		incident.setUpdatedAt(new Date());

		List<IncidentType> incidents = new ArrayList<IncidentType>();
		long id = 0;
		incidentTypeDao.merge(incident);
		incidents = getAll();

		return incidents;
	}

	@Override
	public Boolean checkIncidenttypeUniqueOrNot(String incidentType) {
		Boolean status = false;
		status = incidentTypeDao.checkIncidenttypeUnique(incidentType);

		return status;
	}

	@Override
	public List<IncidentType> getAll(Supplier supplierSelected)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IncidentType> getAllIncidentTypeFromStatus(String status) {
		List<IncidentType> incideTypes = incidentTypeDao
				.getAllIncidentAccordingRoStatus(status);
		return incideTypes;
	}
	@Override
	public Boolean getIncidentbyNameStatus(IncidentType incident) {
		Boolean incideTypes = incidentTypeDao.getIncidentbyNameStatus(incident);
		return incideTypes;
	}

}

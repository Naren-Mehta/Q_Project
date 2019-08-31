package com.wm.brta.service;

import java.util.List;

import com.wm.brta.domain.IncidentType;

public interface IncidentTypeService {

	public Boolean checkIncidenttypeUniqueOrNot(String incidentType);
	public List<IncidentType> getAllIncidentTypeFromStatus(String status);
	public Boolean getIncidentbyNameStatus(IncidentType incident);
	
}

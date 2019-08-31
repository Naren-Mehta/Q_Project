package com.wm.brta.service;

import java.util.LinkedList;
import java.util.List;

import com.wm.brta.domain.IncidentType;
import com.wm.brta.domain.Supplier;
import com.wm.brta.dto.UserDTO;


public interface ApplicationService<T>
{
	
	public  List<T> add(T persistenceObject, UserDTO userDto) throws Exception;
	public  LinkedList<T> delete(T object) throws Exception;
	public  List<T> getAll(Supplier supplierSelected) throws Exception;
	public List<T> edit(T user) throws Exception;
	public List<IncidentType> getAll();
	
	

}

package com.wm.brta.service;

import java.util.HashSet;

public interface MasterService<T> {
	
	
	public  HashSet<T> getAll() throws Exception;
	
}

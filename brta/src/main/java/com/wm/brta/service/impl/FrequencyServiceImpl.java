package com.wm.brta.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wm.brta.dao.FrequencyDAO;
import com.wm.brta.domain.Frequency;
import com.wm.brta.service.FrequencyService;

@Component("frequencyService")
@Transactional
public class FrequencyServiceImpl implements FrequencyService {

	@Autowired
	FrequencyDAO frequencyDAO;

	@Override
	public List<Frequency> getAllFrequency() {

		List<Frequency> frequencies = frequencyDAO.getAllFrequency();
		return frequencies;
	}

}

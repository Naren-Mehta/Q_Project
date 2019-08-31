package com.wm.brta.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.wm.brta.util.BaleUtils;

public class BrtaUtilTest {

	@Test
	public void testGetWeekNumber() {

		int weekNumber = 0;
		try {
			weekNumber = BaleUtils.getWeekNumber("04/12/2017");
		} catch (Exception e) {

		}

		//System.out.println("==weekNumber====" + weekNumber);
		Assert.assertEquals(weekNumber, 3);

	}

	@Test
	public void testGetDay() {

		int day = 0;
		try {
			day = BaleUtils.getWeekNumber("04/12/2017");
		} catch (Exception e) {

		}

		//System.out.println("==weekNumber====" + day);
		Assert.assertEquals(day, 3);

	}

	@Test
	public void testGetLastOneWeekDates() {

		List<String> listOfDates = new ArrayList<String>();
		try {
			listOfDates = BaleUtils.getLastOneWeekDates();
		} catch (Exception e) {
		}
		//System.out.println("==listOfDates====" + listOfDates);
		Assert.assertNotNull(listOfDates);

	}
	
	
	@Test 
	public void testConvertWeekNumberAndDayToDate(){
		
		Date startDate = new Date("01/02/1970");
		String date =BaleUtils.convertWeekNumberAndDayToDate(5, 2, startDate);
		
	//	System.out.println("=======date======="+date);
		
	}
	

	@Test
	public void massageTest() {
		Assert.assertEquals("abc", "abc");
	}

}

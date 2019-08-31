package com.wm.brta.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wm.brta.constants.ServiceChannelEnumConstants;
import com.wm.brta.dto.BalePickupMobileDetailsForSC;
import com.wm.brta.dto.SavedWorkOrdersToServiceChannel;
import com.wm.brta.dto.ServiceChannelESBDTO;
import com.wm.brta.service.ServiceChannelService;

public class BaleUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(BaleUtils.class);
	@Autowired
	ServiceChannelService serviceChannelService;
	
	private static SessionFactory mockedSessionFactory;
	private static Session mockedSession;
	private static Transaction mockedTransaction;
	public static Double versionNo = 2.00;
	public static String sc_primary_status = "IN PROGRESS";
	public static String sc_extended_status = "DISPATCH CONFIRMED";
	public static String sc_status_primary_checkinDate="";
	public static String sc_status_primary_checkoutDate="";
	public static String sc_type = "RECYCLING - OCC BALES";
	public static String sc_esb_status = "/status";
	/*public static String from_date =  getNextWeekMonday();
	public static String to_date = getNextWeekSunday();*/
	public static String androidApkUrl = "https://play.google.com/store/apps/details?id=baleroute.tracking.app";
	public static String iOsApkUrl = "https://play.google.com/store/apps/details?id=baleroute.tracking.app";
	
	public static Integer getWeekNumber(String date) throws ParseException {

		String format = "MM/dd/yyyy";
		SimpleDateFormat df = new SimpleDateFormat(format);
		Date dateFormat = df.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateFormat);

		int weeknumber = cal.get(Calendar.WEEK_OF_MONTH);

		if (cal.get(Calendar.DAY_OF_WEEK) == 1)
			weeknumber = weeknumber - 1;

		return weeknumber;
	}

	
	
	
	public static String getIsoDateForServiceChannel(Date date){
		TimeZone tz = TimeZone.getTimeZone("UTC");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
		df.setTimeZone(tz);
		String nowAsISO = df.format(date);
		
	return nowAsISO;
	}
	
	
	public static ServiceChannelEnumConstants getStatus(BalePickupMobileDetailsForSC balePickupMobileDetailsForSC) {
		ServiceChannelEnumConstants status = ServiceChannelEnumConstants.INCOMPLETE;

		if ((balePickupMobileDetailsForSC.getCheckinDate() != null || balePickupMobileDetailsForSC.getCheckinDate() != "")
				&& (balePickupMobileDetailsForSC.getCheckoutDate() == null || balePickupMobileDetailsForSC.getCheckoutDate() == "")) {
					status = ServiceChannelEnumConstants.OnSite;
		} else if (((balePickupMobileDetailsForSC.getCheckinDate() != null || balePickupMobileDetailsForSC.getCheckinDate() != ""))
				&& ((balePickupMobileDetailsForSC.getCheckoutDate() != null || balePickupMobileDetailsForSC.getCheckoutDate() != ""))) {
			if (balePickupMobileDetailsForSC.getBalesPicked() >= 0 && balePickupMobileDetailsForSC.getBalesRemaining()< 10) {
				status = ServiceChannelEnumConstants.Complete;
			} else {
				status = ServiceChannelEnumConstants.INCOMPLETE;
			}
		} else {
			// TODO : Need to update after proper requirement
			status = ServiceChannelEnumConstants.DispatchConfirmed;
		}

		return status;
	}

	
	public static String generateUniqueIdByCalader() {
		String concat = "";
		String yearInString = String.valueOf(Calendar.getInstance().get(
				Calendar.YEAR));
		String MMInString = String.valueOf(Calendar.getInstance().get(
				Calendar.MONTH) + 1);
		String ddInString = String.valueOf(Calendar.getInstance().get(
				Calendar.DATE));
		String hhInString = String.valueOf(Calendar.getInstance().get(
				Calendar.HOUR_OF_DAY));
		String mmInString = String.valueOf(Calendar.getInstance().get(
				Calendar.MINUTE));
		String ssInString = String.valueOf(Calendar.getInstance().get(
				Calendar.SECOND));
		MMInString = MMInString.length() < 2 ? 0 + MMInString : MMInString;
		ddInString = ddInString.length() < 2 ? 0 + ddInString : ddInString;
		hhInString = hhInString.length() < 2 ? 0 + hhInString : hhInString;
		mmInString = mmInString.length() < 2 ? 0 + mmInString : mmInString;
		ssInString = ssInString.length() < 2 ? 0 + ssInString : ssInString;
		concat = yearInString + MMInString + ddInString + hhInString
				+ mmInString + ssInString;
		return concat;
	}

	public static Date convertISODate(String strDate) {
		Date date = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	

		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			
			LOGGER.error("Error in BaleUtils : convertISODate  " +e);
		}

		

		return date;

	}
	public static Integer getDay(String date) throws ParseException {

		String format = "MM/dd/yyyy";

		SimpleDateFormat df = new SimpleDateFormat(format);
		Date dateFormat = df.parse(date);
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateFormat);

		int day = cal.get(Calendar.DAY_OF_WEEK);
		day = day - 1;
		if (day == 0)
			day = 7;

		return day;
	}

	public static List<String> getLastOneWeekDates() {

		List<String> dateList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
		c.add(Calendar.DATE, -i - 6);
		Date start = c.getTime();
		c.add(Calendar.DATE, 6);
		Date end = c.getTime();
		
		dateList.add(sdf.format(start.getTime()));
		dateList.add(sdf.format(end.getTime()));
		

		return dateList;
	}

	public static List<String> getLastTwoWeekDates() {

		Date end = null;
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		c.add(Calendar.DATE, -14);
		Date start = c.getTime();
		
		List<Date> dateList = new ArrayList<Date>();
		List<String> dateList2 = new ArrayList<String>();

		for (int i = 0; i < 13; i++) {
			dateList.add(start);
			c.add(Calendar.DATE, +1);
			end = c.getTime();
		}
		dateList2.add(sdf.format(start.getTime()));
		dateList2.add(sdf.format(end.getTime()));
		
		return dateList2;
	}

	public static List<String> getLastMonthDates() {
		Calendar aCalendar = Calendar.getInstance();
		List<String> dateList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		aCalendar.add(Calendar.MONTH, -1);
		aCalendar.set(Calendar.DATE, 1);
		Date firstDateOfPreviousMonth = aCalendar.getTime();
		aCalendar.set(Calendar.DATE,
				aCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date lastDateOfPreviousMonth = aCalendar.getTime();
		dateList.add(sdf.format(firstDateOfPreviousMonth.getTime()));
		dateList.add(sdf.format(lastDateOfPreviousMonth.getTime()));
		
		return dateList;
	}

	public static void setUpMokito() {
		mockedSessionFactory = Mockito.mock(SessionFactory.class);
		mockedSession = Mockito.mock(Session.class);
		mockedTransaction = Mockito.mock(Transaction.class);
	}

	@SuppressWarnings("deprecation")
	public static String convertWeekNumberAndDayToDate1(int weekNumber,
			int day, Date startDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		if (day == 7) {
			day = 0;
			weekNumber = weekNumber + 1;
		}
		cal.set(Calendar.WEEK_OF_MONTH, weekNumber);
		boolean entryFlag = false;
		cal.set(Calendar.MONTH, cal.getTime().getMonth());
		cal.set(Calendar.DAY_OF_WEEK, day + 1);
		String dateToreturn = sdf.format(cal.getTime());
		Calendar startDateCalendar = Calendar.getInstance();
		Calendar todaysDatecalendar = Calendar.getInstance();
		Calendar dateToReturnCalendar = Calendar.getInstance();
		if (startDate.after(new Date())) {
			if (startDate.after(new Date(dateToreturn))) {
				cal.set(Calendar.WEEK_OF_MONTH, weekNumber);
				startDateCalendar.setTime(startDate);
				cal.set(Calendar.MONTH,
						startDateCalendar.getTime().getMonth() + 1);
				cal.set(Calendar.DAY_OF_WEEK, day + 1);
				dateToreturn = sdf.format(cal.getTime());

			}

		}

		if ((startDate.before(new Date()) || startDate.equals(new Date()))) {

			if (new Date().getMonth() == (new Date(dateToreturn)).getMonth()
					&& new Date().getYear() == (new Date(dateToreturn))
							.getYear()
					&& new Date().getDate() == (new Date(dateToreturn))
							.getDate()) {

				cal.set(Calendar.WEEK_OF_MONTH, weekNumber);
				todaysDatecalendar.setTime(new Date());
				cal.set(Calendar.MONTH, todaysDatecalendar.getTime().getMonth());
				cal.set(Calendar.DAY_OF_WEEK, day + 1);
				dateToreturn = sdf.format(cal.getTime());
			}

			else if (new Date().after(new Date(dateToreturn))) {

				cal.set(Calendar.WEEK_OF_MONTH, weekNumber);
				todaysDatecalendar.setTime(new Date());
				cal.set(Calendar.MONTH,
						todaysDatecalendar.getTime().getMonth() + 1);
				cal.set(Calendar.DAY_OF_WEEK, day + 1);
				dateToreturn = sdf.format(cal.getTime());

			}

		}

		return dateToreturn;

	}
	
	
	
	public static Date getISODate(Date date)
	{
	TimeZone tz = TimeZone.getTimeZone("UTC");
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
	df.setTimeZone(tz);
	String nowAsISO = df.format(date);
	
	
	
	return date;
	}
	public static String convertWeekNumberAndDayToDate(int weekNumber, int day) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		Calendar cal1 = Calendar.getInstance();

		if (day == 7) {
			day = 0;
			weekNumber = weekNumber + 1;
		}

		cal1.set(Calendar.WEEK_OF_MONTH, weekNumber);
		cal1.set(Calendar.MONTH, cal.getTime().getMonth());
		cal1.set(Calendar.DAY_OF_WEEK, day + 1);
		String dateToreturn = sdf.format(cal1.getTime());

		return dateToreturn;

	}

	public static byte[] loadFile(File file) throws IOException {
		InputStream is = new FileInputStream(file);

		long length = file.length();
		if (length > Integer.MAX_VALUE) {
			// File is too large
		}
		byte[] bytes = new byte[(int) length];

		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length
				&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
			offset += numRead;
		}

		if (offset < bytes.length) {
			throw new IOException("Could not completely read file "
					+ file.getName());
		}

		is.close();
		return bytes;
	}

	public static String encodedBase64String(String filePath) {
		String encodedString = "";
		try {
			File file = new File(filePath.toString());
			if (file.length() >= 0) {
				byte[] bytes = BaleUtils.loadFile(file);
				byte[] encoded = org.apache.tomcat.util.codec.binary.Base64
						.encodeBase64(bytes);
				encodedString = new String(encoded);
			}

		} catch (Exception e) {
			LOGGER.error("======exception while reading file ====" + e);
		}
		return encodedString;
	}

	public static String convertWeekNumberAndDayToDate(int weekNumber,
			int dayNumber, Date startDate) {

		
		int _dayOfTheWeek = getDayOfTheWeek(dayNumber);
		Date _baseDate = new Date();
		if ((startDate != null) && startDate.after(_baseDate)) {

			_baseDate = startDate;
		}

		Calendar _cal = Calendar.getInstance();
		_cal.setFirstDayOfWeek(Calendar.MONDAY);
		_cal.setTime(_baseDate);

		Calendar _nextDateCal = Calendar.getInstance();
		_nextDateCal.setFirstDayOfWeek(Calendar.MONDAY);
		Calendar _calcDateCal = Calendar.getInstance();
		_calcDateCal.setFirstDayOfWeek(Calendar.MONDAY);

		int _monthNumber = _cal.get(Calendar.MONTH);
		boolean _checkMonth = true;

		do {

			_nextDateCal.set(Calendar.MONTH, _monthNumber);
			_nextDateCal.set(Calendar.WEEK_OF_MONTH, weekNumber);
			_nextDateCal.set(Calendar.DAY_OF_WEEK, _dayOfTheWeek);

			_monthNumber++;
			if (_monthNumber > 12) {
				_monthNumber = 1;
				_checkMonth = false;
			}

			// Following code is to ensure even if it is today's date, it is
			// greater than _baseDate
			_calcDateCal.setTime(_nextDateCal.getTime());
			_calcDateCal.set(Calendar.HOUR_OF_DAY, 23);
			_calcDateCal.set(Calendar.MINUTE, 59);
			_calcDateCal.set(Calendar.SECOND, 59);
			_calcDateCal.set(Calendar.MILLISECOND, 59);

		} while (!((_calcDateCal.getTime().after(_baseDate))
				&& ((_calcDateCal.get(Calendar.MONTH) >= _cal
						.get(Calendar.MONTH)) || !_checkMonth)
				&& (_calcDateCal.get(Calendar.WEEK_OF_MONTH) == weekNumber) && (_calcDateCal
					.get(Calendar.DAY_OF_WEEK) == _dayOfTheWeek)));

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String dateString = sdf.format(_calcDateCal.getTime());

		return dateString;

	}
	
	public static Date toNearestWholeMinute(Date d) {
        Calendar c = new GregorianCalendar();
        c.setTime(d);

        if (c.get(Calendar.SECOND) >= 30)
            c.add(Calendar.MINUTE, 1);

        c.set(Calendar.SECOND, 0);

        return c.getTime();
    }

	/**
	 * Returns Java day of the week
	 * 
	 * @param dayNumberStartingMonday
	 * @return
	 */
	private static int getDayOfTheWeek(int dayNumberStartingMonday) {

		int _dayOfTheWeek = Calendar.SUNDAY;
		switch (dayNumberStartingMonday) {
		case 1:
			_dayOfTheWeek = Calendar.MONDAY;

			break;

		case 2:
			_dayOfTheWeek = Calendar.TUESDAY;

			break;

		case 3:
			_dayOfTheWeek = Calendar.WEDNESDAY;

			break;

		case 4:
			_dayOfTheWeek = Calendar.THURSDAY;

			break;

		case 5:
			_dayOfTheWeek = Calendar.FRIDAY;

			break;

		case 6:
			_dayOfTheWeek = Calendar.SATURDAY;

			break;

		case 7:
			_dayOfTheWeek = Calendar.SUNDAY;

			break;
		default:
			break;
		}

		return _dayOfTheWeek;
	}

	public static boolean isValidEmailAddress(String email) {
		boolean result = true;
		try {
			InternetAddress emailAddr = new InternetAddress(email);
			emailAddr.validate();
		} catch (AddressException ex) {
			result = false;
		}
		return result;
	}

	public static String convertDateFormat(Date date) {

		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		String dateStr = formatter.format(date);
		return dateStr;
	}

	public static Date convertStringToDate(String strDate) {

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		Date date = new Date();
		try {
			date = formatter.parse(strDate);
		} catch (ParseException e) {
			formatter = new SimpleDateFormat("MM-dd-yyyy");
			try {
				date = formatter.parse(strDate);
			} catch (ParseException e1) {
				LOGGER.error("====Exception convert to date in bale utils convertStringToDate========"
								+ date + e1);
				
			}
		}

		return date;
	}

	// service channel
	public static Date convertStringToDateSC(String strDate) {

		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		Date date = new Date();
		try {
			date = formatter.parse(strDate);
		} catch (ParseException e) {
			formatter = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
			try {
				date = formatter.parse(strDate);
			} catch (ParseException e1) {
				formatter = new SimpleDateFormat("MM/dd/yyyy");
				try {
					date = formatter.parse(strDate);
				} catch (ParseException e2) {
					formatter = new SimpleDateFormat("MM-dd-yyyy");
					try {
						date = formatter.parse(strDate);
					} catch (ParseException e3) {
						LOGGER.info("====Exception convert to date in bale utils convertStringToDateSC========"
										+ date + e3);
						//e3.printStackTrace();
					}
				}
			}
		}

		return date;
	}

	public static String appendTimetoDate(String strDate) {
		Date date = convertStringToDate(strDate);
		Calendar calDateThen = Calendar.getInstance();
		Calendar calTimeNow = Calendar.getInstance();
		calDateThen.setTime(date);
		calDateThen.set(Calendar.HOUR_OF_DAY,calTimeNow.get(Calendar.HOUR_OF_DAY));
		calDateThen.set(Calendar.MINUTE, calTimeNow.get(Calendar.MINUTE));
		calDateThen.set(Calendar.SECOND, calTimeNow.get(Calendar.SECOND));
		date = calDateThen.getTime();

		DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String reportDate = df.format(date);
		

		return reportDate;
	}

	// for service channel
	public static Date getStartOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}

	public static Date getEndOfDay(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
	
		return calendar.getTime();
	}

	public static Date getDateBefor7Days() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -7);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		Date date = cal.getTime();
		return date;
	}
	
	public static Date getDateAfter7Days() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 7);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		Date date = cal.getTime();
		return date;
	}

	public static Date getLastDateOfPreviousMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DATE, 1);
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date lastDateOfPreviousMonth = cal.getTime();

		return lastDateOfPreviousMonth;
	}
	
	public String getNextWeekMonday() {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.add(Calendar.DATE, +7);
		Date nextWeekMondayDate = cal.getTime();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String nextWeekMonday= df.format(nextWeekMondayDate);
		LOGGER.info("nextWeekMonday Service Channel = "+nextWeekMonday);
		return  nextWeekMonday;
	}

	
	public  String getNextWeekSunday() {
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.add(Calendar.DATE, +7);
		Date nextWeekMondayDate = cal.getTime();
		cal.add(Calendar.DATE, +6);
		Date nextSunday = cal.getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String nextWeekSunday= df.format(nextSunday);
		LOGGER.info("nextWeekSunday Service Channel= "+nextWeekSunday);
		return  nextWeekSunday;
	}
	
	

	public static String getBody(HttpServletRequest request) throws IOException {

		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		
		InputStream inputStream = null;
		try {
			
			inputStream = request.getInputStream();
			

			if (inputStream != null) {
				
				bufferedReader = new BufferedReader(new InputStreamReader(
						inputStream));
				
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				// System.out.println("=====inputStream else==" + inputStream);
				stringBuilder.append("");
			}

		} catch (IOException ex) {
			LOGGER.error("===exception while " + ex);
			throw ex;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					LOGGER.error("===exception inside finally== " + e);
					
				}
			}

			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {
					LOGGER.error("===exception 2 " + ex);
					throw ex;
				}
			}
		}

		body = stringBuilder.toString();
		inputStream.close();
		return body;
	}

	public static void invalidateSession(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		Cookie[] cookies = request.getCookies();

		if (cookies != null)
			for (int i = 0; i < cookies.length; i++) {

				if ("SMSESSION".equals(cookies[i].getName())) {
					cookies[i].setValue("null");
					cookies[i].setPath("/");
					cookies[i].setMaxAge(0);
					cookies[i].setDomain(".wm.com");
					// cookies[i].setComment(MESSAGE +
					// System.currentTimeMillis());
					response.addCookie(cookies[i]);

				} else if ("JSESSIONID".equals(cookies[i].getName())) {

					cookies[i].setValue("null");
					// cookies[i].setPath(cookiePath);
					cookies[i].setMaxAge(0);
					// cookies[i].setDomain(cookieDomain);
					// cookies[i].setComment(MESSAGE +
					// System.currentTimeMillis());
					response.addCookie(cookies[i]);
				} else {
					cookies[i].setValue("");
					cookies[i].setPath("/");
					cookies[i].setMaxAge(0);
					// cookies[i].setComment(MESSAGE +
					// System.currentTimeMillis());
					response.addCookie(cookies[i]);
				}

			}
		session.invalidate();
	}

	public static void writeXLSXFile(HttpServletResponse response)
			throws IOException {

		String excelFileName = "Test.xlsx";// name of excel file

		String sheetName = "Sheet1";// name of sheet

		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = wb.createSheet(sheetName);

		String[] excelHeader = { "BuyCustomer", "StoreName", "PickupDate",
				"SellCustomer", "SellCustomerLocation", "DeliveryDate",
				"ReleaseNumber", "DestinationGross", "DestinationTare",
				"MaterialProfile", "BalesPicked", "BalesRemaining",
				"BOL/TicketNo", "SupplierName", "DriverName", "Incident",
				"Comments" };

		String[] excelSampleData = { "ACME INDUSTRIES", "ACM1234",
				"02/28/2018", "WASTE MANAGEMENT INC.", "HOUSTON", "02/28/2018",
				"12345678", "80000", "40000", "OCC - BALED", "40", "1",
				"98765432", "XYZ LOGISTICS", "JOHN DOE", "BROKEN BALES", "NONE" };

		// style open

		CellStyle style1 = wb.createCellStyle();
		Font font = wb.createFont();
		font.setColor(IndexedColors.BLACK.getIndex());
		font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		style1.setFont(font);

		CellStyle style2 = wb.createCellStyle();
		style2.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		style2.setFillPattern(CellStyle.SOLID_FOREGROUND);

		CellStyle cellStyleDate = wb.createCellStyle();
		cellStyleDate.setFillForegroundColor(IndexedColors.GREY_25_PERCENT
				.getIndex());
		cellStyleDate.setFillPattern(CellStyle.SOLID_FOREGROUND);
		CreationHelper createHelper = wb.getCreationHelper();
		cellStyleDate.setDataFormat(createHelper.createDataFormat().getFormat(
				"MM/dd/yyyy"));

		// style close

		// Header
		XSSFRow row1 = sheet.createRow(0);
		for (int c = 0; c < excelHeader.length; c++) {
			XSSFCell cell = row1.createCell(c);
			cell.setCellValue(excelHeader[c]);
			cell.setCellStyle(style1);
		}

		// sampleRow
		XSSFRow row2 = sheet.createRow(1);
		XSSFCell cell0 = row2.createCell(0);
		cell0.setCellValue("ACME INDUSTRIES");
		cell0.setCellStyle(style2);

		XSSFCell cell1 = row2.createCell(1);
		cell1.setCellValue("ACM1234");
		cell1.setCellStyle(style2);

		XSSFCell cell2 = row2.createCell(2);
		DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		String dateString = "02/28/2018";
		Date dateObject = new Date();
		try {
			dateObject = sdf.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error in BaleUtils : writeXLSXFile  " +e);
		}

		cell2.setCellValue(dateObject);
		cell2.setCellStyle(cellStyleDate);

		// cell2.setCellStyle(cellStyleDate);

		XSSFCell cell3 = row2.createCell(3);
		cell3.setCellValue("WASTE MANAGEMENT INC.");
		cell3.setCellStyle(style2);

		XSSFCell cell4 = row2.createCell(4);
		cell4.setCellValue("HOUSTON");
		cell4.setCellStyle(style2);

		XSSFCell cell5 = row2.createCell(5);
		cell5.setCellValue(dateObject);
		cell5.setCellStyle(cellStyleDate);

		XSSFCell cell6 = row2.createCell(6);
		cell6.setCellValue("12345678");
		cell6.setCellStyle(style2);

		XSSFCell cell7 = row2.createCell(7);
		cell7.setCellValue(80000);
		cell7.setCellStyle(style2);

		XSSFCell cell8 = row2.createCell(8);
		cell8.setCellValue(40000);
		cell8.setCellStyle(style2);

		XSSFCell cell9 = row2.createCell(9);
		cell9.setCellValue("OCC - BALED");
		cell9.setCellStyle(style2);

		XSSFCell cell10 = row2.createCell(10);
		cell10.setCellValue(40);
		cell10.setCellStyle(style2);

		XSSFCell cell11 = row2.createCell(11);
		cell11.setCellValue(1);
		cell11.setCellStyle(style2);

		XSSFCell cell12 = row2.createCell(12);
		cell12.setCellValue(98765432);
		cell12.setCellStyle(style2);

		XSSFCell cell13 = row2.createCell(13);
		cell13.setCellValue("XYZ LOGISTICS");
		cell13.setCellStyle(style2);

		XSSFCell cell14 = row2.createCell(14);
		cell14.setCellValue("JOHN DOE");
		cell14.setCellStyle(style2);

		XSSFCell cell15 = row2.createCell(15);
		cell15.setCellValue("BROKEN BALES");
		cell15.setCellStyle(style2);

		XSSFCell cell16 = row2.createCell(16);
		cell16.setCellValue("NONE");
		cell16.setCellStyle(style2);

		row2.setRowStyle(style2);

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition",
				"attachment; filename=BalePickupSample.xlsx");

		// response.setContentType("Application/x-msexcel");
		OutputStream out = response.getOutputStream();
		wb.write(out);
		out.close();

	}

	/***************************************** Service Channel Code **************************************/

	public static String createAPIUrlForFetchingWorkOrderFromESB(Long subscriberId, Integer offset) {
		ResourceBundle props = EnvironmentDetails.getPropertyFiles();
		BaleUtils baleUtils = new BaleUtils();
		
		String esbUrl = props.getString("brta.sc.esb.url");
	
		String apiUrl = esbUrl + "subscriber_id=" + subscriberId
				+ "&status_primary=" + BaleUtils.sc_primary_status
				+ "&status_extended=" + BaleUtils.sc_extended_status + "&type="
	+ BaleUtils.sc_type + "&limit=50&offset=" + offset +"&from_date="+baleUtils.getNextWeekMonday() +"&to_date="+baleUtils.getNextWeekSunday();
		System.out.println("===apiUrlTest====" + apiUrl);
		return apiUrl;
	}

	
	/*****************************************Sending Back WO to ESB **************************************/
	public static String createAPIUrlForSendingWorkOrdersToESB(Integer WorkOrderNumber){
		
		ResourceBundle props = EnvironmentDetails.getPropertyFiles();
		String sendWOToESBUrl = props.getString("brta.wo.esb.url");
		String BaleToESBUrl =  sendWOToESBUrl + WorkOrderNumber + sc_esb_status;
		
		return BaleToESBUrl;
	}
	
	
	
	
	public static ResponseEntity<?> getResponseFromEsbWorkOrderAPI(String url, HttpMethod methodType, ServiceChannelESBDTO dtoObj) {
		ResponseEntity<?> servicChannelESBDTO = null;
		try{
			String plainCreds = "baleuser:baleuser";
			byte[] plainCredsBytes = plainCreds.getBytes();
			byte[] base64CredsBytes = Base64Utils.encode(plainCredsBytes);
			String base64Creds = new String(base64CredsBytes);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Basic " + base64Creds);
			headers.set("ContentType", "application/json");
			headers.set("Accept", "application/json");
			headers.set("Request-Tracking-Id",
					BaleUtils.generateUniqueIdByCalader());
			headers.set("ProfileId", BaleUtils.getProileIdForESBApi());

			HttpEntity<String> entity = new HttpEntity<String>(headers);
			RestTemplate restTemplate = new RestTemplate();

			
			if (dtoObj instanceof ServiceChannelESBDTO) {
				servicChannelESBDTO = restTemplate.exchange(url,
						HttpMethod.GET, entity, ServiceChannelESBDTO.class);
			}

		}catch(Exception e){
			
			LOGGER.error("Error in BaleUtils : getResponseFromEsbWorkOrderAPI  " +e);
		}
		
		return servicChannelESBDTO;
	}

	
	public static String updateWorkOrderToServiceChannel(String url, HttpMethod methodType, SavedWorkOrdersToServiceChannel dtoObj,String requestTrackingId) {
		String status = "failed";
		Integer id=-1;
		
		try{
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(dtoObj);
			String plainCreds = "baleuser:baleuser";
			byte[] plainCredsBytes = plainCreds.getBytes();
			byte[] base64CredsBytes = Base64Utils.encode(plainCredsBytes);
			String base64Creds = new String(base64CredsBytes);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", "Basic " + base64Creds);
			headers.set("ContentType", "application/json");
			headers.set("Request-Tracking-Id",requestTrackingId);
			headers.set("ProfileId", BaleUtils.getProileIdtoUpdateWorkOrders());
			HttpEntity<SavedWorkOrdersToServiceChannel> entity = new HttpEntity<SavedWorkOrdersToServiceChannel>(dtoObj, headers);
			
			
			RestTemplate restTemplate = new RestTemplate();
			if (dtoObj instanceof SavedWorkOrdersToServiceChannel) {
				
			    HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);
			   	
			     status = ((ResponseEntity<String>) response).getStatusCode().toString();
			     if(status.equals("200")){
			    	status="Passed";
			    }else{
			    	
			    	status="failed";
			    	//id = serviceChannelService.saveFailedServiceChannelWorkOrder(dtoObj);
			    }
			}else{
				
				status="failed";
			//	id = serviceChannelService.saveFailedServiceChannelWorkOrder(dtoObj);
			}

		}catch(Exception e){
			LOGGER.error("===Error while updateWorkOrderToServiceChannel =====" +e);
			status="failed";
		
			
			return status;
		}
		
		return status;
	}
	
	
	
	
	public static String getProileIdForESBApi() {

		ResourceBundle props = EnvironmentDetails.getPropertyFiles();
		String profileId = props.getString("brta.sc.esb.profileid");
	
		return profileId;
	}
	
	public static String getProileIdtoUpdateWorkOrders() {

		ResourceBundle props = EnvironmentDetails.getPropertyFiles();
		String profileId = props.getString("brta.wo.esb.profileid");
		
		return profileId;
	}
	
	

}
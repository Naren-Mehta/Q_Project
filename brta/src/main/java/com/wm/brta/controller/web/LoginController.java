package com.wm.brta.controller.web;

import java.io.IOException;
import java.util.Enumeration;
import java.util.ResourceBundle;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.LoginService;
import com.wm.brta.util.BaleUtils;
import com.wm.brta.util.EnvironmentDetails;
import com.wm.brta.util.XSSUtils;
import com.wm.brta.utility.UserDetailsUtility;

@Controller
public class LoginController {

	@Autowired
	private UserDetailsUtility userDetails;

	@Autowired
	@Qualifier(value = "driverLoginService")
	private LoginService loginService;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LoginController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String getIndexPage(ModelMap map, HttpServletRequest request) {

		UserDTO userDto = new UserDTO();
		String host = "";
		try {
			try {
				Enumeration headerNames = request.getHeaderNames();
				while (headerNames.hasMoreElements()) {
					String headerName = (String) headerNames.nextElement();

				}

				userDto = userDetails.getUserDetails(request);
				host = request.getHeader("host");
				try{
					
					EnvironmentDetails.setEnvName(request);
					ResourceBundle props=EnvironmentDetails.getPropertyFiles();
					String profileId = props.getString("brta.sc.esb.profileid");
					String esbUrl = props.getString("brta.sc.esb.url");
					
				}catch(Exception e){
					
					LOGGER.error("Error Failing whiling fatching environment variable "+e);
				}
				

			} catch (Exception e) {
				LOGGER.info("Error:Login Controller:getUserDetails=====>" + e);
			}

			if (userDto.getRole() == null	&& (host.contains("localhost") || host.contains("127.0.0.1"))) {
				userDto = new UserDTO();
				userDto.setFirstName("nmehta");
				userDto.setRole("ROBRCSR");
				//userDto.setRole("ROBRAPPSUPPORT");

			}

			HttpSession session = request.getSession();

			Double versionNo = BaleUtils.versionNo;
			session.setAttribute("versionNo", versionNo);
			session.setAttribute("user", userDto);
			session.setAttribute("sm_groups",XSSUtils.stripXSS( userDto.getRole()));
			session.setAttribute("role", XSSUtils.stripXSS(userDto.getRole()));

			Gson gson = new Gson();
			map.addAttribute("user", gson.toJson(userDto));
			map.addAttribute("role", XSSUtils.stripXSS(userDto.getRole()));

			LOGGER.info("Logged in user name:" + userDto.getFirstName());
			LOGGER.info("Logged in user role:" + userDto.getRole());

		} catch (Exception e) {
			LOGGER.error("Error:Login Controller:getRole,getFirstName=====>"
					+ e);
		}

		return "landing";
	}

	@RequestMapping(value = "/supplier", method = RequestMethod.GET)
	public String getSupplierIndexPage(ModelMap map,
			HttpServletRequest request, HttpServletResponse response) {
		UserDTO userDto;
		Gson gson = new Gson();
		HttpSession session = request.getSession();

		try {

			userDto = (UserDTO) session.getAttribute("user");

			Integer supplierId = (Integer) session.getAttribute("supplierId");

			String url = request.getRequestURL().toString();
			if (userDto != null && userDto.getRole() != null
					&& userDto.getRole().equals("Supplier")) {
				BaleUtils.invalidateSession(request, response);
				HttpSession session1 = request.getSession(true);
				Double versionNo = BaleUtils.versionNo;
				session1.setAttribute("versionNo", versionNo);
				session1.setAttribute("user", userDto);
				session1.setAttribute("sm_groups", userDto.getRole());
				session1.setAttribute("role", userDto.getRole());
				session1.setAttribute("supplierId", supplierId);
				UserDTO newUserDto = (UserDTO) session1.getAttribute("user");
				map.addAttribute("user", gson.toJson(newUserDto));
				map.addAttribute("role", newUserDto.getRole());
			} else {
				userDto = new UserDTO();
				userDto.setRole("Supplier");
				Double versionNo = BaleUtils.versionNo;
				session.setAttribute("versionNo", versionNo);
				session.setAttribute("user", userDto);
				session.setAttribute("role", XSSUtils.stripXSS(userDto.getRole()));

				map.addAttribute("user", gson.toJson(userDto));
				map.addAttribute("role", XSSUtils.stripXSS(userDto.getRole()));
			}

			LOGGER.info("Logged in user name:" + userDto.getFirstName());
			LOGGER.info("Logged in user role:" + userDto.getRole());

		} catch (Exception e) {
			LOGGER.error("Error:Login Controller:getRole,getFirstName=====>"
					+ e);
		}

		return "landing";
	}

	@RequestMapping(value = "/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

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
		String siteuri = "/";
		response.sendRedirect(siteuri);
	}

	@RequestMapping(value = "/supplier/logoutSupplier")
	public void logoutSupplier(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

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

		String siteuri = "../supplier/";
		response.sendRedirect(siteuri);
	}

	@RequestMapping(value = "/ui/common/getdetails/user", method = RequestMethod.GET)
	@ResponseBody
	public UserDTO getLoggedInUserDetails(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserDTO user = (UserDTO) session.getAttribute("user");
		return user;
	}

	@RequestMapping(value = "/sampleExcelDownload", method = RequestMethod.GET)
	@ResponseBody
	public void sampleExcelDownload(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			BaleUtils.writeXLSXFile(response);
		} catch (Exception e) {
			LOGGER.error("Error: sampleExcelDownload=====>" + e);
		}

	}

	
	@RequestMapping(value = "/supplier/sampleExcelDownload", method = RequestMethod.GET)
	@ResponseBody
	public void sampleExcelDownloadForSupplier(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			BaleUtils.writeXLSXFile(response);
		} catch (Exception e) {
			LOGGER.error("Error: sampleExcelDownload=====>" + e);
		}

	}

}

package com.wm.brta.security;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.google.gson.Gson;
import com.wm.brta.domain.Role;
import com.wm.brta.domain.User;
import com.wm.brta.dto.UserDTO;
import com.wm.brta.service.LoginService;
import com.wm.brta.service.UserService;
import com.wm.brta.util.BaleUtils;

@Component
public class SupplierAuthenticationFilter implements Filter {

	@Autowired
	@Qualifier(value = "supplierLoginService")
	private LoginService supplierLoginService;

	@Autowired
	UserService userService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {

		ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(
				(HttpServletResponse) res);

		MyRequestWrapper multiReadRequest = new MyRequestWrapper(
				(HttpServletRequest) req);

		HttpSession session = multiReadRequest.getSession();

		String url = ((HttpServletRequest) multiReadRequest).getRequestURL()
				.toString();

		UserDTO userDto = (UserDTO) session.getAttribute("user");
		Integer supplierId = (Integer) session.getAttribute("supplierId");
		Long userId = null;
		if (userDto != null) {
			userId = userDto.getUserId();
		}


		Boolean validRequest = true;
		if (url.contains("/ui/assignMgmt/") || url.contains("/ui/tripMgmt/")
				|| url.contains("/ui/common/")
				|| url.contains("/ui/incidentMgmt/")
				|| url.contains("/ui/reportMgmt/")
				|| url.contains("/ui/userMgmt/")) {
			
			
			//test 
			if(url.contains("ui/common/get/sellcustomer/bybuycustomer")){
				
			}
			
			if (userDto != null) {
				if (userDto.getRole() == null || userDto.getFirstName() == null) {
					validRequest = false;
					responseWrapper.setContentType("application/json");
					responseWrapper.setCharacterEncoding("UTF-8");
					responseWrapper
							.setStatus(HttpServletResponse.SC_UNAUTHORIZED,
									"Unauthorized");
				}

			} else {
				validRequest = false;
				responseWrapper.setContentType("application/json");
				responseWrapper.setCharacterEncoding("UTF-8");
				responseWrapper.setStatus(HttpServletResponse.SC_UNAUTHORIZED,
						"Unauthorized");
			}
		}
		
		if (validRequest) {
			if (userDto != null && userDto.getRole() != null
					&& userDto.getRole().equals("Supplier")) {
				if (url.contains("/userMgmt/add")) {

					String payloadRequest = BaleUtils.getBody(multiReadRequest);
					JSONObject jsonObj = new JSONObject(payloadRequest);
					JSONObject jsonObjRole = jsonObj.getJSONObject("role");
					JSONObject jsonObjUser = jsonObj.getJSONObject("user");

					JSONObject jsonObjSupplier = jsonObjUser
							.getJSONObject("supplier");

					Integer supplierIdFromRequest = jsonObjSupplier
							.getInt("supplierId");
				

					Integer roleFromRequest = jsonObjRole.getInt("roleId");

					if (supplierId != null
							&& supplierId.equals(supplierIdFromRequest)) {
						if (roleFromRequest == 1) {
							chain.doFilter(multiReadRequest, res);
						} else {
							responseWrapper.setContentType("application/json");
							responseWrapper.setCharacterEncoding("UTF-8");
							Map map = new HashMap();
							map.put("status",
									HttpServletResponse.SC_UNAUTHORIZED);
							map.put("message", "Unauthorized Supplier");

							String json = new Gson().toJson(map);
							responseWrapper.getWriter().write(json);
							responseWrapper.flushBuffer();
							responseWrapper.getWriter().flush();
							responseWrapper.getWriter().close();

						}

					} else {
						responseWrapper.setContentType("application/json");
						responseWrapper.setCharacterEncoding("UTF-8");
						Map map = new HashMap();
						map.put("status", HttpServletResponse.SC_UNAUTHORIZED);
						map.put("message", "Unauthorized Supplier");

						String json = new Gson().toJson(map);
						responseWrapper.getWriter().write(json);
						responseWrapper.flushBuffer();
						responseWrapper.getWriter().flush();
						responseWrapper.getWriter().close();

					}
				} else if (url.contains("/userMgmt/edit")) {
					String payloadRequest = BaleUtils.getBody(multiReadRequest);
					JSONObject jsonObj = new JSONObject(payloadRequest);
					JSONObject jsonObjRole = jsonObj.getJSONObject("role");
					JSONObject jsonObjSupplier = jsonObj
							.getJSONObject("supplier");

					Integer roleId=jsonObj.getInt("roleId");
					Integer supplierIdFromRequest = jsonObjSupplier
							.getInt("supplierId");
					

					Integer roleFromRequest = jsonObjRole.getInt("roleId");

					if (supplierId != null
							&& supplierId.equals(supplierIdFromRequest)) {
						
						if (roleId==1 && roleFromRequest == 1) {
							chain.doFilter(multiReadRequest, res);
						} else {
							responseWrapper.setContentType("application/json");
							responseWrapper.setCharacterEncoding("UTF-8");
							Map map = new HashMap();
							map.put("status",
									HttpServletResponse.SC_UNAUTHORIZED);
							map.put("message", "Unauthorized Supplier");

							String json = new Gson().toJson(map);
							responseWrapper.getWriter().write(json);
							responseWrapper.flushBuffer();
							responseWrapper.getWriter().flush();
							responseWrapper.getWriter().close();

						}

					} else {
						/*
						 * responseWrapper
						 * .setStatus(HttpServletResponse.SC_UNAUTHORIZED
						 * ,"Unauthorized Supplier");
						 */
						responseWrapper.setContentType("application/json");
						responseWrapper.setCharacterEncoding("UTF-8");
						Map map = new HashMap();
						map.put("status", HttpServletResponse.SC_UNAUTHORIZED);
						map.put("message", "Unauthorized Supplier");

						String json = new Gson().toJson(map);
						responseWrapper.getWriter().write(json);
						responseWrapper.flushBuffer();
						responseWrapper.getWriter().flush();
						responseWrapper.getWriter().close();
					}
				} else if (url.contains("/userMgmt/get/user/byfilters")) {
					String payloadRequest = BaleUtils.getBody(multiReadRequest);
					JSONObject jsonObj = new JSONObject(payloadRequest);
					Integer supplierIdFromRequest = (Integer) jsonObj
							.getInt("supplierFilter");
					if (supplierId != null
							&& supplierId.equals(supplierIdFromRequest)) {
						chain.doFilter(multiReadRequest, res);

					} else {
						responseWrapper.setContentType("application/json");
						responseWrapper.setCharacterEncoding("UTF-8");
						Map map = new HashMap();
						map.put("status", HttpServletResponse.SC_UNAUTHORIZED);
						map.put("message", "Unauthorized Supplier");

						String json = new Gson().toJson(map);
						responseWrapper.getWriter().write(json);
						responseWrapper.flushBuffer();
						responseWrapper.getWriter().flush();
						responseWrapper.getWriter().close();
					}
				} else if (url.contains("/tripMgmt/add/tripdetails")) {
					String payloadRequest = BaleUtils.getBody(multiReadRequest);
					JSONObject jsonObj = new JSONObject(payloadRequest);
					Long userIdFromRequest = (Long) jsonObj.getLong("userId");

					User user = userService
							.getUserObjectFromUserId(userIdFromRequest);
					Integer supplierIdFromRequestedUser = null;
					
					Boolean isValidaUser=false;
					if (user != null && user.getSupplier() != null) {
						supplierIdFromRequestedUser = user.getSupplier()
								.getSupplierId();
						
						List<Role> roles = userService.getAllRoleFromUser(user, "Driver");
						if(roles!=null && roles.size() >0){
							isValidaUser=true;
						}
					}


					if (supplierIdFromRequestedUser != null
							&& supplierId != null && isValidaUser 
							&& supplierId.equals(supplierIdFromRequestedUser)) {
						chain.doFilter(multiReadRequest, res);
					} else {
						responseWrapper.setContentType("application/json");
						responseWrapper.setCharacterEncoding("UTF-8");
						Map map = new HashMap();
						map.put("status", HttpServletResponse.SC_UNAUTHORIZED);
						map.put("message", "Unauthorized Supplier");

						String json = new Gson().toJson(map);
						responseWrapper.getWriter().write(json);
						responseWrapper.flushBuffer();
						responseWrapper.getWriter().flush();
						responseWrapper.getWriter().close();
					}
				} else if (url.contains("/userMgmt/get/driverOnly")) {
					String payloadRequest = BaleUtils.getBody(multiReadRequest);
					
					Integer supplierIdFromRequest = Integer
							.parseInt(payloadRequest);
					
					if (supplierId != null
							&& supplierId.equals(supplierIdFromRequest)) {
						chain.doFilter(multiReadRequest, res);

					} else {
						responseWrapper.setContentType("application/json");
						responseWrapper.setCharacterEncoding("UTF-8");
						Map map = new HashMap();
						map.put("status", HttpServletResponse.SC_UNAUTHORIZED);
						map.put("message", "Unauthorized Supplier");

						String json = new Gson().toJson(map);
						responseWrapper.getWriter().write(json);
						responseWrapper.flushBuffer();
						responseWrapper.getWriter().flush();
						responseWrapper.getWriter().close();
					}
				} else if (url.contains("/assignMgmt/getAll/storeSupplierList")
						|| url.contains("/assignMgmt/display/driversDashboard")
						|| url.contains("/ui/common/getAll/pickUpsForDARAndRMT")
						|| url.contains("/ui/common/getAll/pickUps")
						|| url.contains("/common/get/customersitefrombuycustomerandsupplier")
						|| url.contains("/ui/reportMgmt/getAll/pendingReports")) {
					String payloadRequest = BaleUtils.getBody(multiReadRequest);
					JSONObject jsonObj = new JSONObject(payloadRequest);
					Integer supplierIdFromRequest = (Integer) jsonObj
							.getInt("supplierId");
					if (supplierId != null
							&& supplierId.equals(supplierIdFromRequest)) {
						chain.doFilter(multiReadRequest, res);
					} else {
						responseWrapper.setContentType("application/json");
						responseWrapper.setCharacterEncoding("UTF-8");
						Map map = new HashMap();
						map.put("status", HttpServletResponse.SC_UNAUTHORIZED);
						map.put("message", "Unauthorized Supplier");

						String json = new Gson().toJson(map);
						responseWrapper.getWriter().write(json);
						responseWrapper.flushBuffer();
						responseWrapper.getWriter().flush();
						responseWrapper.getWriter().close();
					}
				} else if (url.contains("/assignMgmt/update/drivers")) {
					String payloadRequest = BaleUtils.getBody(multiReadRequest);
					JSONArray jsonarray = new JSONArray(payloadRequest);
					Boolean status = true;

					for (int i = 0; i < jsonarray.length(); i++) {
						JSONObject jsonobject = jsonarray.getJSONObject(i);
						Integer supplierIdFromRequest = (Integer) jsonobject
								.getInt("supplierId");
						if (supplierId != null
								&& !supplierId.equals(supplierIdFromRequest)) {
							status = false;
							break;
						}
					}

					if (status) {
						chain.doFilter(multiReadRequest, res);
					} else {
						responseWrapper.setContentType("application/json");
						responseWrapper.setCharacterEncoding("UTF-8");
						Map map = new HashMap();
						map.put("status", HttpServletResponse.SC_UNAUTHORIZED);
						map.put("message", "Unauthorized Supplier");

						String json = new Gson().toJson(map);
						responseWrapper.getWriter().write(json);
						responseWrapper.flushBuffer();
						responseWrapper.getWriter().flush();
						responseWrapper.getWriter().close();
					}

				} else {
					chain.doFilter(multiReadRequest, res);
				}

			} else {
				chain.doFilter(multiReadRequest, res);
			}
		}


	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}

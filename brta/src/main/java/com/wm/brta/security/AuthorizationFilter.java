package com.wm.brta.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
public class AuthorizationFilter implements Filter {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationFilter.class);
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

		String url = ((HttpServletRequest) multiReadRequest).getRequestURL()
				.toString();
		


		String queryString = ((HttpServletRequest) multiReadRequest)
				.getQueryString();

		if (url.contains("/app/supplier/")||url.contains("/app/service")
				|| url.contains("/landing.jsp") ||  url.contains("/app/ui/resources")
				||  url.contains("/logout")||  url.contains("/app/ui/views") ||url.contains("/common") || url.endsWith("/app/")
				|| url.endsWith("/app")|| url.contains("/get/")|| url.contains("/app/")){

			try{
				chain.doFilter(multiReadRequest, res);
			}catch(Exception e){
				LOGGER.error("===Exception in Filter if===="+e);
			}
			
		}else{

			
			HttpSession session = session();
			String role = (String) session.getAttribute("sm_groups");
			
			if(role!=null){
				if (url.contains("/app/incidentMgmt")) {
					if(role.equals("ROBRMANAGER")){
						chain.doFilter(multiReadRequest, res);
					}else{
						responseWrapper.sendError(HttpServletResponse.SC_UNAUTHORIZED,
								"Unauthorized");
					}
				} else if(url.contains("/app/reportMgmt")){
					chain.doFilter(multiReadRequest, res);
				}else{
					if(role.equals("ROBRCSR")){
						chain.doFilter(multiReadRequest, res);
					}if(role.equals("ROBRSUPPLIER")){
						chain.doFilter(multiReadRequest, res);
					}else{
						//System.out.println("====Else else===");
						responseWrapper.sendError(HttpServletResponse.SC_UNAUTHORIZED,
								"Unauthorized");
					}
				}

			}else{
				//chain.doFilter(multiReadRequest, res);
				responseWrapper.sendError(HttpServletResponse.SC_UNAUTHORIZED,
						"Unauthorized");
			}
		}

	}

	@Override
	public void destroy() {

	}

	public static HttpSession session() {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder
				.currentRequestAttributes();
		return attr.getRequest().getSession(true); // true == allow create
	}
	
	
	

}
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

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.wm.brta.util.BaleUtils;
import com.wm.brta.util.XSSUtils;

@Component
public class XSSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {


		MyRequestWrapper multiReadRequest = new MyRequestWrapper(
				(HttpServletRequest) req);

		
		String url = ((HttpServletRequest) multiReadRequest).getRequestURL()
				.toString();
		
		if(url!=null && url!="" && !url.contains("/app/ui/resources/") && !url.contains("/app/ui/views/")){
			String body = BaleUtils.getBody(multiReadRequest);
			
			try {
				if (body != null && body.trim() != "" && body.length() > 0
						&& body.startsWith("{") && body.endsWith("}")) {

					String newBody = XSSUtils.stripXSS(body);
					ResettableStreamHttpServletRequest wrappedRequest = new ResettableStreamHttpServletRequest(
							(HttpServletRequest) multiReadRequest,
							newBody.getBytes());

					wrappedRequest.resetInputStream();

					MyRequestWrapper multiReadRequest1 = new MyRequestWrapper(
							(HttpServletRequest) wrappedRequest);
					String body1 = BaleUtils.getBody(multiReadRequest1);

					ResponseWrapper responseWrapper = new ResponseWrapper(
							(HttpServletResponse) res);

					chain.doFilter(multiReadRequest1, responseWrapper);

					String responseContent = new String(
							responseWrapper.getDataStream());
					
					String newResponseBody = XSSUtils.stripXSS(responseContent);

					byte[] responseToSend = responseContent.getBytes();

					res.getOutputStream().write(responseToSend);

				} else {
					chain.doFilter(multiReadRequest, res);
				}
			} catch (Exception e) {
				e.printStackTrace();
				chain.doFilter(req, res);
			}
		}else{
			chain.doFilter(req, res);
		}
		
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}

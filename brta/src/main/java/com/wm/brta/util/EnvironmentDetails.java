package com.wm.brta.util;

import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentDetails {

	public static String environmentName = "DEV";
	private static final Logger logger = LoggerFactory.getLogger(EnvironmentDetails.class);
	
	
	public static void setEnvName(HttpServletRequest request) {
		try{
		String env = System.getProperty("environment") != null ? System.getProperty("environment") : "DEV2" ;
		
		if (env.equalsIgnoreCase("PROD")) {
			environmentName = "PROD";
		} else if (env.equalsIgnoreCase("QA2")) {
			environmentName = "QA2";
		}else{
			environmentName = "DEV2";
		}
		}catch(Exception e){
			logger.error("Error in setEnvName  "+e);
		}
	}

	public static ResourceBundle getPropertyFiles() {
		ResourceBundle props = ResourceBundle.getBundle("application-dev2");
		if (EnvironmentDetails.environmentName.equals("PROD")) {
			props = ResourceBundle.getBundle("application-prod");
		} else if (EnvironmentDetails.environmentName.equals("QA2")) {
			props = ResourceBundle.getBundle("application-qa2");
		}
			
		return props;
	}
}

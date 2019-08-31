package com.wm.brta.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigValues {
	
	private static Properties properties;
	private static Properties templates;
	

	static {
		// Load Configuration Values from Properties File to Memory
		ConfigValues configValues = new ConfigValues();
	properties = configValues.loadProperties("config.properties");
	
	}

	public static String getConfigValue(String key) {
		String value = "";
		synchronized (properties) {
			String appEnv = properties.getProperty("APP_ENV");
			value = properties.getProperty(key);
		}
	//	System.out.println("Value is:"+value);
		return value;
	}
	
	
	private Properties loadProperties(String configFilePath) {
		Properties prop = new Properties();
		InputStream inputstream = null;
		try {
			inputstream = getClass().getResourceAsStream(configFilePath);
			prop.load(inputstream);
		} catch (Exception e) {

		} finally {
			try {
				if (inputstream != null) {
					inputstream.close();
				}
			} catch (Exception ex) {
			}
		}
		return prop;
	}

}

package com.settings;

import java.io.InputStream;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import com.udp.io.Log4j;

/**
 * Read the server's configurations from the file stored in the classpath.
 * @author Nicolae
 *
 */
public class ConfigurationsManager {
	
	Logger log = Log4j.initLog4j(ConfigurationsManager.class);
	
	String configFile = "config.properties";	
	String configValue = "";
	InputStream inputStream = null;
	
	/**
	 * Get from the configuration file the value for the specified field.
	 * 
	 * @param field the resource that interests us
	 * @return      the value of the resource
	 */
	public String readConfigValue(String field) {			
		try {			
			PropertiesConfiguration config = new PropertiesConfiguration(configFile);
			configValue = config.getString(field);
		} catch (Exception e) {
			log.error("Exception in readConfigValue() function, ConfigurationsManager class : " + e.getMessage());
		} 
		
		return configValue;
	}
	
	/**
	 * Set configuration data to the configuration file.
	 * 
	 * @param field the resource
	 * @param value the resource value
	 */
	public void setConfigValue(String field, String value) {
		try {
			PropertiesConfiguration config = new PropertiesConfiguration(configFile);
			config.setProperty(field, value);
			config.save();
		} catch (ConfigurationException e) {
			log.error("Exception in setConfigValue() function, ConfigurationsManager class : " + e.getMessage());
			e.printStackTrace();
		}
	}
}

package com.udp.io;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log4j {
	
	public static <T> Logger initLog4j(Class<T> clazz) {
		
		Logger log = Logger.getLogger(clazz);
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		PropertyConfigurator.configure(classLoader.getResourceAsStream("log4j.properties") );
		
		return log;
	}

}

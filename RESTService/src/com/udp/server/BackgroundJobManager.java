package com.udp.server;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.udp.io.Log4j;
import org.apache.log4j.Logger;


/**
 * Defines the business logic for receiving messages 
 * from the Arduino board and recording them into
 * the Database
 * 
 * @author sscerbatiuc
 *
 */
public class BackgroundJobManager implements ServletContextListener{
	
	private final Logger log = Log4j.initLog4j(BackgroundJobManager.class);
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	
	/**
	 * Initializes the thread responsible for receiving 
	 * messages from the Arduino board and configures it to 
	 * run once in a specified interval.
	 * @Override
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		log.info("BACKGROUND MANAGER STARTED");
		System.out.println("\nBACKGROUND MANAGER STARTED\n");
		scheduler.scheduleAtFixedRate(new SensorJob(), 0, 5, TimeUnit.SECONDS);
	}
	
	/**
	 * Shuts down the <code>ScheduledExecutorService</code> when
	 * the <code>Servlet</code> context is being destroyed
	 * @Override
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		scheduler.shutdown();
	}
}

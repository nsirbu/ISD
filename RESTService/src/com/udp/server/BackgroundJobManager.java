package com.udp.server;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Contains the initializing code for tasks that need
 * to be run in background.
 * @author stas
 *
 */
public class BackgroundJobManager implements ServletContextListener{
	
	private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		scheduler.scheduleAtFixedRate(new SensorJob(), 0, 60, TimeUnit.SECONDS);
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		scheduler.shutdown();
	}

	

}

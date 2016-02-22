package com.udp.server;

import java.text.ParseException;

import com.udp.io.Log4j;

import org.apache.log4j.Logger;

import com.settings.ConfigurationsManager;
import com.model.Message;
import com.database.DBQuery;
import com.udp.helper.Constants;
import com.udp.helper.TimeHelper;

import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * This class is responsible for logging the events related to the 
 * heart-beat message. Basically, it detects if the heart-beat message 
 * @author sscerbatiuc
 *
 */
public class HeartBeatJob implements Runnable {
	
	private volatile boolean running = true;
	private ConfigurationsManager confManager;
	private final Logger logger = Log4j.initLog4j(HeartBeatJob.class);
	
	// Default Constructor
	public HeartBeatJob() {
		confManager = new ConfigurationsManager();
	}
	
	/**
	 * Detects whether the heart-beat message has been received in the specified 
	 * period
	 * @return <code>boolean</code>
	 */
	public boolean isHeartBeatOnTime() {
		
		Message lastMsg = DBQuery.getLastEntry();
		try {
			long maxHbRate = Long.parseLong(confManager.readConfigValue("HBFrequency"));
			
			if(TimeHelper.howMuchMillisSince(lastMsg.getTimeReceived()) < 
					MILLISECONDS.convert(maxHbRate, SECONDS)){
				return Constants.HEARTBEAT_OK;
			} else 
				return Constants.HEARTBEAT_IS_LATE;
			
		} catch (ParseException e) {
			logger.error("Couldn't parse time received String");
			return false;
		}
	}
	
	@Override
	public void run(){
		if(running){
			try {
				if(!this.isHeartBeatOnTime()){
					logger.warn("Last message was late. Heart-beat message hasn't arrived at the specified time");
				}
			} catch (Exception e) {
				running = false;
				logger.error("Couldn't check heart-beat message frequency");
			}
		}
	}

}

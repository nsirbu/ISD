package com.udp.server;

import org.apache.log4j.Logger;

import com.udp.io.Log4j;

import isd.database.DBQuery;
import isd.model.Message;

/**
 * This class is responsible for receiving the 
 * sensors' state information from the Arduino Board.
 * The main logic is implemented in the <code>run()</code>
 * method, ensuring that this activity will be executed in 
 * a separated thread.
 * @author sscerbatiuc
 *
 */
public class SensorJob implements Runnable{
	
	private Server server;
	private final Logger logger = Log4j.initLog4j(SensorJob.class);

	/**
	 * Defines the way the data should be received from the
	 * Arduino Board. The method logs the errors to the log file.
	 * @Override
	 */
	public void run() {
		try {
			server = Server.getInstance();
			while(true){
				try {
					Message receivedMessage = server.readMessage();
					if(receivedMessage != null){
						int queryAffectedRows = DBQuery.recordMessage(receivedMessage);
						if(queryAffectedRows <= 0){
							logger.error("Message couldn't be recorded into the database");
						}
					} else {
						logger.error("Received null message from the Arduino board");
					}
					
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
	}

}

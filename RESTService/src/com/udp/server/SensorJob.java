package com.udp.server;

import org.apache.log4j.Logger;

import com.database.DBQuery;
import com.model.Message;
import com.udp.io.Log4j;

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
	
	public SensorJob(){
		try {
			server = Server.getInstance();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}
	/**
	 * Defines the way the data should be received from the
	 * Arduino Board. The method logs the errors to the log file.
	 * @Override
	 */
	public void run() {
		try {
			while(true){
				try {
					Message receivedMessage = server.readMessage();
					if(receivedMessage != null){
						int queryAffectedRows = DBQuery.recordMessage(receivedMessage);
						System.out.println(receivedMessage.toString());
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

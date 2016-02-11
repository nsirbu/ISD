package com.udp.server;

import isd.database.DBQuery;
import isd.model.Message;

public class SensorJob implements Runnable{
	
	private Server server;

	@Override
	public void run() {
		try {
			server = Server.getInstance();
			while(true){
				Message receivedMessage = server.readMessage();
				DBQuery.recordMessage(receivedMessage);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}

package protocol;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import isd.model.Message;

public class MessageTest {
	
	/**
	 * Method that tests the behavior of the message parsing
	 */
	@Test
	public void MessageParseTest(){
		
		String receivedString	= "P 1|L 130|H 1";
		Message receivedMessage = Message.parse(receivedString);
		String timeReceived 	= receivedMessage.getTimeReceived();
		assertEquals(true, receivedMessage.getPirSensorVal());
		assertEquals(130, receivedMessage.getLightSensorVal());
		assertEquals(true, receivedMessage.isHeartbeat());
		assertEquals(timeReceived, receivedMessage.getTimeReceived());
		
	}

}

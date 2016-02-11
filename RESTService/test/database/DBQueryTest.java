package database;

import org.junit.Assert;
import org.junit.Test;
import isd.database.DBQuery;
import isd.model.Message;

/**
 * 
 * @author Nicolae
 *
 */
public class DBQueryTest {
	
	@Test
	public void getLastEntryShouldReturnTheLastEntryInDB() {
		Message actualVersion = new Message(false, true, 111, "2020-11-30 00:00:00.0");
		Message expectedVersion = DBQuery.getLastEntry();
		Assert.assertEquals(actualVersion.isHeartbeat(), expectedVersion.isHeartbeat()); 
		Assert.assertEquals(actualVersion.getTimeReceived(), expectedVersion.getTimeReceived()); 
		Assert.assertEquals(actualVersion.getLightSensorVal(), expectedVersion.getLightSensorVal()); 
		Assert.assertEquals(actualVersion.getPirSensorVal(), expectedVersion.getPirSensorVal()); 
	}
}

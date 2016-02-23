package database;

import org.junit.Assert;
import org.junit.Test;

import com.database.DBQuery;
import com.model.Message;

/**
 * 
 * @author Nicolae
 *
 *         Test case for query execution on database.
 */
public class DBQueryTest {

	@Test
	public void getLastEntryShouldReturnTheLastEntryInDB() {
		Message actualVersion = new Message(false, true, 111, "2020-11-30 00:00:00.0");
		Message expectedVersion = DBQuery.getLastEntry();
		Assert.assertEquals(expectedVersion.isHeartbeat(), actualVersion.isHeartbeat());
		Assert.assertEquals(expectedVersion.getTimeReceived(), actualVersion.getTimeReceived());
		Assert.assertEquals(expectedVersion.getLightSensorVal(), actualVersion.getLightSensorVal());
		Assert.assertEquals(expectedVersion.getPirSensorVal(), actualVersion.getPirSensorVal());
	}
}

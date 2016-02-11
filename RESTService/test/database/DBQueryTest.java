package database;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

import isd.database.DBQuery;
import isd.model.Message;

public class DBQueryTest {
	
	@Test
	public void getLastEntryShouldReturnAnMessage() {
		 DBQuery test = Mockito.mock(DBQuery.class);
		 Message message = new Message(false, false, 111, "2020-11-30 00:00:00.0");
		  
		  // define return value for method getUniqueId()
		  when(test.getLastEntry()).thenReturn(message);
		  
		  // use mock in test.... 
		  assertEquals(test.getLastEntry(), message);
	}
}

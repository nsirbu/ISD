package com.udp.helper;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.text.ParseException;


public class TimeTest {
	
	@Test
	public void testDifference(){
		String timeString  = "2015-11-20 14:19:06.0";
		String timeString2 = "2015-11-20 14:19:11.2";
		String timeString3 = "2015-11-20 14:19:15.0";
		String timeString4 = "2015-11-20 14:19:16.0";
		
		try {
			assertEquals(5002, TimeHelper.getDifference(timeString2, timeString));
			assertEquals(3998, TimeHelper.getDifference(timeString3, timeString2));
			assertEquals(1000, TimeHelper.getDifference(timeString4, timeString3));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}

}

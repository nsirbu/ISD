package com.udp.helper;

import org.junit.Assert;
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
			assertEquals(5002, TimeHelper.getDifference("yyyy-MM-dd HH:mm:ss.S", timeString2, timeString));
			assertEquals(3998, TimeHelper.getDifference("yyyy-MM-dd HH:mm:ss.S", timeString3, timeString2));
			assertEquals(1000, TimeHelper.getDifference("yyyy-MM-dd HH:mm:ss.S", timeString4, timeString3));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test 
	public void createDateTimeWithFormatTest() {
		String dateToTest = "2016-02-16 08:00:00";
		String expectedDate = "2016-02-16";
		String expectedHour = "08:00:00";
		
		String actualDate = TimeHelper.createDateTimeWithFormat("yyyy-MM-dd", dateToTest);
		String actualHour = TimeHelper.createDateTimeWithFormat("HH:mm:ss", dateToTest);
		
		Assert.assertEquals(expectedDate, actualDate);
		Assert.assertEquals(expectedHour, actualHour);
	}
}

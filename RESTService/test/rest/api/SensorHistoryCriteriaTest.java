package rest.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.rest.api.SensorHistoryCriteria;
import com.udp.helper.TimeHelper;

/**
 * 
 * @author Nicolae
 *
 *         Test cases for the methods used to show statistical data.
 */
public class SensorHistoryCriteriaTest {

	@Test
	public void filterLuminosityStatisticsMapShouldReturnAMapWithMaxAndAvgValue_Test1() {
		HashMap<String, Integer> actualMap = new HashMap<String, Integer>();
		actualMap.put("date_1", 100);
		actualMap.put("date_2", 200);
		actualMap.put("date_3", 300);
		actualMap.put("date_4", 400);
		actualMap.put("date_5", 500);
		HashMap<String, Integer> expectedMap = SensorHistoryCriteria.filterLuminosityStatisticsMap(actualMap);

		Assert.assertEquals(expectedMap.size(), 2);
		int maxValue = expectedMap.get("maxValue");
		int avgValue = expectedMap.get("avgValue");
		Assert.assertEquals(500, maxValue);
		Assert.assertEquals(300, avgValue);
	}

	@Test
	public void filterLuminosityStatisticsMapShouldReturnAMapWithMaxAndAvgValue_Test2() {
		HashMap<String, Integer> actualMap = new HashMap<String, Integer>();
		HashMap<String, Integer> expectedMap = SensorHistoryCriteria.filterLuminosityStatisticsMap(actualMap);

		Assert.assertEquals(expectedMap.size(), 2);
		int maxValue = expectedMap.get("maxValue");
		int avgValue = expectedMap.get("avgValue");
		Assert.assertEquals(0, maxValue);
		Assert.assertEquals(0, avgValue);
	}

	@Test
	public void calculateAverageShouldReturnTheAverageOfMapElements() {
		HashMap<String, Integer> actualMap = new HashMap<String, Integer>();
		actualMap.put("date_1", 100);
		actualMap.put("date_2", 200);
		actualMap.put("date_3", 300);
		actualMap.put("date_4", 400);
		actualMap.put("date_5", 500);

		double avgValue = SensorHistoryCriteria.calculateAverage(actualMap);

		Assert.assertEquals(300.0, avgValue, 2);
	}

	@Test
	public void getDatesBetweenTwoDatesShoulReturnTheDatesBetweenTwoGivenDates() {
		List<LocalDate> actualTotalDates = new ArrayList<>();
		actualTotalDates.add(LocalDate.parse("2016-02-14"));
		actualTotalDates.add(LocalDate.parse("2016-02-15"));
		actualTotalDates.add(LocalDate.parse("2016-02-16"));

		List<LocalDate> expectedTotalDates = TimeHelper.getDatesBetweenTwoDates("2016-02-14", "2016-02-16");

		Assert.assertEquals(expectedTotalDates.size(), actualTotalDates.size());
		Assert.assertEquals(expectedTotalDates.get(0), LocalDate.parse("2016-02-14"));
		Assert.assertEquals(expectedTotalDates.get(1), LocalDate.parse("2016-02-15"));
		Assert.assertEquals(expectedTotalDates.get(2), LocalDate.parse("2016-02-16"));
	}
}

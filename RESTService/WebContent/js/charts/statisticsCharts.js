/**
 * This file contains the methods necessary for displaying the sensors'
 * statistic data using charts
 * 
 * @author: sscerbatiuc
 */
// ---------------------------------------------------------------------
// -------------------- GLOBAL VARIABLES -------------------------------
// ---------------------------------------------------------------------
var LIGHT_LEVEL_CHART = "light_levels";
var ACTIVITY_LEVEL_CHART = "activity_chart";
var LIGHT_DURATION_CHART = "light_duration";

var STATISTIC_AREA_VISIBLE = false;

// --------------------- TIME VARIABLES --------------------------------
var TIME_FORMAT = "YYYY-MM-DD HH:mm:ss";

// ------------------- CLEAR HELPER VARIABLES ---------------------------
var CLEAR_DATE_TIME_PICKERS = "date_time_pickers";

// ---------------------------------------------------------------------

/**
 * INITIALISATION CODE block
 */
$(document).ready(function() {

	$("#dtpickerStart").datetimepicker();
	$("#dtpickerStart").data("DateTimePicker").options({
		format : TIME_FORMAT
	});
	$("#dtpickerStart").on("dp.change", function() {
		displayChart();
	})
	$("#dtpickerEnd").datetimepicker();
	$("#dtpickerEnd").data("DateTimePicker").options({
		format : TIME_FORMAT
	});
	$("#dtpickerEnd").on("dp.change", function() {
		displayChart();
	})
});

/**
 * Displays the selected value in the drop-down element.
 */
$(function() {
	$(".dropdown-menu li a").click(function() {

		$(".btn:first-child").text($(this).text())
		$(".btn:first-child").attr("data-chart", $(this).attr("data-chart"));
		$(".btn:first-child").val($(this).text());
		displayStatisticsControls();
	});
});

/**
 * Displays the statistics area, date-time pickers for selecting the interval
 * and statistic's description.
 */
function displayStatisticsControls() {

	showStatisticArea();
	var chartType = $("#dropDown_statistics").attr("data-chart");
	switch (chartType) {
	case LIGHT_LEVEL_CHART: { /* lws - Light levels week statistic */

		clear(CLEAR_DATE_TIME_PICKERS);
		$("#statistic_description")
				.html(
						"This is a light level statistic chart. More description coming soon.");
		// setTimeout(function() {
		// document.querySelector('.ct-chart').__chartist__.update();
		// }, 400);
		break;
	}
	case ACTIVITY_LEVEL_CHART: {

		$("#statistic_description")
				.html(
						"This is the activity level statistic chart. More description coming soon.");
		clear(CLEAR_DATE_TIME_PICKERS);
		// motionPieChartWeek();
		break;
	}
	case LIGHT_DURATION_CHART: {

		clear(CLEAR_DATE_TIME_PICKERS);
		$("#statistic_description").html(
				"This is the light duration statistic chart");
		break;
	}
	default:
		break;
	}

}

/**
 * Checks if the selected time interval is correct, gets the chart data from the
 * server and displays it.
 */
function displayChart() {

	var startDate = $("#dtpicker_start_date").val();
	var endDate = $("#dtpicker_end_date").val();
	var datesSelected = ((startDate !== "") && (endDate !== ""));
	var dateIntervalValid = false;
	if (datesSelected) {
		/**
		 * Check if the selected interval is correct (start date IS EARLIER than
		 * the end date)
		 */
		dateIntervalValid = validateDateInterval(startDate, endDate);
		console.log(dateIntervalValid);
	}
	if (dateIntervalValid) {
		drawChart(startDate, endDate);
	}
}

/**
 * Displays the statistics' section, which is expanded when the user selects a
 * value from the drop-down.
 */

function showStatisticArea() {
	if (!STATISTIC_AREA_VISIBLE) {
		$('#statDescription').collapse('toggle');
		STATISTIC_AREA_VISIBLE = true;
	}
}

// ----------------------------------------------------------------------------
// --------------------- HELPER METHODS --------------------------------------
// ----------------------------------------------------------------------------
/**
 * Parses the date String and returns the request
 * 
 * @param mySqlDate
 * @param format -
 *            String representing the format of the date. Accepted formats: <br>
 *            "dd/MM/yyyy" - 11/01/2016; <br>
 *            "dd/MM" - 11/01; <br>
 *            "dd/MM/yy" - 11/01/2016
 * @returns {String} <b>Default:</b> "dd/MM" - 11/01
 */
function parseDate(mySqlDate, format) {

	var dateArray = mySqlDate.split("-");
	var year = dateArray[0];
	var month = dateArray[1];
	var day = dateArray[2];
	switch (format) {
	case "dd/MM":
		return day + "/" + month;
	case "dd/MM/yyyy":
		return day + "/" + month + "/" + year;
	case "dd/MM/yy":
		return day + "/" + month + "/" + year.substring(2, 3);
	default:
		return day + "/" + month;
	}
}

/**
 * Verifies if the start date is earlier than the end date in a given interval.
 * 
 * @param startDate
 *            {String} - the first date of a given interval
 * @param endDate
 *            {String} - the last date of a given interval
 */
function validateDateInterval(startDate, endDate) {
	return moment(startDate).isBefore(endDate);
}

/**
 * Clears the value stored in the HTML element. This method accepts short-codes
 * for clearing a given set of elements: clear("date_time_pickers") will clear
 * the value of date-time-pickers
 * 
 * @param element
 *            {String}
 */
function clear(element) {
	switch (element) {
	case CLEAR_DATE_TIME_PICKERS: {
		$("#dtpicker_start_date").val('');
		$("#dtpicker_end_date").val('');
		break;
	}
	default:
		$(element).val('');
		break;
	}

}

// ----------------------------------------------------------------------------
// ------------------ SENSORS' DATA PARSERS ----------------------------------
// ----------------------------------------------------------------------------
/**
 * Parses the received information into a chart data object
 * 
 * @param receivedData -
 *            JSON object (format:
 *            [{"date":"2016-02-15","avgValue":111,"maxValue":111}])
 * 
 * @return chartistDataObject - object containing the labels & data necessary to
 *         build the chart
 */
function parseLightMaxAvgData(receivedData) {
	// Initialization block
	var chartLabels = [];
	var chartSeries = [];
	chartSeries[0] = [];
	chartSeries[1] = [];
	var chartDataObject = {};
	var recDataLength = receivedData.length;

	for (var int = 0; int < recDataLength; int++) {

		var recDataObject = receivedData[int];
		chartLabels[int] = parseDate(recDataObject.date, "dd/MM");
		chartSeries[0][int] = recDataObject.avgValue;
		chartSeries[1][int] = recDataObject.maxValue;
	}
	chartDataObject.labels = chartLabels;
	chartDataObject.series = chartSeries;
	return chartDataObject;
}

/**
 * Parses the data received from the server & converts it to the Chartist
 * Object, containing labels & series, necessary for rendering the chart.
 * 
 * @param receivedData -
 *            JSON containing the statistics data
 * @returns {ChartistObject} - <b>format:</b> {labels:[label1, label2,..],
 *          series: [data1, data2,..]}
 */
function parseMotionData(receivedData) {
	// Initialization block
	var chartLabels = [];
	var chartSeries = [];
	var chartDataObject = {};
	var recDataLength = receivedData.length;

	// Parsing
	for (var int = 0; int < recDataLength; int++) {

		var recDataObject = receivedData[int];
		if (recDataObject.activity !== 0) {
			chartLabels.push(parseDate(recDataObject.date, "dd/MM"));
			chartSeries.push(recDataObject.activity);
		}
	}
	chartDataObject.labels = chartLabels;
	chartDataObject.series = chartSeries;
	return chartDataObject;
}

// ----------------------------------------------------------------------------
// ---------------------- CHART DRAWERS --------------------------------------
// ----------------------------------------------------------------------------
/**
 * Checks the type of chart to be drawn and displays it on the page
 */
function drawChart(startDate, endDate) {
	var chartType = $("#dropDown_statistics").attr("data-chart");
	switch(chartType){
	case LIGHT_LEVEL_CHART:{
		
		break;
	}
	case LIGHT_DURATION_CHART:{
		
		break;
	}
	case ACTIVITY_LEVEL_CHART:{
		
		break;
	}
	}
}

/**
 * Draws a bar chart
 * 
 * @param charData -
 *            Object containing the labels and/or the values that need to be
 *            displayed on the chart.
 */
function drawBarChart(chartData, customOptions) {

	var barChart = new Chartist.Bar('.ct-chart', chartData, customOptions);
	return barChart;

}

/**
 * Draws a pie chart
 * 
 * @param {chartData} -
 *            containing the labels and the data that needs to be displayed
 * @param {customOptions} -
 *            custom options for the chart
 */
function drawPieChart(chartData, customOptions) {

	var pieChart = new Chartist.Pie('.ct-chart', chartData, customOptions);
}

// ---------------------------------------------------------------------------
// ----------------------- MAIN MEHTODS --------------------------------------
// ---------------------------------------------------------------------------
/**
 * Executes a request to the server and builds a bar chart representing the
 * average & maximum light levels for the last week.
 */
function lightBarChartWeek() {

	var chartOptions = {
		axisY : {
			offset : 80,
			labelInterpolationFnc : function(value) {
				return value + ' lx'
			}
		}
	};

	$.ajax({
		url : 'sensor/history/luminosity/lastweek',
		type : "GET",
		dataType : "json",
		success : function(receivedData) {
			var barChart = drawBarChart(parseLightMaxAvgData(receivedData),
					chartOptions);

		}
	});

}

/**
 * Executes an AJAX request to the server and displays a pie chart that reflects
 * the overall activity in the room for the last week.
 */
function motionPieChartWeek() {

	$.ajax({
		url : 'sensor/history/motion/lastweek',
		type : "GET",
		dataType : "json",
		success : function(receivedData) {
			drawPieChart(parseMotionData(receivedData));
		}
	})
}

// -------------------------------------------------------------------------
// -------------------------- END OF FILE ----------------------------------
// --------------------------------------------------------------------------

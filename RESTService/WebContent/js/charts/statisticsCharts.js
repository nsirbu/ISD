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

// ---------------------- HTML Content ----------------------------------
var HOW_TO_USE = "url('/RESTService/img/howto.png')";
var ERROR_DATA_MSG = "url('/RESTService/img/no_data.png')";
var ERROR_TIME_MSG = "url('/RESTService/img/error_time.png')"

// --------------------- TIME VARIABLES --------------------------------
var DATE_FORMAT = "YYYY-MM-DD";
var TIME_FORMAT = "HH:mm:ss";
var DEFAULT_START_HOUR = "09:00:00";
var DEFAULT_END_HOUR = "18:00:00";

// ------------------- CLEAR HELPER VARIABLES ---------------------------
var CLEAR_DATE_TIME_PICKERS = "date_time_pickers";
var CHART_AREA_CLASS = ".ct-chart";
// ---------------------------------------------------------------------

// setTimeout(function() {
// document.querySelector('.ct-chart').__chartist__.update();
// }, 400);

/**
 * INITIALISATION CODE block
 */
$(document).ready(
		function() {

			var today = moment(DATE_FORMAT);

			// DATE-TIME PICKERS INITIALIZATION
			// START date
			$("#dtpickerStart").datetimepicker();
			$("#dtpickerStart").data("DateTimePicker").options({
				format : DATE_FORMAT
			});
			$("#dtpickerStart").on("dp.change", function() {
				displayChart();
			})
			// END date
			$("#dtpickerEnd").datetimepicker();
			$("#dtpickerEnd").data("DateTimePicker").options({
				format : DATE_FORMAT,
				defaultDate : new Date("YYYY-MM-DD")
			});
			$("#dtpickerEnd").on("dp.change", function() {
				displayChart();
			})
			// START hour
			$("#hrpickerStart").datetimepicker();
			$("#hrpickerStart").data("DateTimePicker").options({
				format : TIME_FORMAT,
			});
			// END hour
			$("#hrpickerEnd").datetimepicker();
			$("#hrpickerEnd").data("DateTimePicker").options({
				format : TIME_FORMAT,
				defaultDate : DEFAULT_END_HOUR
			});
			$("#hrpickerStart").on("dp.change", function() {
				displayChart();
			});
			$("#hrpickerEnd").on("dp.change", function() {
				displayChart();
			});

			/**
			 * Displays the selected value in the drop-down element.
			 */
			$(".dropdown-menu li a").click(
					function() {

						$("#dropDown_statistics").text($(this).text())
						$("#dropDown_statistics").attr("data-chart",
								$(this).attr("data-chart"));
						$("#dropDown_statistics").val($(this).text());
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
	case LIGHT_LEVEL_CHART: {

		clearInputs(CLEAR_DATE_TIME_PICKERS);
		toggleMessageImage(CHART_AREA_CLASS, HOW_TO_USE);
		$(CHART_AREA_CLASS)
		$("#statistic_description")
				.html(
						"This is a light level statistic chart. More description coming soon.");

		break;
	}
	case ACTIVITY_LEVEL_CHART: {

		clearInputs(CLEAR_DATE_TIME_PICKERS);
		toggleMessageImage(CHART_AREA_CLASS, HOW_TO_USE);
		$("#statistic_description")
				.html(
						"This is the activity level statistic chart. More description coming soon.");
		break;
	}
	case LIGHT_DURATION_CHART: {

		toggleMessageImage(CHART_AREA_CLASS, HOW_TO_USE);
		clearInputs(CLEAR_DATE_TIME_PICKERS);
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
	var startHour = $("#dtpicker_start_hour").val();
	var endHour = $("#dtpicker_end_hour").val();
	var datesSelected = ((startDate !== "") && (endDate !== "")
			&& (startHour !== "") && (endHour !== ""));
	var dateIntervalValid = false;
	if (datesSelected) {
		/**
		 * Check if the selected interval is correct (start date IS EARLIER than
		 * the end date)
		 */
		dateIntervalValid = validateDateInterval(startDate, endDate, startHour,
				endHour);
	}
	if (dateIntervalValid) {
		drawChart(startDate, endDate, startHour, endHour);
	}
	if (datesSelected && !dateIntervalValid) {
		toggleMessageImage(CHART_AREA_CLASS, ERROR_TIME_MSG);
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
function validateDateInterval(startDate, endDate, startHour, endHour) {
	return (moment(startDate).isBefore(endDate));
}

/**
 * Clears the value stored in the HTML element. This method accepts short-codes
 * for clearing a given set of elements: clear("date_time_pickers") will clear
 * the value of date-time-pickers
 * 
 * @param element
 *            {clearInputsg}
 */
function clearInputs(element) {
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

/**
 * Removes the HTML content of a an element <b>Note: </b> use with caution with
 * element's class: it will erase all the child elements
 * 
 * @param element
 *            {String} element class or id
 */
function clearHtml(element) {
	$(element).html('');
}

/**
 * Displays a user friendly message in the chart area section
 * 
 * @param element
 * @param imagePath
 */
function toggleMessageImage(element, imagePath) {

	$(element).html('');
	$(element).css("background", imagePath);
}

// ----------------------------------------------------------------------------
// ------------------ SENSORS' DATA PARSERS ----------------------------------
// ----------------------------------------------------------------------------
/**
 * Parses the received information into a chart data object.
 * 
 * @param receivedData -
 *            JSON object (format:
 *            [{"date":"2016-02-15","avgValue":111,"maxValue":111}])
 * 
 * @return chartistDataObject - {JSON} object containing the labels & data
 *         necessary to build the chart
 */
function parseLightMaxAvgData(receivedData) {
	// Initialization block
	// {labels:[],series:[[][]]}
	var chartLabels = [];
	var chartSeries = [];
	chartSeries[0] = [];
	chartSeries[1] = [];
	var chartDataObject = {};
	var recDataLength = receivedData.length;
	var jsonHasNonNullValues = false;

	for (var int = 0; int < recDataLength; int++) {

		var recDataObject = receivedData[int];
		chartLabels[int] = parseDate(recDataObject.date, "dd/MM");
		/**
		 * As the chart will display simultaneously max and average values, we
		 * need 2 arrays for attribute series in the Chartist data object
		 * Example: {labels:[..], series:[[..][..]]}
		 */
		chartSeries[0][int] = recDataObject.avgValue;
		chartSeries[1][int] = recDataObject.maxValue;
		if (!jsonHasNonNullValues) {
			jsonHasNonNullValues = (recDataObject.avgValue !== 0) ? true
					: false;
			jsonHasNonNullValues = (recDataObject.maxValue !== 0) ? true
					: false;
		}

	}
	if (jsonHasNonNullValues) {
		chartDataObject.labels = chartLabels;
		chartDataObject.series = chartSeries;
		return chartDataObject;
	} else
		return new Object();
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
	var jsonHasNonNullValues = false;

	// Parsing
	for (var int = 0; int < recDataLength; int++) {

		var recDataObject = receivedData[int];
		if (recDataObject.activity !== 0) {
			chartLabels.push(parseDate(recDataObject.date, "dd/MM"));
			chartSeries.push(recDataObject.activity);
			jsonHasNonNullValues = true;
		}
	}
	if (jsonHasNonNullValues) {
		chartDataObject.labels = chartLabels;
		chartDataObject.series = chartSeries;
		return chartDataObject;
	} else
		return new Object();
}

/**
 * Parses the data received from the server and converts it into a Chartist data
 * object, containing labels & series, necessary for rendering the chart.
 * 
 * @param receivedData -
 *            JSON containing the statistics data
 * @returns {ChartistObject} - <b>format:</b> {labels:[label1, label2,..],
 *          series: [data1, data2,..]}
 */
function parseLightDurationData(receivedData) {

}

// ----------------------------------------------------------------------------
// ---------------------- CHART DRAWERS --------------------------------------
// ----------------------------------------------------------------------------
/**
 * Checks the type of chart to be drawn and displays it on the page
 */
function drawChart(startDate, endDate, startHour, endHour) {
	var chartType = $("#dropDown_statistics").attr("data-chart");
	switch (chartType) {
	case LIGHT_LEVEL_CHART: {
		lightBarChart(startDate, endDate, startHour, endHour);
		break;
	}
	case LIGHT_DURATION_CHART: {

		break;
	}
	case ACTIVITY_LEVEL_CHART: {
		motionPieChart(startDate, endDate, startHour, endHour);
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

	if (Object.keys(chartData).length > 0) {
		toggleMessageImage(CHART_AREA_CLASS, "none");
		var barChart = new Chartist.Bar('.ct-chart', chartData, customOptions);
		return barChart;
	} else {
		toggleMessageImage(CHART_AREA_CLASS, ERROR_DATA_MSG);
	}

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

	if (Object.keys(chartData).length > 0) {

		toggleMessageImage(CHART_AREA_CLASS, "none");
		var pieChart = new Chartist.Pie('.ct-chart', chartData, customOptions);
		return pieChart;
	} else {
		toggleMessageImage(CHART_AREA_CLASS, ERROR_DATA_MSG);
	}

}

// ---------------------------------------------------------------------------
// ----------------------- MAIN MEHTODS --------------------------------------
// ---------------------------------------------------------------------------

/**
 * Executes a request to the server and builds a bar chart representing the
 * average & maximum light levels for the last week.
 */
function lightBarChart(startDate, endDate, startHour, endHour) {

	var chartOptions = {
		axisY : {
			offset : 80,
			labelInterpolationFnc : function(value) {
				return value + ' lx'
			}
		}
	};
	var statisticUrl = 'sensor/history/luminosity/' + startDate + "%20"
			+ startHour + '&' + endDate + "%20" + endHour;

	$.ajax({
		url : statisticUrl,
		type : "GET",
		dataType : "json",
		success : function(receivedData) {

			var chart = drawBarChart(parseLightMaxAvgData(receivedData),
					chartOptions);
		}
	});

}

// TODO: verify url. Implement method
function lightDurationBarChart() {
	var chartOptions = {
		axisY : {
			offset : 80,
			labelInterpolationFnc : function(value) {
				return value + ' m.'
			}
		}
	};
	var statisticUrl = 'sensor/history/lightOn/'
			+ startDate.replace(" ", "%20") + '&' + endDate.replace(" ", "%20");

	$.ajax({
		url : statisticUrl,
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
function motionPieChart(startDate, endDate) {

	var statisticUrl = 'sensor/history/motion/lastweek/'
			+ startDate.replace(" ", "%20") + '&' + endDate.replace(" ", "%20");
	$.ajax({
		url : statisticUrl,
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

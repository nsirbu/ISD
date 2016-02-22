/* This file contains the methods necessary for displaying
 * the sensors' statistic data using charts
 * 
 * @author: sscerbatiuc
 */

/**
 * This method is used for determining which chart user has requested to
 * display. It is called on "<code>onChange</code>" event of the select
 * element displayed on the home-page.
 */
function displayStats(type) {

	
	switch (type) {
	case "lws": /* lws - Light levels week statistic */
		lightBarChartWeek();
		$('#statDescription').collapse('toggle');
		break;
	case "aws": /* aws - Activity week statistic */
		motionPieChartWeek();
		break;
	default:
		break;
	}

}
// --------------------- HELPER METHODS --------------------------------------
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

// ------------------ SENSORS' DATA PARSERS ----------------------------------
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
		if(recDataObject.activity !== 0){
			chartLabels.push(parseDate(recDataObject.date, "dd/MM"));
			chartSeries.push(recDataObject.activity);
		}
	}
	chartDataObject.labels = chartLabels;
	chartDataObject.series = chartSeries;
	console.log(chartDataObject);
	return chartDataObject;
}

// ---------------------- CHART DRAWERS --------------------------------------
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
			var barChart = drawBarChart(parseLightMaxAvgData(receivedData), chartOptions);
			barChart.update();
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

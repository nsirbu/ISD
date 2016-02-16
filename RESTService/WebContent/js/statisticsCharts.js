/* This file contains the methods necessary for displaying
 * the sensors' statistic data using charts
 */

/**
 * This method is used for determining which chart user has requested to display.
 * It is called on "<code>onChange</code>" event of the select element displayed on
 * the homepage.
 */
function drawChart(){

	var selectBox 	  = document.getElementById("history_option");
	var selectedValue = selectBox.options[selectBox.selectedIndex].value;

	switch(selectedValue){
		case "1": barChartWeek(); break;
		case "2": pieChartWeek(); break;
		default: break;
	}

}

/**
 * Parses the received information into a chart data object
 * [{"date":"2016-02-09"}],[{"date":"2016-02-10"}],[{"date":"2016-02-11"}],[{"date":"2016-02-12"}],
 * [{"date":"2016-02-13"}],[{"date":"2016-02-14"}],[{"date":"2016-02-15","avgValue":111,"maxValue":111}],
 * [{"date":"2016-02-16","avgValue":111,"maxValue":111}]
 */
function parseChartData(receivedData){
	var chartLabels 	= [];
	var chartSeries		= [];
	var chartDataObject = {};
	var recDataLength	= receivedData.length;
	
	for (var int = 0; int < recDataLength; int++) {
		
		var recDataObject = receivedData[int];
		console.log(recDataObject.date + " " + recDataObject.avgValue + " " + recDataObject.maxValue);
		chartLabels[int] = recDataObject.date;
		
		chartSeries[0][int] = [,(recDataObject.avgValue === undefined) ? 0 : recDataObject.avgValue ];
		chartSeries[1][int] = [,(recDataObject.maxValue === undefined) ? 0 : recDataObject.maxValue ];
	}
	console.log("Chart Series: " + chartSeries);
	chartDataObject.labels = chartLabels;
	chartDataObject.series = chartSeries;
	return chartDataObject;
}

//------------- BAR Chart - Last week statistics for light levels -----------
/**
 * Draws a chart containing the statistics about last week's light levels
 * @param charData - Object containing the labels and/or the values that need 
 * 					to be displayed on the chart.
 */
function drawBarChart(chartData){
	
	console.log(chartData);
	var barChart = new Chartist.Bar('.ct-chart', chartData );
}


function barChartWeek(){

	$.ajax({
			url:'sensor/history/luminosity/lastweek',
			type: "GET",
			dataType: "json",
			success: function(receivedData){
				var chartData = parseChartData(receivedData);
				drawBarChart(chartData);
			}
		});	
}


/* Pie chart */

function pieChartWeek(){

	var pieData = {
		labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri'],
		series: [20,15,10,4,2]
	};

	new  Chartist.Pie('.ct-chart', pieData);
}
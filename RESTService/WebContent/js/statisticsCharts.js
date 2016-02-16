/* This file contains the methods necessary for displaying
 * the sensors' statistic data using charts
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

// Parses the received information into a chart data object
function parseChartData(receivedData){

}

/* BAR Chart - Last week statistics for light levels*/

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

function drawBarChar(chartData){

	var barChart = new Chartist.Bar('.ct-chart', barData );
}

/* Pie chart */

function pieChartWeek(){

	var pieData = {
		labels: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri'],
		series: [20,15,10,4,2]
	};

	new  Chartist.Pie('.ct-chart', pieData);
}
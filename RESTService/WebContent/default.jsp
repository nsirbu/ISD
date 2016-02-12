<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<link rel="stylesheet" href="css/chartist.min.css">
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
	</head>
<body>
	<script src="js/chartist.min.js"></script>
	
	<div class="ct-chart ct-golden-section" id="chart1"></div>
	
	<script>		
		var data = {
		  labels: ['Week1', 'Week2', 'Week3', 'Week4', 'Week5', 'Week6'],
		  series: [
		    [3, 4, 5, 6, 7, 2]
		  ]
		};
		
		var options = {
		  // Don't draw the line chart points
		  showPoint: true,
		  // Disable line smoothing
		  lineSmooth: true,
		  // X-Axis specific configuration
		  axisX: {
		    // We can disable the grid for this axis
		    showGrid: true,
		    // and also don't show the label
		    showLabel: true
		  },
		  // Y-Axis specific configuration
		  axisY: {
		    // Lets offset the chart a bit from the labels
		    offset: 60,
		    // The label interpolation function enables you to modify the values
		    // used for the labels on each axis. Here we are converting the
		    // values into million pound.
		    labelInterpolationFnc: function(value) {
		      return value;
		    }
		  }
		};
		
		// All you need to do is pass your configuration as third parameter to the chart function
		new Chartist.Line('#chart1', data, options);
	</script>

</body>
</html>
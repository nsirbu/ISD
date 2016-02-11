<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Current sensors' state</title>
		<link rel="stylesheet" href="css/styles.css" type="text/css" /> 
		<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.0/jquery.min.js"></script>
		<script type="text/javascript">
			var auto_refresh = setInterval(
				function ()
				{
					$('#content').load('CurrentData.jsp').fadeIn("slow");
				}, 5000); // autorefresh the content of the div after
			              // every 6000 milliseconds(1min)
		</script>
	</head>
<body>
	<div class="alignCenter">
		<h3>Current sensors' state</h3>
	</div>
	<div id="content">
		<%@ include file="CurrentData.jsp" %>
	</div>
</body>
</html>
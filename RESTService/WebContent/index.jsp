<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- The site was split in different sections. 
	 Each file represents a specific section of the site.
	 To add a section to the page, simply create a new jsp file and add it to the respective part of this file.
	 
	 IMPORTANT: Each section's color alternates from the previous one. 
	 To simplify adding new sections, the section tag was extracted from the file 
	 containing the HTML code for the section itself. -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<title>INTHERnship</title>
<link rel="icon" type="image/png" href="img/fav_icon64.png">

<!-- ----------------- STYLESHEETS ---------------------------- -->

<!-- Bootstrap Core CSS -->
<link rel="stylesheet" href="css/bootstrap/bootstrap.min.css"
	type="text/css">
<link rel="stylesheet" href="css/bootstrap/bootstrap-datetimepicker.css">

<!-- Custom Fonts -->
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800'
	rel='stylesheet' type='text/css'>
<link
	href='http://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic,900,900italic'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" href="font-awesome/css/font-awesome.min.css"
	type="text/css">

<!-- Plugin CSS -->
<link rel="stylesheet" href="css/animation/animate.min.css"
	type="text/css">

<!-- Main CSS -->
<link rel="stylesheet" href="css/main/inthernship.css" type="text/css">
<link rel="stylesheet" href="css/custom.css" type="text/css">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
	        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	    <![endif]-->

<!-- Chartist CSS -->
<link rel="stylesheet" type="text/css"
	href="css/chartist/chartist.min.css">
<link rel="stylesheet" type="text/css"
	href="css/chartist/chartist-custom.css">

<!-- ------------------- /stylesheets --------------------------- -->
</head>
<body id="page-top">

	<%@ include file="includes/page-top.jsp"%>

	<section class="bg-primary" id="about"> <%@ include
		file="includes/about.jsp"%> </section>

	<section id="benefits"> <%@ include
		file="includes/benefits.jsp"%> </section>

	<section class="bg-action" id="how_it_works"> <%@ include
		file="includes/how_it_works.jsp"%> </section>

	<section id="current_state"> <%@ include
		file="includes/current_state.jsp"%> </section>
		
	<section class="bg-primary" id="configuration"> <%@ include
		file="includes/configuration.jsp"%> </section>

	<section id="stats"> <%@ include file="includes/stats.jsp"%>
	</section>

	<section class="bg-primary" id="ressources"> <%@ include
		file="includes/resources.jsp"%> </section>

	<!--------------------- SCRIPTS ------------------------->

	<!-- jQuery -->
	<script src="js/jquery/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap/bootstrap.min.js"></script>

	<!-- Bootstrap DateTimePicker -->
	<script type="text/javascript" src="js/helpers/moment.js"></script>
	<script type="text/javascript"
		src="js/bootstrap/bootstrap-datetimepicker.min.js"></script>

	<!-- Plugin JavaScript -->
	<script src="js/jquery/jquery.easing.min.js"></script>
	<script src="js/jquery/jquery.fittext.js"></script>
	<script src="js/animation/wow.min.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="js/bootstrap/creative.js"></script>

	<!-- Sensors' state request JavaScript -->
	<script type="text/javascript" src="js/sensor.js"></script>

	<!-- Chartist JS -->
	<script type="text/javascript" src="js/charts/chartist.js"></script>
	<script type="text/javascript" src="js/charts/statisticsCharts.js"></script>

	<!-- Main JS -->
	<script type="text/javascript" src="js/inthernship.js"></script>

	<!--------------------- /scripts ------------------------->

</body>
</html>
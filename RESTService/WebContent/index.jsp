<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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

<!-- Bootstrap Core CSS -->
<link rel="stylesheet" href="css/bootstrap/bootstrap.min.css"
	type="text/css">

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

</head>
<body id="page-top">

	<nav id="mainNav" class="navbar navbar-default navbar-fixed-top">
	<div class="container-fluid">
		<!-- Brand and toggle get grouped for better mobile display -->
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand page-scroll" href="#page-top"> <img
				src="img/logo.png"></a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav navbar-right">
				<li><a class="page-scroll" href="#about">About project</a></li>
				<li><a class="page-scroll" href="#benefits">Benefits</a></li>
				<li><a class="page-scroll" href="#how_it_works">How it
						works</a></li>
				<li><a class="page-scroll" href="#current_state">Current
						State</a></li>
				<li><a class="page-scroll" href="#stats">Statistics</a></li>
				<li><a class="page-scroll" href="#ressources">Resources</a></li>
			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid --> </nav>

	<header>
	<div class="header-content">
		<div class="header-content-inner">
			<h1>
				Arduino Board <br>+<br> Raspberry Pi
			</h1>
			<hr>
			<hr>
			<p>Interactive and efficient way of monitoring the office
				environment</p>
			<a href="#about" class="btn btn-primary btn-xl page-scroll">How
				it works?</a>
		</div>
	</div>
	</header>

	<section class="bg-primary" id="about">
	<div class="container">
		<div class="row">
			<div class="col-lg-8 col-lg-offset-2 text-center">
				<h2 class="section-heading">Genius stuff is simple!</h2>
				<hr class="primary">
				<p class="text-faded">We've managed to connect the most simple,
					yet powerful configuration of hardware to monitor the the movement
					and light levels in a room. Just imagine how far can we get from
					here! Adjusting the light level automatically, when it reaches a
					minimal value? Easy! Turning the light off when there is nobody in
					the room? Yes, sir! In fact, it is a small step for us, but a giant
					leap for the entire office!</p>
				<a href="#ressources" class="btn btn-default btn-xl" id="know_more">I
					want to know more!</a>
			</div>
		</div>
	</div>
	</section>

	<section id="benefits">
	<div class="container">
		<div class="row">
			<div class="col-lg-12 text-center">
				<h2 class="section-heading">Key benefits of our project</h2>
				<hr class="primary">
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-lg-3 col-md-6 text-center">
				<div class="service-box">
					<i class="fa fa-4x fa-wifi wow bounceIn text-primary"></i>
					<h3>It's small</h3>
					<p class="text-muted">This configuration can fit in every
						workspace.</p>
					<p class="joke">We care about the Feng shui of the office</p>
				</div>
			</div>
			<div class="col-lg-3 col-md-6 text-center">
				<div class="service-box">
					<i class="fa fa-4x fa-paper-plane wow bounceIn text-primary"
						data-wow-delay=".1s"></i>
					<h3>Fast &amp; Reliable</h3>
					<p class="text-muted">Just test it! We were actually surprised
						that it works *</p>
					<p class="joke">* Just joking - we know what we're doing</p>
				</div>
			</div>
			<div class="col-lg-3 col-md-6 text-center">
				<div class="service-box">
					<i class="fa fa-4x fa-book wow bounceIn text-primary"
						data-wow-delay=".2s"></i>
					<h3>Well documented</h3>
					<p class="text-muted">At least we are doing our best to provide
						it.</p>
				</div>
			</div>
			<div class="col-lg-3 col-md-6 text-center">
				<div class="service-box">
					<i class="fa fa-4x fa-mobile wow bounceIn text-primary"
						data-wow-delay=".3s"></i>
					<h3>It's RESTfull</h3>
					<p class="text-muted">
						3<sup>rd</sup> party clients can easily access sensors data.
					</p>
				</div>
			</div>
		</div>
	</div>
	</section>

	<section class="bg-action" id="how_it_works">
	<div class="container">
		<div class="col-lg-12 text-center">
			<h2 class="section-heading">How it works?</h2>
			<hr class="light">
			<p class="text-faded">The data received from the sensors is
				displayed in the section below, accompanied by the respective icons.
				The server receives data when the sensors' state changes. It will
				track the minor movement and light value variation and send it to
				the Raspberry Pi server.</p>
		</div>
		<div class="row">
			<div class="col-lg-4 text-center">
				<div class="service-box action">
					<i class="fa fa-5x fa-clock-o wow bounceIn text-primary"></i>
					<h3>Time received</h3>
					<p>2016-06-12 12:12:12</p>
				</div>
			</div>
			<div class="col-lg-8 indicator_description">
				<p class="text-faded">The time received represents the exact
					time when the data has been received from the Arduino board.
					Basically the time format on the left represents the exact format
					it's been stored in the database. If you have any idea ||
					concerning this value, we can discuss it and include it in the next
					sprint.</p>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-4 text-center">
				<div class="service-box action">
					<i class="fa fa-5x fa-lightbulb-o wow bounceIn text-primary"
						data-wow-delay=".1s" id="light_demo"></i>
					<h3>Light level</h3>
					<p>150 lx</p>
				</div>
			</div>
			<div class="col-lg-8 indicator_description">
				<p class="text-faded">The light is one of the most fun part of
					this project. The tiny sensor measures the light level and converts
					it from the analog value to the digital one and sends it to the
					server. The 'lx' stands for lux and it's a SI unit of illuminance
					and luminous emittance.</p>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-4 text-center">
				<div class="service-box action">
					<i class="fa fa-5x fa-spinner wow bounceIn text-primary"
						data-wow-delay=".1s" id="pir_demo"></i>
					<h3>PIR Sensor</h3>
					<p>There is movement in the room</p>
				</div>
			</div>
			<div class="col-lg-8 indicator_description">
				<p class="text-faded">This sensor detects the movement. When
					someone enters the room, leaves or makes a dance contest with the a
					colleague, it will track it. You should agree, it's an amazing
					feature for a 3$ device.</p>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-4 text-center">
				<div class="service-box action">
					<i class="fa fa-5x fa-male wow bounceIn text-primary"
						data-wow-delay=".1s"></i>
					<h3>Presence detection</h3>
					<p>There is somebody in the room</p>
				</div>
			</div>
			<div class="col-lg-8 indicator_description">
				<p class="text-faded">Using the data described above, we've
					created an algorythm which detects if there is somebody in the room
					or not.</p>
			</div>
		</div>
	</div>
	</section>

	<section id="current_state">
	<div class="container">
		<div class="row">
			<div class="col-lg-12 text-center">
				<h2 id="current_state">Sensor's current state</h2>
				<p>These are the current sensors' states detected.</p>
				<hr class="primary">
			</div>
		</div>
		<div class="row">
			<div class="col-lg-3 text-center">
				<i class="fa fa-5x fa-clock-o wow bounceIn text-primary"></i>
				<p id="time_value" class="sensor_values">Time value</p>
			</div>
			<div class="col-lg-3 text-center">
				<i class="fa fa-5x fa-lightbulb-o wow bounceIn text-primary"
					data-wow-delay=".1s" id="light_demo"></i>
				<p id="light_value" class="sensor_values">Light value</p>
			</div>
			<div class="col-lg-3 text-center">
				<i class="fa fa-5x fa-spinner wow bounceIn text-primary"
					data-wow-delay=".1s" id="pir_demo"></i>
				<p id="pir_value" class="sensor_values">PIR sensor value</p>
			</div>
			<div class="col-lg-3 text-center">
				<i class="fa fa-5x fa-male wow bounceIn text-primary"
					data-wow-delay=".1s"></i>
				<p id="presence_value" class="sensor_values">Presence detection</p>
			</div>
		</div>
		<div class="row">
			<div class="alert alert-info alert-dismissible" role="alert"
				id="current_state_notification">
				<!-- <button type="button" class="close" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button> -->
				<i class="fa fa-info-circle" id="notification_icon"></i> <span
					id="notification_title" class="hb_notification_text">Hey
					there!</span><span id="notification_text" class="hb_notification_text">
					If something goes wrong, you will see a message in this box. For
					now, you can just ignore it.</span>
			</div>
		</div>
		<div class="row">
			<div class="col-lg-4">
				<h2>Configuration</h2>
				<hr class="primary" id="configuration_separator">
				<p>In the next box you can set the threshold values for the
					light levels sensor and heartbeat frequency, so that you are sure
					the sensors' data reflect the reality correctly and you don't get
					annoyed by the big ammount of messages.</p>
			</div>
			<div class="col-lg-8">
				<div class="panel panel-default" id="settings_panel">
					<div class="panel-heading">
						<h3 class="panel-title">
							<i class="fa fa-cogs"></i> Settings
						</h3>
					</div>
					<div class="panel-body" id="settings_body">
						<div class="row">
							<div class="col-lg-6">
								<h4>
									<i class="fa fa-heart"></i> Heart-beat frequency <span
										class="badge" data-toggle="tooltip" title="Current frequency"
										id="hb_thresh_val">- s.</span>
								</h4>
							</div>
							<div class="col-lg-4">
								<div class="input-group" id="hb_frequency_settings">
									<input type="text" class="form-control settings_input"
										placeholder="seconds" id="hb_frequency_input"> <span
										class="input-group-btn">
										<button class="btn btn-default update_settings" type="button"
											id="hb_change_button" onclick="updateHbRate()">Update</button>
									</span>

								</div>
							</div>
							<div class="col-lg-1">
								<i class="fa fa-check fa-2x" id="hb_update_status"></i>
							</div>
						</div>
						<div class="row">
							<div class="col-lg-6">
								<h4>
									<i class="fa fa-lightbulb-o"></i> Light levels threshold <span
										class="badge" id="light_thresh_val" data-toggle="tooltip"
										title="Current light level threshold">- lx</span>
								</h4>
							</div>
							<div class="col-lg-4">
								<div class="input-group " id="light_level_settings">
									<input type="text" class="form-control settings_input"
										placeholder="lx" id="light_threshold_input"> <span
										class="input-group-btn">
										<button class="btn btn-default update_settings" type="button"
											id="light_change_button" onclick="updateLightTreshVal()">Update</button>
									</span>
								</div>
							</div>
							<div class="col-lg-1">
								<i class="fa fa-check fa-2x" id="light_update_status"></i>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="row text-center">
			<a class="btn btn-info" id="current_state_info_button"
				data-toggle="collapse" aria-expanded="false"
				aria-controls="current_state_info" href="#current_state_info">What
				are these values?</a>
		</div>
		<div class="row">
			<div class="collapse" id="current_state_info">
				<div class="alert alert-info" role="alert">Description coming
					soon, stay tuned.</div>

			</div>
		</div>
	</div>
	</section>
	<section class="bg-primary" id="stats">
	<div class="container">
		<div class="row">
			<div class="col-lg-12 text-center">
				<h2>Statistics</h2>
				<hr class="light">
				<p class="text-faded">Let's face it: everything that is awesome
					have also the same amount of utility. In fact, you could imagine
					what all this project is about? Well, one of its uses is the
					statistics gathering. Let's say one would like to see the light
					levels in this room in order to analyse how well does the room meet
					the working conditions. I think you get the point. We've gathered
					some options that we've thought would be very useful to analyse.
					The filtered data will be displayed using a nice chart. Isn't it
					cool?</p>
			</div>
		</div>
		<div class="row" id="statistics_data">
			<div class="col-lg-12 text-center">
				<p id="statistic_select">What would you like to see?</p>
				<div class="dropdown" id="statistics_options">
					<button class="btn btn-default dropdown-toggle" type="button"
						id="dropDown_statistics" data-toggle="dropdown"
						aria-haspopup="true" aria-expanded="true">
						Select an option <span class="caret"></span>
					</button>
					<ul class="dropdown-menu" aria-labelledby="statistics_options"
						id="dropdown_list">
						<li class="text-important">Week statistics</li>
						<li><a onclick="displayStats('lws'); return false;">Light
								levels statistics (last week)</a></li>
						<li><a onclick="displayStats('aws'); return false;">Activity
								statistics (last week)</a></li>
						<li role="separator" class="divider"></li>
						<li class="text-important">Daily statistics</li>
						<li><a href="#">Awesome statistic</a></li>
						<li><a href="#">Awesomer statistic</a></li>
					</ul>
				</div>
			</div>

			<div class="row collapse" id="statDescription">
				<div class="col-lg-6">
					<div class="ct-chart ct-perfect-fourth"></div>
				</div>
				<div>
					<div class="col-lg-6">
						<h3 class="text-center">Description</h3>
						<hr class="light">
						<p class="text-faded" id="statistic_description">Lorem ipsum
							dolor sit amet, usu illud audire an, at oratio tollit sit.
							Delenit meliore suscipiantur id has. Ex ius aliquip referrentur,
							vix an modo numquam inermis. No imperdiet disputando vis, clita
							ornatus qualisque et qui. Legimus veritus referrentur id nam. Id
							vim eligendi incorrupte necessitatibus, per et causae
							intellegebat.Vim justo paulo consequuntur no, in has tamquam
							facilis philosophia. In mel tota prima justo, quem enim erat mei
							ei, at probo velit quaerendum nec. Sit ut liber facete
							adolescens, vis cu tempor pericula, solum animal vix id. Eu per
							mutat veritus nostrum, odio discere ad quo, ius et prima
							scaevola. Cetero invidunt eum eu, pro falli consul disputationi
							eu, ne eam ullum veritus. Choro antiopam per an, an sed appareat
							praesent.</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	</section>
	<!-- / stats -->
	<section id="ressources">
	<div class="container">
		<div class="row">
			<div class="col-lg-8 col-lg-offset-2 text-center">
				<h2 class="section-heading">Additional ressources &amp;
					contacts</h2>
				<hr class="light">
				<p>Do you think you have an amazing idea on how to make this
					project even awesomer? Go on! We would like to discuss it with you.
					Our purpose is to make a great product, so every step towards it is
					a must.</p>
			</div>
			<div class="col-lg-4 col-lg-offset-2 text-center">
				<h3>Check it out on GitHub</h3>
				<i class="fa fa-github fa-3x wow bounceIn"></i>
				<p>Links are on the way. Somewhere between "ready" and "done".</p>
			</div>
			<!-- / col-lg-4 -->
			<div class="col-lg-4 text-center">
				<h3>Send us a letter</h3>
				<i class="fa fa-envelope-o fa-3x wow bounceIn" data-wow-delay=".1s"></i>
				<p>Click on the links below to start writing an ecouraging
					e-mail.</p>
				<div class="col-lg-3 text-center">
					<a class="mail_links" href="mailto:osipovvictor1994@gmail.com"><i
						class="fa fa-envelope fa-2x wow bounceIn" data-wow-delay=".1s"></i>Victor</a>
				</div>
				<div class="col-lg-3 text-center">
					<a class="mail_links" href="mailto:scerbatiuc.stas@gmail.com"><i
						class="fa fa-envelope fa-2x wow bounceIn" data-wow-delay=".1s"></i>Stanislav</a>
				</div>
				<div class="col-lg-3 text-center">
					<a class="mail_links" href="mailto:niku.sirbu@gmail.com"><i
						class="fa fa-envelope fa-2x wow bounceIn" data-wow-delay=".1s"></i>Nicolae</a>
				</div>
				<div class="col-lg-3 text-center">
					<a class="mail_links" href="mailto:cobylas@gmail.com"><i
						class="fa fa-envelope fa-2x wow bounceIn" data-wow-delay=".1s"></i>Adrian</a>
				</div>
				<!-- / col-lg-4 -->
			</div>
		</div>
	</div>
	</section>
	<!-- / resources-->
	<!-- jQuery -->
	<script src="js/jquery/jquery.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script src="js/bootstrap/bootstrap.min.js"></script>

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

</body>
</html>
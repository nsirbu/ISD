<!--   -->
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
</div>
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

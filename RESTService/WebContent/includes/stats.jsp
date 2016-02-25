
<div class="container">
	<div class="row">
		<div class="col-lg-12 text-center">
			<h2>Statistics</h2>
			<hr class="light">
			<p>Let's face it: everything that is awesome have also the same
				amount of utility. In fact, you could imagine what all this project
				is about? Well, one of its uses is the statistics gathering. Let's
				say one would like to see the light levels in this room in order to
				analyze how well does the room meet the working conditions. I think
				you get the point. We've gathered some options that we've thought
				would be very useful to analyze. The filtered data will be displayed
				using a nice chart. Isn't it cool?</p>
		</div>
	</div>
	<div class="row" id="statistics_data">
		<div class="col-lg-12 text-center">
			<p id="statistic_select">What would you like to see?</p>
			<div class="dropdown" id="statistics_options">
				<button class="btn btn-default dropdown-toggle" type="button"
					id="dropDown_statistics" data-toggle="dropdown"
					aria-haspopup="true" aria-expanded="true" data-chart="">
					Select an option <span class="caret"></span>
				</button>
				<ul class="dropdown-menu" aria-labelledby="statistics_options"
					id="dropdown_list">
					<!-- <li role="separator" class="divider"></li>  -->
					<!-- <li class="text-important">Week statistics</li> -->
					<li><a data-chart="light_levels">Light levels statistic</a></li>
					<li><a data-chart="activity_chart">Activity statistic</a></li>
					<li><a data-chart="light_duration">"Light on" statistic</a></li>
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
					<h3 class="text-center">Select the period</h3>
					<hr class="primary">
					<div class="form-group">
						<div class='input-group date' id='dtpickerStart'>
							<input type='text' class="form-control" placeholder="Start date"
								id="dtpicker_start_date" /> <span
								class="input-group-addon"> <span class="fa fa-calendar"></span>
							</span>
						</div>
						<div class='input-group date' id='dtpickerEnd'>
							<input type='text' class="form-control" placeholder="End date"
								id="dtpicker_end_date"/> <span
								class="input-group-addon"> <span class="fa fa-calendar"></span>
							</span>
						</div>
					</div>
					<h3 class="text-center">Description</h3>
					<hr class="primary">
					<p id="statistic_description">Lorem ipsum dolor sit amet, usu
						illud audire an, at oratio tollit sit. Delenit meliore
						suscipiantur id has. Ex ius aliquip referrentur, vix an modo
						numquam inermis. No imperdiet disputando vis, clita ornatus
						qualisque et qui. Legimus veritus referrentur id nam. Id vim
						eligendi incorrupte necessitatibus, per et causae intellegebat.Vim
						justo paulo consequuntur no, in has tamquam facilis philosophia.
						In mel tota prima justo, quem enim erat mei ei, at probo velit
						quaerendum nec.</p>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="container">
	<div class="row">
		<div class="col-lg-4">
			<h2>Configuration</h2>
			<hr class="primary" id="configuration_separator">
			<p>In the next box you can set the threshold values for the light
				levels sensor and heartbeat frequency, so that you are sure the
				sensors' data reflect the reality correctly and you don't get
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
		<a class="btn btn-default" id="settings_info_button"
			data-toggle="collapse" aria-expanded="false"
			aria-controls="settings_info_alert" href="#settings_info_alert">What
			are these values?</a>
	</div>
	<div class="row">
		<div class="collapse" id="settings_info_alert">
			<div class="alert alert-default" role="alert">Description coming
				soon, stay tuned.</div>
		</div>

	</div>
</div>
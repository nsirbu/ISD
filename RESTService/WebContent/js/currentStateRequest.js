var auto_refresh = setInterval(
	function ()
	{
		$.ajax({
			url:'sensor/current',
			type: "GET",
			dataType: "json",
			success: function(data){
				$("#time_value").html(data.timeReceived);
				$("#light_value").html(data.lightSensorVal + " lx");
				if(data.pirSensorVal == false){
					$("#pir_value").html("Nobody's moving");
				} else {
					$("#pir_value").html("Movement detected");
				}
			}
		});
		
	}, 5000);
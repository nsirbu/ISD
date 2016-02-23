var lastSensorState = {timeReceived:"", lightSensorVal: 0, pirSensorVal: false};

function stateChanged(newSensorState){
	if(lastSensorState.timeReceived == newSensorState.timeReceived) return false;
	if((lastSensorState.lightSensorVal != newSensorState.lightSensorState) ||
		(lastSensorState.pirSensorVal != newSensorState.pirSensorVal)) { return true; }
	else return false;
}

function updateValues(message){
	showUpdateNotification();
	$("#time_value").html(message.timeReceived.substring(0,19));
	lastSensorState.timeReceived = message.timeReceived;
	$("#light_value").html(message.lightSensorVal + " lx");
	lastSensorState.lightSensorVal = message.lightSensorVal;
	if(message.pirSensorVal == false){
		$("#pir_value").html("Nobody's moving");
	} else {
		$("#pir_value").html("Movement detected");
	}
	lastSensorState.pirSensorVal = message.pirSensorVal;
}

var auto_refresh = setInterval(function ()
	{
		$.ajax({
			url:'sensor/current',
			type: "GET",
			dataType: "json",
			success: function(message){
				if(stateChanged(message)){
					updateValues(message);
				};
				
			}
		});
		
	}, 2000);

function showUpdateNotification(){
	$("#update_notification").css("display","inline-block");
	setTimeout(function(){
		$("#update_notification").css("display","none");
	}, 1500);
	
}

/**
 * This file contains the main methods for requesting the data about sensors
 * from the server and displaying it on the home-page
 * 
 * @author sscerbatiuc
 */

// Temporary JSON Object, that holds the last sensors' state information
var lastSensorState = {
	timeReceived : "",
	lightSensorVal : 0,
	pirSensorVal : false
};

var ERROR_STATUS   = -1;
var INFO_STATUS    = 0;
var SUCCESS_STATUS = 1;

// Heart-beat notifications variables
var INFO_ICON    = "fa fa-info-circle";
var ERROR_ICON	 = "fa fa-exclamation-circle";
var SUCCESS_ICON = "fa fa-check-circle";

var ALERT_INFO_TITLE = "Aloha!";
var ALERT_ERROR_TITLE = "Oh snap!";
var ALERT_SUCCESS_TITLE = "Yay!";

var ALERT_INFO_CLASS    = "alert alert-info alert-dismissible";
var ALERT_ERROR_CLASS   = "alert alert-danger alert-dismissible";
var ALERT_SUCCESS_CLASS = "alert alert-success alert-dismissible";

var OK_MESSAGE	   = "Everything is OK";
var ERROR_MESSAGE = "The last message was late. The board is not sending messages " +
					"in the specified interval";

$.fx.step.textShadowBlur = function(fx) {
    $(fx.elem)
        .prop('textShadowBlur', fx.now)
        .css({textShadow: '0 0 ' + Math.floor(fx.now) + 'px #960A31'});
};


setInterval(function() {
    
    $("#seconds").animate({textShadowBlur:20}, {duration: 200, complete: function() { 
        $("#seconds").animate({textShadowBlur:0}, {duration: 500}); 
    }});
}, 1000);

/**
 * Checks if the newly received sensors' state is different from the last one
 * 
 * @param newSensorState -
 *            JSON Object containing information about new state of the sensors
 * @returns {Boolean} <code>true</code> if the new state is different
 */
function stateChanged(newSensorState) {
	if (lastSensorState.timeReceived == newSensorState.timeReceived)
		return false;
	if ((lastSensorState.lightSensorVal != newSensorState.lightSensorState)
			|| (lastSensorState.pirSensorVal != newSensorState.pirSensorVal)) {
		return true;
	} else
		return false;
}

/**
 * Replaces the old values displayed on the page with the new ones
 * 
 * @param message -
 *            JSON Object representing the new state of the sensor
 */
function updateValues(message) {
	
	$("#time_value").html(message.timeReceived.substring(0, 19));
	lastSensorState.timeReceived = message.timeReceived;
	showUpdateNotification("#time_value");
	$("#light_value").html(message.lightSensorVal + " lx");
	showUpdateNotification("#light_value");
	lastSensorState.lightSensorVal = message.lightSensorVal;
	if (message.pirSensorVal == false) {
		$("#pir_value").html("Nobody's moving");
	} else {
		$("#pir_value").html("Movement detected");
	}
	showUpdateNotification("#pir_value");
	lastSensorState.pirSensorVal = message.pirSensorVal;
}

/**
 * Updates the sensors' values displayed in the page. This method executes an
 * asynchronous request to the server and displays the new values in the pages.
 */
var auto_refresh = setInterval(function() {
	$.ajax({
		url : 'sensor/current',
		type : "GET",
		dataType : "json",
		success : function(message) {
			if (stateChanged(message)) {
				updateValues(message);
			}
			;
		}
	});

}, 2000);

/**
 * Displays a textual notification about the information being up to date
 */
function showUpdateNotification(elementId) {
	
	$(elementId).animate({textShadowBlur:20}, {duration: 300, complete: function() { 
        $(elementId).animate({textShadowBlur:0}, {duration: 500}); 
    }});

}

/**
 * Checks for the heart-beat every specified interval and displays
 */
var heartbeat_state = setInterval(function() {
	$.ajax({
		url : 'sensor/hbstate',
		type : 'GET',
		dataType : 'json',
		success : function(hbState) {
			showHbNotification(parseHbState(hbState));
		}
	});
}, 60000);

function parseHbState(hbState){
	
	if(hbState.status === false){ return ERROR_STATUS }
	if(hbState.status === true) { return SUCCESS_STATUS}
}

/**
 * Displays the message about the heart-beat status in an alert box
 * 
 * @param heartBeat -
 *            JSON representing the heart-beat state
 */
function showHbNotification(status) {
	
	if (status === ERROR_STATUS) {
		$('#current_state_notification').removeClass().addClass(ALERT_ERROR_CLASS);
		$('#notification_icon').removeClass().addClass(ERROR_ICON);
		$('#notification_title').html(ALERT_ERROR_TITLE);
		$('#notification_text').html(ERROR_MESSAGE);
	}
	if(status === SUCCESS_STATUS){
		$('#current_state_notification').removeClass().addClass(ALERT_SUCCESS_CLASS);
		$('#notification_icon').removeClass().addClass(SUCCESS_ICON);
		$('#notification_title').html(ALERT_SUCCESS_TITLE);
		$('#notification_text').html(OK_MESSAGE);
	}
}

/**
 * Handles the hiding of the alert box
 */
$('.close').click(function() {

	   $('.alert').hide(200);

	})
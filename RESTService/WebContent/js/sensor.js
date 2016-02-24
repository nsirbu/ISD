/**
 * This file contains the main methods for requesting the data about sensors
 * from the server and displaying it on the home-page
 * 
 * @author sscerbatiuc
 */
// ----------------------------------------------------------------------------
// ------------------- GLOBAL VARIABLES ---------------------------------------
// ----------------------------------------------------------------------------
// Temporary JSON Object, that holds the last sensors' state information
var lastSensorState = {
	timeReceived : "",
	lightSensorVal : 0,
	pirSensorVal : false
};

// Constants used for determining the status of an operation
var ERROR_STATUS = -1;
var INFO_STATUS = 0;
var SUCCESS_STATUS = 1;

// Font awesome icons, which are used in alert boxes (current state section)
var INFO_ICON = "fa fa-info-circle";
var ERROR_ICON = "fa fa-exclamation-circle";
var SUCCESS_ICON = "fa fa-check-circle";

// Alert boxes titles
var ALERT_INFO_TITLE = "Aloha!";
var ALERT_ERROR_TITLE = "Oh snap!";
var ALERT_SUCCESS_TITLE = "Yay!";

// Alert boxes CSS classes
var ALERT_INFO_CLASS = "alert alert-info alert-dismissible";
var ALERT_ERROR_CLASS = "alert alert-danger alert-dismissible";
var ALERT_SUCCESS_CLASS = "alert alert-success alert-dismissible";

// Alert boxes messages
var OK_MESSAGE = "Everything is OK";
var ERROR_MESSAGE = "The last message was late. The board is not sending messages "
		+ "in the specified interval";

// ----------------------------------------------------------------------------
// ----------------------------------------------------------------------------

// ------------------- Animating TEXT-SHADOW property -------------------------
$.fx.step.textShadowBlur = function(fx) {
	$(fx.elem).prop('textShadowBlur', fx.now).css({
		textShadow : '0 0 ' + Math.floor(fx.now) + 'px #960A31'
	});
};

// ----------------------------------------------------------------------------
// ------------------------ PAGE ONLOAD Event --------------------------------
// ----------------------------------------------------------------------------
$(document).ready(function() {
	displayHbFrequencyValue();
	displayLightThresholdValue();
});

// ----------------------------------------------------------------------------
// ------------------ Checking SENSORS' CURRENT VALUES ------------------------
// ----------------------------------------------------------------------------

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
	pulsateUpdatedElement("#time_value");
	$("#light_value").html(message.lightSensorVal + " lx");
	pulsateUpdatedElement("#light_value");
	lastSensorState.lightSensorVal = message.lightSensorVal;
	if (message.pirSensorVal == false) {
		$("#pir_value").html("Nobody's moving");
	} else {
		$("#pir_value").html("Movement detected");
	}
	pulsateUpdatedElement("#pir_value");
	lastSensorState.pirSensorVal = message.pirSensorVal;
}

/**
 * Animates the element being updated to focus it on the page. This method
 * animates the text-shadow property of the element with the given
 * <code>id</code>.
 * 
 * @param elementId
 *            {String} the ID of the HTML element
 */
function pulsateUpdatedElement(elementId) {

	$(elementId).animate({
		textShadowBlur : 20
	}, {
		duration : 300,
		complete : function() {
			$(elementId).animate({
				textShadowBlur : 0
			}, {
				duration : 500
			});
		}
	});

}

// ----------------------------------------------------------------------------
// ------------------ Checking HEART-BEAT STATUS ------------------------------
// ----------------------------------------------------------------------------
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
}, 3000);

/**
 * Checks the status of the last message received from the sensors. It checks
 * the information about the last message and if the last message time is
 * greater than the minimal frequency interval, it returns an object with
 * <code>false</code> status
 * 
 * @param hbState
 *            JSON Object containing the information about the last received
 *            message
 * @returns {Number} 1 - OK; 0 - Info; -1 - Error;
 */
function parseHbState(hbState) {

	if (hbState.status === false) {
		return ERROR_STATUS
	}
	if (hbState.status === true) {
		return SUCCESS_STATUS
	}
}

/**
 * Displays the message about the heart-beat status in an alert box
 * 
 * @param heartBeat -
 *            JSON representing the heart-beat state
 */
function showHbNotification(status) {

	if (status === ERROR_STATUS) {
		$('#current_state_notification').removeClass().addClass(
				ALERT_ERROR_CLASS);
		$('#notification_icon').removeClass().addClass(ERROR_ICON);
		$('#notification_title').html(ALERT_ERROR_TITLE);
		$('#notification_text').html(ERROR_MESSAGE);
	}
	if (status === SUCCESS_STATUS) {
		$('#current_state_notification').removeClass().addClass(
				ALERT_SUCCESS_CLASS);
		$('#notification_icon').removeClass().addClass(SUCCESS_ICON);
		$('#notification_title').html(ALERT_SUCCESS_TITLE);
		$('#notification_text').html(OK_MESSAGE);
	}
}

/**
 * Checks the current frequency value stored on the server and displays it in
 * the page, to the right of the label in the settings box.
 */
function displayHbFrequencyValue() {
	$.ajax({
		url : 'sensor/settings/getHBFrequency',
		type : 'GET',
		dataType : 'json',
		success : function(hbState) {
			$('#hb_thresh_val').html(hbState.HBFrequency + " s.");
			console.log(hbState.HBFrequency);
			return hbState.HBFrequency;
		}
	});
}

//----------------------------------------------------------------------------
//----------------- Checking LIGHT LEVEL THRESHOLD VAL -----------------------
//----------------------------------------------------------------------------

function displayLightThresholdValue(){
	$.ajax({
		url : 'sensor/settings/getLightThreshold',
		type : 'GET',
		dataType : 'json',
		success : function(lightThresh) {
			$('#light_thresh_val').html(lightThresh.lightThreshold + " lx");
			console.log(lightThresh.HBFrequency);
			return lightThresh.HBFrequency;
		}
	});
}

// ----------------------------------------------------------------------------
// ----------------- Updating SERVER'S THRESHOLD VALUES -----------------------
// ----------------------------------------------------------------------------
/**
 * Updates the heart-beat frequency value
 */
function updateHbRate() {
	var statusIcon = '#hb_update_status';
	var hbRate = $('#hb_frequency_input').val();
	if (validateNumberInput(hbRate)) {
		var object = {};
		object.HBFrequency = hbRate;
		$.ajax({
			type : "POST",
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			url : "sensor/settings/setHBFrequency",
			data : JSON.stringify(object),
			contentType : "application/json",
			statusCode : {
				200 : function() {
					displayUpdateStatus(statusIcon, SUCCESS_STATUS);
				},
				404 : function() {
					displayUpdateStatus(statusIcon, ERROR_STATUS);
				},
				401 : function() {
					displayUpdateStatus(statusIcon, ERROR_STATUS);
				},
			},
			error : function() {
				console.log("error");
			}
		});
	} else {
		alert("Please enter a number between 10 and 999");
		displayUpdateStatus(statusIcon, ERROR_STATUS);
	}

}

/**
 * Updates the light level threshold specified by the user. This method executes
 * an asynchronous POST request, containing the new value to the server.
 */

function updateLightTreshVal() {
	var statusIcon = '#light_update_status';
	var lightLevel = $('#light_threshold_input').val();
	console.log("Checking: " + validateNumberInput(lightLevel));
	if (validateNumberInput(lightLevel) === true) {
		var object = {};
		object.lightThreshold = lightLevel;
		$.ajax({
			type : "POST",
			headers : {
				'Accept' : 'application/json',
				'Content-Type' : 'application/json'
			},
			url : "sensor/settings/setLightThreshold",
			data : JSON.stringify(object),
			contentType : "application/json",
			statusCode : {
				200 : function() {
					displayUpdateStatus(statusIcon, SUCCESS_STATUS);
				}
			},
			success : function(data) {
				// console.log(data);
			},
			error : function() {
				console.log("error");
			}
		});
	} else {
		alert("Please enter a number between 10 and 999");
		displayUpdateStatus(statusIcon, ERROR_STATUS);
	}

}

/**
 * Indicates the status of an operation by placing the respective icon into the
 * element with the specified id, usually <code><i></code>.
 * 
 * @param elementId
 *            {String} The id of the HTML element which will hold the status
 *            icon (<i>Ex: check, cross.</i>
 * @param requestStatus
 *            {Integer} Representing the status of the operation: -1 - error; 1
 *            success;
 */

function displayUpdateStatus(elementId, requestStatus) {
	switch (requestStatus) {
	case SUCCESS_STATUS: {
		$(elementId).removeClass().addClass('fa fa-check fa-2x');
		$(elementId).css("color", "#00F511");
		$(elementId).fadeTo(400, 1);
		setTimeout(function() {
			$(elementId).fadeTo(300, 0);
		}, 3000)
		break;
	}
	case ERROR_STATUS: {
		$(elementId).removeClass().addClass('fa fa-times fa-2x');
		$(elementId).css("color", "red");
		$(elementId).fadeTo(400, 1);
		setTimeout(function() {
			$(elementId).fadeTo(300, 0);
		}, 3000)
		break;
	}
	default:
		break;
	}
}

// ----------------------------------------------------------------------------
// ---------------------- VALIDATORS -----------------------------------------
// -----------------------------------------------------------------------------
/**
 * Validates the user input values for heart-beat & light levels threshold
 * 
 */
function validateNumberInput(value) {
	var numberRegex = new RegExp("^[1-9]\\d{1,2}");
	return numberRegex.test(parseInt(value));
}

// ----------------------------------------------------------------------------
// ----------------------- END OF FILE ---------------------------------------
// ----------------------------------------------------------------------------

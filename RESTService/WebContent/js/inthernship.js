/**
 * This file contains the main methods for manipulating
 * the content of the web page. Animating boxes, displaying hided
 * content, dynamically modifying the content of the page.
 * 
 * It is divided in the sections, described using comments.
 */

//----------------------------------------------------------------------
//---------------------- CURRENT STATE ---------------------------------
//----------------------------------------------------------------------
/**
 * Displays the box with more information about the sensor values
 * <b>Section:</b> sensor current state.
 */
function showCurrentStateInfo(element) {

	$('#current_state_info').collapse('toggle');
	$('#current_state_info').on('hidden.bs.collapse', function() {
		$('#current_state_info_button').html("What are these values?");
	});
	$('#current_state_info').on('shown.bs.collapse', function() {
		$('#current_state_info_button').html("Got it!");
	});
}
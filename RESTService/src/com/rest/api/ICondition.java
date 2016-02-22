package com.rest.api;

import com.model.Message;

public interface ICondition {
	/**
	 * Check if the current message contains data indicating whether the
	 * light was turned on or off.
	 * 
	 * @param message
	 *            the message to inspect
	 * @param lightThreshold
	 *            the light value with which the comparison is made to
	 *            detect if at the moment there is or not any motion in the
	 *            room
	 * @param isStartTimeSeted
	 *            boolean value that shows if it can calculate the time
	 *            difference between the time moment when the motion starts
	 *            and the time moment when the motion ends to determine how
	 *            long occurred the motion.
	 * @return true or false
	 */
	boolean isTrueFor(Message message, int lightThreshold, boolean isStartTimeSeted);

	/**
	 * Check if the current message contains data indicating whether the
	 * motion was detected or ended.
	 * 
	 * @param message
	 *            the message to inspect
	 * @param isStartTimeSeted
	 *            boolean value that shows if it can calculate the time
	 *            difference between the time moment when the motion starts
	 *            and the time moment when the motion ends to determine how
	 *            long occurred the motion.
	 * @return true or false
	 */
	boolean isTrueFor(Message message, boolean isStartTimeSeted);
}

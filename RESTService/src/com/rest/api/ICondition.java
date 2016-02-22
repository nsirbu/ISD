package com.rest.api;

import com.model.Message;

public interface ICondition {
	boolean isTrueFor(Message message, int lightThreshold, boolean isStartTimeSeted);

	boolean isTrueFor(Message message, boolean isStartTimeSeted);
}

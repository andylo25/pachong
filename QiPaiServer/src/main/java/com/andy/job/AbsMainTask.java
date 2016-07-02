package com.andy.job;

import com.andy.ssft.cron4j.Task;

/**
 * This task counts from 1 to 30.
 */
public abstract class AbsMainTask extends Task {

	protected String schedulingPattern;

	public String getSchedulingPattern() {
		return schedulingPattern;
	}

	public void setSchedulingPattern(String schedulingPattern) {
		this.schedulingPattern = schedulingPattern;
	}
	
	
	
}
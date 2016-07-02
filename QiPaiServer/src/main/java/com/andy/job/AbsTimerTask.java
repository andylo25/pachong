package com.andy.job;

import java.util.TimerTask;

import org.apache.log4j.Logger;

public abstract class AbsTimerTask extends TimerTask {
	private static final Logger logger = Logger.getLogger(AbsTimerTask.class);
	@Override
	public void run() {
		try {
			doRun();
		} catch (Throwable e) {
			logger.error("执行计划任务异常",e);
		}
	}
	
	public abstract void doRun();

}

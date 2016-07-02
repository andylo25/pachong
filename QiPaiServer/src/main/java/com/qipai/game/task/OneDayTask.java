package com.qipai.game.task;

import java.util.Random;

import com.andy.job.AbsMainTask;
import com.andy.ssft.cron4j.TaskExecutionContext;
import com.andy.util.RandomUtils;

public class OneDayTask extends AbsMainTask{

	public OneDayTask(String schedulingPattern) {
		this.schedulingPattern = schedulingPattern;
	}
	
	@Override
	public void execute(TaskExecutionContext context) throws RuntimeException {
		// 刷新随机数
		RandomUtils.random = new Random();
		
		
	}

}

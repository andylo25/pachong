package com.qipai.game.task;

import com.andy.job.AbsMainTask;
import com.andy.ssft.cron4j.TaskExecutionContext;
import com.qipai.game.action.GameAction;

public class RankTask extends AbsMainTask{

	public RankTask(String schedulingPattern) {
		this.schedulingPattern = schedulingPattern;
	}
	
	@Override
	public void execute(TaskExecutionContext context) throws RuntimeException {
		GameAction.refreshRank();
	}

}

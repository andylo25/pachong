package com.qipai.game.task;

import com.andy.job.AbsTimerTask;
import com.andy.job.TaskManager;
import com.andy.job.TimerManager;
import com.qipai.game.model.PopupMsg;
import com.qipai.util.QPC;

public class TaskUtil {

	public static void initTask(){
		// 弹屏
		TimerManager.schedule(new AbsTimerTask() {
			@Override
			public void doRun() {
				PopupMsg.instance().refreshPopup();
			}
		}, 30*1000,30*1000);
		
		// 奖励公告
		TimerManager.schedule(new AbsTimerTask() {
			@Override
			public void doRun() {
				PopupMsg.instanceGG().refreshPopup();
			}
		}, 40*1000,30*1000);
		
		TaskManager.getInstance().addToRun(QPC.TASK_RANK, new RankTask("0 0 * * *"));//每天一次排行榜刷新
		TaskManager.getInstance().addToRun(QPC.TASK_DAY, new OneDayTask("0 0 * * *"));//每天计划
	}
}

package com.andy.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimerManager {

	private Timer myTimer;
	private static TimerManager self = new TimerManager();
	
	public TimerManager() {
		myTimer = new Timer();
	}
	
	public static void schedule(TimerTask task,long delay){
		self.myTimer.schedule(task, delay);
	}
	
	public static void schedule(TimerTask task,long delay,long period){
		self.myTimer.schedule(task, delay, period);	
	}

	public static void stop() {
		self.myTimer.cancel();
	}
	
}

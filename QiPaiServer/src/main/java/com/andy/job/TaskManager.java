package com.andy.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.andy.ssft.cron4j.Scheduler;
import com.jfinal.plugin.IPlugin;

public class TaskManager implements IPlugin {

	private static final Logger logger = Logger.getLogger(TaskManager.class);
	private Scheduler scheduler;
	private static TaskManager self = new TaskManager();
	private Map<Byte, String> taskKeys = new HashMap<Byte, String>();

	private TaskManager() {
		scheduler = new Scheduler();
	}

	public static TaskManager getInstance() {
		if (self == null) {
			self = new TaskManager();
		}
		return self;
	}

	public String addToRun(byte taskType, AbsMainTask task) {
		if (task != null) {
			String taskId = scheduler.schedule(task.getSchedulingPattern(), task);
			taskKeys.put(taskType, taskId);
			return taskId;
		}
		return null;
	}

	public boolean refreshTask(byte taskType, String pattern) {
		String taskId = taskKeys.get(taskType);
		if (taskId != null) {
			scheduler.reschedule(taskId, pattern);
			return true;
		}
		return false;
	}

	public List<String> addAllToRun(List<AbsMainTask> tasks) {
		if (tasks != null) {
			List<String> ids = new ArrayList<String>();
			for (AbsMainTask task : tasks) {
				ids.add(scheduler.schedule(task.getSchedulingPattern(), task));
			}
			return ids;
		}
		return null;
	}

	public boolean start() {
		scheduler.start();
		if (logger.isDebugEnabled()) {
			logger.debug("TaskManager started...");
		}
		return true;
	}

	public boolean stop() {
		scheduler.stop();
		if (logger.isDebugEnabled()) {
			logger.debug("TaskManager stoped...");
		}
		return true;
	}

}
